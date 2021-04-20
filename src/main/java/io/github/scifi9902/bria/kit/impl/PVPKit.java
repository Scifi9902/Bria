package io.github.scifi9902.bria.kit.impl;

import io.github.scifi9902.bria.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.List;

public class PVPKit extends Kit {

    @Override
    public String getName() {
        return "PvP";
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public ItemStack getHealItem() {
        return new ItemStack(Material.MUSHROOM_SOUP);
    }

    @Override
    public ItemStack[] getArmorContent() {
        return new ItemStack[]{new ItemStack(Material.LEATHER_BOOTS),
                new ItemStack(Material.CHAINMAIL_LEGGINGS),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                new ItemStack(Material.DIAMOND_HELMET)
        };
    }


    @Override
    public List<PotionEffect> getPotionEffects() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getSword() {
        return new ItemStack(Material.STONE_SWORD);
    }
}
