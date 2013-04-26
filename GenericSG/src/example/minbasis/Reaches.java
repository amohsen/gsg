package example.minbasis;

import java.util.Map;

import claim.Predicate;
import claim.Var;
import example.minbasis.Graph.Node;

public class Reaches implements Predicate{
	final Var<Graph> _g;
	final Var<Node> _n, _m;
	private final Var<?>[] params;
	
	public Reaches(Var<Graph> g, Var<Node> n, Var<Node> m) {
		this._g = g;
		this._n = n;
		this._m = m;
		this.params = new Var[]{_g, _n, _m};
	}

	@Override
	public Boolean execute(Map<Var<?>, Object> env) {
		throw new RuntimeException("example.minbasis.Reaches not implemented!");
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}
	
}
