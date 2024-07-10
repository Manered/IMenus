package dev.manere.imenus;

import dev.manere.imenus.button.Button;
import dev.manere.imenus.event.MenuClickEvent;
import dev.manere.imenus.item.ItemBuilder;
import dev.manere.imenus.menu.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InitializedMenuTest extends InitializedMenu<NormalMenu> {
    public InitializedMenuTest(final @NotNull Player player) {
        super(player, Menu.menu(Component.text("initialized menu"), MenuSize.rows(6)));
    }

    @Override
    public void populate() {
        border(ItemBuilder.item(Material.BLACK_STAINED_GLASS_PANE)
            .name(Component.empty())
            .build()
        );

        /*
        selection(MenuSelection.emptySlots(), context -> button(context.slot(), ItemBuilder.item(Material.WHITE_STAINED_GLASS_PANE)
            .name(Component.empty())
            .asButton(MenuClickEvent::cancel)
        ));
         */

        button(10, ItemBuilder.item(Material.DIAMOND_SWORD)
            .name(Component.text("Test", NamedTextColor.GOLD))
            .lore(lore -> {
                lore.add(Component.text("abc"));
            })
            .asButton(event -> {
                event.cancel();
                event.player().sendRichMessage("lol");
            })
        );
    }
}
