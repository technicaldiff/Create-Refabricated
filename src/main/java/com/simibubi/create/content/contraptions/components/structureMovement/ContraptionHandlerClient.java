package com.simibubi.create.content.contraptions.components.structureMovement;

import org.apache.commons.lang3.mutable.MutableObject;

import com.simibubi.create.content.contraptions.components.structureMovement.sync.ContraptionInteractionPacket;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.utility.RaycastHelper;
import com.simibubi.create.foundation.utility.RaycastHelper.PredicateTraceResult;
import com.simibubi.create.lib.helper.EntityHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.Template.BlockInfo;

public class ContraptionHandlerClient {

	@Environment(EnvType.CLIENT)
	public static void preventRemotePlayersWalkingAnimations(PlayerEntity player) {
//		if (event.phase == Phase.START)
//			return;
		if (!(player instanceof RemoteClientPlayerEntity))
			return;
		RemoteClientPlayerEntity remotePlayer = (RemoteClientPlayerEntity) player;
		CompoundNBT data = EntityHelper.getExtraCustomData(remotePlayer);
		if (!data.contains("LastOverrideLimbSwingUpdate"))
			return;

		int lastOverride = data.getInt("LastOverrideLimbSwingUpdate");
		data.putInt("LastOverrideLimbSwingUpdate", lastOverride + 1);
		if (lastOverride > 5) {
			data.remove("LastOverrideLimbSwingUpdate");
			data.remove("OverrideLimbSwing");
			return;
		}

		float limbSwing = data.getFloat("OverrideLimbSwing");
		remotePlayer.prevPosX = remotePlayer.getX() - (limbSwing / 4);
		remotePlayer.prevPosZ = remotePlayer.getZ();
	}

	@Environment(EnvType.CLIENT)
	public static ActionResultType rightClickingOnContraptionsGetsHandledLocally(PlayerEntity player, World world, Hand hand, BlockRayTraceResult hitResult) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity clientPlayer = mc.player;
		if (player == null)
			return ActionResultType.PASS;
		if (player.isPassenger())
			return ActionResultType.PASS;
		if (mc.world == null)
			return ActionResultType.PASS;
//		if (!event.isUseItem())
//			return;
		Vector3d origin = RaycastHelper.getTraceOrigin(player);

		double reach = mc.playerController.getBlockReachDistance();
		if (mc.objectMouseOver != null && mc.objectMouseOver.getHitVec() != null)
			reach = Math.min(mc.objectMouseOver.getHitVec()
				.distanceTo(origin), reach);

		Vector3d target = RaycastHelper.getTraceTarget(player, reach, origin);
		for (AbstractContraptionEntity contraptionEntity : mc.world
			.getEntitiesWithinAABB(AbstractContraptionEntity.class, new AxisAlignedBB(origin, target))) {

			Vector3d localOrigin = contraptionEntity.toLocalVector(origin, 1);
			Vector3d localTarget = contraptionEntity.toLocalVector(target, 1);
			Contraption contraption = contraptionEntity.getContraption();

			MutableObject<BlockRayTraceResult> mutableResult = new MutableObject<>();
			PredicateTraceResult predicateResult = RaycastHelper.rayTraceUntil(localOrigin, localTarget, p -> {
				BlockInfo blockInfo = contraption.getBlocks()
					.get(p);
				if (blockInfo == null)
					return false;
				BlockState state = blockInfo.state;
				VoxelShape raytraceShape = state.getShape(Minecraft.getInstance().world, BlockPos.ZERO.down());
				if (raytraceShape.isEmpty())
					return false;
				BlockRayTraceResult rayTrace = raytraceShape.rayTrace(localOrigin, localTarget, p);
				if (rayTrace != null) {
					mutableResult.setValue(rayTrace);
					return true;
				}
				return false;
			});

			if (predicateResult == null || predicateResult.missed())
				return ActionResultType.PASS;

			BlockRayTraceResult rayTraceResult = mutableResult.getValue();
//			Hand hand = event.getHand();
			Direction face = rayTraceResult.getFace();
			BlockPos pos = rayTraceResult.getPos();

			if (!contraptionEntity.handlePlayerInteraction(player, pos, face, hand))
				return ActionResultType.PASS;
			AllPackets.channel.sendToServer(new ContraptionInteractionPacket(contraptionEntity, hand, pos, face));
//			event.setCanceled(true);
//			event.setSwingHand(false);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}
