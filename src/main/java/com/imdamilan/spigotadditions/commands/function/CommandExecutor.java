package com.imdamilan.spigotadditions.commands.function;

import com.imdamilan.spigotadditions.commands.parameter.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.List;

@FunctionalInterface
public interface CommandExecutor {
    boolean execute(CommandSender sender, List<CommandArgument> args);
}
