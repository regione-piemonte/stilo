/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.InoltroRagioneriaTreeNodeBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.InoltroRagioneriaTreeXmlBean;
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

@Datasource(id="LoadComboInoltroRagioneriaAssegnatarioDataSource")
public class LoadComboInoltroRagioneriaAssegnatarioDataSource extends AbstractTreeDataSource<InoltroRagioneriaTreeNodeBean> {
	
	private static Logger mLogger = Logger.getLogger(LoadComboInoltroRagioneriaAssegnatarioDataSource.class);

	@Override
	public PaginatorBean<InoltroRagioneriaTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		List<InoltroRagioneriaTreeNodeBean> lListResult = new ArrayList<InoltroRagioneriaTreeNodeBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("FILTRO_ATTI_INOLTRO_RAG_X_ASS");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("");
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
		
		try {
			List<InoltroRagioneriaTreeXmlBean> dataXml = XmlListaUtility.recuperaLista(xmlLista, InoltroRagioneriaTreeXmlBean.class);
			if(dataXml != null && !dataXml.isEmpty()) {
				for (InoltroRagioneriaTreeXmlBean inoltroRagioneriaTreeXmlBean : dataXml){			
					InoltroRagioneriaTreeNodeBean inoltroRagioneriaTreeNodeBean = new InoltroRagioneriaTreeNodeBean();
					inoltroRagioneriaTreeNodeBean.setIdNode(inoltroRagioneriaTreeXmlBean.getIdNode());
					inoltroRagioneriaTreeNodeBean.setParentId(inoltroRagioneriaTreeXmlBean.getParentId());
					if(inoltroRagioneriaTreeXmlBean.getNroLivello() != null && !"".equalsIgnoreCase(inoltroRagioneriaTreeXmlBean.getNroLivello())){
						inoltroRagioneriaTreeNodeBean.setNroLivello(new BigDecimal(inoltroRagioneriaTreeXmlBean.getNroLivello()));
					}
					inoltroRagioneriaTreeNodeBean.setNome(inoltroRagioneriaTreeXmlBean.getNome());
					inoltroRagioneriaTreeNodeBean.setConteggiNodo(inoltroRagioneriaTreeXmlBean.getConteggiNodo());
					inoltroRagioneriaTreeNodeBean.setIdProcessType(inoltroRagioneriaTreeXmlBean.getIdProcessType());
					inoltroRagioneriaTreeNodeBean.setAssegnatario(inoltroRagioneriaTreeXmlBean.getAssegnatario());
					inoltroRagioneriaTreeNodeBean.setDtInoltroRagioneria(inoltroRagioneriaTreeXmlBean.getDtInoltroRagioneria() != null
							? inoltroRagioneriaTreeXmlBean.getDtInoltroRagioneria() : null);
					lListResult.add(inoltroRagioneriaTreeNodeBean);
				}
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
//		InoltroRagioneriaTreeNodeBean v1 = new InoltroRagioneriaTreeNodeBean();
//		v1.setNome("primo livello");
//		v1.setIdNode("100");
//		v1.setNroLivello(new BigDecimal(1));
//		v1.setParentId("100");
//		lListResult.add(v1);
//		
//		InoltroRagioneriaTreeNodeBean v2 = new InoltroRagioneriaTreeNodeBean();
//		v2.setNome("secondo livello");
//		v2.setIdNode("101");
//		v2.setNroLivello(new BigDecimal(2));
//		v2.setParentId("100");
//		v2.setFlgEsplodiNodo("0");
//		lListResult.add(v2);
//		
//		InoltroRagioneriaTreeNodeBean v3 = new InoltroRagioneriaTreeNodeBean();
//		v3.setNome("terzo livello");
//		v3.setIdNode("102");
//		v3.setNroLivello(new BigDecimal(3));
//		v3.setParentId("100");
//		v3.setFlgEsplodiNodo("0");
//		lListResult.add(v3);
		
		PaginatorBean<InoltroRagioneriaTreeNodeBean> lPaginatorBean = new PaginatorBean<InoltroRagioneriaTreeNodeBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());
		
		return lPaginatorBean;
	}

}