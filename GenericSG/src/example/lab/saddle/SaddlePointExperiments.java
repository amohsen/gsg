package example.lab.saddle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import lab.CIM;
import lab.ClaimEvaluator;
import lab.LabI;
import lab.ScholarEvaluator;
import lab.ScholarI;
import lab.StrategyI;
import lab.impl.BernoulliStrategy;
import lab.impl.IterativeScholarEvaluator;
import lab.impl.Lab;
import lab.impl.RoundRobinofCAGS;
import lab.impl.Scholar;
import lab.impl.WeightedClaimEvaluator;

import claim.structure.FormulaI;
import claim.structure.VarI;
import claim.structure.impl.Var;
import example.syntax.saddlepoint.Saddle;
import static utils.MathUtils.*;

public class SaddlePointExperiments{

	private static final long SEED = 823636482; 
	private static final Random RANDOM = new Random(SEED);

	/**
	 * CIMs
	 * */
	public static final CIM ALL_GAMES = new RoundRobinofCAGS(RANDOM, 1); 
	public static final CIM THIRD_GAMES = new RoundRobinofCAGS(RANDOM, 0.3334); 

	public static final CIM[] CIMS = new CIM[]{ALL_GAMES, THIRD_GAMES};
	public static final String[] CIM_NAMES = new String[]{"ALL", "THIRD"};


	/**
	 * UEs
	 * */
	public static final ScholarEvaluator SIMPLE_UE = new IterativeScholarEvaluator(0);
	public static final ScholarEvaluator ITERATIVE_UE = new IterativeScholarEvaluator(100);

	public static final ScholarEvaluator[] UES = new ScholarEvaluator[]{SIMPLE_UE, ITERATIVE_UE};
	public static final String[] UE_NAMES = new String[]{"SMPL", "ITER"};


	/**
	 * CEs
	 * */
	public static final ClaimEvaluator UNWEIGHTED_CE = new WeightedClaimEvaluator(true);
	public static final ClaimEvaluator STRENGTH_WEIGHTED_CE = new WeightedClaimEvaluator(false);

	public static final ClaimEvaluator[] CES = new ClaimEvaluator[]{UNWEIGHTED_CE, STRENGTH_WEIGHTED_CE};
	public static final String[] CE_NAMES = new String[]{"UNW8D", "STRW8d"};

	/**
	 * Crowds
	 * */
	public static final StrategyI perfectWinning = new PerfectWinning("Winning");
	public static final StrategyI perfectLosing = new PerfectLosing("Losing");

