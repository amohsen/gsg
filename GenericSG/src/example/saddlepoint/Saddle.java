package example.saddlepoint;

import claim.structure.UniversallyQuantifiedI;
import claim.structure.VarI;
import claim.structure.impl.Quantified;
import claim.structure.impl.Var;

public class Saddle extends Quantified implements UniversallyQuantifiedI{
	public Saddle() {
		this(new Var<Double>("q", Double.class));
	}
	
	public Saddle(VarI<Double> q){
		this(new Var<Double>("x", Double.class), q);
	}
	
	private Saddle(VarI<Double> x, VarI<Double> q) {
		super(x,new ExistsY(x, q), q);
	}

}
