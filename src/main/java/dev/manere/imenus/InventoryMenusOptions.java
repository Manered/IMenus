package dev.manere.imenus;

import dev.manere.imenus.event.MenuEvent;
import dev.manere.imenus.event.action.ClickAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashSet;
import java.util.Set;

public final class InventoryMenusOptions {
    @NotNull
    public static final Set<InventoryMenusOption<?>> REGISTRY = new HashSet<>();

    @NotNull
    public static final InventoryMenusOption<ClickAction> DEFAULT_BUTTON_CLICK_BEHAVIOUR = register(
        "default_button_click_behaviour", MenuEvent.Cancellable::cancel
    );

    @NotNull
    public static final InventoryMenusOption<PaginationItem> PREVIOUS_PAGE = register("previous_page_button", PaginationItem.builder()
        .setSlot((menu) -> menu.getSize() - 6)
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
    public static final InventoryMenusOption<PaginationItem> CURRENT_PAGE = register("current_page_button", PaginationItem.builder()
        .setSlot(menu -> menu.getSize() - 5)
        .setItem((previous, current, next) -> ItemBuilder.builder(Material.PAPER, current)
            .setName(Component.text("Page: " + current, NamedTextColor.WHITE)
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            )
            .build()
        )
        .setBehaviour((menu, previous, current, next) -> MenuEvent.Cancellable::cancel)
    );

    @NotNull
    public static final InventoryMenusOption<PaginationItem> NEXT_PAGE = register("next_page_button", PaginationItem.builder()
        .setSlot((menu) -> menu.getSize() - 4)
        .setItem((previous, current, next) -> ItemBuilder.builder(Material.GREEN_DYE, next)
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
    public static <V> InventoryMenusOption<V> register(final @NotNull String key, final @Nullable V defaultValue) {
        final InventoryMenusOption<V> option = new InventoryMenusOption<>(key, defaultValue);
        REGISTRY.add(option);

        return option;
    }

    @NotNull
    @ApiStatus.Internal
    public static <V> InventoryMenusOption<V> register(final @NotNull String key) {
        return register(key, null);
    }

    @NotNull
    @Unmodifiable
    public static Set<InventoryMenusOption<?>> getRegistry() {
        return Set.copyOf(REGISTRY);
    }
}
