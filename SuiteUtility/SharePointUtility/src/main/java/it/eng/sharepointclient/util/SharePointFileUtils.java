package it.eng.sharepointclient.util;

import it.eng.sharepointclient.list.GetListAndViewResponse.GetListAndViewResult;
import it.eng.sharepointclient.list.GetListItems;
import it.eng.sharepointclient.list.GetListItemsResponse;
import it.eng.sharepointclient.list.Lists;
import it.eng.sharepointclient.list.ListsSoap;
import it.eng.sharepointclient.list.UpdateListItems.Updates;
import it.eng.sharepointclient.list.UpdateListItemsResponse.UpdateListItemsResult;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SharePointFileUtils extends SharePointUtils{

	private static Logger mLogger = Logger.getLogger(SharePointFileUtils.class);
	
	public SharePointFileUtils(String wsdlEndpoint, String serviceName, String serviceNamespace, String username, String password){
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
	
	public void deleteFile(String libraryName, String idDocument, String urlFile) throws Exception{
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port);

		Updates updates = new Updates();
		String xml= "<Batch OnError='Continue'" +
				">" +
	             "<Method ID='d1' Cmd='Delete'>" +
	            "<Field Name='ID'>"+idDocument+"</Field>" +
	            "<Field Name='FileRef'>"+ urlFile +"</Field>" +
	            "</Method>" +
	        "</Batch>";
		
		updates.getContent().add( generateXmlNode(xml)); 
		UpdateListItemsResult result = port.updateListItems(libraryName, updates);
		
		Element node = (Element)result.getContent().get(0);
		if ((node != null) && (node instanceof Element)) {  
			//Dumps the retrieved info in the console  
			Document document = node.getOwnerDocument();  
			mLogger.debug( "\n" + xmlToString(document));  
		}

	}
	
	public void createFile(String libraryName, String idDocument, String nomeFile, String title) throws Exception{
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port);

		Updates updates = new Updates();
		String xml= "<Batch OnError='Continue' " +
				">" +
	            "<Method ID='d1' Cmd='New'>" +
	            "<Field Name='Title'>"+title+"</Field>" +
	            "<Field Name='ID'>"+idDocument+"</Field>" +
	            "<Field Name='ADR_Protocolo_Anno'>"+"2010"+"</Field>" +
	            "<Field Name='FileRef'>"+"http://172.28.102.117:300/Lists/ProtocolloAuriga/prova.txt"+"</Field>" +
	            "</Method>" +
	        "</Batch>";
		
		updates.getContent().add( generateXmlNode(xml)); 
		UpdateListItemsResult result = port.updateListItems(libraryName, updates);
		
		Element node = (Element)result.getContent().get(0);
		if ((node != null) && (node instanceof Element)) {  
			//Dumps the retrieved info in the console  
			Document document = node.getOwnerDocument();  
			mLogger.debug( "\n" + xmlToString(document));  
		}

	} 
	
	public void updateFile(String libraryName, String idDocument, String title, HashMap<String, String> metadati) throws Exception{
		mLogger.debug( "updateFile libraryName: " + libraryName + " idDocument: " + idDocument + " title " + title + " metadati " + metadati);
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port);
 
		Updates updates = new Updates();
		String xml = "<Batch OnError='Continue' " +
				">" +
	            "<Method ID='d1' Cmd='Update'>" +
	            "<Field Name='ID'>"+idDocument+"</Field>" ;
		if( title!=null ){
			title = StringEscapeUtils.escapeXml(title);
			xml += "<Field Name='Title'>"+title+"</Field>" ;
		}
		
		Iterator<String> metadatiItr = metadati.keySet().iterator();
		while( metadatiItr.hasNext() ){
			String nomeMetadato = metadatiItr.next();
			String valoreMetadato = metadati.get(nomeMetadato);
			valoreMetadato = StringEscapeUtils.escapeXml(valoreMetadato);
			xml += "<Field Name='"+nomeMetadato+"'>"+valoreMetadato+"</Field>";
		}
		
	    xml += "</Method>" +
	        "</Batch>";
		
		updates.getContent().add( generateXmlNode(xml)); 
		UpdateListItemsResult result = port.updateListItems(libraryName, updates);
		
		Element node = (Element)result.getContent().get(0);
		if ((node != null) && (node instanceof Element)) {  
			//Dumps the retrieved info in the console  
			Document document = node.getOwnerDocument();  
			mLogger.debug( "\n" + xmlToString(document));  
		}

	}

	public void listItemsFolder(String listName) throws Exception {	
		String rowLimit = "150";  
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port); 

		String viewName = "";  
		GetListItems.ViewFields viewFields = null;  
		GetListItems.Query query = null;  
		GetListItems.QueryOptions queryOptions = new GetListItems.QueryOptions();
		String xml="<QueryOptions>"+
				"<IncludeMandatoryColumns>TRUE</IncludeMandatoryColumns>"+
				"<ViewAttributes Scope=\"RecursiveAll\"/>"+ 
				"<DateInUtc>TRUE</DateInUtc>"+ 
				"</QueryOptions>";
		Node xmlQueryOptionNode = generateXmlNode(xml);
		if( xmlQueryOptionNode!=null )
			queryOptions.getContent().add(xmlQueryOptionNode);
		String webID = "";  

		GetListItemsResponse.GetListItemsResult result = port.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);  
		Object listResult = result.getContent().get(0);  
		if ((listResult != null) && (listResult instanceof Element)) {  
			Element node = (Element) listResult;  

			//Dumps the retrieved info in the console  
			Document document = node.getOwnerDocument();  
			mLogger.debug( "\n" + xmlToString(document));  
			
			NodeList nl = node.getElementsByTagName("z:row");
			for(int i = 0; i < nl.getLength(); i++){
				Node nodeItem = nl.item(i);
				mLogger.debug("ID: " + nodeItem.getAttributes().getNamedItem("ows_ID").getNodeValue());
				mLogger.debug("FileRef: " + nodeItem.getAttributes().getNamedItem("ows_FileRef").getNodeValue());
				mLogger.debug("UniqueId: " + nodeItem.getAttributes().getNamedItem("ows_UniqueId").getNodeValue());
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		String wsdlLocation ="http://172.28.102.117:300/_vti_bin/lists.asmx?wsdl";
		String username="vandelli";
		String password="vandelli";
		String serviceName = "Lists";
		String serviceNamespace = "http://schemas.microsoft.com/sharepoint/soap/";
		SharePointFileUtils u = new SharePointFileUtils(wsdlLocation, serviceName, serviceNamespace, username, password);
		
		HashMap<String, String> metadati =new HashMap<String, String>();
		
		metadati.put("ADR_ProtRicevuto_Numero", "");
		metadati.put("ADR_Protocollo_Ufficio","COMMERCIALE & AMMINISTRAZIONE-TEC");
		metadati.put("ADR_Protocolo_Anno","2014-05-26T11:44:00Z");
		metadati.put("ADR_Protocolo_Data","2014-05-26T11:44:00Z");
//		metadati.put("ADR_ProtRicevuto_Anno","");
//		metadati.put("ADR_InConoscenzaA","");
//		metadati.put("ADR_Protocollo_Utente","Passaro Lorenzo");
//		metadati.put("ADR_Protocolo_Numero","41");
//		metadati.put("ADR_Documento_Primario","1");
//		metadati.put("ADR_Classificazione","");
//		metadati.put("ADR_ProtRicevuto_Rif_Originale","");
//		metadati.put("ADR_Annullamento_DataOra","2014-05-26T11:44:00Z");
//		metadati.put("ADR_Destinatari","123");
//		metadati.put("ADR_Societa","ADR TEL");
//		metadati.put("ADR_Mezzo_Trasmissione","");
//		metadati.put("ADR_Annullamento_EffettuatoDa","");
//		metadati.put("ADR_Riservatezza","");
//		metadati.put("ADR_Mittenti","ADR TEL");
//		metadati.put("ADR_Note","");
//		metadati.put("ADR_Tipologia","");
//		metadati.put("ADR_Annullamento_AutorizzatoConAtto","");
//		metadati.put("ADR_Firmato_Digitalmente","0");
//		metadati.put("ADR_Oggetto_Registrazione","456");
//		metadati.put("ADR_FirmatoDa","");
//		metadati.put("ADR_Protocolo_Tipo","USCITA");
		

		//u.getIdDocument("ProtocolloAuriga", "ProtocolloAuriga/prova1.txt");
		try {
			u.updateFile("ProtocolloAuriga", "10", null, metadati);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getIdDocument(String listName, String fileRef) {	
		mLogger.debug( "getIdDocument listName:" + listName + " fileRef " + fileRef );
		String rowLimit = "150";  
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port); 

		String viewName = "";  
		GetListItems.ViewFields viewFields = new GetListItems.ViewFields();
		String xmlFields = "<ViewFields><FieldRef Name='ID' /></ViewFields>";
		Node xmlFieldNode = generateXmlNode(xmlFields);
		if( xmlFieldNode!=null)
			viewFields.getContent().add( xmlFieldNode );
		
		GetListItems.Query query = new GetListItems.Query(); 
		String xmlQuery = "<Query><Where>"
				+ "<Eq><FieldRef Name='FileRef' /><Value Type='Text'>"+ fileRef +"</Value></Eq>"
				+ "</Where></Query>";  
		Node xmlQueryNode = generateXmlNode(xmlQuery);
		if( xmlQueryNode!=null )
			query.getContent().add( xmlQueryNode );
		
		String xml="<QueryOptions>"+
				"<IncludeMandatoryColumns>TRUE</IncludeMandatoryColumns>"+
				"<ViewAttributes Scope=\"RecursiveAll\"/>"+ 
				"<DateInUtc>TRUE</DateInUtc>"+ 
				"</QueryOptions>";
		GetListItems.QueryOptions queryOptions = new GetListItems.QueryOptions();
		Node queryOptionNode = generateXmlNode(xml);
		if( queryOptionNode!=null )
			queryOptions.getContent().add( queryOptionNode);
		String webID = "";  

		GetListItemsResponse.GetListItemsResult result = port.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);  
		
		Object listResult = result.getContent().get(0);  
		if ((listResult != null) && (listResult instanceof Element)) {  
			Element node = (Element) listResult;  

			Document document = node.getOwnerDocument();  
			mLogger.debug( "\n" + xmlToString(document));  
			
			NodeList nl = node.getElementsByTagName("z:row");
			for(int i = 0; i < nl.getLength(); i++){
				Node node1 = nl.item(i);
				mLogger.debug("ID: " + node1.getAttributes().getNamedItem("ows_ID").getNodeValue());
				return node1.getAttributes().getNamedItem("ows_ID").getNodeValue();
			}
		}
		return null;
	}
	
	public void listAndViewItems(String listName) throws Exception {	
		String rowLimit = "150";  
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port); 

		
		//Calling the List Web Service  
		GetListAndViewResult result = port.getListAndView(listName, "");  
		Element node = (Element)result.getContent().get(0);
		
		if ((node != null) && (node instanceof Element)) {  
			//Dumps the retrieved info in the console  
			Document document = node.getOwnerDocument();  
			mLogger.debug( "\n" + xmlToString(document));  
		
			mLogger.debug("\n");
			NodeList nl = node.getChildNodes();
			for(int i = 0; i < nl.getLength(); i++){
				Node node1 = nl.item(i);
				mLogger.debug("name: " + node1.getNodeValue());
			}
		}
	}

	public boolean checkOutFile( String url) {
		mLogger.debug( "ServiceName " + getServiceName() );
		mLogger.debug( "ServiceNamespace " + getServiceNamespace() );
		QName qname = new QName(getServiceNamespace(), getServiceName());
		mLogger.debug( "WSDLLocation " + getWsdlLocation() );
		Lists service = new Lists(getWsdlLocation(), qname);
		ListsSoap port = service.getListsSoap();

		addCredential(port); 
		
		String checkoutToLocal = "true";
		String lastModified    = "";
		boolean result = port.checkOutFile(url, checkoutToLocal, lastModified);
		mLogger.debug("Check-out result = " + result);
		return result;
	}
	

	
}
