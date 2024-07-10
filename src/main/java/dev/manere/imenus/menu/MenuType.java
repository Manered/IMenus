package dev.manere.imenus.menu;

import org.jetbrains.annotations.NotNull;

/**
 * Enum representing different types of menus.
 */
public enum MenuType {
    PAGED(PagedMenu.class),
    NORMAL(NormalMenu.class);

    private final Class<? extends Menu> menuType;

    /**
     * Constructs a MenuType with the specified menu class.
     *
     * @param menuType the class of the menu type.
     */
    MenuType(final @NotNull Class<? extends Menu> menuType) {
        this.menuType = menuType;
    }

    /**
     * Retrieves the normal menu type.
     *
     * @return the normal menu type.
     */
    @NotNull
    public static MenuType normal() {
        return NORMAL;
    }

    /**
     * Retrieves the paged menu type.
     *
     * @return the paged menu type.
     */
    @NotNull
    public static MenuType paged() {
        return PAGED;
    }

    /**
     * Retrieves the class of the menu type.
     *
     * @return the class of the menu type.
     */
    @NotNull
    public Class<? extends Menu> menuType() {
        return menuType;
    }
}
