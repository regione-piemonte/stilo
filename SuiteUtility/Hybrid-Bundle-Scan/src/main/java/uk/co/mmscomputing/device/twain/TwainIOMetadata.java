package uk.co.mmscomputing.device.twain;

import java.io.IOException;

import uk.co.mmscomputing.device.scanner.*;

public class TwainIOMetadata extends ScannerIOMetadata{
  public boolean isGuiEnabled = false;
  static public final String[] TWAIN_STATE = {
    "",//0
    "Pre-Session",//1
    "Source Manager Loaded",//2
    "Source Manager Open", //3
    "Source Open", //4
    "Source Enabled", //5
    "Transfer Ready",//6
    "Transferring Data", //7
  };

  public String        getStateStr(){ return TWAIN_STATE[getState()];}

  private TwainSource  source=null;

         void          setSource(TwainSource source){this.source=source;}
  public TwainSource   getSource(){return source;}
  public ScannerDevice getDevice(){return source;}
  
  public void setIsGuiEnabled(boolean isGuiEnabled){
  	this.isGuiEnabled = isGuiEnabled;
  	System.out.println("isGuiEnabled: "+ isGuiEnabled);
  }
  // only valid when state changes!
  //--public boolean     isFinished(){return (getState()==3)&&(getLastState()==4);}
  public boolean    isFinished(){
  	System.out.println("*****************isGuiEnabled: "+ isGuiEnabled);
  	if(isGuiEnabled)
  		return (getState()==3)&&(getLastState()==4);
  	else
  		return (getState()==5)&&(getLastState()==7);
  }
}