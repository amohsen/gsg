package claim.structure.impl;

import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.VarI;

public class Compound extends Formula implements CompoundI {
	private final FormulaI left, right;

	public Compound(FormulaI left, FormulaI right, VarI<?>... params) {
		super(params);
		this.left = left;
		this.right = right;
	}

	@Override
	public FormulaI getLeft() {
		return left;
	}

	@Override
	public FormulaI getRight() {
		return right;
	}

}
