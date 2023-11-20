/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "EventoAMCDataSource")
public class EventoAMCDataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {
	
	public boolean isAttivoSIB() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
	public boolean isAttivoContabilia() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC));
	}
	
	public boolean isAttivoSICRA() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SICRA".equalsIgnoreCase(lSistAMC);
	}
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {		
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		List<SimpleKeyValueBean> data = new ArrayList<SimpleKeyValueBean>();
		
		if(isAttivoSIB()) {
			data.add(buildSimpleKeyValueBean("aggiornamento", "aggiornamento"));
			data.add(buildSimpleKeyValueBean("adozione", "adozione"));
			data.add(buildSimpleKeyValueBean("visto", "visto"));
			data.add(buildSimpleKeyValueBean("archiviazione", "archiviazione"));
//			data.add(buildSimpleKeyValueBean("aggiudica", "aggiudica"));
		} else if(isAttivoContabilia()) {
			data.add(buildSimpleKeyValueBean("aggiornaProposta", "aggiornaProposta"));	
			data.add(buildSimpleKeyValueBean("bloccoDatiProposta", "bloccoDatiProposta"));	
			data.add(buildSimpleKeyValueBean("invioAttoDef", "invioAttoDef"));	
			data.add(buildSimpleKeyValueBean("invioAttoDefEsec", "invioAttoDefEsec"));	
			data.add(buildSimpleKeyValueBean("invioAttoEsec", "invioAttoEsec"));	
			data.add(buildSimpleKeyValueBean("sbloccoDatiProposta", "sbloccoDatiProposta"));	
			data.add(buildSimpleKeyValueBean("annullamentoProposta", "annullamentoProposta"));				
		} else if(isAttivoSICRA()) {
			data.add(buildSimpleKeyValueBean("archiviaAtto", "archiviaAtto"));	
			data.add(buildSimpleKeyValueBean("setEsecutivitaAtto", "setEsecutivitaAtto"));
			data.add(buildSimpleKeyValueBean("aggiornaRifAttoLiquidazione", "aggiornaRifAttoLiquidazione"));
		}		
		
		lPaginatorBean.setData(data);			
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;
	}
	
	public SimpleKeyValueBean buildSimpleKeyValueBean(String key, String value) {
		SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
		lSimpleKeyValueBean.setKey(key);
		lSimpleKeyValueBean.setValue(value);
		return lSimpleKeyValueBean;
	}

}
