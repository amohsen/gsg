package sg.general;

import java.util.Map;

import claim.structure.FunctionI;
import claim.structure.PredicateI;
import claim.structure.VarI;

public interface Translator {
	Map<VarI<?>, Object> translateParametersToTarget(Map<VarI<?>, Object> sa);
	Object translateValueToSource(Object val);
	Object translateValueToTarget(Object val);
	VarI<?> translateVarToTarget(VarI<?> var);
	VarI<?> translateVarToSource(VarI<?> var);
	PredicateI translatePredicateToTarget(PredicateI p);
	FunctionI<?> translateFunctionToTarget(FunctionI<?> fn);
}
