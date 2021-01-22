package com.drrotstein.cp.eventhandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.drrotstein.cp.UltimateChat;
import com.drrotstein.cp.helpers.decoding.JQMHelper;
import com.drrotstein.cp.helpers.decoding.JQMType;

public class PlayerJoinQuitHandler implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) throws Exception {
		UltimateChat.updateImportantMethods();
		e.setJoinMessage(JQMHelper.getJQMessage(e.getPlayer(), JQMType.JOINMESSAGE));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) throws Exception {
		UltimateChat.updateImportantMethods();
		e.setQuitMessage(JQMHelper.getJQMessage(e.getPlayer(), JQMType.QUITMESSAGE));
	}
	
}
