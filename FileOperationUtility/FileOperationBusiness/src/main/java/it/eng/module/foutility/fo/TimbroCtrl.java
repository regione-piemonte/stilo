/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.config.ConfigUtil;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.CodificaTimbro;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputFile;
import it.eng.module.foutility.beans.generated.InputFileOperationType;
import it.eng.module.foutility.beans.generated.InputTimbroType;
import it.eng.module.foutility.beans.generated.MappaParametri;
import it.eng.module.foutility.beans.generated.MappaParametri.Parametro;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.PaginaTimbro;
import it.eng.module.foutility.beans.generated.PosizioneRispettoAlTimbro;
import it.eng.module.foutility.beans.generated.PosizioneTimbroNellaPagina;
import it.eng.module.foutility.beans.generated.ResponseTimbroType;
import it.eng.module.foutility.beans.generated.TestoTimbro;
import it.eng.module.foutility.beans.generated.TipoPagina;
import it.eng.module.foutility.beans.generated.TipoRotazione;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CheckPdfEditabili;
import it.eng.module.foutility.util.CheckPdfUtil;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.StaticizzazioneEsternaUtil;
import it.eng.module.foutility.util.TimbroUtils;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.exception.OpenOfficeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itextpdf.text.DocumentException;


public class TimbroCtrl extends AbstractFileController {

	public static final  Logger log = LogManager.getLogger( TimbroCtrl.class );

	public String operationType;
	
	// chiave identificativa della operazione
	public static final String TimbroCtrlCode=TimbroCtrl.class.getName();
	
	public CodificaTimbro codificaDefault;
	public PosizioneTimbroNellaPagina posizioneTimbroDefault;
	public TipoRotazione rotazioneTimbroDefault;
	public TipoPagina tipoPaginaDefault;
	public String outputFileName;
		
