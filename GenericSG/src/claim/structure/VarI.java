package claim.structure;

/** Variable of type T */
/*TODO: Only allows non-parameterized type parameters, 
 * represented by their Class objects*/
public interface VarI<T> {
	String getName();
	Class<T> getType();
	Class<?>[] getTypeParams();
}
