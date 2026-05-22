package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.event.ClickEvent;
import dev.manere.imenus.event.CloseEvent;
import dev.manere.imenus.event.DragEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class InventoryMenus implements Listener {
    public static final InventoryMenus API = new InventoryMenus();
    private JavaPlugin plugin;

    private final InventoryMenusConfig config = new InventoryMenusConfig();

    private InventoryMenus() {}

    @NotNull
    public Menu createMenu(final @NotNull Component title, final int rows) {
        return Menu.menu(title, rows);
    }

    @NotNull
    public SimpleStaticMenu.Builder createStaticMenu(final @NotNull Player player) {
        return SimpleStaticMenu.builder(player);
    }

    @NotNull
    @CanIgnoreReturnValue
    public InventoryMenus register(final @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public InventoryMenus register(final @NotNull JavaPlugin plugin, final @NotNull Consumer<InventoryMenusConfig> consumer) {
        return register(plugin)
            .configure(consumer);
    }

    @NotNull
    @CanIgnoreReturnValue
    public InventoryMenus configure(final @NotNull Consumer<InventoryMenusConfig> consumer) {
        consumer.accept(getConfig());
        return this;
    }

    @NotNull
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @NotNull
    public InventoryMenusConfig getConfig() {
        return config;
    }

    @EventHandler
    public void onClick(final @NotNull InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        final Inventory top = event.getView().getTopInventory();

        if (!(top.getHolder(false) instanceof Menu menu)) return;

        final int raw = event.getRawSlot();
        final boolean topInventory = raw < menu.getSize();

        if (event.isShiftClick() && !menu.shouldAllowShiftClick()) {
            event.setCancelled(true);
            return;
        }

        if (topInventory) {
            if (menu.shouldCancelTopInventoryClicks()) {
                event.setCancelled(true);
            }
        } else {
            if (menu.shouldCancelPlayerInventoryClicks()) {
                event.setCancelled(true);
            }
        }

        // outside inventory click
        if (raw < 0) {
            return;
        }

        // only top inventory buttons are handled
        if (!topInventory) {
            return;
        }

        final Slot slot = Slot.slot(menu.getPage(), raw);
        final Button button = menu.getButtons().get(slot);

        if (button == null) {
            return;
        }

        final ClickEvent clickEvent = new ClickEvent(
            menu,
            player,
            button,
            event.getClick()
        );

        button.getAction().handle(clickEvent);

        event.setCancelled(clickEvent.isCancelled());
    }

    @EventHandler
    public void onDrag(final @NotNull InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        final Inventory top = event.getView().getTopInventory();

        if (!(top.getHolder(false) instanceof Menu menu)) return;

        boolean draggingTop = false;
        boolean draggingBottom = false;

        for (final int rawSlot : event.getRawSlots()) {
            if (rawSlot < menu.getSize()) {
                draggingTop = true;
            } else {
                draggingBottom = true;
            }
        }

        if (draggingTop && menu.shouldCancelTopInventoryDrags()) {
            event.setCancelled(true);
        }

        if (draggingBottom && menu.shouldCancelPlayerInventoryDrags()) {
            event.setCancelled(true);
        }

        final DragEvent dragEvent = new DragEvent(
            menu,
            player,
            event.getType(),
            event.getNewItems(),
            event.getRawSlots()
        );

        menu.getDragAction().handle(dragEvent);

        if (dragEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(final @NotNull InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        final Inventory inv = event.getInventory();
        if (!(inv.getHolder() instanceof Menu menu)) return;

        final CloseEvent closeEvent = new CloseEvent(menu, player, event.getReason());
        menu.getCloseAction().handle(closeEvent);

        if (closeEvent.isCancelled()) {
            Bukkit.getScheduler().runTask(plugin, () ->
                menu.open(player, menu.getPage())
            );
        }
    }
}
