package example.syntax.minbasis;

import java.util.Collection;
import java.util.Map;

import claim.structure.VarI;
import claim.structure.impl.Function;
import example.syntax.minbasis.Graph.Node;

public class GraphNodes extends Function<Collection<Node>> {
	final VarI<Graph> _g;


	public GraphNodes(VarI<Graph> g) {
		super(new VarI[]{g});
		this._g = g;
	}

	@Override
	public Collection<Node> execute(Map<VarI<?>, Object> env) {
		throw new RuntimeException("example.minbasis.GraphNodes.execute(..) is not implemented!");
	}
}
