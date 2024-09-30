package me.a8kj.ww.parent.entity.menu.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing various sizes of menus.
 * 
 * <p>
 * Each enum value corresponds to a specific size for a menu, with fuzzy names
 * that describe the sizes in a less direct manner.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public enum MenuSize {

    SMALLISH(9), // A small menu, suitable for few items
    MODERATELY_SIZED(18), // A moderately sized menu for more items
    SPACIOUS(27), // A spacious menu, good for a larger collection
    ROOMY(36), // A roomy menu for even more items
    EXPANSIVE(45), // An expansive menu for a wide array of options
    GIGANTIC(54); // The largest menu size possible in the game

    private final int size;

}
