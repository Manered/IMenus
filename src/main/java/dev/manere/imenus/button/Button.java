package dev.manere.imenus.button;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.menu.Menu;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a clickable button in a menu.
 */
public interface Button {
    /**
     * Creates a new instance of a menu button.
     *
     * @return a new MenuButton instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    static Button button() {
        return new MenuButton();
    }

    /**
     * Creates a new instance of a menu button with a specific item.
     *
     * @param item the ItemStack representing the button.
     * @return a new MenuButton instance initialized with the item.
     */
    @NotNull
    @CanIgnoreReturnValue
    static Button button(final @NotNull ItemStack item) {
        return button()
            .item(item);
    }

    /**
     * Creates a new instance of a menu button with a specific item and click handler.
     *
     * @param item the ItemStack representing the button.
     * @param handler the click handler for the button.
     * @return a new MenuButton instance initialized with the item and click handler.
     */
    @NotNull
    @CanIgnoreReturnValue
    static Button button(final @NotNull ItemStack item, final @NotNull Consumer<MenuClickEvent<? extends Menu>> handler) {
        return button()
            .item(item)
            .handleClick(handler);
    }

    /**
     * Creates a new instance of a menu button with a click handler.
     *
     * @param handler the click handler for the button.
     * @return a new MenuButton instance initialized with the click handler.
     */
    @NotNull
    @CanIgnoreReturnValue
    static Button button(final @NotNull Consumer<MenuClickEvent<? extends Menu>> handler) {
        return button()
            .handleClick(handler);
    }

    /**
     * Creates a new instance of a menu button with a click handler and a specific item.
     *
     * @param handler the click handler for the button.
     * @param item the ItemStack representing the button.
     * @return a new MenuButton instance initialized with the click handler and item.
     */
    @NotNull
    @CanIgnoreReturnValue
    static Button button(final @NotNull Consumer<MenuClickEvent<? extends Menu>> handler, final @NotNull ItemStack item) {
        return button()
            .item(item)
            .handleClick(handler);
    }

    /**
     * Retrieves the ItemStack representing this button.
     *
     * @return the ItemStack representing the button.
     */
    @NotNull
    ItemStack item();

    /**
     * Retrieves the click handler for this button.
     *
     * @return the click handler.
     */
    @NotNull
    default Consumer<MenuClickEvent<? extends Menu>> handleClick() {
        return MenuClickEvent::cancel;
    }

    /**
     * Sets the ItemStack representing this button.
     *
     * @param item the ItemStack representing the button.
     * @return this Button instance for chaining.
     */
    @NotNull
    @CanIgnoreReturnValue
    Button item(final @NotNull ItemStack item);

    /**
     * Sets the click handler for this button.
     *
     * @param handler the click handler for the button.
     * @return this Button instance for chaining.
     */
    @NotNull
    @CanIgnoreReturnValue
    Button handleClick(final @NotNull Consumer<MenuClickEvent<? extends Menu>> handler);
}
