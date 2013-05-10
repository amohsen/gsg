package lab.impl;

import java.util.Map;

import sg.SemanticGame;

import claim.structure.AndCompoundI;
import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.PredicateI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;
import lab.ScholarI;
import lab.StrategyI;

/**
 * Implements the default scholar behavior
 * 
 * */
public final class Scholar implements ScholarI{
	private final StrategyI strategy;
	
	public Scholar(StrategyI strategy) {
		this.strategy = strategy;
	}

	@Override
	public String getName() {
		return strategy.getName();
	}

	@Override
	public Position selectPosition(FormulaI f, Map<VarI<?>, Object> env) {
		return SemanticGame.SG(f, env, this, this);
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
		return strategy.provideValue(f, env);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public StrategyI getStrategy() {
		return strategy;
	}
}
