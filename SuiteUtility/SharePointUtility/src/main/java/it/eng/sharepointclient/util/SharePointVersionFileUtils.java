package it.eng.sharepointclient.util;

import it.eng.sharepointclient.versions.GetVersionsResponse.GetVersionsResult;
import it.eng.sharepointclient.versions.RestoreVersionResponse.RestoreVersionResult;
import it.eng.sharepointclient.versions.Versions;
import it.eng.sharepointclient.versions.VersionsSoap;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SharePointVersionFileUtils extends SharePointUtils{

	private static Logger logger = Logger.getLogger( SharePointVersionFileUtils.class );
			
	public SharePointVersionFileUtils(String wsdlEndpoint, String serviceName, String serviceNamespace, String username, String password){
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
	
	public LinkedHashMap<String,String> getVersions(String fileUrl){
		LinkedHashMap<String,String> mappaVersioni = new LinkedHashMap<String, String>();
		
		logger.info( "ServiceName " + getServiceName() );
		logger.info( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		logger.info( "WSDLLocation " + getWsdlLocation() );
		Versions service = new Versions(getWsdlLocation(), qname);
		
		VersionsSoap port = service.getVersionsSoap();

		addCredential(port, getUsername(), getPassword());
		
		try{
			GetVersionsResult result = port.getVersions(fileUrl);
			
			Object listResult = result.getContent().get(0);  
			if ((listResult != null) && (listResult instanceof Element)) {  
				Element node = (Element) listResult;  
	
				//Dumps the retrieved info in the console  
				Document document = node.getOwnerDocument();  
				logger.info( "\n" + xmlToString(document));  
				
				NodeList nl = node.getElementsByTagName("result");
				for(int i = 0; i < nl.getLength(); i++){
					Node nodeItem = nl.item(i);
					logger.info("url: " + nodeItem.getAttributes().getNamedItem("url").getNodeValue()
							+ " versione " + nodeItem.getAttributes().getNamedItem("version").getNodeValue());
					mappaVersioni.put(nodeItem.getAttributes().getNamedItem("version").getNodeValue(), nodeItem.getAttributes().getNamedItem("url").getNodeValue());
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("", e);
		}
		
		return mappaVersioni;
	}
	
	public void restoreVersion(String fileUrl, String version){
		logger.info( "ServiceName " + getServiceName() );
		logger.info( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		logger.info( "WSDLLocation " + getWsdlLocation() );
		Versions service = new Versions(getWsdlLocation(), qname);
		
		VersionsSoap port = service.getVersionsSoap();

		addCredential(port, getUsername(), getPassword());
		
		RestoreVersionResult result = port.restoreVersion(fileUrl, version);
		
		Object listResult = result.getContent().get(0);  
		if ((listResult != null) && (listResult instanceof Element)) {  
			Element node = (Element) listResult;  

			//Dumps the retrieved info in the console  
			Document document = node.getOwnerDocument();  
			logger.info( "\n" + xmlToString(document));  
			
//			NodeList nl = node.getElementsByTagName("result");
//			for(int i = 0; i < nl.getLength(); i++){
//				Node nodeItem = nl.item(i);
//				logger.info("url: " + nodeItem.getAttributes().getNamedItem("url").getNodeValue()
//						+ " versione " + nodeItem.getAttributes().getNamedItem("version").getNodeValue());
//				
//			}
		}
	}
	
	public void getVersion(String fileUrl){
		logger.info( "ServiceName " + getServiceName() );
		logger.info( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		logger.info( "WSDLLocation " + getWsdlLocation() );
		Versions service = new Versions(getWsdlLocation(), qname);
		
		VersionsSoap port = service.getVersionsSoap();

		addCredential(port, getUsername(), getPassword());
		
		
	}
	
	public static void main(String[] args) {
		String wsdlLocation ="http://172.28.102.117:300/_vti_bin/versions.asmx?wsdl";
		String username="vandelli";
		String password="vandelli";
		String serviceName = "Versions";
		String serviceNamespace = "http://schemas.microsoft.com/sharepoint/soap/";
		SharePointVersionFileUtils u = new SharePointVersionFileUtils(wsdlLocation, serviceName, serviceNamespace, username, password);
		HashMap<String, String> mapVersion = u.getVersions("ProtocolloAuriga/0010/2014/05/22/Prot__22_05_2014_0000336.pdf");
		logger.info(  mapVersion);
		Iterator<String> versioni = mapVersion.keySet().iterator();
		List<String> uri = new ArrayList<String>();
		
		while( versioni.hasNext() ){
			String versione = versioni.next();
			if( !versione.startsWith("@")){
				String uriFile = mapVersion.get( versione );
				uri.add( uriFile.substring( "http://172.28.102.117:300/".length() ));
			}
		}
		logger.info(uri);
		//u.restoreVersion("ProtocolloAuriga/0010/2014/provaAnna.txt", "0.1");
	}
}
