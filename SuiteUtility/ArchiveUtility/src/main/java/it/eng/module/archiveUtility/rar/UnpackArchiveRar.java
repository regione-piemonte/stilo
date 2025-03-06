package it.eng.module.archiveUtility.rar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.exception.RarException.RarExceptionType;
import com.github.junrar.rarfile.FileHeader;

import it.eng.module.archiveUtility.FileProtetto;
import it.eng.module.archiveUtility.UnpackArchive;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;


public class UnpackArchiveRar implements UnpackArchive {

	private static final Logger log = LogManager.getLogger(UnpackArchiveRar.class);
	
	public DecompressioneArchiviBean unpackArchive(File file, String outputDir) throws Exception {
		log.debug("Gestisco il caso dell'archivio RAR: " + file.getPath());
		
		DecompressioneArchiviBean bean = new DecompressioneArchiviBean();
		
		List<File> extracted = new ArrayList<File>();
		Map<String, String> errorsArchivi = new HashMap<String, String>();
		Map<String, String> errorsFiles = new HashMap<String, String>();
		
		Archive archivio = null;
		try {
			//Se l'archivio � corrotto o protetto da password l'eccezione
			//non viene rilanciata per cui non � possibile raccoglierla
			archivio = new Archive(file);
			
			log.debug("ARCHIVIO PROTETTO? "  + archivio.isEncrypted());
			if( archivio.isEncrypted()==true){
				errorsArchivi.put(file.getName(), "Errore archivio protetto "+ file.getName());
				bean.setErroriArchivi(errorsArchivi);
				bean.setError(true);
				if(archivio!=null )
					archivio.close();
				return bean;
			}
		} catch (RarException e) {
			errorsArchivi.put(file.getName(), "Errore nella decompressionde dell'archivio "+ file.getName());
			log.error("Errore ", e);
			bean.setErroriArchivi(errorsArchivi);
			bean.setError(true);
			return bean;
		} catch (IOException e) {
			log.error("Errore ", e);
			errorsArchivi.put(file.getName(), "Errore nella decompressione dell'archivio "+ file.getName());
			bean.setErroriArchivi(errorsArchivi);
			bean.setError(true);
			return bean;
		} catch (Exception e) {
			log.error("Errore ", e);
			errorsArchivi.put(file.getName(), "Errore nella decompressione dell'archivio "+ file.getName());
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		}

		String path = "";
		boolean isDir = false;
		String esito = null;
		List<FileHeader> fileHeaders = archivio.getFileHeaders();
		for (FileHeader fh : fileHeaders) {
			String fileNameArchivio;

			if (fh.isFileHeader() && fh.isUnicode()) {
				fileNameArchivio = fh.getFileNameW();
			} else {
				fileNameArchivio = fh.getFileNameString();
			}

			if (fileNameArchivio.contains("\\"))
				fileNameArchivio = fileNameArchivio.replace("\\", "/");
			log.debug("fileName file estratto dall'archivio " + fileNameArchivio);
			
			File fileArchivio = new File(outputDir + File.separator + fileNameArchivio);
			log.debug("file estratto dall'archivio " + fileArchivio.getAbsolutePath());
			new File(fileArchivio.getParent()).mkdirs();
			
			log.debug("" + fh.isEncrypted());

			if (fh.isDirectory()) {
				isDir = true;
				fileArchivio.mkdirs();
				path = fileArchivio.getPath();
				log.debug("directory interna all'archivio: " + path);
			} else {
				isDir = false;
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(fileArchivio);
					archivio.extractFile(fh, fos);
					extracted.add( fileArchivio );
					
					// Se il file da estrarre � protetto da password salvo l'eccezione nella mappa errorsFile -> "chiave:nomefile - valore:messaggio"		
					esito = FileProtetto.checkPasswordProtection(fileArchivio);
					if (esito != null) {
						errorsFiles.put(fileArchivio.getName(), esito);
						esito = null;
					}
					
				} catch (RarException e) {
					if (e.getType().equals(RarExceptionType.notImplementedYet)) {
						log.error("error extracting unsupported file: " + fh.getFileNameString());
					}
					log.error("error extracting file: " + fh.getFileNameString());
					errorsArchivi.put(file.getName(), "Errore nella decompressionde dell'archivio "+ file.getName());
				} catch (Exception e) {
					log.error("Errore elaboraRar", e);
					errorsArchivi.put(file.getName(), "Errore nella decompressionde dell'archivio "+ file.getName());
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							log.error("Errore elaboraRar", e);
						}
					}
				}
			}
		}

		if (archivio != null) {
			try {
				archivio.close();
			} catch (IOException e) {
				log.error("Errore elaboraRar", e);
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
		log.debug("Gestisco il caso dell'archivio RAR: " + fileIn.getPath());
		
		String prefixMsgException = "Eccezione UnpackArchiveRar->countElements()." + "File = " + fileIn.getName() + " -> ";
		
		int nElements = 0;
		
		Archive archivio = null;		
		try {
			archivio = new Archive(fileIn);
			log.debug("ARCHIVIO PROTETTO? "  + archivio.isEncrypted());			
			// Se e' protetto restituisco -1
			if( archivio.isEncrypted()==true){
				if(archivio!=null)
					archivio.close();		
				
				return -1;
			}
			
			// Se non e' protetto leggo l'header
			boolean isDir = false;
			List<FileHeader> fileHeaders = archivio.getFileHeaders();
			
			for (FileHeader fh : fileHeaders) {
				// Se e' una directory
				if (fh.isDirectory()) {
					continue;
				} else {
						nElements++;
					}
			}
		} catch (FileNotFoundException e) {
			log.error(prefixMsgException + "Inesistente.", e);
			return -1;
		} catch (RarException e) {
			log.error(prefixMsgException+ "Errore nella decompressione.", e);
			return -1;
		} catch (IOException e) {
			log.error(prefixMsgException + "Protetto da password.", e);
			return -1;
		} catch (Exception e) {
			log.error(prefixMsgException + "Errore nella decompressione.", e);
			return -1;			
		}
		finally {
			if ( archivio != null ) {
				try {
					archivio.close();
				} catch (IOException e) {
					log.error(prefixMsgException + "Error closing archive: " + e);
				}

			}
		}
		return nElements;
	}
	
}
