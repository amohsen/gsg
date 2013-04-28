package example.minbasis;

import java.util.Map;

import claim.structure.PredicateI;
import claim.structure.VarI;
import claim.structure.impl.Formula;
import example.minbasis.Graph.Node;

public class Reaches extends Formula implements PredicateI{
	final VarI<Graph> _g;
	final VarI<Node> _n, _m;
	
	public Reaches(VarI<Graph> g, VarI<Node> n, VarI<Node> m) {
		super(g, n, m);
		this._g = g;
		this._n = n;
		this._m = m;
	}

	@Override
	public Boolean execute(Map<VarI<?>, Object> env) {
		throw new RuntimeException("example.minbasis.Reaches not implemented!");
	}

}
