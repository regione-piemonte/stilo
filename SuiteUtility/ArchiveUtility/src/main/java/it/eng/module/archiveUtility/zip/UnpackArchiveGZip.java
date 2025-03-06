package it.eng.module.archiveUtility.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.eng.module.archiveUtility.FileProtetto;
import it.eng.module.archiveUtility.UnpackArchive;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;
import it.eng.module.archiveUtility.tar.UnpackArchiveTar;

import java.util.zip.GZIPInputStream;

public class UnpackArchiveGZip implements UnpackArchive {

	private static final Logger log = LogManager.getLogger(UnpackArchiveGZip.class);
	
	public DecompressioneArchiviBean unpackArchive(File archive, String outputDir) throws Exception {
		
		log.debug("Gestisco il caso dell'archivio GZIP: " + archive.getPath());
		
		DecompressioneArchiviBean bean = new DecompressioneArchiviBean();
		List<File> extracted = new ArrayList<File>();
		Map<String, String> errorsArchivi = new HashMap<String, String>();
		Map<String, String> errorsFiles = new HashMap<String, String>();
		
		InputStream archiveStream = null;
		GZIPInputStream gzis=null; 
		try {
			archiveStream = new FileInputStream( archive );
			gzis = new GZIPInputStream( archiveStream );			
		} catch (FileNotFoundException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " inesistente");
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		} catch (IOException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " protetto da password");
			try {
				gzis.close();
				if(archiveStream!=null){
					archiveStream.close();
				}
			} catch (IOException e1) {
				log.error("", e1);
			}
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		}
		
		
		String esito = null;
		try {
			String fileName = FilenameUtils.removeExtension(archive.getName());
			File fileEstratto = new File(outputDir + File.separator + fileName);
			log.debug("File unzipping: " + fileEstratto.getAbsoluteFile());

			FileOutputStream fos = new FileOutputStream(fileEstratto);
					
			byte[] buffer = new byte[1024];
			int len;
			while ((len = gzis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			
			if( FilenameUtils.getExtension(fileName).equalsIgnoreCase("tar")){
				bean = new UnpackArchiveTar().unpackArchive(fileEstratto, outputDir);
				FileUtils.forceDelete(fileEstratto);
				return bean;
			}
				
			// Se il file da estrarre � protetto da password salvo l'eccezione nella mappa errorsFile -> "chiave:nomefile - valore:messaggio"				
			esito =	FileProtetto.checkPasswordProtection(fileEstratto);
			if (esito != null){
				errorsFiles.put(fileName, esito);
				esito = null;
			}
				
			extracted.add(fileEstratto);
				
			gzis.close();

		} catch (IOException e) {
			log.error("", e);
		}
		finally {
			if(archiveStream!=null){
				try {
					archiveStream.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
		log.info("Estratti " + extracted.size() + " file da " + archive.getPath());
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
		log.debug("Gestisco il caso dell'archivio GZip: " + fileIn.getPath());
		
		String prefixMsgException = "Eccezione UnpackArchiveGZip->countElements()." + "File " + fileIn.getName();
		
		int nElements = 0;
		
		InputStream archiveStream = null;
		GZIPInputStream gzis=null; 
		try {
			archiveStream = new FileInputStream( fileIn );
			gzis = new GZIPInputStream( archiveStream );			
		} catch (FileNotFoundException e) {
			log.error(prefixMsgException + "Inesistente.", e);
			return -1;
		} catch (IOException e) {
			log.error(prefixMsgException + "Protetto da password.", e);
			return -1;
		} catch (Exception e) {
			log.error(prefixMsgException + "Errore nella decompressione.", e);
			return -1;			
		}
		finally {
			if ( gzis != null ) {
				try {
					gzis.close();
				} catch (IOException e) {
					log.error(prefixMsgException + "Error closing archive: " + e);
				}

			}
		}
		
		// Il file GZIP contiene sempre 1 solo file
		nElements = 1;
		
		return nElements;
	}
	
}
