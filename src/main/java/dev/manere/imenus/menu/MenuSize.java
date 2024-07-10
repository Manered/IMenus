package dev.manere.imenus.menu;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the size of a menu.
 */
public class MenuSize {
    private int size;

    /**
     * Private constructor for creating a MenuSize with a specified size.
     *
     * @param size the size of the menu.
     */
    private MenuSize(final int size) {
        this.size = size;
    }

    /**
     * Creates a MenuSize with the specified size.
     *
     * @param size the size of the menu.
     * @return a new {@link MenuSize} instance.
     */
    @NotNull
    public static MenuSize size(final int size) {
        return new MenuSize(size);
    }

    /**
     * Creates a MenuSize with the specified number of rows.
     *
     * @param rows the number of rows in the menu.
     * @return a new {@link MenuSize} instance.
     */
    @NotNull
    public static MenuSize rows(final int rows) {
        return new MenuSize(rows * 9);
    }

    /**
     * Creates a MenuSize with the specified dimensions.
     *
     * @param x the width of the menu.
     * @param y the height of the menu.
     * @return a new {@link MenuSize} instance.
     */
    @NotNull
    public static MenuSize size(final int x, final int y) {
        return new MenuSize(x * y);
    }

    /**
     * Edits the size of the menu.
     *
     * @param size the new size of the menu.
     * @return the updated {@link MenuSize} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public MenuSize editSize(final int size) {
        this.size = size;
        return this;
    }

    /**
     * Edits the number of rows in the menu.
     *
     * @param rows the new number of rows.
     * @return the updated {@link MenuSize} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public MenuSize editRows(final int rows) {
        this.size = rows * 9;
        return this;
    }

    /**
     * Edits the dimensions of the menu.
     *
     * @param x the new width of the menu.
     * @param y the new height of the menu.
     * @return the updated {@link MenuSize} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public MenuSize editSize(final int x, final int y) {
        this.size = x * y;
        return this;
    }

    /**
     * Retrieves the size of the menu.
     *
     * @return the size of the menu.
     */
    public int size() {
        return size;
    }

    /**
     * Retrieves the number of rows in the menu.
     *
     * @return the number of rows.
     */
    public int rows() {
        return size / 9;
    }

    /**
     * Retrieves the number of columns in the menu.
     *
     * @return the number of columns.
     */
    public int columns() {
        return size / rows();
    }
}
