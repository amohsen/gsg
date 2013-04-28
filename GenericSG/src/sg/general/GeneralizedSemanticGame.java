package sg.general;

import java.util.Map;

import sg.Scholar;
import sg.Scholar.Decision;
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

public class GeneralizedSemanticGame {
	public static void GS(FormulaI f, 
			Map<VarI<?>, Object> sa, 
			TranslatorFactory transFactory, 
			Scholar ver, Scholar fal, boolean vas /*is the verifier at the source lab?*/){
		Translator trans = transFactory.create();
		Map<VarI<?>, Object> ta = trans.translateParametersToTarget(sa);
		GS(f, sa, ta, trans, ver, fal, vas);
	}
	
	public static void GS(FormulaI f, 
			Map<VarI<?>, Object> sa, Map<VarI<?>, Object> ta, 
			Translator trans, 
			Scholar ver, Scholar fal,
			boolean vas){
		System.out.println("SG for formula: "+f.getClass().getCanonicalName()+". Verifier is "+ ver.getName() + ". Falsifier is "+ fal.getName()+". Verifier is "+ (vas?"":"not ") +"at source.");
		System.out.println("Source assignment is: "+ sa);
		System.out.println("Target assignment is: "+ ta);
		if (f instanceof PredicateI) {
			PredicateI pf = (PredicateI) f;
			System.out.println("Formula is a predicate, evaluating in both structures ...");
			//Execute source predicate in sa
			boolean sResult = pf.execute(sa);
			System.out.println("Predicate "+ pf.getClass().getCanonicalName() +"(" + pf.getParameters() + ") "+(sResult?"holds":"does not hold")+" under assignment "+ sa);
			//Translate source predicate to the target model
			PredicateI tp = trans.translatePredicateToTarget(pf);
			System.out.println("Predicate "+ pf.getClass().getCanonicalName() +"(" + pf.getParameters() + ") "+ " translates to predicate "+ tp.getClass().getCanonicalName()+"(" + tp.getParameters() + ") ");
			//Execute target predicate in ta
			boolean tResult = tp.execute(ta);
			System.out.println("Predicate "+ tp.getClass().getCanonicalName() +"(" + tp.getParameters() + ") "+(tResult?"holds":"does not hold")+" under assignment "+ ta);
			//Check the results
			if(sResult!=tResult){
				//Translation is not ok
				System.out.println("Translator "+ trans.getClass().getCanonicalName() +" is incorrect. Source and translated formulas are not equisatisfiable.");
			}else if(sResult){ //verifier wins
				System.out.println("Scholar "+ ver.getName() + "wins!");
			}else{ // falsifier wins
				System.out.println("Scholar "+ fal.getName() + "wins!");
			}
		}else if (f instanceof NegatedI) {
			NegatedI nf = (NegatedI) f;
			System.out.println("Formula is negated, reversing roles ...");
			GS(nf.getSubFormula(), sa, ta, trans, fal, ver, !vas);
		}else if (f instanceof CompoundI) {
			CompoundI cf = (CompoundI) f;
			Decision d;
			if(cf instanceof AndCompoundI){
				System.out.println("Formula is compounded with and, falsifier choosing subformula ...");
				d = fal.selectSubformula(cf, vas?ta:sa);
				System.out.println("Falsifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}else if(cf instanceof OrCompoundI){
				System.out.println("Formula is compounded with or, verifier choosing subformula ...");
				d = ver.selectSubformula(cf, vas?sa:ta);
				System.out.println("Verifier chose"+ d +" subformula, Continuing with the chosen subformula ...");
			}else{
				throw new RuntimeException("New compound type detected: "+ cf.getClass().getCanonicalName());	
			}
			if(d == Decision.LEFT){
				GS(cf.getLeft(), sa, ta, trans, ver, fal, vas);
			}else{ //RIGHT
				GS(cf.getRight(), sa, ta, trans, ver, fal, vas);
			}
		}else if (f instanceof QuantifiedI) {
			QuantifiedI qf = (QuantifiedI) f;
			Object val;
			VarI<?> sVar = qf.getVar();
			VarI<?> tVar = trans.translateVarToTarget(sVar);
			System.out.println("Source var "+ sVar + " translates to target var "+tVar);
			if(qf instanceof UniversallyQuantifiedI){
				System.out.println("Formula is universally quantified, falsifier providing value ...");
				val = fal.provideValue(qf, vas?ta:sa);
				System.out.println("Falsifier provided: "+ val+ " . Continuing with the subforumula ...");
				if(vas){//fat
					ta.put(tVar, val);
					Object sVal = trans.translateValueToSource(val);
					sa.put(sVar, sVal);
				}else{//fas
					sa.put(sVar, val);
					Object tVal = trans.translateValueToTarget(val);
					ta.put(tVar, tVal);
				}
			}else if (qf instanceof ExistentiallyQuantifiedI){
				System.out.println("Formula is existentially quantified, verifier providing value ...");
				val = ver.provideValue(qf, vas?sa:ta);
				System.out.println("Verifier provided: "+ val+ " . Continuing with the subforumula ...");
				if(vas){
					sa.put(sVar, val);
					Object tVal = trans.translateValueToTarget(val);
					ta.put(tVar, tVal);
				}else{
					ta.put(tVar, val);
					Object sVal = trans.translateValueToSource(val);
					sa.put(sVar, sVal);
				}
			}else{
				throw new RuntimeException("New quantified type detected: "+ qf.getClass().getCanonicalName());
			}			
			GS(qf.getSubFormula(), sa, ta, trans, ver, fal, vas);
		}else if (f instanceof LetI<?>){
			LetI<?> lf = (LetI<?>) f;
			System.out.println("Formula is a let, computing the value to be bound ...");
			//get var and translate it
			VarI<?> sVar = lf.getVar();
			VarI<?> tVar = trans.translateVarToTarget(sVar);
			System.out.println("Source var "+ sVar + " translates to target var "+tVar);

			//get function and translate it			
			FunctionI<?> sfn = lf.getFunction();
			FunctionI<?> tfn = trans.translateFunctionToTarget(sfn);
			System.out.println("Function "+ sfn.getClass().getCanonicalName() +"("+sfn.getParameters()+ " translates to function "+ tfn.getClass().getCanonicalName()+"("+tfn.getParameters());

			//Execute both functions
			Object sRes = sfn.execute(sa);
			Object tRes = tfn.execute(ta);
			System.out.println("Function "+ sfn.getClass().getCanonicalName()+"("+sfn.getParameters()+") executed in environment "+ sa +" and produced "+ sRes );
			System.out.println("Function "+ tfn.getClass().getCanonicalName()+"("+tfn.getParameters()+") executed in environment "+ ta +" and produced "+ tRes );
			
			//Update both environments
			sa.put(sVar, sRes);
			ta.put(tVar, tRes);
			System.out.println("Source Result is bound to"+ sVar);
			System.out.println("Target Result is bound to"+ tVar);
			
			//Continue with the Subformula
			GS(lf.getSubFormula(), sa, ta, trans, ver, fal, vas);
		}else{
			throw new RuntimeException("New formula type detected: "+ f.getClass().getCanonicalName());
		}
	
		
	}
}
