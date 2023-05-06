package reconstruction;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import dataStructures.Category;
import dataStructures.Interest;

/**
 * Interest generalization step. To extrapolate broader interests from base ones,
 * we construct a poset of interests and propagate subscription counts upwards
 * from base interests to broader interests. 
 * @author Albert
 *
 */
class InterestGeneralization {
	/**
	 * An array of category hierarchies, one per event attribute.
	 * A category can be accessed by searching from the root or by specifying the
	 * name of the category.
	 */
	private CategoryHierarchy[] categoryHierarchies;
	
	/**
	 * Constructor
	 * @param categoryHierarchies
	 */
	InterestGeneralization(CategoryHierarchy[] categoryHierarchies) {
		this.categoryHierarchies = categoryHierarchies;		
	}
	
	/**
	 * Get the parent category.
	 * @param pathName
	 * @param attributeNum
	 * @return parent category
	 */
	private Category getParentCategory(String pathName, int attributeNum) {
		int index = pathName.lastIndexOf("-");
		String parentPathName;
		if (index != -1) {
			parentPathName = pathName.substring(0,	index).trim();	   						   					
		} else if (!pathName.equals("root")) {
			parentPathName = "root";		   				
		} else {
			return null;
		}
		Category category = categoryHierarchies[attributeNum].getCategory(parentPathName);
		if (category == null) {
			throw new RuntimeException("The category " + parentPathName + " is undefined");
		}
		return category;	
	}

	/**
	 * Create the parent interest according to the partial ordering of Poset.
	 * An interest has at most d parents, where d is the number of attributes.
	 * "attributeNum" indicates the parent interest that we want to create.
	 * @param interest
	 * @param attributeNum
	 * @return parent interest
	 */
	private Interest createParent(Interest interest, int attributeNum) {
		Category category = categoryHierarchies[attributeNum].getCategory(
				interest.categories[attributeNum].pathName);
		if (category == null) {
			return null;
		}			
		Interest parent = new Interest(Config.EVENT_DIM);   					
		Category parentCategory = getParentCategory(category.pathName, attributeNum);
		if (parentCategory == null) {
			// no parent
			return null;
		}   				
		parent.categories[attributeNum] = parentCategory;	   				
		for (int k = 0; k < Config.EVENT_DIM; k++) {
			if (k != attributeNum) {
				parent.categories[k] = interest.categories[k];	   						
			}
		}   					   				
					   				
		parent.region = interest.region;
		return parent;
	}

	/**
	 * Generalize interests from a list of base interests.
	 * @param fraction
	 * @param base interests
	 * @param interestTable
	 * @return hierarchical interests
	 */
	ArrayList<Interest> reconstructHierarchicalInterests(double fraction, ArrayList<Interest> interests, Hashtable<String, Interest> interestTable) {
		if (fraction <= 0 || fraction > 1) {
			throw new RuntimeException("HIERARCHY FRAC must be between 0 (exclusive) and 1 (inclusive)");
		}
		if (Config.GROUP_HIERARCHY_MODE == Param.InterestGeneralizationMode.Proportional) {
			return reconstructHierarchicalInterestsProportional(fraction, interests, interestTable);
		} else {
			return reconstructHierarchicalInterestsUniform(fraction, interests, interestTable);
		}
	}
	
