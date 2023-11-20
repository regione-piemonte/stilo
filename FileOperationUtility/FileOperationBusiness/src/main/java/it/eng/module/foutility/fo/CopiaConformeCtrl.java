/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.config.ConfigUtil;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.CodificaTimbro;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputCopiaConformeType;
import it.eng.module.foutility.beans.generated.InputFile;
import it.eng.module.foutility.beans.generated.InputFileOperationType;
import it.eng.module.foutility.beans.generated.InputTimbroType;
import it.eng.module.foutility.beans.generated.MappaParametri;
import it.eng.module.foutility.beans.generated.MappaParametri.Parametro;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.PaginaTimbro;
import it.eng.module.foutility.beans.generated.PosizioneRispettoAlTimbro;
import it.eng.module.foutility.beans.generated.PosizioneTimbroNellaPagina;
import it.eng.module.foutility.beans.generated.ResponseCopiaConformeType;
import it.eng.module.foutility.beans.generated.TestoTimbro;
import it.eng.module.foutility.beans.generated.TipoPagina;
import it.eng.module.foutility.beans.generated.TipoRotazione;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CheckPdfUtil;
import it.eng.module.foutility.util.CopiaConformeUtils;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.exception.OpenOfficeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Hashtable;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itextpdf.text.DocumentException;
import com.sun.mail.handlers.image_gif;


public class CopiaConformeCtrl extends AbstractFileController {

	public static final  Logger log = LogManager.getLogger( CopiaConformeCtrl.class );

	public String operationType;
	
	// chiave identificativa della operazione
	public static final String CopiaConformeCtrlCode=CopiaConformeCtrl.class.getName();
	
	public PosizioneTimbroNellaPagina posizioneDefault;
	public TipoRotazione rotazioneDefault;
	public TipoPagina tipoPaginaDefault;
	public String outputFileName;
		
