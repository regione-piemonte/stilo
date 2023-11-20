/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboValoriDizionarioDataSource")
public class LoadComboValoriDizionarioDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		String dictionaryEntry = getExtraparams().get("dictionaryEntry") != null ? getExtraparams().get("dictionaryEntry") : "";
		String flgSelectMulti = getExtraparams().get("flgSelectMulti") != null ? getExtraparams().get("flgSelectMulti") : "";		
		boolean requiredStrInDes = getExtraparams().get("requiredStrInDes") != null && getExtraparams().get("requiredStrInDes").equalsIgnoreCase("true");
		String strInDes = getExtraparams().get("strInDes") != null ? getExtraparams().get("strInDes") : "";		
		
		String value = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("value")) {
					value = (String) criterion.getValue();
				}
			}
		}
		
		String altriParametri = null;
		if(StringUtils.isNotBlank(getExtraparams().get("altriParamLoadCombo"))) {
			String uoProponente = StringUtils.isNotBlank(getExtraparams().get("uoProponente")) ? getExtraparams().get("uoProponente") : "";
			if (uoProponente.startsWith("UO")) {
				uoProponente = uoProponente.substring(2);
			}
			String flgSpesa = StringUtils.isNotBlank(getExtraparams().get("flgSpesa")) ? getExtraparams().get("flgSpesa") : "";
			String codValoreVincolo = StringUtils.isNotBlank(getExtraparams().get("codValoreVincolo")) ? getExtraparams().get("codValoreVincolo") : "";
			altriParametri = getExtraparams().get("altriParamLoadCombo");			
			altriParametri = altriParametri.replace("$ID_UO_PROPONENTE$", uoProponente);			
			altriParametri = altriParametri.replace("$ATTO_CON_SPESA$", flgSpesa);
			altriParametri = altriParametri.replace("$COD_VALORE_VINCOLO$", codValoreVincolo);
			altriParametri = altriParametri.replace("$STR_IN_DES$", value);			
		} else if(StringUtils.isNotBlank(dictionaryEntry) && (!requiredStrInDes || StringUtils.isNotBlank(strInDes))) { 
			altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|DICTIONARY_ENTRY|*|" + dictionaryEntry + "|*|FLG_SELECT_MULTI|*|" + flgSelectMulti;
			if(StringUtils.isNotBlank(strInDes)) {
				altriParametri += "|*|STR_IN_DES|*|" + strInDes;
			}
		}
		
		List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		
		if(StringUtils.isNotBlank(altriParametri)) {
			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
			
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
					
			if(!lStoreResultBean.isInError()) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				for (XmlListaSimpleBean lXmlListaSimpleBean : XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class)) {
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
					lSimpleKeyValueBean.setKey(lXmlListaSimpleBean.getKey());
					lSimpleKeyValueBean.setValue(lXmlListaSimpleBean.getValue());
					lista.add(lSimpleKeyValueBean);
				}		
			} 
			
		}
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());
		
		return lPaginatorBean;
	}
	
}