package io.github.scifi9902.bria.profiles;

import io.github.scifi9902.bria.BriaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ProfileListener implements Listener {

    private final BriaPlugin instance;

    private final ProfileHandler profileHandler;

    /**
     * Constructs a new ProfileListener instance
     *
     * @param instance instance of the JavaPlugin
     */
    public ProfileListener(BriaPlugin instance) {
        this.instance = instance;
        this.profileHandler = instance.getHandlerManager().getHandler(ProfileHandler.class);
    }

    @EventHandler
    public void onPlayerAsyncLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        if (profileHandler.getProfile(uuid).isPresent()) {
            return;
        }

        //Fetch the profile from the database
        profileHandler.getProfileRepository().getData(uuid.toString(), Profile.class).handleAsync((profile, b) -> {

            //Check if the found profile is null
            if (profile != null) {
                //add it to the profiles collection if its not
                profileHandler.getProfiles().add((Profile) profile);
            }

            else {
                //Define a new Profile
                Profile newProfile = new Profile(uuid);
                //add into the arraylist
                profileHandler.getProfiles().add(newProfile);
            }
            return profile;
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();

        //Get the Optional<Profile> from a memory cache
        Optional<Profile> optional = profileHandler.getProfile(player.getUniqueId());

        //Check if the Profile is present
        if (optional.isPresent()) {
            //Create a variable to store the profile
            Profile profile = optional.get();

            //Remove the profile from the profiles list
            profileHandler.getProfiles().remove(profile);

            //Save it to mongo
            profileHandler.getProfileRepository().saveData(player.getUniqueId().toString(), profile);
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Optional<Profile> optional = profileHandler.getProfile(player.getUniqueId());

        //If the optional does not contain a profile return
        if (!optional.isPresent()) {
            return;
        }

        Profile profile = optional.get();

        profile.setDeaths(profile.getDeaths() + 1);

        Player killer = player.getKiller();

        //Check if the player was killed by another player
        if (killer == null) {
            return;
        }

        optional = profileHandler.getProfile(killer.getUniqueId());

        //Check if the optional contains the killers profile
        if (!optional.isPresent()) {
            return;
        }

        Profile killerProfile = optional.get();

        killerProfile.setKills(killerProfile.getKills() + 1);
    }
}
