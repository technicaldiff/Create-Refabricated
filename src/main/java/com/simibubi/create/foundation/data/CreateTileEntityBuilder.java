package com.simibubi.create.foundation.data;

import javax.annotation.Nullable;

import com.simibubi.create.foundation.render.backend.instancing.IRendererFactory;
import com.simibubi.create.foundation.render.backend.instancing.InstancedTileRenderRegistry;
import com.simibubi.create.lib.event.InstanceRegistrationCallback;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.TileEntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CreateTileEntityBuilder<T extends TileEntity, P> extends TileEntityBuilder<T, P> implements InstanceRegistrationCallback {

	@Nullable
	private NonNullSupplier<IRendererFactory<? super T>> instanceFactory;

	public static <T extends TileEntity, P> TileEntityBuilder<T, P> create(AbstractRegistrate<?> owner, P parent,
		String name, BuilderCallback callback, NonNullFunction<TileEntityType<T>, ? extends T> factory) {
		return new CreateTileEntityBuilder<>(owner, parent, name, callback, factory);
	}

	protected CreateTileEntityBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
		NonNullFunction<TileEntityType<T>, ? extends T> factory) {
		super(owner, parent, name, callback, factory);
	}

    public CreateTileEntityBuilder<T, P> instance(NonNullSupplier<IRendererFactory<? super T>> instanceFactory) {
//		if (this.instanceFactory == null) { // fixme this is likely to be broken
//			EnvExecutor.runWhenOn(EnvType.CLIENT, () -> this::registerInstance);
//		}

		this.instanceFactory = instanceFactory;

		return this;
	}

	@Override
	public void registerInstance() {
//		OneTimeEventReceiver.addModListener(FMLClientSetupEvent.class, ($) -> {
			NonNullSupplier<IRendererFactory<? super T>> instanceFactory = this.instanceFactory;
			if (instanceFactory != null) {
				InstancedTileRenderRegistry.instance.register(getEntry(), instanceFactory.get());
			}

//		});
	}
}
