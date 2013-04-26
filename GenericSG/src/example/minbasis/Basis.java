package example.minbasis;

import java.util.Collection;

import claim.Formula;
import claim.UniversallyQuantified;
import claim.Var;
import example.minbasis.Graph.Node;

public class Basis implements UniversallyQuantified{
	final Var<Node> _n = new Var<Node>("n", Node.class);
	final Var<Graph> _g;
	final Var<Collection<Node>> _b;

	final Formula subFormula;
	private final Var<?>[] params;

	public Basis(Var<Graph> g, Var<Collection<Node>> b) {
		this._g = g;
		this._b = b;
		this.subFormula = new NInGraphNodesAndExistsMInBReachesN(_g, _b, _n);
		this.params = new Var<?>[]{_g, _b};
	}

	@Override
	public Var<?> getVar() {
		return _n;
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
