package dataStructures;

import java.util.ArrayList;

/**
 * Grid is a multi-dimensional table.  SubscriptionCountTable and
 * EventDistribution are the subclasses of Grid.
 * @author Albert
 *
 */
public class Grid {
	
	/**
	 * Size of the grid.
	 */
	protected int[] lengths;
	
	/**
	 * The multi-dimensional grid is implicitly maintained as an array of cells.
	 */
	protected Cell[] cells;
	
	/**
	 * Constructor with a given size of the grid.
	 * @param lengths
	 */
	public Grid(int[] lengths) {
		this.lengths = lengths;
		int numOfCells = 1;
		for (int i = 0; i < lengths.length; i++) {
			numOfCells *= lengths[i];
		}
		cells = new Cell[numOfCells];		
		/*
		 * Initialize the subscription count table.
		 * Every interest has 0 subscription count.
		 */
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell();			
		}
	}
	
	/** 
	 * @return the number of cells in the grid
	 */
	public int getNumOfCells() {
		return cells.length;
	}
		
	
	/** 
	 * @param multiDimIndices
	 * @return the specified cell
	 */
	public double get(int[] multiDimIndices) {
		int cellNum = convertIndicesToCellNum(multiDimIndices);
		return cells[cellNum].val;
	}
	
	/**
	 * Convert multi-dimensional indices into the corresponding cell number in the
	 * array of cells.
	 * @param indices
	 * @return one-dimensional index
	 */
	public int convertIndicesToCellNum(int[] indices) {
		int index = indices[0];
		int temp = lengths[0];
		for (int i = 1; i < indices.length; i++) {
			index += (indices[i] * temp);
			temp *= lengths[i];
		}
		return index;		
	}
	
	/**
	 * Reverse of the convertIndicesToCellNum method.
	 * @param cellNum
	 * @return multi-dimensional indices
	 */
	public int[] convertCellNumToIndices(int cellNum) {
		int[] indices = new int[lengths.length];
		int temp = lengths[0];
		for (int i = 1; i < lengths.length - 1; i++) {
			temp *= lengths[i];
		}
		int temp2 = cellNum;
		for (int i = lengths.length - 1; i >= 0; i--) {
			indices[i] = temp2 / temp;
			temp2 = temp2 % temp;
			if (i > 0) {
				temp /= lengths[i-1];
			}
		}		
		return indices;
	}	
	
	/**
	 * For a specified sub-grid (hyper-rectangle), find the corresponding cell
	 * numbers in the 1-d array. 
	 * @param ranges
	 * @return an array of cell numbers
	 */
	public ArrayList<Integer> getCellNumbers(Range<Integer>[] ranges) {	
		ArrayList<Integer> indices = new ArrayList<Integer>();

    	for (int i = ranges[0].min; i <= ranges[0].max; i++) {
    		indices.add(i);
    	}
    	for (int i = 1; i < ranges.length; i++) {
    		ArrayList<Integer> temp = new ArrayList<Integer>();
    		for (int j = ranges[i].min; j <= ranges[i].max; j++) {    			
    			for (int k = 0; k < indices.size(); k++) {
    				temp.add(j*lengths[i-1]+ indices.get(k).intValue());
    			}
        	}
    		indices = temp;
    	}    	
		return indices;		
	}	
}