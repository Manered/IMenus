package dev.manere.imenus.menu;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.button.ButtonEntry;
import dev.manere.imenus.button.Buttons;
import dev.manere.imenus.event.CloseEventHandler;
import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.item.ItemBuilder;
import dev.manere.imenus.item.PageItem;
import dev.manere.imenus.item.PageItemData;
import dev.manere.imenus.item.PageItemProvider;
import dev.manere.imenus.slot.MenuSlot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * A {@link PagedMenu} that can have pages.
 */
public class PagedMenu implements Menu, InventoryHolder {
    private final Component title;
    private final MenuSize size;

    private final Inventory inventory;

    private final Buttons buttons = Buttons.empty();

    @Nullable
    private PageItemProvider previousPageItem = PageItemProvider.of(ctx -> PageItemData.data(size().size() - 1 - 5, ItemBuilder.item(Material.ARROW)
        .name(Component.text("Previous Page", NamedTextColor.RED)
            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        )
    ));

    @Nullable
    private PageItemProvider currentPageItem = PageItemProvider.of(ctx -> PageItemData.data(size().size() - 1 - 4, ItemBuilder.item(Material.PAPER)
        .name(Component.text("Page: " + ctx.page(), NamedTextColor.GOLD)
            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        )
    ));

    @Nullable
    private PageItemProvider nextPageItem = PageItemProvider.of(ctx -> PageItemData.data(size().size() - 1 - 3, ItemBuilder.item(Material.ARROW)
        .name(Component.text("Next Page", NamedTextColor.GREEN)
            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        )
    ));

    private int page = 1;

    private CloseEventHandler<Menu> closeHandler = event -> {};

    PagedMenu(final @NotNull Component title, final @NotNull MenuSize size) {
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
        final List<ButtonEntry> buttons = this.buttons.buttons();

        int highestPage = 1;

        for (final ButtonEntry entry : buttons) {
            final MenuSlot slot = entry.slot();
            final int page = slot.page();

            if (page > highestPage) highestPage = page;
        }

        return highestPage;
    }

    @Override
    public int page() {
        return page;
    }

    @Override
    public boolean paginated() {
        return true;
    }

    @NotNull
    @Override
    @CanIgnoreReturnValue
    public Menu open(final @NotNull Player player, final int page) {
        if (page > pages()) return open(player, pages());

        inventory().clear();

        this.page = page;

        if (currentPageItem != null && pages() > 1) {
            final PageItemData data = currentPageItem.item(new PageItemProvider.Context(pages(), page()));
            final Button button = ItemBuilder.item(data.item()).asButton(MenuClickEvent::cancel);

            button(data.slot(), page, button);
        }

        if (nextPageItem != null && page() < pages()) {
            final PageItemData data = nextPageItem.item(new PageItemProvider.Context(pages(), page()));
            final Button button = ItemBuilder.item(data.item()).asButton(event -> {
                event.cancel();

                open(player, page + 1);
            });

            button(data.slot(), page, button);
        }

        if (previousPageItem != null && page() > 1) {
            final PageItemData data = previousPageItem.item(new PageItemProvider.Context(pages(), page()));
            final Button button = ItemBuilder.item(data.item()).asButton(event -> {
                event.cancel();

                open(player, page - 1);
            });

            button(data.slot(), page, button);
        }

        for (final ButtonEntry entry : buttons().buttons()) {
            final MenuSlot slot = entry.slot();
            final Button button = entry.button();

            if (slot.page() == page) {
                if (button != null) {
                    inventory().setItem(slot.slot(), button.item());
                } else {
                    inventory.clear(slot.slot());
                }
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
        switch (item) {
            case CURRENT_PAGE -> this.currentPageItem = provider;
            case NEXT_PAGE -> this.nextPageItem = provider;
            case PREVIOUS_PAGE -> this.previousPageItem = provider;
        } return this;
    }
}
