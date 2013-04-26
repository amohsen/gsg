package example.minbasis;

import java.util.Collection;

import claim.AndCompound;
import claim.Formula;
import claim.Var;
import example.minbasis.Graph.Node;

public class NInTandExistsMInBReachesN implements AndCompound{
	Var<Collection<Node>> _t;
	Var<Node> _n;
	Var<Collection<Node>> _b;
	Var<Graph> _g;
	
	final Formula left;
	final Formula right;
	private final Var<?>[] params;
	
	
	public NInTandExistsMInBReachesN(Var<Collection<Node>> t, Var<Node> n, Var<Collection<Node>> b, Var<Graph> g) {
		this._t = t;
		this._n = n;
		this._b = b;
		this._g = g;
		this.left = new In<Node, Collection<Node>>(_n, _t);
		this.right = new ExistsMInBRachesN(_g, _n, _b);
		this.params = new Var<?>[]{_t, _n, _b, _g};
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
