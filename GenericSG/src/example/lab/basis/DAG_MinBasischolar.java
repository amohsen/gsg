package example.lab.basis;

import java.util.Collection;
import java.lang.Integer;

import example.syntax.minbasis.Graph;
import example.lab.basis.Formula.MinBasis;
import example.lab.basis.Formula.HasBasisSize;
import lab.ScholarI.Position;

/** 
	Generated with claim.services.BabyScholarGenerator for Formula example.lab.basis.Formula$MinBasis
 */
public class DAG_MinBasischolar{

	private final String name;

	public DAG_MinBasischolar(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	/** 
		HasBasisSize(g∈Graph, n∈Integer) := ∃b∈Collection<Integer>:(Size(b, n) ∧ Basis(g, b))
	 */
	public Collection<Integer> support(HasBasisSize formula, Graph g, Integer n){
		return g.srcNodes();
	}

	/** 
		MinBasis(g∈Graph, n∈Integer) := HasBasisSize(g, n) ∧ ¬(let nm1∈Integer = Sub1(n) in HasBasisSize(g, nm1))
		HasBasisSize(g∈Graph, n∈Integer) := ∃b∈Collection<Integer>:(Size(b, n) ∧ Basis(g, b))
		HasBasisSize(g∈Graph, nm1∈Integer) := ∃b∈Collection<Integer>:(Size(b, nm1) ∧ Basis(g, b))
	 */
	public Position selectPosition(MinBasis formula, Graph g, Integer n){
		return Position.create(g.srcNodes().size() == n);
	}

	/** 
		HasBasisSize(g∈Graph, n∈Integer) := ∃b∈Collection<Integer>:(Size(b, n) ∧ Basis(g, b))
	 */
	public Position selectPosition(HasBasisSize formula, Graph g, Integer n){
		return Position.create(g.srcNodes().size() >= n);
	}
}

