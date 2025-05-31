package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.event.MenuEvent;
import dev.manere.imenus.event.action.ClickAction;
import dev.manere.imenus.event.action.CloseAction;
import dev.manere.imenus.event.action.DragAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Menu implements InventoryHolder {
    private final Map<Slot, Button> buttons = new HashMap<>();

    private final Inventory inventory;
    private final Component title;
    private final int size;

    private CloseAction closeAction = event -> {};
    private DragAction dragAction = event -> {};
    private int page = 1;

    public Menu(final @NotNull Component title, final int rows) {
        this.title = title;
        this.size = rows * 9;
        this.inventory = Bukkit.createInventory(this, this.size, title);
    }

    @NotNull
    public static final Function<Component, Menu> CHEST_1x9 = title -> Menu.menu(title, 1);

    @NotNull
    public static final Function<Component, Menu> CHEST_2x9 = title -> Menu.menu(title, 2);

    @NotNull
    public static final Function<Component, Menu> CHEST_3x9 = title -> Menu.menu(title, 3);

    @NotNull
    public static final Function<Component, Menu> CHEST_4x9 = title -> Menu.menu(title, 4);

    @NotNull
    public static final Function<Component, Menu> CHEST_5x9 = title -> Menu.menu(title, 5);

    @NotNull
    public static final Function<Component, Menu> CHEST_6x9 = title -> Menu.menu(title, 6);

    @NotNull
    public static Menu menu(final @NotNull Component title, final int rows) {
        return new Menu(title, rows);
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu removeButton(final @NotNull Slot slot) {
        buttons.remove(slot);
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu removeButton(final int page, final int index) {
        return removeButton(Slot.slot(page, index));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu removeButton(final int index) {
        return removeButton(1, index);
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final @NotNull Slot slot, final @Nullable Button button) {
        if (button == null) {
            buttons.remove(slot);
            if (this.getPage() == slot.getPage()) runSync(() -> inventory.setItem(slot.getIndex(), null));
            return this;
        }

        button.register();
        buttons.put(slot, button);

        if (this.getPage() == slot.getPage()) runSync(() -> inventory.setItem(slot.getIndex(), button.getItem()));
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final @NotNull Slot slot, final @NotNull ItemStack item, final @NotNull ClickAction action) {
        return setButton(slot, new Button(item, action));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final @NotNull Slot slot, final @NotNull ItemStack item) {
        return setButton(slot, new Button(item));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final int page, final int index, final @Nullable Button button) {
        return setButton(new Slot(page, index), button);
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final int page, final int index, final @NotNull ItemStack item, final @NotNull ClickAction action) {
        return setButton(new Slot(page, index), Button.button(item, action));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final int page, final int index, final @NotNull ItemStack item) {
        return setButton(new Slot(page, index), Button.button(item));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final int index, final @Nullable Button button) {
        return setButton(page, index, button);
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final int index, final @NotNull ItemStack item, final @NotNull ClickAction action) {
        return setButton(page, index, Button.button(item, action));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setButton(final int index, final @NotNull ItemStack item) {
        return setButton(page, index, Button.button(item));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setCloseAction(final @NotNull CloseAction action) {
        this.closeAction = action;
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setDragAction(final @NotNull DragAction action) {
        this.dragAction = action;
        return this;
    }

    @NotNull
    public CloseAction getCloseAction() {
        return closeAction;
    }

    @NotNull
    public DragAction getDragAction() {
        return dragAction;
    }

    public int getPages() {
        int highestPage = 1;

        for (final Map.Entry<Slot, Button> entry : buttons.entrySet()) {
            final Slot slot = entry.getKey();
            final int page = slot.getPage();

            if (page > highestPage) highestPage = page;
        }

        return highestPage;
    }

    public int getPage() {
        return page;
    }

    @NotNull
    public Component getTitle() {
        return title;
    }

    public int getRows() {
        return getSize() / 9;
    }

    public int getSize() {
        return size;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @NotNull
    @Unmodifiable
    public Map<Slot, Button> getButtons() {
        return Map.copyOf(new HashMap<>(buttons));
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu clearButtons() {
        this.buttons.clear();
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu clearInventory() {
        this.inventory.clear();
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu clear() {
        return clearButtons().clearInventory();
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu open(final @NotNull Player player, final int page) {
        if (page > getPages()) return open(player, getPages());
        this.page = page;

        runSync(() -> {
            getInventory().clear();

            final PaginationItem nextPageItem = InventoryMenus.API.getConfig().get(InventoryMenus.Options.NEXT_PAGE).orElse(null);
            final PaginationItem currentPageItem = InventoryMenus.API.getConfig().get(InventoryMenus.Options.CURRENT_PAGE).orElse(null);
            final PaginationItem previousPageItem = InventoryMenus.API.getConfig().get(InventoryMenus.Options.PREVIOUS_PAGE).orElse(null);

            if (currentPageItem != null && getPages() > 1) setPaginationButton(page, currentPageItem);
            if (nextPageItem != null && getPage() < getPages()) setPaginationButton(page, nextPageItem);
            if (previousPageItem != null && getPage() > 1) setPaginationButton(page, previousPageItem);

            for (final Map.Entry<Slot, Button> entry : getButtons().entrySet()) {
                final Slot slot = entry.getKey();
                final Button button = entry.getValue();

                if (slot.getPage() == page) {
                    if (button != null) {
                        getInventory().setItem(slot.getIndex(), button.getItem());
                    } else {
                        getInventory().clear(slot.getIndex());
                    }
                }
            }

            player.openInventory(getInventory());
        });

        return this;
    }

    private void runSync(final @NotNull Runnable runnable) {
        Bukkit.getScheduler().runTask(InventoryMenus.API.getPlugin(), runnable);
    }

    @NotNull
    @ApiStatus.Internal
    @CanIgnoreReturnValue
    private Menu setPaginationButton(final int page, final @NotNull PaginationItem paginationItem) {
        final ClickAction behaviour = paginationItem.getBehaviourProvider().accept(this, page - 1, page, page + 1);
        final ItemStack item = paginationItem.getItemProvider().accept(page - 1, page, page + 1);
        final int index = paginationItem.getSlotProvider().apply(this);

        return setButton(page, index, item, behaviour);
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu open(final @NotNull Player player) {
        return open(player, 1);
    }

    @NotNull
    @CanIgnoreReturnValue
    public Menu setBorder(final @NotNull Material type) {
        final ClickAction behaviour = MenuEvent.Cancellable::cancel;

        final ItemStack item = ItemStack.of(type, 1);
        item.editMeta(meta -> {
            meta.setHideTooltip(true);
            meta.setUnbreakable(true);
            meta.displayName(Component.text(" "));
            meta.addItemFlags(ItemFlag.values());
        });

        if (getRows() < 3) return this;

        int[] topSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] bottomSlots = {getSize() - 1, getSize() - 2, getSize() - 3, getSize() - 4, getSize() - 5, getSize() - 6, getSize() - 7, getSize() - 8, getSize() - 9};

        for (int slot : topSlots) {
            setButton(page, slot, item, behaviour);
        }

        for (int row = 1; row < getRows() - 1; row++) {
            setButton(page, (row * 9)    , item, behaviour);
            setButton(page, (row * 9) + 8, item, behaviour);
        }

        for (int slot : bottomSlots) {
            setButton(page, slot, item, behaviour);
        }

        return this;
    }

    @NotNull
    @Override
    public String toString() {
        return "<menu " + PlainTextComponentSerializer.plainText().serialize(title) +">";
    }
}
