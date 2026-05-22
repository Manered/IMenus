package dev.manere.imenus.event;

import dev.manere.imenus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.DragType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class DragEvent extends MenuEvent.Cancellable {
    private final DragType type;
    private final Map<Integer, ItemStack> newItems;
    private final Set<Integer> containerSlots;

    public DragEvent(final @NotNull Menu menu, final @NotNull Player player, final @NotNull DragType type, final @NotNull Map<Integer, ItemStack> newItems, final @NotNull Set<Integer> containerSlots) {
        super(menu, player);
        this.type = type;
        this.newItems = newItems;
        this.containerSlots = containerSlots;
    }

    @NotNull
    @Unmodifiable
    public Map<Integer, ItemStack> getNewItems() {
        return Collections.unmodifiableMap(newItems);
    }

    @NotNull
    @Unmodifiable
    public Set<Integer> getRawSlots() {
        return newItems.keySet();
    }

    @NotNull
    public Set<Integer> getInventorySlots() {
        return containerSlots;
    }

    @NotNull
    public DragType getType() {
        return type;
    }
}
