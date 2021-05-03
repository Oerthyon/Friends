package fr.themachupichu.friends;

import com.google.common.io.ByteStreams;
import fr.themachupichu.friends.commands.FriendsCommand;
import fr.themachupichu.friends.events.PlayersListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class Main extends Plugin {

    private static Main instance;

    public Configuration configuration;

    @Override
    public void onEnable() {

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new FriendsCommand());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayersListener());

        instance = this;

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            File configFile = new File(getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();
                    try (InputStream is = getResourceAsStream("config.yml");
                         OutputStream os = new FileOutputStream(configFile)) {
                        ByteStreams.copy(is, os);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Unable to create configuration file", e);
                }
            }
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onEnable();
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Main getInstance() {
        return instance;
    }
}
