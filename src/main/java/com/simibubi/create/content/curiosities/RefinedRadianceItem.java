package com.simibubi.create.content.curiosities;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.simibubi.create.foundation.mixin.accessor.ItemEntityAccessor;
import com.simibubi.create.foundation.utility.VecHelper;

public class RefinedRadianceItem extends Item {

	public RefinedRadianceItem(Settings properties) {
		super(properties);
	}
	static boolean animationPlayed = false;
	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	//@Override
	public static boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if (stack.getItem() instanceof RefinedRadianceItem) {
			World world = entity.world;
			Vec3d pos = entity.getPos();

			if (world.isClient && entity.hasNoGravity()) {
				if (world.random.nextFloat() < MathHelper.clamp(entity.getStack().getCount() - 10, 1, 100) / 64f) {
					Vec3d ppos = VecHelper.offsetRandomly(pos, world.random, .5f);
					world.addParticle(ParticleTypes.END_ROD, ppos.x, pos.y, ppos.z, 0, -.1f, 0);
				}

				if (animationPlayed == false) {
			//if (!entity.getPersistentData().contains("ClientAnimationPlayed")) {
				Vec3d basemotion = new Vec3d(0, 1, 0);
				world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
				for (int i = 0; i < 20; i++) {
					Vec3d motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
					world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
					world.addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
				}
				//entity.getPersistentData().putBoolean("ClientAnimationPlayed", true);
					animationPlayed = true;
				}

				return false;
			}

			//if (!entity.getPersistentData().contains("FromLight"))
			//return false;

			((ItemEntityAccessor) entity).setAge(0);
			entity.setNoGravity(true);
			//entity.setVelocity(entity.getVelocity().add(0, .15f, 0)); todo
			//entity.getPersistentData().remove("FromLight");
			return false;
		}
		return false;
	}
}
