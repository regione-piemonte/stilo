/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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

@Datasource(id = "LoadComboNomiAttributiCustomDataSource")
public class LoadComboNomiAttributiCustomDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String nomeTabella = StringUtils.isNotBlank(getExtraparams().get("nomeTabella")) ? getExtraparams().get("nomeTabella") : "";
		String idEntitaAssociata = StringUtils.isNotBlank(getExtraparams().get("idEntitaAssociata")) ? getExtraparams().get("idEntitaAssociata") : "";
		String appartenenza = StringUtils.isNotBlank(getExtraparams().get("appartenenza")) ? getExtraparams().get("appartenenza") : "";
		boolean flgComplex = StringUtils.isNotBlank(getExtraparams().get("flgComplex")) && "1".equals(getExtraparams().get("flgComplex"));
		boolean flgRipetibile = StringUtils.isNotBlank(getExtraparams().get("flgRipetibile")) && "1".equals(getExtraparams().get("flgRipetibile"));
		
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
		if(flgComplex) {
			inputParamComboBean.setTipocomboin("NOME_ATTRIBUTO_DINAMICO_COMPLEX");
		} else {
			inputParamComboBean.setTipocomboin("NOME_ATTRIBUTO_DINAMICO_SEMPLICE");
		}
		inputParamComboBean.setFlgsolovldin(new BigDecimal(1));
		String altriParametri = "CI_TO_ADD|*|" + nomeAttributo + "|*|STR_IN_LABEL|*|" + label + "|*|STR_IN_NOME|*|" + nome;
		if(StringUtils.isNotBlank(nomeTabella) && StringUtils.isNotBlank(idEntitaAssociata)) {
			altriParametri += "|*|NOME_TABELLA|*|" + nomeTabella + "|*|CI_TIPO_ENTITA|*|" + idEntitaAssociata;
		}
		if(StringUtils.isNotBlank(appartenenza)) {
			altriParametri += "|*|APPARTENENZA|*|" + appartenenza;
		}	
		if(flgRipetibile) {
			altriParametri += "|*|FLG_RIPETIBILE|*|1";
		} else {
			altriParametri += "|*|FLG_RIPETIBILE|*|0";
		}
		inputParamComboBean.setAltriparametriin(altriParametri);

		// Output
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), inputParamComboBean);

		String xmlLista = storeResult.getResultBean().getListaxmlout();

		List<SimpleKeyValueBean> resultList = new ArrayList<SimpleKeyValueBean>();

		List<XmlListaSimpleBean> listaXML = XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class);
		resultList = new ArrayList<SimpleKeyValueBean>();
		
		for (XmlListaSimpleBean lXmlListaSimpleBean : listaXML) {
			SimpleKeyValueBean lBean = new SimpleKeyValueBean();
			lBean.setKey(lXmlListaSimpleBean.getKey());
			lBean.setValue(lXmlListaSimpleBean.getValue());
			resultList.add(lBean);
		}

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(resultList);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(resultList.size());
		lPaginatorBean.setTotalRows(resultList.size());

		return lPaginatorBean;
	}

}
