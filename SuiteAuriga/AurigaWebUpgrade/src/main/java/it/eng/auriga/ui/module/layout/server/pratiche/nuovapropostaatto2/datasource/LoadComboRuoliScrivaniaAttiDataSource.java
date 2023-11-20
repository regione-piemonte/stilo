/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ScrivaniaOrganigrammaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboRuoliScrivaniaAttiDataSource")
public class LoadComboRuoliScrivaniaAttiDataSource extends SelectDataSource<ScrivaniaOrganigrammaBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboRuoliScrivaniaAttiDataSource.class);

	@Override
	public PaginatorBean<ScrivaniaOrganigrammaBean> realFetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		String idSvSelezionata = StringUtils.isNotBlank(getExtraparams().get("idSvSelezionata")) ? getExtraparams().get("idSvSelezionata") : "";
		String idSvFromLoadDett = StringUtils.isNotBlank(getExtraparams().get("idSvFromLoadDett")) ? getExtraparams().get("idSvFromLoadDett") : "";
		String idUdAtto = StringUtils.isNotBlank(getExtraparams().get("idUdAtto")) ? getExtraparams().get("idUdAtto") : "";
		String idUdAttoDaAnn = StringUtils.isNotBlank(getExtraparams().get("idUdAttoDaAnn")) ? getExtraparams().get("idUdAttoDaAnn") : "";
		
		String uoProponente = StringUtils.isNotBlank(getExtraparams().get("uoProponente")) ? getExtraparams().get("uoProponente") : "";
		if (uoProponente.startsWith("UO")) {
			uoProponente = uoProponente.substring(2);
		}
				
		String uoCompetente = StringUtils.isNotBlank(getExtraparams().get("uoCompetente")) ? getExtraparams().get("uoCompetente") : "";
		if (uoCompetente.startsWith("UO")) {
			uoCompetente = uoCompetente.substring(2);
		}
		
		String flgAttoMeroIndirizzo = StringUtils.isNotBlank(getExtraparams().get("flgAttoMeroIndirizzo")) ? getExtraparams().get("flgAttoMeroIndirizzo") : "";
		
		String codUo = StringUtils.isNotBlank(getExtraparams().get("codUo")) ? getExtraparams().get("codUo") : "";
		String descrizione = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (StringUtils.isBlank(codUo) && criterion.getFieldName().equals("codUo")) {
					codUo = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}
		
		String altriParametri = getExtraparams().get("altriParamLoadCombo");
		boolean isAltriParamWithNriLivelliUo = altriParametri != null && altriParametri.indexOf("NRI_LIVELLI_UO|*|") != -1;
		boolean isAltriParamWithStrInDes = altriParametri != null && altriParametri.indexOf("STR_IN_DES|*|") != -1;
		boolean isAltriParamWithDesRuoloOrIdUo = altriParametri != null && (altriParametri.indexOf("DES_RUOLO|*|") != -1 || altriParametri.indexOf("ID_UO|*|") != -1);
		
		boolean isDimOrganigrammaNonStd = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD");
		boolean isRequiredFilterCodUo = isDimOrganigrammaNonStd && !isAltriParamWithDesRuoloOrIdUo && isAltriParamWithNriLivelliUo;
		boolean isRequiredFilterDescrizione = !isRequiredFilterCodUo && isDimOrganigrammaNonStd && !isAltriParamWithDesRuoloOrIdUo && isAltriParamWithStrInDes;
		
		List<ScrivaniaOrganigrammaBean> lListResult = new ArrayList<ScrivaniaOrganigrammaBean>();
		
		boolean isError = false;
		if ((!isRequiredFilterCodUo && !isRequiredFilterDescrizione) || (isRequiredFilterCodUo && !"".equals(codUo)) || (isRequiredFilterDescrizione && !"".equals(descrizione) && descrizione.length() >= 3)) {
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SCRIVANIE_ORGANIGRAMMA");
			
			if(StringUtils.isNotBlank(altriParametri)) {
				if(altriParametri.contains("$NRI_LIVELLI_UO$") || altriParametri.contains("$STR_IN_DES$")) {
					// se la select è filtrabile e mi arriva un filtro (codUo o descrizione) non devo passare il valore della scrivania selezionata
					if((altriParametri.contains("$NRI_LIVELLI_UO$") && !"".equals(codUo)) || (altriParametri.contains("$STR_IN_DES$") && !"".equals(descrizione))) {
						idSvSelezionata = "";
					}
				} else {
					// se la select non è filtrabile non devo passare il valore della scrivania selezionata
					idSvSelezionata = "";
				}
				altriParametri = altriParametri.replace("$ID_USER_LAVORO$", idUserLavoro);
				altriParametri = altriParametri.replace("$ID_UO_PROPONENTE$", uoProponente);
				altriParametri = altriParametri.replace("$ID_UO_COMPETENTE$", uoCompetente);
				altriParametri = altriParametri.replace("$ID_UD_ATTO$", idUdAtto);
				altriParametri = altriParametri.replace("$ID_UD_ATTO_DA_ANN$", idUdAttoDaAnn);
				altriParametri = altriParametri.replace("$NRI_LIVELLI_UO$", codUo);			
				altriParametri = altriParametri.replace("$STR_IN_DES$", descrizione);
				altriParametri = altriParametri.replace("$CI_TO_ADD$", idSvFromLoadDett);
				altriParametri = altriParametri.replace("$FLG_MERO_INDIRIZZO$", flgAttoMeroIndirizzo);
				altriParametri = altriParametri.replace("$ID_SV_SELEZIONATA$", idSvSelezionata);
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
			} else {
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + idSvFromLoadDett);		
			}
				
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
					loginBean, lDmpkLoadComboDmfn_load_comboBean);
				
			if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				lListResult = XmlListaUtility.recuperaLista(xmlLista, ScrivaniaOrganigrammaBean.class);
			} else {
				isError = true;
				mLogger.error(lStoreResultBean.getDefaultMessage());
			}
		}

		PaginatorBean<ScrivaniaOrganigrammaBean> lPaginatorBean = new PaginatorBean<ScrivaniaOrganigrammaBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(isError ? -1 : lListResult.size());

		return lPaginatorBean;
	}

}
