package io.github.scifi9902.bria.profiles;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.database.MongoHandler;
import io.github.scifi9902.bria.handlers.IHandler;
import io.github.scifi9902.bria.profiles.repository.ProfileRepository;
import lombok.Getter;

import java.util.*;

@Getter
public class ProfileHandler implements IHandler {

    private final BriaPlugin instance;

    private final Map<UUID,Profile> profiles = new HashMap<>();

    private final ProfileRepository profileRepository;

    /**
     * Constructs a new ProfileHandler instance
     *
     * @param instance instance of the JavaPlugin
     */
    public ProfileHandler(BriaPlugin instance) {
        this.instance = instance;
        this.profileRepository = new ProfileRepository(instance);
    }

    /**
     * @param uuid uuid of the player
     * @return optional containing the profile
     */
    public Optional<Profile> getProfile(UUID uuid) {
        if (this.profiles.containsKey(uuid)) {
            return Optional.of(this.profiles.get(uuid));
        }
        return Optional.empty();
    }


    @Override
    public void unload() {
        //Loop through all profiles
        for (Profile profile : this.profiles.values()) {
            //Save the profile to the database
            this.profileRepository.saveData(profile.getUniqueId().toString(), profile);
        }
    }
}
