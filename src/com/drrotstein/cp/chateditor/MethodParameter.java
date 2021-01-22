package com.drrotstein.cp.chateditor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MethodParameter {
	
	String methodName;
	Class[] signature;
	Object[] parameters;
	Class targetClass;
	Object target;
	int customIndex;
	
	public MethodParameter(String methodName, Class[] signature, Object[] parameters, Class targetClass, Object target, int customIndex) {
		this.methodName = methodName;
		this.signature = signature;
		this.parameters = parameters;
		this.targetClass = targetClass;
		this.target = target;
		this.customIndex = customIndex;
	}
	
	public Class<?> getReturnType() throws Exception {
		Method m = targetClass.getDeclaredMethod(methodName, signature);
		if(Modifier.isPrivate(m.getModifiers())) {
			m.setAccessible(true);
		}
		return m.getReturnType();
	}
	
	
	public Object executeMethod(Object custom) throws Exception {
		if(customIndex == -1) return executeNormally();
		else return executeWithCustom(custom);
	}
	
	public Object executeWithCustom(Object custom) throws Exception {
		Method m = targetClass.getDeclaredMethod(methodName, signature);
		
		if(Modifier.isPrivate(m.getModifiers())) {
			m.setAccessible(true);
		}
		
		
		
		parameters[customIndex] = custom;
		
		return m.invoke(target, parameters);
	}
	
	
	public Object executeNormally() throws Exception {
		Method m = targetClass.getDeclaredMethod(methodName, signature);
		
		if(Modifier.isPrivate(m.getModifiers())) {
			m.setAccessible(true);
		}
		
		return m.invoke(target, parameters);
	}
}
