package it.eng.client.applet.util;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.pdf.PdfReader;

import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.common.type.SignerType;

public class FileUtility {

	public static final String[] SigExt={"tsd","p7m"};
	
	public static String getOutputFileName(File inputFile, String tipoBusta){
		String outputFileName = "";
		String inputFileName = "";
		if( inputFile!=null){
			inputFileName = inputFile.getName();
		}
		
		if( tipoBusta.equalsIgnoreCase( SignerType.CAdES_BES.name() ) || tipoBusta.equalsIgnoreCase( SignerType.P7M.name() ) ){
			String nomeSenzaEstensione = inputFileName;
			if( inputFileName.contains(".") )
				nomeSenzaEstensione = inputFileName.substring(0, inputFileName.lastIndexOf("."));
			outputFileName = nomeSenzaEstensione + "_signed.p7m";
		} else if( tipoBusta.equalsIgnoreCase( SignerType.PDF.name() ) ){
			String nomeSenzaEstensione = inputFileName;
			if( inputFileName.contains(".") )
				nomeSenzaEstensione = inputFileName.substring(0, inputFileName.lastIndexOf("."));
			outputFileName = nomeSenzaEstensione + "_signed.pdf";
		} else if( tipoBusta.equalsIgnoreCase( SignerType.XAdES.name() ) ){
			String nomeSenzaEstensione = inputFileName;
			if( inputFileName.contains(".") )
				nomeSenzaEstensione = inputFileName.substring(0, inputFileName.lastIndexOf("."));
			outputFileName = nomeSenzaEstensione + "_signed.xml";
		}
		return outputFileName;
	}
	
	public static String getOutputFileNameToReturn(String inputFile, String tipoBusta){
		String outputFileName = "";

		String inputFileName = "";
		if( inputFile!=null)
			inputFileName = inputFile;
		else 
			inputFileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME );
				
