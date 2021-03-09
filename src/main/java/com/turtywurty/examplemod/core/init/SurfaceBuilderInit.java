package com.turtywurty.examplemod.core.init;

import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.world.generation.TestSurfaceBuilder;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceBuilderInit {
	
	public static final SurfaceBuilder NAKED_TEST_SURFACE_BUILDER = new TestSurfaceBuilder<>(SurfaceBuilderConfig.field_237203_a_);

	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister
			.create(ForgeRegistries.SURFACE_BUILDERS, ExampleMod.MOD_ID);

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> TEST_SURFACE_BUILDER = SURFACE_BUILDERS
			.register("test_surface_builder", () -> NAKED_TEST_SURFACE_BUILDER);
}
