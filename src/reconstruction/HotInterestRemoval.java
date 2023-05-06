package reconstruction;

import java.util.ArrayList;
import java.util.Collections;
import reconstruction.Config;

import dataStructures.Grid;
import dataStructures.Interest;
import dataStructures.SubscriptionCountTable;

/**
 * Hot interest removal step.
 * @author Albert
 *
 */
class HotInterestRemoval extends InterestModifier {
	
	/**
	 * Constructor
	 * @param categoryHierarchies
	 */
	HotInterestRemoval(CategoryHierarchy[] categoryHierarchies) {
		super(categoryHierarchies);
	}
	
	/**
	 * Remove hot interests from the interest list.
	 * @param interests
	 * @param numOfHotInterests
	 * @return updated list of interests
	 */
   	ArrayList<Interest> execute(ArrayList<Interest> interests, int numOfHotInterests){
   		SubscriptionCountTable[] grids = createGrid(interests);
          removeHotInterests(grids, numOfHotInterests);
    	return updateInterests(grids);		
	}	

   	/**
   	 * Remove n hot interests from the grid (subscription count table), where
   	 * n = numOfRemoved.
   	 * @param matrix
   	 * @param numOfRemoved
   	 */
   	private void removeHotInterests(SubscriptionCountTable[] grids, int numOfRemoved) {
		ArrayList<Double> subscriptionCounts = new ArrayList<Double>();		
		SubscriptionCountTable grid = grids[Config.NUM_OF_REGIONS];
		for (int j = 0; j < grid.getNumOfCells(); j++) {
			subscriptionCounts.add(grid.get(j));
		}		
		/*
		 * Find the cutoff threshold that separate top n interests from the rest.
		 */
		Collections.sort(subscriptionCounts);
		double threshold = subscriptionCounts.get(subscriptionCounts.size() - 1 - numOfRemoved);
          System.out.println("threshold: "+threshold+" subscriptionCounts= "+subscriptionCounts.size()+ " numOfremoved= "+numOfRemoved);
          if (threshold == 0) {
             System.out.println("removeHotInterests: All the interests have 0 subscription count after the hot interest removal step");
             System.out.println("the maximun number of hot interest must be lesser than "+numOfRemoved);
             throw new RuntimeException("removeHotInterests: All the interests have 0 subscription count after the hot interest removal step");
		}
		/*
		 * Remove the top n interests from the grid. 
		 */
		for (int j = 0; j < grid.getNumOfCells(); j++) {
			if(grid.get(j) > threshold) {
				for (int i = 0; i < Config.NUM_OF_REGIONS + 1; i++) {
					grids[i].set(j, 0);
				}
			}
		}		
    }    
}