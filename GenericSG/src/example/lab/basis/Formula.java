package example.lab.basis;

import java.util.Collection;
import java.util.Map;

import claim.services.BabyScholarGenerator;
import claim.structure.FormulaI;
import claim.structure.VarI;
import claim.structure.impl.And;
import claim.structure.impl.Exists;
import claim.structure.impl.Function;
import claim.structure.impl.Let;
import claim.structure.impl.Not;
import claim.structure.impl.Predicate;
import claim.structure.impl.Var;
import example.syntax.minbasis.Graph;

public class Formula {
	private static final VarI<Graph> g = new Var<Graph>("g", Graph.class);
	private static final VarI<Integer> n = new Var<Integer>("n", Integer.class);
	private static final VarI<Integer> nm1 = new Var<Integer>("nm1", Integer.class);

	public static final FormulaI formula = new MinBasis(g, n);

	public static class MinBasis extends And{

		public MinBasis(VarI<Graph> g, VarI<Integer> n) {
			super(new VarI[]{g, n}, new HasBasisSize(g, n), new Not(new Let<Integer>(nm1, new Sub1(n), new HasBasisSize(g, nm1))));
		}
		
	}
	public static class HasBasisSize extends Exists{
		private static final VarI<Collection<Integer>> b = new Var("b", Collection.class, Integer.class);

		public HasBasisSize(VarI<Graph> g, VarI<Integer> n) {
			super(new VarI[]{g, n}, b, 
					new And(new Size<Integer>(b, n), 
							new Basis(g, b)));
		}
	}	

	public static class Sub1 extends Function<Integer>{
		private final VarI<Integer> _n ;

		public Sub1(VarI<Integer> n) {
			super(new VarI[]{n});
			this._n = n;
		}

		@Override
		public Integer execute(Map<VarI<?>, Object> env) {
			Integer n = (Integer) env.get(_n);
			return n - 1;
		}
	}

	public static class Size<E> extends Predicate{

		private final VarI<Collection<E>> _col;
		private final VarI<Integer> _n;

		public Size(VarI<Collection<E>> col, VarI<Integer> n ) {
			super(new VarI[]{col, n});
			this._col = col;
			this._n = n;
		}

		@Override
		public Boolean execute(Map<VarI<?>, Object> env) {
			Collection<?> col = (Collection<?>) env.get(_col);
			Integer n = (Integer) env.get(_n);
			return col.size() == n;
		}

	}

	public static class Basis extends Predicate{
		private final VarI<Graph> _g;
		private final VarI<Collection<Integer>> _b;

		public Basis(VarI<Graph> g, VarI<Collection<Integer>> b) {
			super(new VarI[]{g, b});
			this._g = g;
			this._b = b;
		}

		@Override
		public Boolean execute(Map<VarI<?>, Object> env) {
			Graph g = (Graph) env.get(_g);
			Collection<Integer> b = (Collection<Integer>) env.get(_b);
			return g.isBasis(b);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(BabyScholarGenerator.genetateBabyScholar(formula, "MinBasisScholar", "example.lab.basis"));
	}

}
