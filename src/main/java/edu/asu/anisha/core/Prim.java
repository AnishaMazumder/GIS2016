package edu.asu.anisha.core;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Prim {

	Graph graph;
	SimpleWeightedGraph<Node,Edge> wg;
	PrimMinimumSpanningTree<Node, Edge> primMST;
	public Prim(Graph graph) {
		this.graph = graph;
		wg = new SimpleWeightedGraph<Node,Edge>(Edge.class);
		for(Node node:graph.getNodes()){
			wg.addVertex(node);
		}
		for(Edge edge:graph.getEdges()){
			wg.addEdge(edge.getSource(), edge.getEnd(), edge);
			wg.setEdgeWeight(edge,edge.getWeight());
		}
		primMST = new PrimMinimumSpanningTree<Node, Edge>(wg);
	}

	public Graph getMST() {
		Set<Edge> edgesOfMST = primMST.getMinimumSpanningTreeEdgeSet();
		
		//List<Edge> castedEdgesOfMST = new ArrayList<Edge>();
		return new Graph(graph.getNodes(),new ArrayList<Edge>(edgesOfMST), graph.range, false);
	}

}
