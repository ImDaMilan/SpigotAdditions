package com.imdamilan.spigotadditions;

import com.imdamilan.spigotadditions.events.Listeners;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SpigotAdditions extends JavaPlugin {

    private static @Getter SpigotAdditions instance;
    private static @Getter BukkitAudiences adventure;

    @Override
    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(this);

        new BukkitRunnable() {
            public @Override void run() {
                new Listeners().register();
            }
        }.runTaskLater(this, 1);
    }

    @Override
    public void onDisable() {
        adventure.close();
    }
}
