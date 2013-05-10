package lab;

import java.util.Map;

import lab.history.History;

public interface ScholarEvaluator {
	public Map<ScholarI, Double> getStrength(History history);
}
