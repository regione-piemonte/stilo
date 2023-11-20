/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaDsoggettoBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaIusoggettoBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaLoaddettsoggettoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRubricaObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AltraDenominazioneSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaSoggettiBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaSoggettiXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CodTipiIndirizziXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoXmlBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkAnagraficaDsoggetto;
import it.eng.client.DmpkAnagraficaIusoggetto;
import it.eng.client.DmpkAnagraficaLoaddettsoggetto;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AnagraficaSoggettiDataSource")
public class AnagraficaSoggettiDataSource extends AurigaAbstractFetchDatasource<AnagraficaSoggettiBean> {

	@Override
	public String getNomeEntita() {
		return "anagrafiche_soggetti";
	}

	private boolean overflow = false;
	public static final String _COD_ISTAT_ITALIA = "200";
	public static final String _NOME_STATO_ITALIA = "ITALIA";

	@Override
	public AnagraficaSoggettiBean get(AnagraficaSoggettiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAnagraficaLoaddettsoggettoBean input = new DmpkAnagraficaLoaddettsoggettoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsoggrubrio(new BigDecimal(bean.getIdSoggetto()));
		input.setFinalitain(getExtraparams().get("finalita"));

		DmpkAnagraficaLoaddettsoggetto loaddettsoggetto = new DmpkAnagraficaLoaddettsoggetto();
		StoreResultBean<DmpkAnagraficaLoaddettsoggettoBean> output = loaddettsoggetto.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		AnagraficaSoggettiBean res = new AnagraficaSoggettiBean();
		res.setIdSoggetto(String.valueOf(output.getResultBean().getIdsoggrubrio()));
		// res.setFlgPersFisica(output.getResultBean().getFlgpersonafisicaout() != null ? new
		// Integer(String.valueOf(output.getResultBean().getFlgpersonafisicaout())) : new Integer(0));
		res.setTipo(output.getResultBean().getCodtiposottotipoout());
		if (res.getTipo().endsWith(";"))
			res.setTipo(res.getTipo().substring(0, res.getTipo().length() - 1));
		if (res.getTipo() != null && (res.getTipo().startsWith("#APA") || res.getTipo().startsWith("#IAMM") || res.getTipo().startsWith("#AG"))) {
			int pos = output.getResultBean().getCodtiposottotipoout().indexOf(";");
			if (pos != -1) {
				res.setTipo(output.getResultBean().getCodtiposottotipoout().substring(0, pos));
				res.setSottotipo(output.getResultBean().getCodtiposottotipoout().substring(pos + 1));
			}
		}
		// if(res.getFlgPersFisica().equals(new Integer(1))) {
		if (res.getTipo() != null && (res.getTipo().equals("UP") || res.getTipo().equals("#AF"))) {
			// res.setFlgUnitaDiPersonale(output.getResultBean().getCodtiposottotipoout() != null &&
			// "UP".equals(output.getResultBean().getCodtiposottotipoout()));
			res.setCognome(output.getResultBean().getDenomcognomeout());
			res.setNome(output.getResultBean().getNomeout());
			res.setTitolo(output.getResultBean().getTitoloout());
			res.setSesso(output.getResultBean().getFlgsexout());
			res.setCittadinanza(output.getResultBean().getCodistatstatocittout());
		} else {
			// res.setTipologia(output.getResultBean().getCodtiposottotipoout());
			res.setDenominazione(output.getResultBean().getDenomcognomeout());
			res.setPartitaIva(output.getResultBean().getPivaout());
			res.setCondizioneGiuridica(output.getResultBean().getCodcondgiuridicaout());
			res.setCodiceAmmInIpa(output.getResultBean().getCodammipaout());
			res.setCodiceAooInIpa(output.getResultBean().getCodaooipaout());
			res.setCodicUoInIpa(output.getResultBean().getCodindpaout());
		}
		res.setCodiceRapido(output.getResultBean().getCodrapidoout());
		res.setCodiceFiscale(output.getResultBean().getCfout());
		res.setDataNascitaIstituzione(StringUtils.isNotBlank(output.getResultBean().getDtnascitaout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output
				.getResultBean().getDtnascitaout()) : null);
		res.setStatoNascitaIstituzione(output.getResultBean().getCodistatstatonascout());
		if (StringUtils.isNotBlank(res.getStatoNascitaIstituzione())) {
			if ("200".equals(res.getStatoNascitaIstituzione())) {
				res.setComuneNascitaIstituzione(output.getResultBean().getCodistatcomunenascout());
				res.setNomeComuneNascitaIstituzione(output.getResultBean().getNomecomunenascout());
				res.setProvNascitaIstituzione(output.getResultBean().getTargaprovnascout());
			} else {
				res.setCittaNascitaIstituzione(output.getResultBean().getNomecomunenascout());
			}
		}
		res.setDataCessazione(StringUtils.isNotBlank(output.getResultBean().getDtcessazioneout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output
				.getResultBean().getDtcessazioneout()) : null);
		res.setCausaleCessazione(output.getResultBean().getCodcausalecessazioneout());
		res.setFlgDiSistema(output.getResultBean().getFlglockedout() != null ? output.getResultBean().getFlglockedout().intValue() : null);
		res.setFlgValido(output.getResultBean().getFlgattivoout() != null ? output.getResultBean().getFlgattivoout().intValue() : null);
		res.setFlgSelXFinalita(output.getResultBean().getFlgselezionabileout() != null && output.getResultBean().getFlgselezionabileout().equals(1));

		List<IndirizzoSoggettoXmlBean> listaIndirizziSoggettiXmlBean = new ArrayList<IndirizzoSoggettoXmlBean>();
		String xmlListaAttributi = output.getResultBean().getIndirizziout();
		try {
			listaIndirizziSoggettiXmlBean = XmlListaUtility.recuperaLista(xmlListaAttributi, IndirizzoSoggettoXmlBean.class);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		List<IndirizzoSoggettoBean> listaIndirizziSoggettiBean = null;
		if (listaIndirizziSoggettiXmlBean != null && listaIndirizziSoggettiXmlBean.size() > 0) {
			listaIndirizziSoggettiBean = getXmlIndirizzi(listaIndirizziSoggettiXmlBean);
		}
		res.setListaIndirizzi(listaIndirizziSoggettiBean);

		List<ContattoSoggettoBean> listaContatti = new ArrayList<ContattoSoggettoBean>();
		if (output.getResultBean().getXmlcontattiout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlcontattiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					ContattoSoggettoBean contattoBean = new ContattoSoggettoBean();
					contattoBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo (ROWID preceduto da Tipo - T/F/E) con cui il contatto è già stata
					// registrato in banca dati
					contattoBean.setTipo(v.get(1)); // colonna 2 dell'xml : Flag che indica il tipo di contatto. Valori possibili: T=Telefono; F=Fax; E=e-Mail
					String emailTelFax = v.get(2); // colonna 3 dell'xml : N.ro di telefono/fax o indirizzo e-mail
					if ("E".equals(contattoBean.getTipo())) {
						contattoBean.setEmail(emailTelFax);
					} else {
						contattoBean.setTelFax(emailTelFax);
					}
					contattoBean.setTipoTel(v.get(3));                                              // colonna 4  : Tipo di numero di telefono (abitazione, ufficio, cell. ecc.) Valori da apposita
					contattoBean.setFlgPec(v.get(4) != null && "1".equals(v.get(4)));               // colonna 5  : (valori 1/0) Flag PEC
					contattoBean.setFlgDichIpa(v.get(5) != null && "1".equals(v.get(5)));           // colonna 6  : (valori 1/0) Flag dichiarato all'Indice PA
					contattoBean.setFlgCasellaIstituz(v.get(6) != null && "1".equals(v.get(6)));    // colonna 7  : (valori 1/0) Flag Casella Istituzionale di una PA
					contattoBean.setFlgDomicilioDigitale(v.get(9) != null && "1".equals(v.get(9))); // colonna 10 : (valori 1/0) Flag domicilio digitale
					listaContatti.add(contattoBean);
				}
			}
		}
		res.setListaContatti(listaContatti);

