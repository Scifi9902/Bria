package io.github.scifi9902.bria.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.database.MongoHandler;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public abstract class MongoRepository {

    private MongoCollection collection;

    private final BriaPlugin instance;

    private final MongoHandler mongoHandler;

    public MongoRepository(BriaPlugin instance) {
        this.instance = instance;
        this.mongoHandler = this.instance.getHandlerManager().getHandler(MongoHandler.class);
    }

    /**
     * @param key  key of the data stored in the database
     * @param type type of the data
     * @return the data found in the database
     */
    public <T> CompletableFuture<T> getData(String key, Type type) {
        return CompletableFuture.supplyAsync(() -> {
            Document document = (Document) this.collection.find(Filters.eq("_id", key)).first();

            if (document == null) {
                return null;
            }

            return instance.getGson().fromJson(document.toJson(), type);
        });
    }

    /**
     * @param <T> type of data to be returned
     * @param t   type of data to be passed in
     */
    public <T> void saveData(String id, T t) {
        CompletableFuture.supplyAsync(() -> this.collection.replaceOne(Filters.eq("_id", id), Document.parse(instance.getGson().toJson(t)), new UpdateOptions().upsert(true)));
    }
}
