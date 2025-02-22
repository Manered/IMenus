package dev.manere.imenus.button;

import dev.manere.imenus.slot.MenuSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Manages the collection of buttons mapped to specific slots in a menu.
 */
public class Buttons {
    private final Map<MenuSlot, @Nullable Button> buttons = new HashMap<>();

    private Buttons() {}

    @NotNull
    public static Buttons empty() {
        return new Buttons();
    }

    @NotNull
    public Optional<Button> getButton(final @NotNull MenuSlot slot) {
        for (final Map.Entry<MenuSlot, @Nullable Button> entry : buttons.entrySet()) {
            if (entry.getKey().equals(slot)) return Optional.ofNullable(entry.getValue());
        }

        return Optional.empty();
    }

    public void setButton(final @NotNull MenuSlot slot, final @Nullable Button button) {
        if (button != null) this.buttons.put(slot, button);
        if (button == null) this.buttons.remove(slot);
    }

    @NotNull
    public Map<MenuSlot, @Nullable Button> getButtons() {
        return buttons;
    }
}
