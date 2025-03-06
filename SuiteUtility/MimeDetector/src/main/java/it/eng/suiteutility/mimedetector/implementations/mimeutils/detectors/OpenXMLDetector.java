package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class OpenXMLDetector extends MimeDetector {

	private static final String CONTENTTYPEFILE	= "[Content_Types].xml";
	
	private static final String MIMETYPEFILE	= "mimetype.xml";
		
	private static final String OFFICE2007XMLNS	= "http://schemas.openxmlformats.org/package/2006/content-types";
	
	private static final String[] OFFICE2007MIMETYPES = new String[]{
		"application/vnd.openxmlformats-officedocument.wordprocessingml",
		//"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		"application/vnd.openxmlformats-officedocument.spreadsheetml",
		"application/vnd.openxmlformats-officedocument.presentationml.presentation"
		/*,
		"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet"*/
	};
	
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String parseMimeType(ZipFile zipFile, ZipEntry entry){
		 BufferedReader reader = null;
		 String result = null;
		 try {
			reader = new BufferedReader(
			            new InputStreamReader(zipFile.getInputStream(entry)));
			result =  reader.readLine();
		 } catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	private String getOffice2007MimeType(String contentType){
		for (String mime: OFFICE2007MIMETYPES)
			if ( contentType.startsWith(mime))
				return mime;
		return null;
	}
	
	private String parseOffice2007ContentType(ZipFile zipFile, ZipEntry entry) {
		try {
			DocumentBuilder docBuilder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document root = docBuilder.parse(zipFile.getInputStream(entry));
			Element rootElem = root.getDocumentElement();
	/*		NodeList types = rootElem.getElementsByTagName("Types");
			if (types!=null)
				for (int i = 0; i < types.getLength(); ++i) {
					Element type = (Element) types.item(i);
					String xmlns = type.getAttribute("xmlns");
					if (xmlns.equals(OFFICE2007XMLNS)){
						NodeList overrides = type.getElementsByTagName("Override");
						if (overrides != null)
							for (int j = 0; j < overrides.getLength(); ++j){
								Element override = (Element)overrides.item(j);
								String contentType = override.getAttribute("ContentType");
								String mimeType = getOffice2007MimeType(contentType);
								if (mimeType!=null)
									return mimeType;
							}
					}
				}*/
			String xmlns = rootElem.getAttribute("xmlns");
			if (xmlns.equals(OFFICE2007XMLNS)){
				//System.out.println("Nell'if");
				NodeList overrides = rootElem.getElementsByTagName("Override");
				if (overrides != null)
					for (int j = 0; j < overrides.getLength(); ++j){
						Element override = (Element)overrides.item(j);
						String contentType = override.getAttribute("ContentType");
						//System.out.println("" + contentType);
						String mimeType = getOffice2007MimeType(contentType);
						//System.out.println(mimeType);
						if (mimeType!=null){
							if( mimeType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml"))
								mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
							if( mimeType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml"))
								mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
							
							return mimeType;
						}
					}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Collection getMimeTypesFile(File input)
			throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		ZipFile zipFile = null;
		try{
			zipFile = new ZipFile(input);
			String mimeType=null;
			
			ZipEntry openOfficeMimeType = zipFile.getEntry(MIMETYPEFILE);
			if (openOfficeMimeType != null)
				mimeType = parseMimeType(zipFile, openOfficeMimeType);
			//System.out.println("mimeType " + mimeType);
			
			ZipEntry office2007ContentType = zipFile.getEntry(CONTENTTYPEFILE);
			if (office2007ContentType != null)
				mimeType = parseOffice2007ContentType(zipFile, office2007ContentType);
			//System.out.println("mimeType " + mimeType);
			
			
			if (mimeType != null)
				coll.add(new MimeType(mimeType));
		}catch (ZipException e){
			//e.printStackTrace();
		}catch (IOException e) {
			///e.printStackTrace();
		}finally{
			try {
				if( zipFile!=null)
					zipFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return coll;
	}
	
	public static void main(String[] args) {
		File file = new File("C:/Users/Anna Tesauro/Desktop/testDocx - Copia.docx");
		OpenXMLDetector det = new OpenXMLDetector();
		
		det.getMimeTypes(file);
		boolean esitoCancellazione = file.delete();
		System.out.println("esito cancellazione " + esitoCancellazione);
	}

	@Override
	protected Collection getMimeTypesFileName(String arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return getMimeTypes(new File(arg0));
	}

	@Override
	protected Collection getMimeTypesInputStream(InputStream arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesURL(URL arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
