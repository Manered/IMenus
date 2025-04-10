package dev.manere.imenus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Slot {
    private final int page;
    private final int index;

    public Slot(final int page, final int index) {
        this.page = page;
        this.index = index;
    }

    @NotNull
    public static Slot slot(final int page, final int index) {
        return new Slot(page, index);
    }

    @NotNull
    public static Slot slot(final int index) {
        return slot(1, index);
    }

    public int getPage() {
        return page;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(final @Nullable Object obj) {
        return obj instanceof Slot other && other.page == this.page && other.index == this.index;
    }

    @NotNull
    @Override
    public String toString() {
        return "Slot[page = " + page + ", index = " + index +"]";
    }
}
