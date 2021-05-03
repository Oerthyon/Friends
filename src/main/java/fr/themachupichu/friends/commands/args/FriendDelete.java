package fr.themachupichu.friends.commands.args;

import fr.themachupichu.friends.Main;
import fr.themachupichu.friends.api.Player;
import fr.themachupichu.friends.commands.FriendsCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class FriendDelete {
    public FriendDelete(ProxiedPlayer player, String[] args) {

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

        if(!fplayer.getFriends().contains(ftarget.getUUID())) {
            player.sendMessage("§3§lFriends §8» §cCe joueur n'est pas votre ami.");
            return;
        }

        ftarget.setFriend(fplayer.getUUID(), false);
        fplayer.setFriend(ftarget.getUUID(), false);

        player.sendMessage("§3§lFriends §8» §fVous n'êtes désormais plus ami avec §c§l" + ftarget.getName());

    }
}
