package com.drrotstein.cp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.drrotstein.cp.eventhandlers.PlayerChatHandler;
import com.drrotstein.cp.helpers.ConfigHelper;

public class CommandRepeatMessages implements CommandExecutor, TabCompleter {

	private static final String ARG_ALLOW = "allow";
	private static final String ARG_HELP = "help";
	
	private static final String[] ARGS = {ARG_ALLOW, ARG_HELP};
	
	
	private static final String DEFAULT_SYNTAX_ERR = "§cSyntax error! Use /repeatmessages help, to show the possible options";
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(args.length == 1) {
			switch(args[0]) {
			case ARG_ALLOW:
				sender.sendMessage("§c/repeatmessages allow <true/false>");
				break;
				
			case ARG_HELP:
				help(sender);
				break;
				
				default:
					sender.sendMessage(DEFAULT_SYNTAX_ERR);
					break;
			}
		} else if(args.length == 2) {
			switch(args[0]) {
			case ARG_ALLOW:
				if(args[1].equals("true") || args[1].equals("false")) {
					ConfigHelper.save(Boolean.parseBoolean(args[1]), "uc.repeatmessages.allow");
					if((boolean)ConfigHelper.load("uc.repeatmessages.allow")) {
						PlayerChatHandler.lastMessageOfPlayer.clear();
					}
					
					sender.sendMessage("§aYou set the repeatmessages rule to " + args[1]);
				} else {
					sender.sendMessage("§c/repeatmessages allow <true/false>");
				}
				
				break;
				
				
				default:
					sender.sendMessage(DEFAULT_SYNTAX_ERR);
					break;
			}
		} else {
			sender.sendMessage(DEFAULT_SYNTAX_ERR);
			return false;
		}
		
		return false;
	}
	
	private static void help(CommandSender sender) {
		sender.sendMessage("§eCommands: /repeatmessages or /rm");
		sender.sendMessage("§e/repeatmessages allow <true/false>: Allows or forbids all players to enter the same message twice in the chat (Antispam)");
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		List<String> opt = new ArrayList<>();
		if(args.length == 1) {
			for(String s : ARGS) {
				opt.add(s);
			}
			return opt;
		} else if(args.length == 2 && args[0].equals(ARG_ALLOW)) {
			opt.add("true");
			opt.add("false");
			return opt;
		}
		return null;
	}
	
}
