package it.eng.utility.cryptosigner.controller.impl.signature;

import it.eng.utility.cryptosigner.controller.bean.ContentBean;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.AbstractSigner;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Recupera il contenuto della busta tramite il metodo 
 * {@link it.eng.utility.cryptosigner.data.AbstractSigner#getContentAsFile getContentAsFile} del signer ad esso associato.
 * @author Administrator
 */
public class ContentExtraction extends AbstractSignerController{

	private static Logger log = LogManager.getLogger(ContentExtraction.class);
	
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni)
			throws ExceptionController {
		AbstractSigner signer = input.getSigner();
		InputStream contentstream;
		try {
			contentstream = signer.getContentAsInputStream( input.getEnvelope() );
		} catch (IOException e) {
			throw new ExceptionController(e);
		}
		ContentBean content = new ContentBean();
		content.setSigner(signer);
		content.setPossiblySigned(signer.canContentBeSigned());
		content.setContentStream(contentstream);
		output.setContent(content);
		return true;
	}
	

}
