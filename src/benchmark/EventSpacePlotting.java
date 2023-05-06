package benchmark;

import reconstruction.Param.Region;

import java.util.ArrayList;
import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Mohamed Barkallah
 */

public class EventSpacePlotting extends JFrame  {

    private JPanel gri;
    private JPanel legendPanel;
    private JScrollPane scrollPane;
    private Dimension area;
    private int offsetX, offsetY;
    private int bound = (int)(reconstruction.Config.EVENT_SPACE_MAX-reconstruction.Config.EVENT_SPACE_MIN)+50;
    private ArrayList<GridCell> cellsList;
    private ArrayList<Event> eventList;
    private ArrayList<Subscription> subsList;

    public EventSpacePlotting (ArrayList<GridCell> gridcellsList,
                              ArrayList<Event> eventsList,
                              ArrayList<Subscription> subsList ){
        this.cellsList = gridcellsList;
        this.eventList = eventsList;
        this.subsList = subsList;
        this.offsetX = 0;
        this.offsetY = 0;
        this.setBounds(offsetX,offsetY, 715, 540);
        area = new Dimension(bound*(Benchmark.eventScale),bound*(Benchmark.eventScale));
        setTitle("Event space");
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        legendPanel = new JPanel();
        legendPanel.setBounds(510, 10, 200, 600);

        gri = new DrawingPane();
        gri.setBackground(Color.WHITE);


        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(gri);
        scrollPane.getViewport().setViewSize(area);
        scrollPane.setBounds(10, 10, 500,  500);

        panelInit();

    }


    public class DrawingPane extends JPanel {
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          int minX , minY , maxX , maxY, h,w;
          int echelle = (int)Benchmark.eventScale;
          // Change grid's Origine (x,y)
          g.translate((bound*echelle)/2,(bound*echelle)/2);
          // Paint event cells
          for(int i=0; i < cellsList.size() ; i++){

            minX = (int)(cellsList.get(i).minRange[0]);
            minY = (int)(cellsList.get(i).minRange[1]);
            maxX = (int)(cellsList.get(i).maxRange[0]);
            maxY = (int)(cellsList.get(i).maxRange[1]);
            h = Math.abs(maxY - minY);
            w = Math.abs(maxX - minX);

            if (cellsList.get(i).proba != 0.0)
            g.setColor(Color.BLACK);
            else
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(minX*echelle, minY*echelle, w*echelle, h*echelle);
          }

          //Paint event's list
          for(int i=0; i < eventList.size() ; i++){
              g.setColor(Color.RED);
              g.fillOval(((int)eventList.get(i).getEventX())*echelle,
                      ((int)eventList.get(i).getEventY())*echelle, 2, 2);
          }

          //Paint subscription's list
          for(int i=0; i <subsList.size() ; i++){
              if (subsList.get(i).getEventsMatched()==0){
                // draw subscriptions that don't matchs events
                minX = (int)(subsList.get(i).E_Coord_minX);
                minY = (int)(subsList.get(i).E_Coord_minY);
                maxX = (int)(subsList.get(i).E_Coord_maxX);
                maxY = (int)(subsList.get(i).E_Coord_maxY);
                   h = Math.abs(maxY - minY);
                   w = Math.abs(maxX - minX);
                   g.setColor(Color.ORANGE);
                   g.drawRect(minX*echelle, minY*echelle, w*echelle, h*echelle);
              }else {
                minX = (int)(subsList.get(i).E_Coord_minX);
                minY = (int)(subsList.get(i).E_Coord_minY);
                maxX = (int)(subsList.get(i).E_Coord_maxX);
                maxY = (int)(subsList.get(i).E_Coord_maxY);
                h = Math.abs(maxY - minY);
                w = Math.abs(maxX - minX);

                if (subsList.get(i).region.equals(Region.Asia.toString())){
                    g.setColor(Color.BLUE);
                }
                if (subsList.get(i).region.equals(Region.Europe.toString())){
                    g.setColor(Color.GREEN);
                }
                if (subsList.get(i).region.equals(Region.US.toString())){
                    g.setColor(Color.MAGENTA);
                }
                g.drawRect(minX*echelle, minY*echelle, w*echelle, h*echelle);
              }

            }
        }
    }
   
    public void panelInit() {
       Container content = getContentPane();
       content.removeAll();
       content.add(scrollPane, BorderLayout.CENTER);
       content.add(legendPanel, BorderLayout.EAST);

       gri.addMouseListener(new java.awt.event.MouseListener() {
            public void mouseClicked(java.awt.event.MouseEvent evt){}
            public void mouseEntered(java.awt.event.MouseEvent evt){}
            public void mouseExited(java.awt.event.MouseEvent evt){}
            public void mousePressed(java.awt.event.MouseEvent evt){}
            public void mouseReleased(java.awt.event.MouseEvent evt){
              gri.setPreferredSize(area);
              gri.revalidate();
              gri.repaint();
          }
        });
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                 formWindowGainedFocus(evt);
            }
        });
    }

    public void formWindowGainedFocus(java.awt.event.WindowEvent evt) {
       gri.validate();
       super.repaint();
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)legendPanel.getGraphics();
        g2D.setColor(Color.BLACK);
        g2D.drawString("[Legend]", 5, 13);
        g2D.drawLine(5, 16, 195, 16);
        g2D.drawString("Event", 5, 160);
        g2D.drawString("ASIA | EUROPE | US", 80, 40);
        g2D.drawString("Subs (Match)", 5, 60);
        g2D.drawString("Subs (Null Match)", 5, 100);
        g2D.drawString("Cells with p=0", 5, 130);

        g2D.drawLine(5, 175, 195, 175);
        g2D.drawString("Number of events : "+this.eventList.size(), 5, 190);
        g2D.drawString("Number of Subs   : "+this.subsList.size(), 5, 210);

        g2D.setColor(Color.RED);
        g2D.fillOval( 45, 155, 4, 4);

        g2D.setColor(Color.BLUE);
        g2D.drawRect( 85, 50, 10, 10);
        g2D.setColor(Color.GREEN);
        g2D.drawRect( 130, 50, 10, 10);
        g2D.setColor(Color.MAGENTA);
        g2D.drawRect( 175, 50, 10, 10);

        g2D.setColor(Color.ORANGE);
        g2D.drawRect( 120, 90, 10, 10);
        g2D.setColor(Color.LIGHT_GRAY);
        g2D.drawRect( 120, 120, 10, 10);


        this.validate();

        gri.setPreferredSize(area);
        gri.revalidate();
        gri.repaint();
  }
}
