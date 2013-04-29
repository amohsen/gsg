package claim.structure.impl;

import claim.structure.PredicateI;
import claim.structure.VarI;

public abstract class Predicate extends Function<Boolean> implements PredicateI{

	public Predicate(VarI<?>[] args) {
		super(args);
	}

}
