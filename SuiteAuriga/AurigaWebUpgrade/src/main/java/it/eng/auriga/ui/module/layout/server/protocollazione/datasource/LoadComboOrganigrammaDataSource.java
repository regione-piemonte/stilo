/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OrganigrammaBean;
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

@Datasource(id = "LoadComboOrganigrammaDataSource")
public class LoadComboOrganigrammaDataSource extends SelectDataSource<OrganigrammaBean> {

	private static Logger mLogger = Logger.getLogger(LoadComboOrganigrammaDataSource.class);

	@Override
	public PaginatorBean<OrganigrammaBean> realFetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		String uoProponente = StringUtils.isNotBlank(getExtraparams().get("uoProponente")) ? getExtraparams().get("uoProponente") : "";
		if (uoProponente.startsWith("UO")) {
			uoProponente = uoProponente.substring(2);
		}
		
		String ciToAdd = StringUtils.isNotBlank(getExtraparams().get("ciToAdd")) ? getExtraparams().get("ciToAdd") : "";		
		if ("".equals(ciToAdd)) {
			String flgUoSv = StringUtils.isNotBlank(getExtraparams().get("flgUoSv")) ? getExtraparams().get("flgUoSv") : "";
			String idUoSv = StringUtils.isNotBlank(getExtraparams().get("idUoSv")) ? getExtraparams().get("idUoSv") : "";
			ciToAdd = flgUoSv + idUoSv;
		}

		String idEmail = StringUtils.isNotBlank(getExtraparams().get("idEmail")) ? getExtraparams().get("idEmail") : "";
		String idUd = StringUtils.isNotBlank(getExtraparams().get("idUd")) ? getExtraparams().get("idUd") : "";
		String idProcedimento = StringUtils.isNotBlank(getExtraparams().get("idProcedimento")) ? getExtraparams().get("idProcedimento") : "";
		String codTipoUo = StringUtils.isNotBlank(getExtraparams().get("codTipoUo")) ? getExtraparams().get("codTipoUo") : "";
		String tipoAssegnatari = StringUtils.isNotBlank(getExtraparams().get("tipoAssegnatari")) ? getExtraparams().get("tipoAssegnatari") : "UO;SV";
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : "";
		String uoProtocollante = StringUtils.isNotBlank(getExtraparams().get("uoProtocollante")) ? getExtraparams().get("uoProtocollante") : "";
		String idTipoDoc = StringUtils.isNotBlank(getExtraparams().get("idTipoDoc")) ? getExtraparams().get("idTipoDoc") : "";
		
		String codice = StringUtils.isNotBlank(getExtraparams().get("codice")) ? getExtraparams().get("codice") : "";
		String descrizione = StringUtils.isNotBlank(getExtraparams().get("descrizione")) ? getExtraparams().get("descrizione") : "";
		
		BigDecimal flgSoloValide = StringUtils.isNotBlank(getExtraparams().get("flgSoloValide")) && ("1".equals(getExtraparams().get("flgSoloValide")) || Boolean.valueOf(getExtraparams().get("flgSoloValide"))) ? BigDecimal.ONE : BigDecimal.ZERO;
		Boolean isTipoAssL = StringUtils.isNotBlank(getExtraparams().get("isTipoAssL")) && Boolean.valueOf(getExtraparams().get("isTipoAssL"));
		
		boolean isDimOrganigrammaNonStd = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD");
		if(finalita != null && "MITTENTE_REG".equals(finalita)) {
			isDimOrganigrammaNonStd = false; // con finalità MITTENTE_REG la combo si deve comportare come nel caso di dimensione dell'organigramma standard
		}
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (!isDimOrganigrammaNonStd && criterion.getFieldName().equals("codice")) {
					codice = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}

		List<OrganigrammaBean> lListResult = new ArrayList<OrganigrammaBean>();
		PaginatorBean<OrganigrammaBean> lPaginatorBean = new PaginatorBean<OrganigrammaBean>();

