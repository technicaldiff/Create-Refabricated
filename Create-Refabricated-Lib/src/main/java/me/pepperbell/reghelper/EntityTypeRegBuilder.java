package me.pepperbell.reghelper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class EntityTypeRegBuilder<T extends Entity> {
	private final Identifier identifier;
	private final EntityFactory<T> factory;
	private final SpawnGroup spawnGroup;
	private Deque<Consumer<FabricEntityTypeBuilder<T>>> builderDeque = new ArrayDeque<>();
	private EntityRendererRegistry.Factory rendererFactory;
	private Deque<Consumer<EntityType<? super T>>> onRegister = new ArrayDeque<>();

	private EntityTypeRegBuilder(Identifier identifier, EntityFactory<T> factory, SpawnGroup spawnGroup) {
		this.identifier = identifier;
		this.factory = factory;
		this.spawnGroup = spawnGroup;
	}

	public Identifier getId() {
		return identifier;
	}

	public EntityTypeRegBuilder<T> properties(Consumer<FabricEntityTypeBuilder<T>> consumer) {
		builderDeque.add(consumer);
		return this;
	}

	public EntityTypeRegBuilder<T> renderer(Supplier<EntityRendererRegistry.Factory> factorySupplier) {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			rendererFactory = factorySupplier.get();
		}
		return this;
	}

	public EntityTypeRegBuilder<T> onRegister(Consumer<EntityType<? super T>> consumer) {
		onRegister.add(consumer);
		return this;
	}

	public EntityType<T> register() {
		FabricEntityTypeBuilder<T> builder = FabricEntityTypeBuilder.create(spawnGroup, factory);
		for (Consumer<FabricEntityTypeBuilder<T>> consumer : builderDeque) {
			consumer.accept(builder);
		}
		EntityType<T> type = builder.build();
		Registry.register(Registry.ENTITY_TYPE, identifier, type);
		if (rendererFactory != null) {
			EntityRendererRegistry.INSTANCE.register(type, rendererFactory);
		}
		for (Consumer<EntityType<? super T>> consumer : onRegister) {
			consumer.accept(type);
		}
		return type;
	}

	public static <T extends Entity> EntityTypeRegBuilder<T> create(Identifier identifier, EntityFactory<T> factory, SpawnGroup spawnGroup) {
		return new EntityTypeRegBuilder<T>(identifier, factory, spawnGroup);
	}
}
