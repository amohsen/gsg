package sg;

import java.util.Map;

import sg.Scholar.Decision;
import claim.AndCompound;
import claim.Compound;
import claim.ExistentiallyQuantified;
import claim.Formula;
import claim.Function;
import claim.Let;
import claim.Negated;
import claim.OrCompound;
import claim.Predicate;
import claim.Quantified;
import claim.UniversallyQuantified;
import claim.Var;

public class SemanticGame {
	
	public static void SG(Formula f, Map<Var<?>, Object> a, Scholar ver, Scholar fal){
		System.out.println("SG for formula: "+f.getClass().getCanonicalName()+". Verifier is "+ ver.getName() + ". Falsifier is "+ fal.getName()+".");
		System.out.println("Assignment is: "+ a);
		if (f instanceof Predicate) {
			Predicate pf = (Predicate) f;
			System.out.println("Formula is a predicate, evaluating ...");
			if(pf.execute(a)){
				System.out.println("Predicate "+ pf.getClass().getCanonicalName() + " holds under assignment "+ a);
				System.out.println("Scholar "+ ver.getName() + "wins!");
			}else{
				System.out.println("Predicate "+ pf.getClass().getCanonicalName() + " does not hold under assignment "+ a);
				System.out.println("Scholar "+ fal.getName() + "wins!");
			}
		}else if (f instanceof Negated) {
			Negated nf = (Negated) f;
			System.out.println("Formula is negated, reversing roles ...");
			SG(nf.getSubFormula(), a, fal, ver);
		}else if (f instanceof AndCompound) {
			Compound cf = (Compound) f;
			Decision d;
			if(cf instanceof AndCompound){
				System.out.println("Formula is compounded with and, falsifier choosing subformula ...");
				d = fal.selectSubformula(cf, a);
				System.out.println("Falsifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}else if(cf instanceof OrCompound){
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
		}else if (f instanceof Quantified) {
			Quantified qf = (Quantified) f;
			Object val;
			if(qf instanceof UniversallyQuantified){
				System.out.println("Formula is universally quantified, falsifier providing value ...");
				val = fal.provideValue(qf, a);
				System.out.println("Falsifier provided: "+ val+ " . Continuing with the subforumula ...");
			}else if (qf instanceof ExistentiallyQuantified) {
				System.out.println("Formula is existentially quantified, verifier providing value ...");
				val = ver.provideValue(qf, a);
				System.out.println("Verifier provided: "+ val+ " . Continuing with the subforumula ...");
			}else{
				throw new RuntimeException("New quantified type detected: "+ qf.getClass().getCanonicalName());
			}			
			a.put(qf.getVar(), val);
			SG(qf.getSubFormula(), a , ver, fal);
		}else if (f instanceof Let<?>){
			Let<?> lf = (Let<?>) f;
			System.out.println("Formula is a let, computing the value to be bound ...");
			Function<?> fun = lf.getFunction();
			Object res = fun.execute(a);
			System.out.println("Function "+ fun.getClass().getCanonicalName()+"("+fun.getParameters()+") executed in environment "+ a +" and produced "+ res );
			a.put(lf.getVar(), res);
			System.out.println("Result is bound to "+ lf.getVar());
			SG(lf.getSubFormula(), a, ver, fal);
		}else{
			throw new RuntimeException("New formula type detected: "+ f.getClass().getCanonicalName());
		}
	}
}
