package it.eng.client.applet.fileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
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

import com.sun.xml.messaging.saaj.soap.AttachmentPartImpl;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.DigestUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;

public class AurigaFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		
		String aurigaHost=null;
		try {
			aurigaHost = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGAHOST );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGAHOST + ": " + aurigaHost );
        } catch (Exception e) {}
		
		String aurigaPort=null;
		try {
			aurigaPort = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGAPORT );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGAPORT + ": " + aurigaPort );
        } catch (Exception e) {}
		
		String codiceApplicazione=null;
		try {
			codiceApplicazione = PreferenceManager.getString( PreferenceKeys.PROPERTY_CODICEAPPLICAZIONE );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CODICEAPPLICAZIONE + ": " + codiceApplicazione );
        } catch (Exception e) {}
		
		String istanzaApplicazione = null;
		try {
			istanzaApplicazione = PreferenceManager.getString( PreferenceKeys.PROPERTY_CODICEISTANZAAPPLICAZIONE );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CODICEISTANZAAPPLICAZIONE + ": " + istanzaApplicazione );
        } catch (Exception e) {}
		
		String username = null;
		try {
			username = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGAUSERNAME );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGAUSERNAME + ": " + username );
        } catch (Exception e) {}
		
		String password = null;
		try {
			password = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGAPASSWORD );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGAPASSWORD + ": " + password );
        } catch (Exception e) {}
		
		String idDocumento = null;
		try {
			idDocumento = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGAIDDOCUMENTO );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGAIDDOCUMENTO + ": " + idDocumento );
        } catch (Exception e) {}
		String idVersione = null;
		try {
			idVersione = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGAIDVERSIONEDOCUMENTO );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGAIDVERSIONEDOCUMENTO + ": " + idVersione );
        } catch (Exception e) {}
		String numeroAllegato = null;
		try {
			numeroAllegato = PreferenceManager.getString( PreferenceKeys.PROPERTY_AURIGANUMEROALLEGATO );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AURIGANUMEROALLEGATO + ": " + numeroAllegato );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILENAME + ": " + filename );
        } catch (Exception e) {}
		
		if( aurigaHost==null || aurigaPort==null || codiceApplicazione==null 
				|| istanzaApplicazione==null || idDocumento==null || filename==null)
			throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
			
		try {
		
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
		
			SOAPConnection connection = sfc.createConnection();

			SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			soapEnvelope.addNamespaceDeclaration("ext","http://extractone.webservices.jaxws.repository2.auriga.eng.it");
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
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FileUDToExtract>"+
	        "<EstremiXIdentificazioneUD>"+"<IdUD>"+idDocumento+"</IdUD>"+"</EstremiXIdentificazioneUD>" + 
	        "<EstremixIdentificazioneFileUD>";
			if( numeroAllegato!=null && !numeroAllegato.equalsIgnoreCase(""))
				xml +="<NroAllegato>" + numeroAllegato + "</NroAllegato>";
			else 
				xml +="<FlagPrimario>1</FlagPrimario>";
	        if( idVersione!=null && !idVersione.equalsIgnoreCase(""))
	        	xml += "<NroVersione>"+ idVersione + "</NroVersione>";
	        
	        xml += "</EstremixIdentificazioneFileUD>"+
	        "</FileUDToExtract>";
			LogWriter.writeLog("XML " + xml);
			soapElement.addTextNode(xml);
			soapElement = gltp.addChildElement(soapEnvelope.createName("hash"));
			
			String digest_str = DigestUtility.getDigest(new String(xml), DigestAlgID.SHA_1, DigestEncID.BASE_64 );
			LogWriter.writeLog("HASH " + digest_str);
			soapElement.addTextNode(digest_str);
			
			URL endpoint = new URL("http://" + aurigaHost + ":" + aurigaPort + "/aurigarepository/jaxwsservices/WSExtractOne");
			SOAPMessage serviceResponse = connection.call(soapMessage, endpoint);
			String rispostaCodificata=serviceResponse.getSOAPBody().getTextContent();
			byte[] rispostaDedificata = Base64.decodeBase64( rispostaCodificata );
			LogWriter.writeLog("Risposta " + new String(rispostaDedificata));
			int nAtt = serviceResponse.countAttachments();
			LogWriter.writeLog("Numero di attachment:  " + nAtt);
			if( nAtt==0)
				throw new Exception("documento non trovato");
			Iterator itr = serviceResponse.getAttachments();
			int count = 1;
			while(itr.hasNext()){
				AttachmentPartImpl attach = (AttachmentPartImpl) itr.next();
				if(count==2){
					DataHandler dh = attach.getDataHandler();
					LogWriter.writeLog("Creo il file temp " + count + "fileTemp"+".pdf", false );
					File tmp=File.createTempFile(count + "fileTemp", ".pdf");
					FileOutputStream fos = new FileOutputStream(tmp);
					dh.writeTo(fos);
					fos.flush();
					fos.close();
					response = new FileInputResponse();
					FileBean bean = new FileBean();
					bean.setFile( tmp );
					bean.setFileName(filename);
					
					List<FileBean> fileBeanList = new ArrayList<FileBean>() ;
					fileBeanList.add( bean );
					
					response = new FileInputResponse();
					response.setFileBeanList( fileBeanList );
				}
				count++;
			}
			
		} catch (UnsupportedOperationException e) {
			LogWriter.writeLog( "Errore ", e );
		} catch (SOAPException e) {
			LogWriter.writeLog( "Errore ", e );
		} catch (MalformedURLException e) {
			LogWriter.writeLog( "Errore ", e );
		} catch (NoSuchAlgorithmException e) {
			LogWriter.writeLog( "Errore ", e );
		} 
		return response;
	}

	public static void main(String[] args) {
		String xmlEncoded = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iSVNPLTg4NTktMSI/Pgo8QmFzZU91dHB1dF9XUz4KPFdTUmVzdWx0PjA8L1dTUmVzdWx0Pgo8V1NFcnJvcj4KPEVycm9yQ29udGV4dD5ETVBLX1dTLkxlZ2dpRXN0cmVtaVhJZGVudGlmVURUeXBlPC9FcnJvckNvbnRleHQ+CjxFcnJvck51bWJlcj4xMDA0PC9FcnJvck51bWJlcj4KPEVycm9yTWVzc2FnZT5Vbml0w6AgZG9jdW1lbnRhcmlhIGluZXNpc3RlbnRlIG8gbm9uIGlkaXZpZHVhYmlsZSB1bml2b2NhbWVudGUgPC9FcnJvck1lc3NhZ2U+CjwvV1NFcnJvcj4KPC9CYXNlT3V0cHV0X1dTPg==";
		byte[] xml1 = Base64.decodeBase64( xmlEncoded );
		System.out.println(new String(xml1));
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FileUDToExtract>"+
		        "<EstremiXIdentificazioneUD>"+"<IdUD>0000479</IdUD>"+"</EstremiXIdentificazioneUD>" + 
		        "<EstremixIdentificazioneFileUD>"+
		        "<FlagPrimario>1</FlagPrimario>"+
		        //"<NroVersione>1</NroVersione>"+
		        "</EstremixIdentificazioneFileUD>"+
		        "</FileUDToExtract>";
//		String digest = DigestUtility.getDigest(new String(xml), DigestAlgID.SHA_1, DigestEncID.BASE_64 );
//		System.out.println(digest);
		
		java.security.MessageDigest md;
		try {
			md = java.security.MessageDigest.getInstance("SHA");
		
			md.update( xml.getBytes() );
	
			// calcolo lo sha-1
			byte[] digest = md.digest();
			// lo codifico base64
			//it.eng.auriga.repository2.util.Base64 encoder = new it.eng.auriga.repository2.util.Base64();
			//String digest_str = encoder.encode(digest);
			//System.out.println("" + digest_str);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo accessorio che ritorna il valore del flag breakOnWrongPin
	 * @return the breakOnWrongPin
	 */
//	@Override
//	public boolean getBreakOnWrongPin() {
//		
//		/*
//		 * Prelevo il valore del flag breakOnWrongPin che determina il comportamento
//		 * da tenere nel SignerThread
//		 */
//		String breakOnWrongPin = PreferenceManager.getString( PreferenceKeys.PROPERTY_BREAK_ON_WRONG_PIN );
//		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_BREAK_ON_WRONG_PIN + ": " + breakOnWrongPin);
//		if( breakOnWrongPin != null )
//		{
//			if("true".equals(breakOnWrongPin))
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			} 
//		}	
//		else
//		{
//			//Se il parametro non è stato impostato allora si ritorna il valore true che non fa saltare il break
//			return false;
//		}
//	}
}
