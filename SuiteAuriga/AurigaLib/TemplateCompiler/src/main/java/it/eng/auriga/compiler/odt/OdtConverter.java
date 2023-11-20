/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.w3c.dom.NamedNodeMap;

import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.compiler.utility.TemplateStorageFactory;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ConvertToPdfUtil;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.oomanager.bean.ImageBean;
import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.xslt.OdtToHtml;

/**
 * 
 * @author Cristiano
 *
 */


public class OdtConverter {

	public static final String[] MIME_TYPE_ODT = new String[]{"application/vnd.oasis.opendocument.text","application/zip"};
	public static final String[] MIME_TYPE_DOC = new String[]{"application/msword","application/vnd.openxmlformats"};
	public static final String[] MIME_TYPE_PDF = new String[]{"application/pdf"};

	private static Logger mLogger = Logger.getLogger(OdtConverter.class);

	private static String odtPath;

	public static synchronized boolean isPDFMimeType(String mimetype) {
		if (mimetype == null) {
			return false;
		}else {
			return Arrays.asList(MIME_TYPE_PDF).contains(mimetype);
		}
	}

	public static synchronized boolean isODTMimeType(String mimetype) {
		if (mimetype == null) {
			return false;
		}else {
			return Arrays.asList(MIME_TYPE_ODT).contains(mimetype);
		}
	}

	public static synchronized boolean isDOCMimeType(String mimetype) {
		if (mimetype == null) {
			return false;
		}else {
			return Arrays.asList(MIME_TYPE_DOC).contains(mimetype);
		}
	}

	public static InputStream convertOdtToPdf(File odt) throws Exception{
		TemplateStorage templateStorage = TemplateStorageFactory.getTemplateStorageImpl();
		
		return ConvertToPdfUtil.convertToPdf(templateStorage.store(odt, new String[]{}), true);
	}	
	
	public static InputStream convertOdtToDoc(File odt) throws Exception{
		File doc = File.createTempFile("doc", ".doc");
		try {
			OpenOfficeConverter.configure(SpringAppContext.getContext().getBean(OpenOfficeConfiguration.class));
			OpenOfficeConverter.newInstance().convert(odt, doc);
		} catch (Throwable e) {
			throw new Exception("Errore durante la conversione in pdf del modello odt",e);
		}
		InputStream stream = FileUtils.openInputStream(doc);
		return stream;
	}	

	public static String convertOdtToHtml(File odt) throws Exception{

		File input = File.createTempFile("input", ".odt");	
		mLogger.error("Creato il file " + input.getPath());
		FileUtils.copyFile(odt, input);

		//Recupero le immagini
		OdfPackage pack = null;
		OdfDocument odfTextDocument = null;
		try {
			List<ImageBean> images = new ArrayList<ImageBean>(); 
			pack = OdfPackage.loadPackage(input);  
			odfTextDocument = OdfTextDocument.loadDocument(pack);
			List<OdfDrawImage> immagini = OdfDrawImage.getImages(odfTextDocument); 
			mLogger.error("Le immagini recuperate sono " + immagini.size());
			for(int i=0;i<immagini.size();i++){
				ImageBean tmp = new ImageBean();
				String fileName = StringUtils.remove(immagini.get(i).getImageUri().getPath(),"Pictures/");
				//Salvo e setto l'URI dell'immagine
				byte[] data = pack.getBytes(immagini.get(i).getImageUri().getPath());
				URI uri = addImage(data, fileName);
				tmp.setUri(uri);
				tmp.setData(data);
				immagini.get(i).setImagePath(uri.getPath());

				//Recupero il nome dell'immagine
				NamedNodeMap map = immagini.get(i).getParentNode().getAttributes();
				for(int y=0;y<map.getLength();y++){
					if(map.item(y).getLocalName().equals("name")){
						tmp.setName(map.item(y).getNodeValue());
						break;
					}
				}
				images.add(tmp);
			}
			odfTextDocument.save(input);
		}
		finally{
			if(odfTextDocument!=null){
				odfTextDocument.close();
			}

			if(pack!=null){
				pack.close();
			}        	
		}

		File output = File.createTempFile("test", ".html");
		mLogger.error("Creato il file output " + output.getPath());

		mLogger.error("RealPath vale " + odtPath);
		try { 
			OdtToHtml.convertOdtToHtml(odt, output, odtPath);
		} catch (Throwable e){
			mLogger.error("Errore nella conversione " + e.getMessage(), e);
		}	
//		FileUtils.copyFile(odt, new File("C:\\testOdt\\fileOdtRicevuto.odt"));
		mLogger.error("Copiato il file odt");
		String html = FileUtils.readFileToString(output);

		mLogger.error("Html generato vale " + html);
		input.delete();
		output.delete();

		return html;
	}

	private static URI addImage(byte[] data,String fileName) throws Exception{

		String path = ("/");
		File file = new File(path+"/images/"+fileName);
		FileUtils.writeByteArrayToFile(file, data);

		return new URI("/images/"+fileName);
	}

	public static void setOdtPath(String odtPath) {
		OdtConverter.odtPath = odtPath;
	}

	public static String getOdtPath() {
		return odtPath;
	}
}
