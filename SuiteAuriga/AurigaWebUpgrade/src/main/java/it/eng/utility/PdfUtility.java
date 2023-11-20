/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfCopy.PageStamp;

public class PdfUtility {
	
	/**
	 * Metodo che dato un file PDF in ingresso ritorna il file convertito in formato PDFA
	 * 
	 * @param fileOriginalePdf
	 * @return
	 * @throws Exception
	 */
	public static File convertiPdfToPdfA3U(File fileOriginalePdf) throws Exception {

		InputStream fileOriginalePdfIS = FileUtils.openInputStream(fileOriginalePdf);

		File filePdfA = File.createTempFile("fileTemporaneoPDFA", ".pdf");

		convertiPdfToPdfA3U(fileOriginalePdfIS, new FileOutputStream(filePdfA));

		return filePdfA;	
	}
	
	/**
	 * Metodo che dato un file PDF in ingresso ritorna il file convertito in formato PDFA
	 * 
	 * @param fileOriginalePdf
	 * @return
	 * @throws Exception
	 */
	public static File convertiPdfToPdfA1(File fileOriginalePdf) throws Exception {

		InputStream fileOriginalePdfIS = FileUtils.openInputStream(fileOriginalePdf);

		File filePdfA = File.createTempFile("fileTemporaneoPDFA", ".pdf");

		convertiPdfToPdfA1(fileOriginalePdfIS, new FileOutputStream(filePdfA));

		return filePdfA;	
	}
	
	/**
	 * Metodo che dato l'inputStream di un file PDF e l'outputStream di un file temporaneo con estensione pdf, 
	 * scrive sull'OS il file PDF convertito in PDFA
	 * 
	 * @param fileOriginalePdfIS
	 * @param fileConvertitoOS
	 * @throws Exception
	 */
	public static void convertiPdfToPdfA3U(InputStream fileOriginalePdfIS, OutputStream fileConvertitoOS) throws Exception {
		Document document = new Document();

		PdfAWriter writer = PdfAWriter.getInstance(document, fileConvertitoOS, PdfAConformanceLevel.PDF_A_3U); 
		writer.setTagged();
		writer.setLanguage("it");
		writer.setLinearPageMode();
		writer.createXmpMetadata();

		document.open();

		PdfContentByte cb = writer.getDirectContent();

		// Collego il reader al file
		PdfReader reader = new PdfReader(fileOriginalePdfIS);
		// Scorro le pagine da copiare
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {                            
			addPageToDocument(document, writer, cb, reader, i, false);
		}

		fileConvertitoOS.flush();
		document.close();
		fileConvertitoOS.close();
		fileOriginalePdfIS.close();
		reader.close();
	}
	
	/**
	 * Metodo che dato l'inputStream di un file PDF e l'outputStream di un file temporaneo con estensione pdf, 
	 * scrive sull'OS il file PDF convertito in PDFA
	 * 
	 * @param fileOriginalePdfIS
	 * @param fileConvertitoOS
	 * @throws Exception
	 */
	public static File convertiPdfToPdfA1(InputStream fileOriginalePdfIS, OutputStream fileConvertitoOS) throws DocumentException, IOException, URISyntaxException {

		File outputPdf1A = File.createTempFile("conv1A", ".pdf");

		com.itextpdf.text.Document document = new com.itextpdf.text.Document();

//		URL url = PdfUtility.class.getResource("srgb_color_space_profile.icm");
//		System.out.println(url);
		InputStream iccFile = PdfUtility.class.getResourceAsStream("srgb_color_space_profile.icm");

		ICC_Profile icc = ICC_Profile.getInstance(iccFile);

		PdfAWriter writer = PdfAWriter.getInstance(document, fileConvertitoOS, PdfAConformanceLevel.PDF_A_1A);
		//PdfAWriter writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U);
		writer.createXmpMetadata();
		writer.setTagged();
		writer.setLanguage("it");
		writer.setLinearPageMode();

		document.open();

		writer.setOutputIntents("Custom", "", "http://www.color.org", "adobe rgb", icc);

		PdfContentByte cb = writer.getDirectContent();

		// Collego il reader al file
		PdfReader reader = new PdfReader(fileOriginalePdfIS);

		// Scorro le pagine da copiare
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			addPageToDocument(document, writer, cb, reader, i, false);
		}

		document.close();
		fileConvertitoOS.flush();
		fileConvertitoOS.close();
		fileOriginalePdfIS.close();
		reader.close();

