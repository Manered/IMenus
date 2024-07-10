package dev.manere.imenus.menu;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.Buttons;
import dev.manere.imenus.event.CloseEventHandler;
import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.item.PageItem;
import dev.manere.imenus.item.PageItemProvider;
import dev.manere.imenus.slot.MenuSlot;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a menu in the inventory GUI.
 */
public interface Menu extends InventoryHolder {
    Map<Integer, Map<String, Integer>> structures = new ConcurrentHashMap<>();

    /**
     * Creates a new normal menu.
     *
     * @param title the title of the menu.
     * @param size  the size of the menu.
     * @return a new {@link NormalMenu} instance.
     */
    @NotNull
    static NormalMenu menu(final @NotNull Component title, final @NotNull MenuSize size) {
        return new NormalMenu(title, size);
    }

    /**
     * Creates a new paged menu.
     *
     * @param title the title of the menu.
     * @param size  the size of the menu.
     * @return a new {@link PagedMenu} instance.
     */
    @NotNull
    static PagedMenu pagedMenu(final @NotNull Component title, final @NotNull MenuSize size) {
        return new PagedMenu(title, size);
    }

    /**
     * Creates a new {@link MenuBuilder} for a normal menu.
     *
     * @return a new {@link MenuBuilder} instance.
     */
    @NotNull
    static MenuBuilder builder() {
        return MenuBuilder.builder(MenuType.NORMAL);
    }

    /**
     * Creates a new {@link MenuBuilder} for the specified menu type.
     *
     * @param type the type of the menu.
     * @return a new {@link MenuBuilder} instance.
     */
    @NotNull
    static MenuBuilder builder(final @NotNull MenuType type) {
        return MenuBuilder.builder(type);
    }

    /**
     * Creates a new {@link MenuBuilder} for a paged menu.
     *
     * @return a new {@link MenuBuilder} instance.
     */
    @NotNull
    static MenuBuilder pagedBuilder() {
        return builder(MenuType.paged());
    }

    /**
     * Sets a handler to be called when the menu is closed.
     *
     * @param handler the close event handler.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    Menu handleClose(final @NotNull CloseEventHandler<Menu> handler);

    /**
     * Adds a button to the menu at the specified slot.
     *
     * @param slot   the menu slot.
     * @param button the button to add.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    Menu button(final MenuSlot slot, final @NotNull Button button);

    /**
     * Adds an item to the menu at the specified slot.
     *
     * @param slot the menu slot.
     * @param item the item to add.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    Menu item(final MenuSlot slot, final @NotNull ItemStack item);

    @NotNull
    @CanIgnoreReturnValue
    default Menu button(final int slot, final int page, final @NotNull Button button) {
        return button(MenuSlot.of(slot, page), button);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu item(final int slot, final int page, final @NotNull ItemStack item) {
        return item(MenuSlot.of(slot, page), item);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu button(final int slot, final @NotNull Button button) {
        return button(MenuSlot.of(slot), button);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu item(final int slot, final @NotNull ItemStack item) {
        return item(MenuSlot.of(slot), item);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu button(final int page, final @NotNull String character, final @NotNull Button button) {
        return button(MenuSlot.of(structures.get(page).get(character), page), button);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu button(final @NotNull String character, final @NotNull Button button) {
        return button(1, character, button);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu item(final int page, final @NotNull String character, final @NotNull ItemStack item) {
        return item(MenuSlot.of(structures.get(page).get(character), page), item);
    }

    @NotNull
    @CanIgnoreReturnValue
    default Menu item(final @NotNull String character, final @NotNull ItemStack item) {
        return item(1, character, item);
    }

    /**
     * Gets the close event handler for the menu.
     *
     * @return the close event handler.
     */
    @NotNull
    default CloseEventHandler<Menu> closeHandler() {
        return event -> {};
    }

    /**
     * Gets the buttons in the menu.
     *
     * @return the buttons in the menu.
     */
    @NotNull
    Buttons buttons();

    /**
     * Gets the number of pages in the menu.
     *
     * @return the number of pages.
     */
    int pages();

    /**
     * Gets the current page of the menu.
     *
     * @return the current page.
     */
    int page();

    /**
     * Checks if the menu is paginated.
     *
     * @return true if the menu is paginated, false otherwise.
     */
    boolean paginated();

