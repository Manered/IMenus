package dev.manere.imenus;

import dev.manere.imenus.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Main class for handling inventory menus.
 */
public final class InventoryMenus {
    private static JavaPlugin plugin;

    /**
     * Creates a new {@link InventoryMenusBuilder} instance.
     *
     * @return a new {@link InventoryMenusBuilder}.
     */
    @NotNull
    public static InventoryMenusBuilder builder() {
        return new InventoryMenusBuilder();
    }

    /**
     * Registers the inventory menus with the given plugin.
     *
     * @param plugin the {@link JavaPlugin} to register with.
     */
    public void register(final @NotNull JavaPlugin plugin) {
        InventoryMenus.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    /**
     * Retrieves the currently registered plugin.
     *
     * @return the current {@link JavaPlugin}, or {@code null} if none is registered.
     */
    @Nullable
    public static JavaPlugin plugin() {
        return plugin;
    }

    /**
     * Retrieves the currently registered plugin, throwing an exception if none is registered.
     *
     * @return the current {@link JavaPlugin}.
     * @throws RuntimeException if no plugin is registered.
     */
    @NotNull
    public static JavaPlugin pluginOrThrow() {
        if (plugin == null) throw new NullPointerException();
        return plugin;
    }
}
