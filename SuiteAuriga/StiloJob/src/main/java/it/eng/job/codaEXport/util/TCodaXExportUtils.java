/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.eng.database.dao.TCodaXExportDAOImpl;
import it.eng.database.dao.TParametersDAOImpl;
import it.eng.entity.TCodaXExport;
import it.eng.job.codaEXport.types.generated.Files;
import it.eng.job.codaEXport.types.generated.atticoto.Atto;
import it.eng.job.codaEXport.types.generated.determina.Determina;
import it.eng.job.codaEXport.types.generated.determina.NuovaDetermina;
import it.eng.mail.bean.MailAddress;
//import it.eng.mail.bean.SimpleMailSender;

public class TCodaXExportUtils {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private static final String CLASS_NAME = TCodaXExportUtils.class.getName();
	TCodaXExportDAOImpl tCodaXExportDAO = new TCodaXExportDAOImpl();
	TParametersDAOImpl tParametersDAOImpl = new TParametersDAOImpl();
	//SimpleMailSender simpleMailSender = new SimpleMailSender();

	public TCodaXExportUtils() {
		super();
	}

	public TCodaXExport caricaFile(String JOB_CLASS_NAME, MailAddress indirizziMail, TCodaXExport dip) {

		try {

			dip.setIdOggetto(new BigDecimal(tCodaXExportDAO.getNextValue(JOB_CLASS_NAME)));
			dip.setTipoOggetto("XML_METADATI");
			dip.setNumTry(new BigDecimal("1"));

			File fXmlFile = new File("C:\\Downloads\\EXPORT_CODA\\file\\input\\GestioneOrdini-soapui-project.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			Timestamp dataIns = new Timestamp(Calendar.getInstance().getTime().getTime());
			dip.setTsLastTry(dataIns);
			dip.setExpPath("C:\\Downloads\\EXPORT_CODA\\file\\output\\" + dip.getFruitore() + "\\");
			dip.setExpFilename(dip.getIdOggetto() + "_" + dip.getFruitore() + "_" + fXmlFile.getName());
			dip = tCodaXExportDAO.save(JOB_CLASS_NAME, indirizziMail, dip, writeXmlDocumentToXmlFile(doc));
			//simpleMailSender.mailCaricamento(JOB_CLASS_NAME,indirizziMail, dip, fXmlFile);

		} catch (Exception e) {
			//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME, indirizziMail, dip, e.getMessage());
			logger.error("elabora - exc:" + e.getMessage());
		}
		return dip;
	}
	private String clobToString(java.sql.Clob data)
	{
	    final StringBuilder sb = new StringBuilder();
	    Reader         reader = null;
	    BufferedReader br   = null;
	    try
	    {
	        reader = data.getCharacterStream();
	        br     = new BufferedReader(reader);

	        int b;
	        while(-1 != (b = br.read()))
	        {
	            sb.append((char)b);
	        }

	        br.close();
	    }
	    catch (SQLException e)
	    {
	    	logger.error("SQL. Could not convert CLOB to string",e);
	        return e.toString();
	    }
	    catch (IOException e)
	    {
	    	logger.error("IO. Could not convert CLOB to string",e);
	        return e.toString();
	    }finally
	    {
	    	try {
				reader.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("IO. Could not convert CLOB to string finally",e);
			}
	    	
	    }

	    return sb.toString();
	}
	public TCodaXExport esportaFile(String JOB_CLASS_NAME, MailAddress indirizziMail, TCodaXExport rec) throws SerialException, SQLException {

		logger.info(CLASS_NAME + " " + "esportaFile" + " " + "INIZIO");
		FileWriter fileWriter = null;
		try {
			if (rec.getTipoOggetto().equals("XML_METADATI")) {

				Clob clob = rec.getOggDaEsportare();
				//Reader reader = clob.getCharacterStream();
				
				String appClob = clobToString(clob);
				logger.info(CLASS_NAME + " " + "appClob before replace: " + appClob);
			//	String appClobRe = appClob.replaceAll("&", "e");
			//	String appClobRe1 = appClobRe.replaceAll("eapos;", "&apos;");
			//	logger.info(CLASS_NAME + " " + "appClobRe1 after replace: " + appClobRe1);
			//	String appClobRe2 = appClobRe1.replaceAll("eapos;", "&apos;");
			//	logger.info(CLASS_NAME + " " + "appClobRe1 after replace: " + appClobRe1);
				Reader reader = new StringReader(appClob);
				logger.info(CLASS_NAME + " " + "verificaDirectory:" + " " + verificaDirectory(rec.getExpPath()));
				logger.info(CLASS_NAME + " " + "file" + rec.getExpPath() + rec.getExpFilename());
				
					  fileWriter = new FileWriter(rec.getExpPath() + rec.getExpFilename());
						int i;
						while ((i = reader.read()) != -1) {
							fileWriter.write((char) i);
						}
						logger.info("File retrieved successfully.");
						logger.debug("Id. di altro oggetto : " + rec.getIdOggetto() + " esito: " + rec.getEsitoElab() + " file: "
								+ fileWriter.toString());
						fileWriter.close();
				  
				String uuidFile ="";
				/*if (rec.getFruitore().equals("ATTICOTO")) {
					uuidFile = renameMd5Coto(rec.getExpPath(), rec.getExpFilename());
				} else {
					if (rec.getFruitore().equals("BDC")) {
						logger.info("BDC no md5");
					} else {
						if (rec.getFruitore().equals("DECRETI")) {
							uuidFile = renameMd5Decreti(rec.getExpPath(), rec.getExpFilename());
						} else {
							uuidFile = renameMd5(rec.getExpPath(), rec.getExpFilename());
						}
					}// chiudo else BDC
				}//Chiudo else
*/				
				 String fruitore=rec.getFruitore();
				 logger.info("fruitore: "+fruitore);
				 
			     switch (fruitore) {
			         case "ATTICOTO":
			        	 uuidFile = renameMd5Coto(rec.getExpPath(), rec.getExpFilename());
			             break;
			         case "BDC":
			        	 logger.info("BDC no md5");
			        	 break;
			         case "DECRETI":
			        	 uuidFile = renameMd5Decreti(rec.getExpPath(), rec.getExpFilename());
			        	 break;
			         case "DELIBERE":
			        	 uuidFile = renameMd5Delibere(rec.getExpPath(), rec.getExpFilename());
			             break;
			         default:
			        	 uuidFile = renameMd5(rec.getExpPath(), rec.getExpFilename());
			     }
				
				logger.debug("uuidFile : " + uuidFile);

			} else {
				JaxbUtil JaxbUtil = new JaxbUtil(Files.class);
				Clob clob = rec.getOggDaEsportare();
				Reader reader1 = clob.getCharacterStream();
				int j = 0;
				StringBuffer buffer = new StringBuffer();
				int ch;
				while ((ch = reader1.read()) != -1) {
					buffer.append("" + (char) ch);
				}
				logger.debug("clob: " + buffer.toString());
				j++;
				reader1.close();

				Files objFiles = (Files) JaxbUtil.unmarshal(buffer.toString(), Files.class, "ListaFile.xsd");
				if (objFiles == null) {
					logger.info("File : " + rec.getIdOggetto() + "objFiles: null");
				} else {
					for (it.eng.job.codaEXport.types.generated.File fp : objFiles.getFile()) {
						logger.debug(CLASS_NAME + " " + "verificaDirectory:" + " " + verificaDirectory(fp.getPath()));
						InputStream inputStream = new URL(fp.getUri()).openStream();
						Path targetDir = Paths.get(fp.getPath());
						Path targetFile = targetDir.resolve(fp.getNome());
						
						CopyFiles cp =  new CopyFiles();
						long app=cp.copiaStream(inputStream, targetFile);
						
						logger.info("File retrieved successfully. ver 1.0");
						logger.debug("Id. di altro oggetto : " + rec.getIdOggetto());
					} // chiudo for
				} // chiudo else
			} // chiudo else
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String sStackTrace = sw.toString();
			//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME, indirizziMail, rec, sStackTrace);
			rec.setErrMsg(new javax.sql.rowset.serial.SerialClob(sStackTrace.toCharArray()));
			rec.setEsitoElab("KO");
			logger.error("Id. di altro oggetto : " + rec.getIdOggetto() + " esito:" + rec.getEsitoElab());
			return rec;
		}
		logger.info(CLASS_NAME + " " + "esportaFile" + " " + "FINE");
		rec.setEsitoElab("OK");
		return rec;
	}

	public String renameMd5(String pathF,String nomeFile) throws Exception
	{
		String content="";
		String path=pathF+nomeFile;
		File app = new File(path);
		content = org.apache.commons.io.FileUtils.readFileToString(app, java.nio.charset.StandardCharsets.UTF_8.toString());
		
	    if(content.contains("$MD5$"))
	    {
	    	String md5 = calcMD5HashForInputStream(FileUtils.openInputStream(app));
	    	logger.debug("md5: "+md5);
	        JaxbUtil JaxbUtil1 = new JaxbUtil(NuovaDetermina.class);
	        NuovaDetermina nuovaDetermina = (NuovaDetermina) JaxbUtil1.unmarshal(content, NuovaDetermina.class, "schema_determina_0310.xsd");
	        logger.debug("determina: "+nuovaDetermina.getDetermina());
	        Determina determina = nuovaDetermina.getDetermina();
	        String anno = determina.getChiaveDetermina().getAnno().toString();
	        logger.debug("anno: "+anno);
	        String numDeter = determina.getChiaveDetermina().getNumDeterm().toString();
	        logger.debug("numDeter: "+numDeter);
	        String codDir = determina.getChiaveDetermina().getCodDir();
	        logger.debug("codDir: "+codDir);
	        
	        String ext = FilenameUtils.getExtension(determina.getTesto().getNomeFile());
	        String uuidFile = anno+"_"+numDeter+"_"+codDir+"_"+md5+"."+ext;
	        logger.debug("uuidFile: "+uuidFile);
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
			Document document = builder.parse(new InputSource(new FileReader(
					app
			        )));
			document.getDocumentElement().normalize();
			
			XPath xPath =  XPathFactory.newInstance().newXPath();
	        
			NodeList users = document.getElementsByTagName("testo");
	        Element user = null;
	        // loop for each user
	        for (int i = 0; i < users.getLength(); i++) {
	            user = (Element) users.item(i);
	            Node name = user.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            
	            File f = new File(pathF+"testo"+File.separator +name.getNodeValue());
	            File g = new File(pathF+"testo"+File.separator +nom+"_"+md5+"."+ext1);
	            f.renameTo(g);
	            name.setNodeValue(nom+"_"+md5+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        NodeList users1 = document.getElementsByTagName("allegati");
	        Element user1 = null;
	        // loop for each user
	        for (int i = 0; i < users1.getLength(); i++) {
	            user1 = (Element) users1.item(i);
	            Node name = user1.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user1.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            File f1 = new File(pathF+"allegati"+File.separator +name.getNodeValue());
	            InputStream stream = FileUtils.openInputStream(f1);
	            String md5All = calcMD5HashForInputStream(stream);
	            stream.close();
	            File g1 = new File(pathF+"allegati"+File.separator +anno+"_"+numDeter+"_"+codDir+"_"+md5All+"."+ext1);
	            f1.renameTo(g1);
	            name.setNodeValue(anno+"_"+numDeter+"_"+codDir+"_"+md5All+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        // 4- Save the result to a new XML doc
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(new DOMSource(document), new StreamResult(new File(path)));
	        
	        String output = org.apache.commons.io.FileUtils.readFileToString(new File(path), java.nio.charset.StandardCharsets.UTF_8.toString());
	        
	        logger.debug("output: " +output);
	       return output;
	    }
		
       return content;
	}
	public String renameMd5Decreti(String pathF,String nomeFile) throws Exception
	{
		String content="";
		String path=pathF+nomeFile;
		File app = new File(path);
		content = org.apache.commons.io.FileUtils.readFileToString(app, java.nio.charset.StandardCharsets.UTF_8.toString());
		
	    if(content.contains("$MD5$"))
	    {
	    	String md5 = calcMD5HashForInputStream(FileUtils.openInputStream(app));
	    	logger.debug("md5: "+md5);
	      //  JaxbUtil JaxbUtil1 = new JaxbUtil(Atto.class);
	      //  Atto atto = (Atto) JaxbUtil1.unmarshal(content, Atto.class, "ExportAttiCOTO.xsd");
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
			Document document = builder.parse(new InputSource(new FileReader(
					app
			        )));
			document.getDocumentElement().normalize();

			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			Element userElement = (Element) xpath.evaluate("/Decreto/chiaveDecreto", document, XPathConstants.NODE);

			String anno = userElement.getAttribute("anno"); // atto.getAnnoProvv().toString();
			logger.debug("anno: " + anno);
			String numDeter = userElement.getAttribute("numDecreto");
			logger.debug("numDeter: " + numDeter);

			String ext = FilenameUtils.getExtension(path);
			String uuidFile = anno + "_" + numDeter + "_" + md5 + "." + ext;
			logger.debug("uuidFile: " + uuidFile);
	        
			NodeList users = document.getElementsByTagName("testo");
	        Element user = null;
	        // loop for each user
	        for (int i = 0; i < users.getLength(); i++) {
	            user = (Element) users.item(i);
	            Node name = user.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            
	            File f = new File(pathF+"testo"+File.separator +name.getNodeValue());
	            File g = new File(pathF+"testo"+File.separator +nom+"_"+md5+"."+ext1);
	            f.renameTo(g);
	            name.setNodeValue(nom+"_"+md5+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        NodeList users1 = document.getElementsByTagName("allegati");
	        Element user1 = null;
	        // loop for each user
	        for (int i = 0; i < users1.getLength(); i++) {
	            user1 = (Element) users1.item(i);
	            Node name = user1.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user1.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            File f1 = new File(pathF+"allegati"+File.separator +name.getNodeValue());
	            InputStream stream = FileUtils.openInputStream(f1);
	            String md5All = calcMD5HashForInputStream(stream);
	            stream.close();
	            File g1 = new File(pathF+"allegati"+File.separator +anno+"_"+numDeter+"_"+md5All+"."+ext1);
	            f1.renameTo(g1);
	            name.setNodeValue(anno+"_"+numDeter+"_"+md5All+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        // 4- Save the result to a new XML doc
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(new DOMSource(document), new StreamResult(new File(path)));
	        
	        String output = org.apache.commons.io.FileUtils.readFileToString(new File(path), java.nio.charset.StandardCharsets.UTF_8.toString());
	        
	        logger.debug("output: " +output);
	       return output;
	    }
		
       return content;
	}
	public String renameMd5Delibere(String pathF,String nomeFile) throws Exception
	{
		String content="";
		String path=pathF+nomeFile;
		File app = new File(path);
		content = org.apache.commons.io.FileUtils.readFileToString(app, java.nio.charset.StandardCharsets.UTF_8.toString());
		
	    if(content.contains("$MD5$"))
	    {
	    	String md5 = calcMD5HashForInputStream(FileUtils.openInputStream(app));
	    	logger.debug("md5: "+md5);
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
			Document document = builder.parse(new InputSource(new FileReader(
					app
			        )));
			document.getDocumentElement().normalize();

			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			String anno = null;
			String numeroProposta = null;
		
			Element userElement = (Element) xpath.evaluate("/ChiusuraDelibera/chiaveDelibera", document, XPathConstants.NODE);
			if(userElement!=null )
			{
				
				anno = userElement.getAttribute("anno");
				NodeList users = document.getElementsByTagName("ChiusuraDelibera");
		        Element user = null;
		        // loop for each user
		        for (int i = 0; i < users.getLength(); i++) {
		            user = (Element) users.item(i);
		            Node name = user.getElementsByTagName("numDelibera").item(0).getFirstChild();
		            numeroProposta =  name.getNodeValue();
		        }
			}
			else
			{
				 userElement = (Element) xpath.evaluate("/NuovaDelibera/delibera/chiaveDelibera", document, XPathConstants.NODE);	
				numeroProposta = userElement.getAttribute("numeroProposta");
				anno = userElement.getAttribute("anno");
			}
            
            logger.debug("anno: " + anno);
			
			logger.debug("numeroProposta: " + numeroProposta);

			
			String ext = FilenameUtils.getExtension(path);
			String uuidFile = anno + "_" + numeroProposta + "_" + md5 + "." + ext;
			logger.debug("uuidFile: " + uuidFile);
	        
			NodeList users = document.getElementsByTagName("testo");
	        Element user = null;
	        // loop for each user
	        for (int i = 0; i < users.getLength(); i++) {
	            user = (Element) users.item(i);
	            Node name = user.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            
	            File f = new File(pathF+"testo"+File.separator +name.getNodeValue());
	            File g = new File(pathF+"testo"+File.separator +nom+"_"+md5+"."+ext1);
	            f.renameTo(g);
	            name.setNodeValue(nom+"_"+md5+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        NodeList users1 = document.getElementsByTagName("allegati");
	        Element user1 = null;
	        // loop for each user
	        for (int i = 0; i < users1.getLength(); i++) {
	            user1 = (Element) users1.item(i);
	            Node name = user1.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user1.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            File f1 = new File(pathF+"allegati"+File.separator +name.getNodeValue());
	            InputStream stream = FileUtils.openInputStream(f1);
	            String md5All = calcMD5HashForInputStream(stream);
	            stream.close();
	            File g1 = new File(pathF+"allegati"+File.separator +anno+"_"+numeroProposta+"_"+md5All+"."+ext1);
	            f1.renameTo(g1);
	            name.setNodeValue(anno+"_"+numeroProposta+"_"+md5All+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        // 4- Save the result to a new XML doc
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(new DOMSource(document), new StreamResult(new File(path)));
	        
	        String output = org.apache.commons.io.FileUtils.readFileToString(new File(path), java.nio.charset.StandardCharsets.UTF_8.toString());
	        
	        logger.debug("output: " +output);
	       return output;
	    }
		
       return content;
	}
	public String renameMd5Coto(String pathF,String nomeFile) throws Exception
	{
		String content="";
		String path=pathF+nomeFile;
		File app = new File(path);
		content = org.apache.commons.io.FileUtils.readFileToString(app, java.nio.charset.StandardCharsets.UTF_8.toString());
		
	    if(content.contains("$MD5$"))
	    {
	    	String md5 = calcMD5HashForInputStream(FileUtils.openInputStream(app));
	    	logger.debug("md5: "+md5);
	        JaxbUtil JaxbUtil1 = new JaxbUtil(Atto.class);
	        Atto atto = (Atto) JaxbUtil1.unmarshal(content, Atto.class, "ExportAttiCOTO.xsd");
	        
	        String anno = atto.getAnnoProvv().toString();
	        logger.debug("anno: "+anno);
	        String numDeter =String.valueOf(atto.getNroProvv());
	        logger.debug("numDeter: "+numDeter);
	        
	        String ext = FilenameUtils.getExtension(atto.getTesto().getNomeFile());
	        String uuidFile = anno+"_"+numDeter+"_"+md5+"."+ext;
	        logger.debug("uuidFile: "+uuidFile);
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
			Document document = builder.parse(new InputSource(new FileReader(
					app
			        )));
			document.getDocumentElement().normalize();
			
			XPath xPath =  XPathFactory.newInstance().newXPath();
	        
			NodeList users = document.getElementsByTagName("testo");
	        Element user = null;
	        // loop for each user
	        for (int i = 0; i < users.getLength(); i++) {
	            user = (Element) users.item(i);
	            Node name = user.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            
	            File f = new File(pathF+"testo"+File.separator +name.getNodeValue());
	            File g = new File(pathF+"testo"+File.separator +nom+"_"+md5+"."+ext1);
	            f.renameTo(g);
	            name.setNodeValue(nom+"_"+md5+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        NodeList users1 = document.getElementsByTagName("allegato");
	        Element user1 = null;
	        // loop for each user
	        for (int i = 0; i < users1.getLength(); i++) {
	            user1 = (Element) users1.item(i);
	            Node name = user1.getElementsByTagName("uuidFile").item(0).getFirstChild();
	            Node name1 = user1.getElementsByTagName("nomeFile").item(0).getFirstChild();
	            String ext1 = FilenameUtils.getExtension(name1.getNodeValue());
	            String nom =  FilenameUtils.removeExtension(name1.getNodeValue());
	            File f1 = new File(pathF+"allegati"+File.separator +name.getNodeValue());
	            InputStream stream = FileUtils.openInputStream(f1);
	            String md5All = calcMD5HashForInputStream(stream);
	            stream.close();
	            File g1 = new File(pathF+"allegati"+File.separator +anno+"_"+numDeter+"_"+md5All+"."+ext1);
	            f1.renameTo(g1);
	            name.setNodeValue(anno+"_"+numDeter+"_"+md5All+"."+ext1);
	            logger.debug("uuidFileInput: "+name.getNodeValue());
	        }
	        
	        // 4- Save the result to a new XML doc
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(new DOMSource(document), new StreamResult(new File(path)));
	        
	        String output = org.apache.commons.io.FileUtils.readFileToString(new File(path), java.nio.charset.StandardCharsets.UTF_8.toString());
	        
	        logger.debug("output: " +output);
	       return output;
	    }
		
       return content;
	}
	
	 public String renameFile(String path,String nome) throws IOException {

	        File folder = new File(path);
	        File[] listOfFiles = folder.listFiles();
            
	        for (int i = 0; i < listOfFiles.length; i++) {

	            if (listOfFiles[i].isFile()) {

	                File f = new File(path+ File.separator +listOfFiles[i].getName());
	                File g = null;
	                if(i==0)
	                {
	                	g=new File(path+ File.separator +nome);
	                }
	                else
	                {
	                	g=new File(path+ File.separator +i+"_"+nome);
	                }
	                	
	                logger.debug("f: "+f.getName());
	                logger.debug("g: "+g.getName());
	                if(f.renameTo(g)){
	                	logger.debug("Rename succesful");
	        		}else{
	        			logger.debug("Rename failed");
	        		}
                    logger.debug("fN: "+f.getName());
	                logger.debug("gN: "+g.getName());
	            }
	        }

	        return "conversion is done";
	}
	public boolean verificaDirectory(String path) {
		logger.info(CLASS_NAME + " " + "verificaDirectory" + " " + "INIZIO");
		boolean res = false;
		File directory = new File(path);

		if (!directory.exists()) {
			directory.mkdir();
		}
		res = true;
		logger.info(CLASS_NAME + " " + "verificaDirectory" + " " + "FINE");
		return res;
	}

	private String writeXmlDocumentToXmlFile(Document xmlDocument) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String xmlString = "";
		try {
			transformer = tf.newTransformer();

			// Uncomment if you do not require XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

			// A character stream that collects its output in a string buffer,
			// which can then be used to construct a string.
			StringWriter writer = new StringWriter();

			// transform document to string
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

			xmlString = writer.getBuffer().toString();
			logger.info("xmlString: " + xmlString);
			// Print to console or logs
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}
	
	public String stringPath(XPath xpath,Node context, String expression) {
        try {
            String result = (String) xpath.evaluate(expression, context,
                    XPathConstants.STRING);
            if (result == null || result.length() == 0)
                return null;
            else
                return result;
        } catch (XPathExpressionException ex) {
        	logger.error("XPathExpressionException:" +ex.getMessage());
            throw new RuntimeException("invalid xpath expresion used: "+ex.getMessage());
        }
    }
   
	public String calcMD5HashForDir(File dirToHash, boolean includeHiddenFiles) {

		assert (dirToHash.isDirectory());
		Vector<FileInputStream> fileStreams = new Vector<FileInputStream>();

		logger.debug("Found files for hashing:");
		collectInputStreams(dirToHash, fileStreams, includeHiddenFiles);

		SequenceInputStream seqStream = new SequenceInputStream(fileStreams.elements());

		try {
			String md5Hash = DigestUtils.md5Hex(seqStream);
			seqStream.close();
			return md5Hash;
		} catch (IOException e) {
			throw new RuntimeException("Error reading files to hash in " + dirToHash.getAbsolutePath(), e);
		}
	}
    
	public String calcMD5HashForInputStream(InputStream stream) {


		try {
			String md5Hash = DigestUtils.md5Hex(stream);
			return md5Hash;
		} catch (IOException e) {
			throw new RuntimeException("Error reading files to hash in " , e);
		}
	}
	private void collectInputStreams(File dir, List<FileInputStream> foundStreams, boolean includeHiddenFiles) {

		File[] fileList = dir.listFiles();
		Arrays.sort(fileList, // Need in reproducible order
				new Comparator<File>() {
					public int compare(File f1, File f2) {
						return f1.getName().compareTo(f2.getName());
					}
				});

		for (File f : fileList) {
			if (!includeHiddenFiles && f.getName().startsWith(".")) {
				// Skip it
			} else if (f.isDirectory()) {
				collectInputStreams(f, foundStreams, includeHiddenFiles);
			} else {
				try {
					System.out.println("\t" + f.getAbsolutePath());
					foundStreams.add(new FileInputStream(f));
				} catch (FileNotFoundException e) {
					throw new AssertionError(e.getMessage() + ": file should never not be found!");
				}
			}
		}

	}
}
