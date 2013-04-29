package example.syntax.minbasis;

import java.util.Map;

import claim.structure.VarI;
import claim.structure.impl.Predicate;
import example.syntax.minbasis.Graph.Node;

public class Reaches extends Predicate{
	final VarI<Graph> _g;
	final VarI<Node> _n, _m;
	
	public Reaches(VarI<Graph> g, VarI<Node> n, VarI<Node> m) {
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
