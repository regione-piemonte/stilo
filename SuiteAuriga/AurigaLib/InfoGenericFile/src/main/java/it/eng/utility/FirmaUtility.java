/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.DnType;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.InputDigestType;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputSigVerifyType;
import it.eng.fileOperation.clientws.InputSigVerifyType.SignatureVerify;
import it.eng.fileOperation.clientws.InputSigVerifyType.TimestampVerifiy;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponseFileDigestType;
import it.eng.fileOperation.clientws.ResponseSigVerify;
import it.eng.fileOperation.clientws.SigVerifyResultType;
import it.eng.fileOperation.clientws.SigVerifyResultType.SigVerifyResult;
import it.eng.fileOperation.clientws.SignerInformationType;
import it.eng.fileOperation.clientws.TimeStampInfotype;
import it.eng.fileOperation.clientws.VerificationStatusType;
import it.eng.services.fileop.FileOpUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.bean.StampaCertificazioneBean;
import it.eng.utility.bean.StampaCertificazioneBean.FirmatarioExtBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FirmaUtility extends FileOpUtility {

	private static Logger log = Logger.getLogger(FirmaUtility.class);

	private static final String SIGN_OP_FILENOTSIGNED = "SIGN_OP.FILENOTSIGNED";

	// Se possibile non utilizzare questo medoto, ma verificare la presenza o la validità della firma utilizzando le informazioni nell'infoFile
	public static boolean isSigned(String fileUrl, String displayFileName) throws Exception {

		SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext.getContext().getBean("signatureVerifyOptionBean");

		FileOperationResponse lFileOperationResponse = null;

		try {

			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(fileUrl, displayFileName);

			Operations operations = new Operations();

			// FIRMA
			InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
			SignatureVerify lInputSigVerifyTypeSignatureVerify = new SignatureVerify();
			lInputSigVerifyTypeSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
			lInputSigVerifyTypeSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());
			lInputSigVerifyTypeSignatureVerify.setCRLCheck(lSignatureVerifyOptionBean.isCrlReliability());
			TimestampVerifiy timestampVerifiy = new TimestampVerifiy();
			timestampVerifiy.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
			lInputSigVerifyType.setTimestampVerifiy(timestampVerifiy);
			lInputSigVerifyType.setSignatureVerify(lInputSigVerifyTypeSignatureVerify);
			lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
			lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(new Date());
			lInputSigVerifyType.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			operations.getOperation().add(lInputSigVerifyType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore in getInfoFromFile() del file %s [%s]: %s", displayFileName, fileUrl, e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");
			
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();
			
			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				
				if (lAbstractResponseOperationType.getErrorsMessage() != null && lAbstractResponseOperationType.getErrorsMessage().getErrMessage() != null && lAbstractResponseOperationType.getErrorsMessage().getErrMessage().size() > 0) {
					if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						return false;
					}
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				}
				
				if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
					// Verifica firma
					ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;					
					if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
						return false;
					} else { 		
						if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {
							if(!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
								log.error("Firma non valida: il certificato non è in corso di validità");
							}
							if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getVerificationStatus().equals(VerificationStatusType.OK)) {								
								if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage() != null
										&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage() != null
										&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage()
												.size() > 0) {
									StringBuffer lStringBuffer = new StringBuffer();
									for (MessageType lErrorMessage : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage()) {
										lStringBuffer.append(lErrorMessage.getDescription() + "; ");
									}
									log.error("Firma non valida: " + lStringBuffer.toString());
								} else {
									log.error("Firma non valida");
								}													
							}		
							if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
								log.error("Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento");
							}
						}
						return lResponseSigVerify.getSigVerifyResult().getVerificationStatus().equals(VerificationStatusType.OK);
					}					
				}
			}
		}

		return false;
	}
	
	
	public File creaFileCertificazione(String fileUrl, String displayFileName, boolean aggiungiFirma, String processId, Date dataRiferimento) throws Exception {
		return creaFileCertificazione(fileUrl, displayFileName, null, aggiungiFirma, processId, dataRiferimento);
	}
	
	// Utilizzare la firma senza infoFile come parametro di input. Mantengo quasta firma per retrocompatibilità, ma non c'è più bisogno di passare info-file come parametro
	@Deprecated
	public File creaFileCertificazione(String fileUrl, String displayFileName, MimeTypeFirmaBean infoFile, boolean aggiungiFirma, String processId, Date dataRiferimento) throws Exception {

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext.getContext().getBean("signatureVerifyOptionBean");

		FileOperationResponse lFileOperationResponse = null;

		// Viene usato per indendare i firmatari
		int livelloCorrente = 1;
		boolean hasChild = false;
		try {
			
			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(fileUrl, displayFileName);

			Operations operations = new Operations();

			// DIGEST
			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lInputDigestType.setEncoding(lDocumentConfiguration.getEncoding());
			operations.getOperation().add(lInputDigestType);

			// FIRMA
			InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
			SignatureVerify lInputSigVerifyTypeSignatureVerify = new SignatureVerify();
			lInputSigVerifyTypeSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
			lInputSigVerifyTypeSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());
			lInputSigVerifyTypeSignatureVerify.setCRLCheck(lSignatureVerifyOptionBean.isCrlReliability());
			TimestampVerifiy timestampVerifiy = new TimestampVerifiy();
			timestampVerifiy.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
			lInputSigVerifyType.setTimestampVerifiy(timestampVerifiy);
			lInputSigVerifyType.setSignatureVerify(lInputSigVerifyTypeSignatureVerify);
			/**
			 * PER LA VERIFICA RICORSIVA DELLA FIRMA PER TALE FUNZIONALITA I PARAMETRI: RECURSIVE & CHILDVALIDATION
			 * VANNO SETTATI A TRUE
			 */
			lInputSigVerifyType.setRecursive(true);
			lInputSigVerifyType.setChildValidation(true);
			if (dataRiferimento != null) {
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.setTime(dataRiferimento);
				lInputSigVerifyType.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			}
			operations.getOperation().add(lInputSigVerifyType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore in getInfoFromFile() del file %s [%s]: %s", displayFileName, fileUrl, e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();

			GenericFile lGenericFile = new GenericFile();
			StampaCertificazioneBean stampaCertificazioneBean = new StampaCertificazioneBean();

			stampaCertificazioneBean.setNomeFileCertificato(displayFileName);

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				
				if (lAbstractResponseOperationType.getErrorsMessage() != null && lAbstractResponseOperationType.getErrorsMessage().getErrMessage() != null && lAbstractResponseOperationType.getErrorsMessage().getErrMessage().size() > 0) {
					if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						lGenericFile.setFirmato(Flag.NOT_SETTED);
					}
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {
					
					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						stampaCertificazioneBean.setEncode(Hex.encodeHexString(Base64.decodeBase64(lResponseFileDigestType.getResult())));
						if (infoFile != null) {
							stampaCertificazioneBean.setImpronta(infoFile.getImpronta());
							stampaCertificazioneBean.setAlgoritmo(infoFile.getAlgoritmo());
							stampaCertificazioneBean.setEncoding(infoFile.getEncoding());
						} else {
							if (lResponseFileDigestType.getDigestAlgId() != null) {
								stampaCertificazioneBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
							}
							if (lResponseFileDigestType.getEncoding() != null) {
								stampaCertificazioneBean.setEncoding(lResponseFileDigestType.getEncoding().value());
							}
							stampaCertificazioneBean.setImpronta(lResponseFileDigestType.getResult());
						}
					} else if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						// Verifica firma
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
							lGenericFile.setFirmato(Flag.NOT_SETTED);
						} else { 
							lGenericFile.setFirmato(Flag.SETTED);
							if (lResponseSigVerify.getSigVerifyResult().getVerificationStatus().equals(VerificationStatusType.OK)/* || isPAdES*/) {
								stampaCertificazioneBean.setCertificatoIntegro("Le firme risultano valide");
								stampaCertificazioneBean.setEsitoVerificaFirmaBustaEsterna(true);
							} else {
								stampaCertificazioneBean.setCertificatoIntegro("Una/alcune delle firme risultano non valide");
							}								
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {
								if(!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
									log.error("Firma non valida: il certificato non è in corso di validità");
								}
								if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getVerificationStatus().equals(VerificationStatusType.OK)) {								
									if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage() != null
											&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage() != null
											&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage().size() > 0) {
										StringBuffer lStringBuffer = new StringBuffer();
										for (MessageType lErrorMessage : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage()) {
											lStringBuffer.append(lErrorMessage.getDescription() + "; ");
										}
										log.error("Firma non valida: " + lStringBuffer.toString());
									} else {
										log.error("Firma non valida");
									}													
								}	
								// controllo presenza codice eseguibile
								if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.OK) /*|| isPAdES*/) {
									stampaCertificazioneBean.setCodiceEseguibile("Non sono presenti codice eseguibile ne parti variabili all'interno del documento");
								}else if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED) /*|| isPAdES*/) {
										stampaCertificazioneBean.setCodiceEseguibile("Verifica della presenza di codice eseguibile o parti variabili all'interno del documento non eseguita");
								} else {
									log.error("Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento");
									stampaCertificazioneBean.setCodiceEseguibile("Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento");
								}
								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignerInformations().getSignerInformation();
								List<FirmatarioExtBean> firmatariExt = new ArrayList<StampaCertificazioneBean.FirmatarioExtBean>();
								for (SignerInformationType info : infos) {
									firmatariExt.add(creaFirmatarioBean(stampaCertificazioneBean, info, livelloCorrente));
								}
								if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild() != null) {
									hasChild = true;
									analizzaChild(stampaCertificazioneBean, lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(), firmatariExt, livelloCorrente + 1);
								}
								stampaCertificazioneBean.setFirmatari(firmatariExt);
							}
							
						}		
						//  Verifico la presenza di una marca esterna
						SigVerifyResult lSigVerifyResult = lResponseSigVerify.getSigVerifyResult().getSigVerifyResult();
						if(lSigVerifyResult != null && lSigVerifyResult.getTimestampVerificationResult() != null ){
							List<TimeStampInfotype> tsInfos =  lSigVerifyResult.getTimestampVerificationResult().getTimeStampInfo();
							if( tsInfos!=null ){
								for(TimeStampInfotype tsInfo : tsInfos){
									if(tsInfo!=null && tsInfo.getTsaInfo()!=null ) {
										Date oggi = new Date();
										// Se provengo da una firma poi in CalcolaFirmaDatasource aggiorno l'attributo marcaTemporaleAppostaDaAuriga
										stampaCertificazioneBean.setDataOraMarcaTemporale(tsInfo.getDate() != null ? tsInfo.getDate().toGregorianCalendar().getTime() : null);
										stampaCertificazioneBean.setDataOraMarcaTemporale(new Date(new Long(tsInfo.getDateMillisec())));
										stampaCertificazioneBean.setMarcaTemporaleNonValida(tsInfo.getVerificationStatus() != null && tsInfo.getVerificationStatus().equals(VerificationStatusType.KO));
										stampaCertificazioneBean.setDataOraVerificaMarcaTemporale(oggi);
										stampaCertificazioneBean.setTipoMarcaTemporale(tsInfo.getFormat() != null ? tsInfo.getFormat().value() : "");
										stampaCertificazioneBean.setInfoMarcaTemporale(lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString());
										stampaCertificazioneBean.setTsaName(tsInfo.getTsaInfo() != null ? tsInfo.getTsaInfo().getTsaName() : "");
									}
								}
							}
						}
					}					
				}				
			}

			File file = File.createTempFile("export", "");
			Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
			Font font_10Bold = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			Font font_10BoldBlue = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLUE);
			Font font_10BoldRed = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.RED);
			Font font_14 = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD);

			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.newPage();
			document.open();

			document.add(new Paragraph("Rapporto di verifica firma documento digitale", font_14));
			document.add(new Paragraph("Nome file : " + stampaCertificazioneBean.getNomeFileCertificato(), font_10));
			document.add(new Paragraph("Impronta del file : " + stampaCertificazioneBean.getImpronta(), font_10));
			document.add(new Paragraph("Algoritmo di calcolo dell'impronta : " + stampaCertificazioneBean.getAlgoritmo() + " " + stampaCertificazioneBean.getEncoding(), font_10));
			Chunk esitoVerificaChunk = new Chunk("Esito verifica : ", font_10);
			Chunk certificatoIntegroChunk = new Chunk();
			if(stampaCertificazioneBean.isEsitoVerificaFirmaBustaEsterna()) {
				certificatoIntegroChunk = new Chunk(stampaCertificazioneBean.getCertificatoIntegro(), font_10BoldBlue);
			} else {
				certificatoIntegroChunk = new Chunk(stampaCertificazioneBean.getCertificatoIntegro(), font_10BoldRed);
			}
			Paragraph p = new Paragraph();
			p.add(esitoVerificaChunk);
			p.add(certificatoIntegroChunk);
			document.add(p);
