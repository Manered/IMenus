package dev.manere.imenus.button;

import dev.manere.imenus.Slot;
import org.jetbrains.annotations.NotNull;

public final class ButtonContext {
    private final Button button;
    private final Slot slot;

    public ButtonContext(final @NotNull Button button, final @NotNull Slot slot) {
        this.button = button;
        this.slot = slot;
    }

    @NotNull
    public static ButtonContext context(final @NotNull Button button, final @NotNull Slot slot) {
        return new ButtonContext(button, slot);
    }

    @NotNull
    public Button getButton() {
        return button;
    }

    @NotNull
    public Slot getSlot() {
        return slot;
    }
}