package example.lab.basis;

import java.util.ArrayList;
import java.util.Collection;
import java.lang.Integer;

import utils.Pair;
import example.syntax.minbasis.Graph;
import example.lab.basis.Formula.MinBasis;
import example.lab.basis.Formula.HasBasisSize;
import lab.ScholarI.Position;

/** 
	Generated with claim.services.BabyScholarGenerator for Formula example.lab.basis.Formula$MinBasis
 */
public class MinBasisScholar{

	private final String name;

	public MinBasisScholar(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	/** 
		HasBasisSize(g∈Graph, n∈Integer) := ∃b∈Collection<Integer>:(Size(b, n) ∧ Basis(g, b))
	 */
	public Collection<Integer> support(HasBasisSize formula, Graph g, Integer n){
		Pair<Graph, int[]> sccs = g.stronglyConnectedComponentDag();
		Graph sccd = sccs.first;
		int[] map = sccs.second;
		Collection<Integer> srcs = sccd.srcNodes();
		Collection<Integer> basis = new ArrayList<Integer>();
		for (int s : srcs) {
			basis.add(inverseMapLookup(map, s, -1));
		}
		return basis;
	}

	/** 
		MinBasis(g∈Graph, n∈Integer) := HasBasisSize(g, n) ∧ ¬(let nm1∈Integer = Sub1(n) in HasBasisSize(g, nm1))
		HasBasisSize(g∈Graph, n∈Integer) := ∃b∈Collection<Integer>:(Size(b, n) ∧ Basis(g, b))
		HasBasisSize(g∈Graph, nm1∈Integer) := ∃b∈Collection<Integer>:(Size(b, nm1) ∧ Basis(g, b))
	 */
	public Position selectPosition(MinBasis formula, Graph g, Integer n){
		return Position.create(minBasisSize(g) == n);
	}

	/** 
		HasBasisSize(g∈Graph, n∈Integer) := ∃b∈Collection<Integer>:(Size(b, n) ∧ Basis(g, b))
	 */
	public Position selectPosition(HasBasisSize formula, Graph g, Integer n){
		return Position.create(minBasisSize(g) >= n);
	}

	private int minBasisSize(Graph g){
		Pair<Graph, int[]> sccs = g.stronglyConnectedComponentDag();
		return sccs.first.srcNodes().size();
	}

	private int inverseMapLookup(int[] map, int v, int undef){
		for (int i=0;i<map.length;i++) {
			if(map[i] == v) return i;
		}
		return undef;
	}
}

