package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.ChatHelper;
import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.StringHelper;

public class CommandJoinMessage implements CommandExecutor, TabCompleter {

	
	private static final String ARG_HELP = "help";
	private static final String ARG_SET = "set";
	private static final String ARG_OFF = "off";
	private static final String ARG_RESET = "reset";
	private static final String ARG_GET = "get";
	
	public static final String VARG_PLAYER = "player";
	public static final String VARG_DEFAULT = "default";
	
	
	private static final String[] ARGS = {ARG_HELP, ARG_SET, ARG_OFF, ARG_RESET, ARG_GET};
	public static final String[] VARGS = {VARG_PLAYER, VARG_DEFAULT};
	
	
	public static final String DEFAULT_JOINMESSAGE = "§e%player% joined the game";
	
	
	private static final String DEFAULT_SYNTAX_ERR = "§cSyntax error! Use /joinmessage help, to show the possible options";
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(args.length == 1) {
			switch(args[0]) {
			case ARG_HELP:
				help(sender);
				break;
				
			case ARG_SET:
				//This will display an error, because args.length has to be greater than 1
				sender.sendMessage("§c/joinmessage set <message>");
				break;
				
			case ARG_OFF:
				ConfigHelper.save(false, "uc.joinmessage.ison");
				sender.sendMessage("§aYou turned off the joinmessage");
				break;
				
			case ARG_RESET:
				ChatHelper.cmdCustomSender(sender, "joinmessage set %default%");
				break;
				
			case ARG_GET:
				if((boolean) ConfigHelper.load("uc.joinmessage.ison")) sender.sendMessage("§7The joinmessage is turned: §aon");
				else {sender.sendMessage("§7The joinmessage is turned: §coff"); break;}
				
				sender.sendMessage("§7Joinmessage: §f" + ConfigHelper.load("uc.joinmessage.value"));
				
				break;
				
				default:
					sender.sendMessage(DEFAULT_SYNTAX_ERR);
					break;
			}
			
			return false;
		} else if(args.length > 1) {
			switch(args[0]) {
			case ARG_SET:
				set(sender, args);
				break;
				
				
				default:
					sender.sendMessage(DEFAULT_SYNTAX_ERR);
					break;
			}
			
			return false;
		} else {
			sender.sendMessage(DEFAULT_SYNTAX_ERR);
		}
		
		return false;
	}
	
	private void set(CommandSender sender, String[] args) {
		String mes = "";
		
		for(int i = 1; i < args.length; i++) {
			mes += (args[i] + " ");
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
					case VARG_PLAYER:
						break;
						
					case VARG_DEFAULT:
						if(args.length == 2) {
							mes = DEFAULT_JOINMESSAGE;
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
		
//		for(int i = 0; i < mes.length(); i++) {
//			if(mes.charAt(i) == '&') 
//		}
		
		mes = StringHelper.replace(mes, '&', '§');
		
		ConfigHelper.save(true, "uc.joinmessage.ison");
		ConfigHelper.save(mes, "uc.joinmessage.value");
		
		sender.sendMessage("§aYou successfully set the joinmessage to: §f" + ConfigHelper.load("uc.joinmessage.value"));
		
//		for(int i = 0; i < args.length - 1; i++) {
//			
//		}
		
	}
	
	private void help(CommandSender sender) {
		
		sender.sendMessage("§eCommands: /joinmessage or /jm");
		sender.sendMessage("§e/joinmessage help: Shows this message");
		sender.sendMessage("§e/joinmessage set <message>: Sets the joinmessage to <message>. It will automaticely enable joinmessage, in case joinmessage is turned off."
				+ " Use %player% to display the players name. Use & instead of §, to change the color.");
		sender.sendMessage("§e/joinmessage off: No joinmessage will be displayed");
		sender.sendMessage("§e/joinmessage reset: Resets the joinmessage to the default value");
		
	}
	

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		List<String> opt = new ArrayList<>();
		if(args.length == 1) {
			for(String arg : ARGS) {
				opt.add(arg);
			}
			
			return opt;
		}
		
		return null;
	}

}
