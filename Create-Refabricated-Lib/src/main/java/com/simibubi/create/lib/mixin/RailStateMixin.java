package com.simibubi.create.lib.mixin;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;
import com.simibubi.create.lib.block.SlopeCreationCheckingRail;
import com.simibubi.create.lib.mixin.accessor.RailStateAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailState;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(value = RailState.class, priority = 1501) // bigger number is applied first right?
public abstract class RailStateMixin {
	// I hate everything about this file so much
	public boolean canMakeSlopes;
	@Final @Shadow private final World world = null;
	@Final @Shadow private final AbstractRailBlock block = null;
	@Final @Shadow private final BlockPos pos = null;
	@Final @Shadow private final boolean disableCorners = false;
	@Shadow private BlockState newState;
	@Shadow private boolean func_208512_d(BlockPos blockPos) {return false;}
	@Shadow private void reset(RailShape railShape) {}
	@Final @Shadow private final List<BlockPos> connectedRails = Lists.newArrayList();
	@Shadow @Nullable private RailState createForAdjacent(BlockPos blockPos) {return null;}
	@Shadow private boolean isConnectedTo(BlockPos blockPos) {return false;}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/RailState;reset(Lnet/minecraft/state/properties/RailShape;)V", shift = At.Shift.BEFORE),
			method = "Lnet/minecraft/block/RailState;<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V")
	public void RailState(World world, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
		canMakeSlopes = true;
		if (block instanceof SlopeCreationCheckingRail) {
			canMakeSlopes = ((SlopeCreationCheckingRail) block).canMakeSlopes(blockState, world, pos);
		}
	}

	/**
	 * In the event of catastrophic failure, look here first. <br>
	 * I don't see any other way to do a change like this. If you figure out another way, please fix it. <br>
	 * This is hopefully pretty much identical to the original, so ideally this won't conflict.
	 * @author Tropheus Jay
	 */
	@Overwrite
	private void func_208510_c(RailState railState) {
		this.connectedRails.add(((RailStateAccessor) railState).create$pos());
		BlockPos blockPos = this.pos.north();
		BlockPos blockPos2 = this.pos.south();
		BlockPos blockPos3 = this.pos.west();
		BlockPos blockPos4 = this.pos.east();
		boolean bl = this.isConnectedTo(blockPos);
		boolean bl2 = this.isConnectedTo(blockPos2);
		boolean bl3 = this.isConnectedTo(blockPos3);
		boolean bl4 = this.isConnectedTo(blockPos4);
		RailShape railShape = null;
		if (bl || bl2) {
			railShape = RailShape.NORTH_SOUTH;
		}

		if (bl3 || bl4) {
			railShape = RailShape.EAST_WEST;
		}

		if (!this.disableCorners) {
			if (bl2 && bl4 && !bl && !bl3) {
				railShape = RailShape.SOUTH_EAST;
			}

			if (bl2 && bl3 && !bl && !bl4) {
				railShape = RailShape.SOUTH_WEST;
			}

			if (bl && bl3 && !bl2 && !bl4) {
				railShape = RailShape.NORTH_WEST;
			}

			if (bl && bl4 && !bl2 && !bl3) {
				railShape = RailShape.NORTH_EAST;
			}
		}

		if (railShape == RailShape.NORTH_SOUTH && canMakeSlopes) {
			if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
				railShape = RailShape.ASCENDING_NORTH;
			}

			if (AbstractRailBlock.isRail(this.world, blockPos2.up())) {
				railShape = RailShape.ASCENDING_SOUTH;
			}
		}

		if (railShape == RailShape.EAST_WEST && canMakeSlopes) {
			if (AbstractRailBlock.isRail(this.world, blockPos4.up())) {
				railShape = RailShape.ASCENDING_EAST;
			}

			if (AbstractRailBlock.isRail(this.world, blockPos3.up())) {
				railShape = RailShape.ASCENDING_WEST;
			}
		}

		if (railShape == null) {
			railShape = RailShape.NORTH_SOUTH;
		}

		this.newState = (BlockState)this.newState.with(this.block.getShapeProperty(), railShape);
		this.world.setBlockState(this.pos, this.newState, 3);
	}

	/**
	 * See method above
	 * @author Tropheus Jay
	 */
	@Overwrite
	public RailState updateBlockState(boolean bl, boolean bl2, RailShape railShape) {
		BlockPos blockPos = this.pos.north();
		BlockPos blockPos2 = this.pos.south();
		BlockPos blockPos3 = this.pos.west();
		BlockPos blockPos4 = this.pos.east();
		boolean bl3 = this.func_208512_d(blockPos);
		boolean bl4 = this.func_208512_d(blockPos2);
		boolean bl5 = this.func_208512_d(blockPos3);
		boolean bl6 = this.func_208512_d(blockPos4);
		RailShape railShape2 = null;
		boolean bl7 = bl3 || bl4;
		boolean bl8 = bl5 || bl6;
		if (bl7 && !bl8) {
			railShape2 = RailShape.NORTH_SOUTH;
		}

		if (bl8 && !bl7) {
			railShape2 = RailShape.EAST_WEST;
		}

		boolean bl9 = bl4 && bl6;
		boolean bl10 = bl4 && bl5;
		boolean bl11 = bl3 && bl6;
		boolean bl12 = bl3 && bl5;
		if (!this.disableCorners) {
			if (bl9 && !bl3 && !bl5) {
				railShape2 = RailShape.SOUTH_EAST;
			}

			if (bl10 && !bl3 && !bl6) {
				railShape2 = RailShape.SOUTH_WEST;
			}

			if (bl12 && !bl4 && !bl6) {
				railShape2 = RailShape.NORTH_WEST;
			}

			if (bl11 && !bl4 && !bl5) {
				railShape2 = RailShape.NORTH_EAST;
			}
		}

		if (railShape2 == null) {
			if (bl7 && bl8) {
				railShape2 = railShape;
			} else if (bl7) {
				railShape2 = RailShape.NORTH_SOUTH;
			} else if (bl8) {
				railShape2 = RailShape.EAST_WEST;
			}

			if (!this.disableCorners) {
				if (bl) {
					if (bl9) {
						railShape2 = RailShape.SOUTH_EAST;
					}

					if (bl10) {
						railShape2 = RailShape.SOUTH_WEST;
					}

					if (bl11) {
						railShape2 = RailShape.NORTH_EAST;
					}

					if (bl12) {
						railShape2 = RailShape.NORTH_WEST;
					}
				} else {
					if (bl12) {
						railShape2 = RailShape.NORTH_WEST;
					}

					if (bl11) {
						railShape2 = RailShape.NORTH_EAST;
					}

					if (bl10) {
						railShape2 = RailShape.SOUTH_WEST;
					}

					if (bl9) {
						railShape2 = RailShape.SOUTH_EAST;
					}
				}
			}
		}

		if (railShape2 == RailShape.NORTH_SOUTH && canMakeSlopes) {
			if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
				railShape2 = RailShape.ASCENDING_NORTH;
			}

			if (AbstractRailBlock.isRail(this.world, blockPos2.up())) {
				railShape2 = RailShape.ASCENDING_SOUTH;
			}
		}

		if (railShape2 == RailShape.EAST_WEST && canMakeSlopes) {
			if (AbstractRailBlock.isRail(this.world, blockPos4.up())) {
				railShape2 = RailShape.ASCENDING_EAST;
			}

			if (AbstractRailBlock.isRail(this.world, blockPos3.up())) {
				railShape2 = RailShape.ASCENDING_WEST;
			}
		}

		if (railShape2 == null) {
			railShape2 = railShape;
		}

		this.reset(railShape2);
		this.newState = (BlockState)this.newState.with(this.block.getShapeProperty(), railShape2);
		if (bl2 || this.world.getBlockState(this.pos) != this.newState) {
			this.world.setBlockState(this.pos, this.newState, 3);

			for(int i = 0; i < this.connectedRails.size(); ++i) {
				RailState railState = this.createForAdjacent((BlockPos)this.connectedRails.get(i));
				if (railState != null) {
					((RailStateAccessor) railState).create$checkConnected();
					if (((RailStateAccessor) railState).create$func_196905_c(MixinHelper.cast(this))) {
						((RailStateAccessor) railState).create$func_196905_c(MixinHelper.cast(this));
					}
				}
			}
		}

		return MixinHelper.cast(this);
	}
}
