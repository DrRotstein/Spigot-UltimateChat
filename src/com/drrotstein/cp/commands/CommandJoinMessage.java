package com.drrotstein.cp.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.drrotstein.cp.helpers.decoding.JQMHelper;
import com.drrotstein.cp.helpers.decoding.JQMType;

public class CommandJoinMessage implements CommandExecutor, TabCompleter {

	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		return JQMHelper.cmd(sender, args, JQMType.JOINMESSAGE);
	}
	

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		return JQMHelper.onTabComplete(args);
	}

}
