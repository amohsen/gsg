package example.saddlepoint;

import claim.ExistentiallyQuantified;
import claim.Formula;
import claim.Var;

public class ExistsY implements ExistentiallyQuantified{
	final Var<Double> _y = new Var<Double>("y", Double.class);
	final Var<Double> _x, _q;
	final Formula subFormula;
	private final Var<?>[] params;
	
	public ExistsY(Var<Double> x, Var<Double> q) {
		this._x = x;
		this._q = q;
		this.subFormula = new Quality(_x, _y, _q);
		this.params = new Var[]{_x, _q};
	}

	@Override
	public Var<?> getVar() {
		return _y;
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
