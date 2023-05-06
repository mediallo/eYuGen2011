package benchmark;
import reconstruction.Config;


import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * @author Mohamed Barkallah
 *
 * Class BenchmarkingFunctions : generate distributions in outputs fils
 * 
 * @param brokerFile :  the broker's output file
 * @param subFile :     the subscription's output file
 * @param brokerInitid :  id of a broker
 * @param subInitId :     id of a subscription
 * @param brokersList : the broker's list
 * @param subsList :    the subscription's list
 * @param membershipList : the membershipList, specify which broker serve the subscription
 */

public class BenchmarkingFunctions {

    private String brokerFile, subFile;
    private int brokerInitid, subInitId;
    private ArrayList<Broker> brokersList;
    private ArrayList<Subscription> subsList;
    private ArrayList<Membership> membershipList;
    

    public BenchmarkingFunctions(String brokersFile, String subsFile){
        this.brokerFile = brokersFile;
        this.subFile = subsFile;
        this.brokerInitid = 0; //initial id of the broker
        this.subInitId = 0; //initial id of the sub
        this.brokersList = new ArrayList<Broker>();
        this.subsList = new ArrayList<Subscription>();
        this.membershipList = new ArrayList<Membership>();
    }

  /**
   * Create the broker's list from the broker's input file
   * @return brokersList : the broker's list
   */
   public ArrayList<Broker> setBrokersList (){
     Benchmark.outPutMessages ="    {Set the broker's list : Begin}";
     Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
     File file = new File(brokerFile);
     BufferedReader input = null;
      try {
          input = new BufferedReader(new FileReader(file));
          String line = null;
          // Read First line
          if ((line = input.readLine()) != null){
              String[] strs = line.split("; ");
              Benchmark.outPutMessages = "     Number of Brokers  : "+strs[1]+" , Network Dim :"+strs[0]+"\n";
              Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
          }
          while ((line = input.readLine()) != null) {
              Broker broker = new Broker() ;

              String[] strs = line.split("; ");
              broker.id = (int)Double.parseDouble(strs[0]);
              broker.region = strs[1];

                  String[] strCoord= strs[2].split(", ");
              broker.coord_X = Double.parseDouble(strCoord[0]);
              broker.coord_Y = Double.parseDouble(strCoord[1]);

              brokersList.add(brokerInitid,broker);
              brokerInitid++;
          }
      }catch (FileNotFoundException e) {e.printStackTrace();}
       catch (IOException e) {e.printStackTrace();}

        Benchmark.outPutMessages ="    {Set the broker's list : Done}\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
      return brokersList;
   }

  /**
  * Create the subscription's list from the subscription's input file
  * @param pointList
  * @return brokersList : the subscription's list
  */
   public ArrayList<Subscription> setSubscriptionsList (ArrayList<Event> pointList ){
      Benchmark.outPutMessages ="    {Set Subscription's List : Begin}\n";
      Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

      Subscription subscription = null ;
      File file = new File(subFile);
      BufferedReader input = null;
      String[] strCoord_N = new String[2];
      String[] strCoord_E = new String[2];
      String var[] = new String[2];

        try {
            input = new BufferedReader(new FileReader(file));
            String line = null;

            // Read First line
            if ((line = input.readLine()) != null){
                String[] strs = line.split(";");
                Benchmark.outPutMessages ="     Total Subscriptions Number : "+strs[0]+
                        " Network Dimention : "+strs[1]+
                        " Event Dimention : "+strs[2]+"\n";
                Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
            }
            //read next 3 lines
            int l=0;
            while (((line = input.readLine()) != null) && (l<3)) {
                if (l==0){
                    subscription = new Subscription() ;
                    subscription.id = subInitId;
                    subscription.region = line.toString();
                    l++;
                }else if (l==1){
                    strCoord_N = line.split(" ");
                    subscription.N_Coord_X = Double.parseDouble(strCoord_N[0]);
                    subscription.N_Coord_Y = Double.parseDouble(strCoord_N[1]);
                    l++;
                    } else{
                    strCoord_E = line.split("; ");
                        var = strCoord_E[0].split(", ");
                    subscription.E_Coord_minX = Double.parseDouble(var[0]);
                    subscription.E_Coord_maxX = Double.parseDouble(var[1]);
                        var = strCoord_E[1].split(", ");
                    subscription.E_Coord_minY = Double.parseDouble(var[0]);
                    subscription.E_Coord_maxY = Double.parseDouble(var[1]);
                    subscription.setEventsMatched(getNumberPointsInArea(subscription.E_Coord_minX,
                                                                          subscription.E_Coord_minY,
                                                                          subscription.E_Coord_maxX,
                                                                          subscription.E_Coord_maxY,
                                                                          pointList ));
                    subsList.add(subInitId,subscription);
                    subInitId++;
                    l=0;
                    }
            }
            input.close();
        }catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        Benchmark.outPutMessages ="    {Set Subscription's List : Done}\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
        
        return subsList;
    }

