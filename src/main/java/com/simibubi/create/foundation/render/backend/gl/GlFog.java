package com.simibubi.create.foundation.render.backend.gl;

import org.lwjgl.opengl.GL11;

import com.simibubi.create.lib.helper.GlStateManager$BooleanStateHelper;
import com.simibubi.create.lib.helper.GlStateManagerHelper;

public class GlFog {
    public static float[] FOG_COLOR = new float[] {0, 0, 0, 0};

    public static boolean fogEnabled() {
        return GlStateManager$BooleanStateHelper.getState(GlStateManagerHelper.getFOG().field_179049_a);
    }

    public static int getFogModeGlEnum() {
        return GlStateManagerHelper.getFOG().field_179047_b;
    }

    public static float getFogDensity() {
        return GlStateManagerHelper.getFOG().field_179048_c;
    }

    public static float getFogEnd() {
        return GlStateManagerHelper.getFOG().field_179046_e;
    }

    public static float getFogStart() {
        return GlStateManagerHelper.getFOG().field_179045_d;
    }

    public static GlFogMode getFogMode() {
        if (!fogEnabled()) {
            return GlFogMode.NONE;
        }

        int mode = getFogModeGlEnum();

        switch (mode) {
        case GL11.GL_EXP2:
        case GL11.GL_EXP:
            return GlFogMode.EXP2;
        case GL11.GL_LINEAR:
            return GlFogMode.LINEAR;
        default:
            throw new UnsupportedOperationException("Unknown fog mode: " + mode);
        }
    }
}
