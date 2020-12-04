package com.drrotstein.cp.eventhandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.drrotstein.cp.commands.CommandJoinMessage;
import com.drrotstein.cp.commands.CommandQuitMessage;
import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.ConfigHelper;

public class PlayerJoinQuitHandler implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if((boolean) ConfigHelper.load("uc.joinmessage.ison")) {
			
			String jm = "";
			
			String[] args = ((String) ConfigHelper.load("uc.joinmessage.value")).split(" ");
			
			for(String s : args) {
				String toAdd = "";
				if(ArrayHelper.containsStringChar(s, '%')) {
					int indexOfVargIndicator = s.indexOf('%');
					
					if(s.charAt(indexOfVargIndicator) == '%' && s.charAt(s.length() - 1) == '%') {
						String[] split = (s + "a").split("%");
						
						
						String varArg = split[1];
						
						switch(varArg) {
						case CommandJoinMessage.VARG_PLAYER:
							toAdd = split[0] + e.getPlayer().getDisplayName() + " ";
							break;
						}
						
					}
				} else {
					toAdd = s + " ";
				}
				
				jm += toAdd;
			}
			
			e.setJoinMessage(jm);

		} else {
			e.setJoinMessage("");
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if((boolean) ConfigHelper.load("uc.quitmessage.ison")) {
			
			String qm = "";
			
			String[] args = ((String) ConfigHelper.load("uc.quitmessage.value")).split(" ");
			
			for(String s : args) {
				String toAdd = "";
				if(ArrayHelper.containsStringChar(s, '%')) {
					int indexOfVargIndicator = s.indexOf('%');
					
					if(s.charAt(indexOfVargIndicator) == '%' && s.charAt(s.length() - 1) == '%') {
						String[] split = (s + "a").split("%");
						
						
						String varArg = split[1];
						
						switch(varArg) {
						case CommandQuitMessage.VARG_PLAYER:
							toAdd = split[0] + e.getPlayer().getDisplayName() + " ";
							break;
						}
						
					}
				} else {
					toAdd = s + " ";
				}
				
				qm += toAdd;
			}
			
			e.setQuitMessage(qm);

		} else {
			e.setQuitMessage("");
		}
	}
	
}
