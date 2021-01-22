package com.drrotstein.cp.helpers.addon.varg;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class VArgMethods {
	
	public static String player(Player player) {
		return player.getDisplayName();
	}
	
	public static String message(AsyncPlayerChatEvent e) {
		return e.getMessage();
	}
	
}
