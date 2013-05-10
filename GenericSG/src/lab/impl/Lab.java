package lab.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import sg.SemanticGame;

import lab.CIM;
import lab.ClaimEvaluator;
import lab.LabI;
import lab.ScholarEvaluator;
import lab.ScholarI;
import lab.ScholarI.Position;
import lab.history.GameHistoryRecord;
import lab.history.History;
import claim.structure.FormulaI;
import claim.structure.VarI;

/**
 * services to the CIM
 * 
 * */
public class Lab implements LabI {
	String name;
	String description;
	private final CIM cim;
	private final ScholarEvaluator scholarEvaluator;
	private final ClaimEvaluator claimEvaluator;
	private final Collection<ScholarI> scholars;
	private final FormulaI formula;
	private final Map<VarI<?>, Object> assignment;

	public final History history = new History();
	
		public Lab(String name, String description, CIM cim,
			ScholarEvaluator scholarEvaluator, ClaimEvaluator claimEvaluator,
			Collection<ScholarI> scholars, FormulaI formula,
			Map<VarI<?>, Object> assignment) {
		this.name = name;
		this.description = description;
		this.cim = cim;
		this.scholarEvaluator = scholarEvaluator;
		this.claimEvaluator = claimEvaluator;
		this.scholars = scholars;
		this.formula = formula;
		this.assignment = assignment;
	}

	@Override
	public final GameHistoryRecord playSG(FormulaI formula, Map<VarI<?>, Object> assignment, ScholarI verifier, ScholarI falsifier, boolean verifierForced, boolean falsifierForced){
		Position winnerPosition = SemanticGame.SG(formula, assignment, verifier, falsifier);
		GameHistoryRecord ghr = new GameHistoryRecord(verifier, falsifier, winnerPosition == Position.VERIFIER, verifierForced, falsifierForced, formula, assignment);
		history.add(ghr);
		return ghr;
	}
	
	@Override
	public final Collection<ScholarI> getScholars(){
		return Collections.unmodifiableCollection(scholars);
	}
	
	public final void run(){
		cim.run(this);
	}
	
	@Override
	public final History getHistory(){
		return history;
	}
	
	@Override
	public final ScoreBoard getScores(){
		return history.getScoreBoard();
	}
	
	@Override
	public final Map<ScholarI, Double> getStrengths(){
		return scholarEvaluator.getStrength(history);
	}
	
	@Override
	public final double getClaimTruthLikelihood(){
		return claimEvaluator.evaluateClaim(this);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public FormulaI getFormula() {
		return formula;
	}

	public Map<VarI<?>, Object> getAssignment() {
		return assignment;
	}
	
}
