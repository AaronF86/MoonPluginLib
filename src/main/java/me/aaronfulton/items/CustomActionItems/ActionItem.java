package me.aaronfulton.items.CustomActionItems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ActionItem {
    private Material material;
    private String name;
    private List<String> lore;
    private List<PersistentDataRecord> dataValues;
    private int customModelData;
    private Map<ItemActions, Consumer<Player>> customFunctions = new HashMap<>();

    public String getName() {
        return name;
    }

    public Map<ItemActions, Consumer<Player>> getCustomFunctions() {
        return customFunctions;
    }

    public void addCustomFunction(ItemActions action, Consumer<Player> function) {
        customFunctions.put(action, function);
    }

    public void useAction(ItemActions action, Player player) {
        if (customFunctions.containsKey(action)) {
            customFunctions.get(action).accept(player);
        }
    }

    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            for (PersistentDataRecord record : dataValues) {
                container.set(record.key(), record.type(), record.value());
            }
            meta.setCustomModelData(customModelData);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

}
