package com.simibubi.create;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;

public enum AllKeys {

	TOOL_MENU("toolmenu", GLFW.GLFW_KEY_LEFT_ALT),
	// TODO maybe one day we can set things up so it doesn't overwrite the sprint keybind when on left ctrl
	ACTIVATE_TOOL("", GLFW.GLFW_KEY_RIGHT_CONTROL),

	;

	private KeyBinding keybind;
	private String description;
	private int key;
	private boolean modifiable;

	private AllKeys(String description, int defaultKey) {
		this.description = Create.ID + ".keyinfo." + description;
		this.key = defaultKey;
		this.modifiable = true;//!description.isEmpty(); // make ACTIVATE_TOOL rebindable to help with the fact it needed to be changed
	}

	public static void register() {
		for (AllKeys key : values()) {
			key.keybind = new KeyBinding(key.description, key.key, Create.NAME);
			if (!key.modifiable)
				continue;

			KeyBindingHelper.registerKeyBinding(key.keybind);
		}
	}

	public KeyBinding getKeybind() {
		return keybind;
	}

	public boolean isPressed() {
		if (!modifiable)
			return isKeyDown(key);
		return keybind.isKeyDown();
	}

	public String getBoundKey() {
		return keybind.getBoundKeyLocalizedText()
			.getString()
			.toUpperCase();
	}

	public int getBoundCode() {
		return KeyBindingHelper.getBoundKeyOf(keybind)
			.getKeyCode();
	}

	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(Minecraft.getInstance()
			.getWindow()
			.getHandle(), key) != 0;
	}

	public static boolean ctrlDown() {
		return Screen.hasControlDown();
	}

	public static boolean shiftDown() {
		return Screen.hasShiftDown();
	}

	public static boolean altDown() {
		return Screen.hasAltDown();
	}

}