		if (!isDimOrganigrammaNonStd || !"".equals(codice)/* || !"".equals(ciToAdd)*/) {

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			
			if(StringUtils.isNotBlank(getExtraparams().get("tipoCombo"))) {	
				lDmpkLoadComboDmfn_load_comboBean.setTipocomboin(getExtraparams().get("tipoCombo"));
			} else {
				lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ORGANIGRAMMA");	
			}
			
			if(StringUtils.isNotBlank(getExtraparams().get("altriParamLoadCombo"))) {	
				String altriParametri = getExtraparams().get("altriParamLoadCombo");
				altriParametri = altriParametri.replace("$ID_USER_LAVORO$", idUserLavoro);
				altriParametri = altriParametri.replace("$ID_UO_PROPONENTE$", uoProponente);				
				altriParametri = altriParametri.replace("$NRI_LIVELLI_UO$", codice);
				altriParametri = altriParametri.replace("$STR_IN_DES$", descrizione);
				altriParametri = altriParametri.replace("$CI_TO_ADD$", ciToAdd);
				altriParametri = altriParametri.replace("$ID_TIPO_DOC$", idTipoDoc);
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
			} else {
				String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_NODO_TYPE|*|" + tipoAssegnatari + "|*|NRI_LIVELLI_UO|*|" + codice
						+ "|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + ciToAdd + "|*|FINALITA|*|" + finalita;				
				if (StringUtils.isNotBlank(idEmail)) {
					altriParametri += "|*|TY_OBJ_TO_ASS|*|E|*|ID_OBJ_TO_ASS|*|" + idEmail;
				} else if (StringUtils.isNotBlank(idUd)) {
					altriParametri += "|*|TY_OBJ_TO_ASS|*|U|*|ID_OBJ_TO_ASS|*|" + idUd;
				}
				if (StringUtils.isNotBlank(idProcedimento)) {
					altriParametri += "|*|ID_PROCESS|*|" + idProcedimento;
				}
				if (StringUtils.isNotBlank(codTipoUo)) {
					altriParametri += "|*|COD_TIPO_UO|*|" + codTipoUo;
				}
				if (StringUtils.isNotBlank(uoProtocollante)) {					
					altriParametri += "|*|ID_UO|*|" + (uoProtocollante.startsWith("UO") ? uoProtocollante.substring(2) : uoProtocollante);
				}
				lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);										
			}
			
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(flgSoloValide);
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
					loginBean, lDmpkLoadComboDmfn_load_comboBean);

			if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				try {
					for (OrganigrammaBean lOrganigrammaBean : XmlListaUtility.recuperaLista(xmlLista, OrganigrammaBean.class)) {
						lOrganigrammaBean.setId(lOrganigrammaBean.getTypeNodo() + lOrganigrammaBean.getIdUo());
						lOrganigrammaBean.setDescrizioneOrig(lOrganigrammaBean.getDescrizione());
						lOrganigrammaBean.setIconaTypeNodo(lOrganigrammaBean.getTypeNodo());
						if ("UO".equals(lOrganigrammaBean.getTypeNodo())) {
							lOrganigrammaBean.setIconaTypeNodo("UO_" + lOrganigrammaBean.getTipoUo());
							lOrganigrammaBean.setDescrizione("<b>" + lOrganigrammaBean.getDescrizione() + "</b>");
							lOrganigrammaBean.setDescrizioneEstesa("<b>" + lOrganigrammaBean.getDescrizioneEstesa() + "</b>");
						}
						if (StringUtils.isBlank(finalita)) {
							lOrganigrammaBean.setFlgSelXFinalita(null);
						} else if (lOrganigrammaBean.getFlgSelXFinalita() == null || !"1".equals(lOrganigrammaBean.getFlgSelXFinalita())) {
							if (isTipoAssL) {
								lOrganigrammaBean.setFlgSelXFinalita("1");
							} else {
								lOrganigrammaBean.setFlgSelXFinalita("0");
							}
							lOrganigrammaBean.setDescrizione("<span style=\"color:gray\">" + lOrganigrammaBean.getDescrizione() + "</span>");
							lOrganigrammaBean.setDescrizioneEstesa("<span style=\"color:gray\">" + lOrganigrammaBean.getDescrizioneEstesa() + "</span>");
						} else {
							lOrganigrammaBean.setDescrizione("<span style=\"color:#1D66B2\">" + lOrganigrammaBean.getDescrizione() + "</span>");
							lOrganigrammaBean.setDescrizioneEstesa("<span style=\"color:#1D66B2\">" + lOrganigrammaBean.getDescrizioneEstesa() + "</span>");
						}
						lListResult.add(lOrganigrammaBean);
					}
				} catch (Exception e) {
					mLogger.error(e.getMessage(), e);
				}
			} else {
				mLogger.error(lStoreResultBean.getDefaultMessage());
			}

		}

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
