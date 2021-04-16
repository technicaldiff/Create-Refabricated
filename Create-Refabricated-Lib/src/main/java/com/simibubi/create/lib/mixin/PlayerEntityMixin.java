package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.block.CustomHarvestablilityBehavior;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ToolItem;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Final
	@Shadow
	public final PlayerInventory inventory = new PlayerInventory(MixinHelper.cast(this));

	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/entity/player/PlayerEntity;isUsingEffectiveTool(Lnet/minecraft/block/BlockState;)Z", cancellable = true)
	public void create$isUsingEffectiveTool(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		if (blockState.getBlock() instanceof CustomHarvestablilityBehavior && inventory.getCurrentItem().getItem() instanceof ToolItem) {
			cir.setReturnValue(((CustomHarvestablilityBehavior) blockState.getBlock()).isToolEffective(blockState, (ToolItem) inventory.getCurrentItem().getItem()));
		}
	}
}
