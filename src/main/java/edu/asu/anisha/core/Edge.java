package edu.asu.anisha.core;

public class Edge {
	int source;
	int end;
	double weight;
	public Edge(int source, int end, double weight) {
		super();
		this.source = source;
		this.end = end;
		this.weight = weight;
	}
	public int getSource() {
		return source;
	}
	public int getEnd() {
		return end;
	}
	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return "Edge [source=" + source + ", end=" + end + ", weight=" + weight + "]";
	}
	
}
