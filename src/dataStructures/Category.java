package dataStructures;

import java.util.ArrayList;

/**
 * A category contains a name, a full path name, and a range in the event space.
 * @author Albert
 *
 */
public class Category {
	/**
	 * Category name such as "Buddhism"
	 */
	public String name;
	
	/**
	 * Full path name such as "Society - Religion and Spirituality - Buddhism"
	 */
	public String pathName;
	
	/**	  
	 * Every category is converted into an interval [valueMin, valueMax]. in Event Space
	 */
	public double valueMin, valueMax;
	

	/**
	 * Each atomic category is assigned a id number, ranging from 0 to the number
	 * of atomic categories minus one. The leftmost leaf of the tree has
	 * id = 0; the second leftmost one has id = 1 and so on.
	 */
	public int id;
	
	/**	  
	 * The id range of a non-atomic category is defined as the range of ids in
	 * its subtree. For an atomic category, idMin = id = idMax.
	 */
	public int idMin, idMax;
	
	/**
	 * Number of offsprings in the subtree rooted at the category.
	 */
	public int numOfOffsprings;
	
	/**
	 * Its parent category in the category tree.
	 */
	public Category parent;
	
	/**
	 * A list of its children categories in the category tree.
	 */
	public ArrayList<Category> children;
	
	public Category() {
		id = -1;
		children = new ArrayList<Category>();
	}	
}