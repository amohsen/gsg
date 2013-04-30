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

public SaddleScholar(String name) {
this.name = name;
 }
public String getName() {
 return name;
}
Random r = new Random();

/** 
Saddle(q∈Double) := ∀x∈Double:ExistsY(x, q)
ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
 */
public Double support(Saddle formula, Double q){
	return r.nextDouble();
}

/** 
ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
 */
public Double support(ExistsY formula, Double x, Double q){
	return r.nextDouble();
}

/** 
Saddle(q∈Double) := ∀x∈Double:ExistsY(x, q)
ExistsY(x∈Double, q∈Double) := ∃y∈Double:Quality(x, y, q)
 */
public Position selectPosition(Saddle formula, Double q){
	return r.nextBoolean()?Position.VERIFIER:Position.FALSIFIER;
}

}

