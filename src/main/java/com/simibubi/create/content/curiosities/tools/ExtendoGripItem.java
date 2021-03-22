package com.simibubi.create.content.curiosities.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import com.simibubi.create.AllItems;

//@EventBusSubscriber
public class ExtendoGripItem extends Item {
	private static DamageSource lastActiveDamageSource;

//	static Lazy<Multimap<EntityAttribute, EntityAttributeModifier>> rangeModifier = 
//		new Lazy<Multimap<EntityAttribute, EntityAttributeModifier>>(() -> 
//			// Holding an ExtendoGrip
//			ImmutableMultimap.of(
//				ForgeMod.REACH_DISTANCE.get(),
//				new EntityAttributeModifier(UUID.fromString("7f7dbdb2-0d0d-458a-aa40-ac7633691f66"), "Range modifier", 3,
//					EntityAttributeModifier.Operation.ADDITION))
//		);
//
//	static Lazy<Multimap<EntityAttribute, EntityAttributeModifier>> doubleRangeModifier = 
//		new Lazy<Multimap<EntityAttribute, EntityAttributeModifier>>(() -> 
//			// Holding two ExtendoGrips o.O
//			ImmutableMultimap.of(
//				ForgeMod.REACH_DISTANCE.get(),
//				new EntityAttributeModifier(UUID.fromString("8f7dbdb2-0d0d-458a-aa40-ac7633691f66"), "Range modifier", 5,
//					EntityAttributeModifier.Operation.ADDITION))
//		);

	public ExtendoGripItem(Settings properties) {
		super(properties.maxCount(1)
			.rarity(Rarity.UNCOMMON));
	}

	/*@SubscribeEvent
	public static void holdingExtendoGripIncreasesRange(LivingUpdateEvent event) {
		if (!(event.getEntity() instanceof PlayerEntity))
			return;

		PlayerEntity player = (PlayerEntity) event.getEntityLiving();
		String marker = "createExtendo";
		String dualMarker = "createDualExtendo";

		CompoundTag persistentData = player.getPersistentData();
		boolean inOff = AllItems.EXTENDO_GRIP == player.getOffHandStack().getItem();
		boolean inMain = AllItems.EXTENDO_GRIP == player.getMainHandStack().getItem();
		boolean holdingDualExtendo = inOff && inMain;
		boolean holdingExtendo = inOff ^ inMain;
		holdingExtendo &= !holdingDualExtendo;
		boolean wasHoldingExtendo = persistentData.contains(marker);
		boolean wasHoldingDualExtendo = persistentData.contains(dualMarker);

		if (holdingExtendo != wasHoldingExtendo) {
			if (!holdingExtendo) {
				player.getAttributes().removeModifiers(rangeModifier.get());
				persistentData.remove(marker);
			} else {
				if (player instanceof ServerPlayerEntity)
					AllTriggers.EXTENDO.trigger((ServerPlayerEntity) player);
				player.getAttributes()
					.addTemporaryModifiers(rangeModifier.get());
				persistentData.putBoolean(marker, true);
			}
		}

		if (holdingDualExtendo != wasHoldingDualExtendo) {
			if (!holdingDualExtendo) {
				player.getAttributes()
					.removeModifiers(doubleRangeModifier.get());
				persistentData.remove(dualMarker);
			} else {
				if (player instanceof ServerPlayerEntity)
					AllTriggers.GIGA_EXTENDO.trigger((ServerPlayerEntity) player);
				player.getAttributes()
					.addTemporaryModifiers(doubleRangeModifier.get());
				persistentData.putBoolean(dualMarker, true);
			}
		}

	}

	@SubscribeEvent
	@Environment(EnvType.CLIENT)
	public static void dontMissEntitiesWhenYouHaveHighReachDistance(ClickInputEvent event) {
		MinecraftClient mc = MinecraftClient.getInstance();
		ClientPlayerEntity player = mc.player;
		if (mc.world == null || player == null)
			return;
		if (!isHoldingExtendoGrip(player))
			return;
		if (mc.crosshairTarget instanceof BlockHitResult && mc.crosshairTarget.getType() != Type.MISS)
			return;

		// Modified version of GameRenderer#getMouseOver
		double d0 = player.getAttributeInstance(ForgeMod.REACH_DISTANCE.get())
			.getValue();
		if (!player.isCreative())
			d0 -= 0.5f;
		Vec3d Vector3d = player.getCameraPosVec(AnimationTickHolder.getPartialTicks());
		Vec3d Vector3d1 = player.getRotationVec(1.0F);
		Vec3d Vector3d2 = Vector3d.add(Vector3d1.x * d0, Vector3d1.y * d0, Vector3d1.z * d0);
		Box axisalignedbb = player.getBoundingBox()
			.stretch(Vector3d1.multiply(d0))
			.expand(1.0D, 1.0D, 1.0D);
		EntityHitResult entityraytraceresult =
			ProjectileUtil.raycast(player, Vector3d, Vector3d2, axisalignedbb, (e) -> {
				return !e.isSpectator() && e.collides();
			}, d0 * d0);
		if (entityraytraceresult != null) {
			Entity entity1 = entityraytraceresult.getEntity();
			Vec3d Vector3d3 = entityraytraceresult.getPos();
			double d2 = Vector3d.squaredDistanceTo(Vector3d3);
			if (d2 < d0 * d0 || mc.crosshairTarget == null || mc.crosshairTarget.getType() == Type.MISS) {
				mc.crosshairTarget = entityraytraceresult;
				if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrameEntity)
					mc.targetedEntity = entity1;
			}
		}
	}

	@SubscribeEvent
	public static void bufferLivingAttackEvent(LivingAttackEvent event) {
		// Workaround for removed patch to get the attacking entity. Tbf this is a hack and a half, but it should work.
		lastActiveDamageSource = event.getSource();
	}

	@SubscribeEvent
	public static void attacksByExtendoGripHaveMoreKnockback(LivingKnockBackEvent event) {
		if (lastActiveDamageSource == null)
			return;
		Entity entity = lastActiveDamageSource.getSource();
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		if (!isHoldingExtendoGrip(player))
			return;
		event.setStrength(event.getStrength() + 2);
	}*/

