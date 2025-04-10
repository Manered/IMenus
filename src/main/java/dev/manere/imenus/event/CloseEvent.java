package dev.manere.imenus.event;

import dev.manere.imenus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.jetbrains.annotations.NotNull;

public final class CloseEvent extends MenuEvent.Cancellable {
    private final Reason reason;

    public CloseEvent(final @NotNull Menu menu, final @NotNull Player player, final @NotNull Reason reason) {
        super(menu, player);
        this.reason = reason;
    }

    @NotNull
    public Reason getReason() {
        return reason;
    }
}
