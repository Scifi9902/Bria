package io.github.scifi9902.bria.scoreboard;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.profiles.Profile;
import io.github.scifi9902.bria.profiles.ProfileHandler;
import io.github.scifi9902.bria.utils.CC;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScoreboardAdapter implements AssembleAdapter {

    private final BriaPlugin instance;

    private final ProfileHandler profileHandler;

    public ScoreboardAdapter(BriaPlugin instance) {
        this.instance = instance;
        this.profileHandler = instance.getHandlerManager().getHandler(ProfileHandler.class);
    }

    @Override
    public String getTitle(Player player) {
        return CC.chat("&6&lExample &8| &fKitPvP");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        Optional<Profile> optional = profileHandler.getProfile(player.getUniqueId());

        if (optional.isPresent()) {
            Profile profile = optional.get();
            lines.add("&4&lKills: &f" + profile.getKills());
            lines.add("&4&lDeaths: &f" + profile.getDeaths());
            lines.add("&4&lCredits: &f" + profile.getCredits());

        }

        lines.add(0, CC.chat("&8&m" + StringUtils.repeat("-", 32)));
        lines.add(lines.size(), CC.chat("&8&m" + StringUtils.repeat("-", 32)));

        return lines;
    }
}
