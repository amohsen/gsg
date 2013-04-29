package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.And;
import example.syntax.minbasis.Graph.Node;

public class NInTandExistsMInBReachesN extends And{
	
	public NInTandExistsMInBReachesN(VarI<Collection<Node>> t, VarI<Node> n, VarI<Collection<Node>> b, VarI<Graph> g) {
		super(new VarI[]{t, n, b, g}, new In<Node, Collection<Node>>(n, t), new ExistsMInBRachesN(g, n, b));
	}
	
}
