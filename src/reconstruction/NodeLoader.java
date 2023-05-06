package reconstruction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import reconstruction.Param.Region;

import utilities.Distance;

import dataStructures.Node;

/**
 * Extract the set of network locations from files and return coordinates if
 * requested.
 * @author Albert
 *
 */
public class NodeLoader {	
	/**
	 * Network dimension
	 */
	private int dim;
	
	/**
	 * Maximum number of nodes among all regions
	 */
	private int maxRegionSize;
	
	/**
	 * Name of file that stores the region info of nodes
	 */
	private String regionFilename;
	
	/**
	 * Name of file that stores the coordinate info of nodes
	 */
	private String coordFilename;
	
	/**
	 * An array of regions, each of which is a list of nodes.
	 */
	private ArrayList<Node> geographicRegions[];
		
	/**
	 * Generate a coordinate near the selected node in the region.
	 * @param region
	 * @param localId
	 * @param generator
	 * @return coordinate
	 */
static double[] generateCoords(ArrayList<Node> region, int localId, Random generator) {

  double[] coords = region.get(localId).coords.clone();

		/*
		 * Estimate maximum distance from the chosen node
		 */
		double dist = Double.MAX_VALUE;
		for (int j = 0; j < region.size(); j++) {
			if (j != localId) {
				double temp = Distance.getDistance(region.get(j).coords, coords);
				if (temp < dist) dist = temp;
			}
		}
		dist /= 3;

		/*
		 * Get coordinate
		 */
		dist = dist / Math.sqrt(Config.NETWORK_DIM);
		for (int j = 0; j < Config.NETWORK_DIM; j++) {
			coords[j] = coords[j] + dist * generator.nextDouble();
		}
		return coords;
	}
	
	/**
	 * Constructor
	 * @param dim
	 * @param regionFilename
	 * @param coordFilename
	 */
	NodeLoader(int dim, String regionFilename, String coordFilename) {		
		this.dim = dim;
		this.regionFilename = regionFilename;
		this.coordFilename = coordFilename;
		geographicRegions = new ArrayList[Config.NUM_OF_REGIONS];
		for (int i = 0; i < geographicRegions.length; i++) {
			geographicRegions[i] = new ArrayList<Node>();
		}	
	}	
	
	/**
	 * Read a list of nodes from files and find the maximum region size.
	 */
	void execute() {
		ArrayList<Node> nodes = readNodeInfo(regionFilename);
		readCoords(nodes, coordFilename);		
		findMaxRegionSize();		
	}
	
	/**
	 * Getter method for maximum region size.
	 * @return the maximum region size
	 */
	int getMaxNumOfNodesFromARegion() {
		return maxRegionSize;
	}

	/**
	 * Get the (deep) copies of nodes in all geographic regions.
	 * @return an array of regions, each of which is a list of nodes. 
	 */
	ArrayList<Node>[] getRegions() {
          ArrayList<Node> geographicRegions[] = new ArrayList[Config.NUM_OF_REGIONS];
         for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
              geographicRegions[i] = getRegion(Param.Region.values()[i]);
         }
         return geographicRegions;
	}
	
	
	/**
	 * Get the (deep) copies of nodes in the specified region.
	 * @param region
	 * @return a list of nodes in the specified region
	 */
	ArrayList<Node> getRegion(Param.Region region) {		
		ArrayList<Node> temp = null;
		for (int i = 0; i < Param.Region.values().length; i++) {
			if (region == Param.Region.values()[i]) {
				temp = geographicRegions[i];
				break;
			}
		}
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < temp.size(); i++) {
			nodes.add((Node)temp.get(i).clone());
		}		
		return nodes;
	}
	
	/**
	 * Find maximum region size.
	 */
	private void findMaxRegionSize() {
		maxRegionSize = 0;
		for (int i = 0; i < geographicRegions.length; i++) {
			if (maxRegionSize < geographicRegions[i].size()) {
				maxRegionSize = geographicRegions[i].size();
			}
		}
	}
	
	/**
	 * Read node info from the country file:
	 */
	private ArrayList<Node> readNodeInfo(String filename) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		File file = new File(filename);	       
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = input.readLine()) != null) {        
				Node node = new Node(dim);       	
				String[] strs = line.split(";");
				node.globalId = Integer.parseInt(strs[0].trim());
				node.name = strs[1].trim();
				int temp = Integer.parseInt(strs[3].trim());
				if (temp < Region.values().length && temp >= 0) {
					node.region = Param.Region.values()[temp];
				} else {
					node.region = null;
				}
				nodes.add(node);
			}
			input.close();          
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return nodes;        
	}

	/**
	 * Read coordinate info from a file
	 * @param nodes
	 * @param filename
	 */
	private void readCoords(ArrayList<Node> nodes, String filename){
		File file = new File(filename);
		ArrayList<Integer> prunedList = new ArrayList<Integer>();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(file));        	
			String line;
			int numOfNodes[] = new int[Config.NUM_OF_REGIONS];
			for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
				numOfNodes[i] = 0;
			}
			int other = 0;
			int prev = -1;
			while ((line = input.readLine()) != null) {
				String[] strs = line.split(" ");
				int index = Integer.parseInt(strs[0].trim());
				for (int i = prev + 1; i < index; i++) {
					prunedList.add(i);
				}
				Node node = nodes.get(index);
				for (int i = 0; i < dim; i++) {
					node.coords[i] = Double.parseDouble(strs[i+1].trim());
				}       			
				if (node.region == null) {
					other++;
					prunedList.add(index);
				} else {
					geographicRegions[node.region.ordinal()].add(node);
					numOfNodes[node.region.ordinal()]++;
				}       			
			}
			input.close();
			for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
				System.out.println("Num of nodes for " + 
						Param.Region.values()[i] + ": " + numOfNodes[i]);
			}        	
			System.out.println("Num of other nodes: " + other);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Remove nodes from unidentified regions.
		 */
		for (int i = prunedList.size() - 1; i>=0; i--) {
			nodes.remove(prunedList.get(i));
		}

		/*
		 * Reset node id.
		 */
		for (int i = 0; i < nodes.size();i++) {
			nodes.get(i).globalId = i;
		}        

		/*
		 * Reset local id
		 */
		for (int i = 0; i < geographicRegions.length; i++) {
			for (int j = 0; j < geographicRegions[i].size(); j++) {
				geographicRegions[i].get(j).localId = j;
			}
		}        
	}
}
