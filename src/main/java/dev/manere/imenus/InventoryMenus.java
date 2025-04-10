package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.ButtonContext;
import dev.manere.imenus.event.ClickEvent;
import dev.manere.imenus.event.CloseEvent;
import dev.manere.imenus.event.DragEvent;
import dev.manere.imenus.event.MenuEvent;
import dev.manere.imenus.event.action.ClickAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class InventoryMenus implements Listener {
    public static final InventoryMenus API = new InventoryMenus();
    private JavaPlugin plugin;

    private final Config config = new Config();

    private InventoryMenus() {}

    @NotNull
    @CanIgnoreReturnValue
    public InventoryMenus register(final @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    @NotNull
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @NotNull
    public Config getConfig() {
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

    public static final class Config {
        private final Map<Option<?>, Object> options = new ConcurrentHashMap<>();

        @NotNull
        @CanIgnoreReturnValue
        public <V> Config set(final @NotNull Option<V> option, final @Nullable V value) {
            this.options.put(option, value);
            return this;
        }

        @NotNull
        @CanIgnoreReturnValue
        public <V> Config edit(final @NotNull Option<V> option, final @NotNull Consumer<@Nullable V> value) {
            final V appliedValue = get(option).orElse(null);
            value.accept(appliedValue);

            this.options.put(option, appliedValue);
            return this;
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public <V> Optional<V> get(final @NotNull Option<V> option) {
            final Optional<V> defaultValue = option.getDefaultValue();

            try {
                final Optional<V> customValue = Optional.ofNullable((V) options.get(option));
                return customValue.isPresent() ? customValue : defaultValue;
            } catch (final ClassCastException ignored) {
                return defaultValue;
            }
        }

        @NotNull
        @Unmodifiable
        public Map<Option<?>, Object> getOptions() {
            return Map.copyOf(options);
        }
    }

    public static final class Options {
        @NotNull
        public static final Set<Option<?>> REGISTRY = new HashSet<>();

        @NotNull
        public static final Option<ClickAction> DEFAULT_BUTTON_CLICK_BEHAVIOUR = register(
            "default_button_click_behaviour", MenuEvent.Cancellable::cancel
        );

        @NotNull
        public static final Option<PaginationItem> PREVIOUS_PAGE = register("previous_page_button", PaginationItem.builder()
            .setSlot((menu) -> menu.getSize() - 5)
            .setItem((previous, current, next) -> ItemBuilder.builder(Material.RED_DYE, previous)
                .setName(Component.text("Previous Page", NamedTextColor.WHITE)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                )
                .build()
            )
            .setBehaviour((menu, previous, current, next) -> event -> {
                event.cancel();
                menu.open(event.getPlayer(), previous);
            })
        );

        @NotNull
        public static final Option<PaginationItem> CURRENT_PAGE = register("current_page_button", PaginationItem.builder()
            .setSlot(menu -> menu.getSize() - 4)
            .setItem((previous, current, next) -> ItemBuilder.builder(Material.RED_DYE, current)
                .setName(Component.text("Page: " + current, NamedTextColor.WHITE)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                )
                .build()
            )
            .setBehaviour((menu, previous, current, next) -> MenuEvent.Cancellable::cancel)
        );

        @NotNull
        public static final Option<PaginationItem> NEXT_PAGE = register("next_page_button", PaginationItem.builder()
            .setSlot((menu) -> menu.getSize() - 3)
            .setItem((previous, current, next) -> ItemBuilder.builder(Material.RED_DYE, next)
                .setName(Component.text("Next Page", NamedTextColor.WHITE)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                )
                .build()
            )
            .setBehaviour((menu, previous, current, next) -> event -> {
                event.cancel();
                menu.open(event.getPlayer(), next);
            })
        );

        @NotNull
        @ApiStatus.Internal
        public static <V> Option<V> register(final @NotNull String key, final @Nullable V defaultValue) {
            final Option<V> option = new Option<>(key, defaultValue);
            REGISTRY.add(option);

            return option;
        }

        @NotNull
        @ApiStatus.Internal
        public static <V> Option<V> register(final @NotNull String key) {
            return register(key, null);
        }

        @NotNull
        @Unmodifiable
        public static Set<Option<?>> getRegistry() {
            return Set.copyOf(REGISTRY);
        }
    }

    public static final class Option<V> {
        @NotNull
        private final String key;

        @Nullable
        private final V defaultValue;

        public Option(final @NotNull String key, final @Nullable V defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        @NotNull
        public Optional<V> getDefaultValue() {
            return Optional.ofNullable(defaultValue);
        }

        @NotNull
        public String getKey() {
            return key;
        }

        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Option<?> other && other.getKey().equals(this.getKey());
        }
    }
}
