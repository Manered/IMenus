package dev.manere.imenus.button;

import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.menu.Menu;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MenuButton implements Button {
    private ItemStack item;
    private Consumer<MenuClickEvent<? extends Menu>> handler = MenuClickEvent::cancel;

    MenuButton() {}

    @NotNull
    @Override
    public ItemStack item() {
        return item;
    }

    @NotNull
    @Override
    public Consumer<MenuClickEvent<? extends Menu>> handleClick() {
        return handler;
    }

    @NotNull
    @Override
    public Button item(final @NotNull ItemStack item) {
        this.item = item;
        return this;
    }

    @NotNull
    @Override
    public Button handleClick(final @NotNull Consumer<MenuClickEvent<? extends Menu>> handler) {
        this.handler = handler;
        return this;
    }
}
