package com.drrotstein.cp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drrotstein.cp.chateditor.ChatEditorManager;

public class CommandCancelEditor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length == 0) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(ChatEditorManager.isPlayerInEditor(player)) {
					ChatEditorManager.removePlayerFromEditing(player);
					player.sendMessage("§aYou quit the chat-editor");
				} else {
					player.sendMessage("§cYou are not in a chat-editor");
				}
				
			} else {
				sender.sendMessage("§cOnly players can execute that command!");
			}
			
		} else {
			sender.sendMessage("§c/canceleditor. Pretty easy huh?");
		}
		return false;
	}

}
