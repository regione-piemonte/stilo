/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
// import com.itextpdf.text.pdf.PdfAConformanceLevel;
// import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Espone metodi di utilità per la conversione di un pdf in pdfA. Documentazione di riferimento http://www.alliancegroup.co.uk/pdf-a.htm
 * 
 * @author massimo malvestio
 *
 */
public class PdfConverter {

	/**
	 * Converte il pdf specificato in in pdf in formato A/3-U Il sotto livello U è un livello intermedio tra l'A ed il B. <br/>
	 * Il sottolivello A rispetta tutte le specifiche ISO 19005-3, mentre l'U definisce i requisiti minimi per la conservazione lungo termine dei documenti,
	 * assicurando che tutti i caratteri del testo hanno un equivalente UNICODE.
	 * 
	 * Ingloba un profilo icc per rispettare la conformità del documento
	 * 
	 * @param in
	 * @param output
	 * @throws DocumentException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static File convertToPdfA3U(File in, File temporaryDirectory) throws DocumentException, IOException, URISyntaxException {

		File outputPdf3U = File.createTempFile("conv3U", ".pdf", temporaryDirectory);

		Document document = new Document();

		InputStream iccFile = PdfConverter.class.getResourceAsStream("/srgb_color_space_profile.icm");

		ICC_Profile icc = ICC_Profile.getInstance(iccFile);

		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputPdf3U));

		PdfAWriter writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U);
		writer.createXmpMetadata();

		writer.setTagged();
		writer.setLanguage("it");
		writer.setLinearPageMode();

		document.open();

		writer.setOutputIntents("Custom", "", "http://www.color.org", "adobe rgb", icc);

		PdfContentByte cb = writer.getDirectContent();

		// Collego il reader al file
		PdfReader reader = new PdfReader(in.getAbsolutePath());

		// Scorro le pagine da copiare
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {

			addPageToDocument(document, writer, cb, reader, i, false);
//			PdfImportedPage page = writer.getImportedPage(reader, i);
//			cb.addTemplate(page, 0, 0);
		}

		document.close();
		output.flush();
		output.close();
		reader.close();

		return outputPdf3U;
	}
	
	private static void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);
		
		// Federico Cacco 28.01.2016
		// Quando si concatenano pagine che derivano da scansioni si hanno dei problemi di rotazione, in
		// quanto la scansione solitamente produce una immagine orizzontale che viene poi in caso
		// ruotata nel verso giusto prima di essere inserita nel pdf finale. 
		// Devo quindi copiare la pagina tenendo conto di questa rotazione,
		// altrimenti rischio di inserirla storta
		
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
		        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
		        break;
		    case 270:
		        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, psize.getWidth(), 0);
		        break;
		    default:
		        break;
		}
	}

}
