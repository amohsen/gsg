package example.lab.saddle;

import java.util.Random;

import lab.Scholar.Position;
import example.syntax.saddlepoint.ExistsY;
import example.syntax.saddlepoint.Saddle;

public class SaddlePointScholar2 {

	Random r = new Random();

	private final String name;

	public SaddlePointScholar2(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public Position selectPosition(Saddle saddle, Double q){
		return r.nextBoolean()?Position.VERIFIER:Position.FALSIFIER;
	}
	
	public Double support(Saddle saddle, Double q){
		return r.nextDouble();
	}
	
	public Double support(ExistsY existsY, Double x, Double q){
		return r.nextDouble();
	}
}
