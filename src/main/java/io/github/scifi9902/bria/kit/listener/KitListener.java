package io.github.scifi9902.bria.kit.listener;

import io.github.scifi9902.bria.BriaPlugin;
import io.github.scifi9902.bria.kit.Kit;
import io.github.scifi9902.bria.kit.KitHandler;
import io.github.scifi9902.bria.utils.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class KitListener implements Listener {

    private final KitHandler kitHandler;

    public KitListener(BriaPlugin instance) {
        this.kitHandler = instance.getHandlerManager().getHandler(KitHandler.class);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setFoodLevel(20);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();

        if (!(block.getState() instanceof Sign)) {
            return;
        }

        Sign sign = (Sign) block.getState();

        String[] lines = sign.getLines();

        if (!lines[0].equalsIgnoreCase(CC.chat("&9[Kit]")) || lines.length < 2) {
            return;
        }

        Optional<Kit> optional = kitHandler.getKit(lines[1]);

        if (!optional.isPresent()) {
            return;
        }

        Kit kit = optional.get();

        kit.applyKit(player);
        kitHandler.getEquiptMap().put(player.getUniqueId(), kit);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("&9[Kit]") && event.getPlayer().hasPermission("bria.sign.color")) {
            event.setLine(0, CC.chat("&9[Kit]"));
        }
    }

    @EventHandler
    public void onSoup(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack stack = player.getItemInHand();

        if (!event.getAction().name().contains("RIGHT") || stack == null || !stack.getType().equals(Material.MUSHROOM_SOUP) || player.getMaxHealth() == player.getHealth()) {
            return;
        }

        player.getItemInHand().setType(Material.BOWL);
        player.setHealth(Math.min(20.0F, player.getHealth() + 6.5F));
    }

}
