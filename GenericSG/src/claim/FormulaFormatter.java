package claim;

import java.util.Stack;

import example.minbasis.Basis;
import example.saddlepoint.Saddle;

/**
 * 
 * External formatting for Formulas
 * 
 * */
public class FormulaFormatter {
	static String format(Formula f){
		StringBuilder sb = new StringBuilder();
		Stack<Formula> formulas = new Stack<Formula>();
		formulas.push(f);
		boolean first = true;
		while(!formulas.empty()){
			f = formulas.pop();
			if(!first) sb.append("\n");
			if(first) first = false;
			sb.append(f.getClass().getName());
			formatArgumentList(sb, f.getParameters(), true);
			if(f instanceof Predicate){
				sb.append(".");
			}else{
				sb.append(" := ");
				if(f instanceof Quantified){
					Quantified qf = (Quantified) f;
					if (f instanceof ExistentiallyQuantified) {
						sb.append("∃");
					}else if (f instanceof UniversallyQuantified){
						sb.append("∀");
					}else{
						throw new RuntimeException("New quantified type detected: "+ qf.getClass().getName());
					}
					formatVar(sb, qf.getVar(), true);
					sb.append(":");
					formatSubFormula(sb, qf.getSubFormula());
					formulas.push(qf.getSubFormula());
				}else if(f instanceof Compound){
					Compound cf = (Compound) f;
					formatSubFormula(sb, cf.getLeft());
					formulas.push(cf.getLeft());
					if (f instanceof AndCompound) {
						sb.append(" ∧ ");
					}else if (f instanceof OrCompound){
						sb.append(" ∨ ");
					}else{
						throw new RuntimeException("New compound type detected: "+ cf.getClass().getName());
					}
					formatSubFormula(sb, cf.getRight());
					formulas.push(cf.getRight());
				}else if(f instanceof Negated){
					Negated nf = (Negated) f;
					sb.append("¬");
					formatSubFormula(sb, nf.getSubFormula());
					formulas.push(nf.getSubFormula());
				}else if(f instanceof Let){
					Let<?> lf = (Let<?>) f;
					sb.append("let ");
					formatVar(sb, lf.getVar(), true);
					sb.append(" = ");
					formatSubFormula(sb, lf.getFunction());
					sb.append(" in ");
					formatSubFormula(sb, lf.getSubFormula());
					formulas.push(lf.getSubFormula());
				}else{
					throw new RuntimeException("New formula type detected: "+ f.getClass().getName());
				}
			}
		}
		return sb.toString();
	}

	static void formatArgumentList(StringBuilder sb, Var<?>[] params, boolean showTypes){
		sb.append("(");
		for(int i=0;i<params.length-1;i++){
			formatVar(sb, params[i], showTypes);
			sb.append(", ");
		}
		if(params.length > 0){
			formatVar(sb, params[params.length-1], showTypes);
		}
		sb.append(")");
	}	

	static void formatVar(StringBuilder sb, Var<?> var, boolean showTypes){
		sb.append(var.name);
		if(showTypes){
			sb.append("∈");
			sb.append(var.type.getName());
		}
	}

	static void formatSubFormula(StringBuilder sb, Formula f){
		sb.append(f.getClass().getName());
		formatArgumentList(sb, f.getParameters(), false);
	}
	
	public static void main(String[] args) {
		System.out.println(format(new Saddle()));
		System.out.println("====");
		System.out.println(format(new Basis()));
		
	}
}
