package claim.structure;

/* a compound formula */
public interface CompoundI extends FormulaI{
	FormulaI getLeft();
	FormulaI getRight();
}
