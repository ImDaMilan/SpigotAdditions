package com.imdamilan.spigotadditions.commands;

import com.imdamilan.spigotadditions.commands.parameter.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class CommandBuilder {

    private final String label;
    private String permission;
    private String description;
    private String usage;
    private String[] aliases;
    private boolean playerOnly;
    private boolean consoleOnly;
    private BiConsumer<CommandSender, List<CommandArgument>> executor;
    private String consoleOnlyMessage;
    private String playerOnlyMessage;
    private String noPermissionMessage;

    public CommandBuilder(String label) {
        this.label = label;
    }

    public CommandBuilder setAliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public CommandBuilder setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
        return this;
    }

    public CommandBuilder setConsoleOnly(boolean consoleOnly) {
        this.consoleOnly = consoleOnly;
        return this;
    }

    public CommandBuilder setExecutor(BiConsumer<CommandSender, List<CommandArgument>> executor) {
        this.executor = executor;
        return this;
    }

    public CommandBuilder setConsoleOnlyMessage(String consoleOnlyMessage) {
        this.consoleOnlyMessage = consoleOnlyMessage;
        return this;
    }

    public CommandBuilder setPlayerOnlyMessage(String playerOnlyMessage) {
        this.playerOnlyMessage = playerOnlyMessage;
        return this;
    }

    public CommandBuilder setPermissionMessage(String noPermissionMessage) {
        this.noPermissionMessage = noPermissionMessage;
        return this;
    }

    public CommandBuilder executes(BiConsumer<CommandSender, List<CommandArgument>> executor) {
        this.executor = executor;
        return this;
    }

    public BukkitCommand build() {
        if (executor == null) {
            throw new IllegalStateException("Executor cannot be null");
        }
        Optional<String> description = Optional.ofNullable(this.description);
        Optional<String> usage = Optional.ofNullable(this.usage);
        Optional<String[]> aliases = Optional.ofNullable(this.aliases);
        return new BukkitCommand(label, description.orElse(""), usage.orElse("/" + label), Arrays.asList(aliases.orElse(new String[0]))) {
            public @Override boolean execute(CommandSender sender, String commandLabel, String[] args) {
                if (playerOnly && !(sender instanceof Player)) {
                    Optional<String> opom = Optional.ofNullable(consoleOnlyMessage);
                    sender.sendMessage(opom.orElse("This command can only be executed by a player."));
                    return true;
                }
                if (consoleOnly && sender instanceof Player) {
                    Optional<String> ocom = Optional.ofNullable(playerOnlyMessage);
                    sender.sendMessage(ocom.orElse("This command can only be executed by the console."));
                    return true;
                }
                if (permission != null && !sender.hasPermission(permission)) {
                    Optional<String> onpm = Optional.ofNullable(noPermissionMessage);
                    sender.sendMessage(onpm.orElse("You do not have permission to execute this command."));
                    return true;
                }
                executor.accept(sender, CommandArgument.adapt(args));
                return true;
            }
        };
    }

    public BukkitCommand buildAndRegister() {
        BukkitCommand command = build();
        registerCommand(command);
        syncCommands();
        return command;
    }

    private static void syncCommands() {
        try {
            Class<?> craftServer = Bukkit.getServer().getClass();
            Method syncCommandsMethod = craftServer.getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerCommand(BukkitCommand command) {
        Field commandMapField;
        CommandMap commandMap = null;
        try {
            commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (commandMap != null) {
            commandMap.register(command.getLabel(), command);
        }
    }
}