	/**
	 * Generalize interests (proportional mode).  Every interest propagates the
	 * specified fraction of subscription count to its parents. 
	 * @param fraction
	 * @param base interests
	 * @param interestTable
	 * @return hierarchical interests
	 */
	private ArrayList<Interest> reconstructHierarchicalInterestsProportional(
			double fraction,
               ArrayList<Interest> interests,
			Hashtable<String, Interest> interestTable) {
          /*
		 * Interests will be processed that satisfies the partial ordering of the poset.
		 */
		InterestComparator interestComparator = new InterestComparator();
		PriorityQueue<Interest> interestQueue = new PriorityQueue<Interest>(interests.size(), interestComparator);
		interestQueue.addAll(interests);
          System.out.println("reconstructHierarchicalInterestsProportional: Base interests list size = "+interests.size());
          
		ArrayList<Interest> outputs = new ArrayList<Interest>();
          int cnt = 0;
          //parse the interest list
		while (interestQueue.size() > 0) {			
				Interest interest = interestQueue.remove(); // get the first interest from the head tail
				int numOfParents = 0;

				//Calculate the number of parents for each interest
				for (int j = 0; j < Config.EVENT_DIM; j++) {
					Category category = categoryHierarchies[j].getCategory(interest.categories[j].pathName);
					if (category != null) {
						int index = category.pathName.lastIndexOf("-");
						if (index != -1 || !category.pathName.equals("root")) {	   					   						   							
							numOfParents++;	   					
						}
					}
				}  			   		
				// Insert all parents into a list
				for (int j = 0; j < Config.EVENT_DIM; j++) {	   			
					Interest parent = this.createParent(interest, j);
					if (parent == null) {						 
						continue;
					}
                         //extrapolate the subscription's count to parents
					parent.numOfMembers = interest.numOfMembers * fraction / numOfParents;
					parent.numOfOffspringsUB = 0;
					for (int k = 0; k < Config.EVENT_DIM; k++) {
						if (parent.categories[k].children != null) {
							parent.numOfOffspringsUB +=
								parent.categories[k].numOfOffsprings;
										
						}
					}
	   		   		
					// Find key for the interest hash table
					String key = parent.region.toString();
					for (int k = 0; k < Config.EVENT_DIM; k++) {
	   		   			key += ";";
	   		   			key += parent.categories[k].pathName;
					}
	   					
					if (interestTable.get(key)== null) {
						/*
						 * The parent node hasn't been visited before. Create the
						 * node and insert it into the hash table.
						 */
	   		   			interestQueue.add(parent);		
	   		   			interestTable.put(key, parent);
                              cnt++;
	   		   		} else {
	   		   			/*
	   		   			 * The parent node has already been created. Increase its
	   		   			 * membership count.
	   		   			 */	   		   			
	   		   			Interest g = interestTable.get(key);
	   		   			g.numOfMembers += parent.numOfMembers;
	   		   		}	   			
				}
                    //System.out.println("reconstructHierarchicalInterestsProportional: numOfParents = "+numOfParents);
				if (numOfParents != 0) {
                      //System.out.println("interest.numOfMembers = "+interest.numOfMembers);
					/*
					 *  Has incoming edge in the poset.  Reduce the membership
					 *  count in the current node, as they've been propagated up
					 *  to the parent node(s).
					 */
					interest.numOfMembers *= (1 - fraction); // if fraction = 1 then numOfMembers = 0
                      
				}
				outputs.add(interest);
		}
          
		System.out.println("reconstructHierarchicalInterestsProportional: Parent interests list size = "+outputs.size());
          System.out.println("reconstructHierarchicalInterestsProportional: parent interests count = "+cnt);
		return outputs;
	}

