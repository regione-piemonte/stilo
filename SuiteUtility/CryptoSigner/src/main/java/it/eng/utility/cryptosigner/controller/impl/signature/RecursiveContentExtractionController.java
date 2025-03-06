package it.eng.utility.cryptosigner.controller.impl.signature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.NoSignerException;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * da non usare poichè l'estrazione ricorsiva avviene ripetendo tutti i controlli
 * 
 * @author Russo
 *
 */
@Deprecated
public class RecursiveContentExtractionController extends AbstractSignerController {

	private static final Logger log = Logger.getLogger(RecursiveContentExtractionController.class);

	public static final String EXTRACTED_FILE = "extractedFile";
	public static final String IS_ROOT_SIGNED = "isRootSigned";
	public static final String RCE_VINFO = "content Extraction info";// Validationinfo relative alla verifica

	private File extracttempfile = null;

	// private File extractDocument(InputStream stream) throws IOException{
	// extracttempfile = File.createTempFile("Extract", "file");
	// IOUtils.copyLarge(stream, new FileOutputStream(extracttempfile));
	// return extractDocumentFile(extracttempfile);
	// }
	/**
	 * estrae ricorsivamente il file dalle buste innestate
	 * 
	 * @param documentFile
	 * @return
	 * @throws IOException
	 */
	private File extractDocumentFile(File documentFile) throws IOException {

		AbstractSigner signer = null;
		try {
			signer = SignerUtil.newInstance().getSignerManager(documentFile);
		} catch (NoSignerException e) {
			// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o
			// è firmato in maniera ignota al cryptosigner
		}
		if (signer != null) {
			InputStream stream = signer.getContentAsInputStream(documentFile);
			if (stream != null) {
				if (extracttempfile != null) {
					extracttempfile.delete();
				}
				extracttempfile = File.createTempFile("Extract", "file");
				IOUtils.copyLarge(stream, new FileOutputStream(extracttempfile));
				if (signer.getFormat().equals(SignerType.PAdES)) {
					return extracttempfile;
				}
				return extractDocumentFile(extracttempfile);
			} else {
				return documentFile;
			}
		} else {
			return documentFile;
		}
	}

	@Override
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {
		log.debug("start executing controller");
		AbstractSigner signer = input.getSigner();
		InputStream contentstream = null;
		ValidationInfos vinfo = new ValidationInfos();
		if (input.getEnvelopeStream() == null) {
			vinfo.addErrorWithCode(RecursiveMessage.GM_FILE_INPUT_NULL, MessageHelper.getMessage(RecursiveMessage.GM_FILE_INPUT_NULL), true);
			vinfo.addError(MessageHelper.getMessage(RecursiveMessage.GM_FILE_INPUT_NULL), true);
			return false;
		}
		try {
			// FIXME devi riavvolgere lo stream!!
			File origFile = File.createTempFile("Extract", "file");
			IOUtils.copyLarge(input.getEnvelopeStream(), new FileOutputStream(origFile));
			File extractedFile = extractDocumentFile(origFile);
			contentstream = FileUtils.openInputStream(extractedFile);
			output.setProperty(EXTRACTED_FILE, extractedFile);
			if (extractedFile != origFile)
				output.setProperty(IS_ROOT_SIGNED, true);
			else
				output.setProperty(IS_ROOT_SIGNED, false);
		} catch (IOException e) {
			log.fatal("fatal extracting content", e);
			vinfo.addError(MessageHelper.getMessage(MessageConstants.GM_UNEXPECTED_ERROR), true);
			vinfo.addErrorWithCode(MessageConstants.GM_UNEXPECTED_ERROR, MessageHelper.getMessage(MessageConstants.GM_UNEXPECTED_ERROR), true);
			return false;
		}

		// ContentBean content = new ContentBean();
		// content.setPossiblySigned(signer.canContentBeSigned());
		// content.setContentStream(contentstream);
		// output.setContent(content);

		return true;
	}
}