//			document.add(new Paragraph("Esito verifica : " + stampaCertificazioneBean.getCertificatoIntegro(), font_10BoldBlue));
			if(hasChild && stampaCertificazioneBean.isEsitoVerificaFirmaBustaEsterna()) {
				document.add(new Paragraph("La validità si riferisce solo alle firme apposte sulla \"busta crittografica\" più esterna come previsto da normativa vigente.\n", font_10Bold));
			}
			document.add(new Paragraph("Codice eseguibile : " + stampaCertificazioneBean.getCodiceEseguibile(), font_10));

			document.add(new Paragraph("\nFirme", font_14));

			livelloCorrente = 1;

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			int indent = 0;

			if (stampaCertificazioneBean.getFirmatari() != null && !stampaCertificazioneBean.getFirmatari().isEmpty()) {
				boolean isFirmaInnestata = true;
				for (FirmatarioExtBean firmatario : stampaCertificazioneBean.getFirmatari()) {
					
					int livelloFiglio = new Integer(firmatario.getFiglioDi()).intValue();

					if (livelloFiglio > livelloCorrente) {
						indent += 30;
						if(isFirmaInnestata) {
							document.add(new Paragraph("\nLe firme riportate a seguire non sono apposte sulla \"busta crittografica\" più esterna che è quella rilevante ai fini della validità della sottoscrizione digitale del file", font_10));
							isFirmaInnestata=false;
						}
						
					} else if (livelloFiglio < livelloCorrente) {
						indent -= 30;
					}

					Paragraph firma = new Paragraph("\nFirma ");
					firma.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					firma.setIndentationLeft(indent);
					document.add(firma);
					
					if(firmatario.getTipoFirmaQA() != null && !"".equalsIgnoreCase(firmatario.getTipoFirmaQA())) {
						String tipoFirmaQATitle="";
						if("Q".equalsIgnoreCase(firmatario.getTipoFirmaQA())) {
							tipoFirmaQATitle = "firma elettronica qualificata (digitale)";
						} else if("A".equalsIgnoreCase(firmatario.getTipoFirmaQA())) {
							tipoFirmaQATitle = "firma elettronica avanzata (sigillo)";
						}
						Paragraph tipoFirmaQA = new Paragraph("Tipo di firma: " + tipoFirmaQATitle, font_10BoldBlue);
						tipoFirmaQA.setIndentationLeft(indent + 10);
						document.add(tipoFirmaQA);
					}
	
					Paragraph intestatario = new Paragraph("Intestatario " + firmatario.getSubject(), font_10);
					intestatario.setIndentationLeft(indent + 10);
					document.add(intestatario);

					Paragraph ente = new Paragraph("Ente certificatore " + firmatario.getEnteCertificatore(), font_10);
					ente.setIndentationLeft(indent + 10);
					document.add(ente);

					Paragraph statoCertificato = new Paragraph(firmatario.getStatoCertificato(), font_10);
					statoCertificato.setIndentationLeft(indent + 10);
					document.add(statoCertificato);

					Paragraph dataEmissione = new Paragraph("Data emissione certificato " + firmatario.getDataEmissione(), font_10);
					dataEmissione.setIndentationLeft(indent + 10);
					document.add(dataEmissione);

					Paragraph dataScadenza = new Paragraph("Data scadenza certificato " + firmatario.getDataScadenza(), font_10);
					dataScadenza.setIndentationLeft(indent + 10);
					document.add(dataScadenza);

					if (firmatario.getSerialNumber() != null && !firmatario.getSerialNumber().equals("")) {
						Paragraph serialNumber = new Paragraph("SerialNumber " + firmatario.getSerialNumber(), font_10);
						serialNumber.setIndentationLeft(indent + 10);
						document.add(serialNumber);
					}

					Paragraph firmeTitle = new Paragraph("\nVerifiche ");
					firmeTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					firmeTitle.setIndentationLeft(indent);
					document.add(firmeTitle);

					Paragraph verificationStatus;
					if (firmatario.isFirmaValida()) {
						verificationStatus = new Paragraph(firmatario.getVerificationStatus(), font_10BoldBlue);
					} else {
						verificationStatus = new Paragraph(firmatario.getVerificationStatus(), font_10BoldRed);
					}
					verificationStatus.setIndentationLeft(indent + 10);
					document.add(verificationStatus);

					Paragraph certExpiration = null;
					if (firmatario.getMarca() != null && firmatario.getMarca().getDate() != null && firmatario.getMarca().getDate().toGregorianCalendar() != null) {
						certExpiration = new Paragraph(firmatario.getCertExpiration() + " alla data della marca " + sdf.format(firmatario.getMarca().getDate().toGregorianCalendar().getTime()), font_10);
					} else {
						certExpiration = new Paragraph(firmatario.getCertExpiration(), font_10);
					}
					certExpiration.setIndentationLeft(indent + 10);
					document.add(certExpiration);

					Paragraph crlResult = null;
					if (firmatario.getMarca() != null && firmatario.getMarca().getDate() != null && firmatario.getMarca().getDate().toGregorianCalendar() != null) {
						crlResult = new Paragraph(firmatario.getCrlResult() + " alla data della marca " + sdf.format(firmatario.getMarca().getDate().toGregorianCalendar().getTime()), font_10);
					} else {
						crlResult = new Paragraph(firmatario.getCrlResult(), font_10);
					}
					crlResult.setIndentationLeft(indent + 10);
					document.add(crlResult);

					Paragraph caReliability = new Paragraph(firmatario.getCaReliability(), font_10);
					caReliability.setIndentationLeft(indent + 10);
					document.add(caReliability);

					if (firmatario.getQcStatements() != null && firmatario.getQcStatements().length > 0) {

						Paragraph qcStatementsTitle = new Paragraph("\nQcStatements ");
						qcStatementsTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
						qcStatementsTitle.setIndentationLeft(indent);
						document.add(qcStatementsTitle);

						float indentqc = indent + 10;

						for (int i = 0; i < firmatario.getQcStatements().length; i++) {
							Paragraph qcStatement = new Paragraph("QcStatement " + firmatario.getQcStatements()[i], font_10);
							qcStatement.setIndentationLeft(indentqc);
							document.add(qcStatement);
						}
					}

					if (firmatario.getKeyUsages() != null && firmatario.getKeyUsages().length > 0) {

						Paragraph keyUsagesTitle = new Paragraph("\nKeyUsages ");
						keyUsagesTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
						keyUsagesTitle.setIndentationLeft(indent);
						document.add(keyUsagesTitle);

						float indentkeyUsages = indent + 10;

						for (int i = 0; i < firmatario.getKeyUsages().length; i++) {
							Paragraph keyUsage = new Paragraph("KeyUsage " + firmatario.getKeyUsages()[i], font_10);
							keyUsage.setIndentationLeft(indentkeyUsages);
							document.add(keyUsage);
						}
					}

					if (firmatario.getMarca() != null) {
						Paragraph marcaTitle = new Paragraph("\nMarca ");
						marcaTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
						marcaTitle.setIndentationLeft(indent);
						document.add(marcaTitle);

						float indentMarca = indent + 10;

						Paragraph dataMarca = new Paragraph("Data marca " + sdf.format(firmatario.getMarca().getDate().toGregorianCalendar().getTime()), font_10);
						dataMarca.setIndentationLeft(indentMarca);
						document.add(dataMarca);

						Paragraph validitaMarca = null;

						if (firmatario.getMarca().getVerificationStatus().equals(VerificationStatusType.OK)) {
							validitaMarca = new Paragraph("Marca temporale valida", font_10);
						} else {
							validitaMarca = new Paragraph("Marca temporale non valida", font_10);
						}

						validitaMarca.setIndentationLeft(indentMarca);
						document.add(validitaMarca);
					}

					if (firmatario.getControFirma() != null) {
						Paragraph controFirmaTitle = new Paragraph("\nContro firma ");
						controFirmaTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
						controFirmaTitle.setIndentationLeft(indent);
						document.add(controFirmaTitle);

						DnType subject = firmatario.getControFirma().getCertificato().getSubject();

						String controFirmatario = (subject.getC().equals(null) ? "" : "C=" + subject.getC())
								+ (subject.getCn() == null ? "" : " CN=" + subject.getCn())
								+ (subject.getName() == null ? "" : " NAME=" + subject.getName())
								+ (subject.getO() == null ? "" : " O=" + subject.getO())
								+ (subject.getOu() == null ? "" : " OU=" + subject.getOu());

						Paragraph controFirma = new Paragraph("Contro firma " + controFirmatario, font_10);
						controFirma.setIndentationLeft(indent + 10);
						document.add(controFirma);
					}
				} 
				if (stampaCertificazioneBean.getDataOraMarcaTemporale() != null) {
					Paragraph marcaEsternaTitle = new Paragraph("\nMarca temporale ", font_14);
					document.add(marcaEsternaTitle);
					
					Paragraph firmatarioTitle = new Paragraph("\n");
					firmatarioTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					firmatarioTitle.setIndentationLeft(indent);
					document.add(firmatarioTitle);

					float indentMarca = indent + 10;
					
					Paragraph validitaMarca = null;					

					if (!stampaCertificazioneBean.isMarcaTemporaleNonValida()) {
						validitaMarca = new Paragraph("Marca temporale valida", font_10BoldBlue);
					} else {
						validitaMarca = new Paragraph("Marca temporale non valida", font_10BoldRed);
					}

					validitaMarca.setIndentationLeft(indentMarca);
					document.add(validitaMarca);

					Paragraph nameMarca = new Paragraph("Time Stamping Authority " + stampaCertificazioneBean.getTsaName(), font_10);
					nameMarca.setIndentationLeft(indentMarca);
					document.add(nameMarca);

					Paragraph dataMarca = new Paragraph("Data marca " + sdf.format(stampaCertificazioneBean.getDataOraMarcaTemporale()), font_10);
					dataMarca.setIndentationLeft(indentMarca);
					document.add(dataMarca);
	
				}
				
			} else {
				log.error("Errore nella creazione del file di certificazione: nessun firmatario recuperato.");
			}
			if (aggiungiFirma) {
				Paragraph footer = new Paragraph(
						"\n\nPer copia conforme del documento informatico sopra indicato costituito da ___ pagine verificato da __________________________ il __________",
						font_14);
				document.add(footer);

				Paragraph firma = new Paragraph("\n\n Firma _____________________ ", font_14);
				firma.setAlignment(Element.ALIGN_RIGHT);
				document.add(firma);
			}
			document.close();
			return file;
		}
		return null;
	}

	private void analizzaChild(StampaCertificazioneBean stampaCertificazioneBean, SigVerifyResultType child, List<FirmatarioExtBean> firmatari, int livello) {
		SigVerifyResult lSigVerifyResultTypeSigVerifyResultChild = child.getSigVerifyResult();
		lSigVerifyResultTypeSigVerifyResultChild.getFormatResult().getEnvelopeFormat();
		List<SignerInformationType> infosChild = lSigVerifyResultTypeSigVerifyResultChild.getSignerInformations().getSignerInformation();
		for (SignerInformationType info : infosChild) {
			firmatari.add(creaFirmatarioBean(stampaCertificazioneBean, info, livello));
		}
		if (child.getSigVerifyResult().getChild() != null) {
			analizzaChild(stampaCertificazioneBean, child.getSigVerifyResult().getChild(), firmatari, livello + 1);
		}
	}

	private FirmatarioExtBean creaFirmatarioBean(StampaCertificazioneBean stampaCertificazioneBean, SignerInformationType info, int livelloCorrente) {

		FirmatarioExtBean firmatarioExt = stampaCertificazioneBean.new FirmatarioExtBean();

		if( info.getCertificato() != null ){
			DnType subject = info.getCertificato().getSubject();
	
			String firmatario = (subject.getC() == null ? "" : "C=" + subject.getC()) + (subject.getCn() == null ? "" : " CN=" + subject.getCn())
					+ (subject.getName() == null ? "" : " NAME=" + subject.getName()) + (subject.getO() == null ? "" : " O=" + subject.getO())
					+ (subject.getOu() == null ? "" : " OU=" + subject.getOu());
	
			DnType issuer = info.getCertificato().getIssuer();
	
			firmatarioExt.setTipoFirmaQA(info.getTipoFirmaQA() != null ? 
					info.getTipoFirmaQA().name() : "");
			firmatarioExt.setSubject(firmatario);
			firmatarioExt.setEnteCertificatore(issuer.getO());
	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
			firmatarioExt.setDataEmissione(sdf.format(info.getCertificato().getDataDecorrenza().toGregorianCalendar().getTime()));
			firmatarioExt.setDataScadenza(sdf.format(info.getCertificato().getDataScadenza().toGregorianCalendar().getTime()));
	
			firmatarioExt.setSerialNumber(info.getCertificato().getSerialNumber());
			firmatarioExt.setQcStatements(info.getCertificato().getQcStatements().getQcStatement()
					.toArray(new String[info.getCertificato().getQcStatements().getQcStatement().size()]));
			firmatarioExt.setKeyUsages(info.getCertificato().getKeyUsages().getKeyUsage()
					.toArray(new String[info.getCertificato().getKeyUsages().getKeyUsage().size()]));
		}
		
		firmatarioExt.setMarca(info.getMarca());

		firmatarioExt.setControFirma(info.getControFirma());

		firmatarioExt.setStatoCertificato(info.getVerificationStatus().equals(VerificationStatusType.OK) ? "Certificato credibile"
				: "Certificato non credibile");
		firmatarioExt
				.setCertExpiration(info.getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK) ? "Il certificato è in corso di validità"
						: "Il certificato non è in corso di validità");

		if (info.getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setFirmaValida(true);
			firmatarioExt.setVerificationStatus("La firma risulta valida");
		} else {
			firmatarioExt.setFirmaValida(false);
			firmatarioExt.setVerificationStatus("La firma risulta non valida");
		}

		if (info.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setCrlResult("Il certificato non è revocato ne sospeso alla data");
		}
		if (info.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
			firmatarioExt.setCrlResult("Il certificato è revocato e sospeso alla data");
		}
		if (info.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
			firmatarioExt.setCrlResult("La verifica dello stato revoca/sospensione non è stata possibile");
		}

		if (info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setCaReliability("Il certificato è emesso da una CA accreditata");
		}
		if (info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
			firmatarioExt.setCaReliability("Il certificato non è emesso da una CA accreditata");
		}
		if (info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
			firmatarioExt.setCaReliability("Non è possibile verificare l'affidabilità della CA");
		}

		firmatarioExt.setFiglioDi("" + livelloCorrente);

		return firmatarioExt;
	}
	
}