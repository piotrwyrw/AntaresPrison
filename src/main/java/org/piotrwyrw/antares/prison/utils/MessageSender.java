package org.piotrwyrw.antares.prison.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;

/**
 * This class helps to send messages more precisely.
 */

@SuppressWarnings("unused")
public class MessageSender {

    /**
     * Send a single-line-message to all admins
     * @param message
     * @param withPrefix
     */
    public void toAllAdmins(String message, boolean withPrefix) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(PermissionConstants.RECEIVE_ADMIN_MESSAGES)) {
                p.sendMessage(MessageConstants.construct(message, withPrefix));
            }
        }
    }

    /**
     * Send a multi-line-message to all admins
     * @param message
     */
    public void toAllAdmins(String[] message, boolean withPrefix) {
        for (String line : message) {
            toAllAdmins(line, withPrefix);
        }
    }

    /**
     * Send a single-line-message to all players with specified permission
     * @param message
     * @param permission
     * @param withPrefix
     */
    public void toEveryone(String message, String permission, boolean withPrefix) {
        PrisonsUsers users = AntaresPrison.getInstance().users;
        for (Player p : Bukkit.getOnlinePlayers()) {
            PrisonsUser user = users.getUser(p);
            if (p.getWorld() != AntaresPrison.getInstance().world)
                continue;
            if (p.hasPermission(permission)) {
                user.setLastMessage("", false);
                users.updateUser(user);
                p.sendMessage(MessageConstants.construct(message, withPrefix));
            }
        }
    }

    /**
     * Send a multi-line-message to all players with specified permission
     * @param message
     * @param permission
     * @param withPrefix
     */
    public void toEveryone(String[] message, String permission, boolean withPrefix) {
        for (String line : message) {
            toEveryone(line, permission, withPrefix);
        }
    }

    /**
     * Send a single-line-message to all players
     * @param message
     * @param withPrefix
     */
    public void toEveryone(String message, boolean withPrefix) {
        PrisonsUsers users = AntaresPrison.getInstance().users;
        for (Player p : Bukkit.getOnlinePlayers()) {
            PrisonsUser user = users.getUser(p);
            if (p.getWorld() != AntaresPrison.getInstance().world)
                continue;
            user.setLastMessage("", false);
            users.updateUser(user);
            p.sendMessage(MessageConstants.construct(message, withPrefix));
        }
    }

    /**
     * Sebd a nulti-line-message to all players
     * @param message
     * @param withPrefix
     */
    public void toEveryone(String[] message, boolean withPrefix) {
        for (String line : message) {
            toEveryone(line, withPrefix);
        }
    }

    /**
     * Send a single-line-message to a single player
     * @param message
     * @param player
     * @param withPrefix
     */
    public void toPlayer(String message, CommandSender player, boolean withPrefix) {
        PrisonsUsers users = AntaresPrison.getInstance().users;
        if (player instanceof Player) {
            PrisonsUser user = users.getUser((Player) player);
            if (user.getLastMessage().equalsIgnoreCase(message))
                return;
            else {
                user.setLastMessage(message, true);
                users.updateUser(user);
            }
        }
        player.sendMessage(MessageConstants.construct(message, withPrefix));
    }

    public void toPlayer(String message, Player player, boolean withPrefix) {
        toPlayer(message, (CommandSender)player, withPrefix);
    }

    /**
     * Send a multi-line-message to a single player
     * @param message
     * @param player
     * @param withPrefix
     */
    public void toPlayer(String[] message, CommandSender player, boolean withPrefix) {
        for (String line : message) {
            toPlayer(line, player, withPrefix);
        }
    }

    public void toPlayer(String[] message, Player player, boolean withPrefix) {
        toPlayer(message, (CommandSender)player, withPrefix);
    }

}
