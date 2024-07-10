package dev.manere.imenus.menu;

import org.jetbrains.annotations.NotNull;

/**
 * Context class representing a menu and a slot within that menu.
 */
public record SelectionContext(Menu menu, int slot) {
    /**
     * Constructs a new SelectionContext with the specified menu and slot.
     *
     * @param menu the {@link Menu}.
     * @param slot the slot number.
     */
    public SelectionContext(final @NotNull Menu menu, final int slot) {
        this.menu = menu;
        this.slot = slot;
    }

    /**
     * Creates a new SelectionContext with the specified menu and slot.
     *
     * @param menu the {@link Menu}.
     * @param slot the slot number.
     * @return a new {@link SelectionContext} instance.
     */
    @NotNull
    public static SelectionContext context(final @NotNull Menu menu, final int slot) {
        return new SelectionContext(menu, slot);
    }

    /**
     * Retrieves the menu.
     *
     * @return the {@link Menu}.
     */
    @Override
    @NotNull
    public Menu menu() {
        return menu;
    }
}
