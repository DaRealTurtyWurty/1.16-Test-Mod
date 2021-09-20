package com.turtywurty.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turtywurty.examplemod.core.init.BlockInit;
import com.turtywurty.examplemod.core.init.EntityTypeInit;
import com.turtywurty.examplemod.core.init.ItemInit;
import com.turtywurty.examplemod.core.init.SurfaceBuilderInit;
import com.turtywurty.examplemod.core.init.TileEntityInit;
import com.turtywurty.examplemod.world.BiomeInit;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
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
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		TileEntityInit.TILES.register(bus);
		EntityTypeInit.ENTITY_TYPES.register(bus);
		SurfaceBuilderInit.SURFACE_BUILDERS.register(bus);
		BiomeInit.BIOMES.register(bus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void copyProperties(final Block from, final Block to) {
		final Properties fromProps = from.properties;
		final Properties toProps = to.properties;
		to.properties = from.properties;
	}

	private void setup(final FMLCommonSetupEvent event) {
		Blocks.ICE.properties.setAllowsSpawn((state, reader, pos, entity) -> entity.equals(EntityType.CHICKEN)
				|| Blocks.ICE.properties.allowsSpawn.test(state, reader, pos, entity));

		copyProperties(Blocks.GLASS, Blocks.OBSIDIAN);
	}

	/*
	 * private void sus(PlayerEntity player, BlockState state, BlockPos pos,
	 * ServerWorld world) { List<ResourceLocation> loottableLocs =
	 * world.getServer().getLootTableManager().getLootTableKeys().stream()
	 * .filter(location ->
	 * location.equals(state.getBlock().getRegistryName())).collect(Collectors.
	 * toList());
	 *
	 * if (!loottableLocs.isEmpty()) { LootTable loot =
	 * world.getServer().getLootTableManager().getLootTableFromLocation(
	 * loottableLocs.get(0)); ItemStack toolStack =
	 * Items.DIAMOND_PICKAXE.getDefaultInstance();
	 * toolStack.addEnchantment(Enchantments.FORTUNE, 5); LootContext context = new
	 * LootContext.Builder(world).withParameter(LootParameters.TOOL, toolStack)
	 * .build(loot.getParameterSet()); Block.spawnDrops(state, world, pos,
	 * world.getTileEntity(pos), player, toolStack); } }
	 */
}
