package edu.asu.anisha.app;

import java.util.LinkedList;
import java.util.List;

import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.Node;
import edu.asu.anisha.core.Prim;

public class PrimTest {

	public static void main(String[] args) {
		List<Node> v = new LinkedList<Node>();
		v.add(new Node(0,0,0));
		v.add(new Node(0.5,0,1));
		v.add(new Node(0.25,5,2));
		
		Graph g = new Graph(v,false);
		
		Prim prim = new Prim(g);
		System.out.println(prim.getMST().toString());

	}

}
