package example.minbasis;

import java.util.Collection;

import claim.structure.UniversallyQuantifiedI;
import claim.structure.VarI;
import claim.structure.impl.Quantified;
import claim.structure.impl.Var;
import example.minbasis.Graph.Node;

public class Basis extends Quantified implements UniversallyQuantifiedI{

	public Basis() {
		this(new Var<Graph>("g", Graph.class), new Var("b", Collection.class, Node.class));
	}
	public Basis(VarI<Graph> g, VarI<Collection<Node>> b) {
		this(new Var<Node>("n", Node.class), g, b);
	}
	
	public Basis(VarI<Node> n, VarI<Graph> g, VarI<Collection<Node>> b) {
		super(n, new NInGraphNodesAndExistsMInBReachesN(g, b, n), g, b);
	}
}
