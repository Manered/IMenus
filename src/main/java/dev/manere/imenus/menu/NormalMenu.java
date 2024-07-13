package dev.manere.imenus.menu;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.ButtonEntry;
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

import java.util.Map.Entry;

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
        this.buttons().edit(slot, button);
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
    public CloseEventHandler<Menu> closeHandler() {
        return closeHandler;
    }

    @NotNull
    @Override
    public Buttons buttons() {
        return buttons;
    }

    @Override
    public int pages() {
        return 1;
    }

    @Override
    public int page() {
        return 1;
    }

    @Override
    public boolean paginated() {
        return false;
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu open(final @NotNull Player player, final int page) {
        if (page != 1) throw new UnsupportedOperationException("A normal menu can not have more than 1 page!");

        for (final ButtonEntry entry : buttons().buttons()) {
            final MenuSlot slot = entry.slot();
            final Button button = entry.button();

            if (button != null) {
                inventory().setItem(slot.slot(), button.item());
            } else {
                inventory.clear(slot.slot());
            }
        }

        player.openInventory(inventory());
        return this;
    }

    @NotNull
    @Override
    public Component title() {
        return title;
    }

    @NotNull
    @Override
    public MenuSize size() {
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
