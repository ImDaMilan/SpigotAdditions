package com.imdamilan.spigotadditions.events.custom.listeners;

import com.imdamilan.spigotadditions.events.Listener;
import com.imdamilan.spigotadditions.events.custom.PlayerKillEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerKillEntityListener {

    public static @Listener void onPlayerKillEntity(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        if (player != null) {
            Bukkit.getPluginManager().callEvent(new PlayerKillEntityEvent(player, entity, event.getDroppedExp(), event.getDrops()));
        }
    }
}
