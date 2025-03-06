package it.eng.hybrid.module.portScanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.portScanner.ui.PortScannerApplication;

public class JarLib {
	
	public final static Logger logger = Logger.getLogger(JarLib.class);

	static public boolean load(Class cl, String libname) {
		try {
			loadX(cl, libname);
			return true;
		} catch (Exception e) {
			logger.error("JarLib.load\n\tException = " + e.getMessage(), e);
		} catch (Error e) {
			logger.error("JarLib.load\n\tError = " + e.getMessage(), e);
		}
		try { // Shouldn't really need to load from system defaults anymore
			System.loadLibrary(libname);
			logger.info("JarLib.load: Successfully loaded library [" + libname + "] from some default system folder");
			return true;
		} catch (Exception e) {
			logger.error("JarLib.load\n\tException = " + e.getMessage(), e);
		} catch (Error e) {
			logger.error("JarLib.load\n\tError = " + e.getMessage(), e);
		}
		return false;
	}

	static private void loadX(Class cl, String name) throws IOException, UnsatisfiedLinkError {
		String libname = System.mapLibraryName(name);
		URL url = cl.getResource(JarLib.getOsSubDir() + "/" + libname);
		logger.info("URL della libreria: " + url);
		if (url == null) {
			throw new UnsatisfiedLinkError("uk.co.mmscomputing.util.JarLib.loadX: Could not find library [" + libname + "]");
		}
		try {
			URI uri = new URI(url.toString());
			String scheme = uri.getScheme();
			if (scheme.equals("file")) { // if on local file system use this copy
				System.load(new File(uri).getAbsolutePath());
				logger.info("JarLib.load: Successfully loaded library [" + url + "] from mmsc standard file location");
			} else if (scheme.equals("jar")) { // make copy in tmp folder on local file system
				File dir = new File(System.getProperty("java.io.tmpdir"), "mmsc");
				dir.mkdirs();
				File tmp = File.createTempFile("mmsc", libname, dir);
				try {
					// System.out.println(tmp.getAbsolutePath());
					JarLib.extract(tmp, url);
					System.load(tmp.getAbsolutePath());
					logger.info("JarLib.load: Successfully loaded library [" + url + "] from jar file location");
				} finally {
					File[] files = dir.listFiles();
					for (int i = 0; i < files.length; i++) { // delete all unused library copies
						if (files[i].getName().endsWith(libname)) {// if library is still needed we won't be able to delete it
							files[i].delete();
						}
					}
				}
			} else if (scheme.equals("bundle")) {
				String workFolder = getWorkFolderName();
				String hybridLibraryFolderPath = System.getProperty("user.home") + File.separator + workFolder + File.separator + "HybridLibrary" + File.separator + JarLib.getOsSubDir();
				File hybridLibraryFolderFile = new File(hybridLibraryFolderPath);
				if (!hybridLibraryFolderFile.exists()) {
					logger.info("Creo la cartella " + hybridLibraryFolderFile.getAbsolutePath());
					hybridLibraryFolderFile.mkdirs();
				}
				String libraryFilePath = hybridLibraryFolderPath + File.separator + libname;
				File libraryFile = new File(libraryFilePath);
				if (!libraryFile.exists()) {
					logger.info("Il file " + libraryFile.getAbsolutePath() + " non esiste, lo copio da " + url);
					JarLib.extract(libraryFile, url);
				}
				logger.info("File dll in caricamento: " + libraryFile);
				try {
					System.load(libraryFile.getAbsolutePath());
					logger.info("JarLib.load: dll caricato con successo");
				} catch (Throwable e) {
					logger.info("JarLib.load: dll non caricato o già presente nel class loader");
				}
			} else {
				throw new UnsatisfiedLinkError("uk.co.mmscomputing.util.JarLib.loadX:\n\tUnknown URI-Scheme [+scheme+]; Could not load library [" + uri + "]");
			}
		} catch (URISyntaxException urise) {
			logger.error("URISyntaxException " + urise.getMessage(), urise);
			throw new UnsatisfiedLinkError("uk.co.mmscomputing.util.JarLib.loadX:\n\tURI-Syntax Exception; Could not load library [" + url + "]");
		}
	}

	static private void extract(File fn, URL url) throws IOException {
		InputStream in = url.openStream();
		FileOutputStream out = new FileOutputStream(fn);
		byte[] buffer = new byte[4096];
		int count = 0;
		while ((count = in.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();
	}

	static private String getOsSubDir() { // This is where I put my stuff

		String osname = System.getProperty("os.name");
		if (osname.startsWith("Linux")) {
			return "lin32";
		}
		if (osname.startsWith("Windows")) {
			String osarch = System.getProperty("os.arch");
			if (osarch.endsWith("64")) { // amd64
				return "win64";
			} else { // x86
				return "win32";
			}
		}
		if (osname.startsWith("Mac")) {
			return "mac";
		}
		return "";
	}
	
	static public String copyJavaxCommPropertiesFile(Class cl, String javaxCommPropertiesFileName) throws IOException {
		URL url = cl.getResource(JarLib.getOsSubDir() + "/" + javaxCommPropertiesFileName);
		logger.info("URL del file JavaxCommProperties: " + url);
		String workFolder = getWorkFolderName();
		String hybridLibraryFolderPath = System.getProperty("user.home") + File.separator + workFolder + File.separator + "HybridLibrary" + File.separator + JarLib.getOsSubDir();
		File hybridLibraryFolderFile = new File(hybridLibraryFolderPath);
		if (!hybridLibraryFolderFile.exists()) {
			logger.info("Creo la cartella " + hybridLibraryFolderFile.getAbsolutePath());
			hybridLibraryFolderFile.mkdirs();
		}
		String libraryFilePath = hybridLibraryFolderPath + File.separator + javaxCommPropertiesFileName;
		File libraryFile = new File(libraryFilePath);
		if (!libraryFile.exists()) {
			logger.info("Il file " + libraryFile.getAbsolutePath() + " non esiste, lo copio da " + url);
			extract(libraryFile, url);
		}
		logger.info(libraryFilePath + " copiato con successo");
		return libraryFilePath;
	}
	
	static public String getWorkFolderName() {
		String workFolderSuffix = "";
		try {
			workFolderSuffix = PortScannerApplication.getProps().get("hybridWorkFolder");
			if (workFolderSuffix == null || "".equalsIgnoreCase(workFolderSuffix)) {
				workFolderSuffix = ".areas-hybrid";
			}
		} catch (Exception e) {
			workFolderSuffix = ".areas-hybrid";
		}
		return ".areas-hybrid" + "-" + workFolderSuffix;
	}
}