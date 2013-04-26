package example.minbasis;

import java.util.Collection;

import claim.Formula;
import claim.Function;
import claim.Let;
import claim.Var;
import example.minbasis.Graph.Node;

public class NInGraphNodesAndExistsMInBReachesN implements Let<Collection<Node>> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final Var<Collection<Node>> _t = new Var("t", Collection.class);
	final Var<Graph> _g;
	final Var<Node> _n;
	final Var<Collection<Node>> _b;
	
	final Formula subFormula;
	final Function<Collection<Node>> function;
	
	
	public NInGraphNodesAndExistsMInBReachesN(Var<Graph> g, 
			Var<Collection<Node>> b, Var<Node> n) {
		this._g = g;
		this._n = n;
		this._b = b;
		this.subFormula = new NInTandExistsMInBReachesN(_t, _n, _b, _g);
		this.function = new GraphNodes(_g);
		this.params = new Var<?>[]{_g, _b, _n};
	}

	private final Var<?>[] params; 
	@Override
	public Var<?>[] getParameters() {
		return params;
	}

	@Override
	public Var<Collection<Node>> getVar() {
		return _t;
	}

	@Override
	public Formula getSubFormula() {
		return subFormula;
	}

	@Override
	public Function<Collection<Node>> getFunction() {
		return function;
	}

}
