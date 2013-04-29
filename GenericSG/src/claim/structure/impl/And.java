package claim.structure.impl;

import claim.structure.AndCompoundI;
import claim.structure.FormulaI;
import claim.structure.VarI;

public class And extends Compound implements AndCompoundI{

	/** Constructs an inlined and formula*/
	public And(FormulaI left, FormulaI right) {
		super(left, right);
	}
	
	protected And(VarI<?>[] args, FormulaI left, FormulaI right) {
		super(args, left, right);
	}
	
	protected String getConnective(){
		return " \u2227 ";
	}
}