	@Override
	public boolean execute( InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey ) {
		ResponseTimbroType response = new ResponseTimbroType();
		boolean ret=false;
		log.debug(requestKey + " Metodo execute di TimbroCtrl - input " + input + " output "+ output);
		
		if(customInput instanceof InputTimbroType){
			
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
						
						// verifico se e' attivo il servizio esterno di staticizzazione
						boolean isIntegrazioneServizioStaticizzazioneAttiva = StaticizzazioneEsternaUtil.isAttivaIntegrazioneServizioStaticizzazione();
						log.debug(requestKey + " - Integrazione servizio staticizzazione attiva? " + isIntegrazioneServizioStaticizzazioneAttiva);
						if( isIntegrazioneServizioStaticizzazioneAttiva ){
							//richiamo il servizio
							File fileStatizzatoEsternamente=StaticizzazioneEsternaUtil.richiamaServizio(inputFile);
							
							if( fileStatizzatoEsternamente!=null ){
								inputFile = fileStatizzatoEsternamente;
								output.addProperty(FormatRecognitionCtrl.FILE_STATICIZZATO, fileStatizzatoEsternamente );
							} else {
								log.error("errore, formato file pdf editabile - non timbrabile");
								output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_PDFXFORM, VerificationStatusType.ERROR);
								output.addResult( TimbroCtrlCode, response );
								return ret;
							}
						}
					}
					if(mimeType==null || !mimeType.match( "application/pdf" )){
						log.error("errore, formato file non pdf");
						output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF, VerificationStatusType.ERROR);
						output.addResult( TimbroCtrlCode, response );
						return ret;
					}
				} catch (MimeTypeParseException e) {
					log.error("errore, formato file non pdf");
					output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF, VerificationStatusType.ERROR);
					output.addResult( TimbroCtrlCode, response );
					return ret;
				}
			} else {
				log.debug("Uso il file convertito per la funzione di timbro");
			}
			
			
			log.info("Elaborazione file " + inputFile );
			
			InputTimbroType itt=((InputTimbroType) customInput);
			if( itt!=null && inputFile!=null ){
				
				//recupero dall'xml la lista di parametri
				Hashtable<String, String> params = new Hashtable<String, String>();
				MappaParametri mappaParametri = itt.getMappaParametri();
				if(mappaParametri!=null){
					List<Parametro> listaParametri = mappaParametri.getParametro();
					for(Parametro parametro : listaParametri){
						params.put(parametro.getChiave(), parametro.getValore() );
					}
				}
				
				CodificaTimbro codifica = null;
				if( itt.getCodifica()!=null )
					codifica = itt.getCodifica();
				else {
					log.info("Prelevo la codifica di default " + codificaDefault.value() );
					codifica = codificaDefault;
				}
				log.debug("codifica timbro richiesta: " + codifica.value() );

				String testo = null;
				if( itt.getTestoTimbro().getTesto()!=null )
					testo = getTesto( itt.getTestoTimbro().getTesto(), params );
				else if( itt.getTestoTimbro().getTemplate()!=null ) {
					testo = getTestoFromTemplate( itt.getTestoTimbro().getTemplate(), params );
				}
				log.debug("testo timbro richiesto: " + testo );
				if (testo==null || testo.equalsIgnoreCase("")) {
					log.error("errore, parametro testo del timbro non presente");
					output.addError(response, FileOpMessage.TIMBRO_OP_MISSING_TEXT, VerificationStatusType.ERROR);
					output.addResult( TimbroCtrlCode, response );
					return ret;
				}
				

				PosizioneRispettoAlTimbro posizioneTestoInChiaro = null;
				if( itt.getPosizioneTestoInChiaro()!=null ){
					posizioneTestoInChiaro = itt.getPosizioneTestoInChiaro();
					log.debug("posizione testo in chiaro: " + posizioneTestoInChiaro.value() );
				}
				
				String testoIntestazione = null;
				if( itt.getIntestazioneTimbro()!=null && itt.getIntestazioneTimbro().getTesto()!=null )
					testoIntestazione = getTesto( itt.getIntestazioneTimbro().getTesto(), params );
				else if( itt.getIntestazioneTimbro()!=null && itt.getIntestazioneTimbro().getTemplate()!=null ) {
					testoIntestazione = getTestoFromTemplate( itt.getIntestazioneTimbro().getTemplate(), params );
				}
				log.debug("testoIntestazione: " + testoIntestazione );
				
				PosizioneRispettoAlTimbro posizioneIntestazione = null;
				if( itt.getPosizioneIntestazione()!=null ){
					posizioneIntestazione = itt.getPosizioneIntestazione();
					log.debug("posizione intestazione: " + posizioneIntestazione.value() );
				}
				
				boolean timbroSingolo = itt.isTimbroSingolo();
				log.debug("timbroSingolo? " + timbroSingolo );
				
				PosizioneTimbroNellaPagina posizioneTimbro = null;
				if( itt.getPosizioneTimbro()!=null ){
					posizioneTimbro = itt.getPosizioneTimbro();
					log.debug("posizione timbro nella pagina: " + posizioneTimbro.value() );
				} else {
					log.info("Prelevo la posizione del timbro di default: " + posizioneTimbroDefault.value() );
					posizioneTimbro = posizioneTimbroDefault;
				}
				
				TipoRotazione rotazioneTimbro = null;
				if( itt.getRotazioneTimbro()!=null ){
					rotazioneTimbro = itt.getRotazioneTimbro();
					log.debug("rotazione timbro nella pagina: " + rotazioneTimbro.value() );
				} else {
					log.info("Prelevo la rotazione del timbro di default: " + rotazioneTimbroDefault.value() );
					rotazioneTimbro = rotazioneTimbroDefault;
				}
				
				PaginaTimbro paginaTimbro = itt.getPaginaTimbro();
				if( !isSetPaginaTimbro(paginaTimbro ) ){
					log.info("Prelevo la pagina di default da timbrare: " + tipoPaginaDefault.value() );
					paginaTimbro = new PaginaTimbro();
					paginaTimbro.setTipoPagina( tipoPaginaDefault );
				}
				
				if( !isPosizioneValida( posizioneTimbro, rotazioneTimbro,  posizioneIntestazione) || !isPosizioneValida( posizioneTimbro, rotazioneTimbro, posizioneTestoInChiaro ) ){
					log.error("errore .. Posizione testo rispetto al timbro non valida");
					output.addError(response, FileOpMessage.TIMBRO_OP_POSITION_UNSUITABLE,VerificationStatusType.KO);
					output.addResult( TimbroCtrlCode, response );
					return ret;
				}
				if( !isPosizioneValida( posizioneIntestazione, posizioneTestoInChiaro ) ){
					log.error("errore .. Non è possibile posizionare sia il testo in chiaro che l'intestazione nella stessa posizione");
					output.addError(response, FileOpMessage.TIMBRO_OP_SAME_POSITION_TEXT, VerificationStatusType.KO);
					output.addResult( TimbroCtrlCode, response );
					return ret;
				}
								
				try {
					TimbroUtils timbroUtils = (TimbroUtils) FileoperationContextProvider.getApplicationContext().getBean("TimbroUtils");
					String outputFile = inputFile.getName().replace(".", outputFileName+".");
					
					//Gestione inserita per i pdf MultiLayer che devono essere appiattiti in fase di timbro
					File fileDatimbrare = CheckPdfUtil.manageMultiLayerPdf(inputFile, "application/pdf");
					
					File fileTimbrato = timbroUtils.timbraPdf( fileDatimbrare, codifica, testo, paginaTimbro, posizioneTimbro, rotazioneTimbro,
							posizioneTestoInChiaro, 
							timbroSingolo, testoIntestazione, posizioneIntestazione, input.getTemporaryDir().getAbsolutePath() , outputFile, itt.isRigheMultiple() );
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

		output.addResult( TimbroCtrlCode, response );
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
	
	private String getTesto(String testo, Hashtable<String,String> params){
		log.debug("getTesto senza velocity " + testo);
		return testo;
	}
	
	private String getTestoVelocity(String testo, Hashtable<String,String> params){
		
		Velocity.init();
		VelocityContext context = new VelocityContext();
		
		Iterator<String> chiavi = params.keySet().iterator();
		while ( chiavi.hasNext()) {
			String chiave = chiavi.next();
			context.put( chiave, params.get(chiave) );
		}
		
		StringWriter writer = new StringWriter();
	    Velocity.evaluate( context, writer, "", testo);
		return writer.toString();
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

	public CodificaTimbro getCodificaDefault() {
		return codificaDefault;
	}

	public void setCodificaDefault(CodificaTimbro codificaDefault) {
		this.codificaDefault = codificaDefault;
	}

	public PosizioneTimbroNellaPagina getPosizioneTimbroDefault() {
		return posizioneTimbroDefault;
	}

	public void setPosizioneTimbroDefault(	PosizioneTimbroNellaPagina posizioneTimbroDefault) {
		this.posizioneTimbroDefault = posizioneTimbroDefault;
	}

	public TipoRotazione getRotazioneTimbroDefault() {
		return rotazioneTimbroDefault;
	}

	public void setRotazioneTimbroDefault(TipoRotazione rotazioneTimbroDefault) {
		this.rotazioneTimbroDefault = rotazioneTimbroDefault;
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
