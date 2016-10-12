package edu.asu.anisha.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

 

public class RandomDataGenerator {
 private double length;
 private double breadth;
 private int pointsCount;
 private String filename;
public RandomDataGenerator(double length, double breadth, int pointsCount, String filename) {
	super();
	this.length = length;
	this.breadth = breadth;
	this.pointsCount = pointsCount;
	this.filename = filename;
}

public void generateData(){

Random rand = new Random();
double lowL = 0;
double highL = length;
double lowB = 0;
double highB = breadth;
Set<Pair<Double,Double>> points = new HashSet<Pair<Double,Double>>();
	try {
		PrintWriter pr = new PrintWriter(filename);
		for(int i = 0; i<pointsCount; i++){
			double x = rand.nextInt((int) (highL-lowL))+lowL;
			double y = rand.nextInt((int) (highB-lowB))+lowL;
			Pair p = new Pair(x,y);
			if(!points.contains(p)){
				pr.println(x+","+y);
			}
		}
		pr.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
