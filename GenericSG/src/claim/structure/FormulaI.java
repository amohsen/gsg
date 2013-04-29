package claim.structure;


/** A marker inteface for formulas*/
public interface FormulaI { 
	VarI<?>[] getArguments();

	public FormulaI[] getSubformulas();
	public String formatRHS(boolean shortName, boolean inline, StringBuilder...optSB);
}
