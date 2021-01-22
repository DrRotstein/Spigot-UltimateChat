package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.drrotstein.cp.helpers.ChatHelper;
import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;
import com.drrotstein.cp.helpers.decoding.DecodingHelper;

public class CommandPlayerlistFormat implements CommandExecutor, TabCompleter {
	
	private static final String ARG_SET = "set",
								ARG_GET = "get",
								ARG_RESET = "reset",
								ARG_HELP = "help";
	
	private static final String VARG_PLAYER = "player",
								VARG_RANKPREFIX = "rankprefix",
								VARG_GROUPPREFIX = "groupprefix",
								VARG_DEFAULT = "default";
	
	private static final String[] ARGS = {ARG_SET, ARG_GET, ARG_RESET, ARG_HELP};
	public static final String[] VARGS = {VARG_PLAYER, VARG_DEFAULT, VARG_RANKPREFIX, VARG_GROUPPREFIX};
	
	
	public static final String DEFAULT_PLAYERLISTFORMAT = "%player%";
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length == 1) {
			switch(args[0]) {
			case ARG_GET:
				String f = (String) ConfigHelper.load("uc.playerlistformat.value");
				if(f == null || f.equals("")) ChatHelper.cmd("playerlistformat reset");
				f = (String) ConfigHelper.load("uc.playerlistformat.value");
				sender.sendMessage("§aThe current playerlistformat is: §f" + f);
				break;
				
			case ARG_RESET:
				set(sender, new String[] {"", DEFAULT_PLAYERLISTFORMAT});
				break;
				
			case ARG_HELP:
				help(sender);
				break;
				
				
				default:
					sender.sendMessage("§c/playerlistformat help");
					return false;
			}
		} else if(args.length >= 2) {
			switch(args[0]) {
			case ARG_SET:
				set(sender, args);
				break;
				
				
				default:
					sender.sendMessage("§c/playerlistformat help");
					return false;
					
			}
		}
		return false;
	}
	
	private static void set(CommandSender sender, String[] args) {
		
		String format = DecodingHelper.setFormat(sender, DEFAULT_PLAYERLISTFORMAT, VArgAccessType.PLAYERLIST_FORMAT, args, 1);
		if(format == null || format.equals("")) return;
		
		ConfigHelper.save(format, "uc.playerlistformat.value");
		try {
			updateTablist();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void help(CommandSender sender) {
		sender.sendMessage("§eCommands: /playerlistformat or /plr");
		sender.sendMessage("§e/playerlistformat help: Shows this message");
		sender.sendMessage("§e/playerlistformat get: Shows the current playerlistformat");
		sender.sendMessage("§e/playerlistformat reset: Resets the playerlistformat");
		sender.sendMessage("§e/playerlistformat set <format>: Sets the playerlistformat");
	}
	
	public static void updateTablist() throws Exception {
		String format = (String) ConfigHelper.load("uc.playerlistformat.value");
		if(format == null || format.equals("")) ChatHelper.cmd("playerlistformat reset");
		
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setPlayerListName(DecodingHelper.getFormat(new Object[] {p}, VArgAccessType.PLAYERLIST_FORMAT, format));
		}
		
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		List<String> opt = new ArrayList<>();
		if(args.length == 1) {
			for(String arg : ARGS) opt.add(arg);
		}
		return opt;
	}

}
