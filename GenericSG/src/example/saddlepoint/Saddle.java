package example.saddlepoint;

import claim.Formula;
import claim.UniversallyQuantified;
import claim.Var;

public class Saddle implements UniversallyQuantified{
	final Var<Double> _x = new Var<Double>("x", Double.class);
	final Var<Double> _q;
	final Formula subFormula;
	final Var<?>[] params;
	
	public Saddle() {
		this(new Var<Double>("q", Double.class));
	}
	
	public Saddle(Var<Double> _q) {
		this._q = _q;
		subFormula = new ExistsY(_x, _q);
		this.params = new Var[]{_q};
	}

	@Override
	public Var<?> getVar() {
		return _x;
	}

	@Override
	public Formula getSubFormula() {
		return subFormula;
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}

}
