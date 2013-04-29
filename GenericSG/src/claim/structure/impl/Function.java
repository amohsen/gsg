package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.FunctionI;
import claim.structure.VarI;
import static claim.services.Utils.*;

public abstract class Function<RT> extends Formula implements FunctionI<RT>{

	
	public Function(VarI<?>[] args) {
		super(args);
	}

	@Override
	public String formatRHS(boolean shortName, boolean inline, StringBuilder... optSB) {
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		formatLHS(this, false, shortName, sb);
		return optSB.length==0?sb.toString():null;
	}
	
	@Override
	public FormulaI[] getSubformulas(){
		return new FormulaI[0];
	}

}
