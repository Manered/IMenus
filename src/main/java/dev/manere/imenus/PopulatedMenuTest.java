package dev.manere.imenus;

import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.item.ItemBuilder;
import dev.manere.imenus.menu.*;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopulatedMenuTest extends PopulatedMenu<Player> {
    public PopulatedMenuTest(final @NotNull Player player) {
        super(player, Menu.pagedMenu(Component.text("populated menu"), MenuSize.rows(6)), new ArrayList<>(Bukkit.getOnlinePlayers()), 10, 34, List.of(
            0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45
        ), target -> ItemBuilder.skull()
            .name(player.displayName())
            .skull(target.getPlayerProfile())
            .asButton(MenuClickEvent::cancel)
        );
    }
}
