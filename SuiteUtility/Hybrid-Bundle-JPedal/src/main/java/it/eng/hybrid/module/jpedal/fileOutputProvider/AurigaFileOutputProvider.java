package it.eng.hybrid.module.jpedal.fileOutputProvider;

import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.util.DigestUtility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sun.xml.messaging.saaj.soap.AttachmentPartImpl;


public class AurigaFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(AurigaFileOutputProvider.class);
	
	@Override
	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception {
		logger.info("Metodo saveOutputFile");
		String aurigaHost=null;
		try {
			aurigaHost = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_AURIGAHOST );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_AURIGAHOST + ": " + aurigaHost );
        } catch (Exception e) {}
		
		String aurigaPort=null;
		try {
			aurigaPort = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_AURIGAPORT );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_AURIGAPORT + ": " + aurigaPort );
        } catch (Exception e) {}
		
		String codiceApplicazione=null;
		try {
			codiceApplicazione = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CODICEAPPLICAZIONE );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_CODICEAPPLICAZIONE + ": " + codiceApplicazione );
        } catch (Exception e) {}
		
		String istanzaApplicazione = null;
		try {
			istanzaApplicazione = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CODICEISTANZAAPPLICAZIONE );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_CODICEISTANZAAPPLICAZIONE + ": " + istanzaApplicazione );
        } catch (Exception e) {}
		
		String username = null;
		try {
			username = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_AURIGAUSERNAME );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_AURIGAUSERNAME + ": " + username );
        } catch (Exception e) {}
		
		String password = null;
		try {
			password = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_AURIGAPASSWORD );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_AURIGAPASSWORD + ": " + password );
        } catch (Exception e) {}
		
		if( aurigaHost==null || aurigaPort==null || codiceApplicazione==null || istanzaApplicazione==null)
			throw new Exception( Messages.getMessage( MessageConstants.MSG_MISSINGPARAMETERS ) );
		
		try {
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			
			SOAPConnection connection = sfc.createConnection();
			SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			soapEnvelope.addNamespaceDeclaration("ext","http://addunitadoc.webservices.repository2.auriga.eng.it");
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			
			SOAPBody soapBody = soapEnvelope.getBody();
			Name bodyName = soapEnvelope.createName("ext:service");
			
			SOAPBodyElement gltp = soapBody.addBodyElement(bodyName);
			SOAPElement soapElement = gltp.addChildElement(soapEnvelope.createName("codApplicazione"));
			if( codiceApplicazione!=null )
				soapElement.addTextNode( codiceApplicazione );
			soapElement = gltp.addChildElement(soapEnvelope.createName("istanzaApplicazione"));
			if( istanzaApplicazione!=null )
				soapElement.addTextNode( istanzaApplicazione );
			soapElement = gltp.addChildElement(soapEnvelope.createName("userName"));
			if( username!=null )
				soapElement.addTextNode( username );
			else 
				soapElement.addTextNode( "" );
			soapElement = gltp.addChildElement(soapEnvelope.createName("password"));
			soapElement.addTextNode( password );
			soapElement = gltp.addChildElement(soapEnvelope.createName("xml"));
			
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NewUD>"+
	        
	        "</NewUD>";
			logger.info("XML " + xml);
			soapElement.addTextNode(xml);
			soapElement = gltp.addChildElement(soapEnvelope.createName("hash"));
			
			String digest_str = DigestUtility.getDigest(new String(xml), DigestAlgID.SHA_1, DigestEncID.BASE_64 );
			logger.info("HASH " + digest_str);
			soapElement.addTextNode(digest_str);
			
			logger.info("Creo il file fileTemp");
			File tmpOut=File.createTempFile("fileTemp", ".pdf");
			byte[] contentFile = IOUtils.toByteArray(in);
			FileOutputStream out = FileUtils.openOutputStream(tmpOut);
			IOUtils.copy(new ByteArrayInputStream(contentFile), out );
			out.flush();
			out.close();
			soapMessage.addAttachmentPart(soapMessage.createAttachmentPart(new DataHandler(new FileDataSource(tmpOut))));
					
			URL endpoint = new URL("http://" + aurigaHost + ":" + aurigaPort + "/aurigarepository/jaxwsservices/WSAddUd");
			SOAPMessage serviceResponse = connection.call(soapMessage, endpoint);
			String rispostaCodificata=serviceResponse.getSOAPBody().getTextContent();
			byte[] rispostaDedificata = Base64.decodeBase64( rispostaCodificata );
			logger.info("Risposta " + new String(rispostaDedificata));
			
			int nAtt = serviceResponse.countAttachments();
			logger.info("Numero di attachment:  " + nAtt);
			if( nAtt==0)
				throw new Exception( "documento non trovato");
			Iterator itr = serviceResponse.getAttachments();
			int count = 1;
			while(itr.hasNext()){
				AttachmentPartImpl attach = (AttachmentPartImpl) itr.next();
				//if(count==1){
					DataHandler dh = attach.getDataHandler();
					logger.info("Creo il file temp " + count + "fileTemp"+".pdf" );
					File tmp=File.createTempFile(count + "fileTemp", ".pdf");
					FileOutputStream fos = new FileOutputStream(tmp);
					dh.writeTo(fos);
					fos.flush();
					fos.close();
					
				//}
				count++;
			}
		} catch (UnsupportedOperationException e) {
			logger.info( "Errore ", e );
		} catch (SOAPException e) {
			logger.info( "Errore ", e );
		} catch (MalformedURLException e) {
			logger.info( "Errore ", e );
		} catch (NoSuchAlgorithmException e) {
			logger.info( "Errore ", e );
		} catch (IOException e) {
			logger.info( "Errore ", e );
		}
	}

	@Override
	public void saveOutputParameter(PreferenceManager preferenceManager) throws Exception {
		
	}

	@Override
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName,
			PreferenceManager preferenceManager) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	
}
