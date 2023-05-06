package benchmark;

import java.awt.*;
import java.io.*;
import java.io.File;
import java.io.File.*;
import java.awt.Color;
import java.util.Date;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.filechooser.*;


/**
 *
 * @author Mohamed Barkallah
 */
public class Starting extends JFrame {

   private boolean noError;
   public String mode ="N";
   private String nameInputFile;
   private boolean inputConfigFileExist = false;
    
    /** Creates new form Starting */
   public Starting() {

        initComponents();

        this.BenchPanel.enable(false);

        this.yugenStart.setBackground(Color.LIGHT_GRAY);
        this.benchStart.setBackground(Color.LIGHT_GRAY);

        this.eventSpace.enable(false);
        this.networkSpace.enable(false);
        this.benchplot.enable(false);

        this.hot_Remove.enable(false);
        this.smoothingP.enable(false);
        this.h_Frac.enable(false);
        this.jScrollregioRatioTable.setVisible(false);
        this.noError = true;

        this.validate();
    }

    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu2 = new javax.swing.JMenu();
    jMenu3 = new javax.swing.JMenu();
    jMenuBar2 = new javax.swing.JMenuBar();
    jMenu4 = new javax.swing.JMenu();
    jMenu5 = new javax.swing.JMenu();
    exit = new java.awt.Button();
    evaluateParam = new javax.swing.JComboBox();
    jLabel1 = new javax.swing.JLabel();
    GeneralSetting = new javax.swing.JTabbedPane();
    yuGenStetting = new javax.swing.JPanel();
    panel2 = new java.awt.Panel();
    yugenConfig = new java.awt.Button();
    yuGenConfigDone = new java.awt.Button();
    YgenPanel = new java.awt.Panel();
    jPanelBrokers = new javax.swing.JPanel();
    l2 = new java.awt.Label();
    jScrollPane2 = new javax.swing.JScrollPane();
    brokersTable = new javax.swing.JTable();
    jPanelEvents = new javax.swing.JPanel();
    l3 = new java.awt.Label();
    l9 = new java.awt.Label();
    l13 = new java.awt.Label();
    l4 = new java.awt.Label();
    l17 = new java.awt.Label();
    part_N = new java.awt.TextField();
    bandwidth = new java.awt.TextField();
    eventSpaceMinMax = new java.awt.TextField();
    kFunc = new javax.swing.JComboBox();
    jPanelSubscriptions = new javax.swing.JPanel();
    l1 = new java.awt.Label();
    subNum = new java.awt.TextField();
    l12 = new java.awt.Label();
    modeList = new javax.swing.JComboBox();
    l6 = new java.awt.Label();
    h_Frac = new java.awt.TextField();
    l7 = new java.awt.Label();
    hotIInterestRemove = new javax.swing.JComboBox();
    l8 = new java.awt.Label();
    hot_Remove = new java.awt.TextField();
    l10 = new java.awt.Label();
    InterestDiffusion = new javax.swing.JComboBox();
    l11 = new java.awt.Label();
    smoothingP = new java.awt.TextField();
    jScrollregioRatioTable = new javax.swing.JScrollPane();
    regioRatioTable = new javax.swing.JTable();
    regionRatioModeList = new javax.swing.JComboBox();
    l14 = new java.awt.Label();
    jSeparator1 = new javax.swing.JSeparator();
    benchmarkSetting = new javax.swing.JPanel();
    BenchPanel = new java.awt.Panel();
    jPanelBenchmark = new javax.swing.JPanel();
    l5 = new java.awt.Label();
    label5 = new java.awt.Label();
    accuracyDegreeList = new javax.swing.JComboBox();
    eventsNum = new java.awt.TextField();
    label1 = new java.awt.Label();
    l20 = new java.awt.Label();
    l21 = new java.awt.Label();
    eventScaleList = new javax.swing.JComboBox();
    networkScaleList = new javax.swing.JComboBox();
    panel1 = new java.awt.Panel();
    benchConfigModify = new java.awt.Button();
    benchConfigDone = new java.awt.Button();
    defaultSettings = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    l15 = new java.awt.Label();
    textField1 = new java.awt.TextField();
    l16 = new java.awt.Label();
    textField2 = new java.awt.TextField();
    l18 = new java.awt.Label();
    textField4 = new java.awt.TextField();
    l19 = new java.awt.Label();
    textField5 = new java.awt.TextField();
    jPanelMenu = new javax.swing.JPanel();
    yugenStart = new java.awt.Button();
    benchStart = new java.awt.Button();
    jScrollPane1 = new javax.swing.JScrollPane();
    outputMessage = new javax.swing.JTextPane();
    eventSpace = new java.awt.Button();
    networkSpace = new java.awt.Button();
    benchplot = new java.awt.Button();
    jMenuBar = new javax.swing.JMenuBar();
    jMenu6 = new javax.swing.JMenu();
    loadSenarioSetting = new javax.swing.JMenuItem();
    saveSenarioSetting = new javax.swing.JMenuItem();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();

    jMenu2.setText("File");
    jMenuBar1.add(jMenu2);

    jMenu3.setText("Edit");
    jMenuBar1.add(jMenu3);

    jMenu4.setText("File");
    jMenuBar2.add(jMenu4);

