/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.VistaAdaScarichi;
import it.eng.dm.sirabusiness.bean.SiraVistaAdaScarichiBean;

public class VistaAdaScarichiBeanToVistaAdaScarichi implements IBeanPopulate<SiraVistaAdaScarichiBean, VistaAdaScarichi> {

	@Override
	public void populate(SiraVistaAdaScarichiBean src, VistaAdaScarichi dest) throws Exception {
	}

	@Override
	public void populateForUpdate(SiraVistaAdaScarichiBean src, VistaAdaScarichi dest) throws Exception {
	}

}