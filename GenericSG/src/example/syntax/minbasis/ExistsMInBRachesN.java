package example.syntax.minbasis;

import java.util.Collection;

import claim.structure.VarI;
import claim.structure.impl.Exists;
import claim.structure.impl.Var;

public class ExistsMInBRachesN extends Exists{
	private static final VarI<Integer> M = new Var<Integer>("m", Integer.class);
	
	public ExistsMInBRachesN(VarI<Graph> g, VarI<Integer> n, VarI<Collection<Integer>> b) {
		super(new VarI<?>[]{g, n, b}, M, new MInBandReachesN(M, b, n, g));
	}
	
}
