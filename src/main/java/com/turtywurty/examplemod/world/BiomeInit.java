package com.turtywurty.examplemod.world;

import com.turtywurty.examplemod.ExampleMod;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Bus.MOD)
public class BiomeInit {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ExampleMod.MOD_ID);

	private static final RegistryObject<Biome> RUBY_HILLS = BIOMES.register("ruby_hills", BiomeMaker::makeRubyHills);
	private static final RegistryKey<Biome> RUBY_HILLS_KEY = RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
			new ResourceLocation(ExampleMod.MOD_ID, "ruby_hills"));

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void biomeLoading(BiomeLoadingEvent event) {
		if (event.getName() == RUBY_HILLS.get().getRegistryName()) {
			BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RUBY_HILLS_KEY, 6));
		}
	}
}
