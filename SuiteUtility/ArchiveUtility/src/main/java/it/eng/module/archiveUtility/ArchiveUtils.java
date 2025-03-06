package it.eng.module.archiveUtility;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.eng.module.archiveUtility._7z.UnpackArchive7Z;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;
import it.eng.module.archiveUtility.rar.UnpackArchiveRar;
import it.eng.module.archiveUtility.tar.UnpackArchiveTar;
import it.eng.module.archiveUtility.zip.UnpackArchiveZip;

public class ArchiveUtils {
	
	private static final Logger log = LogManager.getLogger(ArchiveUtils.class);
	
	/**
	 *  Metodo per il calcolo del numero dei contenuti presenti nell'archivio
	 *  ritorna -1 se qualcosa è andato in errore
	 * 
	 * @param archive
	 * @param mimeType
	 * @param outputDir
	 * @return
	 * @throws Exception
	 */
	public static int countElementsArchive(File archive, String mimeType) throws Exception{
		
		int countElements = -1;
		UnpackArchive unpackArchive;
		
		log.debug("File da spacchettare: " + archive);
		
		if( mimeType!=null ){
			log.debug("mimeType ricevuto: " + mimeType);
		} else {
			log.debug("Non ho il mimetype dell'archivio, lo ricavo");
			mimeType = MimeUtils.getMimeType(archive);
			log.debug("mimeType: " + mimeType);
		}
		
		if( mimeType!=null && ( mimeType.equalsIgnoreCase(ArchiveType.ZIP.getMime()) 
				|| mimeType.equalsIgnoreCase(ArchiveType.ZIP2.getMime()) )){
			unpackArchive = new UnpackArchiveZip();
		} else if( mimeType!=null && mimeType.equalsIgnoreCase( ArchiveType.RAR.getMime() )){
			unpackArchive = new UnpackArchiveRar();
		} else if( mimeType!=null && mimeType.equalsIgnoreCase( ArchiveType.TAR.getMime() )){
			unpackArchive = new UnpackArchiveTar();
		} else if( mimeType!=null && mimeType.equalsIgnoreCase( ArchiveType._7Z.getMime() )){
			unpackArchive = new UnpackArchive7Z();
		} else {
			throw new Exception("Tipo archivio non supportato");
		}
		
		try {
			countElements = unpackArchive.countElements(archive);
		} catch (Exception e) {
			countElements = -1;
			log.error("Errore durante il calcolo della numerosità degli elementi presenti nell'archivio: " + archive.getAbsolutePath(), e);
		}
		
		return countElements;
	}
	
	public static DecompressioneArchiviBean getArchiveContent(File archive, String mimeType, String outputDir) throws Exception{
		
		DecompressioneArchiviBean bean = null;
		UnpackArchive unpackArchive;
		
		log.debug("File da spacchettare: " + archive);
		log.debug("outputDir: " + outputDir);
		
		if( mimeType!=null ){
			log.debug("mimeType ricevuto: " + mimeType);
		} else {
			log.debug("Non ho il mimetype dell'archivio, lo ricavo");
			mimeType = MimeUtils.getMimeType(archive);
			log.debug("mimeType: " + mimeType);
		}
		
		//verifico se esiste la cartella di output e altrimenti la creo
		File outputDirectory = new File(outputDir);
		if( !outputDirectory.exists() ){
			outputDirectory.mkdir();
		}
		
		if( mimeType!=null && ( mimeType.equalsIgnoreCase(ArchiveType.ZIP.getMime()) 
				|| mimeType.equalsIgnoreCase(ArchiveType.ZIP2.getMime()) )){
			unpackArchive = new UnpackArchiveZip();
		} else if( mimeType!=null && mimeType.equalsIgnoreCase( ArchiveType.RAR.getMime() )){
			unpackArchive = new UnpackArchiveRar();
		} else if( mimeType!=null && mimeType.equalsIgnoreCase( ArchiveType.TAR.getMime() )){
			unpackArchive = new UnpackArchiveTar();
		} else if( mimeType!=null && mimeType.equalsIgnoreCase( ArchiveType._7Z.getMime() )){
			unpackArchive = new UnpackArchive7Z();
		} else {
			throw new Exception("Tipo archivio non supportato");
		}
		
		try {
			bean = unpackArchive.unpackArchive(archive, outputDir);
			//bean.setError(false);
		} catch (Exception e) {
			bean = new DecompressioneArchiviBean();
			bean.setError(true);
			log.error("", e);
		}
		
		return bean;
	}
	
	
	/**
	 * 
	 * @param mime
	 *            mime del file
	 * @param ext
	 *            estensione del file
	 * @return se il file in ingresso � un archivio o meno
	 * 
	 */
	public static boolean checkArchive(String mime, String ext) {
		for (ArchiveType lArchiveType : ArchiveType.values()) {
			if (lArchiveType.getMime().equals(mime) || lArchiveType.getType().equals(ext)) {
				return true;
			}
		}
		return false;
	}
	
}
