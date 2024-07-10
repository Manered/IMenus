package dev.manere.imenus.menu;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for creating instances of {@link Menu}.
 */
public class MenuBuilder {
    private Component title = Component.empty();
    private MenuSize size = MenuSize.rows(3);
    private MenuType type;

    /**
     * Constructs a MenuBuilder with the specified menu type.
     *
     * @param type the type of the menu.
     */
    private MenuBuilder(final @NotNull MenuType type) {
        this.type = type;
    }

    /**
     * Creates a new MenuBuilder for the specified menu type.
     *
     * @param type the type of the menu.
     * @return a new {@link MenuBuilder} instance.
     */
    @NotNull
    static MenuBuilder builder(final @NotNull MenuType type) {
        return new MenuBuilder(type);
    }

    /**
     * Sets the size of the menu.
     *
     * @param size the size of the menu.
     * @return the updated {@link MenuBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public final MenuBuilder size(final @NotNull MenuSize size) {
        this.size = size;
        return this;
    }

    /**
     * Sets the title of the menu.
     *
     * @param title the title of the menu.
     * @return the updated {@link MenuBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public final MenuBuilder title(final @NotNull Component title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the type of the menu.
     *
     * @param type the type of the menu.
     * @return the updated {@link MenuBuilder} instance.
     */
    @NotNull
    @CanIgnoreReturnValue
    public final MenuBuilder type(final @NotNull MenuType type) {
        this.type = type;
        return this;
    }

    /**
     * Builds and returns the menu instance.
     *
     * @param <T> the type of the menu.
     * @return the created menu instance.
     */
    @NotNull
    public <T extends Menu> T build() {
        //noinspection unchecked
        return type == MenuType.NORMAL ? (T) Menu.menu(title, size) : (T) Menu.pagedMenu(title, size);
    }
}
