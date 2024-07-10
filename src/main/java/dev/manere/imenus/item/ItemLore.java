package dev.manere.imenus.item;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Represents lore (description) for an item.
 */
public class ItemLore {
    private final List<Component> lore = new ArrayList<>();

    private ItemLore() {}

    /**
     * Creates an empty {@link ItemLore} instance.
     *
     * @return an empty {@link ItemLore}.
     */
    @NotNull
    public static ItemLore empty() {
        return new ItemLore();
    }

    /**
     * Creates an {@link ItemLore} instance with lore provided by a {@link Consumer}.
     *
     * @param consumer the consumer that provides lore.
     * @return an {@link ItemLore} instance with the provided lore.
     */
    @NotNull
    public static ItemLore lore(final @NotNull Consumer<ItemLore> consumer) {
        final ItemLore lore = empty();
        consumer.accept(lore);
        return lore;
    }

    /**
     * Creates an {@link ItemLore} instance using lore provided by a {@link Supplier}.
     *
     * @param supplier the supplier that provides lore.
     * @return an {@link ItemLore} instance with the provided lore.
     */
    @NotNull
    public static ItemLore lore(final @NotNull Supplier<ItemLore> supplier) {
        return supplier.get();
    }

    /**
     * Creates an {@link ItemLore} instance using lore provided by a {@link UnaryOperator}.
     *
     * @param uop the unary operator that modifies an empty {@link ItemLore}.
     * @return an {@link ItemLore} instance with the provided lore.
     */
    @NotNull
    public static ItemLore lore(final @NotNull UnaryOperator<ItemLore> uop) {
        return uop.apply(empty());
    }

    /**
     * Creates an {@link ItemLore} instance with a single lore component.
     *
     * @param lore the lore component.
     * @return an {@link ItemLore} instance with the provided lore.
     */
    @NotNull
    public static ItemLore lore(final @NotNull Component lore) {
        return lore(List.of(lore));
    }

    /**
     * Creates an {@link ItemLore} instance with multiple lore components.
     *
     * @param lore the list of lore components.
     * @return an {@link ItemLore} instance with the provided lore.
     */
    @NotNull
    public static ItemLore lore(final @NotNull Component @NotNull ... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Creates an {@link ItemLore} instance with a list of lore components.
     *
     * @param lore the list of lore components.
     * @return an {@link ItemLore} instance with the provided lore.
     */
    @NotNull
    public static ItemLore lore(final @NotNull List<Component> lore) {
        final ItemLore itemLore = empty();
        itemLore.lore().clear();
        itemLore.lore().addAll(lore);
        return itemLore;
    }

    /**
     * Adds a lore component to the {@link ItemLore}.
     *
     * @param component the lore component to add.
     * @return the updated {@link ItemLore} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemLore add(final @NotNull Component component) {
        return editLore(components -> components.add(component));
    }

    /**
     * Retrieves the current list of lore components.
     *
     * @return the list of lore components.
     */
    @NotNull
    public List<Component> lore() {
        return lore;
    }

    /**
     * Applies a consumer to modify the lore components of the {@link ItemLore}.
     *
     * @param consumer the consumer that modifies the lore list.
     * @return the updated {@link ItemLore} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public ItemLore editLore(final @NotNull Consumer<List<Component>> consumer) {
        consumer.accept(lore);
        return this;
    }
}
