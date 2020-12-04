package com.drrotstein.cp;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.drrotstein.cp.commands.CommandBypassAntispam;
import com.drrotstein.cp.commands.CommandCancelEditor;
import com.drrotstein.cp.commands.CommandChatSettings;
import com.drrotstein.cp.commands.CommandJoinMessage;
import com.drrotstein.cp.commands.CommandQuitMessage;
import com.drrotstein.cp.commands.CommandRepeatMessages;
import com.drrotstein.cp.commands.CommandSlowMode;
import com.drrotstein.cp.commands.CommandTextFormat;
import com.drrotstein.cp.eventhandlers.InventoryClickHandler;
import com.drrotstein.cp.eventhandlers.PlayerChatHandler;
import com.drrotstein.cp.eventhandlers.PlayerJoinQuitHandler;
import com.drrotstein.cp.helpers.ChatSettingsHelper;
import com.drrotstein.cp.helpers.ConfigHelper;

public class UltimateChat extends JavaPlugin {
	
	private static UltimateChat plugin;
	
	
	public static final ArrayList<Player> bypassAntispam = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		if(ConfigHelper.load("uc.joinmessage.ison") == null || !(ConfigHelper.load("uc.joinmessage.ison") instanceof Boolean))
			ConfigHelper.save(true, "uc.joinmessage.ison");
		if(ConfigHelper.load("uc.quitmessage.ison") == null || !(ConfigHelper.load("uc.quitmessage.ison") instanceof Boolean))
			ConfigHelper.save(true, "uc.quitmessage.ison");
		
		if(ConfigHelper.load("uc.joinmessage.value") == null)
			ConfigHelper.save(CommandJoinMessage.DEFAULT_JOINMESSAGE, "uc.joinmessage.value");
		if(ConfigHelper.load("uc.quitmessage.value") == null)
			ConfigHelper.save(CommandQuitMessage.DEFAULT_QUITMESSAGE, "uc.quitmessage.value");
		
		if(ConfigHelper.load("uc.textformat.ison") == null || !(ConfigHelper.load("uc.textformat.ison") instanceof Boolean))
			ConfigHelper.save(true, "uc.textformat.ison");
		if(ConfigHelper.load("uc.textformat.value") == null)
			ConfigHelper.save(CommandTextFormat.DEFAULT_TEXTFORMAT, "uc.textformat.value");
		
		if(ConfigHelper.load("uc.repeatmessages.allow") == null || !(ConfigHelper.load("uc.repeatmessages.allow") instanceof Boolean))
			ConfigHelper.save(true, "uc.repeatmessages.allow");
		
		if(ConfigHelper.load("uc.slowmode.time") == null || !(ConfigHelper.load("uc.slowmode.time") instanceof Integer))
			ConfigHelper.save(0, "uc.slowmode.time");
		

		ChatSettingsHelper.initializeItemMethods();
		
		
		init(Bukkit.getPluginManager());
		
	}
	
	
	private void init(PluginManager pm) {
		getCommand("chatsettings").setExecutor(new CommandChatSettings());
		
		
		
		getCommand("joinmessage").setExecutor(new CommandJoinMessage()); getCommand("joinmessage").setTabCompleter(new CommandJoinMessage());
		getCommand("quitmessage").setExecutor(new CommandQuitMessage()); getCommand("quitmessage").setTabCompleter(new CommandQuitMessage());
		
		getCommand("repeatmessages").setExecutor(new CommandRepeatMessages()); getCommand("repeatmessages").setTabCompleter(new CommandRepeatMessages());
		getCommand("slowmode").setExecutor(new CommandSlowMode()); getCommand("slowmode").setTabCompleter(new CommandSlowMode());
		getCommand("bypassantispam").setExecutor(new CommandBypassAntispam()); getCommand("bypassantispam").setTabCompleter(new CommandBypassAntispam());
		
		getCommand("canceleditor").setExecutor(new CommandCancelEditor());
		
		getCommand("chatformat").setExecutor(new CommandTextFormat()); getCommand("chatformat").setTabCompleter(new CommandTextFormat());
		
		
		pm.registerEvents(new PlayerJoinQuitHandler(), this);
		
		pm.registerEvents(new PlayerChatHandler(), this);
		
		pm.registerEvents(new InventoryClickHandler(), this);
	}
	
	public static UltimateChat getPlugin() {
		return plugin;
	}
	
	
}
