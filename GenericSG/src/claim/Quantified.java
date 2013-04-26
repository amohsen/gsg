package claim;

/* a quantified formula */
public class Quantified implements Formula{
	public enum Quantifier{
		UNIVERSAL, EXISTENTIAL;
	}
	public final String id;
	public final Quantifier quantifier;
	public final String var; //var name
	public final String type; //type name
	public final Formula subformula;
	public Quantified(String id, Quantifier quantifier, String var, String type, Formula subformula) {
		this.id = id;
		this.quantifier = quantifier;
		this.var = var;
		this.type = type;
		this.subformula = subformula;
	}

	@Override
	public String getId() {
		return id;
	}


}
