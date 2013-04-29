package claim.structure.impl;

import claim.structure.ExistentiallyQuantifiedI;
import claim.structure.FormulaI;
import claim.structure.VarI;

public abstract class Exists extends Quantified implements ExistentiallyQuantifiedI{

	public Exists(VarI<?>[] args, VarI<?> var, FormulaI subFormula) {
		super(args, var, subFormula);
	}

	protected String getQuantifier(){
		return "\u2203";
	}

}
