// PORTED CREATE SOURCE

package com.simibubi.create.events;

import net.minecraft.client.MinecraftClient;

import com.simibubi.create.foundation.block.entity.behaviour.scrollvalue.ScrollValueHandler;
import com.simibubi.create.lib.event.MouseScrolledCallback;

public class InputEvents {

	public static void register() {
		MouseScrolledCallback.EVENT.register(InputEvents::onMouseScrolled);
	}

//	public static void onKeyInput(KeyInputEvent event) {
//		int key = event.getKey();
//		boolean pressed = !(event.getAction() == 0);
//
//		if (MinecraftClient.getInstance().currentScreen != null)
//			return;
//
//		CreateClient.schematicHandler.onKeyInput(key, pressed);
//	}

	public static boolean onMouseScrolled(double delta) {
		if (MinecraftClient.getInstance().currentScreen != null)
			return false;

//		CollisionDebugger.onScroll(delta);
		boolean cancelled = /*CreateClient.schematicHandler.mouseScrolled(delta)
			|| CreateClient.schematicAndQuillHandler.mouseScrolled(delta) || FilteringHandler.onScroll(delta)
			||*/ ScrollValueHandler.onScroll(delta);
		return cancelled;
	}

//	@SubscribeEvent
//	public static void onMouseInput(MouseInputEvent event) {
//		if (MinecraftClient.getInstance().currentScreen != null)
//			return;
//
//		int button = event.getButton();
//		boolean pressed = !(event.getAction() == 0);
//
//		CreateClient.schematicHandler.onMouseInput(button, pressed);
//		CreateClient.schematicAndQuillHandler.onMouseInput(button, pressed);
//	}

}
