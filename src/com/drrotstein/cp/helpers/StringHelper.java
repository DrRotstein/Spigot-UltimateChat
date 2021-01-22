package com.drrotstein.cp.helpers;

public class StringHelper {
	
	public static final String replace(String s, char oldChar, char newChar) {
		String val = "";
		
		
		char[] carr = s.toCharArray();
		char[] newcarr = new char[carr.length];
		
		
		int count = 0;
		for(char c : carr) {
			newcarr[count] = c;
			if(c == oldChar) newcarr[count] = newChar;
			
			count++;
		}
		
		
		
		
		
		val = ArrayHelper.charArrayToString(newcarr);
		
		return val;
	}
	
}
