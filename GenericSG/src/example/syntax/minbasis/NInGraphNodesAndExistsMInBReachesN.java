package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.Let;
import claim.structure.impl.Var;
import example.syntax.minbasis.Graph.Node;

public class NInGraphNodesAndExistsMInBReachesN extends Let<Collection<Node>> {
	private static final VarI<Collection<Node>> T = new Var("t", Collection.class, Node.class);
	
	public NInGraphNodesAndExistsMInBReachesN(
			VarI<Graph> g, 
			VarI<Collection<Node>> b, VarI<Node> n) {
		super(new VarI[]{ g, b, n}, T, new GraphNodes(g), new NInTandExistsMInBReachesN(T, n, b, g));
	}
}
