/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.module.foutility.beans.CheckEditableFileBean;
import it.eng.module.foutility.beans.CheckPdfCommentiFileBean;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.InputFormatRecognitionType;
import it.eng.module.foutility.beans.generated.MultipartContentType;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType.DatiFormato;
import it.eng.module.foutility.beans.generated.ResponseUnpackMultipartType;
import it.eng.module.foutility.beans.generated.ResponseUnpackMultipartType.MultipartContents;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CheckPdfCommenti;
import it.eng.module.foutility.util.CheckPdfEditabili;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.InputFileUtil;
import it.eng.module.foutility.util.PdfNumeroPagineUtil;
import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.MimeUtilsAdapter;
import it.eng.suiteutility.module.mimedb.DaoAnagraficaFormatiDigitali;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;
import it.eng.suiteutility.module.mimedb.entity.TEstensioniFmtDig;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.pdfUtility.commenti.PdfCommentiUtil;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;

/**
 * riconoscimento del formato
 * 
 * @author Russo
 *
 */
public class FormatRecognitionCtrl extends AbstractFileController {

	public static final Logger log = LogManager.getLogger(FormatRecognitionCtrl.class);

	public String operationType;
	private List<String> recursiveMimetypes;

	// mime type rilevato (MimeType)
	public static final String DETECTED_MIME = "DETECTED_MIME";
	public static final String DETECTED_MIME_STRING = "DETECTED_MIME_STRING";
	// dati del formato (TAnagFormatDig)
	public static final String DATI_FORMATO = "DATI_FORMATO";
	// estensioni delle firme
	public static final String[] SigExt = { "tsd", "p7m" };
	
	public static final String FILE_STATICIZZATO = "fileStaticizzato";
	public static final String FILE_SENZA_COMMENTI = "fileSenzaCommenti";
	public static final String FILE_XFORM = "fileXForm";
	
