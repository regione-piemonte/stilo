package it.eng.areas.hybrid.util;

import java.io.File;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LoggerUtils {
	private final static Logger logger = Logger.getLogger(LoggerUtils.class);

	public static void setup(File logFile) {
		RollingFileAppender fa = new RollingFileAppender();
		fa.setName("FileLogger");
		fa.setFile(logFile.getAbsolutePath());
		fa.setLayout(new PatternLayout("%5p (%d{dd/MM/yyyy HH:mm:ss}) [%t] (%F:%L) - %m%n"));
		fa.setThreshold(Level.DEBUG);
		fa.setAppend(true);
		fa.setMaximumFileSize(5000 * 1024);
		fa.setMaxBackupIndex(10);
		fa.activateOptions();
		Logger.getRootLogger().addAppender(fa);
		logger.info("Logger inited");
	}

}
