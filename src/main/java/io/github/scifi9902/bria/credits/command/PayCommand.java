package io.github.scifi9902.bria.credits.command;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.credits.CreditHandler;
import io.github.scifi9902.bria.utils.CC;
import io.github.scifi9902.bria.utils.command.annotation.Command;
import io.github.scifi9902.bria.utils.command.annotation.Parameter;
import io.github.scifi9902.bria.utils.command.utils.UUIDUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PayCommand {

    private final BriaPlugin instance;

    private final CreditHandler creditHandler;

    public PayCommand(BriaPlugin instance) {
        this.instance = instance;
        this.creditHandler = instance.getHandlerManager().getHandler(CreditHandler.class);
    }

    @Command(label = "pay")
    public void execute(Player player, @Parameter("player") String playerName, @Parameter("amount") Integer amount) {
        Player target = UUIDUtils.getPlayer(playerName);

        if (amount == null || amount < 1) {
            player.sendMessage(CC.chat("&cThe minimum credits you can send to a player is 1."));
            return;
        }

        if (this.creditHandler.getCreditBalance(player.getUniqueId()) < amount) {
            player.sendMessage(CC.chat("&cYou do not have enough credits."));
            return;
        }

        if (target != null) {
            this.creditHandler.subtractCredits(player.getUniqueId(), amount);
            this.creditHandler.addCredits(target.getUniqueId(), amount);
            player.sendMessage(CC.chat("&eYou have paid &f" + target.getName() + " " + amount + " &ecredits."));
            target.sendMessage(CC.chat("&eYou have received &f" + amount + " &ecredits from &f" + player.getName() + "&e."));
        } else {
            OfflinePlayer offlinePlayer = UUIDUtils.getOfflinePlayer(playerName);

            if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
                player.sendMessage(CC.chat("&cPlayer " + playerName + " has never joined the server before."));
                return;
            }

            this.creditHandler.subtractCredits(player.getUniqueId(), amount);
            this.creditHandler.addCredits(offlinePlayer.getUniqueId(), amount);

            player.sendMessage(CC.chat("&eYou have paid &f" + offlinePlayer.getName() + " " + amount + " &ecredits."));
        }


    }
}
