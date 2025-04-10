package dev.manere.imenus;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.event.action.ClickAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemBuilder {
    private ItemStack item;

    public ItemBuilder(final @NotNull ItemStack item) {
        this.item = item;
    }

    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder builder(final @NotNull ItemStack item) {
        return new ItemBuilder(item);
    }

    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder builder(final @NotNull Material type, final int amount) {
        return builder(ItemStack.of(type, amount));
    }

    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder builder(final @NotNull Material type) {
        return builder(type, 1);
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder setName(final @NotNull Component name) {
        this.item.editMeta(meta -> meta.displayName(name));
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder setName(final @NotNull String name) {
        return setName(MiniMessage.miniMessage().deserialize(name).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder setLore(final @NotNull Consumer<LoreBuilder> loreBuilder) {
        final LoreBuilder builder = new LoreBuilder();
        loreBuilder.accept(builder);

        this.item.editMeta(meta -> meta.lore(builder.build()));
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder setSkull(final @NotNull UUID uuid) {
        this.item = item.withType(Material.PLAYER_HEAD);

        this.item.editMeta(SkullMeta.class, meta -> {
            final PlayerProfile profile = Bukkit.createProfile(uuid);

            Bukkit.getAsyncScheduler().runNow(InventoryMenus.API.getPlugin(), task -> {
                profile.complete(true);
                Bukkit.getScheduler().runTask(InventoryMenus.API.getPlugin(), () -> meta.setPlayerProfile(profile));
            });
        });

        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder setFlags(final @NotNull ItemFlag... flags) {
        this.item.editMeta(meta -> meta.addItemFlags(flags));
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder overrideEnchantmentGlint() {
        this.item.editMeta(meta -> meta.setEnchantmentGlintOverride(true));
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder enchant(final @NotNull Enchantment enchantment, final int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder setMaxStackSize(final @Nullable Integer max) {
        this.item.editMeta(meta -> meta.setMaxStackSize(max));
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder editMeta(final @NotNull Consumer<ItemMeta> editor) {
        this.item.editMeta(editor);
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public <M extends ItemMeta> ItemBuilder editMeta(final @NotNull Class<M> type, final @NotNull Consumer<M> editor) {
        this.item.editMeta(type, editor);
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder editPDC(final @NotNull Consumer<PersistentDataContainer> editor) {
        return editMeta(meta -> editor.accept(meta.getPersistentDataContainer()));
    }

    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder editItem(final @NotNull Consumer<ItemStack> editor) {
        editor.accept(item);
        return this;
    }

    @NotNull
    public Button asButton() {
        return Button.button(build());
    }

    @NotNull
    public Button asButton(final @NotNull ClickAction action) {
        return Button.button(build(), action);
    }

    @NotNull
    public ItemStack build() {
        return this.item;
    }

    public static class LoreBuilder {
        private final List<Component> lore = new ArrayList<>();

        @NotNull
        @CanIgnoreReturnValue
        public LoreBuilder add(final @NotNull Component line) {
            this.lore.add(line);
            return this;
        }

        @NotNull
        @CanIgnoreReturnValue
        public LoreBuilder add(final @NotNull String line) {
            return add(MiniMessage.miniMessage().deserialize(line).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        }

        @NotNull
        @Unmodifiable
        public List<Component> build() {
            return List.copyOf(lore);
        }
    }
}
