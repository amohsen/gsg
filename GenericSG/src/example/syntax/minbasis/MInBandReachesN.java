package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.And;
import example.syntax.minbasis.Graph.Node;

public class MInBandReachesN extends And{

	public MInBandReachesN(VarI<Node> m, VarI<Collection<Node>> b, VarI<Node> n, VarI<Graph> g) {
		super(new VarI[]{m, b, n, g}, new In<Node, Collection<Node>>(m, b), new Reaches(g, m, n));
	}

}
