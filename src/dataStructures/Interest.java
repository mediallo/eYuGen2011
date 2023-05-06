package dataStructures;

import reconstruction.Param;

/**
 * An interest contains the following info--categories, region, number of
 * members, and number of listings.
 * @author Albert
 *
 */
public class Interest {
	/**
	 * Number of listings for the interest.
	 */
	public int numOfListings;
	
	/**
	 * Number of members for the interest. It may be fractional due to the
	 * smoothing and propagation steps.
	 */
	public double numOfMembers; 

	/**
	 * A set of categories for the interest. The number of path names is equal to
	 * the number of attributes in the event space.
	 */
	public Category[] categories;
	
	/**
	 * Geographic region of the interest. 
	 */
	public Param.Region region;
	
	/**
	 * Upper bound for the number of offsprings for the interest in the poset.
	 * This is only used in the interest generalization step to ensure the
	 * nodes are processed according to the partial ordering of the poset.
	 */
	public int numOfOffspringsUB;
	
	/**
	 * Constructor
	 * @param numOfAttri
	 */
	public Interest(int numOfAttri) {
		numOfListings = 0;
		numOfMembers = 0;
		categories = new Category[numOfAttri];
	}
}
