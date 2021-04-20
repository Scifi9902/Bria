package io.github.scifi9902.bria.profiles;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.database.MongoHandler;
import io.github.scifi9902.bria.handlers.IHandler;
import io.github.scifi9902.bria.profiles.repository.ProfileRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class ProfileHandler implements IHandler {

    private final BriaPlugin instance;

    private final List<Profile> profiles = new ArrayList<>();

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
        for (Profile profile : this.profiles) {
            if (profile.getUniqueId().equals(uuid)) {
                return Optional.of(profile);
            }
        }
        return Optional.empty();
    }


    @Override
    public void unload() {
        for (Profile profile : this.profiles) {
            this.profileRepository.saveData(profile.getUniqueId().toString(), profile);
        }
    }
}
