/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Permette di generare un unico file pdf a partire dai file pdf forniti in ingresso
 *
 */
public class MergeDocument {

	public MergeDocument() {
	}
	
	public void creaFile() throws DocumentException, Exception{
		Document document = new Document();
		File tmpWorkDirectoryFile = new File("c:/tempMerge/");
		File returnFile = new File(tmpWorkDirectoryFile + File.separator +"MergeFiles.pdf");
        
        PdfAWriter writer = PdfAWriter.getInstance(document,
                new FileOutputStream(returnFile), PdfAConformanceLevel.PDF_A_1A);
        writer.setTagged();
        writer.setLanguage("it");
        writer.setLinearPageMode();
        writer.createXmpMetadata();
        
        document.open();
        BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.WINANSI, true);
        Font f = new Font(bf, 12); 
        document.add(new Paragraph("Hello World!",f));
        document.close();
	}

	public static MergeDocument newInstance() {
		MergeDocument instance = new MergeDocument();
		return instance;
	}
	
	/**
	 * Dati i files in ingresso ritorna un file che rappresenta il pdf generato dalla fusione dei file specificati 
	 * @param files File da unire
	 * @return Il file ottenuto unendo i file in ingresso
	 * @throws Exception
	 */
	public File mergeDocuments(File[] files) throws Exception{
		
		File mergedPdf = File.createTempFile("mergedPdf", ".pdf");
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(mergedPdf)); 
		
		mergeDocument(files, output);
		
		return mergedPdf;
	}
	
	/**
	 * Associa allo stream fornito il file pdf risultato della concatenazione dei file specificati
	 * @param mergeFiles File da unire
	 * @param output Stream a cui associre il pdf ottenuto dall'unione dei file in ingresso
	 * @throws Exception
	 */
	@Deprecated
	public void mergeDocument(File[] mergeFiles, OutputStream output) throws Exception {
		// Effettuo il merge dei documenti PDF
		List<File> listMergeFiles = Arrays.asList(mergeFiles);
		concatPDFs(listMergeFiles, output, true);
	}
	
	/**
	 * Associa allo stream fornito il file pdf risultato della concatenazione dei file specificati
	 * @param mergeFiles File da unire
	 * @param output Stream a cui associre il pdf ottenuto dall'unione dei file in ingresso
	 * @param forceA4 Se true viene forzato l'utilizzo del formato A4
	 * @throws Exception
	 */
	@Deprecated
	public void mergeDocument(File[] mergeFiles, OutputStream output, boolean forceA4) throws Exception {
		// Effettuo il merge dei documenti PDF
		List<File> listMergeFiles = Arrays.asList(mergeFiles);
		concatPDFs(listMergeFiles, output, forceA4);
	}

	/**
	 * Associa allo stream fornito il file pdf risultato della concatenazione dei file specificati
	 * @param mergeFiles File da unire
	 * @param output Stream a cui associre il pdf ottenuto dall'unione dei file in ingresso
	 * @throws Exception
	 */
	public void mergeDocument(Collection<File> mergeFiles, OutputStream output) throws Exception {
		// Effettuo il merge dei documenti PDF
		concatPDFs(mergeFiles, output, true);
	}
	
	/**
	 * Associa allo stream fornito il file pdf risultato della concatenazione dei file specificati
	 * @param mergeFiles File da unire
	 * @param output Stream a cui associre il pdf ottenuto dall'unione dei file in ingresso
	 * @param forceA4 Se true viene forzato l'utilizzo del formato A4
	 * @throws Exception
	 */
	public void mergeDocument(Collection<File> mergeFiles, OutputStream output, boolean forceA4) throws Exception {
		// Effettuo il merge dei documenti PDF
		concatPDFs(mergeFiles, output, forceA4);
	}
	
	/**
	 * Associa allo stream fornito il file pdf risultato della concatenazione dei file specificati
	 * 
	 * @param mergeFiles
	 * @param output
	 * @param forceA4
	 * @param pdfA
	 * @throws Exception
	 */
	public void mergeDocument(Collection<File> mergeFiles, OutputStream output, boolean forceA4, boolean pdfA) throws Exception {
		// Effettuo il merge dei documenti PDF
		concatPDFs(mergeFiles, output, forceA4, pdfA);
	}
	
	/**
	 * Associa allo stream fornito il file pdf risultato dalle solo prime pagine del file in ingresso
	 * 
	 * @param inputPdfFile Il file da cui estrarre le prime pagine
	 * @param output Stream a cui associre il pdf ottenuto dall'unione dei file in ingresso
	 * @param lastPageIndex Indice dell'ultima pagina da estrarre (l'indice parte da 1)
	 * @throws Exception
	 */
	public void getFirstPages(File inputPdfFile, OutputStream output, int lastPageIndex) throws Exception {
		Document document = new Document();
		
		PdfWriter writer = PdfWriter.getInstance(document, output); 
		writer.setTagged();
        writer.setLanguage("it");
        writer.setLinearPageMode();
        writer.createXmpMetadata();
		
		document.open();
		
		PdfContentByte cb = writer.getDirectContent();
		        
    	// Collego il reader al file
        PdfReader reader = new PdfReader(inputPdfFile.getAbsolutePath());
        // Scorro le pagine da copiare
        for (int i = 1; i <= reader.getNumberOfPages() && i <= lastPageIndex; i++) {
        	addPageToDocument(document, writer, cb, reader, i, false);
        }
        
        output.flush();
        document.close();
        output.close();
	}
	
	/**
	 * 
	 * @param inputPdfFile Il file da cui rimuovere le pagine
	 * @param output Stream a cui associre il pdf ottenuto
	 * @param pageToRemoveIndex Indici delle pagine da rimouvere (la prima pagina ha indice 1)
	 * @throws Exception
	 */
	public void removePages(File inputPdfFile, OutputStream output, int[] pageToRemoveIndex) throws Exception {
		removePages(inputPdfFile, output, pageToRemoveIndex, false);
	}
	
	/**
	 * 
	 * @param inputPdfFile Il file da cui rimuovere le pagine
	 * @param output Stream a cui associre il pdf ottenuto
	 * @param pageToRemoveIndex Indici delle pagine da rimouvere (la prima pagina ha indice 1)
	 * @param isPDFA boolean che indica se il file è PDFA
	 * @throws Exception
	 */
	public void removePages(File inputPdfFile, OutputStream output, int[] pageToRemoveIndex, boolean isPDFA) throws Exception {
		
		Document document = new Document();
		PdfWriter writer;
		if (isPDFA) {
			writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U); 
		} else {
			writer = PdfWriter.getInstance(document, output); 
		}
		writer.setTagged();
        writer.setLanguage("it");
        writer.setLinearPageMode();
        writer.createXmpMetadata();
		
		document.open();
		
		PdfContentByte cb = writer.getDirectContent();
		        
    	// Collego il reader al file
        PdfReader reader = new PdfReader(inputPdfFile.getAbsolutePath());
        // Scorro le pagine
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        	// Se la pagina no è presente nell'elenco viene aggiunta al documento prodotto
        	if (!contains(pageToRemoveIndex, i)) {
        		addPageToDocument(document, writer, cb, reader, i, false);
	        }
        }
        
        output.flush();
        document.close();
        output.close();
	}
	
	private boolean contains(int[] values, int value) {
		if (values == null) {
			return false;
		} else {
			for (int i = 0; i < values.length; i++) {
				if (values[i] == value) {
					return true;
				}
			}
			return false;
		}
	}
	
	private void concatPDFs(Iterable<File> files, OutputStream output, boolean forceA4) throws Exception {
		
		Document document = new Document();
		
		PdfAWriter writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U); 
		writer.setTagged();
        writer.setLanguage("it");
        writer.setLinearPageMode();
        writer.createXmpMetadata();
		
		document.open();
		
		PdfContentByte cb = writer.getDirectContent();
		
		// Ciclo su tutti i file da concatenare
        for (File in : files) {
        	// Collego il reader al file
            PdfReader reader = new PdfReader(in.getAbsolutePath());
            // Scorro le pagine da copiare
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {                            
            	addPageToDocument(document, writer, cb, reader, i, forceA4);
            }
        }
        
        output.flush();
        document.close();
        output.close();
    }
	
	
	private void concatPDFs(Iterable<File> files, OutputStream output, boolean forceA4, boolean pdfA) throws Exception {
		
		Document document = new Document();
		PdfWriter writer;
		
		if (pdfA) {
			writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U);
		} else {
			writer = PdfWriter.getInstance(document, output);
		}
		writer.setTagged();
		writer.setLanguage("it");
		writer.setLinearPageMode();
		writer.createXmpMetadata();
		
		document.open();
		
		PdfContentByte cb = writer.getDirectContent();
		
		// Ciclo su tutti i file da concatenare
		for (File in : files) {
			// Collego il reader al file
			PdfReader reader = new PdfReader(in.getAbsolutePath());
			// Scorro le pagine da copiare
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {                            
				addPageToDocument(document, writer, cb, reader, i, forceA4);
			}
		}
		
		output.flush();
		document.close();
		output.close();
	}
	
	// LA LOGICA DI QUESTO METODO È PRESENTE ANCHE NEL METODO ADDPAGETODOCUMENT DELLA CLASSE IT.ENG.SERVICES.FILEOP.INFOFILEUTILITY
	// DEL PROGETTO INFOGENERICFILE
	private void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);
		
		// Federico Cacco 28.01.2016
		// Quando si concatenano pagine che derivano da scansioni si hanno dei problemi di rotazione, in
		// quanto la scansione solitamente produce una immagine orizzontale che viene poi in caso
		// ruotata nel verso giusto prima di essere inserita nel pdf finale. 
		// Devo quindi copiare la pagina tenendo conto di questa rotazione,
		// altrimenti rischio di inserirla storta
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
		    	cb.addTemplate(page,-1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
		        break;
		    case 270:
		    	cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
		        break;
		    default:
		        break;
		}
	}
				
	
}