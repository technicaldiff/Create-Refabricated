package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.block.HarvestableBlock;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ToolItem;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Final
	@Shadow
	public PlayerInventory inventory;

	@Inject(at = @At("HEAD"), method = "isUsingEffectiveTool(Lnet/minecraft/block/BlockState;)Z", cancellable = true)
	public void create$isUsingEffectiveTool(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		if (blockState.getBlock() instanceof HarvestableBlock && inventory.getCurrentItem().getItem() instanceof ToolItem) {
			cir.setReturnValue(((HarvestableBlock) blockState.getBlock()).isToolEffective(blockState, (ToolItem) inventory.getCurrentItem().getItem()));
		}
	}
}
