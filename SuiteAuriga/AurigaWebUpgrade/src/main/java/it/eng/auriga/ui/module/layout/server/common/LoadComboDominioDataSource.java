/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

@Datasource(id = "LoadComboDominioDataSource")
public class LoadComboDominioDataSource extends AbstractServiceDataSource<AurigaLoginBean, LoadComboDominioBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboDominioDataSource.class);

	@Override
	public LoadComboDominioBean call(AurigaLoginBean bean) throws Exception {

		DmpkLoadComboDmfn_load_combo load_combo = new DmpkLoadComboDmfn_load_combo();
		DmpkLoadComboDmfn_load_comboBean lBean = new DmpkLoadComboDmfn_load_comboBean();
		lBean.setTipocomboin("DOMINI_ACCRED");
		lBean.setFlgsolovldin(BigDecimal.ONE);
		lBean.setAltriparametriin("USERNAME|*|" + bean.getUserid());

		List<DominioBean> lista = null;
		try {
			String xmlLista = load_combo.execute(getLocale(), bean, lBean).getResultBean().getListaxmlout();
			lista = XmlListaUtility.recuperaLista(xmlLista, DominioBean.class);
		} catch (Exception e) {
			mLogger.warn(e);
		}

		if (lista == null) {
			lista = new ArrayList<DominioBean>();
		}

		LoadComboDominioBean lDominioComboBean = new LoadComboDominioBean();
		lDominioComboBean.setDomini(lista);

		return lDominioComboBean;
	}

}
