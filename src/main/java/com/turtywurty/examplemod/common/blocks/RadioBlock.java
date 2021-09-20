package com.turtywurty.examplemod.common.blocks;

import com.turtywurty.examplemod.client.ClientStuff;
import com.turtywurty.examplemod.common.tiles.RadioTileEntity;
import com.turtywurty.examplemod.core.init.TileEntityInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class RadioBlock extends Block {

	public RadioBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.RADIO.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if (worldIn != null && worldIn.isRemote()) {
			ClientStuff.displayRadioScreen((RadioTileEntity) worldIn.getTileEntity(pos));
		}

		return ActionResultType.SUCCESS;
	}
}
