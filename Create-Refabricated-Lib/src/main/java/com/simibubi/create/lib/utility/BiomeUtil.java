package com.simibubi.create.lib.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.simibubi.create.lib.mixin.accessor.BiomeGenerationSettings$BuilderAccessor;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;

public class BiomeUtil {
	public static BiomeGenerationSettings.Builder settingsToBuilder(BiomeGenerationSettings settings) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
		((BiomeGenerationSettings$BuilderAccessor) builder).setSurfaceBuilder(Optional.of(settings.getSurfaceBuilder()));
		Collections.unmodifiableSet(((BiomeGenerationSettings$BuilderAccessor) builder).getCarvers().keySet()).forEach(c -> {
			Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> newCarvers = ((BiomeGenerationSettings$BuilderAccessor) builder).getCarvers();
			newCarvers.put(c, new ArrayList<>(settings.getCarversForStep(c)));
			((BiomeGenerationSettings$BuilderAccessor) builder).setCarvers(newCarvers);
		});
		((BiomeGenerationSettings$BuilderAccessor) builder).getFeatures().forEach(f -> {
			List<List<Supplier<ConfiguredFeature<?, ?>>>> newFeatures = ((BiomeGenerationSettings$BuilderAccessor) builder).getFeatures();
			newFeatures.add(new ArrayList<>(f));
			((BiomeGenerationSettings$BuilderAccessor) builder).setFeatures(newFeatures);
		});
		List<Supplier<StructureFeature<?, ?>>> newStructureFeatures = ((BiomeGenerationSettings$BuilderAccessor) builder).getStructureFeatures();
		newStructureFeatures.addAll(settings.getStructureFeatures());
		((BiomeGenerationSettings$BuilderAccessor) builder).setStructureFeatures(newStructureFeatures);
		return builder;
	}
}
