package example.minbasis;

import java.util.Collection;

import claim.ExistentiallyQuantified;
import claim.Formula;
import claim.Var;
import example.minbasis.Graph.Node;

public class ExistsMInBRachesN implements ExistentiallyQuantified{
	final Var<Node> _m = new Var<Node>("m", Node.class);
	final Var<Graph> _g;
	final Var<Node> _n;
	final Var<Collection<Node>> _b;
	final Formula subFormula;
	private final Var<?>[] params;
	
	public ExistsMInBRachesN(Var<Graph> g, Var<Node> n, Var<Collection<Node>> b) {
		this._g = g;
		this._n = n;
		this._b = b;
		this.subFormula = new MInBandReachesN(_m, _b, _n, _g);
		this.params = new Var<?>[]{_m, _b, _n, _g};
	}
	
	@Override
	public Var<?> getVar() {
		return _m;
	}
	
	@Override
	public Formula getSubFormula() {
		return subFormula;
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}
	
}
