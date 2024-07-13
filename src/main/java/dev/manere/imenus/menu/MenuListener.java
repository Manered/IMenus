package dev.manere.imenus.menu;

import dev.manere.imenus.button.Button;
import dev.manere.imenus.event.CloseEventHandler;
import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.event.MenuCloseEvent;
import dev.manere.imenus.slot.MenuSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * A listener for handling menu-related events.
 */
public class MenuListener implements Listener {
    /**
     * Handles inventory click events.
     *
     * @param event the inventory click event.
     */
    @EventHandler
    public void handle(final @NotNull InventoryClickEvent event) {
        final Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        if (inventory.getHolder(false) instanceof NormalMenu menu) {
            final Button button = menu.buttons().button(MenuSlot.of(event.getRawSlot(), menu.page()));
            if (button == null) return;

            final MenuClickEvent<NormalMenu> clickEvent = MenuClickEvent.event(
                menu,
                event
            );

            button.handleClick().accept(clickEvent);
            if (clickEvent.cancelled()) event.setCancelled(true);
        } else if (inventory.getHolder(false) instanceof PagedMenu menu) {
            final Button button = menu.buttons().button(MenuSlot.of(event.getRawSlot(), menu.page()));
            if (button == null) return;

            final MenuClickEvent<PagedMenu> clickEvent = MenuClickEvent.event(
                menu,
                event
            );

            button.handleClick().accept(clickEvent);
            if (clickEvent.cancelled()) event.setCancelled(true);
        }
    }

    /**
     * Handles inventory close events.
     *
     * @param event the inventory close event.
     */
    @EventHandler
    public void handle(final @NotNull InventoryCloseEvent event) {
        if (event.getInventory().getHolder(false) instanceof NormalMenu menu) {
            final CloseEventHandler<Menu> closeHandler = menu.closeHandler();
            closeHandler.handleClose(MenuCloseEvent.event(menu, (Player) event.getPlayer()));
        } else if (event.getInventory().getHolder(false) instanceof PagedMenu menu) {
            final CloseEventHandler<Menu> closeHandler = menu.closeHandler();
            closeHandler.handleClose(MenuCloseEvent.event(menu, (Player) event.getPlayer()));
        }
    }
}
