package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.And;

public class NInTandExistsMInBReachesN extends And{
	
	public NInTandExistsMInBReachesN(VarI<Collection<Integer>> t, VarI<Integer> n, VarI<Collection<Integer>> b, VarI<Graph> g) {
		super(new VarI[]{t, n, b, g}, new In<Integer, Collection<Integer>>(n, t), new ExistsMInBRachesN(g, n, b));
	}
	
}
