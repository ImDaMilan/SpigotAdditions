package com.imdamilan.spigotadditions.updater;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Updater {

    private final Plugin plugin;
    private String version;
    private final int id;

    /**
     * Creates a new Updater for the given plugin and resource id.
     * @param plugin The plugin to check for updates.
     * @param resourceId The resource id of the plugin on SpigotMC.
     */
    public Updater(Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.id = resourceId;
    }

    /**
     * @return The latest version of the plugin.
     */
    public String getLatest() {
        String url = "https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + id;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * Downloads the latest version of the plugin.
     */
    public void download() {
        try {
            String url = "https://api.spiget.org/v2/resources/ "  + id + " /download";
            URL url1 = new URL(url);
            BufferedInputStream in = new BufferedInputStream(url1.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("plugins" + File.separator + plugin.getName() + ".jar");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
