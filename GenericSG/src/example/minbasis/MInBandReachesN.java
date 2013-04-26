package example.minbasis;

import java.util.Collection;

import claim.AndCompound;
import claim.Formula;
import claim.Var;
import example.minbasis.Graph.Node;

public class MInBandReachesN implements AndCompound{
	final Var<Node> _m, _n;
	final Var<Graph> _g;
	final Var<Collection<Node>> _b;
	final Formula left;
	final Formula right;
	
	private Var<?>[] params;
	
	public MInBandReachesN(Var<Node> m, Var<Collection<Node>> b, Var<Node> n, Var<Graph> g) {
		this._m = m;
		this._b = b;
		this._n = n;
		this._g = g;
		this.left = new In<Node, Collection<Node>>(_m, _b);
		this.right = new Reaches(_g, _m, _n);
		this.params = new Var<?>[]{_m, _b, _n, _g};
	}

	@Override
	public Formula getLeft() {
		return left;
	}

	@Override
	public Formula getRight() {
		return right;
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}

}
