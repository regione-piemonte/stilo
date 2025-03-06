package it.eng.applet.util;

import it.eng.applet.configuration.ManagerConfiguration;
//import it.eng.applet.configuration.XsltFopConfigurator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileConfigurationUtil {

//	public static void createIfNotExist(String pStrFileName) throws IOException{
//		File lFile = new File(ManagerConfiguration.userPrefDirPath + File.separator + pStrFileName);
//		if (!lFile.exists()){
//			InputStream lInputStream = XsltFopConfigurator.class.getClassLoader().getResourceAsStream(pStrFileName);
//			lFile.createNewFile();
//			FileUtils.writeByteArrayToFile(lFile, IOUtils.toByteArray(lInputStream));
//		}
//	}
	
	public static void createDirIfNotExist(){
		File lFile = new File(ManagerConfiguration.userPrefDirPath);
		if (!lFile.exists()){
			lFile.mkdir();
		}
	}
}
