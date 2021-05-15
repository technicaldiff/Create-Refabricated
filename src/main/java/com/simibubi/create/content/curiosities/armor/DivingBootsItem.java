package com.simibubi.create.content.curiosities.armor;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.lib.helper.LivingEntityHelper;
import com.simibubi.create.lib.utility.ExtraDataUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Vector3d;

public class DivingBootsItem extends CopperArmorItem {

	public DivingBootsItem(Properties p_i48534_3_) {
		super(EquipmentSlotType.FEET, p_i48534_3_);
	}

	public static void accellerateDescentUnderwater(LivingEntity entity) {
		if (!affects(entity))
			return;

		Vector3d motion = entity.getMotion();
		boolean isJumping = LivingEntityHelper.isFlying(entity);
		entity.setOnGround(entity.isOnGround() | entity.collidedVertically);

		if (isJumping && entity.isOnGround()) {
			motion = motion.add(0, .5f, 0);
			entity.setOnGround(false);
		} else {
			motion = motion.add(0, -0.05f, 0);
		}

		float multiplier = 1.3f;
		if (motion.mul(1, 0, 1)
			.length() < 0.145f && (entity.moveForward > 0 || entity.moveStrafing != 0) && !entity.isSneaking())
			motion = motion.mul(multiplier, 1, multiplier);
		entity.setMotion(motion);
	}

	protected static boolean affects(LivingEntity entity) {
		if (!AllItems.DIVING_BOOTS.get()
			.isWornBy(entity)) {
			ExtraDataUtil.getExtraData(entity)
				.remove("HeavyBoots");

			return false;
		}

		NBTHelper.putMarker(ExtraDataUtil.getExtraData(entity), "HeavyBoots");
		if (!entity.isInWater())
			return false;
		if (entity.getPose() == Pose.SWIMMING)
			return false;
		if (entity instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) entity;
			if (playerEntity.abilities.isFlying)
				return false;
		}
		return true;
	}
}
