package com.simibubi.create.foundation.block.render;

import net.minecraft.client.render.model.BakedModel;

public interface HiddenBakedModel {
	boolean isHidden();

	void setHidden(boolean hidden);

	static void withUnhidden(BakedModel model, Runnable runnable) {
		if (model instanceof HiddenBakedModel) {
			((HiddenBakedModel) model).setHidden(false);
		}
		runnable.run();
		if (model instanceof HiddenBakedModel) {
			((HiddenBakedModel) model).setHidden(true);
		}
	}
}
