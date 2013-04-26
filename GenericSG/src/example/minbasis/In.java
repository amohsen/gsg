package example.minbasis;

import java.util.Collection;
import java.util.Map;

import claim.Predicate;
import claim.Var;

public class In<E, T extends Collection<E>> implements Predicate{
	final Var<E> _element;
	final Var<T> _set;
	private final Var<?>[] params;
	
	public In(Var<E> _element, Var<T> _set) {
		this._element = _element;
		this._set = _set;
		this.params = new Var[]{_element, _set};
	}

	@Override
	public Boolean execute(Map<Var<?>, Object> env) {
		Object element = env.get(_element);
		Collection<?> set = (Collection<?>) env.get(_set);
		
		return set.contains(element);
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}

}
