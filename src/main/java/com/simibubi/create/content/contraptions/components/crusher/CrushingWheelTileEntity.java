package com.simibubi.create.content.contraptions.components.crusher;

import java.util.Collection;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.lib.helper.DamageSourceHelper;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

public class CrushingWheelTileEntity extends KineticTileEntity {

	public static DamageSource damageSource = DamageSourceHelper.create$createDamageSourceWhichBypassesArmor("create.crush").setDifficultyScaled();

	public CrushingWheelTileEntity(TileEntityType<? extends CrushingWheelTileEntity> type) {
		super(type);
		setLazyTickRate(20);
	}

	@Override
	public void onSpeedChanged(float prevSpeed) {
		super.onSpeedChanged(prevSpeed);
		fixControllers();
	}

	public void fixControllers() {
		for (Direction d : Iterate.directions)
			((CrushingWheelBlock) getBlockState().getBlock()).updateControllers(getBlockState(), getWorld(), getPos(),
					d);
	}

//	@Override
//	public AxisAlignedBB makeRenderBoundingBox() {
//		return new AxisAlignedBB(pos).grow(1);
//	}

	@Override
	public void lazyTick() {
		super.lazyTick();
		fixControllers();
	}

	public static int crushingIsFortunate(DamageSource source) {
		if (source != damageSource)
			return 0;
		return 2;		//This does not currently increase mob drops. It seems like this only works for damage done by an entity.
	}

	public static boolean handleCrushedMobDrops(DamageSource source, Collection<ItemEntity> drops) {
		if (source != CrushingWheelTileEntity.damageSource)
			return false;
		Vector3d outSpeed = Vector3d.ZERO;
		for (ItemEntity outputItem : drops) {
			outputItem.setMotion(outSpeed);
		}

		return false;
	}

}
