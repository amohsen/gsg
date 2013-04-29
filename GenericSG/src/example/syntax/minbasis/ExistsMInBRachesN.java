package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.Exists;
import claim.structure.impl.Var;
import example.syntax.minbasis.Graph.Node;

public class ExistsMInBRachesN extends Exists{
	private static final VarI<Node> M = new Var<Node>("m", Node.class);
	
	public ExistsMInBRachesN(VarI<Graph> g, VarI<Node> n, VarI<Collection<Node>> b) {
		super(new VarI<?>[]{g, n, b}, M, new MInBandReachesN(M, b, n, g));
	}
	
}