		if( tipoBusta.equalsIgnoreCase( SignerType.CAdES_BES.name() ) || tipoBusta.equalsIgnoreCase( SignerType.P7M.name() ) ){
			outputFileName = rename(inputFileName,"p7m");
		} else if( tipoBusta.equalsIgnoreCase( SignerType.PDF.name() ) ){
			outputFileName = rename(inputFileName,"pdf");
		} else if( tipoBusta.equalsIgnoreCase( SignerType.XAdES.name() ) ){
			outputFileName = rename(inputFileName,"xml");
		}
		return outputFileName;
	}
	
	public static String rename( String fileName,String extension){

		System.out.println(fileName);
		String[] tokens=StringUtils.split(fileName, '.');
		String nomeFile=fileName;
		if( tokens.length>0)
			nomeFile = tokens[0];//file with non estension

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
			// MOD_2017-09-28_RDM2338
			if(estFirme.contains(token.toLowerCase())){
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
			// MOD_2017-09-28_RDM2338
			if(!nomeFile.toLowerCase().endsWith( extension.toLowerCase() )){
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
	
	public static FileBean valorizzaFile(String tipoFirma, boolean isFileFirmato, FileBean fileBean){
		if( tipoFirma.equals( SignerType.CAdES_BES.name()) ||  tipoFirma.equals(SignerType.P7M.name()) ){
			LogWriter.writeLog("E' stata richiesta la firma Cades o P7m");
			if( !fileBean.getIsPdf() ){
				LogWriter.writeLog("Il file non è pdf");
				if( !isFileFirmato ) {
					LogWriter.writeLog("Il file non è firmato");
					fileBean.setIsFirmato( false );
					boolean isPdfConversionEnabled = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CADES_PDFCONVERSION );
					LogWriter.writeLog("Proprietà " + PreferenceKeys.PROPERTY_SIGN_CADES_PDFCONVERSION + "=" + isPdfConversionEnabled);
					if( isPdfConversionEnabled ){
						LogWriter.writeLog("Tento di convertire il file in pdf");
						
						File outputfile = null;
						try {
							outputfile = WSClientUtils.sbustaEConvertiFile( fileBean.getFile(), fileBean.getFileName() );
						} catch (Exception e) {
							LogWriter.writeLog("La Conversione in pdf non può essere effettuata, verrà firmato il file originale");
						}
						if( outputfile!=null ){
							LogWriter.writeLog("La Conversione in pdf è stata effettuata, verrà firmato il file convertito");
							fileBean.setFile( outputfile );
							fileBean.setIsPdf(true);
							fileBean.setFileNameConvertito( outputfile.getName() );
						} else {
							LogWriter.writeLog("La Conversione in pdf non può essere effettuata, verrà firmato il file originale");
						}
					} else {
						LogWriter.writeLog("La Conversione in pdf non è abilitata o non può essere effettuata, verrà firmato il file originale");
					}
				} else {
					LogWriter.writeLog("Il file da firmare è già firmato, verrà effettuata la firma sul file originale");
					fileBean.setIsFirmato(true);
				}
			} else {
				fileBean.setIsPdf(true);
				LogWriter.writeLog("Il file e' pdf, verrà effettuata la firma sul file pdf");
				if( isFileFirmato ) {
					LogWriter.writeLog("Il file da firmare e' già firmato");
					fileBean.setIsFirmato(true);
				} else {
					LogWriter.writeLog("Il file da firmare non e' già firmato");
					fileBean.setIsFirmato( false );
				}
			}
		} else if( tipoFirma.equals( SignerType.XAdES.name() ) ){
			LogWriter.writeLog("E' stata richiesta la firma Xades"); 
			fileBean.setIsFirmato( false );
			LogWriter.writeLog("La Conversione in pdf non è abilitata o non può essere effettuata, verrà firmato il file originale");
		} else if( tipoFirma.equals( SignerType.PDF.name() ) ){
			LogWriter.writeLog("E' stata richiesta la firma PDF");
			if( !fileBean.getIsPdf() ){
				LogWriter.writeLog("Il file non è pdf");
				LogWriter.writeLog("Tento di convertire il file in pdf");
				
				File outputfile = null;
				try {
					outputfile = WSClientUtils.sbustaEConvertiFile( fileBean.getFile(), fileBean.getFileName() );
				} catch (Exception e) {
					//LogWriter.writeLog("La Conversione in pdf non può essere effettuata, firma non possibile");
				}
				if( outputfile!=null ){
					LogWriter.writeLog("La Conversione in pdf è stata effettuata, verrà firmato il file convertito");
					fileBean.setFile( outputfile );
					fileBean.setIsPdf( true );
					fileBean.setFileNameConvertito( outputfile.getName() );
					if( isFileFirmato ){
						LogWriter.writeLog("Il file è già firmato");
						fileBean.setIsFirmato(true);
					} else {
						LogWriter.writeLog("Il file non è già firmato");
						fileBean.setIsFirmato( false );
					}
				} else {
					LogWriter.writeLog("La Conversione in pdf non può essere effettuata, firma non possibile");
					fileBean = null;
				}
			} else{
				fileBean.setIsPdf(true);
				LogWriter.writeLog("Il file è pdf, verrà firmato il file originale");
				if( isFileFirmato ){
					LogWriter.writeLog("Il file è già firmato");
					fileBean.setIsFirmato(true);
				} else {
					LogWriter.writeLog("Il file non è già firmato");
					fileBean.setIsFirmato( false );
				}
			}
		}
		
		if( fileBean!=null ){
			//String outputFileName = FileUtility.getOutputFileName( fileBean.getFile(), tipoFirma );
			String outputFileName = UUID.randomUUID().toString();
			fileBean.setOutputFile( new File( fileBean.getFile().getParentFile() + "/output/" + outputFileName) );
			LogWriter.writeLog("outputFile " + fileBean.getOutputFile() );
		}
		return fileBean;
	}
	
	public static boolean isPdf(File file){
		try{
			InputStream is = FileUtils.openInputStream(file);
			PdfReader reader = new PdfReader(is);
			reader.close();
			IOUtils.closeQuietly(is);
			return true;
		} catch(Exception e){
			//se non riesci a leggerlo non è un pdf
			return false;
		}
	}
	
}
