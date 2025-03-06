package it.eng.hybrid.module.jpedal.util;


import it.eng.certverify.clientws.CertificateVerifier;
import it.eng.certverify.clientws.CertificateVerifierRequest;
import it.eng.certverify.clientws.CertificateVerifierResponse;
import it.eng.certverify.clientws.VerificationInfo;
import it.eng.certverify.clientws.VerificationTypes;
import it.eng.cryptoutil.verify.util.ResponseUtils;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.FileOperationWS;
import it.eng.fileOperation.clientws.InputConversionType;
import it.eng.fileOperation.clientws.InputFile;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputSigVerifyType;
import it.eng.fileOperation.clientws.InputSigVerifyType.SignatureVerify;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponsePdfConvResultType;
import it.eng.fileOperation.clientws.ResponseUnpackType;
import it.eng.fileOperation.clientws.SigVerifyResultType;
import it.eng.fileOperation.clientws.SignerInformationType;
import it.eng.fileOperation.clientws.SignerInformationType.Certificato;
import it.eng.fileOperation.clientws.SignerInformationType.Marca;
import it.eng.fileOperation.clientws.VerificationStatusType;
import it.eng.hybrid.module.jpedal.exception.FOConversionException;
import it.eng.hybrid.module.jpedal.exception.FOException;
import it.eng.hybrid.module.jpedal.exception.FOUnpackException;
import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

public class WSClientUtils {

	public final static Logger logger = Logger.getLogger(JPedalApplication.class);
	
