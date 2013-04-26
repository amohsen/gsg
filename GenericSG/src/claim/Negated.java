package claim;

/* a negated formula */
public class Negated implements Formula{
	public final Formula subformula;
	public final String id;
	
	public Negated(String id, Formula subformula) {
		this.id = id;
		this.subformula = subformula;
	}
	
	public String getId() {
		return id;
	}

}

