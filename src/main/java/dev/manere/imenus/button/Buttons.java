package dev.manere.imenus.button;

import dev.manere.imenus.slot.MenuSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the collection of buttons mapped to specific slots in a menu.
 */
public class Buttons {
    private final Map<MenuSlot, Button> buttons = new ConcurrentHashMap<>();

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
        return buttons.get(slot);
    }

    /**
     * Edits or adds a button to a specific slot.
     *
     * @param slot the slot where the button is located or will be placed.
     * @param button the Button instance to be placed in the slot.
     */
    public void edit(final @NotNull MenuSlot slot, final @Nullable Button button) {
        this.buttons.put(slot, button);
    }

    /**
     * Retrieves the map of all buttons and their associated slots.
     *
     * @return the map of MenuSlot to Button.
     */
    @NotNull
    public Map<MenuSlot, Button> buttons() {
        return buttons;
    }
}
