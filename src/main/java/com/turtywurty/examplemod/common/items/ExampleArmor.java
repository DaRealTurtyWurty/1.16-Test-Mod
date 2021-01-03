package com.turtywurty.examplemod.common.items;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.turtywurty.examplemod.ExampleMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExampleArmor extends ArmorItem {

	public ExampleArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);

		Stream<Item> armorStream = StreamSupport.stream(player.getArmorInventoryList().spliterator(), false)
				.map(ItemStack::getItem);
		boolean hasAllArmor = armorStream.allMatch(armor -> armor instanceof ExampleArmor);
		boolean hasAnyArmor = armorStream.anyMatch(armor -> armor instanceof ExampleArmor);

		if (hasAllArmor) {
			// do something
		} else if (hasAnyArmor) {
			// do this
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
		if (!world.isRemote()) {
			sendPlayerToDimension((ServerPlayerEntity) player,
					world.getServer().getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
							new ResourceLocation(ExampleMod.MOD_ID, "example_dim"))),
					player.getPositionVec());
		}
		return super.onItemRightClick(world, player, handIn);
	}

	public static void sendPlayerToDimension(ServerPlayerEntity serverPlayer, ServerWorld targetWorld,
			Vector3d targetVec) {
		// ensure destination chunk is loaded before we put the player in it
		targetWorld.getChunk(new BlockPos(targetVec));
		serverPlayer.teleport(targetWorld, targetVec.getX(), targetVec.getY(), targetVec.getZ(),
				serverPlayer.rotationYaw, serverPlayer.rotationPitch);
	}
}
