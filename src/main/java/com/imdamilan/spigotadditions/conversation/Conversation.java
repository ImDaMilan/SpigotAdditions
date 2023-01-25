package com.imdamilan.spigotadditions.conversation;

import com.imdamilan.spigotadditions.SpigotAdditions;
import com.imdamilan.spigotadditions.conversation.graph.ConversationVertex;
import lombok.Data;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public @Data class Conversation {

    private int wait = 1;
    private String prefix;
    private String suffix;
    private TextComponent component;
    private ArrayList<ConversationVertex> vertices = new ArrayList<>();

    public Conversation(String prefix, String suffix, TextComponent component) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.component = component;
    }

    public Conversation(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public Conversation(String prefix) {
        this.prefix = prefix;
    }

    public Conversation() {
        this.prefix = "";
        this.suffix = "";
    }

    public Conversation addVertex(ConversationVertex vertex) {
        vertices.add(vertex);
        return this;
    }

    public Conversation addVertex(ConversationVertex vertex, int index) {
        vertices.add(index, vertex);
        return this;
    }

    public Conversation add(ConversationVertex vertex) {
        return addVertex(vertex);
    }

    public Conversation add(ConversationVertex vertex, int index) {
        return addVertex(vertex, index);
    }

    public Conversation removeVertex(ConversationVertex vertex) {
        vertices.remove(vertex);
        return this;
    }

    public Conversation removeVertex(int index) {
        vertices.remove(index);
        return this;
    }

    public Conversation clearVertices() {
        vertices.clear();
        return this;
    }

    public Conversation setWait(int wait) {
        this.wait = wait;
        return this;
    }

    public Conversation withWait(int wait) {
        return setWait(wait);
    }

    public Conversation setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Conversation withPrefix(String prefix) {
        return setPrefix(prefix);
    }

    public Conversation setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public Conversation withSuffix(String suffix) {
        return setSuffix(suffix);
    }

    public void start(Player player, BukkitAudiences adventure) {
        component = Component.text(prefix);
        vertices.forEach(vertex -> component = component.append(vertex.getMessage().getComponent()));
        component = component.append(Component.text(suffix));
        adventure.player(player).sendMessage(component);
    }

    public void start(Player player) {
        start(player, SpigotAdditions.getAdventure());
    }
}