	public static String sbustaEConvertiFile( PreferenceManager preferenceManager, String selectedFile) throws Exception {
		
		logger.info("Metodo di sbustamento e conversione");
		String wsEndpoint = null;
		String namespace = null;
		String serviceName = null;
		try {
			wsEndpoint = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOPERATION_WSENDPOINT );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_FILEOPERATION_WSENDPOINT + ": " + wsEndpoint );
			namespace = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOPERATION_NAMESPACE );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_FILEOPERATION_NAMESPACE + ": " + namespace );
			serviceName = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOPERATION_SERVICE );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_FILEOPERATION_SERVICE + ": " + serviceName );
		} catch (Exception e) {
			logger.info("Errore nel recupero delle proprietà di configurazione del servizio di sbustamento e conversione", e );
			throw new Exception("Parametri non configurati");
		}
		
		//se esistono le configurazioni effettuo l'invocazione del servizio
		if( wsEndpoint!=null && namespace!=null && serviceName!=null ){
			FileOperationResponse result = null;
			
			logger.info("File da sbustare e convertire " + selectedFile);
			File fileInput = new File(selectedFile);
			
			try {
				URL url = new URL( wsEndpoint );
				QName qname = new QName( namespace, serviceName );
				Service service = Service.create(url, qname);
				FileOperationWS fooService = service.getPort(FileOperationWS.class);
				
				//enable mtom on client
				//((BindingProvider) fooService).getRequestContext().put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 4096);
				SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
				binding.setMTOMEnabled(true);
				
				FileOperationRequest request = new FileOperationRequest();
	
				InputConversionType input = new InputConversionType();
				input.setSignConversion( false );
				
				Operations operations = new Operations();
				operations.getOperation().add( input );
				request.setOperations(operations );
				
				DataHandler fileStream =  new DataHandler(new FileDataSource(fileInput));
				InputFile inputFile = new InputFile();
				inputFile.setFileStream( fileStream );
				InputFileOperationType file = new InputFileOperationType();
				file.setInputType(inputFile );
				request.setFileOperationInput(file );
	
				result = fooService.execute( request );
				
			} catch (MalformedURLException e) {
				logger.info("Errore nell'invocazione del servizio di sbustamento e conversione", e);
				throw new FOException();
			}
			
			// se ho ottenuto la response provo a generare il file	
			if( result!=null && ResponseUtils.isResponseOK(result) ){
				FileOperationResponse.FileoperationResponse response = result.getFileoperationResponse();
				String outputFileName = FileUtility.rename( fileInput.getName(), "pdf");
				String outputFilePath =   fileInput.getParentFile() + "/output/";
				File directory = new File( outputFilePath );
				if( !directory.exists() )
					directory.mkdir();
				File outputFile = new File(outputFilePath + outputFileName);
				try{
					if( !outputFile.exists() )
						outputFile.createNewFile();
					logger.info("Restituisco il file " + outputFilePath + outputFileName );
					FileOutputStream fos = new FileOutputStream( outputFile );
					DataHandler fileResult = response.getFileResult();
					if( fileInput!=null)
						fileResult.writeTo(fos);
					fos.flush();
					fos.close();
					return outputFilePath + outputFileName;
				} catch(Exception e){
					logger.info("Errore", e);
					return null;
				}
			} else {
				if( result!=null && result.getFileoperationResponse()!=null && result.getFileoperationResponse().getFileOperationResults()!=null){
					List<AbstractResponseOperationType> listaRisultati = result.getFileoperationResponse().getFileOperationResults().getFileOperationResult();
					for(AbstractResponseOperationType risultato : listaRisultati){
						if(VerificationStatusType.ERROR.equals(risultato.getVerificationStatus()) || 
								VerificationStatusType.KO.equals(risultato.getVerificationStatus() )){
							List<MessageType> errors = risultato.getErrorsMessage().getErrMessage();
							for(MessageType message : errors) {
								logger.info(risultato.getClass().getName() + " - " +  message.getDescription() );
							}
							if( risultato instanceof ResponsePdfConvResultType )
								throw new FOConversionException( MessageConstants.MSG_FO_CONVERSION_EXCEPTION );
							if( risultato instanceof ResponseUnpackType )
								throw new FOUnpackException( MessageConstants.MSG_FO_UNPACK_EXCEPTION );
						}
					}
				}
				return null;
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
	}


	public static ValidationInfos checkCertificate(PreferenceManager preferenceManager, X509Certificate certificate,boolean caCheck,boolean crlCheck) throws Exception {
		
		logger.info("Metodo di verifica del certificato");
		String wsEndpoint = null;
		String namespace = null;
		String serviceName = null;
		try {
			wsEndpoint = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_VERIFYCERT_WSENDPOINT );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_VERIFYCERT_WSENDPOINT + ": " + wsEndpoint );
			namespace = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_VERIFYCERT_NAMESPACE );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_VERIFYCERT_NAMESPACE + ": " + namespace );
			serviceName = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_VERIFYCERT_SERVICE );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_VERIFYCERT_SERVICE + ": " + serviceName );
		} catch (Exception e) {
			logger.info("Errore nel recupero delle proprietà di configurazione del servizio di verifica certificato", e );
			throw new Exception("Parametri non configurati");
		}
		
		ValidationInfos ret = new ValidationInfos();
		if( wsEndpoint!=null && namespace!=null && serviceName!=null ){
			try{
				logger.info("Controllo ca richiesto? " + caCheck );
				logger.info("Controllo crl richiesto? " + crlCheck );
				if( caCheck || crlCheck ){
					//effettua chiamata
					URL url = new URL( wsEndpoint );
					QName qname = new QName( namespace, serviceName );
	
					Service service = Service.create(url, qname);
					CertificateVerifier verifier = service.getPort(CertificateVerifier.class);
					CertificateVerifierRequest request= new CertificateVerifierRequest();
					request.setX509Cert(certificate.getEncoded() );
					VerificationInfo vinfo= new VerificationInfo();
					vinfo.setVerificationID(VerificationTypes.CERTIFICATE_EXPIRATION);
					request.getVerificationInfo().add(vinfo);
					if(caCheck){
						VerificationInfo vinfoca= new VerificationInfo();
						vinfoca.setVerificationID(VerificationTypes.CA_VERIFY);
						request.getVerificationInfo().add(vinfoca);
					}
					if(crlCheck){
						VerificationInfo vinfocrl= new VerificationInfo();
						vinfocrl.setVerificationID(VerificationTypes.CRL_VERIFY);
						request.getVerificationInfo().add(vinfocrl);
					}
					CertificateVerifierResponse result = verifier.check( request );
					
					//  convert to validation
					if(!CertResponseUtils.isVerificationOk(result, VerificationTypes.CERTIFICATE_EXPIRATION)){
						String error=CertResponseUtils.getError(result, VerificationTypes.CERTIFICATE_EXPIRATION);
						if(error!=null){
							ret.addError(error);
						} 
					}
					if(caCheck){
						if(!CertResponseUtils.isVerificationOk(result, VerificationTypes.CA_VERIFY)){
							String error=CertResponseUtils.getError(result, VerificationTypes.CA_VERIFY);
							if(error!=null){
								ret.addError(error);
							} 
						}
					}
					if(crlCheck){
	
						if(!CertResponseUtils.isVerificationOk(result, VerificationTypes.CRL_VERIFY)){
							String error=CertResponseUtils.getError(result, VerificationTypes.CRL_VERIFY);
							if(error!=null){
								ret.addError(error);
							}
						}
					}
				}
			} catch (Exception e ){
				logger.info("Errore", e);
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
		return ret;
	}

	public static FileOperationResponse verificaFirme(PreferenceManager preferenceManager, boolean checkCA, boolean checkCRL, Date dataRif, File fileInput) 
			throws Exception {
		
		logger.info("Metodo di verifica Firme");
		
		String wsEndpointVerificaFirma = null;
		String namespaceVerificaFirma = null;
		String serviceVerificaFirma = null;
		try {
			wsEndpointVerificaFirma = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOPERATION_WSENDPOINT );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_FILEOPERATION_WSENDPOINT + ": " + wsEndpointVerificaFirma );
			namespaceVerificaFirma = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOPERATION_NAMESPACE );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_FILEOPERATION_NAMESPACE + ": " + namespaceVerificaFirma );
			serviceVerificaFirma = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOPERATION_SERVICE );
			logger.info( "Proprietà " + ConfigConstants.PROPERTY_FILEOPERATION_SERVICE + ": " + serviceVerificaFirma );
		} catch (Exception e) {
			logger.info("Errore nel recupero delle proprietà di configurazione del servizio di verifica firme", e );
			return null;
		}
		
		FileOperationResponse result = null;
		
		if( wsEndpointVerificaFirma!=null && namespaceVerificaFirma!=null && serviceVerificaFirma!=null ){
			try{	
				logger.info("Controllo ca richiesto? " + checkCA );
				logger.info("Controllo crl richiesto? " + checkCRL );
				
				URL url = new URL( wsEndpointVerificaFirma );   
				QName qname = new QName( namespaceVerificaFirma, serviceVerificaFirma );
				Service service = Service.create(url, qname);
				FileOperationWS fooService = service.getPort(FileOperationWS.class);
	
				//enable mtom on client
	//			((BindingProvider) fooService).getRequestContext().put("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size", 4096);
				SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
				binding.setMTOMEnabled(true);
	
				FileOperationRequest request = new FileOperationRequest();
	
				InputSigVerifyType input = new InputSigVerifyType();
				input.setRecursive(true);
				input.setChildValidation(false);
				SignatureVerify signatureVerify = new SignatureVerify();
				signatureVerify.setCAReliability( checkCA );
				signatureVerify.setCRLCheck( checkCRL );
				input.setSignatureVerify( signatureVerify  );
				if( dataRif!=null ){
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime( dataRif);
					XMLGregorianCalendar dataRifgreg = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal );
					logger.info("Imposto la data di riferimento della verifica firme " + dataRif );
					input.setDataRif( dataRifgreg );
				}
									
				Operations operations = new Operations();
				operations.getOperation().add( input );
				request.setOperations(operations );
	
				DataHandler fileStream =  new DataHandler(new FileDataSource(fileInput));
				InputFile inputFile = new InputFile();
				inputFile.setFileStream( fileStream );
				InputFileOperationType file = new InputFileOperationType();
				file.setInputType(inputFile );
				request.setFileOperationInput(file );
	
				result = fooService.execute( request );
			
			} catch(Exception e){
				logger.info("Errore ", e);
				throw new Exception( "errore durante l'invocazione del servizio di verifica firme ", e );
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
		return result;
	}
	
	public static DefaultMutableTreeNode processHierarchy(DefaultMutableTreeNode root, SigVerifyResultType hierarchy) {
		DefaultMutableTreeNode child=null;
		if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getSignerInformations()!=null && hierarchy.getSigVerifyResult().getSignerInformations().getSignerInformation()!=null ){
			List<SignerInformationType> signerInformationList = hierarchy.getSigVerifyResult().getSignerInformations().getSignerInformation();
			int nFirma = 1;
			for(SignerInformationType signerInformation : signerInformationList){
				DefaultMutableTreeNode firmaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEFIRMA ) + " " + nFirma );
				
				Certificato certificato = signerInformation.getCertificato();
				if( certificato!=null ){
					DefaultMutableTreeNode certificatoNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECERTIFICATO ) );
					DefaultMutableTreeNode dataDecorrenza = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEDATADECORRENZA ) + " " + certificato.getDataDecorrenza() );
					certificatoNode.add( dataDecorrenza );
					DefaultMutableTreeNode dataScadenza = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEDATASCADENZA ) + " " + certificato.getDataScadenza() );
					certificatoNode.add( dataScadenza );
					if( certificato.getIssuer()!=null ){
						DefaultMutableTreeNode issuerNode = aggiungiNodo( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEISSUER ), certificato.getIssuer().getC(), certificato.getIssuer().getCn(), certificato.getIssuer().getO(), certificato.getIssuer().getOu() );
