package edu.asu.anisha.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Graph {
	/***
	 * The single data structure to store a graph
	 * 
	 */
	int nodeCount;
	private  List<Node> nodes;
	List<Edge> edges;
	HashMap<Node,HashSet<Node>> adjacencyList;
	HashMap<String,Edge> edgeMap;
	boolean directed;

	
	private static List<Edge> createEdge(List<Node> vertices){
		List<Edge> edges = new LinkedList<Edge>();
		int id=0;
		for(int i=0;i<vertices.size();i++){
			for(int j=i+1;j<vertices.size();j++){
				double distance = Math.pow(vertices.get(i).getX()-vertices.get(j).getX(),2) + 
						Math.pow(vertices.get(i).getY()-vertices.get(j).getY(),2);
				edges.add(new Edge(vertices.get(i),vertices.get(j),Math.sqrt(distance),id++));
			}
		}
		return edges;
	}
	
	public Graph(List<Node> vertices,boolean directed) {
		this(vertices,createEdge(vertices),directed);
	}

	public Graph(List<Node> vertices, List<Edge> edges, boolean directed) {
		super();
		this.nodes = vertices;
		this.nodeCount = vertices.size();
		this.edges = edges;
		adjacencyList = new HashMap<Node,HashSet<Node>>();
		this.directed = directed;
		this.edgeMap = new HashMap<String,Edge>();
		Node source,end;
		for(Edge edge:edges){

			source = edge.getSource();
			end = edge.getEnd();
			edgeMap.put(source.getID()+":"+end.getID(), edge);

			//since undirected graph, hence, put source in end's adjacency list and vice versa
			if(adjacencyList.containsKey(source)){
				HashSet<Node> neighbors = adjacencyList.get(source);
				neighbors.add(end);
			}
			else{
				HashSet<Node> neighbors = new HashSet<Node>();
				neighbors.add(end);
				adjacencyList.put(source,neighbors);
			}
			if(!directed){
				if(adjacencyList.containsKey(end)){
					HashSet<Node> neighbors = adjacencyList.get(end);
					neighbors.add(source);
				}
				else{
					HashSet<Node> neighbors = new HashSet<Node>();
					neighbors.add(source);
					adjacencyList.put(end,neighbors);
				}
			}

		}
	}

	public int getVertices() {
		return nodeCount;
	}
	public List<Edge> getEdges() {
		return edges;
	}

	public void addEdge(Edge edge){
		this.edges.add(edge);
		Node source = edge.getSource();
		Node end = edge.getEnd();
		edgeMap.put(source.getID()+":"+end.getID(), edge);

		//since undirected graph, hence, put source in end's adjacency list and vice versa

		if(adjacencyList.containsKey(source)){
			HashSet<Node> neighbors = adjacencyList.get(source);
			neighbors.add(end);
		}
		else{
			HashSet<Node> neighbors = new HashSet<Node>();
			neighbors.add(end);
			adjacencyList.put(source,neighbors);
		}
		if(directed){
			if(adjacencyList.containsKey(end)){
				HashSet<Node> neighbors = adjacencyList.get(end);
				neighbors.add(source);
			}
			else{
				HashSet<Node> neighbors = new HashSet<Node>();
				neighbors.add(source);
				adjacencyList.put(end,neighbors);
			}
		}
	}

	public int getEdgeCount() {
		// TODO Auto-generated method stub
		return edges.size();
	}

	public int getNodeCount() {
		// TODO Auto-generated method stub
		return nodes.size();
	}

	public ArrayList<ArrayList<Node>> nodeSubsets(Node root) {

		List<Node> nodes = new LinkedList<Node>();
		nodes.addAll(getNodes());
		nodes.remove(root);
		if (nodes.size()==0)
			return null;

		//Arrays.sort(nodes);

		ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();

		for (int i = 0; i < nodes.size(); i++) {
			ArrayList<ArrayList<Node>> temp = new ArrayList<ArrayList<Node>>();

			//get sets that are already in result
			for (ArrayList<Node> a : result) {
				temp.add(new ArrayList<Node>(a));
			}

			//add S[i] to existing sets
			for (ArrayList<Node> a : temp) {
				a.add(nodes.get(i));
			}

			//add S[i] only as a set
			ArrayList<Node> single = new ArrayList<Node>();
			single.add(nodes.get(i));
			temp.add(single);
			
			result.addAll(temp);
		}

		//add empty set
//		result.add(new ArrayList<Node>());

		return result;
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public ArrayList<Edge> getOutEdges(ArrayList<Node> nodeSubset) {
		Set<Node> nodeHashSet = nodeSubset.isEmpty() ? new HashSet<Node>() : new HashSet<Node>(nodeSubset);
		ArrayList<Edge> outEdges = new ArrayList<Edge>();
		Iterator<Node> iterator;
		Node otherNode;
		for(Node node:nodeSubset){
			// create an iterator
			iterator = adjacencyList.get(node).iterator(); 
			// check values
			while (iterator.hasNext()){
				otherNode = (Node) iterator.next();
				if(!nodeHashSet.contains(otherNode)){
					if(!directed){
						if(node.getID()<otherNode.getID())
							outEdges.add(edgeMap.get(node.getID()+":"+otherNode.getID()));
						else{
							outEdges.add(edgeMap.get(otherNode.getID()+":"+node.getID()));
						}
							
					}	
				}
			}
		}
		return outEdges;
	}

	public Node getNode(int i) {
		return nodes.get(i);
	}

	public Edge getEdge(int i){
		return edges.get(i);
	}

	public List<Node> getMatchingNodes(ArrayList<Double> arrayList) {
		
		return null;
	}

	public HashSet<Node> getNeighbors(Node v){
		return adjacencyList.get(v);
	}
	public double getMaxEdgeWeight(){
		double maxEdgeweight = Double.MIN_NORMAL;
		for(Edge e:edges){
			if(maxEdgeweight< e.getWeight())
				maxEdgeweight = e.getWeight();
		}
		return maxEdgeweight;
	}
	public Edge getEdgeFromEndPoints(Node source, Node end){
		return edgeMap.get(source.getID()+":"+end.getID());
	}
	@Override
	public String toString() {
		return "Graph [nodeCount=" + nodeCount + "\t edgeCount = " + getEdgeCount() +",\n nodes=" + nodes + ",\n edges=" + edges + ",\n adjacencyList="
				+ adjacencyList + ",\n directed=" + directed + "]";
	}

	
	
}
