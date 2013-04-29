package claim.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import claim.structure.FormulaI;
import claim.structure.FunctionI;
import claim.structure.VarI;
import example.syntax.minbasis.Basis;
import example.syntax.minbasis2.Basis2;
import example.syntax.saddlepoint.Saddle;

public class Utils {

	public static void checkShadowing(VarI<?> var, VarI<?>[] args){
		for(VarI<?> p : args){
			if(p.getName().equals(var.getName())){
				System.err.println("Warning: Variable "+ formatVar(var, true, false) + " shadows parameter "+ formatVar(p, true, false));
			}
		}
	}

	public static boolean checkEqualArguments(VarI<?>[] args){
		VarI<?>[] argsClone = args.clone();
		Arrays.sort(argsClone, new Comparator<VarI<?>>() {
			public int compare(VarI<?> o1, VarI<?> o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for(int i = 0 ; i<argsClone.length -1 ; i++){
			if(argsClone[i].getName().equals(argsClone[i+1].getName())){
				return true;
			}
		}
		return false;
	}

	public static String formatClassName(Class<?> klass, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		sb.append(shortName?klass.getSimpleName():klass.getName());
		return optSB.length==0?sb.toString():null;
	}

	public static String formatArgumentList(VarI<?>[] args, boolean showTypes, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];		
		sb.append("(");
		for(int i=0;i<args.length-1;i++){
			formatVar(args[i], showTypes, shortName, sb);
			sb.append(", ");
		}
		if(args.length > 0){
			formatVar(args[args.length-1], showTypes, shortName, sb);
		}
		sb.append(")");
		return optSB.length==0?sb.toString():null;
	}	

	public static String formatVar(VarI<?> var, boolean showTypes, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		sb.append(var.getName());
		if(showTypes){
			sb.append("\u2208");
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

	public static String formatLHS(FormulaI f, boolean showTypes, boolean shortName, StringBuilder...optSB){
		StringBuilder sb = optSB.length==0?new StringBuilder():optSB[0];
		formatClassName(f.getClass(), shortName, sb);
		formatArgumentList(f.getArguments(), showTypes, shortName, sb);
		return optSB.length==0?sb.toString():null;
	}

	public static boolean inline(FormulaI f){
		return (f instanceof FunctionI<?>) || 
				(f.getArguments() == null) ||
				checkEqualArguments(f.getArguments());
	}

	public static Collection<FormulaI> AllSubformulas(FormulaI f){
		Collection<FormulaI> acc = new ArrayList<FormulaI>();
		AllSubformulas(f, acc);
		return acc;
	}

	private static void AllSubformulas(FormulaI f, Collection<FormulaI> acc){
		acc.add(f);
		for (FormulaI sf : f.getSubformulas()) {
			AllSubformulas(sf, acc);
		}
	}
}
