package com.imdamilan.spigotadditions.commands.parameter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Stream;

public record CommandArgument(String value) {

    public static List<CommandArgument> adapt(String[] args) {
        return Stream.of(args).map(CommandArgument::new).toList();
    }

    public boolean isInt() {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble() {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat() {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isLong() {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isShort() {
        try {
            Short.parseShort(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isByte() {
        try {
            Byte.parseByte(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isBoolean() {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    public <T extends Enum<T>> boolean isEnum(Class<T> enumClass) {
        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isPlayer() {
        return Bukkit.getPlayer(value) != null;
    }

    public boolean isItem() {
        return Material.matchMaterial(value) != null;
    }

    public int toInt() {
        if (!isInt()) {
            throw new CommandArgumentTypeException("Argument is not an integer");
        }
        return Integer.parseInt(value);
    }

    public double toDouble() {
        if (!isDouble()) {
            throw new CommandArgumentTypeException("Argument is not a double");
        }
        return Double.parseDouble(value);
    }

    public float toFloat() {
        if (!isFloat()) {
            throw new CommandArgumentTypeException("Argument is not a float");
        }
        return Float.parseFloat(value);
    }

    public long toLong() {
        if (!isLong()) {
            throw new CommandArgumentTypeException("Argument is not a long");
        }
        return Long.parseLong(value);
    }

    public short toShort() {
        if (!isShort()) {
            throw new CommandArgumentTypeException("Argument is not a short");
        }
        return Short.parseShort(value);
    }

    public byte toByte() {
        if (!isByte()) {
            throw new CommandArgumentTypeException("Argument is not a byte");
        }
        return Byte.parseByte(value);
    }

    public boolean toBoolean() {
        if (!isBoolean()) {
            throw new CommandArgumentTypeException("Argument is not a boolean");
        }
        return Boolean.parseBoolean(value);
    }

    public <T extends Enum<T>> T toEnum(Class<T> enumClass) {
        if (!isEnum(enumClass)) {
            throw new CommandArgumentTypeException("Argument is not an enum");
        }
        return Enum.valueOf(enumClass, value);
    }

    public Player toPlayer() {
        if (!isPlayer()) {
            throw new CommandArgumentTypeException("Argument is not a player");
        }
        return Bukkit.getPlayer(value);
    }

    public Material toItem() {
        if (!isItem()) {
            throw new CommandArgumentTypeException("Argument is not an item");
        }
        return Material.matchMaterial(value);
    }

    public String toString() {
        return value;
    }
}
