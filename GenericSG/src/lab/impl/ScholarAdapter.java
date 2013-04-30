package lab.impl;

import java.lang.reflect.Method;
import java.util.Map;

import claim.structure.AndCompoundI;
import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.PredicateI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;
import lab.Scholar;

public class ScholarAdapter implements Scholar{
	private final Object inner;
	
	public ScholarAdapter(Object inner) {
		this.inner = inner;
	}

	@Override
	public String getName() {
		try {
			Method getNameMethod = inner.getClass().getMethod("getName");
			return (String) getNameMethod.invoke(inner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Position selectPosition(FormulaI f, Map<VarI<?>, Object> env) {
		return delegate("selectPosition", f, env);
	}

	@Override
	public Decision selectSubformula(CompoundI f, Map<VarI<?>, Object> env) {
		if(f instanceof AndCompoundI){
			// current scholar is the falsifier
			return selectSubformulaAsFalsifier(f, env);
		}else{
			// current scholar is the verifier
			return selectSubformulaAsFalsifier(f, env).revese();
		}
	}
	
	public Decision selectSubformulaAsFalsifier(CompoundI f, Map<VarI<?>, Object> env) {
		if(f.getLeft() instanceof PredicateI){
			if(evalPredicate((PredicateI)f, env)){
				return Decision.RIGHT;
			}else{
				return Decision.LEFT;
			}
		}
		if(f.getRight() instanceof PredicateI){
			if(evalPredicate((PredicateI)f, env)){
				return Decision.LEFT;
			}else{
				return Decision.RIGHT;
			}
		}
		if(selectPosition(f.getLeft(), env) == Position.FALSIFIER){ 
			return Decision.LEFT;
		}else{
			return Decision.RIGHT;
		}			
	}
	
	private static boolean evalPredicate(PredicateI f, Map<VarI<?>, Object> env) {
		return f.execute(env);
	}

	@Override
	public Object provideValue(QuantifiedI f, Map<VarI<?>, Object> env) {
		return delegate("support", f, env);
	}

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
			Method selectPositionMethod = inner.getClass().getMethod(methodName, argClasses);
			return (T) selectPositionMethod.invoke(inner, argVals);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
