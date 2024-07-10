package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builder class for creating {@link InventoryMenus} instances.
 */
public class InventoryMenusBuilder {
    private JavaPlugin plugin;

    /**
     * Sets the plugin to be used by the {@link InventoryMenus}.
     *
     * @param plugin the {@link JavaPlugin} to set.
     * @return the current {@link InventoryMenusBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public InventoryMenusBuilder plugin(final @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    /**
     * Retrieves the currently set plugin.
     *
     * @return the current {@link JavaPlugin}, or {@code null} if none is set.
     */
    @Nullable
    public JavaPlugin plugin() {
        return plugin;
    }

    /**
     * Builds and returns an {@link InventoryMenus} instance.
     *
     * @return a new {@link InventoryMenus} instance.
     * @throws RuntimeException if the plugin has not been set.
     */
    @NotNull
    @CanIgnoreReturnValue
    public InventoryMenus build() {
        final InventoryMenus menus = new InventoryMenus();
        if (plugin == null) throw new RuntimeException();

        menus.register(plugin);
        return menus;
    }
}
