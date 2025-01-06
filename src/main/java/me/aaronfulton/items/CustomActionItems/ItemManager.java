package me.aaronfulton.items.CustomActionItems;

import me.aaronfulton.EventHandlers.ItemEventHanders.ActionHandler;
import me.aaronfulton.debug.logger.AsciiComponent;
import me.aaronfulton.debug.logger.ColorfulLogger;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemManager {
    private final List<ActionItem> registeredItems = new ArrayList<>();
    private final List<ActionHandler> activeHandlers = new ArrayList<>();
    private JavaPlugin plugin;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerItem(ActionItem actionItem) {
        // Log item registration process
        ColorfulLogger.info(AsciiComponent.text("Registering item: " + actionItem.getName())
                .color(AsciiComponent.Color.CYAN)
                .build());

        registeredItems.add(actionItem);

        for (ItemActions action : actionItem.getCustomFunctions().keySet()) {
            action.getHandlerClass().ifPresent(handlerClass -> {
                // Log handler check
                ColorfulLogger.info("Checking for existing handler for action: " + action);

                ActionHandler existingHandler = activeHandlers.stream()
                        .filter(handler -> handler.getClass().equals(handlerClass))
                        .findFirst()
                        .orElse(null);

                if (existingHandler == null) {
                    // Log handler creation
                    ColorfulLogger.info("Creating new handler for action: " + action);
                    try {
                        ColorfulLogger.info("Creating new handler for action: " + String.valueOf(action));
                        ActionHandler newHandler = (ActionHandler) handlerClass
                                .getDeclaredConstructor(JavaPlugin.class)
                                .newInstance(plugin); // Pass the plugin instance
                        newHandler.addItem(actionItem);
                        this.activeHandlers.add(newHandler);
                    } catch (Exception e) {
                        ColorfulLogger.error("Failed to create handler for action: " + action + ", Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    // Log that the handler already exists
                    ColorfulLogger.warning("Handler for action " + action + " already exists.");
                    existingHandler.addItem(actionItem);
                }
            });
        }
    }

    public Optional<ActionItem> getItem(String key) {
        // Log item lookup
        ColorfulLogger.info("Looking up item with key: " + key);
        return registeredItems.stream()
                .filter(item -> item.getKey().equalsIgnoreCase(key))
                .findFirst();
    }

    public Optional<ItemStack> getItemStack(String key) {
        // Log item stack creation
        ColorfulLogger.info("Creating ItemStack for key: " + key);
        return getItem(key).map(ActionItem::createItemStack);
    }

    private void activateHandler(ItemActions action) {
        // Log handler activation
        ColorfulLogger.info("Activating handler for action: " + action);
        // Handler activation logic here
    }

    public List<ActionItem> getRegisteredItems() {
        // Log retrieval of registered items
        ColorfulLogger.info("Retrieving registered items. Total items: " + registeredItems.size());
        return registeredItems;
    }

    public List<ActionHandler> getActiveHandlers() {
        // Log retrieval of active handlers
        ColorfulLogger.info("Retrieving active handlers. Total handlers: " + activeHandlers.size());
        return activeHandlers;
    }
}
