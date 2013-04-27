package claim;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import example.minbasis.Basis;
import example.saddlepoint.Saddle;

/**
 * 
 * External formatting for Formulas
 * 
 * */
public class FormulaFormatter {
	static String format(Formula f, boolean shortName){
		StringBuilder sb = new StringBuilder();
		Stack<Formula> formulas = new Stack<Formula>();
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
			formatClassName(sb, f.getClass(), shortName);
			formatArgumentList(sb, f.getParameters(), true, shortName);
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
					formatVar(sb, qf.getVar(), true, shortName);
					sb.append(":");
					formatSubFormula(sb, qf.getSubFormula(), shortName);
					formulas.push(qf.getSubFormula());
				}else if(f instanceof Compound){
					Compound cf = (Compound) f;
					formatSubFormula(sb, cf.getLeft(), shortName);
					formulas.push(cf.getLeft());
					if (f instanceof AndCompound) {
						sb.append(" ∧ ");
					}else if (f instanceof OrCompound){
						sb.append(" ∨ ");
					}else{
						throw new RuntimeException("New compound type detected: "+ cf.getClass().getName());
					}
					formatSubFormula(sb, cf.getRight(), shortName);
					formulas.push(cf.getRight());
				}else if(f instanceof Negated){
					Negated nf = (Negated) f;
					sb.append("¬");
					formatSubFormula(sb, nf.getSubFormula(), shortName);
					formulas.push(nf.getSubFormula());
				}else if(f instanceof Let){
					Let<?> lf = (Let<?>) f;
					sb.append("let ");
					formatVar(sb, lf.getVar(), true, shortName);
					sb.append(" = ");
					formatSubFormula(sb, lf.getFunction(), shortName);
					sb.append(" in ");
					formatSubFormula(sb, lf.getSubFormula(), shortName);
					formulas.push(lf.getSubFormula());
				}else{
					throw new RuntimeException("New formula type detected: "+ f.getClass().getName());
				}
			}
		}
		return sb.toString();
	}

	static void formatArgumentList(StringBuilder sb, Var<?>[] params, boolean showTypes, boolean shortName){
		sb.append("(");
		for(int i=0;i<params.length-1;i++){
			formatVar(sb, params[i], showTypes, shortName);
			sb.append(", ");
		}
		if(params.length > 0){
			formatVar(sb, params[params.length-1], showTypes, shortName);
		}
		sb.append(")");
	}	

	static void formatVar(StringBuilder sb, Var<?> var, boolean showTypes, boolean shortName){
		sb.append(var.name);
		if(showTypes){
			sb.append("∈");
			formatClassName(sb, var.type, shortName);
		}
	}

	static void formatSubFormula(StringBuilder sb, Formula f, boolean shortName){
		formatClassName(sb, f.getClass(), shortName);
		formatArgumentList(sb, f.getParameters(), false, shortName);
	}
	
	static void formatClassName(StringBuilder sb, Class<?> klass, boolean shortName){
		sb.append(shortName?klass.getSimpleName():klass.getName());
	}
	public static void main(String[] args) {
		System.out.println(format(new Saddle(), true));
		System.out.println("====");
		System.out.println(format(new Basis(), true));

	}
}
