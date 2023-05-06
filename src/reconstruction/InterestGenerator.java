package reconstruction;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

import dataStructures.Interest;

/**
 * Extract base interests from the files and perform a series of
 * transformations.
 * @author Albert
 *
 */
public class InterestGenerator {
	/**
	 * Name of input file.
	 */	
	private String interestFile;
	
	/**
	 * Number of event attributes
	 */
	private int numOfAttributes;

	/**
	 * An array of category hierarchies, one per event attribute.
	 * A category can be accessed by searching from the root or by specifying
	 * the name of the category.
	 */
	private CategoryHierarchy[] categoryHierarchies;

	/**
	 * A list of interests
	 */
	private ArrayList<Interest> interests;
	
	/**
	 * A hash table which allows interests in the list to be accessed by 
	 * specifying their names.
	 */	
	private Hashtable<String, Interest> interestTable;
	
	/**
	 * Constructor
	 * @param numOfAttributes
	 * @param categoryHierarchies
	 * @param interestFile
	 */
	public InterestGenerator(int numOfAttributes, CategoryHierarchy[] categoryHierarchies, String interestFile) {
		this.interestFile = interestFile;
		this.numOfAttributes = numOfAttributes;
		/*
		 * Initialize arrays. 
		 */
		this.categoryHierarchies = categoryHierarchies;		
		interestTable = new Hashtable<String, Interest>();
          
	}

	/**
	 * Extract base interests from the files and perform a series
	 * of transformation.
	 * @return interests
	 */
	public ArrayList<Interest> extractInterests() {			

       extractBaseInterests();
		
		if (Config.PRUNE_HOT_INTERESTS) {
			System.out.println("Pruning hot interests...");
			HotInterestRemoval hotInterestRemoval = new HotInterestRemoval(categoryHierarchies);
			interests = hotInterestRemoval.execute(interests, Config.NUM_OF_HOT_INTERESTS);
			System.out.println("Done");
		}
		
		if (Config.INTEREST_DIFFUSION) {
			System.out.println("Performing interest diffusion...");
			InterestDiffusion smoother = new InterestDiffusion(categoryHierarchies);
			if (Config.SMOOTHING_P >= 1 || Config.SMOOTHING_P < 0) {
				throw new RuntimeException("Smoothing P should be between 0 (inclusive) and 1 (exclusive),\ncurrent P: " + 
						Config.SMOOTHING_P);
			}
			interests = smoother.smooth(Config.SMOOTHING_P, interests);
			System.out.println("Done");
		}

		if (Config.SUBS_RATIO_MODE == Param.SubsRatioMode.ModifyRatio) {
			System.out.println("Modifying subscription ratio across regions...");
			RegionRatioModifier modifier = new RegionRatioModifier(categoryHierarchies);
			interests = modifier.adjustRegionRatio(Config.REGION_RATIO, interests);
			System.out.println("Done");
		}
			
		if (Config.GROUP_HIERARCHY_MODE != Param.InterestGeneralizationMode.None) { 
			System.out.println("Generalizing interests...");
			InterestGeneralization generalization = new InterestGeneralization(categoryHierarchies);
			interests = generalization.reconstructHierarchicalInterests(Config.HIERARCHY_FRAC, interests, interestTable);
			System.out.println("Done");
		} 
		return interests;
	}			

	/**
	 * Extract base interests from the interest file.
	 */
 	private void extractBaseInterests(){
          System.out.println("extractBaseInterests: Extarct base interests from inputfile ( "+interestFile+" )...");
 		File file = new File(interestFile);       
 		BufferedReader input = null;
 		try {
 			input = new BufferedReader(new FileReader(file));
 			String line = null;
 			interests = new ArrayList<Interest>();
 			while ((line = input.readLine()) != null) {        		
 				String[] strs = line.split(";");
 				createInterest(strs);    			
 			}
 			input.close();          
 		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
          
	}
 	
 	/**
 	 * Parse an input string which format is:
 	 * Region; Category1; Category2; #listings; #members
 	 * @param strs
 	 */
	private void createInterest(String[] strs) {		
		Interest interest = new Interest(numOfAttributes);
		interest.region = Param.Region.valueOf(strs[0].trim());
		String key = interest.region.toString();

   		for (int i = 0; i < numOfAttributes; i++) {
   			String pathName = CategoryHierarchy.trim(strs[i+1]);
   			if (!categoryHierarchies[i].isAtomicCategory(pathName)) {
   				throw new RuntimeException("createInterest: An interest contains a" + " non-atomic category: " + pathName);
   			}
   	   			
   			interest.categories[i] = categoryHierarchies[i].getCategory(pathName);
                       
               if (interest.categories[i] == null) {
   				throw new RuntimeException("createInterest: category is undefined");
   			}
   			
   			interest.numOfOffspringsUB = 0;
   			key += ";";
   			key += pathName;
		}
   			
   		interest.numOfListings = Integer.parseInt(strs[numOfAttributes + 1].trim());
   		interest.numOfMembers = Integer.parseInt(strs[numOfAttributes + 2].trim());
   		
   		/*
   		 *  Should we assume there is at least one member (group owner
   		 * himself) for each Google group?
   		 */
   		if (interest.numOfMembers == 0) {
   			interest.numOfMembers = 1;
   		}   		
   		
		interests.add(interest);
		interestTable.put(key, interest);
          //System.out.println("createInterest: interests size = "+interests.size());
          //System.out.println("createInterest: interestTable size = "+interestTable.size());
	}	
}