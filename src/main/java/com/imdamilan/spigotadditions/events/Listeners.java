package com.imdamilan.spigotadditions.events;

import com.google.common.reflect.ClassPath;
import com.imdamilan.spigotadditions.SpigotAdditions;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Listeners implements org.bukkit.event.Listener {

    public void register() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (!plugin.getName().equals("SpigotAdditions")) {
                if (!plugin.getDescription().getDepend().contains("SpigotAdditions") && !plugin.getDescription().getSoftDepend().contains("SpigotAdditions")) continue;
            }
            try {
                for (ClassPath.ClassInfo classInfo : ClassPath.from(plugin.getClass().getClassLoader()).getTopLevelClassesRecursive(plugin.getClass().getPackage().getName())) {
                    try {
                        classInfo.load().getDeclaredMethods();
                    } catch (NoClassDefFoundError e) {
                        continue;
                    }
                    for (Method method : classInfo.load().getDeclaredMethods()) {
                        if (method.isAnnotationPresent(Listener.class)) {
                            Listener annotation = method.getAnnotation(Listener.class);
                            RegisteredListener rl = new RegisteredListener(this, (listener, event) -> {
                                try {
                                    method.invoke(null, event);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }, annotation.priority(), SpigotAdditions.getInstance(), annotation.ignoreCancelled());
                            try {
                                HandlerList handlers = (HandlerList) method.getParameters()[0].getType().getDeclaredMethod("getHandlerList").invoke(null);
                                handlers.register(rl);
                            } catch (Exception e) {
                                SpigotAdditions.getInstance().getLogger().warning("The event " + method.getParameters()[0].getType().getSimpleName() + " does not have a getHandlerList() method!");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
