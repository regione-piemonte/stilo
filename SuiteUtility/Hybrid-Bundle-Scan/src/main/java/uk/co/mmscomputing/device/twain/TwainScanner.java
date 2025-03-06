package uk.co.mmscomputing.device.twain;

import java.util.Vector;
import java.awt.image.*;
import javax.swing.*;

import java.io.*;
//import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;

import uk.co.mmscomputing.device.scanner.*;

public class TwainScanner extends Scanner implements TwainConstants{

  public TwainScanner(){
    metadata=new TwainIOMetadata();
    jtwain.setScanner(this);
  }

// call native routine; TwainScanner -> jtwain.class -> jtwain.dll

  public void select(){		      	       // select twain source
    try{
      jtwain.select(this);
    }catch(Exception e){
      metadata.setException(e);
      fireListenerUpdate(metadata.EXCEPTION);
    }
  }
 
  public void acquire(){                 // initiate scan
    try
    {
      jtwain.acquire(this);
    }catch(Exception e){
      metadata.setException(e);
      fireListenerUpdate(metadata.EXCEPTION);
    }
  }
 
// callback function jtwain.dll -> jtwain.class -> TwainScanner.class

  void setImage(BufferedImage image){    // received an image
    try{
      metadata.setImage(image);
      fireListenerUpdate(metadata.ACQUIRED);
    }catch(Exception e){
      metadata.setException(e);
      fireListenerUpdate(metadata.EXCEPTION);
    }
  }

  void setImage(File file){              // received an image using file transfer
    try{
      metadata.setFile(file);
      fireListenerUpdate(metadata.FILE);
    }catch(Exception e){
      metadata.setException(e);
      fireListenerUpdate(metadata.EXCEPTION);
    }
  }

  void setImageBuffer(TwainTransfer.MemoryTransfer.Info info,byte[] buf){
    // we don't do anything here yet
    System.out.println(info.toString());
  }


  protected void negotiateCapabilities(TwainSource source){     
    // Called in jtwain when source is in state 4: negotiate capabilities
    ((TwainIOMetadata)metadata).setSource(source);
    fireListenerUpdate(metadata.NEGOTIATE);
    if(metadata.getCancel()){                     // application wants us to abort scan
      try{
        source.close();
      }catch(Exception e){
        metadata.setException(e);
        fireListenerUpdate(metadata.EXCEPTION);
      }
    }
  }

  void setState(TwainSource source){
    metadata.setState(source.getState());
    ((TwainIOMetadata)metadata).setSource(source);
    fireListenerUpdate(metadata.STATECHANGE);
  }

  void signalInfo(String msg,int val){
    metadata.setInfo(msg+" [0x"+Integer.toHexString(val)+"]");
    fireListenerUpdate(metadata.INFO);
  }

  void signalException(String msg){
    Exception e=new TwainIOException(getClass().getName()+".setException:\n    "+msg);
    metadata.setException(e);
    fireListenerUpdate(metadata.EXCEPTION);
  }

  public JComponent getScanGUI(){return new TwainPanel(this);}
  public boolean isAPIInstalled(){return jtwain.isInstalled();}

//  protected void finalize()throws Throwable{
//    System.err.println(getClass().getName()+".finalize:");
//  }

  static public Scanner getDevice(){
    return new TwainScanner();
  }

@Override
public String[] getDeviceNames() throws ScannerIOException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void select(String name) throws ScannerIOException {
	// TODO Auto-generated method stub
	
}

@Override
public String getSelectedDeviceName() throws ScannerIOException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setCancel(boolean c) throws ScannerIOException {
	// TODO Auto-generated method stub
	
}
}
