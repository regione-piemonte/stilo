/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Classe di utility che espone i metodi per la compressione, decompressione ed analisi
 * degli archivi 7z
 */
package it.eng.utility.storageutil.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

public class StorageUtil7Z {

	public static List<String> compressTo7Z(List<String> listPathFileIn, String tipoCompressione, boolean sovascritturaArchivio) {
		return compressTo7ZList(listPathFileIn, null, tipoCompressione, sovascritturaArchivio);
	}

	/**
	 * Metodo che esegue la compressione di una lista di file in archivi di tipo
	 * 7z
	 * 
	 * @param listPathFileIn
	 *            lista contenente i path dei file che devono essere compressi
	 */
	public static List<String> compressTo7ZList(List<String> listPathFileIn, String pathFileOut,
			String tipoCompressione, boolean sovascritturaArchivio) {

		List<String> pathFileInError = new ArrayList<String>(); // lista per i
																// path dei file
																// in errore
		// Per ogni file da analizzare
		for (String pathFileIn : listPathFileIn) {

			// Creo l'istanza al file relativo
			File fileIn = new File(pathFileIn);

			if (!compressTo7Z(fileIn, pathFileOut, tipoCompressione, sovascritturaArchivio)) {
				// E andato in errore e quindi lo aggiungo a tale lista
				pathFileInError.add(fileIn.getPath());
			}
		}

		return pathFileInError;
	}

	public static boolean compressTo7Z(File fileIn, String tipoCompressione, boolean sovascritturaArchivio) {
		return compressTo7Z(fileIn, null, tipoCompressione, sovascritturaArchivio);
	}

	/**
	 * Metodo che esegue la compressione di una lista di file in archivi di tipo
	 * 7z
	 * 
	 * @param listPathFileIn lista contenente i path dei file che devono essere compressi
	 * @param pathFileOut percorso di salvataggio dell'archivio. Se null l'archivio viene salvato direttamente dove si trovava il file di input  
	 * @param tipoCompressione indica il tipo di compressione che deve essere eseguita. Necessario se si vuole calcolare il percorso e il nome 
	 * 						   dell'archivio
	 * @param sovascritturaArchivio true se deve essere eseguita la sovrascrittura dell'archivio nel caso quest'ultimo sia gia� presente, false altrimenti
	 * @return true se la compressione e andata a buon fine, false altrimenti
	 * 
	 */
	public static boolean compressTo7Z(File fileIn, String pathFileOut, String tipoCompressione, boolean sovascritturaArchivio) {

		if (fileIn.exists()) {
			// Se il file esiste procedo con la compressione

			if (pathFileOut == null) {
				// Ottengo il path del file di output
				pathFileOut = SharedCompressionUtility.getFileOutputPath(fileIn, tipoCompressione);
			}

			if (pathFileOut != null && pathFileOut != "") {

				
				/*
				 * Se e impostato che deve essere eseguita la sovrascrittura dell'archivio allora, se gia� presente
				 * un archivio, lo cancello e lo rimpiazzo con la nuova istanza. 
				 */
				if(sovascritturaArchivio){
					
					if(new File(pathFileOut).exists()){
						//L'archivio esiste gia�. Ne eseguo la cancellazione per eseguire la sovrascrittura
						new File(pathFileOut).delete();
					}
				}
				
				
				// Creo l'istanza del file di output che dovra� essere in formato
				// 7z
				try {
					
					SevenZOutputFile fileOut = new SevenZOutputFile(new File(pathFileOut)); 
					addToArchiveCompression(fileOut, fileIn);

				} catch (IOException ioe) {

					/*
					 * Poiche e stata generata un eccezione allora ritorno false
					 * all'avvenuta compressione
					 */
					return false;
				}
			} else {
				return false;
			}
		} else {

			/*
			 * Poiche il file non esiste allora ritorno false
			 */
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * Metodo che esegue la compressione del file e la creazione del relativo
	 * archivio
	 * 
	 * @param fileOut
	 *            l'istanza dell'archivio SevenZip che dovra� essere utilizzata
	 * @param fileIn
	 *            file che viene fornito in input e che deve essere compresso
	 * @throws IOException
	 */
	private static void addToArchiveCompression(SevenZOutputFile fileOut, File fileIn) throws IOException {

		String name = fileIn.getName();

		// Controllo se e un file
		if (fileIn.isFile()) {

			// Creo l'archivio comprimendo il file fornito come input
			SevenZArchiveEntry entry = fileOut.createArchiveEntry(fileIn, name);
			fileOut.putArchiveEntry(entry);

			FileInputStream in = new FileInputStream(fileIn);
			byte[] b = new byte[1024];
			int count = 0;
			while ((count = in.read(b)) > 0) {
				fileOut.write(b, 0, count);
			}
			fileOut.closeArchiveEntry();

		} else {
			throw new IOException(fileIn.getName() + " non e un file");
		}
	}
	
	
	/**
	 * Funzione per la decompressione di un archivio 7z
	 * 
	 * @param fileIn l'archivio che si dovr� decomprimere
	 * @param outDir la directory di output in cui verr� salvato il file
	 * @throws Exception nel caso il processo di decompressione non sia andato a buon 
	 * fine oppure non si sia riusciti a salvare il file nella cartella di destinazione
	 */
	public static void decompress7Zip(File fileIn, String outDir) throws Exception {
		
		if(fileIn != null && fileIn.exists() && outDir != null && !outDir.isEmpty()){
			
			SevenZFile sevenZFile = new SevenZFile(fileIn);
	        SevenZArchiveEntry entry;
	        while ((entry = sevenZFile.getNextEntry()) != null){
	            if (entry.isDirectory()){
	                continue;
	            }
	            File curfile = new File(outDir, entry.getName());
	            File parent = curfile.getParentFile();
	            if (!parent.exists()) {
	                parent.mkdirs();
	            }
	            FileOutputStream out = new FileOutputStream(curfile);
	            byte[] content = new byte[(int) entry.getSize()];
	            sevenZFile.read(content, 0, content.length);
	            out.write(content);
	            out.close();
	            sevenZFile.close();
	        }
		}else{
			throw new Exception("File di input o directory di output non valide");
		}
	}

}
