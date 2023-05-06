package dataStructures;

import reconstruction.Param;

/**
 * A node is a network location in the network space. It contains info such as
 * coordinates, geographic region, etc.
 * @author Albert
 *
 */
public class Node {
	/**
	 * Geographic region
	 */
	public Param.Region region;

	/**
	 * Every node has a unique id.
	 */
	public int globalId; 
	
	/**
	 * Every node has a local id.  No two nodes from the same geographic region
	 * has the same local id.
	 */
	public int localId;
	
	/**
	 * Coordinates of the node in the network space
	 */
	public double[] coords;
	
	/**
	 * Name of the node, ex: IP address
	 */
	public String name;	
	
	/**
	 * Constructor
	 * @param numOfDim
	 */
	public Node(int numOfDim) {
		coords = new double[numOfDim];
		for (int i = 0; i < coords.length; i++) {
			coords[i] = 0;
		}
	}	
	    
	public Object clone() {
		Node node = new Node(coords.length);
		node.globalId = globalId;
		node.region = region;
		node.localId = localId;
		node.name = name;      		 
		for (int j = 0; j < coords.length; j++) {
			node.coords[j] = coords[j]; 
		}      		            
		return node;
	}
}