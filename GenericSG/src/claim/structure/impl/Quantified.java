package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;
import static claim.structure.impl.Utils.*;

public class Quantified extends Formula implements QuantifiedI{
	private final VarI<?> var;
	private final FormulaI subFormula;
	
	public Quantified(VarI<?> var, FormulaI subFormula, VarI<?>... params) {
		super(params);
		this.var = var;
		this.subFormula = subFormula;
		checkShadowing(var, params);
	}


	@Override
	public VarI<?> getVar() {
		return var;
	}

	@Override
	public FormulaI getSubFormula() {
		return subFormula;
	}

}