   /** Create the membership's list from the subscription's list and the broker's list
   * @param subsList : the subscription's list
   * @param brokersList : the broker's list
   * @return membershipList : the membership's list
   */
   public ArrayList<Membership> setMembershipList (ArrayList<Subscription> subsList, ArrayList<Broker> brokersList){
        this.subsList = subsList;
        this.brokersList = brokersList;
        double minDistance = 0;
        int idBroker = 0;
        double[] pS = new double[2]; //Subs Coord
        double[] pB = new double[2]; //Broker Coord
        double[] brokerCoord = new double[2];
        String regionOfBroker = "NULL";
        Membership nearest;
       
        for(int s=0; s<subsList.size(); s++){
            pS[0] = subsList.get(s).N_Coord_X;
            pS[1] = subsList.get(s).N_Coord_Y;
            for(int b=0; b< brokersList.size(); b++){
                pB[0] = brokersList.get(b).coord_X;
                pB[1] = brokersList.get(b).coord_Y;
                if (( b==0 )||( getDistance(pS,pB) <= minDistance )){
                    minDistance = getDistance(pS,pB);
                    idBroker = brokersList.get(b).id;
                    regionOfBroker = brokersList.get(b).region;
                    brokerCoord[0] = pB[0];
                    brokerCoord[1] = pB[1];
                }
                
            }
            brokersList.get(idBroker).setSubsCount(brokersList.get(idBroker).getSubsCount()+1);
             nearest = new Membership();
             nearest.idOfSub = subsList.get(s).id;
             nearest.regionOfBroker = regionOfBroker;
             nearest.idOfBroker = idBroker;
             nearest.subCoord[0] = pS[0];
             nearest.subCoord[1] = pS[1];
             nearest.brokerCoord[0] = brokerCoord[0];
             nearest.brokerCoord[1] = brokerCoord[1];
             nearest.distance = minDistance;
             minDistance = 0;
             membershipList.add(s,nearest);
           
        }
     return membershipList;
    }

