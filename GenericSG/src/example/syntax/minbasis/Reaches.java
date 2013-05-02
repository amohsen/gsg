package example.syntax.minbasis;

import java.util.Map;

import claim.structure.VarI;
import claim.structure.impl.Predicate;

public class Reaches extends Predicate{
	final VarI<Graph> _g;
	final VarI<Integer> _n, _m;
	
	public Reaches(VarI<Graph> g, VarI<Integer> n, VarI<Integer> m) {
		super(new VarI[]{g, n, m});
		this._g = g;
		this._n = n;
		this._m = m;
	}

	@Override
	public Boolean execute(Map<VarI<?>, Object> env) {
		throw new RuntimeException("example.minbasis.Reaches not implemented!");
	}

}
