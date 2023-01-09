package com.imdamilan.spigotadditions.sql;

import com.imdamilan.spigotadditions.SpigotAdditions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;

public class SQLConnection {

    private @Getter Connection connection;

    /**
     * Creates a new SQLConnection with the given host, port, database, username and password.
     * @param host The host of the SQLConnection.
     * @param port The port of the SQLConnection.
     * @param database The database of the SQLConnection.
     * @param username The username of the SQLConnection.
     * @param password The password of the SQLConnection.
     */
    public SQLConnection(String host, String port, String database, String username, String password) {
        String URL = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Whether the connection is closed or not.
     */
    public boolean isConnected() {
        return connection != null;
    }

    /**
     * Closes the connection.
     */
    public void close() {
        try {
            if (isConnected()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs an async update query ran by the given plugin.
     * @param plugin The plugin that runs the async update query (used for the BukkitRunnable, usually the instance of this plugin).
     * @param query The query to run.
     */
    public void execute(Plugin plugin, String query) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Runs an update query.
     * @param query The query to run.
     */
    public void execute(String query) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotAdditions.getInstance(), () -> {
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Runs a function for each result of the query ran by the given plugin.
     * @param plugin The plugin that runs the query (used for the BukkitRunnable, usually the instance of this plugin).
     * @param query The query to run.
     * @param lambda The function to run for each result.
     * @param async Whether to run the function asynchronously or not.
     */
    public void forEach(Plugin plugin, String query, Consumer<ResultSet> lambda, boolean async) {
        if (async) Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> forEachLambda(query, lambda));
        else forEachLambda(query, lambda);
    }

    /**
     * Runs a function for each result of the query ran by the given plugin.
     * @param plugin The plugin that runs the async query (used for the BukkitRunnable, usually the instance of this plugin).
     * @param query The query to run.
     * @param lambda The function to run for each result.
     */
    public void forEach(Plugin plugin, String query, Consumer<ResultSet> lambda) {
        forEach(plugin, query, lambda, true);
    }

    /**
     * Runs a function for each result of the query or not.
     * @param query The query to run.
     * @param lambda The function to run for each result.
     * @param async Whether to run the query asynchronously or not.
     */
    public void forEach(String query, Consumer<ResultSet> lambda, boolean async) {
        forEach(SpigotAdditions.getInstance(), query, lambda, async);
    }

    /**
     * Runs a function for each result of the query.
     * @param query The query to run.
     * @param lambda The function to run for each result.
     */
    public void forEach(String query, Consumer<ResultSet> lambda) {
        forEach(SpigotAdditions.getInstance(), query, lambda, true);
    }

    /**
     * Runs the query and returns the result, THIS IS NOT ASYNC, PLEASE BE SURE TO RUN THIS ASYNC OR IT MIGHT FREEZE THE SERVER.
     * @param query The query to run.
     * @return The result of the query.
     */
    public ResultSet getResults(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void forEachLambda(String query, Consumer<ResultSet> lambda) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lambda.accept(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
