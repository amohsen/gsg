package sg;
import claim.Predicate;

public interface Structure {
	boolean executePredicate(Assignment g, Predicate pred);
	boolean wellFormed(String value, String type);
	boolean wellFormedTypeName(String type); 
}
