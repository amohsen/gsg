package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.UniversallyQuantifiedI;
import claim.structure.VarI;

public abstract class Forall extends Quantified implements UniversallyQuantifiedI{

	public Forall(VarI<?>[] args, VarI<?> var, FormulaI subFormula) {
		super(args, var, subFormula);
	}

	protected String getQuantifier(){
		return "\u2200";
	}

}
