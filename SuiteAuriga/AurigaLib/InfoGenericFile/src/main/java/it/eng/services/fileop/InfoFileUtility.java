/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.services.fileop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.apache.axis.types.URI;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.DnType;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.InputConversionType;
import it.eng.fileOperation.clientws.InputDigestType;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputFormatRecognitionType;
import it.eng.fileOperation.clientws.InputSigVerifyType;
import it.eng.fileOperation.clientws.InputSigVerifyType.SignatureVerify;
import it.eng.fileOperation.clientws.InputSigVerifyType.TimestampVerifiy;
import it.eng.fileOperation.clientws.InputUnpackType;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponseFileDigestType;
import it.eng.fileOperation.clientws.ResponseFormatRecognitionType;
import it.eng.fileOperation.clientws.ResponsePdfConvResultType;
import it.eng.fileOperation.clientws.ResponseSigVerify;
import it.eng.fileOperation.clientws.ResponseUnpackType;
import it.eng.fileOperation.clientws.SigVerifyResultType;
import it.eng.fileOperation.clientws.SigVerifyResultType.SigVerifyResult;
import it.eng.fileOperation.clientws.SignerInformationType;
import it.eng.fileOperation.clientws.TimeStampInfotype;
import it.eng.fileOperation.clientws.VerificationStatusType;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FormatConverterInterface;
import it.eng.utility.SignatureVerifyOptionBean;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.Marca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class InfoFileUtility extends FileOpUtility {

	private static final Logger log = Logger.getLogger(InfoFileUtility.class);

	private static final String SIGN_OP_FILENOTSIGNED = "SIGN_OP.FILENOTSIGNED";

//	private int count;
//	private List<String> openFolders;

	public MimeTypeFirmaBean getInfoFromFile(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif) throws Exception {
		return getInfoFromFile(fileUrl, displayFileName, fromScanner, dataRif, false);
	}
	
	public MimeTypeFirmaBean getInfoFromFile(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, boolean fromUploadServlet) throws Exception {

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext.getContext().getBean("signatureVerifyOptionBean");

		FileOperationResponse lFileOperationResponse = null;
		int livelloCorrente = 1;

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
			lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
			lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
	
			if (dataRif != null) {
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.setTime(dataRif);
				lInputSigVerifyType.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			}
			operations.getOperation().add(lInputSigVerifyType);

			// FORMAT RECOGNITION
			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore in getInfoFromFile() del file %s [%s]: %s", displayFileName, fileUrl, e.getMessage()), e);
			throw e;
		}

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lMimeTypeFirmaBean.setBytes(new File(new URI(fileUrl).getPath()).length());
		} catch (Exception e) {
			log.warn(e);
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults()
					.getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				if (lAbstractResponseOperationType.getErrorsMessage() != null && lAbstractResponseOperationType.getErrorsMessage().getErrMessage() != null
						&& lAbstractResponseOperationType.getErrorsMessage().getErrMessage().size() > 0) {
					if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						lMimeTypeFirmaBean.setFirmato(false);
						lMimeTypeFirmaBean.setFirmaValida(false);
					}
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {

					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lMimeTypeFirmaBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lMimeTypeFirmaBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lMimeTypeFirmaBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lMimeTypeFirmaBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lMimeTypeFirmaBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lMimeTypeFirmaBean.setCorrectFileName(getCorrectFileName(displayFileName, lResponseFormatRecognitionType.getNewFileName(), lDocumentConfiguration));
						if (lResponseFormatRecognitionType.getDatiFormatiInterni() != null
								&& lResponseFormatRecognitionType.getDatiFormatiInterni().getMimeType() != null) {
							String mimetypesContenuto = null;
							for (String mimetype : lResponseFormatRecognitionType.getDatiFormatiInterni().getMimeType()) {
								if (mimetypesContenuto == null || "".equals(mimetypesContenuto)) {
									mimetypesContenuto = mimetype;
								} else {
									mimetypesContenuto += ";" + mimetype;
								}
							}
							lMimeTypeFirmaBean.setMimetypesContenuto(mimetypesContenuto);
						}
						//TODO EVOLUTIVE PDF 
						lMimeTypeFirmaBean.setPdfConCommenti(lResponseFormatRecognitionType.isIsPdfCommenti() != null ? lResponseFormatRecognitionType.isIsPdfCommenti() : false);
						lMimeTypeFirmaBean.setPdfEditabile(lResponseFormatRecognitionType.isIsPdfEditabili() != null ? lResponseFormatRecognitionType.isIsPdfEditabili() : false);
						lMimeTypeFirmaBean.setNumPaginePdf(lResponseFormatRecognitionType.getNumeroPaginePdf() != null ? lResponseFormatRecognitionType.getNumeroPaginePdf() : null);
					} else if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						// Verifica firma
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
							
							lMimeTypeFirmaBean.setFirmato(false);
							lMimeTypeFirmaBean.setFirmaValida(false);
							
						} else {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date oggi = new Date();
							lMimeTypeFirmaBean.setFirmato(true);
							lMimeTypeFirmaBean.setFirmaValida(lResponseSigVerify.getSigVerifyResult().getVerificationStatus().equals(VerificationStatusType.OK));
							
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {

								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignerInformations().getSignerInformation();
								lMimeTypeFirmaBean.setTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getFormatResult().getEnvelopeFormat());
								List<String> firmatari = new ArrayList<String>();
								List<Firmatari> buste = new ArrayList<Firmatari>();
								
								for (SignerInformationType info : infos) {
									buste.add(creaFirmatarioBean(info, lResponseSigVerify.getSigVerifyResult().getSigVerifyResult(), livelloCorrente, lDocumentConfiguration, lSignatureVerifyOptionBean));
								}
								
								if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild() != null) {
									analizzaChild(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(), buste, livelloCorrente + 1, lDocumentConfiguration, lSignatureVerifyOptionBean);
								}
				
								// Creo la lista dei firmatari
								if (buste != null && ! buste.isEmpty()){
									for (Firmatari busta : buste) {
										firmatari.add(busta.getNomeFirmatario());
									}						
								}
								lMimeTypeFirmaBean.setFirmatari(firmatari.toArray(new String[] {}));
								
								lMimeTypeFirmaBean.setBuste(buste.toArray(new Firmatari[] {}));
								
								// Inserisco e informazioni di firma e marca della busta più esterna
								InfoFirmaMarca infoFirmaMarca = new InfoFirmaMarca();
								infoFirmaMarca.setDataOraVerificaFirma(oggi);
								infoFirmaMarca.setFirmeNonValideBustaCrittografica(!lResponseSigVerify.getSigVerifyResult().getVerificationStatus().equals(VerificationStatusType.OK));
								// Se provengo da una firma poi in CalcolaFirmaDatasource aggiorno il parametro firmeExtraAuriga e settto i parametri delle firme interne
								infoFirmaMarca.setFirmeExtraAuriga(true);
								infoFirmaMarca.setTipoBustaCrittografica(getTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()));
								infoFirmaMarca.setInfoBustaCrittografica(lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString());
								
								//  Verifico la presenza di una marca esterna
								SigVerifyResult lSigVerifyResult = lResponseSigVerify.getSigVerifyResult().getSigVerifyResult();
								if(lSigVerifyResult != null && lSigVerifyResult.getTimestampVerificationResult() != null ){
									List<TimeStampInfotype> tsInfos =  lSigVerifyResult.getTimestampVerificationResult().getTimeStampInfo();
									if( tsInfos!=null ){
										for(TimeStampInfotype tsInfo : tsInfos){
											if(tsInfo!=null && tsInfo.getTsaInfo()!=null ) {
												// Se provengo da una firma poi in CalcolaFirmaDatasource aggiorno l'attributo marcaTemporaleAppostaDaAuriga
												// infoFirmaMarca.setDataOraMarcaTemporale(tsInfo.getDate() != null ? tsInfo.getDate().toGregorianCalendar().getTime() : null);
												infoFirmaMarca.setDataOraMarcaTemporale(StringUtils.isNotBlank(tsInfo.getDateMillisec()) ? new Date(new Long(tsInfo.getDateMillisec())) : null);
												infoFirmaMarca.setMarcaTemporaleNonValida(tsInfo.getVerificationStatus() != null && tsInfo.getVerificationStatus().equals(VerificationStatusType.KO));
												infoFirmaMarca.setDataOraVerificaMarcaTemporale(oggi);
												infoFirmaMarca.setTipoMarcaTemporale(tsInfo.getFormat() != null ? tsInfo.getFormat().value() : "");
												infoFirmaMarca.setInfoMarcaTemporale(lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString());
											}
										}
									}
								}

								lMimeTypeFirmaBean.setInfoFirmaMarca(infoFirmaMarca);
																
								if(!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
//									lMimeTypeFirmaBean.setFirmaValida(false);
									log.error("Firma non valida: il certificato non è in corso di validità");
								}
								
								if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getVerificationStatus().equals(VerificationStatusType.OK)) {								
//									lMimeTypeFirmaBean.setFirmaValida(false);
									if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage() != null
											&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage() != null
											&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage()
													.size() > 0) {
										StringBuffer lStringBuffer = new StringBuffer();
										for (MessageType lErrorMessage : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult()
												.getErrorsMessage().getErrMessage()) {
											lStringBuffer.append(lErrorMessage.getDescription() + "; ");
										}
										log.error("Firma non valida: " + lStringBuffer.toString());
									} else {
										log.error("Firma non valida");
									}
								}
								
								if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
