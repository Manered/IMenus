package dev.manere.imenus.item.parser;

import org.jetbrains.annotations.NotNull;

public class ItemSerializers {
    public static YAMLItemSerializer YAML = new YAMLItemSerializer();

    @NotNull
    public static YAMLItemSerializer yaml() {
        return YAML;
    }
}
