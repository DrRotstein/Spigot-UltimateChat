package com.drrotstein.cp.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.drrotstein.cp.helpers.ChatSettingsHelper;

public class InventoryClickHandler implements Listener {
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) throws Exception {
		if(e.getInventory() != null) {
			//Inventory inv = e.getInventory();
			
			
			if(e.getCurrentItem() != null) {
				ItemStack item = e.getCurrentItem();
				
				if(ChatSettingsHelper.itemMethods.containsKey(item.toString())) {
					e.setCancelled(true);
					ChatSettingsHelper.itemMethods.get(item.toString()).executeMethod((Player) e.getWhoClicked());
					
				}
			}
			
//			if(InventoryManager.unusableInvs.contains(inv)) {
//				e.setCancelled(true);
//				
//				
//				if(e.getCurrentItem() != null) {
//					ItemStack item = e.getCurrentItem();
//					
//					if(e.getWhoClicked() != null) {
//						Player player = (Player) e.getWhoClicked();
//						
//						
//						
//						
//						
//						if(item.toString().equals(ChatSettingsHelper.closeItem.toString())) player.closeInventory();
//						else if(item.toString().equals(ChatSettingsHelper.jqmSettingsItem.toString())) {
//							
//							ChatSettingsHelper.openJQM(player);
//							
//						} else if(item.toString().equals(ChatSettingsHelper.antispamSettingsItem.toString())) {
//							
//							
//							
//						} else if(item.toString().equals(ChatSettingsHelper.textformatEditorItem.toString())) {
//							
//							
//							
//						} else if(item.toString().equals(ChatSettingsHelper.backToCS.toString())) {
//							
//							ChatSettingsHelper.openCS(player);
//							
//						} else if(item.toString().equals(ChatSettingsHelper.joinmItem.toString())) {
//							
//							ChatSettingsHelper.openJQMEditor(player, "joinmessage");
//							
//						} else if(item.toString().equals(ChatSettingsHelper.quitmItem.toString())) {
//							
//							ChatSettingsHelper.openJQMEditor(player, "quitmessage");
//							
//						}
//					}
//				}
//			}
		}
	}
	
}
