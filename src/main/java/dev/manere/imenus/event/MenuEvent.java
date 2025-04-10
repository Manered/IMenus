package dev.manere.imenus.event;

import dev.manere.imenus.Menu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class MenuEvent {
    private final Menu menu;
    private final Player player;

    public MenuEvent(final @NotNull Menu menu, final @NotNull Player player) {
        this.menu = menu;
        this.player = player;
    }

    @NotNull
    public Menu getMenu() {
        return menu;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    public abstract static class Cancellable extends MenuEvent {
        private boolean isCancelled;

        public Cancellable(final @NotNull Menu menu, final @NotNull Player player) {
            super(menu, player);
        }

        public void cancel() {
            isCancelled = true;
        }

        public void setCancelled(final boolean isCancelled) {
            this.isCancelled = isCancelled;
        }

        public boolean isCancelled() {
            return isCancelled;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "<event " + this.getClass().getSimpleName() +">";
    }
}
