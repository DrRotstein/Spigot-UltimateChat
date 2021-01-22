package com.drrotstein.cp.helpers.addon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.drrotstein.cp.chateditor.MethodParameter;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;
import com.drrotstein.cp.helpers.addon.varg.VArgType;
import com.drrotstein.cp.helpers.addon.varg.VariableArgument;


public class AddonManager {
	
	public static List<VariableArgument> customVargs = new ArrayList<>();
	
	public static void addVariableArgument(String key, VArgType type, MethodParameter mp, VArgAccessType[] accesses, JavaPlugin from) throws Exception {
		
		for(VariableArgument varg : customVargs) {
			if(varg.getKey().equals(key)) {
				printAVAError(key, "Variable-Argument already exists", from);
				return;
			}
		}
		
		switch(type) {
		case PLAYER_ONLY: case EVENT_ONLY:
			if(!mp.getReturnType().equals(String.class)) {
				printAVAError(key, "The method return-type is not a string", from);
				return;
			}
			break;
			
			
			default:
				printAVAError(key, "Invalid VArgType", from);
				return;
		}
		
		customVargs.add(new VariableArgument(key, type, mp, accesses));
		
	}
	
	public static VariableArgument getVargByKey(String key) {
		for(VariableArgument varg : customVargs) if(varg.getKey().equals(key)) return varg;
		return null;
	}
	
	public static List<VariableArgument> getEveryVargFor(VArgAccessType type) {
		List<VariableArgument> val = new ArrayList<>();
		for(VariableArgument varg : customVargs) if(varg.getAccesses().contains(type) || varg.getAccesses().contains(VArgAccessType.ALL)) val.add(varg);
		return val;
	}
	
	
	private static void printAVAError(String arg, String reason, JavaPlugin from) {
		System.err.println("\u001B[1;31mError whilst initializing addon variable-argument! Variable argument '" + arg + "' from " + from.getName() +
				" will not be added. (" + reason + ")\u001B[0m");
	}
	
}
