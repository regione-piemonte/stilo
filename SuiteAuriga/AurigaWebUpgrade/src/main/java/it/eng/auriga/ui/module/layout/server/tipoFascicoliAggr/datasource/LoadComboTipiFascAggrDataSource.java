/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.tipoFascicoliAggr.datasource.bean.TipoFascAggrBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author cristiano
 *
 */
@Datasource(id = "LoadComboTipiFascAggrDataSource")
public class LoadComboTipiFascAggrDataSource extends AbstractFetchDataSource<TipoFascAggrBean> {

	@Override
	public PaginatorBean<TipoFascAggrBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();

		String idFolderType = StringUtils.isNotBlank(getExtraparams().get("idFolderType")) ? getExtraparams().get("idFolderType") : "";
		String idFolderTypeGen = StringUtils.isNotBlank(getExtraparams().get("idFolderTypeGen")) ? getExtraparams().get("idFolderTypeGen") : "";

		String nomeFascAggrTypePadre = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("nomeFascAggrTypePadre")) {
					nomeFascAggrTypePadre = (String) criterion.getValue();
				}
			}
		}

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_FOLDER");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("STR_IN_DES|*|" + nomeFascAggrTypePadre + "|*|CI_TO_ADD|*|" + idFolderTypeGen);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<TipoFascAggrBean> lPaginatorBean = new PaginatorBean<TipoFascAggrBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<XmlListaSimpleBean> lListXml = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
			int size = 0;
			for (XmlListaSimpleBean lRiga : lListXml) {

				String key = lRiga.getKey();
				// Non viene recuperato se stesso
				if (StringUtils.isBlank(idFolderType) || !key.equalsIgnoreCase(idFolderType)) {
					TipoFascAggrBean tipoFascAggrBean = new TipoFascAggrBean();
					tipoFascAggrBean.setIdFascAggrTypePadre(key);
					String value = lRiga.getValue();
					tipoFascAggrBean.setNomeFascAggrTypePadre(value);
					lPaginatorBean.addRecord(tipoFascAggrBean);
					size++;
				}
			}
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(size);
			lPaginatorBean.setTotalRows(size);
		}

		return lPaginatorBean;
	}

}
