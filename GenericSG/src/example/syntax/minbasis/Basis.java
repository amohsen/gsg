package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.Forall;
import claim.structure.impl.Var;

public class Basis extends Forall{
	private static final VarI<Integer> N =  new Var<Integer>("n", Integer.class);
	
	public Basis(VarI<Graph> g, VarI<Collection<Integer>> b) {
		super(new VarI[]{g, b}, N, new NInGraphNodesAndExistsMInBReachesN(g, b, N));
	}

	/** Convenience Constructor */
	public Basis() {
		this(new Var<Graph>("g", Graph.class), new Var("b", Collection.class, Integer.class));
	}
}
