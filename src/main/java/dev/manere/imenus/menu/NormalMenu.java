package dev.manere.imenus.menu;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.Buttons;
import dev.manere.imenus.event.CloseEventHandler;
import dev.manere.imenus.item.PageItem;
import dev.manere.imenus.item.PageItemProvider;
import dev.manere.imenus.slot.MenuSlot;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A {@link Menu} that can not have pages.
 * Could be called the "default" implementation.
 */
public class NormalMenu implements Menu, InventoryHolder {
    private final Component title;
    private final MenuSize size;

    private final Inventory inventory;

    private final Buttons buttons = Buttons.empty();

    private CloseEventHandler<Menu> closeHandler = event -> {};

    NormalMenu(final @NotNull Component title, final @NotNull MenuSize size) {
        this.title = title;
        this.size = size;

        this.inventory = Bukkit.createInventory(this, size.size(), title);
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu handleClose(final @NotNull CloseEventHandler<Menu> handler) {
        this.closeHandler = handler;
        return this;
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu button(final @NotNull MenuSlot slot, final @NotNull Button button) {
        this.getButtonManager().setButton(slot, button);
        return this;
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu item(final @NotNull MenuSlot slot, final @NotNull ItemStack item) {
        return button(slot, Button.button()
            .item(item)
            .handleClick(event -> {})
        );
    }

    @NotNull
    @Override
    public CloseEventHandler<Menu> getCloseHandler() {
        return closeHandler;
    }

    @NotNull
    @Override
    public Buttons getButtonManager() {
        return buttons;
    }

    @Override
    public int getPages() {
        return 1;
    }

    @Override
    public int getPage() {
        return 1;
    }

    @Override
    public boolean isPaginated() {
        return false;
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu open(final @NotNull Player player, final int page) {
        if (page != 1) throw new UnsupportedOperationException("A normal menu can not have more than 1 page!");

        for (final Map.Entry<MenuSlot, Button> entry : getButtonManager().getButtons().entrySet()) {
            final MenuSlot slot = entry.getKey();
            final Button button = entry.getValue();

            if (button != null) {
                getInventory().setItem(slot.slot(), button.getItem());
            } else {
                getInventory().clear(slot.slot());
            }
        }

        player.openInventory(getInventory());
        return this;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return title;
    }

    @NotNull
    @Override
    public MenuSize getSize() {
        return size;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu button(final @NotNull PageItem item, final @NotNull PageItemProvider provider) {
        throw new UnsupportedOperationException();
    }
}
