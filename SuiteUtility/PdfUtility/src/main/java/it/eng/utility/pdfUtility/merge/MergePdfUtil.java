package it.eng.utility.pdfUtility.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;


public class MergePdfUtil {

	public void concatPDFs(File[] files, OutputStream output) throws Exception {
		PdfCopyFields copy = new PdfCopyFields(output);

		// Apro il documento
		copy.open();
		try {
			// Inserisco i documenti pdf in ordine
			for (int i = 0; i < files.length; i++) {
				// Creo un PDFReader
				FileInputStream stream = new FileInputStream(files[i]);
				PdfReader reader = new PdfReader(stream);
				copy.addDocument(reader);
			}
		} finally {
			// Chiudo il documento
			copy.close();
		}
	}
}
