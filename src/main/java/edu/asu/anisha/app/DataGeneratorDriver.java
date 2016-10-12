package edu.asu.anisha.app;

import edu.asu.anisha.core.RandomDataGenerator;

public class DataGeneratorDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i =2; i< 3; i++){
		RandomDataGenerator rdg = new RandomDataGenerator(10,5,20,"Data\\RandomData\\randomData"+i+".txt");
		rdg.generateData();
		}
	}

}
