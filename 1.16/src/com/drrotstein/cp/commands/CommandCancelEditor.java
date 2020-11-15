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
			ChatEditorManager.removePlayerFromEditing((Player) sender);
			((Player) sender).sendMessage("§aYou quit the chat-editor");
		} else {
			sender.sendMessage("§c/canceleditor. Pretty easy huh?");
		}
		return false;
	}

}
