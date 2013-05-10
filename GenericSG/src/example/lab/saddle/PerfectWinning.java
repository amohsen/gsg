package example.lab.saddle;

import lab.impl.Strategy;
import example.syntax.saddlepoint.ExistsY;
import example.syntax.saddlepoint.Saddle;

/** 
	Generated with claim.services.BabyScholarGenerator for Formula example.syntax.saddlepoint.Saddle
 */
public class PerfectWinning extends Strategy{
	private final String name;
	
	public PerfectWinning(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	/** 
	Saddle(q∈Double) := ∀x∈Double:ExistsY(x, q)
	ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
	 */
	public Double support(Saddle formula, Double q){
		return 0.552786;
	}

	/** 
	ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
	 */
	public Double support(ExistsY formula, Double x, Double q){
		if(Math.abs(x - 1) < 1E-5) return x;
		return x/(2-2*x);
	}
	
}

