/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import it.eng.bean.ExecutionResultBean;

public class PdfUtility {

	private static Logger logger = Logger.getLogger(PdfUtility.class);

	/**
	 * Converte il docx fornito nel file pdf di cui è stato specificato il path
	 * 
	 * @param docxInput
	 * @param pdfOutput
	 * @return
	 */
	public static ExecutionResultBean convertDocxToPdf(File docxInput, File pdfOutput) {

		try {

			// 1) Load DOCX into XWPFDocument
			XWPFDocument document = new XWPFDocument(new FileInputStream(docxInput));

			// 2) Prepare Pdf options
			PdfOptions options = PdfOptions.create();

			// 3) Convert XWPFDocument to Pdf
			OutputStream out = new FileOutputStream(pdfOutput);
			PdfConverter.getInstance().convert(document, out, options);

			logger.info(String.format("Il file %1$s è stato generato", pdfOutput.getAbsolutePath()));

			ExecutionResultBean retValue = new ExecutionResultBean();
			retValue.setSuccessful(Boolean.TRUE);

			return retValue;

		} catch (Throwable e) {

			String message = String.format(
					"Durante la generazione del pdf %1$s del file %2$s, si è verificata la seguente eccezione %3$s",
					pdfOutput.getAbsolutePath(), docxInput.getAbsolutePath(), ExceptionUtils.getFullStackTrace(e));

			logger.error(message);

			ExecutionResultBean retValue = new ExecutionResultBean();
			retValue.setSuccessful(Boolean.FALSE);
			retValue.setMessage(message);

			return retValue;

		}
	}

}
