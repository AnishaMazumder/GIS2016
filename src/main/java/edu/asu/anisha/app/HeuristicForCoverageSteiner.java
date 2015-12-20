package edu.asu.anisha.app;

import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.K_MSTSolver;
import edu.asu.anisha.core.QPSolver;

public class HeuristicForCoverageSteiner {

	private static final boolean DEBUG = false;
	private Graph g;
	private int maxIter;
	private double range;
	private double stepFraction;
	private int steinerPointBudget;
	private double epsilon;
	public HeuristicForCoverageSteiner(Graph g, int steinerPointBudget, int maxIter, double range, double stepFraction,
			double epsilon) {
		super();
		this.g = g;
		this.steinerPointBudget = steinerPointBudget;
		this.maxIter = maxIter;
		this.range = range;
		this.stepFraction = stepFraction;
		this.epsilon = epsilon;
	}
	
	public int getLCCwBudget(){
		int currSteinerPointsNeeded;
		int k;
		Graph currGraph = null;
		for(k=g.getNodeCount();k>=2;k--){
			System.out.println("Trying k =" + k);
			K_MSTSolver kmstsolver = new K_MSTSolver(g,k,maxIter,epsilon);
			currGraph = kmstsolver.solve(stepFraction);
			System.out.println("currGraph = "+currGraph.toString());
			currSteinerPointsNeeded = currGraph.getSteinerPointsNeeded(range);
			System.out.println("currSteinerPointsNeeded = "+currSteinerPointsNeeded);
			if(currSteinerPointsNeeded <= steinerPointBudget )
				break;	
		}
		if(k==1)
		return k;
		else
			return currGraph.getNodeCount();
	}
	

}
