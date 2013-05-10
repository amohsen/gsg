package example.lab.saddle;

import lab.impl.Strategy;
import example.syntax.saddlepoint.ExistsY;
import example.syntax.saddlepoint.Saddle;

/** 
	Generated with claim.services.BabyScholarGenerator for Formula example.syntax.saddlepoint.Saddle
 */
public class PerfectLosing extends Strategy{
	private final String name;
	
	public PerfectLosing(String name) {
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
		return 0d;
	}

	/** 
	ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
	 */
	public Double support(ExistsY formula, Double x, Double q){
		return x>0.5?0d:1d;
	}
	
}

