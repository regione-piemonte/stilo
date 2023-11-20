/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioDclassificaBean;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioIuclassificaBean;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioLoaddettclassificaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindTitolarioObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.titolario.TitolarioXmlBean;
import it.eng.auriga.ui.module.layout.server.titolario.TitolarioXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.titolario.datasource.bean.TitolarioBean;
import it.eng.auriga.ui.module.layout.server.titolario.datasource.bean.TitolarioSezioneCache;
import it.eng.client.AurigaService;
import it.eng.client.DmpkTitolarioDclassifica;
import it.eng.client.DmpkTitolarioIuclassifica;
import it.eng.client.DmpkTitolarioLoaddettclassifica;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "TitolarioDatasource")
public class TitolarioDatasource extends AurigaAbstractFetchDatasource<TitolarioBean> {

	@Override
	public PaginatorBean<TitolarioBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		List<TitolarioBean> data = new ArrayList<TitolarioBean>();
		
		boolean overflow = false;
		
		FindTitolarioObjectBean lFindTitolarioObjectBean = createFindTitolarioObjectBean(criteria, loginBean, filtroFullText, checkAttributes);

		if (StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
		} else {
			
			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findtitolarioobject(getLocale(), loginBean, lFindTitolarioObjectBean).getList();
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
			
			if (errorMessageOut != null && !"".equals(errorMessageOut)) {
				addMessage(errorMessageOut, "", MessageType.WARNING);
			}

			overflow = manageOverflow(errorMessageOut);

			// Conversione ListaRisultati ==> EngResultSet
			if (xmlResultSetOut != null) {
				StringReader sr = new StringReader(xmlResultSetOut);
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

						TitolarioBean node = new TitolarioBean();
						node.setNroLivello(v.get(0));                                                                         // 1  : numero livello
						node.setIdClassificazione(v.get(1));                                                                  // 2  : id classificazione
						node.setDescrizione(v.get(2));                                                                        // 3  : descrizione
						node.setTipo(v.get(3));                                                                               // 4  : tipo
						node.setDescrizioneEstesa(v.get(4));                                                                  // 5  : descrizione estesa
						node.setParoleChiave(v.get(5));                                                                       // 6  : parola chiave
						node.setIndice(v.get(6));                                                                             // 7  : indice
						node.setTsValidaDal(v.get(7) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(7)) : null);    // 8  : data inizio validita'
						node.setTsValidaFinoAl(v.get(8) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(8)) : null); // 9  : data fine validita'
						node.setPeriodoConservAnni(v.get(16) != null ? v.get(16) : null);                                     // 17 : anni conservazione 
						node.setFlgSelXFinalita(v.get(18) != null && "1".equals(v.get(18)));                                  // 19 : finalita'
						node.setIdClassificaSup(v.get(19));                                                                   // 20 : id classificazione superiore
						node.setDesClassificaSup(v.get(20));                                                                  // 21 : descrizione classificazione superiore
						node.setScore(v.get(23) != null ? new BigDecimal(v.get(23)) : null);                                  // 24 : score
						node.setIndiceXOrd(v.get(24));                                                                        // 25 : indice per ordinamento
						node.setFlgAttiva(v.get(26));                                                                         // 27 : flag attiva/chiusa
						node.setFlgNumContinua((v.get(27) != null && "C".equals(v.get(27))) ? true : false);                  // 28 : flag tipo numerazione (C=continua, R=annuale)
						data.add(node);
					}
				}
			}
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<TitolarioBean> lPaginatorBean = new PaginatorBean<TitolarioBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	private FindTitolarioObjectBean createFindTitolarioObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean,
			String filtroFullText, String[] checkAttributes) throws Exception {
	
		String idUserLavoro = loginBean.getIdUserLavoro();
		String[] flgObjectTypes = { "CL" };
		Integer searchAllTerms = null;
		Integer idFolder = null;
		String idPianoClassif = null;
		// in modalità di esplora il valore di default è 0
		String includiSottoCartelle = "0";
		String tsRif = null;
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1);
		Integer numPagina = null;
		Integer numRighePagina = null;
		String flgSenzaTot = "";
		Integer online = null;
		String colsToReturn = "1,2,3,4,5,6,7,8,9,17,19,20,21,22,24,25,27";
		String finalita = null;
		String flgSoloAttive = null;
		Date tsInizioVldDa = null;
		Date tsInizioVldA = null;
		Date tsFineVldDa = null;
		Date tsFineVldA = null;

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("idFolder".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = new Integer(String.valueOf(crit.getValue()));
					}
				} else if ("finalita".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						finalita = String.valueOf(crit.getValue());
					}
				} else if ("idPianoClassif".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idPianoClassif = String.valueOf(crit.getValue());
					}
				}

				if ("tsRif".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						tsRif = String.valueOf(crit.getValue());
					}
				} else if ("searchFulltext".equals(crit.getFieldName())) {
					// se sono entrato qui sono in modalità di ricerca con i filtri quindi imposto il valore di default a 1
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
				} else if ("flgSoloAttive".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgSoloAttive = new Boolean(String.valueOf(crit.getValue())) ? "1" : null;
					}
				}

				// DATA INIZIO VALIDITA'
				else if ("tsInizioVld".equals(crit.getFieldName())) {
					Date[] estremiInizioVld = getDateFilterValue(crit);
					if (tsInizioVldDa != null) {
						tsInizioVldDa = tsInizioVldDa.compareTo(estremiInizioVld[0]) < 0 ? estremiInizioVld[0] : tsInizioVldDa;
					} else {
						tsInizioVldDa = estremiInizioVld[0];
					}
					if (tsInizioVldA != null) {
						tsInizioVldA = tsInizioVldA.compareTo(estremiInizioVld[1]) > 0 ? estremiInizioVld[1] : tsInizioVldA;
					} else {
						tsInizioVldA = estremiInizioVld[1];
					}
				}

				// DATA FINE VALIDITA'
				else if ("tsFineVld".equals(crit.getFieldName())) {
					Date[] estremiFineVld = getDateFilterValue(crit);
					if (tsFineVldDa != null) {
						tsFineVldDa = tsFineVldDa.compareTo(estremiFineVld[0]) < 0 ? estremiFineVld[0] : tsFineVldDa;
					} else {
						tsFineVldDa = estremiFineVld[0];
					}
					if (tsFineVldA != null) {
						tsFineVldA = tsFineVldA.compareTo(estremiFineVld[1]) > 0 ? estremiFineVld[1] : tsFineVldA;
					} else {
						tsFineVldA = estremiFineVld[1];
					}
				}
			}
		}
		
		TitolarioSezioneCache lTitolarioSezioneCache = new TitolarioSezioneCache();
		lTitolarioSezioneCache.setIdPianoClassificazione(idPianoClassif);
		lTitolarioSezioneCache.setFlgSoloAttive(flgSoloAttive);
		lTitolarioSezioneCache.setTsFineVldA(tsFineVldA);
		lTitolarioSezioneCache.setTsFineVldDa(tsFineVldDa);
		lTitolarioSezioneCache.setTsInizioVldA(tsInizioVldA);
		lTitolarioSezioneCache.setTsInizioVldDa(tsInizioVldDa);
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(lTitolarioSezioneCache);

		advancedFilters = lStrXml;

		FindTitolarioObjectBean lFindTitolarioObjectBean = new FindTitolarioObjectBean();
		lFindTitolarioObjectBean.setUserIdLavoro(idUserLavoro);
		lFindTitolarioObjectBean.setFiltroFullText(filtroFullText);
		lFindTitolarioObjectBean.setCheckAttributes(checkAttributes);
		lFindTitolarioObjectBean.setFlgObjectTypes(flgObjectTypes);
		lFindTitolarioObjectBean.setSearchAllTerms(searchAllTerms);
		lFindTitolarioObjectBean.setIdClSearchIO(idFolder);
		lFindTitolarioObjectBean.setFlgSubClSearchIn(includiSottoCartelle);
		lFindTitolarioObjectBean.setTsRiferimento(tsRif);
		lFindTitolarioObjectBean.setAdvancedFilters(advancedFilters);
		lFindTitolarioObjectBean.setCustomFilters(customFilters);
		lFindTitolarioObjectBean.setColsOrderBy(colsOrderBy);
		lFindTitolarioObjectBean.setFlgDescOrderBy(flgDescOrderBy);
		lFindTitolarioObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
		lFindTitolarioObjectBean.setNumPagina(numPagina);
		lFindTitolarioObjectBean.setNumRighePagina(numRighePagina);
		lFindTitolarioObjectBean.setFlgSenzaTot(flgSenzaTot);
		lFindTitolarioObjectBean.setOnline(online);
		lFindTitolarioObjectBean.setColsToReturn(colsToReturn);
		lFindTitolarioObjectBean.setFinalita(finalita);

		if (StringUtils.isNotBlank(idPianoClassif)) {
			lFindTitolarioObjectBean.setType("ID_PIANO_CLASSIF");
			String[] lValues = { idPianoClassif };
			lFindTitolarioObjectBean.setValues(lValues);
		} else {
			String idDominio = null;
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
			String[] lValues = { idDominio };
			lFindTitolarioObjectBean.setType("ID_SP_AOO");
			lFindTitolarioObjectBean.setValues(lValues);
		}
		lFindTitolarioObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());
		
		return lFindTitolarioObjectBean;
	}

	@Override
	public TitolarioBean get(TitolarioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkTitolarioLoaddettclassificaBean input = new DmpkTitolarioLoaddettclassificaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclassificazionein(StringUtils.isNotBlank(bean.getIdClassificazione()) ? new BigDecimal(bean.getIdClassificazione()) : null);

		DmpkTitolarioLoaddettclassifica store = new DmpkTitolarioLoaddettclassifica();
		StoreResultBean<DmpkTitolarioLoaddettclassificaBean> output = store.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		bean.setIdClassificaSup(output.getResultBean().getIdclassificazionesupout() != null ? String.valueOf(output.getResultBean().getIdclassificazionesupout()) : null);
		bean.setLivelloCorrente(output.getResultBean().getNrilivelliclassifsupout());
		bean.setLivello(output.getResultBean().getNrolivelloout());
		bean.setIndice((StringUtils.isNotBlank(bean.getLivelloCorrente())) ? bean.getLivelloCorrente() + "." + bean.getLivello() : bean.getLivello());
		bean.setDescrizione(output.getResultBean().getDesclassificazioneout());
		bean.setParoleChiave(output.getResultBean().getParolechiaveout());
		bean.setTsValidaDal(StringUtils.isNotBlank(output.getResultBean().getDtiniziovldout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtiniziovldout()) : null);
		bean.setTsValidaFinoAl(StringUtils.isNotBlank(output.getResultBean().getDtfinevldout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtfinevldout()) : null);
		bean.setFlgNoRichAbil(output.getResultBean().getFlgrichabilxaperfascout() == null || output.getResultBean().getFlgrichabilxaperfascout().intValue() == 0);
		bean.setFlgConservPermIn(output.getResultBean().getFlgconservpermanenteout() != null && output.getResultBean().getFlgconservpermanenteout() == 1 ? true : false);
		bean.setPeriodoConservAnni(output.getResultBean().getPeriodoconservazionout() != null && output.getResultBean().getPeriodoconservazionout() != 0 ? output.getResultBean().getPeriodoconservazionout().toString() : null);
		bean.setFlgIgnoreWarning(new Integer(0));
		bean.setFlgNumContinua(output.getResultBean().getFlgnumcontinuaout() != null && output.getResultBean().getFlgnumcontinuaout() == 1 ? true : false);
		return bean;
	}

	@Override
	public TitolarioBean add(TitolarioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkTitolarioIuclassificaBean input = new DmpkTitolarioIuclassificaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setIdpianoclassifin(StringUtils.isNotBlank(bean.getIdPianoClassif()) ? new BigDecimal(bean.getIdPianoClassif()) : null);
		input.setIdclassificazionesupin(StringUtils.isNotBlank(bean.getIdClassificaSup()) ? new BigDecimal(bean.getIdClassificaSup()) : null);
		input.setNrilivelliclassifsupin(bean.getLivelloCorrente());
		input.setNrolivelloin(bean.getLivello());
		input.setDesclassificazionein(bean.getDescrizione());
		input.setParolechiavein(bean.getParoleChiave());
		input.setDtiniziovldin(bean.getTsValidaDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsValidaDal()) : null);
		input.setDtfinevldin(bean.getTsValidaFinoAl() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsValidaFinoAl()) : null);
		if (bean.getFlgNoRichAbil() == null || !bean.getFlgNoRichAbil()) {
			input.setFlgrichabilxaperfascin(new Integer(1));
			input.setFlgrichabilxassegnin(new Integer(1));
			input.setFlgrichabilxfirmain(new Integer(1));
			input.setFlgrichabilxgestin(new Integer(1));
			input.setFlgrichabilxvisin(new Integer(1));
		} else {
			input.setFlgrichabilxaperfascin(new Integer(0));
			input.setFlgrichabilxassegnin(new Integer(0));
			input.setFlgrichabilxfirmain(new Integer(0));
			input.setFlgrichabilxgestin(new Integer(0));
			input.setFlgrichabilxvisin(new Integer(0));
		}

		if (bean.getFlgConservPermIn() != null && bean.getFlgConservPermIn()) {
			input.setFlgconservpermanentein(new Integer(1));
			input.setPeriodoconservazionein(null);
		} else {
			input.setFlgconservpermanentein(new Integer(0));
			input.setPeriodoconservazionein(bean.getPeriodoConservAnni() != null ? new Integer(bean.getPeriodoConservAnni()) : null);
		}

		if (bean.getFlgNumContinua() != null && bean.getFlgNumContinua()) {
			input.setFlgnumcontinuain(new Integer(1));
		} else{
			input.setFlgnumcontinuain(new Integer(0));
		}
		
		DmpkTitolarioIuclassifica store = new DmpkTitolarioIuclassifica();

		StoreResultBean<DmpkTitolarioIuclassificaBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setIdClassificazione(String.valueOf(output.getResultBean().getIdclassificazioneio()));
			bean.setFlgIgnoreWarning(new Integer(0));
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}

	@Override
	public TitolarioBean update(TitolarioBean bean, TitolarioBean oldvalue) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkTitolarioIuclassificaBean input = new DmpkTitolarioIuclassificaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setIdclassificazioneio(StringUtils.isNotBlank(bean.getIdClassificazione()) ? new BigDecimal(bean.getIdClassificazione()) : null);
		input.setIdclassificazionesupin(StringUtils.isNotBlank(bean.getIdClassificaSup()) ? new BigDecimal(bean.getIdClassificaSup()) : null);
		input.setNrilivelliclassifsupin(bean.getLivelloCorrente());
		input.setNrolivelloin(bean.getLivello());
		input.setDesclassificazionein(bean.getDescrizione());
		input.setParolechiavein(bean.getParoleChiave());
		input.setDtiniziovldin(bean.getTsValidaDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsValidaDal()) : null);
		input.setDtfinevldin(bean.getTsValidaFinoAl() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsValidaFinoAl()) : null);
		if (bean.getFlgNoRichAbil() == null || !bean.getFlgNoRichAbil()) {
			input.setFlgrichabilxaperfascin(new Integer(1));
			input.setFlgrichabilxassegnin(new Integer(1));
			input.setFlgrichabilxfirmain(new Integer(1));
			input.setFlgrichabilxgestin(new Integer(1));
			input.setFlgrichabilxvisin(new Integer(1));
		} else {
			input.setFlgrichabilxaperfascin(new Integer(0));
			input.setFlgrichabilxassegnin(new Integer(0));
			input.setFlgrichabilxfirmain(new Integer(0));
			input.setFlgrichabilxgestin(new Integer(0));
			input.setFlgrichabilxvisin(new Integer(0));
		}

		if (bean.getFlgStoricizzaDati() != null) {
			input.setFlgstoricizzadatiin(bean.getFlgStoricizzaDati() ? new Integer(1) : new Integer(0));
		}
		if (bean.getFlgConservPermIn() != null && bean.getFlgConservPermIn()) {
			input.setFlgconservpermanentein(new Integer(1));
			input.setPeriodoconservazionein(null);
		} else {
			input.setFlgconservpermanentein(new Integer(0));
			input.setPeriodoconservazionein(bean.getPeriodoConservAnni() != null ? new Integer(bean.getPeriodoConservAnni()) : null);
		}

		if (bean.getFlgNumContinua() != null && bean.getFlgNumContinua()) {
			input.setFlgnumcontinuain(new Integer(1));
		} else{
			input.setFlgnumcontinuain(new Integer(0));
		}
		
		DmpkTitolarioIuclassifica store = new DmpkTitolarioIuclassifica();
		StoreResultBean<DmpkTitolarioIuclassificaBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setFlgIgnoreWarning(new Integer(0));
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		bean.setFlgDatiDaStoricizzare(output.getResultBean().getFlgdatidastoricizzareout() != null && output.getResultBean().getFlgdatidastoricizzareout().intValue() == 1);

		return bean;
	}

	@Override
	public TitolarioBean remove(TitolarioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkTitolarioDclassificaBean input = new DmpkTitolarioDclassificaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclassificazioneinin(StringUtils.isNotBlank(bean.getIdClassificazione()) ? new BigDecimal(bean.getIdClassificazione()) : null);
		input.setFlgignorewarningin(new Integer(1));

		DmpkTitolarioDclassifica store = new DmpkTitolarioDclassifica();

		StoreResultBean<DmpkTitolarioDclassificaBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		FindTitolarioObjectBean lFindTitolarioObjectBean = createFindTitolarioObjectBean(criteria, loginBean, filtroFullText, checkAttributes);
		lFindTitolarioObjectBean.setOverFlowLimit(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findtitolarioobject(getLocale(), loginBean, lFindTitolarioObjectBean).getList();
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

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), TitolarioXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), TitolarioXmlBeanDeserializationHelper.class);

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

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		FindTitolarioObjectBean lFindTitolarioObjectBean = createFindTitolarioObjectBean(criteria, loginBean, filtroFullText, checkAttributes);
		lFindTitolarioObjectBean.setOverFlowLimit(-1);
		lFindTitolarioObjectBean.setColsToReturn("1");

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findtitolarioobject(getLocale(), loginBean, lFindTitolarioObjectBean).getList();
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
}