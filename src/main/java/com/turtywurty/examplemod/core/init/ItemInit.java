package com.turtywurty.examplemod.core.init;

import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.common.items.ExampleArmor;
import com.turtywurty.examplemod.core.util.ArmorMaterials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ExampleMod.MOD_ID);

	public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
			() -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<ExampleArmor> EXAMPLE_HELMET = ITEMS.register("example_helmet",
			() -> new ExampleArmor(ArmorMaterials.EXAMPLE, EquipmentSlotType.HEAD,
					new Item.Properties().group(ItemGroup.COMBAT)));

	public static final RegistryObject<ExampleArmor> EXAMPLE_CHESTPLATE = ITEMS.register("example_chestplate",
			() -> new ExampleArmor(ArmorMaterials.EXAMPLE, EquipmentSlotType.CHEST,
					new Item.Properties().group(ItemGroup.COMBAT)));

	public static final RegistryObject<ExampleArmor> EXAMPLE_LEGGINGS = ITEMS.register("example_leggings",
			() -> new ExampleArmor(ArmorMaterials.EXAMPLE, EquipmentSlotType.HEAD,
					new Item.Properties().group(ItemGroup.COMBAT)));

	public static final RegistryObject<ExampleArmor> EXAMPLE_BOOTS = ITEMS.register("example_boots",
			() -> new ExampleArmor(ArmorMaterials.EXAMPLE, EquipmentSlotType.HEAD,
					new Item.Properties().group(ItemGroup.COMBAT)));
}
