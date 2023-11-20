/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovalistalavoroBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AssegnatarioXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.SoggettiEstBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.datasource.CriteriIterNonSvoltBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator.AttributiName;
import it.eng.auriga.ui.module.layout.server.proposteOrganigramma.proposteOrgInIter.bean.ProposteOrganigrammaInIterBean;
import it.eng.auriga.ui.module.layout.server.proposteOrganigramma.proposteOrgInIter.bean.ProposteOrganigrammaInIterXmlBean;
import it.eng.client.DmpkWfTrovalistalavoro;
import it.eng.document.function.bean.Flag;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ProposteOrganigrammaInIterDatasource")
public class ProposteOrganigrammaInIterDatasource extends AbstractFetchDataSource<ProposteOrganigrammaInIterBean> {

	private static Logger mLogger = Logger.getLogger(ProposteOrganigrammaInIterDatasource.class);

	@Override
	public PaginatorBean<ProposteOrganigrammaInIterBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		List<DestInvioNotifica> assegnatario = null;
		String istruttoreAssegnatario = null;
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		ProposteOrganigrammaInIterBean filter = new ProposteOrganigrammaInIterBean(); 
		
		buildFilterBeanFromCriteria(filter, criteria);
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if("assegnatario".equals(crit.getFieldName())) {
					assegnatario = getListaSceltaOrganigrammaFilterValue(crit);						
				} else if ("istruttoreAssegnatario".equals(crit.getFieldName())) {
					istruttoreAssegnatario = getTextFilterValue(crit);
				}
			}
		}
				
		DmpkWfTrovalistalavoroBean input = new DmpkWfTrovalistalavoroBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgsenzapaginazionein(1);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();		
		scCriteriAvanzati.setFlgSoloProcedimentiAmm("1");
		scCriteriAvanzati.setFlgSoloEseguibili(null);
		if (StringUtils.isNotEmpty(istruttoreAssegnatario)){
			scCriteriAvanzati.setIdUserAssegnatario(istruttoreAssegnatario);
		}
		//Data presentazione
		if (filter.getDataPresentazioneStart() != null){
			scCriteriAvanzati.setDtPresentazioneInstanzaDa(filter.getDataPresentazioneStart());
		}
		if (filter.getDataPresentazioneEnd() != null){
			scCriteriAvanzati.setDtPresentazioneIstanzaA(filter.getDataPresentazioneEnd());
		}
		//Anno atti-rif
		if (StringUtils.isNotEmpty(filter.getAnnoAttoRif())){
			scCriteriAvanzati.setAnnoAttoRifDa(filter.getAnnoAttoRif());
			scCriteriAvanzati.setAnnoAttoRifA(filter.getAnnoAttoRif());
		} else {
			if (filter.getAnnoAttoRifStart() != null){
				scCriteriAvanzati.setAnnoAttoRifDa(String.valueOf(filter.getAnnoAttoRifStart()));
			}
			if (filter.getAnnoAttoRifEnd() != null){
				scCriteriAvanzati.setAnnoAttoRifA(String.valueOf(filter.getAnnoAttoRifEnd()));
			}
		}
		if (StringUtils.isNotEmpty(filter.getTipiTributo())){
			scCriteriAvanzati.setTipiTributo(filter.getTipiTributo());
		}
		if (StringUtils.isNotEmpty(filter.getTipoAttoRif())){
			scCriteriAvanzati.setTipoAttoRif(filter.getTipoAttoRif());
		}
		if (StringUtils.isNotEmpty(filter.getNumeroAttoRif())){
			scCriteriAvanzati.setNumeroAttoRif(filter.getNumeroAttoRif());
		}
		if (StringUtils.isNotEmpty(filter.getRegistroAttoRif())){
			scCriteriAvanzati.setRegistroAttoRif(filter.getRegistroAttoRif());
		}
		if (StringUtils.isNotEmpty(filter.getEsitoAttoRif())){
			scCriteriAvanzati.setEsitoAttoRif(filter.getEsitoAttoRif());
		}
		input.setCriteriavanzatiin(lXmlUtilitySerializer.bindXml(scCriteriAvanzati));
		
		//Tipo procedimento
		input.setIdprocesstypeio(StringUtils.isNotEmpty(filter.getIdProcessType())?new BigDecimal(filter.getIdProcessType()):null);

		//Attributi procedimento
		List<AttributiProcBean> lListAttributiProcIO = new ArrayList<AttributiProcBean>();
		//Numero pratica
		AttributiProcBean lAttributiProcBeanNumeroPratica = buildNumber(AttributiName.NRO_PRATICA, criteria);		
		if (lAttributiProcBeanNumeroPratica!=null ) {			
			if (  ( lAttributiProcBeanNumeroPratica.getConfrontoA()  !=null &&  !lAttributiProcBeanNumeroPratica.getConfrontoA().toString().equalsIgnoreCase("")  ) || 
				  ( lAttributiProcBeanNumeroPratica.getConfrontoDa() !=null &&  !lAttributiProcBeanNumeroPratica.getConfrontoDa().toString().equalsIgnoreCase("") )
				)
			{
				lListAttributiProcIO.add(lAttributiProcBeanNumeroPratica);
			}			
		}		
		//Anno pratica
		AttributiProcBean lAttributiProcBeanAnnoPratica = buildNumber(AttributiName.ANNO_PRATICA, criteria);		
		if (lAttributiProcBeanAnnoPratica!=null) {
			if (  ( lAttributiProcBeanAnnoPratica.getConfrontoA()  !=null &&  !lAttributiProcBeanAnnoPratica.getConfrontoA().toString().equalsIgnoreCase("")  ) || 
				  ( lAttributiProcBeanAnnoPratica.getConfrontoDa() !=null &&  !lAttributiProcBeanAnnoPratica.getConfrontoDa().toString().equalsIgnoreCase("") )
			   )
				{
				lListAttributiProcIO.add(lAttributiProcBeanAnnoPratica);
				}			
		}
		String lStrXml = lXmlUtilitySerializer.bindXmlList(lListAttributiProcIO);
		input.setAttributiprocio(lStrXml);
		
		//Oggetto
		input.setOggettoio(criteria.getCriterionByFieldName("oggetto")!=null?(String)criteria.getCriterionByFieldName("oggetto").getValue():null);
		
		//Data di avvio
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(FMT_STD_DATA);
		if (filter.getDataDiAvvioStart()!=null){
			input.setDtavviodaio(lSimpleDateFormat.format(filter.getDataDiAvvioStart()));
		}
		if (filter.getDataDiAvvioEnd()!=null){
			input.setDtavvioaio(lSimpleDateFormat.format(filter.getDataDiAvvioEnd()));
		}
		
		//Stato
		if (StringUtils.isNotEmpty(filter.getStato())){
			input.setFlgstatoprocio(filter.getStato());
		}
		
		//Avviati da me
		if (filter.getAvviatiDaMe()!=null && filter.getAvviatiDaMe()){
			input.setFlgavviouserlavio(1);
		}
		
		//In fase
		if (StringUtils.isNotEmpty(filter.getInFase())){
			input.setWfnamefasecorrio(filter.getInFase());
		}
		
		//Eseguibile task
		if (StringUtils.isNotEmpty(filter.getEseguibileTask())){
			input.setWfnameattesegio(filter.getEseguibileTask());
		}
		
		//Criteri iter svolto
		List<XmlListaSimpleBean> lListCriteriIterSvolto = new ArrayList<XmlListaSimpleBean>();
		//Svolta fase
		if (StringUtils.isNotEmpty(filter.getSvoltaFase())){
			String[] lStrings = filter.getSvoltaFase().split(";");
			for (String lStrFilter : lStrings){
				XmlListaSimpleBean lXmlListaSimpleBean = new XmlListaSimpleBean();
				lXmlListaSimpleBean.setKey("F");
				lXmlListaSimpleBean.setValue(lStrFilter);
				lListCriteriIterSvolto.add(lXmlListaSimpleBean);
			}
		}
		//Effettuato task
		if (StringUtils.isNotEmpty(filter.getEffettuatoTask())){
			String[] lStrings = filter.getEffettuatoTask().split(";");
			for (String lStrFilter : lStrings){
				XmlListaSimpleBean lXmlListaSimpleBean = new XmlListaSimpleBean();
				lXmlListaSimpleBean.setKey("AE");
				lXmlListaSimpleBean.setValue(lStrFilter);
				lListCriteriIterSvolto.add(lXmlListaSimpleBean);
			}
		}
		String criterIterSvolto = lXmlUtilitySerializer.bindXmlList(lListCriteriIterSvolto);
		input.setCriteriitersvoltoio(criterIterSvolto);
		
		//Criteri iter non svolto
		List<CriteriIterNonSvoltBean> lListCriteriIterNonSvolto = new ArrayList<CriteriIterNonSvoltBean>();
		//Da iniziare fase
		if (StringUtils.isNotEmpty(filter.getDaIniziareFase())){
			String[] lStrings = filter.getDaIniziareFase().split(";");
			for (String lStrFilter : lStrings){
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("F");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("1");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		//Da completare fase
		if (StringUtils.isNotEmpty(filter.getDaCompletareFase())){
			String[] lStrings = filter.getDaCompletareFase().split(";");
			for (String lStrFilter : lStrings){
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("F");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("0");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		//Da effettuare task
		if (StringUtils.isNotEmpty(filter.getDaEffettuareTask())){
			String[] lStrings = filter.getDaEffettuareTask().split(";");
			for (String lStrFilter : lStrings){
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("AE");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("1");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		String criterIterNonSvolto = lXmlUtilitySerializer.bindXmlList(lListCriteriIterNonSvolto);
		input.setCriteriiternonsvoltoio(criterIterNonSvolto);
		
		//Soggetti esterni
		List<SoggettiEstBean> lListSoggettiEst = new ArrayList<SoggettiEstBean>();
		//Denominazione richiedente
		if (StringUtils.isNotEmpty(filter.getDenominazioneRichiedente())){
			SoggettiEstBean lSoggettiEstBean = new SoggettiEstBean();
			lSoggettiEstBean.setRichiedente("richiedente");
			lSoggettiEstBean.setDenominazione(filter.getDenominazioneRichiedente());
			lListSoggettiEst.add(lSoggettiEstBean);
		}
		//Cod. fiscale richiedente
		if (StringUtils.isNotEmpty(filter.getCodFiscale())){
			SoggettiEstBean lSoggettiEstBean = new SoggettiEstBean();
			lSoggettiEstBean.setRichiedente("richiedente");
			lSoggettiEstBean.setCodiceFiscale(filter.getCodFiscale());
			lListSoggettiEst.add(lSoggettiEstBean);
		}
		String soggettiEst = lXmlUtilitySerializer.bindXmlList(lListSoggettiEst);
		input.setSoggettiestio(soggettiEst);
		
		//Scadenza
		if (filter.getScadenza()!=null){
			input.setTpscadio(filter.getScadenza().getTipoScadenza());
			input.setScadentronggio(filter.getScadenza().getEntroGiorni());
			input.setScaddaminnggio(filter.getScadenza().getTrascorsaDaGiorni());
			if (filter.getScadenza().getSoloSeTermineScadenzaNonAvvenuto()!=null && filter.getScadenza().getSoloSeTermineScadenzaNonAvvenuto())
				input.setFlgnoscadconevtfinio(1);
		}
		
		//Note
		if (StringUtils.isNotEmpty(filter.getNote())){
			input.setNoteprocio(filter.getNote());
		}
		
		//Soggetti interni
		if (assegnatario != null){			
           List<AssegnatarioXmlBean> listaSoggettiInt = new ArrayList<AssegnatarioXmlBean>();			
			for(int i = 0; i < assegnatario.size(); i++) {
				DestInvioNotifica destInvioNotifica = assegnatario.get(i);			
				AssegnatarioXmlBean assegnatarioXmlBean = new AssegnatarioXmlBean();
				assegnatarioXmlBean.setRuolo("ISTRUTTORE");
				assegnatarioXmlBean.setFlgUoSv(destInvioNotifica.getTipo());
				assegnatarioXmlBean.setIdUoSv(destInvioNotifica.getId());							
				listaSoggettiInt.add(assegnatarioXmlBean);
			}								
			String soggettiInt = lXmlUtilitySerializer.bindXmlList(listaSoggettiInt);
			input.setSoggettiintio(soggettiInt);
		}
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (lResultBean.isInError()){
			mLogger.error(lResultBean.getDefaultMessage());
			throw new StoreException(lResultBean);
		}
		
		String xmlOut = lResultBean.getResultBean().getListaxmlout();
		
		List<ProposteOrganigrammaInIterXmlBean> lListResult = XmlListaUtility.recuperaLista(xmlOut, ProposteOrganigrammaInIterXmlBean.class);
		List<ProposteOrganigrammaInIterBean> lList = new ArrayList<ProposteOrganigrammaInIterBean>(lListResult.size());		
		for (ProposteOrganigrammaInIterXmlBean lProposteOrganigrammaInIterXmlBean : lListResult){
			ProposteOrganigrammaInIterBean lProposteOrganigrammaInIterBean = new ProposteOrganigrammaInIterBean();
			BeanUtilsBean2.getInstance().copyProperties(lProposteOrganigrammaInIterBean, lProposteOrganigrammaInIterXmlBean);
//			lProposteOrganigrammaInIterBean.setOggettoProcedimento(HtmlUtils.htmlEscape(lProposteOrganigrammaInIterBean.getOggettoProcedimento()));
			lProposteOrganigrammaInIterBean.setOggettoProcedimento(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(lProposteOrganigrammaInIterBean.getOggettoProcedimento()));			
			lList.add(lProposteOrganigrammaInIterBean);
		}
		
		return new PaginatorBean<ProposteOrganigrammaInIterBean>(lList);
	}

	public AttributiProcBean buildNumber(AttributiName pAttributiName, AdvancedCriteria criteria) {
		return AttributiProcCreator.buildNumber(pAttributiName,
				criteria.getCriterionByFieldName(pAttributiName.getGuiValueFilter()));
	}

	@Override
	public Map<String, ErrorBean> validate(ProposteOrganigrammaInIterBean bean) throws Exception {
		return null;
	}

	@Override
	public String getNomeEntita() {
		return "procedimenti_in_iter";
	}

	protected List<DestInvioNotifica> getListaSceltaOrganigrammaFilterValue(Criterion crit) {
		
		if (crit != null && crit.getValue() != null) {
			ArrayList lista = new ArrayList<DestInvioNotifica>();
			if (crit.getValue() instanceof Map) {
				Map map = (Map) crit.getValue();
				for (Map val : (ArrayList<Map>) map.get("listaOrganigramma")) {
					DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
					String chiave = (String) val.get("organigramma");
					destInvioNotifica.setTipo(chiave.substring(0, 2));
					destInvioNotifica.setId(chiave.substring(2));
					if (val.get("flgIncludiSottoUO") != null && ((Boolean) val.get("flgIncludiSottoUO"))) {
						destInvioNotifica.setFlgIncludiSottoUO(Flag.SETTED);
					}
					if (val.get("flgIncludiScrivanie") != null && ((Boolean) val.get("flgIncludiScrivanie"))) {
						destInvioNotifica.setFlgIncludiScrivanie(Flag.SETTED);
					}
					lista.add(destInvioNotifica);
				}
			} else {
				String value = getTextFilterValue(crit);
				if (StringUtils.isNotBlank(value)) {
					StringSplitterServer st = new StringSplitterServer(value, ";");
					while (st.hasMoreTokens()) {
						DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
						String chiave = st.nextToken();
						destInvioNotifica.setTipo(chiave.substring(0, 2));
						destInvioNotifica.setId(chiave.substring(2));
						lista.add(destInvioNotifica);
					}
				}
			}
			return lista;
		}
		return null;
	}

	@Override
	public ProposteOrganigrammaInIterBean get(ProposteOrganigrammaInIterBean bean) throws Exception {
		return bean;
	}

	@Override
	public ProposteOrganigrammaInIterBean add(ProposteOrganigrammaInIterBean bean) throws Exception {
		return bean;
	}

	@Override
	public ProposteOrganigrammaInIterBean remove(ProposteOrganigrammaInIterBean bean) throws Exception {
		return bean;
	}

	@Override
	public ProposteOrganigrammaInIterBean update(ProposteOrganigrammaInIterBean bean, ProposteOrganigrammaInIterBean oldvalue)
			throws Exception {
		return bean;
	}

}