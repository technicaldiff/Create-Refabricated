package com.simibubi.create.content.contraptions.components.actors;

import net.minecraft.client.render.BufferBuilder;

import com.simibubi.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.simibubi.create.foundation.render.backend.instancing.InstancedBlockRenderer;
import com.simibubi.create.foundation.render.backend.instancing.InstancedModel;

public class RotatingActorModel extends InstancedModel<ContraptionActorData> {
	public RotatingActorModel(InstancedBlockRenderer<?> renderer, BufferBuilder buf) {
		super(renderer, buf);
	}

	@Override
	protected VertexFormat getInstanceFormat() {
		return ContraptionActorData.FORMAT;
	}

	@Override
	protected ContraptionActorData newInstance() {
		return new ContraptionActorData(this);
	}
}
