package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CKinetics extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder kinetics = group(0, "kinetics", null, CServer.Comments.kinetics);
	public static final ConfigValue<Boolean> disableStress = b(false, "disableStress", kinetics, Comments.disableStress);
	public static final ConfigValue<Integer> maxBeltLength = i(20, 5, "maxBeltLength", kinetics, Comments.maxBeltLength);
	public static final ConfigValue<Integer> crushingDamage = i(4, 0, "crushingDamage", kinetics, Comments.crushingDamage);
	public static final ConfigValue<Integer> maxMotorSpeed = i(256, 64, "maxMotorSpeed", kinetics, Comments.rpm, Comments.maxMotorSpeed);
	public static final ConfigValue<Integer> waterWheelBaseSpeed = i(4, 1, "waterWheelBaseSpeed", kinetics, Comments.rpm, Comments.waterWheelBaseSpeed);
	public static final ConfigValue<Integer> waterWheelFlowSpeed = i(4, 1, "waterWheelFlowSpeed", kinetics, Comments.rpm, Comments.waterWheelFlowSpeed);
	public static final ConfigValue<Integer> furnaceEngineSpeed = i(16, 1, "furnaceEngineSpeed", kinetics, Comments.rpm, Comments.furnaceEngineSpeed);
	public static final ConfigValue<Integer> maxRotationSpeed = i(256, 64, "maxRotationSpeed", kinetics, Comments.rpm, Comments.maxRotationSpeed);
	public static final ConfigValue<DeployerAggroSetting> ignoreDeployerAttacks =
		e(DeployerAggroSetting.CREEPERS, "ignoreDeployerAttacks", kinetics, Comments.ignoreDeployerAttacks);
	public static final ConfigValue<Integer> kineticValidationFrequency =
		i(60, 5, "kineticValidationFrequency", kinetics, Comments.kineticValidationFrequency);
	public static final ConfigValue<Float> crankHungerMultiplier = f(.01f, 0, 1, "crankHungerMultiplier", kinetics, Comments.crankHungerMultiplier);
	public static final ConfigValue<Integer> minimumWindmillSails = i(8, 0, "minimumWindmillSails", kinetics, Comments.minimumWindmillSails);
	public static final ConfigValue<Integer> maxEjectorDistance = i(32, 0, "maxEjectorDistance", kinetics, Comments.maxEjectorDistance);
	public static final ConfigValue<Integer> ejectorScanInterval = i(120, 10, "ejectorScanInterval", kinetics, Comments.ejectorScanInterval);

	public static final CategoryBuilder fan = group(1, "encasedFan", kinetics, "Encased Fan");
	public static final ConfigValue<Integer> fanPushDistance = i(20, 5, "fanPushDistance", fan, Comments.fanPushDistance);
	public static final ConfigValue<Integer> fanPullDistance = i(20, 5, "fanPullDistance", fan, Comments.fanPullDistance);
	public static final ConfigValue<Integer> fanBlockCheckRate = i(30, 10, "fanBlockCheckRate", fan, Comments.fanBlockCheckRate);
	public static final ConfigValue<Integer> fanRotationArgmax = i(256, 64, "fanRotationArgmax", fan, Comments.rpm, Comments.fanRotationArgmax);
	public static final ConfigValue<Integer> generatingFanSpeed = i(4, 0, "generatingFanSpeed", fan, Comments.rpm, Comments.generatingFanSpeed);
	public static final ConfigValue<Integer> inWorldProcessingTime = i(150, 0, "inWorldProcessingTime", fan, Comments.inWorldProcessingTime);

	public static final CategoryBuilder contraptions = group(1, "contraptions", kinetics, "Moving Contraptions");
	public static final ConfigValue<Integer> maxBlocksMoved = i(2048, 1, "maxBlocksMoved", contraptions, Comments.maxBlocksMoved);
	public static final ConfigValue<Integer> maxChassisRange = i(16, 1, "maxChassisRange", contraptions, Comments.maxChassisRange);
	public static final ConfigValue<Integer> maxPistonPoles = i(64, 1, "maxPistonPoles", contraptions, Comments.maxPistonPoles);
	public static final ConfigValue<Integer> maxRopeLength = i(128, 1, "maxRopeLength", contraptions, Comments.maxRopeLength);
	public static final ConfigValue<Integer> maxCartCouplingLength = i(32, 1, "maxCartCouplingLength", contraptions, Comments.maxCartCouplingLength);

