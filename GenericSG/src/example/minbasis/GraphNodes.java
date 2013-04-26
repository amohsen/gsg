package example.minbasis;

import java.util.Collection;
import java.util.Map;

import claim.Function;
import claim.Var;
import example.minbasis.Graph.Node;

public class GraphNodes implements Function<Collection<Node>>{
	final Var<Graph> _g;
	
	final Var<?>[] params;
	
	public GraphNodes(Var<Graph> g) {
		this._g = g;
		this.params = new Var<?>[]{_g};
	}

	@Override
	public Var<?>[] getParameters() {
		return params;
	}

	@Override
	public Collection<Node> execute(Map<Var<?>, Object> env) {
		throw new RuntimeException("example.minbasis.GraphNodes.execute(..) is not implemented!");
	}

}
