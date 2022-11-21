package com.imdamilan.spigotadditions.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class MongoConnection {

    protected @Getter MongoClient client;
    protected @Getter MongoDatabase database;

    public MongoConnection(String connectionString, String databaseName) {
        this.client = MongoClients.create(connectionString);
        this.database = client.getDatabase(databaseName);
        System.setProperty("DEBUG.MONGO", "true");
        System.setProperty("DB.TRACE", "true");
        System.setProperty("DEBUG.GO", "true");
        Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        return database.getCollection(collectionName, clazz);
    }

    public void createCollection(String collectionName) {
        database.createCollection(collectionName);
    }

    public void createDocument(String collectionName, Document document) {
        getCollection(collectionName).insertOne(document);
    }

    public void createDocument(String collectionName, Object object) {
        getCollection(collectionName).insertOne(Document.parse(object.toString()));
    }

    public void createDocuments(String collectionName, List<Document> documents) {
        getCollection(collectionName).insertMany(documents);
    }

    public void dropCollection(String collectionName) {
        database.getCollection(collectionName).drop();
    }

    public void dropCollections(List<String> collectionNames) {
        collectionNames.forEach(this::dropCollection);
    }

    public void dropCollections(String... collectionNames) {
        for (String collectionName : collectionNames) {
            dropCollection(collectionName);
        }
    }

    public String getString(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getString(key);
    }

    public List<String> getStringList(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, String.class);
    }

    public int getInt(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getInteger(key);
    }

    public List<Integer> getIntList(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, Integer.class);
    }

    public double getDouble(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getDouble(key);
    }

    public List<Double> getDoubleList(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, Double.class);
    }

    public boolean getBoolean(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getBoolean(key);
    }

    public List<Boolean> getBooleanList(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, Boolean.class);
    }

    public long getLong(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getLong(key);
    }

    public List<Long> getLongList(String collectionName, String key) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, Long.class);
    }

    public <T> T get(String collectionName, String key, Class<T> clazz) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).get(key, clazz);
    }

    public <T> List<T> getList(String collectionName, String key, Class<T> clazz) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, clazz);
    }

    public <T> List<T> getList(String collectionName, String key, Class<T> clazz, List<T> defaultValue) {
        return Objects.requireNonNull(getCollection(collectionName).find().first()).getList(key, clazz, defaultValue);
    }
}
