package reconstruction;


import benchmark.*;
import java.io.IOException;
import java.util.ArrayList;
import dataStructures.Interest;
import dataStructures.Node;
import dataStructures.Subscription;

/**
 * 
 * @author Albert
 *
 */
public class DataGenerator {
	/**
	 * Generate data
	 */
	public void execute() {
		System.out.println("\nYuGen Start");
          Benchmark.outPutMessages = "\nYuGen Start "+System.currentTimeMillis()+ "------------------------\n";
          Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
		
		System.out.println("Extracting category's informations");
          Benchmark.outPutMessages = "Extracting category's informations\n";
          Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

		String[] hierarchyFiles= Config.TOPIC_FILES;
		CategoryHierarchy[] categoryHierarchies = new CategoryHierarchy[Config.EVENT_DIM];
		for (int i = 0; i < Config.EVENT_DIM; i++) {
			categoryHierarchies[i] = new CategoryHierarchy(hierarchyFiles[i]);
			categoryHierarchies[i].createCategoryTree(i);
		}

		
		System.out.println("Generating event's distribution...");
          Benchmark.outPutMessages = "Generating event's distribution\n";
          Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

          // Event space will be partitioned into N x N x ... x N grid cells.
		int[] lengths = new int[Config.EVENT_DIM];
		for (int i = 0; i < Config.EVENT_DIM; i++) {
			lengths[i] = Config.PARTITION_N;
		}

		EventDistribution eventDistribution = new EventDistribution(categoryHierarchies, lengths, Config.EVENT_SPACE_MIN, Config.EVENT_SPACE_MAX, Config.EVENT_FILE);
          eventDistribution.execute(Config.BANDWIDTH);
          eventDistribution.print(Config.EVENT_DISTRI_FILE, Config.REPORT_All);
		
	System.out.println("Extracting interest's info...");
     Benchmark.outPutMessages = "Extracting interest's informations...\n";
     Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

	InterestGenerator interestGenerator = new InterestGenerator(Config.EVENT_DIM, categoryHierarchies, Config.INTEREST_FILE);
	ArrayList<Interest> interests = interestGenerator.extractInterests();
    
	System.out.println("Extracting node's info...");
     Benchmark.outPutMessages = "Extracting node's informations\n";
     Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

	NodeLoader nodeLoader = new NodeLoader(Config.NETWORK_DIM, Config.IP_COUNTRY_FILE, Config.IP_COORD_FILE);
	nodeLoader.execute();    	
		
      /*
      * When subscribers are generated, we will keep track of how many times
      * each node is chosen to be the (approximate) coordinate of subscribers
      * in the network space.
      */
          int[][]frequencies = new int[Config.NUM_OF_REGIONS][nodeLoader.getMaxNumOfNodesFromARegion()];
          for (int i = 0; i < Config.NUM_OF_REGIONS; i++) {
               for (int j = 0; j < nodeLoader.getMaxNumOfNodesFromARegion(); j++) {
                              (frequencies[i])[j] = 0;
               }
          }
		ArrayList<Node> geographicRegions[] = nodeLoader.getRegions();

		System.out.println("Done\nGenerating subscribers...");
          Benchmark.outPutMessages = "Done\nGenerating subscribers\n";
          Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

		SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(interests, Config.NETWORK_DIM, Config.EVENT_DIM, geographicRegions);
		Subscription[] subs = subscriptionGenerator.execute(Config.NUM_OF_SUBS, frequencies);    	
		subscriptionGenerator.print(subs, Config.SUB_FILE);
                    
		System.out.println("Done");
          Benchmark.outPutMessages = "Done\n";
          Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
		
		if (Config.generateBrokerNetwork) {
			System.out.println("Generating brokers");
               Benchmark.outPutMessages = "Generating brokers\n";
               Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

			geographicRegions = nodeLoader.getRegions();
			BrokerNetworkGenerator brokerNetworkGenerator = new BrokerNetworkGenerator(Config.NETWORK_DIM, geographicRegions);
               brokerNetworkGenerator.generateBrokers(Config.NUM_OF_BROKERS, frequencies);
			brokerNetworkGenerator.writeToFile(Config.BROKER_FILE);

			System.out.println("Done");
               Benchmark.outPutMessages = "Done\n";
               Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
		}

		System.out.println("YuGen End");
          Benchmark.outPutMessages = "YuGen End ..........";
          Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
          Benchmark.yuGenIsDone = true;
	}

	/**
	 *
	 * @param args
	 * @throws IOException
	 */

     /*public static void main(String[] args){// throws IOException{
		DataGenerator dataGenerator = new DataGenerator();
		dataGenerator.execute();
	}*/
}