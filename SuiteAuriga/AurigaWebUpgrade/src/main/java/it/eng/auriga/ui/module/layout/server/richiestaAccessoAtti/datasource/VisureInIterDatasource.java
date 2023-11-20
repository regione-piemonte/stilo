/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovalistalavoroBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.VisureXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.VisureXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.SoggettiEstBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.datasource.CriteriIterNonSvoltBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator.AttributiName;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.VisureBean;
import it.eng.client.DmpkWfTrovalistalavoro;
import it.eng.document.function.bean.Flag;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id = "VisureInIterDatasource")
public class VisureInIterDatasource extends AurigaAbstractFetchDatasource<VisureBean> {

	private static Logger mLogger = Logger.getLogger(VisureInIterDatasource.class);
	
	@Override
	public String getNomeEntita() {
		return "visure_in_iter";
	}

	@Override
	public PaginatorBean<VisureBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkWfTrovalistalavoroBean input = createInputBean(criteria,loginBean);
		
		boolean overflow = false;
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), input);

		if (lResultBean.isInError()){
			mLogger.error(lResultBean.getDefaultMessage());
			throw new StoreException(lResultBean);
		} else {
			overflow = manageOverflow(lResultBean.getDefaultMessage());
		}

		String xmlOut = lResultBean.getResultBean().getListaxmlout();

		List<VisureXmlBean> lListResult = XmlListaUtility.recuperaLista(xmlOut, VisureXmlBean.class);
		List<VisureBean> lList = new ArrayList<VisureBean>(lListResult.size());
		for (VisureXmlBean lVisureInIterXmlBean : lListResult) {
			VisureBean lVisureBean = new VisureBean();
			BeanUtilsBean2.getInstance().copyProperties(lVisureBean, lVisureInIterXmlBean);
			if(lVisureInIterXmlBean.getRichAttiDiFabbrica() != null && 
					"1".equalsIgnoreCase(lVisureInIterXmlBean.getRichAttiDiFabbrica())) {
				lVisureBean.setRichAttiDiFabbrica("SI");
			} else {
				lVisureBean.setRichAttiDiFabbrica("NO");
			}
			if(lVisureInIterXmlBean.getRichModifiche() != null && 
					"1".equalsIgnoreCase(lVisureInIterXmlBean.getRichModifiche())) {
				lVisureBean.setRichModifiche("SI");
			} else {
				lVisureBean.setRichModifiche("NO");
			}
			lList.add(lVisureBean);
		}
		
		getSession().setAttribute(FETCH_SESSION_KEY, lListResult);

		PaginatorBean<VisureBean> lPaginatorBean = new PaginatorBean<VisureBean>();
		lPaginatorBean.setData(lList);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? lList.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(lList.size());
		lPaginatorBean.setOverflow(overflow);

		return lPaginatorBean;
	}
	
	private DmpkWfTrovalistalavoroBean createInputBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		VisureBean filter = new VisureBean();

		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
		
		BeanWrapperImpl wrappperBean = BeanPropertyUtility.getBeanWrapper(filter);
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if("annoPratica".equals(crit.getFieldName())) {
					String[] annoPratica = getNumberFilterValue(crit);
					scCriteriAvanzati.setAnnoPraticaDa(annoPratica[0]);
					scCriteriAvanzati.setAnnoPraticaA(annoPratica[1]);					
				} else if("protRichNro".equals(crit.getFieldName())) {
					String[] protRichNro = getNumberFilterValue(crit);
					scCriteriAvanzati.setNroAttoDa(protRichNro[0]);
					scCriteriAvanzati.setNroAttoA(protRichNro[1]);					
				}  else if("protRichData".equals(crit.getFieldName())) {
					Date[] protRichData = getDateFilterValue(crit);
					scCriteriAvanzati.setDataAttoDa(protRichData[0]);
					scCriteriAvanzati.setDataAttoA(protRichData[1]);	
				} else if("dtAppCittadella".equals(crit.getFieldName())) {
					Date[] dtAppCittadella = getDateFilterValue(crit);
					scCriteriAvanzati.setDtAppuntamento1Da(dtAppCittadella[0]);
					scCriteriAvanzati.setDtAppuntamento1A(dtAppCittadella[1]);	
				} else if("dtAppBernina".equals(crit.getFieldName())) {
					Date[] dtAppBernina = getDateFilterValue(crit);
					scCriteriAvanzati.setDtAppuntamento2Da(dtAppBernina[0]);
					scCriteriAvanzati.setDtAppuntamento2A(dtAppBernina[1]);	
				} else if("tipologiaRichiedente".equals(crit.getFieldName())) {
					scCriteriAvanzati.setTipoRichiedenteVisuraSUE(getTextFilterValue(crit));
				} else if("richAttiDiFabbrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						String richAttiDiFabbrica = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
						scCriteriAvanzati.setRichAttiDiFabbrica(richAttiDiFabbrica);
					}
				} else if("fabbricatoAttiCostrFino1996".equals(crit.getFieldName())) {
					scCriteriAvanzati.setFabbricatoAttiCostrFino1996(getTextFilterValue(crit));
				} else if("fabbricatoAttiCostrDa1997".equals(crit.getFieldName())) {
					scCriteriAvanzati.setFabbricatoAttiCostrDa1997(getTextFilterValue(crit));
				} else if("motivazioneVisuraSUE".equals(crit.getFieldName())) {
					scCriteriAvanzati.setMotivoVisuraSUE(getTextFilterValue(crit));
				} else if("richModifiche".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						String richModifiche = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
						scCriteriAvanzati.setRichModificheVisuraSUE(richModifiche);
					}
				} else if("flgDocAggUltAcc".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						scCriteriAvanzati.setFlgDocAggUltAcc((new Boolean(String.valueOf(crit.getValue())) ? "1" : "0"));
					}
				} else if("dtDocAggiunti".equals(crit.getFieldName())) {
					Date[] dtDocAggiunti = getDateFilterValue(crit);
					scCriteriAvanzati.setDtDocAggiuntiVariatiDal(dtDocAggiunti[0]);
					scCriteriAvanzati.setDtDocAggiuntiVariatiAl(dtDocAggiunti[1]);	
				} else if("dtAutPrelievoArchivio".equals(crit.getFieldName())) {
					Date[] dtAutPrelievoArchivio = getDateFilterValue(crit);
					scCriteriAvanzati.setDtAutPrelievoArchivioDal(dtAutPrelievoArchivio[0]);
					scCriteriAvanzati.setDtAutPrelievoArchivioAl(dtAutPrelievoArchivio[1]);	
				}  else if("archiviCoinvolti".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (StringUtils.isNotBlank(value)) {
						scCriteriAvanzati.setArchiviCoinvolti(value);
					}
				}
				else {
					buildFilterBeanFromCriterion(filter, crit, wrappperBean);
				}
			}
		}

		DmpkWfTrovalistalavoroBean input = new DmpkWfTrovalistalavoroBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgsenzapaginazionein(1);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();

		
		scCriteriAvanzati.setFlgSoloProcedimentiAmm("1");		
		scCriteriAvanzati.setFlgSoloEseguibili("1");
		
		//Ricercatore incaricato
		if (StringUtils.isNotEmpty(filter.getRicercatoreIncaricato())) {
			scCriteriAvanzati.setRicercatoreIncaricato(filter.getRicercatoreIncaricato());
		}
		//Richiedente
		if (StringUtils.isNotEmpty(filter.getRichiedenteFilter())) {
			scCriteriAvanzati.setRichiedente(filter.getRichiedenteFilter());
		}
		//Indirizzo
		if (StringUtils.isNotEmpty(filter.getIndirizzoFilter())) {
			scCriteriAvanzati.setIndirizzo(filter.getIndirizzoFilter());
		}
		//UDC
		if (StringUtils.isNotEmpty(filter.getUdcFilter())) {
			scCriteriAvanzati.setUdc(filter.getUdcFilter());
		}

		input.setCriteriavanzatiin(lXmlUtilitySerializer.bindXml(scCriteriAvanzati));

		//Tipo procedimento
		String idProcessType = ParametriDBUtil.getParametroDB(getSession(), "ID_PROCESS_TYPE_VISURA_URBANISTICA");
		input.setIdprocesstypeio(StringUtils.isNotBlank(idProcessType) ? new BigDecimal(idProcessType) : null);
		
		//Attributi procedimento
		List<AttributiProcBean> lListAttributiProcIO = new ArrayList<AttributiProcBean>();
		//Numero pratica
		AttributiProcBean lAttributiProcBeanNumeroPratica = buildNumber(AttributiName.NRO_PRATICA, criteria);
		if (lAttributiProcBeanNumeroPratica != null) {
			if ((lAttributiProcBeanNumeroPratica.getConfrontoA() != null
					&& !lAttributiProcBeanNumeroPratica.getConfrontoA().toString().equals(""))
					|| (lAttributiProcBeanNumeroPratica.getConfrontoDa() != null
							&& !lAttributiProcBeanNumeroPratica.getConfrontoDa().toString().equals(""))) {
				lListAttributiProcIO.add(lAttributiProcBeanNumeroPratica);
			}
		}
		
		String lStrXml = lXmlUtilitySerializer.bindXmlList(lListAttributiProcIO);
		input.setAttributiprocio(lStrXml);
		
		//Oggetto
		input.setOggettoio(criteria.getCriterionByFieldName("oggetto") != null
				? (String) criteria.getCriterionByFieldName("oggetto").getValue() : null);
		
		//Data di avvio
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(FMT_STD_DATA);
		if (filter.getDataDiAvvioStart() != null) {
			input.setDtavviodaio(lSimpleDateFormat.format(filter.getDataDiAvvioStart()));
		}
		if (filter.getDataDiAvvioEnd() != null) {
			input.setDtavvioaio(lSimpleDateFormat.format(filter.getDataDiAvvioEnd()));
		}
		
		//Stato
		if (StringUtils.isNotEmpty(filter.getStato())) {
			input.setFlgstatoprocio(filter.getStato());
		}
		
		//In fase
		if (StringUtils.isNotEmpty(filter.getInFase())) {
			input.setWfnamefasecorrio(filter.getInFase());
		}
		
		//Eseguibile task
		if (StringUtils.isNotEmpty(filter.getEseguibileTask())) {
			input.setWfnameattesegio(filter.getEseguibileTask());
		}
		
		//Criteri iter svolto
		List<XmlListaSimpleBean> lListCriteriIterSvolto = new ArrayList<XmlListaSimpleBean>();
		//Svolta fase
		if (StringUtils.isNotEmpty(filter.getSvoltaFase())) {
			String[] lStrings = filter.getSvoltaFase().split(";");
			for (String lStrFilter : lStrings) {
				XmlListaSimpleBean lXmlListaSimpleBean = new XmlListaSimpleBean();
				lXmlListaSimpleBean.setKey("F");
				lXmlListaSimpleBean.setValue(lStrFilter);
				lListCriteriIterSvolto.add(lXmlListaSimpleBean);
			}
		}
		//Effettuato task
		if (StringUtils.isNotEmpty(filter.getEffettuatoTask())) {
			String[] lStrings = filter.getEffettuatoTask().split(";");
			for (String lStrFilter : lStrings) {
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
		if (StringUtils.isNotEmpty(filter.getDaIniziareFase())) {
			String[] lStrings = filter.getDaIniziareFase().split(";");
			for (String lStrFilter : lStrings) {
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("F");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("1");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		//Da completare fase
		if (StringUtils.isNotEmpty(filter.getDaCompletareFase())) {
			String[] lStrings = filter.getDaCompletareFase().split(";");
			for (String lStrFilter : lStrings) {
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("F");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("0");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		//Da effettuare task
		if (StringUtils.isNotEmpty(filter.getDaEffettuareTask())) {
			String[] lStrings = filter.getDaEffettuareTask().split(";");
			for (String lStrFilter : lStrings) {
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

		String soggettiEst = lXmlUtilitySerializer.bindXmlList(lListSoggettiEst);
		input.setSoggettiestio(soggettiEst);
		
		//Scadenza
		if (filter.getScadenza() != null) {
			input.setTpscadio(filter.getScadenza().getTipoScadenza());
			input.setScadentronggio(filter.getScadenza().getEntroGiorni());
			input.setScaddaminnggio(filter.getScadenza().getTrascorsaDaGiorni());
			if (filter.getScadenza().getSoloSeTermineScadenzaNonAvvenuto() != null
					&& filter.getScadenza().getSoloSeTermineScadenzaNonAvvenuto())
				input.setFlgnoscadconevtfinio(1);
		}
		
		return input;
	}

	public AttributiProcBean buildNumber(AttributiName pAttributiName, AdvancedCriteria criteria) {
		return AttributiProcCreator.buildNumber(pAttributiName,
				criteria.getCriterionByFieldName(pAttributiName.getGuiValueFilter()));
	}

	@Override
	public Map<String, ErrorBean> validate(VisureBean bean) throws Exception {
		return null;
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
	public VisureBean get(VisureBean bean) throws Exception {
		return bean;
	}

	@Override
	public VisureBean add(VisureBean bean) throws Exception {
		return bean;
	}

	@Override
	public VisureBean remove(VisureBean bean) throws Exception {
		return bean;
	}

	@Override
	public VisureBean update(VisureBean bean, VisureBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(criteria,loginBean);
		input.setOverflowlimitin(-1);
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (lResultBean.isInError()){
			throw new StoreException(lResultBean);
		}
		
		String numTotRecOut = lResultBean.getResultBean().getNrototrecout() != null ? String.valueOf(lResultBean.getResultBean().getNrototrecout()) : "";

		if(StringUtils.isNotBlank(lResultBean.getDefaultMessage())) {
			addMessage(lResultBean.getDefaultMessage(), "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(criteria,loginBean);
		input.setOverflowlimitin(-2);
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> output = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (output.isInError()){
			throw new StoreException(output);
		}
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}
		
		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio() != null ? output.getResultBean().getBachsizeio() : 0;
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), VisureXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(),  VisureXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}
	
	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}

}