package io.github.scifi9902.bria.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import io.github.scifi9902.bria.handlers.IHandler;
import lombok.Getter;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class MongoHandler implements IHandler {

    private final MongoClient client;

    private final MongoDatabase database;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructs a new MongoHandler instance
     *
     * @param host     host of the database
     * @param port     port of the database
     * @param auth     if the server requires auth
     * @param username username of the database
     * @param password password of the database
     * @param database name of the database
     */
    public MongoHandler(String host, int port, boolean auth, String username, String password, String database) {
        if (auth) {
            this.client = new MongoClient(new ServerAddress(host, port), Collections.singletonList(MongoCredential.createCredential(username, database, password.toCharArray())));
        } else {
            this.client = new MongoClient(new ServerAddress(host, port));
        }

        this.database = this.client.getDatabase(database);
    }
}
