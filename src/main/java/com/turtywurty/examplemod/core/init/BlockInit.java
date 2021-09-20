package com.turtywurty.examplemod.core.init;

import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.common.blocks.RadioBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BlockInit {

	private BlockInit() {
	}

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MOD_ID);

	public static final RegistryObject<RadioBlock> RADIO = BLOCKS.register("radio",
			() -> new RadioBlock(AbstractBlock.Properties.create(Material.IRON)));
}
