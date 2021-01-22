package com.drrotstein.cp.helpers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.drrotstein.cp.UltimateChat;

public class ChatHelper {
	
	public static void cmd(String cmd) {
		cmdCustomSender(Bukkit.getConsoleSender(), cmd);
	}
	
	public static void cmdCustomSender(CommandSender sender, String cmd) {
		Bukkit.getServer().dispatchCommand(sender, cmd);
	}
	
	public static void cmdForceMainThread(String cmd) {
		cmdForceMainThreadCustomSender(Bukkit.getConsoleSender(), cmd);
	}
	
	
	public static void cmdForceMainThreadCustomSender(CommandSender sender, String cmd) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				cmdCustomSender(sender, cmd);
			}
		}.runTask(UltimateChat.getPlugin());
	}
}
