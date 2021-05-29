package com.simibubi.create.foundation.worldgen;

import java.util.Optional;

import com.simibubi.create.foundation.config.CWorldGen;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.lib.utility.ConfigValue;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import dev.inkwell.vivian.api.builders.CategoryBuilder;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;

public class ConfigDrivenFeatureEntry {

	public final String id;
	public final NonNullSupplier<? extends Block> block;

	protected ConfigValue<Integer> clusterSize;
	protected ConfigValue<Integer> minHeight;
	protected ConfigValue<Integer> maxHeight;
	protected ConfigValue<Float> frequency;

	public static CategoryBuilder category = null;

	Optional<ConfiguredFeature<?, ?>> feature = Optional.empty();

	public ConfigDrivenFeatureEntry(String id, NonNullSupplier<? extends Block> block, int clusterSize,
		float frequency) {
		category = ConfigBase.group(1, id, CWorldGen.worldgen);
		this.id = id;
		this.block = block;
		this.clusterSize = ConfigBase.i(clusterSize, 0, "clusterSize", category);
		this.minHeight = ConfigBase.i(0, 0, "minHeight", category);
		this.maxHeight = ConfigBase.i(256, 0, "maxHeight", category);
		this.frequency = ConfigBase.f(frequency, 0, 512, "frequency", category, "Amount of clusters generated per Chunk.",
			"  >1 to spawn multiple.", "  <1 to make it a chance.", "  0 to disable.");
	}

	public ConfigDrivenFeatureEntry between(int minHeight, int maxHeight) {
//		allValues.remove(this.minHeight);
//		allValues.remove(this.maxHeight);
		this.minHeight = ConfigBase.i(minHeight, 0, "minHeight", category);
		this.maxHeight = ConfigBase.i(maxHeight, 0, "maxHeight", category);
		return this;
	}

	public ConfiguredFeature<?, ?> getFeature() {
		if (!feature.isPresent())
			feature = Optional.of(createFeature());
		return feature.get();
	}

	private ConfiguredFeature<?, ?> createFeature() {
		ConfigDrivenOreFeatureConfig config =
			new ConfigDrivenOreFeatureConfig(FillerBlockType.BASE_STONE_OVERWORLD, block.get()
				.getDefaultState(), id);

		return ConfigDrivenOreFeature.INSTANCE.configure(config)
			.decorate(ConfigDrivenDecorator.INSTANCE.configure(config));
	}

	public void addToConfig(CategoryBuilder builder) {
//		registerAll(builder);
	}

//	@Override
	public String getName() {
		return id;
	}

}
