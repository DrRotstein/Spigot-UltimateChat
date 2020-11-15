package com.drrotstein.cp.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder extends ItemStack {
	
	
	String displayname;
	List<String> lore;
	
	public ItemBuilder(Material mat, String displayname, List<String> lore) {
		super(mat);
		
		ItemMeta meta = getItemMeta();
		
		meta.setDisplayName(displayname);
		meta.setLore(lore);
		
		setItemMeta(meta);
		
		this.displayname = displayname;
		this.lore = lore;
	}
	
}
