package edu.asu.anisha.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.Node;
import edu.asu.anisha.core.QPSolver;

public class QPSolverTester {

	public static void main(String[] args) {
		List<Node> v = new LinkedList<Node>();
		v.add(new Node(1,1,0));
		v.add(new Node(1,2,1));
		v.add(new Node(2,1,2));
		v.add(new Node(2,2,3));
//		
//		v.add(new Node(0,0,0));
//		v.add(new Node(0.5,0,1));
//		v.add(new Node(0.25,5,2));
		
//		v.add(new Node(-100,-100.2,0));
//		v.add(new Node(-100,-200,1));
//		v.add(new Node(-200,-100,2));
		
//		v.add(new Node(0,0,0));
//		v.add(new Node(14.414,0,1));
//		v.add(new Node(28.828,0,2));
//		v.add(new Node(43.242,0,3));
//		v.add(new Node(0,14.414,4));
//		v.add(new Node(14.414,14.414,5));
//		v.add(new Node(28.828,14.414,6));
//		v.add(new Node(43.242,14.414,7));
		
		Graph g = new Graph(v,0.9, false);
		System.out.println(g + "\n edgecount = "+g.getEdgeCount());
		QPSolver s = new QPSolver(g);
		Map<String, ArrayList<Double>> ret = s.solve(1.175,g.getNode(0));
		System.out.println(ret);
	}
}
