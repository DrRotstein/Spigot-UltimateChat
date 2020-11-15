package com.drrotstein.cp.chateditor;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ChatEditorManager {
	
	public static HashMap<Player, MethodParameter> playersInEditor = new HashMap<>();
	
	
	
	public static void addEditingPlayer(Player player, MethodParameter mte) {
		playersInEditor.put(player, mte);
	}
	
	
	public static Object playerTypeMessage(Player player, String mes) throws Exception {
		MethodParameter mte = playersInEditor.get(player);
		Object val = mte.executeMethod(mes);
		removePlayerFromEditing(player);
		return val;
	}
	
	public static boolean isPlayerInEditor(Player player) {
		return playersInEditor.containsKey(player);
	}
	
	
	public static void removePlayerFromEditing(Player player) {
		playersInEditor.remove(player);
	}
	
}