    jMenu5.setText("Edit");
    jMenuBar2.add(jMenu5);

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("eYuGen");
    setIconImage(Toolkit.getDefaultToolkit().getImage(".\\src\\benchmark\\icone.jpg") );
    setIconImages(null);
    setMinimumSize(new java.awt.Dimension(765, 540));
    setName("Benchmark solution"); // NOI18N
    setResizable(false);
    addWindowFocusListener(new java.awt.event.WindowFocusListener() {
      public void windowGainedFocus(java.awt.event.WindowEvent evt) {
        formWindowGainedFocus(evt);
      }
      public void windowLostFocus(java.awt.event.WindowEvent evt) {
      }
    });
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    exit.setActionCommand("exit");
    exit.setLabel("Exit");
    exit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitActionPerformed(evt);
      }
    });
    getContentPane().add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 460, 30, -1));

    evaluateParam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "#Events", "#Subscriptions", "#Brokers", "#cells", "Kernel function", "Bandwidth", "Hierarchy fraction", "Interest removal", "Smoothing probability" }));
    evaluateParam.setFocusable(false);
    evaluateParam.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent evt) {
        evaluateParamFocusGained(evt);
      }
    });
    getContentPane().add(evaluateParam, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 250, -1));

    jLabel1.setText("Evaluate the parameter :");
    getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

    GeneralSetting.setMinimumSize(new java.awt.Dimension(740, 300));
    GeneralSetting.setPreferredSize(new java.awt.Dimension(740, 300));

    yuGenStetting.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    panel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

    yugenConfig.setLabel("Edit Settings");
    yugenConfig.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        yugenConfigActionPerformed(evt);
      }
    });
    panel2.add(yugenConfig);

    yuGenConfigDone.setEnabled(false);
    yuGenConfigDone.setLabel("Done");
    yuGenConfigDone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        yuGenConfigDoneActionPerformed(evt);
      }
    });
    panel2.add(yuGenConfigDone);

    yuGenStetting.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 241, 430, 34));

    YgenPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanelBrokers.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));

    l2.setFont(new java.awt.Font("Dialog", 1, 12));
    l2.setText("Number of Brokers");

    brokersTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {"Numbers :", new Integer(1), new Integer(1), new Integer(1)}
      },
      new String [] {
        "REGIONS", "Asia", "US", "Europe"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, true, true, true
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    brokersTable.setRowSelectionAllowed(false);
    brokersTable.setShowHorizontalLines(false);
    brokersTable.setSurrendersFocusOnKeystroke(true);
    brokersTable.getTableHeader().setReorderingAllowed(false);
    brokersTable.setUpdateSelectionOnSort(false);
    brokersTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseExited(java.awt.event.MouseEvent evt) {
        brokersTableMouseExited(evt);
      }
    });
    brokersTable.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        brokersTableKeyReleased(evt);
      }
    });
    jScrollPane2.setViewportView(brokersTable);
    brokersTable.getColumnModel().getColumn(0).setResizable(false);
    brokersTable.getColumnModel().getColumn(1).setResizable(false);
    brokersTable.getColumnModel().getColumn(2).setResizable(false);
    brokersTable.getColumnModel().getColumn(3).setResizable(false);
    brokersTable.getAccessibleContext().setAccessibleParent(jPanelBrokers);

    javax.swing.GroupLayout jPanelBrokersLayout = new javax.swing.GroupLayout(jPanelBrokers);
    jPanelBrokers.setLayout(jPanelBrokersLayout);
    jPanelBrokersLayout.setHorizontalGroup(
      jPanelBrokersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelBrokersLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelBrokersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanelBrokersLayout.createSequentialGroup()
            .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(162, 162, 162))
          .addGroup(jPanelBrokersLayout.createSequentialGroup()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
            .addContainerGap())))
    );
    jPanelBrokersLayout.setVerticalGroup(
      jPanelBrokersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelBrokersLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(1, 1, 1)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(23, 23, 23))
    );

    YgenPanel.add(jPanelBrokers, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 294, 80));

    jPanelEvents.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));

    l3.setFont(new java.awt.Font("Dialog", 1, 12));
    l3.setText("Number of grid cells (>0)");

    l9.setFont(new java.awt.Font("Dialog", 1, 12));
    l9.setText("Event's distribution :");

    l13.setText("Kernel Function");

    l4.setText("Bandwidth >0");

    l17.setFont(new java.awt.Font("Dialog", 1, 12));
    l17.setText("Event space size +/-");

    part_N.setText("10");

    bandwidth.setText("1");

    eventSpaceMinMax.setBackground(java.awt.Color.white);
    eventSpaceMinMax.setText("400");

    kFunc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "epanechnikov", "triangular", "gaussian", "tricube", "uniform" }));
    kFunc.setDoubleBuffered(true);
    kFunc.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        kFuncActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelEventsLayout = new javax.swing.GroupLayout(jPanelEvents);
    jPanelEvents.setLayout(jPanelEventsLayout);
    jPanelEventsLayout.setHorizontalGroup(
      jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelEventsLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanelEventsLayout.createSequentialGroup()
            .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(l17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(l3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(eventSpaceMinMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(part_N, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)))
          .addGroup(jPanelEventsLayout.createSequentialGroup()
            .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(l4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(l13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(l9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanelEventsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(bandwidth, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(jPanelEventsLayout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        .addContainerGap(36, Short.MAX_VALUE))
    );
    jPanelEventsLayout.setVerticalGroup(
      jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelEventsLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(part_N, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(l3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(10, 10, 10)
        .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(eventSpaceMinMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(l17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(jPanelEventsLayout.createSequentialGroup()
            .addGap(34, 34, 34)
            .addComponent(l13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(jPanelEventsLayout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(kFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(l4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(bandwidth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(44, 44, 44))
      .addGroup(jPanelEventsLayout.createSequentialGroup()
        .addGap(70, 70, 70)
        .addComponent(l9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGap(98, 98, 98))
    );

    YgenPanel.add(jPanelEvents, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 84, 294, 184));

    jPanelSubscriptions.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
    jPanelSubscriptions.setAutoscrolls(true);
    jPanelSubscriptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    l1.setFont(new java.awt.Font("Dialog", 1, 12));
    l1.setText("Number of Subscriptions (>3)");
    jPanelSubscriptions.add(l1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, -1));

    subNum.setText("50");
    jPanelSubscriptions.add(subNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 90, -1));

    l12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
    l12.setText("Interest generalization mode :");
    jPanelSubscriptions.add(l12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 250, -1));

    modeList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "none", "uniform", "proportional" }));
    modeList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        modeListActionPerformed(evt);
      }
    });
    jPanelSubscriptions.add(modeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 90, -1));

    l6.setAlignment(java.awt.Label.CENTER);
    l6.setText("Hierarchy Frac ]0,1]");
    jPanelSubscriptions.add(l6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 120, -1));

    h_Frac.setText("0");
    jPanelSubscriptions.add(h_Frac, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 50, -1));

    l7.setFont(new java.awt.Font("Dialog", 1, 12));
    l7.setText("Hot interest removal :");
    jPanelSubscriptions.add(l7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 180, -1));

    hotIInterestRemove.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "false", "true" }));
    hotIInterestRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        hotIInterestRemoveActionPerformed(evt);
      }
    });
    jPanelSubscriptions.add(hotIInterestRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 70, -1));

    l8.setText("Number");
    jPanelSubscriptions.add(l8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 70, -1));

    hot_Remove.setText("0");
    jPanelSubscriptions.add(hot_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 50, -1));

    l10.setFont(new java.awt.Font("Dialog", 1, 12));
    l10.setText("Subscription's allocation");
    jPanelSubscriptions.add(l10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, -1));

    InterestDiffusion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "false", "true" }));
    InterestDiffusion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        InterestDiffusionActionPerformed(evt);
      }
    });
    jPanelSubscriptions.add(InterestDiffusion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 70, -1));

    l11.setText("smoothing Proba [0,1[");
    jPanelSubscriptions.add(l11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 140, -1));

    smoothingP.setText("1");
    jPanelSubscriptions.add(smoothingP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 50, -1));

    jScrollregioRatioTable.setOpaque(false);

    regioRatioTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {"Ratio (1-9)", new Integer(1), new Integer(1), new Integer(1)}
      },
      new String [] {
        "REGIONS", "Asia", "US", "Europe"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, true, true, true
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    regioRatioTable.setRowSelectionAllowed(false);
    regioRatioTable.setShowHorizontalLines(false);
    regioRatioTable.setSurrendersFocusOnKeystroke(true);
    regioRatioTable.getTableHeader().setReorderingAllowed(false);
    regioRatioTable.setUpdateSelectionOnSort(false);
    regioRatioTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseExited(java.awt.event.MouseEvent evt) {
        regioRatioTableMouseExited(evt);
      }
    });
    regioRatioTable.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        regioRatioTableKeyReleased(evt);
      }
    });
    jScrollregioRatioTable.setViewportView(regioRatioTable);
    regioRatioTable.getColumnModel().getColumn(0).setResizable(false);
    regioRatioTable.getColumnModel().getColumn(1).setResizable(false);
    regioRatioTable.getColumnModel().getColumn(2).setResizable(false);
    regioRatioTable.getColumnModel().getColumn(3).setResizable(false);
    regioRatioTable.getAccessibleContext().setAccessibleParent(jPanelSubscriptions);

    jPanelSubscriptions.add(jScrollregioRatioTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 270, 40));

    regionRatioModeList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "none", "modifyRatio" }));
    regionRatioModeList.setDoubleBuffered(true);
    regionRatioModeList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        regionRatioModeListActionPerformed(evt);
      }
    });
    jPanelSubscriptions.add(regionRatioModeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 110, -1));

    l14.setFont(new java.awt.Font("Dialog", 1, 12));
    l14.setText("Interest diffusion :");
    jPanelSubscriptions.add(l14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 180, -1));

    jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
    jPanelSubscriptions.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 155, 30, 75));

    YgenPanel.add(jPanelSubscriptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 2, 437, 238));

    yuGenStetting.add(YgenPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 735, 270));

    GeneralSetting.addTab("Initial settings", null, yuGenStetting, "Setting the YuGen's configuration");

    benchmarkSetting.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    BenchPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanelBenchmark.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));
    jPanelBenchmark.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    l5.setFont(new java.awt.Font("Dialog", 1, 12));
    l5.setText("Number of events (>0)");
    jPanelBenchmark.add(l5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, -1));

    label5.setFont(new java.awt.Font("Dialog", 1, 12));
    label5.setText("Accuracy od shapes");
    jPanelBenchmark.add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, -1));

    accuracyDegreeList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10%", "5%" }));
    accuracyDegreeList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        accuracyDegreeListActionPerformed(evt);
      }
    });
    jPanelBenchmark.add(accuracyDegreeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 50, -1));

    eventsNum.setText("1000");
    jPanelBenchmark.add(eventsNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 90, -1));

    label1.setFont(new java.awt.Font("Dialog", 1, 12));
    label1.setText("Space display parameters ");
    jPanelBenchmark.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

    l20.setText("Event space (Zoom)");
    jPanelBenchmark.add(l20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

    l21.setText("Network space (Zoom)");
    jPanelBenchmark.add(l21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

    eventScaleList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
    eventScaleList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        eventScaleListActionPerformed(evt);
      }
    });
    jPanelBenchmark.add(eventScaleList, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 50, -1));

    networkScaleList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
    networkScaleList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        networkScaleListActionPerformed(evt);
      }
    });
    jPanelBenchmark.add(networkScaleList, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 50, -1));

    BenchPanel.add(jPanelBenchmark, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 733, 220));

    benchmarkSetting.add(BenchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 735, 240));

    panel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

    benchConfigModify.setEnabled(false);
    benchConfigModify.setLabel("Edit Settings");
    benchConfigModify.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        benchConfigModifyActionPerformed(evt);
      }
    });
    panel1.add(benchConfigModify);

    benchConfigDone.setEnabled(false);
    benchConfigDone.setLabel("Done");
    benchConfigDone.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        benchConfigDoneActionPerformed(evt);
      }
    });
    panel1.add(benchConfigDone);

    benchmarkSetting.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 735, 35));

    GeneralSetting.addTab("Additional settings", null, benchmarkSetting, "Setting the benchmark's configuration");

    defaultSettings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    l15.setFont(new java.awt.Font("Dialog", 1, 12));
    l15.setText("Events Dimentions");

    textField1.setBackground(new java.awt.Color(255, 255, 255));
    textField1.setEditable(false);
    textField1.setEnabled(false);
    textField1.setText("2");

    l16.setFont(new java.awt.Font("Dialog", 1, 12));
    l16.setText("Network Dimentions");

    textField2.setBackground(new java.awt.Color(255, 255, 255));
    textField2.setEditable(false);
    textField2.setEnabled(false);
    textField2.setText("2");

    l18.setFont(new java.awt.Font("Dialog", 1, 12));
    l18.setText("Shift the boundaries of subscriptions");

    textField4.setBackground(new java.awt.Color(255, 255, 255));
    textField4.setEditable(false);
    textField4.setEnabled(false);
    textField4.setText("true");

    l19.setFont(new java.awt.Font("Dialog", 1, 12));
    l19.setText("Iiterations to reduce data skewness");

    textField5.setBackground(new java.awt.Color(255, 255, 255));
    textField5.setEditable(false);
    textField5.setEnabled(false);
    textField5.setText("0");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(l15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(l16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
              .addComponent(l19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
              .addComponent(l18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(textField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
        .addContainerGap(472, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(l15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(l16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(40, 40, 40)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(textField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(l18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(l19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(90, Short.MAX_VALUE))
    );

    defaultSettings.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 735, 240));

    GeneralSetting.addTab("Default settings", defaultSettings);

    getContentPane().add(GeneralSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 742, 300));
    GeneralSetting.getAccessibleContext().setAccessibleName("");

    jPanelMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
    jPanelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    yugenStart.setEnabled(false);
    yugenStart.setLabel("(1) Initialize distributions");
    yugenStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        yugenStartActionPerformed(evt);
      }
    });
    jPanelMenu.add(yugenStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 230, -1));

    benchStart.setEnabled(false);
    benchStart.setLabel("(2) Generate distributions");
    benchStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        benchStartActionPerformed(evt);
      }
    });
    jPanelMenu.add(benchStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 5, 230, -1));

    jScrollPane1.setAutoscrolls(true);

    outputMessage.setFont(new java.awt.Font("Tahoma", 0, 10));
    outputMessage.setText("run:");
    jScrollPane1.setViewportView(outputMessage);

    jPanelMenu.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 35, 620, 70));

    eventSpace.setActionCommand("eventSpaceDisplay");
    eventSpace.setBackground(new java.awt.Color(153, 204, 255));
    eventSpace.setFont(new java.awt.Font("Dialog", 0, 10));
    eventSpace.setLabel("Event Space");
    eventSpace.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        eventSpaceActionPerformed(evt);
      }
    });
    jPanelMenu.add(eventSpace, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 5, 100, -1));

    networkSpace.setActionCommand("networkSpaceDisplay");
    networkSpace.setBackground(new java.awt.Color(255, 255, 153));
    networkSpace.setFont(new java.awt.Font("Dialog", 0, 10));
    networkSpace.setLabel("Network Space");
    networkSpace.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        networkSpaceActionPerformed(evt);
      }
    });
    jPanelMenu.add(networkSpace, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 35, 100, -1));

    benchplot.setActionCommand("benchplot");
    benchplot.setFont(new java.awt.Font("Dialog", 1, 12));
    benchplot.setLabel("Plot Shapes");
    benchplot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        benchplotActionPerformed(evt);
      }
    });
    jPanelMenu.add(benchplot, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, 100, -1));

    getContentPane().add(jPanelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 345, 740, 110));

    jMenu6.setText("File");

    loadSenarioSetting.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
    loadSenarioSetting.setText("Load Setting");
    loadSenarioSetting.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        loadSenarioSettingActionPerformed(evt);
      }
    });
    jMenu6.add(loadSenarioSetting);
    loadSenarioSetting.getAccessibleContext().setAccessibleParent(jMenuBar);

    saveSenarioSetting.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
    saveSenarioSetting.setText("Save Setting");
    saveSenarioSetting.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveSenarioSettingActionPerformed(evt);
      }
    });
    jMenu6.add(saveSenarioSetting);
    saveSenarioSetting.getAccessibleContext().setAccessibleParent(jMenuBar);

    jMenuBar.add(jMenu6);

    jMenu1.setText("About");
    jMenu1.setActionCommand("About");

    jMenuItem1.setText("Credit");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar.add(jMenu1);

    setJMenuBar(jMenuBar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void yugenConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yugenConfigActionPerformed
      this.validate();
      
      this.YgenPanel.enable(true);
      this.YgenPanel.validate();
      this.yugenStart.setBackground(Color.LIGHT_GRAY);

      this.BenchPanel.enable(false);

      this.yuGenConfigDone.enable(true);
      this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();

      this.YgenPanel.validate();
      this.validate();
    }//GEN-LAST:event_yugenConfigActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
      System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void yugenStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yugenStartActionPerformed
      long time1;
      long time2;
      long h,m,s;
      long sec;

      this.validate();
   
      this.YgenPanel.enable(false);
      this.BenchPanel.enable(false);
      this.yugenStart.enable(false);
      this.yugenStart.setBackground(Color.LIGHT_GRAY);

      if (this.noError){
       reconstruction.Config.NUM_OF_SUBS = Integer.parseInt(this.subNum.getText());

       reconstruction.Config.EVENT_SPACE_MIN = (-1) * Integer.parseInt(this.eventSpaceMinMax.getText())/2;
       reconstruction.Config.EVENT_SPACE_MAX = Integer.parseInt(this.eventSpaceMinMax.getText())/2;
       
       reconstruction.Config.NUM_OF_BROKERS[0] = Integer.parseInt(this.brokersTable.getValueAt(0,1).toString());
       reconstruction.Config.NUM_OF_BROKERS[1] = Integer.parseInt(this.brokersTable.getValueAt(0,2).toString());
       reconstruction.Config.NUM_OF_BROKERS[2] = Integer.parseInt(this.brokersTable.getValueAt(0,3).toString());
       
       reconstruction.Config.PARTITION_N = Integer.parseInt(this.part_N.getText());
       
       String kfunc = this.kFunc.getItemAt(this.kFunc.getSelectedIndex()).toString();
       if ( kfunc.equals("epanechnikov")) reconstruction.Config.KERNEL_FUNC = reconstruction.Config.KERNEL_FUNC.epanechnikov;
       if ( kfunc.equals("gaussian")) reconstruction.Config.KERNEL_FUNC = reconstruction.Config.KERNEL_FUNC.gaussian;
       if ( kfunc.equals("triangular")) reconstruction.Config.KERNEL_FUNC = reconstruction.Config.KERNEL_FUNC.triangular;
       if ( kfunc.equals("tricube")) reconstruction.Config.KERNEL_FUNC = reconstruction.Config.KERNEL_FUNC.tricube;
       if ( kfunc.equals("uniform")) reconstruction.Config.KERNEL_FUNC = reconstruction.Config.KERNEL_FUNC.uniform;

       reconstruction.Config.BANDWIDTH = Integer.parseInt(this.bandwidth.getText());
       
      String H_mode = this.modeList.getItemAt(this.modeList.getSelectedIndex()).toString();
       if ( H_mode.equals("none")){
         reconstruction.Config.GROUP_HIERARCHY_MODE = reconstruction.Param.InterestGeneralizationMode.None;
       }
       if ( H_mode.equals("uniform")){
         reconstruction.Config.GROUP_HIERARCHY_MODE = reconstruction.Param.InterestGeneralizationMode.Uniform;
         reconstruction.Config.HIERARCHY_FRAC = Double.parseDouble(this.h_Frac.getText());
       }
       if ( H_mode.equals("proportional")){
         reconstruction.Config.GROUP_HIERARCHY_MODE = reconstruction.Param.InterestGeneralizationMode.Proportional;
         reconstruction.Config.HIERARCHY_FRAC = Double.parseDouble(this.h_Frac.getText());
        
       }

      String Hot_remove = this.hotIInterestRemove.getItemAt(this.hotIInterestRemove.getSelectedIndex()).toString();
       if ( Hot_remove.equals("false")){
         reconstruction.Config.PRUNE_HOT_INTERESTS = false;
       }else {
        reconstruction.Config.PRUNE_HOT_INTERESTS = true;
        reconstruction.Config.NUM_OF_HOT_INTERESTS = Integer.parseInt(this.hot_Remove.getText());
       }

      String Int_diffusion = this.InterestDiffusion.getItemAt(this.InterestDiffusion.getSelectedIndex()).toString();
       if ( Int_diffusion.equals("false")){
         reconstruction.Config.INTEREST_DIFFUSION = false;
       }else {
        reconstruction.Config.INTEREST_DIFFUSION = true;
        reconstruction.Config.SMOOTHING_P = Double.parseDouble(this.smoothingP.getText());
       }

      String ratio_mode = this.regionRatioModeList.getItemAt(this.regionRatioModeList.getSelectedIndex()).toString();
       if ( ratio_mode.equals("none")){
         reconstruction.Config.SUBS_RATIO_MODE = reconstruction.Param.SubsRatioMode.None;
       }else {
        reconstruction.Config.SUBS_RATIO_MODE = reconstruction.Param.SubsRatioMode.ModifyRatio;
        reconstruction.Config.REGION_RATIO[0] = Double.parseDouble(this.regioRatioTable.getValueAt(0,1).toString()) / 9.;
        reconstruction.Config.REGION_RATIO[1] = Double.parseDouble(this.regioRatioTable.getValueAt(0,2).toString()) / 9.;
        reconstruction.Config.REGION_RATIO[2] = Double.parseDouble(this.regioRatioTable.getValueAt(0,3).toString()) / 9.;
       }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        
        this.yugenStart.setBackground(Color.RED);
        this.yugenConfig.enable(false);
        this.yuGenConfigDone.enable(false);
        this.benchConfigDone.enable(false);
        time1 = System.currentTimeMillis();
        reconstruction.DataGenerator dataGenerator = new reconstruction.DataGenerator();
        dataGenerator.execute();
        time2 = System.currentTimeMillis();
        setCursor(Cursor.getDefaultCursor());
        this.yugenStart.setBackground(Color.LIGHT_GRAY);
        this.yugenConfig.enable(true);

        sec = (time2-time1)/1000;
		h = sec / 3600;
		sec = sec % 3600;
		m = sec / 60;
		s = sec % 60;

      Benchmark.outPutMessages = "YuGen Generation time : ("+(time2-time1)/1000+") "+ h+"h"+m+"m"+s+"s\n";
      outputMessage.setText(outputMessage.getText()+Benchmark.outPutMessages);
       
       this.benchStart.enable(true);
       this.benchStart.setBackground(Color.GREEN);
     
     }else{
        this.yugenConfig.enable(true);
        this.yugenStart.enable(false);
        this.yugenStart.setBackground(Color.LIGHT_GRAY);
        this.noError = true;
     }
    this.validate();
    }//GEN-LAST:event_yugenStartActionPerformed

    private void benchStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benchStartActionPerformed
      long time1;
      long time2;
      long h,m,s;
      long sec;
      //boolean noError = true;
      this.validate();
      this.YgenPanel.enable(false);
      this.BenchPanel.enable(false);

      if (this.noError){
      if (Benchmark.yuGenIsDone){
        Benchmark.TOTAL_EVENTS = Integer.parseInt(this.eventsNum.getText());
        
        int sommeBrokers = Integer.parseInt(this.brokersTable.getValueAt(0,1).toString())+
              Integer.parseInt(this.brokersTable.getValueAt(0,2).toString())+
              Integer.parseInt(this.brokersTable.getValueAt(0,3).toString());
        
        Benchmark.experienceName = mode+""+this.h_Frac.getText();
        Benchmark.experienceName +="-e"+this.eventsNum.getText();
        Benchmark.experienceName +="-s"+this.subNum.getText();
        Benchmark.experienceName +="-b"+sommeBrokers;
        Benchmark.experienceName +="-"+this.kFunc.getItemAt(this.kFunc.getSelectedIndex()).toString();
        Benchmark.experienceName +="-bw"+this.bandwidth.getText();
        Benchmark.experienceName +="-pn"+this.part_N.getText();
        Benchmark.experienceName +="-hr"+this.hot_Remove.getText();
        Benchmark.experienceName +="-sp"+this.smoothingP.getText();


        Benchmark.EVENTS_DENSITY_OF_RECIPIENT_FILE =         "outputs/benchmark/"+Benchmark.experienceName+"-EventsDR.txt";
        Benchmark.EVENTS_MATCHING_DISTRIBUTION_FILE =        "outputs/benchmark/"+Benchmark.experienceName+"-EventsMD.txt";
        Benchmark.CELLS_MATCHING_DISTRIBUTION_FILE =         "outputs/benchmark/"+Benchmark.experienceName+"-CellsMD.txt";
        Benchmark.CELLS_DENSITY_OF_RECIPIENT_FILE =          "outputs/benchmark/"+Benchmark.experienceName+"-CellsDR.txt";
        Benchmark.EVENTS_SERVED_BROKERS_STATISTIC_FILE =      "outputs/benchmark/"+Benchmark.experienceName+"-EventServedBrokers.txt";
        Benchmark.EVENTS_MATCHED_SUBS_STATISTIC_FILE =     "outputs/benchmark/"+Benchmark.experienceName+"-EventMatchedSubs.txt";

        
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        
        this.benchStart.setBackground(Color.RED);
        this.yugenConfig.enable(false);
        this.yuGenConfigDone.enable(false);
        this.benchConfigDone.enable(false);
        time1 = System.currentTimeMillis();
        Benchmark benchmarking = new Benchmark();
        benchmarking.execute();
        time2 = System.currentTimeMillis();
        setCursor(Cursor.getDefaultCursor());
        this.benchStart.setBackground(Color.LIGHT_GRAY);
        this.yugenConfig.enable(true);
        sec = (time2-time1)/1000;
		h = sec / 3600;
		sec = sec % 3600;
		m = sec / 60;
		s = sec % 60;
          
      Benchmark.outPutMessages = "Generation time : ("+(time2-time1)/1000+") "+ h+"h"+m+"m"+s+"s\n";
      outputMessage.setText(outputMessage.getText()+Benchmark.outPutMessages);
          
      }else {
        Benchmark.outPutMessages = "Wait until YuGen is done .........\n";
        outputMessage.setText(outputMessage.getText()+Benchmark.outPutMessages);
      }
      this.eventSpace.enable(true);
      this.networkSpace.enable(true);
      this.benchplot.enable(true);
      this.benchStart.enable(false);
      this.validate();

      }else{
        this.yuGenConfigDone.enable(false);
        this.benchStart.enable(false);
        this.benchStart.setBackground(Color.LIGHT_GRAY);
        this.noError = true;
      }
      this.validate();

    }//GEN-LAST:event_benchStartActionPerformed

    private void modeListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeListActionPerformed
      // TODO add your handling code here:
      this.validate();
      this.YgenPanel.validate();
      this.jPanelSubscriptions.validate();
      if (this.modeList.getSelectedItem().equals("none")){
        this.h_Frac.setText("0");
        this.mode ="N";
        this.h_Frac.enable(false);

      }else{
        this.h_Frac.setText("0.9");
       this.h_Frac.enable(true);
        if (this.modeList.getSelectedItem().equals("uniform"))
          this.mode = "U";
        else
          this.mode = "P";
      }
      this.YgenPanel.validate();
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_modeListActionPerformed

    private void eventSpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventSpaceActionPerformed
      // TODO add your handling code here:
      Benchmark.eventSpaceDisplay = new EventSpacePlotting(Benchmark.gridCellList, Benchmark.eventsListIsUpDate, Benchmark.subsList);
      Benchmark.eventSpaceDisplay.setTitle("(Event space) "+Benchmark.experienceName);
      Benchmark.eventSpaceDisplay.setVisible(true);
      Benchmark.eventSpaceDisplay.validate();
      Benchmark.eventSpaceDisplay.repaint();
      
      this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();
      
      this.validate();
    }//GEN-LAST:event_eventSpaceActionPerformed

    private void benchplotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benchplotActionPerformed
      String Titre="";
      String Label="";
      eYuGenPlot bPlot;
      
      if (this.evaluateParam.getSelectedItem().equals("#Events")){
        Titre = "Event's number";
        Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("#Subscriptions")){
        Titre = "Subscription's number";
        Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("#Brokers")){
      Titre = "Broker's number";
      Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("#cells")){
      Titre = "Cell's number";
      Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("Kernel function")){
      Titre = "kernel Function";
      Label = "["+this.modeList.getSelectedItem().toString()+"] [kernel:"+this.kFunc.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("Bandwidth")){
      Titre = "Kernel Bandwidth";
      Label = "["+this.modeList.getSelectedItem().toString()+"] [kernel:"+this.kFunc.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("Hierarchy fraction")){
      Titre = "Hierarchy fraction";
      Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("Interest removal")){
      Titre = "Hot interests removal";
      Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }else
      if (this.evaluateParam.getSelectedItem().equals("Smoothing probability")){
      Titre = "Smoothing Probability";
      Label = "["+this.modeList.getSelectedItem().toString()+"]";
      }

      bPlot = new eYuGenPlot(Benchmark.EVENTS_MATCHING_DISTRIBUTION_FILE,
              Benchmark.EVENTS_DENSITY_OF_RECIPIENT_FILE,
              Titre, Label);
      bPlot.setVisible(true);
      this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_benchplotActionPerformed

    private void networkSpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networkSpaceActionPerformed
      // TODO add your handling code here:

      Benchmark.networktSpaceDisplay = new NetworkSpacePlotting(Benchmark.subsList, Benchmark.brokersList, Benchmark.membershipList);
      Benchmark.networktSpaceDisplay.setTitle("(Network space) "+Benchmark.experienceName);
      Benchmark.networktSpaceDisplay.setVisible(true);
      Benchmark.networktSpaceDisplay.validate();
      Benchmark.networktSpaceDisplay.repaint();

      
      this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_networkSpaceActionPerformed

    private void InterestDiffusionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InterestDiffusionActionPerformed
      // TODO add your handling code here:
      this.validate();
      this.jPanelSubscriptions.validate();
      this.YgenPanel.validate();
      if (this.InterestDiffusion.getSelectedItem().equals("false")){
        this.smoothingP.setText("0.0");
        this.smoothingP.enable(false);
      }else{
        this.smoothingP.setText("0.0");
        this.smoothingP.enable(true);
      }
      this.YgenPanel.validate();
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_InterestDiffusionActionPerformed

    private void hotIInterestRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotIInterestRemoveActionPerformed
      // TODO add your handling code here:
      this.validate();
      this.jPanelSubscriptions.validate();
      this.YgenPanel.validate();
      if(this.hotIInterestRemove.getSelectedItem().equals("false")){
        this.hot_Remove.setText("0");
        this.hot_Remove.enable(false);
      }else{
        this.hot_Remove.setText("0");
        this.hot_Remove.enable(true);
      }
      this.YgenPanel.validate();
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_hotIInterestRemoveActionPerformed

    private void evaluateParamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_evaluateParamFocusGained
      // TODO add your handling code here:
  
    }//GEN-LAST:event_evaluateParamFocusGained

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
      // TODO add your handling code here:
       AboutBox abox = new AboutBox(this);
       abox.setVisible(true);
       this.validate();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void accuracyDegreeListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accuracyDegreeListActionPerformed
      // TODO add your handling code here:
      this.validate();
      this.BenchPanel.validate();
      if(this.accuracyDegreeList.getSelectedIndex()==0){
        Benchmark.accuracy = 1;
      }else{
        Benchmark.accuracy = 2;
      }
      this.BenchPanel.validate();
      this.validate();
    }//GEN-LAST:event_accuracyDegreeListActionPerformed

    private void benchConfigDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benchConfigDoneActionPerformed
      // TODO add your handling code here:
      this.BenchPanel.enable(false);
      this.benchConfigDone.enable(false);

      this.eventSpace.enable(false);
      this.networkSpace.enable(false);
      this.benchplot.enable(false);
        
      Alert alertDialogEvents = new Alert(new javax.swing.JFrame(), true);

      if ( this.eventsNum.getText().isEmpty() ||
              Integer.parseInt(this.eventsNum.getText().toString()) < 1 ){
          this.eventsNum.setText("1");
          alertDialogEvents.setTitle("Alert in Benchmark (#Event)");
          alertDialogEvents.setVisible(true);
          this.noError = false;
      }
      else{
        this.noError = true;
        if (Benchmark.yuGenIsDone){
           this.benchStart.enable(true);
           this.benchStart.setBackground(Color.GREEN);
        }
      }

      
      this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();
    }//GEN-LAST:event_benchConfigDoneActionPerformed

    private void benchConfigModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benchConfigModifyActionPerformed
      // TODO add your handling code here:
       this.BenchPanel.enable(true);
       this.benchConfigDone.enable(true);
       this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();
    }//GEN-LAST:event_benchConfigModifyActionPerformed

    private void yuGenConfigDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yuGenConfigDoneActionPerformed
      // TODO add your handling code here:
      Alert alertDialogSmoothingP = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogHFrac = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogBandwidth = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogHotRemove = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogSubs = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogBrokers = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogCells = new Alert(new javax.swing.JFrame(), true);
      Alert alertDialogEventSpace = new Alert(new javax.swing.JFrame(), true);


      
      this.yuGenConfigDone.enable(false);
      this.YgenPanel.enable(false);
      this.yugenStart.setBackground(Color.GREEN);
      this.yugenStart.enable(true);

      this.benchConfigModify.enable(true);
      this.benchStart.enable(false);
      this.benchStart.setBackground(Color.LIGHT_GRAY);
      this.BenchPanel.enable(false);


      this.eventSpace.enable(false);
      this.networkSpace.enable(false);
      this.benchplot.enable(false);

       if (this.InterestDiffusion.getSelectedIndex()!= 0){
        if ( this.smoothingP.getText().isEmpty() ||
                Double.parseDouble(this.smoothingP.getText().toString()) >= 1.0 ||
                Double.parseDouble(this.smoothingP.getText().toString()) < 0.0 ){
            this.smoothingP.setText("0.0");
            alertDialogSmoothingP.setTitle("Alert in YuGen (smoothing probability)");
            alertDialogSmoothingP.setVisible(true);
            this.noError = false;
        }
      }else this.smoothingP.setText("1");

      if(this.modeList.getSelectedIndex()!= 0){
        if ( this.h_Frac.getText().isEmpty() ||
                Double.parseDouble(this.h_Frac.getText().toString()) > 1.0 ||
                Double.parseDouble(this.h_Frac.getText().toString()) <= 0.0 ){
            this.h_Frac.setText("0.1");
            alertDialogHFrac.setTitle("Alert in YuGen (hierarchy fraction)");
            alertDialogHFrac.setVisible(true);
            this.noError = false;
        }
      }else  this.h_Frac.setText("0");

      if ( this.bandwidth.getText().isEmpty() || Integer.parseInt(this.bandwidth.getText().toString()) <= 0 ){
          this.bandwidth.setText("1");
          alertDialogBandwidth.setTitle("Alert in YuGen (bandwidth)");
          alertDialogBandwidth.setVisible(true);
          this.noError = false;
      }
      if ( this.hot_Remove.getText().isEmpty() ||
              Integer.parseInt(this.hot_Remove.getText().toString()) < 0 ||
              Integer.parseInt(this.hot_Remove.getText().toString()) > 4000){
          this.hot_Remove.setText("0");
          alertDialogHotRemove.setTitle("Alert in YuGen (#hot interests removal)");
          alertDialogHotRemove.setVisible(true);
          this.noError = false;
      }

      int sommeBrokers = 0;
      for (int i=1; i<4; i++){
        if (this.brokersTable.getValueAt(0, i) == null || Integer.parseInt(this.brokersTable.getValueAt(0, i).toString()) < 1 ){
          this.brokersTable.setValueAt(1, 0, i);
            alertDialogBrokers.setTitle("Alert in YuGen (Number of brokers at region "+i+")");
            alertDialogBrokers.setVisible(true);
            this.noError = false;
            sommeBrokers = 0;
            break;
        }else sommeBrokers += Integer.parseInt(this.brokersTable.getValueAt(0, i).toString());
      }

      if (sommeBrokers > 0){
        if ( this.subNum.getText().isEmpty() ||
                Integer.parseInt(this.subNum.getText().toString()) < 3 ||
                Integer.parseInt(this.subNum.getText().toString()) < sommeBrokers
                ){
            this.subNum.setText("3");
            alertDialogSubs.setTitle("Alert in YuGen (Number of subscriptions)");
            alertDialogSubs.setVisible(true);
            this.noError = false;
        }
      }
      
      if ( this.part_N.getText().isEmpty() ||
              Integer.parseInt(this.part_N.getText().toString()) < 1 ){
          this.part_N.setText("1");
          alertDialogCells.setTitle("Alert in YuGen (Number of cells)");
          alertDialogCells.setVisible(true);
          this.noError = false;
      }
      if ( this.eventSpaceMinMax.getText().isEmpty() ||
              Integer.parseInt(this.eventSpaceMinMax.getText().toString()) < 1 ){
          this.eventSpaceMinMax.setText("400");
          alertDialogEventSpace.setTitle("Alert in Benchmark (#Event Space Range)");
          alertDialogEventSpace.setVisible(true);
          this.noError = false;
      }
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_yuGenConfigDoneActionPerformed

    private void regionRatioModeListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regionRatioModeListActionPerformed
      // TODO add your handling code here:
      
      this.jPanelSubscriptions.validate();
      if (this.regionRatioModeList.getSelectedIndex()==0) this.jScrollregioRatioTable.setVisible(false);
      else  this.jScrollregioRatioTable.setVisible(true);
      this.regionRatioModeList.validate();
      this.jPanelSubscriptions.validate();
      this.jPanelMenu.validate();
      this.validate();
    }//GEN-LAST:event_regionRatioModeListActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
      // TODO add your handling code here:
      this.invalidate();
      this.jPanelBrokers.validate();
      this.jPanelBenchmark.validate();
      this.jPanelMenu.validate();
      this.jPanelSubscriptions.validate();
      this.validate();
    }//GEN-LAST:event_formWindowGainedFocus

    private void eventScaleListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventScaleListActionPerformed
      this.validate();
      this.BenchPanel.validate();
      this.jPanelBenchmark.validate();
      Benchmark.eventScale = Integer.parseInt(this.eventScaleList.getSelectedItem().toString());
      this.BenchPanel.validate();
      this.jPanelBenchmark.validate();
      this.validate();
    }//GEN-LAST:event_eventScaleListActionPerformed

    private void networkScaleListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networkScaleListActionPerformed
      // TODO add your handling code here:
      this.validate();
      this.BenchPanel.validate();
      this.jPanelBenchmark.validate();
      Benchmark.networkScale = Integer.parseInt(this.networkScaleList.getSelectedItem().toString());
      this.jPanelBenchmark.validate();
      this.BenchPanel.validate();

      this.validate();
    }//GEN-LAST:event_networkScaleListActionPerformed

    private void kFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kFuncActionPerformed
      // TODO add your handling code here:
      this.jPanelEvents.validate();
      this.validate();
    }//GEN-LAST:event_kFuncActionPerformed

    private void regioRatioTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_regioRatioTableKeyReleased
      // TODO add your handling code here:
      this.jPanelSubscriptions.validate();
      this.jPanelBenchmark.validate();
      this.jPanelBrokers.validate();
      this.validate();
    }//GEN-LAST:event_regioRatioTableKeyReleased

    private void regioRatioTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regioRatioTableMouseExited
      // TODO add your handling code here:
      this.jPanelSubscriptions.validate();
      this.jPanelBenchmark.validate();
      this.jPanelBrokers.validate();
      this.validate();
    }//GEN-LAST:event_regioRatioTableMouseExited

    private void brokersTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brokersTableMouseExited
      // TODO add your handling code here:
      this.jPanelSubscriptions.validate();
      this.jPanelBenchmark.validate();
      this.jPanelBrokers.validate();
      this.validate();
    }//GEN-LAST:event_brokersTableMouseExited

    private void brokersTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_brokersTableKeyReleased
      // TODO add your handling code here:
      this.jPanelSubscriptions.validate();
      this.jPanelBenchmark.validate();
      this.jPanelBrokers.validate();
      this.validate();
    }//GEN-LAST:event_brokersTableKeyReleased

    private void saveSenarioSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSenarioSettingActionPerformed
      //create a new config file config-XXXXXX.in
      String path;
      JFileChooser choose = new JFileChooser();
      choose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      if (choose.showDialog(this, "Save To")== JFileChooser.CANCEL_OPTION)
        return;
      else{
        path = choose.getSelectedFile().getPath();
      }


        nameInputFile = path+"-config.in";
        try{
            File in = new File(nameInputFile);
            if (in.exists()) in.delete();
            File inputConfigFile = new File(nameInputFile);
            FileWriter fw = new FileWriter(inputConfigFile, true);
            BufferedWriter  out = new BufferedWriter(fw);
            this.inputConfigFileExist = true;
           
        out.write("#########################################\n#     Scenario's Configuration File     #\n#########################################\n\n");
        out.write("#Initial Settings\n");
        out.write("NUM_OF_REGIONS 3\n");
        out.write("EVENT_DIM 2\n");
        out.write("NETWORK_DIM 2\n");
        out.write("Param.Region Asia US Europe\n");
        out.write("\n#Brokers parameters\n");
            out.write("NUM_OF_BROKERS "+this.brokersTable.getValueAt(0,1).toString()+" "+
                    this.brokersTable.getValueAt(0,2).toString()+" "+
                    this.brokersTable.getValueAt(0,3).toString() +"\n");
            
        out.write("\n#Events parameters\n");
            out.write("TOTAL_EVENTS "+this.eventsNum.getText().toString()+"\n");
            out.write("PARTITION_N "+this.part_N.getText().toString()+"\n");
            out.write("EVENT_SPACE_MIN "+(0-(Integer.parseInt(this.eventSpaceMinMax.getText().toString())/2))+"\n");
            out.write("EVENT_SPACE_MAX "+Integer.parseInt(this.eventSpaceMinMax.getText().toString())/2+"\n");
            out.write("KERNEL_FUNC "+this.kFunc.getSelectedItem().toString()+"\n");
            out.write("BANDWIDTH "+this.bandwidth.getText().toString()+"\n");

        out.write("\n#Subscriptions parameters\n");
            out.write("NUM_OF_SUBS "+this.subNum.getText().toString()+"\n");
            out.write("SUBS_RATIO_MODE "+this.regionRatioModeList.getSelectedItem().toString()+"\n");
                    if(this.regionRatioModeList.getSelectedIndex()!= 0)
                      out.write("REGION_RATIO "+this.regioRatioTable.getValueAt(0,1).toString()+" "+
                      this.regioRatioTable.getValueAt(0,2).toString()+" "+
                      this.regioRatioTable.getValueAt(0,3).toString()+"\n");
            out.write("GROUP_HIERARCHY_MODE "+this.modeList.getSelectedItem().toString()+"\n");
                if(this.modeList.getSelectedIndex()!= 0) out.write("HIERARCHY_FRAC "+this.h_Frac.getText().toString()+"\n");
            out.write("INTEREST_DIFFUSION "+this.InterestDiffusion.getSelectedItem().toString()+"\n");
                if(this.InterestDiffusion.getSelectedIndex()!= 0) out.write("SMOOTHING_P "+this.smoothingP.getText().toString()+"\n");
            out.write("PRUNE_HOT_INTERESTS "+this.hotIInterestRemove.getSelectedItem().toString()+"\n");
                if(this.hotIInterestRemove.getSelectedIndex()!= 0) out.write("NUM_OF_HOT_INTERESTS "+this.hot_Remove.getText().toString()+"\n");

       
       Date uDate = new Date(System.currentTimeMillis());
        out.write("\n#File Created [ "+ uDate.toString()+ " ] \n");
        
        out.close();
        fw.close();
        }catch (IOException ex) {System.err.println(ex);}
        System.out.println("The scenario's configuration file '"+ this.nameInputFile +"' has been created.");
    }//GEN-LAST:event_saveSenarioSettingActionPerformed

    private void loadSenarioSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSenarioSettingActionPerformed
      // TODO add your handling code here:
      String path;
      JFileChooser choose = new JFileChooser();
      choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
      if (choose.showDialog(this, "Load Scenario")== JFileChooser.CANCEL_OPTION)
        return;
      else{
        path = choose.getSelectedFile().getPath();
      }
      nameInputFile = path;
      try{
      File inputConfigDFile = new File(nameInputFile);
      FileReader fileReader = new FileReader(inputConfigDFile);
      LineNumberReader reader = new LineNumberReader(fileReader);
      String strLine = "";
      while ((strLine = reader.readLine()) != null){
         if (strLine.length() != 0){
            if(strLine.substring(0,1).equals("#") == false){
              if (this.readItem(14, strLine, "NUM_OF_BROKERS")) {
              String brokerTab = strLine.substring(15);
               if(strLine.substring(15).length()!=0){
                 String[] tempValue = new String[3];
                 tempValue = brokerTab.split(" ");
                 for (int i=0; i<3; i++){
                     this.brokersTable.getModel().setValueAt(Integer.parseInt(tempValue[i]), 0, (i+1));
                 }
               }
              }
              if (this.readItem(12, strLine, "TOTAL_EVENTS")) {
              String eventNum = strLine.substring(13);
                 if(eventNum.length()!=0){
                    this.eventsNum.setText(eventNum);
                 }
              }
              if (this.readItem(11, strLine, "PARTITION_N")) {
              String Part_N = strLine.substring(12);
                 if(Part_N.length()!=0){
                    this.part_N.setText(Part_N);
                 }
              }
              if (this.readItem(15, strLine, "EVENT_SPACE_MAX")) {
              String eventSpaceSize = strLine.substring(16);
                 if(eventSpaceSize.length()!=0){
                   int value = Integer.parseInt(eventSpaceSize)*2;
                   this.eventSpaceMinMax.setText(Integer.toString(value));
                 }
              }
              if (this.readItem(11, strLine, "KERNEL_FUNC")) {
              String kernel_func = strLine.substring(12);
                 if(kernel_func.length()!=0){
                   this.kFunc.setSelectedItem(kernel_func);
                 }
              }
              if (this.readItem(9, strLine, "BANDWIDTH")) {
              String band = strLine.substring(10);
                 if(band.length()!=0){
                   this.bandwidth.setText(band);
                 }
              }
              if (this.readItem(11, strLine, "NUM_OF_SUBS")) {
              String subNum = strLine.substring(12);
                 if(subNum.length()!=0){
                    this.subNum.setText(subNum);
                 }
              }
              if (this.readItem(15, strLine, "SUBS_RATIO_MODE")) {
              String ratioMode = strLine.substring(16);
                 if(ratioMode.length()!=0){
                    this.regionRatioModeList.setSelectedItem(ratioMode);
                 }
              }
              if (this.readItem(12, strLine, "REGION_RATIO")) {
                String regionTab = strLine.substring(13);
                  if(regionTab.length()!=0){
                       String[] tempValue = new String[3];
                       tempValue = regionTab.split(" ");
                       for (int i=0; i<3; i++){
                           this.regioRatioTable.getModel().setValueAt(Integer.parseInt(tempValue[i]), 0, (i+1));
                       }
                  }
              }
              if (this.readItem(20, strLine, "GROUP_HIERARCHY_MODE")) {
              String hierarchyMode = strLine.substring(21);
                 if(hierarchyMode.length()!=0){
                    this.modeList.setSelectedItem(hierarchyMode);
                 }
              }
              if (this.readItem(14, strLine, "HIERARCHY_FRAC")) {
              String h_frac = strLine.substring(15);
                 if(h_frac.length()!=0){
                    this.h_Frac.setText(h_frac);
                 }
              }
              if (this.readItem(18, strLine, "INTEREST_DIFFUSION")) {
              String diffMode = strLine.substring(19);
                 if(diffMode.length()!=0){
                    this.InterestDiffusion.setSelectedItem(diffMode);
                 }
              }
              if (this.readItem(11, strLine, "SMOOTHING_P")) {
              String smoothP = strLine.substring(12);
                 if(smoothP.length()!=0){
                    this.smoothingP.setText(smoothP);
                 }
              }
              if (this.readItem(18, strLine, "PRUNE_HOT_INTERESTS")) {
              String hotInterestMode = strLine.substring(19);
                 if(hotInterestMode.length()!=0){
                    this.hotIInterestRemove.setSelectedItem(hotInterestMode);
                 }
              }
              if (this.readItem(20, strLine, "NUM_OF_HOT_INTERESTS")) {
              String numHot = strLine.substring(21);
                 if(numHot.length()!=0){
                    this.hot_Remove.setText(numHot);
                 }
              }
              
            }
         }
      }
      }catch (IOException ex) {System.err.println(ex);}

    }//GEN-LAST:event_loadSenarioSettingActionPerformed

    public boolean readItem(int position, String strSource, String strCompared){
		if (strSource.length()>= position && strSource.substring(0,position).compareTo(strCompared)==0)
			return true;
		else{
			return false;
		}
	}

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Starting().setVisible(true);
            }
        });
        
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private java.awt.Panel BenchPanel;
  private javax.swing.JTabbedPane GeneralSetting;
  private javax.swing.JComboBox InterestDiffusion;
  private java.awt.Panel YgenPanel;
  private javax.swing.JComboBox accuracyDegreeList;
  private java.awt.TextField bandwidth;
  private java.awt.Button benchConfigDone;
  private java.awt.Button benchConfigModify;
  private java.awt.Button benchStart;
  private javax.swing.JPanel benchmarkSetting;
  private java.awt.Button benchplot;
  private javax.swing.JTable brokersTable;
  private javax.swing.JPanel defaultSettings;
  private javax.swing.JComboBox evaluateParam;
  private javax.swing.JComboBox eventScaleList;
  private java.awt.Button eventSpace;
  private java.awt.TextField eventSpaceMinMax;
  private java.awt.TextField eventsNum;
  private java.awt.Button exit;
  private java.awt.TextField h_Frac;
  private javax.swing.JComboBox hotIInterestRemove;
  private java.awt.TextField hot_Remove;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenu jMenu4;
  private javax.swing.JMenu jMenu5;
  private javax.swing.JMenu jMenu6;
  private javax.swing.JMenuBar jMenuBar;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuBar jMenuBar2;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanelBenchmark;
  private javax.swing.JPanel jPanelBrokers;
  private javax.swing.JPanel jPanelEvents;
  private javax.swing.JPanel jPanelMenu;
  private javax.swing.JPanel jPanelSubscriptions;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollregioRatioTable;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JComboBox kFunc;
  private java.awt.Label l1;
  private java.awt.Label l10;
  private java.awt.Label l11;
  private java.awt.Label l12;
  private java.awt.Label l13;
  private java.awt.Label l14;
  private java.awt.Label l15;
  private java.awt.Label l16;
  private java.awt.Label l17;
  private java.awt.Label l18;
  private java.awt.Label l19;
  private java.awt.Label l2;
  private java.awt.Label l20;
  private java.awt.Label l21;
  private java.awt.Label l3;
  private java.awt.Label l4;
  private java.awt.Label l5;
  private java.awt.Label l6;
  private java.awt.Label l7;
  private java.awt.Label l8;
  private java.awt.Label l9;
  private java.awt.Label label1;
  private java.awt.Label label5;
  private javax.swing.JMenuItem loadSenarioSetting;
  private javax.swing.JComboBox modeList;
  private javax.swing.JComboBox networkScaleList;
  private java.awt.Button networkSpace;
  public static javax.swing.JTextPane outputMessage;
  private java.awt.Panel panel1;
  private java.awt.Panel panel2;
  private java.awt.TextField part_N;
  private javax.swing.JTable regioRatioTable;
  private javax.swing.JComboBox regionRatioModeList;
  private javax.swing.JMenuItem saveSenarioSetting;
  private java.awt.TextField smoothingP;
  private java.awt.TextField subNum;
  private java.awt.TextField textField1;
  private java.awt.TextField textField2;
  private java.awt.TextField textField4;
  private java.awt.TextField textField5;
  private java.awt.Button yuGenConfigDone;
  private javax.swing.JPanel yuGenStetting;
  private java.awt.Button yugenConfig;
  private java.awt.Button yugenStart;
  // End of variables declaration//GEN-END:variables

}
