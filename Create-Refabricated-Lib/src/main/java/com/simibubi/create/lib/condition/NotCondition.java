package com.simibubi.create.lib.condition;

import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.multipart.ICondition;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;

public class NotCondition implements ICondition
{
	private static final ResourceLocation NAME = new ResourceLocation("forge", "not");
	private final ICondition child;

	public NotCondition(ICondition child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "!" + child;
	}

	@Override
	public Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> stateContainer) {
		return child.getPredicate(stateContainer).negate();
	}
}
