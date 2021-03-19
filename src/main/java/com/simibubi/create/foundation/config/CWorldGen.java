package com.simibubi.create.foundation.config;

import com.simibubi.create.foundation.config.util.Validatable;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class CWorldGen implements Validatable {
	@Tooltip
	boolean disable = false; // "Prevents all worldgen added by Create from taking effect"

	@Tooltip
	@Limit(min = 0)
	int copperOreMinHeight = 40; // min 0,

	@Tooltip
	@Limit(min = 0)
	int copperOreMaxHeight = 85; // min 0,
	@Tooltip
	@Limit(min = 0)
	int copperOreClusterSize = 18; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float copperOreFrequency = 2.0f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.


	@Tooltip
	@Limit(min = 0)
	int weatheredLimestoneMinHeight = 10; // min 0,

	@Tooltip
	@Limit(min = 0)
	int weatheredLimestoneMaxHeight = 30; // min 0,
	@Tooltip
	@Limit(min = 0)
	int weatheredLimestoneClusterSize = 128; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float weatheredLimestoneFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.


	@Tooltip
	@Limit(min = 0)
	int zincOreMinHeight = 15; // min 0,

	@Tooltip
	@Limit(min = 0)
	int zincOreMaxHeight = 70; // min 0,
	@Tooltip
	@Limit(min = 0)
	int zincOreClusterSize = 14; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float zincOreFrequency = 4.0f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.


	@Tooltip
	@Limit(min = 0)
	int limestoneMinHeight = 30; // min 0,

	@Tooltip
	@Limit(min = 0)
	int limestoneMaxHeight = 70; // min 0,
	@Tooltip
	@Limit(min = 0)
	int limestoneClusterSize = 128; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float limestoneFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@Tooltip
	@Limit(min = 0)
	int dolomiteMinHeight = 20; // min 0,

	@Tooltip
	@Limit(min = 0)
	int dolomiteMaxHeight = 70; // min 0,
	@Tooltip
	@Limit(min = 0)
	int dolomiteClusterSize = 128; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float dolomiteFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.


	@Tooltip
	@Limit(min = 0)
	int gabbroMinHeight = 20; // min 0,

	@Tooltip
	@Limit(min = 0)
	int gabbroMaxHeight = 70; // min 0,
	@Tooltip
	@Limit(min = 0)
	int gabbroClusterSize = 128; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float gabbroFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@Tooltip
	@Limit(min = 0)
	int scoriaMinHeight = 0; // min 0,

	@Tooltip
	@Limit(min = 0)
	int scoriaMaxHeight = 10; // min 0,
	@Tooltip
	@Limit(min = 0)
	int scoriaClusterSize = 128; // min 0,
	@Tooltip
	@Limit(min = 0, max = 512)
	float scoriaFrequency = 0.03125f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	/**
	 * Awfully slow method that uses reflection to validate
	 * fields with the {@link Limit} annotation.
	 */
	@Override
	public void validate() throws ConfigData.ValidationException {
		for (Field f : getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(Limit.class)) {
				Limit l = f.getAnnotation(Limit.class);
				try {
					if (f.getType().equals(int.class)) {
						f.set(this, MathHelper.clamp((int) f.get(this), l.min(), l.max()));
					} else if (f.getType().equals(float.class)) {
						f.set(this, MathHelper.clamp((float) f.get(this), (float) l.min(), (float) l.max()));
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException("Could not reflectively access field!", e);
				}
			}
		}

		/*
		 * TODO Remove reflection
		 *
		 * int ensure0(int v) { return Math.max(v, 0); }
		 *
		 * copperOreMinHeight = ensure0(copperOreMinHeight);
		 * copperOreMaxHeight = ensure0(copperOreMaxHeight);
		 * copperOreClusterSize = ensure0(copperOreClusterSize);
		 * copperOreFrequency = MathHelper.clamp(copperOreFrequency, 0f, 512f);
		 * weatheredLimestoneMinHeight = ensure0(weatheredLimestoneMinHeight);
		 * weatheredLimestoneMaxHeight = ensure0(weatheredLimestoneMaxHeight);
		 * weatheredLimestoneClusterSize = ensure0(weatheredLimestoneClusterSize);
		 * weatheredLimestoneFrequency = MathHelper.clamp(weatheredLimestoneFrequency, 0f, 512f);
		 * zincOreMinHeight = ensure0(zincOreMinHeight);
		 */
	}

	/**
	 * Awful annotation to be used with reflection.
	 *
	 * @author YTG1234 and I'm not proud of that
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@ApiStatus.Internal
	private @interface Limit {
		int min() default Integer.MIN_VALUE;

		int max() default Integer.MAX_VALUE;
	}
}
