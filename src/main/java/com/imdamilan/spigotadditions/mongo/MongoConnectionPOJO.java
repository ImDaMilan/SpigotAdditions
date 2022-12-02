package com.imdamilan.spigotadditions.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

public class MongoConnectionPOJO<T> extends MongoConnection {

    private final @Getter MongoCollection<T> collection;

    /**
     * Creates a new MongoConnectionPOJO with the given connection details and POJO class.
     * @param connectionString The connection string of the database.
     * @param databaseName The name of the database.
     * @param collectionName The name of the collection.
     * @param clazz The POJO class.
     */
    public MongoConnectionPOJO(String connectionString, String databaseName, String collectionName, Class<T> clazz) {
        super(connectionString, databaseName);
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));
        database = client.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
        this.collection = database.getCollection(collectionName, clazz);
    }

    /**
     * Creates a new document from the given object and inserts it into the collection.
     * @param object The object to insert.
     */
    public void insertOne(T object) {
        this.collection.insertOne(object);
    }

    /**
     * Creates new documents from the given objects and inserts them into the collection.
     * @param objects The objects to insert.
     */
    public void insertMany(List<T> objects) {
        this.collection.insertMany(objects);
    }

    /**
     * Inserts all the documents from the collection into the list that is passed as a parameter.
     * @param objects The list to insert the documents into.
     */
    public void into(List<T> objects) {
        this.collection.find().into(objects);
    }

    /**
     * @return The first object from the first document from the collection.
     */
    public T getOne() {
        return this.collection.find().first();
    }

    /**
     * @return A list of all the objects from the collection.
     */
    public List<T> getAll() {
        List<T> objects = new ArrayList<>();
        this.collection.find().into(objects);
        return objects;
    }

    /**
     * Gets a list of all the objects from the collection with the limit of the given number.
     * @param limit The limit of the objects to get.
     * @return A list of all the objects from the collection with the limit of the given number.
     */
    public List<T> getAll(int limit) {
        List<T> objects = new ArrayList<>();
        this.collection.find().limit(limit).into(objects);
        return objects;
    }

    /**
     * Gets a list of all the objects from the collection with the limit of the given number and the offset of the given number.
     * @param limit The limit of the objects to get.
     * @param skip The offset of the objects to get.
     * @return A list of all the objects from the collection with the limit of the given number and the offset of the given number.
     */
    public List<T> getAll(int limit, int skip) {
        List<T> objects = new ArrayList<>();
        this.collection.find().limit(limit).skip(skip).into(objects);
        return objects;
    }

    /**
     * Gets the number of documents in the collection.
     * @return The number of documents in the collection.
     */
    public long getCount() {
        return this.collection.countDocuments();
    }
}
