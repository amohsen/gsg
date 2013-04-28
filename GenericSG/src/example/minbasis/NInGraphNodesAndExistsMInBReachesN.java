package example.minbasis;

import java.util.Collection;

import claim.structure.LetI;
import claim.structure.VarI;
import claim.structure.impl.Let;
import claim.structure.impl.Var;
import example.minbasis.Graph.Node;

public class NInGraphNodesAndExistsMInBReachesN extends Let<Collection<Node>> implements LetI<Collection<Node>> {

	public NInGraphNodesAndExistsMInBReachesN(VarI<Graph> g, 
			VarI<Collection<Node>> b, VarI<Node> n) {
		this(new Var("t", Collection.class), g, b, n);
	}

	public NInGraphNodesAndExistsMInBReachesN(
			VarI<Collection<Node>> t, 
			VarI<Graph> g, 
			VarI<Collection<Node>> b, VarI<Node> n) {
		super(t, new NInTandExistsMInBReachesN(t, n, b, g), new GraphNodes(g), g, b, n);
	}
}
