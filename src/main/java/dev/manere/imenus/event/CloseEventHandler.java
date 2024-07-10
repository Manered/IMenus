package dev.manere.imenus.event;

import dev.manere.imenus.menu.Menu;
import org.jetbrains.annotations.NotNull;

/**
 * Functional interface for handling menu close events.
 *
 * @param <M> the type of menu associated with the handler.
 */
@FunctionalInterface
public interface CloseEventHandler<M extends Menu> {
    /**
     * Handles the menu close event.
     *
     * @param event the close event containing details about the menu.
     */
    void handleClose(final @NotNull MenuCloseEvent<M> event);
}
