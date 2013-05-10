package lab.impl;

import java.util.Map;
import java.util.Random;

import lab.StrategyI;
import claim.structure.QuantifiedI;
import claim.structure.UniversallyQuantifiedI;
import claim.structure.VarI;

public class BernoulliStrategy implements StrategyI{

	private final StrategyI perfectPositive, perfectNegative;
	private final double pv, pf;
	private Random random;
	
	
	public BernoulliStrategy(StrategyI perfectPositive,
			StrategyI perfectNegative, double pv, double pf, Random random) {
		this.perfectPositive = perfectPositive;
		this.perfectNegative = perfectNegative;
		this.pv = pv;
		this.pf = pf;
		this.random = random;
	}

	@Override
	public String getName() {
		return "Bernoulli["+perfectPositive.getName()+", "+perfectNegative.getName()+" , "+pv+", "+pf+"]";
	}

	@Override
	public Object provideValue(QuantifiedI f, Map<VarI<?>, Object> g) {
		if(f instanceof UniversallyQuantifiedI){ // falsification
			if(random.nextDouble() < pf){
				return perfectPositive.provideValue(f, g);
			}else{
				return perfectNegative.provideValue(f, g);
			}
		}else{ // verification
			if(random.nextDouble() < pv){
				return perfectPositive.provideValue(f, g);
			}else{
				return perfectNegative.provideValue(f, g);
			}
		}
	}

	public double getPv() {
		return pv;
	}

	public double getPf() {
		return pf;
	}
	
}
