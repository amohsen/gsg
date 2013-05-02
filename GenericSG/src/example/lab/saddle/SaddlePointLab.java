package example.lab.saddle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import utils.Pair;

import lab.Scholar;
import lab.Scholar.Position;
import lab.impl.ScholarAdapter;

import claim.structure.FormulaI;
import claim.structure.VarI;
import claim.structure.impl.Var;
import example.syntax.saddlepoint.Saddle;
import static sg.SemanticGame.SG;

public class SaddlePointLab {
	private static final double EPSILON = 1E-4;

	public static void main(String[] args) {
		VarI<Double> q = new Var<Double>("q", Double.class);
		FormulaI f = new Saddle(q);
		Map<VarI<?>, Object> env = new HashMap<VarI<?>, Object>();
		double qval = 0.61803398875;
		boolean claimIsFalse = qval>0.61803398875; 
		env.put(q, qval);
		
		double[] meanPerturbation = new double[10];
		double[] avgEstMissClassification = new double[10];

		for(int exp = 0 ; exp<10; exp++){
			double mp = exp * 0.1;
			double missClassification = 0;
			for(int i = 0; i< 400 ;i++){
				Pair<Scholar[], Double> generatedScholars = generateScholars(7, mp, 0.3); 
				Scholar[] scholars = generatedScholars.first;
				meanPerturbation[exp] = generatedScholars.second;
				History history = roundRobinCAG(f, env, scholars);
				double[] strengths = scholarStrength(history.scores);
				double estimatedCorrectness = estimateCorrectness(history.records, strengths);
				missClassification += claimIsFalse?estimatedCorrectness:(1 - estimatedCorrectness);
			}
			avgEstMissClassification[exp] = missClassification/400;
		}

		System.out.print("Mean perturbation \t MissClassification \n");
		for(int i=0; i<meanPerturbation.length; i++){
			System.out.println(meanPerturbation[i]+"\t\t"+avgEstMissClassification[i]);
		}
/*
Mean perturbation 	 MissClassification 
0.0		0.0
0.1		0.019060012911834064
0.2		0.0562416574236469
0.30000000000000004		0.09227524978463598
0.4		0.11974372566125335
0.5		0.14052049438719375
0.6000000000000001		0.17334970327262333
0.7000000000000001		0.1794614298400804
0.8		0.19813887378390194
0.9		0.2311555679193773
 * */
		
		/** nextdouble()
		 * 
		 Mean perturbation 	 MissClassification 
0.0		0.14539441501070796
0.1		0.13521172884253857
0.2		0.13631778668695224
0.30000000000000004		0.112426577651262
0.4		0.1442776891295244
0.5		0.1142885682136046
0.6000000000000001		0.13899009424779954
0.7000000000000001		0.13399343745636216
0.8		0.1116383826843271
0.9		0.12359072997501475

		 * */
		/** nextGauss()
		 Mean perturbation 	 MissClassification 
0.0		0.36073224020262495
0.1		0.3510440720607685
0.2		0.39431790347715584
0.30000000000000004		0.39592228181450045
0.4		0.38469750833437
0.5		0.3780593009878399
0.6000000000000001		0.34693182984938103
0.7000000000000001		0.364217187659655
0.8		0.362759905453431
0.9		0.35259277961425134

		 * */
		//		System.out.println("==============");
		//		System.out.println("==============");
		//		for(int i = 0 ; i < scholars.length ; i++){
		//			for(int j = 0 ; j < scholars.length ; j++){
		//				System.out.print(history.scores[i][j]);
		//				System.out.print(", ");
		//			}
		//			System.out.println("");
		//		}
		//		System.out.println("==============");
		//		System.out.println("==============");
		//		for(int i = 0 ; i < scholars.length ; i++){
		//			System.out.println(scholars[i].getName()+" has sterngth "+ strengths[i]);
		//		}
		//		System.out.println("===============");
		//		System.out.println("Estimated Correctness : "+ estimatedCorrectness);
	}
	
	static Pair<Scholar[],Double> generateScholars(int numScholars, double mean, double sd){
		Random r = new Random();
		Scholar[] scholars = new Scholar[numScholars];
		double sumPerturbation = 0;
		for(int i=1;i<numScholars;i++){
			double perturbation = mean + r.nextGaussian() * sd;
			sumPerturbation += perturbation;
			scholars[i] = new ScholarAdapter(new SaddleScholar("PS"+perturbation, perturbation ));
		}
		scholars[0] = new ScholarAdapter(new SaddleScholar("PSperfect", 0 ));
		double avgPerturbation = sumPerturbation/numScholars;
		return new Pair(scholars, avgPerturbation);
	}

