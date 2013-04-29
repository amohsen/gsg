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
import example.syntax.minbasis.Graph.Node;

public class Basis2 extends Forall{
	private static final VarI<Graph> G =  new Var<Graph>("g", Graph.class);
	private static final VarI<Collection<Node>> B =  new Var("b", Collection.class, Node.class);
	private static final VarI<Node> N =  new Var<Node>("n", Node.class);
	private static final VarI<Collection<Node>> T =  new Var("t", Collection.class, Node.class);

	public Basis2(VarI<Graph> g, VarI<Collection<Node>> b) {
		super(new VarI[]{g, b}, N,
				new Let<Collection<Node>>(T, new GraphNodes(g),
						new And(
								new In<Node, Collection<Node>>(N, T), 
								new ReachableFromB(g, N, b))));
	}
	
	static class ReachableFromB extends Exists{
		private static final VarI<Node> M = new Var<Node>("m", Node.class);
		public ReachableFromB(VarI<Graph> g, VarI<Node> n, VarI<Collection<Node>> b) {
			super(new VarI<?>[]{g, n, b}, M, 
					new And(new In<Node, Collection<Node>>(M, b), 
							new Reaches(g, M, n)));
		}
	}
	
	/** Convenience Constructor */
	public Basis2() {
		this(G, B);
	}
}
