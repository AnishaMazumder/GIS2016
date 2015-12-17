package edu.asu.anisha.core;

import java.util.ArrayList;
import java.util.Map;

public class QPSolver {

	/******
	 *  Solves a QP problem
	 *  Input: lambda
	 *  Output: Map { X=[arrayList], Y=[arrayList]}
	 ***/
	private Graph g;

	public QPSolver(Graph g) {
		this.g = g;
		/**
		 * Create a common QP solver instance object for a graph
		 * Next time when a solve method is called 
		 * the lambda will be specified 
		 */
	}

	public Map<String,ArrayList<Double>> solve(double lambda){
		/**
		 * replace lambda 
		 * call solver
		 * take the output 
		 * create a map with key "X" and "Y" with the values of "x_e" and "y_e"
		 * return
		 */
		return null;
	}
}
