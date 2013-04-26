package sg;

import claim.Compound;
import claim.Formula;
import claim.Quantified;

public interface Scholar {
	public enum Decision{
		LEFT, RIGHT;
	}
	
	public enum Position{
		FALSIFIER, VERIFIER;
	}
	
	String getName();
	Position selectPosition(Formula f, Assignment g);
	Decision selectSubformula(Compound f, Assignment g);
	String provideValue(Quantified f, Assignment g);
}
