package sg;

import sg.Scholar.Decision;
import claim.Compound;
import claim.Compound.Connective;
import claim.Formula;
import claim.Negated;
import claim.Predicate;
import claim.Quantified;
import claim.Quantified.Quantifier;

public class SemanticGame {
	
	public static void SG(Formula f, Assignment a, Structure s, Scholar ver, Scholar fal){
		System.out.println("SG for formula: "+f.getId()+". Verifier is "+ ver.getName() + ". Falsifier is "+ fal.getName()+".");
		System.out.println("Assignment is: "+ a);
		if (f instanceof Predicate) {
			Predicate pf = (Predicate) f;
			System.out.println("Formula is a predicate, evaluating ...");
			if(s.executePredicate(a, pf)){
				System.out.println("Predicate "+ pf.name + " holds under assignment "+ a);
				System.out.println("Scholar "+ ver.getName() + "wins!");
			}else{
				System.out.println("Predicate "+ pf.name + " does not hold under assignment "+ a);
				System.out.println("Scholar "+ fal.getName() + "wins!");
			}
		}else if (f instanceof Negated) {
			Negated nf = (Negated) f;
			System.out.println("Formula is negated, reversing roles ...");
			SG(nf.subformula, a, s, fal, ver);
		}else if (f instanceof Compound) {
			Compound cf = (Compound) f;
			if(cf.Connective == Connective.AND){
				System.out.println("Formula is compounded with and, falsifier choosing subformula ...");
				Decision d = fal.selectSubformula(cf, a);
				System.out.println("Falsifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
				if(d == Decision.LEFT){
					SG(cf.left, a, s, ver, fal);
				}else{ //RIGHT
					SG(cf.right, a, s, ver, fal);
				}
			}else{ //Or Connective
				System.out.println("Formula is compounded with or, verifier choosing subformula ...");
				Decision d = ver.selectSubformula(cf, a);
				System.out.println("Verifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
				if(d == Decision.LEFT){
					SG(cf.left, a, s, ver, fal);
				}else{ //RIGHT
					SG(cf.right, a, s, ver, fal);
				}
			}
		}else if (f instanceof Quantified) {
			Quantified qf = (Quantified) f;
			String val;
			if(qf.quantifier == Quantifier.UNIVERSAL){
				System.out.println("Formula is universally quantified, falsifier providing value ...");
				val = fal.provideValue(qf, a);
				System.out.println("Falsifier provided: "+ val+ " . Continuing with the subforumula ...");
			}else{ //EXISTENTIAL
				System.out.println("Formula is existentially quantified, verifier providing value ...");
				val = ver.provideValue(qf, a);
				System.out.println("Verifier provided: "+ val+ " . Continuing with the subforumula ...");
			}			
			a.add(qf.var, qf.type, val);
			SG(qf.subformula, a , s, ver, fal);
		}
	}
}
