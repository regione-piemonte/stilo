package uk.co.mmscomputing.device.twain;

import java.beans.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import uk.co.mmscomputing.util.*;
import uk.co.mmscomputing.device.scanner.*;


public class TwainPanel extends JComponent implements TwainConstants,ScannerListener{

  Scanner scanner=null;
  JButton acqbutton=null;
  JButton selbutton=null;
  JCheckBox guicheckbox=null;


  public TwainPanel(Scanner scanner){
    this.scanner=scanner;
    scanner.addListener(this);
    setLayout(new GridLayout(0,1));
    acqbutton=new JButton("Acquire",new JarImageIcon(getClass(),"32x32/scanner.png"));
    acqbutton.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "acquire"));
    acqbutton.setMnemonic(KeyEvent.VK_F9);
    acqbutton.requestFocus();
    add(acqbutton);

    JPanel p=new JPanel();
    p.setBorder(BorderFactory.createEtchedBorder());
    guicheckbox = new JCheckBox("Enable GUI");
    guicheckbox.setSelected(false);
    p.add(guicheckbox);
    add(p);
    selbutton=new JButton("Select",new JarImageIcon(getClass(),"32x32/list.png"));
    selbutton.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "select"));
    add(selbutton);

    if(jtwain.getSource().isBusy())
    {
      // applets might not be in state 3
      acqbutton.setEnabled(false);
      selbutton.setEnabled(false);
      guicheckbox.setEnabled(false);
    }
  }

  public void  setDisableButton(boolean disable)
  {
 	acqbutton.setEnabled(!disable);
	selbutton.setEnabled(!disable);
	guicheckbox.setEnabled(!disable);
	if(!disable)
		acqbutton.setMnemonic(KeyEvent.VK_F9);
  }
  
  public void acquire(){try {
	scanner.acquire();
} catch (ScannerIOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}}		         // acquire BufferedImage from selected/default twain source
  public void select(){try {
	scanner.select();
} catch (ScannerIOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}}  		         // select twain data source

  public void update(ScannerIOMetadata.Type type, final ScannerIOMetadata md){
    if(md instanceof TwainIOMetadata){
      TwainIOMetadata metadata=(TwainIOMetadata)md;
      TwainSource source=metadata.getSource();
	  metadata.setIsGuiEnabled(guicheckbox.isSelected());
      if(type.equals(ScannerIOMetadata.STATECHANGE)){
        if(metadata.isState(STATE_SRCMNGOPEN)){        // state = 3
          if(source.isBusy())
          {
		  	if ((scanner.getJsWindow()!=null)&&(scanner.getProperty("FunctionDisableBlock")!=null))
		  		scanner.getJsWindow().eval(scanner.getProperty("FunctionDisableBlock")+"("+"'true'"+")");
            acqbutton.setEnabled(false);
            selbutton.setEnabled(false);
            guicheckbox.setEnabled(false);
//            scanner.getScanTab().disableButton();
          }
          else
          {
			if ((scanner.getJsWindow()!=null)&&(scanner.getProperty("FunctionDisableBlock")!=null))
		      	scanner.getJsWindow().eval(scanner.getProperty("FunctionDisableBlock")+"("+"'false'"+")");
            acqbutton.setEnabled(true);
            selbutton.setEnabled(true);
            guicheckbox.setEnabled(true);
//            scanner.getScanTab().enableButton();
          }
        }
      }else if(type.equals(ScannerIOMetadata.NEGOTIATE)){
        if(source.isUIControllable()){                 // if it is possible to hide source's GUI
          source.setShowUI(guicheckbox.isSelected());  // then use checkbox value
        }else{
          if(!guicheckbox.isSelected()){System.out.println("9\bCannot hide twain source's GUI.");}
          guicheckbox.setSelected(true);               // else set to true whatever the user selected
        }
      }
    }
  }
}

