package example.syntax.saddlepoint;

import java.util.Map;

import claim.structure.VarI;
import claim.structure.impl.Predicate;

public class Quality extends Predicate{
	private final VarI<Double> _x, _y, _q;
	
	public Quality(VarI<Double> x, VarI<Double> y, VarI<Double> q) {
		super(new VarI[]{x, y, q});
		this._x = x;
		this._y = y;
		this._q = q;
	}

	@Override
	public Boolean execute(Map<VarI<?>, Object> env) {
		double x = (Double) env.get(_x);
		double y = (Double) env.get(_y);
		double q = (Double) env.get(_q);
		return (x*y + (1-x)*(1-y*y)) >= q;
	}

}
