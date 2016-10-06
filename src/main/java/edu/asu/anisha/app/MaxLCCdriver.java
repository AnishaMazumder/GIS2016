package edu.asu.anisha.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import edu.asu.anisha.core.Graph;
import edu.asu.anisha.core.Node;

public class MaxLCCdriver {

//	private static String prefix = "Data\\MaxLCC\\";
	private static String prefix = "Data\\RandomData\\";
	public static int maxIter = 1000;
	public static double stepFraction = 0.5;
	public static double epsilon = 0.00001;
	public static void main(String[] args) throws IOException,FileNotFoundException {
		//Type 1 data
		//read core graph
//		BufferedReader br = new BufferedReader(new FileReader(prefix+"Type1CoreGraph.txt"));
//		BufferedReader br = new BufferedReader(new FileReader(prefix+"Type1CoreGraphBig.txt"));
//		BufferedReader br = new BufferedReader(new FileReader(prefix+"Type2CoreGraph.txt"));
		BufferedReader br = new BufferedReader(new FileReader(prefix+"randomData0.txt"));
		List<Node> v = new LinkedList<Node>();
		int id = 0;
		String points;
		while((points=br.readLine())!=null){
			String[] coordinates = points.split(",");
			Node node = new Node(Double.parseDouble(coordinates[0]),Double.parseDouble(coordinates[1]),id++);
			v.add(node);
		}
		br.close();
		//Write output ratio into a file
		PrintWriter pr1;
		PrintWriter pr2;
		//read Budget::Range::OPT
//		br = new BufferedReader(new FileReader(prefix+"Type1Data.txt"));
//		br = new BufferedReader(new FileReader(prefix+"Type1DataTestSmall.txt"));
//		br = new BufferedReader(new FileReader(prefix+"Type2DataTestSmall.txt"));
//		br = new BufferedReader(new FileReader(prefix+"Type2Data.txt"));
		br = new BufferedReader(new FileReader(prefix+"RandomData0Test.txt"));
		String dataString;
		while((dataString = br.readLine())!=null){
			String[] dataArray = dataString.split("::");
			int steinerPointBudget = Integer.parseInt(dataArray[0]);
			double range = Double.parseDouble(dataArray[1]);
			int opt = Integer.parseInt(dataArray[2]);
			Graph g = new Graph(v,false);
			HeuristicForCoverageSteiner h2 = new HeuristicForCoverageSteiner(g,steinerPointBudget,maxIter, range, stepFraction, epsilon);
			int getSizeOfLCC = h2.getLCCwBudget();
			System.out.println("ratio = "+(double)opt/getSizeOfLCC+" size of LCC = "+getSizeOfLCC+" under Budget of " + steinerPointBudget+"  opt = "+opt);
//			pr1 = new PrintWriter(new BufferedWriter(new FileWriter(prefix+"Type1Output.txt", true)));
			pr1 = new PrintWriter(new BufferedWriter(new FileWriter(prefix+"Type2Output.txt", true)));
			pr1.println((double)opt/getSizeOfLCC);
			pr1.close();
//			pr2 = new PrintWriter(new BufferedWriter(new FileWriter(prefix+"Type1DetailedOutput.txt", true)));
			pr2 = new PrintWriter(new BufferedWriter(new FileWriter(prefix+"Type2DetailedOutput.txt", true)));
			pr2.println("ratio = "+(double)opt/getSizeOfLCC+" size of LCC = "+getSizeOfLCC+" under Budget of " + steinerPointBudget);
			pr2.close();
			
		}
		br.close();
		
		

	}

}
