package it.eng.areas.hybrid.module.cryptoLight.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SmartCardCheckGUI extends JFrame implements ITrace {

  private static final long serialVersionUID = 1L;
  private JPanel jContentPane = null;
  private JPanel loginPanel = null;
  private JPanel bottomPanel = null;
  private JButton exitButton = null;
  private JScrollPane logScrollPane = null;
  private JTextPane logTextPane = null;
  private JPasswordField pinField = null;
  private JLabel pinLabel = null;
  private JButton pinButton = null;
  private JCheckBox logfileCheckBox = null;
  
  private FileOutputStream logStream;
  
  /**
   * This is the default constructor
   */
  public SmartCardCheckGUI() {
    super();
    initialize();
  }
  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize() {
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(getJContentPane());
    this.setTitle("Verifica SmartCard");
  }
  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new JPanel();
      jContentPane.setLayout(new BorderLayout());
      jContentPane.add(getLoginPanel(), BorderLayout.NORTH);
      jContentPane.add(getBottomPanel(), BorderLayout.SOUTH);
      jContentPane.add(getLogScrollPane(), BorderLayout.CENTER);
    }
    return jContentPane;
  }
  /**
   * This method initializes loginPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getLoginPanel() {
    if (loginPanel == null) {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.LEFT);
      pinLabel = new JLabel();
      pinLabel.setText("Pin:");
      loginPanel = new JPanel();
      loginPanel.setLayout(flowLayout);
      loginPanel.setPreferredSize(new Dimension(0, 40));
      loginPanel.add(pinLabel, null);
      loginPanel.add(getPinField(), null);
      loginPanel.add(getPinButton(), null);
      loginPanel.add(getLogfileCheckBox(), null);
    }
    return loginPanel;
  }
  /**
   * This method initializes bottomPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getBottomPanel() {
    if (bottomPanel == null) {
      bottomPanel = new JPanel();
      bottomPanel.setLayout(new FlowLayout());
      bottomPanel.setPreferredSize(new Dimension(0, 36));
      bottomPanel.add(getExitButton(), null);
    }
    return bottomPanel;
  }
  /**
   * This method initializes exitButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getExitButton() {
    if (exitButton == null) {
      exitButton = new JButton();
      exitButton.setText("Esci");
      exitButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          System.exit(0);
        }
      });
    }
    return exitButton;
  }
  /**
   * This method initializes logScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getLogScrollPane() {
    if (logScrollPane == null) {
      logScrollPane = new JScrollPane();
      logScrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      logScrollPane.setViewportView(getLogTextPane());
    }
    return logScrollPane;
  }
  /**
   * This method initializes logTextPane	
   * 	
   * @return javax.swing.JTextPane	
   */
  private JTextPane getLogTextPane() {
    if (logTextPane == null) {
      logTextPane = new JTextPane();
      logTextPane.setFont(new Font("Courier New", Font.PLAIN, 12));
    }
    return logTextPane;
  }
  /**
   * This method initializes pinField	
   * 	
   * @return javax.swing.JPasswordField	
   */
  private JPasswordField getPinField() {
    if (pinField == null) {
      pinField = new JPasswordField();
      pinField.setPreferredSize(new Dimension(120, 20));
      pinField.setPreferredSize(new Dimension(80, 20));
    }
    return pinField;
  }
  /**
   * This method initializes pinButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getPinButton() {
    if (pinButton == null) {
      pinButton = new JButton();
      pinButton.setText("Verifica");
      pinButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          if (getPinField().getPassword().length == 0) {
            JOptionPane.showMessageDialog(SmartCardCheckGUI.this, "Inserire il pin di accesso alla SmartCard","Attenzione",JOptionPane.WARNING_MESSAGE);
            return;
          }
          
          getLogTextPane().setText("");
          
          Thread t = new Thread() {

            @Override
            public void run() {
              pinButton.setEnabled(false);
              SmartCardChecker checker = new SmartCardChecker();
              try {
                if (getLogfileCheckBox().isSelected()) {
                  logStream = new FileOutputStream("smartcardcheck.log");
                }
                try {
                  checker.check(SmartCardCheckGUI.this,new String(getPinField().getPassword()));
                } finally {
                  if (logStream != null) {
                    logStream.close();
                    logStream = null;
                  }
                }
              } catch (Exception e) {
                JOptionPane.showMessageDialog(SmartCardCheckGUI.this, e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
              } finally {
                pinButton.setEnabled(true);
              }
              
            }
          };
          
          t.start();
        }
      });
    }
    return pinButton;
  }
  
  
  public void trace(final Level level, final String message) {
    if (logStream != null) {
      try {
        logStream.write(message.getBytes());
        logStream.write(13);
        logStream.write(10);
      } catch (Exception e) {
        //nop
      }
    }
    
    try {
      SwingUtilities.invokeAndWait(new Runnable() {

        public void run() {
          StyledDocument doc = getLogTextPane().getStyledDocument();
          try {
            SimpleAttributeSet aset = new SimpleAttributeSet();
            if (Level.INFO.equals(level)) {
              StyleConstants.setForeground(aset, Color.black);
              StyleConstants.setBold(aset, true);
            } else if (Level.ERROR.equals(level)) {
              StyleConstants.setForeground(aset, Color.red);
              StyleConstants.setBold(aset, true);
            } else {
              StyleConstants.setForeground(aset, Color.black);
              StyleConstants.setBold(aset, false);
              
            }
            doc.insertString(doc.getLength(),message+"\n",aset);
          } catch (Exception e) {
            System.err.println(e);
          }
        }
        
      });
    } catch (Exception e) {
      System.err.println(e);
    }
    
  }
  
  /**
   * This method initializes logfileCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getLogfileCheckBox() {
    if (logfileCheckBox == null) {
      logfileCheckBox = new JCheckBox();
      logfileCheckBox.setText("Salva file di log");
    }
    return logfileCheckBox;
  }
  public static void main(String[] args) {
    SmartCardCheckGUI smartCardCheck = new SmartCardCheckGUI();
    smartCardCheck.setVisible(true);
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
