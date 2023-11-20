/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil.TipoFiltro;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.CriteriAvanzatiRichAccessoAtti;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiXmlBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiXmlBeanDeserializationHelper;
import it.eng.client.AurigaService;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="RichiestaAccessoAttiDatasource")
public class RichiestaAccessoAttiDatasource extends AurigaAbstractFetchDatasource<RichiestaAccessoAttiBean> {
	
	private static Logger mLogger = Logger.getLogger(RichiestaAccessoAttiDatasource.class);

	@Override
	public PaginatorBean<RichiestaAccessoAttiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<RichiestaAccessoAttiBean> data = new ArrayList<RichiestaAccessoAttiBean>();

		boolean overflow = false;

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		if (StringUtils.isNotBlank(lFindRepositoryObjectBean.getFiltroFullText())
				&& (lFindRepositoryObjectBean.getCheckAttributes() == null || lFindRepositoryObjectBean.getCheckAttributes().length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
		} else {

			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
			String xmlResultSetOut = (String) resFinder.get(0);
			String numTotRecOut = (String) resFinder.get(1);
			String numRecInPagOut = (String) resFinder.get(2);
			String xmlPercorsiOut = null;
			String dettagliCercaInFolderOut = null;
			String errorMessageOut = null;
			if (resFinder.size() > 3) {
				xmlPercorsiOut = (String) resFinder.get(3);
			}
			if (resFinder.size() > 4) {
				dettagliCercaInFolderOut = (String) resFinder.get(4);
			}
			if (resFinder.size() > 5) {
				errorMessageOut = (String) resFinder.get(5);
			}

			overflow = manageOverflow(errorMessageOut);

			// Conversione ListaRisultati ==> EngResultSet
			if (xmlResultSetOut != null) {
				data = XmlListaUtility.recuperaLista(xmlResultSetOut, RichiestaAccessoAttiBean.class, new RichiestaAccessoAttiBeanDeserializationHelper(createRemapConditionsMap()));
			}
		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<RichiestaAccessoAttiBean> lPaginatorBean = new PaginatorBean<RichiestaAccessoAttiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);

		return lPaginatorBean;
	}
	
	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
	
