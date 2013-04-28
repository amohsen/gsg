package example.minbasis;

import java.util.Collection;
import java.util.Map;

import claim.structure.FunctionI;
import claim.structure.VarI;
import claim.structure.impl.Formula;
import example.minbasis.Graph.Node;

public class GraphNodes extends Formula implements FunctionI<Collection<Node>>{
	final VarI<Graph> _g;
	
	
	public GraphNodes(VarI<Graph> g) {
		super(g);
		this._g = g;
	}

	@Override
	public Collection<Node> execute(Map<VarI<?>, Object> env) {
		throw new RuntimeException("example.minbasis.GraphNodes.execute(..) is not implemented!");
	}
}
