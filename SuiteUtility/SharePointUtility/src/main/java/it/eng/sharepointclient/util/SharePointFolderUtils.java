package it.eng.sharepointclient.util;

import it.eng.sharepointclient.dws.Dws;
import it.eng.sharepointclient.dws.DwsSoap;
import it.eng.sharepointclient.list.GetListItems;
import it.eng.sharepointclient.list.GetListItemsResponse;
import it.eng.sharepointclient.list.Lists;
import it.eng.sharepointclient.list.ListsSoap;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SharePointFolderUtils extends SharePointUtils{

	private static Logger mLogger = Logger.getLogger( SharePointFolderUtils.class );
			
	public SharePointFolderUtils(String wsdlEndpoint, String serviceName, String serviceNamespace, String username, String password){
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
	
	public void createFolder(String nomeCartella){

		mLogger.info( "ServiceName " + getServiceName() );
		mLogger.info( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.info( "WSDLLocation " + getWsdlLocation() );
		
		Dws service = new Dws(getWsdlLocation(), qname );
		DwsSoap	port = service.getDwsSoap();

		addCredential(port);

		try{
			mLogger.info(nomeCartella);
			String resultCreateFolder = port.createFolder(nomeCartella);
			mLogger.info(resultCreateFolder);
		} catch(Exception e){
			mLogger.error("", e);
		}
	}

}
