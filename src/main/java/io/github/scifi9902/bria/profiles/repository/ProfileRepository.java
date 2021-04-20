package io.github.scifi9902.bria.profiles.repository;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.database.MongoHandler;
import io.github.scifi9902.bria.database.repository.MongoRepository;

public class ProfileRepository extends MongoRepository {

    public ProfileRepository(BriaPlugin instance) {
        super(instance);
        this.setCollection(instance.getHandlerManager().getHandler(MongoHandler.class).getDatabase().getCollection("profiles"));
    }
}
