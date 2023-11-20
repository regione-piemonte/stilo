/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.SmistamentoAttiRagioneriaTreeNodeBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.SmistamentoAttiRagioneriaTreeXmlBean;
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

@Datasource(id="LoadComboSmistamentoAttiRagioneriaDataSource")
public class LoadComboSmistamentoAttiRagioneriaDataSource extends AbstractTreeDataSource<SmistamentoAttiRagioneriaTreeNodeBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboSmistamentoAttiRagioneriaDataSource.class);

	@Override
	public PaginatorBean<SmistamentoAttiRagioneriaTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		List<SmistamentoAttiRagioneriaTreeNodeBean> lListResult = new ArrayList<SmistamentoAttiRagioneriaTreeNodeBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ASS_RAG_ATTO_CON_CARICHI_LAV");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			List<SmistamentoAttiRagioneriaTreeXmlBean> dataXml = XmlListaUtility.recuperaLista(xmlLista, SmistamentoAttiRagioneriaTreeXmlBean.class);
			if(dataXml != null && !dataXml.isEmpty()) {
				for (SmistamentoAttiRagioneriaTreeXmlBean conteggioAssegnatarioTreeXmlBean : dataXml){			
					SmistamentoAttiRagioneriaTreeNodeBean smistamentoAttiRagioneriaTreeNodeBean = new SmistamentoAttiRagioneriaTreeNodeBean();
					smistamentoAttiRagioneriaTreeNodeBean.setIdNode(conteggioAssegnatarioTreeXmlBean.getIdNode());
					smistamentoAttiRagioneriaTreeNodeBean.setParentId(conteggioAssegnatarioTreeXmlBean.getParentId());
					if(conteggioAssegnatarioTreeXmlBean.getNroLivello() != null && !"".equalsIgnoreCase(conteggioAssegnatarioTreeXmlBean.getNroLivello())){
						smistamentoAttiRagioneriaTreeNodeBean.setNroLivello(new BigDecimal(conteggioAssegnatarioTreeXmlBean.getNroLivello()));
					}
					smistamentoAttiRagioneriaTreeNodeBean.setNome(conteggioAssegnatarioTreeXmlBean.getNome());
					smistamentoAttiRagioneriaTreeNodeBean.setConteggiNodo(conteggioAssegnatarioTreeXmlBean.getConteggiNodo());
					smistamentoAttiRagioneriaTreeNodeBean.setIdProcessType(conteggioAssegnatarioTreeXmlBean.getIdProcessType());
					smistamentoAttiRagioneriaTreeNodeBean.setAssegnatario(conteggioAssegnatarioTreeXmlBean.getAssegnatario());
					smistamentoAttiRagioneriaTreeNodeBean.setDtInoltroRagioneria(conteggioAssegnatarioTreeXmlBean.getDtInoltroRagioneria() != null
							? conteggioAssegnatarioTreeXmlBean.getDtInoltroRagioneria() : null);
					lListResult.add(smistamentoAttiRagioneriaTreeNodeBean);
				}
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
//		SmistamentoAttiRagioneriaTreeNodeBean v1 = new SmistamentoAttiRagioneriaTreeNodeBean();
//		v1.setNome("primo livello");
//		v1.setIdNode("100");
//		v1.setNroLivello(new BigDecimal(1));
//		v1.setParentId("100");
//		lListResult.add(v1);
//		
//		SmistamentoAttiRagioneriaTreeNodeBean v2 = new SmistamentoAttiRagioneriaTreeNodeBean();
//		v2.setNome("secondo livello");
//		v2.setIdNode("101");
//		v2.setNroLivello(new BigDecimal(2));
//		v2.setParentId("100");
//		v2.setFlgEsplodiNodo("0");
//		lListResult.add(v2);
//		
//		SmistamentoAttiRagioneriaTreeNodeBean v3 = new SmistamentoAttiRagioneriaTreeNodeBean();
//		v3.setNome("terzo livello");
//		v3.setIdNode("102");
//		v3.setNroLivello(new BigDecimal(3));
//		v3.setParentId("100");
//		v3.setFlgEsplodiNodo("0");
//		lListResult.add(v3);
		
		PaginatorBean<SmistamentoAttiRagioneriaTreeNodeBean> lPaginatorBean = new PaginatorBean<SmistamentoAttiRagioneriaTreeNodeBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}