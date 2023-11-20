/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.TagItemLavorazioneBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@Datasource(id = "LoadComboTagBozzeMailDataSource")
public class LoadComboTagBozzeMailDataSource extends OptionFetchDataSource<TagItemLavorazioneBean> {

	@Override
	public PaginatorBean<TagItemLavorazioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		boolean isFromFilter = StringUtils.isNotBlank(getExtraparams().get("isFromFilter")) && "true".equalsIgnoreCase(getExtraparams().get("isFromFilter"));

		// Leggo i parametri di inp
		boolean ignoraVociPerFiltro = false;
		if (getExtraparams() != null) {
			ignoraVociPerFiltro = Boolean.parseBoolean(getExtraparams().get("ignoraVociPerFiltro"));
		}
		
		PaginatorBean<TagItemLavorazioneBean> lPaginatorBean = new PaginatorBean<TagItemLavorazioneBean>();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DICTIONARY_ENTRY|*|TAG_PER_EMAIL|*|ID_USER_LAVORO|*|"  + idUserLavoro);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(isFromFilter ? null : new BigDecimal(1));

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<TagItemLavorazioneBean> lista = XmlListaUtility.recuperaLista(xmlLista, TagItemLavorazioneBean.class);
			if (!ignoraVociPerFiltro) {
				TagItemLavorazioneBean bean1 = new TagItemLavorazioneBean();
				bean1.setKey("#NONE");
				bean1.setValue("<i>nessuno</i>");
				TagItemLavorazioneBean bean2 = new TagItemLavorazioneBean();
				bean2.setKey("#ANY");
				bean2.setValue("<i>qualsiasi</i>");
				lista.add(bean1);
				lista.add(bean2);
			}

			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());
		}
		return lPaginatorBean;
	}

}