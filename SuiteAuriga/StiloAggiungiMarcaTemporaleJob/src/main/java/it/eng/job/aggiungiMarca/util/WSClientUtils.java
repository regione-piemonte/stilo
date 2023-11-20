/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import it.eng.cryptoutil.verify.util.ResponseUtils;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
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
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponseFileDigestType;
import it.eng.fileOperation.clientws.ResponseFormatRecognitionType;
import it.eng.fileOperation.clientws.ResponseSigVerify;
import it.eng.fileOperation.clientws.SigVerifyResultType;
import it.eng.fileOperation.clientws.SigVerifyResultType.SigVerifyResult;
import it.eng.fileOperation.clientws.SignerInformationType;
import it.eng.fileOperation.clientws.TimeStampInfotype;
import it.eng.fileOperation.clientws.TimeStampInfotype.TsaInfo;
import it.eng.fileOperation.clientws.VerificationStatusType;
import it.eng.job.SpringHelper;
import it.eng.job.aggiungiMarca.bean.DocumentConfiguration;
import it.eng.job.aggiungiMarca.bean.FileMarcatoBean;
import it.eng.job.aggiungiMarca.bean.MarcaGenericFile;
import it.eng.job.aggiungiMarca.bean.SignatureVerifyOptionBean;


public class WSClientUtils {

	private Logger logger = Logger.getLogger(WSClientUtils.class);

	private static final String FILEOP_NAMESPACE = "it.eng.fileoperation.ws";
	private static final String FILEOP_SERVICENAME = "FOImplService";

	public FileMarcatoBean verificaFileMarcato(File pFile) throws Exception {
		
		DocumentConfiguration lDocumentConfiguration = SpringHelper.getMainApplicationContext()
				.getBean(DocumentConfiguration.class);

		SignatureVerifyOptionBean lSignatureVerifyOptionBean = SpringHelper.getMainApplicationContext()
				.getBean(SignatureVerifyOptionBean.class);

		String wsEndpoint = lDocumentConfiguration.getOperationWsAddress();

		logger.debug("endpoint get formato:" + wsEndpoint);

		URL url = new URL(wsEndpoint);
		QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
		Service service = Service.create(url, qname);
		logger.info("---Service " + service);
		FileOperationWS fooService = service.getPort(FileOperationWS.class);

		// enable mtom on client
		((BindingProvider) fooService).getRequestContext();
		SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
		binding.setMTOMEnabled(true);

		FileOperationRequest request = createFileOperationRequestMarca(pFile, lDocumentConfiguration,
				lSignatureVerifyOptionBean);

		FileOperationResponse risposta = fooService.execute(request);
		
		return createResponseMarca(risposta);
	}
	
