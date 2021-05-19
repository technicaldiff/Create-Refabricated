package com.simibubi.create.content.contraptions.components.deployer;

import java.util.Collection;
import java.util.OptionalInt;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.config.CKinetics;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.lib.helper.FakePlayerHelper;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DeployerFakePlayer extends ServerPlayerEntity {

	private static final NetworkManager NETWORK_MANAGER = new NetworkManager(PacketDirection.CLIENTBOUND);
	public static final GameProfile DEPLOYER_PROFILE =
		new GameProfile(UUID.fromString("9e2faded-cafe-4ec2-c314-dad129ae971d"), "Deployer");
	Pair<BlockPos, Float> blockBreakingProgress;
	ItemStack spawnedItemEffects;

	public DeployerFakePlayer(ServerWorld world) {
		super(world.getServer(), world, DEPLOYER_PROFILE, new PlayerInteractionManager(world));
		connection = new FakePlayNetHandler(world.getServer(), this);
		FakePlayerHelper.setFake(this, true);
	}

	@Override
	public OptionalInt openContainer(INamedContainerProvider container) {
		return OptionalInt.empty();
	}

	@Override
	public ITextComponent getDisplayName() {
		return Lang.translate("block.deployer.damage_source_name");
	}

	@Override
	@Environment(EnvType.CLIENT)
	public float getEyeHeight(Pose poseIn) {
		return 0;
	}

	@Override
	public Vector3d getPositionVec() {
		return new Vector3d(getX(), getY(), getZ());
	}

	@Override
	public float getCooldownPeriod() {
		return 1 / 64f;
	}

	@Override
	public boolean canEat(boolean ignoreHunger) {
		return false;
	}

	@Override
	public ItemStack onFoodEaten(World world, ItemStack stack) {
		stack.shrink(1);
		return stack;
	}

	public static int deployerHasEyesOnHisFeet(Entity entity) {
		if (entity instanceof DeployerFakePlayer)
			return 0;
		return -1;
	}

	public static boolean deployerCollectsDropsFromKilledEntities(DamageSource s, Collection<ItemEntity> drops) {
		if (!(s instanceof EntityDamageSource))
			return false;
		EntityDamageSource source = (EntityDamageSource) s;
		Entity trueSource = source.getTrueSource();
		if (trueSource != null && trueSource instanceof DeployerFakePlayer) {
			DeployerFakePlayer fakePlayer = (DeployerFakePlayer) trueSource;
			drops
				.forEach(stack -> fakePlayer.inventory.placeItemBackInInventory(trueSource.world, stack.getItem()));
			return true;
		}

		return false;
	}

	@Override
	protected void playEquipSound(ItemStack p_184606_1_) {}

	@Override
	public void remove() {
		if (blockBreakingProgress != null && !world.isRemote)
			world.sendBlockBreakProgress(getEntityId(), blockBreakingProgress.getKey(), -1);
		super.remove();
	}

	public static int deployerKillsDoNotSpawnXP(int i, PlayerEntity player) {
		if (player instanceof DeployerFakePlayer)
			return 0;
		return i;
	}

	public static void entitiesDontRetaliate(LivingEntity entityLiving, LivingEntity target) {
		if (!(target instanceof DeployerFakePlayer))
			return;
		if (!(entityLiving instanceof MobEntity))
			return;
		MobEntity mob = (MobEntity) entityLiving;

		CKinetics.DeployerAggroSetting setting = AllConfigs.SERVER.kinetics.ignoreDeployerAttacks.get();

		switch (setting) {
		case ALL:
			mob.setAttackTarget(null);
			break;
		case CREEPERS:
			if (mob instanceof CreeperEntity)
				mob.setAttackTarget(null);
			break;
		case NONE:
		default:
		}
	}

	private static class FakePlayNetHandler extends ServerPlayNetHandler {
		public FakePlayNetHandler(MinecraftServer server, ServerPlayerEntity playerIn) {
			super(server, NETWORK_MANAGER, playerIn);
		}

		@Override
		public void sendPacket(IPacket<?> packetIn) {}

		@Override
		public void sendPacket(IPacket<?> packetIn,
			GenericFutureListener<? extends Future<? super Void>> futureListeners) {}
	}
}
