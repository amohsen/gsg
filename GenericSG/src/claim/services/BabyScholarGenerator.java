package claim.services;

import static claim.services.Utils.AllSubformulas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lab.Scholar.Position;
import claim.structure.CompoundI;
import claim.structure.FormulaI;
import claim.structure.PredicateI;
import claim.structure.QuantifiedI;
import claim.structure.VarI;
import example.syntax.saddlepoint.Saddle;

public class BabyScholarGenerator {

	public static String genetateBabyScholar(FormulaI f, String className, String...pkg){
		Collection<FormulaI> subFormulas = AllSubformulas(f);
		
		Collection<QuantifiedI> toBeSupportedFormulas = new ArrayList<QuantifiedI>();
		for (FormulaI formula : subFormulas) {
			if (formula instanceof QuantifiedI) {
				toBeSupportedFormulas.add((QuantifiedI) formula);
			}
		}
		
		Collection<FormulaI> toBeDecidedFormula = new ArrayList<FormulaI>();
		toBeDecidedFormula.add(f);
		for (FormulaI formula : subFormulas) {
			if (formula instanceof CompoundI) {
				CompoundI cf = (CompoundI) formula;
				if(!(cf.getLeft() instanceof PredicateI) && !(cf.getRight() instanceof PredicateI)){
					toBeDecidedFormula.add(cf.getLeft());
				}
			}
		}

		Set<Class<?>> imports = new HashSet<Class<?>>();
		for (QuantifiedI qf : toBeSupportedFormulas) {
			imports.add(qf.getClass());
			imports.add(qf.getVar().getType());
			for (VarI<?> arg : qf.getArguments()) {
				imports.add(arg.getType());
			}
		}
		
		for (FormulaI formula : toBeDecidedFormula) {
			imports.add(formula.getClass());
			for (VarI<?> arg : formula.getArguments()) {
				imports.add(arg.getType());
			}
		}
		
		StringBuilder sb = new StringBuilder();
		if(pkg.length != 0) {
			sb.append("package ");
			sb.append(pkg[0]);
			sb.append(";\n\n");
		}
		
		for (Class<?> imp : imports) {
			sb.append("import ");
			sb.append(imp.getName());
			sb.append(";\n");
		}
		sb.append("import lab.Scholar.Position;\n");
		sb.append("\n");
		
		sb.append("/** \n");
		sb.append("Generated with claim.services.BabyScholarGenerator for Formula "+ f.getClass().getName());
		sb.append("\n */\n");

		sb.append("public class ");
		sb.append(className);
		sb.append("{\n\n");
		
		sb.append("private final String name;\n\n");

		sb.append("public ");
		sb.append(className);
		sb.append("(String name) {\n");
		sb.append("this.name = name;\n }\n");

		sb.append("public String getName() {\n return name;\n}\n");
		
		for (QuantifiedI qf : toBeSupportedFormulas) {
			generateMethod(qf, "support", qf.getVar().getType(), qf.getClass(), "formula", qf.getArguments(), sb);
		}
		for (FormulaI formula : toBeDecidedFormula) {
			generateMethod(formula, "selectPosition", Position.class, formula.getClass(), "formula", formula.getArguments(), sb);
		}
		sb.append("}\n");
		return sb.toString();
	}
	private static void generateMethod(FormulaI formula, String name, Class<?> returnType, Class<?> firstArgType, String firstArgName, VarI<?>[] args, StringBuilder sb){
		sb.append("/** \n");
		formula.format(true, sb);
		sb.append(" */\n");
		sb.append("public "+ returnType.getSimpleName()+ " "+ name+ "(");
		
		sb.append(firstArgType.getSimpleName());
		sb.append(" ");
		sb.append(firstArgName);
		for(int i = 0 ; i < args.length ; i++){
			VarI<?> arg = args[i];
			sb.append(", ");
			sb.append(arg.getType().getSimpleName());
			sb.append(" ");
			sb.append(arg.getName());
		}
		sb.append("){\n\treturn null;\n}\n\n");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(genetateBabyScholar(new Saddle(), "SaddleScholar", "example.lab.saddle"));
	}

}
