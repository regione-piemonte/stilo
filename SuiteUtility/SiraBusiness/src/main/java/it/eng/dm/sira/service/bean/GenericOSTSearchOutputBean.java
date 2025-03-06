package it.eng.dm.sira.service.bean;

import it.eng.core.business.beans.AbstractBean;

public class GenericOSTSearchOutputBean extends AbstractBean {

	private static final long serialVersionUID = -7972765568840501232L;

	private Object[] elementi;

	public Object[] getElementi() {
		return elementi;
	}

	public void setElementi(Object[] elementi) {
		this.elementi = elementi;
	}

}
