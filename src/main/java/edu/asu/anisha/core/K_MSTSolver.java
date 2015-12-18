package edu.asu.anisha.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class K_MSTSolver {
	private Graph g;
	private QPSolver qpSolver;
	private int maxIter;
	private int k;
	private double epsilon;
	public K_MSTSolver(Graph g, int k,  int maxIter,double epsilon) {
		super();
		this.g = g;
		this.qpSolver = new QPSolver(g);
		this.maxIter = maxIter;
		this.k = k;
		this.epsilon = epsilon;
	}
	
	/**
	 * Select appropriate return type
	 */
	public void solve(){
		double lambda = 100;
		double leftLambda, rightLambda;
		
		/**
		 * Finding a interval [left_lambda, right_lambda] so that it contains k
		 */
		Map<String, ArrayList<Double>> ret = this.qpSolver.solve(lambda);
		/**
		 * Find used_k from ret
		 */
		int used_k = getUsedK(ret.get("Y"));
		int k1,k2;
		int sign = (k-used_k)>0?1:-1;
		int newSign = sign;
		double newLambda;
		int iter = 1;
		
		k2=getUsedK(ret.get("Y"));
		
		while(true) {
			newLambda = lambda + sign*10;
			ret = this.qpSolver.solve(lambda);
			
			k1 = k2; //value for lambda
			k2 = getUsedK(ret.get("Y"));
			
			newSign = k-k2;
			if(newSign*sign>0)
				break;
			lambda= newLambda;
			 
			//iter++;
			
		}
		
		leftLambda = sign>0?lambda:newLambda;
		rightLambda = sign>0?newLambda:lambda;
		
		if(k2<k1){
			int temp1;
			temp1 = k1;
			k1 = k2;
			k2 = temp1;
		}
		
		/**
		 * now do binary search
		 * and stop either when you find k (success) or iter exceeds maxIter(failure or no such lambda exists)
		 * if success do A
		 * if failure interpolate and then do A
		 */
		double midLambda;
		int currentK;
		boolean success = false;
		while(leftLambda <= rightLambda && iter <= maxIter){
			midLambda = (leftLambda+rightLambda)/2;
			ret = this.qpSolver.solve(lambda);
			currentK = getUsedK(ret.get("Y"));;
			if(currentK==k)
				{
					success = true;
					break;
					
				}
			else if(currentK>k)
				{
					rightLambda = midLambda;
					k2 = currentK;
					
				}
			else
				{
					leftLambda = midLambda;
					k1 = currentK;
				}
			iter++;
			
		}
		if(!success)
			{
				//iterpolate
			double mu1 = ((double)(k2 - k))/(k2 - k1);
			double mu2 = ((double)(k - k1))/(k2 - k1);
			if(mu2 >=0.5){
				//simply use T2 as our solution;
				ret = this.qpSolver.solve(rightLambda);
				return ret;
			}
			else{
				//mu1 >=0.5
				Map<String, ArrayList<Double>> t1ret = this.qpSolver.solve(leftLambda);
				Map<String, ArrayList<Double>> t2ret = this.qpSolver.solve(rightLambda);
				Set<Node> t1 = new HashSet<Node>();
				ArrayList<Double> nodesT1 = t1ret.get("Y");
				for(int i=0; i<nodesT1.size();i++){
					if(nodesT1.get(i) == 0)
						t1.add(g.getNode(i));
				}
				
				Set<Node> t2 = new HashSet<Node>();
				ArrayList<Double> nodesT2 = t2ret.get("Y");
				for(int i=0;i<nodesT2.size();i++){
					if(nodesT2.get(i)==0)
						t2.add(g.getNode(i));
				}
				Set<Node> t2prime = new HashSet<Node>(t2);
				t2prime.removeAll(t1);
				
				List<Edge> t2EdgeList = new ArrayList<Edge>();
				List<Double> t2AllEdges = t2ret.get("X");
				for(int i=0;i<t2AllEdges.size();i++)
				{
					if(t2AllEdges.get(i)==1){
						t2EdgeList.add(g.getEdge(i));
						t2EdgeList.add(g.getEdge(i).reverse());
					}
				}
				Graph g1 = new Graph(getUsedK(t2ret.get("Y")),t2EdgeList,true);
				
			}
			}
		doA();
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
