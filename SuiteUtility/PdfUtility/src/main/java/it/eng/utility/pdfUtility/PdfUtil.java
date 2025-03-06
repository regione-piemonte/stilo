package it.eng.utility.pdfUtility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.bean.PdfConstant;
import it.eng.utility.pdfUtility.pdfA.PdfAUtil;


public class PdfUtil {

	public static final Logger logger = LogManager.getLogger(PdfUtil.class);
	
	private static final int defaultDPI = 185; //prima era 300 ma le immagina venivano troppo pesanti

	public static Integer recuperaNumeroPagine(File file) throws Exception{
		PdfReader readerPdf = null;
		try {
			readerPdf = new PdfReader( file.getAbsolutePath() );
			Integer numPages = readerPdf.getNumberOfPages();
			return numPages;
		} catch (IOException e) {
			throw new Exception("Errore lettura pdf, forse non si tratta di un pdf");
		}finally {
			if( readerPdf!=null ){
				readerPdf.close();
			}
		}
	}
	
	public static File rewriteFile(File inputFile) throws Exception {
		List<Integer> pagesToConvertInImages = new ArrayList<Integer>();
		return rewriteFile(inputFile, pagesToConvertInImages);
	}
	
	public static File rewriteFile(File inputFile, List<Integer> listPagesToConvert) throws Exception {

		File destTempFile = File.createTempFile("tempFile", "." + PdfConstant.pdfExtension , inputFile.getParentFile());
		FileOutputStream destTempStream = new FileOutputStream( destTempFile );

		Document document = new Document();
		PdfWriter writer;

		try {
			// il file generato dovrà essere un PDFA se quello di origine era un PDFA, altrimenti no.
			PdfBean pdfBean = PdfAUtil.isPdfA( inputFile );
			if ( pdfBean!=null && pdfBean.getPdfA()!=null && pdfBean.getPdfA()) {
				writer = PdfAWriter.getInstance(document, destTempStream, PdfAConformanceLevel.PDF_A_3U );
			} else {
				writer = PdfWriter.getInstance(document, destTempStream );
			}

			writer.setTagged();
			writer.setLanguage("it");
			writer.setLinearPageMode();
			writer.createXmpMetadata();

			document.open();

			PdfContentByte cb = writer.getDirectContent();

			PdfReader reader = new PdfReader( inputFile.getAbsolutePath() );

			// Questo parametro mi fa bypassare la password in modifica presente sul file e quindi mi permette di leggerlo con PdfReader
			reader.unethicalreading = true;

			// Scorro le pagine da copiare
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				if(listPagesToConvert.contains(i)) {
					rewritePage(inputFile, document, writer, cb, reader, i, false, true);
				} else {
					rewritePage(inputFile, document, writer, cb, reader, i, false, false);
				}

			}

			destTempStream.flush();
			document.close();
			destTempStream.close();

			return destTempFile;
		} catch (Exception e) {
			throw new Exception("Errore durante la copia del file protetto con i permessi: " + e.getMessage(), e);
		}

	}

	private static void rewritePage(File filePdf, Document document, PdfWriter writer, 
			PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4, boolean convertPageToImage) throws Exception {

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

		if (convertPageToImage) {
			convertPageToImage(filePdf, pageNumber, document);
		} else {
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
	}

	private static void convertPageToImage(File filePdf, int pageNumber, Document documentPrincipal)
			throws Exception {

		PDDocument document = PDDocument.load(filePdf);
		   
		PDFRenderer pdfRenderer = new PDFRenderer(document);

		File imageTemp = File.createTempFile("tempImage", ".jpg", filePdf.getParentFile());
		File imageCompressed = File.createTempFile("tempImageCompressed", ".jpg", filePdf.getParentFile());

		BufferedImage bImage = pdfRenderer.renderImageWithDPI(pageNumber-1, PdfConstant.defaultDPI, ImageType.RGB);
		ImageIOUtil.writeImage(bImage, imageTemp.getPath(), PdfConstant.defaultDPI);

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

	public static File fromPdfToPdfImage(File fileConRitagli) throws Exception {
		List<String> listaImages = fromPdfToImage(fileConRitagli);
		return fromImageToPdf(listaImages, false);
	}
	
	public static List<String> fromPdfToImage(File filePdf) throws Exception {
		List<String> listaImages = new ArrayList<>();

		PDDocument document = null;
		
		try {
			document = PDDocument.load(filePdf);
			
			if (document.isEncrypted()) {
				document.setAllSecurityToBeRemoved(true);
			}		
			
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
	
	//metodo che verifica se itext puo aprire il file in lettura e di conseguenza si possono fare le varei operazioni di timbratura, unione file ecc
	public static boolean checkPdfReadItext(String uriFile) {
		PdfReader reader = null;
		try {
			reader = new PdfReader(uriFile);
		}catch(Exception ex) {
			logger.warn("Il file caricato in upload non puo essere lavorato da itext per il seguente motivo: " + ex.getMessage(), ex);
			return false;
		} finally {
			if( reader!=null){
				reader.close();
			}
		}

		return true;
	}
	
}
