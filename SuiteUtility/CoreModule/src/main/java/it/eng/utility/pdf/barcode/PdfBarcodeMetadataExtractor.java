package it.eng.utility.pdf.barcode;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.zxing.Result;

/**
 * Espone un'api statica per l'estrazione di codici a barre da un documento pdf,
 * basata su PdfBarcodePageExtractor
 *
 */
public class PdfBarcodeMetadataExtractor {

	protected static int maximumBlankPixelDelimiterCount = 20;

	/**
	 * Effettua l'analisi del documento pdf, estraendo le informazioni
	 * sottoforma di com.google.zxing.Result dai qrcode trovati nelle pagine
	 * specificate
	 * 
	 * @param file
	 *            il documento da anlizzare
	 * @param pageNumbers
	 *            numeri delle pagine da considerare nell'analisi, se impostato
	 *            a null vengono analizzate tutte le pagine
	 * @param workDirectory
	 *            directory di lavoro in cui creare i file temporanei necessari
	 *            per l'estrazione del bar code
	 * @throws IOException
	 */
	public static synchronized List<Result> extract(File file, List<Integer> pageNumbers) throws IOException {

		return extract(file, pageNumbers, null);
	}

	/**
	 * Effettua l'analisi del documento pdf, estraendo le informazioni
	 * sottoforma di com.google.zxing.Result dai qrcode trovati nelle pagine
	 * specificate
	 * 
	 * @param file
	 *            il documento da anlizzare
	 * @param pageNumbers
	 *            numeri delle pagine da considerare nell'analisi, se impostato
	 *            a null vengono analizzate tutte le pagine
	 * @param workDirectory
	 *            directory di lavoro in cui creare i file temporanei necessari
	 *            per l'estrazione del bar code
	 * 
	 * @param barCodeArea
	 *            area che racchiude il barCode, evita l'analisi dell'intera
	 *            immagine
	 * @throws IOException
	 */
	public static synchronized List<Result> extract(File file, List<Integer> pageNumbers, Rectangle barCodeArea)
			throws IOException {

		PDDocument pdDocument = null;

		try {
			InputStream pdfInputStream = new BufferedInputStream(new FileInputStream(file));
			pdDocument = PDDocument.load(pdfInputStream);
			pdfInputStream.close();

			List<Result> scanResults = new ArrayList<Result>();

			PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

			// se non sono state specificate delle pagine precise, allora
			// analizzo
			// tutte quelle presenti
			if (pageNumbers == null) {

				for (int page = 0; page < pdDocument.getNumberOfPages(); ++page) {

					List<Result> results = scanPage(pdfRenderer, page, barCodeArea);

					scanResults.addAll(results);

				}

			} else {

				// analizzo solo le pagine che sono state specificate
				for (Integer currentPageNumber : pageNumbers) {

					List<Result> results = scanPage(pdfRenderer, currentPageNumber, barCodeArea);

					scanResults.addAll(results);

				}
			}

			return scanResults;
		} finally {

			if (pdDocument != null) {
				pdDocument.close();
			}

		}
	}

	/**
	 * @param workDirectory
	 * @param pageScannerList
	 * @param pdfRenderer
	 * @param currentPageNumber
	 * @return
	 * @throws IOException
	 */
	protected static List<Result> scanPage(PDFRenderer pdfRenderer, Integer currentPageNumber) throws IOException {

		return scanPage(pdfRenderer, currentPageNumber, null);

	}

	/**
	 * @param workDirectory
	 * @param pageScannerList
	 * @param pdfRenderer
	 * @param currentPageNumber
	 * @return
	 * @throws IOException
	 */
	protected static List<Result> scanPage(PDFRenderer pdfRenderer, Integer currentPageNumber, Rectangle barCodeArea)
			throws IOException {

		BufferedImage bim = pdfRenderer.renderImageWithDPI(currentPageNumber, 300, ImageType.RGB);

		PdfBarcodePageExtractor pageScanner = new PdfBarcodePageExtractor(bim, maximumBlankPixelDelimiterCount,
				barCodeArea);

		return pageScanner.extractBarCodes();

	}

}
