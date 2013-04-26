package claim;

/* a quantified formula */
public interface Quantified extends Formula{
	Var<?> getVar();
	Formula getSubFormula();
}
