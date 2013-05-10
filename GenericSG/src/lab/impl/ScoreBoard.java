package lab.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lab.ScholarI;
import lab.history.GameHistoryRecord;
import lab.history.History;

public class ScoreBoard {
	private final Map<ScholarI, Map<ScholarI, Double>> scores = new HashMap<ScholarI, Map<ScholarI,Double>>();
	private final Collection<ScholarI> scholars;

	public ScoreBoard(History history) {
		scholars = history.getScholars();
		for (GameHistoryRecord ghr : history.getRecords()) {
			ScholarI winner = ghr.getWinner();
			ScholarI loser = ghr.getLoser();
			double winnerPayoff = ghr.getPayoff(winner);
			double loserPayoff = ghr.getPayoff(loser);
			add(winner, loser, winnerPayoff);
			add(loser, winner, loserPayoff);
		}
	}

	private void add(ScholarI si, ScholarI sj, double payoff){
		Map<ScholarI, Double> sjs = scores.get(si);
		if(sjs == null){
			sjs = new HashMap<ScholarI, Double>();
			sjs.put(sj, 0d);
			scores.put(si, sjs);
		}
		Double prevScore = sjs.get(sj);
		sjs.put(sj, prevScore==null?payoff:prevScore+payoff);
	}


	public Double getScore(ScholarI si, ScholarI sj){
		try{
			Double val = scores.get(si).get(sj);
			return val==null?0:val;
		}catch(Exception ex){
			return 0.0;
		}
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		for (ScholarI si : scholars) {
			sb.append(si.toString());
			sb.append("\t");
			for (ScholarI sj : scholars) {
				sb.append(getScore(si, sj));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
