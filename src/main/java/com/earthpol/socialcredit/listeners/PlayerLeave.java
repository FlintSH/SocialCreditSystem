package com.earthpol.socialcredit.listeners;

import com.earthpol.socialcredit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeave implements Listener {

    private static Main instance = Main.getInstance();

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(instance.data.exists(playerUUID)){
            //Generate a new vote key on login.
            instance.data.createCCPKey(playerUUID);
        }
    }
}