/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.module.foutility.beans.CheckPdfCommentiFileBean;
import it.eng.module.foutility.beans.VerificaPdfBean;

public class PdfCommentiUtil {

	public static final Logger logger = LogManager.getLogger(PdfCommentiUtil.class);
	
	private static final int defaultDPI = 185; //prima era 300 ma le immagina venivano troppo pesanti
	
	public static CheckPdfCommentiFileBean isPdfConCommenti(String file) {
		logger.debug("Verifico se il file " + file + " contiene commenti");
		PdfReader pdfReader = null;
		CheckPdfCommentiFileBean checkPdfCommentiFileBean = new CheckPdfCommentiFileBean();
		try {
			pdfReader = new PdfReader(file);
			long t0 = System.currentTimeMillis();
			List<Integer> pageWithCommentBox = returnPageWithCommentBox( file );
			long t1 = System.currentTimeMillis();
			long timeOperation = t1 - t0;
			
			if(pageWithCommentBox.size()>0) {
				logger.debug("Il file " + file + " ha commenti su " + pageWithCommentBox.size() + " pagine ---   ha impiegato " + timeOperation + " millisecondi");
				checkPdfCommentiFileBean.setFlgContieneCommenti( true );
				checkPdfCommentiFileBean.setPageWithCommentBox(pageWithCommentBox);
			} else {
				logger.debug("Il file " + file + " NON ha commenti  ---   ha impiegato " + timeOperation + " millisecondi");
				checkPdfCommentiFileBean.setFlgContieneCommenti( false );
			}
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			if(pdfReader != null) {
				pdfReader.close();
			}
		}

		return checkPdfCommentiFileBean;
	}
	
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
		}catch (Exception e) {
			logger.error("Errore durante il check dei commenti sulle pagine del pdf: " + e.getMessage(),e);
		} finally {
			if( reader!=null ){
				reader.close();
			}
		}
		
		return listPageWithCommentBox;
	}
	
	public static Boolean hasCommentBox(String uri) {
		Boolean verify = false;
		try {
			PdfReader reader = new PdfReader(uri);
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
		}catch (Exception e) {
			logger.error("Errore durante il check dei commenti sulle pagine del pdf: " + e.getMessage(),e);
		}
		return verify;
	}
	
	public static File convertPagesPdfToPdfImages(File filePdf, List<Integer> listPagesToConvert) throws Exception {
		return rewriteDocument(filePdf, listPagesToConvert, false);
	}
	
	public static File rewriteDocument(File filePdf, List<Integer> listPagesToConvert, boolean fromScannedExc) throws Exception {
		
		File documentoRiscritto = File.createTempFile("DocRiscritto", ".pdf", filePdf.getParentFile() );
		
		OutputStream output = new FileOutputStream(documentoRiscritto);
		Document document = new Document();
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
		PdfReader reader = new PdfReader(filePdf.getAbsolutePath());
		// Scorro le pagine da copiare
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			if(listPagesToConvert.contains(i)) {
				rewritePage(filePdf, document, writer, cb, reader, i, false, true);
			} else {
				rewritePage(filePdf, document, writer, cb, reader, i, false, false);
			}
		}

		output.flush();
		document.close();
		output.close();
		
		return documentoRiscritto;
	}
	
	private static void rewritePage(File filePdf, Document document, PdfWriter writer, PdfContentByte cb,
			PdfReader reader, int pageNumber, boolean forceA4, boolean convertPageToImage) throws Exception {
		
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
			convertPageToImage(filePdf, pageNumber, document);
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
	
	private static void convertPageToImage(File filePdf, int pageNumber, Document documentPrincipal)
			throws Exception {

		PDDocument document = PDDocument.load(filePdf);
		   
		PDFRenderer pdfRenderer = new PDFRenderer(document);

		File imageTemp = File.createTempFile("tempImage", ".jpg", filePdf.getParentFile());
		File imageCompressed = File.createTempFile("tempImageCompressed", ".jpg", filePdf.getParentFile());

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

		document.close();

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
	
	public static boolean isAttivaVerificaPdfCommenti() {
		try {
			ApplicationContext context = FileoperationContextProvider.getApplicationContext();
			VerificaPdfBean formatoBean = (VerificaPdfBean) context.getBean("VerificaPdfBean");
			
			// VERIFICA FORMATO PDF CON COMMENTI = ATTIVA
			if("true".equalsIgnoreCase(formatoBean.getAttivaVerificaCommenti())) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchBeanDefinitionException e) {
			//log.error(e);
			logger.warn("Verifica pdf con commenti non attiva, bean di configurazione non presente");
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			
		}

		return false;
	}
	
	public static void main(String[] args) {
		String filePath = "C:/Users/TESAURO/Downloads/All risk 2020 con quietanza 2021.pdf";
		filePath = "C:/Users/TESAURO/Desktop/FileEstratto-1223928707796676054.pdf";
		filePath = "C:/Users/TESAURO/Desktop/FileEstratto-9073785111623771608.pdf";
		filePath = "C:/Users/TESAURO/Downloads/Ricevuta di versamento_bonificoSingolo-3_problema.pdf";
		filePath = "C:/Users/TESAURO/Downloads/FileEstratto-1506504980292632273.file";
		
		File file = new File(filePath);
		
		try {
			PdfReader pdfReader = new PdfReader(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//CheckPdfCommentiFileBean pages = PdfCommentiUtil.isPdfConCommenti(filePath);
		//System.out.println(pages.getFlgContieneCommenti());
		//System.out.println(pages.getPageWithCommentBox().size());
		
//		List<Integer> listPagesToConvert = new ArrayList<Integer>();
//		//listPagesToConvert.add(new Integer(3));
//		listPagesToConvert = pages.getPageWithCommentBox();
//		try {
//			System.out.println(rewriteDocument(file, listPagesToConvert , false));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		String testo="acbcbccxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
//		try{
//			PdfReader pdfReader = new PdfReader(new FileInputStream(file));
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
//			
//			int totalPages = pdfReader.getNumberOfPages();
//			System.out.println(totalPages);
//			
//			PdfContentByte over = pdfStamper.getOverContent(3);
//			BaseFont bf = BaseFont.createFont("Courier", BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//			over.setFontAndSize(bf, 20.0f);
//			
//			float dimensioneSingoloCarattere = bf.getWidthPoint(testo.charAt(0), 10.0f);
//			System.out.println("dimensioneSingoloCarattere::::  "  + dimensioneSingoloCarattere);
//			
//			System.out.println(pdfStamper.getReader().getPageSize(3).getWidth() );
//			System.out.println(pdfStamper.getReader().getPageSize(3).getHeight() );
//
//			System.out.println(pdfStamper.getReader().getPageSize(3).getRotation() );
//
//			int alignText = 0;
//			float orientationText = 0;
//			over.beginText();
//			over.showTextAligned(alignText, testo, 50, 50, orientationText);
//			over.endText();
//			
//			pdfStamper.getWriter().createXmpMetadata();
//
//			pdfStamper.close();
//			pdfReader.close();
//
//			// File fileTimbrato = new File( "provaTimbrata.pdf");
//			File fileTimbrato = new File(file.getParent() + "/" + "output/abc.pdf");
//			FileOutputStream fileOutputStream = new FileOutputStream(fileTimbrato);
//			IOUtils.write(baos.toByteArray(), fileOutputStream);
//			
//		} catch (Exception e){
//			e.printStackTrace();
//		}
		
	}
	
	
}
