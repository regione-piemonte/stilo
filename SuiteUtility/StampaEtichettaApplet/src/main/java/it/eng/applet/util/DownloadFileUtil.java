package it.eng.applet.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class DownloadFileUtil {

	public static void downloadFile(String address, String idUtente, String idSchema, String idDominio, String version, String pathFile) throws MalformedURLException, IOException{
		URL lUrl = new URL(address + "?idUtente=" + idUtente + "&idSchema=" + idSchema + "&idDominio=" + idDominio + "&version=" + version);
		File lFile = new File(pathFile);
		FileUtils.copyURLToFile(lUrl, lFile);
	}
}
