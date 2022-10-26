package com.imdamilan.spigotadditions.commands;

import org.bukkit.command.CommandSender;

@Command(name = "test", permission = "spigotadditions.test", usage = "/test", description = "Test command", aliases = {"t"})
public class TestCommand {
    public static boolean execute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("Test command executed!");
        return true;
    }
}
