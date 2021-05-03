package fr.themachupichu.friends.api;

import fr.themachupichu.friends.Main;

import java.util.List;

public class Player {

    private final String uuid;

    public Player(String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    public static List<String> getAllPlayers() {
        return Main.getInstance().configuration.getStringList("players.list");
    }

    public String getName() {
        return Main.getInstance().configuration.getString(uuid + ".username");
    }

    public List<String> getFriends() {
        return Main.getInstance().configuration.getStringList(uuid + ".friends");
    }

    public List<String> getInvites() {
        return Main.getInstance().configuration.getStringList(uuid + ".invites");
    }

    public void setFriend(String uuid, boolean b) {
        List<String> list = getFriends();
        if(b) {
            list.add(uuid);
        } else {
            list.remove(uuid);
        }
        Main.getInstance().configuration.set(this.uuid + ".friends", list);
        Main.getInstance().saveConfig();
    }

    public boolean heInvited(String uuid) {
        if(Main.getInstance().configuration.getStringList(getUUID() + ".invited").contains(uuid)) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getHeInvited() {
        return Main.getInstance().configuration.getStringList(getUUID() + ".invited");
    }

    public void setHeInvited(String uuid, boolean b) {
        List<String> list = getHeInvited();
        if(b) {
            list.add(uuid);
        } else {
            list.remove(uuid);
        }
        Main.getInstance().configuration.set(this.uuid + ".invited", list);
        Main.getInstance().saveConfig();
    }

    public void setInvited(String uuid, boolean b) {
        List<String> list = getInvites();
        if(b) {
            list.add(uuid);
        } else {
            list.remove(uuid);
        }
        Main.getInstance().configuration.set(this.uuid + ".invites", list);
        Main.getInstance().saveConfig();
    }
}
