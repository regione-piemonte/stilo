package it.eng.utility.cryptosigner.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.cryptosigner.controller.ISignerController;
import it.eng.utility.cryptosigner.controller.bean.InputBean;
import it.eng.utility.cryptosigner.controller.bean.OutputBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.SignerUtil;

public abstract class AbstractController implements ISignerController{
	
	public static final Logger log = LogManager.getLogger(AbstractController.class);
	
	/**
	 *  Indica se il controllo corrente � bloccante
	 *  (default=false)
	 */
	protected boolean critical = false;
	
	/** 
	 * Utilita' per l'analisi dei formati fi firma 
	 */
	protected SignerUtil signerUtil = SignerUtil.newInstance();
	
	/**
	 * L'implementazione recupera il flag associato
	 * al controller attuale invocando il metodo 
	 * {@link it.eng.utility.cryptosigner.controller.impl.signature.AbstractSignerController#getCheckProperty}.
	 * Se esso � null viene restituito true, altrimenti viene restituito il
	 * valore del flag
	 */
	public boolean canExecute(InputBean input) {
		//log.debug("::canExecute input.getChecks() " + input.getChecks());
		if (input==null || input.getChecks()==null)
			return true;
		String checkProperty = getCheckProperty();
		//log.debug("::checkProperty " + checkProperty);
		if (checkProperty==null)
			return true;
		Boolean check = input.getFlag(checkProperty);
		//log.debug("::check " + check);
		return check==null ? true : check; 
	}
	
	public boolean getCheckValue(InputBean input, String propertyValue) {
		//log.debug("::canExecute input.getChecks() " + input.getChecks());
		if (input==null || input.getChecks()==null)
			return false;
		Boolean check = input.getFlag(propertyValue);
		//log.debug("::check " + check);
		return check==null ? true : check; 
	}

	/**
	 * Restituisce la chiave da utilizzare per ottenere il
	 * nome del flag di esecuzione del filtro.
	 * L'implementazione di default restituisce il valore null.
	 */
	public String getCheckProperty(){
		return null;
	}
	
	public abstract boolean execute(InputBean input,OutputBean output, boolean childValidation)throws ExceptionController;

	/**
	 * Restituisce true se il controllo � bloccante
	 */
	public boolean isCritical() {
		return critical;
	}

	/**
	 * Definisce se il controllo � bloccante
	 * @param critical
	 */
	public void setCritical(boolean critical) {
		this.critical = critical;
	}
}
