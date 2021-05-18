package com.simibubi.create.lib.utility;

import org.jetbrains.annotations.Nullable;

import alexiil.mc.lib.attributes.Attribute;
import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.SearchOption;
import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * I realize that the math in this class is almost definitely wrong, I will fix it Soon™
 */
public class FluidUtil {
	public static FluidAmount millibucketsToFluidAmount(int millibuckets) {
		double buckets = millibuckets / (float) 1000;
		int wholeBuckets = 0;
		for (int i = 0; i < buckets; i++) {
			if (buckets >= 1) {
				buckets--;
				wholeBuckets++;
			}
		}
		return FluidAmount.of(wholeBuckets, (long) buckets * 100, 100);
	}

	public static int fluidAmountToMillibuckets(FluidAmount amount) {
		return (int) ((float) amount.numerator / (float) amount.denominator) * 1000;
	}

	public static <T> AttributeList<T> getAttributeProvidersAtPos(World world, BlockPos pos, @Nullable Direction direction, Attribute attribute) {
		SearchOption option;
		if (direction != null) {
			 option = SearchOptions.inDirection(direction);
		} else {
			option = SearchOptions.ALL;
		}

		return attribute.getAll(world, pos, option);
	}

	public static FluidAmount divideFluidAmounts(FluidAmount one, FluidAmount two) {
		float millibuckets1 = fluidAmountToMillibuckets(one);
		float millibuckets2 = fluidAmountToMillibuckets(two);
		float result = millibuckets1 / millibuckets2;
		return millibucketsToFluidAmount((int) result);
	}

	public static boolean isLighterThanAir(FluidVolume volume) {
		return fluidAmountToMillibuckets(volume.fluidKey.density) <= fluidAmountToMillibuckets(FluidAmount.ZERO);
	}
}
