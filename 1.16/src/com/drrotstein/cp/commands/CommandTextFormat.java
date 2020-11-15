package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.StringHelper;

public class CommandTextFormat implements CommandExecutor, TabCompleter {

	private static final String ARG_HELP = "help";
	private static final String ARG_OFF = "off";
	private static final String ARG_SET = "set";
	private static final String ARG_GET = "get";
	private static final String ARG_RESET = "reset";
	
	public static final String VARG_PLAYER = "player";
	public static final String VARG_MESSAGE = "message";
	public static final String VARG_DEFAULT = "default";
	
	private static final String[] ARGS = {ARG_HELP, ARG_OFF, ARG_SET, ARG_GET, ARG_RESET};
	public static final String[] VARGS = {VARG_PLAYER, VARG_MESSAGE, VARG_DEFAULT};
	
	public static final String DEFAULT_TEXTFORMAT = "<%player%> %message%";
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length == 1) {
			switch(args[0]) {
			case ARG_HELP:
				help(sender);
				break;
				
			case ARG_OFF:
				ConfigHelper.save(false, "uc.textformat.ison");
				sender.sendMessage("§aYou disabled chat!");
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
		String format = "";
		for(int i = 1; i < args.length; i++) {
			format += args[i] + " ";
		}
		
		for(String s : args) {
			
			if(ArrayHelper.containsStringChar(s, '%')) {
				int indexOfVargIndicator = s.indexOf('%');
				
				if(s.charAt(indexOfVargIndicator) == '%' && s.charAt(s.length() - 1) == '%') {
					String[] split = (s + "a").split("%");
					
					if(split.length != 3) {
						sender.sendMessage("§cYou are not allowed to use the variable-argument indicator '%' more or less than twice");
						return;
					}
					
					String varArg = split[1];
					
					switch(varArg) {
					case VARG_PLAYER: case VARG_MESSAGE:
						break;
						
					case VARG_DEFAULT:
						if(args.length == 2) {
							format = DEFAULT_TEXTFORMAT;
						} else {
							sender.sendMessage("§cYou may not type more than the variable-argument, using %default%");
							return;
						}
						break;
						
						
						
						default:
							String errmes = "§cThere are only those variable-arguments: ";
							for(int i = 0; i < VARGS.length; i++) { 
								if(i == VARGS.length - 1) {
									errmes += VARGS[i];
								} else {
									errmes += VARGS[i] + ", ";
								}
							}
							
							sender.sendMessage(errmes);
							return;
					}
				}
			}
		}
		
		format = StringHelper.replace(format, '&', '§');
		
		ConfigHelper.save(true, "uc.textformat.ison");
		ConfigHelper.save(format, "uc.textformat.value");
		
		sender.sendMessage("§aYou successfully set the chatformat to: §f" + ConfigHelper.load("uc.textformat.value"));
	}
	
	private static void help(CommandSender sender) {
		sender.sendMessage("§eCommands: /chatformat or /cf");
		sender.sendMessage("§e/chatformat help: Shows this page");
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