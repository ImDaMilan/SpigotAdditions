package com.imdamilan.spigotadditions.conversation.graph;

import com.imdamilan.spigotadditions.conversation.message.ChatMessage;
import lombok.Data;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;

import java.util.HashMap;

public @Data class ConversationVertex {

    private ChatMessage message;
    private final HashMap<String, ConversationVertex> edges = new HashMap<>();

    public ConversationVertex(ChatMessage message) {
        this.message = message;
    }

    public ConversationVertex addEdge(String name, ConversationVertex vertex) {
        edges.put(name, vertex);
        return this;
    }

    public ConversationVertex getEdge(String name) {
        return edges.get(name);
    }

    public boolean hasEdge(String name) {
        return edges.containsKey(name);
    }

    public ConversationVertex removeEdge(String name) {
        edges.remove(name);
        return this;
    }

    public ConversationVertex clearEdges() {
        edges.clear();
        return this;
    }

    public ConversationVertex setMessage(ChatMessage message) {
        this.message = message;
        return this;
    }

    public ConversationVertex withMessage(ChatMessage message) {
        return setMessage(message);
    }

    public void send(Player player) {
        message.send(player);
    }

    public void send(Player player, BukkitAudiences adventure) {
        message.send(player, adventure);
    }
}