	public static final String IS_PDF_COMMENTI = "isPdfCommenti";
	public static final String IS_PDF_EDITABILI = "isPdfEditabili";
	public static final String IS_PDF_XFORMS = "isPdfEditabiliXForms";
	
	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey) {

		log.debug(requestKey + " Metodo execute di FormatRecognitionCtrl  - input " + input + " output "+ output);

		MimeUtilsAdapter adapter = new MimeUtilsAdapter();

		boolean ret = false;
		boolean isFileSbustato = false;
		boolean isVerificaPdfEditableAttiva = false;
		boolean isVerificaPdfCommentiAttiva = false;
		boolean isPdfEditable = false;
		boolean isPdfCommenti = false;
		boolean isPdfEditableXform = false;
		boolean isVerificaPdfNumeroPagineAttiva = false;
		Integer numeroPaginePdf=null;
		// risultato dell'operazione
		ResponseFormatRecognitionType opResult = new ResponseFormatRecognitionType();

		InputFormatRecognitionType frRequest = ((InputFormatRecognitionType) customInput);

		File file = output.getPropOfType(UnpackCtrl.EXTRACTED_FILE, File.class);
		log.debug(requestKey + " - File sbustato " + file);
		if (file == null) {
			log.warn(requestKey + " - File sbustato non trovato, uso il file di input");
			file = input.getInputFile();
		} else {
			//isFileSbustato = true;
		}
		
		Boolean rootIsSigned = (Boolean) output.getProperty(UnpackCtrl.IS_ROOT_SIGNED);
		if (rootIsSigned != null && rootIsSigned == true) {
			isFileSbustato = true;
		}
		log.debug(requestKey + " - isFileSbustato? " + isFileSbustato);
		
		isVerificaPdfEditableAttiva = CheckPdfEditabili.isAttivaVerificaPdfEditable();
		log.debug(requestKey + " - Verifica pdf editabili attiva? " + isVerificaPdfEditableAttiva);
		
		boolean esitoStaticizzazione = false;
		File fileStaticizzato = null;
		// solo se la verifica sui pdf editabili e' attiva provo a manipolare il file staticizzandolo
		if( isVerificaPdfEditableAttiva ){
			// Verifico se il file e' editabile
			CheckEditableFileBean checkEditable = CheckPdfEditabili.isPdfEditable( file );
			if(checkEditable.getFlgEditable() ) {
				log.debug(requestKey + " - Il file e' un pdf editabile");
				isPdfEditable = true;
			}
			if(checkEditable.getFlgContainsXfaForm()) {
				log.debug(requestKey + " - Il file e' un pdf che contiene xform");
				isPdfEditableXform = true;
				
				output.addProperty(FILE_XFORM, "true");
			}
			if(!checkEditable.getFlgEditable() && !checkEditable.getFlgContainsXfaForm()) {
				log.debug(requestKey + " - Il file non e' un pdf editabile");
			}
			
			// staticizzo solo se il file e' editabile e non contiene xform
			if(checkEditable.getFlgEditable() && !checkEditable.getFlgContainsXfaForm()){
				try {
					fileStaticizzato = PdfEditabiliUtil.staticizzaPdfEditabile( file );
					log.debug(requestKey + " - File staticizzato: " + fileStaticizzato );
					esitoStaticizzazione = true;
				} catch (Exception e) {
					log.error(requestKey + " Errore nella staticizzazione del file in input ", e);
				}
			}
		}
		
		isVerificaPdfCommentiAttiva = CheckPdfCommenti.isAttivaVerificaPdfCommenti();
		log.debug(requestKey + " - Verifica pdf con commenti attiva? " + isVerificaPdfCommentiAttiva);
		
		boolean esitoRimozioneCommenti = false;
		File fileSenzaCommenti = null;
		// solo se la verifica sui pdf con commenti e' attiva provo a manipolare il file 
		if( isVerificaPdfCommentiAttiva ){
			// Verifica di file pdf con commenti
			CheckPdfCommentiFileBean checkPdfCommenti = CheckPdfCommenti.isPdfConCommenti( file );
			if(checkPdfCommenti.getFlgContieneCommenti()!=null && checkPdfCommenti.getFlgContieneCommenti() ) {
				log.debug(requestKey + " - Il file e' un pdf con commenti");
				isPdfCommenti = true;
				
				// provo a manipolare il file 
				try {
					fileSenzaCommenti = PdfCommentiUtil.staticizzaFileConCommenti(file, checkPdfCommenti.getPageWithCommentBox());
					log.debug(requestKey + " - File senza commenti: " + fileSenzaCommenti );
					esitoRimozioneCommenti = true;
				} catch (Exception e) {
					log.error(requestKey + " Errore nella rimozione dei commenti sul file in input ", e);
				}
				
			}
			if(checkPdfCommenti.getFlgContieneCommenti()!=null && !checkPdfCommenti.getFlgContieneCommenti() ) {
				log.debug(requestKey + " - Il file non e' un pdf con commenti");
				isPdfCommenti = false;
			}
		}
		
		// solo se la verifica sui numeri di pagina e' attiva provo a calcolare questo dato
		isVerificaPdfNumeroPagineAttiva = PdfNumeroPagineUtil.isAttivaVerificaPdfNumeriPagina();
		log.debug(requestKey + " - Verifica pdf numero pagine attiva? " + isVerificaPdfNumeroPagineAttiva);
		if( isVerificaPdfNumeroPagineAttiva ){
			try {
				numeroPaginePdf = PdfUtil.recuperaNumeroPagine(file);
			} catch (Exception e) {
				log.error("Errore nella verifica del numero di pagine ", e.getMessage());
			}
		}
		
		
		
		Boolean abilitaSbustamento = null;
		if (frRequest != null) {
			abilitaSbustamento = frRequest.isAbilitaSbustamento();
		}
		log.debug(requestKey + " - sbustamento richiesto? " + abilitaSbustamento);

		// il default dello sbustamento e' true quindi non prendo il file sbustato solo se
		// il parametro e' specificato ed e' settato a false
		if (abilitaSbustamento != null && abilitaSbustamento == false) {
			log.debug(requestKey + " sbustamento non richiesto, uso il file di input");
			file = input.getInputFile();
		}
		log.info(requestKey + " Elaborazione file " + file);

		TAnagFormatiDig formato = null;
		MimeType mimeType = null;
		boolean mimeAffidabile = false;

		// se viene passato il nome file in input provo a recuperare il formato in base all'estensione
		String fileName = null;
		try {
			fileName = InputFileUtil.getTempFileName(input);
			log.debug(requestKey + " fileName " + fileName);
		} catch (Exception e1) {
			log.error("", e1);
		}

		if (!StringUtils.isBlank(fileName) && fileName.contains(".")) {
			String nomeFilePassato = fileName;
			//String estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
			String estensione = FilenameUtils.getExtension(nomeFilePassato);
			if (estensione!=null && 
					(estensione.equalsIgnoreCase("p7m") || estensione.equalsIgnoreCase("m7m") || estensione.equalsIgnoreCase("tsd"))
					) {
				//nomeFilePassato = nomeFilePassato.substring(0, nomeFilePassato.indexOf(estensione) - 1);
				nomeFilePassato = FilenameUtils.getBaseName(nomeFilePassato);
				//estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
				estensione = FilenameUtils.getExtension(nomeFilePassato);
			}
			log.debug(requestKey + " estensione " + estensione);

			DaoAnagraficaFormatiDigitali dao = new DaoAnagraficaFormatiDigitali();
			try {
				TAnagFormatiDig formatoTxt = dao.findFormatByMimeType("text/plain");
				
				if( estensione!=null && formatoTxt!=null && estensione.equalsIgnoreCase( formatoTxt.getEstensionePrincipale() ) ){
					
				} else {
					log.debug(requestKey + " recupero il formato con l'estensione ");
					formato = dao.findFormatByExt(estensione);
					
					boolean verificaAffidabilitaMime = true;
					/*if( isPdfEditableXform && !isFileSbustato ) {
						mimeType = new MimeType();
						mimeType.setSubType("pdfe");
						mimeType.setPrimaryType("application");
						mimeAffidabile = true;
						verificaAffidabilitaMime = false;
					} else if(isPdfEditable && !isPdfEditableXform && !isFileSbustato ) {
						mimeType = new MimeType();
						mimeType.setSubType("pdfe");
						mimeType.setPrimaryType("application");
						mimeAffidabile = true;
						verificaAffidabilitaMime = false;
					} else if(isPdfCommenti && !isFileSbustato) {
						mimeType = new MimeType();
						mimeType.setSubType("pdfc");
						mimeType.setPrimaryType("application");
						mimeAffidabile = true;
						verificaAffidabilitaMime = false;
					} else {*/
						mimeType = getMimeTypeExt(file, estensione, requestKey, adapter);
					//}
					log.info(requestKey + " formato recuperato dal mime " + mimeType);
	
					try {
						formato = dao.findFormatByMimeType(mimeType.getBaseType());
						// if( formato!=null )
						// log.info("formato recuperato dal db " + formato.getMimetypePrincipale() );
					} catch (Exception e) {
						log.error(requestKey + " Eccezione FormatRecognitionCtrl", e);
						OutputOperations.addError(opResult, FileOpMessage.FR_DB_ERROR, VerificationStatusType.KO);
					}
	
					if( verificaAffidabilitaMime ){
						mimeAffidabile = adapter.checkIfExtensionMimeIsReliable(mimeType, file);
					}
					log.info(requestKey + " Il mimeRecuperato dall'estensione e' affidabile? " + mimeAffidabile);
					
				}

			} catch (Exception e) {
				log.error(requestKey + " Eccezione FormatRecognitionCtrl", e);
				OutputOperations.addError(opResult, FileOpMessage.FR_DB_ERROR, VerificationStatusType.KO);
			}
		}
		if (!mimeAffidabile) {
			
			try {
				/*if((isPdfEditable || isPdfEditableXform) && !isFileSbustato) {
					mimeType = new MimeType();
					mimeType.setSubType("pdfe");
					mimeType.setPrimaryType("application");
				} else if(isPdfCommenti && !isFileSbustato) {
					mimeType = new MimeType();
					mimeType.setSubType("pdfc");
					mimeType.setPrimaryType("application");
				} else {*/
					mimeType = getBestMimeType(file, adapter);
				//}
			} catch (Throwable e) {
				log.fatal(requestKey + " Eccezione FormatRecognitionCtrl", e);
				OutputOperations.addError(opResult, FileOpMessage.FR_MIME_UNKNOWN, VerificationStatusType.KO);
			}
			log.info(requestKey + " - Determinato mime: " + mimeType);
			if (mimeType == null) {
				log.error(requestKey + " errore nel reperimento del mimeType");
				ret = false;
				OutputOperations.addError(opResult, FileOpMessage.FR_FORMAT_NOT_FOUND, VerificationStatusType.KO);
			} else {
				String mimeTypeString = mimeType.getBaseType();
				log.debug(mimeTypeString + " - mimeTypeString " + formato);
				DaoAnagraficaFormatiDigitali dao = new DaoAnagraficaFormatiDigitali();
				try {
					formato = dao.findFormatByMimeType(mimeTypeString);
					log.debug(requestKey + " - formato " + formato);
				} catch (Exception e) {
					log.error(requestKey + " - Eccezione FormatRecognitionCtrl", e);
					OutputOperations.addError(opResult, FileOpMessage.FR_DB_ERROR, VerificationStatusType.KO);
				}
			}
			
		}

		if (formato == null) {
			if( mimeType!=null )
				opResult.setMimeTypeRilevato(mimeType.getBaseType());
			log.error(requestKey + " Formato non trovato in banca dati");
			ret = false;
			OutputOperations.addError(opResult, FileOpMessage.FR_FORMAT_NOT_FOUND, VerificationStatusType.KO);
		} else {
			
			ret = true;
			// costruisco il bean risultato
			opResult.setVerificationStatus(VerificationStatusType.OK);

			opResult.setMimeType(formato.getMimetypePrincipale());

			if (formato.getMimetypePrincipale() != null) {
				boolean verificaConversione = verificaConversionePdf(formato.getMimetypePrincipale());
				log.debug(requestKey + " verificaConversione " + verificaConversione);
				opResult.setPdfConversion(verificaConversione);
				boolean verificaConversionePerFirma = verificaConversionePdfPerFirma(formato.getMimetypePrincipale());
				log.debug(requestKey + " verificaConversionePerFirma " + verificaConversionePerFirma);
				opResult.setSignConversion(verificaConversionePerFirma);
				
				if(isPdfEditable || isPdfEditableXform){
					opResult.setIsPdfEditabili(true);
					if( isPdfEditable)
						output.addProperty(IS_PDF_EDITABILI, Boolean.TRUE);
					if( isPdfEditableXform)
						output.addProperty(IS_PDF_XFORMS, Boolean.TRUE);
				} else {
					opResult.setIsPdfEditabili(false);
					output.addProperty(IS_PDF_EDITABILI, Boolean.FALSE);
					output.addProperty(IS_PDF_XFORMS, Boolean.FALSE);
				}
				if(isPdfCommenti){
					opResult.setIsPdfCommenti(true);
					output.addProperty(IS_PDF_COMMENTI, Boolean.TRUE);
				} else {
					opResult.setIsPdfCommenti(false);
					output.addProperty(IS_PDF_COMMENTI, Boolean.FALSE);
				}
				
				if(numeroPaginePdf!=null){
					opResult.setNumeroPaginePdf(numeroPaginePdf);
				}
			}

			DatiFormato datiFormato = new DatiFormato();
			datiFormato.setIdFormato(formato.getIdDigFormat());
			opResult.setDatiFormato(datiFormato);
			if (!StringUtils.isBlank(input.getFileName())) {
				boolean firmato = false;
				Boolean rootSigned = (Boolean) output.getProperty(UnpackCtrl.IS_ROOT_SIGNED);
				if (rootSigned != null && rootSigned == true) {
					firmato = true;
				}
				SignerType envelopeFormat = (SignerType) output.getProperty(UnpackCtrl.ENVELOPE_FORMAT);
				SignerType envelopeEsternaFormat = (SignerType) output.getProperty(UnpackCtrl.ENVELOPE_FORMAT_ESTERNA);
				
				String renamed = rename(input.getFileName(), formato.getEstensionePrincipale(), formato.getTEstensioniFmtDigs(), firmato, 
						envelopeFormat, envelopeEsternaFormat);
				log.info(requestKey + " Setto il nome del file: " + renamed);
				opResult.setNewFileName(renamed);
			}

			Boolean recursive = false;
			if (frRequest != null) {
				recursive = frRequest.isRecursive();
			}
			log.info(requestKey + " E' richiesto il controllo ricorsivo sui formati: " + recursive);
			// il default della ricorsione e' false quindi cerco i formati interni solo se
			// il parametro e' specificato ed e' settato a true
			if (recursive != null && recursive) {
				if (recursiveMimetypes != null && recursiveMimetypes.size() > 0) {
					boolean effettuoRicercaRicorsiva = recursiveMimetypes.contains(opResult.getMimeType());
					log.info(requestKey + " Verifico se il formato " + opResult.getMimeType() + " ammette il controllo ricorsivo sui formati " + effettuoRicercaRicorsiva);
					if (effettuoRicercaRicorsiva) {
						String extractedFileName = output.getPropOfType(UnpackCtrl.EXTRACTED_FILE_NAME, String.class);
						List<String> listaFormatiInterni = getFormatiInterni(file, input.getTemporaryDir().getAbsolutePath(), abilitaSbustamento,
								extractedFileName, requestKey);
						log.info(requestKey + " formatiInterni " + listaFormatiInterni);
						if (listaFormatiInterni != null && !listaFormatiInterni.isEmpty()) {
							ResponseFormatRecognitionType.DatiFormatiInterni formatiInterni = new ResponseFormatRecognitionType.DatiFormatiInterni();
							for (String formatoInterno : listaFormatiInterni) {
								formatiInterni.getMimeType().add(formatoInterno);
							}
							opResult.setDatiFormatiInterni(formatiInterni);
						}
					}
				}
			}

			// tento di rinominare il file aggiungendo l'estensione individuata
			// questo mi serve poiche' alcuni controlli come la verifica macro
			// basati su OO falliscono se i lfiel non possiede l'estensione richiesta
			File renamedFile = renameFile(file, formato.getEstensionePrincipale());

			if (output.getPropOfType(UnpackCtrl.EXTRACTED_FILE, File.class) != null) {
				output.addProperty(UnpackCtrl.EXTRACTED_FILE, renamedFile);
				// se il file non è firmato l'extracted coinciude con l'input per cui
				// avendo rinominato deve cambiare il file anche dell'input
				Boolean rootSigned = (Boolean) output.getProperty(UnpackCtrl.IS_ROOT_SIGNED);
				if (rootSigned != null && rootSigned == false) {
					input.setInputFile(renamedFile);
				}
			} else {
				// hai rinominato il file input
				input.setInputFile(renamedFile);
			}
			
			if( isVerificaPdfEditableAttiva || isVerificaPdfCommentiAttiva ){
				
				if( esitoStaticizzazione && fileStaticizzato!=null && isPdfEditable ){
					output.addProperty(FormatRecognitionCtrl.FILE_STATICIZZATO, fileStaticizzato );
					MimeType mimeTypeStaticizzato = new MimeType();
					try {
						mimeTypeStaticizzato.setSubType("pdf");
						mimeTypeStaticizzato.setPrimaryType("application");
						
						log.debug(requestKey + " memorizzo il mime " + mimeTypeStaticizzato + " per i controller successivi");
						// memorizzo il mime rilevato per controller successivi
						output.addProperty(DETECTED_MIME, mimeTypeStaticizzato);
						output.addProperty(DETECTED_MIME_STRING, mimeTypeStaticizzato.toString());
						
						output.setFileResult(fileStaticizzato);
					} catch (MimeTypeParseException e) {
						
					}
				} else if( isPdfEditableXform ){
					MimeType mimeTypeStaticizzato = new MimeType();
					try {
						mimeTypeStaticizzato.setSubType("pdfe");
						mimeTypeStaticizzato.setPrimaryType("application");
						log.debug(requestKey + " memorizzo il mime " + mimeTypeStaticizzato + " per i controller successivi");
						// memorizzo il mime rilevato per controller successivi
						output.addProperty(DETECTED_MIME, mimeTypeStaticizzato);
						output.addProperty(DETECTED_MIME_STRING, mimeTypeStaticizzato.toString());
						
						output.setFileResult(renamedFile);
					} catch (MimeTypeParseException e1) {
						
					}
				} else if( esitoRimozioneCommenti && fileSenzaCommenti!=null && isPdfCommenti ){
					output.addProperty(FormatRecognitionCtrl.FILE_SENZA_COMMENTI, fileSenzaCommenti );
					log.debug(requestKey + " salvo il file senza commenti " + fileSenzaCommenti + " per i controller successivi");
					MimeType mimeTypeSenzaCommenti = new MimeType();
					try {
						mimeTypeSenzaCommenti.setSubType("pdf");
						mimeTypeSenzaCommenti.setPrimaryType("application");
						
						log.debug(requestKey + " memorizzo il mime " + mimeTypeSenzaCommenti + " per i controller successivi");
						// memorizzo il mime rilevato per controller successivi
						output.addProperty(DETECTED_MIME, mimeTypeSenzaCommenti);
						output.addProperty(DETECTED_MIME_STRING, mimeTypeSenzaCommenti.toString());
						
						output.setFileResult(fileStaticizzato);
					} catch (MimeTypeParseException e) {
						
					}
				} else {
					log.debug(requestKey + " memorizzo il mime " + mimeType + " per i controller successivi");
					// memorizzo il mime rilevato per controller successivi
					output.addProperty(DETECTED_MIME, mimeType);
					output.addProperty(DETECTED_MIME_STRING, formato.getMimetypePrincipale() );
					
					output.setFileResult(renamedFile);
				}
			} else {
				log.debug(requestKey + " memorizzo il mime " + mimeType + " per i controller successivi");
				// memorizzo il mime rilevato per controller successivi
				output.addProperty(DETECTED_MIME, mimeType);
				output.addProperty(DETECTED_MIME_STRING, formato.getMimetypePrincipale() );
				
				output.setFileResult(renamedFile);
			}
			
			// memorizzo i dati del formato per ctrl successivi
			output.addProperty(DATI_FORMATO, formato);
			
			
		}
		output.addResult(this.getClass().getName(), opResult);
		return ret;
	}

	public MimeType getMimeTypeExt(File file, String ext, String requestKey, MimeUtilsAdapter adapter) throws MimeDetectorException {
		log.info(requestKey + " Metodo di determinazione del mimetype per il file " + file + " estensione " + ext);
		// verifico se il file ha degli invii all'inizio e eventualmente li rimuovo
		//RandomAccessFile raf;
		//try {
			/*raf = new RandomAccessFile(file, "rw");
			boolean esito = deleteInitialCarriageReturn(raf);
			// log.info("Esito presenza invio " + esito );
			while (esito) {
				esito = deleteInitialCarriageReturn(raf);
				// log.info("Esito presenza invio " + esito );
			}
			raf.close();*/
			return adapter.detectBestWithExtension(file, ext);
		/*} catch (IOException e) {
			log.error("Errore getMimeTypeExt", e);
		}*/
		//return null;
	}

	//public static void main(String[] args) {
		// ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		// init business logic
		// try {
		// ConfigUtil.initialize();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// FormatRecognitionCtrl ctr = new FormatRecognitionCtrl();
		// File file = new File("C:/Users/Anna Tesauro/Desktop/testDocx - Copia.docx");
		// try {
		// MimeType mimeType = ctr.getMimeTypeExt(file, "docx");
		// System.out.println("mimeType " + mimeType);
		//
		// boolean mimeAffidabile = adapter.checkIfExtensionMimeIsReliable(mimeType, file);
		// System.out.println("mimeAffidabile " + mimeAffidabile);
		//
		// if( !mimeAffidabile ){
		// mimeType = ctr.getBestMimeType(file);
		// System.out.println("mimeType " + mimeType);
		// }
		//
		// //ctr.getFormatiInterni(file, null);
		// } catch (MimeDetectorException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		//
		// boolean esitoCancellazione = file.delete();
		// System.out.println("esito cancellazione " + esitoCancellazione);

