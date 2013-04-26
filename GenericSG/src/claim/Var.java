package claim;

public class Var<T> {
	final String name;
	final Class<T> type;
	
	public Var(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type.getCanonicalName()+" "+name;
	}
}
