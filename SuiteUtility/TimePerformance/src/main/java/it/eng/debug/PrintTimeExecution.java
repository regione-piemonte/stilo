package it.eng.debug;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrintTimeExecution {

	static Map<Long, Map<String,Long>> mappaGlobaleStartEnd = new HashMap<Long, Map<String,Long>>();
	
	static Logger mLogger = LogManager.getLogger(PrintTimeExecution.class);
	
	public static void print(String comment){
		Long id = Thread.currentThread().getId();
		String traceElement = Thread.currentThread().getStackTrace()[2].toString();
		String lStrPoint = traceElement.substring(0, traceElement.indexOf("(")); 
		Map<String, Long> lMappaStartEnd = null;
		if (mappaGlobaleStartEnd.get(id) == null){
			lMappaStartEnd = new HashMap<String, Long>();
			lMappaStartEnd.put(lStrPoint, new Long(-1));
			mappaGlobaleStartEnd.put(id, lMappaStartEnd);
		} 
		lMappaStartEnd = mappaGlobaleStartEnd.get(id);
		if (lMappaStartEnd.get(lStrPoint) == null){
			lMappaStartEnd.put(lStrPoint, new Long(-1));
		}
		if (lMappaStartEnd.get(lStrPoint) == -1){
			long time = new Date().getTime();
			lMappaStartEnd.put(lStrPoint, time);
			mLogger.debug(Thread.currentThread().getStackTrace()[2] + " - " + " start - Thread Id. " + id + (comment!=null?comment:""));
		} else {
			long timeExecution = new Date().getTime() - lMappaStartEnd.get(lStrPoint);
			long time = new Date().getTime();
			lMappaStartEnd.put(lStrPoint, time);
			mLogger.debug(Thread.currentThread().getStackTrace()[2] + " - " + timeExecution + " - Thread Id. " + id + " " + (comment!=null?comment:""));
		}
		mappaGlobaleStartEnd.put(id, lMappaStartEnd);
		
	}
}
