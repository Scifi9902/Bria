package io.github.scifi9902.bria.kit;

import io.github.scifi9902.bria.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public abstract class Kit {

    public abstract String getName();

    public abstract int getCost();

    public abstract ItemStack getHealItem();

    public abstract ItemStack[] getArmorContent();

    public abstract List<PotionEffect> getPotionEffects();

    public abstract ItemStack getSword();

    public void applyKit(Player player) {
        player.sendMessage(CC.chat("&eKit &a" + this.getName() + " &ehas been applied."));

        player.getInventory().clear();
        player.getInventory().setArmorContents(this.getArmorContent());

        player.getInventory().setItem(0, this.getSword());

        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack stack = player.getInventory().getContents()[i];
            player.getInventory().setItem(i, stack == null ? this.getHealItem() : stack);
        }

        player.getActivePotionEffects().clear();

        for (PotionEffect potionEffect : this.getPotionEffects()) {
            player.addPotionEffect(potionEffect);
        }
    }

}
