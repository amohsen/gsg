package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.OrCompoundI;
import claim.structure.VarI;

public class OR extends Compound implements OrCompoundI {

	/** Constructs an inlined or formula*/
	public OR(FormulaI left, FormulaI right) {
		super(left, right);
	}

	public OR(VarI<?>[] args, FormulaI left, FormulaI right) {
		super(args, left, right);
	}

	protected String getConnective(){
		return " \u2228 ";
	}

}
