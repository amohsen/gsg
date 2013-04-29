package claim.structure.impl;

import static claim.services.Utils.*;
import claim.structure.FormulaI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;

public abstract class Quantified extends Formula implements QuantifiedI{
	private final VarI<?> var;
	private final FormulaI subFormula;

	public Quantified(VarI<?>[] args, VarI<?> var, FormulaI subFormula) {
		super(args);
		if(this.getClass().isAnonymousClass()){
			throw new RuntimeException(" Quantified formulas cannot be anonymous");
		}
		this.var = var;
		this.subFormula = subFormula;
		checkShadowing(var, args);
	}

	protected abstract String getQuantifier();

	public String formatRHS(boolean shortName, boolean inline, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		if(inline || inline(this)){
			if(!inline) sb.append("(");
			sb.append(getQuantifier());
			formatVar(var, true, shortName, sb);
			sb.append(":");
			subFormula.formatRHS(shortName, false, sb);
			if(!inline) sb.append(")");
		}else{
			formatLHS(this, false, shortName, sb);
		}
		return optSB.length==0?sb.toString():null;
	}

	@Override
	public VarI<?> getVar() {
		return var;
	}

	@Override
	public FormulaI getSubFormula() {
		return subFormula;
	}

	@Override
	public FormulaI[] getSubformulas(){
		return new FormulaI[]{subFormula};
	}

}
