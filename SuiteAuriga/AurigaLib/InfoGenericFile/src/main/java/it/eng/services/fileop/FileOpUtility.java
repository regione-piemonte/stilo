/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.axis.types.URI;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.fileOperation.clientws.FileOperationNoOutputWS;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.FileOperationWS;
import it.eng.fileOperation.clientws.InputFile;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class FileOpUtility {

	private static final Logger log = Logger.getLogger(FileOpUtility.class);

	private static final String FILEOP_NAMESPACE = "it.eng.fileoperation.ws";
	private static final String FILEOP_SERVICENAME = "FOImplService";	
	private static final String FILEOPNOOUT_SERVICENAME = "FONoOutputImplService";	
	
	public static FileOperationResponse callFileOperation(InputFileOperationType inputFileOperationType, Operations operations) throws Exception {
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		
		FileOperationResponse lFileOperationResponse = null;

		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			lFileOperationResponse = sendCallFileop(inputFileOperationType, operations, lDocumentConfiguration, url);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(String.format("Errore nella chiamata a FileOperation: %s", e.getMessage()));
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			
			log.debug("Risposta dal servizio di FileOperation");			
			return lFileOperationResponse;
									
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				String errors = null;				
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage()) {
					if(errors == null) { 
						errors = err;
					} else {
						errors += "; " + err;
					}					
				}
				throw new Exception(String.format("Errore generico nella chiamata a FileOperation: %s", errors));
			}
		} else {
			throw new Exception("Errore generico: nessuna risposta da FileOperation");
		}
		
		return null;
	}
	
	public static FileOperationResponse callFileOperationNoOut(InputFileOperationType inputFileOperationType, Operations operations) throws Exception {
		
		DocumentConfiguration lDocumentConfiguration = null;
		if (SpringAppContext.getContext().containsBean("DocumentConfigurationNoOutput")) {
			lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfigurationNoOutput");
		} else {
			lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		}
		
		FileOperationResponse lFileOperationResponse = null;

		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			// Tengo la retrocompatibilità con il vecchio endpoint
			if (url.toString().indexOf("fileopNoOutput") > -1) {
				// Sto usando l'end point fileopNoOutput
				lFileOperationResponse = sendCallFileopNoOut(inputFileOperationType, operations, lDocumentConfiguration, url);
			} else {
				// Sto usando l'end point vecchio
				lFileOperationResponse = sendCallFileop(inputFileOperationType, operations, lDocumentConfiguration, url);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(String.format("Errore nella chiamata a FileOperation: %s", e.getMessage()));
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			
			log.debug("Risposta dal servizio di FileOperation");			
			return lFileOperationResponse;
									
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				String errors = null;				
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage()) {
					if(errors == null) { 
						errors = err;
					} else {
						errors += "; " + err;
					}					
				}
				throw new Exception(String.format("Errore generico nella chiamata a FileOperation: %s", errors));
			}
		} else {
			throw new Exception("Errore generico: nessuna risposta da FileOperation");
		}
		
		return null;
	}
	
	private static FileOperationResponse sendCallFileop(InputFileOperationType inputFileOperationType, Operations operations,
			DocumentConfiguration lDocumentConfiguration, URL url) {
		FileOperationResponse lFileOperationResponse;
		QName qname = new QName(FILEOP_NAMESPACE, FILEOP_SERVICENAME);
		Service service = Service.create(url, qname);
		FileOperationWS fileOperationWS = service.getPort(FileOperationWS.class);
		//((org.apache.axis.client.Stub) fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
		
		int timeout = lDocumentConfiguration != null ? lDocumentConfiguration.getTimeout() : 300000;
		setTimeout((BindingProvider) fileOperationWS, timeout);
		
		// enable mtom on client
		SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
		binding.setMTOMEnabled(true);

		FileOperationRequest lFileOperationRequest = new FileOperationRequest();
		
		lFileOperationRequest.setFileOperationInput(inputFileOperationType);

		lFileOperationRequest.setOperations(operations);

		lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		return lFileOperationResponse;
	}
	
	private static FileOperationResponse sendCallFileopNoOut(InputFileOperationType inputFileOperationType, Operations operations,
			DocumentConfiguration lDocumentConfiguration, URL url) {
		FileOperationResponse lFileOperationResponse;
		QName qname = new QName(FILEOP_NAMESPACE, FILEOPNOOUT_SERVICENAME);
		Service service = Service.create(url, qname);
		FileOperationNoOutputWS fileOperationWS = service.getPort(FileOperationNoOutputWS.class);
		//((org.apache.axis.client.Stub) fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
		
		int timeout = lDocumentConfiguration != null ? lDocumentConfiguration.getTimeout() : 300000;
		setTimeout((BindingProvider) fileOperationWS, timeout);
		
		// enable mtom on client
		SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
		binding.setMTOMEnabled(true);

		FileOperationRequest lFileOperationRequest = new FileOperationRequest();
		
		lFileOperationRequest.setFileOperationInput(inputFileOperationType);

		lFileOperationRequest.setOperations(operations);

		lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		return lFileOperationResponse;
	}
	
	/**
	 * Sets the timeout for this web service client. Every port created by a JAX-WS can be cast to
	 * BindingProvider.
	 */
	public static void setTimeout(BindingProvider port, int timeout) {
		
		if (port != null) {			
			
			port.getRequestContext().put("com.sun.xml.ws.developer.JAXWSProperties.CONNECT_TIMEOUT", timeout);
			port.getRequestContext().put("com.sun.xml.ws.connect.timeout", timeout);
			port.getRequestContext().put("com.sun.xml.ws.internal.connect.timeout", timeout);		
			port.getRequestContext().put("com.sun.xml.ws.request.timeout", timeout);
			port.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", timeout);	
			
			// We don't want to use proprietary Sun code
			// port.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, timeout);
			// port.getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, timeout);
			
			// Properties for JBoss implementation of JAX-WS
			//port.getRequestContext().put("javax.xml.ws.client.connectionTimeout", timeout);
			//port.getRequestContext().put("javax.xml.ws.client.receiveTimeout", timeout); 										
		}
	}
	
	public static InputFileOperationType buildInputFileOperationType(String fileUrl, String displayFilename) throws Exception {
		InputFileOperationType lInputFileOperationType = new InputFileOperationType();
		InputFile lInputFile = new InputFile();
		
		Boolean callFileOpWithStream = false;
		
		GenericConfigBean lGenericPropertyConfigurator = null;
		
		try {
			lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		}
		catch (Exception e) {}
		
		if(lGenericPropertyConfigurator != null){
			callFileOpWithStream = ((GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator")).getCallFileOpWithStream();
		}		
		
		if(callFileOpWithStream != null && callFileOpWithStream) {
			File file = new File(new URI(fileUrl).getPath());			
			lInputFile.setFileStream(new DataHandler(new FileDataSource(file)));
		} else {
			lInputFile.setFileUrl(fileUrl);			
		}
		lInputFileOperationType.setInputType(lInputFile);
		lInputFileOperationType.setOriginalName(displayFilename);
		return lInputFileOperationType;
	}
	
	public static InputFileOperationType buildInputFileOperationTypeRer(File fileUrl, String displayFilename) throws Exception {

	    InputFileOperationType lInputFileOperationType = new InputFileOperationType();
	    InputFile lInputFile = new InputFile();

	    log.info("File: " + fileUrl.getAbsolutePath());
	    lInputFile.setFileStream(new DataHandler(new FileDataSource(fileUrl)));

	    log.info("lInputFile: " + lInputFile.toString());
	    lInputFileOperationType.setInputType(lInputFile);
	    lInputFileOperationType.setOriginalName(displayFilename);
	    return lInputFileOperationType;
    }
	
	public static FileOperationResponse callFileOperationRer(InputFileOperationType inputFileOperationType, Operations operations) throws Exception {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("document.xml");
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) appContext.getBean("DocumentConfiguration");

		FileOperationResponse lFileOperationResponse = null;
		QName qname;
		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fileOperationWS = (FileOperationWS) service.getPort(FileOperationWS.class);

			int timeout = lDocumentConfiguration != null ? lDocumentConfiguration.getTimeout() : 300000;
			setTimeout((BindingProvider) fileOperationWS, timeout);

			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();

			lFileOperationRequest.setFileOperationInput(inputFileOperationType);

			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw new Exception(String.format("Errore nella chiamata a FileOperation: %s", new Object[] { e.getMessage() }));
		}

		if (lFileOperationResponse == null) {
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");
			return lFileOperationResponse;
		}
		if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				String errors = null;
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage()) {
					if (errors == null) {
						errors = err;
					} else {
						errors = errors + "; " + err;
					}
				}
				throw new Exception(String.format("Errore generico nella chiamata a FileOperation: %s", new Object[] { errors }));
			}
		} else {
			throw new Exception("Errore generico: nessuna risposta da FileOperation");
		}

		return null;
	}

	public static InputFileOperationType buildInputFileOperationType(File file, String displayFilename) throws Exception {
		/*
		InputFileOperationType lInputFileOperationType = new InputFileOperationType();
		InputFile lInputFile = new InputFile();						
		lInputFile.setFileStream(new DataHandler(new FileDataSource(file)));						
		lInputFileOperationType.setInputType(lInputFile);
		lInputFileOperationType.setOriginalName(displayFilename);
		return lInputFileOperationType;
		*/
		
		return buildInputFileOperationType(file.toURI().toString(), displayFilename);
	}	
}
