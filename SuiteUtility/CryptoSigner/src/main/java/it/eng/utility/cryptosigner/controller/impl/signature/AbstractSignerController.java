package it.eng.utility.cryptosigner.controller.impl.signature;

import it.eng.utility.cryptosigner.controller.bean.InputBean;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.AbstractController;

/**
 * Definisce la classe di base per l'implementazione di un 
 * {@link it.eng.utility.cryptosigner.controller.ISignerController}
 * @author Administrator
 *
 */
public abstract class AbstractSignerController  extends AbstractController {
	/**
	 *  Indica se occorre effettuare i controlli anche sulle controfirme
	 *  (default = true)
	 */
	protected boolean performCounterSignaturesCheck = true;
	
	/**
	 * Restituisce true se occorre effettuare i controlli anche sulle controfirme
	 * @return boolean 
	 */
	public boolean isPerformCounterSignaturesCheck() {
		return performCounterSignaturesCheck;
	}

	/**
	 * Definisce se occorre effettuare i controlli anche sulle controfirme
	 * @param performCounterSignaturesCheck
	 */
	public void setPerformCounterSignaturesCheck(
			boolean performCounterSignaturesCheck) {
		this.performCounterSignaturesCheck = performCounterSignaturesCheck;
	}
	
	public boolean execute(InputBean input,OutputBean output, boolean eseguiValidazioni)throws ExceptionController{
		if (input instanceof InputSignerBean && output instanceof OutputSignerBean)
			return execute((InputSignerBean)input, (OutputSignerBean)output, eseguiValidazioni);
		return false;
	}
	
	public abstract boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController;

}
