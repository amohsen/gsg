package claim.structure;


/* a quantified formula */
public interface QuantifiedI extends FormulaI{
	VarI<?> getVar();
	FormulaI getSubFormula();
}
