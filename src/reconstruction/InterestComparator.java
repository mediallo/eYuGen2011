package reconstruction;

import java.util.Comparator;

import dataStructures.Interest;

/**
 * Order the interests by the number of offsprings in the poset.  This
 * ensures that interests would be processed that satisfies the partial
 * ordering of the poset.	 
 * @author Albert
 *
 */
public class InterestComparator implements Comparator<Interest> {
	
	public InterestComparator() {
	}	
	
    public int compare(Interest i1, Interest i2) {    	
    	if (i1.numOfOffspringsUB < i2.numOfOffspringsUB) {
			return -1;
		} else if (i1.numOfOffspringsUB > i2.numOfOffspringsUB) {
			return 1;
		} else {
			return 0;
		}
    }
}