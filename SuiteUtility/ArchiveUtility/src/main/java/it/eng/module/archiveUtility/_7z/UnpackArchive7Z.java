package it.eng.module.archiveUtility._7z;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.eng.module.archiveUtility.FileProtetto;
import it.eng.module.archiveUtility.UnpackArchive;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;

/**
 * 
 * @author FEBUONO
 *
 */

public class UnpackArchive7Z implements UnpackArchive {

	private static final Logger log = LogManager.getLogger(UnpackArchive7Z.class);

	public UnpackArchive7Z() {

	}

	public static void compress(String name, File... files) throws IOException  {
		try (SevenZOutputFile out = new SevenZOutputFile(new File(name))){
			for (File file : files){
				addToArchiveCompression(out, file, ".");
			}
		}
	}

	public DecompressioneArchiviBean unpackArchive(File archive, String outputDir) {
		log.debug("Gestisco il caso dell'archivio 7z: " + archive.getPath());

		DecompressioneArchiviBean bean = new DecompressioneArchiviBean();
		List<File> extracted = new ArrayList<File>();
		Map<String, String> errorsArchivi = new HashMap<String, String>();
		Map<String, String> errorsFiles = new HashMap<String, String>();
		SevenZFile sevenZFile = null;
		try {
			sevenZFile = new SevenZFile(archive);

			SevenZArchiveEntry entry;
			String esito = null;

			while ((entry = sevenZFile.getNextEntry()) != null){
				if (entry.isDirectory()){
					continue;
				}
				File currentfile = new File(outputDir, entry.getName());
				File parent = currentfile.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				FileOutputStream out = new FileOutputStream(currentfile);

				byte[] content = new byte[(int) entry.getSize()];
				sevenZFile.read(content, 0, content.length);
				out.write(content);
				out.close();
				
				// Se il file da estrarre � protetto da password salvo l'eccezione nella mappa errorsFile -> "chiave:nomefile - valore:messaggio"				
				esito =	FileProtetto.checkPasswordProtection(currentfile);
				if (esito != null){
					errorsFiles.put(entry.getName(), esito);
					esito = null;
				}
				
				extracted.add(currentfile);
			}
		} catch (FileNotFoundException e) {
			log.error("Eccezione elabora7Zip, archivio inesistente", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " inesistente");
			bean.setErroriArchivi(errorsArchivi);
			return bean;
		} catch (IOException e) {
			log.error("", e);
			errorsArchivi.put(archive.getName(), "Archivio " + archive.getName() + " protetto da password");
			bean.setErroriArchivi(errorsArchivi);
			bean.setError(true);
			return bean;
		} finally {
			if ( sevenZFile != null ) {
				try {
					sevenZFile.close();
				} catch (IOException e) {
					log.error("Error closing archive: " + e);
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


	private static void addToArchiveCompression(SevenZOutputFile out, File file, String dir) throws IOException {
		String name = dir + File.separator + file.getName();
		if (file.isFile()){
			SevenZArchiveEntry entry = out.createArchiveEntry(file, name);
			out.putArchiveEntry(entry);

			FileInputStream in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int count = 0;
			while ((count = in.read(b)) > 0) {
				out.write(b, 0, count);
			}
			out.closeArchiveEntry();

		} else if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children != null){
				for (File child : children){
					addToArchiveCompression(out, child, name);
				}
			}
		} else {
			System.out.println(file.getName() + " is not supported");
		}
	}

	@Override
	public int countElements(File fileIn) throws Exception {
		log.debug("Gestisco il caso dell'archivio 7z: " + fileIn.getPath());
		
		String prefixMsgException = "Eccezione UnpackArchive7Z->countElements()." + "File = " + fileIn.getName() + " -> ";
		
		int nElements = 0;
		SevenZFile sevenZFile = null;
		try {
			sevenZFile = new SevenZFile(fileIn);

			SevenZArchiveEntry entry;
			while ((entry = sevenZFile.getNextEntry()) != null){
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
		finally {
			if ( sevenZFile != null ) {
				try {
					sevenZFile.close();
				} catch (IOException e) {
					log.error(prefixMsgException + "Error closing archive: " + e);
				}

			}
		}
		return nElements;
	}

}