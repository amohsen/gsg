package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.Forall;
import claim.structure.impl.Var;
import example.syntax.minbasis.Graph.Node;

public class Basis extends Forall{
	private static final VarI<Node> N =  new Var<Node>("n", Node.class);
	
	public Basis(VarI<Graph> g, VarI<Collection<Node>> b) {
		super(new VarI[]{g, b}, N, new NInGraphNodesAndExistsMInBReachesN(g, b, N));
	}

	/** Convenience Constructor */
	public Basis() {
		this(new Var<Graph>("g", Graph.class), new Var("b", Collection.class, Node.class));
	}
}
