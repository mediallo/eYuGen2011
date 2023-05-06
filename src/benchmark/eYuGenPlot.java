package benchmark;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.BasicStroke;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Mohamed Barkallah
 */
public class eYuGenPlot extends JFrame {
  private int Xorigine, Yorigine, h;

  //private int width = (int)Toolkit.getDefaultToolkit().getScreenSize().width;
  //private int heigh = (int)Toolkit.getDefaultToolkit().getScreenSize().height;
  String[] params;
  int bins = Benchmark.accuracy;
  private String MDFile = "";
  private String DRFile = "";
  private String T = "";
  private String L = "";
  private float [] Dashes = {2.0F, 4.0F, 2.0F, 4.0F};
  private BasicStroke gridStyle = new BasicStroke (1.0F, BasicStroke.CAP_ROUND,  BasicStroke.JOIN_MITER, 10.0F, Dashes, 0.F);
  private BasicStroke shapeStyle = new BasicStroke (2.0F, BasicStroke.CAP_ROUND,  BasicStroke.JOIN_ROUND, 10.0F, null, 0.F);
  private BasicStroke axeStyle = new BasicStroke (1.0F, BasicStroke.CAP_ROUND,  BasicStroke.JOIN_ROUND, 10.0F, null, 0.F);

    /** Creates new form eYuGenPlot */

    public eYuGenPlot(String MD_NameFile, String DR_NameFile, String Param, String Label) {
       
        initComponents();
        this.MDFile = MD_NameFile;
        this.DRFile = DR_NameFile;
        this.T = Param;
        this.L = Label;
        Xorigine = 4;
        Yorigine = 301;
        h = 300;        
        this.paramText.setSize(170, 50);
        this.paramLabel.setSize(170, 30);
        //this.setBounds(0,0,this.width, this.heigh);
        this.setResizable(false);
        
        addWindowListener( new java.awt.event.WindowAdapter(){
                public void windowClosing(java.awt.event.WindowEvent e){
                setVisible(false);
                dispose();
        }});
    }

    public void paint ( Graphics g ) {
        //to-do here:
        super.paint(g);
        paintMD(this.zoneMD.getGraphics());
        paintDR(this.zoneDR.getGraphics());
        paintlegende(this.legendPanel.getGraphics());
        paintTable();
        super.invalidate();
        super.validate();
        super.repaint();
     }

    public void paintMD ( Graphics gp ) { // paint the matching distribution shape
     Graphics2D g2D = (Graphics2D) gp;     
     String[][] pPoint ;
     pPoint = getArrayValues(MDFile, bins);

    //Draw the X and Y lines
     g2D.setColor(Color.BLACK);
     g2D.setStroke(axeStyle);
     g2D.drawLine(Xorigine, Yorigine, Xorigine+h, Yorigine);
     for (int i=0; i<= 11 ; i++) g2D.drawLine((i*(h/10))+Xorigine, Yorigine, (i*(h/10))+Xorigine, Yorigine+4);
     g2D.drawLine(Xorigine, Yorigine, Xorigine, h-Yorigine);
     for (int i=0; i<= 11; i++) g2D.drawLine(Xorigine, (i*(h/10))+1 , Xorigine-4, (i*(h/10))+1);
      for (int j=0; j<=100; j++) g2D.drawLine(Xorigine, (j*(h/100))+1 , Xorigine-2, (j*(h/100))+1);

     //Draw the Grid
     g2D.setColor(Color.lightGray);
     g2D.setStroke(gridStyle);
     for (int i=1; i< 11; i++) g2D.drawLine((i*(h/10))+Xorigine, Yorigine, (i*(h/10))+Xorigine, Yorigine-h);
     for (int i=1; i< 11; i++) g2D.drawLine(Xorigine, Yorigine-(i*(h/10)), Xorigine+h, Yorigine-(i*(h/10)));

     //Draw the shape

     g2D.setColor(Color.red);
     g2D.setStroke(shapeStyle);
     
     Double y1;
     Double y2 = 100.0;
     for (int i=0; i< pPoint.length-1; i++){
       y1 = (100 - Double.parseDouble(pPoint[i][1])) / 100; // a value between [0,1]
       y2 = (100 - Double.parseDouble(pPoint[i+1][1])) / 100; // a value between [0,1]
       
       g2D.drawLine(Xorigine+(Integer.parseInt(pPoint[i][0])*(h/100)), Yorigine-((int)(h*y1)) ,
               Xorigine+(Integer.parseInt(pPoint[i+1][0])*(h/100)),  Yorigine-((int)(h*y2)));
       g2D.fillOval(Xorigine+(Integer.parseInt(pPoint[i][0])*(h/100))-3, Yorigine-((int)(h*y1))-3 , 6, 6);
     }
     g2D.fillOval(Xorigine+(Integer.parseInt(pPoint[pPoint.length-1][0])*(h/100))-3, Yorigine-((int)(h*y2))-3 , 6, 6);

     this.zoneMD.validate();
     this.validate();
   }

