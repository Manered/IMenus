package dev.manere.imenus.menu;

import com.google.common.collect.ImmutableList;
import dev.manere.imenus.button.Button;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Function;

/**
 * Represents a menu that is populated with a list of items and converts each item into a button.
 *
 * @param <T> the type of items in the list.
 */
public abstract class PopulatedMenu<T> extends InitializedMenu<PagedMenu> {
    private final List<T> list;
    private final int start;
    private final int end;
    private final List<Integer> skipSlots;
    private final Function<T, Button> button;

    /**
     * Constructs a PopulatedMenu.
     *
     * @param player the player associated with this menu.
     * @param list the list of items to populate the menu with.
     * @param start the starting index of items to include.
     * @param end the ending index of items to include.
     * @param skipSlots the slots to skip when populating the menu.
     * @param button the function to convert an item into a button.
     */
    public PopulatedMenu(final @NotNull Player player, final @NotNull PagedMenu menu, final @NotNull List<T> list, final int start, final int end, final @NotNull List<Integer> skipSlots, final @NotNull Function<T, Button> button) {
        super(player, menu);
        this.list = list;
        this.start = start;
        this.end = end;
        this.skipSlots = skipSlots;
        this.button = button;
    }

    /**
     * Populates the menu with items from the list.
     */
    @Override
    public void populate() {
        populateEntries(list, start, end, skipSlots, button);
    }

    /**
     * Retrieves the list of items.
     *
     * @return the list of items.
     */
    @NotNull
    @Unmodifiable
    public List<T> list() {
        return ImmutableList.copyOf(list);
    }

    /**
     * Retrieves the start index.
     *
     * @return the start index.
     */
    public int start() {
        return start;
    }

    /**
     * Retrieves the end index.
     *
     * @return the end index.
     */
    public int end() {
        return end;
    }

    /**
     * Retrieves the list of slots to skip.
     *
     * @return the list of slots to skip.
     */
    @NotNull
    public List<Integer> skipSlots() {
        return skipSlots;
    }

    /**
     * Retrieves the function to convert an item into a button.
     *
     * @return the function to convert an item into a button.
     */
    @NotNull
    public Function<T, Button> function() {
        return button;
    }
}
