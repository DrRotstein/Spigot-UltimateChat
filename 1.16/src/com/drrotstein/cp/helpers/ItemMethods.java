package com.drrotstein.cp.helpers;

import org.bukkit.entity.Player;

public class ItemMethods {
	
	
	public static void close(Player player) {
		player.closeInventory();
	}
	
	
	public static void setJoinmessage(Player from, String jm) {
		ChatHelper.cmdForceMainThreadCustomSender(from, "joinmessage set " + jm);
		
	}
	public static void setQuitmessage(Player from, String qm) {
		ChatHelper.cmdForceMainThreadCustomSender(from, "quitmessage set " + qm);
	}
}
