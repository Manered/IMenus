package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.event.CloseEvent;
import dev.manere.imenus.event.DragEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class SimpleMenu {
    protected final Player player;
    protected final Menu menu;

    public SimpleMenu(final @NotNull Player player, final @NotNull Component title, final int rows) {
        this.menu = Menu.menu(title, rows)
            .setCloseAction(this::handle)
            .setDragAction(this::handle);

        this.player = player;
    }

    @NotNull
    @CanIgnoreReturnValue
    public static <M extends SimpleMenu> M open(final @NotNull Supplier<M> menuSupplier) {
        final M simpleMenu = menuSupplier.get();

        simpleMenu.init(() -> Bukkit.getScheduler().runTask(InventoryMenus.API.getPlugin(), () ->
            simpleMenu.getMenu().open(simpleMenu.getPlayer(), 1)
        ));

        return simpleMenu;
    }

    public void open() {
        open(() -> this);
    }

    public abstract void init(final @NotNull CallbackStatus status);

    public void handle(final @NotNull CloseEvent event) {}
    public void handle(final @NotNull DragEvent event) {}

    @NotNull
    public Menu getMenu() {
        return menu;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + "[player = " + player.getName() + "]";
    }
}
