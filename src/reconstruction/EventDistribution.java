package reconstruction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import dataStructures.Grid;
import dataStructures.Range;


/**
 * Estimate the event distribution. We partition the event space into grid
 * cells and report the probability that an event will be published in each
 * cell.  The summation over the probabilities of all cells is 1.
 * @author Albert
 *
 */
public class EventDistribution extends Grid {
	/**
	 * An array of category hierarchies, one per event attribute.
	 * A category can be accessed by searching from the root or by specifying
	 * the name of the category.
	 */
	CategoryHierarchy[] categoryHierarchies;
	
	/**
	 * The i^th element is the width of a cell along the i^th event attribute
	 */
	double[] cellWidths;
	
	/**
	 * Volume of a cell
	 */
	double cellVol;
	
	/**
	 * Event dimension
	 */
	int dim;
	
	/**
	 * Minimum value of an event attribute
	 */
	double eventSpaceMin;
	
	/**
	 * Maximum value of an event attribute
	 */
	double eventSpaceMax;
	
	/**
	 * Map base interests to their message count.
	 */
	Hashtable<String, Double> eventTable;
	
	/**
	 * The name of the input file that stores the message info 
	 */
	String filename;
	
	/**
	 * Constructor
	 * @param categoryHierarchies
	 * @param lengths
	 * @param eventSpaceMin
	 * @param eventSpaceMax
	 * @param filename
	 */
	public EventDistribution(CategoryHierarchy[] categoryHierarchies, int[] lengths, double eventSpaceMin, double eventSpaceMax,
			String filename) {
		super(lengths);		
		this.categoryHierarchies = categoryHierarchies;
		this.dim = categoryHierarchies.length;
		this.eventSpaceMin = eventSpaceMin;
		this.eventSpaceMax = eventSpaceMax;
		this.filename = filename;
		cellVol = 1;
		cellWidths = new double[lengths.length];
		for (int i = 0; i < lengths.length; i++) {
			cellWidths[i] = (eventSpaceMax - eventSpaceMin)/lengths[i];
			cellVol *= cellWidths[i];
		}
                
	}
	
	/**
	 * Compute the event distribution.
	 * @param bandwidth
	 */
	void execute(int bandwidth) {
		readEventFile(filename);
		System.out.println("Estimate message count in the uncovered space");
		estimateUncoveredSpace(eventTable, bandwidth);
		
		for (int i = 1; i <= Config.EXTRA_ITER_KERNEL; i++) {
			System.out.println("Decrease skewness, iteration: " + i);
			smoothEventDistri(bandwidth, false);
		}
	}
	
	/**
	 * Write the event distribution into a file
	 * @param filename
	 */
	void print(String filename, boolean reportAll) {

		try{    			
		FileWriter fstream = new FileWriter(filename);
		BufferedWriter  out = new BufferedWriter(fstream);
		  out.write(cells.length+"; " + dim + "\n");
		    double total = 0;
		    int countGr0 = 0;
		    int countEq0 = 0;
		    
		    // find total number of events in the system.
		    for (int i = 0; i < cells.length; i++) {
		    	total += cells[i].val;
		    	if (cells[i].val > 0) {
		    		countGr0++;
		    	} else {
		    		countEq0++;
		    	} 
		    }
		    System.out.println("Number of cells with probability > 0: " +
		    		countGr0);
		    System.out.println("Number of cells with probability = 0: " +
		    		countEq0);
		    
		    /*
		     *  write the probability of each cell into the file if its
		     *  probability > 0, i.e., the number of events within that
		     *  cell is greater than 0.
		     */		    
		for (int i = 0; i < cells.length; i++) {
                    if (reportAll || cells[i].val > 0) {
			int[] indices = convertCellNumToIndices(i);
			for (int j = 0; j < dim; j++) {
			double min = (indices[j] + 0) * cellWidths[j] +eventSpaceMin;
			double max = (indices[j] + 1) * cellWidths[j] +eventSpaceMin;
			out.write(min + ", " + max + ";");
			}						    		    
			out.write((cells[i].val/total)+"\n"); // give a probability for each cell
                    }
		}
		    out.close();
		}	catch (IOException ex) {
			System.err.println(ex); 
		}
	}
	
