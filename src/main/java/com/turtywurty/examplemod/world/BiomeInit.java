package com.turtywurty.examplemod.world;

import com.turtywurty.examplemod.ExampleMod;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Bus.FORGE)
public class BiomeInit {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ExampleMod.MOD_ID);

	private static final RegistryObject<Biome> TEST_BIOME = BIOMES.register("test", BiomeMaker::makeTestBiome);
	private static final RegistryKey<Biome> TEST_BIOME_KEY = RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
			new ResourceLocation(ExampleMod.MOD_ID, "test"));

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void biomeLoading(BiomeLoadingEvent event) {
		if (event.getName().equals(TEST_BIOME.get().getRegistryName())) {
			BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(TEST_BIOME_KEY, 6));
			BiomeDictionary.addTypes(TEST_BIOME_KEY, Type.HILLS, Type.MOUNTAIN);
		}
	}
}
