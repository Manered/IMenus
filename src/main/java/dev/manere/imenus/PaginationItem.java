package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.event.action.ClickAction;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class PaginationItem {
    private ItemProvider itemProvider;
    private BehaviourProvider behaviourProvider;
    private Function<Menu, Integer> slotProvider;

    public PaginationItem() {}

    @NotNull
    public static PaginationItem builder() {
        return new PaginationItem();
    }

    @NotNull
    public PaginationItem.ItemProvider getItemProvider() {
        return itemProvider;
    }

    @NotNull
    public PaginationItem.BehaviourProvider getBehaviourProvider() {
        return behaviourProvider;
    }

    @NotNull
    public Function<Menu, Integer> getSlotProvider() {
        return slotProvider;
    }

    @NotNull
    @CanIgnoreReturnValue
    public PaginationItem setItem(final @NotNull PaginationItem.ItemProvider itemProvider) {
        this.itemProvider = itemProvider;
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public PaginationItem setBehaviour(final @NotNull PaginationItem.BehaviourProvider behaviourProvider) {
        this.behaviourProvider = behaviourProvider;
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public PaginationItem setSlot(final @NotNull Function<Menu, Integer> slotProvider) {
        this.slotProvider = slotProvider;
        return this;
    }

    @FunctionalInterface
    public interface BehaviourProvider {
        @NotNull
        ClickAction accept(final @NotNull Menu menu, final int previousPage, final int currentPage, final int nextPage);
    }

    @FunctionalInterface
    public interface ItemProvider {
        @NotNull
        ItemStack accept(final int previousPage, final int currentPage, final int nextPage);
    }
}
