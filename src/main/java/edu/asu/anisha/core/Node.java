package edu.asu.anisha.core;

public class Node {
	
	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + ", nodeID=" + nodeID + "]";
	}

	private double x;
	private double y;
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	private int nodeID;
	public Node(double x, double y, int id) {
		super();
		this.x = x;
		this.y = y;
		this.nodeID = id;
	}
	public int getID() {
		return nodeID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeID;
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
		Node other = (Node) obj;
		if (nodeID != other.nodeID)
			return false;
		return true;
	}
	
	

}
