package it.eng.hybrid.module.jpedal.util;

import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.sf.jsignpdf.AbstractSigner;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfReader;

public class PdfUtils {

	public final static Logger logger = Logger.getLogger(PdfUtils.class);
	
	public static boolean isPdf( String filePath) {
		try {
			System.out.println("Verifico se il file " + filePath +"è un pdf ");
			PdfReader reader = new PdfReader( filePath );
			return true;
		} catch(Exception e){
			//e.printStackTrace();
			return false;
		}
	}
	
	public static AbstractSigner isFirmato( String filePath) {
		File file = new File(filePath);
		System.out.println("Verifico il file " + filePath);
		AbstractSigner signer;
		boolean fileFirmato = false;
		try{
			signer = SignerUtil.newInstance().getSignerManager(file);
			logger.info("Invoco il metodo isSignedType del signer " + signer);
			//verifico se il file è firmato
			//se è firmato provo a sbustarlo
			fileFirmato = signer.isSignedType(file);
			logger.info("is pdf firmato " + fileFirmato);
			
			signer.setFile( file );
			
			//InputStream stream = signer.getContentAsInputStream();
			//outputFileName = filePath.replace(".p7m", ".pdf");
			//System.out.println("outputFileName " + outputFileName );
			//IOUtils.copyLarge(stream, new FileOutputStream( outputFileName ));
			
			return signer;
		} catch(Exception e){
			//e.printStackTrace();
			System.out.println("Errore " + e.getMessage());
			return null;
		}
	}
	
	public static String sbustaPdf(AbstractSigner signer, String filePath){
		boolean fileFirmato = false;
		String outputFilePath = null;
		try{
			fileFirmato = signer.isSignedType(signer.getFile());
			logger.info("is pdf firmato " + fileFirmato);
			
			InputStream stream = signer.getContentAsInputStream();
			if( stream!=null){
				File f = new File(filePath);
				
				File directory = new File( f.getParentFile().getAbsolutePath() + "/output/" );
				if( !directory.exists() )
					directory.mkdir();
				
				outputFilePath = f.getParentFile().getAbsolutePath() + "/output/" + FileUtility.rename(f.getName(),"pdf");
				File outputFile = new File(outputFilePath);
				logger.info("outputFilePath " + outputFilePath );
				
				FileOutputStream out = new FileOutputStream( outputFile );
				IOUtils.copyLarge(stream, out);
				out.flush();
				out.close();
			} else {
				logger.info("Stream null ");
			}
			return outputFilePath;
		} catch(Exception e){
			//e.printStackTrace();
			logger.info("Errore " + e.getMessage());
			return outputFilePath;
		}
	}
	
//	public static void main(String[] args) {
//		//String filePath="/sviluppo/eclipse-indigo/workspace/jPedal/samplefiles/Nuovo documento.log";
//		//String filePath="/sviluppo/eclipse-indigo/workspace/jPedal/samplefiles/Cartella-Clinica.pdf";
//		//String filePath="/sviluppo/eclipse-indigo/workspace/jPedal/samplefiles/Alfresco-Implementing-Document-Management-Sample-Chapter.pdf.p7m";
//		//String filePath="/sviluppo/eclipse-indigo/workspace/jPedal/samplefiles/lettera a fornitori ASC.pdf.p7m";
//		String filePath="/sviluppo/eclipse-indigo/workspace/jPedal/samplefiles/NuovoBandoMis121anno2010doc.p7m";
//		boolean isFileFirmato = false;
//		boolean isPdf = false;
//		String outputFilePath = null;
//		try {
//			outputFilePath = isFirmato(filePath);
//			if( outputFilePath!=null )
//				isFileFirmato = true;
//			
//			System.out.println("isFileFirmato " + isFileFirmato );
//			if( isFileFirmato ){
//				System.out.println("Verifico il file sbustato");
//				isPdf = isPdf( outputFilePath );
//			} else {
//				System.out.println("Verifico il file originale");
//				isPdf = isPdf( filePath );
//			}
//			System.out.println("isPdf " + isPdf );
//			
//		} catch (Exception e) {
//			//e.printStackTrace();
//		}
//	}
}
