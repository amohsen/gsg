package lab.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import claim.structure.FormulaI;
import lab.ClaimEvaluator;
import lab.LabI;
import lab.ScholarI;
import lab.history.GameHistoryRecord;
import lab.history.History;

import static utils.MathUtils.*;

public class WeightedClaimEvaluator implements ClaimEvaluator{
	private final boolean useUniformWeights;
	
	public WeightedClaimEvaluator(boolean useUniformWeights) {
		this.useUniformWeights = useUniformWeights;
	}

	@Override
	public double evaluateClaim(LabI lab){
		History history = lab.getHistory();
		Map<ScholarI, Double> weights = useUniformWeights? uniformWeights(lab.getScholars()): strengthsAsWeights(lab) ;
		double cT = 0;
		double cF = 0;
		for (GameHistoryRecord ghr : history.getRecords()) {
			ScholarI winner = ghr.getWinner();
			if(!ghr.isForced(winner)){
				if(ghr.isVerifierWins()){
					cT += weights.get(ghr.getFalsifier());
				}else{
					cF += weights.get(ghr.getVerifier());
				}
			}
		}
		return safedivision(cT, cT+cF, 0.5);
	}
	
	/**
	 * All scholars have the same weights
	 * */
	private Map<ScholarI, Double> uniformWeights(Collection<ScholarI> scholars) {
		Map<ScholarI, Double> weights = new HashMap<ScholarI, Double>();
		for (ScholarI scholar : scholars) {
			weights.put(scholar, 1d);
		}
		return weights;
	}
	
	private Map<ScholarI, Double> strengthsAsWeights(LabI lab) {
		return lab.getStrengths();
	}
	
}
