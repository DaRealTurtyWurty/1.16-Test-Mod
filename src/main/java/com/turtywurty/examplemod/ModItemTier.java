package com.turtywurty.examplemod;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public interface ModItemTier extends IItemTier {

	IItemTier TEST = createTier(10, 20, 30, 40, 2, () -> Ingredient.fromItems(Items.ACACIA_LOG));

	static IItemTier createTier(float attackDamage, float efficiency, int enchantability, int maxUses, int harvestLevel,
			Supplier<Ingredient> repairMaterial) {
		return new ModItemTier() {
			@Override
			public float getAttackDamage() {
				return attackDamage;
			}

			@Override
			public float getEfficiency() {
				return efficiency;
			}

			@Override
			public int getEnchantability() {
				return enchantability;
			}

			@Override
			public Ingredient getRepairMaterial() {
				return repairMaterial.get();
			}

			@Override
			public int getMaxUses() {
				return maxUses;
			}

			@Override
			public int getHarvestLevel() {
				return harvestLevel;
			}
		};
	}
}