    public void paintDR ( Graphics gp ) { // paint the Density of recipient shape
     Graphics2D g2D = (Graphics2D) gp;
     String[][] pPoint ;
     pPoint = getArrayValues(DRFile, bins);

     //Draw the X and Y lines
     g2D.setColor(Color.BLACK);
     g2D.setStroke(axeStyle);
     g2D.drawLine(Xorigine, Yorigine, Xorigine+h, Yorigine);
     for (int i=0; i<= 11; i++) g2D.drawLine((i*(h/10))+Xorigine, Yorigine, (i*(h/10))+Xorigine, Yorigine+4);
     g2D.drawLine(Xorigine, Yorigine, Xorigine, h-Yorigine);
     for (int i=0; i<= 11; i++) g2D.drawLine(Xorigine, (i*(h/10))+1 , Xorigine-4, (i*(h/10))+1);
      for (int j=0; j<=100; j++) g2D.drawLine(Xorigine, (j*(h/100))+1 , Xorigine-2, (j*(h/100))+1);

     //Draw the Grid
     g2D.setColor(Color.lightGray);
     g2D.setStroke(gridStyle);
     for (int i=1; i<11; i++) g2D.drawLine((i*(h/10))+Xorigine, Yorigine, (i*(h/10))+Xorigine, Yorigine-h);
     for (int i=1; i<11; i++) g2D.drawLine(Xorigine, Yorigine-(i*(h/10)), Xorigine+h, Yorigine-(i*(h/10)));

     //Draw the shape

     g2D.setColor(Color.red);
     g2D.setStroke(shapeStyle);

     Double y1;
     Double y2 = 100.0;
     for (int i=0; i< pPoint.length-1; i++){
       y1 = (100 - Double.parseDouble(pPoint[i][1])) / 100; // a value between [0,1]
       y2 = (100 - Double.parseDouble(pPoint[i+1][1])) / 100; // a value between [0,1]

       g2D.drawLine(Xorigine+(Integer.parseInt(pPoint[i][0])*(h/100)), Yorigine-((int)(h*y1)) ,
               Xorigine+(Integer.parseInt(pPoint[i+1][0])*(h/100)),  Yorigine-((int)(h*y2)));
       g2D.fillOval(Xorigine+(Integer.parseInt(pPoint[i][0])*(h/100))-3, Yorigine-((int)(h*y1))-3 , 6, 6);
     }
     g2D.fillOval(Xorigine+(Integer.parseInt(pPoint[pPoint.length-1][0])*(h/100))-3, Yorigine-((int)(h*y2))-3 , 6, 6);
     
     this.zoneDR.validate();
     this.validate();
   }

   public String[][] getArrayValues (String nameOfFile, int dgree){
      
      File file = new File(nameOfFile);
      BufferedReader input = null;
      String[] str = new String[6];
      String [][] point = new String[(10*bins)+1][2];
      try {
            input = new BufferedReader(new FileReader(file));
            String line = null;
            int j=0;
            while ((line = input.readLine()) != null)  {
                str = line.split(" ");
                point[j][0] = str[0];
                point[j][1] = str[4];
                j++;
            }
            input.close();
        }catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
      
      return point;
    }

