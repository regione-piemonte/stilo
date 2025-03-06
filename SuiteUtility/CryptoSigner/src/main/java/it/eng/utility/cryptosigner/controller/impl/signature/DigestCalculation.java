package it.eng.utility.cryptosigner.controller.impl.signature;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
/**
 * Non usare
 *
 */
 @Deprecated
public class DigestCalculation extends AbstractSignerController{
	
	public static final String INPUT_DIGEST_RESULT="Input file digest result";
	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String performDigestCalculation = "performDigestCalculation";
	
	public String getCheckProperty() {
		return performDigestCalculation;
	}
 
	
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni)
			throws ExceptionController {
		
		boolean result=true;
		 
		return result;
	}
  
	
}
