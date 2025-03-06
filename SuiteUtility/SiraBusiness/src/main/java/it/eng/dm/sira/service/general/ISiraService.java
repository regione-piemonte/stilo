package it.eng.dm.sira.service.general;

import it.eng.core.business.beans.AbstractBean;

import java.util.List;

public interface ISiraService<I extends AbstractBean, O extends AbstractBean> {
	
	public List<O> search(I input) throws Exception;

}
