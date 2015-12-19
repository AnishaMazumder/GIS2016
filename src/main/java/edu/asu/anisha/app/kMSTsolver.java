package edu.asu.anisha.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.K_MSTSolver;
import edu.asu.anisha.core.Node;
//import edu.asu.anisha.core.QPSolver;

public class kMSTsolver {

	private static double stepFraction = 0.5;
	public static void main(String[] args) {
		List<Node> v = new LinkedList<Node>();
//		v.add(new Node(100,50,0));
//		v.add(new Node(-1,20,1));
//		v.add(new Node(2,-1,2));
//		v.add(new Node(20,2,3));
//		v.add(new Node(1.5,2,4));
//		
//		
//		v.add(new Node(10.50,5.50,5));
//		v.add(new Node(-10.6,200,6));
//		v.add(new Node(20,-10.9,7));
//		v.add(new Node(60,42,8));
//		v.add(new Node(81.5,72,9));
		
//		v.add(new Node(1,1.2,0));
//		v.add(new Node(1,2,1));
//		v.add(new Node(2,1,2));
//		
//		v.add(new Node(100,100.2,3));
//		v.add(new Node(100,200,4));
//		v.add(new Node(200,100,5));
//		
//		v.add(new Node(-100,-100.2,6));
//		v.add(new Node(-100,-200,7));
//		v.add(new Node(-200,-100,8));
//		
		
		v.add(new Node(0,0,0));
		v.add(new Node(0.5,0,1));
		v.add(new Node(0.25,5,2));
		
		Graph g = new Graph(v,false);
//		System.out.println(g + "\n edgecount = "+g.getEdgeCount());
//		QPSolver s = new QPSolver(g);
//		Map<String, ArrayList<Double>> ret = s.solve(0.66666666667,g.getNode(0));
//		System.out.println(ret);
		K_MSTSolver kmstsolver = new K_MSTSolver(g,2,5,0.00001);
		Graph bestRet = kmstsolver.solve(stepFraction);
		System.out.println(bestRet.toString());

	}

}
