package com.simibubi.create.content.contraptions.base;

import net.minecraft.client.render.BufferBuilder;

import com.simibubi.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.simibubi.create.foundation.render.backend.instancing.InstancedBlockRenderer;
import com.simibubi.create.foundation.render.backend.instancing.InstancedModel;

public class RotatingInstancedModel extends InstancedModel<RotatingData> {
	public RotatingInstancedModel(InstancedBlockRenderer<?> renderer, BufferBuilder buf) {
		super(renderer, buf);
	}

	@Override
	protected RotatingData newInstance() {
		return new RotatingData(this);
	}

	@Override
	protected VertexFormat getInstanceFormat() {
		return RotatingData.FORMAT;
	}

}
