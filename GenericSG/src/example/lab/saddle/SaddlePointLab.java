package example.lab.saddle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lab.Scholar;
import lab.impl.ScholarAdapter;

import claim.structure.FormulaI;
import claim.structure.VarI;
import claim.structure.impl.Var;
import example.syntax.saddlepoint.Saddle;
import sg.SemanticGame;

public class SaddlePointLab {
	public static void main(String[] args) {
		Random r = new Random();
		Scholar s1 = new SaddlePointScholar("Scholar1");
		Scholar s2 = new ScholarAdapter(new SaddleScholar("asdf"));
		
		VarI<Double> q = new Var<Double>("q", Double.class);
		FormulaI f = new Saddle(q);
		Map<VarI<?>, Object> env = new HashMap<VarI<?>, Object>();
		env.put(q, r.nextDouble());
		
		SemanticGame.SG(f, env, s1, s2);
	}
}
