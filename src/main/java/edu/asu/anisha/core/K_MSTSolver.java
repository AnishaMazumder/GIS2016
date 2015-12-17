package edu.asu.anisha.core;

import java.util.ArrayList;
import java.util.Map;

public class K_MSTSolver {
	private Graph g;
	private QPSolver qpSolver;
	private int maxIter;
	private int k;
	
	public K_MSTSolver(Graph g, int k,  int maxIter) {
		super();
		this.g = g;
		this.qpSolver = new QPSolver(g);
		this.maxIter = maxIter;
		this.k = k;
	}
	
	/**
	 * Select appropriate return type
	 */
	public void solve(){
		double lambda = 100;
		double left_lambda, right_lambda;
		
		/**
		 * Finding a interval [left_lambda, right_lambda] so that it contains k
		 */
		Map<String, ArrayList<Double>> ret = this.qpSolver.solve(lambda);
		/**
		 * Find used_k from ret
		 */
		int used_k = getUsedK(ret.get("Y"));
		int sign = (k-used_k)>0?1:-1;
		int newSign = sign;
		double newLambda;
		int iter = 1;
		do {
			newLambda = lambda + sign*10;
			ret = this.qpSolver.solve(lambda);
			newSign = getUsedK(ret.get("Y"));;
			lambda= newLambda;
			iter++;
		}while(newSign*sign>0);
		
		left_lambda = sign>0?lambda:newLambda;
		right_lambda = sign>0?newLambda:lambda;
		
		/**
		 * now do binary search
		 * and stop either when you find k (success) or iter exceeds maxIter(failure or no such lambda exists)
		 * if success do A
		 * if failure interpolate and then do A
		 */
	}

	private int getUsedK(ArrayList<Double> y) {
		int usedK = 0;
		for(Double d: y){
			if(d.equals(0))
				usedK++;
		}
		return usedK;
	}
}
