package com.simibubi.create.content.curiosities;

import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.lib.item.EntityTickListenerItem;
import com.simibubi.create.lib.utility.ExtraDataUtil;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class NoGravMagicalDohickyItem extends Item implements EntityTickListenerItem {

	public NoGravMagicalDohickyItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		World world = entity.world;
		Vector3d pos = entity.getPositionVec();
		CompoundNBT persistentData = ExtraDataUtil.getExtraData(entity);

		if (world.isRemote) {
			if (world.rand.nextFloat() < getIdleParticleChance(entity)) {
				Vector3d ppos = VecHelper.offsetRandomly(pos, world.rand, .5f);
				world.addParticle(ParticleTypes.END_ROD, ppos.x, pos.y, ppos.z, 0, -.1f, 0);
			}

			if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
				Vector3d basemotion = new Vector3d(0, 1, 0);
				world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
				for (int i = 0; i < 20; i++) {
					Vector3d motion = VecHelper.offsetRandomly(basemotion, world.rand, 1);
					world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
					world.addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
				}
				persistentData.putBoolean("PlayEffects", true);
			}

			return false;
		}

		entity.setNoGravity(true);

		if (!persistentData.contains("JustCreated"))
			return false;
		onCreated(entity, persistentData);
		return false;
	}

	protected float getIdleParticleChance(ItemEntity entity) {
		return MathHelper.clamp(entity.getItem()
			.getCount() - 10, 5, 100) / 64f;
	}

	protected void onCreated(ItemEntity entity, CompoundNBT persistentData) {
//		entity.lifespan = 6000; fixme if needed
		persistentData.remove("JustCreated");

		// just a flag to tell the client to play an effect
		entity.setSilent(true);
	}

}
