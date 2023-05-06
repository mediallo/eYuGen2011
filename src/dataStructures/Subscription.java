package dataStructures;

import reconstruction.Param;

/**
 * @author Mohamed Barkallah
 *
 * A subscription contains the info including geographic region, coordinates in
 * the network space, and a hyper-rectangle in the event space.
 * 
 */
public class Subscription {
	/**
	 * Geographic region
	 */
	public Param.Region region;
	
	/**
	 * Coordinates in the network space
	 */
	private double coords[];
	
	/**
	 * Minimal point of the rectangle in the event space
	 */
    private double[] minimalPoint;

	/**
	 * Maximal point of the rectangle in the event space
	 */
    private double[] maximalPoint;
    
    /**
     * Constructor
     * @param eventDim
     * @param physicalDim
     */
	public Subscription(int eventDim, int physicalDim) {		
		coords = new double[physicalDim];
		minimalPoint = new double[eventDim];
		maximalPoint = new double[eventDim];
	}
	
	/**
	 * @return the coordinates of the subscription
	 */
	public double[] getCoords() {
		return coords.clone(); //Network coods
	}
	
	/**
	 * @return the minimal point of the subscription
	 */
	public double[] getMinimalPoint() {
       //System.out.println("minX ("+minimalPoint[0]+", minY "+minimalPoint[1]+")");
		return minimalPoint.clone();
	}    

	/**
	 * @return the maximal point of the subscription
	 */
	public double[] getMaximalPoint() {
       //System.out.println("maxX ("+maximalPoint[0]+", maxY "+maximalPoint[1]+")");
		return maximalPoint.clone();
	}

	/**
	 * Set the range of the subscription for a specified attribute
	 * @param attriNum
	 * @param min
	 * @param max
	 */
	public void setPoints(int attriNum, double min, double max) { //minValue
		minimalPoint[attriNum] = min;
		maximalPoint[attriNum] = max;
		if (min >= max) {
			throw new RuntimeException("setMaximalPoint: min >= max: " + min + ", " + max);
		}
	}

	/**
	 * Set the coordinate of the subscriber in the Network space
	 * @param coords
	 */
	public void setCoords(double[] coords) {
		for (int i = 0; i < coords.length; i++) {
			this.coords[i] = coords[i];
		}
	}
}

