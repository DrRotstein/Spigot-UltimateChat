package com.drrotstein.cp.helpers;

import com.drrotstein.cp.UltimateChat;

public class ConfigHelper {
	
	public static void save(Object o, String path) {
		UltimateChat.getPlugin().getConfig().set(path, o);
		UltimateChat.getPlugin().saveConfig();
	}
	
	public static Object load(String path) {
		return UltimateChat.getPlugin().getConfig().get(path);
	}
	
}
