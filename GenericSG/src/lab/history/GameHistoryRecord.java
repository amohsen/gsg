package lab.history;

import java.util.Map;

import lab.ScholarI;

import claim.structure.FormulaI;
import claim.structure.VarI;

public class GameHistoryRecord {
	private final ScholarI verifier, falsifier;
	private final boolean verifierWins, verifierForced, falsifierForced;
	private final FormulaI formula;
	private final Map<VarI<?>, Object> assignment;
	
	private double verifierPayoff, falsifierPayoff;

	public GameHistoryRecord(ScholarI verifier, ScholarI falsifier,
			boolean verifierWins, boolean verifierForced,
			boolean falsifierForced, FormulaI formula,
			Map<VarI<?>, Object> assignment) {
		this.verifier = verifier;
		this.falsifier = falsifier;
		this.verifierWins = verifierWins;
		this.verifierForced = verifierForced;
		this.falsifierForced = falsifierForced;
		this.formula = formula;
		this.assignment = assignment;
	}
	public double getVerifierPayoff() {
		return verifierPayoff;
	}
	public void setVerifierPayoff(double verifierPayoff) {
		this.verifierPayoff = verifierPayoff;
	}
	public double getFalsifierPayoff() {
		return falsifierPayoff;
	}
	public void setFalsifierPayoff(double falsifierPayoff) {
		this.falsifierPayoff = falsifierPayoff;
	}
	public ScholarI getVerifier() {
		return verifier;
	}
	public ScholarI getFalsifier() {
		return falsifier;
	}
	public boolean isVerifierWins() {
		return verifierWins;
	}
	public boolean isVerifierForced() {
		return verifierForced;
	}
	public boolean isFalsifierForced() {
		return falsifierForced;
	}
	public FormulaI getFormula() {
		return formula;
	}
	public Map<VarI<?>, Object> getAssignment() {
		return assignment;
	}
	public boolean isWinner(ScholarI scholar){
		return (scholar==verifier && isVerifierWins()) || (scholar==falsifier && !isVerifierWins());
	}
	public void setPayoff(ScholarI scholar, double payoff){
		if(scholar == verifier) verifierPayoff = payoff;
		if(scholar == falsifier) falsifierPayoff = payoff;
	}
	public ScholarI getWinner(){
		return isVerifierWins()?verifier:falsifier;
	}
	public ScholarI getLoser() {
		return isVerifierWins()?falsifier:verifier;
	}
	public Double getPayoff(ScholarI scholar){
		if(isVerifier(scholar)) return getVerifierPayoff();
		if(isFalsifier(scholar)) return getFalsifierPayoff();
		return null;
	}
	public boolean isVerifier(ScholarI scholar){
		return scholar == verifier;
	}
	public boolean isFalsifier(ScholarI scholar){
		return scholar == falsifier;
	}
	public boolean isForced(ScholarI scholar){
		return (scholar==verifier && verifierForced) 
				|| (scholar==falsifier && falsifierForced);
	}
}