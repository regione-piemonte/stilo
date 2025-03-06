package it.eng.client.applet.operation.detector;

import it.eng.common.type.SignerType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

public class PadesDetetector implements EnvelopeDetector {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				e.printStackTrace();
			}
			return null;
		}
	 
	public static void main(String[] args) throws Exception {
		File signedFile= new File("c:/testPades/Alfresco-Implementing-Document-Management-Sample-Chapter_sign.pdf");
		File unsignedFile= new File("c:/testPades/Alfresco-Implementing-Document-Management-Sample-Chapter.pdf");
		PadesDetetector pd= new PadesDetetector();
		SignerType sign=pd.isSignedType(signedFile);
		System.out.println("sign:"+sign);
		InputStream sbust=pd.getContent(signedFile);
		IOUtils.copyLarge(sbust, FileUtils.openOutputStream(new File("c:/testPades/sbustato.pdf")));
	}
}