		return outputPdf1A;
	}
	
	/**
	 *  Metodo che a partire da una lista di path di immagini crea un file PDF
	 * 
	 * @param listaPathImage
	 * @param fromImageException 
	 * @param fileOriginale 
	 * @return
	 * @throws Exception
	 */
	public static File fromImageToPdf(List<String> listaPathImage, boolean fromScannedException) throws Exception {
		
		OutputStream os = null;
		try {
		
			File pdfFinale = File.createTempFile("pdfTemp", ".pdf");
	
	        Document document;
	        if (fromScannedException) {
	        	document = new Document(PageSize.A4,0,0,0,0);
	        } else {
	        	document = new Document();
	        }
	        
	        os = new FileOutputStream(pdfFinale);
			PdfWriter.getInstance(document,os);
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
		
			document.close();
	
			return pdfFinale;
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (Exception e) {}
			}
		}
	}

	private static void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		// Federico Cacco 28.01.2016
		// Quando si concatenano pagine che derivano da scansioni si hanno dei problemi di rotazione, in
		// quanto la scansione solitamente produce una immagine orizzontale che viene poi in caso
		// ruotata nel verso giusto prima di essere inserita nel pdf finale. 
		// Devo quindi copiare la pagina tenendo conto di questa rotazione,
		// altrimenti rischio di inserirla storta
		//
		// Questa logica è presente nche nella classe it.eng.auriga.ui.module.layout.server.common.MergeDocument di ListExportUtility
		//
		// cb.addTemplate(page, 0, 0);

		// Verifico la rotazione della pagina corrente
		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4){
				document.setPageSize(PageSize.A4.rotate());
			}else{
				document.setPageSize(psize);
			}
		} else {
			if (forceA4){
				document.setPageSize(PageSize.A4);
			}else{
				document.setPageSize(psize);
			}
		}

		// Creo una nuova pagina nel document in cui copiare la pagina corrente
		document.newPage();
		// Raddrizzo l'immagine a seconda della rotazione

		switch (psize.getRotation()){
		case 0:
			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			break;
		case 90:
			cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
			break;
		case 180:
			cb.addTemplate(page,-1f, 0, 0, -1f, psize.getWidth(),psize.getHeight());
			break;
		case 270:
			cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * Metodo per aggiungere del testo ad un file pdf
	 * 
	 * @param copy com.itextpdf.text.pdf.PdfCopy
	 * @param text stringa da scrivere sul pdf
	 * @param font font da applicare alla stringa che si vuole scrivere (com.itextpdf.text.Font)
	 * @param importedPage com.itextpdf.text.pdf.PdfImportedPage
	 * @param margine stringa che identifica la posizione del testo nella pagina. Default: "basso dx". 
	 * Opzioni verticali: "alto" "basso"(default). Opzioni orizzontali: "sx" "dx"(default) "centrato". Le opzioni sono combinabili tra loro.
	 * es: "alto sx" o "basso centrato" etc
	 * @param orientamento stringa che definisce l'orientamento del testo inserito. Opzioni: "orizzontale"(default), "verticale"
	 * @param pageSizeWithRotation com.itextpdf.text.Rectangle ottenuto con il metodo reader.getPageSizeWithRotation(numPage), dove reader è un'istanza di com.itextpdf.text.pdf.Reader
	 * @throws IOException
	 */
	public static void addTesto(PdfCopy copy, String text, Font font,
			PdfImportedPage importedPage, String margine, String orientamento, Rectangle pageSizeWithRotation) throws IOException {
		
		PageStamp stampPdf = copy.createPageStamp( importedPage );
		PdfContentByte canvas = stampPdf.getOverContent();
		Phrase phrase = new Phrase(text, font);
		float phraseWidth = ColumnText.getWidth( phrase );
		float y = 0; 
		float x = 0;
		float rotation = 0;
		
		if (orientamento.equalsIgnoreCase("verticale")) {

			rotation = 90;
			if (margine.toLowerCase().contains("alto")) {
				y = pageSizeWithRotation.getHeight() - phraseWidth - 15;
			} else {
				y = 15;
			}

			if (margine.toLowerCase().contains("sx")) {
				x = 15;
			} else if (margine.toLowerCase().contains("centrato")) {
				x = (pageSizeWithRotation.getWidth() - 15 )/2;
			} else {
				x = pageSizeWithRotation.getWidth() - 15;
			}

		} else {
			rotation = 0;
			
			if (margine.toLowerCase().contains("alto")) {
				y = pageSizeWithRotation.getHeight() - 15;
			} else {
				y = 15;
			}
			
			if (margine.toLowerCase().contains("sx")) {
				x = 15;
			} else if (margine.toLowerCase().contains("centrato")) {
				x = (pageSizeWithRotation.getWidth() - 15 - phraseWidth)/2;
			} else {
				x = pageSizeWithRotation.getWidth() - 15 - phraseWidth;
			}
			
		}
				
		ColumnText.showTextAligned( 
				canvas, 
				Element.ALIGN_LEFT,
				phrase,
				x,
				y,
				rotation );
		stampPdf.alterContents();
	}
	
	/**
	 * Metodo per identificare file con dimensioni maggiori di un file A4
	 * 
	 * @param pageSizeWithRotation com.itextpdf.text.Rectangle ottenuto con il metodo reader.getPageSizeWithRotation(numPage), dove reader è un'istanza di com.itextpdf.text.pdf.Reader
	 * @return
	 */
	public static boolean isFileMaggioreA4(Rectangle pageSizeWithRotation) {
		if ((pageSizeWithRotation.getWidth() > PageSize.A4.getWidth() && pageSizeWithRotation.getHeight() > PageSize.A4.getHeight())
				|| (pageSizeWithRotation.getHeight() > PageSize.A4.getWidth() && pageSizeWithRotation.getWidth() > PageSize.A4.getHeight())){
			return true;
		} else {
			return false;

		}
	}
	
	/**
	 * Metodo per ruotare un file PDF
	 * 
	 * @param filePdfOriginale
	 * @param rotateDegree
	 * @throws IOException
	 * @throws DocumentException
	 * @return
	 */
    public static File rotatePdf(File filePdfOriginale,int rotateDegree) throws IOException, DocumentException {
    	
    	PdfReader readerFileOriginale = null;
    	PdfStamper stamper = null;
    	File fileRuotato = File.createTempFile("ruotatoPdf", ".pdf");
    	InputStream is = null;
    	OutputStream os = null;
    	try {
    		is = new FileInputStream(filePdfOriginale);
	        readerFileOriginale = new PdfReader(is);
	        os = new FileOutputStream(fileRuotato);
	        stamper = new PdfStamper(readerFileOriginale,os);
	        int n = readerFileOriginale.getNumberOfPages();
	        PdfDictionary page;
	        PdfNumber rotate;
	        for (int p = 1; p <= n; p++) {
	            page = readerFileOriginale.getPageN(p);
	            rotate = page.getAsNumber(PdfName.ROTATE);
	            if (rotate == null) {
	                page.put(PdfName.ROTATE, new PdfNumber(rotateDegree));
	            }
	            else {
	                page.put(PdfName.ROTATE, new PdfNumber((rotate.intValue() + rotateDegree) % 360));
	            }
	        }
    	} catch (Exception e) {
    		throw e;
    	} finally {
    		stamper.close();
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
        return fileRuotato;
    }
    
    public static final String SRC = "C:\\Users\\fabcasta\\Downloads\\SIRTEC_Aut._provv_Mn_LA_SUPREMA_signed_signed.pdf";
    public static final String DEST = "C:\\Users\\fabcasta\\Downloads\\ruotato.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        PdfUtility.manipulatePdf(SRC, DEST);
    }


    public static void manipulatePdf(String src, String dest) throws IOException, DocumentException {
    	
    	OutputStream os = null;
    	try {
	        PdfReader reader = new PdfReader(src);
	        int n = reader.getNumberOfPages();
	        PdfDictionary page;
	        PdfNumber rotate;
	        for (int p = 1; p <= n; p++) {
	            page = reader.getPageN(p);
	            rotate = page.getAsNumber(PdfName.ROTATE);
	            if (rotate == null) {
	                page.put(PdfName.ROTATE, new PdfNumber(90));
	            }
	            else {
	                page.put(PdfName.ROTATE, new PdfNumber((rotate.intValue() + 90) % 360));
	            }
	        }
	        os = new FileOutputStream(dest);
	        PdfStamper stamper = new PdfStamper(reader,os);
	        stamper.close();
	        reader.close();
    	} finally {
    		if(os != null) {
				try {
				    os.close();
				} catch (Exception e) {}
			}
		}
    }
}