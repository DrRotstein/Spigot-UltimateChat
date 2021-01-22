package com.drrotstein.cp.helpers;

public class ArrayHelper {
	
	public static String charArrayToString(char[] carr) {
		String val = "";
		
		for(char c : carr) {
			val += c;
		}
		
		return val;
	}
	
	public static boolean containsStringChar(String s, char c) {
		char[] arr = s.toCharArray();
		
		for(char ch : arr) {
			if(ch == c) return true;
		}
		
		return false;
	}
	
	public static String[] splitStringAt(String s, int index, boolean cut) {
		String[] val = new String[2];
		val[0] = "";
		val[1] = "";
		
		if(s.length() > 2) {
			for(int i = 0; i < index; i++) {
				val[0] += s.charAt(i);
			}
			
			if(cut) {
				for(int i = index + 1; i < s.length(); i++) {
					val[1] += s.charAt(i);
				}
			} else {
				for(int i = index; i < s.length(); i++) {
					val[1] += s.charAt(i);
				}
			}
		}
		
		
		
		return val;
	}
	
	public static String firstCharUppercase(String str) {
		if(str.equals("") || str == null) return null;
		char[] carr = str.toCharArray();
		String fin = "";
		fin = (str.charAt(0) + "").toUpperCase();
		for(int i = 1; i < carr.length; i++) {
			fin += carr[i];
		}
		return fin;
	}
	
	
	public static boolean containsArray(Object[] arr, Object o) {
		for(Object obj : arr) {
			if(obj.equals(o)) return true;
		}
		return false;
	}
	
	
}
