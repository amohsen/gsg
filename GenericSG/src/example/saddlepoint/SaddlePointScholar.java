package example.saddlepoint;

import java.util.Map;
import java.util.Random;

import claim.Compound;
import claim.Formula;
import claim.Quantified;
import claim.Var;
import sg.Scholar;

public class SaddlePointScholar implements Scholar{

	private final String name;
	Random r = new Random();

	public SaddlePointScholar(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Position selectPosition(Formula f, Map<Var<?>, Object> g) {
		return r.nextBoolean()?Position.VERIFIER:Position.FALSIFIER;
	}

	@Override
	public Decision selectSubformula(Compound f, Map<Var<?>, Object> g) {
		return r.nextBoolean()?Decision.LEFT:Decision.RIGHT;
	}

	@Override
	public Object provideValue(Quantified f, Map<Var<?>, Object> g) {
		return r.nextDouble();
	}

}