    /**
     * Opens the menu for the specified player.
     *
     * @param player the player to open the menu for.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default Menu open(final @NotNull Player player) {
        return open(player, 1);
    }

    /**
     * Opens the menu for the specified player and page.
     *
     * @param player the player to open the menu for.
     * @param page   the page to open.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    Menu open(final @NotNull Player player, final int page);

    /**
     * Gets the title of the menu.
     *
     * @return the title of the menu.
     */
    @NotNull
    default Component title() {
        return Component.empty();
    }

    /**
     * Gets the size of the menu.
     *
     * @return the size of the menu.
     */
    @NotNull
    default MenuSize size() {
        return MenuSize.rows(3);
    }

    /**
     * Gets the inventory of the menu.
     *
     * @return the inventory of the menu.
     */
    @NotNull
    default Inventory inventory() {
        return getInventory();
    }

    /**
     * Refreshes the menu for the specified player.
     *
     * @param player the player to refresh the menu for.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default Menu refresh(final @NotNull Player player) {
        player.updateInventory();
        return this;
    }

    /**
     * Gets the inventory of the menu.
     *
     * @return the inventory.
     */
    @NotNull
    @Override
    Inventory getInventory();

    /**
     * Adds a button to the menu using a page item and provider.
     *
     * @param item     the page item.
     * @param provider the page item provider.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    Menu button(final @NotNull PageItem item, final @NotNull PageItemProvider provider);

    /**
     * Sets the structure of the menu.
     *
     * @param structure the structure of the menu.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default Menu structure(final @NotNull String @NotNull ... structure) {
        return structure(page(), structure);
    }

    /**
     * Sets the structure of the menu for the specified page.
     *
     * @param page      the page to set the structure for.
     * @param structure the structure of the menu.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default Menu structure(final int page, final @NotNull String @NotNull ... structure) {
        final Map<Integer, Map<String, Integer>> structures = new ConcurrentHashMap<>();

        int row = 0;

        for (final String rowStructure : structure) {
            if (row < size().size() / 9) {
                for (int column = 0; column < rowStructure.split(" ").length && column < 9; column++) {
                    final String character = rowStructure.split(" ")[column];
                    int slot = column + row * 9;

                    structures.put(page, new ConcurrentHashMap<>(Map.of(character, slot)));
                }

                row++;
            }
        }

        this.structures.clear();
        this.structures.putAll(structures);
        return this;
    }

    /**
     * Sets the border of the menu with the specified item.
     *
     * @param item the item to use for the border.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default Menu border(final @NotNull ItemStack item) {
        for (int page = 0; page <= pages(); page++) {
            final List<String> structure = new ArrayList<>(
                List.of("b b b b b b b b b")
            );

            final int rows = size().size() / 9;

            for (int i = 0; i < rows - 2; i++) {
                structure.add("b . . . . . . . b");
            }

            structure.add("b b b b b b b b b");

            structure(page, structure.toArray(String[]::new));
        }

        for (int page = 0; page <= pages(); page++) {
            button(page, 'b', Button.button(item, MenuClickEvent::cancel));
        }

        return this;
    }

    /**
     * Adds a selection to the menu.
     *
     * @param selection the menu selection.
     * @param action    the action to perform on the selection.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default Menu selection(final @NotNull MenuSelection selection, final @NotNull Consumer<SelectionContext> action) {
        final List<Integer> slots = selection.slots().apply(this);

        for (final int slot : slots) {
            action.accept(SelectionContext.context(this, slot));
        }

        return this;
    }

    /**
     * Populates the menu with entries from a list.
     *
     * @param <V>        the type of the entries.
     * @param list       the list of entries.
     * @param start      the start slot.
     * @param end        the end slot.
     * @param skipSlots  the slots to skip.
     * @param button     the function to create buttons from entries.
     * @return the updated {@link Menu} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    default <V> Menu populateEntries(final @NotNull List<V> list, final int start, final int end, final @NotNull Collection<Integer> skipSlots, final @NotNull Function<V, Button> button) {
        int page = 1;
        int slot = start;

        if (!paginated()) throw new UnsupportedOperationException();

        for (final V value : list) {
            while (skipSlots.contains(slot)) {
                slot++;
            }

            if (slot >= end) {
                slot = start;
                page++;
            }

            button(MenuSlot.of(slot, page), button.apply(value));

            slot++;
        }

        return this;
    }
}
