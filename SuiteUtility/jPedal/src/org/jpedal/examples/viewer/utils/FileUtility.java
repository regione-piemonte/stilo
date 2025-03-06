package org.jpedal.examples.viewer.utils;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import net.sf.jsignpdf.SignerType;

import org.apache.commons.lang.StringUtils;
import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.utils.LogWriter;


public class FileUtility {

	public static final String[] SigExt={"tsd","p7m"};
	
	public static String getOutputFileName(File inputFile, PreferenceManager preferenceManager){
		String outputFileName = "";
		String inputFileName = "";
		if( inputFile!=null){
			inputFileName = inputFile.getName();
		}
		
		String tipoBusta = "";
		try {
			tipoBusta = preferenceManager.getConfiguration().getString( ConfigConstants.ENVELOPE_DEFAULT_TYPE );
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( tipoBusta.equalsIgnoreCase( SignerType.CAdES_BES.name() ) || tipoBusta.equalsIgnoreCase( SignerType.P7M.name() ) ){
			String nomeSenzaEstensione = inputFileName.substring(0, inputFileName.lastIndexOf("."));
			outputFileName = nomeSenzaEstensione + "_signed.p7m";
		} else if( tipoBusta.equalsIgnoreCase( SignerType.PDF.name() ) ){
			String nomeSenzaEstensione = inputFileName.substring(0, inputFileName.lastIndexOf("."));
			outputFileName = nomeSenzaEstensione + "_signed.pdf";
		}
		return outputFileName;
	}
	
	public static String getOutputFileNameToReturn(String inputFile, PreferenceManager preferenceManager){
		String outputFileName = "";

		String inputFileName = "";
		if( inputFile!=null)
			inputFileName = inputFile;
		else {
			try {
				inputFileName = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		outputFileName = rename(inputFileName,"pdf");
			
		
		return outputFileName;
	}
	
	public static String rename( String fileName,String extension){

		String[] tokens=StringUtils.split(fileName, '.');
		String nomeFile=tokens[0];//file with non estension

		List<String> estFirme=Arrays.asList(SigExt);

		boolean extPresent=false;//indica se l'estensioen richiesta è presente
		boolean extSigPresent=false;// indica se è presente una estensione firma
		
		for (String token : tokens) {
			if(nomeFile.equals(token)){
				//skip first element
				continue;
			}
			//se trovi l'estensione ritorna il nome del file senza rinominare
			if(token.equalsIgnoreCase(extension)){
				//estensione presente
				extPresent=true;
				//break;
			}
			//se trovi una firma   inserisci l'estensione prima della firma
			if(estFirme.contains(token)){
				// se non hai già inserito l'estensione
				//dovrai inserirla quì;
				if(!extSigPresent && !extPresent && !nomeFile.endsWith( token ))
					nomeFile+="."+token+"."+extension;
					//nomeFile+="."+extension+"."+token;
				else if(!nomeFile.endsWith( token ))
					nomeFile+="."+token;//ci sono più estensioni firma e le mantengo
				extSigPresent=true;
			}else if(!nomeFile.endsWith( extension )){
				nomeFile+="."+token;
			}
		}
		if(extPresent){
			if(!nomeFile.endsWith( extension )){
				nomeFile+="."+extension;
			}
			//estensione richiesta presente per cui ritorno il file name orig
			//nomeFile=fileName;
			//nomeFile+="."+extension;
		} else if(!extSigPresent){
			//se non è presente una estensine firma l'est si inserisce alla fine
			nomeFile+="."+extension;
		}
		return nomeFile;
	}
	
	public static void deleteTempDirectory( File directoryPath ){
		File directoryToDelete = new File( directoryPath + "/output" );
		LogWriter.writeLog("deleteTempDirectory " + directoryToDelete, false );
		if( directoryToDelete.exists() && directoryToDelete.isDirectory() ){
			File[] filesToDelete = directoryToDelete.listFiles();
			for(File fileToDelete : filesToDelete){
				if( fileToDelete.isDirectory() ){
					File[] filesToDelete1 = fileToDelete.listFiles();
					for(File fileToDelete1 : filesToDelete1){
						LogWriter.writeLog("Cancello il file " + fileToDelete1, false );
						fileToDelete1.delete();
					}
					LogWriter.writeLog("Cancello la directory " + fileToDelete, false );
					fileToDelete.delete();
				} else if( fileToDelete.isFile() ){
					LogWriter.writeLog("Cancello il file " + fileToDelete, false );
					fileToDelete.delete();
				}
			}
			LogWriter.writeLog("Cancello la directory " + directoryToDelete, false );
			directoryToDelete.delete();
		}
	}
	
	
}
