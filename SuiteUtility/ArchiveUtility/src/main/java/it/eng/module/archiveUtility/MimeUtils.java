package it.eng.module.archiveUtility;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.activation.MimeType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.vfs2.FileNotFoundException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.MimeUtilsAdapter;
import it.eng.suiteutility.module.mimedb.dao.DaoAnagraficaFormatiDigitali;
import it.eng.suiteutility.module.mimedb.dao.HibernateUtil;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;

public class MimeUtils {

	private static final Logger log = LogManager.getLogger(MimeUtils.class);
	
	public static MimeUtilsAdapter adapter;
	public static DaoAnagraficaFormatiDigitali dao;
	
	public static String getMimeType(File archive){
		return getMimeType(archive, archive.getName());
	}
	
	public static String getMimeType(File archive, String fileName){
	
		String estensione  = FilenameUtils.getExtension( fileName );
		
		log.debug("estensione file : " + estensione);
		
		if( dao==null ){
			initDb();
		}
		
		TAnagFormatiDig formato = null;
		MimeType mimeType = null;
		boolean mimeAffidabile = false;
		String mimeTypeString = "";
		try {
			//TAnagFormatiDig formato = dao.findFormatByExt(estensione);
			log.debug("Provo a recuperare il mime in base all'estensione");
			mimeType = getMimeTypeExt(archive, estensione);
			log.info("formato recuperato dal mime " + mimeType);
			
			mimeAffidabile = adapter.checkIfExtensionMimeIsReliable(mimeType, archive);
			log.info("Il mimeRecuperato dall'estensione e' affidabile? " + mimeAffidabile);
		} catch (Exception e) {
			log.error("", e);
		}
			
		if (!mimeAffidabile) {
			try {	
				log.debug("Provo a recuperare il mime in base al file");
				mimeType = getBestMimeType(archive);
				log.info("determinato mime: " + mimeType);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
		if( mimeType!=null ){
			mimeTypeString = mimeType.getBaseType();
			log.info("formato recuperato dal mime " + mimeTypeString);
			
			try {
				formato = dao.findFormatByMimeType( mimeTypeString );
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
		if( formato!=null ){
			return formato.getMimetypePrincipale();
		}
		return null;
	}
	
	private static void initDb(){
		HibernateUtil.setEntitypackage( "it.eng.suiteutility.module.mimedb.entity" );
		HibernateUtil.buildSessionFactory( "hibernate.cfg.xml", "archiveUtils" );
		
		adapter = new MimeUtilsAdapter();
		dao = new DaoAnagraficaFormatiDigitali();
	}
	
	public static MimeType getMimeTypeExt(File file, String ext) throws MimeDetectorException {
		log.info("Metodo di determinazione del mimetype per il file " + file);
		// verifico se il file ha degli invii all'inizio e eventualmente li rimuovo
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "rw");
			boolean esito = deleteInitialCarriageReturn(raf);
			// log.info("Esito presenza invio " + esito );
			while (esito) {
				esito = deleteInitialCarriageReturn(raf);
				// log.info("Esito presenza invio " + esito );
			}
			raf.close();
			return adapter.detectBestWithExtension(file, ext);
		} catch (IOException e) {
			log.error("Errore getMimeTypeExt", e);
		}
		return null;
	}
	
	public static MimeType getBestMimeType(File file) throws MimeDetectorException {
		log.info("Metodo di determinazione del miglior mimetype per il file " + file);
		// verifico se il file ha degli invii all'inizio e eventualmente li rimuovo
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "rw");
			boolean esito = deleteInitialCarriageReturn(raf);
			while (esito) {
				esito = deleteInitialCarriageReturn(raf);
			}
			raf.close();

			return adapter.detectBest(file);
		}
		catch (IOException e) {
			log.error("Errore getBestMimeType", e);
		}
		return null;
	}
	
	private static boolean deleteInitialCarriageReturn(RandomAccessFile raf) {
		byte[] remainingBytes = null;
		try {
			long position = 0;
			String line = null;
			raf.seek(position);
			line = raf.readLine();
			if (line != null && line.trim().equalsIgnoreCase("")) {
				remainingBytes = new byte[(int) (raf.length() - raf.getFilePointer())];
				raf.read(remainingBytes);
				// Truncate the file to the position of where we deleted the information.
				raf.getChannel().truncate(position);
				raf.seek(position);
				raf.write(remainingBytes);
				return true;
			}
		} catch (FileNotFoundException ex) {
			log.error("Errore deleteInitialCarriageReturn", ex);
		} catch (IOException ex) {
			log.error("Errore deleteInitialCarriageReturn", ex);
		}
		return false;
	}
	
}
