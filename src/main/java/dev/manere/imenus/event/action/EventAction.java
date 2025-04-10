package dev.manere.imenus.event.action;

import dev.manere.imenus.event.MenuEvent;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface EventAction<E extends MenuEvent> {
    void handle(final @NotNull E event);
}
