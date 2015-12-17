package edu.asu.anisha.core;

import java.util.LinkedList;
import java.util.List;

public class Graph {
	/***
	 * The single data structure to store a graph
	 * 
	 */
	int vertices;
	List<Edge> edges;
	
	public Graph(int vertices) {
		this(vertices,new LinkedList<Edge>());
	}
	
	public Graph(int vertices, List<Edge> edges) {
		super();
		this.vertices = vertices;
		this.edges = edges;
	}
	
	public int getVertices() {
		return vertices;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	
	public void addEdge(Edge e){
		this.edges.add(e);
	}
	
}
