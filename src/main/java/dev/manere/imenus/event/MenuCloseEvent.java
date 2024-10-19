package dev.manere.imenus.event;

import dev.manere.imenus.InventoryMenus;
import dev.manere.imenus.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a menu close event.
 *
 * @param <M> the type of menu associated with the event.
 */
public interface MenuCloseEvent<M extends Menu> {
    /**
     * Creates a new MenuCloseEvent instance.
     *
     * @param menu the menu that is being closed.
     * @param player the player who closed the menu.
     * @param <M> the type of menu associated with the event.
     * @return a new MenuCloseEvent instance.
     */
    @NotNull
    static <M extends Menu> MenuCloseEvent<M> event(final @NotNull M menu, final @NotNull Player player, final @NotNull InventoryCloseEvent.Reason reason) {
        return new MenuCloseEvent<>() {
            @NotNull
            @Override
            public M menu() {
                return menu;
            }

            @NotNull
            @Override
            public Player player() {
                return player;
            }

            @Override
            public @NotNull InventoryCloseEvent.Reason reason() {
                return reason;
            }
        };
    }

    /**
     * Retrieves the menu associated with this close event.
     *
     * @return the menu that is being closed.
     */
    @NotNull
    M menu();

    /**
     * Retrieves the player who closed the menu.
     *
     * @return the player who closed the menu.
     */
    @NotNull
    Player player();

    /**
     * Retrieves the reason of closing the menu.
     *
     * @return the reason of closing the menu.
     */
    @NotNull
    InventoryCloseEvent.Reason reason();

    default void reopen() {
        Bukkit.getScheduler().runTaskLater(InventoryMenus.pluginOrThrow(), () -> menu().open(player()), 1L);
    }

    default void redirect(final @NotNull Menu newMenu) {
        redirect(newMenu, 1);
    }

    default void redirect(final @NotNull Menu newMenu, final int page) {
        Bukkit.getScheduler().runTaskLater(InventoryMenus.pluginOrThrow(), () -> newMenu.open(player(), page), 1L);
    }
}