    public void paintlegende ( Graphics gp ) {
     Graphics2D g2D = (Graphics2D) gp;
    
     this.paramText.setFont(new Font("Arial", 1, 14));
     this.paramLabel.setFont(new Font("Arial", 2, 11));
     this.paramText.setText(this.T);
     this.paramLabel.setText(this.L);
     
     g2D.setColor(Color.RED);
     g2D.setStroke(shapeStyle);
     g2D.drawString("line ",70, 100);
     g2D.fillOval(137,92, 6, 6 ); // the point
     g2D.drawLine(120, 95 , 160 , 95); // the line

     this.legendPanel.validate();
     this.validate();
   }

    public void paintTable(){
      File fDir = new File(MDFile);
      String listParams = fDir.getName().toString();
        int index = listParams.lastIndexOf("-");
        listParams = listParams.substring(0, index);
        params = listParams.split("-");
        if ( params[0].substring(0,1).equals("N"))
        this.paramsTable.setValueAt("None", 0, 0);
        else  if ( params[0].substring(0,1).equals("U"))
          this.paramsTable.setValueAt("Uniform", 0, 0);
        else this.paramsTable.setValueAt("Proportional", 0, 0);

        for (int i=0; i<4; i++)
          params[i] = params[i].substring(1);
        for (int i=5; i<params.length; i++)
          params[i] = params[i].substring(2);

        int j=1;
        for (int i=0; i<params.length; i++){
          this.paramsTable.setValueAt(params[i], 0, j);
          j++;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    label5 = new java.awt.Label();
    zoneMD = new java.awt.Panel();
    zoneDR = new java.awt.Panel();
    label2 = new java.awt.Label();
    label4 = new java.awt.Label();
    label6 = new java.awt.Label();
    label3 = new java.awt.Label();
    label7 = new java.awt.Label();
    popularityGraphTitle = new java.awt.Label();
    legendPanel = new java.awt.Panel();
    paramText = new java.awt.Label();
    paramLabel = new java.awt.Label();
    localityGraphTitlle = new java.awt.Label();
    paramsTablePanel = new javax.swing.JScrollPane();
    paramsTable = new javax.swing.JTable();
    jSeparator1 = new javax.swing.JSeparator();
    jPanelMenu = new javax.swing.JPanel();
    save = new java.awt.Button();
    open = new java.awt.Button();
    statistic = new java.awt.Button();
    printToJpg = new java.awt.Button();
    outputsFolder = new java.awt.Button();
    help = new java.awt.Button();
    label8 = new java.awt.Label();
    label9 = new java.awt.Label();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("eYuGenPlot");
    setMinimumSize(new java.awt.Dimension(865, 485));
    setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
    setName("benchplot"); // NOI18N
    getContentPane().setLayout(null);

    label5.setAlignment(java.awt.Label.RIGHT);
    label5.setFont(new java.awt.Font("Dialog", 0, 10));
    label5.setText("100%");
    getContentPane().add(label5);
    label5.setBounds(510, 80, 30, 18);

    zoneMD.setBackground(new java.awt.Color(255, 255, 255));
    zoneMD.setName("zoneMD"); // NOI18N

    javax.swing.GroupLayout zoneMDLayout = new javax.swing.GroupLayout(zoneMD);
    zoneMD.setLayout(zoneMDLayout);
    zoneMDLayout.setHorizontalGroup(
      zoneMDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 306, Short.MAX_VALUE)
    );
    zoneMDLayout.setVerticalGroup(
      zoneMDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 306, Short.MAX_VALUE)
    );

    getContentPane().add(zoneMD);
    zoneMD.setBounds(30, 80, 306, 306);

    zoneDR.setBackground(new java.awt.Color(255, 255, 255));
    zoneDR.setName("zonePopularity"); // NOI18N

