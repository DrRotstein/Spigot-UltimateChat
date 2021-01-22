package com.drrotstein.cp.helpers.decoding;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drrotstein.cp.helpers.ArrayHelper;
import com.drrotstein.cp.helpers.addon.AddonManager;
import com.drrotstein.cp.helpers.addon.varg.VArgAccessType;
import com.drrotstein.cp.helpers.addon.varg.VariableArgument;

public class DecodingHelper {
	
	public static final String VARG_DEFAULT = "default";
//	public static final String VARG_PLAYER = "player";
//	
//	public static final String[] VARGS = {VARG_PLAYER, VARG_DEFAULT};
//	
	public static String setFormat(CommandSender sender, String defaultF, VArgAccessType type, String[] args, int argIndex) {
		
		List<VariableArgument> vargs = AddonManager.getEveryVargFor(type);
		
		
		String format = "";
		
		for(int i = argIndex; i < args.length; i++) {
			if(i != args.length - 1) format += (args[i] + " ");
			else format += args[i];
		}
		
		
		for(String s : args) {
			
			if(ArrayHelper.containsStringChar(s, '%')) {
				
				String[] split = ("a" + s + "a").split("%");
				
				for(int i = 1; i < split.length; i+=2) {
					String varArg = split[i];
					VariableArgument varg = AddonManager.getVargByKey(varArg);
					
					if(varArg.equals(VARG_DEFAULT)) {
						if(varArg.equals(VARG_DEFAULT)) {
							if(format.equals("%" + VARG_DEFAULT + "%")) {
								format = defaultF;
							} else {
								sender.sendMessage("§cYou may not type more than the variable-argument, using %default%");
								return null;
							}
						}
					} else if(varg == null) {
						String errmes = "§cThere are only those variable-arguments: ";
						for(int l = 0; l < vargs.size(); l++) { 
							if(l == vargs.size() - 1) errmes += vargs.get(l).getKey(); else errmes += vargs.get(l).getKey() + ", ";
						}
						
						sender.sendMessage(errmes);
						return null;
					}
				}
			}
			
		}
		
		format = format.replace('&', '§');
		
		sender.sendMessage("§aYou successfully set " + type + " to: §f" + format);
		
		return format;
	}
	
	
	public static String getFormat(Object[] o, VArgAccessType type, String raw) throws Exception {
		String mes = "";
		
		List<VariableArgument> vargs = AddonManager.getEveryVargFor(type);
		
		if(ArrayHelper.containsStringChar(raw, '%')) {
			String[] split = (raw + "a").split("%");
			
			
			for(int i = 0; i < split.length; i++) {
				
				String varArg = split[i];
				
				if(vargs.contains(AddonManager.getVargByKey(varArg))) {
					for(Object obj : o) {
						try {
							varArg = (String) AddonManager.getVargByKey(varArg).getMp().
									executeMethod(obj);
						} catch (Exception e) {}
					}
				}
				
				
				
				
				if(i == split.length - 1) varArg = ArrayHelper.splitStringAt(varArg, varArg.length() - 1, false)[0];
				mes += varArg;
			}
			
		} else mes = raw;
		return mes;
	}
	
}
