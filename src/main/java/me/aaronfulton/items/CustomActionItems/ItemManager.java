package me.aaronfulton.items.CustomActionItems;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemManager {
    private List<ActionItem> registeredItems = new ArrayList<>();
    private List<ActionHandler> activeHandlers = new ArrayList<>();

    public void registerItem(ActionItem actionItem) {
        registeredItems.add(actionItem);
        actionItem.getCustomFunctions().keySet().forEach(this::activateHandler);
    }

    public Optional<ActionItem> getItem(String key) {
        return registeredItems.stream()
                .filter(item -> item.getName().equalsIgnoreCase(key))
                .findFirst();
    }

    public Optional<ItemStack> getItemStack(String key) {
        return getItem(key).map(ActionItem::createItemStack);
    }

    private void activateHandler(ItemActions action) {
        //Turn on handlers
    }
}
