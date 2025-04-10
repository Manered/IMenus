package dev.manere.imenus.event;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.Menu;
import dev.manere.imenus.button.ButtonContext;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.DragType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class DragEvent extends MenuEvent.Cancellable {
    private final DragType type;
    private final Map<Integer, ItemStack> addedItems;
    private final Set<Integer> containerSlots;
    private final ButtonContext oldCursor;
    private ButtonContext newCursor;

    public DragEvent(final @NotNull Menu menu, final @NotNull Player player, final @NotNull DragType type, final @NotNull Map<Integer, ItemStack> addedItems, final @NotNull Set<Integer> containerSlots, final @NotNull ButtonContext oldCursor, final @Nullable ButtonContext newCursor) {
        super(menu, player);
        this.type = type;
        this.addedItems = addedItems;
        this.containerSlots = containerSlots;
        this.oldCursor = oldCursor;
        this.newCursor = newCursor;
    }

    @NotNull
    @Unmodifiable
    public Map<Integer, ItemStack> getNewItems() {
        return Collections.unmodifiableMap(addedItems);
    }

    @NotNull
    @Unmodifiable
    public Set<Integer> getRawSlots() {
        return addedItems.keySet();
    }

    @NotNull
    public Set<Integer> getInventorySlots() {
        return containerSlots;
    }

    @NotNull
    public Optional<ButtonContext> getCursor() {
        return Optional.ofNullable(newCursor);
    }

    @NotNull
    public ButtonContext getOldCursor() {
        return oldCursor;
    }

    @NotNull
    public DragType getType() {
        return type;
    }

    @NotNull
    @CanIgnoreReturnValue
    public DragEvent setCursor(final @Nullable ButtonContext newCursor) {
        this.newCursor = newCursor;
        return this;
    }
}
