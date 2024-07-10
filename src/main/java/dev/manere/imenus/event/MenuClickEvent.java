package dev.manere.imenus.event;

import dev.manere.imenus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a click event that occurs within a menu.
 *
 * @param <M> the type of menu associated with the event.
 */
public interface MenuClickEvent<M extends Menu> {
    /**
     * Creates a new MenuClickEvent instance.
     *
     * @param menu the menu where the click event occurred.
     * @param player the player who initiated the click event.
     * @param item the item that was clicked, if any.
     * @param slot the slot where the click occurred.
     * @param <M> the type of menu associated with the event.
     * @return a new MenuClickEvent instance.
     */
    @NotNull
    static <M extends Menu> MenuClickEvent<M> event(final @NotNull M menu, final @NotNull Player player, final @Nullable ItemStack item, final int slot) {
        return new MenuClickEvent<>() {
            private boolean cancelled = false;

            @NotNull
            @Override
            public M menu() {
                return menu;
            }

            @Override
            public int slot() {
                return slot;
            }

            @Nullable
            @Override
            public ItemStack item() {
                return item;
            }

            @Override
            public void cancel() {
                cancelled = true;
            }

            @NotNull
            @Override
            public Player player() {
                return player;
            }

            @Override
            public boolean cancelled() {
                return cancelled;
            }
        };
    }

    /**
     * Creates a MenuClickEvent instance from a Bukkit InventoryClickEvent.
     *
     * @param menu the menu where the click event occurred.
     * @param clickEvent the Bukkit InventoryClickEvent.
     * @param <M> the type of menu associated with the event.
     * @return a MenuClickEvent instance.
     */
    @NotNull
    static <M extends Menu> MenuClickEvent<M> event(final @NotNull M menu, final @NotNull InventoryClickEvent clickEvent) {
        return event(
            menu,
            (Player) clickEvent.getView().getPlayer(),
            clickEvent.getCurrentItem(),
            clickEvent.getRawSlot()
        );
    }

    /**
     * Retrieves the menu associated with this click event.
     *
     * @return the menu associated with the click event.
     */
    @NotNull
    M menu();

    /**
     * Retrieves the slot where the click occurred.
     *
     * @return the slot number.
     */
    int slot();

    /**
     * Retrieves the item that was clicked.
     *
     * @return the ItemStack representing the clicked item, or null if no item was clicked.
     */
    @Nullable
    ItemStack item();

    /**
     * Cancels the click event, preventing further processing.
     */
    void cancel();

    /**
     * Retrieves the player who initiated the click event.
     *
     * @return the player who clicked.
     */
    @NotNull
    Player player();

    /**
     * Checks if the click event has been cancelled.
     *
     * @return true if cancelled, false otherwise.
     */
    boolean cancelled();
}
