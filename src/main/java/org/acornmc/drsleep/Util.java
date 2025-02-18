package org.acornmc.drsleep;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class Util {
    /**
     *
     * @param player the player to remove
     * @return true if it worked
     */
    public static boolean processPlayerRemoval(Player player) {
        World w = player.getWorld();
        ManagedWorld m = getManagedWorld(w);
        if (m == null) return false;
        m.preventingSleep.remove(player);
        return true;
    }

    /**
     *
     * @param player the player to add
     * @return true if it worked
     */
    public static boolean processPlayerAddition(Player player) {
        World w = player.getWorld();
        ManagedWorld m = getManagedWorld(w);
        if (m == null) return false;
        m.preventingSleep.add(player);
        return true;
    }

    public static ManagedWorld getManagedWorld(World w) {
        return ManagedWorld.managedWorlds.get(w);
    }
}
