package it.eng.client.applet.util;

import it.eng.certverify.clientws.CertificateVerifier;
import it.eng.certverify.clientws.CertificateVerifierRequest;
import it.eng.certverify.clientws.CertificateVerifierResponse;
import it.eng.certverify.clientws.VerificationInfo;
import it.eng.certverify.clientws.VerificationTypes;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.common.LogWriter;
import it.eng.common.bean.ValidationInfos;
import it.eng.cryptoutil.verify.util.ResponseUtils;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.FileOperationWS;
import it.eng.fileOperation.clientws.InputCodeDetectorType;
import it.eng.fileOperation.clientws.InputConversionType;
import it.eng.fileOperation.clientws.InputFile;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputSigVerifyType;
import it.eng.fileOperation.clientws.ResponseCodeDetector;
import it.eng.fileOperation.clientws.InputSigVerifyType.SignatureVerify;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.SigVerifyResultType;
import it.eng.fileOperation.clientws.SignerInformationType;
import it.eng.fileOperation.clientws.SignerInformationType.Certificato;
import it.eng.fileOperation.clientws.SignerInformationType.Marca;
import it.eng.fileOperation.clientws.VerificationStatusType;

import java.io.File;
import java.io.FileOutputStream;
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


public class WSClientUtils {

	public static File sbustaEConvertiFile( File fileInput, String fileName ) throws Exception{
		
		LogWriter.writeLog("Metodo di sbustamento e conversione");
		String wsEndpoint = null;
		String namespace = null;
		String serviceName = null;
		try {
			wsEndpoint = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_WSENDPOINT );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_WSENDPOINT + ": " + wsEndpoint );
			namespace = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_NAMESPACE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_NAMESPACE + ": " + namespace );
			serviceName = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_SERVICE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_SERVICE + ": " + serviceName );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà di configurazione del servizio di sbustamento e conversione", e );
			throw new Exception("Parametri non configurati");
		}
		
		//se esistono le configurazioni effettuo l'invocazione del servizio
		if( wsEndpoint!=null && namespace!=null && serviceName!=null ){
			
			FileOperationResponse result = null;
			
			LogWriter.writeLog("File da sbustare e convertire " + fileInput.getAbsolutePath(), false);
			try{
				LogWriter.writeLog("Invoco il WS " + wsEndpoint);
				URL url = new URL(wsEndpoint);
				QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
				Service service = Service.create(url, qname);
				FileOperationWS fooService = service.getPort(FileOperationWS.class);
				
				//enable mtom on client
				((BindingProvider) fooService).getRequestContext();
				//.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 4096);
				SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
				binding.setMTOMEnabled(true);
				
				FileOperationRequest request = new FileOperationRequest();
				
				InputConversionType input = new InputConversionType();
				input.setSignConversion(true);
				
				Operations operations = new Operations();
				operations.getOperation().add( input );
				request.setOperations(operations );
				
				DataHandler fileStream =  new DataHandler(new FileDataSource(fileInput));
				InputFile inputFile = new InputFile();
				inputFile.setFileStream( fileStream );
				InputFileOperationType file = new InputFileOperationType();
				file.setInputType(inputFile );
				LogWriter.writeLog("FILENAME " + fileName);
				if( fileName!=null )
					file.setOriginalName(fileName);
				request.setFileOperationInput(file );
				
				result = fooService.execute( request );
			} catch(Exception e){
				LogWriter.writeLog("Errore nell'invocazione del servizio di sbustamento e conversione", e);
				throw e;
			}
			
			// se ho ottenuto la response provo a generare il file	
			if( result!=null && ResponseUtils.isResponseOK(result) ){
				LogWriter.writeLog("Response is OK");
				FileOperationResponse.FileoperationResponse response = result.getFileoperationResponse();
				DataHandler fileResult = response.getFileResult();
				String outputFileName = null;
				if( fileName!=null )
					outputFileName = FileUtility.rename( fileName, "pdf");
				else 
					outputFileName = FileUtility.rename( fileInput.getName(), "pdf");
				String outputFilePath =   fileInput.getParentFile() + "/output/";
				File directory = new File( outputFilePath );
				if( !directory.exists() )
					directory.mkdir();
				try{
					File outputFile = new File(outputFilePath + outputFileName);
					if( !outputFile.exists() )
						outputFile.createNewFile();
					LogWriter.writeLog("Restituisco il file " + outputFilePath + outputFileName, false );
					FileOutputStream fos = new FileOutputStream(outputFile);
					fileResult.writeTo(fos);
					fos.flush();
					fos.close();
					return outputFile;
				} catch(Exception e){
					LogWriter.writeLog("Errore", e);
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
								LogWriter.writeLog(risultato.getClass().getName() +  message.getDescription() );
							}
						}
					}
				}
				return null;
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
	}
	
	public static ValidationInfos checkCertificate(X509Certificate certificate,boolean caCheck,boolean crlCheck) throws Exception {
		
		LogWriter.writeLog("Metodo di verifica del certificato");
		String wsEndpoint = null;
		String namespace = null;
		String serviceName = null;
		try {
			wsEndpoint = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERIFYCERT_WSENDPOINT );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_VERIFYCERT_WSENDPOINT + ": " + wsEndpoint );
			namespace = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERIFYCERT_NAMESPACE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_VERIFYCERT_NAMESPACE + ": " + namespace );
			serviceName = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERIFYCERT_SERVICE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_VERIFYCERT_SERVICE + ": " + serviceName );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà di configurazione del servizio di verifica certificato", e );
			throw new Exception("Parametri non configurati");
		}
		
		ValidationInfos ret = new ValidationInfos();
		if( wsEndpoint!=null && namespace!=null && serviceName!=null ){
			try{
				LogWriter.writeLog("Controllo ca richiesto? " + caCheck );
				LogWriter.writeLog("Controllo crl richiesto? " + crlCheck );
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
					
					LogWriter.writeLog("esito verifica " + CertificateUtils.isVerificationOk(result , VerificationTypes.CERTIFICATE_EXPIRATION ) );
					if(!CertificateUtils.isVerificationOk( result, VerificationTypes.CERTIFICATE_EXPIRATION)){
						String error=CertificateUtils.getError(result, VerificationTypes.CERTIFICATE_EXPIRATION );
						if(error!=null){
							ret.addError(error);
						} 
					}
					if(caCheck){
						LogWriter.writeLog("esito verifica ca " + CertificateUtils.isVerificationOk( result, VerificationTypes.CA_VERIFY ) );
						if(!CertificateUtils.isVerificationOk(result, VerificationTypes.CA_VERIFY)){
							String error=CertificateUtils.getError(result, VerificationTypes.CA_VERIFY);
							if(error!=null){
								ret.addError(error);
							} 
						}
					}
					if(crlCheck){
						LogWriter.writeLog("esito verifica crl " + CertificateUtils.isVerificationOk( result, VerificationTypes.CRL_VERIFY ) );
						if(!CertificateUtils.isVerificationOk(result, VerificationTypes.CRL_VERIFY)){
							String error=CertificateUtils.getError(result, VerificationTypes.CRL_VERIFY);
							if(error!=null){
								ret.addError(error);
							}
						}
					}
				}
			} catch (Exception e){
				LogWriter.writeLog("Errore", e);
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
		return ret;
	}
	
	public static ValidationInfos checkFileForMacro( File fileInput ) throws Exception {
		ValidationInfos ret = new ValidationInfos();
		
		FileOperationResponse risposta = null;
		try {
			risposta = verificaMacro( fileInput );
		} catch (Exception e) {
			LogWriter.writeLog( "Errore", e );
			throw e;
		}
		if(risposta!=null && !ResponseUtils.isResponseOK(risposta)){
			//prendo l'errore generico
			if(risposta.getGenericError()!=null && risposta.getGenericError().getErrorMessage()!=null){
				List<String> errors=risposta.getGenericError().getErrorMessage();
				for (String string : errors) {
					ret.addError(string);
				}  
			}
			if(risposta.getFileoperationResponse()!=null && risposta.getFileoperationResponse().getFileOperationResults()!=null){
			//prendo la siposta specifica
				it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults=new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
				foresults.getFileOperationResult().addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());	 
				ResponseCodeDetector responsecodedet=ResponseUtils.getResponse(foresults, ResponseCodeDetector.class);
				
				if(responsecodedet==null){
					//nessuna risposta per l'operazione
					ret.addError("Si è verificato un errore nelle verifica");
				} else if(responsecodedet.getErrorsMessage()!=null && responsecodedet.getErrorsMessage().getErrMessage()!=null){
					List<MessageType> listaErrori=responsecodedet.getErrorsMessage().getErrMessage();
					for (MessageType err : listaErrori) {
						if( err.getDescription()!=null){
							System.out.println("descr " + err.getDescription() );
							ret.addError( err.getDescription() );
						}
						else{ 
							System.out.println("code " + err.getCode() );
							ret.addError( err.getCode() );
						}
					}
				}
			}	
		}
		
		return ret;
	}
	
	public static FileOperationResponse verificaMacro( File fileInput) throws Exception {
		
		LogWriter.writeLog("Metodo di verifica Macro");
		
		String wsEndpointVerificaFirma = null;
		String namespaceVerificaFirma = null;
		String serviceVerificaFirma = null;
		try {
			wsEndpointVerificaFirma = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_WSENDPOINT );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_WSENDPOINT + ": " + wsEndpointVerificaFirma );
			namespaceVerificaFirma = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_NAMESPACE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_NAMESPACE + ": " + namespaceVerificaFirma );
			serviceVerificaFirma = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_SERVICE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_SERVICE + ": " + serviceVerificaFirma );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà di configurazione del servizio di verifica firme", e );
			return null;
		}
		
		FileOperationResponse result = null;
		
		if( wsEndpointVerificaFirma!=null && namespaceVerificaFirma!=null && serviceVerificaFirma!=null ){
			try{	
				URL url = new URL( wsEndpointVerificaFirma );   
				QName qname = new QName( namespaceVerificaFirma, serviceVerificaFirma );
				Service service = Service.create(url, qname);
				FileOperationWS fooService = service.getPort(FileOperationWS.class);
	
				//enable mtom on client
				SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
				binding.setMTOMEnabled(true);
	
				FileOperationRequest request = new FileOperationRequest();
	
				InputCodeDetectorType  input = new InputCodeDetectorType();
									
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
				LogWriter.writeLog("Errore ", e);
				throw new Exception( "errore durante l'invocazione del servizio di verifica firme ", e );
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
		return result;
	}
	
	public static FileOperationResponse verificaFirme( boolean checkCA, boolean checkCRL, Date dataRif, File fileInput) 
			throws Exception {
		
		LogWriter.writeLog("Metodo di verifica Firme");
		
		String wsEndpointVerificaFirma = null;
		String namespaceVerificaFirma = null;
		String serviceVerificaFirma = null;
		try {
			wsEndpointVerificaFirma = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_WSENDPOINT );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_WSENDPOINT + ": " + wsEndpointVerificaFirma );
			namespaceVerificaFirma = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_NAMESPACE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_NAMESPACE + ": " + namespaceVerificaFirma );
			serviceVerificaFirma = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILEOPERATION_SERVICE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_FILEOPERATION_SERVICE + ": " + serviceVerificaFirma );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà di configurazione del servizio di verifica firme", e );
			return null;
		}
		
		FileOperationResponse result = null;
		
		if( wsEndpointVerificaFirma!=null && namespaceVerificaFirma!=null && serviceVerificaFirma!=null ){
			try{	
				LogWriter.writeLog("Controllo ca richiesto? " + checkCA );
				LogWriter.writeLog("Controllo crl richiesto? " + checkCRL );
				
				URL url = new URL( wsEndpointVerificaFirma );   
				QName qname = new QName( namespaceVerificaFirma, serviceVerificaFirma );
				Service service = Service.create(url, qname);
				FileOperationWS fooService = service.getPort(FileOperationWS.class);
	
				//enable mtom on client
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
					LogWriter.writeLog("Imposto la data di riferimento della verifica firme " + dataRif );
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
				LogWriter.writeLog("Errore ", e);
				throw new Exception( "errore durante l'invocazione del servizio di verifica firme ", e );
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
		return result;
	}
	
	public static DefaultMutableTreeNode processHierarchy(SigVerifyResultType hierarchy) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITO ) );
		DefaultMutableTreeNode node = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOVALIDAZIONI ) );
	    DefaultMutableTreeNode child=null;
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getFormatResult()!=null){
	    	 child = buildFromResponse( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOFORMATO ) + ":",hierarchy.getSigVerifyResult().getFormatResult());  
	    	 DefaultMutableTreeNode tipoBusta=new DefaultMutableTreeNode(hierarchy.getSigVerifyResult().getFormatResult().getEnvelopeFormat());
	    	 child.add(tipoBusta);
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getSignatureValResult()!=null){
	    	 child = buildFromResponse( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOFIRMA ) + ":",hierarchy.getSigVerifyResult().getSignatureValResult()); 
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getCertExpirationResult()!=null){
	    	 child = buildFromResponse( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOCERTIFICATO ) + ":",hierarchy.getSigVerifyResult().getCertExpirationResult());  
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getCRLResult()!=null){
	    	 child = buildFromResponse( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOCRL ) + ":",hierarchy.getSigVerifyResult().getCRLResult() );  
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getCAReliabilityResult()!=null){
	    	 child = buildFromResponse( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOCA ) + ":",hierarchy.getSigVerifyResult().getCAReliabilityResult());  
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getDetectionCodeResult()!=null){
	    	 child = buildFromResponse( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMACRO ) + ":",hierarchy.getSigVerifyResult().getDetectionCodeResult());  
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getTimestampVerificationResult()!=null){
	    	 child = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA ) + ":" + hierarchy.getSigVerifyResult().getTimestampVerificationResult().getTimeStampInfo()); // Ie Leaf
	    	 node.add(child);
	    }
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getChild()!=null){
	    	child=processHierarchy(hierarchy.getSigVerifyResult().getChild());
	    	node.add(child);
	    }
	    root.add( node );
	    
	    if(hierarchy.getSigVerifyResult()!=null && hierarchy.getSigVerifyResult().getSignerInformations()!=null && hierarchy.getSigVerifyResult().getSignerInformations().getSignerInformation()!=null ){
	    	DefaultMutableTreeNode firme = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEROOT ) );
	    	List<SignerInformationType> signerInformationList = hierarchy.getSigVerifyResult().getSignerInformations().getSignerInformation();
	    	for(SignerInformationType signerInformation : signerInformationList){
	    		DefaultMutableTreeNode firmaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEFIRMA ) );
		    	
	    		Certificato certificato = signerInformation.getCertificato();
	    		if( certificato!=null ){
	    			DefaultMutableTreeNode certificatoNode = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODECERTIFICATO ) );
	    			DefaultMutableTreeNode dataDecorrenza = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEDATADECORRENZA ) + ": " + certificato.getDataDecorrenza() );
	    			certificatoNode.add( dataDecorrenza );
	    			DefaultMutableTreeNode dataScadenza = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEDATASCADENZA ) + ": " + certificato.getDataScadenza() );
	    			certificatoNode.add( dataScadenza );
	    			if( certificato.getIssuer()!=null ){
	    				DefaultMutableTreeNode issuerNode = aggiungiNodo( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEISSUER ), certificato.getIssuer().getC(), certificato.getIssuer().getCn(), certificato.getIssuer().getO(), certificato.getIssuer().getOu() );
	    				certificatoNode.add( issuerNode );
		    		}
	    			if( certificato.getSubject()!=null ){
	    				DefaultMutableTreeNode subjectNode = aggiungiNodo( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT ), certificato.getSubject().getC(), certificato.getSubject().getCn(), certificato.getSubject().getO(), certificato.getSubject().getOu() );
		    			certificatoNode.add( subjectNode );
		    		}
	    			firmaNode.add( certificatoNode );
	    		}
	    		Marca marca = signerInformation.getMarca();
	    		if( marca!=null ){
	    			DefaultMutableTreeNode marcaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEMARCA ) );
	    			if(marca.getTsaName()!=null)
	    				marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODETSANAME ) + ":" + marca.getTsaName() ) );
	    			if(marca.getDate()!=null)
	    				marcaNode.add( new DefaultMutableTreeNode("Data:" + marca.getDate() ) );
	    			if(marca.getPolicy()!=null)
	    				marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEPOLICY ) + ":" + marca.getPolicy() ) );
	    			if(marca.getSerialNumber()!=null)
	    				marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODESERIALNUMBER ) + ":" + marca.getSerialNumber() ) );
	    			if(marca.getVerificationStatus()!=null && marca.getVerificationStatus().equals( VerificationStatusType.OK ) )
						marcaNode.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA ) + " " + marca.getVerificationStatus() ) );
					else {
						DefaultMutableTreeNode esitoControlloMarcaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA ) + " " + marca.getVerificationStatus() );
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
	    			DefaultMutableTreeNode controFirmaNode = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODECONTROFIRMA ) );
	    			certificato = controFirma.getCertificato();
	    			if( certificato!=null ){
		    			DefaultMutableTreeNode certificatoNode = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODECERTIFICATO ));
		    			DefaultMutableTreeNode dataDecorrenza = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEDATADECORRENZA ) + " " +certificato.getDataDecorrenza() );
						certificatoNode.add( dataDecorrenza );
						DefaultMutableTreeNode dataScadenza = new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEDATASCADENZA ) + " " + certificato.getDataScadenza() );
						certificatoNode.add( dataScadenza );
		    			if( certificato.getIssuer()!=null ){
		    				DefaultMutableTreeNode issuerNode = aggiungiNodo( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEISSUER ), certificato.getIssuer().getC(), certificato.getIssuer().getCn(), certificato.getIssuer().getO(), certificato.getIssuer().getOu() );
		    				certificatoNode.add( issuerNode );
			    		}
		    			if( certificato.getSubject()!=null ){
		    				DefaultMutableTreeNode subjectNode = aggiungiNodo( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT ), certificato.getSubject().getC(), certificato.getSubject().getCn(), certificato.getSubject().getO(), certificato.getSubject().getOu() );
			    			certificatoNode.add( subjectNode );
			    		}
		    			controFirmaNode.add( certificatoNode );
		    		}
	    			firmaNode.add( controFirmaNode );
	    		}
	    		
	    		firme.add( firmaNode );
	    	}
	    	root.add( firme );
	    }    
	    
		return(root);
	}
	
	private static DefaultMutableTreeNode aggiungiNodo( String title, String c, String cn, String o, String ou ){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode( title );
		if( c!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEC ) + " " + c ) );
		if( cn!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODECN ) + " " + cn ) );
		if( o!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEO ) + " " + o ) );
		if( ou!=null)
			node.add( new DefaultMutableTreeNode( Messages.getMessage( MessageKeys.PANEL_FIRMAMARCA_HIERARCHYNODEOU ) + " " + ou ) );
		return node;
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
	
}