//									lMimeTypeFirmaBean.setFirmaValida(false);
									log.error("Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento");
								}
							}						
						}
					}
				}
			}
			
			try {
				lMimeTypeFirmaBean.setConvertibile(SpringAppContext.getContext().getBean(FormatConverterInterface.class)
						.isValidFormat(lMimeTypeFirmaBean.getMimetype(), null));
			} catch (Exception e) {}
			
			// Verifico se è un pdf protetto
			boolean pdfProtetto = checkProtectedFile(fileUrl, lMimeTypeFirmaBean);
			lMimeTypeFirmaBean.setPdfProtetto(pdfProtetto);
			
			return lMimeTypeFirmaBean;

		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico" + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lMimeTypeFirmaBean;
	}
	
	public MimeTypeFirmaBean getInfoFromFileNoOut(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, boolean fromUploadServlet) throws Exception {
		return getInfoFromFile(fileUrl, displayFileName, fromScanner, dataRif, fromUploadServlet);
	}
	
	public MimeTypeFirmaBean getInfoFromFileRer(File fileUrl, String displayFileName, boolean fromScanner, Date dataRif, boolean fromUploadServlet) throws Exception {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("document.xml");
	    DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)appContext.getBean("DocumentConfiguration");
		SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) appContext.getBean("signatureVerifyOptionBean");

		FileOperationResponse lFileOperationResponse = null;
		int livelloCorrente = 1;

		try {

			InputFileOperationType lInputFileOperationType = buildInputFileOperationTypeRer(fileUrl, displayFileName);

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
			lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
			lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
			/**
			 * PER LA VERIFICA RICORSIVA DELLA FIRMA PER TALE FUNZIONALITA I PARAMETRI: RECURSIVE & CHILDVALIDATION
			 * VANNO SETTATI A TRUE
			 */
			lInputSigVerifyType.setRecursive(true);
			lInputSigVerifyType.setChildValidation(true);
			if (dataRif != null) {
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.setTime(dataRif);
				lInputSigVerifyType.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			}
			operations.getOperation().add(lInputSigVerifyType);

			// FORMAT RECOGNITION
			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			lFileOperationResponse = callFileOperationRer(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore in getInfoFromFile() del file %s [%s]: %s", displayFileName, fileUrl, e.getMessage()), e);
			throw e;
		}

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lMimeTypeFirmaBean.setBytes(fileUrl.length());
		} catch (Exception e) {
			log.warn(e);
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults()
					.getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				if (lAbstractResponseOperationType.getErrorsMessage() != null && lAbstractResponseOperationType.getErrorsMessage().getErrMessage() != null
						&& lAbstractResponseOperationType.getErrorsMessage().getErrMessage().size() > 0) {
					if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						lMimeTypeFirmaBean.setFirmato(false);
						lMimeTypeFirmaBean.setFirmaValida(false);
					}
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {

					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lMimeTypeFirmaBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lMimeTypeFirmaBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lMimeTypeFirmaBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lMimeTypeFirmaBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lMimeTypeFirmaBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lMimeTypeFirmaBean.setCorrectFileName(getCorrectFileName(displayFileName, lResponseFormatRecognitionType.getNewFileName(), lDocumentConfiguration));
						if (lResponseFormatRecognitionType.getDatiFormatiInterni() != null
								&& lResponseFormatRecognitionType.getDatiFormatiInterni().getMimeType() != null) {
							String mimetypesContenuto = null;
							for (String mimetype : lResponseFormatRecognitionType.getDatiFormatiInterni().getMimeType()) {
								if (mimetypesContenuto == null || "".equals(mimetypesContenuto)) {
									mimetypesContenuto = mimetype;
								} else {
									mimetypesContenuto += ";" + mimetype;
								}
							}
							lMimeTypeFirmaBean.setMimetypesContenuto(mimetypesContenuto);
						}
					} else if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						// Verifica firma
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
							
							lMimeTypeFirmaBean.setFirmato(false);
							lMimeTypeFirmaBean.setFirmaValida(false);
							
						} else {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date oggi = new Date();
							lMimeTypeFirmaBean.setFirmato(true);
							lMimeTypeFirmaBean.setFirmaValida(lResponseSigVerify.getSigVerifyResult().getVerificationStatus().equals(VerificationStatusType.OK));
							
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {

								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignerInformations().getSignerInformation();
								lMimeTypeFirmaBean.setTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getFormatResult().getEnvelopeFormat());
								List<String> firmatari = new ArrayList<String>();
								List<Firmatari> buste = new ArrayList<Firmatari>();
								
								for (SignerInformationType info : infos) {
									buste.add(creaFirmatarioBean(info, lResponseSigVerify.getSigVerifyResult().getSigVerifyResult(), livelloCorrente, lDocumentConfiguration, lSignatureVerifyOptionBean));
								}
								
								if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild() != null) {
									analizzaChild(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(), buste, livelloCorrente + 1, lDocumentConfiguration, lSignatureVerifyOptionBean);
								}
				
								// Creo la lista dei firmatari
								if (buste != null && ! buste.isEmpty()){
									for (Firmatari busta : buste) {
										firmatari.add(busta.getNomeFirmatario());
									}						
								}
								lMimeTypeFirmaBean.setFirmatari(firmatari.toArray(new String[] {}));
								
								lMimeTypeFirmaBean.setBuste(buste.toArray(new Firmatari[] {}));
								
								// Inserisco e informazioni di firma e marca della busta più esterna
								InfoFirmaMarca infoFirmaMarca = new InfoFirmaMarca();
								infoFirmaMarca.setDataOraVerificaFirma(oggi);
								infoFirmaMarca.setFirmeNonValideBustaCrittografica(!lResponseSigVerify.getSigVerifyResult().getVerificationStatus().equals(VerificationStatusType.OK));
								// Se provengo da una firma poi in CalcolaFirmaDatasource aggiorno il parametro firmeExtraAuriga e settto i parametri delle firme interne
								infoFirmaMarca.setFirmeExtraAuriga(true);
								infoFirmaMarca.setTipoBustaCrittografica(getTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()));
								infoFirmaMarca.setInfoBustaCrittografica(lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString());
								
								//  Verifico la presenza di una marca esterna
								SigVerifyResult lSigVerifyResult = lResponseSigVerify.getSigVerifyResult().getSigVerifyResult();
								if(lSigVerifyResult != null && lSigVerifyResult.getTimestampVerificationResult() != null ){
									List<TimeStampInfotype> tsInfos =  lSigVerifyResult.getTimestampVerificationResult().getTimeStampInfo();
									if( tsInfos!=null ){
										for(TimeStampInfotype tsInfo : tsInfos){
											if(tsInfo!=null && tsInfo.getTsaInfo()!=null ) {
												// Se provengo da una firma poi in CalcolaFirmaDatasource aggiorno l'attributo marcaTemporaleAppostaDaAuriga
												// infoFirmaMarca.setDataOraMarcaTemporale(tsInfo.getDate() != null ? tsInfo.getDate().toGregorianCalendar().getTime() : null);
												infoFirmaMarca.setDataOraMarcaTemporale(StringUtils.isNotBlank(tsInfo.getDateMillisec()) ? new Date(new Long(tsInfo.getDateMillisec())) : null);
												infoFirmaMarca.setMarcaTemporaleNonValida(tsInfo.getVerificationStatus() != null && tsInfo.getVerificationStatus().equals(VerificationStatusType.KO));
												infoFirmaMarca.setDataOraVerificaMarcaTemporale(oggi);
												infoFirmaMarca.setTipoMarcaTemporale(tsInfo.getFormat() != null ? tsInfo.getFormat().value() : "");
												infoFirmaMarca.setInfoMarcaTemporale(lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString());
											}
										}
									}
								}
								
								lMimeTypeFirmaBean.setInfoFirmaMarca(infoFirmaMarca);
																
								if(!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
//									lMimeTypeFirmaBean.setFirmaValida(false);
									log.error("Firma non valida: il certificato non in corso di validità");
								}
								
								if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getVerificationStatus().equals(VerificationStatusType.OK)) {								
//									lMimeTypeFirmaBean.setFirmaValida(false);
									if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage() != null
											&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage() != null
											&& lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage()
													.size() > 0) {
										StringBuffer lStringBuffer = new StringBuffer();
										for (MessageType lErrorMessage : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult()
												.getErrorsMessage().getErrMessage()) {
											lStringBuffer.append(lErrorMessage.getDescription() + "; ");
										}
										log.error("Firma non valida: " + lStringBuffer.toString());
									} else {
										log.error("Firma non valida");
									}
								}
								
								if (!lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
//									lMimeTypeFirmaBean.setFirmaValida(false);
									log.error("Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento");
								}
							}						
						}
					}
				}
			}
			
			try {
				lMimeTypeFirmaBean.setConvertibile(SpringAppContext.getContext().getBean(FormatConverterInterface.class)
						.isValidFormat(lMimeTypeFirmaBean.getMimetype(), null));
			} catch (Exception e) {}
			
			/*// Verifico se è un pdf protetto
			boolean pdfProtetto = checkProtectedFile(fileUrl, lMimeTypeFirmaBean);
			lMimeTypeFirmaBean.setPdfProtetto(pdfProtetto);*/
			
			return lMimeTypeFirmaBean;

		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico" + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lMimeTypeFirmaBean;
	}

	public String calcolaImpronta(String fileUrl, String displayFileName) throws Exception {

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		FileOperationResponse lFileOperationResponse = null;

		try {

			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(fileUrl, displayFileName);

			Operations operations = new Operations();

			// DIGEST
			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lInputDigestType.setEncoding(lDocumentConfiguration.getEncoding());
			operations.getOperation().add(lInputDigestType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore in calcolaImpronta() del file %s [%s]: %s", displayFileName, fileUrl, e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults()
					.getFileOperationResult();
			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
					ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
					if (lResponseFileDigestType.getErrorsMessage() != null && lResponseFileDigestType.getErrorsMessage().getErrMessage() != null
							&& lResponseFileDigestType.getErrorsMessage().getErrMessage().size() > 0) {
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						for (MessageType message : errors) {
							log.error("Errore " + message.getCode() + " - " + message.getDescription());
						}
					} else {
						return lResponseFileDigestType.getResult();
					}
				}
			}
		}

		return null;
	}

	public InputStream sbusta(String fileUrl, String displayFileName) throws Exception {
		InputFileOperationType lInputFileOperationType = buildInputFileOperationType(fileUrl, displayFileName);
		return sbusta(lInputFileOperationType);
	}

	@Deprecated
	// Utilizzare il metodo sbusta che riceve in ingresso l'url del file
	public InputStream sbusta(File file, String displayFileName) throws Exception {
		InputFileOperationType lInputFileOperationType = buildInputFileOperationType(file, displayFileName);
		return sbusta(lInputFileOperationType);
	}

	public InputStream sbusta(InputFileOperationType lInputFileOperationType) throws Exception {

		FileOperationResponse lFileOperationResponse = null;

		try {

			Operations operations = new Operations();

			// SBUSTA
			InputUnpackType lInputUnpackType = new InputUnpackType();
			operations.getOperation().add(lInputUnpackType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore durante lo sbustamento del file: %s", e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults()
					.getFileOperationResult();
			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				if (lAbstractResponseOperationType instanceof ResponseUnpackType) {
					ResponseUnpackType lResponseUnpackType = (ResponseUnpackType) lAbstractResponseOperationType;
					if (lResponseUnpackType.getErrorsMessage() != null && lResponseUnpackType.getErrorsMessage().getErrMessage() != null
							&& lResponseUnpackType.getErrorsMessage().getErrMessage().size() > 0) {
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						for (MessageType message : errors) {
							log.error("Errore " + message.getCode() + " - " + message.getDescription());
						}
					} else {
						return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Riceve un input stream di un file che non è un pdf e restituisce un pdf
	 * 
	 * @param lInputStream
	 * @return
	 * @throws Exception
	 */

	public InputStream converti(String fileUrl, String displayFileName) throws Exception {
		return converti(fileUrl, displayFileName, null);
	}

	public InputStream converti(String fileUrl, String displayFileName, Boolean isOdt) throws Exception {
		return converti(fileUrl, displayFileName, isOdt, null);
	}

	public InputStream converti(String fileUrl, String displayFileName, Boolean isOdt, Boolean pdfa) throws Exception {

		FileOperationResponse lFileOperationResponse = null;

		try {

			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(fileUrl, displayFileName);

			Operations operations = new Operations();

			InputConversionType lInputConversionType = new InputConversionType();
			lInputConversionType.setPdfA(pdfa != null ? pdfa : false);
			if (isOdt != null) {
				// lInputConversionType.setIsOdt(isOdt);
			}
			operations.getOperation().add(lInputConversionType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore durante la conversione del file: %s", e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults()
					.getFileOperationResult();
			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				if (lAbstractResponseOperationType instanceof ResponsePdfConvResultType) {
					ResponsePdfConvResultType lResponsePdfConvResultType = (ResponsePdfConvResultType) lAbstractResponseOperationType;
					if (lResponsePdfConvResultType.getErrorsMessage() != null && lResponsePdfConvResultType.getErrorsMessage().getErrMessage() != null
							&& lResponsePdfConvResultType.getErrorsMessage().getErrMessage().size() > 0) {
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						StringBuffer error = new StringBuffer();
						boolean first = true;
						for (MessageType message : errors) {
							log.error(message.getDescription());
							if (first) {
								first = false;
							} else {
								error.append(";");
							}
							error.append(message.getDescription());
						}
						throw new Exception(error.toString());
					} else {
						return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();
					}
				}
			}
		}

		return null;
	}
	
	/**
	 * Verifica se il file pdf associato all'inputstream in ingresso è un PDFA
	 * @param inputStream InputStream del file pdf da verificare
	 * @return true se il file è un PDFA
	 * @throws Exception
	 */
//	public boolean isPdfA(InputStream inputStream) throws Exception {
//		boolean isPdfA = false;
//		try {
//			File.crea
//			// Collego il reader al file e verifico se è già PDFA
//        	PdfReader reader = new PdfReader(inputStream);
//	        return reader.getMetadata() != null && (new String(reader.getMetadata())).contains("pdfa");
//	        String pdfMatadata = reader.getMetadata() != null ? new String(reader.getMetadata()) : null;
//	        if (StringUtils.isNotBlank(pdfMatadata)) {
//		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		        factory.setIgnoringElementContentWhitespace(true);
//		        factory.setExpandEntityReferences(false);
//		        DocumentBuilder builder = factory.newDocumentBuilder();
//		        InputSource is = new InputSource(new StringReader(pdfMatadata));
//		        org.w3c.dom.Document doc = builder.parse(is);
//		        Node pdfConformanceNode = doc.getElementsByTagName("pdfaid:conformance") != null && doc.getElementsByTagName("pdfaid:conformance").getLength() > 0 ? doc.getElementsByTagName("pdfaid:conformance").item(0) : null;
//		        if (pdfConformanceNode != null) {
//		        	if ("A".equalsIgnoreCase(pdfConformanceNode.getTextContent())){
//		        		isPdfA = true;
//		        	}
//		        }
//	        }
//	        return isPdfA;
//        }catch (Exception e) {
//        	log.error("Errore nell'analisi del pdf. isPdfA restituisce false", e);
//			return false;
//        }
//	}
	
	private Firmatari creaFirmatarioBean(SignerInformationType infoFirmatario, SigVerifyResult infoBusta, int livelloCorrente, DocumentConfiguration lDocumentConfiguration, SignatureVerifyOptionBean lSignatureVerifyOptionBean) {

		Firmatari firmatarioExt = new Firmatari();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String nomeFirmatario = "";
		
		if (infoFirmatario.getCertificato() != null) {
			DnType subject = infoFirmatario.getCertificato().getSubject();
	
			// Dal firmatario tolgo il subject.getName() perchè contiene caratteri che creano troncamenti nel json
			String firmatario = (subject.getC() == null ? "" : "C=" + subject.getC()) + (subject.getCn() == null ? "" : " CN=" + subject.getCn())
					/**+ (subject.getName() == null ? "" : " NAME=" + subject.getName())*/ + (subject.getO() == null ? "" : " O=" + subject.getO())
					+ (subject.getOu() == null ? "" : " OU=" + subject.getOu());
			DnType issuer = infoFirmatario.getCertificato().getIssuer();
			firmatarioExt.setSubject(firmatario);
			firmatarioExt.setEnteCertificatore(issuer.getO());
			nomeFirmatario = subject.getCn();
			firmatarioExt.setNomeFirmatario(nomeFirmatario);
			firmatarioExt.setDataEmissione(infoFirmatario.getCertificato().getDataDecorrenza().toGregorianCalendar().getTime());
			firmatarioExt.setDataScadenza(infoFirmatario.getCertificato().getDataScadenza().toGregorianCalendar().getTime());
			firmatarioExt.setSerialNumber(infoFirmatario.getCertificato().getSerialNumber());
			firmatarioExt.setKeyUsages(infoFirmatario.getCertificato().getKeyUsages().getKeyUsage().toArray(new String[infoFirmatario.getCertificato().getKeyUsages().getKeyUsage().size()]));
		}
		
		Date dataFirma = infoFirmatario.getSigningTime() != null ? infoFirmatario.getSigningTime().toGregorianCalendar().getTime() : null;
		firmatarioExt.setDataFirma(dataFirma);
		
		if (infoFirmatario.getMarca() != null) {
			Marca marca = new Marca();
			marca.setTsaName(infoFirmatario.getMarca().getTsaName());
			marca.setSerialNumber(infoFirmatario.getMarca().getSerialNumber());
			marca.setPolicy(infoFirmatario.getMarca().getPolicy());
			marca.setDate(infoFirmatario.getMarca().getDate() != null ? infoFirmatario.getMarca().getDate().toGregorianCalendar().getTime() : null);
			marca.setMarcaValida(infoFirmatario.getMarca().getVerificationStatus().equals(VerificationStatusType.OK));
			firmatarioExt.setMarca(marca);
		}		

		firmatarioExt.setStatoCertificato(infoFirmatario.getVerificationStatus().equals(VerificationStatusType.OK) ? "Certificato credibile" : "Certificato non credibile");
		firmatarioExt.setCertExpiration(infoFirmatario.getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK) ? "Il certificato è in corso di validità" : "Il certificato non è in corso di validità");
		
		if (infoFirmatario.getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setFirmaValida(true);
			firmatarioExt.setVerificationStatus("La firma risulta valida");
		} else {
			firmatarioExt.setFirmaValida(false);
			firmatarioExt.setVerificationStatus("La firma risulta non valida");
		}
		Date oggi = new Date();
		firmatarioExt.setDataVerificaFirma(oggi);
		
		String crlResult = "";
		if (infoFirmatario.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
			crlResult = "Il certificato non è revocato ne sospeso alla data";
		}
		if (infoFirmatario.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
			crlResult = "Il certificato è revocato e sospeso alla data";
		}
		if (infoFirmatario.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
			crlResult = "La verifica dello stato revoca/sospensione non è stata possibile";
		}
		firmatarioExt.setCrlResult(crlResult);

		String caReliability = "";
		if (infoFirmatario.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
			caReliability = "Il certificato è emesso da una CA accreditata";
		}
		if (infoFirmatario.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
			caReliability = "Il certificato non è emesso da una CA accreditata";
		}
		if (infoFirmatario.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
			caReliability = "Non è possibile verificare l'affidabilità della CA";
		}
		firmatarioExt.setCaReliability(caReliability);

		String figlioDi = "" + livelloCorrente;
		firmatarioExt.setFiglioDi(figlioDi);
		
		firmatarioExt.setTipoFirmaQA(infoFirmatario.getTipoFirmaQA() != null ? infoFirmatario.getTipoFirmaQA().name() : "");
		firmatarioExt.setInfoFirma(getInfoFirma(lDocumentConfiguration, lSignatureVerifyOptionBean));
		
		String tipoFirma = getTipoFirma(infoBusta);
		firmatarioExt.setTipoFirma(tipoFirma);
		
		if (infoFirmatario.getControFirma() != null && infoFirmatario.getControFirma().getCertificato() != null) {
			SignerInformationType infoFirmatarioControFirma = infoFirmatario.getControFirma();
			Firmatari firmatarioExtControFirma = new Firmatari();	
			
			DnType subjectControFirma = infoFirmatarioControFirma.getCertificato().getSubject();
			// Dal firmatario tolgo il subject.getName() perchè contiene caratteri che creano troncamenti nel json
			String firmatarioControFirma = (subjectControFirma.getC() == null ? "" : "C=" + subjectControFirma.getC()) + (subjectControFirma.getCn() == null ? "" : " CN=" + subjectControFirma.getCn())
					/**+ (subject.getName() == null ? "" : " NAME=" + subject.getName())*/ + (subjectControFirma.getO() == null ? "" : " O=" + subjectControFirma.getO())
					+ (subjectControFirma.getOu() == null ? "" : " OU=" + subjectControFirma.getOu());
	
			DnType issuerControFirma = infoFirmatarioControFirma.getCertificato().getIssuer();
			firmatarioExtControFirma.setSubject(firmatarioControFirma);
			firmatarioExtControFirma.setEnteCertificatore(issuerControFirma.getO());
			firmatarioExtControFirma.setNomeFirmatario(subjectControFirma.getCn());
			firmatarioExtControFirma.setDataEmissione(infoFirmatarioControFirma.getCertificato().getDataDecorrenza() != null ? infoFirmatarioControFirma.getCertificato().getDataDecorrenza().toGregorianCalendar().getTime() : null);
			firmatarioExtControFirma.setDataScadenza(infoFirmatarioControFirma.getCertificato().getDataScadenza() != null ? infoFirmatarioControFirma.getCertificato().getDataScadenza().toGregorianCalendar().getTime() : null);
			firmatarioExtControFirma.setSerialNumber(infoFirmatarioControFirma.getCertificato().getSerialNumber());
			if (infoFirmatarioControFirma.getCertificato().getKeyUsages() != null && infoFirmatarioControFirma.getCertificato().getKeyUsages().getKeyUsage() != null) {
				firmatarioExtControFirma.setKeyUsages(infoFirmatarioControFirma.getCertificato().getKeyUsages().getKeyUsage().toArray(new String[infoFirmatarioControFirma.getCertificato().getKeyUsages().getKeyUsage().size()]));
			}
			firmatarioExtControFirma.setFirmaValida(firmatarioExt.isFirmaValida());
			firmatarioExtControFirma.setVerificationStatus(firmatarioExt.getVerificationStatus());
			firmatarioExtControFirma.setDataVerificaFirma(oggi);
			firmatarioExtControFirma.setCrlResult(crlResult);
			firmatarioExtControFirma.setCaReliability(caReliability);
			firmatarioExtControFirma.setFiglioDi(figlioDi);
			firmatarioExtControFirma.setInfoFirma(getInfoFirmaControFirma(lDocumentConfiguration, lSignatureVerifyOptionBean, nomeFirmatario, dataFirma));
			firmatarioExtControFirma.setTipoFirma(tipoFirma);
			firmatarioExt.setControFirma(firmatarioExtControFirma);
		}

		return firmatarioExt;
	}
	
	private void analizzaChild(SigVerifyResultType child, List<Firmatari> firmatari, int livello, DocumentConfiguration lDocumentConfiguration, SignatureVerifyOptionBean lSignatureVerifyOptionBean) {
		SigVerifyResult lSigVerifyResultTypeSigVerifyResultChild = child.getSigVerifyResult();
		List<SignerInformationType> infosChild = lSigVerifyResultTypeSigVerifyResultChild.getSignerInformations().getSignerInformation();
		for (SignerInformationType info : infosChild) {
			firmatari.add(creaFirmatarioBean(info, lSigVerifyResultTypeSigVerifyResultChild, livello, lDocumentConfiguration, lSignatureVerifyOptionBean));
		}
		if (child.getSigVerifyResult().getChild() != null) {
			analizzaChild(child.getSigVerifyResult().getChild(), firmatari, livello + 1, lDocumentConfiguration, lSignatureVerifyOptionBean);
		}
	}
	
	private String getTipoFirma(SigVerifyResult lSigVerifyResult) {
		if (lSigVerifyResult != null && lSigVerifyResult.getFormatResult() != null) {
			return lSigVerifyResult.getFormatResult().getEnvelopeFormat();
		} 
		return null;
	}
	
	public static String getInfoFirma(DocumentConfiguration lDocumentConfiguration, SignatureVerifyOptionBean lSignatureVerifyOptionBean) {
		return lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString();
	}
	
	public static String getInfoFirmaControFirma(DocumentConfiguration lDocumentConfiguration, SignatureVerifyOptionBean lSignatureVerifyOptionBean, String firmatarioPrimaFirma, Date dataFirma) {
		return "Controfirma alla firma apposta" + (StringUtils.isNotBlank(firmatarioPrimaFirma) ? " da " + firmatarioPrimaFirma : "") + (dataFirma != null ? " in data " + dataFirma : "") + "/n" + lDocumentConfiguration.toString() + "/n" + lSignatureVerifyOptionBean.toString();
	}
	
//	private boolean checkIfPdfProtetto(String fileUrl, MimeTypeFirmaBean lMimeTypeFirmaBean) throws IOException {
//		try {
//			String estensione = (StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName()) ? FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()) : "");
//			boolean isFirmatoCades = lMimeTypeFirmaBean.getTipoFirma() != null && lMimeTypeFirmaBean.getTipoFirma().toLowerCase().contains("cades");
//			if (("pdf").equals(estensione.toLowerCase()) && !isFirmatoCades) {
//				PdfReader reader = new PdfReader(fileUrl);
//				return reader.isEncrypted();
//			} else {
//				return false;
//			}
//		} catch(Exception e) {
//			log.error("Errore nell'analisi del pdf. checkIfPdfProtetto restituisce false", e);
//			return false;
//		}
//	}
	
	/**
	 * Associa all'outputstream fornito in input il file pdf risultato della conversione in pdfa dell'inputstream pdf in ingresso
	 * @param inputStream InputStream dei file pdf da convertire in pdfa
	 * @param output Stream a cui associare il pdf ottenuto dall'unione dei file in ingresso
	 * @throws Exception
	 */
	public void convertiPdfToPdfA(InputStream inputStream, OutputStream output) throws Exception {
		
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		
		Document document = new Document();
		
		PdfAWriter writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U); 
		writer.setTagged();
        writer.setLanguage("it");
        writer.setLinearPageMode();
        writer.createXmpMetadata();
		
		document.open();
		
		PdfContentByte cb = writer.getDirectContent();
		
        // Collego il reader al file
        PdfReader reader = new PdfReader(inputStream);
        // Scorro le pagine da copiare
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {                            
          	addPageToDocument(document, writer, cb, reader, i, false);
        }
        
        output.flush();
        document.close();
        output.close();
	}
	
	// LA LOGICA DI QUESTO METODO È PRESENTE ANCHE NEL METODO ADDPAGETODOCUMENT CLASSE IT.ENG.AURIGA.UI.MODULE.LAYOUT.SERVER.COMMON.MERGEDOCUMENT
	// DEL PROGETTO LISTEXPORTUTILITY
	private void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);
		
		// Federico Cacco 28.01.2016
		// Quando si concatenano pagine che derivano da scansioni si hanno dei problemi di rotazione, in
		// quanto la scansione solitamente produce una immagine orizzontale che viene poi in caso
		// ruotata nel verso giusto prima di essere inserita nel pdf finale. 
		// Devo quindi copiare la pagina tenendo conto di questa rotazione,
		// altrimenti rischio di inserirla storta
		//
		// cb.addTemplate(page, 0, 0);
		
		// Verifico la rotazione della pagina corrente
		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);
		
		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4){
				document.setPageSize(PageSize.A4.rotate());
			}else{
				document.setPageSize(psize);
			}
		} else {
			if (forceA4){
				document.setPageSize(PageSize.A4);
			}else{
				document.setPageSize(psize);
			}
		}
		
		// Creo una nuova pagina nel document in cui copiare la pagina corrente
		document.newPage();
		// Raddrizzo l'immagine a seconda della rotazione
		switch (psize.getRotation()){
		    case 0:
		        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
		        break;
		    case 90:
		        cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
		        break;
		    case 180:
		        cb.addTemplate(page,-1f, 0, 0, -1f, psize.getWidth(),psize.getHeight());
		        break;
		    case 270:
		        cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
		        break;
		    default:
		        break;
		}
	}
	
	public static boolean checkPdfA(String pathFile) throws Exception {
		try {
			PdfReader reader = new PdfReader(pathFile);
			return checkPdfA(reader);
		} catch (Exception e) {
			log.error("Errore nella verifica del pdfA: " + e.getMessage() + ", ritorno false", e);
			return false;
		}
	}
	
	public static boolean checkPdfA(InputStream is) throws Exception {
		try {
			PdfReader reader = new PdfReader(is);
			return checkPdfA(reader);
		} catch (Exception e) {
			log.error("Errore nella verifica del pdfA: " + e.getMessage() + ", ritorno false", e);
			return false;
		}
	}
	
	private static boolean checkPdfA(PdfReader reader) throws IOException {
		return reader.getMetadata() != null && (new String(reader.getMetadata())).contains("pdfaid:conformance");
	}
	
	public static boolean checkProtectedFile(String fileUrl, MimeTypeFirmaBean lMimeTypeFirmaBean) throws IOException {
		try {
			String estensione = (StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName()) ? FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()) : "");
			boolean isFirmatoCades = lMimeTypeFirmaBean.getTipoFirma() != null && lMimeTypeFirmaBean.getTipoFirma().toLowerCase().contains("cades");
			if (("pdf").equals(estensione.toLowerCase()) && !isFirmatoCades) {
				PdfReader reader = new PdfReader(fileUrl);
				return reader.isEncrypted();
			} else {
				return false;
			}
		} catch(Exception e) {
			log.error("Errore nell'analisi del pdf. checkIfPdfProtetto restituisce false", e);
			return false;
		}
	}
	
	public static boolean checkProtectedFile(String pathFile) throws Exception{
		try {
			PdfReader reader = new PdfReader(pathFile);
			return reader.isEncrypted();
		} catch (Exception e) {
			throw new Exception("Errore durante la verifica dei permessi del file " + FilenameUtils.getName(pathFile)+ " - Errore: "+ e.getMessage(), e);
		}
	}
	
	public static String getCorrectFileName(String originalFileName, String newFileNameFromfileop, DocumentConfiguration lDocumentConfiguration) {
		if (StringUtils.isNotBlank(originalFileName) && lDocumentConfiguration.isIgnoreFONewNameWhenUploaded()) {
			// Controllo se in oldFileName ho una estensione valida, altrimenti restituisco il newFileNameFromfileop
			String tmp = originalFileName;
			while(tmp.toLowerCase().endsWith(".p7m")) {
				tmp = tmp .substring(0, tmp.length() - 4);
			}
			// Ho tolto tutti i p7m, controllo se ho una possibile estensione
			if (tmp.lastIndexOf(".") != -1) {
				String estensione = tmp.substring(tmp.lastIndexOf("."), tmp.length());
				if (estensione.length() > 4) {
					// Non ho una possibile estensione valida
					return newFileNameFromfileop;
				} else {
					// Ho una possibile estensione valida
					return originalFileName;
				}
			} else {
				// Non ho una estesione
				return newFileNameFromfileop;
			}
		} else {
			return newFileNameFromfileop;
		}
	}

}
