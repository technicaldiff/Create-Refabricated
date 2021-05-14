package com.simibubi.create.lib.capabilities;

import java.util.concurrent.Callable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Throwables;
import com.simibubi.create.lib.utility.LazyOptional;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;

public class Capability<T> {
	private final String name;
	private final IStorage<T> storage;
	private final Callable<? extends T> factory;

	Capability(String name, IStorage<T> storage, Callable<? extends T> factory) {
		this.name = name;
		this.storage = storage;
		this.factory = factory;
	}

	public String getName() {
		return name;
	}

	public IStorage<T> getStorage() {
		return storage;
	}

	public void readNBT(T instance, Direction side, INBT nbt) {
		storage.readNBT(this, instance, side, nbt);
	}

	@Nullable
	public INBT writeNBT(T instance, Direction side) {
		return storage.writeNBT(this, instance, side);
	}

	@Nullable
	public T getDefaultInstance() {
		try {
			return this.factory.call();
		} catch (Exception e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	public @Nonnull <R> LazyOptional<R> orEmpty(Capability<R> toCheck, LazyOptional<T> inst) {
		return this == toCheck ? inst.cast() : LazyOptional.empty();
	}

	public interface IStorage<T> {
		@Nullable
		INBT writeNBT(Capability<T> capability, T instance, Direction side);

		void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt);
	}
}
