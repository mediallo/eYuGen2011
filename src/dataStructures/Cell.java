package dataStructures;

public class Cell {
	/**
	 * Value of a cell
	 * val = subscription count in a subscription count table
	 * val = message count for a region in the event space
	 */
	public double val;
	
	/**
	 * updatedVal is used to temporarily store the up-to-date value.
	 * It is used when a kernel smoother is used for estimating the
	 * event distribution. 
	 */
	public double updatedVal;
	
	/**
	 * The fraction of the cell that is covered by the base interests. 
	 */
	public double fracCovered;
	
	/**
	 * true iff val is up-to-date
	 */
	public boolean isUpdated;
	
	public Cell() {		
		val = 0;	
		updatedVal = 0;
		fracCovered = 0;		
		isUpdated = false;
	}
}