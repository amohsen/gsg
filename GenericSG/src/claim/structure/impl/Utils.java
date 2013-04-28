package claim.structure.impl;

import java.util.Arrays;
import java.util.Comparator;

import claim.structure.VarI;
import static claim.services.FormulaFormatter.*;

public class Utils {
	
	static void checkShadowing(VarI<?> var, VarI<?>[] params){
		for(VarI<?> p : params){
			if(p.getName().equals(var.getName())){
				System.err.println("Warning: Variable "+ formatVar(var, true, false) + " shadows parameter "+ formatVar(p, true, false));
			}
		}
	}
	
	static void checkEqualParameters(VarI<?>[] params){
		VarI<?>[] paramsClone = params.clone();
		Arrays.sort(paramsClone, new Comparator<VarI<?>>() {
			public int compare(VarI<?> o1, VarI<?> o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for(int i = 0 ; i<paramsClone.length -1 ; i++){
			if(paramsClone[i].getName().equals(paramsClone[i+1].getName())){
				throw new RuntimeException ("Parameter "+ formatVar(paramsClone[i], true, false) + " is equal to parameter " + formatVar(paramsClone[i], true, false));
			}
		}
	}
	
}
