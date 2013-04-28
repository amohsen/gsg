package example.saddlepoint;

import java.util.Map;
import java.util.Random;

import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;
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
	public Position selectPosition(FormulaI f, Map<VarI<?>, Object> g) {
		return r.nextBoolean()?Position.VERIFIER:Position.FALSIFIER;
	}

	@Override
	public Decision selectSubformula(CompoundI f, Map<VarI<?>, Object> g) {
		return r.nextBoolean()?Decision.LEFT:Decision.RIGHT;
	}

	@Override
	public Object provideValue(QuantifiedI f, Map<VarI<?>, Object> g) {
		return r.nextDouble();
	}

}
