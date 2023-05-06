package reconstruction;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

import dataStructures.Category;
import dataStructures.Range;

/**
 * A category tree for an event attribute 
 * @author Albert
 *
 */
public class CategoryHierarchy {

     public static ArrayList<Category> catgDim0 = new ArrayList<Category>();
     public static ArrayList<Category> catgDim1 = new ArrayList<Category>();
	/**
	 * Input hierarchy file
	 */
	private String hierarchyFile;	

	/**
	 * the root of the hierarchy tree	  
	 */
	private Category root;	
	
	/**
	 * A hash table which allows access to a category node by specifying the
	 * name of the category.
	 */
	private Hashtable<String, Category> categories;

	/**
	 * Category id to name mapping, where id = index number.
	 */
	private ArrayList<String> idsToAtomicCategories;
	
	/**
	 * Reverse mapping from category to id
	 */
	private Hashtable<String, Integer> atomicCategoriesToIds;
	
	/**
	 * Mapping from category path to id.
	 */
	private Hashtable<String, Integer> atomicPathsToIds; 
	
	
	/**
	 * Separator in the path name
	 */
	private final static String SEPARATOR = "-";
	
	/**
	 * Constructor
	 * @param hierarchyFile
	 */
	public CategoryHierarchy(String hierarchyFile) {
		this.hierarchyFile = hierarchyFile;
		
		/*
		 * Initialize arrays and tables. 
		 */		
		idsToAtomicCategories = new ArrayList<String>();			
		atomicCategoriesToIds = new Hashtable<String, Integer>();
		atomicPathsToIds = new Hashtable<String, Integer>();
		categories = new Hashtable<String, Category>();
		
		/*
		 * Create the root.
		 */
		root = new Category();
          root.name = "root";
		root.pathName = "root";
		root.valueMin = Config.EVENT_SPACE_MIN;
		root.valueMax = Config.EVENT_SPACE_MAX;		
		categories.put("root", root);		
	}
	
	/**
	 * Create a category tree.	 
	 */
	public void createCategoryTree(int dim) {
	createCategoryTree(hierarchyFile);
    	assignRanges(root);
    	assignIds(root, 0);
    	computeNumOfOffsprings(root);
     
     //displaySubTree(root);
     if (dim == 0) saveSubTree(root,0);
     if (dim == 1) saveSubTree(root,1);

	}	
     
	/** 
	 * @return the root of the category tree.
	 */
	public Category getRoot() {
		return root;
	}
 
	/**
	 * @param pathName
	 * @return a category node which path name matches the specified path name.
	 */
	public Category getCategory(String pathName) {
		return categories.get(pathName);
	}
	
	/**
	 * @param id
	 * @return a category node which path name matches the specified id.
	 */
	public Category getCategory(int id) {
		String pathName = idsToAtomicCategories.get(id);
		if (pathName == null) {
			return null;
		} else {
			return categories.get(pathName);
		}
	}	
	
	/**
	 * @return the id of atomic categories.
	 */
	public int getNumOfAtomicCategories() {
		return idsToAtomicCategories.size();
	}
	
	/*
	public int getId(String pathName) {
		Integer id = atomicPathsToIds.get(pathName);
		if (id == null) {
			return -1;
		} else {
			return id;
		}
	}*/
	
	public Range<Double> getRange(String pathName) {
		Category category = categories.get(trim(pathName));
		if (category == null) {
			return null;
		} else {
			Range<Double> range = new Range();
			range.min = category.valueMin;
			range.max = category.valueMax;
			return range;
		}
	}
	
	
	/**  
	 * @param pathName
	 * @return true if a category node with specified path name is an atomic
	 * category.
	 */
	public boolean isAtomicCategory(String pathName) {
		Integer id = atomicPathsToIds.get(pathName);
		if (id == null) {
			return false;
		} else {
			return true;
		}
	}       
	
	
	public void displaySubTree(Category category) {
   		for (int i = 0; i < category.children.size(); i++) {
			System.out.println(category.pathName + "; " +
					category.children.get(i).pathName + ", [" +
					category.children.get(i).valueMin + ", " +
					category.children.get(i).valueMax + "]");
			displaySubTree(category.children.get(i));
		}
   	}

     public void saveSubTree(Category category, int dim) {
   		for (int i = 0; i < category.children.size(); i++) {
              if (dim == 0 ) catgDim0.add(category.children.get(i));
              if (dim == 1 ) catgDim1.add(category.children.get(i));
              saveSubTree(category.children.get(i),dim);
		}
   	}
	
