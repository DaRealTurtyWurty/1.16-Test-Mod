package com.turtywurty.examplemod;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Bus.FORGE)
public final class SomeEvents {

	private SomeEvents() {
	}

	@SubscribeEvent
	public static void rightClickItem(RightClickItem event) {
		ItemStack stack = event.getItemStack();
		PlayerEntity player = event.getPlayer();
		if (stack.getItem() == Items.ACACIA_BOAT) {
			Effect effect = ForgeRegistries.POTIONS.getValues().parallelStream().filter(e -> !e.isInstant())
					.collect(Collectors.toList())
					.get(ThreadLocalRandom.current().nextInt(ForgeRegistries.POTIONS.getValues().size()));
			player.addPotionEffect(new EffectInstance(effect, 100, 1));
		}
	}
}
