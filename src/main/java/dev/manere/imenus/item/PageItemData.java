package dev.manere.imenus.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents data associated with a page item, including the item itself and its slot.
 */
public class PageItemData {
    private final ItemStack item;
    private final int slot;

    private PageItemData(final @NotNull ItemStack item, final int slot) {
        this.item = item;
        this.slot = slot;
    }

    /**
     * Creates {@link PageItemData} with the specified item and slot.
     *
     * @param item the {@link ItemStack}.
     * @param slot the slot number.
     * @return a new {@link PageItemData} instance.
     */
    @NotNull
    public static PageItemData data(final @NotNull ItemStack item, final int slot) {
        return new PageItemData(item, slot);
    }

    /**
     * Creates {@link PageItemData} with the specified slot and item.
     *
     * @param slot the slot number.
     * @param item the {@link ItemStack}.
     * @return a new {@link PageItemData} instance.
     */
    @NotNull
    public static PageItemData data(final int slot, final @NotNull ItemStack item) {
        return data(item, slot);
    }

    /**
     * Creates {@link PageItemData} with the specified {@link ItemBuilder} and slot.
     *
     * @param item the {@link ItemBuilder}.
     * @param slot the slot number.
     * @return a new {@link PageItemData} instance.
     */
    @NotNull
    public static PageItemData data(final @NotNull ItemBuilder item, final int slot) {
        return data(item.asItem(), slot);
    }

    /**
     * Creates {@link PageItemData} with the specified slot and {@link ItemBuilder}.
     *
     * @param slot the slot number.
     * @param item the {@link ItemBuilder}.
     * @return a new {@link PageItemData} instance.
     */
    @NotNull
    public static PageItemData data(final int slot, final @NotNull ItemBuilder item) {
        return data(slot, item.asItem());
    }

    /**
     * Retrieves the {@link ItemStack} associated with this {@link PageItemData}.
     *
     * @return the {@link ItemStack}.
     */
    @NotNull
    public ItemStack item() {
        return item;
    }

    /**
     * Retrieves the slot number associated with this {@link PageItemData}.
     *
     * @return the slot number.
     */
    public int slot() {
        return slot;
    }
}
