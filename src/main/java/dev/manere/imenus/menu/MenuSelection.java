package dev.manere.imenus.menu;

import dev.manere.imenus.button.Button;
import dev.manere.imenus.slot.MenuSlot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents a selection of slots within a menu.
 */
public class MenuSelection {
    private final Function<Menu, List<Integer>> slots;

    /**
     * Private constructor for creating a MenuSelection with a specified slot function.
     *
     * @param slots the function defining the slot selection.
     */
    private MenuSelection(final @NotNull Function<Menu, List<Integer>> slots) {
        this.slots = slots;
    }

    /**
     * Creates a new MenuSelection with the specified slot function.
     *
     * @param slots the function defining the slot selection.
     * @return a new {@link MenuSelection} instance.
     */
    public static MenuSelection selection(final @NotNull Function<Menu, List<Integer>> slots) {
        return new MenuSelection(slots);
    }

    /**
     * Retrieves the slot function of this MenuSelection.
     *
     * @return the slot function.
     */
    @NotNull
    public Function<Menu, List<Integer>> slots() {
        return slots;
    }

    /**
     * Predefined MenuSelection representing the padding slots of a menu.
     */
    public static final MenuSelection PADDING = MenuSelection.selection(menu -> switch (menu.size().rows()) {
        case 3 -> List.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
        );
        case 4 -> List.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35
        );
        case 5 -> List.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
        );
        case 6 -> List.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
        );
        default -> List.of();
    });

    /**
     * Retrieves the predefined MenuSelection representing the padding slots of a menu.
     *
     * @return the padding {@link MenuSelection}.
     */
    @NotNull
    public static MenuSelection padding() {
        return PADDING;
    }

    /**
     * Predefined MenuSelection representing non-empty slots of a menu.
     */
    public static final MenuSelection NON_EMPTY_SLOTS = MenuSelection.selection(menu -> {
        final List<Integer> slots = new ArrayList<>();

        for (final Map.Entry<MenuSlot, Button> entry : menu.buttons().buttons().entrySet()) {
            final MenuSlot menuSlot = entry.getKey();
            final int slot = menuSlot.slot();
            slots.add(slot);
        }

        return slots;
    });

    /**
     * Retrieves the predefined MenuSelection representing non-empty slots of a menu.
     *
     * @return the non-empty slots {@link MenuSelection}.
     */
    @NotNull
    public static MenuSelection nonEmptySlots() {
        return NON_EMPTY_SLOTS;
    }

    /**
     * Predefined MenuSelection representing empty slots of a menu.
     */
    public static final MenuSelection EMPTY_SLOTS = MenuSelection.selection(menu -> {
        final List<Integer> nonEmpty = MenuSelection.NON_EMPTY_SLOTS.slots().apply(menu);
        final List<Integer> empty = MenuSelection.ALL_SLOTS.slots().apply(menu);

        empty.removeAll(nonEmpty);

        return empty;
    });

    /**
     * Retrieves the predefined MenuSelection representing empty slots of a menu.
     *
     * @return the empty slots {@link MenuSelection}.
     */
    @NotNull
    public static MenuSelection emptySlots() {
        return EMPTY_SLOTS;
    }

    /**
     * Predefined MenuSelection representing all slots of a menu.
     */
    public static final MenuSelection ALL_SLOTS = MenuSelection.selection(menu -> {
        final List<Integer> slots = new ArrayList<>();

        for (int i = 0; i < menu.size().size(); i++) {
            slots.add(i);
        }

        return slots;
    });

    /**
     * Retrieves the predefined MenuSelection representing all slots of a menu.
     *
     * @return the all slots {@link MenuSelection}.
     */
    @NotNull
    public static MenuSelection allSlots() {
        return ALL_SLOTS;
    }

    /**
     * Alias for {@link #allSlots()}.
     *
     * @return the all slots {@link MenuSelection}.
     */
    @NotNull
    public static MenuSelection all() {
        return allSlots();
    }

    /**
     * Alias for {@link #PADDING}.
     * Returns the padding {@link MenuSelection}.
     */
    public static final MenuSelection BORDER = PADDING;

    /**
     * Retrieves the predefined MenuSelection representing the border slots of a menu.
     *
     * @return the border {@link MenuSelection}.
     */
    @NotNull
    public static MenuSelection border() {
        return PADDING;
    }
}
