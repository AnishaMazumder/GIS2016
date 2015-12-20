package edu.asu.anisha.core;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge>{

	public int compare(Edge arg0, Edge arg1) {
		if(arg0.getWeight()>arg1.getWeight())
			return -1;
		else if (arg0.getWeight()==arg1.getWeight())
			return 0;
		else return 1;
	}

}
