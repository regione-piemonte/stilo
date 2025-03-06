package it.eng.hybrid.module.firmaCertificato.detectors;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.common.type.SignerType;

public class PadesDetetector implements EnvelopeDetector {

	public final static Logger logger = Logger.getLogger(PadesDetetector.class);
	
	@Override
	public SignerType isSignedType(File file) {
		SignerType pdf=null;
		if( file==null )
			return null;
		PdfReader reader;
		try {
			InputStream is =FileUtils.openInputStream(file);
			reader = new PdfReader( is );
			AcroFields af = reader.getAcroFields();
			List names = af.getSignatureNames();
			if( names!=null && names.size()>0 )
				pdf=SignerType.PDF;
		} catch (IOException e) {
			logger.info("Errore " + e.getMessage() + " - Il file non � pdf");
		}
		return pdf;
	}
 

	@Override
	public InputStream getContent(File file) throws Exception {
 
		 //TODO rimuovere le firme al momento ritorna il pdf firmato
			try{
				File fileinternal = File.createTempFile("Extrcat", "ext");
				FileUtils.copyFile(file, fileinternal);
				PipedInputStream in = new PipedInputStream();
				PipedOutputStream out = new PipedOutputStream(in);
				CopyThread copy = new CopyThread(fileinternal, out);				
				copy.start();
				return in;
			}catch(Exception e){
				logger.error(e);
			}
			return null;
		}
	 
}
