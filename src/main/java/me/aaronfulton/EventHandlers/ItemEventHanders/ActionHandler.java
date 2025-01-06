package me.aaronfulton.EventHandlers.ItemEventHanders;

import me.aaronfulton.items.CustomActionItems.ActionItem;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class ActionHandler implements Listener {
    private final List<ActionItem> itemWithAction;


    public ActionHandler(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        itemWithAction = new ArrayList<>();

    }

    public List<ActionItem> getItemWithAction() {
        return itemWithAction;
    }

    public void addItem(ActionItem item) {
        itemWithAction.add(item);
    }

}
