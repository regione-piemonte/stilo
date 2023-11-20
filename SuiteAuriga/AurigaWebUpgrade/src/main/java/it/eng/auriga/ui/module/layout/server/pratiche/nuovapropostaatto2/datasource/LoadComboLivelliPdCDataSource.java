/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.LivelliPDCTreeXmlBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

/**
 * 
 * @author dbe4235
 *
 */

@Datasource(id="LoadComboLivelliPdCDataSource")
public class LoadComboLivelliPdCDataSource extends AbstractTreeDataSource<TreeNodeBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboLivelliPdCDataSource.class);

	@Override
	public PaginatorBean<TreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro()	: "";		
		String flgEntrataUscita = getExtraparams().get("flgEntrataUscita") != null ? getExtraparams().get("flgEntrataUscita") : "";
		String codice = getExtraparams().get("codice") != null ? getExtraparams().get("codice") : "";
		
		List<TreeNodeBean> lListResult = new ArrayList<TreeNodeBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("PDC_DATI_CONT");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_EU|*|" + flgEntrataUscita + "|*|COD_FILTRO|*|" + codice);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), loginBean, lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			List<LivelliPDCTreeXmlBean> dataXml = XmlListaUtility.recuperaLista(xmlLista, LivelliPDCTreeXmlBean.class);
			if(dataXml != null && !dataXml.isEmpty()) {
				for (LivelliPDCTreeXmlBean inoltroRagioneriaTreeXmlBean : dataXml){			
					TreeNodeBean lTreeNodeBean = new TreeNodeBean();
					lTreeNodeBean.setIdNode(inoltroRagioneriaTreeXmlBean.getIdNode());
					lTreeNodeBean.setParentId(inoltroRagioneriaTreeXmlBean.getParentId());
					if(inoltroRagioneriaTreeXmlBean.getNroLivello() != null && !"".equalsIgnoreCase(inoltroRagioneriaTreeXmlBean.getNroLivello())){
						lTreeNodeBean.setNroLivello(new BigDecimal(inoltroRagioneriaTreeXmlBean.getNroLivello()));
					}
					lTreeNodeBean.setNome(inoltroRagioneriaTreeXmlBean.getNome());
					lListResult.add(lTreeNodeBean);
				}
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		PaginatorBean<TreeNodeBean> lPaginatorBean = new PaginatorBean<TreeNodeBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}