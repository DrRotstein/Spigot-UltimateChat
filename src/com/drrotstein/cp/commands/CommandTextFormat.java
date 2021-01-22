package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;
import com.drrotstein.cp.helpers.decoding.DecodingHelper;

public class CommandTextFormat implements CommandExecutor, TabCompleter {

	private static final String ARG_HELP = "help";
	private static final String ARG_TOGGLE = "toggle";
	private static final String ARG_SET = "set";
	private static final String ARG_GET = "get";
	private static final String ARG_RESET = "reset";
	
	public static final String VARG_MESSAGE = "message";
	
	private static final String[] ARGS = {ARG_HELP, ARG_TOGGLE, ARG_SET, ARG_GET, ARG_RESET};
	
	public static final String DEFAULT_TEXTFORMAT = "<%player%> %message%";
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length == 1) {
			switch(args[0]) {
			case ARG_HELP:
				help(sender);
				break;
				
			case ARG_TOGGLE:
				ConfigHelper.save(!((boolean) ConfigHelper.load("uc.textformat.ison")), "uc.textformat.ison");
				sender.sendMessage("§aChat enabled: " + (boolean) ConfigHelper.load("uc.textformat.ison"));
				break;
				
			case ARG_GET:
				String curFor = (String) ConfigHelper.load("uc.textformat.value");
				sender.sendMessage("§aThe current textformat is: §f" + curFor);
				break;
				
				default:
					sender.sendMessage("§c/chatformat help, if you are having problems using this command");
					break;
			}
		} else if(args.length == 0) {
			sender.sendMessage("§c/chatformat help, if you are having problems using this command");
		} else {
			
			if(args[0].equals(ARG_SET)) {
				
				set(sender, args);
				
				
				
			}
			
		}
		return false;
	}
	
	
	private static void set(CommandSender sender, String[] args) {
		String format = DecodingHelper.setFormat(sender, DEFAULT_TEXTFORMAT, VArgAccessType.CHAT_FORMAT, args, 1);
		if(format == null || format.equals("")) return;
		
		ConfigHelper.save(true, "uc.textformat.ison");
		ConfigHelper.save(format, "uc.textformat.value");
	}
	
	private static void help(CommandSender sender) {
		sender.sendMessage("§eCommands: /chatformat or /cf");
		sender.sendMessage("§e/chatformat help: Shows this page");
		sender.sendMessage("§e/chatformat off: Turns off the chat");
		sender.sendMessage("§e/chatformat reset: Resets the chatformat");
		sender.sendMessage("§e/chatformat get: Get the current chatformat");
		sender.sendMessage("§e/chatformat set <format>: Set the chatformat");
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		
		List<String> opt = new ArrayList<String>();
		
		if(args.length == 1) {
			for(String arg : ARGS) {
				opt.add(arg);
			}
			
			return opt;
		}
		
		return null;
	}
	
	
	
}