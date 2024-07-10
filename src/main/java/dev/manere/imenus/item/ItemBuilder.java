package dev.manere.imenus.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.button.Button;
import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Fluent builder for creating and customizing {@link ItemStack}s easily.
 */
public class ItemBuilder {
    private ItemStack item;

    /**
     * Constructs an {@link ItemBuilder} instance with the specified {@link ItemStack}.
     *
     * @param item the base {@link ItemStack} to build upon.
     */
    private ItemBuilder(final @NotNull ItemStack item) {
        this.item = item;
    }

    /**
     * Constructs an {@link ItemBuilder} instance with a new {@link ItemStack} of the specified {@link Material} and amount.
     *
     * @param material the {@link Material} of the item.
     * @param amount   the amount of items.
     */
    private ItemBuilder(final @NotNull Material material, final int amount) {
        this(new ItemStack(material, amount));
    }

    /**
     * Constructs an {@link ItemBuilder} instance with a new {@link ItemStack} of the specified {@link Material} and default amount (1).
     *
     * @param material the {@link Material} of the item.
     */
    private ItemBuilder(final @NotNull Material material) {
        this(material, 1);
    }

    /**
     * Creates a new {@link ItemBuilder} with an empty {@link ItemStack} (Material AIR).
     *
     * @return a new {@link ItemBuilder} instance with an empty item.
     */
    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder empty() {
        return ItemBuilder.item(Material.AIR, 1);
    }

    /**
     * Creates a new {@link ItemBuilder} with a skull {@link ItemStack} (Material PLAYER_HEAD).
     *
     * @return a new {@link ItemBuilder} instance with a skull item.
     */
    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder skull() {
        return skull(1);
    }

    /**
     * Creates a new {@link ItemBuilder} with a skull {@link ItemStack} (Material PLAYER_HEAD) and the specified amount.
     *
     * @param amount the amount of skull items.
     * @return a new {@link ItemBuilder} instance with a skull item.
     */
    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder skull(final int amount) {
        return item(Material.PLAYER_HEAD, amount);
    }

    /**
     * Creates a new {@link ItemBuilder} with a new {@link ItemStack} of the specified {@link Material} and amount.
     *
     * @param material the {@link Material} of the item.
     * @param amount   the amount of items.
     * @return a new {@link ItemBuilder} instance with the specified item.
     */
    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder item(final @NotNull Material material, final int amount) {
        return new ItemBuilder(material, amount);
    }

    /**
     * Creates a new {@link ItemBuilder} with a new {@link ItemStack} of the specified {@link Material}.
     *
     * @param material the {@link Material} of the item.
     * @return a new {@link ItemBuilder} instance with the specified item.
     */
    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder item(final @NotNull Material material) {
        return new ItemBuilder(material);
    }

    /**
     * Creates a new {@link ItemBuilder} with the specified {@link ItemStack}.
     *
     * @param item the {@link ItemStack} to use.
     * @return a new {@link ItemBuilder} instance with the specified item.
     */
    @NotNull
    @CanIgnoreReturnValue
    public static ItemBuilder item(final @NotNull ItemStack item) {
        return new ItemBuilder(item);
    }

