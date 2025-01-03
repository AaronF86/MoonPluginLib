package me.aaronfulton.items.CustomActionItems;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public record PersistentDataRecord<T>(NamespacedKey key, PersistentDataType<T, ?> type, T value) {
}
