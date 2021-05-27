package com.simibubi.create.content.contraptions.components.structureMovement.glue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.utility.placement.IPlacementHelper;
import com.simibubi.create.foundation.utility.worldWrappers.RayTraceWorld;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SuperGlueHandler {

	public static Map<Direction, SuperGlueEntity> gatherGlue(IWorld world, BlockPos pos) {
		List<SuperGlueEntity> entities = world.getEntitiesWithinAABB(SuperGlueEntity.class, new AxisAlignedBB(pos));
		Map<Direction, SuperGlueEntity> map = new HashMap<>();
		for (SuperGlueEntity entity : entities)
			map.put(entity.getAttachedDirection(pos), entity);
		return map;
	}

	public static ActionResultType glueListensForBlockPlacement(ItemUseContext context) {
		IWorld world = context.getWorld();
		Entity entity = context.getPlayer();
		BlockPos pos = context.getPos();

		if (entity == null || world == null || pos == null)
			return ActionResultType.PASS;
		if (world.isRemote())
			return ActionResultType.PASS;

		Map<Direction, SuperGlueEntity> gatheredGlue = gatherGlue(world, pos);
		for (Direction direction : gatheredGlue.keySet())
			AllPackets.channel.sendToClientsTrackingAndSelf(
				new GlueEffectPacket(pos, direction, true), entity);

		if (entity instanceof PlayerEntity)
			return glueInOffHandAppliesOnBlockPlace(context.getWorld().getBlockState(context.getPos().offset(context.getFace().getOpposite())), pos, (PlayerEntity) entity);
		return ActionResultType.PASS;
	}

	public static ActionResultType glueInOffHandAppliesOnBlockPlace(BlockState placedAgainst, BlockPos pos, PlayerEntity placer) {
		ItemStack itemstack = placer.getHeldItemOffhand();
		ModifiableAttributeInstance reachAttribute = placer.getAttribute(ReachEntityAttributes.REACH);
		if (!AllItems.SUPER_GLUE.isIn(itemstack) || reachAttribute == null)
			return ActionResultType.PASS;
		if (AllItems.WRENCH.isIn(placer.getHeldItemMainhand()))
			return ActionResultType.PASS;
		if (placedAgainst == IPlacementHelper.ID)
			return ActionResultType.PASS;

		double distance = reachAttribute.getValue();
		Vector3d start = placer.getEyePosition(1);
		Vector3d look = placer.getLook(1);
		Vector3d end = start.add(look.x * distance, look.y * distance, look.z * distance);
		World world = placer.world;

		RayTraceWorld rayTraceWorld =
			new RayTraceWorld(world, (p, state) -> p.equals(pos) ? Blocks.AIR.getDefaultState() : state);
		BlockRayTraceResult ray = rayTraceWorld.rayTraceBlocks(
			new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, placer));

		Direction face = ray.getFace();
		if (face == null || ray.getType() == Type.MISS)
			return ActionResultType.PASS;

		if (!ray.getPos()
			.offset(face)
			.equals(pos)) {
			return ActionResultType.SUCCESS;
		}

		SuperGlueEntity entity = new SuperGlueEntity(world, ray.getPos(), face.getOpposite());
		CompoundNBT compoundnbt = itemstack.getTag();
		if (compoundnbt != null)
			EntityType.applyItemNBT(world, placer, entity, compoundnbt);

		if (entity.onValidSurface()) {
			if (!world.isRemote) {
				entity.playPlaceSound();
				world.addEntity(entity);
				AllPackets.channel.sendToClientsTracking(
					new GlueEffectPacket(ray.getPos(), face, true), entity);
			}
			itemstack.damageItem(1, placer, SuperGlueItem::onBroken);
		}
		return ActionResultType.PASS;
	}
}