		AdvancedCriteria criteria = bean.getCriteria();

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		lFindRepositoryObjectBean.setOverflowlimitin(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		// imposto l'id del job creato
		Integer jobId = Integer.valueOf((String) resFinder.get(6));
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), RichiestaAccessoAttiXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), RichiestaAccessoAttiXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean filterBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = filterBean.getCriteria();

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		lFindRepositoryObjectBean.setColsToReturn("1");
		lFindRepositoryObjectBean.setOverflowlimitin(-1);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String numTotRecOut = (String) resFinder.get(1);

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}
	
	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();

		String idNode = getExtraparams().get("idNode");
		retValue.put("idNode", idNode);

		return retValue;
	}
	
	protected FindRepositoryObjectBean createFindRepositoryObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		String colsToReturn = "2,4,8,9,14,62,91,201,214,237,250,251,252,DES_TOPONIMO_ALTRA_UBI,TS_INVIO_VS_AUTORIZZ_RICH_ACCESSO_ATTI,TS_AUTORIZZ_RICH_ACCESSO_ATTI,TS_VERIFICA_CITTADELLA_RICH_ACCESSO_ATTI,TS_APPUNTAMENTO,DT_PRELIEVO_ATTI_RICHIESTI,STATO_PRELIEVO_DA_ARCHIVIO,COGNOME_NOME_AUTORIZZ_RICH_ACCESSO_ATTI";

		String idNode = null;
		Long idFolder = null;
		
		// in modalita' di esplora il valore di default e' 0
		String includiSottoCartelle = "0";
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		Integer searchAllTerms = null;
				
		String criteriAvanzati = null;
			
		String nroProtocolloDa = null;
		String nroProtocolloA = null;
		String annoProtocolloDa = null;
		String annoProtocolloA = null;
		Date dataProtocolloDa = null;
		Date dataProtocolloA = null;
		
		String richRegistrataDa = null;
		
		String dataAppuntamentoDa = null;
		String dataAppuntamentoA = null;
		
		String nroAltraNumerazioneDa = null;
		String nroAltraNumerazioneA = null;
		String annoAltraNumerazioneDa = null;
		String annoAltraNumerazioneA = null;
		
		String unitaDiConservazione = null; //UDC
		String verificaCompletata = null; //VerificaArchivioCompletata 1/0
		
		String richPrenotabiliDaAgendaDigitale = null;
		
		Date tsNotificaDa = null;
		Date tsNotificaA = null;
		
		List<CriteriPersonalizzati> listaCriteriPersonalizzati = new ArrayList<CriteriPersonalizzati>();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {		
				if ("idNode".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idNode = (String) crit.getValue();
					}
				} else if ("idFolder".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = Long.parseLong((String) crit.getValue());
					}
				} else if ("searchFulltext".equals(crit.getFieldName())) {
					// se sono entrato qui sono in modalita' di ricerca con i filtri quindi imposto il valore di default a 1
					includiSottoCartelle = "1";
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[] {}) : null;
						Boolean flgRicorsiva = (Boolean) map.get("flgRicorsiva");
						if (flgRicorsiva != null) {
							includiSottoCartelle = flgRicorsiva ? "1" : "0";
						}
						String operator = crit.getOperator();
						if (StringUtils.isNotBlank(operator)) {
							if ("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							}
						}
					}
				} else if ("criteriAvanzati".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						criteriAvanzati = String.valueOf(crit.getValue());
					}
				} else if ("nroProtocollo".equals(crit.getFieldName())) {
					String[] estremi = getNumberFilterValue(crit);
					nroProtocolloDa = estremi[0] != null ? estremi[0] : null;
					nroProtocolloA = estremi[1] != null ? estremi[1] : null;
				} else if ("annoProtocollo".equals(crit.getFieldName())) {
					String[] estremi = getNumberFilterValue(crit);
					annoProtocolloDa = estremi[0] != null ? estremi[0] : null;
					annoProtocolloA = estremi[1] != null ? estremi[1] : null;
				} else if ("dataProtocollo".equals(crit.getFieldName())) {
					Date[] estremi = getDateFilterValue(crit);
					if (dataProtocolloDa != null) {
						dataProtocolloDa = dataProtocolloDa.compareTo(estremi[0]) < 0 ? estremi[0] : dataProtocolloDa;
					} else {
						dataProtocolloDa = estremi[0];
					}
					if (dataProtocolloA != null) {
						dataProtocolloA = dataProtocolloA.compareTo(estremi[1]) > 0 ? estremi[1] : dataProtocolloA;
					} else {
						dataProtocolloA = estremi[1];
					}
					
				} else if ("richRegistrataDa".equals(crit.getFieldName())) {
					richRegistrataDa = (String) crit.getValue();					
				} else if ("nroAltraNumerazione".equals(crit.getFieldName())) {
					String[] estremi = getNumberFilterValue(crit);
					nroAltraNumerazioneDa = estremi[0] != null ? estremi[0] : null;
					nroAltraNumerazioneA = estremi[1] != null ? estremi[1] : null;
				} else if ("annoAltraNumerazione".equals(crit.getFieldName())) {
					String[] estremiAnnoBozza = getNumberFilterValue(crit);
					annoAltraNumerazioneDa = estremiAnnoBozza[0] != null ? estremiAnnoBozza[0] : null;
					annoAltraNumerazioneA = estremiAnnoBozza[1] != null ? estremiAnnoBozza[1] : null;
				} else if ("indirizzo".equals(crit.getFieldName())) {
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DES_TOPONIMO_ALTRA_UBI", crit));
				} else if ("dataApprovazione".equals(crit.getFieldName())) {
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("TS_AUTORIZZ_RICH_ACCESSO_ATTI", crit, TipoFiltro.DATA_SENZA_ORA));
				} else if ("richApprovataDa".equals(crit.getFieldName())) {
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("ID_USER_AUTORIZZ_RICH_ACCESSO_ATTI", crit));
				} else if ("unitaDiConservazione".equals(crit.getFieldName())) {
					unitaDiConservazione = getTextFilterValue(crit);
				} else if ("dataEsitoCittadella".equals(crit.getFieldName())) {
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("TS_VERIFICA_CITTADELLA_RICH_ACCESSO_ATTI", crit, TipoFiltro.DATA_SENZA_ORA));
				} else if ("dataInvioApprovazione".equals(crit.getFieldName())) {
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("TS_INVIO_VS_AUTORIZZ_RICH_ACCESSO_ATTI", crit, TipoFiltro.DATA_SENZA_ORA));
				} else if ("verificaCompletata".equals(crit.getFieldName())) {
					verificaCompletata = (crit.getValue() != null && new Boolean(String.valueOf(crit.getValue()))) ? "1" : "0";
				} else if ("dataAppuntamento".equals(crit.getFieldName())) {
//					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("TS_APPUNTAMENTO", crit, TipoFiltro.DATA_SENZA_ORA));
					Date[] dataAppuntamento = getDateConOraFilterValue(crit);
					if (dataAppuntamento[0] != null) {
						dataAppuntamentoDa = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataAppuntamento[0]);
					}
					if (dataAppuntamento[1] != null) {
						dataAppuntamentoA = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataAppuntamento[1]);
					}
					CriteriPersonalizzati cpDataApp = new CriteriPersonalizzati();
					cpDataApp.setAttrName("TS_APPUNTAMENTO");
					cpDataApp.setOperator(CriteriPersonalizzatiUtil.getDecodeCritOperator(crit.getOperator()));
					cpDataApp.setValue1(dataAppuntamentoDa);
					cpDataApp.setValue2(dataAppuntamentoA);
					
					listaCriteriPersonalizzati.add(cpDataApp);
					
				} else if ("dataPrelievo".equals(crit.getFieldName())) {
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DT_PRELIEVO_ATTI_RICHIESTI", crit, TipoFiltro.DATA_SENZA_ORA));
				} else if ("prelievoEffettuato".equals(crit.getFieldName())) {
					String statoPrelievoDaArchivio = (crit.getValue() != null && new Boolean(String.valueOf(crit.getValue()))) ? "effettuato" : "da effettuare";
					listaCriteriPersonalizzati.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("STATO_PRELIEVO_DA_ARCHIVIO", "equals", statoPrelievoDaArchivio, TipoFiltro.STRINGA));					
				} else if ("richPrenotabiliDaAgendaDigitale".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						richPrenotabiliDaAgendaDigitale = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("tsNotifica".equals(crit.getFieldName())) {
					Date[] estremiDataNotifica = getDateFilterValue(crit);
					if (tsNotificaDa != null) {
						tsNotificaDa = tsNotificaDa.compareTo(estremiDataNotifica[0]) < 0 ? estremiDataNotifica[0] : tsNotificaDa;
					} else {
						tsNotificaDa = estremiDataNotifica[0];
					}
					if (tsNotificaA != null) {
						tsNotificaA = tsNotificaA.compareTo(estremiDataNotifica[1]) > 0 ? estremiDataNotifica[1] : tsNotificaA;
					} else {
						tsNotificaA = estremiDataNotifica[1];
					}
				}
			}
		}
		
		CriteriAvanzatiRichAccessoAtti scCriteriAvanzati = new CriteriAvanzatiRichAccessoAtti();
		if (criteriAvanzati != null) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scCriteriAvanzati = lXmlUtilityDeserializer.unbindXml(criteriAvanzati, CriteriAvanzatiRichAccessoAtti.class);			
		}
		
		scCriteriAvanzati.setIdNode(idNode);		
		
		List<Registrazione> listaRegistrazioni = new ArrayList<Registrazione>();
		if (scCriteriAvanzati.getRegistrazioni() != null && scCriteriAvanzati.getRegistrazioni().size() > 0) {
			listaRegistrazioni = scCriteriAvanzati.getRegistrazioni();
		}

		if ((StringUtils.isNotBlank(nroProtocolloDa)) || (StringUtils.isNotBlank(nroProtocolloA)) || 
			(StringUtils.isNotBlank(annoProtocolloDa)) || (StringUtils.isNotBlank(annoProtocolloA)) || 
			(dataProtocolloDa != null) || (dataProtocolloA != null) || (StringUtils.isNotBlank(richRegistrataDa))){
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("PG;R");
			registrazione.setSigla(null);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroProtocolloDa);
			registrazione.setNumeroA(nroProtocolloA);
			registrazione.setAnnoDa(annoProtocolloDa);
			registrazione.setAnnoA(annoProtocolloA);
			registrazione.setDataDa(dataProtocolloDa);
			registrazione.setDataA(dataProtocolloA);
			registrazione.setEffettuataDa(richRegistrataDa);
			listaRegistrazioni.add(registrazione);
		}
		
		if ((StringUtils.isNotBlank(nroAltraNumerazioneDa)) || (StringUtils.isNotBlank(nroAltraNumerazioneA)) || 
			(StringUtils.isNotBlank(annoAltraNumerazioneDa)) || (StringUtils.isNotBlank(annoAltraNumerazioneA))) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("A");
			registrazione.setSigla(null);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroAltraNumerazioneDa);
			registrazione.setNumeroA(nroAltraNumerazioneA);
			registrazione.setAnnoDa(annoAltraNumerazioneDa);
			registrazione.setAnnoA(annoAltraNumerazioneA);
			registrazione.setDataDa(null);
			registrazione.setDataA(null);
			registrazione.setEffettuataDa(null);
			listaRegistrazioni.add(registrazione);
		}
		
		scCriteriAvanzati.setRegistrazioni(listaRegistrazioni);
		
		if(StringUtils.isNotBlank(unitaDiConservazione)) {
			scCriteriAvanzati.setUDC(unitaDiConservazione);
		}
		
		if(StringUtils.isNotBlank(verificaCompletata)) {
			scCriteriAvanzati.setVerificaArchivioCompletata(verificaCompletata);
		}
		
		scCriteriAvanzati.setRichPrenotabiliDaAgendaDigitale(richPrenotabiliDaAgendaDigitale);
		
		if (tsNotificaDa != null) {
			scCriteriAvanzati.setDataNotificaDa(tsNotificaDa);
		}

		if (tsNotificaA != null) {
			scCriteriAvanzati.setDataNotificaA(tsNotificaA);
		}
		
		List<CriteriPersonalizzati> listaCriteriPersonalizzatiSkipNullValues = new ArrayList<CriteriPersonalizzati>();
		for (int i = 0; i < listaCriteriPersonalizzati.size(); i++) {
			if (listaCriteriPersonalizzati.get(i) != null) {
				listaCriteriPersonalizzatiSkipNullValues.add(listaCriteriPersonalizzati.get(i));
			}
		}
		
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();
		lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());		
		
		lFindRepositoryObjectBean.setFlgUdFolder("U");
		lFindRepositoryObjectBean.setFormatoEstremiReg("<ANNO> / <NRO>");
		lFindRepositoryObjectBean.setIdFolderSearchIn(idFolder);
		// Per le richieste accesso atto non va passato il FlgSubfolderSearchIn
//		lFindRepositoryObjectBean.setFlgSubfoderSearchIn(includiSottoCartelle);
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
		lFindRepositoryObjectBean.setFinalita(null);
		
		lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
		lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
		lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);				
		
		lFindRepositoryObjectBean.setAdvancedFilters(new XmlUtilitySerializer().bindXml(scCriteriAvanzati));
		lFindRepositoryObjectBean.setCustomFilters(new XmlUtilitySerializer().bindXmlList(listaCriteriPersonalizzatiSkipNullValues));
		
		lFindRepositoryObjectBean.setColsOrderBy(null);
		lFindRepositoryObjectBean.setFlgDescOrderBy(null);
		lFindRepositoryObjectBean.setFlgSenzaPaginazione(new Integer(1));
		lFindRepositoryObjectBean.setNumPagina(null);
		lFindRepositoryObjectBean.setNumRighePagina(null);
		lFindRepositoryObjectBean.setOnline(null);
		lFindRepositoryObjectBean.setPercorsoRicerca(null);
		lFindRepositoryObjectBean.setFlagTipoRicerca(null);
		
		return lFindRepositoryObjectBean;
	}

}
