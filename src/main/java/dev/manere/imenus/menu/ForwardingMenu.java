package dev.manere.imenus.menu;

import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.Buttons;
import dev.manere.imenus.event.CloseEventHandler;
import dev.manere.imenus.item.PageItem;
import dev.manere.imenus.item.PageItemProvider;
import dev.manere.imenus.slot.MenuSlot;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Forwards a menu.
 */
@FunctionalInterface
public interface ForwardingMenu extends Menu {
    /**
     * The menu to forward.
     * @return the menu to forward;
     */
    @NotNull
    Menu menu();

    @Override
    default @NotNull Menu handleClose(final @NotNull CloseEventHandler<Menu> handler) {
        return menu().handleClose(handler);
    }

    @Override
    default @NotNull Menu button(final MenuSlot slot, final @NotNull Button button) {
        return menu().button(slot, button);
    }

    @Override
    default @NotNull Menu item(final MenuSlot slot, final @NotNull ItemStack item) {
        return menu().item(slot, item);
    }

    @Override
    default @NotNull CloseEventHandler<Menu> closeHandler() {
        return menu().closeHandler();
    }

    @Override
    default @NotNull Buttons buttons() {
        return menu().buttons();
    }

    @Override
    default int pages() {
        return menu().pages();
    }

    @Override
    default int page() {
        return menu().page();
    }

    @Override
    default boolean paginated() {
        return menu().paginated();
    }

    @Override
    @NotNull
    default Menu open(final @NotNull Player player) {
        return menu().open(player);
    }

    @Override
    default @NotNull Menu open(final @NotNull Player player, final int page) {
        return menu().open(player, page);
    }

    @Override
    default @NotNull Component title() {
        return menu().title();
    }

    @Override
    @NotNull
    default MenuSize size() {
        return menu().size();
    }

    @Override
    default @NotNull Inventory inventory() {
        return menu().inventory();
    }

    @Override
    @NotNull
    default Menu refresh(final @NotNull Player player) {
        return menu().refresh(player);
    }

    @Override
    default @NotNull Inventory getInventory() {
        return menu().getInventory();
    }

    @Override
    default @NotNull Menu button(final @NotNull PageItem item, final @NotNull PageItemProvider provider) {
        return menu().button(item, provider);
    }
}
