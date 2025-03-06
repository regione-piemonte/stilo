package uk.co.mmscomputing.util;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;
import javax.swing.*;

import uk.co.mmscomputing.util.log.LogBook;

abstract public class UtilMainApp extends JApplet{

  private Properties properties=new Properties();
  private File       propertiesFile;
  protected String scannerFilePath = null;


  public UtilMainApp(){
    super();
    this.scannerFilePath = System.getProperty("user.home")+"\\ProtocolScannerDoc";
  }

  public UtilMainApp(String title, String[] argv)
  {    
    this.scannerFilePath = System.getProperty("user.home")+"\\ProtocolScannerDoc";

    JFrame.setDefaultLookAndFeelDecorated(true);

    JFrame frame=new JFrame(title);
//    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent ev) {
        stop();System.exit(0);
      }
    });
    frame.getContentPane().add(this);

    GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    setFrameSize(frame,ge.getMaximumWindowBounds());
    init();
    frame.setLocationRelativeTo(null);
//    frame.pack();
    frame.setVisible(true);
    start();
  }

  protected void setFrameSize(JFrame frame, Rectangle bounds){
    frame.setSize(bounds.width*4/5,bounds.height*4/5);
  }
  public void setProperty(String nameProp,String valueProp)
  {
  	if(properties.get(nameProp)!=null) 
  		properties.remove(nameProp);	
  	properties.put(nameProp,valueProp);
  }
  public Object getProperty(String nameProp)
  {
  	return properties.get(nameProp);
  }

  abstract protected JPanel getCenterPanel(Properties properties)throws Exception;
  
  public void createGUI(){
    try{
      //LogBook log=new LogBook(false);
      Runtime rt=Runtime.getRuntime();
      System.out.println("Runtime Total Memory: "+(rt.totalMemory()/(1024*1024))+" MB");
      System.out.println("Runtime Max   Memory: "+(rt.maxMemory()/(1024*1024))+" MB");

      String s=System.getProperty("java.home");
      System.out.println("java directory: "+s);

      String userdir=System.getProperty("user.home")+"\\ProtocolCFGFile";
      System.out.println("user directory: "+userdir);

      File   parent   =new File(userdir);
      String classname=getClass().getName();
      String filename ="ScannerDevice.cfg";
      try{   
        parent.mkdirs();
        propertiesFile=new File(parent.getAbsolutePath(),filename);
      }catch(Exception e){
        System.out.println("9\bCould not create directory:\n\t"+parent.getAbsolutePath()+"\n\t"+e);
        propertiesFile=new File(filename);
      }

      System.out.println("properties file: "+propertiesFile.getAbsolutePath());

      if(propertiesFile.exists()){properties.load(new FileInputStream(propertiesFile));}


      //JTabbedPane tp=new JTabbedPane();
      //tp.addTab("MainApp",getIconPanel(properties));
      //tp.addTab("Log",log);
      Container cp=this.getContentPane();
      cp.setLayout(new BorderLayout());
//      cp.add(tp,BorderLayout.CENTER);
      cp.add(getCenterPanel(properties),BorderLayout.CENTER);

    }catch(Exception e){
      System.out.println("9\b"+getClass().getName()+".createGUI:\n\tCould not create GUI\n\t"+e.getMessage());
      e.printStackTrace();
    }
  }
  public void deleteTmpFile()
  {
  	File dirFile = null;
  	File filesDirFiles[] = null;
  	try
  	{
  		dirFile = new File(scannerFilePath);
  		if((dirFile.exists())&&(dirFile.isDirectory()))
  		{
  			filesDirFiles = dirFile.listFiles();
  			for(int i = 0;i < filesDirFiles.length;i++)
  			{
  				if(filesDirFiles[i].isFile()
  					&&
  				  (
  				  	filesDirFiles[i].getName().toLowerCase().indexOf(".pdf")!=-1
  				  	||
  				    (
  				    filesDirFiles[i].getName().toLowerCase().indexOf(".tif")!=-1
  				  	&&
  				  	filesDirFiles[i].getName().toLowerCase().indexOf("scannedprotocol.tif")==-1
  				  	)
  				  )
  				 )
  				{
  					System.out.println("File in Cancellazione: "+filesDirFiles[i].getName().toLowerCase());
  					filesDirFiles[i].delete();
  				}
  			}
  		}
  	}
  	catch(Exception e)
  	{
  		
  	}
  }
  public void init(){
    try
    {    
      javax.swing.SwingUtilities.invokeAndWait(
        new Runnable(){
          public void run(){
            createGUI();
          }
        }
      );
    }
    catch(Exception e)
    {
      System.out.println("9\b"+getClass().getName()+".init:\n\tCould not create GUI\n\t"+e.getMessage());
      e.printStackTrace();
    }
  }

  public void stop(){
    try{
      properties.store(new FileOutputStream(propertiesFile),propertiesFile.getAbsolutePath());
    }catch(Exception e){
      System.out.println("9\b"+getClass().getName()+".stop:\n\tCould not save properties\n\t"+e.getMessage());
      e.printStackTrace();
    }
    super.stop();
  }
}


