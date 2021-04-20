package io.github.scifi9902.bria.profiles;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Profile {

    @SerializedName("_id")
    private UUID uniqueId;

    private int kills;

    private int deaths;

    private int credits;

    /**
     * Constructs a new Profile instance
     *
     * @param uniqueId uuid of the player
     */
    public Profile(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
