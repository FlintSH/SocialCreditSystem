package com.earthpol.socialcredit;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Indicators {

    public void positiveSound(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.5f, 1.0f);
    }

    public void errorSound(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 5f, 1.2f);
    }

    public void negativeSound(Player player){
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_HIT, 5f, 0.6f);
    }

    public void interactMenuSound(Player player){
        player.playSound(player.getLocation(), Sound.UI_STONECUTTER_SELECT_RECIPE, 5f, 1.0f);
    }

}
