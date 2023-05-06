package benchmark;

/**
 * @author Mohamed Barkallah
 *
 * Class GridCell : it's cell in the event space E
 * @param id : the cell's id
 * @param minRange : the minimal boundary of the cell
 * @param maxRange : the maximal boundary of the cell
 * @param proba : the probability of receiving a event in the cell
 * @param eventsNumber : the number of event in the cell
 */
public class GridCell {
    public double id;
    public double [] minRange = new double [2];
    public double [] maxRange = new double [2];
    public double proba;
    public int eventsNumber;

    public GridCell(){
        id = -1;
        minRange[0] = 0 ;//minX
        minRange[1] = 0 ;//minY
        maxRange[0] = 0 ;//maxX
        maxRange[1] = 0 ;//maxY
        proba = -1 ;
        eventsNumber = 0;
    }
}
