package com.imdamilan.spigotadditions;

import com.imdamilan.spigotadditions.events.Listeners;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SpigotAdditions extends JavaPlugin {

    private static @Getter SpigotAdditions instance;

    @Override
    public void onEnable() {
        instance = this;

        new BukkitRunnable() {
            public @Override void run() {
                new Listeners().register();
            }
        }.runTaskLater(this, 1);
    }
}
