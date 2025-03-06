package eng.util;

/**
 * <p>Title: Thebit@Web</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Engineering</p>
 * @author not attributable
 * @version 1.0
 */
public class Logger {

  static org.apache.log4j.Logger logger;

  public static String getlogMode() {
    return null;
  }
  
  public static void setlogMode(String newlogMode) {
    //logMode = newlogMode;
  }

  public static void PrintOut( String message ) {
  	
    //if ( logMode != null && logMode.equalsIgnoreCase( "DEBUG" ) )
    //System.out.println( message );
    getLogger().info(message);
  }
  
  public static org.apache.log4j.Logger getLogger()
  {
       if (logger == null)
       {
           logger = org.apache.log4j.Logger.getLogger(Logger.class.getName());
           java.util.Properties p = new java.util.Properties();
           try {
           	
           	p.load( Logger.class.getClassLoader().getResourceAsStream("log4j.properties"));
           	
           } catch (Exception ex) {	
    	   		p.setProperty("log4j.rootLogger","debug, mystdout");
    	   		p.setProperty("log4j."+ Logger.class.getName(),"debug, mystdout");
    	   		p.setProperty("log4j.appender.mystdout","org.apache.log4j.ConsoleAppender");
    	   		p.setProperty("log4j.appender.mystdout.layout","org.apache.log4j.PatternLayout");
    	   		p.setProperty("log4j.appender.mystdout.layout.ConversionPattern","[ENG Common Core] %5p (%F:%L) - %m%n");
           }
	   
           org.apache.log4j.PropertyConfigurator.configure(p);
       }     
       return logger;     
  }

}
