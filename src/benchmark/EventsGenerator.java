package benchmark;

import dataStructures.Category;
import reconstruction.CategoryHierarchy;


import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Random;


/**
 * Class EventsGenerator : generate event's points
 * @author Mohamed Barkallah
 *
 * @param cellsFile : the event's input file
 * @param idOfCell : id of an event
 * @param maxEventsNumber : number of points to be generated in the event space
 * @param eventsList : the points list
 * @param catgDimX : the categorie hierarchy of the topic dimension
 * @param catgDimY : the categorie hierarchy of the language dimension
 */

public class EventsGenerator {
    
    private String cellsFile;
    private int idOfCell ;
    private int maxEventsNumber;
    private ArrayList<Event> eventsList;
    private ArrayList<Category> catgDimX = new ArrayList<Category>();
    private ArrayList<Category> catgDimY = new ArrayList<Category>();

  /**
   * EventsGenerator Constructor
   * @param cellsFile : the event file
   * @param eventsNumber: the number of points to be generated
   */
public EventsGenerator(String eventFile, int pointsNumber){
    this.cellsFile = eventFile;
    this.maxEventsNumber = pointsNumber;
    this.idOfCell = 0; //Initialise the Cell's id
    this.catgDimX = CategoryHierarchy.catgDim0;
    this.catgDimY = CategoryHierarchy.catgDim1;
}

  /**
   * Create the event cell's list from the events file.
   * @return cellsList : the list of events
   */
public ArrayList<GridCell> getCellsList() {
    File file = new File(cellsFile);
    BufferedReader input = null;
    ArrayList<GridCell> cellsList = new ArrayList<GridCell>();

    Benchmark.outPutMessages = "    {Get the List of Cells : Begin}\n";
    Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
    try {
            input = new BufferedReader(new FileReader(file));
            String line = null;
            
            if ((line = input.readLine()) != null){
                String[] strs = line.split(";");
            }
            
            while ((line = input.readLine()) != null) {
                GridCell cell = new GridCell() ;
                
                    String[] strs = line.split(";");
                cell.id = idOfCell;
                    String[] strmin = strs[0].split(",");
                cell.minRange[0]= Double.parseDouble(strmin[0]);
                cell.maxRange[0]= Double.parseDouble(strmin[1]);
                    String[] strmax = strs[1].split(",");
                cell.minRange[1]= Double.parseDouble(strmax[0]);
                cell.maxRange[1]= Double.parseDouble(strmax[1]);
                cell.proba = Double.parseDouble(strs[2]);
                
                cellsList.add(idOfCell,cell);
                idOfCell++;
            }
 	input.close();
        Benchmark.outPutMessages = "    {Get the List of Cells  : Done}\n";
        Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
 	}  
        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

    return cellsList;
}

  /**
   * Create the points list, assigne the number of points to be generatedfor
   * in each cell from the cell's probability.
   * @param inputCellsList: the event cells list
   * @return eventsList : return the list of the event points
   */
public ArrayList<Event> setEventsList(ArrayList<GridCell> inputCellsList){

    Benchmark.outPutMessages = "    {Create the  List of events' point : Begin}\n";
    Starting.outputMessage.setText(Starting.outputMessage.getText());

    eventsList = new ArrayList<Event>();
    ArrayList<GridCell> updateCellsList = new ArrayList<GridCell>();
    Event event ;
    int idEvent = 0;
    double X = 0.0;
    double Y = 0.0;
    boolean exist;
    // generate the list of the cells that contains all the event points
    updateCellsList = eventDistributionOverCellsProba(inputCellsList);
    
    /*Generate event's coordinates (x,y) that fits the
    * categorie's range (topic, language).
    *
    */
    for (int c=0 ; c<updateCellsList.size(); c++){
      int p=0;
      exist= false;
      while ( p < updateCellsList.get(c).eventsNumber){

            X = getRandomDouble(updateCellsList.get(c).minRange[0], updateCellsList.get(c).maxRange[0]);
            Y = getRandomDouble(updateCellsList.get(c).minRange[1], updateCellsList.get(c).maxRange[1]);
            
            /**Option: don't generate similar events
             *
             * for (int i=0; i < eventsList.size(); i++ ){
                if( (eventsList.get(i).getEventX()== X)&&
                    (eventsList.get(i).getEventY()== Y)){
                  exist = true;
                  break;
                }
               }
             * 
             */

            if (!exist){ // if the coordinate are valid, we can add the event to the event's list
              p++;
              event = new Event();
              event.setCoordinatesOfEvent(X,Y);
              event.setEventId(idEvent);
              event.setMatchedSubs(0);
              event.setMatchedSubsPercentage(0.0);
              event.setCellId((int)updateCellsList.get(c).id);
             
              eventsList.add(event);
              idEvent++;
            }
            exist=false;
        }
    }

   Benchmark.outPutMessages += "    Number of Events : "+eventsList.size()+"\n";
   Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);

    Benchmark.outPutMessages = "    {Create the Event's List End}\n";
    Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
   return eventsList;
}

  /**
   * Distributes the number of events over the cells, proportional to the
   * probability that an event is generated in that cell
   * @param cellsList : the list of grid cells
   * @return cellsUpdatedList: the list of grid cells with the number of points to each cell.
   */
