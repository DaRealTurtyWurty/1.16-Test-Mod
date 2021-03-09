package com.turtywurty.examplemod.world;

import com.turtywurty.examplemod.core.init.SurfaceBuilderInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeMaker {

	public static Biome makeTestBiome() {
		BiomeGenerationSettings.Builder generationSettings = genSettings(SurfaceBuilderInit.NAKED_TEST_SURFACE_BUILDER,
				SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);

		WorldGenRegistries.init();
		DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
		DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
		DefaultBiomeFeatures.withLavaAndWaterLakes(generationSettings);

		MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
		// spawnSettings.withSpawner(EntityClassification.CREATURE, new
		// MobSpawnInfo.Spawners(EntityTypeInit.TEST_ENTITY.get(), 12, 2, 3));
		spawnSettings.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.MULE, 5, 1, 3));
		DefaultBiomeFeatures.withPassiveMobs(spawnSettings);
		DefaultBiomeFeatures.withBatsAndHostiles(spawnSettings);

		return biome(RainType.NONE, Category.EXTREME_HILLS, 0.13f, 0.5f, 0.5f, 0.3f,
				new BiomeAmbience.Builder().withGrassColor(0xe80a0a).setWaterColor(0x3f76e4).setWaterFogColor(0x50533)
						.withFoliageColor(0x77ab2f).setFogColor(12638463).withSkyColor(getSkyForTemp(0.5f)),
				generationSettings, spawnSettings.copy());
	}

	private static Biome biome(RainType precipitation, Category category, float depth, float scale, float temperature,
			float downfall, BiomeAmbience.Builder effects, BiomeGenerationSettings.Builder genSettings,
			MobSpawnInfo spawnSettings) {
		return new Biome.Builder().precipitation(precipitation).category(category).depth(depth).scale(scale)
				.temperature(temperature).downfall(downfall).setEffects(effects.build())
				.withGenerationSettings(genSettings.build()).withMobSpawnSettings(spawnSettings).build();
	}

	private static <C extends ISurfaceBuilderConfig> BiomeGenerationSettings.Builder genSettings(
			SurfaceBuilder<C> surfaceBuilder, C config) {
		return new BiomeGenerationSettings.Builder().withSurfaceBuilder(surfaceBuilder.func_242929_a(config));
	}

	private static int getSkyForTemp(float temperature) {
		float a = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f);
		return MathHelper.hsvToRGB(0.62222224f - a * 0.05f, 0.5f + a * 0.1f, 1.0f);
	}
}