	/**
	 * Estimate the message count in the regions that are uncovered by any
	 * base interests.
	 * @param eventTable
	 * @param bandwidth
	 */
	private void estimateUncoveredSpace(Hashtable<String, Double> eventTable, int bandwidth) {
		// Read the existing event info and update the cells
		Enumeration e = eventTable.keys();
		while(e.hasMoreElements()) {
		    	String key = (String)e.nextElement();
		    	double num = eventTable.get(key);
		    	String strs[] = key.split(";");
               Range<Double>[] ranges = new Range[dim];
		    	boolean isBaseInterest = true;

		    	for (int i = 0; i < dim; i++) {
		    		ranges[i] = categoryHierarchies[i].getRange(strs[i]);
		    		if (ranges[i] == null) {
		    			isBaseInterest = false;
		    		} 
		    	}
		    	if (isBaseInterest) updateCells(ranges, num);		    	
		}		
		/*
		 *  Estimate the message count in the uncovered regions by using
		 *  a kernel smoother.    
		 */		
		 smoothEventDistri(bandwidth, true);		
	}

	/**
	 * Update the information of all the cells that intersect with the
	 * specified interest.
      *   First, the number of messages for that interest
	 * will be proportionally distributed to the cells that intersect with
	 * the interest.
      *   Second, the info about the covered parts of those cells
	 * need to be updated.  
	 * @param rangesOfInterest
	 * @param numOfMessages
	 */
	private void updateCells(Range<Double>[]  rangesOfInterest,	double numOfMessages) {


           /*
		 *  Find all the cells overlapped by the interest. Recall that an
		 *  interest is a rectangle, so those cells can be specified as a
		 *  rectangle.
		 */
		Range<Integer>[] indexRanges = new Range[rangesOfInterest.length];
		for (int i =0;i < rangesOfInterest.length; i++) {
			double min = rangesOfInterest[i].min;
			double max = rangesOfInterest[i].max;
               //System.out.println(min +" "+ max);
			/*
			 *  Range.min <- minimum index number
			 *  Range.max <- maximum index number 
			 *  Note: every cell has width = cellWidths[i] for the i^th dimension.
			 */
			Range<Integer> range = new Range<Integer>();
			range.min = (int)((min - eventSpaceMin) / cellWidths[i]);
			range.max = (int)((max - eventSpaceMin) / cellWidths[i]);
              
			indexRanges[i] = range;
		}		
		/*
		 * Find the volume of the interest
		 */
		double volOfInterest = 1; 
		for (int i = 0; i < rangesOfInterest.length; i++) {
			volOfInterest *= (rangesOfInterest[i].max - rangesOfInterest[i].min);
		}		
		/*
		 * Let m be the number of messages per month published for the
		 * interest. For each cell C that is overlapped with the interest I,
		 * 1) find the fraction f of interest I that intersects with cell C,
		 *    i.e., f = vol(Intersection(C and I)) / vol(I)    
		 * 2) find the fraction f' of cell C that intersects with interest I,
		 *    i.e., f' = vol(Intersection(C and I)) / vol(C)
		 * 3) for cell C, increase the number of events and the fraction of
		 *    covered region by f * m and f', respectively.  
		 */
		ArrayList<Integer> cellNumbers = getCellNumbers(indexRanges);

		for (int i = 0; i < cellNumbers.size(); i++) {
			int[] indices = convertCellNumToIndices(cellNumbers.get(i));
              
			double volOfIntersect = 1;
			for (int j = 0; j < dim; j++) {
                 
				double min = indices[j] * cellWidths[j] + eventSpaceMin;
                    double max = (indices[j]+1) * cellWidths[j] + eventSpaceMin;
                    
                   
                    
				if (min < rangesOfInterest[j].min) {
                      min = rangesOfInterest[j].min;
				}
                    
				if (max > rangesOfInterest[j].max) {
                     // System.out.println("updateCells : min= "+max +">"+rangesOfInterest[j].max);
					max = rangesOfInterest[j].max;
				}
                    if (min == max){ min -= 0.0000001;}
                     // System.out.println("updateCells : min= "+min +">"+rangesOfInterest[j].min);
                     //System.out.println(indices[j]+" "+min +" "+ max);
				if (min >= max) {
                      System.out.println("updateCells : dim(j)="+j+" "+min +" >= "+ max);
				  throw new RuntimeException("This cannot happen as the cell is overlapped with the interest: try to \n");
				} else {
					volOfIntersect *= (max - min);
				}
			}

			double frac = volOfIntersect / volOfInterest;
               
			cells[cellNumbers.get(i)].val += frac * numOfMessages;
			cells[cellNumbers.get(i)].fracCovered += volOfIntersect / cellVol;									
		}
	}

