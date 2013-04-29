package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.FunctionI;
import claim.structure.LetI;
import claim.structure.VarI;
import static claim.services.Utils.*;

public class Let<T> extends Formula implements LetI<T>{
	private final VarI<T> var;

	private final FormulaI subFormula;
	private final FunctionI<T> function;

	/** Constructs an inlined let formula */
	public Let(VarI<T> var, FunctionI<T> function, FormulaI subFormula) {
		this.var = var;
		this.subFormula = subFormula;
		this.function = function;
	}

	protected Let(VarI<?>[] args, VarI<T> var, FunctionI<T> function, FormulaI subFormula) {
		super(args);
		this.var = var;
		this.subFormula = subFormula;
		this.function = function;
		checkShadowing(var, args);
	}

	@Override
	public VarI<T> getVar() {
		return var;
	}

	@Override
	public FunctionI<T> getFunction() {
		return function;
	}

	@Override
	public FormulaI getSubFormula() {
		return subFormula;
	}

	@Override
	public String formatRHS(boolean shortName, boolean inline, StringBuilder... optSB) {
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		if(inline || inline(this)){
			if(!inline) sb.append("(");
			sb.append("let ");
			formatVar(var, true, shortName, sb);
			sb.append(" = ");
			function.formatRHS(shortName, false, sb);
			sb.append(" in ");
			subFormula.formatRHS(shortName, false, sb);
			if(!inline) sb.append(")");
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
