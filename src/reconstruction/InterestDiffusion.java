package reconstruction;

import java.util.ArrayList;

import dataStructures.Category;
import dataStructures.SubscriptionCountTable;
import dataStructures.Interest;
import dataStructures.Range;

/**
 * Interest diffusion step. It aims at reducing the popularity variance among
 * related interests. In the case of Google Groups, the notion of
 * relatedness can derived from the topic and language hierarchies.
 * @author Albert
 *
 */
public class InterestDiffusion extends InterestModifier{
	/**
	 * Constructor
	 * @param categoryHierarchies
	 */
	public InterestDiffusion(CategoryHierarchy[] categoryHierarchies) {
		super(categoryHierarchies);
	}	
    
	/**
	 * Perform an interest diffusion
	 * @param p
	 * @param interests
	 * @return updatedInterests
	 */
    public ArrayList<Interest> smooth(double p, ArrayList<Interest> interests) {
    	/*
    	 * One grid (subscription count table) for each geographic region,
    	 * and an extra grid for total subscription count over all regions.
    	 */
    	SubscriptionCountTable[] grids = createGrid(interests);
    	
    	Category[] categories = new Category[categoryHierarchies.length];
    	for (int i = 0; i < categoryHierarchies.length; i++) {
    		categories[i] = categoryHierarchies[i].getRoot();
    	}
    	/*
    	 * Start from the root.  The average subscription count for the
    	 * children of the root node is not changed by the diffusion step.
    	 */
    	double[] oldAvg = computeAvg(categories, grids);
    	double[] newAvg = new double[oldAvg.length];
    	for (int i = 0; i < oldAvg.length; i++) {
    		newAvg[i] = oldAvg[i];
    	}
    	
    	diffuse(categories, Math.sqrt(p), grids, oldAvg, newAvg);
    	
    	/*
	* Re-adjust the last grid (total subscription counts)
	*/
		for (int j = 0; j < grids[Config.NUM_OF_REGIONS].getNumOfCells(); j++) {
			double total = 0;
			for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
				total += grids[i].get(j); 
			}
			grids[Config.NUM_OF_REGIONS].set(j, total);
		}
    	
