package it.eng.utility.oomanager.xslt;

import org.odftoolkit.odfxsltrunner.Logger;

public class XsltLogger extends Logger{

	
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(XsltLogger.class);
	
	
	@Override
	protected void logMessage(String arg0, String arg1, String arg2, int arg3) {
		log.info(arg0+" - "+arg1+" - "+arg2+" - "+arg3);
		
	}

	@Override
	protected void logMessageWithLocation(String arg0, String arg1, int arg2) {
		log.info(arg0+" - "+arg1+" - "+arg2);
		
	}

	
	
}
