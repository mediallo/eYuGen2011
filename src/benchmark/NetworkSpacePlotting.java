package benchmark;

import java.util.ArrayList;
import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import reconstruction.Param.Region;

/**
 *
 * @author Mohamed Barkallah
 */


public class NetworkSpacePlotting extends JFrame  {

    private JPanel gri;
    private JPanel legendPanel;
    private JScrollPane scrollPane;
    private Dimension area;
    private int offsetX;
    private int offsetY;
    private ArrayList<Subscription> subsList;
    private ArrayList<Broker> brokersList;
    private ArrayList<Membership> membershipList;

    public class DrawingPane extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int bX , bY , sX , sY;
            double echelle = Benchmark.networkScale;
            String region ="";
            String reg1 = Region.Asia.toString();//"Asia";
            String reg2 = Region.Europe.toString();//"Europe";
            String reg3 = Region.US.toString();//"US";

            //Change Origine (x,y)
            g.translate(offsetX+500, offsetY+500);

            // Paint links between brokers and subscribers
            for(int k=0; k < membershipList.size() ; k++){
                bX = (int)(membershipList.get(k).brokerCoord[0]);
                bY = (int)(membershipList.get(k).brokerCoord[1]);
                sX = (int)(membershipList.get(k).subCoord[0]);
                sY = (int)(membershipList.get(k).subCoord[1]);
                region = membershipList.get(k).regionOfBroker;
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine((int)(sX*echelle), (int)(sY*echelle), (int)(bX*echelle), (int)(bY*echelle));
            }

            // Paint Subscribers' list
            for(int j=0; j < subsList.size() ; j++){
                sX = (int)(subsList.get(j).N_Coord_X);
                sY = (int)(subsList.get(j).N_Coord_Y);
                region = subsList.get(j).region;

                if (region.equals(reg1)){
                    g.setColor(Color.BLUE);
                }
                if (region.equals(reg2)){
                    g.setColor(Color.GREEN);
                }
                if (region.equals(reg3)){
                    g.setColor(Color.MAGENTA);
                }
                 g.fillOval((int)(sX*echelle)-1, (int)(sY*echelle)-1, 4, 4);
            }

            // Paint brokers' list
            for(int k=0; k < brokersList.size() ; k++){
                bX = (int)(brokersList.get(k).coord_X);
                bY = (int)(brokersList.get(k).coord_Y);
                region = brokersList.get(k).region;

                if (region.equals(reg1)){
                    g.setColor(Color.BLUE);
                }
                if (region.equals(reg2)){
                    g.setColor(Color.GREEN);
                }
                if (region.equals(reg3)){
                    g.setColor(Color.MAGENTA);
                }
                g.drawRect((int)(bX*echelle)-5, (int)(bY*echelle)-5, 10, 10);

            }
        }
    }

    public NetworkSpacePlotting (ArrayList<Subscription> subsList,ArrayList<Broker> brokersList, ArrayList<Membership> membershipList ){
        this.subsList = subsList;
        this.brokersList = brokersList;
        this.membershipList = membershipList;
        this.offsetX = 0;
        this.offsetY = 0;
        this.setBounds(offsetX,offsetY, 715, 540);
        area = new Dimension(1500*(Benchmark.networkScale),1500*(Benchmark.networkScale));
        
        setTitle("Network Space");
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        legendPanel = new JPanel();
        legendPanel.setBounds(510, 10, 200, 200);
        
        gri = new DrawingPane();
        gri.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(gri);
        scrollPane.getViewport().setViewSize(area);
        scrollPane.setBounds(10, 10, 500,  500);

        panelInit();
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
        g2D.drawString("ASIA | EUROPE | US", 80, 30);
        g2D.drawString("Subs", 5, 50);
        g2D.drawString("Brokers", 5, 80);


        g2D.drawLine(5, 135, 195, 135);
        g2D.drawString("Number of Brokers : "+this.brokersList.size(), 5, 150);
        g2D.drawString("Number of Subs   : "+this.subsList.size(), 5, 170);



        g2D.setColor(Color.BLUE);
        g2D.fillOval( 88, 45, 4, 4);
        g2D.setColor(Color.GREEN);
        g2D.fillOval( 132, 45, 4, 4);
        g2D.setColor(Color.MAGENTA);
        g2D.fillOval( 178, 45, 4, 4);

        g2D.setColor(Color.BLUE);
        g2D.drawRect( 85, 70, 10, 10);
        g2D.setColor(Color.GREEN);
        g2D.drawRect( 130, 70, 10, 10);
        g2D.setColor(Color.MAGENTA);
        g2D.drawRect( 175, 70, 10, 10);
      

        this.validate();

        gri.setPreferredSize(area);
        gri.revalidate();
        gri.repaint();
     }

}
