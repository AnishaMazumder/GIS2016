package edu.asu.anisha.core;

public class Edge {
	Node source;
	Node end;
	double weight;
	int edgeID;
	public Edge(Node source, Node end, double weight, int id) {
		super();
		this.source = source;
		this.end = end;
		this.weight = weight;
		this.edgeID = id;
	}
	public Node getSource() {
		return source;
	}
	public Node getEnd() {
		return end;
	}
	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return "Edge [source=" + source + ", end=" + end + ", weight=" + weight + ", edgeID=" + edgeID + "]";
	}
	public int getID() {
		return edgeID;
	}
	public Edge reverse() {
		return new Edge(end,source,weight,Integer.MIN_VALUE);
	}
	
}