	public static final Collection<ScholarI> NORMAL = makeCrowd(new double[]{0, 0.1, 0.2, 0.2, 0.3, 0.3, 0.3, 0.4, 0.4, 0.4, 0.4, 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.6, 0.6, 0.6, 0.7, 0.7, 0.8, 0.9, 1});
	public static final Collection<ScholarI> BIMODAL = makeCrowd(new double[]{0, 0.1, 0.1, 0.2, 0.2, 0.2, 0.2, 0.3, 0.3, 0.4, 0.5, 0.6, 0.6, 0.7, 0.7, 0.7, 0.7, 0.8, 0.8, 0.9, 1});
	public static final Collection<ScholarI> UNIFORM = makeCrowd(new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1});
	public static final Collection<ScholarI> SMALL = makeCrowd(new double[]{0, 1});

	public static final Collection<ScholarI>[] CROWDS = (Collection<ScholarI>[]) new Collection[]{BIMODAL, NORMAL, UNIFORM};
	public static final String[] CROWD_NAMES = new String[]{"BIMODAL", "NORMAL", "UNIFORM"};

	public static Collection<ScholarI> makeCrowd(double...skills){
		Collection<ScholarI> scholars = new ArrayList<ScholarI>();
		for (double d : skills) {
			scholars.add(new Scholar(new BernoulliStrategy(perfectWinning, perfectLosing, d, d, RANDOM)));
		}
		return scholars;
	}

	public static void main(String[] args) {

		VarI<Double> q = new Var<Double>("q", Double.class);
		FormulaI f = new Saddle(q);

		//True Claim
		Map<VarI<?>, Object> envt = new HashMap<VarI<?>, Object>();
		envt.put(q, 0.2);

		//False Claim
		Map<VarI<?>, Object> envf = new HashMap<VarI<?>, Object>();
		envf.put(q, 0.75);

		Map<VarI<?>, Object>[] envs = (Map<VarI<?>, Object>[]) new Map[]{envt, envf};
		String[] envNames = new String[]{"True", "False"};
		boolean[] tf = new boolean[]{true, false};

		int iters = 10000;
		
		for (int crowdIndex = 0; crowdIndex < CROWD_NAMES.length; crowdIndex++) {
			for (int envIndex = 0; envIndex < envNames.length; envIndex++) {
				for (int cimIndex = 0; cimIndex < CIMS.length; cimIndex++) {
					for (int ueIndex = 0; ueIndex < UE_NAMES.length; ueIndex++) {
						for (int ceIndex = 0; ceIndex < CE_NAMES.length; ceIndex++) {
							String description = CIM_NAMES[cimIndex]+", "+UE_NAMES[ueIndex]+", "+CE_NAMES[ceIndex]+", "+CROWD_NAMES[crowdIndex]+", "+envNames[envIndex];
							experiment("", description, CIMS[cimIndex], UES[ueIndex], CES[ceIndex], CROWDS[crowdIndex], f, envs[envIndex], tf[envIndex], iters);
						}
					}
				}
			}
		}
	}

	public static void experiment(String name, String description, CIM cim, ScholarEvaluator ue, ClaimEvaluator ce, Collection<ScholarI> crowd, FormulaI formula, Map<VarI<?>, Object> env, boolean isClaimTrue, int iters){
		System.out.println(description);
		double avgClassificationScore = 0;
		double avgInconsistentScholarSkills = 0;
		for (int i = 0; i < iters; i++) {
			LabI lab = new Lab(name, description, cim, ue, ce, crowd, formula, env);		
			lab.run();
			//System.out.println("Estimated Likelihood Claim is true = "+ lab.getClaimTruthLikelihood());
			//System.out.println("Scores ...");
			//System.out.println(lab.getScores());
			//System.out.println("Estimated Strengths "+lab.getStrengths());
			double classificationScore = isClaimTrue?lab.getClaimTruthLikelihood():1-lab.getClaimTruthLikelihood();
			double inconsistencies = evaluateStrengths(lab.getStrengths());
			avgClassificationScore += classificationScore;
			avgInconsistentScholarSkills += inconsistencies;
		}
		avgClassificationScore /= iters;
		avgInconsistentScholarSkills /= iters;
		
		System.out.println("Claim Correctness: "+ avgClassificationScore);
		System.out.println("Inconsistent Scholar Skills: "+ avgInconsistentScholarSkills);
		System.out.println("======");
	}

	private static double evaluateStrengths(Map<ScholarI, Double> strengths){
		double inconsistencies = 0;
		Collection<ScholarI> scholars = strengths.keySet();
		for (ScholarI si : scholars) {
			double siSkill = ((BernoulliStrategy)si.getStrategy()).getPv();
			double siEstimatedSkill = strengths.get(si);
			for (ScholarI sj : scholars) {
				double sjSkill = ((BernoulliStrategy)sj.getStrategy()).getPv();
				double sjEstimatedSkill = strengths.get(sj);
				if((greater(sjSkill, siSkill) && !greater(sjEstimatedSkill, siEstimatedSkill)
						|| (greater(siSkill, sjSkill) && !greater(siEstimatedSkill, sjEstimatedSkill)))){
					inconsistencies++;
				}

			}
		}
		double allPairs = scholars.size() * (scholars.size() -1); 
		return inconsistencies/allPairs;
	}
	
//	public static void main(String[] args) {
//
//		VarI<Double> q = new Var<Double>("q", Double.class);
//		FormulaI f = new Saddle(q);
//
//		//True Claim
//		Map<VarI<?>, Object> envt = new HashMap<VarI<?>, Object>();
//		envt.put(q, 0.2);
//
//		//False Claim
//		Map<VarI<?>, Object> envf = new HashMap<VarI<?>, Object>();
//		envf.put(q, 0.75);
//
//		LabI lab = new Lab("", "", ALL_GAMES, SIMPLE_UE, UNWEIGHTED_CE, UNIFORM, f, envt);		
//		lab.run();
//		System.out.println("Estimated Likelihood Claim is true = "+ lab.getClaimTruthLikelihood());
//		System.out.println("Scores ...");
//		System.out.println(lab.getScores());
//		System.out.println("Estimated Strengths "+lab.getStrengths());
//		System.out.println(evaluateStrengths(lab.getStrengths()));
//		
//		lab = new Lab("", "", ALL_GAMES, SIMPLE_UE, UNWEIGHTED_CE, UNIFORM, f, envf);		
//		lab.run();
//		System.out.println("Estimated Likelihood Claim is true = "+ (1-lab.getClaimTruthLikelihood()));
//		System.out.println("Scores ...");
//		System.out.println(lab.getScores());
//		System.out.println("Estimated Strengths "+lab.getStrengths());
//		System.out.println(evaluateStrengths(lab.getStrengths()));
//	}

}