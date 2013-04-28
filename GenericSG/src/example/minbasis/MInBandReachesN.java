package example.minbasis;

import java.util.Collection;

import claim.structure.AndCompoundI;
import claim.structure.VarI;
import claim.structure.impl.Compound;
import example.minbasis.Graph.Node;

public class MInBandReachesN extends Compound implements AndCompoundI{

	public MInBandReachesN(VarI<Node> m, VarI<Collection<Node>> b, VarI<Node> n, VarI<Graph> g) {
		super(new In<Node, Collection<Node>>(m, b), new Reaches(g, m, n), m, b, n, g);
	}

}