//						DefaultMutableTreeNode issuerNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEISSUER ) );
//						if(certificato.getIssuer().getName()!=null)
//							issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODENOME ) + " " + certificato.getIssuer().getName() ) );
//						if(certificato.getIssuer().getC()!=null)
//							issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEC ) + " " + certificato.getIssuer().getC() ) );
//						if(certificato.getIssuer().getCn()!=null)
//							issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECN ) + " " + certificato.getIssuer().getCn() ) );
//						if(certificato.getIssuer().getO()!=null)
//							issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEO ) + " " + certificato.getIssuer().getO() ) );
//						if(certificato.getIssuer().getOu()!=null)
//							issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEOU ) + " " + certificato.getIssuer().getOu() ) );
						certificatoNode.add( issuerNode );
					}
					if( certificato.getSubject()!=null ){
						DefaultMutableTreeNode subjectNode = aggiungiNodo( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT ), certificato.getSubject().getC(), certificato.getSubject().getCn(), certificato.getSubject().getO(), certificato.getSubject().getOu() );
//						DefaultMutableTreeNode subjectNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT ) );
//						if(certificato.getSubject().getName()!=null)
//							subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODENOME ) + " " + certificato.getSubject().getName() ) );
//						if(certificato.getSubject().getC()!=null)
//							subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEC ) + " " + certificato.getSubject().getC() ) );
//						if(certificato.getSubject().getCn()!=null)
//							subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECN ) + " " + certificato.getSubject().getCn() ) );
//						if(certificato.getSubject().getO()!=null)
//							subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEO ) + " " + certificato.getSubject().getO() ) );
//						if(certificato.getSubject().getOu()!=null)
//							subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEOU ) + " " + certificato.getSubject().getOu() ) );
						certificatoNode.add( subjectNode );
					}
					firmaNode.add( certificatoNode );
				}
				Marca marca = signerInformation.getMarca();
				if( marca!=null ){
					DefaultMutableTreeNode marcaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEMARCA ) );
					if(marca.getTsaName()!=null)
						marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODETSANAME ) + " " + marca.getTsaName() ) );
					if(marca.getDate()!=null)
						marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEDATA ) + " " + marca.getDate() ) );
					if(marca.getPolicy()!=null)
						marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEPOLICY ) + " " + marca.getPolicy() ) );
					if(marca.getSerialNumber()!=null)
						marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODESERIALNUMBER ) + " " + marca.getSerialNumber() ) );
					if(marca.getVerificationStatus()!=null && marca.getVerificationStatus().equals( VerificationStatusType.OK ) )
						marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA ) + " " + marca.getVerificationStatus() ) );
					else {
						DefaultMutableTreeNode esitoControlloMarcaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA ) + " " + marca.getVerificationStatus() );
						marcaNode.add( esitoControlloMarcaNode );
						if( marca.getErrorsMessage()!=null ){
							List<MessageType> errorsList = marca.getErrorsMessage().getErrMessage();
							for (MessageType errorBean: errorsList) {
								DefaultMutableTreeNode errorBeanNode=new DefaultMutableTreeNode(errorBean.getCode()+" "+errorBean.getDescription());
								esitoControlloMarcaNode.add( errorBeanNode );
							}
						}
						if( marca.getWarnings()!=null ){
							List<MessageType> warningsList = marca.getWarnings().getWarnMessage();
							for (MessageType warningBean: warningsList) {
								DefaultMutableTreeNode warningBeanNode=new DefaultMutableTreeNode(warningBean.getCode()+" "+warningBean.getDescription());
								esitoControlloMarcaNode.add( warningBeanNode );
							}
						}
					}
					firmaNode.add( marcaNode );
				}
				SignerInformationType controFirma = signerInformation.getControFirma();
				if( controFirma!=null ){
					DefaultMutableTreeNode controFirmaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECONTROFIRMA ) );
					certificato = controFirma.getCertificato();
					if( certificato!=null ){
						DefaultMutableTreeNode certificatoNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECERTIFICATO ) );
						DefaultMutableTreeNode dataDecorrenza = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEDATADECORRENZA ) + " " +certificato.getDataDecorrenza() );
						certificatoNode.add( dataDecorrenza );
						DefaultMutableTreeNode dataScadenza = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEDATASCADENZA ) + " " + certificato.getDataScadenza() );
						certificatoNode.add( dataScadenza );
						if( certificato.getIssuer()!=null ){
							DefaultMutableTreeNode issuerNode = aggiungiNodo( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEISSUER ), certificato.getIssuer().getC(), certificato.getIssuer().getCn(), certificato.getIssuer().getO(), certificato.getIssuer().getOu() );
