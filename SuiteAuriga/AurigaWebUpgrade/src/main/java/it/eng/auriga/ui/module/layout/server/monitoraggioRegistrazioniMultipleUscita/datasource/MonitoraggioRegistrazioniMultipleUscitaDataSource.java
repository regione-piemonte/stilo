/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.monitoraggioRegistrazioniMultipleUscita.datasource;

import java.util.List;

import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioRegistrazioniMultipleUscita.datasource.bean.RegistrazioniMultipleUscitaBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

@Datasource(id="MonitoraggioRegistrazioniMultipleUscitaDataSource")
public class MonitoraggioRegistrazioniMultipleUscitaDataSource extends AurigaAbstractFetchDatasource<RegistrazioniMultipleUscitaBean> {

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatorBean<RegistrazioniMultipleUscitaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
