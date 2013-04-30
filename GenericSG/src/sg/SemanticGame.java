package sg;

import java.util.Map;

import lab.Scholar;
import lab.Scholar.Decision;

import claim.structure.AndCompoundI;
import claim.structure.CompoundI;
import claim.structure.ExistentiallyQuantifiedI;
import claim.structure.FormulaI;
import claim.structure.FunctionI;
import claim.structure.LetI;
import claim.structure.NegatedI;
import claim.structure.OrCompoundI;
import claim.structure.PredicateI;
import claim.structure.QuantifiedI;
import claim.structure.UniversallyQuantifiedI;
import claim.structure.VarI;

public class SemanticGame {
	
	public static void SG(FormulaI f, Map<VarI<?>, Object> a, Scholar ver, Scholar fal){
		System.out.println("SG for formula: "+f.getClass().getCanonicalName()+". Verifier is "+ ver.getName() + ". Falsifier is "+ fal.getName()+".");
		System.out.println("Assignment is: "+ a);
		if (f instanceof PredicateI) {
			PredicateI pf = (PredicateI) f;
			System.out.println("Formula is a predicate, evaluating ...");
			if(pf.execute(a)){
				System.out.println("Predicate "+ pf.getClass().getCanonicalName() + " holds under assignment "+ a);
				System.out.println("Scholar "+ ver.getName() + " wins!");
			}else{
				System.out.println("Predicate "+ pf.getClass().getCanonicalName() + " does not hold under assignment "+ a);
				System.out.println("Scholar "+ fal.getName() + " wins!");
			}
		}else if (f instanceof NegatedI) {
			NegatedI nf = (NegatedI) f;
			System.out.println("Formula is negated, reversing roles ...");
			SG(nf.getSubFormula(), a, fal, ver);
		}else if (f instanceof AndCompoundI) {
			CompoundI cf = (CompoundI) f;
			Decision d;
			if(cf instanceof AndCompoundI){
				System.out.println("Formula is compounded with and, falsifier choosing subformula ...");
				d = fal.selectSubformula(cf, a);
				System.out.println("Falsifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}else if(cf instanceof OrCompoundI){
				System.out.println("Formula is compounded with or, verifier choosing subformula ...");
				d = ver.selectSubformula(cf, a);
				System.out.println("Verifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}else{
				throw new RuntimeException("New compound type detected: "+ cf.getClass().getCanonicalName());
			}
			if(d == Decision.LEFT){
				SG(cf.getLeft(), a, ver, fal);
			}else{ //RIGHT
				SG(cf.getRight(), a, ver, fal);
			}			
		}else if (f instanceof QuantifiedI) {
			QuantifiedI qf = (QuantifiedI) f;
			Object val;
			if(qf instanceof UniversallyQuantifiedI){
				System.out.println("Formula is universally quantified, falsifier providing value ...");
				val = fal.provideValue(qf, a);
				System.out.println("Falsifier provided: "+ val+ " . Continuing with the subforumula ...");
			}else if (qf instanceof ExistentiallyQuantifiedI) {
				System.out.println("Formula is existentially quantified, verifier providing value ...");
				val = ver.provideValue(qf, a);
				System.out.println("Verifier provided: "+ val+ " . Continuing with the subforumula ...");
			}else{
				throw new RuntimeException("New quantified type detected: "+ qf.getClass().getCanonicalName());
			}			
			a.put(qf.getVar(), val);
			SG(qf.getSubFormula(), a , ver, fal);
		}else if (f instanceof LetI<?>){
			LetI<?> lf = (LetI<?>) f;
			System.out.println("Formula is a let, computing the value to be bound ...");
			FunctionI<?> fun = lf.getFunction();
			Object res = fun.execute(a);
			System.out.println("Function "+ fun.getClass().getCanonicalName()+"("+fun.getArguments()+") executed in environment "+ a +" and produced "+ res );
			a.put(lf.getVar(), res);
			System.out.println("Result is bound to "+ lf.getVar());
			SG(lf.getSubFormula(), a, ver, fal);
		}else{
			throw new RuntimeException("New formula type detected: "+ f.getClass().getCanonicalName());
		}
	}
}
