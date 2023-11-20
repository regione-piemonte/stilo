/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboAttributiAddEditabiliDataSource")
public class LoadComboAttributiAddEditabiliDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {
	
	private static final Logger log = Logger.getLogger(LoadComboAttributiAddEditabiliDataSource.class);

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String codTipoFlusso = StringUtils.isNotBlank(getExtraparams().get("codTipoFlusso")) ? getExtraparams().get("codTipoFlusso") : "";
		String nomeTask = StringUtils.isNotBlank(getExtraparams().get("nomeTask")) ? getExtraparams().get("nomeTask") : "";
		String nomeAttributo =  StringUtils.isNotBlank(getExtraparams().get("nomeAttributo")) ? getExtraparams().get("nomeAttributo") : "";
		
		String nome = "";
		String label = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("key")) {
					nome = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("value")) {
					label = (String) criterion.getValue();
				}
			}
		}	
		
		// Input
		DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
		inputParamComboBean.setTipocomboin("NOME_ATTRIBUTO_DINAMICO_SEMPLICE");
		inputParamComboBean.setFlgsolovldin(new BigDecimal(1));
		inputParamComboBean.setAltriparametriin("CI_TO_ADD|*|" + nomeAttributo + "|*|STR_IN_LABEL|*|" + label + "|*|STR_IN_NOME|*|" + nome + "|*|PROV_CI_TY_FLUSSO_WF|*|" + codTipoFlusso + "|*|DES_ATTIVITA|*|" + nomeTask);	
		
		// Output
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);

		List<SimpleKeyValueBean> resultList = new ArrayList<SimpleKeyValueBean>();
		String xmlList = storeResult.getResultBean().getListaxmlout();
		
		try {
			List<XmlListaSimpleBean> listaXML = XmlListaUtility.recuperaLista(xmlList, XmlListaSimpleBean.class);
	
			for (XmlListaSimpleBean lXmlListaSimpleBean : listaXML) {
				SimpleKeyValueBean lBean = new SimpleKeyValueBean();
				lBean.setKey(lXmlListaSimpleBean.getKey());
				lBean.setValue(lXmlListaSimpleBean.getValue());
				resultList.add(lBean);
			}
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(resultList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(resultList.size());
		lPaginatorBean.setTotalRows(resultList.size());

		return lPaginatorBean;
	}

}
