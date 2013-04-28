package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.FunctionI;
import claim.structure.LetI;
import claim.structure.VarI;
import static claim.structure.impl.Utils.*;

public class Let<T> extends Formula implements LetI<T>{
	private final VarI<T> var;
	
	private final FormulaI subFormula;
	private final FunctionI<T> function;
	

	public Let(VarI<T> var, FormulaI subFormula,
			FunctionI<T> function, VarI<?>... params) {
		super(params);
		this.var = var;
		this.subFormula = subFormula;
		this.function = function;
		checkShadowing(var, params);
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

}
