package com.drrotstein.cp.eventhandlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.drrotstein.cp.chateditor.ChatEditorManager;
import com.drrotstein.cp.commands.CommandTextFormat;
import com.drrotstein.cp.countdown.SlowModeCountdown;
import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.ConfigHelper;

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
			
			String finalMessage = "";
			String[] formatArgs = ((String) ConfigHelper.load("uc.textformat.value")).split(" ");
			
			for(String s : formatArgs) {
				String toAdd = "";
				if(ArrayHelper.containsStringChar(s, '%')) {
					int indexOfVargIndicator = s.indexOf('%');
					
					if(s.charAt(indexOfVargIndicator) == '%' && s.charAt(s.length() - 1) == '%') {
						String[] split = (s + "a").split("%");
						
						
						String varArg = split[1];
						
						switch(varArg) {
						case CommandTextFormat.VARG_PLAYER:
							toAdd = split[0] + plr.getDisplayName() + " ";
							break;
							
						case CommandTextFormat.VARG_MESSAGE:
							toAdd = split[0] + e.getMessage();
							break;
						}
						
					}
				} else {
					toAdd = s + " ";
				}
				
				finalMessage += toAdd;
			}
			
			e.setFormat(finalMessage);
		} else {
			plr.sendMessage("§cCurrently, the chat is disabled to everyone");
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
