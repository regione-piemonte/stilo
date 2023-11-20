/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.ResponseFileCompressType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CompressAuriga;
import it.eng.module.foutility.util.FileOpMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.MimeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * operazione di compressione
 * 
 * @author GAROFALO
 * 
 */
public class CompressCtrl extends AbstractFileController {
	public static final Logger log = LogManager.getLogger(CompressCtrl.class);

	public String operationType;

	// file estratto dalle buste (File)
	public static final String COMPRESSED_FILE = "compressedFile";

	// chiave identificativa della operazione
	public static final String CompressCtrlCode = CompressCtrl.class.getName();

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey) {
		ResponseFileCompressType response = new ResponseFileCompressType();
		boolean ret = false;
		try {
			File inputFile = input.getInputFile();

			String file = input.getFileName();
			log.debug("file: " + file);

			MimeType mimeType = output.getPropOfType( FormatRecognitionCtrl.DETECTED_MIME, MimeType.class);
			log.debug("mimeType " + mimeType);
			boolean isPdf = mimeType.match("application/pdf");

			boolean isTif = mimeType.match("image/tiff");

//			MimeType mimeType = new MimeType();
//			boolean isPdf = false;
//			boolean isTif = true;

			if (isTif) {
				log.debug("isTif");
				inputFile = convertImagetoPdf(inputFile, input.getTemporaryDir());
			} else {
				if (!isPdf) {
					log.error("Formato " + mimeType.getPrimaryType() + " non valido");
					throw new IOException("Formato non valido");
				}
				log.debug("isPdf");
			}
			File compressedFile = compressDocumentFile(inputFile, input.getTemporaryDir());
			

			//output.addProperty(COMPRESSED_FILE, compressedFile);
			if( compressedFile!=null ){
				log.debug("compressedFile " + compressedFile.getPath());
				ret = true;
				output.setFileResult( compressedFile );
				response.setVerificationStatus(VerificationStatusType.OK);
			} else {
				response.setVerificationStatus(VerificationStatusType.KO);
			}
		} catch (Exception e) {
			log.fatal("fatal error..", e);
			OutputOperations.addError(response, FileOpMessage.COMPRESS_OP_ERROR, VerificationStatusType.KO, e.getMessage());
		}
		output.addResult(CompressCtrlCode, response);
		return ret;
	}

	private File compressDocumentFile(File documentFile, File tempDir) throws IOException {

		String[] input = new String[] { "-nostruct", "-jpeg", documentFile.getPath() };
		File fileOut = null;
		if (CompressAuriga.compress(input)) {
			fileOut = new File(documentFile.getPath() + "-compress");
		}

		return fileOut;
	}

	private File convertImagetoPdf(File documentFile, File tempDir) throws IOException {

		Image image;
//		String filename = null;
		File fileOut = null;
		try {
			image = Image.getInstance(documentFile.getPath());

			Rectangle rect = new RectangleReadOnly(image.getWidth() * 1.05f, image.getHeight() * 1.08f);
			Document document = new Document(rect);

//			filename = documentFile.getPath().substring(0, documentFile.getPath().lastIndexOf("."));
			PdfWriter.getInstance(document, new FileOutputStream(documentFile.getPath()));
			document.open();
			document.add(image);
			document.close();
			fileOut = new File(documentFile.getPath());
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return fileOut;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
