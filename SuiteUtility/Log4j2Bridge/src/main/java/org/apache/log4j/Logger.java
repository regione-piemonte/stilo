package org.apache.log4j;

import org.apache.logging.log4j.LogManager;

public class Logger {

	private org.apache.logging.log4j.Logger logger;

	public Logger(Class pClass){
		logger = LogManager.getLogger(pClass);
	}
	
	public Logger(String pClass){
		logger = LogManager.getLogger(pClass);
	}

	public static Logger getLogger(Class pClass){
		return new Logger(pClass);
	}
	
	public static Logger getLogger(String pClass){
		return new Logger(pClass);
	}

	public void debug(Object pObject){
		logger.debug(pObject);
	}
	
	public void debug(Object pObject, Throwable t){
		logger.debug(pObject);
	}
	
	public void error(Object pObject){
		logger.error(pObject);
	}
	
	public void error(Object pObject, Throwable t){
		logger.error(pObject, t);
	}
	
	public void trace(Object pObject){
		logger.error(pObject);
	}
	
	public void trace(Object pObject, Throwable t){
		logger.error(pObject, t);
	}
	
	public void info(Object pObject){
		logger.error(pObject);
	}
	
	public void info(Object pObject, Throwable t){
		logger.error(pObject, t);
	}
	
	public void warn(Object pObject){
		logger.error(pObject);
	}
	
	public void warn(Object pObject, Throwable t){
		logger.error(pObject, t);
	}
	
	public void fatal(Object pObject){
		logger.error(pObject);
	}
	
	public void fatal(Object pObject, Throwable t){
		logger.error(pObject, t);
	}
	
	public void log(String pString, Priority pPriority, Object pObject, Throwable pThrowable){
		logger.log(getLevel(pPriority), pString, pObject, pThrowable);
	}
	
	private org.apache.logging.log4j.Level getLevel(Priority pPriority){
		if (pPriority.toInt() == Priority.ALL_INT)
			return org.apache.logging.log4j.Level.ALL;
		if (pPriority.toInt() == Priority.DEBUG_INT)
			return org.apache.logging.log4j.Level.DEBUG;
		if (pPriority.toInt() == Priority.ERROR_INT)
			return org.apache.logging.log4j.Level.ERROR;
		if (pPriority.toInt() == Priority.FATAL_INT)
			return org.apache.logging.log4j.Level.FATAL;
		if (pPriority.toInt() == Priority.INFO_INT)
			return org.apache.logging.log4j.Level.INFO;
		if (pPriority.toInt() == Priority.OFF_INT)
			return org.apache.logging.log4j.Level.OFF;
		if (pPriority.toInt() == Priority.WARN_INT)
			return org.apache.logging.log4j.Level.WARN;
		return org.apache.logging.log4j.Level.ALL;
	}
	
	
	
	public boolean isEnabledFor(Priority pPriority){
		if (pPriority.toInt() == Priority.ALL_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.ALL);
		if (pPriority.toInt() == Priority.DEBUG_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.DEBUG);
		if (pPriority.toInt() == Priority.ERROR_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.ERROR);
		if (pPriority.toInt() == Priority.FATAL_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.FATAL);
		if (pPriority.toInt() == Priority.INFO_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.INFO);
		if (pPriority.toInt() == Priority.OFF_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.OFF);
		if (pPriority.toInt() == Priority.WARN_INT)
			return logger.isEnabled(org.apache.logging.log4j.Level.WARN);
		return true;
	}
}
