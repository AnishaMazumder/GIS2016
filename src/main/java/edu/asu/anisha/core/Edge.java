package edu.asu.anisha.core;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Node source;
	Node end;
	double weight;
	int edgeID;
	static int defaultId = 0;
	public Edge(Node source, Node end, double weight, int id) {
		super();
		this.source = source;
		this.end = end;
		this.weight = weight;
		this.edgeID = id;
	}
	public Edge(Node source, Node end) {
		super();
		this.source = source;
		this.end = end;
		this.weight = 1.0;
		this.edgeID = defaultId++;
	}
	public Node getSource() {
		return source;
	}
	public Node getEnd() {
		return end;
	}
	public Node getTarget() {
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
	
	public int steinerPointsNeededForEdge(double range){
		return (int)Math.ceil(this.getWeight()/(2*range));
	}
	
}