	/**
	 * Generalize interests (uniform mode).  Every child of an interest
	 * propagates the same amount of subscription count to the interest. 
	 * @param fraction
	 * @param base interests
	 * @param interestTable
	 * @return hierarchical interests
	 */
	private ArrayList<Interest> reconstructHierarchicalInterestsUniform(
			double fraction,
               ArrayList<Interest> interests,
			Hashtable<String, Interest> interestTable) {

          /*
		 * Interests will be processed that satisfies the partial ordering of 
		 * the poset.
		 */
          
          InterestComparator interestComparator = new InterestComparator();
		
		PriorityQueue<Interest> interestQueue = new PriorityQueue<Interest>(interests.size(), interestComparator);
		interestQueue.addAll(interests);
          System.out.println("reconstructHierarchicalInterestsUniform: Base interests list size = "+interests.size());
		ArrayList<Interest> outputs = new ArrayList<Interest>(); // returned list of parents
                int cnt =0;
		while (interestQueue.size() > 0) {
				Interest interest = interestQueue.remove();
				int numOfParents = 0;
				//Calculate the number of parents for each interest
				for (int j = 0; j < Config.EVENT_DIM; j++) {
					Category category = categoryHierarchies[j].getCategory(interest.categories[j].pathName);
					if (category != null) {
						int index = category.pathName.lastIndexOf("-");
						if (index != -1 || !category.pathName.equals("root")) {
							numOfParents++;	   
						}
					}
				}  			   		
                    //System.out.println("reconstructHierarchicalInterestsUniform:"+interestQueue.size()+" numOfParents = "+numOfParents);
				// Insert all parent into a list
				for (int j = 0; j < Config.EVENT_DIM; j++) {
					Interest parent = this.createParent(interest, j);
                         
					if (parent == null) {							
						continue;
					}
                         
                         /*
                          * the more the fraction is nearst to 0, the less is the number of members of the interest
                          * i.e the number of subscriptions to this interest.
                          *
                          */
					parent.numOfMembers = interest.numOfMembers * fraction / numOfParents;
					parent.numOfOffspringsUB = 0;

					for (int k = 0; k < Config.EVENT_DIM; k++) {
						if (parent.categories[k].children != null) {
							parent.numOfOffspringsUB += parent.categories[k].numOfOffsprings;
						}
					}
	   		   		
					// Find key for hashtable
					String key = parent.region.toString();
	   		   		for (int k = 0; k < Config.EVENT_DIM; k++) {
	   		   			key += ";";
	   		   			key += parent.categories[k].pathName;
	   		   		}
	   					
	   		   		if (interestTable.get(key)== null) {
	   		   			/*
						 * The parent node hasn't been visited before. Create the
						 * node and insert it into the hash table.
						 */	   		   		
	   		   			interestQueue.add(parent);
                              cnt++;
	   		   			interestTable.put(key, parent);
	   		   		} else {
	   		   			/*
	   		   			 * The parent node has already been created. Decrease its
	   		   			 * membership count if the number of subs propagated from
	   		   			 * the current node is less than its membership count.
	   		   			 */	   		   		
	   		   			Interest g = interestTable.get(key);	   		   			
	   		   			if (parent.numOfMembers < g.numOfMembers) {
	   		   				g.numOfMembers = parent.numOfMembers;
	   		   			}
	   		   		}

				}
				outputs.add(interest);
		}
		
		/*
		 * The interests in outputs have already been ordered according to the
		 * partial ordering of the poset.
		 */
		for (int i = 0; i < outputs.size(); i++) {
			Interest interest = outputs.get(i);			
			for (int j = 0; j < Config.EVENT_DIM; j++) {
	   			Category category = categoryHierarchies[j].getCategory(outputs.get(i).categories[j].pathName);
	   			if (category != null) {
	   				int index = category.pathName.lastIndexOf("-");
	   				if (index != -1 || !category.pathName.equals("root")) {  					   						   							
	   					String parentPathName;	   				
		   				if (index != -1) {
		   					parentPathName = category.pathName.substring(0, index).trim();
		   				} else {
		   					parentPathName = "root";
		   				} 	   					   				

		   				// Find key for hashtable
		   		   		String key = outputs.get(i).region.toString();
		   		   		for (int k = 0; k < Config.EVENT_DIM; k++) {
		   		   			key += ";";
		   		   			if (k != j) {
		   		   				key += interest.categories[k].pathName;
		   		   			} else {
		   		   				key += parentPathName;
		   		   			}
		   		   		}

		   		   		Interest g = interestTable.get(key);
                              //this will reduce the number of member for each interests in the outputs list
		   		   		outputs.get(i).numOfMembers -= g.numOfMembers;
	   				}
	   			}
	   		}  		
		}
         System.out.println("reconstructHierarchicalInterestsUniform: parent interests list size = "+outputs.size());
         System.out.println("reconstructHierarchicalInterestsUniform: parent interests count = "+cnt);



          return outputs;
	}

}