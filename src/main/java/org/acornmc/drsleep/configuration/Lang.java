package org.acornmc.drsleep.configuration;
import com.google.common.base.Throwables;
import org.acornmc.drsleep.DrSleep;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Lang {
    private static YamlConfiguration config;

    // Command outputs
    public static String NOW_PREVENTING_SKIP = "&aYou are now preventing night skipping.";
    public static String RELOADED = "&aDrSleep reloaded.";
    public static String NOW_ALLOWING_SKIP = "&aYou are now allowing night skipping.";
    public static String PREVENTING_SKIP_LIST_HEADER = "&eThe following players are preventing sleep:";
    public static String PREVENTING_SKIP_LIST_ITEM = "&e%WORLD%: %PLAYERS%";
    public static String NOT_PLAYER = "&4This command can only be executed by players.";
    public static String SKIPPED_NIGHT = "&e%PLAYER% slept in a bed and skipped the night.\nIf you would like to prevent skipping night, use /preventsleep";
    public static String CANNOT_SKIP = "&4At least one player is preventing night skip at this time.";
    public static String DISALLOWED_WORLD = "&4That command cannot be used in this world.";
    public static String NO_PERMISSION = "&4You do not have permission for that.";
    public static String LIST_CLEARED = "&aList cleared.";

    private static void init() {
        NOW_PREVENTING_SKIP = getString("now-preventing-skip", NOW_PREVENTING_SKIP);
        RELOADED = getString("reloaded", RELOADED);
        NOW_ALLOWING_SKIP = getString("now-allowing-skip", NOW_ALLOWING_SKIP);
        PREVENTING_SKIP_LIST_HEADER = getString("preventing-skip-list-header", PREVENTING_SKIP_LIST_HEADER);
        PREVENTING_SKIP_LIST_ITEM = getString("preventing-skip-list-item", PREVENTING_SKIP_LIST_ITEM);
        NOT_PLAYER = getString("not-player", NOT_PLAYER);
        SKIPPED_NIGHT = getString("skipped-night", SKIPPED_NIGHT);
        CANNOT_SKIP = getString("cannot-skip", CANNOT_SKIP);
        DISALLOWED_WORLD = getString("disallowed-world", DISALLOWED_WORLD);
        LIST_CLEARED = getString("list-cleared", LIST_CLEARED);
    }

    // ############################  DO NOT EDIT BELOW THIS LINE  ############################

    public static void reload() {
        reload(DrSleep.plugin);
    }

    /**
     * Reload the language file
     */
    public static void reload(Plugin plugin) {
        File configFile = new File(plugin.getDataFolder(), Config.LANGUAGE_FILE);
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load " + Config.LANGUAGE_FILE + ", please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }
        config.options().header("This is the main language file for " + plugin.getName());
        config.options().copyDefaults(true);

        Lang.init();

        try {
            config.save(configFile);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    /**
     * Sends a message to a recipient
     *
     * @param recipient Recipient of message
     * @param message   Message to send
     */
    public static void send(CommandSender recipient, String message) {
        if (recipient != null) {
            for (String part : colorize(message).split("\n")) {
                recipient.sendMessage(part);
            }
        }
    }

    /**
     * Colorize a String
     *
     * @param str String to colorize
     * @return Colorized String
     */
    public static String colorize(String str) {
        if (str == null) {
            return "";
        }
        str = ChatColor.translateAlternateColorCodes('&', str);
        if (ChatColor.stripColor(str).isEmpty()) {
            return "";
        }
        return str;
    }
}
