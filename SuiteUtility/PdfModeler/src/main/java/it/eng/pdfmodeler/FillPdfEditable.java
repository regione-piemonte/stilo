package it.eng.pdfmodeler;

import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.pdfmodeler.exception.PdfModelerException;
import it.eng.xml.XmlUtilityDeserializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class FillPdfEditable {

	private PdfReader mPdfReader;
	private SezioneCache mSezioneCache;

	private static Logger mLogger = Logger.getLogger(FillPdfEditable.class);

	public FillPdfEditable(byte[] pByteContent) throws IOException{
		mPdfReader = new PdfReader(pByteContent);
	}

	public FillPdfEditable(InputStream pInputStreamContent) throws IOException{
		mPdfReader = new PdfReader(pInputStreamContent);
	}

	public FillPdfEditable(String pStrPdfLocation) throws IOException{
		mPdfReader = new PdfReader(pStrPdfLocation);
	}

	public void fillPdf(SezioneCache pSezioneCache, OutputStream pOutputStream) throws PdfModelerException{
		mSezioneCache = pSezioneCache;
		fill(pOutputStream);
	}

	public void fillPdf(String pStrXmlIn, OutputStream pOutputStream) throws Exception {
		mSezioneCache = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(pStrXmlIn));
		fill(pOutputStream);
	}

	private void fill(OutputStream pOutputStream) throws PdfModelerException{
		PdfStamper stamper = null;
		try {
			stamper = new PdfStamper(mPdfReader, pOutputStream);
			AcroFields form = stamper.getAcroFields();
			for (Variabile lVariabile : mSezioneCache.getVariabile()){
				String[] checkboxstates = form.getAppearanceStates(lVariabile.getNome());
				//Sono variabili normali
				if (checkboxstates==null || checkboxstates.length!=2){
					form.setField(lVariabile.getNome(), lVariabile.getValoreSemplice());
				} else {
					try {
						Integer lInteger = Integer.valueOf(lVariabile.getValoreSemplice());
						boolean lBoolValue;
						if (lInteger == null || lInteger == 0){
							lBoolValue = false;
						} else lBoolValue = true;
						if (lBoolValue){
							form.setField(lVariabile.getNome(), checkboxstates[1]);
						} else {
							form.setField(lVariabile.getNome(), checkboxstates[0]);
						}
					} catch (Exception e){
						mLogger.error("Errore nella gestione della variabile " + lVariabile.getNome());
					}
				}
			}
		}
		catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			throw new PdfModelerException(e);
		} finally {
			if (stamper != null)
				try {
					stamper.close();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mPdfReader.close();
		}
	}
}
