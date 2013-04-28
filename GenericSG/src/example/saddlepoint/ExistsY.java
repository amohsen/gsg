package example.saddlepoint;

import claim.structure.ExistentiallyQuantifiedI;
import claim.structure.VarI;
import claim.structure.impl.Quantified;
import claim.structure.impl.Var;

public class ExistsY extends Quantified implements ExistentiallyQuantifiedI{
	
	public ExistsY(){
		this(new Var<Double>("x", Double.class), new Var<Double>("q", Double.class));
	}
	
	public ExistsY(VarI<Double> x, VarI<Double> q) {
		this(new Var<Double>("y", Double.class), x, q);
	}
	
	private ExistsY(VarI<Double> y, VarI<Double> x, VarI<Double> q) {
		super(y, new Quality(x, y, q), x, q);
	}

}
