package it.eng.applet.stampaFile.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class DownloadFileUtil {

	public final static Logger logger = Logger.getLogger(DownloadFileUtil.class);
	
	public static void downloadFile(String address, String idUtente, String idSchema, String idDominio, String version, String pathFile)
			throws MalformedURLException, IOException {
		logger.debug("Entro in downloadFile di DownloadFileUtil con i seguenti parametri di invocazione");
		logger.debug("address: " + address);
		logger.debug("idUtente: " + idUtente);
		logger.debug("idSchema: " + idSchema);
		logger.debug("idDominio: " + idDominio);
		logger.debug("version: " + version);
		logger.debug("pathFile: " + pathFile);
		URL lUrl = new URL(address + "?idUtente=" + idUtente + "&idSchema=" + idSchema + "&idDominio=" + idDominio + "&version=" + version);
		logger.debug("lUrl vale: " + lUrl.getPath());
		File lFile = new File(pathFile);
		FileUtils.copyURLToFile(lUrl, lFile);
		logger.debug("Ho salvato il file in : " + pathFile);
	}
}
