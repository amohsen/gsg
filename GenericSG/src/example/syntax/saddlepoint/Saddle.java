package example.syntax.saddlepoint;

import claim.structure.VarI;
import claim.structure.impl.Forall;
import claim.structure.impl.Var;

public class Saddle extends Forall{
	private static final VarI<Double> X =  new Var<Double>("x", Double.class);
	
	public Saddle(VarI<Double> q) {
		super(new VarI[]{q}, X, new ExistsY(X, q));
	}

	/** Convenience constructor*/
	public Saddle() {
		this(new Var<Double>("q", Double.class));
	}
}
