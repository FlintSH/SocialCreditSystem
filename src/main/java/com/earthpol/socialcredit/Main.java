package com.earthpol.socialcredit;

import com.earthpol.socialcredit.commands.CCPSystemCommand;
import com.earthpol.socialcredit.commands.SocialCreditCommand;
import com.earthpol.socialcredit.listeners.PlayerJoin;
import com.earthpol.socialcredit.sql.MySQL;
import com.earthpol.socialcredit.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public MySQL SQL;
    public SQLGetter data;
    public Indicators indicator;
    public static String prefix = "§4[§cSocial Credit System§4]: ";
    public static Logger log = Bukkit.getLogger();
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        log.info(prefix + "§eHail to the CCP");
        log.info(prefix + "§eInitializing MySQL Class");
        instance.SQL = new MySQL();
        instance.data = new SQLGetter(instance);
        instance.indicator = new Indicators();
        log.info(prefix + "§eInitializing Listeners");
        setupListeners();
        log.info(prefix + "§eInitializing Commands");
        setupCommands();
        log.info(prefix + "§eConnecting to Database...");
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            log.info(prefix + "§cDatabase Connection Failed");
        }

        if(SQL.isConnected()){
            log.info(prefix + "§eDatabase Connection Successful");
            data.createTable();
        }

        log.info(prefix + "§eScoail Credit System has loaded successfully.");

    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoin(), instance);
    }

    private void setupCommands(){
        Objects.requireNonNull(getCommand("ccpsystem")).setExecutor(new CCPSystemCommand());
        Objects.requireNonNull(getCommand("socialcredits")).setExecutor(new SocialCreditCommand());
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }
}
