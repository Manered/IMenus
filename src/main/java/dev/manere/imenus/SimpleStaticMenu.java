package dev.manere.imenus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SimpleStaticMenu extends SimpleMenu {
    private final Consumer<Menu> handler;

    private SimpleStaticMenu(final @NotNull Player player, final @NotNull Component title, final int rows, final @NotNull Consumer<Menu> handler) {
        super(player, title, rows);
        this.handler = handler;
    }

    @Override
    public void init(final @NotNull CallbackStatus status) {
        handler.accept(menu);
        status.update();
    }

    @NotNull
    public static SimpleStaticMenu.Builder builder(final @NotNull Player player) {
        return new Builder(player);
    }

    public static class Builder {
        private final Player player;

        private Component title = Component.empty();
        private int rows = 3;
        private Consumer<Menu> handler = menu -> {};

        private Builder(final @NotNull Player player) {
            this.player = player;
        }

        @NotNull
        public Builder setTitle(final @NotNull Component title) {
            this.title = title;
            return this;
        }

        @NotNull
        public Builder setTitle(final @NotNull String title) {
            return setTitle(MiniMessage.miniMessage().deserialize(title));
        }

        @NotNull
        public Builder setRows(final int rows) {
            this.rows = rows;
            return this;
        }

        @NotNull
        public Builder setSize(final int size) {
            return setRows(size / 9);
        }

        @NotNull
        public Builder handle(final @NotNull Consumer<Menu> handler) {
            this.handler = handler;
            return this;
        }

        @NotNull
        public SimpleStaticMenu build() {
            return new SimpleStaticMenu(player, title, rows, handler);
        }
    }
}
