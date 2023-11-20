/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CapitoloBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboCapitoliDatiContabiliDataSource")
public class LoadComboCapitoliDatiContabiliDataSource extends SelectDataSource<CapitoloBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboCapitoliDatiContabiliDataSource.class);

	@Override
	public PaginatorBean<CapitoloBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String codice      = StringUtils.isNotBlank(getExtraparams().get("codiceCapitolo"))      ? getExtraparams().get("codiceCapitolo")      : "";
		String descrizione = StringUtils.isNotBlank(getExtraparams().get("descrizioneCapitolo")) ? getExtraparams().get("descrizioneCapitolo") : "";
		
		boolean isCodiceFromFilter = false;		
		boolean isDescrizioneFromFilter = false;		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("codiceCapitolo")) {
					codice = (String) criterion.getValue();
					isCodiceFromFilter = true;
				} else if (criterion.getFieldName().equals("descrizioneCapitolo")) {
					descrizione = (String) criterion.getValue();
					isDescrizioneFromFilter = true;
				}
			}
		}
		
		if(!isDescrizioneFromFilter && !"".equals(codice) && isCodiceFromFilter) {
			descrizione = "";
		}
		
		if(!"".equals(descrizione) && isDescrizioneFromFilter && !isCodiceFromFilter) {
			codice = "";
		}	

		List<CapitoloBean> lListResult = new ArrayList<CapitoloBean>();
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("CAPITOLI_CONTABILI");	
		String altriParametri = "FLG_EU|*|U";
		if (StringUtils.isNotBlank(codice)) {
			altriParametri += "|*|COD_CAP|*|" + codice;
		}
		if (StringUtils.isNotBlank(descrizione)) {
			altriParametri += "|*|DES_CAP|*|" + descrizione;
		}
			
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);										
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), loginBean, lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			lListResult = XmlListaUtility.recuperaLista(xmlLista, CapitoloBean.class);
		} else {
			mLogger.error(lStoreResultBean.getDefaultMessage());
		}
		
		
		PaginatorBean<CapitoloBean> lPaginatorBean = new PaginatorBean<CapitoloBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		return lPaginatorBean;
	}
}