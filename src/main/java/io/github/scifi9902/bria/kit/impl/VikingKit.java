package io.github.scifi9902.bria.kit.impl;

import io.github.scifi9902.bria.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class VikingKit extends Kit {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public ItemStack getHealItem() {
        return null;
    }

    @Override
    public ItemStack[] getArmorContent() {
        return new ItemStack[0];
    }

    @Override
    public List<PotionEffect> getPotionEffects() {
        return null;
    }

    @Override
    public ItemStack getSword() {
        return new ItemStack(Material.IRON_AXE);
    }
}
