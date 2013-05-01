package example.lab.saddle;

import java.lang.Double;
import java.util.Random;

import example.syntax.saddlepoint.Saddle;
import example.syntax.saddlepoint.ExistsY;
import lab.Scholar.Position;

/** 
Generated with claim.services.BabyScholarGenerator for Formula example.syntax.saddlepoint.Saddle
 */
public class SaddleScholar{

	private final String name;
	/** Perturbation Level*/
	private final double p;

	public String getName() {
		return name;
	}

	public SaddleScholar(String name, double p) {
		this.name = name;
		this.p = p;
	}
	
	Random r = new Random();

	/** 
	Saddle(q∈Double) := ∀x∈Double:ExistsY(x, q)
	ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
	 */
	public Double support(Saddle formula, Double q){
		return perturb(0.552786);
	}

	/** 
	ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
	 */
	public Double support(ExistsY formula, Double x, Double q){
		if(Math.abs(x - 1) < 1E-5) return perturb(x);
		return perturb(x/(2-2*x));
	}

	/** 
	Saddle(q∈Double) := ∀x∈Double:ExistsY(x, q)
	ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
	 */
	public Position selectPosition(Saddle formula, Double q){
		return perturb(q)>0.61803398875?Position.FALSIFIER:Position.VERIFIER;
	}
	
	double perturb(double v){
		double pv = v + r.nextGaussian() * p;
		if(pv < 0) return 0;
		if(pv > 1) return 1;
		return pv;
	}
}

