package example.syntax.saddlepoint;

import claim.structure.VarI;
import claim.structure.impl.Exists;
import claim.structure.impl.Var;

public class ExistsY extends Exists{
	private static final VarI<Double> Y =  new Var<Double>("y", Double.class);
	
	public ExistsY(VarI<Double> x, VarI<Double> q) {
		super(new VarI[]{x, q}, Y, new Quality(x, Y, q));
	}

}
