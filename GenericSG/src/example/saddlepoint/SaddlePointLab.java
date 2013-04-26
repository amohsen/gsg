package example.saddlepoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import claim.Formula;
import claim.Var;
import sg.Scholar;
import sg.SemanticGame;

public class SaddlePointLab {
	public static void main(String[] args) {
		Random r = new Random();
		Scholar s1 = new SaddlePointScholar("Scholar1");
		Scholar s2 = new SaddlePointScholar("Scholar2");
		
		Var<Double> q = new Var<Double>("q", Double.class);
		Formula f = new Saddle(q);
		Map<Var<?>, Object> env = new HashMap<Var<?>, Object>();
		env.put(q, r.nextDouble());
		
		SemanticGame.SG(f, env, s1, s2);
	}
}
