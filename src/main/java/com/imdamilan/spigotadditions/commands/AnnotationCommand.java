package com.imdamilan.spigotadditions.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AnnotationCommand extends BukkitCommand {

    private final Command annotation;
    private final Class<?> clazz;

    protected AnnotationCommand(Command annotation, Class<?> clazz) {
        super(annotation.name(), annotation.description(), annotation.usage(), Arrays.asList(annotation.aliases()));
        setPermission(annotation.permission());
        setPermissionMessage(annotation.permissionMessage());
        setLabel(annotation.name());
        this.annotation = annotation;
        this.clazz = clazz;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (annotation.playerOnly() && !(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        if (annotation.consoleOnly() && sender instanceof Player) {
            sender.sendMessage("You must be the console to use this command.");
            return true;
        }
        if (!sender.hasPermission(annotation.permission())) {
            sender.sendMessage(annotation.permissionMessage());
            return true;
        }
        if (args.length < annotation.minArgs()) {
            sender.sendMessage(annotation.usage());
            return true;
        }
        try {
            return (boolean) clazz.getDeclaredMethod("execute", CommandSender.class, String.class, String[].class).invoke(null, sender, commandLabel, args);
        } catch (ClassCastException e) {
            throw new RuntimeException("The execute method in " + clazz.getName() + " must return a boolean.");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Command class " + clazz.getName() + " does not have a static boolean method named execute(CommandSender, String, String[]), which is required for commands to work.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
