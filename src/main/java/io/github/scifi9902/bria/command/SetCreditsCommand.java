package io.github.scifi9902.bria.command;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.profiles.Profile;
import io.github.scifi9902.bria.profiles.ProfileHandler;
import io.github.scifi9902.bria.utils.CC;
import io.github.scifi9902.bria.utils.command.annotation.Command;
import io.github.scifi9902.bria.utils.command.annotation.Parameter;
import io.github.scifi9902.bria.utils.command.utils.UUIDUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetCreditsCommand {


    private final ProfileHandler profileHandler;

    public SetCreditsCommand(BriaPlugin instance) {
        this.profileHandler = instance.getHandlerManager().getHandler(ProfileHandler.class);
    }

    @Command(label = "setcredits", aliases = {"setbalance"}, permission = "bria.command.setcredits")
    public void execute(CommandSender sender, @Parameter("player")String playerName, @Parameter("amount") Integer amount) {

        Player target = UUIDUtils.getPlayer(playerName);

        if (amount == null || amount < 0) {
            sender.sendMessage(CC.chat("&cThe amount must be a valid positive integer."));
            return;
        }

        Optional<Profile> optional;

        if (target == null) {
            OfflinePlayer offlinePlayer = UUIDUtils.getOfflinePlayer(playerName);

            if (offlinePlayer == null || offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage(CC.chat("&cPlayer " + playerName + " has never joined the server."));
                return;
            }

            optional = profileHandler.getProfile(offlinePlayer.getUniqueId());
        }

        else {
            optional = profileHandler.getProfile(target.getUniqueId());
        }

        if (optional.isPresent()) {
            optional.get().setCredits(amount);
            sender.sendMessage(CC.chat("&eYou have set &f" + playerName + "'s &ebalance to &f" + amount));
        }

        else {
            sender.sendMessage(CC.chat("&cFailed to load the profile of " + playerName + "."));
        }
    }


}
