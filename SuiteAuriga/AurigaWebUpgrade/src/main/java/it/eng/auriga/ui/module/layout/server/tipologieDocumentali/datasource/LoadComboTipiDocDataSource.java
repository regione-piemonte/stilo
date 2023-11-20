/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean.TipoDocBean;
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

@Datasource(id = "LoadComboTipiDocDataSource")
public class LoadComboTipiDocDataSource extends AbstractFetchDataSource<TipoDocBean> {

	@Override
	public PaginatorBean<TipoDocBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();

		String idTipoDoc = StringUtils.isNotBlank(getExtraparams().get("idTipoDoc")) ? getExtraparams().get("idTipoDoc") : "";
		String idDocTypeGen = StringUtils.isNotBlank(getExtraparams().get("idDocTypeGen")) ? getExtraparams().get("idDocTypeGen") : "";

		String nomeDocumentoTypePadre = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("nomeDocumentoTypePadre")) {
					nomeDocumentoTypePadre = (String) criterion.getValue();
				}
			}
		}

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("STR_IN_DES|*|" + nomeDocumentoTypePadre + "|*|CI_TO_ADD|*|" + idDocTypeGen);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<TipoDocBean> lPaginatorBean = new PaginatorBean<TipoDocBean>();

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
				if (StringUtils.isBlank(idTipoDoc) || !key.equalsIgnoreCase(idTipoDoc)) {
					TipoDocBean tipoDocBean = new TipoDocBean();
					tipoDocBean.setIdDocumentoTypePadre(key);
					String value = lRiga.getValue();
					tipoDocBean.setNomeDocumentoTypePadre(value);
					lPaginatorBean.addRecord(tipoDocBean);
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
