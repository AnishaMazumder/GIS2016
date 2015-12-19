package edu.asu.anisha.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class K_MSTSolver {
	private static final boolean DEBUG = true;
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
	public Graph solve(double stepFraction){

		@SuppressWarnings("rawtypes")
		Map<String, ArrayList> bestRet = new HashMap<String, ArrayList>(); //stores list of nodes, list of edges and obj value
//		for(Node root:g.getNodes()){
			{
		Node root = g.getNode(0); //comment this out and uncomment the for loop
				double lambda = 0.0;//g.getMaxEdgeWeight()*stepFraction;
				double leftLambda, rightLambda;

				/**
				 * Finding a interval [left_lambda, right_lambda] so that it contains k
				 */
				Map<String, ArrayList<Double>> ret = (new QPSolver(g)).solve(lambda,root);
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
					//				newLambda = lambda + sign*10;
					newLambda = lambda + sign*stepFraction;
					ret = (this.qpSolver).solve(newLambda,root);

					k1 = k2; //value for lambda
					k2 = getUsedK(ret.get("Y"));

					newSign = (k-k2)>0?1:-1;
					if(newSign*sign<0)
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
				if(DEBUG)
					System.out.println("******Initilizing Binary Search ***** \n k1 = "+ k1 +",\t k2 = "+k2);
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
					if(DEBUG && iter == 53)
						System.out.println("This is iter 53");
					midLambda = (leftLambda+rightLambda)/2;
					ret = (new QPSolver(g)).solve(midLambda,root);
					//				ret = (new QPSolver(g)).solve(1.0,root);
					currentK = getUsedK(ret.get("Y"));;
					if(DEBUG){
						System.out.println("leftLambda =" + leftLambda + ",\t rightLambda = "+rightLambda + ",\t iter = "+iter +
								",\t currentK = " +currentK);
					}
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
				if(DEBUG)
					System.out.println("leftLambda =" + leftLambda + ",\t rightLambda = "+rightLambda + ",\t iter = "+iter);
				if(!success)
				{

					//iterpolate
					double mu1 = ((double)(k2 - k))/(k2 - k1);
					double mu2 = ((double)(k - k1))/(k2 - k1);
					if(mu2 >=0.5){
						//simply use T2 as our solution;
						ret = this.qpSolver.solve(rightLambda,root);
						if(bestRet.size()==0||(Double)bestRet.get("obj").get(0)<(Double)ret.get("obj").get(0)){
							bestRet.put("Nodes",getListOfNodes(ret.get("Y")));
							bestRet.put("Edges",getListOfEdges(ret.get("X")));
							bestRet.put("obj", computeObjValueAsList(getListOfEdges(ret.get("X"))));
						}

					}
					else{
						//mu1 >=0.5
						Map<String, ArrayList<Double>> t1ret = this.qpSolver.solve(leftLambda,root);
						Map<String, ArrayList<Double>> t2ret = this.qpSolver.solve(rightLambda,root);
						Set<Node> t1 = new HashSet<Node>();
						ArrayList<Double> nodesT1 = t1ret.get("Y");
						for(int i=0; i<nodesT1.size();i++){
							if(nodesT1.get(i) == 0.0)
								t1.add(g.getNode(i));
						}

						Set<Node> t2 = new HashSet<Node>();
						ArrayList<Double> nodesT2 = t2ret.get("Y");
						for(int i=0;i<nodesT2.size();i++){
							if(nodesT2.get(i)==0.0)
								t2.add(g.getNode(i));
						}
						Set<Node> t2prime = new HashSet<Node>(t2);
						t2prime.removeAll(t1);

						Set<Edge> t2EdgeSet = new HashSet<Edge>();
						List<Double> t2AllEdges = t2ret.get("X");
						for(int i=0;i<t2AllEdges.size();i++)
						{
							if(t2AllEdges.get(i)==1.0){
								t2EdgeSet.add(g.getEdge(i));
//								t2EdgeSet.add(g.getEdge(i).reverse()); // The euler code automatically considers each node
							}
						}
						List<Node> eulerTourResult = new LinkedList<Node>();
						getEulerTour(nodesT2,t2EdgeSet,root,eulerTourResult);
						
						// Step 2 of Find-Subtree
						Iterator<Node> it = t2prime.iterator();
						List<Edge> bestSubTour = null;
						while(it.hasNext()){
							Node nxt = (Node) it.next();
							//continue on the euler tour until 2(k-k1) nodes of t2prime are encountered including repeats
							int nodesEncountered = 1;
							int prevNodePosition = eulerTourResult.indexOf(nxt);
							int nodePosition = prevNodePosition + 1;
							List<Edge> currSubTour = new ArrayList<Edge>();
							while(nodesEncountered<=2*(k-k1)){
								if(nodePosition>=eulerTourResult.size())
									nodePosition = 0;
								currSubTour.add(g.getEdgeFromEndPoints(eulerTourResult.get(prevNodePosition), 
										eulerTourResult.get(nodePosition)));
								if(t2prime.contains(eulerTourResult.get(nodePosition))){
									nodesEncountered++;
								}
								prevNodePosition = nodePosition;
							}
							if(bestSubTour == null)
								bestSubTour = currSubTour;
							else if(bestSubTour.size()>currSubTour.size())
								bestSubTour = currSubTour;
							
						}
						
						// connect T1 and bestSubTour with the shortest edge
						// Because this is Euler plane so all the edges are present
						
						//get all the nodes in bestSubTour
						Set<Node> nodeSetBestSubTour = new HashSet<Node>();
						for(Edge edge:bestSubTour){
							nodeSetBestSubTour.add(edge.getSource());
							nodeSetBestSubTour.add(edge.getEnd());
						}
						List<Node> nodeListBestSubTour = new LinkedList<Node>(nodeSetBestSubTour);
						Edge bestEdge = null;
						for(Node nodeT1:t1){
							for(Node nodeBestSubTour: nodeListBestSubTour ){
								Edge currEdge = g.getEdgeFromEndPoints(nodeT1, nodeBestSubTour);
								if(bestEdge == null)
									bestEdge = currEdge;
								else if (bestEdge.getWeight()>currEdge.getWeight())
									bestEdge = currEdge;
							}
							
						}
						//add this bestEdge to bestSubtour
						bestSubTour.add(bestEdge);
						//Add nodes of t2prime in bestSubTour to t1
						for(Node nodeBestSubTour: nodeListBestSubTour){
							if(t2prime.contains(nodeBestSubTour))
								t1.add(nodeBestSubTour);
						}
						
						//return new Graph(t1.addAll(nodesOfT2primeInbestSubTour),edges of bestSubtour+shortest edge connected
						// t1 and bestSubTour)
						if((Double)bestRet.get("obj").get(0)> (Double)computeObjValueAsList(bestSubTour).get(0)){
							bestRet.put("Nodes",new ArrayList<Node>(t1));
							bestRet.put("Edges",(ArrayList<Edge>) bestSubTour);
							bestRet.put("obj", computeObjValueAsList(bestSubTour));
						}
						
					}
				}
							
		}
			return new Graph((List<Node>)bestRet.get("Nodes"),(List<Edge>)bestRet.get("Edges"),false);
		
	}

	private ArrayList<Double> computeObjValueAsList( List<Edge> bestSubTour) {
		ArrayList<Double> costOfEdgeList = new ArrayList<Double>();
		Double d = 0.0;
		for(Edge edge: bestSubTour){
			d+=edge.getWeight();
		}
		costOfEdgeList.add(d);
		return costOfEdgeList;
		
	}

	private ArrayList<Edge> getListOfEdges(ArrayList<Double> arrayList) {
		ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
		for(int i=0;i<arrayList.size();i++){
			if(arrayList.get(i)==1.0)
				listOfEdges.add(g.getEdge(i));
		}
		return listOfEdges;
	}

	private ArrayList<Node> getListOfNodes(ArrayList<Double> arrayList) {
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		for(int i=0;i<arrayList.size();i++){
			if(arrayList.get(i)==0.0)
				listOfNodes.add(g.getNode(i));
		}
		return listOfNodes;
	}

	private void getEulerTour(ArrayList<Double> nodesT2, Set<Edge> t2EdgeSet,Node root, List<Node> eulerTourResult) {
		if(root==null)
			return;
		
		eulerTourResult.add(root);
		ArrayList<Node> neighbors = new ArrayList<Node>(g.getNeighbors(root));
		for(Node neighbor:neighbors){
			if(nodesT2.contains(neighbor)&& t2EdgeSet.contains(g.getEdgeFromEndPoints(root, neighbor))){
				getEulerTour(nodesT2,t2EdgeSet,neighbor,eulerTourResult);
				eulerTourResult.add(root);
			}
		}
	}
	

	private int getUsedK(ArrayList<Double> y) {
		int usedK = 0;
		for(Double d: y){
			if(d.equals(0.0))
				usedK++;
		}
		return usedK;
	}
}