  /** Generate the matching distribution's distribution of events
   * @param eventsList : the event's list
   * @param subscriptionList : the cell's list
   * @return eventsList
   */
   public ArrayList<Event> eventsMatchingDistribution(ArrayList<Event> eventsList,
                                            ArrayList<Subscription> subscriptionList){
    int subsCounter = 0; // number of subscriptions matching a given event
    int eventMatched, eventsNoMatched;
    double[] SubRange = new double[4];
    double x , y, Counter;
    String subLine = "#EventId NumberOfSubscriptions PercentageOfSubscriptions\n";
    String MDLine = "";

    for (int e=0; e < eventsList.size(); e++){
        x = eventsList.get(e).getEventX();
        y = eventsList.get(e).getEventY();
        for (int s=0; s < subscriptionList.size(); s++){
            SubRange[0] = subscriptionList.get(s).E_Coord_minX;
            SubRange[1] = subscriptionList.get(s).E_Coord_minY;
            SubRange[2] = subscriptionList.get(s).E_Coord_maxX;
            SubRange[3] = subscriptionList.get(s).E_Coord_maxY;
            if ((x>=SubRange[0])&&(x<=SubRange[2])&&
                (y>=SubRange[1])&&(y<=SubRange[3])){
              subsCounter++;
             //System.out.println("Sub "+s+" "+SubRange[0]+" "+SubRange[1]+" "+SubRange[2]+" "+SubRange[3] );
             subscriptionList.get(s).setCellsMatched(eventsList.get(e).getIdOfCell()); //save the  id if of the event matched by the subscription in a table
            }
        }
        subLine = subLine + e +" "+ subsCounter+ " "+ (((double)subsCounter*100) / (double)Config.NUM_OF_SUBS) +" %\n";
        eventsList.get(e).setMatchedSubs(subsCounter);
        eventsList.get(e).setMatchedSubsPercentage( (double)(((double)subsCounter*100) / (double)Config.NUM_OF_SUBS) );
       

        subsCounter = 0;
    }

    Counter=0;
    eventsNoMatched = 0;
    
    int degree = 10/Benchmark.accuracy;
    for (int i=1; i<=(10*Benchmark.accuracy) ; i++){
        eventMatched = 0;
        
        for (int e=0; e < eventsList.size(); e++){
            if ((((i-1)*degree)<eventsList.get(e).getMatchedSubsPercentage()) &&
                    (eventsList.get(e).getMatchedSubsPercentage()<=(i*degree))){
                    eventMatched++;// event's number that the % of subs fit the given % intervall
            }
            if(eventsList.get(e).getMatchedSubsPercentage() == 0.0 ){
              eventsNoMatched++; // event's number that don't match any subscriptions
            } 
        }
        if (i!=1) Counter = Counter + eventMatched ;  // cumulate the matched event's number
        else Counter = Counter + eventMatched + eventsNoMatched; // cumulate with the non matched event's

        MDLine = MDLine +
                (i*degree) +" %Subs "+
                eventMatched+" Events "+
                (double)(((double)Counter*100)/(double)Benchmark.TOTAL_EVENTS)+" %Events\n";
      
    }
    MDLine = "0 %Subs "+
            (eventsNoMatched/(10*Benchmark.accuracy)) +" NoEvents "+
            (double)(((double)eventsNoMatched*degree)/(double)Benchmark.TOTAL_EVENTS)+ " %Events\n"+
            MDLine;

    ligneToFilePlot(Benchmark.EVENTS_MATCHED_SUBS_STATISTIC_FILE,subLine);
    ligneToFilePlot(Benchmark.EVENTS_MATCHING_DISTRIBUTION_FILE,MDLine);

    return eventsList;
   }

   /** Generate the density of recipient's distribution of events
   * @param eventsList : the event's list
   * @param subsList : the subscription's list
   * @param brokersList : the broker's list
   * @return eventsList
   */
   public ArrayList<Event> eventsDensityOfRecipient(ArrayList<Event> eventsList,
                                          ArrayList<Subscription> subsList,
                                          ArrayList<Broker> brokersList ){

        int brokerCounter = 0;
        int pointsCounter=0;
        int eventsMatched, eventsNoMatched;
        double[] pS = new double[4];
        double x, y;
        String brokerLine = "#EventId NumberOfBrokers PercentageOfBrokers\n";
        String DRLine = "";


        for (int e=0; e < eventsList.size(); e++){
            x = eventsList.get(e).getEventX();
            y = eventsList.get(e).getEventY();

            for (int s=0; s < subsList.size(); s++){
                pS[0] = subsList.get(s).E_Coord_minX;
                pS[1] = subsList.get(s).E_Coord_minY;
                pS[2] = subsList.get(s).E_Coord_maxX;
                pS[3] = subsList.get(s).E_Coord_maxY;
                if ((x>=pS[0])&&(x<=pS[2])&&(y>=pS[1])&&(y<=pS[3])){
                  if (membershipList.get(s).idOfSub == subsList.get(s).id){
                      brokersList.get(membershipList.get(s).idOfBroker).setIdOfCellsMatched(eventsList.get(e).getIdOfCell());
                      eventsList.get(e).setBrokersList(membershipList.get(s).idOfBroker);
                      brokerCounter = eventsList.get(e).getBrokersListSize();
                      eventsList.get(e).setEventRegions(subsList.get(s).region);
                  }
               }
            }
            brokerLine = brokerLine + e +" "+ brokerCounter+" "+(((double)brokerCounter*100)/(double) brokersList.size())+" %\n";

            eventsList.get(e).setMatchedBrokers(brokerCounter);
            eventsList.get(e).setMatchedBrokersPercentage(((double)brokerCounter*100)/(double) brokersList.size());
            brokerCounter = 0;
        }

        eventsNoMatched = 0;
        int degree = 10/Benchmark.accuracy;
        for (int i=1; i<=(10*Benchmark.accuracy)  ; i++){
            eventsMatched = 0;
            for (int e=0; e < eventsList.size(); e++){
                if ((((i-1)*degree)< eventsList.get(e).getMatchedBrokersPercentage()) &&
                        (eventsList.get(e).getMatchedBrokersPercentage()<=(i*degree))){
                        eventsMatched++;
                }else if (eventsList.get(e).getMatchedBrokersPercentage()== 0.0 ) eventsNoMatched++; // event's number that don't match subs
            }
            if (i!=1) pointsCounter = pointsCounter + eventsMatched ;  // cumulate the matched event's number
            else pointsCounter = pointsCounter + eventsMatched + eventsNoMatched;
            DRLine = DRLine +(i*degree)+" %Brokers "+
                    eventsMatched+" Events "+
                    (double)(((double)pointsCounter*100)/(double)Benchmark.TOTAL_EVENTS)+" %Events\n";
        }

        DRLine = "0 %Brokers "+
                 (eventsNoMatched/(10*Benchmark.accuracy)) +" NoEvents "+
                (double)(((double)eventsNoMatched*degree)/(double)Benchmark.TOTAL_EVENTS)+ " %Events\n"+
                DRLine;

        ligneToFilePlot(Benchmark.EVENTS_SERVED_BROKERS_STATISTIC_FILE,brokerLine);
        ligneToFilePlot(Benchmark.EVENTS_DENSITY_OF_RECIPIENT_FILE,DRLine);

        return eventsList;
   }

