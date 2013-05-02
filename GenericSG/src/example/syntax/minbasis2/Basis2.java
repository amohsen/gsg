package example.syntax.minbasis2;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.And;
import claim.structure.impl.Exists;
import claim.structure.impl.Forall;
import claim.structure.impl.Let;
import claim.structure.impl.Var;
import example.syntax.minbasis.Graph;
import example.syntax.minbasis.GraphNodes;
import example.syntax.minbasis.In;
import example.syntax.minbasis.Reaches;

public class Basis2 extends Forall{
	private static final VarI<Graph> G =  new Var<Graph>("g", Graph.class);
	private static final VarI<Collection<Integer>> B =  new Var("b", Collection.class, Integer.class);
	private static final VarI<Integer> N =  new Var<Integer>("n", Integer.class);
	private static final VarI<Collection<Integer>> T =  new Var("t", Collection.class, Integer.class);

	public Basis2(VarI<Graph> g, VarI<Collection<Integer>> b) {
		super(new VarI[]{g, b}, N,
				new Let<Collection<Integer>>(T, new GraphNodes(g),
						new And(
								new In<Integer, Collection<Integer>>(N, T), 
								new ReachableFromB(g, N, b))));
	}
	
	static class ReachableFromB extends Exists{
		private static final VarI<Integer> M = new Var<Integer>("m", Integer.class);
		public ReachableFromB(VarI<Graph> g, VarI<Integer> n, VarI<Collection<Integer>> b) {
			super(new VarI<?>[]{g, n, b}, M, 
					new And(new In<Integer, Collection<Integer>>(M, b), 
							new Reaches(g, M, n)));
		}
	}
	
	/** Convenience Constructor */
	public Basis2() {
		this(G, B);
	}
}
