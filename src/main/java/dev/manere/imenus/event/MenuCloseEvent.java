package dev.manere.imenus.event;

import dev.manere.imenus.menu.Menu;
import org.bukkit.entity.Player;
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
    static <M extends Menu> MenuCloseEvent<M> event(final @NotNull M menu, final @NotNull Player player) {
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
}
