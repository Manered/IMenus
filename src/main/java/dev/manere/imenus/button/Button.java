package dev.manere.imenus.button;

import dev.manere.imenus.event.action.ClickAction;
import dev.manere.imenus.event.MenuEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Button {
    private final ItemStack item;
    private final ClickAction action;

    public Button(final @NotNull ItemStack item, final @Nullable ClickAction action) {
        this.item = item.clone();
        this.action = action != null ? action : MenuEvent.Cancellable::cancel;
    }

    public Button(final @NotNull ItemStack item) {
        this(item, MenuEvent.Cancellable::cancel);
    }

    @NotNull
    public static Button button(final @NotNull ItemStack item, final @Nullable ClickAction action) {
        return new Button(item, action);
    }

    @NotNull
    public static Button button(final @NotNull ItemStack item) {
        return new Button(item);
    }

    @NotNull
    public ItemStack getItem() {
        return item.clone();
    }

    @NotNull
    public ClickAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "Button";
    }
}