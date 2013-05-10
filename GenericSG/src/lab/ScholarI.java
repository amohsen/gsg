package lab;

import java.util.Map;

import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;

public interface ScholarI {
	
	public enum Decision{
		LEFT, RIGHT;
		
		public Decision revese(){
			if(this == LEFT) return RIGHT;
			return LEFT;
		}
	}
	
	public enum Position{
		FALSIFIER, VERIFIER;
		
		public Position reverse(){
			if(this == FALSIFIER) return VERIFIER;
			return FALSIFIER;
		}
		public static Position create(boolean b){
			return b?VERIFIER:FALSIFIER;
		} 
	}
	
	String getName();
	Position selectPosition(FormulaI f, Map<VarI<?>, Object> g);
	Decision selectSubformula(CompoundI f, Map<VarI<?>, Object> g);
	Object provideValue(QuantifiedI f, Map<VarI<?>, Object> g);
	StrategyI getStrategy();
}
