package com.drrotstein.cp;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.drrotstein.cp.chateditor.MethodParameter;
import com.drrotstein.cp.commands.CommandBypassAntispam;
import com.drrotstein.cp.commands.CommandCancelEditor;
import com.drrotstein.cp.commands.CommandChatSettings;
import com.drrotstein.cp.commands.CommandJoinMessage;
import com.drrotstein.cp.commands.CommandPlayerlistFormat;
import com.drrotstein.cp.commands.CommandQuitMessage;
import com.drrotstein.cp.commands.CommandRepeatMessages;
import com.drrotstein.cp.commands.CommandSlowMode;
import com.drrotstein.cp.commands.CommandTextFormat;
import com.drrotstein.cp.eventhandlers.InventoryClickHandler;
import com.drrotstein.cp.eventhandlers.PlayerChatHandler;
import com.drrotstein.cp.eventhandlers.PlayerJoinQuitHandler;
import com.drrotstein.cp.helpers.ChatSettingsHelper;
import com.drrotstein.cp.helpers.ConfigHelper;
import com.drrotstein.cp.helpers.addon.AddonManager;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;
import com.drrotstein.cp.helpers.addon.varg.VArgMethods;
import com.drrotstein.cp.helpers.addon.varg.VArgType;
import com.drrotstein.cp.helpers.decoding.JQMHelper;

public class UltimateChat extends JavaPlugin {
	
	private static UltimateChat plugin;
	
	
	public static final ArrayList<Player> bypassAntispam = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		//Instance
		plugin = this;

		//Avoiding Nullpointer-Exceptions in the config
		if(ConfigHelper.load("uc.joinmessage.ison") == null || !(ConfigHelper.load("uc.joinmessage.ison") instanceof Boolean))
			ConfigHelper.save(true, "uc.joinmessage.ison");
		if(ConfigHelper.load("uc.quitmessage.ison") == null || !(ConfigHelper.load("uc.quitmessage.ison") instanceof Boolean))
			ConfigHelper.save(true, "uc.quitmessage.ison");
		
		if(ConfigHelper.load("uc.joinmessage.value") == null)
			ConfigHelper.save(JQMHelper.DEFAULT_JOINMESSAGE, "uc.joinmessage.value");
		if(ConfigHelper.load("uc.quitmessage.value") == null)
			ConfigHelper.save(JQMHelper.DEFAULT_QUITMESSAGE, "uc.quitmessage.value");
		
		if(ConfigHelper.load("uc.textformat.ison") == null || !(ConfigHelper.load("uc.textformat.ison") instanceof Boolean))
			ConfigHelper.save(true, "uc.textformat.ison");
		if(ConfigHelper.load("uc.textformat.value") == null)
			ConfigHelper.save(CommandTextFormat.DEFAULT_TEXTFORMAT, "uc.textformat.value");
		
		if(ConfigHelper.load("uc.repeatmessages.allow") == null || !(ConfigHelper.load("uc.repeatmessages.allow") instanceof Boolean))
			ConfigHelper.save(true, "uc.repeatmessages.allow");
		
		if(ConfigHelper.load("uc.slowmode.time") == null || !(ConfigHelper.load("uc.slowmode.time") instanceof Integer))
			ConfigHelper.save(0, "uc.slowmode.time");
		
		//Initialization of item-methods for the ui
		ChatSettingsHelper.initializeItemMethods();
		
		//Initialization of vargs  --  Overriding them with plugins loaded before at own risk
		VArgMethods vm = new VArgMethods();
		try {
			AddonManager.addVariableArgument("player", VArgType.PLAYER_ONLY, new MethodParameter("player", new Class[] {Player.class}, new Object[] {null},
					vm.getClass(), vm, 0), new VArgAccessType[] {VArgAccessType.ALL}, plugin);
			AddonManager.addVariableArgument("message", VArgType.EVENT_ONLY, new MethodParameter("message", new Class[]  {AsyncPlayerChatEvent.class},
					new Object[] {null}, vm.getClass(), vm, 0), new VArgAccessType[] {VArgAccessType.CHAT_FORMAT}, plugin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Initialization of commands and events
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
		
		getCommand("playerlistformat").setExecutor(new CommandPlayerlistFormat()); getCommand("playerlistformat").
			setTabCompleter(new CommandPlayerlistFormat());
		
		
		pm.registerEvents(new PlayerJoinQuitHandler(), this);
		
		pm.registerEvents(new PlayerChatHandler(), this);
		
		pm.registerEvents(new InventoryClickHandler(), this);
	}
	
	public static void updateImportantMethods() {
		try {
			CommandPlayerlistFormat.updateTablist();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static UltimateChat getPlugin() {
		return plugin;
	}
	
	
	public static boolean isSRLoaded() {
		return Bukkit.getPluginManager().isPluginEnabled("ServerRanks");
	}
	
	public static JavaPlugin getSR() {
		if(isSRLoaded()) return (JavaPlugin) Bukkit.getPluginManager().getPlugin("ServerRanks");
		return null;
	}
	
}
