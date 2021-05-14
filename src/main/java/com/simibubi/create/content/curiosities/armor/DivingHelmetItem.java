package com.simibubi.create.content.curiosities.armor;

import com.simibubi.create.AllItems;
import com.simibubi.create.lib.helper.EntityHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class DivingHelmetItem extends CopperArmorItem {

	public DivingHelmetItem(Properties p_i48534_3_) {
		super(EquipmentSlotType.HEAD, p_i48534_3_);
	}

	public static void breatheUnderwater(LivingEntity entity) {
		World world = entity.world;
		boolean second = world.getGameTime() % 20 == 0;
		boolean drowning = entity.getAir() == 0;

		if (world.isRemote)
			EntityHelper.getExtraCustomData(entity)
				.remove("VisualBacktankAir");

		if (!AllItems.DIVING_HELMET.get()
			.isWornBy(entity))
			return;
		if (!entity.isInWater())
			return;

		ItemStack backtank = ItemStack.EMPTY;
		for (ItemStack itemStack : entity.getArmorInventoryList()) {
			if (AllItems.COPPER_BACKTANK.isIn(itemStack)) {
				backtank = itemStack;
				break;
			}
		}

		if (backtank.isEmpty())
			return;

		CompoundNBT tag = backtank.getOrCreateTag();
		int airRemaining = tag.getInt("Air");
		if (airRemaining == 0)
			return;

		if (drowning)
			entity.setAir(10);

		if (world.isRemote)
			EntityHelper.getExtraCustomData(entity)
				.putInt("VisualBacktankAir", airRemaining);

		if (!second)
			return;

		entity.setAir(Math.min(entity.getMaxAir(), entity.getAir() + 10));
		entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 30, 0, true, false, true));
		tag.putInt("Air", airRemaining - 1);
		backtank.setTag(tag);
	}

}
