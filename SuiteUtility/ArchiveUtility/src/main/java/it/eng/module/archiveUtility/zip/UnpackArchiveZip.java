package it.eng.module.archiveUtility.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.eng.module.archiveUtility.FileProtetto;
import it.eng.module.archiveUtility.UnpackArchive;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;

public class UnpackArchiveZip implements UnpackArchive {

	private static final Logger log = LogManager.getLogger(UnpackArchiveZip.class);
	
	public DecompressioneArchiviBean unpackArchive(File archive, String outputDir) /*throws Exception*/ {
		
		log.debug("Gestisco il caso dell'archivio ZIP: " + archive.getPath());
		
		DecompressioneArchiviBean bean = new DecompressioneArchiviBean();
		List<File> extracted = new ArrayList<File>();
		Map<String, String> errorsArchivi = new HashMap<String, String>();
		Map<String, String> errorsFiles = new HashMap<String, String>();
		
		InputStream archiveStream = null;
		ZipInputStream zis = null;
		ZipEntry ze = null;
		try {
			archiveStream = new FileInputStream( archive );
			zis = new ZipInputStream( archiveStream ,  Charset.forName("Cp437") );

			ze = zis.getNextEntry();
		} catch (FileNotFoundException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " inesistente");
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		} catch (IOException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " protetto da password");
			try {
				zis.close();
				if(archiveStream!=null){
					archiveStream.close();
				}
			} catch (IOException e1) {
				log.error("", e1);
			}
			bean.setErroriArchivi(errorsArchivi);
			bean.setError( true );
			return bean;
		}
		
		//provo a vedere se l'archivio e' un gzip
		if( ze==null ){
			try {
				bean = new UnpackArchiveGZip().unpackArchive(archive, outputDir);
			} catch (Exception e) {
				log.error("", e);
			}
			try {
				zis.close();
				if(archiveStream!=null){
					archiveStream.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
			return bean;
		}
		
		
		byte[] buffer = new byte[1024];
		String esito = null;
		try {
			while (ze != null) {

				String fileName = ze.getName();
				

				if (ze.isDirectory()) {
					fileName = fileName.substring(0, fileName.lastIndexOf("/"));
//					String fileNameEncoded = URLEncoder.encode(fileName,"UTF-8"); 
					File fileEstratto = new File(outputDir + File.separator +  fileName );
					
					log.debug("File unzipping: " + fileEstratto.getAbsoluteFile());
					
					//creo la cartella nella cartella di output
					boolean fileCreato = fileEstratto.mkdirs(); 
					 
				} else {
					File fileEstratto = null;
					if( fileName.contains("/")){
						String parent = fileName.substring(0, fileName.lastIndexOf("/"));
//						String fileParentPathEncoded = URLEncoder.encode(parent,"UTF-8"); 
						File fileEstrattoParent = new File(outputDir + File.separator + parent);
						
						if (!fileEstrattoParent.exists()) {
							fileEstrattoParent.mkdirs();
						}
						
						fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
//						String fileNameEncoded = URLEncoder.encode(fileName,"UTF-8"); 
						fileEstratto = new File(fileEstrattoParent.getAbsolutePath() + File.separator + fileName );
					} else {
//						String fileNameEncoded = URLEncoder.encode(fileName,"UTF-8"); 
						fileEstratto = new File(outputDir + File.separator + fileName );
					}
					
					
					log.debug("File unzipping: " + fileEstratto.getAbsoluteFile());
					
					
					//creo il file nella cartella di output
					FileOutputStream fos = new FileOutputStream(fileEstratto);
					
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				
					// Se il file da estrarre � protetto da password salvo l'eccezione nella mappa errorsFile -> "chiave:nomefile - valore:messaggio"				
					esito =	FileProtetto.checkPasswordProtection(fileEstratto);
					if (esito != null){
						errorsFiles.put(fileName, esito);
						esito = null;
					}
					
					extracted.add(fileEstratto);

				}
				
				zis.closeEntry();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

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
		log.debug("Gestisco il caso dell'archivio zip: " + fileIn.getPath());
		
		String prefixMsgException = "Eccezione UnpackArchiveZip->countElements()." + "File = " + fileIn.getName() + " -> ";
		
		int nElements = 0;
		InputStream archiveStream = null;
		ZipInputStream zipFile = null;
		ZipEntry entry;
		boolean isZipFile = false;
		
		try {
			archiveStream = new FileInputStream( fileIn );
			zipFile = new ZipInputStream( archiveStream ,  Charset.forName("Cp437") );
			
			while ((entry = zipFile.getNextEntry()) != null){
				isZipFile = true;
				if (entry.isDirectory()){
					continue;
				} else {
					nElements++;
				}
			}
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
		
		
		//provo a vedere se l'archivio e' un gzip
		try {
			if( !isZipFile){
				try {
						nElements = new UnpackArchiveGZip().countElements(fileIn);
					} catch (Exception e) {
							log.error(prefixMsgException, e);
					}
			}	
		}
		
		finally {
			if ( zipFile != null ) {
				try {
					zipFile.close();
				} catch (IOException e) {
					log.error(prefixMsgException + "Error closing archive: " + e);
				}

			}
			
			if(archiveStream!=null){
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
