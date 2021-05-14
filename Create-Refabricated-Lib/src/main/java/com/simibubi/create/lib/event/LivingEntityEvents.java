package com.simibubi.create.lib.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class LivingEntityEvents {
	@Environment(EnvType.CLIENT)
	public static final Event<ItemSwing> ITEM_SWING = EventFactory.createArrayBacked(ItemSwing.class, callbacks -> (stack, entity) -> {
		for (ItemSwing callback : callbacks) {
			return callback.onEntityItemSwing(stack, entity);
		}

		return false;
	});

	public static final Event<ExperienceDrop> EXPERIENCE_DROP = EventFactory.createArrayBacked(ExperienceDrop.class, callbacks -> (i, player) -> {
		for (ExperienceDrop callback : callbacks) {
			return callback.onLivingEntityExperienceDrop(i, player);
		}

		return 0;
	});

	public static final Event<KnockBackStrength> KNOCKBACK_STRENGTH = EventFactory.createArrayBacked(KnockBackStrength.class, callbacks -> (strength, player) -> {
		for (KnockBackStrength callback : callbacks) {
			return callback.onLivingEntityTakeKnockback(strength, player);
		}

		return 0;
	});

	public static final Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class, callbacks -> (entity) -> {
		for (Tick callback : callbacks) {
			callback.onLivingEntityTick(entity);
		}
	});

	@Environment(EnvType.CLIENT)
	@FunctionalInterface
	public interface ItemSwing {
		boolean onEntityItemSwing(ItemStack stack, LivingEntity entity);
	}

	@FunctionalInterface
	public interface ExperienceDrop {
		int onLivingEntityExperienceDrop(int i, PlayerEntity player);
	}

	@FunctionalInterface
	public interface KnockBackStrength {
		float onLivingEntityTakeKnockback(float strength, PlayerEntity player);
	}

	@FunctionalInterface
	public interface Tick {
		void onLivingEntityTick(LivingEntity entity);
	}
}
