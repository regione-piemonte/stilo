/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.bean.FieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

@Datasource(id = "ListFieldDataSource")
public class ListFieldDataSource extends AbstractFetchDataSource<FieldBean> {

	@Override
	public PaginatorBean<FieldBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String tableName = getExtraparams().get("tableName");
		String idNode = getExtraparams().get("idNode") != null ? getExtraparams().get("idNode") : "";
		
		String selectedCriteria = null;
		if (criteria.getCriteria() != null && criteria.getCriteria().size() > 0) {
			selectedCriteria = (String) criteria.getCriteria().get(0).getValue();
		}

		if (criteria != null) {
			String value = (String) criteria.getValue();
			if (StringUtils.isNotBlank(value)) {
				int posizione = value.indexOf(";");
				selectedCriteria = value.substring(0, posizione);
			}
		}
		// Federico Cacco 14-10-2015
		// Carico filtro tradotto
		FilterConfigurator lFilterConfigurator = getXmlFiltriTradotto();
		// FilterConfigurator lFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("FilterConfigurator");
		FilterBean filterBean = lFilterConfigurator.getListe().get(tableName);

		HashSet<String> lSetToExclude = null;
		if (StringUtils.isNotBlank(selectedCriteria)) {
			lSetToExclude = new HashSet<String>(Arrays.asList(selectedCriteria.split(",")));
		}

		PaginatorBean<FieldBean> lPaginatorBean = new PaginatorBean<FieldBean>();
		List<FieldBean> lList = new ArrayList<FieldBean>();

		if(filterBean != null) { 
			for (FilterFieldBean field : filterBean.getFields()) {
				FieldBean lFieldBean = new FieldBean();
				lFieldBean.setName(field.getName());
				if (field.isRequiredIfPrivilegi()) {
					String[] privilegi = ServiceRestUserUtil.getPrivilegi().getPrivilegi(getSession());
					field.setRequired(ServiceRestUserUtil.getFilterPrivilegi().isRequired(field.getName(), privilegi));
				}
				String title = field.isRequired() ? FrontendUtil.getRequiredFormItemTitle(field.getTitle()) : field.getTitle();
				lFieldBean.setTitle(title);
				
				// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT
				// rinomino In competenza a => Protocollo assegnatario
				// rinomino U.O. di registrazione => U.O. di creazione
				if (idNode!= null && idNode.equals("D.BOZZE.DAPROT")) {
					if("assegnatoA".equals(lFieldBean.getName())) {				
						lFieldBean.setTitle("Protocollo assegnatario");
					}
					if("uoRegistrazione".equals(lFieldBean.getName())) {
						lFieldBean.setTitle("U.O. di creazione");
					}
				}
					
				// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE o D.2A.DAPROT
				// rinomino U.O. di registrazione => U.O. di creazione 
				if (idNode!= null && (idNode.equals("D.BOZZE") || idNode.equals("D.2A.DAPROT"))) {
					if("uoRegistrazione".equals(lFieldBean.getName())) {
						lFieldBean.setTitle("U.O. di creazione");
					}
				}
				
				lFieldBean.setType("text");
				if (lSetToExclude == null || !lSetToExclude.contains(field.getName())) {
					lList.add(lFieldBean);
				}
			}
	
			/**
			 * Se il valore isOrdinableFilter configurato dentro il file di configurazione di aurigaweb filter.xml è settato a true, tutti i filtri delle portlet
			 * vengono ordinati per titolo ( valore della properties relativa) Se viene settatto a false oppure viene omesso non vengono ordinate
			 */
			if (lFilterConfigurator.getIsOrdinableFilter() != null && lFilterConfigurator.getIsOrdinableFilter()) {
				Collections.sort(lList, new Comparator<FieldBean>() {
	
					@Override
					public int compare(FieldBean fb1, FieldBean fb2) {
						
						return fb1.getTitle().compareTo(fb2.getTitle());
					}
				});
			}
		}

		lPaginatorBean.setData(lList);
		lPaginatorBean.setEndRow(lList.size());
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setTotalRows(lList.size());

		return lPaginatorBean;
	}

}
