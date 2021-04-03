// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.components.structureMovement.piston;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionLighter;
import com.simibubi.create.foundation.render.backend.light.GridAlignedBB;

public class PistonLighter extends ContraptionLighter<PistonContraption> {
    public PistonLighter(PistonContraption contraption) {
        super(contraption);
    }

    @Override
    public GridAlignedBB getContraptionBounds() {
        GridAlignedBB bounds = GridAlignedBB.fromAABB(contraption.bounds);
        bounds.translate(new Vec3d(contraption.anchor.getX(), contraption.anchor.getY(), contraption.anchor.getZ()));

        int length = contraption.extensionLength;
        Vec3i direction = contraption.orientation.getVector();

        int shift = length / 2;
        int shiftX = direction.getX() * shift;
        int shiftY = direction.getY() * shift;
        int shiftZ = direction.getZ() * shift;
        bounds.translate(shiftX, shiftY, shiftZ);

        int grow = (length + 1) / 2;
        int extendX = Math.abs(direction.getX() * grow);
        int extendY = Math.abs(direction.getY() * grow);
        int extendZ = Math.abs(direction.getZ() * grow);
        bounds.grow(extendX, extendY, extendZ);

        return bounds;
    }
}
