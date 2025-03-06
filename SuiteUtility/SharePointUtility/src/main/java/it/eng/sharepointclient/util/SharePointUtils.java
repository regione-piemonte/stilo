package it.eng.sharepointclient.util;

import it.eng.sharepointclient.copy.CopySoap;
import it.eng.sharepointclient.dws.DwsSoap;
import it.eng.sharepointclient.list.ListsSoap;
import it.eng.sharepointclient.versions.VersionsSoap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SharePointUtils {

	private URL wsdlLocation;
	private String serviceName;
	private String serviceNamespace;
	private String username; 
	private String password; 

	public SharePointUtils(String wsdlEndpoint, String serviceName, String serviceNamespace, String username, String password){
		this.username = username;
		this.password = password;
		this.serviceName = serviceName;
		this.serviceNamespace = serviceNamespace;
		try {
			wsdlLocation = new URL(wsdlEndpoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public ListsSoap addCredential(ListsSoap port){

		if( username!=null && password!=null ){
			((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
			((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
			//((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation );
		} else {
			System.out.println("Credenziali nulle");
		}

		return port;
	}

	public DwsSoap addCredential(DwsSoap port){

		if( username!=null && password!=null ){
			((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
			((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
		//	((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation );
		} else {
			System.out.println("Credenziali nulle");
		}

		return port;
	}

	public CopySoap addCredential(CopySoap port, String username, String password){

		if( username!=null && password!=null ){
			((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
			((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
		} else {
			System.out.println("Credenziali nulle");
		}

		return port;
	}
	
	public VersionsSoap addCredential(VersionsSoap port, String username, String password){

		if( username!=null && password!=null ){
			((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
			((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
		} else {
			System.out.println("Credenziali nulle");
		}

		return port;
	}
	

	protected static byte[] readAll(File file) throws IOException {
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1)
				ous.write(buffer, 0, read);
		} finally {
			try {
				if (ous != null)
					ous.close();
			} finally {
				if (ios != null)
					ios.close();
			}
		}
		return ous.toByteArray();
	}

	public static String xmlToString(Document docToString) {
		String returnString = "";
		try {
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter sw = new StringWriter();
			StreamResult streamResult = new StreamResult(sw);
			DOMSource source = new DOMSource(docToString);
			trans.transform(source, streamResult);
			String xmlString = sw.toString();
			returnString = returnString + xmlString;
		} catch (TransformerException ex) {
			System.out.println(ex.toString());
		}
		return returnString;
	}

	
	
	public Document createBatchElement( String opType, String libraryName){
		Document rootDocument = null;
		Element rootDocContent;
		Element rootElement = null;
		try {
			DocumentBuilder docBuilder = null;
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			docBuilder = dbfac.newDocumentBuilder();
			rootDocument = docBuilder.newDocument();

			//Creates the root element
			rootElement = rootDocument.createElement("Batch");
			rootDocument.appendChild(rootElement);

			//Creates the batch attributes
			//rootElement.setAttribute("ListVersion", "1");
			rootElement.setAttribute("ListName", libraryName);
			rootElement.setAttribute("OnError", "Continue");
			rootDocContent = rootDocument.createElement("Method");
			rootDocContent.setAttribute("Cmd", opType);
			rootDocContent.setAttribute("ID", "1");
			rootDocument.getElementsByTagName("Batch").item(0).appendChild(rootDocContent);
			
			System.out.println( xmlToString(rootDocument) );
		} catch (ParserConfigurationException ex) {
		
		}
		return rootDocument;
	}

	protected static Node generateXmlNode(String theXML) {
		System.out.println("xml: \n" + theXML);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(theXML)));
			Node node = document.getDocumentElement();
			return node;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public URL getWsdlLocation() {
		return wsdlLocation;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceNamespace() {
		return serviceNamespace;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
