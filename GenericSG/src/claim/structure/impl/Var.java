package claim.structure.impl;

import claim.structure.VarI;

public class Var<T> implements VarI<T>{
	final String name;
	final Class<T> type;
	final Class<?>[] paramTypes;

	public Var(String name, Class<T> type) {
		this.name = name;
		this.type = type;
		this.paramTypes = new Class<?>[0];
	}

	public Var(String name, Class<T> type, Class<?>...argTypes) {
		this.name = name;
		this.type = type;
		this.paramTypes = argTypes;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public Class<?>[] getTypeParams() {
		return paramTypes;
	}
}
