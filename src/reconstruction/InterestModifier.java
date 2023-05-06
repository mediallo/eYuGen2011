package reconstruction;

import java.util.ArrayList;

import dataStructures.SubscriptionCountTable;
import dataStructures.Interest;

/**
 * Convert a list of interests into subscription count tables (such that a series
 * of transformations can be performed) and vice versa.    
 * @author Albert
 *
 */
public class InterestModifier {
	/**
	 * An array of category hierarchies, one per event attribute.
	 * A category can be accessed by searching from the root or by specifying
	 * the name of the category.
	 */
	protected CategoryHierarchy[] categoryHierarchies;
	
	/**
	 * Constructor
	 * @param categoryHierarchies
	 */
	InterestModifier(CategoryHierarchy[] categoryHierarchies) {
		this.categoryHierarchies = categoryHierarchies;
	}    

	/**
	 * A grid is a multi-dimensional subscription count table and each cell
	 * stores the subscription count of a base interest. Given a list of
	 * interests, the method creates m grids (subscription count tables),
	 * where m is the number of geographic regions plus one. Each region has
	 * its own grid and the extra grid stores the total subscription counts
	 * over all regions.  
	 * Prerequisite: Interests are not hierarchical.
	 * @param interests
	 * @return an array of grids (subscription count tables), one for each
	 * region
	 */
	protected SubscriptionCountTable[] createGrid(
			ArrayList<Interest> interests) {
		int[] sizes = new int[categoryHierarchies.length]; 
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = categoryHierarchies[i].getNumOfAtomicCategories();
		}
		
		SubscriptionCountTable[] grids =
			new SubscriptionCountTable[Config.NUM_OF_REGIONS + 1];
		for (int i = 0; i < grids.length; i++) {
			grids[i] =  new SubscriptionCountTable(sizes); 
		}		 	
    	
		for (int i = 0; i < interests.size(); i++) {			
			Interest interest = interests.get(i);
			int[] multiDimIndices = new int[interest.categories.length]; 
			for (int j = 0; j < interest.categories.length; j++) {
				int index = interest.categories[j].id;
				if (index == -1) {
					throw new RuntimeException("The interest list contains an undefined base interest(s)");
				}	
				multiDimIndices[j] = index;
			}
			grids[interest.region.ordinal()].set(multiDimIndices, interest.numOfMembers);
		}
    	
		/*
		 * compute total number of subs across all geographic regions for
		 * every interest. 
		 */
		for (int j = 0; j < Config.NUM_OF_REGIONS; j++) {
			add(grids[j], grids[Config.NUM_OF_REGIONS]);
		}
		return grids;    	
	}
	
	/**
	 * For each interest in grid2, increase its subscription count by its
	 * subscription count in grid1.
	 * @param grid1
	 * @param grid2
	 */
	private void add(SubscriptionCountTable grid1, SubscriptionCountTable grid2) {
		for (int i = 0; i < grid2.getNumOfCells(); i++) {
			grid2.add(i, grid1.get(i));
		}
	}
	
	/**
	 * Given subscription count tables, create a list of corresponding
	 * interests.
	 * @param grids
	 * @return interests
	 */
	protected ArrayList<Interest> updateInterests(SubscriptionCountTable[] grids) {
		ArrayList<Interest> interests = new ArrayList<Interest>();
		for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
			SubscriptionCountTable grid = grids[i];
			for (int j = 0; j < grid.getNumOfCells(); j++) {
				Interest interest = new Interest(Config.EVENT_DIM);
				interest.numOfMembers = grid.get(j);
				if (interest.numOfMembers < 0) {
					throw new RuntimeException("Subscription count cannot be negative: " + interest.numOfMembers);
				}				 
				int[] indices = grid.convertCellNumToIndices(j);
				for (int k = 0; k < indices.length; k++) {
					interest.categories[k] = categoryHierarchies[k].getCategory(indices[k]);
				}
				interest.region = Param.Region.values()[i];
				interests.add(interest);		
			}
		}		
		return interests;    	
	}
}