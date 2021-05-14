package com.simibubi.create.lib.condition;

import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.multipart.ICondition;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.fabricmc.loader.api.FabricLoader;

public class ModLoadedCondition implements ICondition {
	private static final ResourceLocation NAME = new ResourceLocation("create", "mod_loaded");
	private final String modid;

	public ModLoadedCondition(String modid) {
		this.modid = modid;
	}

	@Override
	public String toString() {
		return "mod_loaded(\"" + modid + "\")";
	}

	@Override
	public Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> stateContainer) {
		return (blockState) -> FabricLoader.getInstance().isModLoaded(modid);
	}
}