public ArrayList<GridCell> eventDistributionOverCellsProba (ArrayList<GridCell> cellsList){
  ArrayList<GridCell> cellsUpdatedList = cellsList;
  int totalEventsNumber =0;
  Random generator = new Random(cellsUpdatedList.size()*System.currentTimeMillis());
  double r ;
  while(totalEventsNumber < this.maxEventsNumber){
      r = generator.nextDouble();
      int index = (int) (cellsUpdatedList.size() - 1)/2;
      int min = -1;
      int max = cellsUpdatedList.size() ;
      while(true) { // repeat until we found the index of the cell with probability ~= r
        if (cumulativeProbability(cellsUpdatedList, index) > r) {
          max = index;
          index = (int) Math.ceil((index + min + 0.)/2);
        } else if ((index < (cellsUpdatedList.size() - 1) ) && (cumulativeProbability(cellsUpdatedList, (index+1)) < r)) {
                   min = index;
                   index = (int)(index + max) /2;

                } else {
                     break;
                }
     }
     if (cellsUpdatedList.get(index).proba != 0.0){
       cellsUpdatedList.get(index).eventsNumber++ ;
       totalEventsNumber++;
     }
 }

  if (totalEventsNumber < this.maxEventsNumber )
    System.out.println("eventDistributionOverCellsProba(): the total number of events are not generated: " + totalEventsNumber + " < "+this.maxEventsNumber);

  /*for (int c=0; c < cellsUpdatedList.size(); c++){
        System.out.println("cell ("+c+") = "+cellsUpdatedList.get(c).eventsNumber);
  }*/
  return cellsUpdatedList;
}

  /**
   * Calculate the cumulative propabilite from the cell 0 to the cell indexCell
   * probability that a event is generated in that cell
   * @param cellsList : the list of grid cells
   * @return cumulativeProbs: the summation of probability
   */
public double cumulativeProbability(ArrayList<GridCell> cellsList, int indexCell){
  double cumulativeProbs = 0.0;
  for (int c=0; c < indexCell; c++) cumulativeProbs += cellsList.get(c).proba;
  return cumulativeProbs;
}

  /**
   * Save the event's list to a text file, using backspace separator
   * the file can be readed by Gnuplot
   * @param eventList : the event's list
   * @param filename : the name of the output file
   */
public void BenchplotEventsList (ArrayList<Event> eventList, String filename ){
    Benchmark.outPutMessages = "    {Begin exporting event list to a text file...}\n";
    Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
    int eventId, cellId ;
    double x, y;
    int nbSubs ;
    String eregion ="";
    try{
	FileWriter fstream = new FileWriter(filename);
	BufferedWriter  out = new BufferedWriter(fstream);
       out.write("#EventId XCoord YCoord NumberOfSubscriptions Regions\n");
	for (int i = 0; i < eventList.size(); i++) {
            eventId = eventList.get(i).getIdOfEvent();
            x = eventList.get(i).getEventX();
            y = eventList.get(i).getEventY();
            //cellId = eventList.get(i).getIdOfCell();
            nbSubs = eventList.get(i).getMatchedSubs();
            eregion = eventList.get(i).getEventRegions();
            /*the line is:
            * the event's id,  Xcoord, Ycoord  and the number of subscriptions 
             matching this event with their recipient's region
            */
            out.write(eventId+ " " + x +" "+ y+" "+nbSubs+" "+eregion+"\n");
        }
        out.close();
        }catch (IOException ex) {System.err.println(ex);}
     Benchmark.outPutMessages = "    {Event list exporting Done.}\n";
     Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
}

  /**
   * Save the cell's list to a text file,
   * using backspace separator,
   * the file can be readed by Gnuplot.
   */
public void BenchplotCellsList (ArrayList<GridCell> cellsList, String filename ){
    Benchmark.outPutMessages = "    {Begin exporting cells list to a text file...}\n";
    Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
    try{
       FileWriter fstream = new FileWriter(filename);
       BufferedWriter  out = new BufferedWriter(fstream);
       for (int i = 0; i < cellsList.size(); i++) {
              int cellId = (int)cellsList.get(i).id;
              double minX = cellsList.get(i).minRange[0];
              double minY = cellsList.get(i).minRange[1];
              double maxX = cellsList.get(i).maxRange[0];
              double maxY = cellsList.get(i).maxRange[1];
              out.write(cellId+ " " + minX +" "+ minY +" "+ maxX +" "+ maxY+"\n");
        }
        out.close();
     }catch (IOException ex) {System.err.println(ex);}
     Benchmark.outPutMessages = "    {Cells list exporting Done.}\n";
     Starting.outputMessage.setText(Starting.outputMessage.getText()+Benchmark.outPutMessages);
}

  /**
   * Select a random double from range [min,max]
   * @param min : min range
   * @param max : max range
   * @return double
   */
public double getRandomDouble (double min, double max){
    return min + Math.floor(Math.random()*(max+1-min));
}

  /**
   * Check if the choosen coordinate belongs to a valid category
   * @param value : the X or the Y coordinate of the event in the event space
   * @param dimension : dimension of the event space
   * @return valid : the event have a good coordinate if valid = true
   */
public boolean isValidCoordinates(double value, int dimension){
  boolean valid = true;
  if (dimension == 0){
    for (int i=0; i< catgDimX.size();i++){ // verify X
      if( ( value > catgDimX.get(i).valueMin) && (value < catgDimX.get(i).valueMax) )
        valid = true;
    }
  }else{
    for (int i=0; i< catgDimY.size();i++){ // verify X
      if( ( value > catgDimY.get(i).valueMin) && (value < catgDimY.get(i).valueMax) )
        valid = true;
    }
  }
  return valid;
}

}
