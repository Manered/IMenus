package dev.manere.imenus.button;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.event.action.ClickAction;
import dev.manere.imenus.InventoryMenus;
import dev.manere.imenus.event.MenuEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class Button {
    private final UUID uuid = UUID.randomUUID();
    private final ItemStack item;
    private final ClickAction action;

    public Button(final @NotNull ItemStack item, final @Nullable ClickAction action) {
        this.item = item;
        this.action = action != null ? action : InventoryMenus.API.getConfig()
            .get(InventoryMenus.Options.DEFAULT_BUTTON_CLICK_BEHAVIOUR)
            .orElse(MenuEvent.Cancellable::cancel);
    }

    public Button(final @NotNull ItemStack item) {
        this(item, MenuEvent.Cancellable::cancel);
    }

    @NotNull
    public static Button button(final @NotNull ItemStack item, final @NotNull ClickAction action) {
        return new Button(item, action);
    }

    @NotNull
    public static Button button(final @NotNull ItemStack item) {
        return new Button(item);
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @ApiStatus.Internal
    public void register() {
        item.editMeta(meta -> meta.getPersistentDataContainer()
            .set(NamespacedKey.minecraft("inventory_menus"), PersistentDataType.STRING, uuid.toString())
        );
    }

    public static class Builder {
        private ItemStack item;
        private ClickAction action = MenuEvent.Cancellable::cancel;

        @NotNull
        @CanIgnoreReturnValue
        public Builder setItem(final @NotNull ItemStack item) {
            this.item = item;
            return this;
        }

        @NotNull
        @CanIgnoreReturnValue
        public Builder setAction(final @Nullable ClickAction action) {
            this.action = action;
            return this;
        }

        @NotNull
        public Button build() {
            return new Button(item, action);
        }
    }

    @NotNull
    public ItemStack getItem() {
        return item;
    }

    @NotNull
    public ClickAction getAction() {
        return action;
    }

    @NotNull
    public UUID getUUID() {
        return uuid;
    }

    @NotNull
    @Override
    public String toString() {
        return "Button[uuid = " + uuid + "]";
    }
}
