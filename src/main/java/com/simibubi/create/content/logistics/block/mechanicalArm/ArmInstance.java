package com.simibubi.create.content.logistics.block.mechanicalArm;

import net.minecraft.block.entity.BlockEntityType;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.contraptions.base.RotatingData;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.foundation.render.backend.instancing.InstancedBlockRenderer;
import com.simibubi.create.foundation.render.backend.instancing.InstancedModel;
import com.simibubi.create.foundation.render.backend.instancing.InstancedTileRenderRegistry;

public class ArmInstance extends SingleRotatingInstance {
    public static void register(BlockEntityType<? extends KineticBlockEntity> type) {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			InstancedTileRenderRegistry.instance.register(type, ArmInstance::new);
    }

    public ArmInstance(InstancedBlockRenderer modelManager, KineticBlockEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected InstancedModel<RotatingData> getModel() {
        return AllBlockPartials.ARM_COG.renderOnRotating(modelManager, tile.getCachedState());
    }
}
