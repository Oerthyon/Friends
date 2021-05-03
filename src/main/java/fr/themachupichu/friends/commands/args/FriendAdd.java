package fr.themachupichu.friends.commands.args;

import fr.themachupichu.friends.Main;
import fr.themachupichu.friends.api.Player;
import fr.themachupichu.friends.commands.FriendsCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class FriendAdd {
    public  FriendAdd(ProxiedPlayer player, String[] args) {

        if(args.length != 2) {
            FriendsCommand.sendHelpCommand();
            return;
        }

        String target = args[1];

        boolean exists = false;
        Player ftarget = null;

        for(String string : Player.getAllPlayers()) {
            String username = Main.getInstance().configuration.getString(string + ".username");
            if(username.equals(target)) {
                exists = true;
                ftarget = new Player(string);
            }
        }

        if(!exists) {
            player.sendMessage("§3§lFriends §8» §cCe joueur n'existe pas.");
            return;
        }

        Player fplayer = new Player(player.getUniqueId().toString());

        if(fplayer.getFriends().contains(ftarget.getUUID())) {
            player.sendMessage("§3§lFriends §8» §cCe joueur est déjà votre ami.");
            return;
        }

        if(fplayer.getInvites().contains(ftarget.getUUID())) {
            player.sendMessage("§3§lFriends §8» §cVous avez déjà envoyé une demande à ce joueur.");
            return;
        }

        ftarget.setInvited(fplayer.getUUID(), true);
        fplayer.setInvited(ftarget.getUUID(), true);
        fplayer.setHeInvited(ftarget.getUUID(), true);
        if(ProxyServer.getInstance().getPlayer(UUID.fromString(ftarget.getUUID())) != null) {
            ProxiedPlayer playerTarget = ProxyServer.getInstance().getPlayer(UUID.fromString(ftarget.getUUID()));
            playerTarget.sendMessage(" ");
            playerTarget.sendMessage("§3§lFriends §8» §fDemande d'ami de §a§l" + player.getName());
            ComponentBuilder accepter = new ComponentBuilder("            §8[§aAccepter§8]  ")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f accept " + player.getName()));
            ComponentBuilder refuser = new ComponentBuilder(" §8[§cRefuser§8]")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f deny " + player.getName()));
            playerTarget.sendMessage(accepter.getCurrentComponent(), refuser.getCurrentComponent());
            playerTarget.sendMessage(" ");
        }

        player.sendMessage("§3§lFriends §8» §fVous avez envoyé une demande d'ami à §a§l" + args[1]);

    }
}
