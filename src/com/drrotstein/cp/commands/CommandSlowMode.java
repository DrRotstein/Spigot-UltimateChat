package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.drrotstein.cp.helpers.ConfigHelper;

public class CommandSlowMode implements CommandExecutor, TabCompleter {

	private static final String ARG_TIME = "time";
	private static final String ARG_TIME_SET = "set";
	private static final String ARG_TIME_GET = "get";
	private static final String ARG_HELP = "help";
	
	private static final String[] ARGS = {ARG_TIME, ARG_HELP};
	private static final String[] TIME_SUBARGS = {ARG_TIME_SET, ARG_TIME_GET};
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length == 3) {
			switch(args[0]) {
			case ARG_TIME:
				
				switch(args[1]) {
				case ARG_TIME_SET:
					int value = 0;
					try {
						value = Integer.parseInt(args[2]);
					} catch(NumberFormatException e) {
						sender.sendMessage("§c" + args[2] + " is not a valid number");
						return false;
					}
					
					if(value < 0 || value > 60) {sender.sendMessage("§cThe value has to be between 0 and 60, where 0 turns the slowmode off"); return false;}
					
					ConfigHelper.save(value, "uc.slowmode.time");
					sender.sendMessage("§aYou set the slowmode time to " + value + " second(s)");
					
					break;
					
					
					default:
						sender.sendMessage("§c/slowmode time <set/get>");
						break;
				}
				
				
				break;
				
				
				default:
					sender.sendMessage("§cUse /slowmode help, to get the valid syntax!");
					break;
			}
		} else if(args.length == 1) {
			switch(args[0]) {
			case ARG_HELP:
				help(sender);
				break;
				
			case ARG_TIME_GET:
				sender.sendMessage("§aThe current slowmode time is: " + ConfigHelper.load("uc.slowmode.time"));
				break;
				
				default:
					sender.sendMessage("§cUse /slowmode help, to get the valid syntax!");
					break;
			}
		} else {
			sender.sendMessage("§cUse /slowmode help, to get the valid syntax!");
		}
		return false;
	}
	
	
	private static void help(CommandSender sender) {
		sender.sendMessage("§eCommands: /slowmode or /sm");
		sender.sendMessage("§e/slowmode time set <value>: Set the time in seconds, that players have to wait before typing a new message. Use 0 to disable the slowmode");
		sender.sendMessage("§e/slowmode time get: Gives you the value of the slowmode-time");
		sender.sendMessage("§e/slowmode time help: Gets you this message");
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		List<String> opt = new ArrayList<>();
		
		if(args.length == 1) {
			for(String arg : ARGS) {
				opt.add(arg);
			}
			
			return opt;
		} else if(args.length == 2 && args[0].equals(ARG_TIME)) {
			for(String arg : TIME_SUBARGS) {
				opt.add(arg);
			}
			
			return opt;
		}
		
		return null;
	}

}
