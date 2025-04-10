package dev.manere.imenus.event;

import dev.manere.imenus.Menu;
import dev.manere.imenus.button.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public final class ClickEvent extends MenuEvent.Cancellable {
    private final Button button;
    private final ClickType type;

    public ClickEvent(final @NotNull Menu menu, final @NotNull Player player, final @NotNull Button button, final @NotNull ClickType type) {
        super(menu, player);
        this.button = button;
        this.type = type;
    }

    @NotNull
    public Button getButton() {
        return button;
    }

    @NotNull
    public ClickType getType() {
        return type;
    }
}