    javax.swing.GroupLayout zoneDRLayout = new javax.swing.GroupLayout(zoneDR);
    zoneDR.setLayout(zoneDRLayout);
    zoneDRLayout.setHorizontalGroup(
      zoneDRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 306, Short.MAX_VALUE)
    );
    zoneDRLayout.setVerticalGroup(
      zoneDRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 306, Short.MAX_VALUE)
    );

    getContentPane().add(zoneDR);
    zoneDR.setBounds(540, 80, 306, 306);

    label2.setAlignment(java.awt.Label.RIGHT);
    label2.setFont(new java.awt.Font("Dialog", 0, 10));
    label2.setText("100%");
    getContentPane().add(label2);
    label2.setBounds(820, 390, 30, 18);

    label4.setAlignment(java.awt.Label.RIGHT);
    label4.setFont(new java.awt.Font("Dialog", 0, 10));
    label4.setText("0");
    getContentPane().add(label4);
    label4.setBounds(510, 390, 30, 18);

    label6.setAlignment(java.awt.Label.RIGHT);
    label6.setFont(new java.awt.Font("Dialog", 0, 10));
    label6.setText("100%");
    getContentPane().add(label6);
    label6.setBounds(0, 80, 30, 18);

    label3.setAlignment(java.awt.Label.RIGHT);
    label3.setFont(new java.awt.Font("Dialog", 0, 10));
    label3.setText("50");
    getContentPane().add(label3);
    label3.setBounds(670, 390, 30, 18);

    label7.setAlignment(java.awt.Label.RIGHT);
    label7.setFont(new java.awt.Font("Dialog", 0, 10));
    label7.setText("0");
    getContentPane().add(label7);
    label7.setBounds(0, 390, 30, 18);

    popularityGraphTitle.setAlignment(java.awt.Label.CENTER);
    popularityGraphTitle.setFont(new java.awt.Font("Arial", 1, 24));
    popularityGraphTitle.setText("Matching Distribution");
    getContentPane().add(popularityGraphTitle);
    popularityGraphTitle.setBounds(30, 30, 305, 50);

    legendPanel.setName("zonePopularity"); // NOI18N

    paramText.setAlignment(java.awt.Label.CENTER);
    paramText.setFont(new java.awt.Font("Arial", 1, 12));
    paramText.setText("The parameter");

    paramLabel.setAlignment(java.awt.Label.CENTER);
    paramLabel.setFont(new java.awt.Font("Arial", 0, 12));
    paramLabel.setText("[mode]");

    javax.swing.GroupLayout legendPanelLayout = new javax.swing.GroupLayout(legendPanel);
    legendPanel.setLayout(legendPanelLayout);
    legendPanelLayout.setHorizontalGroup(
      legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(legendPanelLayout.createSequentialGroup()
        .addGroup(legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(paramText, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(paramLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    legendPanelLayout.setVerticalGroup(
      legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(legendPanelLayout.createSequentialGroup()
        .addComponent(paramText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(1, 1, 1)
        .addComponent(paramLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(225, Short.MAX_VALUE))
    );

    getContentPane().add(legendPanel);
    legendPanel.setBounds(340, 80, 180, 306);

    localityGraphTitlle.setAlignment(java.awt.Label.CENTER);
    localityGraphTitlle.setFont(new java.awt.Font("Arial", 1, 24));
    localityGraphTitlle.setText("Density Of Recipients");
    getContentPane().add(localityGraphTitlle);
    localityGraphTitlle.setBounds(540, 30, 305, 50);

    paramsTablePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    paramsTablePanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    paramsTablePanel.setAlignmentX(0.0F);
    paramsTablePanel.setAlignmentY(0.0F);
    paramsTablePanel.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
    paramsTablePanel.setFocusable(false);
    paramsTablePanel.setFont(new java.awt.Font("Tahoma", 0, 10));
    paramsTablePanel.setHorizontalScrollBar(null);
    paramsTablePanel.setWheelScrollingEnabled(false);

    paramsTable.setFont(new java.awt.Font("Tahoma", 0, 10));
    paramsTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null, null, "", null, null, null, null}
      },
      new String [] {
        "Generalization mode", "Generalization frac", "Events", "Subscriptions", "Brokers", "Kernel mode", "Kernel bandwidth", "Grid's cells", "Hot interest", "Diffusion proba"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
      };
      boolean[] canEdit = new boolean [] {
        true, false, false, false, false, false, false, false, false, false
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    paramsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    paramsTable.setAutoscrolls(false);
    paramsTable.setFocusable(false);
    paramsTable.setRequestFocusEnabled(false);
    paramsTable.setRowSelectionAllowed(false);
    paramsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paramsTable.setUpdateSelectionOnSort(false);
    paramsTable.setVerifyInputWhenFocusTarget(false);
    paramsTablePanel.setViewportView(paramsTable);
    paramsTable.getAccessibleContext().setAccessibleParent(this);

    getContentPane().add(paramsTablePanel);
    paramsTablePanel.setBounds(30, 410, 820, 40);
    getContentPane().add(jSeparator1);
    jSeparator1.setBounds(0, 25, 860, 5);

    jPanelMenu.setLayout(new java.awt.GridLayout(1, 0));

    save.setLabel("Export Scenario");
    save.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveActionPerformed(evt);
      }
    });
    jPanelMenu.add(save);

    open.setLabel("Import Scenario");
    open.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        openActionPerformed(evt);
      }
    });
    jPanelMenu.add(open);

    statistic.setLabel("Statistics");
    statistic.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        statisticActionPerformed(evt);
      }
    });
    jPanelMenu.add(statistic);

    printToJpg.setLabel("Screen Shot");
    printToJpg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        printToJpgActionPerformed(evt);
      }
    });
    jPanelMenu.add(printToJpg);

    outputsFolder.setLabel("Output folder");
    outputsFolder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        outputsFolderActionPerformed(evt);
      }
    });
    jPanelMenu.add(outputsFolder);

    help.setLabel("Help");
    help.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        helpActionPerformed(evt);
      }
    });
    jPanelMenu.add(help);

    getContentPane().add(jPanelMenu);
    jPanelMenu.setBounds(0, 0, 860, 24);

    label8.setAlignment(java.awt.Label.RIGHT);
    label8.setFont(new java.awt.Font("Dialog", 0, 10));
    label8.setText("100%");
    getContentPane().add(label8);
    label8.setBounds(310, 390, 30, 18);

    label9.setAlignment(java.awt.Label.RIGHT);
    label9.setFont(new java.awt.Font("Dialog", 0, 10));
    label9.setText("50");
    getContentPane().add(label9);
    label9.setBounds(155, 390, 30, 18);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void printToJpgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printToJpgActionPerformed
      this.printToJpg.validate();
      BufferedImage image = null;
      Rectangle r = new Rectangle();
      r = this.getBounds();
      r.y = r.y + 46;
      r.height = r.height - 46;
      try {
        image = new Robot().createScreenCapture(r);
      } catch(AWTException e) {
        e.printStackTrace();
      }

      FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Files", "jpg");
      File file = null;
      JFileChooser choose = new JFileChooser(file);
      choose.setFileFilter(filter);

      if (choose.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
        return;
      else
        file = choose.getSelectedFile();

      try {
        ImageIO.write(image, "jpg", file);
      } catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }//GEN-LAST:event_printToJpgActionPerformed

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
      this.open.validate();
      File fDir = new File(MDFile);
      FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
      JFileChooser fc = new JFileChooser(fDir);
      fc.setFileFilter(filter);
      int returnVal = fc.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {

        File file = fc.getSelectedFile();

        String listParams = file.getName().toString();
        int index = listParams.lastIndexOf("-");
        listParams = listParams.substring(0, index);
        MDFile = "outputs/benchmark/"+listParams+"-EventsMD.txt";
        DRFile = "outputs/benchmark/"+listParams+"-EventsDR.txt";
        this.T ="";
        this.L ="";

        this.zoneMD.update(this.zoneMD.getGraphics());
        this.zoneDR.update(this.zoneDR.getGraphics());
        this.zoneMD.validate();
        this.zoneDR.validate();
        this.validate();

      } else {
        System.out.println("Open command cancelled by user." );
      }
    }//GEN-LAST:event_openActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
      this.save.validate();
      String path;
      JFileChooser choose = new JFileChooser();
      choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      if (choose.showDialog(this, "Export To")== JFileChooser.CANCEL_OPTION)
        return;
      else
        path = choose.getSelectedFile().getPath();

        String listParams = this.MDFile;
        int index = listParams.lastIndexOf("-");
        int deb = listParams.lastIndexOf("/");
        listParams = listParams.substring(deb+1, index);

      if (!copyFile(new File(this.MDFile), path, MDFile.substring(17)))
         System.out.println("Error Copying the matching distribution's file");
      else System.out.println("matching distribution : Save done");

      if (!copyFile(new File(this.DRFile), path, DRFile.substring(17)))
         System.out.println("Error Copying the density of recipient's file");
      else System.out.println("density of recipient : Save done");

      String f1 = this.DRFile.replaceAll("EventsDR", "EventSelectivitySubs");
       if (!copyFile(new File(f1), path, f1.substring(17)))
         System.out.println("Error Copying the matching distribution's file");
      else System.out.println("Event Selectivity : Save done");

      String f2 = this.DRFile.replaceAll("EventsDR", "EventMembershipBrokers");
      if (!copyFile(new File(f2), path, f2.substring(17)))
         System.out.println("Error Copying the Membership Brokers file");
      else System.out.println("Membership Brokers : Save done");

       if (!copyFile(new File(Benchmark.OUTPUT_EVENTS_FILE), path, listParams+"-"+Benchmark.OUTPUT_EVENTS_FILE.substring(8)))
         System.out.println("Error Copying the event's coordinates file");
      else System.out.println("Event's coordinates : Save done");

      if (!copyFile(new File(Benchmark.OUTPUT_SUBS_COORD_FILE), path, listParams+"-"+Benchmark.OUTPUT_SUBS_COORD_FILE.substring(8)))
         System.out.println("Error Copying the subscription's coordinates file");
      else System.out.println("Subscription's coordinates : Save done");

      if (!copyFile(new File(Benchmark.OUTPUT_BROKERS_COORD_FILE), path, listParams+"-"+Benchmark.OUTPUT_BROKERS_COORD_FILE.substring(8)))
         System.out.println("Error Copying the broker's coordinates file");
      else System.out.println("Broker's coordinates : Save done");

    }//GEN-LAST:event_saveActionPerformed

    private void outputsFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputsFolderActionPerformed
        File file = new File("outputs");
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
          desktop = Desktop.getDesktop();
        }
        try {
          desktop.open(file);
        }
        catch (IOException e){
          System.out.println("outputsFolderActionPerformed: Error to find the outputs folder.");
        }
    }//GEN-LAST:event_outputsFolderActionPerformed

    private void helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_helpActionPerformed

    private void statisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticActionPerformed

      statForm stats = new statForm();
      stats.setVisible(true);
      stats.validate();
    }//GEN-LAST:event_statisticActionPerformed

    public boolean copyFile( File source, String path, String fileName){ //Methode permettant la copie d'un fichier
      boolean resultat = false;

      // Declaration des flux
      FileInputStream sourceFile = null;
      FileOutputStream destinationFile = null;
      File dest;
      try {
      dest = new File(path+"\\"+fileName);
      
      sourceFile = new java.io.FileInputStream(source);
      destinationFile = new java.io.FileOutputStream(dest);

      byte buffer[]=new byte[512*1024];
      int nbLecture;

      while( (nbLecture = sourceFile.read(buffer)) != -1 ) {
      destinationFile.write(buffer, 0, nbLecture);
      }

      resultat = true;
      } catch( java.io.FileNotFoundException f ) {
      } catch( java.io.IOException e ) {
      } finally {
      try {
      sourceFile.close();
      } catch(Exception e) { }
      try {
      destinationFile.close();
      } catch(Exception e) { }
      }
      return( resultat );
  }
    

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private java.awt.Button help;
  private javax.swing.JPanel jPanelMenu;
  private javax.swing.JSeparator jSeparator1;
  private java.awt.Label label2;
  private java.awt.Label label3;
  private java.awt.Label label4;
  private java.awt.Label label5;
  private java.awt.Label label6;
  private java.awt.Label label7;
  private java.awt.Label label8;
  private java.awt.Label label9;
  private java.awt.Panel legendPanel;
  private java.awt.Label localityGraphTitlle;
  private java.awt.Button open;
  private java.awt.Button outputsFolder;
  private java.awt.Label paramLabel;
  private java.awt.Label paramText;
  private javax.swing.JTable paramsTable;
  private javax.swing.JScrollPane paramsTablePanel;
  private java.awt.Label popularityGraphTitle;
  private java.awt.Button printToJpg;
  private java.awt.Button save;
  private java.awt.Button statistic;
  private java.awt.Panel zoneDR;
  private java.awt.Panel zoneMD;
  // End of variables declaration//GEN-END:variables

}