	/**
	 * Perform kernel smoothing. If estimateGap = false, perform smoothing
	 * on every cell. If estimateGap = true, we will only perform smoothing
	 * for every cell C which frac = f < 1, i.e., (1-f) portion of the cell
	 * is not covered.  We estimate the number of events in that portion
	 * using the density in its neighborhood.		 
	 * @param bandwidth
	 * @param estimateGap
	 */
	private void smoothEventDistri(int bandwidth, boolean estimateGap) {
		for (int i = 0; i < cells.length; i++) {			
			
			if (!estimateGap || cells[i].fracCovered < 1) {
				if (Config.KERNEL_FUNC == Param.KernelFunc.gaussian) {
					runKernelSmoothing(i, bandwidth, estimateGap);
				} else {
					/*
					 * Need not to retrieve info from every cell.
					 * Limit to the cells in the neighborhood (maximum norm
					 * dist <= bandwidth).  The neighborhood is specified
					 * as a hyper-rectangle.
					 */
					int[] indices = convertCellNumToIndices(i);					
					Range<Integer>[] ranges = new Range[dim];
					for (int j = 0; j < dim; j++) {
						Range<Integer> range = new Range<Integer>();
						range.min = indices[j] - bandwidth;
						range.max = indices[j] + bandwidth;
						if (range.min < 0) {
							range.min = 0;						
						}
						if (range.max > lengths[j] - 1) {
							range.max = lengths[j] - 1;
						}						
						ranges[j] = range;
					}
					runKernelSmoothing(getCellNumbers(ranges), i, bandwidth, estimateGap);
				}
			}
			if ((i+1) % (cells.length * 0.1) == 0) {
				System.out.println((int)((i+1) / (cells.length * 0.1)) +"0% complete");
			}
		}		
		/*
		 * Update the cells which information is not up-to-date
		 */
		for (int i = 0; i < cells.length; i++) {
			if (!cells[i].isUpdated) {
				cells[i].val = cells[i].updatedVal;
				cells[i].updatedVal = 0;
				cells[i].fracCovered = 1;
				cells[i].isUpdated = true;
			}
		}
	}
	
	/**
	 * Run kernel smoother to estimate the regions that are uncovered by the
	 * base interests in the input file.
	 * @param cellNum
	 * @param bandwidth
	 */
	private void runKernelSmoothing(int cellNum, int bandwidth, boolean estimateGap) {
		double density = 0;
		double totalWeight = 0;
		int[] currentCellIndices = convertCellNumToIndices(cellNum);

		for (int i = 0; i < cells.length; i++) {		
			// Skip if the cell is empty (no message count)
			if (cells[i].fracCovered != 0) {
				/*
				 * K_lambda(x_0, x) = KernelFunc(||x - x_0||/lambda)
				 */
				int[] indices = convertCellNumToIndices(i);
				double dist = getDistance(indices, currentCellIndices);
				double weight = runKernelFunc(dist/bandwidth);
				/*
				 * The density of a cell is equal to the number of messages
				 * in the cell divided by size of the covered region in the
				 * cell 
				 */
				density += weight * (cells[i].val / (cells[i].fracCovered * cellVol));
				totalWeight += weight;
			}
		}

		if (totalWeight > 0) {			
			density /= totalWeight;
			cells[cellNum].isUpdated = false;
			if (estimateGap) {
			/*
			 * The total number of messages in the cell is equal to the
			 * number of messages in the original and new covered regions
			 * in the cell.  The message count for the new covered region
			 * is the same as density of the new region times volume of the
			 * new region. 
			 */
				cells[cellNum].updatedVal = density * cellVol * (1 - cells[cellNum].fracCovered) + cells[cellNum].val;
			} else {
				cells[cellNum].updatedVal = density * cellVol;						
			}
		}
	}
	
