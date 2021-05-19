package com.simibubi.create.lib.utility;

import javax.annotation.Nullable;

import com.simibubi.create.lib.extensions.AbstractMinecartEntityExtensions;
import com.simibubi.create.lib.helper.AbstractMinecartEntityHelper;
import com.simibubi.create.lib.helper.AbstractRailBlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class MinecartAndRailUtil {

	// rails

	// doesn't support modded activator rails
	public static boolean isActivatorRail(Block rail) {
		return rail == Blocks.ACTIVATOR_RAIL;
	}

	public static RailShape getDirectionOfRail(BlockState state, IBlockReader world, BlockPos pos, @Nullable AbstractMinecartEntity cart) {
		return AbstractRailBlockHelper.getDirectionOfRail(state, world, pos, cart);
	}

	public static RailShape getDirectionOfRail(BlockState state, @Nullable AbstractMinecartEntity cart) {
		return AbstractRailBlockHelper.getDirectionOfRail(state, cart);
	}

	// carts

	public static void moveMinecartOnRail(AbstractMinecartEntity cart, BlockPos pos) {
		AbstractMinecartEntityHelper.moveMinecartOnRail(cart, pos);
	}

	public static ItemStack getItemFromCart(AbstractMinecartEntity cart) {
		return AbstractMinecartEntityHelper.getCartItem(cart);
	}

	public static double getMaximumSpeed(AbstractMinecartEntity cart) {
		return AbstractMinecartEntityHelper.getMaximumSpeed(cart);
	}

	public static boolean canCartUseRail(AbstractMinecartEntity cart) {
		return AbstractMinecartEntityHelper.canCartUseRail(cart);
	}

	public static BlockPos getExpectedRailPos(AbstractMinecartEntity cart) {
		return AbstractMinecartEntityHelper.getCurrentRailPos(cart);
	}

	public static double getSlopeAdjustment() {
		return 0.0078125D;
	}

	public static MinecartController getController(AbstractMinecartEntity cart) {
		return ((AbstractMinecartEntityExtensions) cart).getController();
	}
}
