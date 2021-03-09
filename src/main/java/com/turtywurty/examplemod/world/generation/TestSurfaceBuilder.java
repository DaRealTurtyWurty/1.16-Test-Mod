package com.turtywurty.examplemod.world.generation;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class TestSurfaceBuilder<T extends SurfaceBuilderConfig> extends SurfaceBuilder<T> {

	public TestSurfaceBuilder(Codec<T> codec) {
		super(codec);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, T config) {
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		OpenSimplexNoise simplexNoise = new OpenSimplexNoise(seed);
		int worldX = x & 15;
		int worldZ = z & 15;
		int i = -1;
		for (int y = startHeight; y >= 0; --y) {
			double value = simplexNoise.eval(x, y, z);
			int wholeNoise = (int) ((value / 3.0D + 3.0D + random.nextDouble() * 0.25D));
			mutablePos.setPos(worldX, y, worldZ);
			BlockState blockstate = Blocks.GRASS_BLOCK.getDefaultState();
			BlockState blockstate1 = chunkIn.getBlockState(mutablePos);
			if (blockstate1.isAir()) {
				i = -1;
			} else if (blockstate1.isIn(defaultBlock.getBlock())) {
				if (i == -1) {
					if (wholeNoise <= 0) {
						blockstate = Blocks.AIR.getDefaultState();
						blockstate1 = defaultBlock;
					} else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
						blockstate = Blocks.GRASS_BLOCK.getDefaultState();
						blockstate1 = Blocks.STONE.getDefaultState();
					}

					if (y < seaLevel && (blockstate == null || blockstate.isAir())) {
						if (biomeIn.getTemperature(mutablePos.setPos(x, y, z)) < 0.15F) {
							blockstate = Blocks.ICE.getDefaultState();
						} else {
							blockstate = defaultFluid;
						}

						mutablePos.setPos(x, y, z);
					}

					i = wholeNoise;
					if (y >= seaLevel - 1) {
						chunkIn.setBlockState(mutablePos, blockstate, false);
					} else if (y < seaLevel - 7 - wholeNoise) {
						blockstate = Blocks.AIR.getDefaultState();
						blockstate1 = defaultBlock;
						chunkIn.setBlockState(mutablePos, Blocks.BEDROCK.getDefaultState(), false);
					} else {
						chunkIn.setBlockState(mutablePos, blockstate1, false);
					}
				} else if (i > 0) {
					--i;
					chunkIn.setBlockState(mutablePos, blockstate1, false);
					if (i == 0 && blockstate1.isIn(Blocks.SAND) && wholeNoise > 1) {
						i = random.nextInt(4) + Math.max(0, y - 63);
						blockstate1 = blockstate1.isIn(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState()
								: Blocks.SANDSTONE.getDefaultState();
					}
				}
			}
		}
	}
}