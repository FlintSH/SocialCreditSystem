package com.earthpol.socialcredit.commands;

import com.earthpol.socialcredit.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SocialCreditCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Main instance = Main.getInstance();

        if (sender instanceof Player player) {
            UUID playerUUID = player.getUniqueId();
            instance.indicator.positiveSound(player);
            TextComponent vote = new TextComponent("                                  §4[§cView Social Credits§4]                    ");
            vote.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Opens link to your social credit profile on your web browser.").create()));
            vote.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://earthpol.com/social/credits.php?uuid=" + playerUUID + "&key=" + instance.data.getCCPKey(playerUUID)));
            player.sendMessage("");
            player.sendMessage("§e=================  [ §5CCP SOCIAL CREDITS §e] ================");
            /* 2 */
            player.sendMessage("");
            /* 3 */
            player.sendMessage("");
            /* 4 */
            player.sendMessage("");
            /* 5 */
            player.sendMessage("                                §eClick Below To                   ");
            /* 6 */
            player.sendMessage(vote);
            /* 7 */
            player.sendMessage("");
            /* 8 */
            player.sendMessage("");
            /* 9 */
            player.sendMessage("");
            /* 10 */
            player.sendMessage("");
        } else {
            Main.log.info(Main.prefix + " You must be a player to execute this command.");
        }

        return false;
    }
}
