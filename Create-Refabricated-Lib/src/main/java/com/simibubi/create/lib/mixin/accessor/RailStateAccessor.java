package com.simibubi.create.lib.mixin.accessor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.RailState;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;

import net.minecraft.util.math.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(RailState.class)
public interface RailStateAccessor {
	@Accessor("pos")
	BlockPos create$pos();

	@Invoker("checkConnected")
	void create$checkConnected();

	@Invoker("func_196905_c")
	boolean create$func_196905_c(RailState railState);
}
