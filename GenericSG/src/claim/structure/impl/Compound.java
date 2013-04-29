package claim.structure.impl;

import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.VarI;
import static claim.services.Utils.*;

public abstract class Compound extends Formula implements CompoundI {
	private final FormulaI left, right;

	/** Constructs an inlined compound formula*/
	protected Compound(FormulaI left, FormulaI right) {
		this.left = left;
		this.right = right;
	}
	
	protected Compound(VarI<?>[] args, FormulaI left, FormulaI right) {
		super(args);
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

	@Override
	public FormulaI[] getSubformulas(){
		return new FormulaI[]{left, right};
	}
	
	protected abstract String getConnective();
	
	public String formatRHS(boolean shortName, boolean inline, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		if(inline || inline(this)){
			if(!inline) sb.append("(");
			left.formatRHS(shortName, false, sb);
			sb.append(getConnective());
			right.formatRHS(shortName, false, sb);
			if(!inline) sb.append(")");
		}else{
			formatLHS(this, false, shortName, sb);
		}
		return optSB.length==0?sb.toString():null;
	}

}
