package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.Let;
import claim.structure.impl.Var;

public class NInGraphNodesAndExistsMInBReachesN extends Let<Collection<Integer>> {
	private static final VarI<Collection<Integer>> T = new Var("t", Collection.class, Integer.class);
	
	public NInGraphNodesAndExistsMInBReachesN(
			VarI<Graph> g, 
			VarI<Collection<Integer>> b, VarI<Integer> n) {
		super(new VarI[]{ g, b, n}, T, new GraphNodes(g), new NInTandExistsMInBReachesN(T, n, b, g));
	}
}
