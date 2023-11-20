/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityAssociapuntiprotocolloBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDuoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIuuoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettuoestesaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityScollegapuntiprotocolloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindOrgStructureObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CentroCostoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoXmlBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.LoadAttrDinamicoListaDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaOutputBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.organigramma.OrganigrammaXmlBean;
import it.eng.auriga.ui.module.layout.server.organigramma.OrganigrammaXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.AssociaScollegaPuntoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.AttributiDinamiciOrganigrammaXmlBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.OrganigrammaBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.OrganigrammaPredecessoreXmlBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.OrganigrammaSearchSezioneCacheBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.OrganigrammaSuccessoreXmlBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.PuntoProtocolloCollegatoBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaUOBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.TipologiaOffertaPuntoVenditaBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.TipologiaOffertaPuntoVenditaXmlBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.TipologiaPromozionePuntoVenditaBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.TipologiaPromozionePuntoVenditaXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkDefSecurityAssociapuntiprotocollo;
import it.eng.client.DmpkDefSecurityDuo;
import it.eng.client.DmpkDefSecurityIuuo;
import it.eng.client.DmpkDefSecurityLoaddettuoestesa;
import it.eng.client.DmpkDefSecurityScollegapuntiprotocollo;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.ListUtility;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;


@Datasource(id = "OrganigrammaDatasource")
public class OrganigrammaDatasource extends AurigaAbstractFetchDatasource<OrganigrammaBean> {

	private static final Logger logger = Logger.getLogger(OrganigrammaDatasource.class);

	@Override
	public PaginatorBean<OrganigrammaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String filtroFullText = null;
		String[] checkAttributes = null;
		
		List<OrganigrammaBean> data = new ArrayList<OrganigrammaBean>();
		
		boolean overflow = false;
		
		FindOrgStructureObjectBean lFindOrgStructureObjectBean = createFindRepositoryObjectBean(criteria, loginBean, filtroFullText, checkAttributes);

