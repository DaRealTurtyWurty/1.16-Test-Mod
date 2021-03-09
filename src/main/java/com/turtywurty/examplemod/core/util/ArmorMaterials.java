package com.turtywurty.examplemod.core.util;

import java.util.function.Supplier;

import com.turtywurty.examplemod.ExampleMod;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags.Items;

public enum ArmorMaterials implements IArmorMaterial {
	EXAMPLE(ExampleMod.MOD_ID + ":example", 5, new int[] { 4, 7, 9, 4 }, 30, SoundEvents.ENTITY_PLAYER_LEVELUP, 0.0f, 0.2f,
			() -> Ingredient.fromTag(Items.BONES));

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyValue<Ingredient> repairMaterial;

	/**
	 * Create the armor material.
	 * 
	 * @param name                       - The name of the armor material. Should be
	 *                                   in the format <code>"modid:name"</code>
	 * @param maxDamageFactor            - The multiplier for the durability of the
	 *                                   armor pieces.
	 * @param damageReductionAmountArray - The array from toe to head that each
	 *                                   armor piece will use to reduct damage.
	 * @param enchantability             - How enchantable this armor is; higher the
	 *                                   number, the more enchantable.
	 * @param soundEvent                 - The sound event that will play when this
	 *                                   armor set is equipped.
	 * @param toughness                  - The amount of toughness this armor set
	 *                                   will use. Information on toughness can be
	 *                                   found <a
	 *                                   href"https://minecraft.gamepedia.com/Armor#Armor_toughness">here</a>
	 * @param knockbackResistance        - The amount of knockback resistance this
	 *                                   armor material applies.
	 * @param repairMaterial             - An ingredient for the repair material
	 *                                   that is used for this armor material.
	 */
	private ArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
			SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = new LazyValue<Ingredient>(repairMaterial);
	}

	/**
	 * Gets the durability for this armor material based on the slot.
	 * 
	 * @param slotIn - The equipment slot to get the durability for.
	 * 
	 * @return The durability of the armor for that slot.
	 */
	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	/**
	 * Gets the reduction amount for this armor material based on the slot.
	 *
	 * @param slotIn - The equipment slot to get the reduction amount for.
	 * 
	 * @return The reduction amount of the armor for that slot.
	 */
	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	/**
	 * Gets the enchantability for this armor material.
	 * 
	 * @return The enchantability for this armor material
	 */
	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	/**
	 * Gets the equip sound for this armor material.
	 * 
	 * @return The sound for this armor material.
	 */
	@Override
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	/**
	 * Gets the items that can be used to repair this armor in an anvil. This should
	 * only be called after items have been registered.
	 * 
	 * @return The ingredient that should be used. This can either be from a tag or
	 *         from an item.
	 */
	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

	/**
	 * Gets the name of this armor material. This should only be called client side
	 * as the server will not know the name.
	 * 
	 * @return The name of the armor material.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the toughness of this armor material.
	 * 
	 * @return The toughness.
	 */
	@Override
	public float getToughness() {
		return this.toughness;
	}

	/**
	 * Gets the knockback resistance that this armor material has.
	 * 
	 * @returns The knockback resistance.
	 */
	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}