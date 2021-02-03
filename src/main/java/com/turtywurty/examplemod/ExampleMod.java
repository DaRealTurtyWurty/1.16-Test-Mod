package com.turtywurty.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turtywurty.examplemod.core.init.BlockInit;
import com.turtywurty.examplemod.core.init.EntityTypeInit;
import com.turtywurty.examplemod.core.init.ItemInit;
import com.turtywurty.examplemod.world.BiomeInit;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MOD_ID)
public class ExampleMod {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "examplemod";

	public ExampleMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EntityTypeInit.ENTITY_TYPES.register(bus);
		BiomeInit.BIOMES.register(bus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		Blocks.ICE.properties.setAllowsSpawn((state, reader, pos, entity) -> entity.equals(EntityType.CHICKEN)
				|| Blocks.ICE.properties.allowsSpawn.test(state, reader, pos, entity));
	}
}
