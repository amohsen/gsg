package example.syntax.minbasis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import utils.ArrayUtils;
import utils.Pair;
import static java.lang.Math.min;

public class Graph {
	private int n = 0; // number of nodes
	private List<Collection<Integer>> adjacency = new ArrayList<Collection<Integer>>();

	public int addNode(){
		adjacency.add(new HashSet<Integer>());
		return n++;
	}

	public void addEdge(int s, int t){
		adjacency.get(s).add(t);
	}

	public boolean contains(int node){
		return 0 <= node && node < n;
	}

	Collection<Integer> successors(int node){
		return Collections.unmodifiableCollection(adjacency.get(node));
	}

	public Pair<Graph, int[]> stronglyConnectedComponentDag(){
		class StrongConnected{
			static final int UNDEFINED = -1;
			
			private Graph g;
			
			private int currentIndex = 0;
			private Stack<Integer> s = new Stack<Integer>();
			private int index[];
			private int lowLink[];

			public Graph d = new Graph();
			public int[] map;
			
			StrongConnected(Graph g){
				this.g = g;
				index = new int[g.n];
				Arrays.fill(index, UNDEFINED);
				lowLink = new int[g.n];
				Arrays.fill(lowLink, UNDEFINED);
				map = new int[g.n];
				Arrays.fill(map, UNDEFINED);
				
				for(int v = 0 ; v < g.n ; v++){
					if(index[v] == UNDEFINED){
						strongConnect(v);
					}
				}
				
				dagConnect();
			}

			private void strongConnect(int v) {
				index[v] = currentIndex;
				lowLink[v] = currentIndex;
				currentIndex++;
				s.push(v);

				// Consider successors of v
				for (int w : g.successors(v)) {
					if (index[w] == UNDEFINED){
						// Successor w has not yet been visited; recurse on it
						strongConnect(w);
						lowLink[v] = min(lowLink[v], lowLink[w]);
					}else if (s.contains(w)) {
						// Successor w is in stack S and hence in the current SCC
						lowLink[v] = min(lowLink[v], index[w]);
					}
				}
			    // If v is a root node, pop the stack and generate an SCC
			    if (lowLink[v] == index[v]){
			    	int newComp = d.addNode();
			    	int w;
			      do{
			        w = s.pop();
			      	map[w] = newComp;
			      }while(w != v);
			    }

			}
			
			private void dagConnect() {
				for(int v = 0 ; v < g.n ; v++){
					for (int w : g.successors(v)) {
						if(map[v] != map[w]){
							d.addEdge(map[v], map[w]);
						}
					}
				}
			}
		}
		
		StrongConnected sc = new StrongConnected(this);
		return new Pair<Graph, int[]>(sc.d, sc.map);
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		for(int i=0;i<8;i++) g.addNode();
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(3, 1);
		g.addEdge(3, 2);
		g.addEdge(3, 4);
		g.addEdge(4, 3);
		g.addEdge(4, 5);
		g.addEdge(5, 2);
		g.addEdge(5, 6);
		g.addEdge(6, 5);
		g.addEdge(7, 4);
		g.addEdge(7, 6);
		g.addEdge(7, 7);
		
		Pair<Graph, int[]> sccs = g.stronglyConnectedComponentDag();
		Graph dag = sccs.first;
		int[] map = sccs.second;
		System.out.println(dag.n);
		System.out.println(ArrayUtils.format(map));
		System.out.println(g);
		System.out.println(dag);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int v=0;v<n;v++){
			sb.append(v);
			sb.append("->");
			sb.append(adjacency.get(v).toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public boolean isBasis(final Collection<Integer> b) {
		
		class isBasis{
			boolean[] visited = new boolean[n];
			
			boolean allVisited = true;
			
			public isBasis() {
				for (int v : b) {
					dfs(v);
				}
				for (boolean v : visited) {
					if(!v){
						allVisited = false;
						break;
					}
				}
			}

			private void dfs(int v) {
				if(visited[v]) return;
				visited[v] = true;
				for (int w : successors(v)) {
					dfs(w);
				}
			}
		}
		
		return new isBasis().allVisited;
	}
	
	public Collection<Integer> srcNodes(){
		boolean[] notSource = new boolean[n];
		
		for(int v=0;v<n;v++){
			for (int w : successors(v)) {
				notSource[w] = true;
			}
		}
		Collection<Integer> srcs = new ArrayList<Integer>();
		for(int v=0;v<n;v++){
			if(!notSource[v]) srcs.add(v);
		}
		return srcs;
	}
}
