package me.aaronfulton.items.CustomActionItems;

import me.aaronfulton.EventHandlers.ItemEventHanders.ActionHandler;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemManager {
    private List<ActionItem> registeredItems = new ArrayList<>();
    private List<ActionHandler> activeHandlers = new ArrayList<>();

    public void registerItem(ActionItem actionItem) {
        registeredItems.add(actionItem);
        for (ItemActions action : actionItem.getCustomFunctions().keySet()) {
            action.getHandlerClass().ifPresent(handlerClass -> {
                ActionHandler existingHandler = activeHandlers.stream()
                        .filter(handler -> handler.getClass().equals(handlerClass))
                        .findFirst()
                        .orElse(null);

                if (existingHandler == null) {
                    try {
                        ActionHandler newHandler = handlerClass.getDeclaredConstructor().newInstance();
                        activeHandlers.add(newHandler);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
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

    public List<ActionItem> getRegisteredItems() {
        return registeredItems;
    }

    public List<ActionHandler> getActiveHandlers() {
        return activeHandlers;
    }
}
