package dev.manere.imenus.button;

import com.google.common.collect.ImmutableList;
import dev.manere.imenus.slot.MenuSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the collection of buttons mapped to specific slots in a menu.
 */
public class Buttons {
    private final List<ButtonEntry> buttons = new ArrayList<>();

    private Buttons() {}

    /**
     * Creates a new instance of Buttons.
     *
     * @return a new Buttons instance.
     */
    @NotNull
    public static Buttons empty() {
        return new Buttons();
    }

    /**
     * Retrieves the button associated with a specific slot.
     *
     * @param slot the slot where the button is located.
     * @return the Button associated with the slot, or null if no button is found.
     */
    @Nullable
    public Button button(final @NotNull MenuSlot slot) {
        for (final ButtonEntry entry : buttons) {
            if (entry.slot().equals(slot)) return entry.button();
        }

        return null;
    }

    /**
     * Edits or adds a button to a specific slot.
     *
     * @param slot the slot where the button is located or will be placed.
     * @param button the Button instance to be placed in the slot.
     */
    public void edit(final @NotNull MenuSlot slot, final @Nullable Button button) {
        this.buttons.add(ButtonEntry.entry(slot, button));
    }

    /**
     * Retrieves the map of all buttons and their associated slots.
     *
     * @return the list of button entries.
     * @see ButtonEntry
     */
    @NotNull
    @Unmodifiable
    public List<ButtonEntry> buttons() {
        return ImmutableList.copyOf(buttons);
    }
}
