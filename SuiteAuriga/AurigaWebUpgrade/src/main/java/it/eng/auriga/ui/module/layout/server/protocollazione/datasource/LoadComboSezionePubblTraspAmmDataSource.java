/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspTreeNodeBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspTreeXmlBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author dbe4235
 *
 */

@Datasource(id="LoadComboSezionePubblTraspAmmDataSource")
public class LoadComboSezionePubblTraspAmmDataSource extends AbstractTreeDataSource<ContenutiAmmTraspTreeNodeBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboSezionePubblTraspAmmDataSource.class);

	@Override
	public PaginatorBean<ContenutiAmmTraspTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro()	: "";		
		
		List<ContenutiAmmTraspTreeNodeBean> lListResult = new ArrayList<ContenutiAmmTraspTreeNodeBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SEZIONI_AMM_TRASPARENTE");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FINALITA|*|PUBBLICAZIONE|*|ID_USER_LAVORO|*|" + idUserLavoro);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), loginBean, lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			List<ContenutiAmmTraspTreeXmlBean> dataXml = XmlListaUtility.recuperaLista(xmlLista, ContenutiAmmTraspTreeXmlBean.class);
			if(dataXml != null && !dataXml.isEmpty()) {
				for (ContenutiAmmTraspTreeXmlBean lXmlBean : dataXml){			
					ContenutiAmmTraspTreeNodeBean lBean = new ContenutiAmmTraspTreeNodeBean();
					lBean.setIdNode(lXmlBean.getIdNode());
					lBean.setParentId(lXmlBean.getParentId());									
					if (lXmlBean.getFlgToAbil() != null && "1".equalsIgnoreCase(lXmlBean.getFlgToAbil())) {
						lBean.setFlgToAbil(true);
						lBean.setNome("<span style=\"font-weight:bold; color:#1D66B2\">" + lXmlBean.getNome() + "</span>");							
					} else {
						lBean.setFlgToAbil(false);
						lBean.setNome("<span style=\"font-weight:normal; color:gray\">" + lXmlBean.getNome() + "</span>");						
					}
					lListResult.add(lBean);
				}
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<ContenutiAmmTraspTreeNodeBean> lPaginatorBean = new PaginatorBean<ContenutiAmmTraspTreeNodeBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}
}