package dev.manere.imenus.menu;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an initialized menu that can be opened and populated.
 *
 * @param <M> the type of the menu.
 */
public abstract class InitializedMenu<M extends Menu> implements ForwardingMenu {
    private final Player player;
    private final M menu;

    /**
     * Constructs an InitializedMenu with the specified player.
     *
     * @param player the player associated with this menu.
     */
    public InitializedMenu(final @NotNull Player player, final @NotNull M menu) {
        this.player = player;
        this.menu = menu;
    }

    /**
     * Opens the menu and populates it with content.
     */
    public final void open() {
        open(1);
    }

    /**
     * Opens the menu to a specific page and populates it with content.
     *
     * @param page the page to open.
     */
    public final void open(final int page) {
        populate();
        open(player, page);
    }

    /**
     * Populates the menu with content.
     */
    public abstract void populate();

    /**
     * Retrieves the menu instance.
     *
     * @return the menu instance.
     */
    @NotNull
    public M menu() {
        return menu;
    }

    /**
     * Retrieves the player associated with this menu.
     *
     * @return the player.
     */
    @NotNull
    public Player player() {
        return player;
    }
}
