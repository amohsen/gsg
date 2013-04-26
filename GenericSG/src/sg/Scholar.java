package sg;

import java.util.Map;

import claim.Compound;
import claim.Formula;
import claim.Quantified;
import claim.Var;

public interface Scholar {
	public enum Decision{
		LEFT, RIGHT;
	}
	
	public enum Position{
		FALSIFIER, VERIFIER;
	}
	
	String getName();
	Position selectPosition(Formula f, Map<Var<?>, Object> g);
	Decision selectSubformula(Compound f, Map<Var<?>, Object> g);
	Object provideValue(Quantified f, Map<Var<?>, Object> g);
}
