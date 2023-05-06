package benchmark;
import java.util.ArrayList;

/**
 * @author Mohamed Barkallah
 *
 * Class Benchmark : the main class, execute the bechmark
 * @param INPUT_CELLS_FILE :
 * @param INPUT_SUBS_FILE :
 * @param INPUT_BROKERS_FILE :
 * @param OUTPUT_EVENTS_FILE :
 * @param OUTPUT_CELLS_FILE :
 * @param OUTPUT_SUBS_COORD_FILE :
 * @param OUTPUT_BROKERS_COORD_FILE :
 * @param OUTPUT_BROKERS_ASSIGNED_SUBS_FILE :
 * @param EVENTS_DENSITY_OF_RECIPIENT_FILE :
 * @param EVENTS_MATCHING_DISTRIBUTION_FILE :
 * @param CELLS_MATCHING_DISTRIBUTION_FILE :
 * @param CELLS_DENSITY_OF_RECIPIENT_FILE :
 * @param CELLS_DENSITY_OF_RECIPIENT_FILE :
 * @param EVENTS_MATCHED_SUBS_STATISTIC_FILE :
 * @param EVENTS_SERVED_BROKERS_STATISTIC_FILE :
 * @param TOTAL_EVENTS :
 *
 */

public class Benchmark {
    
    //INPUT FILES
    public static String INPUT_CELLS_FILE =               "outputs/eventDistri.txt";
    public static String INPUT_SUBS_FILE =                "outputs/subsDistri.txt";
    public static String INPUT_BROKERS_FILE =             "outputs/brokersDistri.txt";

    //OUTPUT FILES
    public static String OUTPUT_EVENTS_FILE =                       "outputs/eventsCoord.txt";
    public static String OUTPUT_CELLS_FILE =                        "outputs/cellsList.txt";
    public static String OUTPUT_SUBS_COORD_FILE =                   "outputs/subsCoord.txt";
    public static String OUTPUT_BROKERS_COORD_FILE =                "outputs/brokersCoord.txt";
    public static String OUTPUT_BROKERS_ASSIGNED_SUBS_FILE =        "outputs/assignedSubsToBrokers.txt";
    
    //OUTPUT DISTRIBUTIONS FILES
    public static String experienceName ="";

    public static String EVENTS_DENSITY_OF_RECIPIENT_FILE =     "outputs/benchmark/"+experienceName+"-EventsDR.txt";
    public static String EVENTS_MATCHING_DISTRIBUTION_FILE =    "outputs/benchmark/"+experienceName+"-EventsMD.txt";
    public static String CELLS_MATCHING_DISTRIBUTION_FILE =     "outputs/benchmark/"+experienceName+"-CellsMD.txt";
    public static String CELLS_DENSITY_OF_RECIPIENT_FILE =      "outputs/benchmark/"+experienceName+"-CellsDR.txt";
    public static String EVENTS_MATCHED_SUBS_STATISTIC_FILE =   "outputs/benchmark/"+experienceName+"-EventMatchedSubs.txt";
    public static String EVENTS_SERVED_BROKERS_STATISTIC_FILE =  "outputs/benchmark/"+experienceName+"-EventServedBrokers.txt";


    //User configuration
    //BENCHMARK INPUT PARAMETERS
    public static int    TOTAL_EVENTS = 100 ;
    public static int    accuracy = 1;
    
    //GRAPHICAL DISPLAY PARAMETERS
    public static int  eventScale =   1;
    public static int  networkScale = 1;
    public static boolean bechmarkIsDone = false;
    public static boolean yuGenIsDone = false;

    //VARIABLES
    public static ArrayList<GridCell>     gridCellList;
    public static ArrayList<Event>        eventsList;
    public static ArrayList<Event>        eventsListIsUpDate;
    public static ArrayList<Broker>       brokersList;
    public static ArrayList<Subscription> subsList;
    public static ArrayList<Membership>   membershipList;
    public static EventSpacePlotting      eventSpaceDisplay;
    public static NetworkSpacePlotting    networktSpaceDisplay;
    public static String                  outPutMessages = "";


    void execute(){
      int step =0;
        outPutMessages ="[Start Generation]\n";
        System.out.println("[Start Generation]");
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

        outPutMessages = (step++)+"- Extracting Cell's Informations:\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        EventsGenerator events = new EventsGenerator(INPUT_CELLS_FILE, TOTAL_EVENTS);
        gridCellList = events.getCellsList();

        outPutMessages = (step++)+"- Generating Events list:\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        eventsList = events.setEventsList(gridCellList);
        
        outPutMessages = (step++)+"- Extracting Subscriptions' Informations:\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        BenchmarkingFunctions extractor = new BenchmarkingFunctions(INPUT_BROKERS_FILE, INPUT_SUBS_FILE);
        subsList = extractor.setSubscriptionsList(eventsList);
        
        outPutMessages = (step++)+"- Extracting Brokers' Informations: \n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        brokersList = extractor.setBrokersList();
        
        outPutMessages = (step++)+"- Generate Brokers' Memberships:\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        membershipList = extractor.setMembershipList(subsList,brokersList);
        extractor.membershipListToFilePlot(OUTPUT_BROKERS_ASSIGNED_SUBS_FILE,membershipList);
        
        outPutMessages = (step++)+"- Compute Event's Matching Distribution ...\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        eventsListIsUpDate = extractor.eventsMatchingDistribution(eventsList, subsList);
        
        //outPutMessages = (step++)+"- Compute Cells's Matching Distribution ...\n";
        //Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        //extractor.cellsMatchingDistribution(subsList,gridCellList.size());

        outPutMessages = (step++)+"- Compute Event's Density of Recipient Distribution ...\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        eventsListIsUpDate = extractor.eventsDensityOfRecipient(eventsListIsUpDate, subsList, brokersList);

        //outPutMessages = (step++)+"- Compute Cell's Density of Recipient Distribution ...\n";
        //Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        //extractor.cellsDensityOfRecipient(brokersList, gridCellList.size());

        outPutMessages = (step++)+"- Export Events to a file ...\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        events.BenchplotEventsList(eventsListIsUpDate, OUTPUT_EVENTS_FILE);

        //outPutMessages = (step++)+"- Export Event Cells to a Gnuplot file ...\n";
        //Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        //events.BenchplotCellsList(gridCellList, OUTPUT_CELLS_FILE);
        
        outPutMessages = (step++)+"- Export Subscriptions and Brokers to a file ...\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        extractor.subsListAndBrokersListToFilePlot(OUTPUT_SUBS_COORD_FILE, OUTPUT_BROKERS_COORD_FILE, eventsListIsUpDate, gridCellList );
        
        outPutMessages = "[Generation Done]\n";
        System.out.println(System.currentTimeMillis()+"[Generation Done]");
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        bechmarkIsDone = true;
    }

}

