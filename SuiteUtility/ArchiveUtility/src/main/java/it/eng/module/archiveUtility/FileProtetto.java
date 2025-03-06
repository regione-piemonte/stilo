package it.eng.module.archiveUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.exceptions.EncryptedPowerPointFileException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.TextDocument;

import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfReader;

public class FileProtetto {

	private static final Logger log = Logger.getLogger(FileProtetto.class);
	private static FileInputStream in = null;

	public static String checkPasswordProtection(File file) {
		String esito = null;

		if (file.isFile()) {
			log.debug("Controllo se il file " + file + " e' protetto");

			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e4) {
				log.error("Errore nella lettura del file '" + file.getName(), e4);
				esito = "Errore nella lettura del file " + file.getName();
				return esito;
			}

			switch (FilenameUtils.getExtension(file.getName())) {

			case "pdf":
				if (log.isDebugEnabled()) {
					log.debug("Gestisco il caso del file .pdf");
				}	
				PdfReader reader = null;
				try {
					reader = new PdfReader(in);
				} catch (BadPasswordException e2) {
					log.error("File " + file.getName() + " protetto da password: ", e2);
					esito = "File " + file.getName() + " protetto da password";
				} catch (IOException e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				} finally {
					if(reader != null) {
						reader.close();
					}
				}
				break;

			case "pptx":
				OPCPackage opcPptx = null;
				try {
					opcPptx = OPCPackage.open(in);
					POIXMLDocument pptx = new XSLFSlideShow(opcPptx);
				} catch (OpenXML4JException e1) {
					log.error("File " + file.getName() + " protetto da password: ", e1);
					esito = "File " + file.getName() + " protetto da password";
				} catch (IOException e1) {
					log.error("Errore nella lettura del file '" + file.getName(), e1);
					esito = "Errore nella lettura del file " + file.getName();
				} catch (XmlException e1) {
					log.error("Errore nella lettura del file '" + file.getName(), e1);
					esito = "Errore nella lettura del file " + file.getName();
				} finally {
					if(opcPptx != null) {
						try {
							opcPptx.close();
						} catch (IOException e) {
							log.error(e);
						}
					}
				}
				break;

			case "ppt":
				try {
					POIFSFileSystem fs = new POIFSFileSystem(in); 
					HSLFSlideShow ppt = new HSLFSlideShow(fs);  
				} catch (FileNotFoundException e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				} catch (EncryptedPowerPointFileException e) {
					log.error("File " + file.getName() + " protetto da password: ", e);
					esito = "File " + file.getName() + " protetto da password";
				} catch (IOException e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				}
				break;

			case "xlsx":
				Workbook wb = null;
				
				try {
					InputStream is = new FileInputStream(file);
			        if (!is.markSupported()) {
			            is = new PushbackInputStream(is, 8);
			        }

			        if (POIFSFileSystem.hasPOIFSHeader(is)) {
			        	log.error("File " + file.getName() + " protetto da password: ");
						esito = "File " + file.getName() + " protetto da password";
			        }
				}catch (Exception e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				}
				break;

			case "xls":
				if (log.isDebugEnabled())
					log.debug("Gestisco il caso del file .xls");
				try {
					InputStream is = new FileInputStream(file);
			        if (!is.markSupported()) {
			            is = new PushbackInputStream(is, 8);
			        }

			        if (POIFSFileSystem.hasPOIFSHeader(is)) {
			        	log.error("File " + file.getName() + " protetto da password: ");
						esito = "File " + file.getName() + " protetto da password";
			        }
				}catch (Exception e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				}
				break;

			case "docx":
				OPCPackage opcDoc = null;
				try {
					opcDoc = OPCPackage.open(in);
					POIXMLDocument docx = new XWPFDocument(opcDoc);
				} catch (OpenXML4JException e1) {
					log.error("File " + file.getName() + " protetto da password: ", e1);
					esito = "File " + file.getName() + " protetto da password";
				} catch (IOException e1) {
					log.error("Errore nella lettura del file '" + file.getName(), e1);
					esito = "Errore nella lettura del file " + file.getName();
				} finally {
					if(opcDoc != null) {
						try {
							opcDoc.close();
						} catch (IOException e) {
							log.error(e);
						}
					}
				}
				break;

			case "doc":
				try {
					POIFSFileSystem fs = new POIFSFileSystem(in); 
					HWPFDocument doc = new HWPFDocument(fs);  
				} catch (FileNotFoundException e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				} catch (EncryptedDocumentException e) {
					log.error("File " + file.getName() + " protetto da password: ", e);
					esito = "File " + file.getName() + " protetto da password";
				} catch (IOException e) {
					log.error("Errore nella lettura del file " + file.getName(), e);
					esito = "Errore nella lettura del file " + file.getName();
				}
				break;

			case "odt":
			case "odp":
			case "ods":
				OdfPackage odfPackage = null;
				Document document = null;
				try {
					odfPackage = OdfPackage.loadPackage(file);  
		        	document =  TextDocument.loadDocument(odfPackage);	
				} catch (Exception e) {
					log.error("File " + file.getName() + " protetto da password: ", e);
					esito = "File " + file.getName() + " protetto da password";
				} finally {
					if(odfPackage != null) {
						odfPackage.close();
					}
					if(document != null) {
						document.close();
					}
				}
				break;

			default:
				log.info("Formato del file " + file.getName() + " non riconosciuto come protetto.");
				break;
			}
			
			
			try {
				in.close();
			} catch (IOException e) {
				log.error("impossibile chiudere il file: " + file.getName(), e);
			}
			
		}

		return esito;
	}
}
