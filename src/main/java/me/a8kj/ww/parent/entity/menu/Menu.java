package me.a8kj.ww.parent.entity.menu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.google.common.collect.Maps;

import lombok.Getter;
import me.a8kj.ww.parent.entity.menu.attributes.MenuSettings;
import me.a8kj.ww.parent.utils.ItemStackBuilder;
import me.a8kj.ww.parent.utils.StringUtils;

import java.util.Map;

@Getter
public abstract class Menu implements MenuHandler {

    protected final Map<Integer, ItemStackBuilder> contents = Maps.newHashMap();
    private final MenuSettings settings = new MenuSettings();

    /**
     * Constructs a Menu instance and initializes its contents and settings.
     */
    public Menu() {
        defineContents(contents);
        defineSettings(settings);
    }

    /**
     * Defines the contents of the menu.
     *
     * @param contents the map to populate with items
     */
    public abstract void defineContents(Map<Integer, ItemStackBuilder> contents);

    /**
     * Defines the settings of the menu.
     *
     * @param menuSettings the settings to configure
     */
    public abstract void defineSettings(MenuSettings menuSettings);

    /**
     * Builds the inventory items based on the contents map.
     *
     * @param inventory the inventory to populate
     * @return the populated inventory
     */
    public Inventory buildItems(Inventory inventory) {
        if (!contents.isEmpty()) {
            contents.forEach((k, v) -> inventory.setItem(k, v.build()));
        }
        return inventory;
    }

    /**
     * Builds the inventory using the menu settings and title.
     *
     * @return the constructed inventory
     */
    public Inventory build() {
        return buildItems(Bukkit.createInventory(null, settings.getMenuSize().getSize(),
                StringUtils.legacyColorize(settings.getTitle())));
    }

}