	/**
	 * Run kernel smoother to estimate the regions that are uncovered by the
	 * base interests in the input file.
	 * @param neighborhood
	 * @param cellNum
	 * @param bandwidth
	 */
	private void runKernelSmoothing(ArrayList<Integer> neighborhood, int cellNum, int bandwidth, boolean estimateGap) {
		double density = 0;
		double totalWeight = 0;
		int[] currentCellIndices = convertCellNumToIndices(cellNum);
           //System.out.println(" neighborhood.size() = "+neighborhood.size());
		for (int i = 0; i < neighborhood.size(); i++) {
			// Skip if the cell is empty (no message count)
			if (cells[neighborhood.get(i)].fracCovered != 0) {
				/*
				 * K_lambda(x_0, x) = KernelFunc(||x - x_0||/lambda)
				 */	
				int[] indices = convertCellNumToIndices(neighborhood.get(i));				
				double dist = getDistance(indices, currentCellIndices);
				double weight = runKernelFunc(dist/bandwidth);
                   // System.out.println(weight+" = runKernelSmoothing("+dist+"/"+bandwidth+") ");
				/*
				 * The density of a cell is equal to the number of messages
				 * in the cell divided by size of the covered region in the
				 * cell 
				 */
				density += weight * (cells[neighborhood.get(i)].val / (cells[neighborhood.get(i)].fracCovered * cellVol));
                    //System.out.println("density = "+ density);
				totalWeight += weight;		
			}
		}
		if (totalWeight > 0) {			
			density /= totalWeight;
			cells[cellNum].isUpdated = false;
			if (estimateGap) {
			/*
			 * The total number of messages in the cell is equal to the
			 * number of messages in the original and new covered regions
			 * in the cell.  The message count for the new covered region
			 * is the same as density of the new region times volume of the
			 * new region. 
			 */
				cells[cellNum].updatedVal = density * cellVol * (1 - cells[cellNum].fracCovered) + cells[cellNum].val;
			} else {
				cells[cellNum].updatedVal = density * cellVol;						
			}

		}
          //System.out.println("cells["+cellNum+"].updatedVal = "+ cells[cellNum].updatedVal);
	}
		
	/**
	 * Compute the output value of a user-specified kernel function given the input u.
	 * @param u
	 * @return
	 */
	private double runKernelFunc(double u) {
       
		double d = 0;
		if (Config.KERNEL_FUNC == Param.KernelFunc.epanechnikov) {
			if (u <= 1) {
				d = 3./4 * (1-u*u);
			}		
		} else if (Config.KERNEL_FUNC == Param.KernelFunc.gaussian) {
			d = 1/(2*Math.PI) * Math.exp(-0.5*u*u);					
		} else if (Config.KERNEL_FUNC == Param.KernelFunc.triangular) {
			if (u <= 1) {
				d = 1-u;
			}		
		} else if (Config.KERNEL_FUNC == Param.KernelFunc.tricube) {
			if (u <= 1) {
				d = 35./32 * Math.pow((1-u*u), 3);
			}		
		} else if (Config.KERNEL_FUNC == Param.KernelFunc.uniform) {
			if (u <= 1) {
				d = 1./2;
			}		
		} 
		return d;
	}
	
	/**
	 * Compute the distance between two indices.
	 * @param indices1
	 * @param indices2
	 * @return
	 */
	private double getDistance(int[] indices1, int[] indices2) {
		int dist = 0;
		for (int i = 0; i < indices1.length; i++) {
			dist += Math.pow((indices1[i] - indices2[i]),2);
		}
		return Math.sqrt(dist);
	}
	
	/**
	 * Read the input file about the message info.
	 * @param filename
	 */
	private void readEventFile(String filename) {
		eventTable = new Hashtable<String, Double>();
 		File file = new File(filename); 		
 		BufferedReader input = null;
 		try {
 			input = new BufferedReader(new FileReader(file));		
 			String line = null;
 			while ((line = input.readLine()) != null) {  		 				
 				String[] strs = line.split(";");
 				String key = "";
 				for (int i = 0; i < dim - 1; i++) {
 					key += CategoryHierarchy.trim(strs[i]);
 					key += ";";
 				}
 				key += CategoryHierarchy.trim(strs[dim -1]);
                    
 				double num = Double.parseDouble(strs[dim].trim()); // Volume de messages (nb de publications)
 				eventTable.put(key, num);
 			}
 			input.close();
 		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}	 
	}
}
