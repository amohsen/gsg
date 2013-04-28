package claim.structure;


public interface LetI<T> extends FormulaI{
	VarI<T> getVar();
	FunctionI<T> getFunction();
	FormulaI getSubFormula();
}