//	public CStress stressValues = nested(1, CStress::new, Comments.stress); // moved to bottom

	public static final CategoryBuilder state = group(1, "stats", kinetics, Comments.stats);
	public static final ConfigValue<Float> mediumSpeed = f(30, 0, 4096, "mediumSpeed", kinetics, Comments.rpm, Comments.mediumSpeed);
	public static final ConfigValue<Float> fastSpeed = f(100, 0, 65535, "fastSpeed", kinetics, Comments.rpm, Comments.fastSpeed);
	public static final ConfigValue<Float> mediumStressImpact =
		f(4, 0, 4096, "mediumStressImpact", kinetics, Comments.su, Comments.mediumStressImpact);
	public ConfigValue<Float> highStressImpact = f(8, 0, 65535, "highStressImpact", kinetics, Comments.su, Comments.highStressImpact);
	public static final ConfigValue<Float> mediumCapacity = f(128, 0, 4096, "mediumCapacity", kinetics, Comments.su, Comments.mediumCapacity);
	public static final ConfigValue<Float> highCapacity = f(512, 0, 65535, "highCapacity", kinetics, Comments.su, Comments.highCapacity);

	public CStress stressValues = new CStress();

	@Override
	public String getName() {
		return "kinetics";
	}

	private static class Comments {
		static String maxBeltLength = "Maximum length in blocks of mechanical belts.";
		static String crushingDamage = "Damage dealt by active Crushing Wheels.";
		static String maxMotorSpeed = "Maximum allowed speed of a configurable motor.";
		static String maxRotationSpeed = "Maximum allowed rotation speed for any Kinetic Tile.";
		static String fanPushDistance = "Maximum distance in blocks Fans can push entities.";
		static String fanPullDistance = "Maximum distance in blocks from where Fans can pull entities.";
		static String fanBlockCheckRate = "Game ticks between Fans checking for anything blocking their air flow.";
		static String fanRotationArgmax = "Rotation speed at which the maximum stats of fans are reached.";
		static String generatingFanSpeed = "Rotation speed generated by a vertical fan above fire.";
		static String inWorldProcessingTime = "Game ticks required for a Fan-based processing recipe to take effect.";
		static String crankHungerMultiplier =
			"multiplier used for calculating exhaustion from speed when a crank is turned.";
		static String maxBlocksMoved =
			"Maximum amount of blocks in a structure movable by Pistons, Bearings or other means.";
		static String maxChassisRange = "Maximum value of a chassis attachment range.";
		static String maxPistonPoles = "Maximum amount of extension poles behind a Mechanical Piston.";
		static String maxRopeLength = "Max length of rope available off a Rope Pulley.";
		static String maxCartCouplingLength = "Maximum allowed distance of two coupled minecarts.";
		static String stats = "Configure speed/capacity levels for requirements and indicators.";
		static String rpm = "[in Revolutions per Minute]";
		static String su = "[in Stress Units]";
		static String mediumSpeed = "Minimum speed of rotation to be considered 'medium'";
		static String fastSpeed = "Minimum speed of rotation to be considered 'fast'";
		static String mediumStressImpact = "Minimum stress impact to be considered 'medium'";
		static String highStressImpact = "Minimum stress impact to be considered 'high'";
		static String mediumCapacity = "Minimum added Capacity by sources to be considered 'medium'";
		static String highCapacity = "Minimum added Capacity by sources to be considered 'high'";
		static String stress = "Fine tune the kinetic stats of individual components";
		static String ignoreDeployerAttacks = "Select what mobs should ignore Deployers when attacked by them.";
		static String waterWheelBaseSpeed = "Added rotation speed by a water wheel when at least one flow is present.";
		static String waterWheelFlowSpeed =
			"Rotation speed gained by a water wheel for each side with running fluids. (halved if not against blades)";
		static String furnaceEngineSpeed = "Base rotation speed for the furnace engine generator";
		static String disableStress = "Disable the Stress mechanic altogether.";
		static String kineticValidationFrequency =
			"Game ticks between Kinetic Blocks checking whether their source is still valid.";
		static String minimumWindmillSails =
			"Amount of sail-type blocks required for a windmill to assemble successfully.";
		static String maxEjectorDistance = "Max Distance in blocks a Weighted Ejector can throw";
		static String ejectorScanInterval =
			"Time in ticks until the next item launched by an ejector scans blocks for potential collisions";
	}

	public static enum DeployerAggroSetting {
		ALL, CREEPERS, NONE
	}

}
