package fr.themachupichu.friends.commands.args;

import fr.themachupichu.friends.api.Player;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class FriendsInvites {
    public FriendsInvites(ProxiedPlayer player, String[] args) {

        Player fplayer = new Player(player.getUniqueId().toString());

        if(fplayer.getInvites().size() == 0) {
            player.sendMessage("§3§lAMI §f§l│ §fInvitations:");
            player.sendMessage(" ");
            player.sendMessage("§cVous n'avez aucune invitation :(");
            return;
        }

        player.sendMessage(" ");
        player.sendMessage("§3§lAMI §f§l│ §fInvitations:");
        for(String string : fplayer.getInvites()) {
            if(!fplayer.getHeInvited().contains(string)) {
                String name = new Player(string).getName();
                ComponentBuilder supprimer = new ComponentBuilder("§8[§c§l×§8]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f deny " + name));
                if(ProxyServer.getInstance().getPlayer(UUID.fromString(new Player(string).getUUID())) == null) {
                    player.sendMessage(new TextComponent("  "), supprimer.getCurrentComponent(), new TextComponent(" §7" + name));
                } else {
                    player.sendMessage(new TextComponent("  "), supprimer.getCurrentComponent(), new TextComponent(" §a" + name));
                }
            }

        }
        player.sendMessage(" ");

    }
}
