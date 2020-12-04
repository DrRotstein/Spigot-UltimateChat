package com.drrotstein.cp.helpers;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.drrotstein.cp.UltimateChat;
import com.drrotstein.cp.chateditor.ChatEditorManager;
import com.drrotstein.cp.chateditor.MethodParameter;
import com.drrotstein.cp.item.ItemBuilder;

public class ChatSettingsHelper {
	
	public static  HashMap<String, MethodParameter> itemMethods = new HashMap<>();
	
	
	public static ItemStack jqmSettingsItem = new ItemBuilder(Material.DARK_OAK_DOOR, "§aJoin-, Quitmessage", Arrays.asList("§7Manage the join- and quit messages"));
	public static ItemStack antispamSettingsItem = new ItemBuilder(Material.EMERALD, "§aAnti-Spam", Arrays.asList("§7Some anti-spam methods:", "§7-Repeatmessages", "§7-SlowMode"));
	public static ItemStack textformatEditorItem = new ItemBuilder(Material.NAME_TAG, "§bChat-format Editor", Arrays.asList("§7Edit the chatformat"));
	
	public static ItemStack joinmItem = new ItemBuilder(Material.JACK_O_LANTERN, "§aEdit Joinmessage", null);
	public static ItemStack quitmItem = new ItemBuilder(Material.CARVED_PUMPKIN, "§4Edit Quitmessage", null);
	
	
	public static ItemStack repeatMessagesItem = new ItemBuilder(Material.TRIPWIRE_HOOK, "§5Repeat-Messages", Arrays.asList("§7Allow players to type the same message twice"));
	
	public static ItemStack slowmodeItem = new ItemBuilder(Material.DAYLIGHT_DETECTOR, "§dSlowMode", Arrays.asList("§7Wait x seconds between messages"));
	
	
	public static ItemStack s10 = new ItemBuilder(Material.IRON_BLOCK, "10s", null);
	public static ItemStack s15 = new ItemBuilder(Material.GOLD_BLOCK, "§615s", null);
	public static ItemStack sOff = new ItemBuilder(Material.REDSTONE_BLOCK, "§4Off", null);
	public static ItemStack s30 = new ItemBuilder(Material.LAPIS_BLOCK, "§130s", null);
	public static ItemStack s45 = new ItemBuilder(Material.EMERALD_BLOCK, "§a45s", null);
	public static ItemStack s60 = new ItemBuilder(Material.OBSIDIAN, "§560s", null);
	public static ItemStack customSM = new ItemBuilder(Material.OAK_SIGN, "§4Custom", null);
	
	public static ItemStack rmOffItem = new ItemBuilder(Material.RED_CONCRETE, "§4Off", null);
	public static ItemStack rmOnItem = new ItemBuilder(Material.LIME_CONCRETE, "§aOn", null);
	
	
	
	public static ItemStack backToCS = new ItemBuilder(Material.ARROW, "§2Back", null);
	public static ItemStack closeItem = new ItemBuilder(Material.BARRIER, "§cClose", null);
	
	
	private static ChatSettingsHelper instance = new ChatSettingsHelper();
	
	
	private static String[] allItems = {closeItem.toString(), jqmSettingsItem.toString(), joinmItem.toString(), quitmItem.toString(),
			backToCS.toString(), antispamSettingsItem.toString(), textformatEditorItem.toString(), slowmodeItem.toString(), repeatMessagesItem.toString(),
			s10.toString(), s15.toString(), sOff.toString(), s30.toString(), s45.toString(), s60.toString(), customSM.toString(), rmOnItem.toString(), rmOffItem.toString()};
	
	public static void initializeItemMethods() {
		itemMethods.clear();
		
		MethodParameter closeMethod = new MethodParameter("close", new Class[] {Player.class}, new Object[] {null}, instance.getClass(), instance,
				0);
		MethodParameter joinmMethod = getDefaultMPWithString("openJQMEditor", "joinmessage"), quitmMethod = getDefaultMPWithString("openJQMEditor", "quitmessage");
		
		MethodParameter jqmSettingsMethod = getDefaultMP("openJQM"), backToCSMethod = getDefaultMP("openCS"), antispamMethod = getDefaultMP("openAntispam"),
				tfeMethod = getDefaultMP("openTextformatEditor"), slowmodeMethod = getDefaultMP("openSM"), rmMethod = getDefaultMP("openRM"),
				s5Method = getDefaultMPWithString("setSMTime", "10"), s10Method = getDefaultMPWithString("setSMTime", "15"),
				s15Method = getDefaultMPWithString("setSMTime", "0"), s30Method = getDefaultMPWithString("setSMTime", "30"),
				s45Method = getDefaultMPWithString("setSMTime", "45"), s60Method = getDefaultMPWithString("setSMTime", "60"),
				customSMMethod = getDefaultMP("openSMTimeEditor"), rmOffMethod = getDefaultMPWithString("setRM", "false"),
				rmOnMethod = getDefaultMPWithString("setRM", "true");
		
		MethodParameter[] allMethods = {closeMethod, jqmSettingsMethod, joinmMethod, quitmMethod, backToCSMethod, antispamMethod, tfeMethod, slowmodeMethod, rmMethod,
				s5Method, s10Method, s15Method, s30Method, s45Method, s60Method, customSMMethod, rmOffMethod, rmOnMethod};
		
		itemMethods = putAll(allItems, allMethods, itemMethods);
	}
	
