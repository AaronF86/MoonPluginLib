package me.aaronfulton.items.CustomActionItems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ActionItem {
    private String key;
    private Material material;
    private String name;
    private List<String> lore;
    private List<PersistentDataRecord> dataValues;
    private int customModelData;
    private Map<ItemActions, Consumer<Player>> customFunctions = new HashMap<>();
    private boolean eventCancel;

    public ActionItem(Material material, String name, List<String> lore, int customModelData,boolean EventCancell) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.customModelData = customModelData;
        this.eventCancel =EventCancell;
    }

    public String getName() {
        return this.name;
    }

    public Map<ItemActions, Consumer<Player>> getCustomFunctions() {
        return this.customFunctions;
    }

    public void addCustomFunction(ItemActions action, Consumer<Player> function) {
        this.customFunctions.put(action, function);
    }

    public void useAction(ItemActions action, Player player) {
        if (this.customFunctions.containsKey(action)) {
            this.customFunctions.get(action).accept(player);
        }
    }

    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(this.name);
            meta.setLore(this.lore);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.UNBREAKING, 1,false);

            if (dataValues != null) {
                for (PersistentDataRecord record : this.dataValues) {
                    container.set(record.key(), record.type(), record.value());
                }
            }

            meta.setCustomModelData(this.customModelData);
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    public String getKey() {
        return this.key;
    }

    public boolean isEventCancel() {
        return eventCancel;
    }
}
