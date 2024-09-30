package me.a8kj.ww.parent.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple builder class for creating and customizing ItemStack objects in
 * Bukkit.
 */
public class ItemStackBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * Creates an ItemStackBuilder for the specified material.
     *
     * @param material The material for the item.
     */
    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Sets the display name for the item.
     *
     * @param name The display name to set.
     * @return The current ItemStackBuilder instance for chaining.
     */
    public ItemStackBuilder setName(String name) {
        itemMeta.setDisplayName(StringUtils.legacyColorize(name));
        return this;
    }

    /**
     * Adds lore lines to the item.
     *
     * @param lore The lore lines to add.
     * @return The current ItemStackBuilder instance for chaining.
     */
    public ItemStackBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore.stream()
                .map(StringUtils::legacyColorize)
                .collect(Collectors.toList()));
        return this;
    }

    /**
     * Adds a single lore line to the item.
     *
     * @param line The lore line to add.
     * @return The current ItemStackBuilder instance for chaining.
     */
    public ItemStackBuilder addLoreLine(String line) {
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(StringUtils.legacyColorize(line));
        itemMeta.setLore(lore);
        return this;
    }

    /**
     * Sets the item amount.
     *
     * @param amount The amount of the item.
     * @return The current ItemStackBuilder instance for chaining.
     */
    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Finalizes the ItemStack by applying the ItemMeta to it.
     *
     * @return The customized ItemStack.
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
