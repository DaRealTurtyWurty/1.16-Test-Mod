package com.turtywurty.examplemod.core.init;

import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.common.tiles.RadioTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class TileEntityInit {

	private TileEntityInit() {
	}

	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,
			ExampleMod.MOD_ID);

	public static final RegistryObject<TileEntityType<RadioTileEntity>> RADIO = TILES.register("radio",
			() -> TileEntityType.Builder.create(RadioTileEntity::new, BlockInit.RADIO.get()).build(null));
}
