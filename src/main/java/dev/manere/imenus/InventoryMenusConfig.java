package dev.manere.imenus;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class InventoryMenusConfig {
    private final Map<InventoryMenusOption<?>, Object> options = new ConcurrentHashMap<>();

    @NotNull
    @CanIgnoreReturnValue
    public <V> InventoryMenusConfig set(final @NotNull InventoryMenusOption<V> option, final @Nullable V value) {
        this.options.put(option, value);
        return this;
    }

    @NotNull
    @CanIgnoreReturnValue
    public <V> InventoryMenusConfig edit(final @NotNull InventoryMenusOption<V> option, final @NotNull Consumer<@Nullable V> value) {
        final V appliedValue = get(option).orElse(null);
        value.accept(appliedValue);

        this.options.put(option, appliedValue);
        return this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <V> Optional<V> get(final @NotNull InventoryMenusOption<V> option) {
        final Optional<V> defaultValue = option.getDefaultValue();

        try {
            final Optional<V> customValue = Optional.ofNullable((V) options.get(option));
            return customValue.isPresent() ? customValue : defaultValue;
        } catch (final ClassCastException ignored) {
            return defaultValue;
        }
    }

    @NotNull
    @Unmodifiable
    public Map<InventoryMenusOption<?>, Object> getOptions() {
        return Map.copyOf(options);
    }
}