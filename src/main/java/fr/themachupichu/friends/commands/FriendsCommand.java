package fr.themachupichu.friends.commands;

import com.mysql.jdbc.StringUtils;
import fr.themachupichu.friends.commands.args.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsCommand extends Command implements TabExecutor {

    public FriendsCommand() {
        super("friends", "oerthyon.friends", "f", "friend", "ami", "amis");
    }

    public static void sendHelpCommand() {
        player.sendMessage("§7§m-------------------------------");
        player.sendMessage(" §3Aide /friend");
        player.sendMessage("  §3§l» §7/f add <joueur>");
        player.sendMessage("  §3§l» §7/f accept <joueur>");
        player.sendMessage("  §3§l» §7/f deny <joueur>");
        player.sendMessage("  §3§l» §7/f delete <joueur>");
        player.sendMessage("  §3§l» §7/f invites");
        player.sendMessage("  §3§l» §7/f list");
        player.sendMessage("  §3§l» §7/f help");
        player.sendMessage("§7§m-------------------------------");
    }

    static ProxiedPlayer player;

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if(!(commandSender instanceof ProxiedPlayer)) return;

        player = (ProxiedPlayer) commandSender;

        if(args.length == 0) {
            sendHelpCommand();
            return;
        }

        if(args[0].equalsIgnoreCase("help")) {
            sendHelpCommand();
        } else if(args[0].equalsIgnoreCase("add")) {
            new FriendAdd(player, args);
        } else if(args[0].equalsIgnoreCase("accept")) {
            new FriendAccept(player, args);
        } else if(args[0].equalsIgnoreCase("deny")) {
            new FriendDeny(player, args);
        } else if(args[0].equalsIgnoreCase("invites")) {
            new FriendsInvites(player, args);
        } else if(args[0].equalsIgnoreCase("list")) {
            new FriendList(player, args);
        } else if(args[0].equalsIgnoreCase("delete")) {
            new FriendDelete(player, args);
        } else {
            sendHelpCommand();
        }

    }


    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        List<String> commandsArgs = new ArrayList<>(Arrays.asList("help", "add", "accept", "deny", "invites", "list", "delete"));
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String toComplete = args[0].toLowerCase();
            for (String string : commandsArgs) {
                if (StringUtils.startsWithIgnoreCase(string, toComplete)) {
                    completions.add(string);
                }
            }
        } else {
            for (ProxiedPlayer string : ProxyServer.getInstance().getPlayers()) {
                if (StringUtils.startsWithIgnoreCase(string.getName(), args[1].toLowerCase())) {
                    completions.add(string.getName());
                }
            }
        }
        return completions;
    }
}
