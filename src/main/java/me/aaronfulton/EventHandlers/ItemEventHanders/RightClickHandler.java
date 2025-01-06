package me.aaronfulton.EventHandlers.ItemEventHanders;

import me.aaronfulton.debug.logger.ColorfulLogger;
import me.aaronfulton.items.CustomActionItems.ActionItem;
import me.aaronfulton.items.CustomActionItems.ItemActions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class RightClickHandler extends ActionHandler {

    public RightClickHandler(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
            if (itemInHand == null || itemInHand.getType().isAir()) {
                return;
            }

            Optional<ActionItem> matchingItem = getItemWithAction().stream()
                    .filter(actionItem -> actionItem.createItemStack().isSimilar(itemInHand)) // Assuming ActionItem has a `matches` method
                    .findFirst();
            if (matchingItem.isPresent()) {
                ActionItem actionItem = matchingItem.get();
                if (event.getPlayer().isSneaking()) {
                    actionItem.useAction(ItemActions.SHIFT_RIGHT_CLICK, event.getPlayer());
                } else {
                    actionItem.useAction(ItemActions.RIGHT_CLICK, event.getPlayer());
                }
            }
        }
    }
}
