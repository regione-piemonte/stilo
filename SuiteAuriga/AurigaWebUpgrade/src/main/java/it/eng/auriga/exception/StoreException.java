/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.manager.AurigaStoreExceptionManager;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.document.function.bean.GestioneAllegatiPraticaOutBean;
import it.eng.document.function.bean.LoadFascicoloOut;
import it.eng.document.function.bean.ModificaFascicoloOut;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.SalvaFascicoloOut;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.utility.ui.module.core.shared.annotation.ManagedException;

@ManagedException(gestore=AurigaStoreExceptionManager.class)
public class StoreException extends Exception {

	private static Logger mLogger = Logger.getLogger(StoreException.class);
	private static final long serialVersionUID = 2486557506993341755L;
	
	private String completeMessage;
	public StoreException(String message){
		super(message);
	}
	
	public StoreException(StoreResultBean<?> pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(String message, Integer code, String context){
		super(message);
		setCompleteMessage(message + "; code: " + (code != null ? String.valueOf(code) : "") + "; context: " + context);
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(CreaModDocumentoOutBean pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(VersionaDocumentoOutBean pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(LoadFascicoloOut pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(ModificaFascicoloOut pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(SalvaFascicoloOut pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(RecuperaDocumentoOutBean pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(GestioneAllegatiPraticaOutBean pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public StoreException(EseguiTaskOutBean pBean){
		super(pBean.getDefaultMessage());
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}
	
	public String getCompleteMessage() {
		return completeMessage;
	}

	public void setCompleteMessage(String completeMessage) {
		this.completeMessage = completeMessage;
	}
	
}