	/**
	 * Compute the number of offsprings for every node in the subtree rooted
	 * at the node corresponding to the given category.
	 * @param category
	 */
	private void computeNumOfOffsprings(Category category) {
		if (category.children.size() == 0) {
			category.numOfOffsprings = 0;
		} else {
			for (int i = 0; i < category.children.size(); i++) {
				Category child = category.children.get(i);
				computeNumOfOffsprings(child);
				category.numOfOffsprings += child.numOfOffsprings;	
			}
			category.numOfOffsprings += category.children.size();
		}
	}
	    
	/**
	 * Assign the id range for every node in the subtree rooted at the node
	 * corresponding to the given category. The leftmost leaf of the subtree
	 * has id = num; the second leftmost leaf of the subtree has id = num + 1
	 * and so on.
	 * @param category
	 * @param num
	 * @return the maximum id in the subtree
	 */
	private int assignIds(Category category, int num) {
		if (category.children.size() == 0) {
			category.id = num;
			category.idMin = num;
			category.idMax = num;
			idsToAtomicCategories.add(category.pathName);
			atomicPathsToIds.put(category.pathName, num);
			atomicCategoriesToIds.put(category.name, num);
			num++;
		} else {
			for (int i = 0; i < category.children.size(); i++) {
				Category child = category.children.get(i);       			
				num = assignIds(child, num);
			}	
			category.idMin = category.children.get(0).idMin;
			category.idMax = category.children.get(category.children.size() - 1).idMax;
		}
		return num; 	
	}
    
	/**
	 * Assign ranges for the given category and its sub-categories.
	 * @param category
	 */
	private void assignRanges(Category category) {
		if (category.children.size() == 0) return;
         
		double width = (category.valueMax - category.valueMin) / (category.children.size() * 2 + 1);
		if (width == 0) {
			throw new RuntimeException("assignRanges: Width = 0");
		}

		for (int i = 0; i < category.children.size(); i++) {
			Category child = category.children.get(i);
			child.valueMin = category.valueMin + (2 * i + 1) * width;
			child.valueMax = category.valueMin + (2 * i + 2) * width;
               

			if (child.valueMin >= child.valueMax) {
				throw new RuntimeException("assignRanges: min >= max: " +child.valueMin + ", " + child.valueMax);
			}
			assignRanges(category.children.get(i));
		}
	}
    
	/**
	 * Read the category file and create a corresponding tree.
	 * @param filename
	 */
	private void createCategoryTree(String filename){
		File file = new File(filename);
		BufferedReader input = null;
		int lineNum = 1;
		String line = null;
		try {			
			input = new BufferedReader(new FileReader(file));			
			while ((line = input.readLine()) != null) {
				addCategory(trim(line));
				lineNum++;
			}
              
			input.close();          
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error:\nFile name: " + filename +"\nline number: " + lineNum + "\nInput line: " + line);
			e.printStackTrace();
		}
          
	}	
	
	/**
	 * Create and add category node into the category tree. The path name
	 * indicates the sequence of categories along the path from the root to
	 * the category node. 
	 * @param pathName
	 * @return the category node corresponding to the given pathname
	 */
	private Category addCategory(String pathName) {
		if (pathName.isEmpty()) {
			throw new RuntimeException("The full path name of the category is empty");
		} 
		
		Category category = categories.get(pathName);
		if (category != null) {
			// category has already been created
			return category;
		}
		
		category = new Category();
		int index = pathName.lastIndexOf(SEPARATOR);
		if (index == pathName.length() - 1) {
			throw new RuntimeException("The category name is empty");			
		}
		category.name = pathName.substring(index + 1,pathName.length());
		category.pathName = pathName;

		if (index == -1) {
			root.children.add(category);
			category.parent = root;
		} else {
			String parentPathName = category.pathName.substring(0, index);
			Category parent = categories.get(parentPathName);
			if (parent == null) {
				parent  = addCategory(parentPathName);
			}			
			parent.children.add(category);
			category.parent = parent;			    					
		} 
		categories.put(category.pathName, category);
         
		return category;
	}
	
	/**
	 * Remove whitespace before and after each category in the line.
	 * @param line
	 * @return trimmed line
	 */
	public static String trim(String line) {
		if (line == null) return null;
		
		String[] names = line.split(SEPARATOR);     		
		
		StringBuffer result = new StringBuffer();
	    result.append(names[0].trim());
	    for (int i=1; i< names.length; i++) {
	    	result.append(SEPARATOR);
	        result.append(names[i].trim());	        
	    }
	    return result.toString();
	}		
}