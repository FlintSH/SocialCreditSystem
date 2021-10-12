package com.earthpol.socialcredit.commands;

import com.earthpol.socialcredit.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CCPSystemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Main instance = Main.getInstance();
        if(sender instanceof Player){
            if(sender.hasPermission("earthpol.ccpsystem.admin")){
                Player player = (Player) sender;

                if(args.length > 0) {
                    switch (args[0]) {
                        case "purge":
                            Main.getInstance().data.emptyTable();
                            player.sendMessage(Main.prefix + "§eDatabase Emptied.");
                            instance.indicator.interactMenuSound(player);
                            break;
                        case "addpoints":
                            if (args[1].isEmpty() || args[2].isEmpty()) {
                                player.sendMessage(Main.prefix + "§eCorrect Usage: §c/ccpsystem addpoints <username> <amount>");
                            } else {
                                String username = args[1];
                                String addedPoints = args[2];
                                Player user;
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(username))) {
                                    user = Bukkit.getPlayer(username);
                                    if (user != null) {
                                        UUID userUUID = user.getUniqueId();
                                        int addPoints = Integer.parseInt(addedPoints);
                                        Main.getInstance().data.addSocialPoints(userUUID, addPoints);
                                        player.sendMessage(Main.prefix + "Added " + addedPoints + " to " + user.getName() + "'s Credits Points");
                                        instance.indicator.interactMenuSound(player);
                                        user.sendMessage(Main.prefix + addedPoints + " Social Credits have been added to your profile.");
                                        if(!user.getName().equals(player.getName()))
                                            instance.indicator.positiveSound(user);

                                    } else {
                                        player.sendMessage(Main.prefix + "§cAn error occurred. ");
                                        instance.indicator.errorSound(player);
                                        Main.log.warning(Main.prefix + "§cUser was equal to Null (/ccpsystem addpoints)");
                                    }
                                } else {
                                    player.sendMessage(Main.prefix + "§c" + username + " is not currently online.");
                                    instance.indicator.errorSound(player);
                                }

                            }
                            break;
                        case "setpoints":
                            if (args[1].isEmpty() || args[2].isEmpty()) {
                                player.sendMessage(Main.prefix + " §eCorrect Usage: §c/ccpsystem setpoints <username> <amount>");
                            } else {
                                String username = args[1];
                                String points = args[2];
                                Player user;
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(username))) {
                                    user = Bukkit.getPlayer(username);
                                    if (user != null) {
                                        UUID userUUID = user.getUniqueId();
                                        int setPoints = Integer.parseInt(points);

                                        Main.getInstance().data.setSocialPoints(userUUID, setPoints);
                                        player.sendMessage(Main.prefix + "Set " + user.getName() + "'s social points to " + setPoints);
                                        instance.indicator.interactMenuSound(player);
                                        user.sendMessage(Main.prefix + "Your Social Credits on your profile have been set to " + setPoints + ".");
                                        if(!user.getName().equals(player.getName()))
                                            instance.indicator.positiveSound(user);
                                    } else {
                                        player.sendMessage(Main.prefix + "§cAn error occurred. ");
                                        instance.indicator.errorSound(player);
                                        Main.log.warning(Main.prefix + "§cUser was equal to Null (/ccpsystem setpoints)");
                                    }
                                } else {
                                    player.sendMessage(Main.prefix + "§c" + username + " is not currently online.");
                                    instance.indicator.errorSound(player);
                                }
                            }
                            break;
                        case "removepoints":
                            if (args[1].isEmpty() || args[2].isEmpty()) {
                                player.sendMessage(Main.prefix + "§eCorrect Usage: §c/ccpsystem removepoints <username> <amount>");
                            } else {
                                String username = args[1];
                                String points = args[2];
                                Player user;
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(username))) {
                                    user = Bukkit.getPlayer(username);
                                    if (user != null) {
                                        UUID userUUID = user.getUniqueId();
                                        int removePoints = Integer.parseInt(points);

                                        Main.getInstance().data.removeSocialPoints(userUUID, removePoints);
                                        player.sendMessage(Main.prefix + "Removed " + removePoints + " from " + user.getName() + "'s Social Points");
                                        instance.indicator.interactMenuSound(player);
                                        user.sendMessage(Main.prefix + "" + removePoints + " Social Credits have been removed to your profile.");
                                        if(!user.getName().equals(player.getName()))
                                            instance.indicator.positiveSound(user);
                                    } else {
                                        player.sendMessage(Main.prefix + "§cAn error occurred. ");
                                        instance.indicator.errorSound(player);
                                        Main.log.warning(Main.prefix + "§cUser was equal to Null (/ccpsystem removepoints)");
                                    }
                                } else {
                                    player.sendMessage(Main.prefix + "§c" + username + " is not currently online.");
                                    instance.indicator.errorSound(player);
                                }
                            }
                            break;
                        case "remove":
                            if (args[1].isEmpty()) {
                                player.sendMessage(Main.prefix + "§eCorrect Usage: §c/ccpsystem remove <username>");
                            } else {
                                String username = args[1];
                                Player user;
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(username))) {
                                    user = Bukkit.getPlayer(username);
                                    if (user != null) {
                                        UUID userUUID = user.getUniqueId();
                                        Main.getInstance().data.removePlayer(userUUID);
                                        player.sendMessage(Main.prefix + "§e" + user.getName() + "'s profile has been removed.");
                                        instance.indicator.interactMenuSound(player);
                                        user.sendMessage(Main.prefix + "§eYour social profile has been removed.");
                                        if(!user.getName().equals(player.getName()))
                                            instance.indicator.positiveSound(user);
                                    } else {
                                        player.sendMessage(Main.prefix + "§cAn error occurred. ");
                                        instance.indicator.errorSound(player);
                                        Main.log.warning(Main.prefix + "§cUser was equal to Null (/ccpsystem remove)");
                                    }
                                } else {
                                    player.sendMessage(Main.prefix + "§c" + username + " is not currently online.");
                                    instance.indicator.errorSound(player);
                                }
                            }
                            break;
                        case "newkey":
                            if (args[1].isEmpty()) {
                                player.sendMessage(Main.prefix + "§eCorrect Usage: §c/ccpsystem newkey <username>");
                            } else {
                                String username = args[1];
                                Player user;
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(username))) {
                                    user = Bukkit.getPlayer(username);
                                    if (user != null) {
                                        UUID userUUID = user.getUniqueId();
                                        Main.getInstance().data.createCCPKey(userUUID);
                                        player.sendMessage(Main.prefix + "§eCreated New CCP Key for " + user.getName() + ".");
                                        player.sendMessage(Main.prefix + "§eKEY: " + Main.getInstance().data.getCCPKey(userUUID));
                                        instance.indicator.interactMenuSound(player);
                                        user.sendMessage(Main.prefix + "§eYour CCP Key was reset. ");
                                        user.sendMessage(Main.prefix +  "§ePlease type §c/socialcredits §eto obtain a valid link.");
                                        if(!user.getName().equals(player.getName()))
                                            instance.indicator.positiveSound(user);
                                    } else {
                                        player.sendMessage(Main.prefix + "§cAn error occurred. ");
                                        instance.indicator.errorSound(player);
                                        Main.log.warning(Main.prefix + "§cUser was equal to Null (/ccpsystem newkey)");
                                    }
                                } else {
                                    player.sendMessage(Main.prefix + "§c" + username + " is not currently online.");
                                    instance.indicator.errorSound(player);
                                }
                            }

                            break;
                        case "create":
                            if (args[1].isEmpty()) {
                                player.sendMessage(Main.prefix + " §eCorrect Usage: §c/ccpsystem create <username>");
                            } else {
                                String username = args[1];
                                Player user;
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(username))) {
                                    user = Bukkit.getPlayer(username);
                                    if (user != null) {
                                        Main.getInstance().data.createPlayer(user);
                                        player.sendMessage(Main.prefix + "§eCreated Player " + user.getName());
                                        instance.indicator.interactMenuSound(player);
                                        user.sendMessage(Main.prefix + "A new Social Credit profile has been created for you. Please type §c/socialcredits §bto gain access to this profile!");
                                        if(!user.getName().equals(player.getName()))
                                            instance.indicator.positiveSound(user);
                                    } else {
                                        player.sendMessage(Main.prefix + "§cAn error occurred. ");
                                        instance.indicator.errorSound(player);
                                        Main.log.warning(Main.prefix + "§cUser was equal to Null (/ccpsystem create)");
                                    }
                                } else {
                                    player.sendMessage(Main.prefix + "§c" + username + " is not currently online.");
                                    instance.indicator.errorSound(player);
                                }
                            }

                            break;
                        default:
                            sendMenu(player);
                            break;
                    }
                } else {
                    sendMenu(player);
                }


                return true;
            }
        }


        return false;
    }

    public void sendMenu(Player player) {
        player.sendMessage("§4========[§cCCP SYSTEM§4]========");
        player.sendMessage("§e/ccpsystem addpoints <username> <amount>");
        player.sendMessage("§e/ccpsystem setpoints <username> <amount>");
        player.sendMessage("§e/ccpsystem removepoints <username> <amount>");
        player.sendMessage("§e/ccpsystem create <username>");
        player.sendMessage("§e/ccpsystem remove <username>");
        player.sendMessage("§e/ccpsystem newkey <username>");
        player.sendMessage("§e/ccpsystem purge");
        player.sendMessage("§4===========================");
    }
}
