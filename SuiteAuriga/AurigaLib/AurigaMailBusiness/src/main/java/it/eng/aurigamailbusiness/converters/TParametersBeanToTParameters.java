/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.database.mail.TParameters;
import it.eng.core.business.converter.IBeanPopulate;

public class TParametersBeanToTParameters implements IBeanPopulate<TParametersBean, TParameters> {

	@Override
	public void populate(TParametersBean src, TParameters dest) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateForUpdate(TParametersBean src, TParameters dest) throws Exception {
		// TODO Auto-generated method stub

	}

}