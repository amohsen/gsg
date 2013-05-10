package lab;

import java.util.Collection;
import java.util.Map;

import lab.history.GameHistoryRecord;
import lab.history.History;
import lab.impl.ScoreBoard;
import claim.structure.FormulaI;
import claim.structure.VarI;

/**
 * 
 * Provides services to the CIM, ClaimEvaluator, ScholarEvaluator
 * 
 * */
public interface LabI {
	
	public GameHistoryRecord playSG(FormulaI formula, Map<VarI<?>, Object> assignment, ScholarI verifier, ScholarI falsifier, boolean verifierForced, boolean falsifierForced);
	public Collection<ScholarI> getScholars();
	public void run();
	public Map<ScholarI, Double> getStrengths();
	public ScoreBoard getScores();
	public History getHistory();
	public Map<VarI<?>, Object> getAssignment();
	public FormulaI getFormula();
	public double getClaimTruthLikelihood();
}