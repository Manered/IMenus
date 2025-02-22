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
    Menu getForwardedMenu();

    @Override
    default @NotNull Menu handleClose(final @NotNull CloseEventHandler<Menu> handler) {
        return getForwardedMenu().handleClose(handler);
    }

    @Override
    default @NotNull Menu button(final MenuSlot slot, final @NotNull Button button) {
        return getForwardedMenu().button(slot, button);
    }

    @Override
    default @NotNull Menu item(final MenuSlot slot, final @NotNull ItemStack item) {
        return getForwardedMenu().item(slot, item);
    }

    @Override
    default @NotNull CloseEventHandler<Menu> getCloseHandler() {
        return getForwardedMenu().getCloseHandler();
    }

    @Override
    default @NotNull Buttons getButtonManager() {
        return getForwardedMenu().getButtonManager();
    }

    @Override
    default int getPages() {
        return getForwardedMenu().getPages();
    }

    @Override
    default int getPage() {
        return getForwardedMenu().getPage();
    }

    @Override
    default boolean isPaginated() {
        return getForwardedMenu().isPaginated();
    }

    @Override
    @NotNull
    default Menu open(final @NotNull Player player) {
        return getForwardedMenu().open(player);
    }

    @Override
    default @NotNull Menu open(final @NotNull Player player, final int page) {
        return getForwardedMenu().open(player, page);
    }

    @Override
    default @NotNull Component getTitle() {
        return getForwardedMenu().getTitle();
    }

    @Override
    @NotNull
    default MenuSize getSize() {
        return getForwardedMenu().getSize();
    }

    @Override
    default @NotNull Inventory getInventory() {
        return getForwardedMenu().getInventory();
    }

    @Override
    default @NotNull Menu button(final @NotNull PageItem item, final @NotNull PageItemProvider provider) {
        return getForwardedMenu().button(item, provider);
    }
}
