/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import it.eng.auriga.database.store.result.bean.StoreResultBean;

public class StoreException extends Exception {
	
	private static Logger mLogger = Logger.getLogger(StoreException.class);
	private StoreResultBean<?> pBean;
	private String completeMessage;
	
	public StoreException(String message){
		super(message);
	}

	public StoreException(StoreResultBean<?> pBean) {
		super(pBean.getDefaultMessage());
		this.pBean = pBean;
		setCompleteMessage(pBean.getDefaultMessage() + "; code: " + pBean.getErrorCode() + "; context: " + pBean.getErrorContext());
		mLogger.error(completeMessage, this);
	}

	public void setError(StoreResultBean<?> pBean) {
		this.pBean = pBean;
	}

	public StoreResultBean<?> getError() {
		return pBean;
	}

	public String getCompleteMessage() {
		return completeMessage;
	}

	public void setCompleteMessage(String completeMessage) {
		this.completeMessage = completeMessage;
	}
	
}