	private static HashMap<String, MethodParameter> putAll(String[] keys, MethodParameter[] values, HashMap<String, MethodParameter> defaultMap){
		
		if(keys.length != values.length) throw new IllegalStateException("Can't put keys and values in HashMap, when they have different size");
		
		for(int i = 0; i < keys.length; i++) {
			defaultMap.put(keys[i], values[i]);
		}
		
		return defaultMap;
		
	}
	
	private static MethodParameter getDefaultMPWithString(String methodName, String added) {
		return new MethodParameter(methodName, new Class[] {Player.class, String.class}, new Object[] {null, added}, instance.getClass(), instance, 0);
	}
	
	private static MethodParameter getDefaultMP(String methodName) {
		return new MethodParameter(methodName, new Class[] {Player.class}, new Object[] {null}, instance.getClass(), instance, 0);
	}
	
	
	public static void openCS(Player player) {
		Inventory csInv = Bukkit.createInventory(null, 6 * 9, "§4§lChat-§8§lSettings");
		
		
		
		
		csInv.setItem(10, jqmSettingsItem);
		csInv.setItem(13, antispamSettingsItem);
		csInv.setItem(16, textformatEditorItem);
		
		csInv.setItem(49, closeItem);
		
		
		
		
		player.openInventory(csInv);
	}
	
	
	public static void openJQM(Player player) {
		
		
		Inventory jqmInv = Bukkit.createInventory(null, 3 * 9, "§aJoin-, Quitmessage");
		
		
		jqmInv.setItem(2, joinmItem);
		jqmInv.setItem(6, quitmItem);
		
		jqmInv.setItem(13, backToCS);
		jqmInv.setItem(22, closeItem);
		
		
		
		player.openInventory(jqmInv);
	}
	
	public static void openJQMEditor(Player player, String type) {
		
		player.closeInventory();
		player.sendMessage("§ePlease enter a join- or quitmessage. See /joinmessage help or /quitmessage help for more information!");
		
		String methodName = "";
		
		if(type.equals("joinmessage")) methodName = "setJoinmessage";
		else if(type.equals("quitmessage")) methodName = "setQuitmessage";
		
		ChatEditorManager.addEditingPlayer(player, new MethodParameter(methodName, new Class[] {Player.class, String.class}, new Object[] {player, null},
				instance.getClass(), instance, 1));
		
	}
	
	public static void openAntispam(Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§aAnti-Spam");
		
		inv.setItem(2, repeatMessagesItem);
		inv.setItem(6, slowmodeItem);
		
		
		inv.setItem(13, backToCS);
		inv.setItem(22, closeItem);
		
		
		player.openInventory(inv);
	}
	
	
	public static void openSM(Player player) {
		
		Inventory rmInv = Bukkit.createInventory(null, 3 * 9, "§dSlowMode");
		
		
		rmInv.setItem(1, s10);
		rmInv.setItem(2, s15);
		rmInv.setItem(3, s30);
		rmInv.setItem(4, s45);
		rmInv.setItem(5, s60);
		rmInv.setItem(6, sOff);
		rmInv.setItem(7, customSM);
		
		rmInv.setItem(13, backToCS);
		rmInv.setItem(22, closeItem);
		
		player.openInventory(rmInv);
		
	}
	
	public static void openRM(Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§5Repeat-Messages");
		
		inv.setItem(2, rmOnItem);
		inv.setItem(6, rmOffItem);
		
		
		inv.setItem(13, backToCS);
		inv.setItem(22, closeItem);
		
		player.openInventory(inv);
		
	}
	
	public static void setRM(Player player, String rm) {
		ChatHelper.cmdCustomSender(player, "repeatmessages allow " + rm);
	}
	
	public static void openTextformatEditor(Player player) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				player.closeInventory();
				player.sendMessage("§ePlease enter the chatformat you like!");
				ChatEditorManager.addEditingPlayer(player, new MethodParameter("setTextformat", new Class[] {Player.class, String.class},
						new Object[] {player, null}, instance.getClass(), instance, 1));
			}
		}.runTask(UltimateChat.getPlugin());
		
	}
	
	public static void setTextformat(Player player, String format) {
		ChatHelper.cmdForceMainThreadCustomSender(player, "chatformat set " + format);
	}
	
	public static void setSMTime(Player player, String time) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				int timeInS;
				
				try {
					timeInS = Integer.parseInt(time);
				} catch(NumberFormatException e) {
					player.sendMessage("§c" + time + " is not a valid time!");
					openSMTimeEditor(player);
					return;
				}
				
				ChatHelper.cmdForceMainThreadCustomSender(player, "slowmode time set " + timeInS);
				player.closeInventory();
			}
		}.runTask(UltimateChat.getPlugin());
		
	}
	
	public static void openSMTimeEditor(Player player) {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				player.closeInventory();
				player.sendMessage("§ePlease enter a time!");
				
				
				MethodParameter mte = new MethodParameter("setSMTime", new Class[] {Player.class, String.class}, new Object[] {player, null}, instance.getClass(), instance, 1);
				
				ChatEditorManager.addEditingPlayer(player, mte);
			}
		}.runTask(UltimateChat.getPlugin());
		
	}
	
	
}
