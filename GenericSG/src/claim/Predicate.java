package claim;

/* a primitive formula */
public class Predicate implements Formula{
	public final String name;
	final String[] args;
	final String id;
	
	public Predicate(String id, String name, String[] args) {
		this.id = id;
		this.name = name;
		this.args = args;
	}

	public String[] getArgs() {
		return args.clone();
	}

	public String getId() {
		return id;
	}
}
