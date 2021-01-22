package com.drrotstein.cp.helpers.decoding;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.ChatHelper;
import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;

public class JQMHelper {
	
	
	private static final String ARG_HELP = "help";
	private static final String ARG_SET = "set";
	private static final String ARG_TOGGLE = "toggle";
	private static final String ARG_RESET = "reset";
	private static final String ARG_GET = "get";
	
	public static final String VARG_PLAYER = "player";
	public static final String VARG_RANKPREFIX = "rankprefix";
	public static final String VARG_GROUPPREFIX = "groupprefix";
	public static final String VARG_DEFAULT = "default";
	
	
	private static final String[] ARGS = {ARG_HELP, ARG_SET, ARG_TOGGLE, ARG_RESET, ARG_GET};
	public static final String[] VARGS = {VARG_PLAYER, VARG_DEFAULT, VARG_GROUPPREFIX, VARG_RANKPREFIX};
	
	
	public static final String DEFAULT_JOINMESSAGE = "§e%player% joined the game";
	public static final String DEFAULT_QUITMESSAGE = "§e%player% left the game";
	
	
	public static boolean cmd(CommandSender sender, String[] args, JQMType type) {
		if(args.length == 1) {
			switch(args[0]) {
			case ARG_HELP:
				help(sender, type);
				break;
				
			case ARG_SET:
				//This will display an error, because args.length has to be greater than 1
				sender.sendMessage("§c/" + type.toString().toLowerCase() + " set <message>");
				break;
				
			case ARG_TOGGLE:
				ConfigHelper.save(!((boolean) ConfigHelper.load("uc." + type.toString().toLowerCase() + ".ison")),
						"uc." + type.toString().toLowerCase() + ".ison");
				sender.sendMessage("§a" + type.toString().toLowerCase() + " enabled: " + (boolean) ConfigHelper.load("uc." + type.toString().
						toLowerCase() + ".ison"));
				break;
				
			case ARG_RESET:
				ChatHelper.cmdCustomSender(sender,  type.toString().toLowerCase() + " set %default%");
				break;
				
			case ARG_GET:
				if((boolean) ConfigHelper.load("uc." + type.toString().toLowerCase() + ".ison")) sender.sendMessage("§7The " + type.toString().toLowerCase() + " is turned: §aon");
				else {sender.sendMessage("§7The " + type.toString().toLowerCase() + " is turned: §coff"); break;}
				
				sender.sendMessage("§7" + ArrayHelper.firstCharUppercase(type.toString().toLowerCase()) +
						": §f" + ConfigHelper.load("uc." + type.toString().toLowerCase() + ".value"));
				
				break;
				
				default:
					sender.sendMessage(defaultSyntaxErr(type));
					break;
			}
			
			return false;
		} else if(args.length > 1) {
			switch(args[0]) {
			case ARG_SET:
				set(sender, type, args);
				break;
				
				
				default:
					sender.sendMessage(defaultSyntaxErr(type));
					break;
			}
			
			return false;
		} else {
			sender.sendMessage(defaultSyntaxErr(type));
		}
		
		return false;
	}
	
	private static String defaultSyntaxErr(JQMType type) {
		return "§cSyntax error! Use /" + type.toString().toLowerCase() + " help, to show the possible options";
	}
	
	private static void help(CommandSender sender, JQMType type) {
		String command = type.toString().toLowerCase();
		sender.sendMessage("§eCommands: /" + command);
		sender.sendMessage("§e/" + command + " help: Shows this message");
		sender.sendMessage("§e/" + command + " set <message>: Sets the " + command + " to <message>. It will automaticely enable " + command +
				", in case it is turned off. Use %player% to display the players name. Use & instead of §, to change the color.");
		sender.sendMessage("§e/" + command + " off: No " + command + " will be displayed");
		sender.sendMessage("§e/" + command +" reset: Resets the " + command + " to the default value");
		
	}
	
	
	private static void set(CommandSender sender, JQMType type, String[] args) {
		String mes;
		if(type == JQMType.JOINMESSAGE) mes = DecodingHelper.setFormat(sender, DEFAULT_JOINMESSAGE, VArgAccessType.JOIN_MESSAGE, args, 1);
		else mes = DecodingHelper.setFormat(sender, DEFAULT_QUITMESSAGE, VArgAccessType.QUIT_MESSAGE, args, 1);
		if(mes == null || mes.equals("")) return;
		
		ConfigHelper.save(true, "uc." + type.toString().toLowerCase() + ".ison");
		ConfigHelper.save(mes, "uc." + type.toString().toLowerCase() + ".value");
		
		
	}
	
	public static List<String> onTabComplete(String[] args) {
		List<String> opt = new ArrayList<>();
		if(args.length == 1) {
			for(String arg : ARGS) {
				opt.add(arg);
			}
			
			return opt;
		}
		
		return null;
	}
	
	
	
	
	
	
	public static String getJQMessage(Player p, JQMType type) throws Exception {
		if((boolean) ConfigHelper.load("uc." + type.toString().toLowerCase() + ".ison")) {
			String raw = (String) ConfigHelper.load("uc." + type.toString().toLowerCase() + ".value");
			VArgAccessType access = type == JQMType.JOINMESSAGE ? VArgAccessType.JOIN_MESSAGE : VArgAccessType.QUIT_MESSAGE;
			return DecodingHelper.getFormat(new Object[] {p}, access, raw);
		} else {
			return "";
		}
	}
	
	
}
