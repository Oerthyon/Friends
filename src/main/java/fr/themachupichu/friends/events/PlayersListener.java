package fr.themachupichu.friends.events;

import fr.themachupichu.friends.Main;
import fr.themachupichu.friends.api.Player;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class PlayersListener implements Listener {
    @EventHandler
    public void onLogin(PostLoginEvent event) {

        ProxiedPlayer player = event.getPlayer();

        if(Main.getInstance().configuration.get(player.getUniqueId() + "") == null) {
            Main.getInstance().configuration.set(player.getUniqueId() + ".friends", new ArrayList<>());
            Main.getInstance().configuration.set(player.getUniqueId() + ".invites", new ArrayList<>());
            Main.getInstance().configuration.set(player.getUniqueId() + ".username", player.getName());
            Main.getInstance().configuration.set(player.getUniqueId() + ".invited", new ArrayList<>());
            List<String> list = Main.getInstance().configuration.getStringList("players.list");
            list.add("" + player.getUniqueId());
            Main.getInstance().configuration.set("players.list", list);
            Main.getInstance().saveConfig();
        } else {

            for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                Player fplayers = new Player(players.getUniqueId().toString());
                if(fplayers.getFriends().contains(player.getUniqueId().toString())) {
                    players.sendMessage("§3§lFriends §8» §a§l" + player.getName() + " §fs'est connecté.");
                }
            }

        }

    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();
        for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
            Player fplayers = new Player(players.getUniqueId().toString());
            if(fplayers.getFriends().contains(player.getUniqueId().toString())) {
                players.sendMessage("§3§lFriends §8» §c§l" + player.getName() + " §fs'est déconnecté.");
            }
        }
    }

}
