package lab.impl;

import java.lang.reflect.Method;
import java.util.Map;

import claim.structure.FormulaI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;
import lab.StrategyI;

public abstract class Strategy implements StrategyI {

	@Override
	public Object provideValue(QuantifiedI f, Map<VarI<?>, Object> g) {
		return delegate("support", f, g);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T delegate(String methodName, FormulaI f, Map<VarI<?>, Object> env) {
		VarI<?>[] args = f.getArguments();
		Class<?>[] argClasses = new Class<?>[args.length + 1];
		argClasses[0] = f.getClass();
		for (int i = 0; i < args.length; i++) {
			argClasses[i+1] = args[i].getType();
		}
		Object[] argVals = new Object[argClasses.length];
		argVals[0] = f;
		for (int i = 0; i < args.length; i++) {
			argVals[i+1] = env.get(args[i]);
		}
		try {
			Method selectPositionMethod = this.getClass().getMethod(methodName, argClasses);
			return (T) selectPositionMethod.invoke(this, argVals);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