//							DefaultMutableTreeNode issuerNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEISSUER ) );
//							if(certificato.getIssuer().getName()!=null)
//								issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODENOME ) + " " + certificato.getIssuer().getName() ) );
//							if(certificato.getIssuer().getC()!=null)
//								issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEC ) + " " + certificato.getIssuer().getC() ) );
//							if(certificato.getIssuer().getCn()!=null)
//								issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECN ) + " " + certificato.getIssuer().getCn() ) );
//							if(certificato.getIssuer().getO()!=null)
//								issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEO ) + " " + certificato.getIssuer().getO() ) );
//							if(certificato.getIssuer().getOu()!=null)
//								issuerNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEOU ) + " " + certificato.getIssuer().getOu() ) );
							certificatoNode.add( issuerNode );
						}
						if( certificato.getSubject()!=null ){
							DefaultMutableTreeNode subjectNode = aggiungiNodo( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT ), certificato.getSubject().getC(), certificato.getSubject().getCn(), certificato.getSubject().getO(), certificato.getSubject().getOu() );
//							DefaultMutableTreeNode subjectNode = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT ) );
//							if(certificato.getSubject().getName()!=null)
//								subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODENOME ) + " " + certificato.getSubject().getName() ) );
//							if(certificato.getSubject().getC()!=null)
//								subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEC ) + " " + certificato.getSubject().getC() ) );
//							if(certificato.getSubject().getCn()!=null)
//								subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECN ) + " " + certificato.getSubject().getCn() ) );
//							if(certificato.getSubject().getO()!=null)
//								subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEO ) + " " + certificato.getSubject().getO() ) );
//							if(certificato.getSubject().getOu()!=null)
//								subjectNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEOU ) + " " + certificato.getSubject().getOu() ) );
							certificatoNode.add( subjectNode );
						}
						controFirmaNode.add( certificatoNode );
					}
					firmaNode.add( controFirmaNode );
				}
				if(signerInformation.getSigVerifyResult()!=null && signerInformation.getSigVerifyResult().getSignatureValResult()!=null){
					child = buildFromResponse( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOFIRMA ), signerInformation.getSigVerifyResult().getSignatureValResult()); 
					firmaNode.add(child);
				}
				if(signerInformation.getSigVerifyResult()!=null && signerInformation.getSigVerifyResult().getCertExpirationResult()!=null){
					child = buildFromResponse( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOCERTIFICATO ),signerInformation.getSigVerifyResult().getCertExpirationResult());  
					firmaNode.add(child);
				}
				if(signerInformation.getSigVerifyResult()!=null && signerInformation.getSigVerifyResult().getCRLResult()!=null){
					child = buildFromResponse( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOCRL ),signerInformation.getSigVerifyResult().getCRLResult() );  
					firmaNode.add(child);
				}
				if(signerInformation.getSigVerifyResult()!=null && signerInformation.getSigVerifyResult().getCAReliabilityResult()!=null){
					child = buildFromResponse( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOCA ),signerInformation.getSigVerifyResult().getCAReliabilityResult());  
					firmaNode.add(child);
				}
				if(signerInformation.getSigVerifyResult()!=null && signerInformation.getSigVerifyResult().getTimestampVerificationResult()!=null){
					child = new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA ) + signerInformation.getSigVerifyResult().getTimestampVerificationResult().getTimeStampInfo()); // Ie Leaf
					firmaNode.add(child);
				}
				if(signerInformation.getSigVerifyResult()!=null && signerInformation.getSigVerifyResult().getChild()!=null){
					child=processHierarchy(firmaNode, signerInformation.getSigVerifyResult().getChild());
					firmaNode.add(child);
				}

				nFirma++;
				root.add( firmaNode );
			}
		}    

		return root;
	}

	private static DefaultMutableTreeNode buildFromResponse(String title, AbstractResponseOperationType art){
		DefaultMutableTreeNode ret=new DefaultMutableTreeNode(title+" "+art.getVerificationStatus());
		if(art.getErrorsMessage()!=null && art.getErrorsMessage().getErrMessage()!=null){
			List<MessageType> errorsList = art.getErrorsMessage().getErrMessage();
			for (MessageType errorBean: errorsList) {
				DefaultMutableTreeNode errorBeanNode=new DefaultMutableTreeNode(errorBean.getCode()+" "+errorBean.getDescription());
				ret.add( errorBeanNode );
			}
		}
		if(art.getWarnings()!=null && art.getWarnings().getWarnMessage()!=null){
			List<MessageType> warningsList = art.getWarnings().getWarnMessage();
			for (MessageType warningBean: warningsList) {
				DefaultMutableTreeNode warningBeanNode=new DefaultMutableTreeNode(warningBean.getCode()+" "+warningBean.getDescription());
				ret.add( warningBeanNode );
			}
		}
		return ret;
	}

	private static DefaultMutableTreeNode aggiungiNodo( String title, String c, String cn, String o, String ou ){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode( title );
		if( c!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEC ) + " " + c ) );
		if( cn!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODECN ) + " " + cn ) );
		if( o!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEO ) + " " + o ) );
		if( ou!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageConstants.PANEL_FIRMAMARCA_HIERARCHYNODEOU ) + " " + ou ) );
		return node;
	}
	
}
