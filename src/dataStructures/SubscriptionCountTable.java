package dataStructures;

/**
 * Grid is a multi-dimensional subscription count table.  Each cell stores a
 * subscription count for a base interest.  This data structure is needed for
 * hot interest removal, interest diffusion, etc.
 * @author Albert
 *
 */
public class SubscriptionCountTable extends Grid {	
	
	/**
	 * Constructor with a given size of the subscription count table.
	 * @param lengths
	 */
	public SubscriptionCountTable(int[] lengths) {
		super(lengths);
	}
		
	/**
	 * Set the subscription count of a given cell.
	 * @param cellNum
	 * @param numOfSubs
	 */
	public void set(int cellNum, double numOfSubs) {
		cells[cellNum].val = numOfSubs;
	}
	
	/**
	 * increase the subscription count of a given cell.
	 * @param cellNum
	 * @param numOfSubs
	 */
	public void add(int cellNum, double numOfSubs) {
		cells[cellNum].val += numOfSubs;
	}
	
	/**
	 * Set the subscription count of a given cell.
	 * @param multiDimIndices
	 * @param numOfSubs
	 */
	public void set(int[] multiDimIndices, double numOfSubs) {
		int cellNum = convertIndicesToCellNum(multiDimIndices);
		cells[cellNum].val = numOfSubs;
	}
	
	/**	 
	 * @param cellNum
	 * @return the subscription count of the specified cell.
	 */
	public double get(int cellNum) {
		return cells[cellNum].val;
	}
	
}