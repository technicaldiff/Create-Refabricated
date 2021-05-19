package com.simibubi.create.lib.extensions;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.simibubi.create.lib.mixin.accessor.TemplateAccessor;

import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public interface TemplateExtensions {
	List<Template.EntityInfo> create$getEntities();
	default Vector3d transformedVec3d(PlacementSettings placementIn, Vector3d pos) {
		return Template.getTransformedPos(pos, placementIn.getMirror(), placementIn.getRotation(), placementIn.getCenterOffset());
	}

	default List<Template.EntityInfo> create$processEntityInfos(@Nullable Template template, IWorld world, BlockPos blockPos, PlacementSettings settings, List<Template.EntityInfo> infos) {
		List<Template.EntityInfo> list = Lists.newArrayList();
		for(Template.EntityInfo entityInfo : infos) {
			Vector3d pos = transformedVec3d(settings, entityInfo.pos).add(Vector3d.of(blockPos));
			BlockPos blockpos = Template.transformedBlockPos(settings, entityInfo.blockPos).add(blockPos);
			Template.EntityInfo info = new Template.EntityInfo(pos, blockpos, entityInfo.nbt);
			for (StructureProcessor proc : settings.getProcessors()) {
				info = ((StructureProcessorExtensions) proc).processEntity(world, blockPos, entityInfo, info, settings, template);
				if (info == null)
					break;
			}
			if (info != null)
				list.add(info);
		}
		return list;
	}

	default void create$addEntitiesToWorld(IServerWorld world, BlockPos blockPos, PlacementSettings settings) {
		for(Template.EntityInfo template$entityinfo : create$processEntityInfos((Template) this, world, blockPos, settings, this.create$getEntities())) {
			BlockPos blockpos = Template.getTransformedPos(template$entityinfo.blockPos, settings.getMirror(), settings.getRotation(), settings.getCenterOffset()).add(blockPos);
			blockpos = template$entityinfo.blockPos;
			if (settings.getBoundingBox() == null || settings.getBoundingBox().isVecInside(blockpos)) {
				CompoundNBT compoundnbt = template$entityinfo.nbt.copy();
				Vector3d vector3d1 = template$entityinfo.pos;
				ListNBT listnbt = new ListNBT();
				listnbt.add(DoubleNBT.of(vector3d1.x));
				listnbt.add(DoubleNBT.of(vector3d1.y));
				listnbt.add(DoubleNBT.of(vector3d1.z));
				compoundnbt.put("Pos", listnbt);
				compoundnbt.remove("UUID");
				TemplateAccessor.loadEntity(world, compoundnbt).ifPresent((entity) -> {
					float f = entity.getMirroredYaw(settings.getMirror());
					f = f + (entity.rotationYaw - entity.getRotatedYaw(settings.getRotation()));
					entity.setLocationAndAngles(vector3d1.x, vector3d1.y, vector3d1.z, f, entity.rotationPitch);
					if (settings.method_27265() && entity instanceof MobEntity) {
						((MobEntity) entity).onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(vector3d1)), SpawnReason.STRUCTURE, (ILivingEntityData)null, compoundnbt);
					}

					world.spawnEntityAndPassengers(entity);
				});
			}
		}

	}
}
