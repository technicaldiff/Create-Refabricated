package com.simibubi.create.content.curiosities.zapper;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.simibubi.create.lib.event.RenderHandCallback;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public abstract class ShootableGadgetRenderHandler {

	protected float leftHandAnimation;
	protected float rightHandAnimation;
	protected float lastLeftHandAnimation;
	protected float lastRightHandAnimation;
	protected boolean dontReequipLeft;
	protected boolean dontReequipRight;

	public void tick() {
		lastLeftHandAnimation = leftHandAnimation;
		lastRightHandAnimation = rightHandAnimation;
		leftHandAnimation *= animationDecay();
		rightHandAnimation *= animationDecay();
	}

	public float getAnimation(boolean rightHand, float partialTicks) {
		return MathHelper.lerp(partialTicks, rightHand ? lastRightHandAnimation : lastLeftHandAnimation,
			rightHand ? rightHandAnimation : leftHandAnimation);
	}

	protected float animationDecay() {
		return 0.8f;
	}

	public void shoot(Hand hand, Vector3d location) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		boolean rightHand = hand == Hand.MAIN_HAND ^ player.getPrimaryHand() == HandSide.LEFT;
		if (rightHand) {
			rightHandAnimation = .2f;
			dontReequipRight = false;
		} else {
			leftHandAnimation = .2f;
			dontReequipLeft = false;
		}
		playSound(hand, location);
	}

	protected abstract void playSound(Hand hand, Vector3d position);

	protected abstract boolean appliesTo(ItemStack stack);

	protected abstract void transformTool(MatrixStack ms, float flip, float equipProgress, float recoil, float pt);

	protected abstract void transformHand(MatrixStack ms, float flip, float equipProgress, float recoil, float pt);

	public void register() {
		RenderHandCallback.EVENT.register(this::onRenderPlayerHand);
	}

	public boolean onRenderPlayerHand(AbstractClientPlayerEntity client, Hand hand, ItemStack heldItem, MatrixStack ms, IRenderTypeBuffer vertexConsumers, float tickDelta, float pitch, float swingProgress, float equipProgress, int light) {
		if (!appliesTo(heldItem))
			return false;

		Minecraft mc = Minecraft.getInstance();
		AbstractClientPlayerEntity player = mc.player;
		TextureManager textureManager = mc.getTextureManager();
		PlayerRenderer playerrenderer = (PlayerRenderer) mc.getRenderManager()
			.getRenderer(player);
		FirstPersonRenderer firstPersonRenderer = mc.getFirstPersonRenderer();

		boolean rightHand = hand == Hand.MAIN_HAND ^ mc.player.getPrimaryHand() == HandSide.LEFT;
		float recoil = rightHand ? MathHelper.lerp(tickDelta, lastRightHandAnimation, rightHandAnimation)
			: MathHelper.lerp(tickDelta, lastLeftHandAnimation, leftHandAnimation);

		if (rightHand && (rightHandAnimation > .01f || dontReequipRight))
			equipProgress = 0;
		if (!rightHand && (leftHandAnimation > .01f || dontReequipLeft))
			equipProgress = 0;

		// Render arm
		ms.push();
		textureManager.bindTexture(player.getLocationSkin());

		float flip = rightHand ? 1.0F : -1.0F;
		float f1 = MathHelper.sqrt(swingProgress);
		float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
		float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
		float f4 = -0.4F * MathHelper.sin(swingProgress * (float) Math.PI);
		float f5 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
		float f6 = MathHelper.sin(f1 * (float) Math.PI);

		ms.translate(flip * (f2 + 0.64F - .1f), f3 + -0.4F + equipProgress * -0.6F, f4 + -0.72F + .3f + recoil);
		ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(flip * 75.0F));
		ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(flip * f6 * 70.0F));
		ms.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(flip * f5 * -20.0F));
		ms.translate(flip * -1.0F, 3.6F, 3.5F);
		ms.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(flip * 120.0F));
		ms.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(200.0F));
		ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(flip * -135.0F));
		ms.translate(flip * 5.6F, 0.0F, 0.0F);
		ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(flip * 40.0F));
		transformHand(ms, flip, equipProgress, recoil, tickDelta);
		if (rightHand)
			playerrenderer.renderRightArm(ms, vertexConsumers, light, player);
		else
			playerrenderer.renderLeftArm(ms, vertexConsumers, light, player);
		ms.pop();

		// Render gadget
		ms.push();
		ms.translate(flip * (f2 + 0.64F - .1f), f3 + -0.4F + equipProgress * -0.6F, f4 + -0.72F - 0.1f + recoil);
		ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(flip * f6 * 70.0F));
		ms.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(flip * f5 * -20.0F));
		transformTool(ms, flip, equipProgress, recoil, tickDelta);
		firstPersonRenderer.renderItem(mc.player, heldItem,
			rightHand ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
				: ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND,
			!rightHand, ms, vertexConsumers, light);
		ms.pop();

		return true;
	}

	public void dontAnimateItem(Hand hand) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		boolean rightHand = hand == Hand.MAIN_HAND ^ player.getPrimaryHand() == HandSide.LEFT;
		dontReequipRight |= rightHand;
		dontReequipLeft |= !rightHand;
	}

}
