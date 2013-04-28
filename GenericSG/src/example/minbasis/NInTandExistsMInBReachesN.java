package example.minbasis;

import java.util.Collection;

import claim.structure.AndCompoundI;
import claim.structure.VarI;
import claim.structure.impl.Compound;
import example.minbasis.Graph.Node;

public class NInTandExistsMInBReachesN extends Compound implements AndCompoundI{
	
	public NInTandExistsMInBReachesN(VarI<Collection<Node>> t, VarI<Node> n, VarI<Collection<Node>> b, VarI<Graph> g) {
		super(new In<Node, Collection<Node>>(n, t), new ExistsMInBRachesN(g, n, b), t, n, b, g);
	}
	
}
