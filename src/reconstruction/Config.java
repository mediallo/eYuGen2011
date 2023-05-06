package reconstruction;

/**
 * User configuration
 * @author Albert
 *
 */
public class Config {
	/*
	 * Input files		
	 */
	public static String        EVENT_FILE = "testData/messageInfo.txt";
	public static final String  INTEREST_FILE = "testData/interests.txt";
	public static final String  TOPIC_FILES[] = new String[] { "testData/categories1.txt", "testData/categories2.txt" };
	public static final String  IP_COUNTRY_FILE = "testData/ip2country.txt";
	public static final String  IP_COORD_FILE = "testData/coordinates.txt";
	
	/*
	 * Basic settings
	 */
	public static final int     NUM_OF_REGIONS = 3;

	public static final int     EVENT_DIM = 2; // >=2
	public static final int     NETWORK_DIM = 2;
	public static double  EVENT_SPACE_MIN = -250;
	public static double  EVENT_SPACE_MAX = 250;

	public static final long    SUB_RANDOM_SEED = 4531; //System.currentTimeMillis();//
	public static final long    BROKER_RANDOM_SEED = 839;//System.currentTimeMillis();//
		
	/*
	 * Subscriptions
	 * A. SUB_FILE --- output file name
	 * B. SHIFT_RECT --- shift the boundaries of subscriptions in the event space
	 */
	public static String         SUB_FILE = "outputs/subsDistri.txt";
	public static int            NUM_OF_SUBS = 100;
	public static final boolean  SHIFT_RECT = false; // add an error margin for the subscription area
	
	/*
	 * Brokers
	 * A. generateBrokerNetwork is optional
	 * B. NUM_OF_BROKERS consists of a list of integers; The i^th integer denotes
	 *    the number of brokers in the i^th geographic regions.
	 */
	public static final boolean generateBrokerNetwork = true;
	public static String BROKER_FILE = "outputs/brokersDistri.txt";
	public static int NUM_OF_BROKERS[] = new int[]{3, 3, 3};
		
	/*
	 * Hot interest removal
	 */
	public static  boolean PRUNE_HOT_INTERESTS = true;
	public static  int NUM_OF_HOT_INTERESTS = 100;
	
	/*
	 * Interest diffusion
	 */
	public static  boolean INTEREST_DIFFUSION  = false;
	public static  double SMOOTHING_P = 0.1;
	
	/*
	 * Interest generalization
	 * Three modes: NONE, PROPORTIONAL, UNIFORM
	 * Proportional: Every interest propagates the same fraction of subscription
	 * count to its parents. 
	 * Uniform: Every child of an interest propagates the same amount of
	 * subscription count to the interest.
      *
      *
	 */
	public static Param.InterestGeneralizationMode GROUP_HIERARCHY_MODE = Param.InterestGeneralizationMode.None;
	public static double HIERARCHY_FRAC = 0.5; // taille des sub cells

	/*
	 * Subscriber count ratio across geographic regions:
	 * If the ratio has to be modified, the new ratio is proportional to the
	 * one specified in REGION_RATIO, in which the i^th number represents
	 * the probability of a sub locating in the i^th geographic region.	      
	 */
	public static Param.SubsRatioMode SUBS_RATIO_MODE = Param.SubsRatioMode.ModifyRatio;
                //Param.SubsRatioMode.ModifyRatio;
	public static double[] REGION_RATIO = new double[]{1/9., 3/9., 5/9.};
	
	/*
	 * Configuration for generating event distribution.
	 * 1. For event dimension = 2, PARTITION_N = 200 means the event space
	 * will be partitioned into 200 x 200 grid cells of equal size. We
	 * associate every cell with the probability that an event is generated
	 * in that cell. The total probability (summation over the probabilities
	 * of all cells) is 1. 
	 * 2. BANDWIDTH is the bandwidth of the user specified kernel.
	 * 3. If REPORT_ALL = false, only the cells that have probability > 0
	 * will be written to the output file.
	 * 4. Repeat additional EXTRA_ITER_KERNEL iterations to reduce data skewness  
	 */
	public static String EVENT_DISTRI_FILE = "outputs/eventDistri.txt";
     public static int BANDWIDTH = 100; //Controls how wide the probability mass is spread around the cells
	public static Param.KernelFunc KERNEL_FUNC = Param.KernelFunc.epanechnikov; //

	public static int PARTITION_N = 10; // PARTITION_N x PARTITION_N x .... = The cells numbers

	public static final boolean REPORT_All = true;
	public static final int EXTRA_ITER_KERNEL = 0;
}	
