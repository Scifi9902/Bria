package io.github.scifi9902.bria.credits.command;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.credits.CreditHandler;
import io.github.scifi9902.bria.utils.CC;
import io.github.scifi9902.bria.utils.command.annotation.Command;
import io.github.scifi9902.bria.utils.command.annotation.Parameter;
import io.github.scifi9902.bria.utils.command.utils.UUIDUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCreditsCommand {

    private final BriaPlugin instance;

    public SetCreditsCommand(BriaPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "setcredits", aliases = {"setbalance"}, permission = "bria.command.setcredits")
    public void execute(CommandSender sender, @Parameter("player")String playerName, @Parameter("amount") Integer amount) {

        Player target = UUIDUtils.getPlayer(playerName);

        if (amount == null || amount < 0) {
            sender.sendMessage(CC.chat("&cThe amount must be a valid positive integer."));
            return;
        }

        if (target != null) {
            instance.getHandlerManager().getHandler(CreditHandler.class).setCreditBalance(target.getUniqueId(), amount);
            sender.sendMessage(CC.chat("&eYou have set &f" + target.getName() + "'s &ebalance to &f" + amount));

        } else {

            OfflinePlayer offlinePlayer = UUIDUtils.getOfflinePlayer(playerName);
            if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage(CC.chat("&cPlayer " + playerName + " has never joined the server."));
                return;
            }


            instance.getHandlerManager().getHandler(CreditHandler.class).setCreditBalance(offlinePlayer.getUniqueId(), amount);
            sender.sendMessage(CC.chat("&eYou have set &f" + offlinePlayer.getName() + "'s &ebalance to &f" + amount));
        }
    }


}
