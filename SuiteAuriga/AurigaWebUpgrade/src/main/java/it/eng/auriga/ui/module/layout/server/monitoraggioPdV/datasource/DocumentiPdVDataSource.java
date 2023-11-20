/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_int_cs.bean.DmpkIntCsTrovaitempdvBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.DocPdVBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.ErroriRdVBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.TrovaDocPdVFiltriXmlBean;
import it.eng.client.DmpkIntCsTrovaitempdv;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="DocumentiPdVDataSource")
public class DocumentiPdVDataSource extends AbstractFetchDataSource<DocPdVBean>{

	@Override
	public PaginatorBean<DocPdVBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<DocPdVBean> data = new ArrayList<DocPdVBean>();
		
		String idPdV = getExtraparams().get("idPdV") != null && !"".equals(getExtraparams().get("idPdV")) ?
				getExtraparams().get("idPdV") : "";
		
		DmpkIntCsTrovaitempdvBean lDmpkIntCsTrovaitempdvBean = new DmpkIntCsTrovaitempdvBean();
		lDmpkIntCsTrovaitempdvBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkIntCsTrovaitempdvBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkIntCsTrovaitempdvBean.setColorderbyio(null);
		lDmpkIntCsTrovaitempdvBean.setFlgdescorderbyio(null);
		lDmpkIntCsTrovaitempdvBean.setFlgsenzapaginazionein(new Integer(1)); // lista non paginata
		lDmpkIntCsTrovaitempdvBean.setNropaginaio(null);
		lDmpkIntCsTrovaitempdvBean.setBachsizeio(null);
		lDmpkIntCsTrovaitempdvBean.setOverflowlimitin(null);
		lDmpkIntCsTrovaitempdvBean.setFlgsenzatotin(null);
		lDmpkIntCsTrovaitempdvBean.setFlgbatchsearchin(null);
		lDmpkIntCsTrovaitempdvBean.setFiltriio(new XmlUtilitySerializer().bindXml(buildFilter(idPdV,criteria)));	
		
		DmpkIntCsTrovaitempdv lDmpkIntCsTrovaitempdv = new DmpkIntCsTrovaitempdv();
		StoreResultBean<DmpkIntCsTrovaitempdvBean> lStoreResultBean = lDmpkIntCsTrovaitempdv.execute(getLocale(), loginBean, lDmpkIntCsTrovaitempdvBean);
		
		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		} 
		
		if(StringUtils.isNotBlank(lStoreResultBean.getResultBean().getResultout())) {			
			data = XmlListaUtility.recuperaLista(lStoreResultBean.getResultBean().getResultout(), DocPdVBean.class);
		}else {
			data = getTestData();				
		}	

		PaginatorBean<DocPdVBean> lPaginatorBean = new PaginatorBean<DocPdVBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;	
	}

	private List<DocPdVBean> getTestData() {
		
		List<DocPdVBean> data = new ArrayList<DocPdVBean>();
		
		DocPdVBean lDocPdVBean = new DocPdVBean();
		lDocPdVBean.setIdPdV("1");
		lDocPdVBean.setIdDocOriginale("1");
		lDocPdVBean.setAttributiChiaveDoc(new LinkedHashMap<String, Object>());
		lDocPdVBean.setEsito("OK");
		lDocPdVBean.setErroriRdV(new ArrayList<ErroriRdVBean>());		
		lDocPdVBean.setDimensione("1024");	

		data.add(lDocPdVBean);
		
		return data;
	}
	
	private TrovaDocPdVFiltriXmlBean buildFilter(String idPdV,AdvancedCriteria criteria) {
		
		TrovaDocPdVFiltriXmlBean lTrovaDocPdVFiltriXmlBean = new TrovaDocPdVFiltriXmlBean();
		
		String idItemInviatoCons = null;
		String esitoInvioCons = null;
		String codiciErrWarn = null;
		String msgErrWarn = null;
		String idTipiItem = null;
		String idItemConservatore = null;
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("idItemInviatoCons".equals(crit.getFieldName())) {
					idItemInviatoCons = getTextFilterValue(crit);
				} else if ("esitoInvioCons".equals(crit.getFieldName())) {
					esitoInvioCons = getTextFilterValue(crit);
				} else if("codiciErrWarn".equals(crit.getFieldName())) {
					codiciErrWarn = getTextFilterValue(crit);
				} else if("msgErrWarn".equals(crit.getFieldName())) {
					msgErrWarn = getTextFilterValue(crit);
				} else if("idTipiItem".equals(crit.getFieldName())) {
					idTipiItem = getTextFilterValue(crit);
				} else if("idItemConservatore".equals(crit.getFieldName())) {
					idItemConservatore = getTextFilterValue(crit);
				} 
			}
		}
		
		if (StringUtils.isNotBlank(idPdV)) {
			lTrovaDocPdVFiltriXmlBean.setIdPdV(idPdV);
		}
		if (StringUtils.isNotBlank(idItemInviatoCons)) {
			lTrovaDocPdVFiltriXmlBean.setIdItemInviatoCons(idItemInviatoCons);
		}
		if (StringUtils.isNotBlank(esitoInvioCons)) {
			lTrovaDocPdVFiltriXmlBean.setEsitoInvioCons(esitoInvioCons);
		}
		if (StringUtils.isNotBlank(codiciErrWarn)) {
			lTrovaDocPdVFiltriXmlBean.setCodiciErrWarn(codiciErrWarn);
		}
		if (StringUtils.isNotBlank(msgErrWarn)) {
			lTrovaDocPdVFiltriXmlBean.setMsgErrWarn(msgErrWarn);
		}
		if (StringUtils.isNotBlank(idTipiItem)) {
			lTrovaDocPdVFiltriXmlBean.setIdTipiItem(idTipiItem);
		}
		if (StringUtils.isNotBlank(idItemConservatore)) {
			lTrovaDocPdVFiltriXmlBean.setIdItemConservatore(idItemConservatore);
		}
		
		return lTrovaDocPdVFiltriXmlBean;
	}

}
