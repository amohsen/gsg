package example.lab.basis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import claim.structure.FunctionI;
import claim.structure.PredicateI;
import claim.structure.VarI;
import example.syntax.minbasis.Graph;
import lab.Translator;

//public class TarjanAdapter implements Translator{
//
//	@Override
//	public Map<VarI<?>, Object> translateParametersToTarget(
//			Map<VarI<?>, Object> sa) {
//		Map<VarI<?>, Object> ta = new HashMap<VarI<?>, Object>();
//		for (Entry<VarI<?>, Object> entry : sa.entrySet()) {
//			ta.put(entry.getKey(), translateValueToTarget(entry.getValue()));
//		}
//		return ta;
//	}
//
//	@Override
//	public Object translateValueToSource(Object val) {
//		if (val instanceof Integer) {
//			return val;
//		}else if (val instanceof Graph) {
//			Graph g = (Graph) val;
//			
//		}
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object translateValueToTarget(Object val) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public VarI<?> translateVarToTarget(VarI<?> var) {
//		return var;
//	}
//
//	@Override
//	public VarI<?> translateVarToSource(VarI<?> var) {
//		return var;
//	}
//
//	@Override
//	public PredicateI translatePredicateToTarget(PredicateI p) {
//		return p;
//	}
//
//	@Override
//	public FunctionI<?> translateFunctionToTarget(FunctionI<?> fn) {
//		return fn;
//	}
//
//}
