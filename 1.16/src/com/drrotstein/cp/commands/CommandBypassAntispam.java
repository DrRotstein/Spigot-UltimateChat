package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.drrotstein.cp.UltimateChat;

public class CommandBypassAntispam implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 1) {
				
				boolean flag;
				
				try {
					flag = Boolean.parseBoolean(args[0]);
				} catch(ClassCastException e) {
					player.sendMessage("§c/bypassantispam <true/false>");
					return false;
				}
				
				if(flag) {
					if(!UltimateChat.bypassAntispam.contains(player)) UltimateChat.bypassAntispam.add(player);
					else {
						player.sendMessage("§cYou are already bypassing the antispam!");
						return false;
					}
				} else {
					if(UltimateChat.bypassAntispam.contains(player)) UltimateChat.bypassAntispam.remove(player);
					else {
						player.sendMessage("§cYou are not bypassing the antispam yet!");
						return false;
					}
				}
				
			} else {
				player.sendMessage("§c/bypassantispam <true/false>");
			}
		} else {
			sender.sendMessage("§cOnly players can execute that command!");
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		List<String> opt = new ArrayList<>();
		
		if(args.length == 1) {
			opt.add("true");
			opt.add("false");
		}
		
		return opt;
	}
	
	
	
}