//		String nomeFilePassato = "201505241054531751722028538";
//		String estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
//		log.debug("Estensione: " + estensione);
//		if (estensione.equalsIgnoreCase("p7m") || estensione.equalsIgnoreCase("m7m") || estensione.equalsIgnoreCase("tsd")) {
//			nomeFilePassato = nomeFilePassato.substring(0, nomeFilePassato.indexOf(estensione) - 1);
//			estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
//		}
//
//		log.info("finding formato by ext:" + estensione);
//
//	}

	public MimeType getBestMimeType(File file, MimeUtilsAdapter adapter) throws MimeDetectorException {
		log.info("Metodo di determinazione del miglior mimetype per il file " + file);
		// verifico se il file ha degli invii all'inizio e eventualmente li rimuovo
		//RandomAccessFile raf;
		//try {
			/*raf = new RandomAccessFile(file, "rw");
			boolean esito = deleteInitialCarriageReturn(raf);
			// log.info("Esito presenza invio " + esito );
			while (esito) {
				esito = deleteInitialCarriageReturn(raf);
				// log.info("Esito presenza invio " + esito );
			}
			raf.close();*/

			// byte[] bytes = new byte[(int) (raf.length() )];
			// raf.seek(0);
			// raf.readFully(bytes);
			// raf.close();

			// return adapter.detectBest(bytes);
			return adapter.detectBest(file);
		//}
		// catch (FileNotFoundException e) {
		// log.error("Errore ",e);
		// }
		/*catch (IOException e) {
			log.error("Errore getBestMimeType", e);
		}*/
		//return null;
	}

	// public MimeType getMimeType(File file) throws MimeDetectorException{
	// return adapter.detectBest(file);
	// }

	private boolean deleteInitialCarriageReturn(RandomAccessFile raf) {
		byte[] remainingBytes = null;
		try {
			long position = 0;
			String line = null;
			// System.out.println(raf.getFilePointer());
			raf.seek(position);
			line = raf.readLine();
			// while (line != null) {

			// if (line.trim().equalsIgnoreCase(System.getProperty("line.separator"))) {
			if (line != null && line.trim().equalsIgnoreCase("")) {
				// System.out.println(line + "::" + line.length() + "::" + position + "::" + raf.getFilePointer());
				// Create a byte[] to contain the remainder of the file.
				remainingBytes = new byte[(int) (raf.length() - raf.getFilePointer())];
				raf.read(remainingBytes);
				// Truncate the file to the position of where we deleted the information.
				raf.getChannel().truncate(position);
				raf.seek(position);
				raf.write(remainingBytes);
				return true;
			}
			// position += raf.getFilePointer();
			// raf.seek(position);
			// line = raf.readLine();
			// }
		} catch (FileNotFoundException ex) {
			log.error("Errore deleteInitialCarriageReturn", ex);
		} catch (IOException ex) {
			log.error("Errore deleteInitialCarriageReturn", ex);
		}
		return false;
	}

	public File renameFile(File inputfile, String extension) {
		File ret = inputfile;
		if (inputfile == null) {
			return ret;
		}
		String actualExt = FilenameUtils.getExtension(inputfile.getName());
		if (actualExt != null && actualExt.equals(extension)) {
			return ret;
		} else {
			File newFilename = new File(FilenameUtils.removeExtension(inputfile.getAbsolutePath()) + "." + extension);
			log.debug("Nuovo nome file: " + newFilename);
			//boolean resRen = inputfile.renameTo(newFilename);
			//if (resRen) {
			//	ret = newFilename;
			//} else {
				try {
					FileUtils.copyFile(inputfile, newFilename);
					ret = newFilename;
				} catch (IOException e) {
					log.fatal("Eccezione coping file", e);
				}

			//}
		}
		return ret;
	}

	/**
	 * rinomina il nome del file postponendo l'estensione passata, a meno chè non ci siano estensioni relative a firme in tal caso l'estensione viene inserita
	 * prima della prima estensione firma. Se l'estensione è già presente si ritorna il nome originale del file
	 * 
	 * @param fileName
	 * @param extension
	 * @return
	 */
	public String rename(String fileName, String extension, Set<TEstensioniFmtDig> estensioniAssociate, boolean firmato, 
			SignerType envelopeFormat, SignerType envelopeEsternaFormat) {

		List<String> estFirme = Arrays.asList(SigExt);
		
		String suffissoEstFirma = "";
		String fileNameWithoutSignExtension = fileName;
		boolean estensioneTrovata = true;
		while (estensioneTrovata) {
			estensioneTrovata = false;
			for (String estFirma : estFirme) {
				if (fileNameWithoutSignExtension != null && fileNameWithoutSignExtension.endsWith("." + estFirma)) {
					suffissoEstFirma = suffissoEstFirma + fileNameWithoutSignExtension.substring(fileNameWithoutSignExtension.length() - estFirma.length() - 1); 
					fileNameWithoutSignExtension = fileNameWithoutSignExtension.substring(0, fileNameWithoutSignExtension.length() - estFirma.length() - 1);
					estensioneTrovata = true;
					break;
				} 	
			}
		}
		
		boolean extValida = false;		
	
		if (StringUtils.isNotBlank(fileNameWithoutSignExtension)) {
			String[] tokens = StringUtils.split(fileNameWithoutSignExtension, '.');
			String token = tokens[tokens.length - 1];
			// Sono arrivato alla prima estensione utile del file, verifico se è coerente con il mimetype del file
			if (token.equalsIgnoreCase(extension)) {
				extValida = true;
			} else {
				for (TEstensioniFmtDig estensioneAssociata : estensioniAssociate) {
					if (token.equalsIgnoreCase(estensioneAssociata.getId().getEstensione())) {
						extValida = true;
					}
				}
			}
		}

		String ret = "";
		if (extValida) {
			// estensione richiesta presente pr cui ritorno il file name orig
			ret = fileName;
		} else {
			// Compongo il nome del file, aggiungendo l'estensione corretta nel punto giusto
			ret = fileNameWithoutSignExtension + "." + extension + suffissoEstFirma;
			
		}

		log.info("Il file da rinominare e' firmato? " + firmato);
		log.info("Formato busta " + envelopeFormat);
		log.info("Formato busta esterna " + envelopeEsternaFormat);
		log.info("Risultato " + ret);
		if (firmato && envelopeEsternaFormat != null && (envelopeEsternaFormat.equals(SignerType.CAdES_BES) || envelopeEsternaFormat.equals(SignerType.CAdES_T)
				|| envelopeEsternaFormat.equals(SignerType.CAdES_C) || envelopeEsternaFormat.equals(SignerType.CAdES_X_Long))) {
			if (!ret.toLowerCase().endsWith("p7m"))
				ret += ".p7m";
		}
		return ret;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	private boolean verificaConversionePdf(String mimetype) {
		PdfConvCtrl bean = FileoperationContextProvider.getApplicationContext().getBean("PdvConversionCtrl", PdfConvCtrl.class);
		List<String> listaMimeType = bean.getConvertibleMimetypes();
		// log.info("listaMimeType " + listaMimeType);
		// log.info("Mime " + mimetype );
		if (listaMimeType != null && listaMimeType.contains(mimetype))
			return true;

		return false;
	}

	private boolean verificaConversionePdfPerFirma(String mimetype) {
		PdfConvCtrl bean = FileoperationContextProvider.getApplicationContext().getBean("PdvConversionCtrl", PdfConvCtrl.class);
		List<String> listaMimeType = bean.getSignConvertibleMimetypes();
		if (listaMimeType != null && listaMimeType.contains(mimetype))
			return true;

		return false;
	}

	public Map<String, TAnagFormatiDig> listAllMimetypes() throws Exception {
		DaoAnagraficaFormatiDigitali dao = new DaoAnagraficaFormatiDigitali();
		return dao.listAll();

	}

	private List<String> getFormatiInterni(File file, String root, Boolean abilitaSbustamento, String extractedFileName, 
			String requestKey) {
		List<String> formatiInterni = new ArrayList<String>();
		UnpackMultipartCtrl bean = FileoperationContextProvider.getApplicationContext().getBean("UnpackMultipartCtrl", UnpackMultipartCtrl.class);

		ResponseUnpackMultipartType opResultMultipart = new ResponseUnpackMultipartType();
		opResultMultipart.setMultipartContents(new MultipartContents());
		try {
			boolean ret = bean.addResponseContentMultipart(file, root, "", true, abilitaSbustamento, opResultMultipart, null,
					opResultMultipart.getMultipartContents().getMultipartContent(), 1, extractedFileName, null, requestKey);
			if (ret) {
				if (opResultMultipart.getMultipartContents() != null) {
					List<MultipartContentType> multipartContents = opResultMultipart.getMultipartContents().getMultipartContent();
					for (MultipartContentType multipartContent : multipartContents) {
						addMimeInterno(formatiInterni, multipartContent);
					}
				}
			}
		} catch (Exception e) {
			log.error("Eccezione getFormatiInterni", e);
		}
		return formatiInterni;
	}

	private void addMimeInterno(List<String> formatiInterni, MultipartContentType multipartContent) {
		if (!formatiInterni.contains(multipartContent.getMimeType())) {
			formatiInterni.add(multipartContent.getMimeType());
		}
		if (multipartContent.getMultipartContent() != null && multipartContent.getMultipartContent().size() > 0) {
			for (MultipartContentType multipartContent1 : multipartContent.getMultipartContent()) {
				addMimeInterno(formatiInterni, multipartContent1);
			}
		}
	}

	public List<String> getRecursiveMimetypes() {
		return recursiveMimetypes;
	}

	public void setRecursiveMimetypes(List<String> recursiveMimetypes) {
		this.recursiveMimetypes = recursiveMimetypes;
	}
	
	

	public static void main(String[] args) {
		String fileName = "";
		String[] tokens = StringUtils.split(fileName, '.');
		String ret = tokens[0];// file with non estension
		
	}
}