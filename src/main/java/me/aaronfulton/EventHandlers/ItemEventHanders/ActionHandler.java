package me.aaronfulton.EventHandlers.ItemEventHanders;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ActionHandler implements Listener {

    public ActionHandler(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

}
