package sg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** Representation of Assignment */
public class Assignment{
	public class VarAssignment{
	    protected final String type;
	    protected final String var;
	    protected final String value;

	    /** Construct a(n) VarAssignment Instance */
	    public VarAssignment(String type, String var, String value){
	        this.type = type;
	        this.var = var;
	        this.value = value;
	    }

		public String getType() {
			return type;
		}

		public String getVar() {
			return var;
		}

		public String getValue() {
			return value;
		}
	    @Override
	    public String toString() {
	    	return type+" "+var+" "+value;
	    }
	}
	@SuppressWarnings("serial")
	public class IncompleteAssignmentException extends RuntimeException{};
	private Map<String, String> var2type = new HashMap<String, String>();
	private Map<String, String> var2val = new HashMap<String, String>();
	protected final List<VarAssignment> varAssignments = new ArrayList<Assignment.VarAssignment>();

	public String getType(String var){
		String type = var2type.get(var);
		if(type == null) throw new IncompleteAssignmentException();
		return type;
	}

	public String getValue(String var){
		String val = var2val.get(var);
		if(val == null) throw new IncompleteAssignmentException();
		return val;
	}

	public void add(String var, String type, String value){
		VarAssignment nva = new VarAssignment(var, type, value);
		var2type.put(var, type);
		var2val.put(var, value);
		varAssignments.add(nva);
	}
	
	@Override
	public String toString() {
		return varAssignments.toString();
	}
}
