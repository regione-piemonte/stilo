package it.eng.module.archiveUtility.tar;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.module.archiveUtility.FileProtetto;
import it.eng.module.archiveUtility.UnpackArchive;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;


public class UnpackArchiveTar implements UnpackArchive {

	private static final Logger log = LogManager.getLogger(UnpackArchiveTar.class);
	
	public DecompressioneArchiviBean unpackArchive(File archive, String outputDir) /*throws Exception*/ {
		log.debug("Gestisco il caso dell'archivio TAR: " + archive.getPath());
		
		DecompressioneArchiviBean bean = new DecompressioneArchiviBean();
		
		List<File> extracted = new ArrayList<File>();
		Map<String, String> errorsArchivi = new HashMap<String, String>();
		Map<String, String> errorsFiles = new HashMap<String, String>();
				
		InputStream archiveStream = null;
		GZIPInputStream gzipIS = null;
		TarArchiveInputStream tarIS = null;
		try {
			archiveStream = new FileInputStream(archive);
			gzipIS = new GZIPInputStream( archiveStream );
			tarIS = new TarArchiveInputStream( gzipIS , "UTF-8");
		} catch (FileNotFoundException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " inesistente");
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		} catch (ZipException e) {
			log.error("", e);
			try {
				archiveStream.close();
				archiveStream = new FileInputStream(archive);
				tarIS = new TarArchiveInputStream( archiveStream , "UTF-8");
			} catch (FileNotFoundException e1) {
				log.error("", e);
				errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " inesistente");
				bean.setErroriArchivi(errorsArchivi);
				return bean;
			} catch (IOException e1) {
				log.error("", e);
				errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " protetto da password");
				bean.setErroriArchivi(errorsArchivi);
				return bean;
			}
			
		} catch (IOException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " protetto da password");
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		}
				
		TarArchiveEntry gze = null;
		String esito = null;
		try {
			while ((gze = tarIS.getNextTarEntry()) != null) {
				String fileName = gze.getName();
				String parent = fileName.substring(0, fileName.lastIndexOf("/")+1);
				log.debug("fileName file estratto " + fileName);
				
				File newFile = new File( outputDir + File.separator + fileName );
				File newFileParent = new File(outputDir + File.separator + parent);
				
				if (gze.isDirectory()) {
					newFile.mkdirs();
				} else {
					
					if(!newFileParent.exists()) {
						newFileParent.mkdirs();
					}
					
					OutputStream outputFileStream = new FileOutputStream(newFile);
					IOUtils.copy(tarIS, outputFileStream);
					outputFileStream.close();
					extracted.add(newFile);
					
					// Se il file da estrarre � protetto da password salvo l'eccezione nella mappa errorsFile -> "chiave:nomefile - valore:messaggio"		
					esito = FileProtetto.checkPasswordProtection(newFile);
					if (esito != null) {
						errorsFiles.put(fileName, esito);
						esito = null;
					}
					
				}
			}
		} catch (IOException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " protetto da password");
		}
		log.info("Estratti " + extracted.size() + " file da " + archive.getPath());
		try {
			if( tarIS!=null )
				tarIS.close();
			if( gzipIS!=null )
				gzipIS.close();
		} catch (IOException e) {
			log.error("",e);
		}	
		finally {
			if( archiveStream!=null ){
				try {
					archiveStream.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
		}

		bean.setFilesEstratti(extracted);
		bean.setErroriArchivi(errorsArchivi);
		bean.setErroriFiles(errorsFiles);
		
		if( errorsArchivi.isEmpty() && errorsFiles.isEmpty() ){
			bean.setError( false );
		} else {
			bean.setError( true );
		}
		
		return bean;
	}

	@Override
	public int countElements(File fileIn) throws Exception {
		log.debug("Gestisco il caso dell'archivio tar: " + fileIn.getPath());
		
		String prefixMsgException = "Eccezione UnpackArchiveTar->countElements()." + "File = " + fileIn.getName() + " -> ";
				
		int nElements = 0;
		
		InputStream archiveStream = null;
		GZIPInputStream gzipIS = null;
		TarArchiveInputStream tarIS = null;
		
		// caso 1 : estraggo da GZIPInputStream
		try {
			archiveStream = new FileInputStream(fileIn);
			gzipIS = new GZIPInputStream( archiveStream );
			tarIS = new TarArchiveInputStream( gzipIS , "UTF-8");
		} catch (FileNotFoundException e) {
			log.error(prefixMsgException +  "Inesistente", e);
			return -1;
		} 
		
		catch (ZipException e) {
			// caso 2 : estraggo da FileInputStream
			log.error("", e);
			try {
				archiveStream.close();
				archiveStream = new FileInputStream(fileIn);
				tarIS = new TarArchiveInputStream( archiveStream , "UTF-8");
			} catch (FileNotFoundException e1) {
				log.error(prefixMsgException +  "Inesistente", e);
				return -1;
				
			} catch (IOException e1) {
				log.error(prefixMsgException + "Protetto da password.", e);
				return -1;
			}
		}
		catch (IOException e) {
			log.error(prefixMsgException + "Protetto da password.", e);
			return -1;
		}
				
		try {			
			TarArchiveEntry entry;
			while ((entry = tarIS.getNextTarEntry()) != null){
				if (entry.isDirectory()){
					continue;
				} else {
					nElements++;
				}
			}
		} catch (IOException e) {
			log.error(prefixMsgException + "Protetto da password.", e);
			return -1;
		} 
		
		try {
			if( tarIS!=null )
				tarIS.close();
			if( gzipIS!=null )
				gzipIS.close();
		} catch (IOException e) {
			log.error(prefixMsgException + "Error closing archive: " + e);
		}	
		finally {
			if( archiveStream!=null ){
				try {
					archiveStream.close();
				} catch (IOException e) {
					log.error(prefixMsgException + "Error closing archive: " + e);
				}
			}
		}
		
		return nElements;
	}
}