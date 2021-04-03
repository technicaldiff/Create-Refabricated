// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.components.flywheel;

import java.util.function.Consumer;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticBlockInstance;
import com.simibubi.create.content.contraptions.base.Rotating;
import com.simibubi.create.content.contraptions.base.RotatingData;
import com.simibubi.create.foundation.render.backend.instancing.InstanceKey;
import com.simibubi.create.foundation.render.backend.instancing.InstancedBlockRenderer;
import com.simibubi.create.foundation.render.backend.instancing.InstancedModel;
import com.simibubi.create.foundation.render.backend.instancing.InstancedTileRenderRegistry;

public class FlyWheelInstance extends KineticBlockInstance<FlywheelBlockEntity> {
    public static void register(BlockEntityType<? extends FlywheelBlockEntity> type) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			InstancedTileRenderRegistry.instance.register(type, FlyWheelInstance::new);
    }

    protected Direction facing;

    protected InstanceKey<RotatingData> shaft;
//    protected InstanceKey<RotatingData> wheel; !! from upstream

    public FlyWheelInstance(InstancedBlockRenderer<?> modelManager, FlywheelBlockEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected void init() {
        facing = lastState.get(Properties.HORIZONTAL_FACING);

        Direction.Axis axis = ((Rotating) lastState.getBlock()).getRotationAxis(lastState);
        Consumer<RotatingData> setup = setupFunc(tile.getSpeed(), axis);
        shaft = shaftModel().setupInstance(setup);
//        wheel = wheelModel().setupInstance(setup); !! from upstream
    }

    @Override
    protected void onUpdate() {
        Direction.Axis axis = ((Rotating) lastState.getBlock()).getRotationAxis(lastState);
        updateRotation(shaft, axis);
//        updateRotation(wheel, axis); !! from upstream
    }

    @Override
    public void updateLight() {
        shaft.modifyInstance(this::relight);
//        wheel.modifyInstance(this::relight); !! from upstream
    }

    @Override
    public void remove() {
        shaft.delete();
        shaft = null;
//        wheel.delete(); !! from upstream
//        wheel = null; !! from upstream
    }

    protected InstancedModel<RotatingData> shaftModel() {
        return AllBlockPartials.SHAFT_HALF.renderOnDirectionalSouthRotating(modelManager, lastState, facing.getOpposite());
    }

    protected InstancedModel<RotatingData> wheelModel() {
        BlockState rotate = lastState.rotate(BlockRotation.CLOCKWISE_90);
        return AllBlockPartials.FLYWHEEL.renderOnDirectionalSouthRotating(modelManager, rotate, rotate.get(Properties.HORIZONTAL_FACING));
    }
}
