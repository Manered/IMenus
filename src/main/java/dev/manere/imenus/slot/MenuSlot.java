package dev.manere.imenus.slot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a slot in a menu, potentially a paged slot (on a specific page).
 */
public class MenuSlot {
    private final int slot;
    private final int page;

    /**
     * Creates a MenuSlot instance with the specified slot on the first page.
     *
     * @param slot the slot number.
     */
    private MenuSlot(final int slot) {
        this(slot, 1);
    }

    /**
     * Creates a MenuSlot instance with the specified slot and page.
     *
     * @param slot the slot number.
     * @param page the page number.
     */
    private MenuSlot(final int slot, final int page) {
        this.slot = slot;
        this.page = page;
    }

    /**
     * Creates a MenuSlot instance for the specified slot on the first page.
     *
     * @param slot the slot number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot of(final int slot) {
        return new MenuSlot(slot);
    }

    /**
     * Alias for {@link #of(int)}.
     *
     * @param slot the slot number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot slot(final int slot) {
        return of(slot);
    }

    /**
     * Creates a MenuSlot instance for the specified slot and page.
     *
     * @param slot the slot number.
     * @param page the page number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot of(final int slot, final int page) {
        return new MenuSlot(slot, page);
    }

    /**
     * Alias for {@link #of(int, int)}.
     *
     * @param slot the slot number.
     * @param page the page number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot slot(final int slot, final int page) {
        return of(slot, page);
    }

    /**
     * Alias for {@link #of(int, int)}.
     *
     * @param slot the slot number.
     * @param page the page number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot pagedSlot(final int slot, final int page) {
        return of(slot, page);
    }

    /**
     * Alias for {@link #of(int, int)}.
     *
     * @param slot the slot number.
     * @param page the page number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot paginatedSlot(final int slot, final int page) {
        return of(slot, page);
    }

    /**
     * Alias for {@link #of(int, int)}.
     *
     * @param slot the slot number.
     * @param page the page number.
     * @return a new {@link MenuSlot} instance.
     */
    @NotNull
    public static MenuSlot slotAndPage(final int slot, final int page) {
        return of(slot, page);
    }

    /**
     * Gets the slot number.
     *
     * @return the slot number.
     */
    public int slot() {
        return slot;
    }

    /**
     * Gets the page number.
     *
     * @return the page number.
     */
    public int page() {
        return page;
    }

    @Override
    public boolean equals(final @Nullable Object obj) {
        return obj instanceof MenuSlot other && slot == other.slot && page == other.page;
    }

    @Override
    public String toString() {
        return "MenuSlot{slot=" + slot + ",page=" + page + "}";
    }
}
