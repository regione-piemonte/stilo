package it.eng.sharepointclient.util;

import it.eng.sharepointclient.copy.Copy;
import it.eng.sharepointclient.copy.CopyErrorCode;
import it.eng.sharepointclient.copy.CopyResult;
import it.eng.sharepointclient.copy.CopyResultCollection;
import it.eng.sharepointclient.copy.CopySoap;
import it.eng.sharepointclient.copy.DestinationUrlCollection;
import it.eng.sharepointclient.copy.FieldInformation;
import it.eng.sharepointclient.copy.FieldInformationCollection;
import it.eng.sharepointclient.copy.FieldType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class SharePointUploadFileUtils extends SharePointUtils{

	private static Logger logger = Logger.getLogger( SharePointUploadFileUtils.class );
			
	public SharePointUploadFileUtils(String wsdlEndpoint, String serviceName, String serviceNamespace, String username, String password){
		super(wsdlEndpoint, serviceName, serviceNamespace, username, password);
		
		Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        getUsername(),
                        getPassword().toCharArray());
            }
        });
	}
	
	public void uploadFile(String sourceUrl, String destinationUrl) throws Exception{
		File fileSource = new File(sourceUrl);
		uploadFile(fileSource, destinationUrl);
	}
	
	public void uploadFile(File fileSource, String destinationUrl) throws Exception{
		 
		logger.info( "ServiceName " + getServiceName() );
		logger.info( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		logger.info( "WSDLLocation " + getWsdlLocation() );
		Copy service = new Copy( getWsdlLocation(), qname);
		CopySoap port = service.getCopySoap();

		addCredential(port, getUsername(), getPassword());
	
		logger.info("Uploading: " + fileSource.getName());

		DestinationUrlCollection destinationUrlCollection = new DestinationUrlCollection();
		destinationUrlCollection.getString().add(destinationUrl);

		FieldInformationCollection fields = new FieldInformationCollection();
//		FieldInformation field = new FieldInformation();
//		field.setValue("Prova");
//		field.setType( FieldType.TEXT);
//		field.setDisplayName("Title");                              
//		fields.getFieldInformation().add(field );
		
		CopyResultCollection results = new CopyResultCollection();
		Holder<CopyResultCollection> resultHolder = new Holder<CopyResultCollection>(results);
		Holder<Long> longHolder = new Holder<Long>(new Long(-1));

		//make the call to upload
		try {
			port.copyIntoItems(fileSource.getAbsolutePath(), destinationUrlCollection, fields, 
					readAll(fileSource), longHolder,resultHolder);
			
			//does not seem to change based on different CopyResults
			
			//do something meaningful here
			for (CopyResult copyResult : resultHolder.value.getCopyResult()) {				
				logger.info("Destination: " + copyResult.getDestinationUrl());
				logger.info("Error Message: " + copyResult.getErrorMessage());
				logger.info("Error Code: " + copyResult.getErrorCode());
				if(copyResult.getErrorCode() != CopyErrorCode.SUCCESS){
					logger.info("Upload failed for: " + copyResult.getDestinationUrl() + " Message: " 
							+ copyResult.getErrorMessage() + " Code: " +   copyResult.getErrorCode() );
					throw new Exception(""+copyResult.getErrorCode());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("", e);
		}
	}
	
	public byte[] getItem(String pathFile){
		 
		logger.info( "ServiceName " + getServiceName() );
		logger.info( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		logger.info( "WSDLLocation " + getWsdlLocation() );
		Copy service = new Copy( getWsdlLocation(), qname);
		CopySoap port = service.getCopySoap();

		addCredential(port, getUsername(), getPassword());
	
		Holder<Long> h =  new Holder<Long>();
		
		Holder<FieldInformationCollection> hf =  new Holder<FieldInformationCollection>();
		
		byte[] b = new byte[14016];
        Holder<byte[]> d = new Holder<byte[]>(b);
        
    	logger.info("get " + pathFile );
    	port.getItem(pathFile,h,hf,d );
	
    	if(d.value != null){
    		return d.value;
        } else{
            logger.info("document does not exists .....");
        }
		
		return null;

	}
	
	public byte[] getItemVersion(String pathFile) throws IOException{
		URL url = new URL(String.format(pathFile));
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		InputStream in = connection.getInputStream();
		return IOUtils.toByteArray(in);
	}
	
	
}