    	return updateInterests(grids);    	
    }  

    /**
     * Compute the average subscription count for the children of the node
     * corresponding to the specified categories.  The subscription count is
     * returned in an array format; The i^th element is an average
     * subscription count for i^th geographic region.
     * @param categories
     * @param grids
     * @return subscription counts
     */
    private double[] computeAvg(Category[] categories,
    		SubscriptionCountTable[] grids) {
    	double[] totals = countTotalSubs(categories, grids);
    	int numOfChildren = getNumOfChildren(categories);
    	for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
    		totals[i] /= numOfChildren;
    	}
    	return totals;
    }

    /**
     * Count the total number of subscriptions under the node corresponding
     * to the specified categories.  The subscription count is returned in an
     * array format; The i^th element is the total subscription count for
     * i^th geographic region.
     * @param categories
     * @param grids
     * @return subscription counts 
     */
    private double[] countTotalSubs(Category[] categories,
    		SubscriptionCountTable[] grids) {
    	double[] sum = new double[Config.NUM_OF_REGIONS];    	
    	for (int i = 0; i< Config.NUM_OF_REGIONS; i++) {
    		sum[i] = 0;
    	}           	
    	Range<Integer>[] ranges = new Range[categories.length];
    	for (int i = 0; i < categories.length; i++) {
    		ranges[i] = new Range<Integer>();
    		ranges[i].min = categories[i].idMin;
    		ranges[i].max = categories[i].idMax;
    	}
    	
    	ArrayList<Integer> cellNumbers = grids[0].getCellNumbers(ranges);
    	
    	for (int k = 0; k < Config.NUM_OF_REGIONS; k++) {
    		sum[k] += findNumberOfMembers(grids[k], cellNumbers);
    	}
    	return sum;
    }    
       
    /**
     * Find the number of children for the node corresponding to the
     * specified categories.
     * @param categories
     * @return the number of children
     */
    private int getNumOfChildren(Category[] categories) {
    	int size = 1;
    	for (int i = 0; i < categories.length; i++) {
    		if (categories[i].children != null && categories[i].children.size() != 0) {
    			size *= categories[i].children.size();
    		}
    	}
    	return size;
    }

    /**
     * Start an interest diffusion for the subtree rooted at the node 
     * corresponding to the specified categories.
     * @param categories
     * @param sqrtP
     * @param grids
     * @param oldAvg
     * @param newAvg
     */
    private void diffuse(Category[] categories, double sqrtP,
    		SubscriptionCountTable[] grids, double[] oldAvg, double[] newAvg){
    	// Base case
    	boolean isBaseCase = true;
    	for (int i = 0; i < categories.length; i++) {
    		if (categories[i].children != null && categories[i].children.size() > 0) {
    			isBaseCase = false;
    		}
    	}
    	
    	if (isBaseCase) {
			int[] multiDimIndices = new int[categories.length]; 
			for (int j = 0; j < categories.length; j++) {
				int index = categories[j].idMin;
				if (index == -1) {
					throw new RuntimeException("The interest list contains" +
							" an undefined base interest(s)");
				}	
				multiDimIndices[j] = index;
			}

    		for (int k = 0; k < Config.NUM_OF_REGIONS; k++) {								
				grids[k].set(multiDimIndices, newAvg[k]);
			}
        	return;
    	}
    	
    	ArrayList<Category[]> children = getChildren(categories);
    	for (int i = 0; i < children.size(); i++) {
    		computeNewAvg(children.get(i), sqrtP, grids, oldAvg, newAvg);
    	}   	        
    }
        
    /** 
     * @param categories
     * @return deep copy of categories
     */
    private Category[] getClone(Category[] categories) {
    	Category[] newCategories = new Category[categories.length];
    	for (int i = 0; i < categories.length; i++) {
    		newCategories[i] = categories[i];
    	}
    	return newCategories;
    }
    
    /**
     * For the node corresponding to the specified categories, find all its
     * children nodes and return their corresponding categories.
     * @param categories
     * @return children's categories
     */
    private ArrayList<Category[]> getChildren(Category[] categories) {
    	ArrayList<Category[]> children = new ArrayList<Category[]>();
    	
    	if (categories[0] != null && categories[0].children.size() != 0) {
    		for (int i = 0; i < categories[0].children.size(); i++) {
    			Category[] child = new Category[categories.length];
    			child[0] = categories[0].children.get(i);
    			children.add(child);
    		}
		} else {
			Category[] child = new Category[categories.length];
			child[0] = categories[0];
			children.add(child);
		}
    	
    	for (int i = 1; i < categories.length; i++) {
    		if (categories[i] != null && categories[i].children.size() != 0) {
    			ArrayList<Category[]> temp = new ArrayList<Category[]>();
    			for (int j = 0; j < children.size(); j++) {
    				for (int k = 0; k < categories[i].children.size(); k++) {
    					Category[] child = getClone(children.get(j));    					
    					child[i] = categories[i].children.get(k);
    					temp.add(child);
    				}    			
    			}
    			children = temp;
    		} else {
    			for (int j = 0; j < children.size(); j++) {
    				children.get(j)[i] = categories[i];
    			}
    		}
    	}
    	return children;
    }
    
    
    /**
     * Compute the new average subscription count for the node corresponding to 
     * the specified categories after the diffusion.
     * @param categories
     * @param sqrtP
     * @param grids
     * @param oldAvg
     * @param newAvg
     */
    private void computeNewAvg(Category[] categories, double sqrtP,
    		SubscriptionCountTable[] grids, double[] oldAvg, double[] newAvg){
    	double[] oldAvgIJ = new double[Config.NUM_OF_REGIONS];
    	double[] newAvgIJ = new double[Config.NUM_OF_REGIONS];
    	int numOfChildren = getNumOfChildren(categories);
        
		oldAvgIJ = computeAvg(categories, grids);		
		for (int k = 0; k < Config.NUM_OF_REGIONS; k++) {        			
			newAvgIJ[k] = newAvg[k] / numOfChildren - 
					sqrtP * (oldAvg[k] / numOfChildren - oldAvgIJ[k]);
		}
		// Recursively diffuse the interest
		diffuse(categories, sqrtP , grids, oldAvgIJ, newAvgIJ);
    }
    
    /**
     * Sum over the subscription counts of the specified cells.
     * @param grid
     * @param cells
     * @return subscription count
     */
    private double findNumberOfMembers(SubscriptionCountTable grid,
    		ArrayList<Integer> cells) {
    	double count = 0;
    	
    	for (int i = 0; i < cells.size(); i++) {
    		count += grid.get(cells.get(i).intValue());
    	}
    	return count;
    }
}