		if (StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
		} else {

			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findorgstructureobject(getLocale(), loginBean, lFindOrgStructureObjectBean).getList();
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
						OrganigrammaBean bean = new OrganigrammaBean();
						bean.setTipo(v.get(0)); // colonna 1 dell'xml
						if (bean.getTipo().equals("UO")) {
							bean.setTipo("UO_" + v.get(4)); // colonna 5 dell'xml
							bean.setDescrTipo(v.get(5)); // colonna 6 dell'xml
						}
						bean.setNome(v.get(2)); // colonna 3 dell'xml
						bean.setNroLivello(v.get(3));// colonna 4 dell'xml
						bean.setIdUoSvUt(v.get(6)); // colonna 7 dell'xml
						bean.setDenominazioneEstesa(v.get(7)); // colonna 8 dell'xml
						bean.setIdUo(v.get(8));// colonna 9
						bean.setIdUser(v.get(9));// colonna 10
						bean.setTsInizio(v.get(10) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(10)) : null); // colonna 11 dell'xml
						bean.setTsFine(v.get(11) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(11)) : null); // colonna 12 dell'xml
						bean.setCodRapidoUo(v.get(13)); // colonna 14 dell'xml
						if (bean.getTipo().startsWith("UO")) {
							bean.setDescrUoSvUt(bean.getNome());
						} else {
							bean.setDescrUoSvUt(v.get(15)); // colonna 16 dell'xml
						}
						bean.setTipoRelUtenteUo(v.get(30)); // colonna 31 dell'xml
						bean.setDataInizio(v.get(10));// colonna 11
						bean.setIdRuolo(v.get(17));// colonnga 18
						bean.setCiRelUserUo(bean.getIdUo() + "." + bean.getIdUser() + "." + bean.getTipoRelUtenteUo() + "." + bean.getDataInizio() + "."
								+ bean.getIdRuolo());
						bean.setFlgSelXFinalita(v.get(34) != null && "1".equals(v.get(34))); // colonna 35 dell'xml
						bean.setIdUoSup(v.get(35)); // colonna 36 dell'xml
						bean.setDenomUoSup(v.get(37)); // colonna 38 dell'xml
						bean.setScore(v.get(38) != null ? new BigDecimal(v.get(38)) : null); // colonna 39 dell'xml
						bean.setCodRapidoSvUt(v.get(39)); // colonna 40 dell'xml
						bean.setCodRapidoUoXOrd(v.get(40)); // colonna 41 dell'xml
						bean.setCodFiscale(v.get(41)); // colonna 42 dell'xml
						bean.setIdRubrica(v.get(42)); // colonna 43 dell'xml
						bean.setRuolo(v.get(18)); // colonna 19 : ruolo
						bean.setUsername(v.get(19)); // colonna 20 : username
						bean.setTitolo(v.get(20)); // colonna 21 : titolo
						bean.setQualifica(v.get(21)); // colonna 22 : qualifica
						bean.setCompetenzefunzioni(v.get(22)); // colonna 23 : competenze/funzioni
						bean.setCompetenze(v.get(22));
						bean.setFlgPubblicataSuIpa(v.get(24)); // colonna 25 : flag pubblicata su IPA
						bean.setDtPubblicazioneIpa(v.get(25) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(25)) : null); // colonna 26 : data pubblicazione IPA
						bean.setIndirizzo(v.get(26)); // colonna 27 : indirizzo
						bean.setTelefono(v.get(27)); // colonna 28 : telefono
						bean.setEmail(v.get(28)); // colonna 29 : email
						bean.setFax(v.get(29)); // colonna 30 : fax
						bean.setFlgInibitaAssegnazione(v.get(43) != null && "0".equals(v.get(43))); // colonna Flag 1/0 che indica (se 1) se è consentita l'assegnazione di documenti alla UO
						bean.setFlgInibitoInvioCC(v.get(44) != null && "0".equals(v.get(44))); // colonna 45 Flag 1/0 che indica (se 1) se è consentito l'invio per conoscenza di documenti alla UO
						bean.setProfilo(v.get(45)); // colonna 46 profilo
						bean.setFlgPuntoProtocollo(v.get(46) != null && "1".equals(v.get(46))); // colonna 47: punto di protocollo
						bean.setNomePostazioneInPrecedenza(v.get(49)); // colonna 50 dell'xml
						bean.setFlgDestinatarioNeiPreferiti(v.get(50) != null && "1".equals(v.get(50)));  // col.51 : la UO/scrivania e' nei preferiti (1 = si, 0 = no)						
						bean.setFlgSelXAssegnazione(v.get(51)); // colonna 52 dell'xml
						bean.setFlgProtocollista(v.get(54) != null && "1".equals(v.get(54))); // colonna 55 : protocollista
						bean.setCollegataAPuntoProt(v.get(55)); // colonna 56: Punto di Prot.
						bean.setFlgInibitaAssegnazioneEmail(v.get(56) != null && "0".equals(v.get(56))); // colonna 57 Flag 1/0 che indica (se 1) se è consentita l’assegnazione di mail alla UO
						bean.setFlgPuntoRaccoltaEmail(v.get(57) != null  && "1".equals(v.get(57))); //colonna 58						
						bean.setSubProfili(v.get(60)); // colonna 61 sub profili
						bean.setAbilitaUoProtEntrata(v.get(61)    != null && "1".equals(v.get(61)));   // col.62 abilitata alla protocollazione in entrata (1=si, 0=no)
						bean.setAbilitaUoProtUscita(v.get(62)     != null && "1".equals(v.get(62)));   // col.63 abilitata alla protocollazione in uscita (1=si, 0=no)
						bean.setAbilitaUoProtUscitaFull(v.get(63) != null && "1".equals(v.get(63)));   // col.64 abilitata alla protocollazione in uscita con qualsiasi mezzo (1=si, 0=no)
						bean.setAbilitaUoGestMulte(v.get(64)      != null && "1".equals(v.get(64)));   // col.65 abilitata alla registrazione multe (1=si, 0=no)
						bean.setAbilitaUoGestContratti(v.get(65)  != null && "1".equals(v.get(65)));   // col.66 abilitata alla registrazione contratti (1=si, 0=no)
						bean.setTsStrutturaDaCessareDal(v.get(66) != null ? v.get(66) : "");           // col.67 colonna con data senza ora
						bean.setAbilitaUoAssRiservatezza(v.get(67)!= null && "1".equals(v.get(67)));   // col.68 abilitata ad assegnare riservatezza (1=si, 0=no)						
						bean.setOrigineCreazione(v.get(68));                                           // col.69 origine creazione (HR = da sistema HR, M = creazione manuale)						
						data.add(bean);
					}
				}
			}
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<OrganigrammaBean> lPaginatorBean = new PaginatorBean<OrganigrammaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	@Override
	public OrganigrammaBean get(OrganigrammaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityLoaddettuoestesaBean input = new DmpkDefSecurityLoaddettuoestesaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setIduoio(new BigDecimal(bean.getIdUoSvUt()));

		DmpkDefSecurityLoaddettuoestesa defSecurityLoaddettuoestesa = new DmpkDefSecurityLoaddettuoestesa();
		StoreResultBean<DmpkDefSecurityLoaddettuoestesaBean> output = defSecurityLoaddettuoestesa.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		OrganigrammaBean res = new OrganigrammaBean();
		res.setIdUoSvUt(bean.getIdUoSvUt());
		res.setIdUoSup(bean.getIdUoSup());
		res.setDataDichIndicePA(StringUtils.isNotBlank(output.getResultBean().getDtdichipaout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtdichipaout()) : null);
		res.setCheckDichiarataIndicePA(output.getResultBean().getFlgdichipaout() != null && output.getResultBean().getFlgdichipaout().intValue() == 1);
		res.setDataIstituita(StringUtils.isNotBlank(output.getResultBean().getDtistituzioneout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtistituzioneout()) : null);
		res.setDataSoppressa(StringUtils.isNotBlank(output.getResultBean().getDtsoppressioneout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtsoppressioneout()) : null);
		res.setDenominazioneEstesa(output.getResultBean().getDenominazioneio());
		res.setTipo(output.getResultBean().getCodtipoout());
		res.setCodRapidoUo(output.getResultBean().getNrilivelliuoio());

		if (StringUtils.isBlank(output.getResultBean().getNrilivelliuoio())) {
			res.setLivelloCorrente(null);
			res.setLivello(null);
			res.setNroLivello("");
		} else {
			int pos = output.getResultBean().getNrilivelliuoio().lastIndexOf(".");
			if (pos == -1) {
				res.setLivelloCorrente(null);
				res.setLivello(output.getResultBean().getNrilivelliuoio());
				res.setNroLivello("1");
			} else {
				res.setLivelloCorrente(output.getResultBean().getNrilivelliuoio().substring(0, pos));
				res.setLivello(output.getResultBean().getNrilivelliuoio().substring(pos + 1));
				StringSplitterServer st = new StringSplitterServer(output.getResultBean().getNrilivelliuoio(), ".");
				res.setNroLivello("" + st.countTokens());
			}
		}

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
					contattoBean.setTipoTel(v.get(3)); // colonna 4 dell'xml : Tipo di numero di telefono (abitazione, ufficio, cell. ecc.) Valori da apposita
					// dictionary entry
					contattoBean.setFlgPec(v.get(4) != null && "1".equals(v.get(4))); // colonna 5 dell'xml : (valori 1/0) Flag di indirizzo e-mail di Posta
					// Elettronica Certificata
					contattoBean.setFlgDichIpa(v.get(5) != null && "1".equals(v.get(5))); // colonna 6 dell'xml : (valori 1/0) Flag di indirizzo e-mail
					// dichiarato all'Indice PA
					contattoBean.setFlgCasellaIstituz(v.get(6) != null && "1".equals(v.get(6))); // colonna 7 dell'xml : (valori 1/0) Flag di indirizzo e-mail
					// che è Casella Istituzionale di una PA
					listaContatti.add(contattoBean);
				}
			}
		}
		res.setListaContatti(listaContatti);

		/**
		 * Precessori
		 */
		if (output.getResultBean().getXmlprececessoriout() != null) {
			List<SelezionaUOBean> listaUODerivataDa = new ArrayList<SelezionaUOBean>();
			String tipoAzione = null;
			StringReader sr = new StringReader(output.getResultBean().getXmlprececessoriout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null && lista.getRiga().size()>0) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

					SelezionaUOBean selezionaUOBean = new SelezionaUOBean();

					tipoAzione = v.get(0);
					selezionaUOBean.setIdUo(v.get(1));
					selezionaUOBean.setCodRapido(v.get(2));
					selezionaUOBean.setDescrizione(v.get(3));
					selezionaUOBean.setDescrizioneEstesa(v.get(3));
					selezionaUOBean.setOrganigramma("UO "+ v.get(1));
					listaUODerivataDa.add(selezionaUOBean);
				}
			}
			res.setUoDerivataPer(tipoAzione);
			res.setListaUODerivataDa(listaUODerivataDa);
		}

		/**
		 * Successori
		 */
		if (output.getResultBean().getXmlsuccessoriout() != null) {
			String tipoAzione = null;
			List<SelezionaUOBean> listaUODatoLuogoA = new ArrayList<SelezionaUOBean>();
			StringReader sr = new StringReader(output.getResultBean().getXmlsuccessoriout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null && lista.getRiga().size()>0) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

					SelezionaUOBean selezionaUOBean = new SelezionaUOBean();

					tipoAzione = v.get(0);
					selezionaUOBean.setIdUo(v.get(1));
					selezionaUOBean.setCodRapido(v.get(2));
					selezionaUOBean.setDescrizione(v.get(3));
					selezionaUOBean.setDescrizioneEstesa(v.get(3));
					selezionaUOBean.setOrganigramma("UO "+ v.get(1));
					listaUODatoLuogoA.add(selezionaUOBean);
				}
			}
			res.setUoDatoLuogoAPer(tipoAzione);
			res.setListaUODatoLuogoA(listaUODatoLuogoA);
		}

		// Federico Cacco 05.07.2016
		// Setto la lista dei punti protocollo collegati
		res.setUoPuntoProtocolloCollegate(getListaPuntiProtocollo(output.getResultBean()));

		// Setto il flag che indica se la UO ha in carico documenti/fascicoli 
		res.setFlgPresentiDocFasc(output.getResultBean().getFlgpresentidocfascout());
		
		// Setto la UO che ha in carico i documenti/fascicoli
		res.setIdUODestDocfasc(output.getResultBean().getIduodestdocfascout());
		res.setLivelliUODestDocFasc(output.getResultBean().getLivelliuodestdocfascout());
		res.setDesUODestDocFasc(output.getResultBean().getDesuodestdocfascout());
		//res.setIdUODestDocfasc(new BigDecimal(100020));
		//res.setLivelliUODestDocFasc("A10");
		//res.setDesUODestDocFasc("GABINETTO DEL SINDACO");
		
		// Setto il flag che indica se la UO ha in carico mail
		res.setFlgPresentiMail(output.getResultBean().getFlgpresentimailout());
		//res.setFlgPresentiMail(new Integer(1));
		
		// Setto la UO che ha in carico le mail
		res.setIdUODestMail(output.getResultBean().getIduodestmailout());
		res.setLivelliUODestMail(output.getResultBean().getLivelliuodestmailout());
		res.setDesUODestMail(output.getResultBean().getDesuodestmailout());
		//res.setIdUODestMail(new BigDecimal(100020));
		//res.setLivelliUODestMail("A10");
		//res.setDesUODestMail("GABINETTO DEL SINDACO");
		
		//String sDate1="31/12/2000";
		//Date dataSoppressa=new SimpleDateFormat(FMT_STD_DATA).parse(sDate1);
		//res.setDataSoppressa(dataSoppressa);
		
		// Resoconto sulla situazione documentazione e mail in competenza alla UO
		 // Situazione dei documenti/fascicoli assegnati
		res.setNrDocAssegnati(output.getResultBean().getNrodocassout());
		res.setDataConteggioDocAssegnati(StringUtils.isNotBlank(output.getResultBean().getTsrilevnrodocassout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsrilevnrodocassout()) : null);
		res.setNrFascAssegnati(output.getResultBean().getNrofascassout());
		res.setDataConteggioFascAssegnati(StringUtils.isNotBlank(output.getResultBean().getTsrilevnrofascassout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsrilevnrofascassout()) : null);
		res.setDescUoSpostamentoDocFasc((StringUtils.isNotBlank(output.getResultBean().getLivelliuodestdocfascout()) ? output.getResultBean().getLivelliuodestdocfascout() :  "") + " " + (StringUtils.isNotBlank(output.getResultBean().getDesuodestdocfascout()) ? output.getResultBean().getDesuodestdocfascout() :  ""));
		
		res.setStatoSpostamentoDocFasc(output.getResultBean().getStatospostdocfascout());
		res.setDataInizioSpostamentoDocFasc(StringUtils.isNotBlank(output.getResultBean().getTsiniziospostdocfascout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsiniziospostdocfascout()) : null);
		res.setDataFineSpostamentoDocFasc(StringUtils.isNotBlank(output.getResultBean().getTsfinespostdocfascout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsfinespostdocfascout()) : null);
		
		// Situazione delle mail assegnate
		res.setNrMailAssegnati(output.getResultBean().getNromailassout() );
		res.setDataConteggioMailAssegnati(StringUtils.isNotBlank(output.getResultBean().getTsrilevnromailassout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsrilevnromailassout()) : null);
		res.setDescUoSpostamentoMail((StringUtils.isNotBlank(output.getResultBean().getLivelliuodestmailout()) ? output.getResultBean().getLivelliuodestmailout() :  "") + " " + (StringUtils.isNotBlank(output.getResultBean().getDesuodestmailout()) ? output.getResultBean().getDesuodestmailout() :  ""));
		res.setStatoSpostamentoMail(output.getResultBean().getStatospostmailout());		
		res.setDataInizioSpostamentoMail(StringUtils.isNotBlank(output.getResultBean().getTsiniziospostmailout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsiniziospostmailout()) : null);
		res.setDataFineSpostamentoMail(StringUtils.isNotBlank(output.getResultBean().getTsfinespostmailout()) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getTsfinespostmailout()) : null);

		// Legge il campo competenze
		res.setCompetenze(output.getResultBean().getCompetenzeout());

		// Legge il campo CIProvUOOut
		res.setCiProvUO(output.getResultBean().getCiprovuoout());
				
		// Legge il campo flgAssInvioUPOut 
		res.setFlgAssInvioUP(output.getResultBean().getFlgassinvioupout());
		
		// Legge il campo flgPropagaAssInvioUPOut
		res.setFlgPropagaAssInvioUP(output.getResultBean().getFlgpropagaassinvioupout() != null ? new Boolean(output.getResultBean().getFlgpropagaassinvioupout()==1) : false);
		
		// Legge il campo flgEreditaAssInvioUPOut 
		res.setFlgEreditaAssInvioUP(output.getResultBean().getFlgereditaassinvioupout());
		
		// legge gli attributi
		LoadAttrDinamicoListaOutputBean lAttributiDinamici = new LoadAttrDinamicoListaOutputBean();

		List<DettColonnaAttributoListaBean> ldatiDettLista = new ArrayList<DettColonnaAttributoListaBean>();

		String xmlListaAttributi = output.getResultBean().getAttributiaddout();
		try {
			ldatiDettLista = XmlListaUtility.recuperaLista(xmlListaAttributi, DettColonnaAttributoListaBean.class);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		if (ldatiDettLista != null && ldatiDettLista.size() > 0) {

			for (DettColonnaAttributoListaBean lDettColonnaAttributoListaBean : ldatiDettLista) {

				// flgInibitaAssegnazione
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_INIBITA_ASS")) {
					res.setFlgInibitaAssegnazione(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean
							.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				// if(lDettColonnaAttributoListaBean.getValoreDefault()!=null && !lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase(""))
				// res.setFlgInibitaAssegnazione(lDettColonnaAttributoListaBean.getValoreDefault());
				// else
				// res.setFlgInibitaAssegnazione("false");

				// flgInibitoInvioCC
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_INIBITO_INVIO_CC")) {
					res.setFlgInibitoInvioCC(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean
							.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				// if(lDettColonnaAttributoListaBean.getValoreDefault()!=null && !lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase(""))
				// res.setFlgInibitoInvioCC(lDettColonnaAttributoListaBean.getValoreDefault());
				// else
				// res.setFlgInibitoInvioCC("false");
				// flgPuntoProtocollo
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_PUNTO_PROTOCOLLO")) {
					res.setFlgPuntoProtocollo(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean
							.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("DES_BREVE_UO")) {
					res.setDenominazioneBreve(lDettColonnaAttributoListaBean.getValoreDefault() != null ? lDettColonnaAttributoListaBean.getValoreDefault()
							: "");
				}
			
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("CDC_STRUTTURA")) {
					String rowId = output.getResultBean().getRowidout();
					List<CentroCostoBean>    listaCentriCosto = leggiListaCentriCosto(rowId);
					res.setListaCentriCosto(listaCentriCosto);
				}

				// Lista ASSESSORI
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("ID_USER_ASSESSORE")) {
					String rowId = output.getResultBean().getRowidout();
					List<String>    listaAssessoriRif = leggiListaAssessoriRif(rowId);
					res.setIdAssessoreRif(listaAssessoriRif);
				}
				
				
				// ***************************************************
				// Ufficio liquidatore rif.
				// ***************************************************
				// ID
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("ID_UFF_LIQUIDATORE")) {
					res.setUoUfficioLiquidatore(lDettColonnaAttributoListaBean.getValoreDefault()       != null ? lDettColonnaAttributoListaBean.getValoreDefault(): "");
					res.setIdUoUfficioLiquidatore(lDettColonnaAttributoListaBean.getValoreDefault()       != null ? lDettColonnaAttributoListaBean.getValoreDefault(): "");
				}
				
				// TYPE NODO
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("TYPENODO_UFF_LIQUIDATORE")) {
					res.setTypeNodoUfficioLiquidatore(lDettColonnaAttributoListaBean.getValoreDefault()       != null ? lDettColonnaAttributoListaBean.getValoreDefault(): "");
				}
				
				// COD RAPIDO
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("CODRAPIDO_UFF_LIQUIDATORE")) {
					res.setCodRapidoUfficioLiquidatore(lDettColonnaAttributoListaBean.getValoreDefault()       != null ? lDettColonnaAttributoListaBean.getValoreDefault(): "");
				}
				
				// DESCRIZIONE
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("DESC_UFF_LIQUIDATORE")) {
					res.setDescrizioneUfficioLiquidatore(lDettColonnaAttributoListaBean.getValoreDefault()       != null ? lDettColonnaAttributoListaBean.getValoreDefault(): "");
				}
				
				
				// Valore del radio button presenteAttoDefOrganigramma
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_UO_IN_ATTO")) {
					if(lDettColonnaAttributoListaBean.getValoreDefault() != null){
						if("SI".equals(lDettColonnaAttributoListaBean.getValoreDefault())){
							res.setPresenteAttoDefOrganigramma("Si");
						}else if("NO".equals(lDettColonnaAttributoListaBean.getValoreDefault())){
							res.setPresenteAttoDefOrganigramma("No");
						}
					}
				}
				
				// Valore del check inibitaPreimpDestProtEntrata
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_NO_PREIMP_DEST_COME_UO_PROT")) {
					res.setInibitaPreimpDestProtEntrata(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				
				// Valore del check flgPuntoRaccoltaEmail
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_PUNTO_RACCOLTA_EMAIL")) {
					res.setFlgPuntoRaccoltaEmail(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				
				// Valore del check flgPuntoRaccoltaDocumenti
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_PUNTO_RACCOLTA_DOC")) {
					res.setFlgPuntoRaccoltaDocumenti(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				
				// Valore del check flgInibitaAssegnazioneEmail
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_INIBITA_ASS_EMAIL")) {
					res.setFlgInibitaAssegnazioneEmail(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				
				// Valore del check abilitaUoProtEntrata
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_ABIL_PROT_E")) {
					res.setAbilitaUoProtEntrata(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				// Valore del check abilitaUoProtUscita
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_ABIL_PROT_U")) {
					res.setAbilitaUoProtUscita(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				
				// Valore del check abilitaUoProtUscitaFull
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_ABIL_UO_PROT_USCITA_FULL")) {
					res.setAbilitaUoProtUscitaFull(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				// Valore del check abilitaUoAssRiservatezza
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_ABIL_ASS_RISERVATEZZA")) {
					res.setAbilitaUoAssRiservatezza(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				// Valore del check abilitaUoGestMulte
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_GEST_MULTE")) {
					res.setAbilitaUoGestMulte(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				// Valore del check abilitaUoGestContratti
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_GEST_CONTRATTI")) {
					res.setAbilitaUoGestContratti(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				// Valore del check flgUfficioGareAppalti
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_UO_GARE_APPALTI")) {
					res.setFlgUfficioGareAppalti(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}
				
				// Valore del check abilitaUoScansioneMassiva
				if (lDettColonnaAttributoListaBean.getNome().equalsIgnoreCase("FLG_ABIL_SCANSIONE_MASSIVA")) {
					res.setAbilitaUoScansioneMassiva(lDettColonnaAttributoListaBean.getValoreDefault() != null ? new Boolean(lDettColonnaAttributoListaBean.getValoreDefault().equalsIgnoreCase("1")) : false);
				}

				

			}
		}

		res.setFlgIgnoreWarning(new Integer(0));

		return res;
	}

	@Override
	public OrganigrammaBean add(OrganigrammaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Controllo di passare una lista vuota non nulla nel caso in cui sia un pounto di protocollo
		controllaListaPuntiProtocollo(bean);

		DmpkDefSecurityIuuoBean input = new DmpkDefSecurityIuuoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setDtdichipain(bean.getDataDichIndicePA() != null && bean.getCheckDichiarataIndicePA() != null && bean.getCheckDichiarataIndicePA() ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDichIndicePA()) : null);
		input.setFlgdichipain(bean.getCheckDichiarataIndicePA() != null && bean.getCheckDichiarataIndicePA() ? new Integer("1") : new Integer("0"));
		input.setDenominazionein(bean.getDenominazioneEstesa());
		input.setCodtipoin(bean.getTipo());
		input.setNrilivelliuosupin(bean.getLivelloCorrente());
		input.setNrolivelloin(bean.getLivello());
		input.setDtistituzionein(bean.getDataIstituita() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataIstituita()) : null);
		input.setDtsoppressionein(bean.getDataSoppressa() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataSoppressa()) : null);
		input.setFlgmodcontattiin("C");

		input.setXmlprececessoriin(getPrecessoreXml(bean));
		input.setXmlsuccessoriin(getSuccessoreXml(bean));

		String xmlContatti = null;
		if (bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			xmlContatti = getXmlContatti(bean);
		}
		input.setXmlcontattiin(xmlContatti);

		// Salvo gli attributi		
		input.setAttributiaddin(getXMLAttributiDinamici(bean));

		// Federico Cacco 05.07.2016
		// Inserisco la lista xml dei punti di protocollo associati
		input.setXmlpuntiprotocolloin(getXMLPuntiProtocolloIn(bean));

		// Id. della UO a cui spostare documenti e fascicoli assegnati alla UO (in genere impostata in caso di cessazione della UO)
		input.setIduodestdocin(bean.getIdUoSpostaDocFasc());
				
		// Id. della UO a cui spostare le mail assegnate alla UO (in genere impostata in caso di cessazione della UO)	
		input.setIduodestmailin(bean.getIdUoSpostaMail());				
		
		// Competenze
		input.setCompetenzein(bean.getCompetenze());
				
		// ciProvUO
		input.setCiprovuoin(bean.getCiProvUO());
				
		// flgAssInvioUP
		input.setFlgassinvioupin(bean.getFlgAssInvioUP());
				
		// flgPropagaAssInvioUP
		if (bean.getFlgPropagaAssInvioUP() != null && bean.getFlgPropagaAssInvioUP().equals(new Boolean(true)))
			input.setFlgpropagaassinvioupin(new Integer(1));
		else
			input.setFlgpropagaassinvioupin(new Integer(0));

								
		DmpkDefSecurityIuuo dmpk_def_security = new DmpkDefSecurityIuuo();
		
		StoreResultBean<DmpkDefSecurityIuuoBean> output = dmpk_def_security.execute(getLocale(), loginBean, input);

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

		bean.setIdUoSvUt(String.valueOf(output.getResultBean().getIduoio()));

		return bean;
	}

	@Override
	public OrganigrammaBean remove(OrganigrammaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityDuoBean input = new DmpkDefSecurityDuoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		if(getExtraparams().get("isFromList") != null && "true".equals(getExtraparams().get("isFromList"))){
			input.setIduoin(StringUtils.isNotBlank(bean.getIdUo()) ? new BigDecimal(bean.getIdUo()) : new BigDecimal(bean.getIdUoSvUt()));
		}else{
			input.setIduoin(StringUtils.isNotBlank(bean.getIdUoSvUt()) ? new BigDecimal(bean.getIdUoSvUt()) : null);;
		}
		input.setFlgignorewarningin(getExtraparams().get("flgIgnoreWarning") != null &&
				"1".equals(getExtraparams().get("flgIgnoreWarning")) ? new Integer(1) : null);

		DmpkDefSecurityDuo dmpkDefSecurityDuo = new DmpkDefSecurityDuo(); 
		StoreResultBean<DmpkDefSecurityDuoBean> output = dmpkDefSecurityDuo.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				logger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			bean.setWarningMsgOut(output.getResultBean().getWarningmsgout());
		}

		return bean;
	}

	private List<PuntoProtocolloCollegatoBean> getListaPuntiProtocollo(DmpkDefSecurityLoaddettuoestesaBean resultBean) {

		List<PuntoProtocolloCollegatoBean> listaPuntiProtocollo = new ArrayList<PuntoProtocolloCollegatoBean>();
		if (resultBean.getXmlpuntiprotocolloout() != null) {
			StringReader sr = new StringReader(resultBean.getXmlpuntiprotocolloout());
			Lista lista;
			try {
				lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						PuntoProtocolloCollegatoBean puntoProtocolloBean = new PuntoProtocolloCollegatoBean();
						puntoProtocolloBean.setIdUo(v.get(1)); // colonna 2 dell'xml
						puntoProtocolloBean.setCodRapido(v.get(2)); // colonna 3 dell'xml
						puntoProtocolloBean.setDescrizione(v.get(3)); // colonna 4 dell'xml
						puntoProtocolloBean.setDescrizioneEstesa(v.get(4)); // colonna 5 dell'xml
						puntoProtocolloBean.setOrganigramma("UO" + puntoProtocolloBean.getIdUo());
						try {
							puntoProtocolloBean
							.setDataInizioValidazione((StringUtils.isNotBlank(v.get(5))) ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(5)) : null); // colonna
							// 6
							// dell'xml
							puntoProtocolloBean.setDataFineValidazione((StringUtils.isNotBlank(v.get(6))) ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6))
									: null); // colonna 7 dell'xml
						} catch (ParseException e) {
							logger.error("Errore nella lettura delle date di inizio o fine validazione", e);
						}
						listaPuntiProtocollo.add(puntoProtocolloBean);
					}
				}
			} catch (JAXBException e) {
				logger.error("Errore nell'unmarshaller", e);
			}
		}
		return listaPuntiProtocollo;
	}

	private String getXMLAttributiDinamici(OrganigrammaBean bean) throws Exception {

		// Salvo gli attributi custom
		AttributiDinamiciOrganigrammaXmlBean lAttributiDinamiciOrganigrammaXmlBean = new AttributiDinamiciOrganigrammaXmlBean();

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();

		if (bean.getFlgInibitaAssegnazione() != null && bean.getFlgInibitaAssegnazione().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlagInibitaAssegnazione("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlagInibitaAssegnazione("0");

		if (bean.getFlgInibitoInvioCC() != null && bean.getFlgInibitoInvioCC().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlagInibitoInvioCC("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlagInibitoInvioCC("0");

		if (bean.getFlgPuntoProtocollo() != null && bean.getFlgPuntoProtocollo().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlagPuntoProtocollo("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlagPuntoProtocollo("0");

		if(bean.getPresenteAttoDefOrganigramma() != null && "Si".equals(bean.getPresenteAttoDefOrganigramma()))
			lAttributiDinamiciOrganigrammaXmlBean.setPresenteAttoDefOrganigramma("SI");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setPresenteAttoDefOrganigramma("NO");

		if (bean.getInibitaPreimpDestProtEntrata() != null && bean.getInibitaPreimpDestProtEntrata().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setInibitaPreimpDestProtEntrata("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setInibitaPreimpDestProtEntrata("0");
		
		if (bean.getFlgPuntoRaccoltaEmail() != null && bean.getFlgPuntoRaccoltaEmail().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlgPuntoRaccoltaEmail("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlgPuntoRaccoltaEmail("0");

		if (bean.getFlgPuntoRaccoltaDocumenti() != null && bean.getFlgPuntoRaccoltaDocumenti().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlgPuntoRaccoltaDocumenti("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlgPuntoRaccoltaDocumenti("0");
		
		if (bean.getFlgInibitaAssegnazioneEmail() != null && bean.getFlgInibitaAssegnazioneEmail().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlgInibitaAssegnazioneEmail("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlgInibitaAssegnazioneEmail("0");
		
		// Valore del check abilitaUoProtEntrata
		if (bean.getAbilitaUoProtEntrata() != null && bean.getAbilitaUoProtEntrata().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoProtEntrata("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoProtEntrata("0");

		// Valore del check abilitaUoProtUscita
		if (bean.getAbilitaUoProtUscita() != null && bean.getAbilitaUoProtUscita().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoProtUscita("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoProtUscita("0");
		
				
		// Valore del check abilitaUoProtUscitaFull
		if (bean.getAbilitaUoProtUscitaFull() != null && bean.getAbilitaUoProtUscitaFull().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoProtUscitaFull("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoProtUscitaFull("0");
				
		// Valore del check abilitaUoAssRiservatezza
		if (bean.getAbilitaUoAssRiservatezza() != null && bean.getAbilitaUoAssRiservatezza().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoAssRiservatezza("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoAssRiservatezza("0");

		// Valore del check abilitaUoGestMulte
		if (bean.getAbilitaUoGestMulte() != null && bean.getAbilitaUoGestMulte().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoGestMulte("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoGestMulte("0");

		// Valore del check abilitaUoGestContratti
		if (bean.getAbilitaUoGestContratti() != null && bean.getAbilitaUoGestContratti().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoGestContratti("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoGestContratti("0");

				
		// Valore del check flgUfficioGareAppalti
		if (bean.getFlgUfficioGareAppalti() != null && bean.getFlgUfficioGareAppalti().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setFlgUfficioGareAppalti("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setFlgUfficioGareAppalti("0");
		
		// Valore del check abilitaUoScansioneMassiva
		if (bean.getAbilitaUoScansioneMassiva() != null && bean.getAbilitaUoScansioneMassiva().equals(new Boolean(true)))
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoScansioneMassiva("1");
		else
			lAttributiDinamiciOrganigrammaXmlBean.setAbilitaUoScansioneMassiva("0");
		
				
		lAttributiDinamiciOrganigrammaXmlBean.setDenominazioneBreveUO(bean.getDenominazioneBreve() != null ? bean.getDenominazioneBreve() : "");
		
		if (bean.getListaCentriCosto()!=null /*&& !bean.getListaCentriCosto().isEmpty()*/) {		
			lAttributiDinamiciOrganigrammaXmlBean.setDenominazioneCdCCdR(bean.getListaCentriCosto());
		}
				
		if (bean.getIdAssessoreRif()!=null && bean.getIdAssessoreRif().size()>0) {
			List<SimpleValueBean> listaAssessori = new ArrayList<SimpleValueBean>();
			for (String lIdAssessore : bean.getIdAssessoreRif()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lIdAssessore);
				listaAssessori.add(lSimpleValueBean);
			}
			lAttributiDinamiciOrganigrammaXmlBean.setIdAssessoreRif(listaAssessori);
		}

		
		// ***************************************************
		// Ufficio liquidatore rif.
		// ***************************************************
		// ID
		lAttributiDinamiciOrganigrammaXmlBean.setIdUfficioLiquidatore(bean.getIdUoUfficioLiquidatore() != null ? bean.getIdUoUfficioLiquidatore() : "");
		
		// TYPE NODO
		lAttributiDinamiciOrganigrammaXmlBean.setTypeNodoUfficioLiquidatore(bean.getTypeNodoUfficioLiquidatore() != null ? bean.getTypeNodoUfficioLiquidatore() : "");
		
		// COD RAPIDO
		lAttributiDinamiciOrganigrammaXmlBean.setCodRapidoUfficioLiquidatore(bean.getCodRapidoUfficioLiquidatore() != null ? bean.getCodRapidoUfficioLiquidatore() : "");
		
		// DESCRIZIONE
		lAttributiDinamiciOrganigrammaXmlBean.setDescrizioneUfficioLiquidatore(bean.getDescrizioneUfficioLiquidatore() != null ? bean.getDescrizioneUfficioLiquidatore() : "");
		
		
		
		boolean isGestionePuntoVendita = ParametriDBUtil.getParametroDBAsBoolean(getSession(),"ATTIVA_OFFERTE_PROMO_ORGANIGRAMMA");

		if(isGestionePuntoVendita){
			// Lista tipologie offerte punti vendita					
			if (bean.getListaTipologieOffertePuntoVendita() != null) {

				if (bean.getListaTipologieOffertePuntoVendita().size()>0) {
					List<TipologiaOffertaPuntoVenditaXmlBean> listaTipologieOffertePuntoVendita= new ArrayList<TipologiaOffertaPuntoVenditaXmlBean>();
					for (TipologiaOffertaPuntoVenditaBean lTipologiaOffertaPuntoVenditaBean : bean.getListaTipologieOffertePuntoVendita()) {
						TipologiaOffertaPuntoVenditaXmlBean lTipologiaOffertaPuntoVenditaXmlBean = new TipologiaOffertaPuntoVenditaXmlBean();

						// Codice offerta
						lTipologiaOffertaPuntoVenditaXmlBean.setCodiceOfferta(lTipologiaOffertaPuntoVenditaBean.getCodiceOfferta());

						// Inizio validita'
						lTipologiaOffertaPuntoVenditaXmlBean.setDataInizioValiditaOfferta(lTipologiaOffertaPuntoVenditaBean.getDataInizioValiditaOfferta());

						// Fine validita'
						lTipologiaOffertaPuntoVenditaXmlBean.setDataFineValiditaOfferta(lTipologiaOffertaPuntoVenditaBean.getDataFineValiditaOfferta());


						listaTipologieOffertePuntoVendita.add(lTipologiaOffertaPuntoVenditaXmlBean);
					}
					lAttributiDinamiciOrganigrammaXmlBean.setListaTipologieOffertePuntoVendita(listaTipologieOffertePuntoVendita);
				}
			}


			// Lista tipologie promozioni punti vendita
			if (bean.getListaTipologiePromozioniPuntoVendita() != null ) {
				if(bean.getListaTipologiePromozioniPuntoVendita().size()>0 ){
					List<TipologiaPromozionePuntoVenditaXmlBean> listaTipologiePromozioniPuntoVendita= new ArrayList<TipologiaPromozionePuntoVenditaXmlBean>();				
					for (TipologiaPromozionePuntoVenditaBean lTipologiaPromozionePuntoVenditaBean : bean.getListaTipologiePromozioniPuntoVendita()) {			
						TipologiaPromozionePuntoVenditaXmlBean lTipologiaPromozionePuntoVenditaXmlBean = new TipologiaPromozionePuntoVenditaXmlBean();

						// Codice promozione
						lTipologiaPromozionePuntoVenditaXmlBean.setCodicePromozione(lTipologiaPromozionePuntoVenditaBean.getCodicePromozione());

						// Inizio validita'
						lTipologiaPromozionePuntoVenditaXmlBean.setDataInizioValiditaPromozione(lTipologiaPromozionePuntoVenditaBean.getDataInizioValiditaPromozione());

						// Fine validita'
						lTipologiaPromozionePuntoVenditaXmlBean.setDataFineValiditaPromozione(lTipologiaPromozionePuntoVenditaBean.getDataFineValiditaPromozione());
						listaTipologiePromozioniPuntoVendita.add(lTipologiaPromozionePuntoVenditaXmlBean);
					}
					lAttributiDinamiciOrganigrammaXmlBean.setListaTipologiePromozioniPuntoVendita(listaTipologiePromozioniPuntoVendita);
				}


			}		

			// Codice Istat Comune punto vendita
			if (bean.getComuneNascitaIstituzione() != null && !bean.getComuneNascitaIstituzione().equalsIgnoreCase("")) {
				lAttributiDinamiciOrganigrammaXmlBean.setComuneNascitaIstituzione(bean.getComuneNascitaIstituzione());
			}		

			// Nome Comune punto vendita
			if (bean.getNomeComuneNascitaIstituzione() != null && !bean.getNomeComuneNascitaIstituzione().equalsIgnoreCase("")) {
				lAttributiDinamiciOrganigrammaXmlBean.setNomeComuneNascitaIstituzione(bean.getNomeComuneNascitaIstituzione());
			}		

			// Valore della label del campo Socio Coop
			if (bean.getLabelSocioCoopDatiClienteForm() != null && !bean.getLabelSocioCoopDatiClienteForm().equalsIgnoreCase("")) {
				lAttributiDinamiciOrganigrammaXmlBean.setLabelSocioCoopDatiClienteForm(bean.getLabelSocioCoopDatiClienteForm());
			}

			// Categoria socio coop
			if (bean.getCategoriaSocioCoop() != null && !bean.getCategoriaSocioCoop().equalsIgnoreCase("")) {
				lAttributiDinamiciOrganigrammaXmlBean.setCategoriaSocioCoop(bean.getCategoriaSocioCoop());
			}
		}

		// Destinatario disclaimer message
		if (bean.getDestinatarioDisclaimerMessage() != null && !bean.getDestinatarioDisclaimerMessage().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setDestinatarioDisclaimerMessage(bean.getDestinatarioDisclaimerMessage());
		}

		// Mail mittente (ordinaria)
		if (bean.getMailMittenteOrdinaria() != null && !bean.getMailMittenteOrdinaria().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setMailMittenteOrdinaria(bean.getMailMittenteOrdinaria());
		}

		// Mail mittente PEC
		if (bean.getMailMittentePec() != null && !bean.getMailMittentePec().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setMailMittentePec(bean.getMailMittentePec());
		}

		// Primo Sollecito GG
		if (bean.getGiorniAttesaPrimoSollecito() != null && !bean.getGiorniAttesaPrimoSollecito().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setGiorniAttesaPrimoSollecito(bean.getGiorniAttesaPrimoSollecito());
		}

		// In posta dopo GG
		if (bean.getGiorniAttesaInvioPosta() != null && !bean.getGiorniAttesaInvioPosta().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setGiorniAttesaInvioPosta(bean.getGiorniAttesaInvioPosta());
		}

		// Invio mail per utente WEB
		if (bean.getInvioMailPerUtenteWeb() != null && !bean.getInvioMailPerUtenteWeb().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setInvioMailPerUtenteWeb(bean.getInvioMailPerUtenteWeb());
		}

		// Flag Attivo Export
		if (bean.getFlgExport() != null && !bean.getFlgExport().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setFlgExport(bean.getFlgExport());
		}

		// Logo cluster nestle
		if (bean.getLogoClusterNestle() != null && !bean.getLogoClusterNestle().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setLogoClusterNestle(bean.getLogoClusterNestle());
		}

		// Tipo documento cluster nestle
		if (bean.getTipoDocClusterNestle() != null && !bean.getTipoDocClusterNestle().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setTipoDocClusterNestle(bean.getTipoDocClusterNestle());
		}

		// Layout doc. composition nestle
		if (bean.getLayoutDocClusterNestle() != null && !bean.getLayoutDocClusterNestle().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setLayoutDocClusterNestle(bean.getLayoutDocClusterNestle());
		}

		// Prefisso lotti documenti
		if (bean.getPrefLottiClusterNestle() != null && !bean.getPrefLottiClusterNestle().equalsIgnoreCase("")) {
			lAttributiDinamiciOrganigrammaXmlBean.setPrefLottiClusterNestle(bean.getPrefLottiClusterNestle());
		}


		String xmlOut = lXmlUtilitySerializer.bindXml(lAttributiDinamiciOrganigrammaXmlBean);
		return xmlOut;
	}

	protected String getXMLPuntiProtocolloIn(OrganigrammaBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		if (bean.getUoPuntoProtocolloCollegate() != null && (bean.getFlgPuntoProtocollo() == null || !bean.getFlgPuntoProtocollo())) {
			List<Object> appList = ListUtility.removeDuplicateObject(bean.getUoPuntoProtocolloCollegate());
			return lXmlUtilitySerializer.bindXmlList(appList);
		} else {
			return lXmlUtilitySerializer.bindXmlList(new ArrayList<PuntoProtocolloCollegatoBean>());
		}
	}

	@Override
	public OrganigrammaBean update(OrganigrammaBean bean, OrganigrammaBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Controllo di passare una lista vuota non nulla nel caso in cui sia un pounto di protocollo
		controllaListaPuntiProtocollo(bean);

		DmpkDefSecurityIuuoBean input = new DmpkDefSecurityIuuoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setIduoio(new BigDecimal(bean.getIdUoSvUt()));
		input.setDtdichipain(bean.getDataDichIndicePA() != null && bean.getCheckDichiarataIndicePA() != null && bean.getCheckDichiarataIndicePA() ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDichIndicePA()) : null);
		input.setFlgdichipain(bean.getCheckDichiarataIndicePA() != null && bean.getCheckDichiarataIndicePA() ? new Integer("1") : new Integer("0"));
		input.setDenominazionein(bean.getDenominazioneEstesa());
		input.setCodtipoin(bean.getTipo());
		input.setNrolivelloin(bean.getLivello());
		input.setNrilivelliuosupin(bean.getLivelloCorrente());
		input.setDtistituzionein(bean.getDataIstituita() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataIstituita()) : null);
		input.setDtsoppressionein(bean.getDataSoppressa() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataSoppressa()) : null);
		input.setFlgmodcontattiin("C");
		input.setTsvariazionedatiin(bean.getVariazioneDatiIn() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getVariazioneDatiIn()) : null);
		input.setFlgstoricizzadatiin(bean.getStoricizzaDatiVariati() != null && bean.getStoricizzaDatiVariati() ? 1 : 0);
		input.setMotivivariazionein(bean.getMotiviVariazioneIn());
		
		input.setXmlprececessoriin(getPrecessoreXml(bean));
		input.setXmlsuccessoriin(getSuccessoreXml(bean));

		String xmlContatti = null;
		if (bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			xmlContatti = getXmlContatti(bean);
		}
		input.setXmlcontattiin(xmlContatti);

		// Salvo gli attributi
		input.setAttributiaddin(getXMLAttributiDinamici(bean));

		// Federico Cacco 05.07.2016
		// Inserisco la lista xml dei punti di protocollo associati
		input.setXmlpuntiprotocolloin(getXMLPuntiProtocolloIn(bean));

		// Id. della UO a cui spostare documenti e fascicoli assegnati alla UO (in genere impostata in caso di cessazione della UO)
		input.setIduodestdocin(bean.getIdUoSpostaDocFasc());
		
		// Id. della UO a cui spostare le mail assegnate alla UO (in genere impostata in caso di cessazione della UO)	
		input.setIduodestmailin(bean.getIdUoSpostaMail());
		
		// Competenze
		input.setCompetenzein(bean.getCompetenze());

		// ciProvUO
		input.setCiprovuoin(bean.getCiProvUO());
		
		// flgAssInvioUP
		input.setFlgassinvioupin(bean.getFlgAssInvioUP());
		
		// flgPropagaAssInvioUP
		if (bean.getFlgPropagaAssInvioUP() != null && bean.getFlgPropagaAssInvioUP().equals(new Boolean(true)))
			input.setFlgpropagaassinvioupin(new Integer(1));
		else
			input.setFlgpropagaassinvioupin(new Integer(0));

		DmpkDefSecurityIuuo dmpk_def_security = new DmpkDefSecurityIuuo();

		StoreResultBean<DmpkDefSecurityIuuoBean> output = dmpk_def_security.execute(getLocale(), loginBean, input);

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

	public AssociaScollegaPuntoProtocolloBean associaPuntiProtocollo(AssociaScollegaPuntoProtocolloBean puntiProtocolloDaAssociareBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityAssociapuntiprotocolloBean input = new DmpkDefSecurityAssociapuntiprotocolloBean();

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		// Inserisco la lista delle uo a cui associare i punti di protocollo
		if (puntiProtocolloDaAssociareBean.getIdUoList() != null) {
			StringWriter sw = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(getListaFormList(puntiProtocolloDaAssociareBean.getIdUoList()), sw);
			input.setXmluoin(sw.toString());
		} else {
			logger.debug("La lista delle UO a cui associare i punti di protocollo è vuota");
			throw new Exception("La lista delle uo a cui associare i punti di protocollo è vuota");
		}

		// Inserisco la lista dei punti di protocollo da associare alle uo
		if (puntiProtocolloDaAssociareBean.getIdUoPuntiProtocolloList() != null) {
			StringWriter sw = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(getListaFormList(puntiProtocolloDaAssociareBean.getIdUoPuntiProtocolloList()), sw);
			input.setXmlpuntiprotin(sw.toString());
		} else {
			logger.debug("La lista dei punti di protocollo da associare alle UO è vuota");
			throw new Exception("La lista dei punti di protocollo da associare alle UO è vuota");
		}

		DmpkDefSecurityAssociapuntiprotocollo dmpk_def_security = new DmpkDefSecurityAssociapuntiprotocollo();
		StoreResultBean<DmpkDefSecurityAssociapuntiprotocolloBean> output = dmpk_def_security.execute(getLocale(), loginBean, input);

		HashMap<String, String> errorMessages = new HashMap<String, String>();
		;
		if (output.isInError()) {
			puntiProtocolloDaAssociareBean.setStoreInError(true);
			String storeErrorMesssage = StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "";
			puntiProtocolloDaAssociareBean.setStoreErrorMessage(storeErrorMesssage);
		} else if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(0), v.get(4));
				}

			}
		}
		puntiProtocolloDaAssociareBean.setErrorMessages(errorMessages);
		return puntiProtocolloDaAssociareBean;
	}

	public AssociaScollegaPuntoProtocolloBean scollegaPuntiProtocollo(AssociaScollegaPuntoProtocolloBean puntiProtocolloDaScollegareBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityScollegapuntiprotocolloBean input = new DmpkDefSecurityScollegapuntiprotocolloBean();

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		// Inserisco la lista delle uo a cui associare i punti di protocollo
		if (puntiProtocolloDaScollegareBean.getIdUoList() != null) {
			StringWriter sw = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(getListaFormList(puntiProtocolloDaScollegareBean.getIdUoList()), sw);
			input.setXmluoin(sw.toString());
		} else {
			logger.debug("La lista delle UO a cui scollegare i punti di protocollo è vuota");
			throw new Exception("La lista delle uo a cui scollegare i punti di protocollo è vuota");
		}

		// Inserisco la lista dei punti di protocollo da associare alle uo
		if (puntiProtocolloDaScollegareBean.getIdUoPuntiProtocolloList() != null) {
			StringWriter sw = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(getListaFormList(puntiProtocolloDaScollegareBean.getIdUoPuntiProtocolloList()), sw);
			input.setXmlpuntiprotin(sw.toString());
		} else {
			logger.debug("La lista dei punti di protocollo da scollegare alle UO è vuota");
			throw new Exception("La lista dei punti di protocollo da scollegare alle UO è vuota");
		}

		DmpkDefSecurityScollegapuntiprotocollo dmpk_def_security = new DmpkDefSecurityScollegapuntiprotocollo();
		StoreResultBean<DmpkDefSecurityScollegapuntiprotocolloBean> output = dmpk_def_security.execute(getLocale(), loginBean, input);

		HashMap<String, String> errorMessages = null;
		if (output.isInError()) {
			puntiProtocolloDaScollegareBean.setStoreInError(true);
			String storeErrorMesssage = StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "";
			puntiProtocolloDaScollegareBean.setStoreErrorMessage(storeErrorMesssage);
		} else if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(0), v.get(4));
				}

			}
		}
		puntiProtocolloDaScollegareBean.setErrorMessages(errorMessages);
		return puntiProtocolloDaScollegareBean;

	}

	private void controllaListaPuntiProtocollo(OrganigrammaBean bean) {
		if (bean.getFlgPuntoProtocollo() != null && bean.getFlgPuntoProtocollo().booleanValue()) {
			List<PuntoProtocolloCollegatoBean> lista = new ArrayList<PuntoProtocolloCollegatoBean>();
			bean.setUoPuntoProtocolloCollegate(lista);
		}
	}

	private Lista getListaFormList(List<String> listaStringhe) {
		Lista lista = new Lista();
		if (listaStringhe != null) {
			for (String s : listaStringhe) {
				Riga riga = new Riga();
				Colonna col1 = new Colonna();
				col1.setNro(new BigInteger("1"));
				col1.setContent(s);
				riga.getColonna().add(col1);
				lista.getRiga().add(riga);
			}
		}
		return lista;
	}

	// Legge i valori dell'attributo COD_TIPO_OFFERTA_PV
	private List<TipologiaOffertaPuntoVenditaBean> leggiListaTipologieOffertePuntiVendita(String rowId) throws Exception {

		List<TipologiaOffertaPuntoVenditaBean>    listaTipologieOffertePuntoVendita  = new ArrayList<TipologiaOffertaPuntoVenditaBean>();

		LoadAttrDinamicoListaOutputBean lLoadAttrDinamicoListaOutputBean = new LoadAttrDinamicoListaOutputBean();

		// Per ogni attributo chiamo il servizo che mi restituisce la
		// lista dei valori
		lLoadAttrDinamicoListaOutputBean = leggiListaValoriAttributi("DMT_STRUTTURA_ORG", "TIPO_OFFERTA_PV", rowId);
		List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
		valoriLista = lLoadAttrDinamicoListaOutputBean.getValoriLista();

		// Leggo i valori degli attributi
		for (HashMap<String, String> valori : valoriLista) {

			TipologiaOffertaPuntoVenditaBean lTipologiaOffertaPuntoVenditaBean = new TipologiaOffertaPuntoVenditaBean();

			for (String key : valori.keySet()) {
				String valoreAttributo = valori.get(key);

				// Se e' COD_TIPO_OFFERTA_PV
				if (key.equalsIgnoreCase("COD_TIPO_OFFERTA_PV")) {
					if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
						lTipologiaOffertaPuntoVenditaBean.setCodiceOfferta(valoreAttributo);
					}
				}

				// Se e' DT_INIZIO_VLD_OFFERTA_PV
				if (key.equalsIgnoreCase("DT_INIZIO_VLD_TIPO_OFFERTA_PV")) {
					if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
						lTipologiaOffertaPuntoVenditaBean.setDataInizioValiditaOfferta(new SimpleDateFormat(FMT_STD_DATA).parse(valoreAttributo));
					}
				}

				// Se e' DT_FINE_VLD_OFFERTA_PV
				if (key.equalsIgnoreCase("DT_FINE_VLD_TIPO_OFFERTA_PV")) {
					if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
						lTipologiaOffertaPuntoVenditaBean.setDataFineValiditaOfferta(new SimpleDateFormat(FMT_STD_DATA).parse(valoreAttributo));
					}
				}
			}
			listaTipologieOffertePuntoVendita.add(lTipologiaOffertaPuntoVenditaBean);
		}
		return listaTipologieOffertePuntoVendita;
	}


	private LoadAttrDinamicoListaOutputBean leggiListaValoriAttributi(String nomeTabella, String nomeAttrLista, String rowId) throws Exception {

		LoadAttrDinamicoListaOutputBean result = new LoadAttrDinamicoListaOutputBean();

		if (nomeTabella != null && !nomeTabella.equalsIgnoreCase("") && nomeAttrLista != null && !nomeAttrLista.equalsIgnoreCase("") && rowId != null
				&& !rowId.equalsIgnoreCase("")) {
			// Leggo la lista degli attributi
			LoadAttrDinamicoListaInputBean inputDataSourceBean = new LoadAttrDinamicoListaInputBean();

			inputDataSourceBean.setNomeAttrLista(nomeAttrLista);
			inputDataSourceBean.setNomeTabella(nomeTabella);
			inputDataSourceBean.setRowId(rowId);

			LoadAttrDinamicoListaDatasource lDataSource = new LoadAttrDinamicoListaDatasource();
			try {
				lDataSource.setSession(getSession());
				result = lDataSource.call(inputDataSourceBean);
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
		return result;
	}

	protected String getXmlContatti(OrganigrammaBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmlContatti;
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
					lContattoSoggettoXmlBean.setFlgCasellaIstituz(lContattoSoggettoBean.getFlgCasellaIstituz() != null
							&& lContattoSoggettoBean.getFlgCasellaIstituz() ? "1" : "0");
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
		xmlContatti = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlContatti;
	}

	private String getPrecessoreXml(OrganigrammaBean bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException {
		
		List<SelezionaUOBean> screenListDerivataDa = new ArrayList<>();
		if (bean.getUoDerivataPer() != null && !"".equals(bean.getUoDerivataPer())) {
			if(bean.getListaUODerivataDa() != null && bean.getListaUODerivataDa().size() > 0) {
				for (SelezionaUOBean riga : bean.getListaUODerivataDa()) {
					if (riga.getIdUo() != null && !"".equals(riga.getIdUo())) {
						screenListDerivataDa.add(riga);
					}
				}
			}
		}		
		
		String result = null;
		List<OrganigrammaSuccessoreXmlBean> listOrganigrammaSuccessoreXmlBean = new ArrayList<OrganigrammaSuccessoreXmlBean>();
		if (screenListDerivataDa != null && !screenListDerivataDa.isEmpty()) {
			for (SelezionaUOBean item : screenListDerivataDa) {

				OrganigrammaSuccessoreXmlBean organigrammaSuccessoreXmlBean = new OrganigrammaSuccessoreXmlBean();
				organigrammaSuccessoreXmlBean.setTipoAzione(bean.getUoDerivataPer());
				organigrammaSuccessoreXmlBean.setIdUo(item.getIdUo());

				listOrganigrammaSuccessoreXmlBean.add(organigrammaSuccessoreXmlBean);
			}
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			result = lXmlUtilitySerializer.bindXmlList(listOrganigrammaSuccessoreXmlBean);
		}
		return result;
	}

	private String getSuccessoreXml(OrganigrammaBean bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException {
		
		List<SelezionaUOBean> screenListDatoLuogoA = new ArrayList<>();
		if (bean.getUoDatoLuogoAPer() != null && !"".equals(bean.getUoDatoLuogoAPer())) {
			if(bean.getListaUODatoLuogoA() != null && bean.getListaUODatoLuogoA().size() > 0) {
				for (SelezionaUOBean riga : bean.getListaUODatoLuogoA()) {
					if (riga.getIdUo() != null && !"".equals(riga.getIdUo())) {
						screenListDatoLuogoA.add(riga);
					}
				}
			}
		}
		
		String result = null;
		List<OrganigrammaPredecessoreXmlBean> listOrganigrammaPredecessoreXmlBean = new ArrayList<OrganigrammaPredecessoreXmlBean>();
		if (screenListDatoLuogoA != null && !screenListDatoLuogoA.isEmpty()) {
			for (SelezionaUOBean item : screenListDatoLuogoA) {

				OrganigrammaPredecessoreXmlBean organigrammaPredecessoreXmlBean = new OrganigrammaPredecessoreXmlBean();
				organigrammaPredecessoreXmlBean.setTipoAzione(bean.getUoDatoLuogoAPer());
				organigrammaPredecessoreXmlBean.setIdUo(item.getIdUo());

				listOrganigrammaPredecessoreXmlBean.add(organigrammaPredecessoreXmlBean);
			}
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			result = lXmlUtilitySerializer.bindXmlList(listOrganigrammaPredecessoreXmlBean);
		}
		return result;
	}
	
	private FindOrgStructureObjectBean createFindRepositoryObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean,
			String filtroFullText, String[] checkAttributes) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException {
		
		Integer flgIncludUtenti = StringUtils.isNotBlank(getExtraparams().get("flgIncludiUtenti")) ? Integer.valueOf(getExtraparams().get("flgIncludiUtenti")) : null;
		String flgSoloAttive = getExtraparams().get("flgSoloAttive");
		String tipoRelUtentiConUo = getExtraparams().get("tipoRelUtentiConUo");
		String finalita = getExtraparams().get("finalita");
		String idUd = getExtraparams().get("idUd");
		String idEmail = StringUtils.isNotBlank(getExtraparams().get("idEmail")) ? getExtraparams().get("idEmail") : null;
		
		
		Integer searchAllTerms = null;
		// String[] flgObjectTypes = {"SV","UO","UT"}; da assegnatari SV UO altrimenti null
		String[] flgObjectTypes = null;
		
		String flgObjType = null;
		Integer idFolder = null;
		String idOrganigramma = null;
		// in modalità  di esplora il valore di default è 0
		String includiSottoCartelle = "0";
		String tsRif = null;
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1);
		Integer numPagina = null;
		Integer numRighePagina = null;
		Integer online = null;
		String colsToReturn = "1,2,3,4,5,6,7,8,9,10,11,12,14,16,31,19,20,21,22,23,25,26,27,28,29,30,35,36,37,38,39,40,41,42,43,44,45,46,47,50,51,52,55,56,57,58,61,62,63,64,65,66,67,68,69";
		String percorsoRicerca = null;
		String strInDenominazione = null;
		String email = null;
		String idUOPP = null;
		String flgPuntoRaccoltaEmail = null;
		String competenze = null;
		
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("idFolder".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = new Integer(String.valueOf(crit.getValue()));
					}
				} else if ("idOrganigramma".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idOrganigramma = String.valueOf(crit.getValue());
					}
				} else if ("finalita".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						finalita = String.valueOf(crit.getValue());
					}
				} else if ("flgIncludiUtenti".equals(crit.getFieldName())) {
					if (crit.getValue() != null && flgIncludUtenti == null) {
						flgIncludUtenti = new Integer(String.valueOf(crit.getValue()));						
					}
				} else if ("tsRif".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						tsRif = String.valueOf(crit.getValue());
					}
				} else if ("idUOPP".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idUOPP = getTextFilterValue(crit);
						idUOPP = idUOPP != null && idUOPP.startsWith("UO") ? idUOPP.substring(2) : null; // va passato SOLO id numerico della UO
					}
				} else if ("flgObjType".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						flgObjType = getTextFilterValue(crit);
					}
				} else if ("searchFulltext".equals(crit.getFieldName())) {
					// se sono entrato qui sono in modalitÃ  di ricerca con i filtri quindi imposto il valore di default a 1
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
				} else if ("strInDenominazione".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							strInDenominazione = String.valueOf(crit.getValue());
						}
					}
				} else if ("email".equals(crit.getFieldName())) {
					email = getTextFilterValue(crit);
				} else if ("flgPuntoRaccoltaEmail".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgPuntoRaccoltaEmail = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}				
				} else if ("competenze".equals(crit.getFieldName())){
					if (crit.getValue() != null) {
						competenze = getTextFilterValue(crit);
					}
				}
			}
		}
		
		if (flgIncludUtenti != null && flgIncludUtenti.equals(new Integer(0))) {
			flgObjectTypes = new String[1];
			flgObjectTypes[0] = "UO";
		} else if (flgIncludUtenti != null && flgIncludUtenti.equals(new Integer(1))) {
			if(StringUtils.isNotBlank(flgObjType)) {
				flgObjectTypes = new String[1];
				flgObjectTypes[0] = flgObjType;
			} else {
				flgObjectTypes = new String[2];
				flgObjectTypes[0] = "SV";
				flgObjectTypes[1] = "UO";
			}
		} else if (flgIncludUtenti != null && flgIncludUtenti.equals(new Integer(2))) {
			flgObjectTypes = new String[1];
			flgObjectTypes[0] = "SV";
		}
		
		OrganigrammaSearchSezioneCacheBean lOrganigrammaSearchSezioneCacheBean = new OrganigrammaSearchSezioneCacheBean();
		lOrganigrammaSearchSezioneCacheBean.setIdOrganigramma(idOrganigramma);
		lOrganigrammaSearchSezioneCacheBean.setEmail(email);
		lOrganigrammaSearchSezioneCacheBean.setFlgSoloUOAttive(flgSoloAttive);
		lOrganigrammaSearchSezioneCacheBean.setTipoRelUtentiConUO(tipoRelUtentiConUo);
		lOrganigrammaSearchSezioneCacheBean.setFlgSoloRelUserUOAttive(flgSoloAttive);
		lOrganigrammaSearchSezioneCacheBean.setDescrizioneRFT(strInDenominazione);
		lOrganigrammaSearchSezioneCacheBean.setIdUOPP(idUOPP);
		lOrganigrammaSearchSezioneCacheBean.setFlgPuntoRaccoltaEmail(flgPuntoRaccoltaEmail);
		lOrganigrammaSearchSezioneCacheBean.setCompetenze(competenze);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(lOrganigrammaSearchSezioneCacheBean);
		FindOrgStructureObjectBean lFindOrgStructureObjectBean = new FindOrgStructureObjectBean();
		lFindOrgStructureObjectBean.setFiltroFullText(filtroFullText);
		lFindOrgStructureObjectBean.setCheckAttributes(checkAttributes);
		lFindOrgStructureObjectBean.setSearchAllTerms(searchAllTerms);
		lFindOrgStructureObjectBean.setFlgObjectTypes(flgObjectTypes);
		lFindOrgStructureObjectBean.setIdUoSearchIn(idFolder);
		lFindOrgStructureObjectBean.setFlgSubUoSearchIn(includiSottoCartelle);
		lFindOrgStructureObjectBean.setTsRiferimento(tsRif);
		lFindOrgStructureObjectBean.setAdvancedFilters(advancedFilters);
		lFindOrgStructureObjectBean.setCustomFilters(customFilters);
		lFindOrgStructureObjectBean.setColsOrderBy(colsOrderBy);
		lFindOrgStructureObjectBean.setFlgDescOrderBy(flgDescOrderBy);
		lFindOrgStructureObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
		lFindOrgStructureObjectBean.setNumPagina(numPagina);
		lFindOrgStructureObjectBean.setNumRighePagina(numRighePagina);
		lFindOrgStructureObjectBean.setOnline(online);
		lFindOrgStructureObjectBean.setColsToReturn(colsToReturn);
		lFindOrgStructureObjectBean.setPercorsoRicerca(percorsoRicerca);
		lFindOrgStructureObjectBean.setFinalita(finalita);
		lFindOrgStructureObjectBean.setIdUd(idUd);
		if (StringUtils.isNotBlank(idEmail)) {
			lFindOrgStructureObjectBean.setTyobjxfinalitain("E");
			lFindOrgStructureObjectBean.setIdobjxfinalitain(idEmail);
		} else if (StringUtils.isNotBlank(idUd)) {
			lFindOrgStructureObjectBean.setTyobjxfinalitain("U");
			lFindOrgStructureObjectBean.setIdobjxfinalitain(idUd);
		}

		if (StringUtils.isNotBlank(idOrganigramma)) {
			lFindOrgStructureObjectBean.setType("ID_ORGANIGRAMMA");
			String[] lValues = { idOrganigramma };
			lFindOrgStructureObjectBean.setValues(lValues);
		} else {
			String idDominio = null;
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
			String[] lValues = { idDominio };
			lFindOrgStructureObjectBean.setType("ID_SP_AOO");
			lFindOrgStructureObjectBean.setValues(lValues);
		}
		lFindOrgStructureObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());
		
		return lFindOrgStructureObjectBean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		FindOrgStructureObjectBean lFindOrgStructureObjectBean = createFindRepositoryObjectBean(criteria, loginBean, filtroFullText, checkAttributes);
		lFindOrgStructureObjectBean.setOverflowlimitin(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findorgstructureobject(getLocale(), loginBean, lFindOrgStructureObjectBean).getList();
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

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), OrganigrammaXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), OrganigrammaXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		FindOrgStructureObjectBean lFindOrgStructureObjectBean = createFindRepositoryObjectBean(criteria, loginBean, filtroFullText, checkAttributes);
		lFindOrgStructureObjectBean.setOverflowlimitin(-1);
		lFindOrgStructureObjectBean.setColsToReturn("1");

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findorgstructureobject(getLocale(), loginBean, lFindOrgStructureObjectBean).getList();
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


		return retValue;
	}
	
	// Legge i valori dell'attributo ID_USER_ASSESSORE
	private List<String> leggiListaAssessoriRif(String rowId) throws Exception {
				
				List<String>    listaAssessoriRif  = new ArrayList<String>();
				
				LoadAttrDinamicoListaOutputBean lLoadAttrDinamicoListaOutputBean = new LoadAttrDinamicoListaOutputBean();

				// Per ogni attributo chiamo il servizo che mi restituisce la
				// lista dei valori
				lLoadAttrDinamicoListaOutputBean = leggiListaValoriAttributi("DMT_STRUTTURA_ORG", "ID_USER_ASSESSORE", rowId);
				List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
				valoriLista = lLoadAttrDinamicoListaOutputBean.getValoriLista();
				
				// Leggo i valori degli attributi
				for (HashMap<String, String> valori : valoriLista) {
					for (String key : valori.keySet()) {
						String valoreAttributo = valori.get(key);
						listaAssessoriRif.add(valoreAttributo);
					}
				}
				return listaAssessoriRif;
	}
	
	// Legge i valori dell'attributo CDC_STRUTTURA
	private List<CentroCostoBean> leggiListaCentriCosto(String rowId) throws Exception {
		
		List<CentroCostoBean>    listaCentriCosto  = new ArrayList<CentroCostoBean>();
		
		LoadAttrDinamicoListaOutputBean lLoadAttrDinamicoListaOutputBean = new LoadAttrDinamicoListaOutputBean();
		
		// Per ogni attributo chiamo il servizo che mi restituisce la
		// lista dei valori
		lLoadAttrDinamicoListaOutputBean = leggiListaValoriAttributi("DMT_STRUTTURA_ORG", "CDC_STRUTTURA", rowId);
		List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
		valoriLista = lLoadAttrDinamicoListaOutputBean.getValoriLista();
		
		// Leggo i valori degli attributi
		for (HashMap<String, String> valori : valoriLista) {
			for (String key : valori.keySet()) {
				String valoreAttributo = valori.get(key);
				CentroCostoBean lCentroCostoBean = new CentroCostoBean();
				lCentroCostoBean.setDenominazioneCdCCdR(valoreAttributo);
				listaCentriCosto.add(lCentroCostoBean);
			}
		}
		return listaCentriCosto;
	}

}