   /** Generate the matching distribution's distribution of cells
   * @param subsList : the subscription's list
   * @param  cellsNumber : the number of the grid's cells
   */
   public void cellsMatchingDistribution(ArrayList<Subscription> subsList,int cellsNumber){
       int subCounter=0;
       int subCounterNoMatch = 0;
       double percentage = 0;
       String ligne="";

       for (int i=1; i<=10 ; i++){
                subCounter=0;
                for (int s=0; s < subsList.size(); s++){
                    percentage = (double)(((double)subsList.get(s).getCellsMatchedSize()*100)/(double)cellsNumber);
                    if ((0<percentage) && (percentage<=(i*10))){
                        subCounter++;
                    }else if (0.0 == percentage) subCounterNoMatch++;
                }
                ligne = ligne + (i*10) +" %Cells matched by "+
                        (double)(((double)subCounter*100)/(double)subsList.size())+" %Subs\n";
       }
            ligne = "0 %Cells matched by 0 %Subs\n"+ligne;
            ligneToFilePlot(Benchmark.CELLS_MATCHING_DISTRIBUTION_FILE,ligne);
   }

   /** Generate the density Of recipient's distribution of cells
   * @param brokersList : the broker's list
   * @param  cellsNumber : the number of the grid's cells
   */
   public void cellsDensityOfRecipient(ArrayList<Broker> brokersList,int cellsNumber){
       int brokerCounter=0;
       int brokerCounterWMatch = 0;
       double percentage = 0;
       String ligne="";

       for (int i=1; i<=10 ; i++){
                brokerCounter=0;
                for (int s=0; s < brokersList.size(); s++){
                    percentage = (double)(((double)brokersList.get(s).getCellsMatchedSize()*100)/(double)cellsNumber);
                    if ((0<percentage) && (percentage<=(i*10))){
                        brokerCounter++;
                    }else if (0.0 == percentage) brokerCounterWMatch++;
                }
                ligne = ligne + (i*10) +" %Cells matched by "+
                        (double)(((double)brokerCounter*100)/(double)brokersList.size())+" %Brokers\n";
       }
            ligne = "0 %Cells matched by 0 %Brokers\n"+ligne;
            ligneToFilePlot(Benchmark.CELLS_DENSITY_OF_RECIPIENT_FILE,ligne);
   }

  /** Write the subscription's list and the broker'slist into tow files
   * @param subFileName : the subscription's file name
   * @param brokFileName : the broker's file name
   * @param eventsList : the event's list
   * @param cellsList : the cell's list
   */
   public void subsListAndBrokersListToFilePlot (String subFileName, String brokFileName,
                                                ArrayList<Event> eventsList,
                                                ArrayList<GridCell> cellsList  ){

      Benchmark.outPutMessages ="    {Subs list exporting to a text file : Begin }\n";
      Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
      double minX , minY , maxX, maxY , x, y ;
      int id;
      try{
          FileWriter fstream = new FileWriter(subFileName);
          BufferedWriter  out = new BufferedWriter(fstream);
          out.write("#SubId Region BrokerId(Owner) NumberOfEventsMatched minX maxX minY maxY (Subs'coordinates)\n");
          for (int i = 0; i < subsList.size(); i++) {
             id = subsList.get(i).id;
             minX = subsList.get(i).E_Coord_minX;
             minY = subsList.get(i).E_Coord_minY;
             maxX = subsList.get(i).E_Coord_maxX;
             maxY = subsList.get(i).E_Coord_maxY;
             String region = subsList.get(i).region;

            out.write(id+ " "+region+" "+membershipList.get(i).idOfBroker+" "+subsList.get(i).getEventsMatched()+" "+minX+" "+maxX+" "+minY+" "+maxY+"\n");
          }
          out.close();
      }catch (IOException ex) {System.err.println(ex);}
      Benchmark.outPutMessages ="    {Subs list exporting : Done.}\n";
      Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);