	static History roundRobinCAG(FormulaI f, Map<VarI<?>, Object> env, Scholar[] scholars){
		int numScholars = scholars.length;
		Collection<HistoryRecord> records = new ArrayList<HistoryRecord>();
		int[][] scores = new int[numScholars][];
		for (int i = 0 ; i<numScholars; i++){ 
			scores[i] = new int[numScholars];
		}

		for(int i = 0 ; i < numScholars ; i++){
			for(int j = 0 ; j < numScholars ; j++){
				if(i==j) continue;
				Position pi = scholars[i].selectPosition(f, env);
				Position pj = scholars[j].selectPosition(f, env);
				if(pi != pj){
					Scholar winner = SG(f, env, pi==Position.VERIFIER?scholars[i]:scholars[j], pi==Position.FALSIFIER?scholars[i]:scholars[j]);
					if(winner == scholars[i]){
						scores[i][j]++;
						records.add(new HistoryRecord(i, false, j, false, pi));
					}else{
						scores[j][i]++;
						records.add(new HistoryRecord(j, false, i, false, pj));
					}
				}else{ // same position
					//Force j
					Scholar winnerT1 = SG(f, env, pi==Position.VERIFIER?scholars[i]:scholars[j], pi==Position.VERIFIER?scholars[j]:scholars[i]);
					if(winnerT1 == scholars[j]){
						scores[j][i]++;
						records.add(new HistoryRecord(j, true, i, false, pj));
					}else{
						records.add(new HistoryRecord(i, false, j, true, pi));
					}
					//Force i
					Scholar winnerT2 = SG(f, env, pi==Position.VERIFIER?scholars[j]:scholars[i], pi==Position.VERIFIER?scholars[i]:scholars[j]);
					if(winnerT2 == scholars[i]){
						scores[i][j]++;
						records.add(new HistoryRecord(i, true, j, false, pi));
					}else{
						records.add(new HistoryRecord(j, false, i, true, pj));
					}
				}
			}
		}
		return new History(records, scores);
	}

	static class History{
		Collection<HistoryRecord> records;
		int[][] scores;
		public History(Collection<HistoryRecord> records, int[][] scores) {
			this.records = records;
			this.scores = scores;
		}		
	}

	static class HistoryRecord{
		int winnerScholarId;
		boolean forcedWinner;
		int loserScholarId;
		boolean forcedLoser;
		Position winnerPosition;
		public HistoryRecord(int winnerScholarId, boolean forcedWinner,
				int loserScholarId, boolean forcedLoser, Position winnerPosition) {
			this.winnerScholarId = winnerScholarId;
			this.forcedWinner = forcedWinner;
			this.loserScholarId = loserScholarId;
			this.forcedLoser = forcedLoser;
			this.winnerPosition = winnerPosition;
		}

	}

	static double[] scholarStrength(int[][] scores){
		int numScholars = scores.length;
		double[] prevStrengths = new double[numScholars];
		double[] strengths = new double[numScholars];
		for(int i = 0 ; i<numScholars ; i++){
			double wins = 0;
			double losses = 0;
			for(int j = 0 ; j<numScholars ; j++){
				wins += scores[i][j];
				losses += scores[j][i];
			}
			double total = wins + losses;
			if(total < EPSILON){
				strengths[i] = 0.5;					
			}else{
				strengths[i] = wins / total;
			}
		}
		int iter=0;
		do{
			prevStrengths = strengths;
			strengths = new double[numScholars];
			for(int i = 0 ; i<numScholars ; i++){
				double wins = 0;
				double losses = 0;
				for(int j = 0 ; j<numScholars ; j++){
					wins += (scores[i][j] * prevStrengths[j]);
					losses += (scores[j][i] * (1 - prevStrengths[j]));
				}
				double total = wins + losses;
				if(total < EPSILON){
					strengths[i] = 0.5;					
				}else{
					strengths[i] = wins / total;
				}
			}
			iter++;
			if(iter>100){
				System.out.println("to many iterations");
				break;
			}
		}while(!converged(prevStrengths, strengths));
		return strengths;
	}

	static boolean converged(double[] prev, double[] current){
		for(int i = 0 ; i<prev.length; i++){
			Double diff = Math.abs(prev[i] - current[i]);
			if(diff > EPSILON) return false;
		}
		return true;
	}

	static double estimateCorrectness(Collection<HistoryRecord> records, double[] strengths){
		double cT = 0;
		double cF = 0;

		for (HistoryRecord record : records) {
			if(!record.forcedWinner){
				double evidence = strengths[record.loserScholarId];
				if(record.winnerPosition == Position.VERIFIER){
					cT += evidence;
				}else{
					cF += evidence;
				}
			}
		}

		double total = cT + cF;
		if(total < EPSILON){
			return 0.5;					
		}else{
			return cT / total;
		}

	}
}
