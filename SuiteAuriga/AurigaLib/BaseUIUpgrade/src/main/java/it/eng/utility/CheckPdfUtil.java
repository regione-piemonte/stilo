/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.axis.types.URI;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.XfaForm;

import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CheckPdfUtil {
	
	static Logger logger = Logger.getLogger(CheckPdfUtil.class);
	
	private static final int defaultDPI = 185; //prima era 300 ma le immagina venivano troppo pesanti
	
	//metodo che verifica se itext puo aprire il file in lettura e di conseguenza si possono fare le varei operazioni di timbratura, unione file ecc
	public static boolean checkPdfReadItext(String uriFile) {
		try {
			PdfReader reader = new PdfReader(uriFile);
		}catch(Exception ex) {
			logger.warn("Il file caricato in upload non puo essere lavorato da itext per il seguente motivo: " + ex.getMessage(), ex);
			return false;
		}
		
		return true;
	}
	
	public static Boolean hasCommentBox(String uri) {
		Boolean verify = false;
		PdfReader reader = null;
		try {
			reader = new PdfReader(uri);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {

				PdfDictionary page = reader.getPageN(i);
				PdfArray annotsArray = null;

				if(page.getAsArray(PdfName.ANNOTS)==null)
				continue;

				annotsArray = page.getAsArray(PdfName.ANNOTS);
				if(annotsArray != null && !annotsArray.isEmpty()) {
					ListIterator<PdfObject> iter = annotsArray.listIterator(); 
					while (iter.hasNext()) {
						PdfDictionary annot = (PdfDictionary) PdfReader.getPdfObject(iter.next());
						PdfString content = (PdfString) PdfReader.getPdfObject(annot.get(PdfName.CONTENTS));
						if (content != null) {
							PdfString tagAutoCad = (PdfString) PdfReader.getPdfObject(annot.get(PdfName.T));
							if (tagAutoCad != null && StringUtils.isNotBlank(tagAutoCad.toString())) {
								if (!tagAutoCad.toString().toUpperCase().contains("AUTOCAD")) {
									verify = true;
									break;
								}
							}else {
								verify = true;
								break;
							}													
						}
					}
				}
			}			
		} catch (Exception e) {
			logger.error("Errore durante il check dei commenti sulle pagine del pdf: " + e.getMessage(),e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return verify;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Integer> returnPageWithCommentBox(String uri) {
		List<Integer> listPageWithCommentBox = new ArrayList<>();
		PdfReader reader = null;
		try {
			reader = new PdfReader(uri);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				
				PdfDictionary page = reader.getPageN(i);
				PdfArray annotsArray = null;
				
				if(page.getAsArray(PdfName.ANNOTS)==null)
					continue;
				
				annotsArray = page.getAsArray(PdfName.ANNOTS);
				if(annotsArray != null && !annotsArray.isEmpty()) {
					ListIterator iter = annotsArray.listIterator(); 
					while (iter.hasNext()) {
						PdfDictionary annot = (PdfDictionary) PdfReader.getPdfObject((PdfObject) iter.next());
						PdfString content = (PdfString) PdfReader.getPdfObject(annot.get(PdfName.CONTENTS));
						if (content != null) {
							PdfString tagAutoCad = (PdfString) PdfReader.getPdfObject(annot.get(PdfName.T));
							if (tagAutoCad != null && StringUtils.isNotBlank(tagAutoCad.toString())) {
								if (!tagAutoCad.toString().toUpperCase().contains("AUTOCAD")) {
									listPageWithCommentBox.add(i);
								}
							}else {
								listPageWithCommentBox.add(i);
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
		 	logger.error("Errore durante il check dei commenti sulle pagine del pdf: " + e.getMessage(),e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		return listPageWithCommentBox;
	}
	
	public static IdFileBean manageProtectedPdf(String pathFile, String mimetypeInput, String displayFileName, boolean fromScanner, Date dataRif){
		IdFileBean idFileBean = new IdFileBean();
		final StorageService storageService = StorageImplementation.getStorage();
		MimeTypeFirmaBean mimeTypeFirmaBean;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();

		try {		
			if("application/pdf".equalsIgnoreCase(mimetypeInput)) {
				if(InfoFileUtility.checkProtectedFile(pathFile)) {
					logger.debug("Il pdf è protetto");
					logger.debug("--- GESTIONE PDF PROTETTI ---");
					File fileWithPermissions = copyFileInAnotherFile(pathFile);
					String pathFileStorato = storageService.storeStream(new FileInputStream(fileWithPermissions));
					String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
					logger.debug("Il file protetto è stato copiato con tutti i permessi al seguente uri: " + realFilePath);
					logger.debug("Ricalcolo mimetype con fileop");
					
					//ricalcolo il mimetype con fileop
					mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
					
					idFileBean.setInfoFile(mimeTypeFirmaBean);
					idFileBean.setUri(pathFileStorato);
				}
			}

			return idFileBean;
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf protetti: " + e.getMessage(), e);
		}
		
		return idFileBean;
	}


	public static EditablePdfBean checkEditableFile(String pathFile) throws Exception {
		EditablePdfBean checkEditableFileBean = new EditablePdfBean();
		
		logger.debug("Verifico se il pdf è editabile");
		PdfReader reader = null;

		try {
			reader = new PdfReader(pathFile);
//			reader.unethicalreading = true;

			AcroFields fields = reader.getAcroFields();
			
			List<String> listaNomiSignature = fields.getSignatureNames();

			if (fields != null && fields.getFields() != null && fields.getFields().size() > 0) {
				for (String key : fields.getFields().keySet()) {
					if (!listaNomiSignature.contains(key)) {
						checkEditableFileBean.setFlgEditable(true);
						logger.debug("Il pdf è editabile");
					}
				}
			}

			if (checkEditableFileWithXfaForm(pathFile)) {
				checkEditableFileBean.setFlgEditable(true);
				checkEditableFileBean.setFlgContainsXfaForm(true);
				logger.debug("Il pdf è editabile e contiene xfa form");
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo checkEditableFile", e);
			throw new Exception("Errore durante la verifica dei pdf editabili: " + e.getMessage(), e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return checkEditableFileBean;
	}

	public static boolean checkEditableFileWithXfaForm(String pathFile) throws Exception {
		logger.debug("Verifico se il pdf contiene xfa Form");
		PdfReader reader = null;
		
		try {
			reader = new PdfReader(pathFile);
//			reader.unethicalreading = true;

			XfaForm xfaForm;

			AcroFields acroFileds = reader.getAcroFields();
			if (acroFileds != null) {
				xfaForm = acroFileds.getXfa();
				if (xfaForm != null && xfaForm.getDomDocument() != null) {
					logger.debug("Il pdf contiene xfa Form");
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo checkEditableFileWithXfaForm", e);
			throw new Exception("Errore durante la verifica degli xfa form contenuti nel documento: " + e.getMessage(),e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		logger.debug("Il pdf non contiene xfa Form");
		return false;
	}
	
	public static String staticizzaPdf(String filePath) throws Exception {
		logger.debug("Staticizzo documento");
		PdfReader reader = null;
		try {
			File tempFlatteFile = File.createTempFile("tempFlatten", ".pdf");

			reader = new PdfReader(filePath);
//			reader.unethicalreading = true;
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tempFlatteFile));
			AcroFields form = stamper.getAcroFields();
			form.setGenerateAppearances(true);

			stamper.setFormFlattening(true);
			stamper.close();

			return tempFlatteFile.getPath();
		} catch (Exception e) {
			logger.error("Errore nel metodo staticizzaPdf", e);
			throw new Exception("Errore durante la staticizzazione del documento editabile: " + e.getMessage(),e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	public static String staticizzaPdfConXfaForm(String filePath) throws Exception {
		logger.debug("Staticizzo documento con xfa form");
		PDDocument pdDoc = null;
		
		try {
			File tempFlattenFile = File.createTempFile("tempFlatten", ".pdf");
			pdDoc = PDDocument.load(new File(new URI(filePath).getPath()));
			
			if (pdDoc.isEncrypted()) {
				pdDoc.setAllSecurityToBeRemoved(true);
			}
			
			PDDocumentCatalog pdCatalog = pdDoc.getDocumentCatalog();
			PDAcroForm acroForm = pdCatalog.getAcroForm();

			if (acroForm != null) {

				@SuppressWarnings("unchecked")
				List<PDField> fields = acroForm.getFields();
				for (PDField field : fields) {
					if (field.getFullyQualifiedName().equals("formfield1")) {
						field.setReadOnly(true);
					}
				}
				pdDoc.save(tempFlattenFile.getPath());
				
				return tempFlattenFile.getPath();
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo staticizzaPdfConXfaForm", e);
			throw new Exception("Errore durante la staticizzazione del documento editabile con xfaform: " + e.getMessage(),e);
		} finally {
			if (pdDoc != null) {
				pdDoc.close();
			}
		}
		
		return null;
	}
	
	public static IdFileBean manageEditablePdf(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, MimeTypeFirmaBean lMimeTypeFirmaBean) throws Exception {
		IdFileBean idFileBean = new IdFileBean();
		MimeTypeFirmaBean mimeTypeFirmaBean = lMimeTypeFirmaBean;
		String fileStaticizzatoUrl;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		final StorageService storageService = StorageImplementation.getStorage();
		
		
		try {
			if(lMimeTypeFirmaBean.isPdfEditabile()) {
				logger.debug("Il pdf è editabile");
				logger.debug("--- GESTIONE PDF EDITABILI ---");
				
				//controllo se il file contiene xfa Form
				if(!checkEditableFileWithXfaForm(fileUrl)) {
					//staticizzo il file
					fileStaticizzatoUrl = staticizzaPdf(fileUrl);
					String pathFileStorato = storageService.storeStream(new FileInputStream(new File(fileStaticizzatoUrl)));
					String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
					logger.debug("File staticizzato è stato salvato al seguente uri: " + realFilePath);
					logger.debug("Ricalcolo mimetype con fileop");
					
					//ricalcolo il mimetype con fileop
					mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
					mimeTypeFirmaBean.setPdfEditabile(true);
					
					idFileBean.setInfoFile(mimeTypeFirmaBean);
					idFileBean.setUri(pathFileStorato);
				}else {
					//staticizzo il file con xfaform
					fileStaticizzatoUrl = staticizzaPdfConXfaForm(fileUrl);
					if(fileStaticizzatoUrl!=null) {
						String pathFileStorato = storageService.storeStream(new FileInputStream(new File(fileStaticizzatoUrl)));
						String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
						logger.debug("File staticizzato con xfa form è stato salvato al seguente uri: " + pathFileStorato);
						logger.debug("Ricalcolo mimetype con fileop");
						
						//ricalcolo il mimetype con fileop
						mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
					
						idFileBean.setInfoFile(mimeTypeFirmaBean);
						idFileBean.setUri(pathFileStorato);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf editabili: " + e.getMessage(), e);
		}
		
		return idFileBean;
		
	}
	
	public static IdFileBean managePdfCorrotti(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, MimeTypeFirmaBean lMimeTypeFirmaBean) throws Exception {
		IdFileBean idFileBean = new IdFileBean();
		MimeTypeFirmaBean mimeTypeFirmaBean = lMimeTypeFirmaBean;
		File fileConvertitoInImmagine;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		final StorageService storageService = StorageImplementation.getStorage();

		logger.debug("Il pdf non puo essere letto da itext in quanto corrotto, lo converto in immagine");
		logger.debug("--- GESTIONE PDF CORROTTI ---");

		// converto il file in immagine
		try {
			if("application/pdf".equalsIgnoreCase(lMimeTypeFirmaBean.getMimetype())) {
				String pathFile = new URI(fileUrl).getPath();
				File fileToConvert = new File(pathFile);
				
				fileConvertitoInImmagine = fromPdfToPdfImage(fileToConvert);
	
				String pathFileStorato = storageService.storeStream(new FileInputStream(fileConvertitoInImmagine));
				String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
				logger.debug("Il File convertito in immagine è stato salvato al seguente uri: " + realFilePath);
				logger.debug("Ricalcolo mimetype con fileop");
	
				// ricalcolo il mimetype con fileop
				mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
	
				idFileBean.setInfoFile(mimeTypeFirmaBean);
				idFileBean.setUri(pathFileStorato);
			}

		} catch (Exception e) {
			logger.error("E' avvenuto un errore durante la converione in immagine del pdf corrotto: " + e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}

		return idFileBean;

	}
	
	public static File fromPdfToPdfImage(File fileConRitagli) throws Exception {
		List<String> listaImages = fromPdfToImage(fileConRitagli);
		return fromImageToPdf(listaImages, false);
	}
	
	public static List<String> fromPdfToImage(File filePdf) throws Exception {
		List<String> listaImages = new ArrayList<>();

		PDDocument document = null;
		
		try {
			document = PDDocument.load(filePdf);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
		
			for (int page = 0; page < document.getNumberOfPages(); page++) {
				File imageTemp = File.createTempFile("image", ".jpg");
				File imageCompressed = File.createTempFile("imageCompressed", ".jpg");
	
				BufferedImage bImage = pdfRenderer.renderImageWithDPI(page, defaultDPI, ImageType.RGB);
				ImageIOUtil.writeImage(bImage, imageTemp.getPath(), defaultDPI);
				
			    BufferedImage bImageForCompression = ImageIO.read(imageTemp);
			    OutputStream imageCompressedOS = new FileOutputStream(imageCompressed);
	
			    Iterator<ImageWriter> wImage = ImageIO.getImageWritersByFormatName("jpg");
			    ImageWriter writer = (ImageWriter) wImage.next();
			    ImageOutputStream imageOS = ImageIO.createImageOutputStream(imageCompressedOS);
			    writer.setOutput(imageOS);
			    
			    ImageWriteParam wImageParam = writer.getDefaultWriteParam();
			    wImageParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			    wImageParam.setCompressionQuality(0.50f);
			    
			    writer.write(null, new IIOImage(bImageForCompression, null, null), wImageParam);
			    
			    imageCompressedOS.close();
			    imageOS.close();
			    writer.dispose();
			    
			    imageTemp.delete();
			    imageCompressed.deleteOnExit();
			    listaImages.add(imageCompressed.getPath());
			}
			
		} catch (Exception e) {
			logger.error("Errore nel metodo fromPdfToImage", e);
			throw new Exception(e.getMessage(), e);
		} finally {
			if (document != null) {
				document.close();
			}
		}

		return listaImages;
	}
	
	public static File fromImageToPdf(List<String> listaPathImage, boolean fromScannedException) throws Exception {
		
		File pdfFinale = File.createTempFile("pdfTemp", ".pdf");
		Document document = null;
		
		try {
	        if (fromScannedException) {
	        	document = new Document(PageSize.A4,0,0,0,0);
	        } else {
	        	document = new Document();
	        }
			PdfWriter.getInstance(document, new FileOutputStream(pdfFinale));
			document.open();
	
			Rectangle documentRect = document.getPageSize();
	
			
			for (String pathImage : listaPathImage) {
				Image image = Image.getInstance(pathImage);
				image.setScaleToFitHeight(true);
				
				float scalePortrait = Math.min(document.getPageSize().getWidth() / image.getWidth(),
						document.getPageSize().getHeight() / image.getHeight());
	
		        float scaleLandscape = Math.min(document.getPageSize().getHeight() / image.getWidth(),
		        		document.getPageSize().getWidth() / image.getHeight());
				
		        boolean isLandscape = scaleLandscape > scalePortrait;
	
		        float w;
		        float h;
		        if (isLandscape) {
		        	documentRect = documentRect.rotate();
		            w = image.getWidth() * scaleLandscape;
		            h = image.getHeight() * scaleLandscape;
		        } else {
		            w = image.getWidth() * scalePortrait;
		            h = image.getHeight() * scalePortrait;
		        }
		        
		        image.scaleAbsolute(w, h);
	            float posH = (documentRect.getHeight() - h) / 2;
	            float posW = (documentRect.getWidth() - w) / 2;
	
	            image.setAbsolutePosition(posW, posH);
	            image.setBorder(Image.NO_BORDER);
	            image.setBorderWidth(0);
	            
	            document.setPageSize(documentRect);
	            document.newPage();
	            document.add(image);
			}
			
			return pdfFinale;
			
		} catch (Exception e) {
			logger.error("Errore nel metodo fromImageToPdf", e);
			throw new Exception(e.getMessage(), e);
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}
	
	public static File manageMultiLayerPdf(String pathFile, String mimeTypeFile) {
		
		File fileWithoutLayer = new File(pathFile);
		
		try {
			if ("application/pdf".equalsIgnoreCase(mimeTypeFile) && checkPdfMultiLayer(pathFile)) {
				logger.debug("Il pdf è multiLayer");
				logger.debug("--- GESTIONE PDF MULTILAYER ---");
				fileWithoutLayer = copyFileInAnotherFile(pathFile);
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf multiLayer: " + e.getMessage(), e);
		}
		
		return fileWithoutLayer;
		
	}
	
	public static boolean isPdfA(String pathFile) throws Exception {
		PdfReader reader = null;
		try {
			reader = new PdfReader(pathFile);
			boolean booleanToReturn = reader.getMetadata() != null && (new String(reader.getMetadata())).contains("pdfaid:conformance"); 
			return booleanToReturn;
		} catch (Exception e) {
			logger.error("Errore nella verifica del pdfA: " + e.getMessage() + ", ritorno false", e);
			return false;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	private static File copyFileInAnotherFile(String pathFile) throws Exception {
		File destTempFile = File.createTempFile("tempFile", ".pdf");
		FileOutputStream os = new FileOutputStream(destTempFile);

		Document document = new Document();
		PdfWriter writer = null;

		try {
			// il file generato dovrà essere un PDFA se quello di origine era un PDFA, altrimenti no.
			if (InfoFileUtility.checkPdfA(pathFile)) {
				writer = PdfAWriter.getInstance(document, os, PdfAConformanceLevel.PDF_A_3U);
			} else {
				writer = PdfWriter.getInstance(document, os);
			}

			writer.setTagged();
			writer.setLanguage("it");
			writer.setLinearPageMode();
			writer.createXmpMetadata();

			document.open();

			PdfContentByte cb = writer.getDirectContent();
			
			PdfReader reader = new PdfReader(pathFile);
			// Questo parametro mi fa bypassare la password in modifica presente sul file e quindi mi permette di leggerlo con PdfReader
			PdfReader.unethicalreading = true;
			
			// Scorro le pagine da copiare
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				addPageToDocument(document, writer, cb, reader, i, false);
			}

			os.flush();

			return destTempFile;
			
		} catch (Exception e) {
			logger.error("Errore durante la copia del file protetto con i permessi", e);
			throw new Exception("Errore durante la copia del file protetto con i permessi: " + e.getMessage(), e);
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (document != null) {
				document.close();
			}
			if (os != null) {
				os.close();
			}
		}

	}
	
	private static File copyFileInAnotherFileWithoutPwd(String pathFile, byte[] pwd) throws Exception {
		File destTempFile = File.createTempFile("tempFile", ".pdf");
		FileOutputStream os = new FileOutputStream(destTempFile);

		Document document = new Document();
		PdfWriter writer = null;

		try {
			// il file generato dovrà essere un PDFA se quello di origine era un PDFA, altrimenti no.
			if (InfoFileUtility.checkPdfA(pathFile)) {
				writer = PdfAWriter.getInstance(document, os, PdfAConformanceLevel.PDF_A_3U);
			} else {
				writer = PdfWriter.getInstance(document, os);
			}

			writer.setTagged();
			writer.setLanguage("it");
			writer.setLinearPageMode();
			writer.createXmpMetadata();

			document.open();

			PdfContentByte cb = writer.getDirectContent();
			
			PdfReader reader = new PdfReader(pathFile, pwd);
			// Questo parametro mi fa bypassare la password in modifica presente sul file e quindi mi permette di leggerlo con PdfReader
			PdfReader.unethicalreading = true;
			
			// Scorro le pagine da copiare
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				addPageToDocument(document, writer, cb, reader, i, false);
			}

			os.flush();

			return destTempFile;
		} catch (Exception e) {
			logger.error("Errore durante la copia del file protetto con i permessi", e);
			throw new Exception("Errore durante la copia del file protetto con i permessi: " + e.getMessage(), e);
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (document != null) {
				document.close();
			}
			if (os != null) {
				os.close();
			}
		}

	}
	
	private static void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		// Verifico la rotazione della pagina corrente
		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4) {
				document.setPageSize(PageSize.A4.rotate());
			} else {
				document.setPageSize(psize);
			}
		} else {
			if (forceA4) {
				document.setPageSize(PageSize.A4);
			} else {
				document.setPageSize(psize);
			}
		}

		// Creo una nuova pagina nel document in cui copiare la pagina corrente
		document.newPage();
		// Raddrizzo l'immagine a seconda della rotazione
		switch (psize.getRotation()) {
		case 0:
			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			break;
		case 90:
			cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
			break;
		case 180:
			cb.addTemplate(page, -1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
			break;
		case 270:
			cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
			break;
		default:
			break;
		}
	}
	
	public static boolean checkPdfMultiLayer(String realFilePath) {
		try {
			PdfReader reader = new PdfReader(realFilePath);
			PdfDictionary catalog = reader.getCatalog();
			if(catalog.get(PdfName.OCPROPERTIES)!=null){
				return true;
			}else {
				 return false;
			}
		}catch(Exception ex) {
			logger.error("Errore durante il controllo dei pdf MultiLayer: " + ex.getMessage());
		}
		return false;
	}
	
	/**
	 * @param pathFile
	 * @return 
	 * @throws IOException
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	/**Serve a rimuovere i livelli da un pdf multilayer**/
	public static String appiattisciFile(String pathFile) throws Exception {
		File tempFile = File.createTempFile("temp", ".pdf");
		
		PdfReader reader = new PdfReader(pathFile);
		PdfDictionary catalog = reader.getCatalog();
		catalog.remove(PdfName.OCPROPERTIES);
		
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tempFile));
		stamper.close();
		reader.close();
		
		return tempFile.getAbsolutePath();
	}
	
	public static File convertPagesPdfToPdfImages(String fileUrl, List<Integer> listPagesToConvert) throws Exception {
		return rewriteDocument(fileUrl, listPagesToConvert, false);
	}
	
	public static File rewriteDocument(String fileUrl, List<Integer> listPagesToConvert, boolean fromScannedExc) throws Exception {
		
		File documentoRiscritto = File.createTempFile("DocRiscritto", ".pdf");
		OutputStream output = null;
		Document document = null;
		PDDocument lPDDocument = null;
		
		try {
			output = new FileOutputStream(documentoRiscritto);
			document = new Document();
			if (fromScannedExc) {
	        	document = new Document(PageSize.A4,0,0,0,0);
	        } else {
	        	document = new Document();
	        }
			
			PdfWriter writer = PdfWriter.getInstance(document, output);
			writer.setTagged();
			writer.setLanguage("it");
			writer.setLinearPageMode();
			writer.createXmpMetadata();
	        
			document.open();
	
			PdfContentByte cb = writer.getDirectContent();
	
			// Collego il reader al file
			PdfReader reader = new PdfReader(fileUrl);
			lPDDocument = PDDocument.load(new File(fileUrl));
			// Scorro le pagine da copiare
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				if(listPagesToConvert.contains(i)) {
					rewritePage(fileUrl, document, writer, cb, reader, i, false, true, lPDDocument);
				} else {
					rewritePage(fileUrl, document, writer, cb, reader, i, false, false, lPDDocument);
				}
			}
	
			output.flush();
			
			return documentoRiscritto;
		} catch (Exception e) {
			logger.error("Errore nel metodo rewriteDocument", e);
			throw new Exception(e.getMessage(), e);
		} finally {
			if (document != null) {
				document.close();
			}
			if (output != null) {
				output.close();
			}
			if (lPDDocument != null) {
				lPDDocument.close();
			}
		}
	}
		
	
	private static void rewritePage(String fileUrl, Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4, boolean convertPageToImage, PDDocument lPDDocument) throws Exception {
		
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4) {
				document.setPageSize(PageSize.A4.rotate());
			} else {
				document.setPageSize(psize);
			}
		} else {
			if (forceA4) {
				document.setPageSize(PageSize.A4);
			} else {
				document.setPageSize(psize);
			}
		}

		if (convertPageToImage) {
			convertPageToImage(fileUrl, pageNumber, document, lPDDocument);
		} else {
			// Creo una nuova pagina nel document in cui copiare la pagina corrente
			document.newPage();
			switch (psize.getRotation()) {
			case 0:
				cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
				break;
			case 90:
				cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
				break;
			case 180:
				cb.addTemplate(page, -1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
				break;
			case 270:
				cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
				break;
			default:
				break;
			}
		}
	}
	
	private static void convertPageToImage(String fileUrl, int pageNumber, Document documentPrincipal, PDDocument document)
			throws Exception {

		/* Si è deciso da passare in input PDDocument cosi da caricarlo una sola volta e non per ogni pagina da convertire rallentando e appesantendo l'elaborazione*/
//		File fileInput = new File(fileUrl);
//		PDDocument document = PDDocument.load(fileInput);
				   
		PDFRenderer pdfRenderer = new PDFRenderer(document);

		File imageTemp = File.createTempFile("tempImage", ".jpg");
		File imageCompressed = File.createTempFile("tempImageCompressed", ".jpg");

		BufferedImage bImage = pdfRenderer.renderImageWithDPI(pageNumber-1, defaultDPI, ImageType.RGB);
		ImageIOUtil.writeImage(bImage, imageTemp.getPath(), defaultDPI);

		BufferedImage bImageForCompression = ImageIO.read(imageTemp);
		OutputStream imageCompressedOS = new FileOutputStream(imageCompressed);

		Iterator<ImageWriter> wImage = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter) wImage.next();
		ImageOutputStream imageOS = ImageIO.createImageOutputStream(imageCompressedOS);
		writer.setOutput(imageOS);

		ImageWriteParam wImageParam = writer.getDefaultWriteParam();
		wImageParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		wImageParam.setCompressionQuality(0.50f);

		writer.write(null, new IIOImage(bImageForCompression, null, null), wImageParam);

		imageCompressedOS.close();
		imageOS.close();
		writer.dispose();

		imageTemp.delete();
		imageCompressed.deleteOnExit();

//		document.close();

//		documentPrincipal.open();

		Rectangle documentRect = documentPrincipal.getPageSize();

		Image image = Image.getInstance(imageCompressed.getPath());
		image.setScaleToFitHeight(true);

		float scalePortrait = Math.min(documentPrincipal.getPageSize().getWidth() / image.getWidth(),
				documentPrincipal.getPageSize().getHeight() / image.getHeight());

		float scaleLandscape = Math.min(documentPrincipal.getPageSize().getHeight() / image.getWidth(),
				documentPrincipal.getPageSize().getWidth() / image.getHeight());

		boolean isLandscape = scaleLandscape > scalePortrait;

		float w;
		float h;
		if (isLandscape) {
			documentRect = documentRect.rotate();
			w = image.getWidth() * scaleLandscape;
			h = image.getHeight() * scaleLandscape;
		} else {
			w = image.getWidth() * scalePortrait;
			h = image.getHeight() * scalePortrait;
		}

		image.scaleAbsolute(w, h);
		float posH = (documentRect.getHeight() - h) / 2;
		float posW = (documentRect.getWidth() - w) / 2;

		image.setAbsolutePosition(posW, posH);
		image.setBorder(Image.NO_BORDER);
		image.setBorderWidth(0);

		documentPrincipal.setPageSize(documentRect);
		documentPrincipal.newPage();
		documentPrincipal.add(image);

//		documentPrincipal.close();

	}

	public static IdFileBean managePdfConCommenti(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, MimeTypeFirmaBean lMimeTypeFirmaBean) {

		IdFileBean idFileBean = new IdFileBean();
		MimeTypeFirmaBean mimeTypeFirmaBean = lMimeTypeFirmaBean;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		final StorageService storageService = StorageImplementation.getStorage();		
		
		try {
			if(lMimeTypeFirmaBean.isPdfConCommenti()) {
				logger.debug("Il pdf ha commenti");
				logger.debug("--- GESTIONE PDF CON COMMENTI ---");
				
				String pathFile = new URI(fileUrl).getPath();
				
				//controllo quali pagine hanno i commenti
				List<Integer> listaPagineConCommenti = returnPageWithCommentBox(pathFile);
				File fileConvertito = convertPagesPdfToPdfImages(pathFile, listaPagineConCommenti);
				
				String pathFileStorato = storageService.storeStream(new FileInputStream(fileConvertito));
				String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
				
				logger.debug("File convertito è stato salvato al seguente uri: " + realFilePath);
				logger.debug("Ricalcolo mimetype con fileop");
				
				//ricalcolo il mimetype con fileop
				mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
				mimeTypeFirmaBean.setPdfConCommenti(true);
				
				idFileBean.setInfoFile(mimeTypeFirmaBean);
				idFileBean.setUri(pathFileStorato);
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf con commenti: " + e.getMessage(), e);
		}
		
		return idFileBean;
		
	
	}
	
	public static Integer getNumbOfPages(File file) {
		PdfReader pdfReader = null;
		try {
			pdfReader = new PdfReader(file.getPath());
			return pdfReader.getNumberOfPages();
		} catch (IOException e) {
			logger.error("Non è stato possibile calcolare il numero di pagine del pdf: " + e.getMessage(), e);
		} finally {
			if(pdfReader!=null) {
				pdfReader.close();
			}
		}
		
		return null;
	}

}
