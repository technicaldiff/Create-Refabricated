package com.simibubi.create;

import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.SpawnGroup;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionEntityRenderer;
import com.simibubi.create.content.contraptions.components.structureMovement.ControlledContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.OrientedContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.OrientedContraptionEntityRenderer;
import com.simibubi.create.content.contraptions.components.structureMovement.gantry.GantryContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.glue.SuperGlueEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.glue.SuperGlueRenderer;
import com.simibubi.create.foundation.utility.Lang;

public class AllEntityTypes {

	public static final RegistryEntry<EntityType<OrientedContraptionEntity>> ORIENTED_CONTRAPTION =
		contraption("contraption", OrientedContraptionEntity::new, 5, 3, true);
	public static final RegistryEntry<EntityType<ControlledContraptionEntity>> CONTROLLED_CONTRAPTION =
		contraption("stationary_contraption", ControlledContraptionEntity::new, 20, 40, false);
	public static final RegistryEntry<EntityType<GantryContraptionEntity>> GANTRY_CONTRAPTION =
		contraption("gantry_contraption", GantryContraptionEntity::new, 10, 40, false);

	public static final EntityEntry<SuperGlueEntity> SUPER_GLUE = register("super_glue",
		SuperGlueEntity::new, SpawnGroup.MISC, 10, Integer.MAX_VALUE, false, SuperGlueEntity::build);
//	public static final RegistryEntry<EntityType<SeatEntity>> SEAT =
//		register("seat", SeatEntity::new, SpawnGroup.MISC, 0, Integer.MAX_VALUE, false, SeatEntity::build);

	//

	public static void register() {}

	private static <T extends Entity> RegistryEntry<EntityType<T>> contraption(String name, EntityFactory<T> factory,
		int range, int updateFrequency, boolean sendVelocity) {
		return register(name, factory, SpawnGroup.MISC, range, updateFrequency, sendVelocity,
			AbstractContraptionEntity::build);
	}

	private static <T extends Entity> EntityEntry<T> register(String name, EntityFactory<T> factory,
		SpawnGroup group, int range, int updateFrequency, boolean sendVelocity,
		NonNullConsumer<FabricEntityTypeBuilder<T>> propertyBuilder) {
		String id = Lang.asId(name);
		return Create.registrate()
			.entity(id, factory, group)
			.properties(b -> b.trackRangeChunks(range)
				.trackedUpdateRate(updateFrequency)
				.forceTrackedVelocityUpdates(sendVelocity))
			.properties(propertyBuilder)
			.register();
	}

	@Environment(EnvType.CLIENT)
	public static void registerRenderers() {
		EntityRendererRegistry.INSTANCE.register(CONTROLLED_CONTRAPTION.get(),
			ContraptionEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(ORIENTED_CONTRAPTION.get(),
			OrientedContraptionEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(GANTRY_CONTRAPTION.get(),
			ContraptionEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(SUPER_GLUE.get(), SuperGlueRenderer::new);
//		EntityRendererRegistry.INSTANCE.register(SEAT.get(), SeatEntity.Render::new);
	}
}

