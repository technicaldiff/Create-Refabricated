package com.simibubi.create.foundation.tileEntity.behaviour.belt;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.block.funnel.BeltFunnelBlock;
import com.simibubi.create.content.logistics.block.funnel.BeltFunnelBlock.Shape;
import com.simibubi.create.content.logistics.block.funnel.FunnelBlock;
import com.simibubi.create.content.logistics.block.funnel.FunnelTileEntity;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import com.simibubi.create.lib.lba.item.IItemHandler;
import com.simibubi.create.lib.lba.item.ItemHandlerHelper;
import com.simibubi.create.lib.utility.LazyOptional;

import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.ItemInsertable;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Behaviour for TileEntities to which belts can transfer items directly in a
 * backup-friendly manner. Example uses: Basin, Saw, Depot
 */
public class DirectBeltInputBehaviour extends TileEntityBehaviour {

	public static BehaviourType<DirectBeltInputBehaviour> TYPE = new BehaviourType<>();

	private InsertionCallback tryInsert;
	private AvailabilityPredicate canInsert;
	private Supplier<Boolean> supportsBeltFunnels;

	public DirectBeltInputBehaviour(SmartTileEntity te) {
		super(te);
		tryInsert = this::defaultInsertionCallback;
		canInsert = d -> true;
		supportsBeltFunnels = () -> false;
	}

	public DirectBeltInputBehaviour allowingBeltFunnelsWhen(Supplier<Boolean> pred) {
		supportsBeltFunnels = pred;
		return this;
	}

	public DirectBeltInputBehaviour allowingBeltFunnels() {
		supportsBeltFunnels = () -> true;
		return this;
	}

	public DirectBeltInputBehaviour onlyInsertWhen(AvailabilityPredicate pred) {
		canInsert = pred;
		return this;
	}

	public DirectBeltInputBehaviour setInsertionHandler(InsertionCallback callback) {
		tryInsert = callback;
		return this;
	}

	private ItemStack defaultInsertionCallback(TransportedItemStack inserted, Direction side, boolean simulate) {
		ItemInsertable insertable = ItemAttributes.INSERTABLE.getFirstOrNull(tileEntity.getWorld(), tileEntity.getPos(), SearchOptions.inDirection(side));
		LazyOptional<IItemHandler> lazy = insertable == null
				? LazyOptional.empty()
				: LazyOptional.of(() -> (IItemHandler) insertable);

		if (!lazy.isPresent())
			return inserted.stack;
		return ItemHandlerHelper.insertItemStacked(lazy.orElse(null), inserted.stack.copy(), simulate);
	}

	public boolean canInsertFromSide(Direction side) {
		return canInsert.test(side);
	}

	public ItemStack handleInsertion(ItemStack stack, Direction side, boolean simulate) {
		return handleInsertion(new TransportedItemStack(stack), side, simulate);
	}

	public ItemStack handleInsertion(TransportedItemStack stack, Direction side, boolean simulate) {
		return tryInsert.apply(stack, side, simulate);
	}

	@Override
	public BehaviourType<?> getType() {
		return TYPE;
	}

	@FunctionalInterface
	public interface InsertionCallback {
		public ItemStack apply(TransportedItemStack stack, Direction side, boolean simulate);
	}

	@FunctionalInterface
	public interface AvailabilityPredicate {
		public boolean test(Direction side);
	}

	@Nullable
	public ItemStack tryExportingToBeltFunnel(ItemStack stack, @Nullable Direction side, boolean simulate) {
		BlockPos funnelPos = tileEntity.getPos()
			.up();
		World world = getWorld();
		BlockState funnelState = world.getBlockState(funnelPos);
		if (!(funnelState.getBlock() instanceof BeltFunnelBlock))
			return null;
		if (funnelState.get(BeltFunnelBlock.SHAPE) != Shape.PULLING)
			return null;
		if (side != null && FunnelBlock.getFunnelFacing(funnelState) != side)
			return null;
		TileEntity te = world.getTileEntity(funnelPos);
		if (!(te instanceof FunnelTileEntity))
			return null;
		if (funnelState.get(BeltFunnelBlock.POWERED))
			return stack;
		ItemStack insert = FunnelBlock.tryInsert(world, funnelPos, stack, simulate);
		if (insert.getCount() != stack.getCount() && !simulate)
			((FunnelTileEntity) te).flap(true);
		return insert;
	}

	public boolean canSupportBeltFunnels() {
		return supportsBeltFunnels.get();
	}

}
