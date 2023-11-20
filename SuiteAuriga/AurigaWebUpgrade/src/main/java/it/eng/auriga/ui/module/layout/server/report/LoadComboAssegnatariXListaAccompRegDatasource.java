/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.report.bean.AssegnatariXListaAccompRegBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboAssegnatariXListaAccompRegDatasource")
public class LoadComboAssegnatariXListaAccompRegDatasource extends AbstractFetchDataSource<AssegnatariXListaAccompRegBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboAssegnatariXListaAccompRegDatasource.class);

	@Override
	public PaginatorBean<AssegnatariXListaAccompRegBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)	throws Exception {
		
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "";
		
		String dtInizioVld = StringUtils.isNotBlank(getExtraparams().get("dtInizioVld")) ? getExtraparams().get("dtInizioVld") : "";
		String dtFineVld = StringUtils.isNotBlank(getExtraparams().get("dtFineVld")) ? getExtraparams().get("dtFineVld") : "";
		String idUserAss = StringUtils.isNotBlank(getExtraparams().get("idUserAss")) ? getExtraparams().get("idUserAss") : "";
		String codCategoriaReg = "PG";		
		String siglaReg = "";
		
		List<AssegnatariXListaAccompRegBean> lListResult = new ArrayList<AssegnatariXListaAccompRegBean>();
		PaginatorBean<AssegnatariXListaAccompRegBean> lPaginatorBean = new PaginatorBean<AssegnatariXListaAccompRegBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ASSEGNATARI_X_LISTA_ACCOMP_REG");
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|REGISTRAZIONI_DAL|*|" + dtInizioVld + "|*|REGISTRAZIONI_AL|*|" + dtFineVld
				+ "|*|ID_USER_ASS|*|" + idUserAss + "|*|COD_CATEGORIA_REG|*|" + codCategoriaReg + "|*|SIGLA_REG|*|" + siglaReg;			
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		if(!lStoreResultBean.isInError()) {
			try {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				lListResult = XmlListaUtility.recuperaLista(xmlLista, AssegnatariXListaAccompRegBean.class);				
			} catch(Exception e) {
				mLogger.error(e.getMessage(), e);
			}
		} else if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
			mLogger.error(lStoreResultBean.getDefaultMessage());
		} 

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		return lPaginatorBean;
	}
	
}
