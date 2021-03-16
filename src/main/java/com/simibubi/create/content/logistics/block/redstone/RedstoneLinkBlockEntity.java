package com.simibubi.create.content.logistics.block.redstone;

import static net.minecraft.state.property.Properties.POWERED;

import java.util.List;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.foundation.block.entity.BlockEntityBehaviour;
import com.simibubi.create.foundation.block.entity.SmartBlockEntity;
import com.simibubi.create.foundation.block.entity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.block.entity.behaviour.linked.LinkBehaviour;
import org.apache.commons.lang3.tuple.Pair;

import com.simibubi.create.AllBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class RedstoneLinkBlockEntity extends SmartBlockEntity {

	private boolean receivedSignalChanged;
	private int receivedSignal;
	private int transmittedSignal;
	private LinkBehaviour link;
	private boolean transmitter;

	public RedstoneLinkBlockEntity() {
		super(AllBlockEntities.REDSTONE_LINK);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
	}

	@Override
	public void addBehavioursDeferred(List<BlockEntityBehaviour> behaviours) {
		createLink();
		behaviours.add(link);
	}

	protected void createLink() {
		Pair<ValueBoxTransform, ValueBoxTransform> slots =
			ValueBoxTransform.Dual.makeSlots(RedstoneLinkFrequencySlot::new);
		link = transmitter ? LinkBehaviour.transmitter(this, slots, this::getSignal)
				: LinkBehaviour.receiver(this, slots, this::setSignal);
	}

	public int getSignal() {
		return transmittedSignal;
	}

	public void setSignal(int power) {
		if (receivedSignal != power)
			receivedSignalChanged = true;
		receivedSignal = power;
	}

	public void transmit(int strength) {
		transmittedSignal = strength;
		if (link != null)
			link.notifySignalChange();
	}

	@Override
	public void toTag(CompoundTag compound, boolean clientPacket) {
		compound.putBoolean("Transmitter", transmitter);
		compound.putInt("Receive", getReceivedSignal());
		compound.putBoolean("ReceivedChanged", receivedSignalChanged);
		compound.putInt("Transmit", transmittedSignal);
		super.toTag(compound, clientPacket);
	}

	@Override
	protected void fromTag(BlockState state, CompoundTag compound, boolean clientPacket) {
		transmitter = compound.getBoolean("Transmitter");
		super.fromTag(state, compound, clientPacket);
		
		receivedSignal = compound.getInt("Receive");
		receivedSignalChanged = compound.getBoolean("ReceivedChanged");
		if (world == null || world.isClient || !link.newPosition)
			transmittedSignal = compound.getInt("Transmit");
	}

	@Override
	public void tick() {
		super.tick();

		if (isTransmitterBlock() != transmitter) {
			transmitter = isTransmitterBlock();
			LinkBehaviour prevlink = link;
			removeBehaviour(LinkBehaviour.TYPE);
			createLink();
			link.copyItemsFrom(prevlink);
			attachBehaviourLate(link);
		}

		if (transmitter)
			return;
		if (world.isClient)
			return;
		
		BlockState blockState = getCachedState();
		if (!AllBlocks.REDSTONE_LINK.getStateManager().getStates().contains(blockState))
			return;

		if ((getReceivedSignal() > 0) != blockState.get(POWERED)) {
			receivedSignalChanged = true;
			world.setBlockState(pos, blockState.cycle(POWERED));
		}
		
		if (receivedSignalChanged) {
			Direction attachedFace = blockState.get(RedstoneLinkBlock.FACING).getOpposite();
			BlockPos attachedPos = pos.offset(attachedFace);
			world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
			world.updateNeighbors(attachedPos, world.getBlockState(attachedPos).getBlock());
		}
	}

	protected Boolean isTransmitterBlock() {
		return !getCachedState().get(RedstoneLinkBlock.RECEIVER);
	}

	public int getReceivedSignal() {
		return receivedSignal;
	}

}
