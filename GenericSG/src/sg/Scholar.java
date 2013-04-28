package sg;

import java.util.Map;

import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;

public interface Scholar {
	public enum Decision{
		LEFT, RIGHT;
	}
	
	public enum Position{
		FALSIFIER, VERIFIER;
	}
	
	String getName();
	Position selectPosition(FormulaI f, Map<VarI<?>, Object> g);
	Decision selectSubformula(CompoundI f, Map<VarI<?>, Object> g);
	Object provideValue(QuantifiedI f, Map<VarI<?>, Object> g);
}
