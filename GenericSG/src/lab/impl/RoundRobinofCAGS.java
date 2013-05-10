package lab.impl;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import claim.structure.FormulaI;
import claim.structure.VarI;

import lab.CIM;
import lab.LabI;
import lab.ScholarI;
import lab.ScholarI.Position;
import lab.history.GameHistoryRecord;

/**
 * 
 * Implements a round robin of Contradiction Agreement Games centered around a given claim
 * 
 * */
public class RoundRobinofCAGS implements CIM {
	private final Random random;
	private final double fraction;
	
	
	public RoundRobinofCAGS(Random random, double fraction) {
		this.random = random;
		this.fraction = fraction;
	}

	public RoundRobinofCAGS() {
		this(new Random(), 1d);
	}

	@Override
	public void run(LabI lab) {
		Map<VarI<?>, Object> env = lab.getAssignment();
		FormulaI f = lab.getFormula();
		Collection<ScholarI> scholars = lab.getScholars();
		for (ScholarI si : scholars) {
			for (ScholarI sj : scholars) {
				if(random.nextDouble() >= fraction) continue;
				if(si!=sj){
					Position pi = si.selectPosition(f, env);
					Position pj = sj.selectPosition(f, env);
					if(pi != pj && random.nextDouble() < fraction){
						ScholarI verifier = pi==Position.VERIFIER?si:sj;
						ScholarI falsifier = pi==Position.FALSIFIER?si:sj;
						GameHistoryRecord ghr = lab.playSG(f, new HashMap<VarI<?>, Object>(env), verifier, falsifier, false, false);
						ghr.setPayoff(ghr.getWinner(), 1);
					}else{ // same position
						//Force j
						ScholarI verifier = pi==Position.VERIFIER?si:sj;
						ScholarI falsifier = pi==Position.VERIFIER?sj:si;
						GameHistoryRecord ghr = lab.playSG(f, new HashMap<VarI<?>, Object>(env), verifier, falsifier, verifier==sj, falsifier==sj);
						if(ghr.isWinner(sj)){
							ghr.setPayoff(sj, 1);
						}
						//Force i
						verifier = pi==Position.VERIFIER?sj:si;
						falsifier = pi==Position.VERIFIER?si:sj;
						ghr = lab.playSG(f, new HashMap<VarI<?>, Object>(env), verifier, falsifier, verifier==si, falsifier==si);
						if(ghr.isWinner(si)){
							ghr.setPayoff(si, 1);
						}
					}					
				}
			}
		}
	}
}
