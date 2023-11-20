/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.codec.Base64;

import it.eng.module.foutility.beans.DatiFirmaPades;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.RiquadroFirmaGrafica;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.InformazioniFirmaGraficaType;
import it.eng.module.foutility.beans.generated.InputPreparaFirmaPadesType;
import it.eng.module.foutility.beans.generated.ResponsePreparaFirmaPadesType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.signature.FirmaPadesUtil;


public class PreparaFirmaPadesCtrl extends AbstractFileController {

	public static final  Logger log = LogManager.getLogger( PreparaFirmaPadesCtrl.class );

	public String operationType;
	
	// chiave identificativa della operazione
	public static final String PreparaFirmaPadesCtrlCode=PreparaFirmaPadesCtrl.class.getName();
	
	public String outputFileName;
	
	public String altezzaRiquadroDefault="100";
	public String ampiezzaRiquadroDefault="190";
	public String numeroRigheFoglio = "8";
	public String numeroColonneFoglio = "3";
	public String margineInf = "20";
	public String margineLaterale = "20";
	public String immagineFirmaDefault;
	
	@Override
	public boolean execute( InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey ) {
		ResponsePreparaFirmaPadesType response = new ResponsePreparaFirmaPadesType();
		boolean ret=false;
		log.debug(requestKey + " Metodo execute di PreparaFirmaPadesCtrl - input " + input + " output "+ output);
		
		if(customInput instanceof InputPreparaFirmaPadesType){
			
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
								
				MimeType mimeType=null;
				try {
					mimeType = new MimeType("application/pdf");
					log.debug(requestKey + " - mimeType ricavato per il file " + mimeType);
					
				} catch (MimeTypeParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //output.getPropOfType( FormatRecognitionCtrl.DETECTED_MIME, MimeType.class);
				
				// verifico che il mimetype corrisponda a quello dei pdf
				try {
					String presenzaXform = output.getPropOfType( FormatRecognitionCtrl.FILE_XFORM, String.class);
					log.debug("presenzaXform ricavato per il file " + presenzaXform);
					
					if(presenzaXform!=null && presenzaXform.equalsIgnoreCase( "true" )){
						log.error("errore, formato file pdf editabile - non timbrabile");
						output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_PDFXFORM, VerificationStatusType.ERROR);
						output.addResult( PreparaFirmaPadesCtrlCode, response );
						return ret;
					}
					if(mimeType==null || !mimeType.match( "application/pdf" )){
						log.error("errore, formato file non pdf");
						output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF, VerificationStatusType.ERROR);
						output.addResult( PreparaFirmaPadesCtrlCode, response );
						return ret;
					}
				} catch (MimeTypeParseException e) {
					log.error("errore, formato file non pdf");
					output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF, VerificationStatusType.ERROR);
					output.addResult( PreparaFirmaPadesCtrlCode, response );
					return ret;
				}
			} else {
				log.debug("Uso il file convertito per la funzione di timbro");
			}
			
			
			log.info("Elaborazione file " + inputFile );
			
			InputPreparaFirmaPadesType ipfp=((InputPreparaFirmaPadesType) customInput);
			if( ipfp!=null && inputFile!=null ){
				
				InformazioniFirmaGraficaType infoFirma = ipfp.getInformazioniFirmaGrafica();
				
				File fileImmagine = null;
				String testoFirma = null;
				Integer ampiezzaRiquadroFirma = 0;
				Integer altezzaRiquadroFirma = 0;
				int coordinataXRiquadroFirma = 0;
				int coordinataYRiquadroFirma = 0;
				Integer paginaFirma = 0;
				if(infoFirma!=null){
					ampiezzaRiquadroFirma = infoFirma.getAmpiezzaRiquadroFirma();
					if( ampiezzaRiquadroFirma==null || ampiezzaRiquadroFirma== 0 ){
						int ampiezzaRiquadroDefInt = 190;
						try{
							ampiezzaRiquadroDefInt = Integer.parseInt(ampiezzaRiquadroDefault);
						} catch(Exception e){
							log.error("Errore nella configurazione dell'ampiezza del riquadro di firma, verra' utilizzato il default");
						}
						ampiezzaRiquadroFirma = ampiezzaRiquadroDefInt;
					}
					log.info("ampiezzaRiquadroFirma " +ampiezzaRiquadroFirma);
					
					altezzaRiquadroFirma = infoFirma.getAltezzaRiquadroFirma();
					if( altezzaRiquadroFirma==null || altezzaRiquadroFirma== 0 ){
						int altezzaRiquadroDefInt = 100;
						try{
							altezzaRiquadroDefInt = Integer.parseInt(altezzaRiquadroDefault);
						} catch(Exception e){
							log.error("Errore nella configurazione dell'altezza del riquadro di firma, verra' utilizzato il default");
						}
						altezzaRiquadroFirma = altezzaRiquadroDefInt;
					}
					log.info("altezzaRiquadroFirma " +altezzaRiquadroFirma);
					
					if( infoFirma.getCoordinataXRiquadroFirma()!=null){
						coordinataXRiquadroFirma = infoFirma.getCoordinataXRiquadroFirma();
						log.info("coordinataXRiquadroFirma " +coordinataXRiquadroFirma);
					}
					if( infoFirma.getCoordinataYRiquadroFirma()!=null){
						coordinataYRiquadroFirma = infoFirma.getCoordinataYRiquadroFirma();
						log.info("coordinataYRiquadroFirma " +coordinataYRiquadroFirma);
					}
					
					float heightA4 = PageSize.A4.getHeight();
					float widthA4 = PageSize.A4.getWidth();
					Integer riqFirmaVerticale = infoFirma.getAreaVerticale();
					Integer riqFirmaOrizzontale = infoFirma.getAreaOrizzontale();
					if( riqFirmaVerticale!=null && riqFirmaOrizzontale!=null ){
						log.info("heightA4 " +heightA4 + " widthA4 " + widthA4);
						log.info("riqFirma orizzontale " +riqFirmaOrizzontale + " verticale " +riqFirmaVerticale);
						
						int margineLateraleInt = 20;
						try{
							margineLateraleInt = Integer.parseInt(margineLaterale);
						} catch(Exception e){
							log.error("Errore nella configurazione del margine laterale della griglia di firma, verra' utilizzato il default");
						}
						int margineInfInt = 20;
						try{
							margineInfInt = Integer.parseInt(margineInf);
						} catch(Exception e){
							log.error("Errore nella configurazione del margine inferiore della griglia di firma, verra' utilizzato il default");
						}
						
						int numeroColonneFoglioInt = 3;
						try{
							numeroColonneFoglioInt = Integer.parseInt(numeroColonneFoglio);
						} catch(Exception e){
							log.error("Errore nella configurazione del numero di colonne della griglia di firma, verra' utilizzato il default");
						}
						int numeroRigheFoglioInt = 8;
						try{
							numeroRigheFoglioInt = Integer.parseInt(numeroRigheFoglio);
						} catch(Exception e){
							log.error("Errore nella configurazione del numero di righe della griglia di firma, verra' utilizzato il default");
						}
						
						if( riqFirmaOrizzontale>numeroColonneFoglioInt || riqFirmaOrizzontale<=0){
							//errore
							log.error("errore, numero di colonna della griglia di firma non consentito");
							output.addError(response, FileOpMessage.VISIBLE_SIGN_COLUMN_ERROR, VerificationStatusType.ERROR);
							output.addResult( PreparaFirmaPadesCtrlCode, response );
							return ret;
						}
						if( riqFirmaVerticale>numeroRigheFoglioInt || riqFirmaVerticale<=0){
							//errore
							log.error("errore, numero di riga della griglia di firma non consentito");
							output.addError(response, FileOpMessage.VISIBLE_SIGN_ROW_ERROR, VerificationStatusType.ERROR);
							output.addResult( PreparaFirmaPadesCtrlCode, response );
							return ret;
						}
						
						coordinataXRiquadroFirma = ((int)(widthA4/numeroColonneFoglioInt * (riqFirmaOrizzontale-1)))+margineLateraleInt;
						coordinataYRiquadroFirma = ((int)(heightA4/numeroRigheFoglioInt * (riqFirmaVerticale-1)))+margineInfInt;
						log.info("coordinataXRiquadroFirma " +coordinataXRiquadroFirma);
						log.info("coordinataYRiquadroFirma " +coordinataYRiquadroFirma);
						
						if( (coordinataXRiquadroFirma+ampiezzaRiquadroFirma)>widthA4){
							//errore
							log.error("errore, con i parametri richiesti la firma grafica andrebbe fuori dai limiti di pagina");
							output.addError(response, FileOpMessage.VISIBLE_SIGN_LIMIT_ERROR, VerificationStatusType.ERROR);
							output.addResult( PreparaFirmaPadesCtrlCode, response );
							return ret;
						}
						if( (coordinataYRiquadroFirma+altezzaRiquadroFirma)>heightA4){
							//errore
							log.error("errore, con i parametri richiesti la firma grafica andrebbe fuori dai limiti di pagina");
							output.addError(response, FileOpMessage.VISIBLE_SIGN_LIMIT_ERROR, VerificationStatusType.ERROR);
							output.addResult( PreparaFirmaPadesCtrlCode, response );
							return ret;
						}
					}
					
					if( infoFirma.getImmagine()!=null ){
						if( infoFirma.getImmagine().getFileStream()!=null ){
							log.debug(requestKey + " - Ho ricevuto il file immagine per stream");
							//read File from DataHandler
							DataHandler dh=infoFirma.getImmagine().getFileStream();
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
								log.error(requestKey + " - Errore nel recupero dell'immagine per la preparazione firma pades", e);
							} catch (IOException e) {
								log.error(requestKey + " - Errore nel recupero dell'immagine per la preparazione firma pades", e);
							}
						} else {
							log.debug(requestKey + " - Ho ricevuto il file immagine per url");
							// produce from URL
							String fileUrl = infoFirma.getImmagine().getFileUrl();
							try {
								fileImmagine=File.createTempFile("input", "." + "png", inputFile.getParentFile());
								FileUtils.copyURLToFile(new URL(fileUrl), fileImmagine);
							} catch (IOException e) {
								log.error(requestKey + " - Errore nel recupero dell'immagine per la preparazione firma pades", e);
							}
						}
					} else {
						log.debug(requestKey + " - Utilizzo immagine di firma di default");
						try {
							// Cerco di caricare l'immagine di firma dalla cartella WEB-INF/classes/immages/firma/ del contesto applicativo di Fileop
							InputStream fileImmagineDefaultImputStream = getClass().getClassLoader().getResourceAsStream("images/firma/" + immagineFirmaDefault);
							if (fileImmagineDefaultImputStream != null) {
								// Immagine trovata
								fileImmagine = File.createTempFile("input", "." + "png", inputFile.getParentFile());
								FileUtils.copyInputStreamToFile(fileImmagineDefaultImputStream, fileImmagine);
							} else {
								// Immgine non trovata, carico l'immagine strandard dalla caretlla immagini_firma di FileoperationBusiness
								fileImmagineDefaultImputStream = getClass().getClassLoader().getResourceAsStream("immagini_firma/firma.png");
								if (fileImmagineDefaultImputStream != null) {
									// Immagine trovata
									fileImmagine = File.createTempFile("input", "." + "png", inputFile.getParentFile());
									FileUtils.copyInputStreamToFile(fileImmagineDefaultImputStream, fileImmagine);
								} else {
									// Immagine non trovata
									log.error(requestKey + " - Errore nel recupero dell'immagine per la preparazione firma pades");
								}
							}
						} catch (Exception e) {
							log.error(requestKey + " - Errore nel recupero dell'immagine per la preparazione firma pades", e);
						}
					}
					
					testoFirma = infoFirma.getTesto();
					
					if(testoFirma.contains("$dataCorrente$")){
						String pattern = "dd-MM-yyyy HH:mm:ss";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						String dataCorrente = simpleDateFormat.format(new Date());
						testoFirma = testoFirma.replace("$dataCorrente$", dataCorrente);
					}
					
					paginaFirma = infoFirma.getPagina();
					if( paginaFirma==null || paginaFirma== 0 ){
						log.info("Imposto di firmare nella prima pagina" );
						paginaFirma = 1;
					}
				}
				
				X509Certificate userCert = null;
				if( ipfp!=null && ipfp.getUserCertificate()!=null ){
					DataHandler dhCert = ipfp.getUserCertificate();
					try {
						InputStream isCertt = dhCert.getInputStream();
						userCert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(isCertt);
						
						if(testoFirma.contains("$intestatarioCertificato$")){
							String nomeIntestatario = "nomeIntestatario";
							X500Name x500name = new JcaX509CertificateHolder(userCert).getSubject();
							RDN cn = x500name.getRDNs(BCStyle.CN)[0];
	
							nomeIntestatario = IETFUtils.valueToString(cn.getFirst().getValue());
							testoFirma = testoFirma.replace("$intestatarioCertificato$", nomeIntestatario);
						}
						
					} catch (Exception e) {
						log.error(requestKey + " - Errore nel recupero del certificato utente", e);
					}
				} else {
					if( ipfp!=null && ipfp.getFirmatario()!=null ){
						String firmatario = ipfp.getFirmatario();
						log.info("firmatario: " + firmatario);
						
						if(testoFirma.contains("$intestatarioCertificato$")){
							testoFirma = testoFirma.replace("$intestatarioCertificato$", firmatario);
						}
					}
				}
				
				String nomeCampoFirma = ipfp.getNomeCampoFirma();
				log.info("nomeCampoFirma: " + nomeCampoFirma);
				
				String location = ipfp.getLocation();
				log.info("location: " + location);
				
				String reason = ipfp.getReason();
				log.info("reason: " + reason);
				
				try {
					String outputFileNameCompleto = inputFile.getName().replace(".", outputFileName+".");
					File outputFile = new File( inputFile.getParent() + "/" + outputFileNameCompleto );
					
					DatiFirmaPades datiFirmaPades = new DatiFirmaPades();
					datiFirmaPades.setNomeCampoFirma( nomeCampoFirma );
					datiFirmaPades.setLocation(location);
					datiFirmaPades.setReason(reason);
					datiFirmaPades.setTesto(testoFirma);
					RiquadroFirmaGrafica riquadroFirma = new RiquadroFirmaGrafica();
					riquadroFirma.setLarghezza(ampiezzaRiquadroFirma);
					riquadroFirma.setAltezza(altezzaRiquadroFirma);
					riquadroFirma.setCoordinataXVertice(coordinataXRiquadroFirma);
					riquadroFirma.setCoordinataYVertice(coordinataYRiquadroFirma);
					if(fileImmagine!=null)
						riquadroFirma.setFileImmagine(fileImmagine);
					
					FirmaPadesUtil firmaPadesUtil = (FirmaPadesUtil) FileoperationContextProvider.getApplicationContext().getBean("FirmaPadesUtil");	
					byte[] hash = firmaPadesUtil.preparaFile(inputFile, outputFile, datiFirmaPades, riquadroFirma , userCert, paginaFirma) ;
					if( hash!=null ){
						log.debug(requestKey + " - File firmato temporaneo " + outputFile );
						ret = true;
						String encodedHash = Base64.encodeBytes(hash);
						response.setEncodedHash(encodedHash);
						output.setFileResult( outputFile );
						response.setVerificationStatus(VerificationStatusType.OK);
					} else {
						response.setVerificationStatus(VerificationStatusType.KO);
					}
				} catch (Exception e) {
					log.error("errore ..",e);
					
				}
			}
			else {
				log.error("errore, formato file non pdf");
				output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF,VerificationStatusType.KO);
			}
		}

		output.addResult( PreparaFirmaPadesCtrlCode, response );
		return ret;
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

	public String getAltezzaRiquadroDefault() {
		return altezzaRiquadroDefault;
	}

	public void setAltezzaRiquadroDefault(String altezzaRiquadroDefault) {
		this.altezzaRiquadroDefault = altezzaRiquadroDefault;
	}

	public String getAmpiezzaRiquadroDefault() {
		return ampiezzaRiquadroDefault;
	}

	public void setAmpiezzaRiquadroDefault(String ampiezzaRiquadroDefault) {
		this.ampiezzaRiquadroDefault = ampiezzaRiquadroDefault;
	}

	public String getImmagineFirmaDefault() {
		return immagineFirmaDefault;
	}

	public void setImmagineFirmaDefault(String immagineFirmaDefault) {
		this.immagineFirmaDefault = immagineFirmaDefault;
	}

	public String getNumeroRigheFoglio() {
		return numeroRigheFoglio;
	}

	public void setNumeroRigheFoglio(String numeroRigheFoglio) {
		this.numeroRigheFoglio = numeroRigheFoglio;
	}

	public String getNumeroColonneFoglio() {
		return numeroColonneFoglio;
	}

	public void setNumeroColonneFoglio(String numeroColonneFoglio) {
		this.numeroColonneFoglio = numeroColonneFoglio;
	}
	
	public String getMargineInf() {
		return margineInf;
	}

	public void setMargineInf(String margineInf) {
		this.margineInf = margineInf;
	}

	public String getMargineLaterale() {
		return margineLaterale;
	}

	public void setMargineLaterale(String margineLaterale) {
		this.margineLaterale = margineLaterale;
	}

	public static void main(String[] args) {
		float heightA4 = PageSize.A4.getHeight();
		float widthA4 = PageSize.A4.getWidth();
		System.out.println(widthA4 + " " + heightA4);
		Integer riqFirmaVerticale = 2;
		Integer riqFirmaOrizzontale = 3;
		float x  = (int)(widthA4/3 * (riqFirmaOrizzontale-1));
		float y  = (int)(heightA4/8 * (riqFirmaVerticale-1));
		
		System.out.println(x+" "+y);
		
		
	}
}
