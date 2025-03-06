package it.eng.module.archiveUtility;

import java.io.File;

import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;

public interface UnpackArchive {
	
	/**
	 * Metodo per la decompressione di un archivio
	 * 
	 * @param archive
	 * @param outputDir
	 * @return
	 * @throws Exception
	 */
	public DecompressioneArchiviBean unpackArchive(File archive, String outputDir) throws Exception;
	
	/**
	 *  Metodo per il calcolo del numero dei contenuti presenti nell'archivio
	 *  ritorna -1 se qualcosa è andato in errore
	 *
	 * @param archive
	 * @return
	 * @throws Exception
	 */
	public int countElements(File archive) throws Exception;
	
	
}
