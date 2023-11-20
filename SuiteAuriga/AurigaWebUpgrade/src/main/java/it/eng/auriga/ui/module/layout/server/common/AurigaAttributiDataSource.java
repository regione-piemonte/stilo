/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.bean.RetrieveIndexedFieldsBean;
import it.eng.auriga.ui.module.layout.shared.bean.AttributiBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.LuceneService;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.RetrieveIndexedFields;
import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterType;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="AurigaAttributiDataSource")

public class AurigaAttributiDataSource extends AbstractServiceDataSource<AttributiBean, AttributiBean>{
	
	private static Logger mLogger = Logger.getLogger(AurigaAttributiDataSource.class);

	@Override
	public AttributiBean call(AttributiBean bean) throws Exception {
		// Federico Cacco 14-10-2015
		// Carico filtro tradotto
		FilterConfigurator lFilterConfigurator = getXmlFiltriTradotto();
		// FilterConfigurator lFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("FilterConfigurator");
		AttributiBean result = new AttributiBean();		
		if(lFilterConfigurator != null && lFilterConfigurator.getListe() != null) {
			for(Object key : lFilterConfigurator.getListe().keySet()) {
				final String nomeEntita = (String) key;
				FilterBean lFilterBean = lFilterConfigurator.getListe().get(nomeEntita);
				if(lFilterBean != null) {
					for(FilterFieldBean lFilterFieldBean : lFilterBean.getFields()) {
						FilterType type = lFilterFieldBean.getType();
						if(type != null && (type.equals(FilterType.stringa_full_text))) {							
							String categoria = StringUtils.isNotBlank(lFilterFieldBean.getCategoria()) ? lFilterFieldBean.getCategoria() : "REP_DOC";
							if(RetrieveIndexedFields.getIndexedFieldsCategoria() == null || !RetrieveIndexedFields.getIndexedFieldsCategoria().containsKey(categoria)) {
								LuceneService lLuceneService = new LuceneService();
								RetrieveIndexedFieldsBean lRetrieveIndexedFieldsBean = new RetrieveIndexedFieldsBean();
								lRetrieveIndexedFieldsBean.setIdentificativo(categoria);
								StringBuffer lStringBuffer = new StringBuffer();
								try  {
									Collection<String> lCollection = lLuceneService.retrieveindexedfields(getLocale(), lRetrieveIndexedFieldsBean).getResults();
									if(lCollection != null) {
										for (String lString : lCollection){
											lStringBuffer.append(lString);
											lStringBuffer.append(";");
										}
									}
								} catch(Exception e) {
									mLogger.warn(e);
								}
								RetrieveIndexedFields.addIndexedFields(categoria, lStringBuffer.toString());
							}
							DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
							lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("INDEXED_ATTRIBUTES");
							lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ATTR_LIST|*|" + RetrieveIndexedFields.getIndexedFieldsCategoria().get(categoria) + "|*|CATEGORIA|*|" + categoria);
//							ATTRL_LIS|*|<lista restituita dal metodo fatto da Jacopo, separati da ;>|*|CATEGORIA|*|<categoria>
							DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
							StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = 
									lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);	
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
							if (lStoreResultBean.getDefaultMessage()==null){
								String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
								List<XmlListaSimpleBean> lista = XmlUtility.recuperaListaSimpleValue(xmlLista);
								for (XmlListaSimpleBean lRiga : lista){
									valueMap.put(lRiga.getKey(), lRiga.getValue());
								}																
							}
							result.addAttributiValueMap(nomeEntita, valueMap);
						}					
					}
				}				
			}
		}
		return result;
	}

}

//public class AurigaAttributiDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
//
//	@Override
//	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
//			Integer startRow, Integer endRow, List<OrderByBean> orderby)
//			throws Exception {
//		
//		String categoria = StringUtils.isNotBlank(getExtraparams().get("categoria")) ? getExtraparams().get("categoria") : "REP_DOC";		
//		
//		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
//		LuceneService lLuceneService = new LuceneService();
//		RetrieveIndexedFieldsBean lRetrieveIndexedFieldsBean = new RetrieveIndexedFieldsBean();
//		lRetrieveIndexedFieldsBean.setIdentificativo(categoria);
//		Collection<String> lCollection = lLuceneService.retrieveindexedfields(getLocale(), lRetrieveIndexedFieldsBean).getResults();
//		StringBuffer lStringBuffer = new StringBuffer();
//		for (String lString : lCollection){
//			lStringBuffer.append(lString);
//			lStringBuffer.append(";");
//		}
//		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
//		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("INDEXED_ATTRIBUTES");
//		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ATTR_LIST|*|" + lStringBuffer.toString() + "|*|CATEGORIA|*|" + categoria);
////		ATTRL_LIS|*|<lista restituita dal metodo fatto da Jacopo, separati da ;>|*|CATEGORIA|*|<categoria>
//		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = 
//			lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
//		if (lStoreResultBean.getDefaultMessage()==null){
//			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
//			Lista lista = Lista.unmarshal(new StringReader(xmlLista));
//			PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
//			for (Riga lRiga : lista.getRiga()){
//				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
//				String key = lRiga.getColonna(0).getContent();
//				lSimpleKeyValueBean.setKey(key);
//				String value = lRiga.getColonna(1).getContent();
//				lSimpleKeyValueBean.setValue(value);
//				lPaginatorBean.addRecord(lSimpleKeyValueBean);
//			}
//			lPaginatorBean.setStartRow(0);
//			lPaginatorBean.setEndRow(lista.getRiga().length);
//			lPaginatorBean.setTotalRows(lista.getRiga().length);
//			return lPaginatorBean;
//		} else {
//			PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
//			for (int i = 0; i<5; i++){
//				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
//				String key = "key" + i;
//				lSimpleKeyValueBean.setKey(key);
//				String value = "value" + i;
//				lSimpleKeyValueBean.setValue(value);
//				lPaginatorBean.addRecord(lSimpleKeyValueBean);
//			}
//			lPaginatorBean.setStartRow(0);
//			lPaginatorBean.setEndRow(5);
//			lPaginatorBean.setTotalRows(5);
//			return lPaginatorBean;
//		}
//	}
//
//}
