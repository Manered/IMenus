package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.ButtonContext;
import dev.manere.imenus.event.ClickEvent;
import dev.manere.imenus.event.CloseEvent;
import dev.manere.imenus.event.DragEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
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
    private void handle(final @NotNull InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        final Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        if (inventory.getHolder(false) instanceof Menu menu) {
            Button button = null;

            final ItemStack item = event.getClickedInventory().getItem(event.getRawSlot());
            if (item == null) return;

            for (final Button b : menu.getButtons().values()) {
                final String text = item.getPersistentDataContainer().get(NamespacedKey.minecraft("inventory_menus"), PersistentDataType.STRING);
                if (text == null || text.isBlank()) continue;

                try {
                    final UUID uuid = UUID.fromString(text);
                    if (!uuid.equals(b.getUUID())) continue;

                    button = b;
                    break;
                } catch (final IllegalArgumentException ignored) {}
            }

            if (button == null) return;

            final ClickEvent clickEvent = new ClickEvent(
                menu, player,
                button,
                event.getClick()
            );

            button.getAction().handle(clickEvent);
            if (clickEvent.isCancelled()) event.setCancelled(true);
        }
    }

    @EventHandler
    private void handle(final @NotNull InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        final Inventory inventory = event.getInventory();

        if (inventory.getHolder(false) instanceof Menu menu) {
            final CloseEvent closeEvent = new CloseEvent(menu, player, event.getReason());

            menu.getCloseAction().handle(closeEvent);

            if (closeEvent.isCancelled()) Bukkit.getScheduler().runTaskLater(plugin, () -> menu.open(player, menu.getPage()), 1L);
        }
    }

    @EventHandler
    private void handle(final @NotNull InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        final Inventory inventory = event.getInventory();

        if (inventory.getHolder(false) instanceof Menu menu) {
            ButtonContext oldCursorCtx = null;
            ButtonContext newCursorCtx = null;

            final ItemStack oldCursorItem = event.getOldCursor();
            final ItemStack newCursorItem = event.getCursor();

            for (final Map.Entry<Slot, Button> entry : menu.getButtons().entrySet()) {
                final String text = oldCursorItem.getPersistentDataContainer().get(NamespacedKey.minecraft("inventory_menus"), PersistentDataType.STRING);
                if (text == null || text.isBlank()) continue;

                try {
                    final UUID uuid = UUID.fromString(text);
                    if (!uuid.equals(entry.getValue().getUUID())) continue;

                    oldCursorCtx = ButtonContext.context(entry.getValue(), entry.getKey());
                    break;
                } catch (final IllegalArgumentException ignored) {}
            }

            if (oldCursorCtx == null) return;

            if (newCursorItem != null) for (final Map.Entry<Slot, Button> entry : menu.getButtons().entrySet()) {
                final String text = newCursorItem.getPersistentDataContainer().get(NamespacedKey.minecraft("inventory_menus"), PersistentDataType.STRING);
                if (text == null || text.isBlank()) continue;

                try {
                    final UUID uuid = UUID.fromString(text);
                    if (!uuid.equals(entry.getValue().getUUID())) continue;

                    newCursorCtx = ButtonContext.context(entry.getValue(), entry.getKey());
                    break;
                } catch (final IllegalArgumentException ignored) {}
            }

            final DragEvent dragEvent = new DragEvent(
                menu, player,
                event.getType(), event.getNewItems(), event.getRawSlots(), oldCursorCtx, newCursorCtx
            );

            menu.getDragAction().handle(dragEvent);

            if (dragEvent.isCancelled()) event.setCancelled(true);
        }
    }
}