      Benchmark.outPutMessages ="    {Brokers list  exporting to  a text file : Begin }\n";
      Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

      try{
       FileWriter fstream = new FileWriter(brokFileName);
       BufferedWriter  out = new BufferedWriter(fstream);
         out.write("#BrokerId XCoord YCoord Region NumberOfSubscriptions\n");
       for (int i = 0; i < brokersList.size(); i++) {
               id = brokersList.get(i).id;
               x = brokersList.get(i).coord_X;
               y = brokersList.get(i).coord_Y;
               String r = brokersList.get(i).region;
              out.write(id+ " " + x +" "+ y+" "+r+" "+brokersList.get(i).getSubsCount()+" \n");
          }
          out.close();
          }catch (IOException ex) {System.err.println(ex);}
       Benchmark.outPutMessages ="    {Brokers list exporting : Done.}\n";
       Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
}

   /** Write a ligne in the file
   * @param fileName : the file's name
   * @param ligne : the line to be written
   */
   public void ligneToFilePlot(String fileName, String ligne){
  Benchmark.outPutMessages ="    {Exporting to file Begin }\n";
  Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
  try{
   FileWriter fstream = new FileWriter(fileName);
   BufferedWriter  out = new BufferedWriter(fstream);
      out.write(ligne);
      out.close();
      }catch (IOException ex) {System.err.println(ex);}
   Benchmark.outPutMessages ="    {Exporting to file Done.}\n";
   Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
  }

  /** Write the membership list into a file
   * @param membershipFileName : the membership's file name
   * @param membershipList : the membership's list
   */
   public void membershipListToFilePlot(String membershipFileName, ArrayList<Membership> membershipList){
   Benchmark.outPutMessages ="    {Membership List exporting to a text file : Begin }\n";
   Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
   try{
	FileWriter fstream = new FileWriter(membershipFileName);
	BufferedWriter  out = new BufferedWriter(fstream);
     out.write("#SubId SubXCoord SubYCoord BrokerId BrokerXCoord BrokerYCoord Region Distance\n");
	for (int i = 0; i < membershipList.size(); i++) {
            int idSub = membershipList.get(i).idOfSub;
            int idBroker = membershipList.get(i).idOfBroker;
            double[] B = membershipList.get(i).brokerCoord;
            double[] S = membershipList.get(i).subCoord;
            String region = membershipList.get(i).regionOfBroker;
            double distance = membershipList.get(i).distance;
            out.write(idSub+ " " +S[0]+ " " +S[1]+ " " + idBroker + " " +B[0]+ " " + B[1]+" "+region+ " "+distance+"\n");
        }
        out.close();
    }catch (IOException ex) {System.err.println(ex);}
    Benchmark.outPutMessages ="    {Membership List exporting : Done.}\n";
    Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
   }

  /** Calculate the distance between tow points in the network space
   * @param s : the subscription's coordinates
   * @param b : the broker's coordinates
   * @return distance
   */
   public double getDistance(double[] s, double[] b) {
        double distance = 0 ;
        for (int i = 0; i < s.length; i++) {
             distance += Math.pow((s[i] - b[i]), 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

   /** Calculate the number of points in a rectangular area
   * @param minX, minY, maxX, maxY : the range of the area
   * @param eList : list of points
   * @return count: the number of points
   */
   public int getNumberPointsInArea(double minX, double minY, double maxX, double maxY,
                                      ArrayList<Event> eList){
       int count = 0;
       double x;
       double y;
            for(int c=0; c< eList.size(); c++){
                x = eList.get(c).getEventX();
                y = eList.get(c).getEventY();
                if((x>=minX)&&(x<=maxX)&&(y>=minY)&&(y<=maxY))
                    count++;
            }
       return count;
    }

}