		List<AltraDenominazioneSoggettoBean> listaAltreDenominazioni = new ArrayList<AltraDenominazioneSoggettoBean>();
		if (output.getResultBean().getXmlaltredenominazioniout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlaltredenominazioniout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					AltraDenominazioneSoggettoBean altraDenominazioneBean = new AltraDenominazioneSoggettoBean();
					altraDenominazioneBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo (ROWID) con cui la denominazione è già stata registrata in
					// banca dati
					altraDenominazioneBean.setTipo(v.get(1)); // colonna 2 dell'xml : Tipo di denominazione (decodifica) (parallela, correlata, pseudonimo ecc.)
					altraDenominazioneBean.setDenominazione(v.get(2)); // colonna 3 dell'xml : Denominazione
					listaAltreDenominazioni.add(altraDenominazioneBean);
				}
			}
		}
		res.setListaAltreDenominazioni(listaAltreDenominazioni);

		res.setFlgIgnoreWarning(new Integer(0));

		res.setFlgInOrganigramma(bean.getFlgInOrganigramma());

		// Federico Cacco 13.07.2016
		// Inserisco le UO di appartenza
		if (output.getResultBean().getIduoout() != null) {
			res.setIdUoAssociata(output.getResultBean().getIduoout().toString());
			res.setNumeroLivelli(output.getResultBean().getLivelliuoout());
			res.setDescrizioneUo(output.getResultBean().getLivelliuoout() + " - " + output.getResultBean().getDesuoout());
			Integer vis = output.getResultBean().getFlgvisibsottouoout();
			res.setFlgVisibileDaSottoUo(((vis == null) || (vis == 0)) ? false : true);
			Integer mod = output.getResultBean().getFlggestsottouoout();
			res.setFlgModificabileDaSottoUo(((mod == null) || (mod == 0)) ? false : true);
		}

		return res;
	}

	private List<Object> getSoggetti(AdvancedCriteria criteria, Integer overflowLimit) throws StoreException, JAXBException, IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {

		String iniziale = null;
		String strInDenominazione = null;
		String filtroFullText = null;
		String[] checkAttributes = null;
		Integer searchAllTerms = null;
		// String flgNotCodTipiSottoTipi = null;
		// String flgFisicaGiuridica = null;
		String listaCodTipiSottoTipi = null;
		Integer flgSoloVld = null;
		Integer flgIncludiAnnullati = null;
		String finalita = null;
		String eMailIO = null;
		String codRapidoIO = null;
		String codiceIstatComune = null;
		String descCitta = null;
		List<DestInvioNotifica> porzioneRubrica = null;
		String viaIndirizzo = null;
		String operViaIndirizzo = null;
		String idViario = null;

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1); // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		Integer online = null;
		String colsToReturn = "1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35,36,37,38,40,41,42,43,44,45,46,47,49,50,51,53,54,55,61,69,82,83,84,85";
		String restringiRicercaIndirizzo = null;

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("iniziale".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						iniziale = String.valueOf(crit.getValue());
					}
				}
				if ("finalita".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						finalita = String.valueOf(crit.getValue());
					}
				} else if ("searchFulltext".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[] {}) : null;
						String operator = crit.getOperator();
						if (StringUtils.isNotBlank(operator)) {
							if ("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							}
						}
					}
				} else if ("tipologia".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (value != null) {
						// if(value.startsWith("!")) {
						// flgNotCodTipiSottoTipi = "1";
						// listaCodTipiSottoTipi = value.substring(1);
						// } else {
						listaCodTipiSottoTipi = value;
						// }
					}
				} else if ("viaIndirizzo".equals(crit.getFieldName())) {
					viaIndirizzo = getValueStringaFullTextMista(crit);
					operViaIndirizzo = getOperatorStringaFullTextMista(crit);
				} else if ("viaIndirizzoViario".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idViario = (String) map.get("id");
					}
				}
				// else if("flgFisicaGiuridica".equals(crit.getFieldName())) {
				// flgFisicaGiuridica = getTextFilterValue(crit);
				// }
				else if ("flgSoloVld".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgSoloVld = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}
				} else if ("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgIncludiAnnullati = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}
				} else if ("strInDenominazione".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							strInDenominazione = String.valueOf(crit.getValue());
						}
					}
				} else if ("eMailIO".equals(crit.getFieldName())) {
					eMailIO = getTextFilterValue(crit);
				} else if ("codRapidoIO".equals(crit.getFieldName())) {
					codRapidoIO = getTextFilterValue(crit);
				} else if ("codIstatComune".equals(crit.getFieldName())) {
					codiceIstatComune = getTextFilterValue(crit);
				} else if ("descCitta".equals(crit.getFieldName())) {
					descCitta = getTextFilterValue(crit);
				} else if ("porzioneRubrica".equals(crit.getFieldName())) {
					porzioneRubrica = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("restringiRicercaIndirizzo".equals(crit.getFieldName())) {
					restringiRicercaIndirizzo = getTextFilterValue(crit);
				}
			}
		}

		if (StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
			return null;
		} else {

			FindRubricaObjectBean lFindRubricaObjectBean = new FindRubricaObjectBean();
			lFindRubricaObjectBean.setFiltroFullText(filtroFullText);
			lFindRubricaObjectBean.setCheckAttributes(checkAttributes);
			lFindRubricaObjectBean.setSearchAllTerms(searchAllTerms);
			lFindRubricaObjectBean.setColsOrderBy(colsOrderBy);
			lFindRubricaObjectBean.setFlgDescOrderBy(flgDescOrderBy);
			lFindRubricaObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
			lFindRubricaObjectBean.setNumPagina(numPagina);
			lFindRubricaObjectBean.setNumRighePagina(numRighePagina);
			lFindRubricaObjectBean.setOnline(online);
			lFindRubricaObjectBean.setColsToReturn(colsToReturn);
			lFindRubricaObjectBean.setFinalita(finalita);
			lFindRubricaObjectBean.setDenominazioneIO(iniziale != null ? iniziale + "%" : null);
			lFindRubricaObjectBean.setStrInDenominazioneIn(strInDenominazione);
			// lFindRubricaObjectBean.setFlgFisicaGiuridicaIn(flgFisicaGiuridica);
			// lFindRubricaObjectBean.setFlgNotCodTipiSottoTipiIn(flgNotCodTipiSottoTipi);
			lFindRubricaObjectBean.setListaCodTipiSottoTipiIO(listaCodTipiSottoTipi);
			lFindRubricaObjectBean.setFlgSoloVldIO(flgSoloVld);
			lFindRubricaObjectBean.setFlgInclAnnullatiIO(flgIncludiAnnullati);
			lFindRubricaObjectBean.seteMailIO(eMailIO);
			lFindRubricaObjectBean.setCodRapidoIO(codRapidoIO);
			lFindRubricaObjectBean.setCodIstatComuneIndIn(codiceIstatComune);
			lFindRubricaObjectBean.setDesCittaIndIn(descCitta);

			// Federico Cacco 18.07.2016
			// Aggiungo le UO per la porzione di rubrica
			Lista listaRestringiARubricaDiUO = new Lista();
			if (porzioneRubrica != null) {
				for (DestInvioNotifica voce : porzioneRubrica) {
					Riga riga = new Riga();
					Colonna col1 = new Colonna();
					col1.setNro(new BigInteger("2"));
					col1.setContent(voce.getId());
					Colonna col2 = new Colonna();
					col2.setNro(new BigInteger("3"));
					String value = voce.getFlgIncludiSottoUO() != null ? voce.getFlgIncludiSottoUO().getDbValue() : "0"; 
					col2.setContent(value);
					riga.getColonna().add(col1);
					riga.getColonna().add(col2);
					listaRestringiARubricaDiUO.getRiga().add(riga);
				}
			}

			StringWriter sw = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(listaRestringiARubricaDiUO, sw);
			lFindRubricaObjectBean.setRestringiARubricaDiUOIn(sw.toString());

			lFindRubricaObjectBean.setOverFlowLimit(overflowLimit);

			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

			String idDominio = null;
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
			String[] lValues = { idDominio };
			lFindRubricaObjectBean.setType("ID_SP_AOO");
			lFindRubricaObjectBean.setValues(lValues);
			lFindRubricaObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());

			CriteriAvanzati criteriAvanzati = new CriteriAvanzati();
			if (operViaIndirizzo != null && !"".equals(operViaIndirizzo)) {
				criteriAvanzati.setOperDesToponimo(operViaIndirizzo);
			}
			if (viaIndirizzo != null && !"".equals(viaIndirizzo)) {
				criteriAvanzati.setDesToponimo(viaIndirizzo);
			}

			String istatComuneRif = ParametriDBUtil.getParametroDB(getSession(), "ISTAT_COMUNE_RIF");
			if (istatComuneRif != null && !"".equals(istatComuneRif)) {
				if (idViario != null && !"".equals(idViario)) {
					criteriAvanzati.setcITiponimo(idViario);
				}
			}

			if (restringiRicercaIndirizzo != null && !"".equals(restringiRicercaIndirizzo)) {
				List<CodTipiIndirizziXmlBean> listCodTipiIndirizziXmlBean = new ArrayList<CodTipiIndirizziXmlBean>();
				String[] values = restringiRicercaIndirizzo.split(";");
				for (String item : values) {
					CodTipiIndirizziXmlBean node = new CodTipiIndirizziXmlBean();
					node.setValue(item);
					listCodTipiIndirizziXmlBean.add(node);
				}
				criteriAvanzati.setlCodTipiIndirizzi(listCodTipiIndirizziXmlBean);
			}

			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String advancedFilters = lXmlUtilitySerializer.bindXml(criteriAvanzati);
			lFindRubricaObjectBean.setFiltriAvanzatiIn(advancedFilters);

			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrubricaobject(getLocale(), loginBean, lFindRubricaObjectBean).getList();
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}

			return resFinder;

		}
	}

	@Override
	public PaginatorBean<AnagraficaSoggettiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<Object> resFinder = getSoggetti(criteria, null);

		String xmlResultSetOut = (String) resFinder.get(0);
		String numTotRecOut = (String) resFinder.get(1);
		String numRecInPagOut = (String) resFinder.get(2);
		String flgAbilInsOut = null;
		String flgMostraAltriAttrOut = null;
		String errorMessageOut = null;
		if (resFinder.size() > 3) {
			flgAbilInsOut = (String) resFinder.get(3);
		}
		if (resFinder.size() > 4) {
			flgMostraAltriAttrOut = (String) resFinder.get(4);
		}
		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {

			GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");

			Boolean showOverflow = config.getShowAlertMaxRecord();

			if (showOverflow != null && showOverflow)
				addMessage(errorMessageOut, "", MessageType.WARNING);
			// else
			overflow = true;

		}

		List<AnagraficaSoggettiBean> data = new ArrayList<AnagraficaSoggettiBean>();

		if (xmlResultSetOut != null) {

			data = XmlListaUtility.recuperaLista(xmlResultSetOut, AnagraficaSoggettiBean.class);

		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<AnagraficaSoggettiBean> lPaginatorBean = new PaginatorBean<AnagraficaSoggettiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);

		return lPaginatorBean;

	}

	@Override
	public AnagraficaSoggettiBean add(AnagraficaSoggettiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Se parametro in DB true controllo che almeno uno dei campi fondamentali sia valorizzato
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CTRL_DATI_MINIMI_RUBRICA")) {
			if (!(checkCFPIVAValorizzato(bean) || checkContattoValorizzato(bean) || checkIndirizzoValorizzato(bean))) {
				throw new StoreException("Inserire almeno uno tra: cod. fiscale/P.IVA, indirizzo, contatto (e-mail, tel, fax)");
			}
		}
		
		DmpkAnagraficaIusoggettoBean input = new DmpkAnagraficaIusoggettoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setFlgmodindirizziin("C");
		input.setFlgmodcontattiin("C");
		input.setFlgmodaltredenomin("C");
		// input.setFlgpersonafisicain(new BigDecimal(bean.getFlgPersFisica()));
		if (StringUtils.isNotBlank(bean.getSottotipo()) && bean.getTipo() != null
				&& (bean.getTipo().equals("#APA") || bean.getTipo().equals("#IAMM") || bean.getTipo().equals("#AG"))) {
			input.setCodtiposottotipoin(bean.getTipo() + ";" + bean.getSottotipo());
		} else {
			input.setCodtiposottotipoin(bean.getTipo());
		}
		// if(input.getFlgpersonafisicain().equals(new BigDecimal(1))) {
		if (bean.getTipo() != null && (bean.getTipo().equals("UP") || bean.getTipo().equals("#AF"))) {
			bean.setDenominazione(bean.getCognome() + " " + bean.getNome());
			// input.setCodtiposottotipoin(bean.getFlgUnitaDiPersonale() != null && bean.getFlgUnitaDiPersonale() ? "UP" : null);
			input.setFlgpersonafisicain(new BigDecimal(1));
			input.setDenomcognomein(bean.getCognome());
			input.setNomein(bean.getNome());
			input.setTitoloin(bean.getTitolo());
			input.setFlgsexin(bean.getSesso());
			input.setCodistatstatocittin(bean.getCittadinanza());
		} else {
			// input.setCodtiposottotipoin(bean.getTipologia());
			input.setDenomcognomein(bean.getDenominazione());
			input.setPivain(bean.getPartitaIva());
			input.setCodcondgiuridicain(bean.getCondizioneGiuridica());
			input.setCodammipain(bean.getCodiceAmmInIpa());
			input.setCodaooipain(bean.getCodiceAooInIpa());
			input.setCodindpain(bean.getCodicUoInIpa());
		}
		input.setCodrapidoin(bean.getCodiceRapido());
		input.setCfin(bean.getCodiceFiscale());
		input.setDtnascitain(bean.getDataNascitaIstituzione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataNascitaIstituzione()) : null);
		if (StringUtils.isNotBlank(bean.getStatoNascitaIstituzione())) {
			input.setCodistatstatonascin(bean.getStatoNascitaIstituzione());
			if ("200".equals(bean.getStatoNascitaIstituzione())) {
				input.setCodistatcomunenascin(bean.getComuneNascitaIstituzione());
				input.setNomecomunenascin(bean.getNomeComuneNascitaIstituzione());
			} else {
				input.setNomecomunenascin(bean.getCittaNascitaIstituzione());
			}
		}
		input.setDtcessazionein(bean.getDataCessazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataCessazione()) : null);
		input.setCodcausalecessazionein(bean.getCausaleCessazione());

		String xmlIndirizzi = null;
		if (bean.getListaIndirizzi() != null && bean.getListaIndirizzi().size() > 0) {
			xmlIndirizzi = setXmlIndirizzi(bean);
		}
		input.setXmlindirizziin(xmlIndirizzi);

		String xmlContatti = null;
		if (bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			xmlContatti = getXmlContatti(bean);
		}
		input.setXmlcontattiin(xmlContatti);

		String xmlAltreDenominazioni = null;
		if (bean.getListaAltreDenominazioni() != null && bean.getListaAltreDenominazioni().size() > 0) {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			xmlAltreDenominazioni = lXmlUtilitySerializer.bindXmlList(bean.getListaAltreDenominazioni());
		}
		input.setXmlaltredenominazioniin(xmlAltreDenominazioni);

		// Federico Cacco 13.07.2016
		// Salvo la UO di appartenenza e i relativi flag
		if (bean.getIdUoAssociata() != null) {
			input.setIduoin(new BigDecimal(bean.getIdUoAssociata()));
		}
		input.setFlgvisibsottouoin((bean.getFlgVisibileDaSottoUo() == null || !bean.getFlgVisibileDaSottoUo()) ? 0 : 1);
		input.setFlggestsottouoin((bean.getFlgModificabileDaSottoUo() == null || !bean.getFlgModificabileDaSottoUo()) ? 0 : 1);

		DmpkAnagraficaIusoggetto iusoggetto = new DmpkAnagraficaIusoggetto();
		StoreResultBean<DmpkAnagraficaIusoggettoBean> output = iusoggetto.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setIdSoggetto(String.valueOf(output.getResultBean().getIdsoggrubrio()));
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
	public AnagraficaSoggettiBean update(AnagraficaSoggettiBean bean, AnagraficaSoggettiBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Se parametro in DB true controllo che almeno uno dei campi fondamentali sia valorizzato
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CTRL_DATI_MINIMI_RUBRICA")) {
			if (!(checkCFPIVAValorizzato(bean) || checkContattoValorizzato(bean) || checkIndirizzoValorizzato(bean))) {
				throw new StoreException("Inserire almeno uno tra: cod. fiscale/P.IVA, indirizzo, contatto (e-mail, tel, fax)");
			}
		}
		
		DmpkAnagraficaIusoggettoBean input = new DmpkAnagraficaIusoggettoBean();
		input.setIdsoggrubrio(new BigDecimal(bean.getIdSoggetto()));
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setFlgmodindirizziin("C");
		input.setFlgmodcontattiin("C");
		input.setFlgmodaltredenomin("C");
		// input.setFlgpersonafisicain(new BigDecimal(bean.getFlgPersFisica()));
		if (StringUtils.isNotBlank(bean.getSottotipo()) && bean.getTipo() != null
				&& (bean.getTipo().equals("#APA") || bean.getTipo().equals("#IAMM") || bean.getTipo().equals("#AG"))) {
			input.setCodtiposottotipoin(bean.getTipo() + ";" + bean.getSottotipo());
		} else {
			input.setCodtiposottotipoin(bean.getTipo());
		}
		// if(input.getFlgpersonafisicain().equals(new BigDecimal(1))) {
		if (bean.getTipo() != null && (bean.getTipo().equals("UP") || bean.getTipo().equals("#AF"))) {
			bean.setDenominazione(bean.getCognome() + " " + bean.getNome());
			// input.setCodtiposottotipoin(bean.getFlgUnitaDiPersonale() != null && bean.getFlgUnitaDiPersonale() ? "UP" : null);
			input.setFlgpersonafisicain(new BigDecimal(1));
			input.setDenomcognomein(bean.getCognome());
			input.setNomein(bean.getNome());
			input.setTitoloin(bean.getTitolo());
			input.setFlgsexin(bean.getSesso());
			input.setCodistatstatocittin(bean.getCittadinanza());
		} else {
			// input.setCodtiposottotipoin(bean.getTipologia());
			input.setDenomcognomein(bean.getDenominazione());
			input.setPivain(bean.getPartitaIva());
			input.setCodcondgiuridicain(bean.getCondizioneGiuridica());
			input.setCodammipain(bean.getCodiceAmmInIpa());
			input.setCodaooipain(bean.getCodiceAooInIpa());
			input.setCodindpain(bean.getCodicUoInIpa());
		}
		input.setCodrapidoin(bean.getCodiceRapido());
		input.setCfin(bean.getCodiceFiscale());
		input.setDtnascitain(bean.getDataNascitaIstituzione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataNascitaIstituzione()) : null);
		if (StringUtils.isNotBlank(bean.getStatoNascitaIstituzione())) {
			input.setCodistatstatonascin(bean.getStatoNascitaIstituzione());
			if ("200".equals(bean.getStatoNascitaIstituzione())) {
				input.setCodistatcomunenascin(bean.getComuneNascitaIstituzione());
				input.setNomecomunenascin(bean.getNomeComuneNascitaIstituzione());
			} else {
				input.setNomecomunenascin(bean.getCittaNascitaIstituzione());
			}
		}
		input.setDtcessazionein(bean.getDataCessazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataCessazione()) : null);
		input.setCodcausalecessazionein(bean.getCausaleCessazione());

		String xmlIndirizzi = null;
		if (bean.getListaIndirizzi() != null && bean.getListaIndirizzi().size() > 0) {
			xmlIndirizzi = setXmlIndirizzi(bean);
		}
		input.setXmlindirizziin(xmlIndirizzi);

		String xmlContatti = null;
		if (bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			xmlContatti = getXmlContatti(bean);
		}
		input.setXmlcontattiin(xmlContatti);

		String xmlAltreDenominazioni = null;
		if (bean.getListaAltreDenominazioni() != null && bean.getListaAltreDenominazioni().size() > 0) {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			xmlAltreDenominazioni = lXmlUtilitySerializer.bindXmlList(bean.getListaAltreDenominazioni());
		}
		input.setXmlaltredenominazioniin(xmlAltreDenominazioni);

		// Federico Cacco 13.07.2016
		// Salvo la UO di appartenenza e i relativi flag
		if (StringUtils.isNotBlank(bean.getIdUoAssociata())) {
			input.setIduoin(new BigDecimal(bean.getIdUoAssociata()));
		}
		input.setFlgvisibsottouoin((bean.getFlgVisibileDaSottoUo() == null || !bean.getFlgVisibileDaSottoUo()) ? 0 : 1);
		input.setFlggestsottouoin((bean.getFlgModificabileDaSottoUo() == null || !bean.getFlgModificabileDaSottoUo()) ? 0 : 1);

		DmpkAnagraficaIusoggetto iusoggetto = new DmpkAnagraficaIusoggetto();
		StoreResultBean<DmpkAnagraficaIusoggettoBean> output = iusoggetto.execute(getLocale(), loginBean, input);
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

		return bean;
	}

	@Override
	public AnagraficaSoggettiBean remove(AnagraficaSoggettiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAnagraficaDsoggettoBean input = new DmpkAnagraficaDsoggettoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsoggrubrin(new BigDecimal(bean.getIdSoggetto()));
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
		input.setFlgignorewarningin(new Integer(1));

		DmpkAnagraficaDsoggetto dsoggetto = new DmpkAnagraficaDsoggetto();
		StoreResultBean<DmpkAnagraficaDsoggettoBean> output = dsoggetto.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

	protected String getXmlContatti(AnagraficaSoggettiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		List<ContattoSoggettoXmlBean> lList = new ArrayList<ContattoSoggettoXmlBean>();

		for (ContattoSoggettoBean lContattoSoggettoBean : bean.getListaContatti()) {
			ContattoSoggettoXmlBean lContattoSoggettoXmlBean = new ContattoSoggettoXmlBean();
			lContattoSoggettoXmlBean.setRowId(lContattoSoggettoBean.getRowId());
			lContattoSoggettoXmlBean.setTipo(lContattoSoggettoBean.getTipo());
			if ("E".equals(lContattoSoggettoBean.getTipo())) {
				if(StringUtils.isNotBlank(lContattoSoggettoBean.getEmail())){
					lContattoSoggettoXmlBean.setEmailTelFax(lContattoSoggettoBean.getEmail());
					lContattoSoggettoXmlBean.setFlgPec(lContattoSoggettoBean.getFlgPec() != null && lContattoSoggettoBean.getFlgPec() ? "1" : "0");
					lContattoSoggettoXmlBean.setFlgDichIpa(lContattoSoggettoBean.getFlgDichIpa() != null && lContattoSoggettoBean.getFlgDichIpa() ? "1" : "0");
					lContattoSoggettoXmlBean.setFlgCasellaIstituz(lContattoSoggettoBean.getFlgCasellaIstituz() != null && lContattoSoggettoBean.getFlgCasellaIstituz() ? "1" : "0");
					lContattoSoggettoXmlBean.setFlgDomicilioDigitale(lContattoSoggettoBean.getFlgDomicilioDigitale() != null && lContattoSoggettoBean.getFlgDomicilioDigitale() ? "1" : "0");
					
					lList.add(lContattoSoggettoXmlBean);
				}
			} else if ("F".equals(lContattoSoggettoBean.getTipo())) {
				if(StringUtils.isNotBlank(lContattoSoggettoBean.getTelFax())){
					lContattoSoggettoXmlBean.setEmailTelFax(lContattoSoggettoBean.getTelFax());
					lList.add(lContattoSoggettoXmlBean);
				}
			} else if ("T".equals(lContattoSoggettoBean.getTipo())) {
				if(StringUtils.isNotBlank(lContattoSoggettoBean.getTelFax())){
					lContattoSoggettoXmlBean.setEmailTelFax(lContattoSoggettoBean.getTelFax());
					lContattoSoggettoXmlBean.setTipoTel(lContattoSoggettoBean.getTipoTel());
					lList.add(lContattoSoggettoXmlBean);
				}	
			}	
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlContatti = lXmlUtilitySerializer.bindXmlList(lList);

		return xmlContatti;
	}

	// Populate Indirizzi in SAVE - UPDATE
	protected String setXmlIndirizzi(AnagraficaSoggettiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		List<IndirizzoSoggettoXmlBean> lListIndirizzi = new ArrayList<IndirizzoSoggettoXmlBean>();

		for (IndirizzoSoggettoBean lIndirizzoSoggettoBean : bean.getListaIndirizzi()) {
			IndirizzoSoggettoXmlBean lIndirizzoSoggettoXmlBean = new IndirizzoSoggettoXmlBean();
			lIndirizzoSoggettoXmlBean.setRowId(lIndirizzoSoggettoBean.getRowId());
			lIndirizzoSoggettoXmlBean.setTipo(lIndirizzoSoggettoBean.getTipo());
			lIndirizzoSoggettoXmlBean.setDataValidoDal(lIndirizzoSoggettoBean.getDataValidoDal());
			lIndirizzoSoggettoXmlBean.setDataValidoFinoAl(lIndirizzoSoggettoBean.getDataValidoFinoAl());

			lIndirizzoSoggettoXmlBean.setStato(lIndirizzoSoggettoBean.getStato());
			lIndirizzoSoggettoXmlBean.setNomeStato(lIndirizzoSoggettoBean.getNomeStato());
			if (StringUtils.isBlank(lIndirizzoSoggettoXmlBean.getStato()) || _COD_ISTAT_ITALIA.equals(lIndirizzoSoggettoXmlBean.getStato())
					|| _NOME_STATO_ITALIA.equals(lIndirizzoSoggettoXmlBean.getStato())) {
				if (StringUtils.isNotBlank(lIndirizzoSoggettoBean.getCodToponimo())) {
					lIndirizzoSoggettoXmlBean.setCodToponimo(lIndirizzoSoggettoBean.getCodToponimo());
					lIndirizzoSoggettoXmlBean.setComune(getCodIstatComuneRif());
					lIndirizzoSoggettoXmlBean.setNomeComuneCitta(getNomeComuneRif());
					lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getIndirizzo());
				} else {
					lIndirizzoSoggettoXmlBean.setTipoToponimo(lIndirizzoSoggettoBean.getTipoToponimo());
					lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getToponimo());
					lIndirizzoSoggettoXmlBean.setComune(lIndirizzoSoggettoBean.getComune());
					lIndirizzoSoggettoXmlBean.setNomeComuneCitta(lIndirizzoSoggettoBean.getNomeComune());
				}
				lIndirizzoSoggettoXmlBean.setFrazione(lIndirizzoSoggettoBean.getFrazione());
				lIndirizzoSoggettoXmlBean.setCap(lIndirizzoSoggettoBean.getCap());
			} else {
				lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getIndirizzo());
				lIndirizzoSoggettoXmlBean.setNomeComuneCitta(lIndirizzoSoggettoBean.getCitta());
			}
			lIndirizzoSoggettoXmlBean.setCivico(lIndirizzoSoggettoBean.getCivico());
			lIndirizzoSoggettoXmlBean.setInterno(lIndirizzoSoggettoBean.getInterno());
			lIndirizzoSoggettoXmlBean.setZona(lIndirizzoSoggettoBean.getZona());
			lIndirizzoSoggettoXmlBean.setComplementoIndirizzo(lIndirizzoSoggettoBean.getComplementoIndirizzo());
			lIndirizzoSoggettoXmlBean.setAppendici(lIndirizzoSoggettoBean.getAppendici());

			lListIndirizzi.add(lIndirizzoSoggettoXmlBean);
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlIndirizzi = lXmlUtilitySerializer.bindXmlList(lListIndirizzi);

		return xmlIndirizzi;
	}

	// Populate Indirizzi in GET
	protected List<IndirizzoSoggettoBean> getXmlIndirizzi(List<IndirizzoSoggettoXmlBean> listaIndirizziSoggettiXmlBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		List<IndirizzoSoggettoBean> listaIndirizzi = new ArrayList<IndirizzoSoggettoBean>();

		for (IndirizzoSoggettoXmlBean lIndirizzoSoggettoXmlBean : listaIndirizziSoggettiXmlBean) {
			IndirizzoSoggettoBean lIndirizzoSoggettoBean = new IndirizzoSoggettoBean();

			lIndirizzoSoggettoBean.setRowId(lIndirizzoSoggettoXmlBean.getRowId());
			lIndirizzoSoggettoBean.setTipo(lIndirizzoSoggettoXmlBean.getTipo());
			lIndirizzoSoggettoBean.setDataValidoDal(lIndirizzoSoggettoXmlBean.getDataValidoDal());
			lIndirizzoSoggettoBean.setDataValidoFinoAl(lIndirizzoSoggettoXmlBean.getDataValidoFinoAl());

			lIndirizzoSoggettoBean.setStato(lIndirizzoSoggettoXmlBean.getStato());
			lIndirizzoSoggettoBean.setNomeStato(lIndirizzoSoggettoXmlBean.getNomeStato());
			if (StringUtils.isBlank(lIndirizzoSoggettoXmlBean.getStato()) || _COD_ISTAT_ITALIA.equals(lIndirizzoSoggettoXmlBean.getStato())
					|| _NOME_STATO_ITALIA.equals(lIndirizzoSoggettoXmlBean.getStato())) {
				if (StringUtils.isNotBlank(lIndirizzoSoggettoXmlBean.getCodToponimo()) || StringUtils.isBlank(lIndirizzoSoggettoXmlBean.getToponimoIndirizzo())) {
					lIndirizzoSoggettoBean.setFlgFuoriComune(false);
					lIndirizzoSoggettoBean.setCodToponimo(lIndirizzoSoggettoXmlBean.getCodToponimo());
					lIndirizzoSoggettoBean.setIndirizzo(lIndirizzoSoggettoXmlBean.getToponimoIndirizzo());
					lIndirizzoSoggettoBean.setComune(getCodIstatComuneRif());
					lIndirizzoSoggettoBean.setNomeComune(getNomeComuneRif());
				} else {
					lIndirizzoSoggettoBean.setFlgFuoriComune(true);
					lIndirizzoSoggettoBean.setTipoToponimo(lIndirizzoSoggettoXmlBean.getTipoToponimo());
					lIndirizzoSoggettoBean.setToponimo(lIndirizzoSoggettoXmlBean.getToponimoIndirizzo());
					lIndirizzoSoggettoBean.setComune(lIndirizzoSoggettoXmlBean.getComune());
					lIndirizzoSoggettoBean.setNomeComune(lIndirizzoSoggettoXmlBean.getNomeComuneCitta());
				}
				lIndirizzoSoggettoBean.setFrazione(lIndirizzoSoggettoXmlBean.getFrazione());
				lIndirizzoSoggettoBean.setCap(lIndirizzoSoggettoXmlBean.getCap());
			} else {
				lIndirizzoSoggettoBean.setCitta(lIndirizzoSoggettoXmlBean.getNomeComuneCitta());
				lIndirizzoSoggettoBean.setIndirizzo(lIndirizzoSoggettoXmlBean.getToponimoIndirizzo());
			}
			lIndirizzoSoggettoBean.setCivico(lIndirizzoSoggettoXmlBean.getCivico());
			lIndirizzoSoggettoBean.setInterno(lIndirizzoSoggettoXmlBean.getInterno());
			lIndirizzoSoggettoBean.setZona(lIndirizzoSoggettoXmlBean.getZona());
			lIndirizzoSoggettoBean.setComplementoIndirizzo(lIndirizzoSoggettoXmlBean.getComplementoIndirizzo());
			lIndirizzoSoggettoBean.setAppendici(lIndirizzoSoggettoXmlBean.getAppendici());

			listaIndirizzi.add(lIndirizzoSoggettoBean);
		}

		return listaIndirizzi;
	}

	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {

		List<Object> soggetti = getSoggetti(bean.getCriteria(), -1);

		NroRecordTotBean retValue = new NroRecordTotBean();

		retValue.setNroRecordTot(Integer.valueOf((String) soggetti.get(1)));

		return retValue;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<Object> resFinder = getSoggetti(bean.getCriteria(), -2);

		Integer jobId = Integer.valueOf((String) resFinder.get(6));
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), AnagraficaSoggettiXmlBean.class.getName());

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString()
			+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return bean;
	}

	// Metodo per controllare che il campo indirizzi sia valorizzato
	public Boolean checkIndirizzoValorizzato(AnagraficaSoggettiBean bean){
		if(bean.getListaIndirizzi() != null && bean.getListaIndirizzi().size() > 0) {
			for (IndirizzoSoggettoBean lIndirizzoSoggettoBean : bean.getListaIndirizzi()) {	
				if (StringUtils.isBlank(lIndirizzoSoggettoBean.getStato()) || _COD_ISTAT_ITALIA.equals(lIndirizzoSoggettoBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lIndirizzoSoggettoBean.getStato())) {
					if (lIndirizzoSoggettoBean.getFlgFuoriComune() == null || !lIndirizzoSoggettoBean.getFlgFuoriComune()) {
						if(StringUtils.isNotBlank(lIndirizzoSoggettoBean.getIndirizzo())) {
							return true;
						}
					} else {
						if(StringUtils.isNotBlank(lIndirizzoSoggettoBean.getComune()) && StringUtils.isNotBlank(lIndirizzoSoggettoBean.getToponimo())) {
							return true;
						}
					}
				} else {
					if(StringUtils.isNotBlank(lIndirizzoSoggettoBean.getCitta()) && StringUtils.isNotBlank(lIndirizzoSoggettoBean.getIndirizzo())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Metodo per controllare che il campo contatto sia valorizzato
	public Boolean checkContattoValorizzato(AnagraficaSoggettiBean bean){
		if(bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			for (ContattoSoggettoBean lContattoSoggettoBean : bean.getListaContatti()) {
				if(StringUtils.isNotBlank(lContattoSoggettoBean.getTipo())) {
					if ("E".equals(lContattoSoggettoBean.getTipo())) {
						if(StringUtils.isNotBlank(lContattoSoggettoBean.getEmail())){
							return true;
						}
					} else if ("F".equals(lContattoSoggettoBean.getTipo())) {
						if(StringUtils.isNotBlank(lContattoSoggettoBean.getTelFax())){
							return true;
						}
					} else if ("T".equals(lContattoSoggettoBean.getTipo())) {
						if(StringUtils.isNotBlank(lContattoSoggettoBean.getTelFax())){
							return true;
						}
					}	
				}
			}
		}
		return false;	
	}
	
	// Metodo per controllare che il campo codice fiscale o la partita IVA siano valorizzati
	public Boolean checkCFPIVAValorizzato(AnagraficaSoggettiBean bean){	
		if(isPersonaFisica(bean)) {
			return StringUtils.isNotBlank(bean.getCodiceFiscale());
		} else {
			return StringUtils.isNotBlank(bean.getCodiceFiscale()) || StringUtils.isNotBlank(bean.getPartitaIva());
		}
	}

	public boolean isPersonaFisica(AnagraficaSoggettiBean bean) {
		String tipo = bean.getTipo() != null ? bean.getTipo() : "";
		return tipo.equals("UP") || tipo.equals("#AF");
	}
	
	public String getCodIstatComuneRif() {
		return ParametriDBUtil.getParametroDB(getSession(), "ISTAT_COMUNE_RIF");
	}

	public String getNomeComuneRif() {
		return ParametriDBUtil.getParametroDB(getSession(), "NOME_COMUNE_RIF");
	}

}
