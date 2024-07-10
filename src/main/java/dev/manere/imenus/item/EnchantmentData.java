package dev.manere.imenus.item;

import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an enchantment and its level.
 */
public class EnchantmentData {
    private final Enchantment enchantment;
    private final int level;

    private EnchantmentData(final @NotNull Enchantment enchantment, final int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    /**
     * Creates an {@link EnchantmentData} instance with the specified enchantment and level.
     *
     * @param enchantment the enchantment.
     * @param level       the level of the enchantment.
     * @return a new {@link EnchantmentData} instance.
     */
    @NotNull
    public static EnchantmentData data(final @NotNull Enchantment enchantment, final int level) {
        return new EnchantmentData(enchantment, level);
    }

    /**
     * Creates an {@link EnchantmentData} instance with the maximum level of the specified enchantment.
     *
     * @param enchantment the enchantment.
     * @return a new {@link EnchantmentData} instance.
     */
    @NotNull
    public static EnchantmentData data(final @NotNull Enchantment enchantment) {
        return data(enchantment, enchantment.getMaxLevel());
    }

    /**
     * Retrieves the level of the enchantment.
     *
     * @return the level of the enchantment.
     */
    public int level() {
        return level;
    }

    /**
     * Retrieves the enchantment.
     *
     * @return the enchantment.
     */
    @NotNull
    public Enchantment enchantment() {
        return enchantment;
    }
}
