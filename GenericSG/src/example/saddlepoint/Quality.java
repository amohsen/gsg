package example.saddlepoint;

import java.util.Map;

import claim.Predicate;
import claim.Var;

public class Quality implements Predicate{
	private final Var<Double> _x, _y, _q;
	private final Var<?>[] params;
	
	public Quality(Var<Double> x, Var<Double> y, Var<Double> q) {
		this._x = x;
		this._y = y;
		this._q = q;
		params = new Var[]{_x, _y, _q};
	}

	@Override
	public Boolean execute(Map<Var<?>, Object> env) {
		double x = (Double) env.get(_x);
		double y = (Double) env.get(_y);
		double q = (Double) env.get(_q);
		return (x*y + (1-x)*(1-y*y)) >= q;
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}

}
