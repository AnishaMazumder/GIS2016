package edu.asu.anisha.app;

import java.util.LinkedList;
import java.util.List;

import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.Node;

public class Heuristic1Driver {
	public static int steinerPointBudget = 3;
	public static double range = 0.5;
	public static void main(String[] args) {
 		List<Node> v = new LinkedList<Node>();
//		v.add(new Node(0,0,0));
//		v.add(new Node(0.5,0,1));
//		v.add(new Node(0.25,5,2));
		
		v.add(new Node(1,1.2,0));
		v.add(new Node(1,2,1));
		v.add(new Node(2,1,2));
		
		v.add(new Node(100,100.2,3));
		v.add(new Node(100,200,4));
		v.add(new Node(200,100,5));
		
//		v.add(new Node(-100,-100.2,6));
//		v.add(new Node(-100,-200,7));
//		v.add(new Node(-200,-100,8));
		
		Graph g = new Graph(v,false);
		HeuristicForMinNoCC h1 = new HeuristicForMinNoCC(g,steinerPointBudget,range);
		int getNoOfCC = h1.getMinNoCC();
		System.out.println("no of CC = "+getNoOfCC+" under Budget of " + steinerPointBudget);

	}

}
