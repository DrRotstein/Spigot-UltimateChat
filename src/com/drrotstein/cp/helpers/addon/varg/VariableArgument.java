package com.drrotstein.cp.helpers.addon.varg;

import java.util.ArrayList;
import java.util.List;

import com.drrotstein.cp.chateditor.MethodParameter;

public class VariableArgument {
	
	String key;
	VArgType type;
	MethodParameter mp;
	List<VArgAccessType> accesses;
	
	
	public VariableArgument(String key, VArgType type, MethodParameter mp, List<VArgAccessType> accesses) {
		this.key = key;
		this.type = type;
		this.mp = mp;
		this.accesses = accesses;
	}
	
	public VariableArgument(String key, VArgType type, MethodParameter mp, VArgAccessType[] accesses) {
		List<VArgAccessType> accessesList = new ArrayList<>();
		for(VArgAccessType varg : accesses) {
			accessesList.add(varg);
		}
		this.key = key;
		this.type = type;
		this.mp = mp;
		this.accesses = accessesList;
	}


	public String getKey() {
		return key;
	}
	
	public VArgType getType() {
		return type;
	}
	
	public MethodParameter getMp() {
		return mp;
	}
	
	public List<VArgAccessType> getAccesses() {
		return accesses;
	}
	
}
