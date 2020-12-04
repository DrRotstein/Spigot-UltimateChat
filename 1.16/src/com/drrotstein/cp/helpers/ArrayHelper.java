package com.drrotstein.cp.helpers;

public class ArrayHelper {
	
	public static char[] stringToCharArray(String s) {
		char[] val = new char[s.length()];
		
		for(int i = 0; i < s.length(); i++) {
			val[i] = s.charAt(i);
		}
		
		return val;
	}
	
	
	public static String charArrayToString(char[] carr) {
		String val = "";
		
		for(char c : carr) {
			val += c;
		}
		
		return val;
	}
	
	
	public static boolean containsStringChar(String s, char c) {
		char[] arr = stringToCharArray(s);
		
		for(char ch : arr) {
			if(ch == c) return true;
		}
		
		return false;
	}
}
