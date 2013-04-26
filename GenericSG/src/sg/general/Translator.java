package sg.general;

import java.util.Map;

import claim.Function;
import claim.Predicate;
import claim.Var;

public interface Translator {
	Map<Var<?>, Object> translateParametersToTarget(Map<Var<?>, Object> sa);
	Object translateValueToSource(Object val);
	Object translateValueToTarget(Object val);
	Var<?> translateVarToTarget(Var<?> var);
	Var<?> translateVarToSource(Var<?> var);
	Predicate translatePredicateToTarget(Predicate p);
	Function<?> translateFunctionToTarget(Function<?> fn);
}