	public MarcaGenericFile getInfo(File pFile) throws Exception {

		DocumentConfiguration lDocumentConfiguration = SpringHelper.getMainApplicationContext()
				.getBean(DocumentConfiguration.class);

		SignatureVerifyOptionBean lSignatureVerifyOptionBean = SpringHelper.getMainApplicationContext()
				.getBean(SignatureVerifyOptionBean.class);

		String wsEndpoint = lDocumentConfiguration.getOperationWsAddress();

		logger.debug("endpoint get formato:" + wsEndpoint);

		URL url = new URL(wsEndpoint);
		QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
		Service service = Service.create(url, qname);
		FileOperationWS fooService = service.getPort(FileOperationWS.class);

		// enable mtom on client
		((BindingProvider) fooService).getRequestContext();
		SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
		binding.setMTOMEnabled(true);

		FileOperationRequest request = createFileOperationRequest(pFile, lDocumentConfiguration,
				lSignatureVerifyOptionBean);

		FileOperationResponse risposta = fooService.execute(request);

		return createGenericFile(risposta);
	}

	
	/**
	 * @param risposta
	 * @return
	 */
	private MarcaGenericFile createGenericFile(FileOperationResponse risposta) {
		if (risposta != null && risposta.getFileoperationResponse() != null) {
			if (risposta.getFileoperationResponse().getFileOperationResults() != null
					&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {

				MarcaGenericFile lGenericFile = new MarcaGenericFile();

				for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse()
						.getFileOperationResults().getFileOperationResult()) {
					if (opResponse instanceof ResponseFileDigestType) {
						ResponseFileDigestType lResponseFileDigestType = (ResponseFileDigestType) opResponse;
						if (lResponseFileDigestType.getErrorsMessage() != null
								&& lResponseFileDigestType.getErrorsMessage().getErrMessage().size() > 0) {
						} else {
							lGenericFile.setImpronta(lResponseFileDigestType.getResult());
							lGenericFile.setAlgoritmo( lResponseFileDigestType.getDigestAlgId().name() );
							lGenericFile.setEncoding( lResponseFileDigestType.getEncoding().value() );
						}

					} else if (opResponse instanceof ResponseFormatRecognitionType) {
						ResponseFormatRecognitionType lResponseFormatRecognitionType = (ResponseFormatRecognitionType) opResponse;
						if (lResponseFormatRecognitionType.getErrorsMessage() != null
								&& lResponseFormatRecognitionType.getErrorsMessage().getErrMessage().size() > 0) {
						} else {
							lGenericFile.setMimetype(lResponseFormatRecognitionType.getMimeType());
						}

					} else if (opResponse instanceof ResponseSigVerify) {
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) opResponse;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
								.getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED"))
							lGenericFile.setFirmato(Flag.NOT_SETTED);
						else if (lResponseSigVerify.getErrorsMessage() != null
								&& lResponseSigVerify.getErrorsMessage().getErrMessage().size() > 0) {
							lGenericFile.setFirmato(Flag.NOT_SETTED);
						} else {
							lGenericFile.setFirmato(Flag.SETTED);
							
							if( lResponseSigVerify!=null ){
								SigVerifyResultType sigVerResult = lResponseSigVerify.getSigVerifyResult();
								if( sigVerResult!=null ){
									SigVerifyResult sigVerRes = sigVerResult.getSigVerifyResult();
									
									if( sigVerRes!=null && 
											sigVerRes.getTimestampVerificationResult()!=null ){
										List<TimeStampInfotype> tsInfos = sigVerRes.getTimestampVerificationResult().getTimeStampInfo();
										if( tsInfos!=null ){
											for(TimeStampInfotype tsInfo : tsInfos){
												if(tsInfo!=null && tsInfo.getTsaInfo()!=null ) {
													TsaInfo tsaInfo = tsInfo.getTsaInfo();
													String tsaName = tsaInfo.getTsaName();
													logger.error("tsaName  " + tsaName);
													lGenericFile.setTsa(tsaName);
												}
											}
										}
									}
								}
							}
							
							if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null) {
								List<SignerInformationType> infos = lResponseSigVerify.getSigVerifyResult()
										.getSigVerifyResult().getSignerInformations().getSignerInformation();
								
								List<Firmatario> firmatari = new ArrayList<Firmatario>();
								for (SignerInformationType info : infos) {
									StringBuffer lStringBuffer = new StringBuffer();
									Firmatario lFirmatario = new Firmatario();
									if( info!=null && info.getCertificato()!=null ){
										DnType subject = info.getCertificato().getSubject();
										String firmatario = "C=" + subject.getC() + "CN=" + subject.getCn() + "NAME="
												+ subject.getName() + "O=" + subject.getO() + "OU=" + subject.getOu();
										lStringBuffer.append("Firmatario " + firmatario + "\n");
										DnType issuer = info.getCertificato().getSubject();
										String enteCertificatore = "C=" + issuer.getC() + "CN=" + issuer.getCn() + "NAME="
												+ issuer.getName() + "O=" + issuer.getO() + "OU=" + issuer.getOu();
										lStringBuffer.append("ENTE " + enteCertificatore);
										lFirmatario.setCommonName(subject.getCn());
									}
									firmatari.add(lFirmatario);
								}
								/*if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild() != null) {
									analizzaChild(
											lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild(),
											firmatari);
								}*/
								lGenericFile.setFirmatari(firmatari);
							}
							if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
									.getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")) {
							} else if (lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
									.getSignatureValResult().getVerificationStatus()
									.compareTo(VerificationStatusType.OK) == 0) {
							} else {
								lResponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult()
										.getErrorsMessage();
								StringBuffer lStringBuffer = new StringBuffer();
								for (MessageType lObject : lResponseSigVerify.getSigVerifyResult().getSigVerifyResult()
										.getSignatureValResult().getErrorsMessage().getErrMessage()) {
									logger.error("Errore firma " + lObject.getDescription());
									lStringBuffer.append(lObject.getDescription() + ";");
								}
								logger.debug("Non valida per " + lStringBuffer.toString());
							}
						}
					}
				}
				return lGenericFile;
			}
		}
		return null;
	}
	
	private FileMarcatoBean createResponseMarca(FileOperationResponse risposta) {
		FileMarcatoBean fileMarcatoBean = new FileMarcatoBean();
		
		boolean esitoVerifica = true;
		
		if (risposta != null && risposta.getFileoperationResponse() != null) {
			if (risposta.getFileoperationResponse().getFileOperationResults() != null
					&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {

				for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse()
						.getFileOperationResults().getFileOperationResult()) {
					
					if (opResponse instanceof ResponseSigVerify) {
						ResponseSigVerify lResponseSigVerify = (ResponseSigVerify) opResponse;
						if (lResponseSigVerify.getSigVerifyResult().getMessage() != null && lResponseSigVerify
								.getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")){
							esitoVerifica = false;
							logger.error("File non firmato");
						} else if (lResponseSigVerify.getErrorsMessage() != null 
								&& lResponseSigVerify.getErrorsMessage().getErrMessage().size() > 0) {
							logger.error("File  firmato con errori");
							esitoVerifica = false;
						} else {
							logger.error("File firmato");
							esitoVerifica = false;
							if( lResponseSigVerify!=null ){
								SigVerifyResultType sigVerResult = lResponseSigVerify.getSigVerifyResult();
								if( sigVerResult!=null ){
									SigVerifyResult sigVerRes = sigVerResult.getSigVerifyResult();
									
									if( sigVerRes!=null && 
											sigVerRes.getTimestampVerificationResult()!=null ){
										List<TimeStampInfotype> tsInfos = sigVerRes.getTimestampVerificationResult().getTimeStampInfo();
										if( tsInfos!=null ){
											for(TimeStampInfotype tsInfo : tsInfos){
												if(tsInfo!=null && tsInfo.getTsaInfo()!=null ) {
													TsaInfo tsaInfo = tsInfo.getTsaInfo();
													String tsaName = tsaInfo.getTsaName();
													logger.error("tsaName  " + tsaName);
													esitoVerifica = true;
													
													if(	tsInfo.getDateMillisec()!=null ){
														Calendar calendar = Calendar.getInstance();
														calendar.setTimeInMillis(Long.parseLong(tsInfo.getDateMillisec()));
														SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
														formatter.setTimeZone(calendar.getTimeZone());
														String dataOraMarca = formatter.format( calendar.getTime() );
														fileMarcatoBean.setTsMarca(dataOraMarca);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		fileMarcatoBean.setMarcato( esitoVerifica );
		return fileMarcatoBean;
	}

	/**
	 * @param pFile
	 * @param documentConfiguration
	 * @param lSignatureVerifyOptionBean
	 * @return
	 */
	private FileOperationRequest createFileOperationRequest(File pFile, DocumentConfiguration documentConfiguration,
			SignatureVerifyOptionBean lSignatureVerifyOptionBean) {
		FileOperationRequest request = new FileOperationRequest();

		Operations operations = new Operations();
		InputFormatRecognitionType input = new InputFormatRecognitionType();

		// Calcolo digest
		InputDigestType lInputDigestType = new InputDigestType();
		lInputDigestType.setDigestAlgId(documentConfiguration.getAlgoritmo());
		lInputDigestType.setEncoding(documentConfiguration.getEncoding());

		// Firma
		InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
		SignatureVerify lInputSigVerifyTypeSignatureVerify = new SignatureVerify();
		lInputSigVerifyTypeSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
		lInputSigVerifyTypeSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());

		TimestampVerifiy timestampVerifiy = new TimestampVerifiy();
		timestampVerifiy.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
		lInputSigVerifyType.setTimestampVerifiy(timestampVerifiy);
		lInputSigVerifyType.setSignatureVerify(lInputSigVerifyTypeSignatureVerify);
		lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
		lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
		operations.getOperation().add(input);
		operations.getOperation().add(lInputDigestType);
		operations.getOperation().add(lInputSigVerifyType);
		request.setOperations(operations);

		logger.debug("pFile:" + pFile.getName());

		DataHandler fileStream = new DataHandler(new FileDataSource(pFile));
		InputFile inputFile = new InputFile();
		inputFile.setFileUrl("file:" + pFile.getAbsolutePath());
		//inputFile.setFileStream(fileStream);
		InputFileOperationType fileOp = new InputFileOperationType();
		fileOp.setInputType(inputFile);
		request.setFileOperationInput(fileOp);
		return request;
	}
	
	private FileOperationRequest createFileOperationRequestMarca(File pFile, DocumentConfiguration documentConfiguration,
			SignatureVerifyOptionBean lSignatureVerifyOptionBean) {
		FileOperationRequest request = new FileOperationRequest();

		Operations operations = new Operations();
		
		// Firma
		InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
		SignatureVerify lInputSigVerifyTypeSignatureVerify = new SignatureVerify();
		lInputSigVerifyTypeSignatureVerify.setDetectCode(lSignatureVerifyOptionBean.isDetectCode());
		lInputSigVerifyTypeSignatureVerify.setCAReliability(lSignatureVerifyOptionBean.isCaReliability());

		TimestampVerifiy timestampVerifiy = new TimestampVerifiy();
		timestampVerifiy.setTSAReliability(lSignatureVerifyOptionBean.isTsaReliability());
		lInputSigVerifyType.setTimestampVerifiy(timestampVerifiy);
		lInputSigVerifyType.setSignatureVerify(lInputSigVerifyTypeSignatureVerify);
		lInputSigVerifyType.setRecursive(lSignatureVerifyOptionBean.isRecursive());
		lInputSigVerifyType.setChildValidation(lSignatureVerifyOptionBean.isChildValidation());
		operations.getOperation().add(lInputSigVerifyType);
		request.setOperations(operations);

		logger.debug("pFile:" + pFile.getName());

		DataHandler fileStream = new DataHandler(new FileDataSource(pFile));
		InputFile inputFile = new InputFile();
		inputFile.setFileUrl("file:" + pFile.getAbsolutePath());
		//inputFile.setFileStream(fileStream);
		InputFileOperationType fileOp = new InputFileOperationType();
		fileOp.setInputType(inputFile);
		request.setFileOperationInput(fileOp);
		return request;
	}
	
	/*private FileOperationRequest createFileOperationRequestMarca(File pFile) {
		FileOperationRequest request = new FileOperationRequest();

		Operations operations = new Operations();
		
		// Firma
		InputSigVerifyType lInputSigVerifyType = new InputSigVerifyType();
		SignatureVerify lInputSigVerifyTypeSignatureVerify = new SignatureVerify();
		lInputSigVerifyTypeSignatureVerify.setDetectCode(false);
		lInputSigVerifyTypeSignatureVerify.setCAReliability(false);

		TimestampVerifiy timestampVerifiy = new TimestampVerifiy();
		timestampVerifiy.setTSAReliability(false);
		lInputSigVerifyType.setTimestampVerifiy(timestampVerifiy);
		lInputSigVerifyType.setSignatureVerify(lInputSigVerifyTypeSignatureVerify);
		lInputSigVerifyType.setRecursive(true);
		lInputSigVerifyType.setChildValidation(true);
		operations.getOperation().add(lInputSigVerifyType);
		request.setOperations(operations);

		logger.debug("pFile:" + pFile.getName());

		DataHandler fileStream = new DataHandler(new FileDataSource(pFile));
		InputFile inputFile = new InputFile();
		inputFile.setFileStream(fileStream);
		InputFileOperationType fileOp = new InputFileOperationType();
		fileOp.setInputType(inputFile);
		request.setFileOperationInput(fileOp);
		return request;
	}*/

	/*private void analizzaChild(SigVerifyResultType child, List<Firmatario> firmatari) {
		SigVerifyResult lSigVerifyResultTypeSigVerifyResultChild = child.getSigVerifyResult();
		lSigVerifyResultTypeSigVerifyResultChild.getFormatResult().getEnvelopeFormat();
		List<SignerInformationType> infosChild = lSigVerifyResultTypeSigVerifyResultChild.getSignerInformations()
				.getSignerInformation();
		for (SignerInformationType info : infosChild) {
			StringBuffer lStringBuffer = new StringBuffer();
			DnType subject = info.getCertificato().getSubject();
			String firmatario = "C=" + subject.getC() + "CN=" + subject.getCn() + "NAME=" + subject.getName() + "O="
					+ subject.getO() + "OU=" + subject.getOu();
			lStringBuffer.append("Firmatario " + firmatario + "\n");
			DnType issuer = info.getCertificato().getSubject();
			String enteCertificatore = "C=" + issuer.getC() + "CN=" + issuer.getCn() + "NAME=" + issuer.getName() + "O="
					+ issuer.getO() + "OU=" + issuer.getOu();
			lStringBuffer.append("ENTE " + enteCertificatore);
			Firmatario lFirmatario = new Firmatario();
			lFirmatario.setCommonName(subject.getCn());
			firmatari.add(lFirmatario);
		}
		if (child.getSigVerifyResult() != null)
			analizzaChild(child.getSigVerifyResult().getChild(), firmatari);
	}*/

	public String getFormato(File file) throws Exception {
		try {
			String wsEndpoint = SpringHelper.getMainApplicationContext().getBean(DocumentConfiguration.class)
					.getOperationWsAddress();
			logger.debug("endpoint get formato:" + wsEndpoint);
			URL url = new URL(wsEndpoint);
			QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
			Service service = Service.create(url, qname);
			FileOperationNoOutputWS fooService = service.getPort(FileOperationNoOutputWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			Operations operations = new Operations();
			InputFormatRecognitionType input = new InputFormatRecognitionType();
			operations.getOperation().add(input);
			request.setOperations(operations);

			DataHandler fileStream = new DataHandler(new FileDataSource(file));
			InputFile inputFile = new InputFile();
			inputFile.setFileStream(fileStream);
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			request.setFileOperationInput(fileOp);
			FileOperationResponse risposta = fooService.execute(request);

			if (risposta != null && risposta.getFileoperationResponse() != null
					&& ResponseUtils.isResponseOK(risposta)) {
				if (risposta.getFileoperationResponse().getFileOperationResults() != null && risposta
						.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
					for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse()
							.getFileOperationResults().getFileOperationResult()) {
						if (opResponse instanceof ResponseFormatRecognitionType) {
							ResponseFormatRecognitionType formatResponse = (ResponseFormatRecognitionType) opResponse;
							return formatResponse.getMimeType();
						}
					}
				}
			}
		} catch (MalformedURLException e) {
			logger.error("Impossibile riconoscere il formato del file", e);
			throw new Exception("Impossibile riconoscere il formato del file", e);
		} catch (Exception e) {
			logger.error("Impossibile riconoscere il formato del file", e);
			throw new Exception("Impossibile riconoscere il formato del file", e);
		}

		return null;
	}
	
	/*public FileMarcatoBean verificaFileMarcato1(File pFile) throws Exception {
		
		String wsEndpoint = "http://10.138.154.6:10452/fileoperationutility/business/soap/fileop?wsdl";

		logger.debug("endpoint get formato:" + wsEndpoint);

		URL url = new URL(wsEndpoint);
		QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
		Service service = Service.create(url, qname);
		FileOperationWS fooService = service.getPort(FileOperationWS.class);

		// enable mtom on client
		((BindingProvider) fooService).getRequestContext();
		SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
		binding.setMTOMEnabled(true);

		FileOperationRequest request = createFileOperationRequestMarca(pFile);

		FileOperationResponse risposta = fooService.execute(request);
		
		return createResponseMarca(risposta);
	}*/
	
	public static void main(String[] args) {
		//File file = new File("C:/Users/Anna Tresauro/Desktop/csi/cmto/fileMarcato24.pdf");
		File file = new File("C:/Users/Anna Tresauro/Downloads/DD-3515-2020-TESTO_ATTO.pdf");
		
		WSClientUtils u = new WSClientUtils();
		/*try {
			FileMarcatoBean bean = u.verificaFileMarcato1(file);
			
			System.out.println(bean.isMarcato() + " " + bean.getTsMarca());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
//		FileMarcatoBean bean1 = MarcaUtils.isMarcato(file, "PADES");
//		System.out.println(bean1.isMarcato() + " " + bean1.getTsMarca());
	}

}
