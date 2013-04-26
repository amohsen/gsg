package claim;

public interface Let<T> extends Formula{
	Var<T> getVar();
	Function<T> getFunction();
	Formula getSubFormula();
}
