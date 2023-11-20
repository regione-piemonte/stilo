/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.GruppoSoggettiBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboGruppoSoggettiDataSource")
public class LoadComboGruppoSoggettiDataSource extends SelectDataSource<GruppoSoggettiBean> {
	
		@Override	
		public PaginatorBean<GruppoSoggettiBean> realFetch(AdvancedCriteria criteria,
				Integer startRow, Integer endRow, List<OrderByBean> orderby)
				throws Exception {		
		
		boolean isGruppiDestinatari = getExtraparams().get("isGruppiDestinatari") != null && "true".equalsIgnoreCase(getExtraparams().get("isGruppiDestinatari"));		
		String usaFlagSoggettiInterni = StringUtils.isNotBlank(getExtraparams().get("usaFlagSoggettiInterni")) ? getExtraparams().get("usaFlagSoggettiInterni") : "";		
		String flgSoloGruppiSoggInt = StringUtils.isNotBlank(getExtraparams().get("flgSoloGruppiSoggInt")) ? getExtraparams().get("flgSoloGruppiSoggInt") : "1";		
		String flgSoloAssegnabili = StringUtils.isNotBlank(getExtraparams().get("flgSoloAssegnabili")) ? getExtraparams().get("flgSoloAssegnabili") : "";	
		String flgEscludiGruppiMisti = StringUtils.isNotBlank(getExtraparams().get("flgEscludiGruppiMisti")) ? getExtraparams().get("flgEscludiGruppiMisti") : "";		
		String flgEscludiGruppiNonEsterni = StringUtils.isNotBlank(getExtraparams().get("flgEscludiGruppiNonEsterni")) ? getExtraparams().get("flgEscludiGruppiNonEsterni") : "";		
		boolean escludiFlgSoloAssegnabili = getExtraparams().get("escludiFlgSoloAssegnabili") != null && "true".equalsIgnoreCase(getExtraparams().get("escludiFlgSoloAssegnabili"));		
		boolean abilSelDestinatariReg = getExtraparams().get("abilSelDestinatariReg") != null && "true".equalsIgnoreCase(getExtraparams().get("abilSelDestinatariReg"));		
		
		String idGruppo = StringUtils.isNotBlank(getExtraparams().get("idGruppo")) ? getExtraparams().get("idGruppo") : "";		
		
		String codiceRapidoGruppo = StringUtils.isNotBlank(getExtraparams().get("codiceRapidoGruppo")) ? getExtraparams().get("codiceRapidoGruppo") : "";
		String nomeGruppo = "";
		String flagSoggettiGruppo = "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("codiceRapidoGruppo")) {
					codiceRapidoGruppo = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("nomeGruppo")) {
					nomeGruppo = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("flagSoggettiGruppo")) {
					flagSoggettiGruppo = (String) criterion.getValue();					
				}
			}
		}			
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("GRUPPO_SOGG_RUBRICA");		
		
		// Se l'utente ha digitato un filtro, il CI_TO_ADD non deve essere passato
		if (!codiceRapidoGruppo.equalsIgnoreCase("")  ||  !nomeGruppo.equalsIgnoreCase("") || !flagSoggettiGruppo.equalsIgnoreCase("")){
			if (usaFlagSoggettiInterni.equalsIgnoreCase("S")) {
				if(escludiFlgSoloAssegnabili) {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_GRUPPI_SOGG_INT|*|" + flgSoloGruppiSoggInt);
				} else {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_GRUPPI_SOGG_INT|*|" + flgSoloGruppiSoggInt + "|*|FLG_SOLO_ASSEGNABILI|*|" + flgSoloAssegnabili);
				}
			} else {
				if(escludiFlgSoloAssegnabili) {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo);		
				} else {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_ASSEGNABILI|*|" + flgSoloAssegnabili);		
				}
			}
		} else {
			if (usaFlagSoggettiInterni.equalsIgnoreCase("S")) {			
				if(escludiFlgSoloAssegnabili) {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_GRUPPI_SOGG_INT|*|" + flgSoloGruppiSoggInt + "|*|ID_GRUPPO_INT_TO_ADD|*|" + idGruppo);
				} else {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_GRUPPI_SOGG_INT|*|" + flgSoloGruppiSoggInt + "|*|FLG_SOLO_ASSEGNABILI|*|" + flgSoloAssegnabili + "|*|ID_GRUPPO_INT_TO_ADD|*|" + idGruppo);
				}
			} else {				
				if(escludiFlgSoloAssegnabili) {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|CI_TO_ADD|*|" + idGruppo);
				} else {
					lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("COD_RAPIDO|*|" + codiceRapidoGruppo + "|*|STR_IN_NOME|*|" + nomeGruppo + "|*|FLG_SOLO_ASSEGNABILI|*|" + flgSoloAssegnabili + "|*|CI_TO_ADD|*|" + idGruppo);
				}
			}
		}
		if (abilSelDestinatariReg) {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(lDmpkLoadComboDmfn_load_comboBean.getAltriparametriin() + "|*|FINALITA|*|SEL_DESTINATARI_REG");
		}
		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
		lDmpkLoadComboDmfn_load_comboBean.setPkrecin(null);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);		
		PaginatorBean<GruppoSoggettiBean> lPaginatorBean = new PaginatorBean<GruppoSoggettiBean>();				
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<GruppoSoggettiBean> lista = new ArrayList<GruppoSoggettiBean>();
			int size = 0;
			if(StringUtils.isNotBlank(xmlLista)) {
				lista = XmlListaUtility.recuperaLista(xmlLista, GruppoSoggettiBean.class);
				if(lista != null) {
					for (GruppoSoggettiBean lRiga : lista){
						GruppoSoggettiBean lGruppoSoggettiBean = new GruppoSoggettiBean();		
						if(isGruppiDestinatari) {
							lGruppoSoggettiBean.setIdGruppo(lRiga.getIdSoggettiNonInterni());
						} else {
							lGruppoSoggettiBean.setIdGruppo(flgSoloGruppiSoggInt.equalsIgnoreCase("2") ? lRiga.getIdSoggettiInterni() : lRiga.getIdSoggettiNonInterni());				
						}
						lGruppoSoggettiBean.setCodiceRapidoGruppo(lRiga.getCodiceRapidoGruppo());				
						lGruppoSoggettiBean.setNomeGruppo(lRiga.getNomeGruppo());
						lGruppoSoggettiBean.setFlagSoggettiGruppo(lRiga.getFlagSoggettiGruppo());
						lGruppoSoggettiBean.setIdSoggettiInterni(lRiga.getIdSoggettiInterni());
						lGruppoSoggettiBean.setIdSoggettiNonInterni(lRiga.getIdSoggettiNonInterni());
						lGruppoSoggettiBean.setFlgSelXAssegnazione(lRiga.getFlgSelXAssegnazione());
						if(flgEscludiGruppiMisti.equalsIgnoreCase("1") && lGruppoSoggettiBean.getFlagSoggettiGruppo() != null && lGruppoSoggettiBean.getFlagSoggettiGruppo().equalsIgnoreCase("M")) {
							// se devo escludere i gruppi misti e flagSoggettiGruppo = M allora non lo aggiungo
						} else if(flgEscludiGruppiNonEsterni.equalsIgnoreCase("1") && (lGruppoSoggettiBean.getFlagSoggettiGruppo() == null || !lGruppoSoggettiBean.getFlagSoggettiGruppo().equalsIgnoreCase("E"))) {
							// se devo escludere i gruppi non esterni e flagSoggettiGruppo != E allora non lo aggiungo	
						} else {
							lPaginatorBean.addRecord(lGruppoSoggettiBean);
							size ++;
						}
					}
				}
			}
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(size);
			lPaginatorBean.setTotalRows(size);			
		} 		
		return lPaginatorBean;		
	}
}