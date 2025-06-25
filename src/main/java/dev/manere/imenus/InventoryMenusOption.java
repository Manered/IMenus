package dev.manere.imenus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class InventoryMenusOption<V> {
    @NotNull
    private final String key;

    @Nullable
    private final V defaultValue;

    public InventoryMenusOption(final @NotNull String key, final @Nullable V defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @NotNull
    public Optional<V> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @NotNull
    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof InventoryMenusOption<?> other && other.getKey().equals(this.getKey());
    }
}
