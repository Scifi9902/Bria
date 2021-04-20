package io.github.scifi9902.bria.command;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.profiles.Profile;
import io.github.scifi9902.bria.profiles.ProfileHandler;
import io.github.scifi9902.bria.utils.CC;
import io.github.scifi9902.bria.utils.command.annotation.Command;
import io.github.scifi9902.bria.utils.command.annotation.Parameter;
import io.github.scifi9902.bria.utils.command.utils.UUIDUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PayCommand {

    private final ProfileHandler profileHandler;

    public PayCommand(BriaPlugin instance) {
        this.profileHandler = instance.getHandlerManager().getHandler(ProfileHandler.class);
    }

    @Command(label = "pay")
    public void execute(Player player, @Parameter("player") String playerName, @Parameter("amount") Integer amount) {
        Player target = UUIDUtils.getPlayer(playerName);

        if (amount == null || amount < 1) {
            player.sendMessage(CC.chat("&cThe minimum credits you can send to a player is 1."));
            return;
        }

        Optional<Profile> optional = profileHandler.getProfile(player.getUniqueId());

        if (!optional.isPresent()) {
            player.sendMessage(CC.chat("&cFailed to fetch your profile."));
            return;
        }

        Profile profile = optional.get();

        if (profile.getCredits() < amount) {
            player.sendMessage(CC.chat("&cYou do not have enough credits."));
            return;
        }

        if (target != null) {
            optional = profileHandler.getProfile(target.getUniqueId());
        } else {
           OfflinePlayer offlinePlayer = UUIDUtils.getOfflinePlayer(playerName);

            if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
                player.sendMessage(CC.chat("&cPlayer " + playerName + " has never joined the server before."));
                return;
            }

            optional = profileHandler.getProfile(offlinePlayer.getUniqueId());
        }

        if (!optional.isPresent()) {
            player.sendMessage(CC.chat("&cFailed to load " + playerName + "'s profile."));
            return;
        }

        Profile targetProfile = optional.get();

        targetProfile.addCredits(amount);
        profile.subtractCredits(amount);

        player.sendMessage(CC.chat("&cYou have paid &f" + amount + " &ecredits to &f" + playerName + "&e."));
    }
}
