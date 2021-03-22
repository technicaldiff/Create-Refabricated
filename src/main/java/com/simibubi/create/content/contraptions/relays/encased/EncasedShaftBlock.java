package com.simibubi.create.content.contraptions.relays.encased;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.BlockView;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.CasingBlock;
import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.schematics.ItemRequirement;
import com.simibubi.create.content.schematics.SpecialBlockItemRequirement;

public class EncasedShaftBlock extends AbstractEncasedShaftBlock implements SpecialBlockItemRequirement {

	private CasingBlock casing;

	public static EncasedShaftBlock andesite(Settings properties) {
		return new EncasedShaftBlock(properties, AllBlocks.ANDESITE_CASING);
	}
	
	public static EncasedShaftBlock brass(Settings properties) {
		return new EncasedShaftBlock(properties, AllBlocks.BRASS_CASING);
	}
	
	protected EncasedShaftBlock(Settings properties, CasingBlock casing) {
		super(properties);
		this.casing = casing;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return AllBlockEntities.ENCASED_SHAFT.instantiate();
	}

	public CasingBlock getCasing() {
		return casing;
	}

	@Override
	public ActionResult onSneakWrenched(BlockState state, ItemUsageContext context) {
		if (context.getWorld().isClient)
			return ActionResult.SUCCESS;
		context.getWorld().syncWorldEvent(2001, context.getBlockPos(), Block.getRawIdFromState(state));
		KineticBlockEntity.switchToBlockState(context.getWorld(), context.getBlockPos(), AllBlocks.SHAFT.getDefaultState().with(AXIS, state.get(AXIS)));
		return ActionResult.SUCCESS;
	}
	
	@Override
	public ItemRequirement getRequiredItems(BlockState state) {
		return ItemRequirement.of(AllBlocks.SHAFT.getDefaultState());
	}

}
