package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.NegatedI;
import claim.structure.VarI;

public class Negated extends Formula implements NegatedI{
	private final FormulaI subFormula;
	
	public Negated(FormulaI subFormula, VarI<?>... params) {
		super(params);
		this.subFormula = subFormula;
	}

	@Override
	public FormulaI getSubFormula() {
		return subFormula;
	}

}
