package com.simibubi.create.lib.mixin.accessor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

@Mixin(BiomeGenerationSettings.Builder.class)
public interface BiomeGenerationSettings$BuilderAccessor {
	@Accessor("surfaceBuilder")
	public Optional<Supplier<ConfiguredSurfaceBuilder<?>>> getSurfaceBuilder();
	@Accessor("surfaceBuilder")
	public void setSurfaceBuilder(Optional<Supplier<ConfiguredSurfaceBuilder<?>>> surfaceBuilder);

	@Accessor("carvers")
	public Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> getCarvers();
	@Accessor("carvers")
	public void setCarvers(Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> carvers);

	@Accessor("features")
	public List<List<Supplier<ConfiguredFeature<?, ?>>>> getFeatures();
	@Accessor("features")
	public void setFeatures(List<List<Supplier<ConfiguredFeature<?, ?>>>> features);

	@Accessor("structureFeatures")
	public List<Supplier<StructureFeature<?, ?>>> getStructureFeatures();
	@Accessor("structureFeatures")
	public void setStructureFeatures(List<Supplier<StructureFeature<?, ?>>> structureFeatures);
}
