package claim;

/* a compound formula */
public class Compound implements Formula{
	public enum Connective{
		AND, OR;
	}
	public final Connective Connective; // "and" or "or"
	public final Formula left;
	public final Formula right;
	public final String id;
	public Compound(String id, Connective connective, Formula left, Formula right) {
		this.id = id;
		this.Connective = connective;
		this.left = left;
		this.right = right;
	}
	public String getId() {
		return id;
	}
}
