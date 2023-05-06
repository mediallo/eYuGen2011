package benchmark;
import java.util.ArrayList;


/**
 * Class Broker : a broker is an node to be generated in the network space
 * @author Mohamed Barkallah
 *
 * 
 * 
 */

public class Broker {

     public int id;
     public String region;
     public double  coord_X ;
     public double  coord_Y ;
     private ArrayList<Double> idOfCellsMatched;// the ID of events matched by the subscription
     private int SubsCount;

 /*
 * Constructor Broker() : a broker is an node to be generated in the network space
 * @param id :  the broker's id
 * @param region : the region of the broker
 * @param coord_X :  X coordinates in the network space
 * @param coord_Y :  Y coordinates in the network space
 * @param idOfCellsMatched : the list of id of the served cells
 */
     public Broker(){
        id = 0;
        region = "" ;
        coord_X = 0 ;
        coord_Y = 0 ;
        idOfCellsMatched = new ArrayList<Double>();
        SubsCount = 0;
    }

     
     public int getCellsMatchedSize (){
        return this.idOfCellsMatched.size();
     }
     
     public ArrayList<Double> getListOfCellsMatched (){
        return this.idOfCellsMatched;
     }

      public int getSubsCount (){
        return this.SubsCount;
     }


     public void setIdOfCellsMatched (double idOfCell){
       boolean found = false;
       for(int i=0;i<idOfCellsMatched.size();i++){
         if (idOfCellsMatched.get(i).equals(idOfCell)){
           found = true;
           break;
         }
       }
       if (!found) this.idOfCellsMatched.add(idOfCell);
     }

     public void setSubsCount (int count){
        this.SubsCount = count;
     }
}
