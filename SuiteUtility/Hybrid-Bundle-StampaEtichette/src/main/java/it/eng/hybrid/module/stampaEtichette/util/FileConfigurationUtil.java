package it.eng.hybrid.module.stampaEtichette.util;

import it.eng.hybrid.module.stampaEtichette.config.ManagerConfiguration;
import it.eng.hybrid.module.stampaEtichette.config.XsltFopConfigurator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


public class FileConfigurationUtil {

	public static void createIfNotExist(String pStrFileName) throws IOException{
		File lFile = new File(ManagerConfiguration.userPrefDirPath + File.separator + pStrFileName);
		if (!lFile.exists()){
			InputStream lInputStream = XsltFopConfigurator.class.getClassLoader().getResourceAsStream(pStrFileName);
			lFile.createNewFile();
			FileUtils.writeByteArrayToFile(lFile, IOUtils.toByteArray(lInputStream));
		}
	}
	
	public static void createDirIfNotExist(String filePath){
		File lFile = new File( filePath );
		if (!lFile.exists()){
			lFile.mkdir();
		}
	}
}