	@Override
	public boolean execute( InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey ) {
		ResponseCopiaConformeType response = new ResponseCopiaConformeType();
		boolean ret=false;
		log.debug(requestKey + " Metodo execute di CopiaConformeCtrl - input " + input + " output "+ output);
		
		if(customInput instanceof InputCopiaConformeType){
			
			File inputFile  = output.getPropOfType( PdfConvCtrl.CONVERTED_FILE, File.class );
			log.debug(requestKey + " File convertito " + inputFile);
				
			if( inputFile==null ){
				
				log.debug(requestKey + " - file convertito non trovato, provo a usare uso il file staticizzato");
				File fileStaticizzato = output.getPropOfType(FormatRecognitionCtrl.FILE_STATICIZZATO, File.class);
				if (fileStaticizzato == null) {
					log.debug(requestKey + " - file fileStaticizzato non trovato, provo a usare uso il file privo di commenti");
					File fileSenzaCommenti = output.getPropOfType(FormatRecognitionCtrl.FILE_SENZA_COMMENTI, File.class);
					
					if( fileSenzaCommenti==null ){
						log.debug("file privo di commenti non trovato, provo a usare uso il file estratto");
						inputFile = output.getPropOfType( UnpackCtrl.EXTRACTED_FILE, File.class );
						log.debug("File estratto " + inputFile);
						if( inputFile==null ){
							log.warn("file sbustato non trovato, uso il file di input");
							inputFile = input.getInputFile();
							log.debug("Uso il file di input per la funzione di timbro");
						} else {
							log.debug("Uso il file sbustato per la funzione di timbro");
						}
					} else {
						log.debug("Uso il file senza commenti per la funzione di timbro");
						inputFile = fileSenzaCommenti;
					}
				} else {
					log.debug("Uso il file staticizzato per la funzione di timbro");
					inputFile = fileStaticizzato;
				}
				log.info(requestKey + " - Elaborazione file " + inputFile);
								
				MimeType mimeType = output.getPropOfType( FormatRecognitionCtrl.DETECTED_MIME, MimeType.class);
				log.debug(requestKey + " - mimeType ricavato per il file " + mimeType);
				
				// verifico che il mimetype corrisponda a quello dei pdf
				try {
					String presenzaXform = output.getPropOfType( FormatRecognitionCtrl.FILE_XFORM, String.class);
					log.debug("presenzaXform ricavato per il file " + presenzaXform);
					
					if(presenzaXform!=null && presenzaXform.equalsIgnoreCase( "true" )){
						log.error("errore, formato file pdf editabile - non timbrabile");
						output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_PDFXFORM, VerificationStatusType.ERROR);
						output.addResult( CopiaConformeCtrlCode, response );
						return ret;
					}
					if(mimeType==null || !mimeType.match( "application/pdf" )){
						log.error("errore, formato file non pdf");
						output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF, VerificationStatusType.ERROR);
						output.addResult( CopiaConformeCtrlCode, response );
						return ret;
					}
				} catch (MimeTypeParseException e) {
					log.error("errore, formato file non pdf");
					output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF, VerificationStatusType.ERROR);
					output.addResult( CopiaConformeCtrlCode, response );
					return ret;
				}
			} else {
				log.debug("Uso il file convertito per la funzione di timbro");
			}
			
			
			log.info("Elaborazione file " + inputFile );
			
			InputCopiaConformeType itt=((InputCopiaConformeType) customInput);
			if( itt!=null && inputFile!=null ){
				
				String testoIntestazione = null;
				if( itt.getIntestazione()!=null && itt.getIntestazione().getTesto()!=null ){
					testoIntestazione = getTesto( itt.getIntestazione().getTesto() );
				}
				log.debug("testoIntestazione: " + testoIntestazione );
				if (testoIntestazione==null || testoIntestazione.equalsIgnoreCase("")) {
					log.error("errore, parametro testo del timbro non presente");
					output.addError(response, FileOpMessage.TIMBRO_OP_MISSING_TEXT, VerificationStatusType.ERROR);
					output.addResult( CopiaConformeCtrlCode, response );
					return ret;
				}
				
				File fileImmagine = null;
				if( itt.getImmagine()!=null && itt.getImmagine().getFileStream()!=null){
					log.debug(requestKey + " - Ho ricevuto il file immagine per stream");
					//read File from DataHandler
					DataHandler dh=itt.getImmagine().getFileStream();
					FileOutputStream lFileOutputStream;
					try {
						fileImmagine = File.createTempFile("input", ".tmp" , inputFile.getParentFile());
						lFileOutputStream = new FileOutputStream(fileImmagine);
						InputStream is = dh.getInputStream();
						IOUtils.copyLarge(is, lFileOutputStream);
						if (is != null )
							is.close();
						if (lFileOutputStream != null)
							lFileOutputStream.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						log.error(requestKey + " - Errore nel recupero dell'immagine per la copia conforme", e);
					}
				}
				
				PosizioneTimbroNellaPagina posizioneTimbro = null;
				if( itt.getPosizione()!=null ){
					posizioneTimbro = itt.getPosizione();
					log.debug("posizione timbro nella pagina: " + posizioneTimbro.value() );
				} else {
					log.info("Prelevo la posizione del timbro di default: " + posizioneDefault.value() );
					posizioneTimbro = posizioneDefault;
				}
				
				TipoRotazione rotazioneTimbro = null;
				if( itt.getRotazione()!=null ){
					rotazioneTimbro = itt.getRotazione();
					log.debug("rotazione timbro nella pagina: " + rotazioneTimbro.value() );
				} else {
					log.info("Prelevo la rotazione del timbro di default: " + rotazioneDefault.value() );
					rotazioneTimbro = rotazioneDefault;
				}
				
				PaginaTimbro paginaTimbro = itt.getPagina();
				if( !isSetPaginaTimbro(paginaTimbro ) ){
					log.info("Prelevo la pagina di default da timbrare: " + tipoPaginaDefault.value() );
					paginaTimbro = new PaginaTimbro();
					paginaTimbro.setTipoPagina( tipoPaginaDefault );
				}
				
				Float ampiezzaRiquadro = null;
				if( itt.getAmpiezzaRiquadro()!=null  ){
					ampiezzaRiquadro = itt.getAmpiezzaRiquadro().floatValue();
				} else {
					ampiezzaRiquadro = 25f;
				}
				log.debug("ampiezzaRiquadro: " + ampiezzaRiquadro );
							
				String testoAggiuntivo = null;
				if( itt.getTestoAggiuntivo()!=null && itt.getTestoAggiuntivo().getTesto()!=null ){
					testoAggiuntivo = itt.getTestoAggiuntivo().getTesto();
				} 
				log.debug("testoAggiuntivo: " + testoAggiuntivo );
				
				try {
					CopiaConformeUtils timbroConformitaUtils = (CopiaConformeUtils) FileoperationContextProvider.getApplicationContext().getBean("CopiaConformeUtils");
					String outputFile = inputFile.getName().replace(".", outputFileName+".");
					
					//Gestione inserita per i pdf MultiLayer che devono essere appiattiti in fase di timbro
					File fileDatimbrare = CheckPdfUtil.manageMultiLayerPdf(inputFile, "application/pdf");
					
					File fileTimbrato = timbroConformitaUtils.aggiungiTimbro( fileDatimbrare, paginaTimbro, posizioneTimbro, rotazioneTimbro,
							testoIntestazione, testoAggiuntivo, fileImmagine, ampiezzaRiquadro, input.getTemporaryDir().getAbsolutePath() , outputFile );
					if( fileTimbrato!=null ){
						log.debug(requestKey + " - File timbrato " + fileTimbrato );
						ret = true;
						output.setFileResult( fileTimbrato );
						response.setVerificationStatus(VerificationStatusType.OK);
					} else {
						response.setVerificationStatus(VerificationStatusType.KO);
					}
				} catch (FileNotFoundException e) {
					log.error("errore ..",e);
					output.addError(response, FileOpMessage.TIMBRO_OP_ERROR, VerificationStatusType.KO, e.getMessage());
				} catch (UnsupportedEncodingException e) {
					log.error("errore ..",e);
					output.addError(response, FileOpMessage.TIMBRO_OP_ERROR,VerificationStatusType.KO, e.getMessage());
				} catch (DocumentException e) {
					log.error("errore ..",e);
					output.addError(response, FileOpMessage.TIMBRO_OP_ERROR,VerificationStatusType.KO, e.getMessage());
				} catch (Exception e) {
					log.error("errore ..",e);
					if( e.getMessage().equalsIgnoreCase( FileOpMessage.TIMBRO_OP_PDF_PASSWORD )){
						output.addError(response, FileOpMessage.TIMBRO_OP_PDF_PASSWORD,VerificationStatusType.KO);
					} else {
						output.addError(response, FileOpMessage.TIMBRO_OP_ERROR,VerificationStatusType.KO, e.getMessage());
					}
				} 
			}
			else {
				log.error("errore, formato file non pdf");
				output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF,VerificationStatusType.KO);
			}
		}

		output.addResult( CopiaConformeCtrlCode, response );
		return ret;
	}

	private boolean isPosizioneValida(PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro, PosizioneRispettoAlTimbro posizioneTesto){
		if( posizioneTesto!=null && rotazioneTimbro.equals( TipoRotazione.ORIZZONTALE ) ){
			if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOPRA) && 
					(posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN) ) )
				return false;
			if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOTTO) && 
					(posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX) ) )
				return false;
		} 
		if( posizioneTesto!=null && rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
			if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOPRA)  )
				return false;
		} 
		return true;
	}
	
	private boolean isPosizioneValida(PosizioneRispettoAlTimbro posizioneIntestazione, PosizioneRispettoAlTimbro posizioneTestoInChiaro){
		if( posizioneIntestazione!=null && posizioneTestoInChiaro!=null && posizioneIntestazione.equals(posizioneTestoInChiaro) ){
			return false;
		}
		return true;
	}
	
	private boolean isSetPaginaTimbro(PaginaTimbro paginaTimbro){
		if( paginaTimbro==null)
			return false;
		if( paginaTimbro.getPagine()==null && paginaTimbro.getTipoPagina()==null )
			return false;
		if( paginaTimbro.getPagine()!=null && paginaTimbro.getPagine().getPagina().size()==0 && 
				paginaTimbro.getPagine().getPaginaA()==0 && paginaTimbro.getPagine().getPaginaDa()==0 )
			return false;
		if( paginaTimbro.getPagine()!=null && paginaTimbro.getPagine().getPagina().size()>0 && 
				paginaTimbro.getPagine().getPagina().get(0).equals(0) )
			return false;
			
		return true;
	}
	
	private String getTestoFromTemplate(String templateName, Hashtable<String,String> params){
		Velocity.init();
		VelocityContext context = new VelocityContext();
		
		Template template =  null;
		template = Velocity.getTemplate(templateName);
		Iterator<String> chiavi = params.keySet().iterator();
		while ( chiavi.hasNext()) {
			String chiave = chiavi.next();
			context.put( chiave, params.get(chiave) );
		}
		StringWriter writer = new StringWriter();
		template.merge( context, writer );
        
		return writer.toString();
	}
	
	private String getTesto(String testo){
		log.debug("getTesto senza velocity " + testo);
		return testo;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		try {
			ConfigUtil.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Security.addProvider(new BouncyCastleProvider());
		//configuro OpenOffice
		OpenOfficeConfiguration config = (OpenOfficeConfiguration)FileoperationContextProvider.getApplicationContext().getBean("oomanager");
		try {
			OpenOfficeConverter.configure(config);
		} catch (OpenOfficeException e) {
			e.printStackTrace();
		}	
		
		FileOperation inputFileOp = new FileOperation();
		
		InputTimbroType customInput = new InputTimbroType();

		MappaParametri mappaParametri = new MappaParametri();
		Parametro p1 = new Parametro();
		p1.setChiave("firma");
		p1.setValore( "asdasdadasda" );
		mappaParametri.getParametro().add( p1 );
		Parametro p2 = new Parametro();
		p2.setChiave("firma1");
		p2.setValore( "aaaaaaaaaaaaaaaa" );
		mappaParametri.getParametro().add( p2 );
		customInput.setMappaParametri( mappaParametri  );
		
		//imposto la codifica
		customInput.setCodifica( CodificaTimbro.BARCODE_PDF_417  );
		//customInput.setCodifica( CodificaTimbro.BARCODE_DATAMATRIX  );

		//imposto il testo con cui generare il timbro
		TestoTimbro testoTimbro = new TestoTimbro();
		String testo = "";
		for(int i=0; i<1*1850 ; i++ )
			testo = testo + "a";
		//testo = "firma1:$firma1";
		testoTimbro.setTesto( testo );
		//testoTimbro.setTemplate( "prova.vm" );
		customInput.setTestoTimbro( testoTimbro  );

		//imposto su quali pagine mettere il timbro
		PaginaTimbro paginaTimbro = new PaginaTimbro();
		//Pagine pagine = new Pagine();
		//		pagine.getPagina().add( 1 );
		//		pagine.getPagina().add( 2 );
		//pagine.setPaginaDa( 1 );
		//pagine.setPaginaA( 3 );
		//paginaTimbro.setPagine( pagine  );
		paginaTimbro.setTipoPagina( TipoPagina.PRIMA );
		//paginaTimbro.setTipoPagina( TipoPagina.ULTIMA );
		//paginaTimbro.setTipoPagina( TipoPagina.TUTTE );
		customInput.setPaginaTimbro( paginaTimbro );

		//imposto la posizione del timbro nel pdf
		//customInput.setPosizioneTimbro( PosizioneTimbroNellaPagina.ALTO_DX );
		//customInput.setPosizioneTimbro( PosizioneTimbroNellaPagina.ALTO_SN );
		customInput.setPosizioneTimbro( PosizioneTimbroNellaPagina.BASSO_DX );
		//customInput.setPosizioneTimbro( PosizioneTimbroNellaPagina.BASSO_SN );

		//customInput.setRotazioneTimbro( TipoRotazione.ORIZZONTALE  );
		customInput.setRotazioneTimbro( TipoRotazione.VERTICALE  );
		
		TestoTimbro intestazione = new TestoTimbro();
		intestazione.setTesto("Firmato digitalmente da... ");
		customInput.setIntestazioneTimbro( intestazione  );
		customInput.setPosizioneIntestazione( PosizioneRispettoAlTimbro.SOPRA );
		//customInput.setPosizioneIntestazione( PosizioneRispettoAlTimbro.SOTTO );
		//customInput.setPosizioneIntestazione( PosizioneRispettoAlTimbro.INLINEA );
		
		customInput.setTimbroSingolo( false );

		//customInput.setPosizioneTestoInChiaro( PosizioneRispettoAlTimbro.SOPRA );
		//customInput.setPosizioneTestoInChiaro( PosizioneRispettoAlTimbro.SOTTO );
		customInput.setPosizioneTestoInChiaro( PosizioneRispettoAlTimbro.INLINEA );
		//customInput.setPosizioneTestoInChiaro( PosizioneRispettoAlTimbro.SINISTRA );
		
		customInput.setRigheMultiple( true );
		
		InputFileOperationType inputFileOpType = new InputFileOperationType();
		InputFile inputFile = new InputFile();
		DataHandler fileStream =  new DataHandler(new FileDataSource( new File("prova.pdf")));
		inputFile.setFileStream(fileStream);
		inputFileOpType.setInputType( inputFile  );
		inputFileOp.setFileOperationInput( inputFileOpType  );
		Operations operations = new Operations();
		operations.getOperation().add( customInput );
		inputFileOp.setOperations(operations);
				
		OutputOperations output = new OutputOperations();
		FOImpl foImpl = new FOImpl();
		foImpl.execute(inputFileOp);

	}

	public PosizioneTimbroNellaPagina getPosizioneDefault() {
		return posizioneDefault;
	}

	public void setPosizioneDefault(PosizioneTimbroNellaPagina posizioneDefault) {
		this.posizioneDefault = posizioneDefault;
	}

	public TipoRotazione getRotazioneDefault() {
		return rotazioneDefault;
	}

	public void setRotazioneDefault(TipoRotazione rotazioneDefault) {
		this.rotazioneDefault = rotazioneDefault;
	}

	public TipoPagina getTipoPaginaDefault() {
		return tipoPaginaDefault;
	}

	public void setTipoPaginaDefault(TipoPagina tipoPaginaDefault) {
		this.tipoPaginaDefault = tipoPaginaDefault;
	}
	
	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
