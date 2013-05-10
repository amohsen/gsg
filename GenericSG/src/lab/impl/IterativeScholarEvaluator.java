package lab.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lab.ScholarEvaluator;
import lab.ScholarI;
import lab.history.GameHistoryRecord;
import lab.history.History;

import static utils.MathUtils.*;

public class IterativeScholarEvaluator implements ScholarEvaluator{
	private static final double EPSILON = 1E-4;
	private final int maxIterations;
	
	public IterativeScholarEvaluator() {
		this(100);
	}
	
	public IterativeScholarEvaluator(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	@Override
	public Map<ScholarI, Double> getStrength(History history) {
		ScoreBoard scores = history.getScoreBoard();
		Collection<ScholarI> scholars = history.getScholars();
		
		Map<ScholarI, Double> strengths = new HashMap<ScholarI, Double>();
		Map<ScholarI, Double> prevStrengths = new HashMap<ScholarI, Double>();
		
		//Init strengths
		for (ScholarI si : scholars) {
			double wins = 0;
			double losses = 0;
			for (ScholarI sj : scholars) {
				wins += scores.getScore(si, sj);
				losses += scores.getScore(sj, si);
			}
			double total = wins + losses;
			strengths.put(si, safedivision(wins, total, 0.5));
		}
		
		for(int iter=0;iter<maxIterations;iter++){
			prevStrengths = strengths;
			strengths = new HashMap<ScholarI, Double>();
			for (ScholarI si : scholars) {
				double wins = 0;
				double losses = 0;
				for (ScholarI sj : scholars) {
					wins += scores.getScore(si, sj) * prevStrengths.get(sj);
					losses += scores.getScore(sj, si) * (1 - prevStrengths.get(sj));
				}
				double total = wins + losses;
				strengths.put(si, safedivision(wins, total, 0.5));
			}
			if(converged(scholars, prevStrengths, strengths)) break;
		}
		return strengths;
	}

	private boolean converged(Collection<ScholarI> scholars, Map<ScholarI, Double> prev, Map<ScholarI, Double> current){
		for (ScholarI scholar : scholars) {
			Double diff = Math.abs(prev.get(scholar) - current.get(scholar));
			if(diff > EPSILON) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		ScholarEvaluator ue = new IterativeScholarEvaluator(20);
		ScholarI sc1 = new Scholar(new Strategy() {
			@Override
			public String getName() {
				return "S1";
			}
		});
		ScholarI sc2 = new Scholar(new Strategy() {
			
			@Override
			public String getName() {
				return "S2";
			}
		});
		ScholarI sc3 = new Scholar(new Strategy() {
			
			@Override
			public String getName() {
				return "S3";
			}
		});
		History history = new History();
		GameHistoryRecord ghr12 = new GameHistoryRecord(sc1, sc2, true, false, false, null, null);
		ghr12.setVerifierPayoff(2);
		ghr12.setFalsifierPayoff(7);
		history.add(ghr12);
		GameHistoryRecord ghr13 = new GameHistoryRecord(sc1, sc3, true, false, false, null, null);
		ghr13.setVerifierPayoff(6);
		ghr13.setFalsifierPayoff(2);
		history.add(ghr13);
		GameHistoryRecord ghr23 = new GameHistoryRecord(sc2, sc3, true, false, false, null, null);
		ghr23.setVerifierPayoff(1);
		ghr23.setFalsifierPayoff(6);
		history.add(ghr23);
		System.out.println(history.getScoreBoard());
		System.out.println(ue.getStrength(history));
		
	}
}
