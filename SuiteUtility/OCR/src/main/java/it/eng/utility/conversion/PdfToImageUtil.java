package it.eng.utility.conversion;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author jravagnan
 * classe che converte i pdf in immagini TIFF utilizzando jpedal come libreria
 * sostituir� le precedenti versioni che utilizzavano le librerie ghostview
 * @author jravagnan
 *
 */
public class PdfToImageUtil {
	
	private static Logger aLogger = Logger.getLogger(PdfToImageUtil.class.getName());	
	private static final String TIFF_FORMAT = "tif";
	

	/**
	 * converte un file pdf in un file formato immagine(tif,jpg,png)
	 * @param pdfFileName
	 * @param imageFormat
	 * @return
	 */
	public static List<File> convertToImage(File pdfFile, String imageFormat){
		try {
			ConvertPagesToHiResImages converter = new ConvertPagesToHiResImages();
			return  converter.convertiPdf(pdfFile,imageFormat);
		} catch (Exception e) {
			aLogger.error("Impossibile convertire il file");
		}
		return null;
	}

//	public static List<File> convertToImageLowRes(File pdfFile, String imageFormat){
//		try {
//			ConvertPagesToImages converter = new ConvertPagesToImages();
//			return converter.convertiPdf(pdfFile, imageFormat);
//		} catch (Exception e) {
//			aLogger.error("Impossibile convertire il file");
//		}
//		return null;
//
//	}
	
	/**
	 * converte un file pdf in una lista di file tiff
	 * @param pdfFileName
	 * @return
	 */
	public static List<File> convertToTiffHiRes(File pdfFile) throws IOException{
		aLogger.debug("metodo d'utilita' per la conversione di pdf in una lista di file tiff alta risoluzione");
		return convertToImage(pdfFile,TIFF_FORMAT); 
	}
	
	/**
	 * converte un file pdf in una lista di file tiff a bassa risoluzione
	 * @param pdfFileName
	 * @return
	 */
//	public static List<File> convertToTiffLowRes(File pdfFile){
//		aLogger.debug("metodo d'utilita' per la conversione di pdf in una lista di file tiff bassa risoluzione");
//		return convertToImageLowRes(pdfFile,TIFF_FORMAT); 
//	}
	
	/**
	 * converte un file pdf in una lista di file tiff
	 * prova prima la conversione in alta risoluzione
	 * poi, se va in OutOfMemory, prova in bassa risoluzione
	 * @param pdfFileName
	 * @return
	 * @throws IOException 
	 */
	public static List<File> convertToTiff(File pdfFile) throws IOException{
		aLogger.debug("metodo d'utilita' per la conversione di pdf in una lista di file tiff");
		List<File> ret = null;
		ret=convertToTiffHiRes(pdfFile);
		// se la risoluzione in alta definizione non funziona provo quella in bassa risoluzione
		//if (ret==null)
		//ret = convertToTiffLowRes(pdfFile);	
		return ret;
	}
			
//	/**
//	 * main di test
//	 * @param args
//	 * @throws IOException 
//	 */
//	public static void main(String[] args) throws IOException {
//		String pdfFileName = "C:\\doc lavoro\\doc_pdf_test\\Prot 2012374915_errore.pdf";
//		List<File> files = convertToTiff(pdfFileName);
//		System.out.println(files.size());
//		for (File file : files){
//			System.out.println(file.getAbsolutePath());
//	}
//	}
	

}
