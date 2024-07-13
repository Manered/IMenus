package dev.manere.imenus.button;

import dev.manere.imenus.slot.MenuSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an entry that pairs a {@link MenuSlot} with a {@link Button}.
 */
public class ButtonEntry {
    private final MenuSlot slot;
    private final Button button;

    /**
     * Constructs a new {@code ButtonEntry} with the specified slot and button.
     *
     * @param slot   the menu slot, must not be null
     * @param button the button, may be null
     */
    private ButtonEntry(final @NotNull MenuSlot slot, final @Nullable Button button) {
        this.slot = slot;
        this.button = button;
    }

    /**
     * Creates a new {@code ButtonEntry} with the specified slot and button.
     *
     * @param slot   the menu slot, must not be null
     * @param button the button, may be null
     * @return a new {@code ButtonEntry} instance
     */
    @NotNull
    public static ButtonEntry entry(final @NotNull MenuSlot slot, final @Nullable Button button) {
        return new ButtonEntry(slot, button);
    }

    /**
     * Returns the menu slot associated with this entry.
     *
     * @return the menu slot, never null
     */
    @NotNull
    public MenuSlot slot() {
        return slot;
    }

    /**
     * Returns the button associated with this entry.
     *
     * @return the button, may be null
     */
    @Nullable
    public Button button() {
        return button;
    }
}
