package com.imdamilan.spigotadditions.commands.function;

import com.imdamilan.spigotadditions.commands.parameter.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.List;

@FunctionalInterface
public interface TabCompleteExecutor {
    List<String> execute(CommandSender sender, List<CommandArgument> args);
}
