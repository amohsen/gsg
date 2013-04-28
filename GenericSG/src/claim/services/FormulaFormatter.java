package claim.services;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import claim.structure.AndCompoundI;
import claim.structure.CompoundI;
import claim.structure.ExistentiallyQuantifiedI;
import claim.structure.FormulaI;
import claim.structure.LetI;
import claim.structure.NegatedI;
import claim.structure.OrCompoundI;
import claim.structure.PredicateI;
import claim.structure.QuantifiedI;
import claim.structure.UniversallyQuantifiedI;
import claim.structure.VarI;

import example.minbasis.Basis;
import example.saddlepoint.Saddle;

/**
 * 
 * External formatting for Formulas
 * 
 * */
public class FormulaFormatter {
	public static String format(FormulaI f, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		Stack<FormulaI> formulas = new Stack<FormulaI>();
		formulas.push(f);
		Set<Class<?>> printed = new HashSet<Class<?>>();
		boolean first = true;
		outer: while(!formulas.empty()){
			do{
				if(formulas.empty()) break outer;
				f = formulas.pop();
			}while(printed.contains(f.getClass()));
			printed.add(f.getClass());
			if(!first) sb.append("\n");
			if(first) first = false;
			formatClassName(f.getClass(), shortName, sb);
			formatArgumentList(f.getParameters(), true, shortName, sb);
			if(f instanceof PredicateI){
				sb.append(".");
			}else{
				sb.append(" := ");
				if(f instanceof QuantifiedI){
					QuantifiedI qf = (QuantifiedI) f;
					if (f instanceof ExistentiallyQuantifiedI) {
						sb.append("∃");
					}else if (f instanceof UniversallyQuantifiedI){
						sb.append("∀");
					}else{
						throw new RuntimeException("New quantified type detected: "+ qf.getClass().getName());
					}
					formatVar(qf.getVar(), true, shortName, sb);
					sb.append(":");
					formatSubFormula(qf.getSubFormula(), shortName, sb);
					formulas.push(qf.getSubFormula());
				}else if(f instanceof CompoundI){
					CompoundI cf = (CompoundI) f;
					formatSubFormula(cf.getLeft(), shortName, sb);
					formulas.push(cf.getLeft());
					if (f instanceof AndCompoundI) {
						sb.append(" ∧ ");
					}else if (f instanceof OrCompoundI){
						sb.append(" ∨ ");
					}else{
						throw new RuntimeException("New compound type detected: "+ cf.getClass().getName());
					}
					formatSubFormula(cf.getRight(), shortName, sb);
					formulas.push(cf.getRight());
				}else if(f instanceof NegatedI){
					NegatedI nf = (NegatedI) f;
					sb.append("¬");
					formatSubFormula(nf.getSubFormula(), shortName, sb);
					formulas.push(nf.getSubFormula());
				}else if(f instanceof LetI){
					LetI<?> lf = (LetI<?>) f;
					sb.append("let ");
					formatVar(lf.getVar(), true, shortName, sb);
					sb.append(" = ");
					formatSubFormula(lf.getFunction(), shortName, sb);
					sb.append(" in ");
					formatSubFormula(lf.getSubFormula(), shortName, sb);
					formulas.push(lf.getSubFormula());
				}else{
					throw new RuntimeException("New formula type detected: "+ f.getClass().getName());
				}
			}
		}
		return optSB.length==0?sb.toString():null;
	}

	public static String formatArgumentList(VarI<?>[] params, boolean showTypes, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];		
		sb.append("(");
		for(int i=0;i<params.length-1;i++){
			formatVar(params[i], showTypes, shortName, sb);
			sb.append(", ");
		}
		if(params.length > 0){
			formatVar(params[params.length-1], showTypes, shortName, sb);
		}
		sb.append(")");
		return optSB.length==0?sb.toString():null;
	}	

	public static String formatVar(VarI<?> var, boolean showTypes, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		sb.append(var.getName());
		if(showTypes){
			sb.append("∈");
			formatClassName(var.getType(), shortName, sb);
			Class<?>[] typeParams = var.getTypeParams();
			if(typeParams.length > 0){
				sb.append("<");
				for (Class<?> tp : typeParams) {
					formatClassName(tp, shortName, sb);
				}
				sb.append(">");
			}
		}
		return optSB.length==0?sb.toString():null;
	}

	public static String formatSubFormula(FormulaI f, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		formatClassName(f.getClass(), shortName, sb);
		formatArgumentList(f.getParameters(), false, shortName, sb);
		return optSB.length==0?sb.toString():null;
	}
	
	public static String formatClassName(Class<?> klass, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		sb.append(shortName?klass.getSimpleName():klass.getName());
		return optSB.length==0?sb.toString():null;
	}
	public static void main(String[] args) {
		System.out.println(format(new Saddle(), true));
		System.out.println("====");
		System.out.println(format(new Basis(), true));

	}
}
