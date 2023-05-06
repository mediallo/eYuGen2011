package benchmark;
import java.util.ArrayList;

/**
 * @author Mohamed Barkallah
 *
 * Class Event : a Event is an event to be generated in the event space
 * @param id :  the event's id
 * @param idOfCell : the id of the recipient cell
 * @param coord_X :  X coordinates in the event space
 * @param coord_Y :  Y coordinates in the event space
 * @param matchedSubs : the number of subscriptions that matches the event
 * @param matchedBrokers : the number of brokers that serves the event
 * @param mutchedSubsPercentage : the percentage of subscriptions that matches the event
 * @param mutchedBrokersPercentage : the percentage of brokers that serves the event
 * @param idOfBrokersList : the list of the broker's id that serve the event
 */

public class Event {
    
    private int id;
    private int idOfCell;
    private double coord_X;
    private double coord_Y;
    private String[] eRegions;
    private int matchedSubs;
    private int matchedBrokers;
    private double mutchedSubsPercentage;
    private double mutchedBrokersPercentage;
    private ArrayList<Integer> idOfBrokersList;// the ID of events matched by the subscription


    public Event (){
        this.coord_X = 0;
        this.coord_Y = 0;
        this.id = 0;
        this.idOfCell = -1;
        this.matchedSubs = 0;
        this.mutchedSubsPercentage = 0;
        this.matchedBrokers = 0;
        this.mutchedBrokersPercentage = 0;
        idOfBrokersList = new ArrayList<Integer>();
        this.eRegions = new String[reconstruction.Config.NUM_OF_REGIONS];
        for (int i=0; i<this.eRegions.length; i++)  this.eRegions[i]="";
    }

    public Event (double x, double y){
        this.coord_X = x;
        this.coord_Y = y;
    }

    public void setEventId(int id){
        this.id = id;
    }

    public void setCellId(int id){
        this.idOfCell = id;
    }

    public void setCoordinatesOfEvent (double x, double y){
        this.coord_X = x;
        this.coord_Y = y;
    }

    public void setMatchedSubs(int m){
        this.matchedSubs = m ;
    }

    public void setMatchedSubsPercentage(double m){
        this.mutchedSubsPercentage = m ;
    }

    public void setMatchedBrokers(int m){
        this.matchedBrokers = m;
    }

    public void setMatchedBrokersPercentage(double m){
        this.mutchedBrokersPercentage = m ;
    }

    public void setEventRegions(String region){
     
    boolean exist = false;
    for (int i=0; i<this.eRegions.length; i++){
       if(this.eRegions[i].length()!= 0){
         if (this.eRegions[i].equals(region)){
           exist = true;
           break;
         }else continue;
       }else{
         if (!exist) {
           this.eRegions[i] = region;
           break;
         }
       }
    }
    }
    
    public int getIdOfEvent (){
      return  this.id;
    }

    public int getIdOfCell (){
      return  this.idOfCell;
    }

    public double getEventX (){
      return  this.coord_X;
    }

    public double getEventY (){
      return  this.coord_Y;
    }

    public int getMatchedSubs(){
      return this.matchedSubs;
    }

    public double getMatchedSubsPercentage(){
      return this.mutchedSubsPercentage;
    }

    public double getMatchedBrokers(){
      return this.matchedBrokers;
    }

    public double getMatchedBrokersPercentage(){
      return this.mutchedBrokersPercentage;
    }

    public int getBrokersListSize (){
        return this.idOfBrokersList.size();
     }

     public ArrayList<Integer> getBrokersList (){
        return this.idOfBrokersList;
     }

     public String getEventRegions (){
       String list = "";
       for (int j=0; j < this.eRegions.length; j++){
         if (this.eRegions[j].length() != 0){
          list +=  this.eRegions[j];
          if (j < this.eRegions.length)  list += "-";
         }
       }
      if (list.length()>2) list = list.substring(0, list.length()-1);
      return list;
    }
     
     public void setBrokersList (int idOfBroker){
       boolean found = false;
       for(int i=0;i<idOfBrokersList.size();i++){
         if (idOfBrokersList.get(i).equals(idOfBroker)){
           found = true;
           break;
         }
       }
       if (!found) this.idOfBrokersList.add(idOfBroker);
     }
}
