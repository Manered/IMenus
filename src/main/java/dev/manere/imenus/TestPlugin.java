package dev.manere.imenus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        InventoryMenus.builder()
            .plugin(this)
            .build();

        Bukkit.getCommandMap().register("test", "minecraft", new BukkitCommand("test", "idk", "usage: /test", List.of()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                return onCommand(sender, this, commandLabel, args);
            }
        });

        Bukkit.getCommandMap().register("players", "minecraft", new BukkitCommand("players", "idk aswell", "usage: /players", List.of()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                return onCommand(sender, this, commandLabel, args);
            }
        });
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final Player player = (Player) sender;
        if (label.equals("test")) {
            new InitializedMenuTest(player).open();
        } else if (label.equals("players")) {
            new PopulatedMenuTest(player).open();
        }

        return true;
    }
}
