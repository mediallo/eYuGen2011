package benchmark;

/**
 *
 * @author Mohamed Barkallah
 */
public class Membership {
    public int idOfSub; // the Id of the sub
    public int idOfBroker; // the Id of the broker that serve the subscription
    public String regionOfBroker;
    public double[]  brokerCoord ;
    public double[]  subCoord ;
    public double  distance ;

    public Membership(){
        idOfSub = 0 ;
        idOfBroker = 0;
        distance = 0.0;
        brokerCoord = new double[2];
        subCoord = new double[2];
        regionOfBroker = "";
    }

}
