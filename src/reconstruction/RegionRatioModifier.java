package reconstruction;

import java.util.ArrayList;

import dataStructures.SubscriptionCountTable;
import dataStructures.Interest;

/**
 * Allow users to change the subscriber ratio among different
 * geographic regions. 
 * @author Albert
 *
 */
public class RegionRatioModifier extends InterestModifier{
	/**
	 * Constructor
	 * @param categoryHierarchies
	 */
	RegionRatioModifier(CategoryHierarchy[] categoryHierarchies) {
		super(categoryHierarchies);
	}    
    
	/**
	 * Adjust the number of subscribers among different geographic regions.
	 * Note: Ratio is a list of probabilities (one for each region) which sums
	 * to one. 
	 * @param ratio
	 * @param interests
	 * @return updated interests
	 */
    ArrayList<Interest> adjustRegionRatio(double[] ratio,
    		ArrayList<Interest> interests) {
    	SubscriptionCountTable[] grids = createGrid(interests);    	   	
    	adjustRegionRatio(ratio, grids);    	
    	return updateInterests(grids); 	
    }
    
    /**
     * Adjust the number of subscribers among different geographic regions in
     * the grids (subscription count tables).
     * @param ratio
     * @param grids
     */
    private void adjustRegionRatio(double[] ratio, SubscriptionCountTable[] grids) {
    	if (ratio.length != Config.NUM_OF_REGIONS) {
    		throw new RuntimeException("Dim mismatch!");
    	}
    	
    	double[] currentRatio = new double[ratio.length];
    	for (int i = 0; i < currentRatio.length; i++) {
    		currentRatio[i] = 0;
    	}    	
    	double total = 0;
    	for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
    		SubscriptionCountTable grid = grids[i];
			for (int j = 0; j < grid.getNumOfCells(); j++) {
				currentRatio[i] += grid.get(j);
				total += grid.get(j);
			}
    	}
    	for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
    		currentRatio[i] /= total;
    	}
    	
    	    	
		for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
			SubscriptionCountTable grid = grids[i];
			if (ratio[i] != 0 && currentRatio[i] == 0) {
				throw new RuntimeException("Cannot increase the number of" +
						" subscriptions in a region if the region is" +
						" empty");
			}
			for (int j = 0; j < grid.getNumOfCells(); j++) {
				grid.set(j, grid.get(j)*ratio[i]/currentRatio[i]);
			}
		}					
		
		/*
		 * Re-adjust the total subscription counts
		 */
		for (int j = 0; j < grids[Config.NUM_OF_REGIONS].getNumOfCells(); j++) {
			total = 0;
			for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
				total += grids[i].get(j); 
			}
			grids[Config.NUM_OF_REGIONS].set(j, total);
		}
    }   
}