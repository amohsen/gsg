package lab;

import java.util.Map;

import claim.structure.QuantifiedI;
import claim.structure.VarI;

public interface StrategyI {
	public String getName();
	public Object provideValue(QuantifiedI f, Map<VarI<?>, Object> g);
}
