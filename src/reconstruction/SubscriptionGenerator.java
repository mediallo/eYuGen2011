package reconstruction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import dataStructures.*;

/**
 * The subscription generator generates a set of subscriptions.
 * @author Albert
 *
 */
public class SubscriptionGenerator {
	/**
	 * A list of interests
	 */
	private ArrayList<Interest> interests;
	
	/**
	 * An array of geographic regions, each of which contains a list of
	 * nodes (network locations).
	 */
	private ArrayList<Node> geographicRegions[];
	
	/**
	 * Dimension of the network space
	 */
	private int networkDim;
	
	/**
	 * Dimension of the event space
	 */
	private int eventDim;
	
	/**
	 * Constructor
	 * @param interests
	 * @param networkDim
	 * @param eventDim
	 * @param geographicRegions
	 */
	SubscriptionGenerator(ArrayList<Interest> interests, int networkDim, int eventDim, ArrayList<Node>[] geographicRegions) {
		this.interests = interests;
		this.networkDim = networkDim;
		this.eventDim = eventDim;
		this.geographicRegions = geographicRegions;
	}

	/**
	 * Sample subscriptions based on their popularity.
	 * Note: For all nodes, we keep track of the frequencies that their neighborhoods
	 * are chosen to be the coordinate of a subscription.
	 * @param numOfSubs
	 * @param frequencies
	 * @return
	 */
	Subscription[] execute(int numOfSubs, int[][] frequencies) {	

       Subscription[] subscriptions = new Subscription[numOfSubs];
		
		double totalWeight = 0;
		for (int i = 0; i < interests.size(); i++) {
			totalWeight += interests.get(i).numOfMembers;
          // System.out.println("SubscriptionGenerator:execute: interests.get("+i+").numOfMembers = "+interests.get(i).numOfMembers);
		}
          System.out.println("SubscriptionGenerator:execute: number of interests = "+interests.size());
		System.out.println("SubscriptionGenerator:execute: Total based subscriptions = "+totalWeight);
          System.out.println("SubscriptionGenerator:execute: Total subscriptions to be generate = "+numOfSubs);
		/*
		 * Inverse transform sampling method:
		 * Partition [0,1] to a set of intervals; each element is assigned to
		 * an interval which length is proportional to its probability. A point
		 * is then uniformly chosen from [0,1], the interval that contains
		 * the point is then found, and the corresponding element will be
		 * selected.  
		 */		
		double[] cumulativeProbs = new double[interests.size()];
		double cumulativeWeight = 0;

		for (int i = 0; i < interests.size(); i++) {
			cumulativeProbs[i] = cumulativeWeight/totalWeight;
			cumulativeWeight += interests.get(i).numOfMembers;
          //System.out.println("SubscriptionGenerator:execute: cumulativeProbs["+i+"]= "+cumulativeProbs[i]);
          }
		int[] subscriptionCounts = new int[Config.NUM_OF_REGIONS];
		for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
			subscriptionCounts[i] = 0; //init the table
		}
          
		Random generator = new Random(Config.SUB_RANDOM_SEED);
          //Random generator = new Random();
          double r = generator.nextDouble();
          
		for (int i = 0; i < numOfSubs; i++) {
			
			/*
			 *  Binary search the interval that contains the point
			 */    
			int index = (int) (cumulativeProbs.length - 1)/2;
               
			int min = -1;
			int max = cumulativeProbs.length ;
			while(true) { // repeat until we found the index of the interest with cumulativeProbs ~= r
				if (cumulativeProbs[index] > r) {
					max = index;
					index = (int) Math.ceil((index + min + 0.)/2);	    		  
				} else if (index < cumulativeProbs.length - 1 && cumulativeProbs[index+1] < r) {
					min = index;
					index = (int)(index + max) /2;
				} else {
					break;
				}
			}

               
			Interest interest = interests.get(index);
			subscriptions[i] = new Subscription(interest.categories.length, networkDim);

               // set the subscription's coordinates in the Event space
			for (int j = 0; j < interest.categories.length; j++) {				
				if (Config.SHIFT_RECT) {
					double range = interest.categories[j].valueMax - interest.categories[j].valueMin;
					r = generator.nextGaussian();
					double err = Math.abs(r * range/4);
					subscriptions[i].setPoints(j, (interest.categories[j].valueMin - err), (interest.categories[j].valueMax +err));
				} else {
					subscriptions[i].setPoints(j, interest.categories[j].valueMin, interest.categories[j].valueMax);	
				}
			}
               
			//System.out.println("SubscriptionGenerator:execute:" +subscriptions[i].getMinimalPoint()+" "+ subscriptions[i].getMaximalPoint());

               subscriptions[i].region = interest.region;
			subscriptionCounts[interest.region.ordinal()]++;

			/*
			 * Randomly select one node from the region and set the coordinate
			 * of the subscription near the chosen node. 
			 */			
			ArrayList<Node> region = geographicRegions[interest.region.ordinal()];
			int nodeIndex = generator.nextInt(region.size());	// select randomly node from the region
               
			(frequencies[interest.region.ordinal()])[nodeIndex]++;
               // set the subscription's coordinates in the Network space
			subscriptions[i].setCoords(NodeLoader.generateCoords(region, nodeIndex, generator));
               r = generator.nextDouble();
		}	
		
		for (int i = 0; i < Param.Region.values().length; i++) {
			System.out.println("Geographic region " + Param.Region.values()[i] +" : " + subscriptionCounts[i]+ " Subs");
		}
		return subscriptions;
	}
	
	/**
	 * Print the subscription info to a file.
	 * @param subs
	 * @param filename
	 */
	void print(Subscription[] subs, String filename) {
		try{    			
			FileWriter fstream = new FileWriter(filename);
		    BufferedWriter  out = new BufferedWriter(fstream);
		    out.write(subs.length+"; " + networkDim + "; " + eventDim + "\n");
			for (int i = 0; i < subs.length; i++) {
				double[] coords = subs[i].getCoords();
				double[] min = subs[i].getMinimalPoint();
				double[] max = subs[i].getMaximalPoint();				
				out.write(subs[i].region + "\n" + coords[0]);
                                
				for (int j = 1; j < coords.length; j++) {
					out.write(" " + coords[j]);
				}
				out.write('\n');
				out.write(min[0]+ ", " + max[0]);
				for (int j = 1; j < min.length; j++) {
					out.write("; " + min[j]+ ", " + max[j]);
				}
				out.write('\n');				
			}    		    
		    out.close();
		}	catch (IOException ex) {
			System.err.println(ex); 
		}    		
	}
}
