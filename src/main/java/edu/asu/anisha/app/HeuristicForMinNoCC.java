package edu.asu.anisha.app;

import java.util.ArrayList;
import java.util.List;

import edu.asu.anisha.core.Edge;
import edu.asu.anisha.core.EdgeComparator;
import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.Prim;

public class HeuristicForMinNoCC {
	private Graph g;
	private double range;
	private int steinerPointBudget;
	private boolean DEBUG = true;

	public HeuristicForMinNoCC(Graph g, int steinerPointBudget, double range) {
		super();
		this.g = g;
		this.steinerPointBudget = steinerPointBudget;
		this.range = range;
	}

	int getMinNoCC(){
		Prim prim = new Prim(g);
		Graph mst = prim.getMST();
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(mst.getEdges());
		edges.sort(new EdgeComparator());
		int noOfCC=1;
		for(Edge edge:edges){
			if(mst.getSteinerPointsNeeded(range)<=steinerPointBudget)
				{
				if(DEBUG)
					System.out.println("Final forest = "+mst.toString());
				return noOfCC;
				}
			mst.removeEdge(edge);
			noOfCC++;
		}
		if(DEBUG)
			System.out.println("Final forest = "+mst.toString());
		return noOfCC;

	}



}
