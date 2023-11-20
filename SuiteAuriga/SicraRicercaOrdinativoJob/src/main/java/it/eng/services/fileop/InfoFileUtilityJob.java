/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import org.apache.log4j.Logger;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.fileOperation.clientws.DnType;
import it.eng.fileOperation.clientws.FileOperationNoOutputWS;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.FileOperationWS;
import it.eng.fileOperation.clientws.InputDigestType;
import it.eng.fileOperation.clientws.InputFile;
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
import it.eng.fileOperation.clientws.ResponseSigVerify;
import it.eng.fileOperation.clientws.ResponseUnpackType;
import it.eng.fileOperation.clientws.SigVerifyResultType;
import it.eng.fileOperation.clientws.SigVerifyResultType.SigVerifyResult;
import it.eng.fileOperation.clientws.SignerInformationType;
import it.eng.fileOperation.clientws.SignerInformationType.Certificato;
import it.eng.fileOperation.clientws.SignerInformationType.Marca;
import it.eng.fileOperation.clientws.TimeStampInfotype;
import it.eng.fileOperation.clientws.VerificationStatusType;
import it.eng.job.AurigaSpringContext;
import it.eng.job.SpringHelper;
import it.eng.services.fileop.bean.DocumentConfiguration;
import it.eng.services.fileop.bean.Firmatari;
import it.eng.services.fileop.bean.InfoFileBean;
import it.eng.services.fileop.bean.ResponseSbustamentoBean;
import it.eng.services.fileop.bean.ResponseVerificaFirmaBean;
import it.eng.services.fileop.bean.RisultatoVerificaFirmaBean;
import it.eng.services.fileop.bean.RisultatoVerificaMarcaBean;
import it.eng.services.fileop.bean.SignatureVerifyOptionBean;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class InfoFileUtilityJob {

	private String fileSeparator = System.getProperty("file.separator");

	private static final Logger log = Logger.getLogger(InfoFileUtilityJob.class);

	private static final String FILEOP_NAMESPACE = "it.eng.fileoperation.ws";
	private static final String FILEOP_SERVICENAME = "FONoOutputImplService";
	private static final String FILEOP_SERVICENAME_OUTPUT = "FOImplService";
	private static final String SIGN_OP_FILENOTSIGNED = "SIGN_OP.FILENOTSIGNED";

	private int count;
	private List<String> openFolders;

	public InfoFileBean getInfoFromFile(File fileToCheck) throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringHelper.getMainApplicationContext().getBean("DocumentConfiguration");

		FileOperationResponse lFileOperationResponse = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug(String.format("fileToCheck: %s", fileToCheck.getAbsolutePath()));
				log.debug("fileToCheck URI: " + fileToCheck.toURI().toString());
				log.debug("fileOperationWS URL: " + lDocumentConfiguration.getOperationWsAddress());
			}

			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
			Service service = Service.create(url, qname);
			FileOperationNoOutputWS fileOperationWS = service.getPort(FileOperationNoOutputWS.class);
			// ((org.apache.axis.client.Stub)
			// fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
			// enable mtom on client
			((BindingProvider) fileOperationWS).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();
			Operations operations = new Operations();

			// DataHandler fileStream = new DataHandler(new
			// FileDataSource(fileToCheck));
			InputFile lInputFile = new InputFile();
			// lInputFile.setFileStream(fileStream);
			lInputFile.setFileUrl(fileToCheck.toURI().toString());
			InputFileOperationType lInputFileOperationType = new InputFileOperationType();
			lInputFileOperationType.setOriginalName(fileToCheck.getName());
			lInputFileOperationType.setInputType(lInputFile);
			lFileOperationRequest.setFileOperationInput(lInputFileOperationType);

			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lInputDigestType.setEncoding(lDocumentConfiguration.getEncoding());
			operations.getOperation().add(lInputDigestType);

			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			log.error(String.format("Errore nella ricerca info per il file [%s]: %s", fileToCheck, e.getMessage()), e);
			throw e;
		}

		InfoFileBean lInfoFileBean = new InfoFileBean();
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lInfoFileBean.setBytes(fileToCheck.length());
		} catch (Exception e) {
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				log.debug(String.format("Risposta operazione %s : %s", lAbstractResponseOperationType,
						lAbstractResponseOperationType.getVerificationStatus()));

				if (lAbstractResponseOperationType.getErrorsMessage() != null) {
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {
					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lInfoFileBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lInfoFileBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lInfoFileBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lInfoFileBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lInfoFileBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lInfoFileBean.setCorrectFileName(lResponseFormatRecognitionType.getNewFileName());
					}
				}
			}
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico: " + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lInfoFileBean;
	}

	public InfoFileBean getInfoFromFile(File fileToCheck, DocumentConfiguration lDocumentConfiguration)
			throws Exception {

		FileOperationResponse lFileOperationResponse = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug(String.format("fileToCheck: %s", fileToCheck.getAbsolutePath()));
				log.debug("fileToCheck URI: " + fileToCheck.toURI().toString());
				log.debug("fileOperationWS URL: " + lDocumentConfiguration.getOperationWsAddress());
			}

			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
			Service service = Service.create(url, qname);
			FileOperationNoOutputWS fileOperationWS = service.getPort(FileOperationNoOutputWS.class);
			// ((org.apache.axis.client.Stub)
			// fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
			// enable mtom on client
			((BindingProvider) fileOperationWS).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();
			Operations operations = new Operations();

			// DataHandler fileStream = new DataHandler(new
			// FileDataSource(fileToCheck));
			InputFile lInputFile = new InputFile();
			// lInputFile.setFileStream(fileStream);
			lInputFile.setFileUrl(fileToCheck.toURI().toString());
			InputFileOperationType lInputFileOperationType = new InputFileOperationType();
			lInputFileOperationType.setOriginalName(fileToCheck.getName());
			lInputFileOperationType.setInputType(lInputFile);
			lFileOperationRequest.setFileOperationInput(lInputFileOperationType);

			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lInputDigestType.setEncoding(lDocumentConfiguration.getEncoding());
			operations.getOperation().add(lInputDigestType);

			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			log.error(String.format("Errore nella ricerca info per il file [%s]: %s", fileToCheck, e.getMessage()), e);
			throw e;
		}

		InfoFileBean lInfoFileBean = new InfoFileBean();
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lInfoFileBean.setBytes(fileToCheck.length());
		} catch (Exception e) {
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				log.debug(String.format("Risposta operazione %s : %s", lAbstractResponseOperationType,
						lAbstractResponseOperationType.getVerificationStatus()));

				if (lAbstractResponseOperationType.getErrorsMessage() != null) {
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {
					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lInfoFileBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lInfoFileBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lInfoFileBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lInfoFileBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lInfoFileBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lInfoFileBean.setCorrectFileName(lResponseFormatRecognitionType.getNewFileName());
					}
				}
			}
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico: " + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lInfoFileBean;
	}

	public InfoFileBean getInfoFirmaFromFile(File fileToCheck, Date dataRiferimento) throws Exception {
		return getInfoFirmaFromFile(fileToCheck, true, dataRiferimento);
	}

	public MimeTypeFirmaBean getMimeTypeFirmaFromFile(File fileToCheck, boolean controllaFirma, Date dataRiferimento)
			throws Exception {

		MimeTypeFirmaBean retValue = new MimeTypeFirmaBean();

		InfoFileBean infoFile = getInfoFirmaFromFile(fileToCheck, controllaFirma, dataRiferimento);

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setCorrectFileName(infoFile.getCorrectFileName());
		lMimeTypeFirmaBean.setBytes(infoFile.getBytes());
		lMimeTypeFirmaBean.setConvertibile(infoFile.isConvertibile());
		lMimeTypeFirmaBean.setFirmatari(infoFile.getFirmatari());
		lMimeTypeFirmaBean.setFirmato(infoFile.isFirmato());
		lMimeTypeFirmaBean.setFirmaValida(infoFile.isFirmato());
		lMimeTypeFirmaBean.setImpronta(infoFile.getImpronta());
		lMimeTypeFirmaBean.setInfoFirma(infoFile.getInfoFirma());
		lMimeTypeFirmaBean.setMimetype(infoFile.getMimetype());
		lMimeTypeFirmaBean.setOpenFolders(infoFile.getOpenFolders());
		lMimeTypeFirmaBean.setTipoFirma(infoFile.getTipoFirma());

		return retValue;
	}

	public InfoFileBean getInfoFirmaFromFile(File fileToCheck, boolean controllaFirma, Date dataRiferimento)
			throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringHelper.getMainApplicationContext().getBean("DocumentConfiguration");

		FileOperationResponse lFileOperationResponse = null;

		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
			Service service = Service.create(url, qname);
			FileOperationNoOutputWS fileOperationWS = service.getPort(FileOperationNoOutputWS.class);
			// ((org.apache.axis.client.Stub)
			// fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
			// enable mtom on client
			((BindingProvider) fileOperationWS).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();
			Operations operations = new Operations();

			// DataHandler fileStream = new DataHandler(new
			// FileDataSource(fileToCheck));
			InputFile lInputFile = new InputFile();
			// lInputFile.setFileStream(fileStream);
			lInputFile.setFileUrl(fileToCheck.toURI().toString());
			InputFileOperationType lInputFileOperationType = new InputFileOperationType();
			lInputFileOperationType.setOriginalName(fileToCheck.getName());
			lInputFileOperationType.setInputType(lInputFile);
			lFileOperationRequest.setFileOperationInput(lInputFileOperationType);

			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lInputDigestType.setEncoding(lDocumentConfiguration.getEncoding());
			operations.getOperation().add(lInputDigestType);

			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			if (controllaFirma) {
				SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext
						.getContext().getBean("signatureVerifyOptionBean");
				InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
				SignatureVerify lSignatureVerify = new SignatureVerify();
				lSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
				lSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());
				lInputSigVerifyType.setSignatureVerify(lSignatureVerify);
				TimestampVerifiy lTimestampVerify = new TimestampVerifiy();
				lTimestampVerify.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
				lInputSigVerifyType.setTimestampVerifiy(lTimestampVerify);
				lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
				lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
				if (dataRiferimento != null) {
					GregorianCalendar gregorianCalendar = new GregorianCalendar();
					gregorianCalendar.setTime(dataRiferimento);
					lInputSigVerifyType
							.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
				}
				operations.getOperation().add(lInputSigVerifyType);
			}
			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			log.error("Errore " + e.getMessage(), e);
			throw e;
		}

		InfoFileBean lInfoFileBean = new InfoFileBean();
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lInfoFileBean.setBytes(fileToCheck.length());
		} catch (Exception e) {
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				log.debug(String.format("Risposta operazione %s : %s", lAbstractResponseOperationType,
						lAbstractResponseOperationType.getVerificationStatus()));

				if (lAbstractResponseOperationType.getErrorsMessage() != null) {
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {
					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lInfoFileBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lInfoFileBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lInfoFileBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lInfoFileBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lInfoFileBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lInfoFileBean.setCorrectFileName(lResponseFormatRecognitionType.getNewFileName());
					} else if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						// Verifica firma
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
								.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
							lInfoFileBean.setFirmato(false);
						} else if (lResponseSigVerify.getErrorsMessage() != null
								&& lResponseSigVerify.getErrorsMessage().getErrMessage().size() > 0) {
							lInfoFileBean.setFirmato(false);
						} else {
							lInfoFileBean.setFirmato(true);
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {
								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getSignerInformations().getSignerInformation();
								lInfoFileBean.setTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getFormatResult().getEnvelopeFormat());
								List<String> firmatari = new ArrayList<String>();
								openFolders = new ArrayList<String>();
								List<Firmatari> buste = new ArrayList<Firmatari>();
								count = 2;
								Firmatari lFirmatari = new Firmatari();
								lFirmatari.setFiglioDi("1");
								lFirmatari.setId(count + "");
								lFirmatari.setSubject("FIRMA");
								int livello = Integer.valueOf(lFirmatari.getId());
								openFolders.add(lFirmatari.getId());
								buste.add(lFirmatari);

								count++;
								for (SignerInformationType info : infos) {
									StringBuffer lStringBuffer = new StringBuffer();
									DnType subject = info.getCertificato().getSubject();
									DnType issuer = info.getCertificato().getSubject();
									lStringBuffer.append("Firmatario ").append("C=").append(subject.getC())
											.append("CN=").append(subject.getCn()).append("NAME=")
											.append(subject.getName()).append("O=").append(subject.getO()).append("OU=")
											.append(subject.getOu()).append("\n").append("ENTE ").append("C=")
											.append(issuer.getC()).append("CN=").append(issuer.getCn()).append("NAME=")
											.append(issuer.getName()).append("O=").append(issuer.getO()).append("OU=")
											.append(issuer.getOu());
									buste.addAll(creaFirmatari(subject, livello));
									firmatari.add(subject.getCn());
									lInfoFileBean.setInfoFirma(subject.getCn());
								}

								analizzaChild(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(),
										firmatari, buste, livello);
								lInfoFileBean.setFirmatari(firmatari.toArray(new String[] {}));
								lInfoFileBean.setBuste(buste.toArray(new Firmatari[] {}));
								lInfoFileBean.setOpenFolders(openFolders.toArray(new String[] {}));
								lInfoFileBean.setFirmaValida(lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getCertExpirationResult().getVerificationStatus()
										.equals(VerificationStatusType.OK));
							}
							if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
									.getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")) {
								lInfoFileBean.setFirmaValida(false);
							} else if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
									.getSignatureValResult().getVerificationStatus()
									.equals(VerificationStatusType.OK)) {
								lInfoFileBean.setFirmaValida(true);
							} else {
								lInfoFileBean.setFirmaValida(false);
								lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult()
										.getErrorsMessage();
								StringBuffer lStringBuffer = new StringBuffer();
								for (MessageType lObject : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getSignatureValResult().getErrorsMessage().getErrMessage()) {
									log.error("Errore firma " + lObject.getDescription());
									lStringBuffer.append(lObject.getDescription() + ";");
								}
								log.debug("Non valida per " + lStringBuffer.toString());
							}
						}
					}
				}
			}
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico" + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lInfoFileBean;
	}
	
	public InfoFileBean getInfoFirmaFromFile(File fileToCheck, boolean controllaFirma, Date dataRiferimento, boolean stream)
			throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringHelper.getMainApplicationContext().getBean("DocumentConfiguration");
		FileOperationResponse lFileOperationResponse = null;

		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
			Service service = Service.create(url, qname);
			FileOperationNoOutputWS fileOperationWS = service.getPort(FileOperationNoOutputWS.class);
			// ((org.apache.axis.client.Stub)
			// fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
			// enable mtom on client
			((BindingProvider) fileOperationWS).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();
			Operations operations = new Operations();

			InputFile lInputFile = new InputFile();
			InputFileOperationType lInputFileOperationType = new InputFileOperationType();
			if( stream ){
				DataHandler fileStream =  new DataHandler(new FileDataSource(fileToCheck));
				lInputFile.setFileStream( fileStream );
			} else {
				lInputFile.setFileUrl("file:"+fileToCheck.getAbsolutePath());
			}
			lInputFileOperationType.setOriginalName(fileToCheck.getName());
			lInputFileOperationType.setInputType(lInputFile);
			lFileOperationRequest.setFileOperationInput(lInputFileOperationType);

			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lInputDigestType.setEncoding(lDocumentConfiguration.getEncoding());
			operations.getOperation().add(lInputDigestType);

			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			if (controllaFirma) {
				SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext
						.getContext().getBean("signatureVerifyOptionBean");
				InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
				SignatureVerify lSignatureVerify = new SignatureVerify();
				lSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
				lSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());
				lInputSigVerifyType.setSignatureVerify(lSignatureVerify);
				TimestampVerifiy lTimestampVerify = new TimestampVerifiy();
				lTimestampVerify.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
				lInputSigVerifyType.setTimestampVerifiy(lTimestampVerify);
				lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
				lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
				if (dataRiferimento != null) {
					GregorianCalendar gregorianCalendar = new GregorianCalendar();
					gregorianCalendar.setTime(dataRiferimento);
					lInputSigVerifyType
							.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
				}
				operations.getOperation().add(lInputSigVerifyType);
			}
			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			log.error("Errore " + e.getMessage(), e);
			throw e;
		}

		InfoFileBean lInfoFileBean = new InfoFileBean();
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lInfoFileBean.setBytes(fileToCheck.length());
		} catch (Exception e) {
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				log.debug(String.format("Risposta operazione %s : %s", lAbstractResponseOperationType,
						lAbstractResponseOperationType.getVerificationStatus()));

				if (lAbstractResponseOperationType.getErrorsMessage() != null) {
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {
					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lInfoFileBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lInfoFileBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lInfoFileBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lInfoFileBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lInfoFileBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lInfoFileBean.setCorrectFileName(lResponseFormatRecognitionType.getNewFileName());
					} else if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						// Verifica firma
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
								.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
							lInfoFileBean.setFirmato(false);
						} else if (lResponseSigVerify.getErrorsMessage() != null
								&& lResponseSigVerify.getErrorsMessage().getErrMessage().size() > 0) {
							lInfoFileBean.setFirmato(false);
						} else {
							lInfoFileBean.setFirmato(true);
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {
								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getSignerInformations().getSignerInformation();
								lInfoFileBean.setTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getFormatResult().getEnvelopeFormat());
								List<String> firmatari = new ArrayList<String>();
								openFolders = new ArrayList<String>();
								List<Firmatari> buste = new ArrayList<Firmatari>();
								count = 2;
								Firmatari lFirmatari = new Firmatari();
								lFirmatari.setFiglioDi("1");
								lFirmatari.setId(count + "");
								lFirmatari.setSubject("FIRMA");
								int livello = Integer.valueOf(lFirmatari.getId());
								openFolders.add(lFirmatari.getId());
								buste.add(lFirmatari);

								count++;
								for (SignerInformationType info : infos) {
									StringBuffer lStringBuffer = new StringBuffer();
									DnType subject = info.getCertificato().getSubject();
									DnType issuer = info.getCertificato().getSubject();
									lStringBuffer.append("Firmatario ").append("C=").append(subject.getC())
											.append("CN=").append(subject.getCn()).append("NAME=")
											.append(subject.getName()).append("O=").append(subject.getO()).append("OU=")
											.append(subject.getOu()).append("\n").append("ENTE ").append("C=")
											.append(issuer.getC()).append("CN=").append(issuer.getCn()).append("NAME=")
											.append(issuer.getName()).append("O=").append(issuer.getO()).append("OU=")
											.append(issuer.getOu());
									buste.addAll(creaFirmatari(subject, livello));
									firmatari.add(subject.getCn());
									lInfoFileBean.setInfoFirma(subject.getCn());
								}

								analizzaChild(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(),
										firmatari, buste, livello);
								lInfoFileBean.setFirmatari(firmatari.toArray(new String[] {}));
								lInfoFileBean.setBuste(buste.toArray(new Firmatari[] {}));
								lInfoFileBean.setOpenFolders(openFolders.toArray(new String[] {}));
								lInfoFileBean.setFirmaValida(lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getCertExpirationResult().getVerificationStatus()
										.equals(VerificationStatusType.OK));
							}
							if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
									.getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")) {
								lInfoFileBean.setFirmaValida(false);
							} else if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
									.getSignatureValResult().getVerificationStatus()
									.equals(VerificationStatusType.OK)) {
								lInfoFileBean.setFirmaValida(true);
							} else {
								lInfoFileBean.setFirmaValida(false);
								lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult()
										.getErrorsMessage();
								StringBuffer lStringBuffer = new StringBuffer();
								for (MessageType lObject : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getSignatureValResult().getErrorsMessage().getErrMessage()) {
									log.error("Errore firma " + lObject.getDescription());
									lStringBuffer.append(lObject.getDescription() + ";");
								}
								log.debug("Non valida per " + lStringBuffer.toString());
							}
						}
					}
				}
			}
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico" + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lInfoFileBean;
	}
	
	public InfoFileBean getInfoFirmaFromFile(File fileToCheck, boolean controllaFirma, Date dataRiferimento, boolean stream, String algoritmo, String encoding)
			throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringHelper.getMainApplicationContext().getBean("DocumentConfiguration");

		FileOperationResponse lFileOperationResponse = null;

		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
			Service service = Service.create(url, qname);
			FileOperationNoOutputWS fileOperationWS = service.getPort(FileOperationNoOutputWS.class);
			// ((org.apache.axis.client.Stub)
			// fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
			// enable mtom on client
			((BindingProvider) fileOperationWS).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();
			Operations operations = new Operations();

			InputFile lInputFile = new InputFile();
			InputFileOperationType lInputFileOperationType = new InputFileOperationType();
			if( stream ){
				DataHandler fileStream =  new DataHandler(new FileDataSource(fileToCheck));
				lInputFile.setFileStream( fileStream );
			} else {
				lInputFile.setFileUrl("file:"+fileToCheck.getAbsolutePath());
			}
			lInputFileOperationType.setOriginalName(fileToCheck.getName());
			lInputFileOperationType.setInputType(lInputFile);
			lFileOperationRequest.setFileOperationInput(lInputFileOperationType);

			InputDigestType lInputDigestType = new InputDigestType();
			lInputDigestType.setDigestAlgId(DigestAlgID.fromValue(algoritmo.toLowerCase()));
			lInputDigestType.setEncoding(DigestEncID.fromValue(encoding.toLowerCase()));
			operations.getOperation().add(lInputDigestType);

			InputFormatRecognitionType lInputFormatRecognitionType = new InputFormatRecognitionType();
			operations.getOperation().add(lInputFormatRecognitionType);

			if (controllaFirma) {
				SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext
						.getContext().getBean("signatureVerifyOptionBean");
				InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
				SignatureVerify lSignatureVerify = new SignatureVerify();
				lSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
				lSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());
				lInputSigVerifyType.setSignatureVerify(lSignatureVerify);
				TimestampVerifiy lTimestampVerify = new TimestampVerifiy();
				lTimestampVerify.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
				lInputSigVerifyType.setTimestampVerifiy(lTimestampVerify);
				lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
				lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
				if (dataRiferimento != null) {
					GregorianCalendar gregorianCalendar = new GregorianCalendar();
					gregorianCalendar.setTime(dataRiferimento);
					lInputSigVerifyType
							.setDataRif(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
				}
				operations.getOperation().add(lInputSigVerifyType);
			}
			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			log.error("Errore " + e.getMessage(), e);
			throw e;
		}

		InfoFileBean lInfoFileBean = new InfoFileBean();
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}
		try {
			lInfoFileBean.setBytes(fileToCheck.length());
		} catch (Exception e) {
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");

			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();

			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {

				log.debug(String.format("Risposta operazione %s : %s", lAbstractResponseOperationType,
						lAbstractResponseOperationType.getVerificationStatus()));

				if (lAbstractResponseOperationType.getErrorsMessage() != null) {
					List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
					for (MessageType message : errors) {
						log.error("Errore " + message.getCode() + " - " + message.getDescription());
					}
				} else {
					if (lAbstractResponseOperationType instanceof ResponseFileDigestType) {
						// Impronta file
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) lAbstractResponseOperationType;
						if (lResponseFileDigestType.getDigestAlgId() != null)
							lInfoFileBean.setAlgoritmo(lResponseFileDigestType.getDigestAlgId().value());
						if (lResponseFileDigestType.getEncoding() != null)
							lInfoFileBean.setEncoding(lResponseFileDigestType.getEncoding().value());
						lInfoFileBean.setImpronta(lResponseFileDigestType.getResult());
					} else if (lAbstractResponseOperationType instanceof ResponseFormatRecognitionType) {
						// Riconoscimento formato
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) lAbstractResponseOperationType;
						if (lResponseFormatRecognitionType.getDatiFormato() != null)
							lInfoFileBean.setIdFormato(lResponseFormatRecognitionType.getDatiFormato().getIdFormato());
						lInfoFileBean.setMimetype(lResponseFormatRecognitionType.getMimeType());
						lInfoFileBean.setCorrectFileName(lResponseFormatRecognitionType.getNewFileName());
					} else if (lAbstractResponseOperationType instanceof ResponseSigVerify) {
						// Verifica firma
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) lAbstractResponseOperationType;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
								.getSigVerifyResult().getMessage().getCode().equals(SIGN_OP_FILENOTSIGNED)) {
							lInfoFileBean.setFirmato(false);
						} else if (lResponseSigVerify.getErrorsMessage() != null
								&& lResponseSigVerify.getErrorsMessage().getErrMessage().size() > 0) {
							lInfoFileBean.setFirmato(false);
						} else {
							lInfoFileBean.setFirmato(true);
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {
								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getSignerInformations().getSignerInformation();
								lInfoFileBean.setTipoFirma(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getFormatResult().getEnvelopeFormat());
								List<String> firmatari = new ArrayList<String>();
								openFolders = new ArrayList<String>();
								List<Firmatari> buste = new ArrayList<Firmatari>();
								count = 2;
								Firmatari lFirmatari = new Firmatari();
								lFirmatari.setFiglioDi("1");
								lFirmatari.setId(count + "");
								lFirmatari.setSubject("FIRMA");
								int livello = Integer.valueOf(lFirmatari.getId());
								openFolders.add(lFirmatari.getId());
								buste.add(lFirmatari);

								count++;
								for (SignerInformationType info : infos) {
									StringBuffer lStringBuffer = new StringBuffer();
									DnType subject = info.getCertificato().getSubject();
									DnType issuer = info.getCertificato().getSubject();
									lStringBuffer.append("Firmatario ").append("C=").append(subject.getC())
											.append("CN=").append(subject.getCn()).append("NAME=")
											.append(subject.getName()).append("O=").append(subject.getO()).append("OU=")
											.append(subject.getOu()).append("\n").append("ENTE ").append("C=")
											.append(issuer.getC()).append("CN=").append(issuer.getCn()).append("NAME=")
											.append(issuer.getName()).append("O=").append(issuer.getO()).append("OU=")
											.append(issuer.getOu());
									buste.addAll(creaFirmatari(subject, livello));
									firmatari.add(subject.getCn());
									lInfoFileBean.setInfoFirma(subject.getCn());
								}

								analizzaChild(lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(),
										firmatari, buste, livello);
								lInfoFileBean.setFirmatari(firmatari.toArray(new String[] {}));
								lInfoFileBean.setBuste(buste.toArray(new Firmatari[] {}));
								lInfoFileBean.setOpenFolders(openFolders.toArray(new String[] {}));
								lInfoFileBean.setFirmaValida(lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getCertExpirationResult().getVerificationStatus()
										.equals(VerificationStatusType.OK));
							}
							if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
									.getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")) {
								lInfoFileBean.setFirmaValida(false);
							} else if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
									.getSignatureValResult().getVerificationStatus()
									.equals(VerificationStatusType.OK)) {
								lInfoFileBean.setFirmaValida(true);
							} else {
								lInfoFileBean.setFirmaValida(false);
								lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult()
										.getErrorsMessage();
								StringBuffer lStringBuffer = new StringBuffer();
								for (MessageType lObject : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getSignatureValResult().getErrorsMessage().getErrMessage()) {
									log.error("Errore firma " + lObject.getDescription());
									lStringBuffer.append(lObject.getDescription() + ";");
								}
								log.debug("Non valida per " + lStringBuffer.toString());
							}
						}
					}
				}
			}
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage())
					log.error("Errore generico" + err);
			}
		} else {
			log.error("Errore generico: nessuna risposta da FileOperation");
		}

		return lInfoFileBean;
	}

	private Collection<? extends Firmatari> creaFirmatari(DnType subject, int livello) {
		int padre = count;
		List<Firmatari> lListReturn = new ArrayList<Firmatari>();

		lListReturn.add(creaFromAttributo(subject.getCn(), livello));
		lListReturn.add(creaFromAttributo(subject.getC(), "C", padre));
		lListReturn.add(creaFromAttributo(subject.getCn(), "CN", padre));
		lListReturn.add(creaFromAttributo(subject.getO(), "O", padre));
		lListReturn.add(creaFromAttributo(subject.getOu(), "OU", padre));

		return lListReturn;
	}

	private Firmatari creaFromAttributo(String valore, String param, int livello) {
		Firmatari busta = new Firmatari();
		busta.setFiglioDi(livello + "");
		busta.setId(count + "");
		busta.setSubject(param + "=" + valore);
		count++;

		return busta;
	}

	private Firmatari creaFromAttributo(String valore, int livello) {
		Firmatari busta = new Firmatari();
		busta.setFiglioDi(livello + "");
		busta.setId(count + "");
		busta.setSubject(valore);
		count++;

		return busta;
	}

	private void analizzaChild(SigVerifyResultType child, List<String> firmatari, List<Firmatari> buste, int livello) {
		if (child == null)
			return;

		Firmatari lFirmatari = new Firmatari();
		lFirmatari.setFiglioDi(livello + "");
		lFirmatari.setId(count + "");
		lFirmatari.setSubject("FIRMA");
		buste.add(lFirmatari);
		openFolders.add(count + "");
		count++;

		livello = Integer.valueOf(lFirmatari.getId());
		SigVerifyResult lSigVerifyResult = child.getSigVerifyResult();
		lSigVerifyResult.getFormatResult().getEnvelopeFormat();
		List<SignerInformationType> infosChild = lSigVerifyResult.getSignerInformations().getSignerInformation();

		for (SignerInformationType info : infosChild) {
			StringBuffer lStringBuffer = new StringBuffer();
			DnType subject = info.getCertificato().getSubject();
			DnType issuer = info.getCertificato().getSubject();
			lStringBuffer.append("Firmatario ").append("C=").append(subject.getC()).append("CN=")
					.append(subject.getCn()).append("NAME=").append(subject.getName()).append("O=")
					.append(subject.getO()).append("OU=").append(subject.getOu()).append("\n").append("ENTE ")
					.append("C=").append(issuer.getC()).append("CN=").append(issuer.getCn()).append("NAME=")
					.append(issuer.getName()).append("O=").append(issuer.getO()).append("OU=").append(issuer.getOu());
			firmatari.add(subject.getCn());
			buste.addAll(creaFirmatari(subject, livello));
		}

		if (child.getSigVerifyResult() != null)
			analizzaChild(child.getSigVerifyResult().getChild(), firmatari, buste, livello);
	}

	/**
	 * 
	 * Effettua lo sbustamento del file specificato
	 * 
	 * @param filePath
	 * @param originalName
	 * @param abilitaNomeFileSbustato
	 * @param stream
	 * @param documentConfiguration
	 * @return
	 * @throws Exception
	 */
	public ResponseSbustamentoBean sbustaFile(String filePath, String tempDir, boolean originalName, boolean abilitaNomeFileSbustato,
			boolean stream, DocumentConfiguration documentConfiguration) throws Exception {

		return sbustaFile(filePath, tempDir, originalName, abilitaNomeFileSbustato, stream,
				documentConfiguration.getOperationWsAddress(), FILEOP_NAMESPACE, FILEOP_SERVICENAME_OUTPUT);

	}

	/**
	 * Effettua lo sbustamento del file specificato
	 * 
	 * @param filePath
	 * @param tempDir
	 * @param originalName
	 * @param abilitaNomeFileSbustato
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public ResponseSbustamentoBean sbustaFile(String filePath, String tempDir, boolean originalName, boolean abilitaNomeFileSbustato,
			boolean stream, String endPoint, String namespace, String serviceName) throws Exception {

		ResponseSbustamentoBean bean = new ResponseSbustamentoBean();

		// se esistono le configurazioni effettuo l'invocazione del servizio
		if (endPoint != null && namespace != null && serviceName != null) {

			File fileInput = new File(filePath);

			FileOperationResponse result = null;

			log.info("File di cui si richiede lo sbustamento " + fileInput.getName());

			URL url = new URL(endPoint);
			QName qname = new QName(namespace, serviceName);
			Service service = Service.create(url, qname);
			FileOperationWS fileOperationClient = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fileOperationClient).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationClient).getBinding();
			binding.setMTOMEnabled(true);
			FileOperationRequest request = new FileOperationRequest();

			InputUnpackType input = new InputUnpackType();

			input.setAbilitaNomeSbustato(abilitaNomeFileSbustato);

			Operations operations = new Operations();
			operations.getOperation().add(input);
			request.setOperations(operations);

			InputFileOperationType file = new InputFileOperationType();
			InputFile inputFile = new InputFile();
			if (stream) {
				DataHandler fileStream = new DataHandler(new FileDataSource(fileInput));
				inputFile.setFileStream(fileStream);
			} else {
				inputFile.setFileUrl("file:" + fileInput.getAbsolutePath());
			}
			file.setInputType(inputFile);

			if (originalName) {
				file.setOriginalName(fileInput.getName());
			}

			request.setFileOperationInput(file);

			result = fileOperationClient.execute(request);

			// se ho ottenuto la response provo a generare il file

			if (result.getGenericError() != null) {

				StringBuilder messageBuilder = new StringBuilder();

				for (int i = 0; i < result.getGenericError().getErrorMessage().size() - 1; i++) {

					String message = result.getGenericError().getErrorMessage().get(i);

					messageBuilder.append(message).append(", ");
				}

				messageBuilder.append(result.getGenericError().getErrorMessage()
						.get(result.getGenericError().getErrorMessage().size() - 1));

				bean.setErrorMessage(messageBuilder.toString());

			} else {

				FileOperationResponse.FileoperationResponse response = result.getFileoperationResponse();

				List<AbstractResponseOperationType> opResults = response.getFileOperationResults()
						.getFileOperationResult();
				for (AbstractResponseOperationType opResult : opResults) {
					if (opResult instanceof ResponseUnpackType) {
						ResponseUnpackType responseUnpack = (ResponseUnpackType) opResult;
						bean.setNomeFileSbustato(responseUnpack.getNomeFileSbustato());

					}
				}

				DataHandler fileResult = response.getFileResult();

				if (fileResult != null) {

					String outputFileName = bean.getNomeFileSbustato();

					if (outputFileName == null) {
						outputFileName = "fileSbustato";
					}

					String outputFilePath = "";
					if (tempDir != null ||
							!tempDir.equalsIgnoreCase("")) {
						outputFilePath = tempDir + fileSeparator;
					} else
						outputFilePath = fileInput.getParentFile() + fileSeparator;

				
					File directory = new File(outputFilePath);

					if (!directory.exists()) { 
						directory.mkdir();
					}

					File fileResponse = new File(outputFilePath + outputFileName);

					if (!fileResponse.exists()) {
						fileResponse.createNewFile();
					}

					FileOutputStream fos = new FileOutputStream(fileResponse);
					fileResult.writeTo(fos);

					fos.flush();

					fos.close();

					bean.setFileSbustato(fileResponse);

				}
			}
		}

		return bean;
	}

	/**
	 * Verifica la firma del file specificato
	 * 
	 * @param filePath
	 * @param originalName
	 * @param stream
	 * @param docConf
	 * @return
	 * @throws Exception
	 */
	public ResponseVerificaFirmaBean verificaFirme(String filePath, boolean originalName, boolean stream,
			DocumentConfiguration docConf) throws Exception {

		return verificaFirme(filePath, originalName, stream, docConf.getOperationWsAddress(), FILEOP_NAMESPACE,
				FILEOP_SERVICENAME_OUTPUT);

	}

	/**
	 * Verifica la firma del file specificato
	 * 
	 * @param filePath
	 * @param originalName
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public ResponseVerificaFirmaBean verificaFirme(String filePath, boolean originalName, boolean stream,
			String endpoint, String namespace, String serviceName) throws Exception {

		log.info("Verifica della firma del file " + filePath);

		ResponseVerificaFirmaBean responseVerificaFirmaBean = new ResponseVerificaFirmaBean();

		if (endpoint != null && namespace != null && serviceName != null) {

			File fileInput = new File(filePath);
			FileOperationResponse result = null;

			URL url = new URL(endpoint);
			QName qname = new QName(namespace, serviceName);
			Service service = Service.create(url, qname);
			FileOperationWS fileOpService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fileOpService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOpService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			InputSigVerifyType input = new InputSigVerifyType();
			input.setChildValidation(true);
			input.setRecursive(true);
			SignatureVerify signatureVerify = new SignatureVerify();
			signatureVerify.setCAReliability(true);
			signatureVerify.setCRLCheck(true);
			signatureVerify.setDetectCode(false);
			input.setSignatureVerify(signatureVerify);

			TimestampVerifiy timeStampVerify = new TimestampVerifiy();
			timeStampVerify.setTSAReliability(true);
			input.setTimestampVerifiy(timeStampVerify);

			Operations operations = new Operations();
			operations.getOperation().add(input);
			request.setOperations(operations);

			InputFileOperationType file = new InputFileOperationType();
			InputFile inputFile = new InputFile();

			if (stream) {
				DataHandler fileStream = new DataHandler(new FileDataSource(fileInput));
				inputFile.setFileStream(fileStream);
			} else {
				inputFile.setFileUrl("file:" + fileInput.getAbsolutePath());
			}

			file.setInputType(inputFile);
			if (originalName) {
				file.setOriginalName(fileInput.getName());
			}

			request.setFileOperationInput(file);

			result = fileOpService.execute(request);

			if (result.getGenericError() != null) {

				StringBuilder errorBuilder = new StringBuilder();

				List<String> errorMessage = result.getGenericError().getErrorMessage();
				for (int i = 0; i < errorMessage.size() - 1; i++) {
					errorBuilder.append(errorMessage.get(i)).append(", ");
				}

				errorBuilder.append(errorMessage.get(errorMessage.size() - 1));

				responseVerificaFirmaBean.setErrorMessage(errorBuilder.toString());

			} else {

				FileOperationResponse.FileoperationResponse response = result.getFileoperationResponse();

				List<AbstractResponseOperationType> opResults = response.getFileOperationResults()
						.getFileOperationResult();

				for (AbstractResponseOperationType opResult : opResults) {

					if (opResult instanceof ResponseSigVerify) {

						ResponseSigVerify responseSigOp = (ResponseSigVerify) opResult;

						SigVerifyResultType sigVerResult = responseSigOp.getSigVerifyResult();
						responseVerificaFirmaBean = valorizzaDatiFirma(sigVerResult);

						if (responseSigOp.getVerificationStatus() != null) {
							RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
							verifica.setEsitoVerifica(responseSigOp.getVerificationStatus());
							if (responseSigOp.getErrorsMessage() != null
									&& responseSigOp.getErrorsMessage().getErrMessage() != null
									&& responseSigOp.getErrorsMessage().getErrMessage().size() > 0)
								verifica.setDescrizioneErrore(
										responseSigOp.getErrorsMessage().getErrMessage().get(0).getDescription());
							responseVerificaFirmaBean.setVerificaGlobale(verifica);
						}
					}
				}
			}
		}

		return responseVerificaFirmaBean;
	}

	private ResponseVerificaFirmaBean valorizzaDatiFirma(SigVerifyResultType sigVerResultType) {

		ResponseVerificaFirmaBean responseVerificaFirmaBean = new ResponseVerificaFirmaBean();

		MessageType message = sigVerResultType.getMessage();
		if (message != null) {
			RisultatoVerificaFirmaBean verificaFirmato = new RisultatoVerificaFirmaBean();
			verificaFirmato.setDescrizioneErrore(message.getDescription());
			responseVerificaFirmaBean.setVerificaFirmato(verificaFirmato);
		}

		if (sigVerResultType.getVerificationStatus() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerResultType.getVerificationStatus());
			if (sigVerResultType.getErrorsMessage() != null
					&& sigVerResultType.getErrorsMessage().getErrMessage() != null
					&& sigVerResultType.getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerResultType.getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setVerificaGlobaleSingolaFirma(verifica);
		}

		SigVerifyResult sigVerRes = sigVerResultType.getSigVerifyResult();
		if (sigVerRes != null && sigVerRes.getCertExpirationResult() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerRes.getCertExpirationResult().getVerificationStatus());
			if (sigVerRes.getCertExpirationResult().getErrorsMessage() != null
					&& sigVerRes.getCertExpirationResult().getErrorsMessage() != null
					&& sigVerRes.getCertExpirationResult().getErrorsMessage().getErrMessage() != null
					&& sigVerRes.getCertExpirationResult().getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerRes.getCertExpirationResult().getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setVerificaCertificato(verifica);
		}
		if (sigVerRes != null && sigVerRes.getCAReliabilityResult() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerRes.getCAReliabilityResult().getVerificationStatus());
			if (sigVerRes.getCAReliabilityResult().getErrorsMessage() != null
					&& sigVerRes.getCAReliabilityResult().getErrorsMessage() != null
					&& sigVerRes.getCAReliabilityResult().getErrorsMessage().getErrMessage() != null
					&& sigVerRes.getCAReliabilityResult().getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerRes.getCAReliabilityResult().getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setVerificaCa(verifica);
		}
		if (sigVerRes != null && sigVerRes.getCRLResult() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerRes.getCRLResult().getVerificationStatus());
			if (sigVerRes.getCRLResult().getErrorsMessage() != null
					&& sigVerRes.getCRLResult().getErrorsMessage() != null
					&& sigVerRes.getCRLResult().getErrorsMessage().getErrMessage() != null
					&& sigVerRes.getCRLResult().getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerRes.getCRLResult().getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setVerificaCrl(verifica);
		}
		if (sigVerRes != null && sigVerRes.getFormatResult() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerRes.getFormatResult().getVerificationStatus());
			responseVerificaFirmaBean.setVerificaFormato(verifica);
			if (sigVerRes.getFormatResult().getErrorsMessage() != null
					&& sigVerRes.getFormatResult().getErrorsMessage() != null
					&& sigVerRes.getFormatResult().getErrorsMessage().getErrMessage() != null
					&& sigVerRes.getFormatResult().getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerRes.getFormatResult().getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setFormatoBusta(sigVerRes.getFormatResult().getEnvelopeFormat());
		}
		if (sigVerRes != null && sigVerRes.getDetectionCodeResult() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerRes.getDetectionCodeResult().getVerificationStatus());
			if (sigVerRes.getDetectionCodeResult().getErrorsMessage() != null
					&& sigVerRes.getDetectionCodeResult().getErrorsMessage() != null
					&& sigVerRes.getDetectionCodeResult().getErrorsMessage().getErrMessage() != null
					&& sigVerRes.getDetectionCodeResult().getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerRes.getDetectionCodeResult().getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setVerificaCodiceEseguibile(verifica);
		}
		if (sigVerRes != null && sigVerRes.getSignatureValResult() != null) {
			RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
			verifica.setEsitoVerifica(sigVerRes.getSignatureValResult().getVerificationStatus());
			if (sigVerRes.getSignatureValResult().getErrorsMessage() != null
					&& sigVerRes.getSignatureValResult().getErrorsMessage() != null
					&& sigVerRes.getSignatureValResult().getErrorsMessage().getErrMessage() != null
					&& sigVerRes.getSignatureValResult().getErrorsMessage().getErrMessage().size() > 0)
				verifica.setDescrizioneErrore(
						sigVerRes.getSignatureValResult().getErrorsMessage().getErrMessage().get(0).getDescription());
			responseVerificaFirmaBean.setVerificaFirma(verifica);
		}

		if (sigVerRes != null && sigVerRes.getTimestampVerificationResult() != null) {
			if (sigVerRes.getTimestampVerificationResult().getTimeStampInfo() != null
					&& sigVerRes.getTimestampVerificationResult().getTimeStampInfo().size() > 0) {
				for (TimeStampInfotype timeStampInfo : sigVerRes.getTimestampVerificationResult().getTimeStampInfo()) {
					RisultatoVerificaMarcaBean verifica = new RisultatoVerificaMarcaBean();
					verifica.setEsitoVerifica(timeStampInfo.getVerificationStatus());

					if (timeStampInfo.getErrorsMessage() != null
							&& timeStampInfo.getErrorsMessage().getErrMessage() != null
							&& timeStampInfo.getErrorsMessage().getErrMessage().size() > 0)
						verifica.setMessaggioErrore(
								timeStampInfo.getErrorsMessage().getErrMessage().get(0).getDescription());

					if (timeStampInfo.getDate() != null) {
						Calendar calendar = timeStampInfo.getDate().toGregorianCalendar();
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						formatter.setTimeZone(calendar.getTimeZone());
						String d = formatter.format(calendar.getTime());
						verifica.setDataMarca(d);
					}
					verifica.setFormatoMarca(timeStampInfo.getFormat().name());
					if (timeStampInfo.getTsaInfo() != null)
						verifica.setTsaName(timeStampInfo.getTsaInfo().getTsaName());

					responseVerificaFirmaBean.getVerificaMarcheList().add(verifica);
				}
			}

		}

		if (sigVerRes != null && sigVerRes.getSignerInformations() != null
				&& sigVerRes.getSignerInformations().getSignerInformation() != null) {
			List<SignerInformationType> infos = sigVerRes.getSignerInformations().getSignerInformation();
			for (SignerInformationType info : infos) {
				ResponseVerificaFirmaBean bean = new ResponseVerificaFirmaBean();

				if (info != null) {
					if (info.getSigningTime() != null) {
						Calendar calendar = info.getSigningTime().toGregorianCalendar();
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						formatter.setTimeZone(calendar.getTimeZone());
						String d = formatter.format(calendar.getTime());
						bean.setDataFirma(d);
					}

					Certificato certificato = info.getCertificato();
					if (certificato != null) {
						if (certificato.getDataDecorrenza() != null) {
							Calendar calendar = certificato.getDataDecorrenza().toGregorianCalendar();
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							formatter.setTimeZone(calendar.getTimeZone());
							String d = formatter.format(calendar.getTime());
							bean.setDataDecorrenzaCertificato(d);
						}
						if (certificato.getDataScadenza() != null) {
							Calendar calendar = certificato.getDataScadenza().toGregorianCalendar();
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							formatter.setTimeZone(calendar.getTimeZone());
							String d = formatter.format(calendar.getTime());
							bean.setDataScadenzaCertificato(d);
						}
						if (certificato.getSubject() != null) {
							String firmatario = certificato.getSubject().getCn();
							bean.setFirmatario(firmatario);
						}
					}

					Marca marca = info.getMarca();
					if (marca != null) {
						if (marca.getTsaName() != null)
							bean.setTsaName(marca.getTsaName());
						if (marca.getDate() != null) {
							Calendar calendar = marca.getDate().toGregorianCalendar();
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							formatter.setTimeZone(calendar.getTimeZone());
							String d = formatter.format(calendar.getTime());
							bean.setDataMarca(d);
						}
						if (marca.getPolicy() != null)
							bean.setTsPolicy(marca.getPolicy());

					}

				}
				if (info.getSigVerifyResult() != null && info.getSigVerifyResult().getCertExpirationResult() != null) {
					RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
					verifica.setEsitoVerifica(
							info.getSigVerifyResult().getCertExpirationResult().getVerificationStatus());
					if (info.getSigVerifyResult().getCertExpirationResult() != null
							&& info.getSigVerifyResult().getCertExpirationResult().getErrorsMessage() != null
							&& info.getSigVerifyResult().getCertExpirationResult().getErrorsMessage()
									.getErrMessage() != null
							&& info.getSigVerifyResult().getCertExpirationResult().getErrorsMessage().getErrMessage()
									.size() > 0)
						verifica.setDescrizioneErrore(info.getSigVerifyResult().getCertExpirationResult()
								.getErrorsMessage().getErrMessage().get(0).getDescription());
					bean.setVerificaCertificato(verifica);
				}
				if (info.getSigVerifyResult() != null && info.getSigVerifyResult().getCAReliabilityResult() != null) {
					RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
					verifica.setEsitoVerifica(
							info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus());
					if (info.getSigVerifyResult().getCAReliabilityResult() != null
							&& info.getSigVerifyResult().getCAReliabilityResult().getErrorsMessage() != null
							&& info.getSigVerifyResult().getCAReliabilityResult().getErrorsMessage()
									.getErrMessage() != null
							&& info.getSigVerifyResult().getCAReliabilityResult().getErrorsMessage().getErrMessage()
									.size() > 0)
						verifica.setDescrizioneErrore(info.getSigVerifyResult().getCAReliabilityResult()
								.getErrorsMessage().getErrMessage().get(0).getDescription());
					bean.setVerificaCa(verifica);
				}
				if (info.getSigVerifyResult() != null && info.getSigVerifyResult().getCRLResult() != null) {
					RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
					verifica.setEsitoVerifica(info.getSigVerifyResult().getCRLResult().getVerificationStatus());
					if (info.getSigVerifyResult().getCRLResult() != null
							&& info.getSigVerifyResult().getCRLResult().getErrorsMessage() != null
							&& info.getSigVerifyResult().getCRLResult().getErrorsMessage().getErrMessage() != null
							&& info.getSigVerifyResult().getCRLResult().getErrorsMessage().getErrMessage().size() > 0)
						verifica.setDescrizioneErrore(info.getSigVerifyResult().getCRLResult().getErrorsMessage()
								.getErrMessage().get(0).getDescription());
					bean.setVerificaCrl(verifica);
				}
				if (info.getSigVerifyResult() != null && info.getSigVerifyResult().getFormatResult() != null) {
					RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
					verifica.setEsitoVerifica(info.getSigVerifyResult().getFormatResult().getVerificationStatus());
					if (info.getSigVerifyResult().getFormatResult() != null
							&& info.getSigVerifyResult().getFormatResult().getErrorsMessage() != null
							&& info.getSigVerifyResult().getFormatResult().getErrorsMessage().getErrMessage() != null
							&& info.getSigVerifyResult().getFormatResult().getErrorsMessage().getErrMessage()
									.size() > 0)
						verifica.setDescrizioneErrore(info.getSigVerifyResult().getFormatResult().getErrorsMessage()
								.getErrMessage().get(0).getDescription());
					bean.setVerificaFormato(verifica);
					bean.setFormatoBusta(info.getSigVerifyResult().getFormatResult().getEnvelopeFormat());
				}
				if (info.getSigVerifyResult() != null && info.getSigVerifyResult().getDetectionCodeResult() != null) {
					RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
					verifica.setEsitoVerifica(
							info.getSigVerifyResult().getDetectionCodeResult().getVerificationStatus());
					if (info.getSigVerifyResult().getDetectionCodeResult() != null
							&& info.getSigVerifyResult().getDetectionCodeResult().getErrorsMessage() != null
							&& info.getSigVerifyResult().getDetectionCodeResult().getErrorsMessage()
									.getErrMessage() != null
							&& info.getSigVerifyResult().getDetectionCodeResult().getErrorsMessage().getErrMessage()
									.size() > 0)
						verifica.setDescrizioneErrore(info.getSigVerifyResult().getDetectionCodeResult()
								.getErrorsMessage().getErrMessage().get(0).getDescription());
					bean.setVerificaCodiceEseguibile(verifica);
				}
				if (info.getSigVerifyResult() != null && info.getSigVerifyResult().getSignatureValResult() != null) {
					RisultatoVerificaFirmaBean verifica = new RisultatoVerificaFirmaBean();
					verifica.setEsitoVerifica(
							info.getSigVerifyResult().getSignatureValResult().getVerificationStatus());
					if (info.getSigVerifyResult().getSignatureValResult() != null
							&& info.getSigVerifyResult().getSignatureValResult().getErrorsMessage() != null
							&& info.getSigVerifyResult().getSignatureValResult().getErrorsMessage()
									.getErrMessage() != null
							&& info.getSigVerifyResult().getSignatureValResult().getErrorsMessage().getErrMessage()
									.size() > 0)
						verifica.setDescrizioneErrore(info.getSigVerifyResult().getSignatureValResult()
								.getErrorsMessage().getErrMessage().get(0).getDescription());
					bean.setVerificaFirma(verifica);
				}

				responseVerificaFirmaBean.getVerificaFirmeList().add(bean);
			}
		}

		if (sigVerRes != null && sigVerRes.getChild() != null) {
			SigVerifyResultType child = sigVerRes.getChild();
			ResponseVerificaFirmaBean firmaInterna = valorizzaDatiFirma(child);
			responseVerificaFirmaBean.setFirmaInterna(firmaInterna);
		}

		return responseVerificaFirmaBean;
	}

}
