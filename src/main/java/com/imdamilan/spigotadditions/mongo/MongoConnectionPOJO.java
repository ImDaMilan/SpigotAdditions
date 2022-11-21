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

    public MongoConnectionPOJO(String connectionString, String databaseName, String collectionName, Class<T> clazz) {
        super(connectionString, databaseName);
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));
        database = client.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
        this.collection = database.getCollection(collectionName, clazz);
    }

    public void insertOne(T object) {
        this.collection.insertOne(object);
    }

    public void insertMany(List<T> objects) {
        this.collection.insertMany(objects);
    }

    public void into(List<T> objects) {
        this.collection.find().into(objects);
    }

    public T getOne() {
        return this.collection.find().first();
    }

    public List<T> getAll() {
        List<T> objects = new ArrayList<>();
        this.collection.find().into(objects);
        return objects;
    }

    public List<T> getAll(int limit) {
        List<T> objects = new ArrayList<>();
        this.collection.find().limit(limit).into(objects);
        return objects;
    }

    public List<T> getAll(int limit, int skip) {
        List<T> objects = new ArrayList<>();
        this.collection.find().limit(limit).skip(skip).into(objects);
        return objects;
    }
}
