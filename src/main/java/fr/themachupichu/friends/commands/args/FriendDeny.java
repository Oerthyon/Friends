package fr.themachupichu.friends.commands.args;

import fr.themachupichu.friends.Main;
import fr.themachupichu.friends.api.Player;
import fr.themachupichu.friends.commands.FriendsCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class FriendDeny {
    public FriendDeny(ProxiedPlayer player, String[] args) {

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

        if(!fplayer.getInvites().contains(ftarget.getUUID())) {
            player.sendMessage("§3§lFriends §8» §cVous n'avez pas de requêtes de ce joueur.");
            return;
        }

        if(fplayer.heInvited(ftarget.getUUID())) {
            player.sendMessage("§3§lFriends §8» §cVous n'avez pas de requêtes de ce joueur.");
            return;
        }



        if(fplayer.getFriends().contains(ftarget.getUUID())) {
            player.sendMessage("§3§lFriends §8» §cVous êtes déjà ami avec ce joueur.");
            return;
        }

        fplayer.setInvited(ftarget.getUUID(), false);
        ftarget.setInvited(fplayer.getUUID(), false);
        fplayer.setHeInvited(ftarget.getUUID(), false);
        ftarget.setHeInvited(fplayer.getUUID(), false);
        if(ProxyServer.getInstance().getPlayer(UUID.fromString(ftarget.getUUID())) != null) {
            ProxiedPlayer playerTarget = ProxyServer.getInstance().getPlayer(UUID.fromString(ftarget.getUUID()));
            playerTarget.sendMessage("§3§lFriends §8» §c§l" + ftarget.getName() + " §fa refusé votre demande d'ami.");

        }
        player.sendMessage("§3§lFriends §8» §fVous avez refusé la demande d'ami de §c§l" + player.getName());


    }
}