	private static boolean isUncaughtClientInteraction(Entity entity, Entity target) {
		// Server ignores entity interaction further than 6m
		if (entity.squaredDistanceTo(target) < 36)
			return false;
		if (!entity.world.isClient)
			return false;
		if (!(entity instanceof PlayerEntity))
			return false;
		return true;
	}

	/*@SubscribeEvent
	@Environment(EnvType.CLIENT)
	public static void notifyServerOfLongRangeAttacks(AttackEntityEvent event) {
		Entity entity = event.getEntity();
		Entity target = event.getTarget();
		if (!isUncaughtClientInteraction(entity, target))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		if (isHoldingExtendoGrip(player))
			AllPackets.channel.sendToServer(new ExtendoGripInteractionPacket(target));
	}

	@SubscribeEvent
	@Environment(EnvType.CLIENT)
	public static void notifyServerOfLongRangeInteractions(PlayerInteractEvent.EntityInteract event) {
		Entity entity = event.getEntity();
		Entity target = event.getTarget();
		if (!isUncaughtClientInteraction(entity, target))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		if (isHoldingExtendoGrip(player))
			AllPackets.channel.sendToServer(new ExtendoGripInteractionPacket(target, event.getHand()));
	}

	@SubscribeEvent
	@Environment(EnvType.CLIENT)
	public static void notifyServerOfLongRangeSpecificInteractions(PlayerInteractEvent.EntityInteractSpecific event) {
		Entity entity = event.getEntity();
		Entity target = event.getTarget();
		if (!isUncaughtClientInteraction(entity, target))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		if (isHoldingExtendoGrip(player))
			AllPackets.channel
				.sendToServer(new ExtendoGripInteractionPacket(target, event.getHand(), event.getLocalPos()));
	}*/

	public static boolean isHoldingExtendoGrip(PlayerEntity player) {
		boolean inOff = AllItems.EXTENDO_GRIP == player.getOffHandStack().getItem();
		boolean inMain = AllItems.EXTENDO_GRIP == player.getMainHandStack().getItem();
		boolean holdingGrip = inOff || inMain;
		return holdingGrip;
	}

}
