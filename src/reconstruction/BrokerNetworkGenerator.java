package reconstruction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import dataStructures.Node;

/**
 * Given a subscriber distribution, the broker network generator generates
 * brokers in the network space.
 * @author Albert
 *
 */
public class BrokerNetworkGenerator {
	/**
	 * An array of geographic regions, each of which contains a list of
	 * nodes (network locations).
	 */
	private ArrayList<Node> geographicRegions[];
	
	/**
	 * Network dimension
	 */
	private int dim;
	
	/**
	 * Number of brokers in the network
	 */
	private int numOfBrokers;
	
	/**
	 * Random generator
	 */
	private Random generator = null;
	
	/**
	 * Constructor
	 * @param dim
	 * @param geographicRegions
	 */
	BrokerNetworkGenerator(int dim, ArrayList<Node>[] geographicRegions) {
		this.dim = dim;
		this.geographicRegions = geographicRegions;		
		numOfBrokers = 0;		
		generator = new Random(Config.BROKER_RANDOM_SEED);
	}
	
	/**
	 * Generate brokers in the network space based on the subscriber distribution
	 * in the network space.  "Frequencies" provides info about the frequency
	 * of each node being chosen to be the (approximate) network location of
	 * the subscribers.
	 * @param numOfBrokers
	 * @param frequencies
	 */
	void generateBrokers(int[] numOfBrokers, int[][]frequencies) {
		for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
			generateBrokers(geographicRegions[i], numOfBrokers[i], frequencies[i]);
		}
		
		this.numOfBrokers = 0;
		for (int i = 0; i < geographicRegions.length; i++) {
			this.numOfBrokers += geographicRegions[i].size();
		}
		/*
		 * Reset IDs s.t. they are consecutive integers from 0 to numOfBrokers.
		 */	
		resetID();
		
		for (int i = 0; i < Param.Region.values().length; i++) {
			System.out.println("Geographic region " + Param.Region.values()[i] +
					": " + geographicRegions[i].size());
		}
	}
	
	/**
	 * Generate brokers in a particular geographic region in the network space.
	 * "Frequencies" provides info about the frequency of each node (in the
	 * region) being chosen to be the (approximate) network location of the
	 * subscribers. 
	 * @param nodes
	 * @param numOfNodes
	 * @param frequencies
	 */
	private void generateBrokers(ArrayList<Node> brokers, int numOfBrokers, int[] frequencies) {

       if (numOfBrokers <= 0) {
			brokers.clear();
			return;			
       }

		/*
		 * Inverse transform sampling method:
		 * Partition [0,1] to a set of intervals; each element is assigned to
		 * an interval which length is proportional to its probability. A point
		 * is then uniformly chosen from [0,1], the interval that contains
		 * the point is then found, and the corresponding element will be
		 * selected.  
		 */		
		double[] cdf = new double[frequencies.length];
		cdf[0] = 0;
		for (int i = 1; i < frequencies.length; i++) {
			cdf[i] = cdf[i-1] + frequencies[i-1];
		}
		double totalWeight = cdf[cdf.length-1] + frequencies[frequencies.length - 1];
		if (totalWeight == 0) {
			throw new RuntimeException("generateBrokers: Cannot generate brokers in the geographic region that has no subscriber");
		}
		for (int i = 1; i < frequencies.length; i++) {
			cdf[i] /= totalWeight;
		}		
		ArrayList<Integer> newIndices = new ArrayList<Integer>();
		while (numOfBrokers > 0) {
			double rand = generator.nextDouble();		
			int min = -1;
			int max = cdf.length ;
			int index = (cdf.length - 1 )/2;
			while(true) {
				if (cdf[index] > rand) {
					max = index;
					index = (int) Math.ceil((index + min + 0.)/2);	    		  
				} else if (index < cdf.length - 1 &&
						cdf[index+1] < rand) {
					min = index;
					index = (int)(index + max) /2;
				} else {
					break;
				}						
			}					 
			
			newIndices.add(index);
			numOfBrokers--;
		}
		
		ArrayList<Node> newNodes = new ArrayList<Node>();
		for (int i = 0; i < newIndices.size(); i++) {
			Node node = brokers.get(newIndices.get(i).intValue());		
			Node newNode = new Node(dim);
			newNode.name += (node.name + "-clone");
			newNode.region = node.region;			
			newNode.coords = NodeLoader.generateCoords(brokers, newIndices.get(i).intValue(), generator);
			newNodes.add(newNode);
		}
		brokers.clear();
		brokers.addAll(newNodes);
	}

	/**
	 * Reset the globalID of every node;
	 */
	private void resetID() {
		int id = 0;
		for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
			for (int j = 0; j < geographicRegions[i].size(); j++) {
				geographicRegions[i].get(j).globalId = id;
				id++;
			}
		}		
	}		

	/**
	 * Write broker info to a file
	 * @param filename
	 */
	void writeToFile(String filename) {
		try{    			
                    FileWriter fstream = new FileWriter(filename);
		    BufferedWriter  out = new BufferedWriter(fstream);

		    out.write(dim + "; " + numOfBrokers + "\n");
		    for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
		    	for (int j = 0; j < geographicRegions[i].size(); j++) {
		    		Node node = geographicRegions[i].get(j);
		    		out.write(node.globalId + "; " + node.region + "; ");
		    		for (int k = 0; k < dim - 1; k++) {
                                    out.write(node.coords[k] + ", ");
		    		}
		    		out.write(node.coords[dim - 1] + "\n");
		    	}
		    }
		    out.close();
		}	catch (IOException ex) {
			System.err.println(ex); 
		}    				
	}
}
