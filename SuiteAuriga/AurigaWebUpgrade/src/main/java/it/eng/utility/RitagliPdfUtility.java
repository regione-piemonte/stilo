/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.text.pdf.pdfcleanup.PdfCleanUpProcessor;

import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.PreviewFileOmissisBean;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.RitagliDocBean;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.RitaglioBean;

public class RitagliPdfUtility {
	
	private static final float ratio = 0.7503f;
	private static final int defaultDPI = 185; //prima era 300 ma le immagina venivano troppo pesanti
	private static final String esitoOK = "OK";
	private static final String esitoKO = "KO";
	
	private static Logger mLogger = Logger.getLogger(RitagliPdfUtility.class);

	
	/**
	 * Metodo per l'applicazione di rettangoli sulle aree selezionate dal viewer.
	 * E' una semplice applicazione che non comporta la pulizia del testo sottostante. 
	 * Il file su cui viene apposto il rettangolo viene poi convertito in immagine e 
	 * riconvertito in PDF.
	 * 
	 * @param filePdfOriginale
	 * @param ritagliJson
	 * @return
	 * @throws Exception
	 */
	public static File addRitagliNotClean(File filePdfOriginale, String ritagliJson, boolean fromScannedExc) throws Exception {
		
		OutputStream os = null;
		try {
			List<RitagliDocBean> listRitagli = fromJsonToObject(ritagliJson);
			HashMap<Integer, List<RitaglioBean>> mappaRitagli = fromListToMap(listRitagli);
	
			File fileConRitagli = File.createTempFile("ritagliImagePdf", ".pdf");
			os = new FileOutputStream(fileConRitagli);
			BufferedOutputStream fileConRitagliBOS = new BufferedOutputStream(os);
	
			addRectangleOnPdf(filePdfOriginale, mappaRitagli, fileConRitagliBOS);
			
			File fileConRitagliRiconvertito = riconvertiPdf(fileConRitagli, fromScannedExc, new ArrayList<>(mappaRitagli.keySet()));
			
			return fileConRitagliRiconvertito;
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (Exception e) {}
			}
		}
	}

	/**
	 * Metodo per la doppia conversione: viene convertito in immagine e poi
	 * riconvertito in PDF.
	 *  
	 * 
	 * @param fileConRitagli
	 * @param fromScannedExc 
	 * @param listPageToConvert 
	 * @return
	 * @throws Exception
	 */
	public static File riconvertiPdf(File fileConRitagli, boolean fromScannedExc, ArrayList<Integer> listPageToConvert) throws Exception {
//		List<String> listaImages = fromPdfToImage(fileConRitagli);
//		return PdfUtility.fromImageToPdf(listaImages, fromScannedExc);
		
		return RitagliPdfUtility.rewriteDocument(fileConRitagli, listPageToConvert, fromScannedExc);
	}


	/**
	 * Metodo che cancella il testo del pdf in corrispondenza delle aree selezionate nel viewer
	 * 
	 * 
	 * @param filePdfOriginale
	 * @param pPreviewFileOmissisBean
	 * @return
	 * @throws Exception
	 */
	public static File addRitagliAndClean(File filePdfOriginale, PreviewFileOmissisBean pPreviewFileOmissisBean) throws Exception {
		
		InputStream is = null;
		OutputStream os = null;

		File fileConRitagli = File.createTempFile("ritagliPdf", ".pdf");
		is = new FileInputStream(filePdfOriginale);
		PdfReader readerFileOriginale = new PdfReader(is);
		os = new FileOutputStream(fileConRitagli);
		PdfStamper stamperFileConRitagli = new PdfStamper(readerFileOriginale, os);
		stamperFileConRitagli.setRotateContents(false);

		// Recupero la lista dei ritagli e per ogni ritaglio popolo la lista cleanUpLocations
		String ritagliJson = pPreviewFileOmissisBean.getRitagli();
		List<RitagliDocBean> listaRitagli = fromJsonToObject(ritagliJson);

		ArrayList<PdfCleanUpLocation> cleanUpLocations = new ArrayList<>();
		for (RitagliDocBean ritaglioDoc : listaRitagli) {
			int numeroPagina = ritaglioDoc.getNumeroPagina();
			
			for (RitaglioBean ritaglio : ritaglioDoc.getRitaglioBean()) {
				Rectangle pSize = readerFileOriginale.getPageSizeWithRotation(numeroPagina);
				Rectangle rect = new Rectangle(ritaglio.getLlx() * ratio, ritaglio.getLly() * ratio, ritaglio.getUrx() * ratio, ritaglio.getUry() * ratio);
				// costruisco il rettangolo sotto cui cancellare il testo in base alla rotazione della pagina del file originale 
				// (nel viewer viene sempre mostrata verticale anche se in realtà è ruotato)
				switch (pSize.getRotation()) {
				case 0:
					rect = new Rectangle(ritaglio.getLlx() * ratio, ritaglio.getLly() * ratio, ritaglio.getUrx() * ratio, ritaglio.getUry() * ratio);
					break;
				case 90:
					rect = new Rectangle(pSize.getHeight() - ritaglio.getLly() * ratio, ritaglio.getLlx() * ratio, pSize.getHeight() - ritaglio.getUry() * ratio, ritaglio.getUrx() * ratio);
					break;
				case 180:
					rect = new Rectangle(pSize.getWidth() - ritaglio.getLlx() * ratio, pSize.getHeight() - ritaglio.getLly() * ratio, pSize.getWidth() - ritaglio.getUrx() * ratio, pSize.getHeight() - ritaglio.getUry() * ratio);
					break;
				case 270:
					rect = new Rectangle(ritaglio.getLly() * ratio, pSize.getWidth() - ritaglio.getLlx() * ratio, ritaglio.getUry() * ratio, pSize.getWidth() - ritaglio.getUrx() * ratio);
					break;
				default:
					break;
				}
				cleanUpLocations.add(new PdfCleanUpLocation(numeroPagina, rect, BaseColor.GRAY));
			}
		}

		PdfCleanUpProcessor cleaner = new PdfCleanUpProcessor(cleanUpLocations, stamperFileConRitagli);
		try {
			// provo a pulire il testo sottostante le aree indicate.
			pPreviewFileOmissisBean.setTryToCleanText(cleaner.cleanUp(true));
		} catch (RuntimeException e) {
			// Se va in errore per "RuntimeException: ImageReadException" 
			// stiamo cercando di pulire un particolare pdf risultante dalla scansione di un file. 
			// Non tutte le scansioni vanno in eccezione. In questo caso appongo la peccetta in modalità immagine
			if (StringUtils.isNotBlank(e.getCause().getMessage()) && e.getCause().getMessage().contains("ImageReadException")) {
				pPreviewFileOmissisBean.setTryToCleanText(false);
				return addRitagliNotClean(filePdfOriginale, ritagliJson, true);
			} else if(e.getCause().getMessage().contains("ColorSpaceException")) {
//				/*converto il file pdf in immagini*/
//				List<String> listaImagePath = fromPdfToImage(filePdfOriginale);					
//				/*converto le immagini in pdf*/
//				File fileFromImage = PdfUtility.fromImageToPdf(listaImagePath, true);
				File rewrittenFile = RitagliPdfUtility.rewriteDocument(filePdfOriginale, ritagliJson);
				/*applico le peccette*/
				return addRitagliNotClean(rewrittenFile, ritagliJson, true);
			} else {
				mLogger.error(e.getMessage(), e);
				throw new Exception(e);
			}
			
		} finally {
			stamperFileConRitagli.close();
			if(os != null) {
				try {
				    os.close();
				} catch (Exception e) {}
			}
			readerFileOriginale.close();
			if(is != null) {
				try {
					is.close(); 
				} catch (Exception e) {}
			}
		}

		return fileConRitagli;
	}	

	/**
	 * Metodo che confronta il testo delle pagine del file prima e dopo l'apposizione della peccetta.
	 * L'apposizione della peccetta deve cancellare il testo, quindi mi aspetto che il testo della pagina su cui
	 * è stata applicata una peccetta sia cambiato rispetto alla versione originale.
	 * 
	 * Il controllo non viene effettuato sulle pagine che successivamente verranno rimosse.
	 * 
	 * 
	 * @param fileOriginalePdf
	 * @param fileConPecettePdf
	 * @param pPreviewFileOmissisBean
	 * @return
	 * @throws Exception
	 */
	public static boolean isCorruptedFile(File fileOriginalePdf, File fileConPecettePdf, PreviewFileOmissisBean pPreviewFileOmissisBean) throws Exception {
		
		InputStream isBP = null;
		InputStream isAP = null;
		try {
			isBP = new FileInputStream(fileOriginalePdf);
			PdfReader readerBeforePecette = new PdfReader(isBP);
			isAP = new FileInputStream(fileConPecettePdf);
			PdfReader readerAfterPecette = new PdfReader(isAP); 
			
			List<RitagliDocBean> lListaPeccette = fromJsonToObject(pPreviewFileOmissisBean.getRitagli());
			HashMap<Integer, List<RitaglioBean>> lMappaPeccette = fromListToMap(lListaPeccette);
			String[] pagesToRemove = pPreviewFileOmissisBean.getPagesToRemove().split(",");
			String[] esitoPeccetta = new String[lMappaPeccette.size()];
			
			int count = 0;
			for (Integer numeroPagina : lMappaPeccette.keySet()) {
				// confronto il testo delle pagine prima e dopo 
				// se la pagina NON è tra quelle che poi verranno rimosse
				if (contains(pagesToRemove, numeroPagina.toString())) {
					// se la pagina verrà poi rimossa non controllo
					esitoPeccetta[count] = esitoOK;
				} else {
					// se la pagina NON verrà poi rimossa non controllo
					String textBeforePecette = PdfTextExtractor.getTextFromPage(readerBeforePecette, numeroPagina, new SimpleTextExtractionStrategy()).replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
					String textAfterPecette = PdfTextExtractor.getTextFromPage(readerAfterPecette, numeroPagina, new SimpleTextExtractionStrategy()).replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
					if (!textBeforePecette.equals(textAfterPecette)) {
						// se il testo è cambiato
						esitoPeccetta[count] = esitoOK;
					} else {
						// se il testo non è cambiato e la pagina NON è tra quelle che poi verranno rimosse
						esitoPeccetta[count] = esitoKO;
					}
				}
				
				count++;
			}
			readerAfterPecette.close();
			readerBeforePecette.close();
			
			return contains(esitoPeccetta, esitoKO);
		} finally {
			if(isAP != null) {
				try {
					isAP.close();
				} catch (Exception e) {}
			}
			if(isBP != null) {
				try {
					isBP.close(); 
				} catch (Exception e) {}
			}
		}
	}
	
	public static File rewriteDocument(File filePdf, String ritagliJson) throws Exception {
		return rewriteDocument( filePdf, ritagliJson, false);
	}
	
	public static File rewriteDocument(File filePdf, String ritagliJson,  boolean fromScannedExc) throws Exception {
		List<RitagliDocBean> listRitagli = fromJsonToObject(ritagliJson);
		HashMap<Integer, List<RitaglioBean>> mappaRitagli = fromListToMap(listRitagli);

		List<Integer> listaPagesToConvert = new ArrayList<>(mappaRitagli.keySet());
		
		return rewriteDocument(filePdf, listaPagesToConvert, fromScannedExc);
	}
	
	
	public static File rewriteDocument(File filePdf, List<Integer> listaPagesToConvert) throws Exception {
		return rewriteDocument(filePdf, listaPagesToConvert, false);
	}
	
	public static File rewriteDocument(File filePdf, List<Integer> listaPagesToConvert, boolean fromScannedExc) throws Exception {
		
		File documentoRiscritto = File.createTempFile("DocRiscritto", ".pdf");
		
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
			if(listaPagesToConvert.contains(i)) {
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
		
		if (document.isEncrypted()) {
			document.setAllSecurityToBeRemoved(true);
		}
		   
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
	
	/**
	 * Metodo che prende in ingresso un file PDF e ritorna una lista di immagini
	 * 
	 * 
	 * @param filePdf
	 * @return
	 * @throws Exception
	 */
	public static List<String> fromPdfToImage(File filePdf) throws Exception {
		
		List<String> listaImages = new ArrayList<>();

		PDDocument document = PDDocument.load(filePdf);
		
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
		document.close();

		return listaImages;
	}
	
	private static void addRectangleOnPdf(File filePdf, HashMap<Integer, List<RitaglioBean>> mappaRitagli,
			OutputStream output) throws Exception {
		
		Document document = new Document();

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
			addPageToDocumentWithRect(document, writer, cb, reader, i, false, mappaRitagli);
		}

		output.flush();
		document.close();
		output.close();
	}

	private static void addPageToDocumentWithRect(Document document, PdfWriter writer, PdfContentByte cb,
			PdfReader reader, int pageNumber, boolean forceA4, HashMap<Integer, List<RitaglioBean>> mappaRitagli) {
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

		if (mappaRitagli.get(pageNumber) != null) {
			addRitaglioOnPage(cb, mappaRitagli.get(pageNumber));
		}

	}

	private static void addRitaglioOnPage(PdfContentByte cb, List<RitaglioBean> listaRitagli) {

		for (RitaglioBean ritalgioBean : listaRitagli) {

			Rectangle rect = new Rectangle(ritalgioBean.getLlx() * ratio, ritalgioBean.getLly() * ratio, ritalgioBean.getUrx() * ratio, ritalgioBean.getUry() * ratio);

			rect.setBackgroundColor(BaseColor.GRAY);
			rect.setBorderColor(BaseColor.GRAY);

			cb.saveState();
			cb.rectangle(rect);
			cb.stroke();
			cb.restoreState();
		}

	}
	
	/**
	 * Metodo di utiliti per verificare se un array di stringhe (strings) contiene una data stringa (stringToCheck)
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	private static boolean contains(String[] strings, String stringToCheck) {
		if (strings == null) {
			return false;
		} else {
			for (int i = 0; i < strings.length; i++) {
				if (strings[i].equals(stringToCheck)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Metodo per trasformare il json in lista contentente le coordinate dei ritagli e i numeri di pagina su cui sono applicati
	 * 
	 * @param jsonRitagli
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static List<RitagliDocBean> fromJsonToObject(String jsonRitagli) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();

		List<RitagliDocBean> ritagli = mapper.readValue(jsonRitagli, new TypeReference<List<RitagliDocBean>>() {});

		return ritagli;
	}

	/**
	 * Metodo per trasformare la lista di ritagli in una mappa, dove la chiave è il numero pagina e il valore la lista
	 * di oggetti contenenti le coordinate dei ritagli 
	 * 
	 * @param listaRitagli
	 * @return
	 */
	private static HashMap<Integer, List<RitaglioBean>> fromListToMap(List<RitagliDocBean> listaRitagli) {
		HashMap<Integer, List<RitaglioBean>> mappaRitagli = new HashMap<>();

		for (RitagliDocBean ritaglio : listaRitagli) {
			mappaRitagli.put(ritaglio.getNumeroPagina(), ritaglio.getRitaglioBean());
		}

		return mappaRitagli;
	}
}