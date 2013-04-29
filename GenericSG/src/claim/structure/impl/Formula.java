package claim.structure.impl;

import static claim.services.Utils.*;

import java.util.Collection;

import claim.structure.FormulaI;
import claim.structure.VarI;

public abstract class Formula implements FormulaI{
	private final VarI<?>[] args;
	
	/** Constructs a formula with the given arguments */
	protected Formula(VarI<?>[] args) {
		this.args = args;
	}
	
	/** Constructs an inlined formula */
	protected Formula(){
		args = null;
	}

	@Override
	public VarI<?>[] getArguments() {
		return args;
	}

	
	public String format(boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		Collection<FormulaI> formulas = AllSubformulas(this); 
		for (FormulaI f : formulas) {
			if(!inline(f)){
				//Print the LHS
				formatLHS(f, true, shortName, sb);
				sb.append(" := ");
				//Print the RHS
				f.formatRHS(shortName, true, sb);
				sb.append("\n");
			}
		}
		return optSB.length==0?sb.toString():null;
	}
	
}
