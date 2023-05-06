/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package benchmark;
import java.util.ArrayList;
/**
 *
 * @author Mohamed Barkallah
 */
public class Subscription {
    
    public int id;
    private int pointsMatched; // the number of publications matched by the subscription
    private ArrayList<Double> cellsMatched;// the ID of events matched by the subscription

    public String region;
    // coordinates in the network space N
    public double  N_Coord_X ;
    public double  N_Coord_Y ;
    // coordinates in the event space E
    public double  E_Coord_minX ;
    public double  E_Coord_minY ;
    public double  E_Coord_maxX ;
    public double  E_Coord_maxY ;

     public Subscription(){
        id = 0;
        pointsMatched = 0;
        cellsMatched = new ArrayList<Double>();
        
        region = "" ;

        N_Coord_X = 0 ;
        N_Coord_Y = 0 ;

        E_Coord_minX = 0;
        E_Coord_minY = 0;
        E_Coord_maxX = 0;
        E_Coord_maxY = 0;
    }

     public int getEventsMatched (){
        return this.pointsMatched ;
     }
     
     public void setEventsMatched (int p){
         this.pointsMatched = p;
     }

     
      public int getCellsMatchedSize (){
        return this.cellsMatched.size();
     }

     

     public ArrayList<Double> getCellsMatched (){
        return this.cellsMatched;
     }

     public void setCellsMatched (double idOfEvent){
       boolean found = false;
       for(int i=0;i<cellsMatched.size();i++){
         if (cellsMatched.get(i).equals(idOfEvent)){
           found = true;
           break;
         }
       }
       if (!found) this.cellsMatched.add(idOfEvent);
     }
    
}
