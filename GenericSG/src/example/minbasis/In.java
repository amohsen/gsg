package example.minbasis;

import java.util.Collection;
import java.util.Map;

import claim.structure.PredicateI;
import claim.structure.VarI;
import claim.structure.impl.Formula;

public class In<E, T extends Collection<E>> extends Formula implements PredicateI{
	final VarI<E> _element;
	final VarI<T> _set;
	
	public In(VarI<E> element, VarI<T> set) {
		super(element, set);
		this._element = element;
		this._set = set;
	}

	@Override
	public Boolean execute(Map<VarI<?>, Object> env) {
		Object element = env.get(_element);
		Collection<?> set = (Collection<?>) env.get(_set);
		
		return set.contains(element);
	}

}
