package claim.structure.impl;

import static claim.services.Utils.*;
import claim.structure.FormulaI;
import claim.structure.NegatedI;
import claim.structure.VarI;

public class Not extends Formula implements NegatedI{
	private final FormulaI subFormula;

	/** Constructs an inlined negated formula */
	public Not(FormulaI subFormula) {
		this.subFormula = subFormula;
	}

	protected Not(VarI<?>[] args, FormulaI subFormula) {
		super(args);
		this.subFormula = subFormula;
	}

	@Override
	public FormulaI getSubFormula() {
		return subFormula;
	}

	@Override
	public String formatRHS(boolean shortName, boolean inline, StringBuilder... optSB) {
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		if(inline || inline(this)){
			sb.append("Â");
			subFormula.formatRHS(shortName, false, sb);
		}else{
			formatLHS(this, false, shortName, sb);
		}
		return optSB.length==0?sb.toString():null;
	}

	@Override
	public FormulaI[] getSubformulas(){
		return new FormulaI[]{subFormula};
	}

}
