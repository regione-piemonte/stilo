/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheReportemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.report.bean.CaselleMailStoricizzateXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.DatasetReportDocAvazatiBean;
import it.eng.auriga.ui.module.layout.server.report.bean.StatisticheMailStoricizzateBean;
import it.eng.auriga.ui.module.layout.server.report.bean.StatisticheMailStoricizzateFiltriXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.StatisticheMailStoricizzateRaggruppamentiXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.StruttureMailStoricizzateBean;
import it.eng.auriga.ui.module.layout.server.report.bean.UOAssegnatarieMailStoricizzateXmlBean;
import it.eng.client.DmpkStatisticheReportemail;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="StatisticheMailStoricizzateDataSource")
public class StatisticheMailStoricizzateDataSource extends AbstractServiceDataSource<StatisticheMailStoricizzateBean, DatasetReportDocAvazatiBean>{

	@Override
	public DatasetReportDocAvazatiBean call(StatisticheMailStoricizzateBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkStatisticheReportemailBean input = new DmpkStatisticheReportemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgbatchin(null);
		input.setTiporeportin("conteggi_per_storico");
		
		// FILTRI
		StatisticheMailStoricizzateFiltriXmlBean lStatisticheMailStoricizzateFiltriXmlBean =
				new StatisticheMailStoricizzateFiltriXmlBean();
		
		lStatisticheMailStoricizzateFiltriXmlBean.setDataInvioDa(pInBean.getDtInvioDa() != null ? lSimpleDateFormat.format(pInBean.getDtInvioDa()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataInvioA(pInBean.getDtInvioA() != null ? lSimpleDateFormat.format(pInBean.getDtInvioA()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataChiusuraDa(pInBean.getDtChiusuraDa() != null ? lSimpleDateFormat.format(pInBean.getDtChiusuraDa()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataChiusuraA(pInBean.getDtChiusuraA() != null ? lSimpleDateFormat.format(pInBean.getDtChiusuraA()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataStoricizzazioneDa(pInBean.getDtStoricizzazioneDa() != null ? lSimpleDateFormat.format(pInBean.getDtStoricizzazioneDa()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataStoricizzazioneA(pInBean.getDtStoricizzazioneA() != null ? lSimpleDateFormat.format(pInBean.getDtStoricizzazioneA()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataUltimoAggDa(pInBean.getDtUltimoAggiornamentoDa() != null ? lSimpleDateFormat.format(pInBean.getDtUltimoAggiornamentoDa()) : null);
		lStatisticheMailStoricizzateFiltriXmlBean.setDataUltimoAggA(pInBean.getDtUltimoAggiornamentoA() != null ? lSimpleDateFormat.format(pInBean.getDtUltimoAggiornamentoA()) : null);
		
		if(pInBean.getCaselle() != null && !pInBean.getCaselle().isEmpty()) {
			List<CaselleMailStoricizzateXmlBean> caselleXml = new ArrayList<CaselleMailStoricizzateXmlBean>();
			for(String casellaItem : pInBean.getCaselle()) {
				CaselleMailStoricizzateXmlBean lCaselleMailStoricizzateXmlBean = new CaselleMailStoricizzateXmlBean();
				lCaselleMailStoricizzateXmlBean.setIdAccount(casellaItem);
				caselleXml.add(lCaselleMailStoricizzateXmlBean);
			}
			lStatisticheMailStoricizzateFiltriXmlBean.setCaselle(caselleXml);
		}
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD") ) {
			if(pInBean.getStrutture() != null && !pInBean.getStrutture().isEmpty()) {
				List<UOAssegnatarieMailStoricizzateXmlBean> uOAssegnatarieXml = new ArrayList<UOAssegnatarieMailStoricizzateXmlBean>();
				for(StruttureMailStoricizzateBean uoItem : pInBean.getStrutture()) {
					UOAssegnatarieMailStoricizzateXmlBean lUOAssegnatarieMailStoricizzateXmlBean = new UOAssegnatarieMailStoricizzateXmlBean();
					lUOAssegnatarieMailStoricizzateXmlBean.setIdUo(uoItem.getIdUo());
					lUOAssegnatarieMailStoricizzateXmlBean.setFlgIncSottoUO(uoItem.getFlgIncludiSottoUO());
					uOAssegnatarieXml.add(lUOAssegnatarieMailStoricizzateXmlBean);
				}
				lStatisticheMailStoricizzateFiltriXmlBean.setuOAssegnatarie(uOAssegnatarieXml);
			} 
		} else {
			if(pInBean.getStruttureNONSTD() != null && !pInBean.getStruttureNONSTD().isEmpty()) {
				List<UOAssegnatarieMailStoricizzateXmlBean> uOAssegnatarieXml = new ArrayList<UOAssegnatarieMailStoricizzateXmlBean>();
				for(String uoItem : pInBean.getStruttureNONSTD()) {
					UOAssegnatarieMailStoricizzateXmlBean lUOAssegnatarieMailStoricizzateXmlBean = new UOAssegnatarieMailStoricizzateXmlBean();
					lUOAssegnatarieMailStoricizzateXmlBean.setIdUo(uoItem);
					uOAssegnatarieXml.add(lUOAssegnatarieMailStoricizzateXmlBean);
				}
				lStatisticheMailStoricizzateFiltriXmlBean.setuOAssegnatarie(uOAssegnatarieXml);
			} 
		}
		
		lStatisticheMailStoricizzateFiltriXmlBean.setStatoLavorazione(pInBean.getStatoLavorazione());
		lStatisticheMailStoricizzateFiltriXmlBean.setMinNroMesiSenzaOperazioni(pInBean.getNrMesi() != null ? String.valueOf(pInBean.getNrMesi()) : null);
		
		String filtriXml = lXmlUtilitySerializer.bindXml(lStatisticheMailStoricizzateFiltriXmlBean);
		input.setFiltriin(filtriXml);
		
		// RAGGRUPPAMENTO
		
		StatisticheMailStoricizzateRaggruppamentiXmlBean lStatisticheMailStoricizzateRaggruppamentiXmlBean 
									= new StatisticheMailStoricizzateRaggruppamentiXmlBean();
		
		if(pInBean.getFlgRaggruppaStatoLavorazioneEmail() != null && pInBean.getFlgRaggruppaStatoLavorazioneEmail()) {
			lStatisticheMailStoricizzateRaggruppamentiXmlBean.setStatoLavorazione("1");
		} else {
			lStatisticheMailStoricizzateRaggruppamentiXmlBean.setStatoLavorazione("0");
		}
		
		if(pInBean.getFlgRaggruppaArchivio() != null && pInBean.getFlgRaggruppaArchivio()) {
			lStatisticheMailStoricizzateRaggruppamentiXmlBean.setInArchivio("1");
		} else {
			lStatisticheMailStoricizzateRaggruppamentiXmlBean.setInArchivio("0");
		}
		
		if(pInBean.getFlgRaggruppaCaselle() != null && pInBean.getFlgRaggruppaCaselle()) {
			lStatisticheMailStoricizzateRaggruppamentiXmlBean.setCasella("1");
		} else {
			lStatisticheMailStoricizzateRaggruppamentiXmlBean.setCasella("0");
		}
		
		lStatisticheMailStoricizzateRaggruppamentiXmlBean.setTipoPeriodo(pInBean.getRaggruppaTipologiaPeriodo());
		lStatisticheMailStoricizzateRaggruppamentiXmlBean.setPeriodo(pInBean.getRaggruppaPeriodo());
		lStatisticheMailStoricizzateRaggruppamentiXmlBean.setUo(pInBean.getRaggruppaUo());
		
		String raggruppamentiXml = lXmlUtilitySerializer.bindXml(lStatisticheMailStoricizzateRaggruppamentiXmlBean);
		input.setRaggruppamentiin(raggruppamentiXml);
		
		DmpkStatisticheReportemail dmpkStatisticheReportemail = new DmpkStatisticheReportemail();
		StoreResultBean<DmpkStatisticheReportemailBean> result = dmpkStatisticheReportemail.execute(getLocale(), loginBean, input);
		
		DmpkStatisticheReportemailBean lResultBean = new DmpkStatisticheReportemailBean();
		
		if (result.isInError()){
			throw new Exception(result.getDefaultMessage());
		} else {
			lResultBean = result.getResultBean();
		}
		
		/**
		 *  -- 3: Codice della UO assegnataria del raggruppamento (valorizzato se si raggruppa per UO)
			-- 4: Nome della UO assegnataria del raggruppamento (valorizzato se si raggruppa per UO)
			-- 10: Archivio (storico o corrente) del raggruppamento (valorizzato se si raggruppa archivio in cui si trovano le mail: corrente o storico)
			-- 11: Casella del raggruppamento (valorizzato se si raggruppa per casella)
			-- 12: StatoLavorazione del raggruppamento (valorizzato se si raggruppa per StatoLavorazione)
			-- 16: Periodo (valorizzato se si raggruppa per Periodo): è sempre un numero 
			-- 17: N.ro di mail del gruppo
			-- 18: Percentuale che corrisponde al conteggio di col 17. In notazione italiana con la , come separatore dei decimali
			-- 19: Percentuale arrotondata in modo tale che la somma delle varie percentuali sia 100. In notazione italiana con la , come separatore dei decimali			
		 */
		
		DatasetReportDocAvazatiBean output = new DatasetReportDocAvazatiBean();
		
		output.setErrorCode(lResultBean.getErrcodeout());
		output.setErrorContext(lResultBean.getErrcontextout());
		output.setErrorMessage(lResultBean.getErrmsgout());
		output.setIdJob(lResultBean.getIdjobio());
		output.setNroRecord(lResultBean.getNrorecordout());
		output.setTitle(lResultBean.getReporttitleout());
		
		String xmlOut = lResultBean.getReportcontentsxmlout();		
		List<ReportEmailStoricizzateResultBean> lList = new ArrayList<ReportEmailStoricizzateResultBean>();
		List<ReportEmailStoricizzateResultBean> lListNew = new ArrayList<ReportEmailStoricizzateResultBean>();
		
		if (xmlOut!= null && !xmlOut.equalsIgnoreCase("")){
			lList = XmlListaUtility.recuperaLista(xmlOut, ReportEmailStoricizzateResultBean.class);			
			if(lList != null && !lList.isEmpty() ){				
				for (ReportEmailStoricizzateResultBean rec : lList){	
					lListNew.add(rec);
				}
			}
		}
		output.setDatasetEmail(lListNew);
		
		return output;
	} 
}