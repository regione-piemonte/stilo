/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.util.List;

public interface ISiraService<I extends AbstractBean, O extends AbstractBean> {
	
	public List<O> search(I input) throws Exception;

}
