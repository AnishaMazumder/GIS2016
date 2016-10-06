package edu.asu.anisha.data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class MaxLCCType1Data {
	/*
	 * Write data in the form 
	 * Budget::range::OPT::n=Number of nodes in the graph
	 * n following lines containing the X and Y co ordinates of the Nodes
	 */
public static void generateData(String fileLocation, int numNodes, int dimX, int dimY,int countDataSets){
	Random rand = new Random();
	for(int i = 0;i<countDataSets;i++){
		try {
			PrintWriter pr = new PrintWriter(fileLocation+"Data"+i+".txt");
			pr.println(numNodes);
			for(int j = 0; j<numNodes;j++){
				int x = rand.nextInt(dimX+1);
				int y = rand.nextInt(dimY+1);
				pr.println(x+"::"+y);
				
			}
			pr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

}
