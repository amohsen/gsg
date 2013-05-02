package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.And;

public class MInBandReachesN extends And{

	public MInBandReachesN(VarI<Integer> m, VarI<Collection<Integer>> b, VarI<Integer> n, VarI<Graph> g) {
		super(new VarI[]{m, b, n, g}, new In<Integer, Collection<Integer>>(m, b), new Reaches(g, m, n));
	}

}
