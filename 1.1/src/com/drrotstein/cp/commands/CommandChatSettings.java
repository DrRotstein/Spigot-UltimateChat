package com.drrotstein.cp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drrotstein.cp.helpers.ChatSettingsHelper;

public class CommandChatSettings implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length == 0) {
				
				ChatSettingsHelper.openCS(player);
				
			} else {
				player.sendMessage("§c/chatsettings, not more...");
			}
		} else {
			sender.sendMessage("§cOnly players can use that command!");
		}
		return false;
	}

}
