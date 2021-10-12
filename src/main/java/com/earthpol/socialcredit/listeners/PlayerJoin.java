package com.earthpol.socialcredit.listeners;

import com.earthpol.socialcredit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoin implements Listener {

    private static Main instance = Main.getInstance();

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(!instance.data.exists(playerUUID)){
            instance.data.createPlayer(player);
        }
        if(instance.data.exists(playerUUID)){
            //Generate a new vote key on every login.
            instance.data.createCCPKey(playerUUID);

            if(!Objects.equals(instance.data.getName(player), player.getName())){
                instance.data.updateName(player);
            }
        }
    }

}
