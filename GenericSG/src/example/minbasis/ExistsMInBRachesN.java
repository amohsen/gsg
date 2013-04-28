package example.minbasis;

import java.util.Collection;

import claim.structure.ExistentiallyQuantifiedI;
import claim.structure.VarI;
import claim.structure.impl.Quantified;
import claim.structure.impl.Var;
import example.minbasis.Graph.Node;

public class ExistsMInBRachesN extends Quantified implements ExistentiallyQuantifiedI{
	
	public ExistsMInBRachesN(VarI<Graph> g, VarI<Node> n, VarI<Collection<Node>> b) {
		this(new Var<Node>("m", Node.class), g, n, b);
	}
	
	private ExistsMInBRachesN(VarI<Node> m, VarI<Graph> g, VarI<Node> n, VarI<Collection<Node>> b) {
		super(m, new MInBandReachesN(m, b, n, g), g, n, b);
	}
	
}
