package com.imdamilan.spigotadditions.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MongoConnection {

    protected @Getter MongoClient client;
    protected @Getter MongoDatabase database;

    /**
     * Creates a new MongoConnection with the given connection string and database name.
     * @param connectionString The connection string that is used to connect to the database.
     * @param databaseName The name of the database to connect to.
     */
    public MongoConnection(String connectionString, String databaseName) {
        this.client = MongoClients.create(connectionString);
        this.database = client.getDatabase(databaseName);
        System.setProperty("DEBUG.MONGO", "true");
        System.setProperty("DB.TRACE", "true");
        System.setProperty("DEBUG.GO", "true");
        Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);
    }

    /**
     * Gets the collection with the name specified.
     * @param collectionName The name of the collection to get.
     * @return The collection with the name specified.
     */
    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    /**
     * Gets the collection with the name specified and the class that represents it.
     * @param collectionName The name of the collection to get.
     * @param clazz The class that represents the collection.
     * @return The collection with the name specified.
     */
    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        return database.getCollection(collectionName, clazz);
    }

    /**
     * Creates a new collection with the name specified.
     * @param collectionName The name of the collection to create.
     */
    public void createCollection(String collectionName) {
        database.createCollection(collectionName);
    }

    /**
     * Inserts a new document in the collection with the name specified.
     * @param collectionName The name of the collection to create the document in.
     * @param document The document to insert.
     */
    public void createDocument(String collectionName, Document document) {
        getCollection(collectionName).insertOne(document);
    }

    /**
     * Inserts a new document in the collection with the name specified and the object that's data will be used.
     * @param collectionName The name of the collection to create the document in.
     * @param object The object to use the data from.
     */
    public void createDocument(String collectionName, Object object) {
        getCollection(collectionName).insertOne(Document.parse(object.toString()));
    }

    /**
     * Inserts the selected list of documents in the collection with the name specified.
     * @param collectionName The name of the collection to create the documents in.
     * @param documents The documents to insert.
     */
    public void createDocuments(String collectionName, List<Document> documents) {
        getCollection(collectionName).insertMany(documents);
    }

    /**
     * Drops the collection with the name specified.
     * @param collectionName The name of the collection to drop.
     */
    public void dropCollection(String collectionName) {
        database.getCollection(collectionName).drop();
    }

    /**
     * Drops all the collections wit the names specified.
      * @param collectionNames The names of the collections to drop.
     */
    public void dropCollections(List<String> collectionNames) {
        collectionNames.forEach(this::dropCollection);
    }

    /**
     * Drops all the collections in the database.
     * @param collectionNames The names of the collections to drop.
     */
    public void dropCollections(String... collectionNames) {
        for (String collectionName : collectionNames) {
            dropCollection(collectionName);
        }
    }

    /**
     * Gets all the strings from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the strings from.
     * @param key The key to get the strings from.
     * @return A list of selected strings from all the documents in the collection.
     */
    public List<String> getStrings(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getString(key)).into(new ArrayList<>());
    }

    /**
     * Gets all the integers from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the integers from.
     * @param key The key to get the integers from.
     * @return A list of selected integers from all the documents in the collection.
     */
    public List<Integer> getIntegers(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getInteger(key)).into(new ArrayList<>());
    }

    /**
     * Gets all the booleans from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the booleans from.
     * @param key The key to get the booleans from.
     * @return A list of selected booleans from all the documents in the collection.
     */
    public List<Boolean> getBooleans(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getBoolean(key)).into(new ArrayList<>());
    }

    /**
     * Gets all the longs from the collection and returns them in a list.
     * @param collectionName The name of the collection to get the longs from.
     * @param key The key to get the longs from.
     * @return A list of all the longs in the collection.
     */
    public List<Long> getLongs(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getLong(key)).into(new ArrayList<>());
    }

    /**
     * Gets all the doubles from the collection and returns them in a list.
     * @param collectionName The name of the collection to get the doubles from.
     * @param key The key to get the doubles from.
     * @return A list of all the doubles in the collection.
     */
    public List<Double> getDoubles(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getDouble(key)).into(new ArrayList<>());
    }

    /**
     * Gets all the objects from the collection and returns them in a list.
     * @param collectionName The name of the collection to get the objects from.
     * @param key The key to get the objects from.
     * @return A list of all the objects in the collection with the key specified.
     */
    public List<Object> getObjects(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.get(key)).into(new ArrayList<>());
    }

    /**
     * Gets all the objects with the selected class from the collection and returns them in a list.
     * @param collectionName The name of the collection to get the objects from.
     * @param key The key to get the objects from.
     * @param clazz The class of the objects to get.
     * @return A list of all the objects in the collection with the key specified.
     * @param <T> The type of the objects to get.
     */
    public <T> List<T> getObjects(String collectionName, String key, Class<T> clazz) {
        return getCollection(collectionName).find().map(document -> document.get(key, clazz)).into(new ArrayList<>());
    }

    /**
     * Gets all the documents from the collection and returns them in a list.
     * @param collectionName The name of the collection to get the documents from.
     * @return A list of all the documents in the collection.
     */
    public List<Document> getDocuments(String collectionName) {
        return getCollection(collectionName).find().into(new ArrayList<>());
    }

    /**
     * Gets all the documents from the collection and returns them in a list.
     * @param collectionName The name of the collection to get the documents from.
     * @param clazz The class to map the documents to.
     * @return A list of all the documents in the collection.
     */
    public <T> List<T> getDocuments(String collectionName, Class<T> clazz) {
        return getCollection(collectionName, clazz).find().into(new ArrayList<>());
    }

    /**
     * Gets all string lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the string lists from.
     * @param key The key to get the string lists from.
     * @return A list of all the string lists in the collection with the selected key.
     */
    public List<List<String>> getStringLists(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getList(key, String.class)).into(new ArrayList<>());
    }

    /**
     * Gets all integer lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the integer lists from.
     * @param key The key to get the integer lists from.
     * @return A list of all the integer lists in the collection with the selected key.
     */
    public List<List<Integer>> getIntegerLists(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getList(key, Integer.class)).into(new ArrayList<>());
    }

    /**
     * Gets all boolean lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the boolean lists from.
     * @param key The key to get the boolean lists from.
     * @return A list of all the boolean lists in the collection with the selected key.
     */
    public List<List<Boolean>> getBooleanLists(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getList(key, Boolean.class)).into(new ArrayList<>());
    }

    /**
     * Gets all long lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the long lists from.
     * @param key The key to get the long lists from.
     * @return A list of all the long lists in the collection with the selected key.
     */
    public List<List<Long>> getLongLists(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getList(key, Long.class)).into(new ArrayList<>());
    }

    /**
     * Gets all double lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the double lists from.
     * @param key The key to get the double lists from.
     * @return A list of all the double lists in the collection with the selected key.
     */
    public List<List<Double>> getDoubleLists(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getList(key, Double.class)).into(new ArrayList<>());
    }

    /**
     * Gets all object lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the object lists from.
     * @param key The key to get the object lists from.
     * @return A list of all the object lists in the collection with the selected key.
     */
    public List<List<Object>> getObjectLists(String collectionName, String key) {
        return getCollection(collectionName).find().map(document -> document.getList(key, Object.class)).into(new ArrayList<>());
    }

    /**
     * Gets all object lists from all the documents in the collection with the selected key and returns them in a list.
     * @param collectionName The name of the collection to get the object lists from.
     * @param key The key to get the object lists from.
     * @param clazz The class of the objects in the list.
     * @return A list of all the object lists in the collection with the selected key.
     */
    public <T> List<List<T>> getObjectLists(String collectionName, String key, Class<T> clazz) {
        return getCollection(collectionName).find().map(document -> document.getList(key, clazz)).into(new ArrayList<>());
    }
}
