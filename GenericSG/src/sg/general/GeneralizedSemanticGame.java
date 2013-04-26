package sg.general;

import sg.Assignment;
import sg.Structure;
import sg.Scholar;
import sg.Scholar.Decision;
import claim.Compound;
import claim.Formula;
import claim.Negated;
import claim.Predicate;
import claim.Quantified;
import claim.Compound.Connective;
import claim.Quantified.Quantifier;

public class GeneralizedSemanticGame {
	public static void GS(Formula f, 
			Assignment sa, 
			Structure ss, Structure ts, 
			TranslatorFactory transFactory, 
			Scholar ver, Scholar fal, boolean vas /*is the verifier at the source lab?*/){
		Translator trans = transFactory.create();
		Assignment ta = trans.translateClaimParameters(f.getId(), sa);
		GS(f, sa, ta, ss, ts, trans, ver, fal, vas);
	}
	
	public static void GS(Formula f, 
			Assignment sa, Assignment ta, 
			Structure ss, Structure ts, 
			Translator trans, 
			Scholar ver, Scholar fal,
			boolean vas){
		System.out.println("SG for formula: "+f.getId()+". Verifier is "+ ver.getName() + ". Falsifier is "+ fal.getName()+". Verifier is "+ (vas?"":"not ") +"at source. Source is interpreted in structure "+ss.getClass().getCanonicalName()+". Target is interpreted in structure "+ts.getClass().getCanonicalName()+".");
		System.out.println("Source assignment is: "+ sa);
		System.out.println("Target assignment is: "+ ta);
		if (f instanceof Predicate) {
			Predicate pf = (Predicate) f;
			System.out.println("Formula is a predicate, evaluating in both structures ...");
			//Execute source predicate in sa
			boolean sResult = ss.executePredicate(sa, pf);
			System.out.println("Predicate "+ pf.name + " "+(sResult?"holds":"does not hold")+" under assignment "+ sa);
			//Translate source predicate to the target model
			Predicate tp = trans.translateSrcPredicate(pf);
			System.out.println("Predicate "+ pf.name + " translates to predicate "+ tp.name);
			//Execute target predicate in ta
			boolean tResult = ts.executePredicate(ta, tp);
			System.out.println("Predicate "+ tp.name + " "+(tResult?"holds":"does not hold")+" under assignment "+ ta);
			//Check the results
			if(sResult!=tResult){
				//Translation is not ok
				System.out.println("Translator "+ trans.getClass().getCanonicalName() +" is incorrect. Source and translated formulas are not equisatisfiable.");
			}else if(sResult){ //verifier wins
				System.out.println("Scholar "+ ver.getName() + "wins!");
			}else{ // falsifier wins
				System.out.println("Scholar "+ fal.getName() + "wins!");
			}
		}else if (f instanceof Negated) {
			Negated nf = (Negated) f;
			System.out.println("Formula is negated, reversing roles ...");
			GS(nf.subformula, sa, ta, ss, ts, trans, fal, ver, !vas);
		}else if (f instanceof Compound) {
			Compound cf = (Compound) f;
			Decision d;
			if(cf.Connective == Connective.AND){
				System.out.println("Formula is compounded with and, falsifier choosing subformula ...");
				d = fal.selectSubformula(cf, vas?ta:sa);
				System.out.println("Falsifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}else{ //Or Connective
				System.out.println("Formula is compounded with or, verifier choosing subformula ...");
				d = ver.selectSubformula(cf, vas?sa:ta);
				System.out.println("Verifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}
			if(d == Decision.LEFT){
				GS(cf.left, sa, ta, ss, ts, trans, ver, fal, vas);
			}else{ //RIGHT
				GS(cf.right, sa, ta, ss, ts, trans, ver, fal, vas);
			}
		}else if (f instanceof Quantified) {
			Quantified qf = (Quantified) f;
			String val;
			String tType = trans.translateTypeToTarget(qf.type);
			System.out.println("Source type "+ qf.type + " translates to target type "+tType);
			if(qf.quantifier == Quantifier.UNIVERSAL){
				System.out.println("Formula is universally quantified, falsifier providing value ...");
				val = fal.provideValue(qf, vas?ta:sa);
				System.out.println("Falsifier provided: "+ val+ " . Continuing with the subforumula ...");
				if(vas){//fat
					ta.add(qf.var, tType, val);
					String sVal = trans.translateValueToSource(qf.id, val);
					sa.add(qf.var, qf.type, sVal);
				}else{//fas
					sa.add(qf.var, qf.type, val);
					String tVal = trans.translateValueToTarget(qf.id, val);
					ta.add(qf.var, tType, tVal);
				}
			}else{ //EXISTENTIAL
				System.out.println("Formula is existentially quantified, verifier providing value ...");
				val = ver.provideValue(qf, vas?sa:ta);
				System.out.println("Verifier provided: "+ val+ " . Continuing with the subforumula ...");
				if(vas){
					sa.add(qf.var, qf.type, val);
					String tVal = trans.translateValueToTarget(qf.id, val);
					ta.add(qf.var, tType, tVal);
				}else{
					ta.add(qf.var, tType, val);
					String sVal = trans.translateValueToSource(qf.id, val);
					sa.add(qf.var, qf.type, sVal);
				}
			}
			GS(qf.subformula, sa, ta, ss, ts, trans, ver, fal, vas);

		}
	
		
	}
}