    /**
     * Sets the display name of the item.
     *
     * @param name the display name {@link Component}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder name(final @NotNull Component name) {
        return meta(meta -> meta.displayName(name));
    }

    /**
     * Sets the skull owner of the item.
     *
     * @param owner the {@link OfflinePlayer} skull owner.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder skull(final @Nullable OfflinePlayer owner) {
        return meta(SkullMeta.class, meta -> meta.setOwningPlayer(owner));
    }

    /**
     * Sets the skull owner of the item using a {@link PlayerProfile}.
     *
     * @param owner the {@link PlayerProfile} skull owner.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder skull(final @Nullable PlayerProfile owner) {
        return meta(SkullMeta.class, meta -> meta.setPlayerProfile(owner));
    }

    /**
     * Sets the skull owner of the item using a player's name.
     *
     * @param owner the name of the player skull owner.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder skull(final @Nullable String owner) {
        type(Material.PLAYER_HEAD);

        return meta(SkullMeta.class, meta -> {
            if (owner != null) meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        });
    }

    /**
     * Sets the skull owner of the item using a player's UUID.
     *
     * @param owner the UUID of the player skull owner.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder skull(final @Nullable UUID owner) {
        type(Material.PLAYER_HEAD);

        return meta(SkullMeta.class, meta -> {
            if (owner != null) meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        });
    }

    /**
     * Adds lore to the item.
     *
     * @param lore the lore {@link Component}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull Component lore) {
        return meta(meta -> meta.lore(Collections.singletonList(lore)));
    }

    /**
     * Adds multiple lines of lore to the item.
     *
     * @param lore the array of lore {@link Component}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull Component... lore) {
        return meta(meta -> meta.lore(new ArrayList<>(Arrays.asList(lore))));
    }

    /**
     * Adds a list of lore to the item.
     *
     * @param lore the list of lore {@link Component}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull List<Component> lore) {
        return meta(meta -> meta.lore(new ArrayList<>(lore)));
    }

    /**
     * Adds lore to the item using an {@link ItemLore} instance.
     *
     * @param lore the {@link ItemLore} instance.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull ItemLore lore) {
        return meta(meta -> meta.lore(lore.lore()));
    }

    /**
     * Adds lore to the item using a {@link Consumer} that accepts {@link ItemLore}.
     *
     * @param lore the lore {@link Consumer}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull Consumer<ItemLore> lore) {
        final ItemLore itemLore = ItemLore.empty();
        lore.accept(itemLore);

        return lore(itemLore);
    }

    /**
     * Adds lore to the item using a {@link Supplier} that supplies {@link ItemLore}.
     *
     * @param lore the lore {@link Supplier}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull Supplier<ItemLore> lore) {
        return lore(lore.get());
    }

    /**
     * Applies a {@link UnaryOperator} to modify the current lore of the item.
     *
     * @param lore the lore {@link UnaryOperator}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder lore(final @NotNull UnaryOperator<ItemLore> lore) {
        return lore(lore.apply(ItemLore.empty()));
    }

    /**
     * Adds an enchantment to the item with the specified level.
     *
     * @param enchantment the {@link Enchantment} to add.
     * @param level       the level of the enchantment.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder enchant(final @NotNull Enchantment enchantment, final int level) {
        return meta(meta -> meta.addEnchant(enchantment, level, true));
    }

    /**
     * Adds the maximum level of an enchantment to the item.
     *
     * @param enchantment the {@link Enchantment} to add.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder enchant(final @NotNull Enchantment enchantment) {
        return meta(meta -> meta.addEnchant(enchantment, enchantment.getMaxLevel(), true));
    }

    /**
     * Adds multiple enchantments to the item.
     *
     * @param enchantments the array of {@link Enchantment}s to add.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder enchant(final @NotNull Enchantment @NotNull ... enchantments) {
        return meta(meta -> {
            for (final Enchantment enchantment : enchantments) {
                meta.addEnchant(enchantment, enchantment.getMaxLevel(), true);
            }
        });
    }

    /**
     * Adds enchantments or {@link EnchantmentData} instances to the item.
     *
     * @param enchantments the array of enchantments or {@link EnchantmentData} instances.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder enchant(final @NotNull Object @NotNull ... enchantments) {
        return meta(meta -> {
            for (final Object data : enchantments) {
                if (data instanceof EnchantmentData enchantmentData) {
                    meta.addEnchant(enchantmentData.enchantment(), enchantmentData.level(), true);
                } else if (data instanceof Enchantment enchantment) {
                    meta.addEnchant(enchantment, enchantment.getMaxLevel(), true);
                }
            }
        });
    }

    /**
     * Removes an enchantment from the item.
     *
     * @param enchantment the {@link Enchantment} to remove.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder remove(final @NotNull Enchantment enchantment) {
        return removeEnchantment(enchantment);
    }

    /**
     * Removes an enchantment from the item.
     *
     * @param enchantment the {@link Enchantment} to remove.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder removeEnchantment(final @NotNull Enchantment enchantment) {
        return meta(meta -> {
            meta.removeEnchant(enchantment);
        });
    }

    /**
     * Clears all enchantments from the item.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder clearEnchantments() {
        return meta(ItemMeta::removeEnchantments);
    }

    /**
     * Sets the damage value of the item.
     *
     * @param damage the damage value.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder damage(final int damage) {
        return meta(Damageable.class, damageable -> damageable.setDamage(damage));
    }

    /**
     * Adds item flags to the item.
     *
     * @param flags the {@link ItemFlag}s to add.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder add(final @NotNull ItemFlag @NotNull ... flags) {
        return meta(meta -> meta.addItemFlags(flags));
    }

    /**
     * Removes item flags from the item.
     *
     * @param flags the {@link ItemFlag}s to remove.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder remove(final @NotNull ItemFlag @NotNull ... flags) {
        return meta(meta -> meta.removeItemFlags(flags));
    }

    /**
     * Adds a glow effect to the item by enchanting it with LUCK (if supported) and hiding the enchantment.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder glow() {
        return enchant(Enchantment.LUCK, 1).add(ItemFlag.HIDE_ENCHANTS);
    }

    /**
     * Alias for {@link #glow()}.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder shine() {
        return glow();
    }

    /**
     * Removes the glow effect from the item by removing the LUCK enchantment and the HIDE_ENCHANTS flag.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder removeGlow() {
        return remove(Enchantment.LUCK).remove(ItemFlag.HIDE_ENCHANTS);
    }

    /**
     * Sets whether the item is unbreakable.
     *
     * @param unbreakable true to set the item as unbreakable, false otherwise.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder unbreakable(final boolean unbreakable) {
        return meta(meta -> meta.setUnbreakable(unbreakable));
    }

    /**
     * Sets the item as unbreakable.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder unbreakable() {
        return unbreakable(true);
    }

    /**
     * Sets the item as breakable (not unbreakable).
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder breakable() {
        return unbreakable(false);
    }

    /**
     * Sets the custom model data of the item.
     *
     * @param data the custom model data value.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder customModelData(final @Nullable Integer data) {
        return meta(meta -> meta.setCustomModelData(data));
    }

    /**
     * Sets the amount of items in the stack.
     *
     * @param amount the amount of items.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder amount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }

    /**
     * Sets the amount of items in the stack to 1.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder single() {
        return amount(1);
    }

    /**
     * Sets the amount of items in the stack to the maximum stack size of the item.
     *
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder stackSize() {
        return amount(item.getMaxStackSize());
    }

    /**
     * Sets the {@link Material} type of the item.
     *
     * @param material the {@link Material} type.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder material(final @NotNull Material material) {
        return type(material);
    }

    /**
     * Sets the {@link Material} type of the item.
     *
     * @param material the {@link Material} type.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder type(final @NotNull Material material) {
        this.item = this.item.withType(material);
        return this;
    }

    /**
     * Edits the meta of the item using a {@link Consumer}.
     *
     * @param editor the meta {@link Consumer}.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder meta(final @NotNull Consumer<ItemMeta> editor) {
        item.editMeta(editor);
        return this;
    }

    /**
     * Edits the meta of the item using a {@link Consumer} for a specific meta type.
     *
     * @param type   the meta type class.
     * @param editor the meta {@link Consumer}.
     * @param <M>    the type of meta.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public <M extends ItemMeta> ItemBuilder meta(final @NotNull Class<M> type, final @NotNull Consumer<M> editor) {
        item.editMeta(type, editor);
        return this;
    }

    /**
     * Applies an {@link Consumer} to edit the current {@link ItemStack}.
     *
     * @param editor the {@link Consumer} to apply.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder edit(final @NotNull Consumer<ItemStack> editor) {
        editor.accept(item);
        return this;
    }

    /**
     * Applies an {@link Consumer} to edit the persistent data container of the current {@link ItemStack}.
     *
     * @param editor the {@link Consumer} to apply.
     * @return the updated {@link ItemBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemBuilder pdc(final @NotNull Consumer<PersistentDataContainer> editor) {
        final PersistentDataContainer container = asMeta().getPersistentDataContainer();
        editor.accept(container);
        return this;
    }

    /**
     * Converts the {@link ItemStack} into a {@link Button}.
     *
     * @return the {@link Button} representation of the {@link ItemStack}.
     */
    @NotNull
    public Button asButton() {
        return Button.button(asItem());
    }

    /**
     * Converts the {@link ItemStack} into a {@link Button} with a click handler.
     *
     * @param clickHandler the click handler {@link Consumer}.
     * @return the {@link Button} representation of the {@link ItemStack} with the specified click handler.
     */
    @NotNull
    public Button asButton(final @NotNull Consumer<MenuClickEvent<? extends Menu>> clickHandler) {
        return Button.button(asItem(), clickHandler);
    }

    /**
     * Builds and retrieves the {@link ItemStack}.
     *
     * @return the built {@link ItemStack}.
     */
    @NotNull
    public ItemStack build() {
        return asItem();
    }

    /**
     * Retrieves the {@link ItemStack}.
     *
     * @return the {@link ItemStack}.
     */
    @NotNull
    public ItemStack asItem() {
        return item;
    }

    /**
     * Retrieves the {@link ItemMeta} of the {@link ItemStack}.
     *
     * @return the {@link ItemMeta}.
     */
    @NotNull
    public ItemMeta asMeta() {
        return item.getItemMeta();
    }
}
