package claim.structure;

import java.util.Map;


public interface FunctionI<RT> extends FormulaI{
	RT execute(Map<VarI<?>, Object> env);
}
