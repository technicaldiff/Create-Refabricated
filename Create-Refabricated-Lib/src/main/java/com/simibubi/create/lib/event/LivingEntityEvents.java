package com.simibubi.create.lib.event;

import java.util.Collection;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

public class LivingEntityEvents {
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

	public static final Event<Drops> DROPS = EventFactory.createArrayBacked(Drops.class, callbacks -> (source, drops) -> {
		for (Drops callback : callbacks) {
			return callback.onLivingEntityDrops(source, drops);
		}

		return false;
	});

	public static final Event<LootingLevel> LOOTING_LEVEL = EventFactory.createArrayBacked(LootingLevel.class, callbacks -> (source) -> {
		for (LootingLevel callback : callbacks) {
			int lootingLevel = callback.modifyLootingLevel(source);
			if (lootingLevel != 0) {
				return lootingLevel;
			}
		}

		return 0;
	});

	public static final Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class, callbacks -> (entity) -> {
		for (Tick callback : callbacks) {
			callback.onLivingEntityTick(entity);
		}
	});

	@FunctionalInterface
	public interface ExperienceDrop {
		int onLivingEntityExperienceDrop(int i, PlayerEntity player);
	}

	@FunctionalInterface
	public interface KnockBackStrength {
		float onLivingEntityTakeKnockback(float strength, PlayerEntity player);
	}

	@FunctionalInterface
	public interface Drops {
		boolean onLivingEntityDrops(DamageSource source, Collection<ItemEntity> drops);
	}

	@FunctionalInterface
	public interface LootingLevel {
		int modifyLootingLevel(DamageSource source);
	}

	@FunctionalInterface
	public interface Tick {
		void onLivingEntityTick(LivingEntity entity);
	}
}
