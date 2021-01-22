package com.drrotstein.cp.eventhandlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.drrotstein.cp.UltimateChat;
import com.drrotstein.cp.chateditor.ChatEditorManager;
import com.drrotstein.cp.commands.CommandTextFormat;
import com.drrotstein.cp.countdown.SlowModeCountdown;
import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;
import com.drrotstein.cp.helpers.decoding.DecodingHelper;

public class PlayerChatHandler implements Listener {
	
	public static final HashMap<Player, String> lastMessageOfPlayer = new HashMap<>();
	
	public static final ArrayList<SlowModeCountdown> smcs = new ArrayList<>();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) throws Exception {
		Player plr = e.getPlayer();
		
		//Chat-Editor
		if(ChatEditorManager.isPlayerInEditor(e.getPlayer())) {
			
			ChatEditorManager.playerTypeMessage(plr, e.getMessage());
			
			e.setCancelled(true);
			plr.sendMessage(e.getMessage());
			return;
		}
		
		
		
		//Textformat
		if((boolean) ConfigHelper.load("uc.textformat.ison")) {
			String raw = (String) ConfigHelper.load("uc.textformat.value");
			HashMap<String, String> adds = new HashMap<>();
			adds.put("message", e.getMessage());
			String finalMessage = DecodingHelper.getFormat(new Object[] {plr, e}, VArgAccessType.CHAT_FORMAT, raw);
			e.setFormat(finalMessage);
		} else {
			e.setCancelled(true);
			plr.sendMessage("§cCurrently, the chat is disabled to everyone");
			return;
		}
		
		//---Antispam
		boolean rmTriggered = false;
		boolean smTriggered = false;
		int smTime = -1;
		
		
		//SlowMode
		if((int) ConfigHelper.load("uc.slowmode.time") == 0) {
			e.setCancelled(false);
		} else {
			
			for(SlowModeCountdown scm : smcs) {
				if(scm.getPlayer() == plr && !scm.isFree()) {
					smTriggered = true;
				}
			}
			
			
		}		
		
		//Repeat-Messages
		if((boolean) ConfigHelper.load("uc.repeatmessages.allow")) {
			e.setCancelled(false);
		} else {
			if(lastMessageOfPlayer.containsKey(plr)) {
				
				
				String lastMes = lastMessageOfPlayer.get(plr);
				
				if(lastMes.equalsIgnoreCase(e.getMessage())) {
					rmTriggered = true;
				}
			}
		}
		
		//SlowMode
		if((int) ConfigHelper.load("uc.slowmode.time") == 0) {
			e.setCancelled(false);
		} else {
			
			
			for(SlowModeCountdown scm : smcs) {
				if(scm.getPlayer() == plr && !scm.isFree()) {
					smTriggered = true;
					smTime = scm.getSeconds();
				}
			}
		}		
		
		//Deciding
		if(!UltimateChat.bypassAntispam.contains(plr)) {
			if((rmTriggered && smTriggered) || (!rmTriggered && smTriggered)) {
				e.setCancelled(true);
				plr.sendMessage("§cSorry, you have to wait " + smTime + " more seconds before writing again. This is to protect the chat from spamming!");
				return;
			} else if(rmTriggered && !smTriggered) {
				e.setCancelled(true);
				plr.sendMessage("§cSorry, currently you are not allowed to type the same message twice. This is to protect the chat from spamming!");
				return;
			} else if(!rmTriggered && !smTriggered) {
				e.setCancelled(false);
				int time = (int) ConfigHelper.load("uc.slowmode.time");
				SlowModeCountdown scm = new SlowModeCountdown(plr, time);
				smcs.add(scm);
				scm.start();
				
				lastMessageOfPlayer.put(plr, e.getMessage());
			}
		}
		
		
	}
	
}
