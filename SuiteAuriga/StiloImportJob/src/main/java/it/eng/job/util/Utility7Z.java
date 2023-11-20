/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Classe di utility che espone i metodi per la compressione, decompressione ed analisi degli archivi 7z
 */
package it.eng.job.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import it.eng.utility.storageutil.util.SharedCompressionUtility;

public class Utility7Z {

	public static List<String> compressTo7Z(List<String> listPathFileIn, String tipoCompressione, boolean sovascritturaArchivio) {
		return compressTo7ZList(listPathFileIn, null, tipoCompressione, sovascritturaArchivio);
	}

	/**
	 * Metodo che esegue la compressione di una lista di file in archivi di tipo 7z
	 * 
	 * @param listPathFileIn
	 *            lista contenente i path dei file che devono essere compressi
	 */
	public static List<String> compressTo7ZList(List<String> listPathFileIn, String pathFileOut, String tipoCompressione, boolean sovascritturaArchivio) {

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
	 * Metodo che esegue la compressione di una lista di file in archivi di tipo 7z
	 * 
	 * @param listPathFileIn
	 *            lista contenente i path dei file che devono essere compressi
	 * @param pathFileOut
	 *            percorso di salvataggio dell'archivio. Se null l'archivio viene salvato direttamente dove si trovava il file di input
	 * @param tipoCompressione
	 *            indica il tipo di compressione che deve essere eseguita. Necessario se si vuole calcolare il percorso e il nome dell'archivio
	 * @param sovascritturaArchivio
	 *            true se deve essere eseguita la sovrascrittura dell'archivio nel caso quest'ultimo sia già presente, false altrimenti
	 * @return true se la compressione è andata a buon fine, false altrimenti
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
				 * Se è impostato che deve essere eseguita la sovrascrittura dell'archivio allora, se già presente un archivio, lo cancello e lo rimpiazzo con
				 * la nuova istanza.
				 */
				if (sovascritturaArchivio) {

					if (new File(pathFileOut).exists()) {
						// L'archivio esiste già. Ne eseguo la cancellazione per eseguire la sovrascrittura
						new File(pathFileOut).delete();
					}
				}

				// Creo l'istanza del file di output che dovrà essere in formato
				// 7z
				try (SevenZOutputFile fileOut = new SevenZOutputFile(new File(pathFileOut))) {
					addToArchiveCompression(fileOut, fileIn);

				} catch (IOException ioe) {

					/*
					 * Poichè è stata generata un eccezione allora ritorno false all'avvenuta compressione
					 */
					return false;
				}
			} else {
				return false;
			}
		} else {

			/*
			 * Poichè il file non esiste allora ritorno false
			 */
			return false;
		}
		return true;
	}

	/**
	 * Metodo che esegue la compressione del file e la creazione del relativo archivio
	 * 
	 * @param fileOut
	 *            l'istanza dell'archivio SevenZip che dovrà essere utilizzata
	 * @param fileIn
	 *            file che viene fornito in input e che deve essere compresso
	 * @throws IOException
	 */
	private static void addToArchiveCompression(SevenZOutputFile fileOut, File fileIn) throws IOException {

		String name = fileIn.getName();

		// Controllo se è un file
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
			in.close();

		} else {
			throw new IOException(fileIn.getName() + " non e un file");
		}
	}

}
