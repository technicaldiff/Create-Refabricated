package com.simibubi.create.events;

import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.tileEntity.behaviour.filtering.FilteringHandler;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueHandler;
import com.simibubi.create.lib.event.KeyInputCallback;
import com.simibubi.create.lib.event.MouseButtonCallback;
import com.simibubi.create.lib.event.MouseScrolledCallback;

import net.minecraft.client.Minecraft;

public class InputEvents {

	public static void onKeyInput(int key, int scancode, int action, int mods) {
		boolean pressed = action != 0;

		if (Minecraft.getInstance().currentScreen != null)
			return;

		CreateClient.schematicHandler.onKeyInput(key, pressed);
	}

	public static boolean onMouseScrolled(double delta) {
		if (Minecraft.getInstance().currentScreen != null)
			return false;

		// upstream comment
//		CollisionDebugger.onScroll(delta);
		boolean cancelled = CreateClient.schematicHandler.mouseScrolled(delta)
			|| CreateClient.schematicAndQuillHandler.mouseScrolled(delta) || FilteringHandler.onScroll(delta)
			|| ScrollValueHandler.onScroll(delta);
		return cancelled;
	}

	public static void onMouseInput(int button, int action, int mods) {
		if (Minecraft.getInstance().currentScreen != null)
			return;

		boolean pressed = action != 0;

		CreateClient.schematicHandler.onMouseInput(button, pressed);
		CreateClient.schematicAndQuillHandler.onMouseInput(button, pressed);
	}

	public static void register() {
		KeyInputCallback.EVENT.register(InputEvents::onKeyInput);
		MouseScrolledCallback.EVENT.register(InputEvents::onMouseScrolled);
		MouseButtonCallback.EVENT.register(InputEvents::onMouseInput);
	}

}
