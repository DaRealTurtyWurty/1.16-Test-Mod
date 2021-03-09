package com.turtywurty.examplemod.core.util;

import java.util.function.Supplier;

import com.turtywurty.examplemod.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ToolMaterials implements IItemTier {
	TUTORIAL(5, 5000, 12.0f, 5.5f, 8, () -> Ingredient.fromItems(ItemInit.EXAMPLE_ITEM.get()));

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;

	/**
	 * Create the tool material.
	 * 
	 * @param harvestLevelIn   - The level of the tool(0: Wood, 1: Stone and Gold,
	 *                         2: Iron, 3: Diamond, 4: Netherite)
	 * @param maxUsesIn        - The maximum durability of this tool material.
	 * @param efficiencyIn     - The efficiency of this tool material.
	 * @param attackDamageIn   - The base attack damage of the tool material.
	 * @param enchantabilityIn - The enchantability of this tool material.
	 * @param repairMaterialIn - An ingredient for the repair material of this tool
	 *                         material.
	 */
	private ToolMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn,
			Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
	}

	/**
	 * @return the base amount of damage for this tool material.
	 */
	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	/**
	 * @return the efficiency of this tool material.
	 */
	@Override
	public float getEfficiency() {
		return this.efficiency;
	}

	/**
	 * @return the enchantability of this tool material.
	 */
	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	/**
	 * @return the harvest level of this tool material.
	 */
	@Override
	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	/**
	 * @return the maximum durability for this tool material.
	 */
	@Override
	public int getMaxUses() {
		return this.maxUses;
	}

	/**
	 * @return the ingredient used to repair tools with this tool material.
	 */
	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}
}
