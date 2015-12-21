package edu.asu.anisha.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.UnknownObjectException;

public class QPSolver {

	private static final boolean DEBUG = false;
	/******
	 *  Solves a QP problem
	 *  Input: lambda
	 *  Output: Map { X=[arrayList], Y=[arrayList]}
	 ***/
	private Graph g;
	//cplex variable
	IloCplex cplex;
	private IloNumVar[] X_e; // variable for each edge
	private IloNumVar[] Y_v; // variable for each vertex
	
	public QPSolver(Graph g) {
		this.g = g;
		//lambda = 100; // default;
		/**
		 * Create a common QP solver instance object for a graph
		 * Next time when a solve method is called 
		 * the lambda will be specified 
		 */
	}

	
	public void optimize(double lambda, Node root/*, int k*/) {
		try {		
			createX_eVariables();
			createY_vVariables();
			//cplex.setOut(null);
			createConstraint(root);
			createObjective(lambda/*,k*/);
	          
			if ( cplex.solve() ) {
				if(DEBUG){
					System.out.println("----------------------------------------");
		            System.out.println("Obj " + cplex.getObjValue());
	//	            total_successful_runs++;
		            System.out.println();
				}
	            
			}
			
		} catch (Exception exc) {
			System.err.println("Concert exception '" + exc + "' caught");
		}
	}

	private void createConstraint(Node root) {
		ArrayList<ArrayList<Node>> nodePowerSet = g.nodeSubsets(root);
		
		try {
		for(ArrayList<Node> nodeSubset: nodePowerSet){
			ArrayList<Edge> outEdges = g.getOutEdges(nodeSubset);
			IloLinearNumExpr exprLHS=cplex.linearNumExpr();
			for(int i=0;i<outEdges.size();i++)
				{
					//expr5.addTerm(2.0,X_v[i]);
//					exprLHS.addTerm(outEdges.get(i).getWeight(),X_e[outEdges.get(i).getID()]);
					exprLHS.addTerm(1.0,X_e[outEdges.get(i).getID()]);
				}
			for(Node node:nodeSubset){

//					IloLinearNumExpr exprRHS=cplex.linearNumExpr(1.0);
//				IloLinearNumExpr exprRHS=cplex.linearNumExpr();
//				IloLinearNumExpr exprRHS=cplex.linearNumExpr();
//					exprRHS.addTerm(-1.0, Y_v[node.getID()]);
//					cplex.diff(1.0,Y_v[node.getID()]);
//					exprRHS.add((IloLinearNumExpr) cplex.constant(1.0));
//					if(DEBUG)
//						System.out.println("exprLHS = " + exprLHS.toString() + "\n exprRHS = "+exprRHS.toString());
					cplex.addGe(exprLHS, cplex.diff(1.0,Y_v[node.getID()]) , ": Contraint for node "+node.getID() );
					
					
			}
		}
		} catch (Exception e) {
			System.out.println("createConstraint exception"+e.getMessage());
		}
		
	}
	
	public void createObjective(double lambda/*, int k*/) {
		try {
//			IloLinearNumExpr expr = cplex.linearNumExpr(-lambda*(g.getNodeCount()-k));
			IloLinearNumExpr expr = cplex.linearNumExpr();
			List<Edge> edges = g.getEdges();
			for(int i=0;i<edges.size();i++)
				{
					expr.addTerm(edges.get(i).getWeight(), X_e[edges.get(i).getID()]);
				}
			List<Node> nodes = g.getNodes();
			for(int i=0;i<nodes.size();i++)
			{
				expr.addTerm(lambda, Y_v[nodes.get(i).getID()]);
			}
			cplex.addMinimize(expr, ": Obejective to maximize sum(c_e*X_e)+lambda*(sum(Y_v)-(n-k)))");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public Map<String,ArrayList<Double>> solve(double lambda, Node root/*, int k*/){
		/**
		 * replace lambda 
		 * call solver
		 * take the output 
		 * create a map with key "X" and "Y" with the values of "X_e" and "Y_v"
		 * return
		 */
		
		try {
			cplex = new IloCplex();
			cplex.setOut(null);
			cplex.setParam(IloCplex.BooleanParam.MemoryEmphasis,true);
			cplex.setParam(IloCplex.DoubleParam.WorkMem, 3000);
			optimize(lambda, root/*,k*/);
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,ArrayList<Double>> result = new HashMap<String,ArrayList<Double>>();

		ArrayList<Double> values;
		//"X" with values of "X_e"
		values = new ArrayList<Double>();
		for(int i=0;i<g.getEdgeCount();i++){
			try {
				values.add(cplex.getValue(X_e[i]));
			} catch (UnknownObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		result.put("X", values);
		// "Y" with values of "Y_v"
		values = new ArrayList<Double>();
		for(int i=0;i<g.getNodeCount();i++){
			try {
				values.add(cplex.getValue(Y_v[i]));
			} catch (UnknownObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		result.put("Y", values);
		values = new ArrayList<Double>();
		try {
			values.add(cplex.getObjValue());
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("obj", values);
		return result;
	}
	
	public void createX_eVariables() {
		int m= this.g.getEdgeCount();
		try {
			double[]    lb = new double[m];
			double[]    ub = new double[m];
			for(int i=0;i<m;i++)
			{
				lb[i]=0;
				ub[i]=1;
			}			
			X_e = cplex.numVarArray(m, lb, ub);	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		
	}
	public void createY_vVariables() {	
		int n = this.g.getNodeCount();
		try {
			double[]    lb = new double[n];
			double[]    ub = new double[n];
			for(int i=0;i<n;i++)
			{
				lb[i]=0;
				ub[i]=1;
			}
		      
		      Y_v = cplex.numVarArray(n, lb, ub);				
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		
	}
	
}
