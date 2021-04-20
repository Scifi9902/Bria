package io.github.scifi9902.bria.credits;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.handlers.IHandler;
import io.github.scifi9902.bria.profiles.Profile;
import io.github.scifi9902.bria.profiles.ProfileHandler;

import java.util.Optional;
import java.util.UUID;

public class CreditHandler implements IHandler {

    private final BriaPlugin instance;

    /**
     * @param instance instance of the JavaPlugin
     */
    public CreditHandler(BriaPlugin instance) {
        this.instance = instance;
    }

    /**
     * @param uuid uuid of the player
     * @return the players balance
     */
    public int getCreditBalance(UUID uuid) {
        Optional<Profile> optional = instance.getHandlerManager().getHandler(ProfileHandler.class).getProfile(uuid);
        return optional.map(Profile::getCredits).orElse(0);
    }

    /**
     * @param uuid   uuid of the player
     * @param amount amount to set the players balance to
     */
    public void setCreditBalance(UUID uuid, int amount) {
        Optional<Profile> optional = instance.getHandlerManager().getHandler(ProfileHandler.class).getProfile(uuid);

        optional.ifPresent(profile -> profile.setCredits(amount));
    }

    /**
     *
     * @param uuid uuid of the player
     * @param amount amount to add to the players balance
     */
    public void addCredits(UUID uuid, int amount) {
        Optional<Profile> optional = instance.getHandlerManager().getHandler(ProfileHandler.class).getProfile(uuid);

        if (optional.isPresent()) {
            Profile profile = optional.get();
            profile.setCredits(profile.getCredits() + amount);
        }
    }

    /**
     *
     * @param uuid uuid of the player
     * @param amount amount to subtract from the players balance
     */
    public void subtractCredits(UUID uuid, int amount) {
        Optional<Profile> optional = instance.getHandlerManager().getHandler(ProfileHandler.class).getProfile(uuid);

        if (optional.isPresent()) {
            Profile profile = optional.get();
            profile.setCredits(profile.getCredits() - amount);
        }    }


}
