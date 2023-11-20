/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGrantprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityRevokeprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesDdoctypeBean;
import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesIudoctypeBean;
import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesLoaddettdoctypeBean;
import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesTrovadoctypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.AttrAddXEvtDelTipoBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.XMLAttrAddXEvtDelTipoBean;
import it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean.ListaTipologieDocBean;
import it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean.TipologieDocumentaliBean;
import it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean.UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean;
import it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean.UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBeanDeserializationHelper;
import it.eng.client.DmpkDefSecurityGrantprivsudefcontesto;
import it.eng.client.DmpkDefSecurityRevokeprivsudefcontesto;
import it.eng.client.DmpkDocTypesDdoctype;
import it.eng.client.DmpkDocTypesIudoctype;
import it.eng.client.DmpkDocTypesLoaddettdoctype;
import it.eng.client.DmpkDocTypesTrovadoctypes;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author cristiano
 *
 */

@Datasource(id = "TipologieDocumentaliDataSource")
public class TipologieDocumentaliDataSource extends AbstractFetchDataSource<TipologieDocumentaliBean> {

	private static final Logger log = Logger.getLogger(TipologieDocumentaliDataSource.class);

	@Override
	public String getNomeEntita() {
		return "tipologiedocumentali";
	}

	@Override
	public PaginatorBean<TipologieDocumentaliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
	
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		List<TipologieDocumentaliBean> data = new ArrayList<TipologieDocumentaliBean>();

		DmpkDocTypesTrovadoctypesBean input = new DmpkDocTypesTrovadoctypesBean();
		input.setCodidconnectiontokenin(token);
		input.setFlgsenzapaginazionein(1);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		if (getExtraparams().get("viewTipologiaDaUO") != null) {
			String idUO = getExtraparams().get("idUo");
			String flgTipDestIn = getExtraparams().get("flgTipologiaDestAbilIn");
			String flgStatoIn = getExtraparams().get("flgStatoAbilitazioneIn");

			input.setIddestabilin(idUO != null && idUO.length() > 0 ? new BigDecimal(idUO) : null);
			input.setFlgtpdestabilin(flgTipDestIn);
			input.setFlgstatoabilin(flgStatoIn != null && flgStatoIn.length() > 0 ? Integer.decode(flgStatoIn) : null);
		}

		if (getExtraparams().get("opzioniAbil") != null) {
			String opzioniAbil = getExtraparams().get("opzioniAbil");
			input.setOpzioniabilin(opzioniAbil);			
		}
		
		
		input.setFlginclannullatiio(new BigDecimal(1));
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nome".equals(crit.getFieldName())) {
					input.setNomeio(getTextFilterValue(crit));
				} else if ("flgInclAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						input.setFlginclannullatiio(new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(0) : new BigDecimal(1));
					}
				}
			}
		}

		DmpkDocTypesTrovadoctypes dmpkDocTypesTrovadoctypes = new DmpkDocTypesTrovadoctypes();
		StoreResultBean<DmpkDocTypesTrovadoctypesBean> output = dmpkDocTypesTrovadoctypes.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (output.getResultBean().getListaxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					TipologieDocumentaliBean bean = new TipologieDocumentaliBean();
					bean.setIdTipoDoc(v.get(0));
					bean.setNome(v.get(1));
					bean.setDescrizione(v.get(2));
					bean.setNomeDocTypeGen(v.get(3));
					bean.setFlgAllegato(v.get(5));
					bean.setPeriodoConserv(v.get(13));
					bean.setDescrSuppConserv(v.get(14));
					bean.setSpecAccess(v.get(15));
					bean.setSpecRiprod(v.get(16));
					bean.setAnnotazioni(v.get(19));
					bean.setValido(v.get(20).equalsIgnoreCase("0") ? true : false);
					bean.setCreataIl(v.get(23) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(23)) : null);
					bean.setCreataDa(v.get(24));
					bean.setUltAggiorn(v.get(25) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(25)) : null);
					bean.setUltAggiornEffeDa(v.get(26));
					bean.setFlgSistema(v.get(27).equalsIgnoreCase("1") ? true : false);
					bean.setFlgRichAbilVis(v.get(28).equalsIgnoreCase("1") ? true : false);
					bean.setFlgAbilLavor(v.get(29).equalsIgnoreCase("1") ? true : false);
					bean.setFlgAbilUtilizzo(v.get(30).equalsIgnoreCase("1") ? true : false);
					bean.setFlgAbilFirma(v.get(31).equalsIgnoreCase("1") ? true : false);
					bean.setFlgIsAssociataIterWf(v.get(32).equalsIgnoreCase("1") ? true : false);
					bean.setFlgRichFirmaDigitale(v.get(33) == null ? "0" : v.get(33));
					data.add(bean);
				}
			}
		}

		PaginatorBean<TipologieDocumentaliBean> lPaginatorBean = new PaginatorBean<TipologieDocumentaliBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public TipologieDocumentaliBean get(TipologieDocumentaliBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDocTypesLoaddettdoctypeBean input = new DmpkDocTypesLoaddettdoctypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIddoctypeio(bean.getIdTipoDoc() != null && !"".equals(bean.getIdTipoDoc()) ? new BigDecimal(bean.getIdTipoDoc()) : null);

		DmpkDocTypesLoaddettdoctype dmpkDocTypesLoaddettdoctype = new DmpkDocTypesLoaddettdoctype();
		StoreResultBean<DmpkDocTypesLoaddettdoctypeBean> output = dmpkDocTypesLoaddettdoctype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		TipologieDocumentaliBean result = new TipologieDocumentaliBean();
		result.setIdTipoDoc(output.getResultBean().getIddoctypeio().toString());
		result.setNome(output.getResultBean().getNomeout());
		result.setDescrizione(output.getResultBean().getDescrizioneout());
		result.setFlgAllegato(output.getResultBean().getFlgallegatoout() != null ? output.getResultBean().getFlgallegatoout().toString() : null);
		result.setIdDocTypeGen(output.getResultBean().getIddoctypegenout() != null && !"".equals(output.getResultBean().getIddoctypegenout()) ? output.getResultBean().getIddoctypegenout().toString() : null);
		result.setNomeDocTypeGen(output.getResultBean().getNomedoctypegenout());
		result.setIdProcessType(output.getResultBean().getIdprocesstypeout() != null ? output.getResultBean().getIdprocesstypeout().toString() : null);
		result.setNomeProcessType(output.getResultBean().getNomeprocesstypeout());
		result.setFlgConservPermIn(output.getResultBean().getFlgconservpermout() != null && output.getResultBean().getFlgconservpermout() == 1 ? true : false);
		result.setPeriodoConservAnni(output.getResultBean().getPeriodoconservout() != null && output.getResultBean().getPeriodoconservout().intValue() != 0 ? output.getResultBean().getPeriodoconservout().intValue() : null);

		result.setFlgRichAbilVis(output.getResultBean().getFlgrichabilxvisualizzout() != null && output.getResultBean().getFlgrichabilxvisualizzout() == 1 ? true : false);
		result.setFlgRichAbilXGestIn(output.getResultBean().getFlgrichabilxgestout() != null && output.getResultBean().getFlgrichabilxgestout() == 1 ? true : false);
		result.setFlgRichAbilXAssegnIn(output.getResultBean().getFlgrichabilxassegnout() != null && output.getResultBean().getFlgrichabilxassegnout() == 1 ? true : false);
		result.setFlgAbilFirma(output.getResultBean().getFlgrichabilxfirmaout() != null && output.getResultBean().getFlgrichabilxfirmaout() == 1 ? true : false);
		result.setRowid(output.getResultBean().getRowidout());
		result.setFlgIsAssociataIterWf(bean.getFlgIsAssociataIterWf());
		result.setFlgRichFirmaDigitale(output.getResultBean().getFlgrichfirmadigitaleout() != null ? output.getResultBean().getFlgrichfirmadigitaleout().toString() : null);
		
		List<AttrAddXEvtDelTipoBean> listaAttrAddXml = new ArrayList<AttrAddXEvtDelTipoBean>();
		if (output.getResultBean().getXmlattraddxdocdeltipoout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlattraddxdocdeltipoout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					AttrAddXEvtDelTipoBean attrAdd = new AttrAddXEvtDelTipoBean();
					attrAdd.setNome(v.get(1)); // colonna 2 non editabile
					attrAdd.setEtichetta(v.get(2));// colonna 3 non editabile
					attrAdd.setCheckObbligatorio(v.get(3) != null && "1".equals(v.get(3)));// colonna 4 xml editabile
					attrAdd.setMaxNumValori(v.get(4) != null ? v.get(4) : null);// colonna 5 (visibile solo se “Ripetibile” è spuntato) editabile
					if (attrAdd.getMaxNumValori() != null && "1".equals(attrAdd.getMaxNumValori())) {
						attrAdd.setCheckRipetibile(false);
					} else {
						attrAdd.setCheckRipetibile(true);
					}
					if (v.get(9) != null && "1".equals(v.get(9))) {
						attrAdd.setFlgTabPrincipale(true);
					} else {
						attrAdd.setCodice(v.get(10));
						attrAdd.setLabel(v.get(11));
					}
					attrAdd.setCheckExtraIterWf(v.get(12) != null && "1".equals(v.get(12)));
					listaAttrAddXml.add(attrAdd);
				}
			}
		}
		
		result.setListaAttributiAddizionali(listaAttrAddXml);
		result.setTemplateTimbroUD(output.getResultBean().getTemplatetimbroudout());
		result.setTemplateNomeUD(output.getResultBean().getTemplatenomeudout());		
		result.setFlgTipoProv(output.getResultBean().getFlgtipoprovout());
		
		// Leggo le UO e i gruppi di privilegi abilitati alla pubblicazione dei documenti della data tipologia
		String xmlUoGpPrivAbilitatiPubblicazione = output.getResultBean().getAbilitazionipubblout();
		if (xmlUoGpPrivAbilitatiPubblicazione != null) {
			List<UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean> listaUoGpPrivAbilitatiPubblicazione = XmlListaUtility.recuperaLista(xmlUoGpPrivAbilitatiPubblicazione, UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean.class, new UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBeanDeserializationHelper(createRemapConditionsMap()));
			result.setListaUoGpPrivAbilitatiPubblicazione(listaUoGpPrivAbilitatiPubblicazione);
		}
		return result;
	}

	@Override
	public TipologieDocumentaliBean add(TipologieDocumentaliBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkDocTypesIudoctypeBean input = settaInputAddOrUpdate(bean, loginBean);
		
		DmpkDocTypesIudoctype dmpkDocTypesIudoctype = new DmpkDocTypesIudoctype();
		StoreResultBean<DmpkDocTypesIudoctypeBean> result = dmpkDocTypesIudoctype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				log.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			bean.setIdTipoDoc(result.getResultBean().getIddoctypeio().toString());
		}
		return bean;
	}

	@Override
	public TipologieDocumentaliBean update(TipologieDocumentaliBean bean, TipologieDocumentaliBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkDocTypesIudoctypeBean input = settaInputAddOrUpdate(bean, loginBean);
		
		DmpkDocTypesIudoctype dmpkDocTypesIudoctype = new DmpkDocTypesIudoctype();
		StoreResultBean<DmpkDocTypesIudoctypeBean> result = dmpkDocTypesIudoctype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				log.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}

	@Override
	public TipologieDocumentaliBean remove(TipologieDocumentaliBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDocTypesDdoctypeBean input = new DmpkDocTypesDdoctypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIddoctypein(bean.getIdTipoDoc() != null && !"".equals(bean.getIdTipoDoc()) ? new BigDecimal(bean.getIdTipoDoc()) : null);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(1);

		DmpkDocTypesDdoctype dmpkDocTypesDdoctype = new DmpkDocTypesDdoctype();
		StoreResultBean<DmpkDocTypesDdoctypeBean> output = dmpkDocTypesDdoctype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}

	
	
	protected String getXMLAbilitazioniPubbl(TipologieDocumentaliBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmlBean;
		List<UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean> lList = new ArrayList<UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean>();
		lList = bean.getListaUoGpPrivAbilitatiPubblicazione();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlBean = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlBean;
	}
	
	
	/**
	 * 
	 * @param bean
	 * @return
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected String getXMLAttrAddXEvtDelTipoInOutBean(TipologieDocumentaliBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmlBean;
		List<XMLAttrAddXEvtDelTipoBean> lList = new ArrayList<XMLAttrAddXEvtDelTipoBean>();
		for (AttrAddXEvtDelTipoBean lBean : bean.getListaAttributiAddizionali()) {
			XMLAttrAddXEvtDelTipoBean lXmlBean = new XMLAttrAddXEvtDelTipoBean();
			lXmlBean.setNome(lBean.getNome());
			lXmlBean.setEtichetta(lBean.getEtichetta());
			lXmlBean.setCheckObbligatorio(lBean.getCheckObbligatorio() != null && lBean.getCheckObbligatorio() ? "1" : "0");
			if (lBean.getCheckRipetibile() != null && lBean.getCheckRipetibile()) {
				lXmlBean.setMaxNumValori(lBean.getMaxNumValori());
			} else {
				lXmlBean.setMaxNumValori("1");
			}
			if (lBean.getFlgTabPrincipale() != null && lBean.getFlgTabPrincipale()) {
				lXmlBean.setFlgTabPrincipale("1");
			} else {
				lXmlBean.setCodice(lBean.getCodice());
				lXmlBean.setLabel(lBean.getLabel());
			}
			if (bean.getFlgIsAssociataIterWf() != null && bean.getFlgIsAssociataIterWf() && lBean.getCheckExtraIterWf() != null) {
				lXmlBean.setCheckExtraIterWf(lBean.getCheckExtraIterWf() ? "1" : "0");
			}
			lList.add(lXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlBean = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlBean;
	}

	public ListaTipologieDocBean aggiungiTipologieDocAUO(ListaTipologieDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityGrantprivsudefcontestoBean input = new DmpkDefSecurityGrantprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));

		input.setFlgtpobjtograntin("TD");
		input.setListaprivilegiin("A;M;F");

		/*
		 * 
		 * in FlgTpObjToGrantIn si passa TD per le tipologie doc. in ListaPrivilegiIn si passa A;M;F per i tipi documenti
		 */

		Lista lista = new Lista();
		for (TipologieDocumentaliBean funzione : bean.getListaTipologieDoc()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent(funzione.getIdTipoDoc());
			riga.getColonna().add(col1);

			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtograntxmlin(sw.toString());

		DmpkDefSecurityGrantprivsudefcontesto grantPrivSuDefContesto = new DmpkDefSecurityGrantprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityGrantprivsudefcontestoBean> output = grantPrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}
	
	public ListaTipologieDocBean aggiungiTipologieDocPubblicabiliAUO(ListaTipologieDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityGrantprivsudefcontestoBean input = new DmpkDefSecurityGrantprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));

		input.setFlgtpobjtograntin("TD");
		input.setListaprivilegiin("A;P");

		/*
		 * 
		 * in FlgTpObjToGrantIn si passa TD per le tipologie doc. in ListaPrivilegiIn si passa A;M;F per i tipi documenti
		 */

		Lista lista = new Lista();
		for (TipologieDocumentaliBean funzione : bean.getListaTipologieDoc()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent(funzione.getIdTipoDoc());
			riga.getColonna().add(col1);

			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtograntxmlin(sw.toString());

		DmpkDefSecurityGrantprivsudefcontesto grantPrivSuDefContesto = new DmpkDefSecurityGrantprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityGrantprivsudefcontestoBean> output = grantPrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}
	

	public ListaTipologieDocBean rimuoviTipologieDocDaUO(ListaTipologieDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityRevokeprivsudefcontestoBean input = new DmpkDefSecurityRevokeprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		input.setListaprivilegiin("");

		Lista lista = new Lista();
		for (TipologieDocumentaliBean funzione : bean.getListaTipologieDoc()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent("TD");
			riga.getColonna().add(col1);
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getIdTipoDoc());
			riga.getColonna().add(col2);
			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtorevokexmlin(sw.toString());

		DmpkDefSecurityRevokeprivsudefcontesto revokePrivSuDefContesto = new DmpkDefSecurityRevokeprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityRevokeprivsudefcontestoBean> output = revokePrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}
	
	public ListaTipologieDocBean rimuoviTipologieDocPubblicabiliDaUO(ListaTipologieDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityRevokeprivsudefcontestoBean input = new DmpkDefSecurityRevokeprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		input.setListaprivilegiin("P");

		Lista lista = new Lista();
		for (TipologieDocumentaliBean funzione : bean.getListaTipologieDoc()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent("TD");
			riga.getColonna().add(col1);
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getIdTipoDoc());
			riga.getColonna().add(col2);
			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtorevokexmlin(sw.toString());

		DmpkDefSecurityRevokeprivsudefcontesto revokePrivSuDefContesto = new DmpkDefSecurityRevokeprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityRevokeprivsudefcontestoBean> output = revokePrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}
	
	private DmpkDocTypesIudoctypeBean settaInputAddOrUpdate(TipologieDocumentaliBean bean, AurigaLoginBean loginBean) throws Exception {
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkDocTypesIudoctypeBean input = new DmpkDocTypesIudoctypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setIddoctypeio(bean.getIdTipoDoc() != null && !"".equals(bean.getIdTipoDoc()) ? new BigDecimal(bean.getIdTipoDoc()) : null);
		
		input.setNomein(bean.getNome());
		input.setDescrizionein(bean.getDescrizione());
		input.setFlgallegatoin(bean.getFlgAllegato() != null && !"".equals(bean.getFlgAllegato()) ? new Integer(bean.getFlgAllegato()) : null);
		input.setNomedoctypegenin(bean.getNomeDocTypeGen());
		input.setIdprocesstypein(bean.getIdProcessType() != null && !"".equalsIgnoreCase(bean.getIdProcessType()) ? new BigDecimal(bean.getIdProcessType()) : null);
		input.setNomeprocesstypein(bean.getNomeProcessType());	
		if (bean.getFlgConservPermIn() != null && bean.getFlgConservPermIn()) {
			input.setFlgconservpermin(1);
			input.setPeriodoconservin(null);
		} else {
			input.setFlgconservpermin(0);
			input.setPeriodoconservin(bean.getPeriodoConservAnni() != null ? new BigDecimal(bean.getPeriodoConservAnni()) : null);
		}
		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilVis() != null && bean.getFlgRichAbilVis() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxgestin(bean.getFlgRichAbilXGestIn() != null && bean.getFlgRichAbilXGestIn() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxassegnin(bean.getFlgRichAbilXAssegnIn() != null && bean.getFlgRichAbilXAssegnIn() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxfirmain(bean.getFlgAbilFirma() != null && bean.getFlgAbilFirma() ? new Integer(1) : new Integer(0));
        String xmlAttrAdd = null;
		if (bean.getListaAttributiAddizionali() != null && bean.getListaAttributiAddizionali().size() > 0) {
			xmlAttrAdd = getXMLAttrAddXEvtDelTipoInOutBean(bean);
		}
		input.setXmlattraddxdocdeltipoin(xmlAttrAdd);
		
		input.setFlgmodattraddxdocdeltipoin("C");
		input.setTemplatetimbroudin(bean.getTemplateTimbroUD());
		input.setTemplatenomeudin(bean.getTemplateNomeUD());
		input.setFlgtipoprovin(bean.getFlgTipoProv());


		// Salvo le UO e i gruppi di privilegi abilitati alla pubblicazione dei documenti della data tipologia
		String xmlUoGpPrivAbilitatiPubblicazione = null;
		if (bean.getListaUoGpPrivAbilitatiPubblicazione() != null && bean.getListaUoGpPrivAbilitatiPubblicazione().size() > 0){
			xmlUoGpPrivAbilitatiPubblicazione = getXMLAbilitazioniPubbl(bean);   
		}
		input.setAbilitazionipubblin(xmlUoGpPrivAbilitatiPubblicazione);
		input.setFlgrichfirmadigitalein(bean.getFlgRichFirmaDigitale() != null && !"".equals(bean.getFlgRichFirmaDigitale()) && !"0".equals(bean.getFlgRichFirmaDigitale()) ? bean.getFlgRichFirmaDigitale() : null);
		
		
		
		// Attributi dinamici
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciXmlBean);
		input.setAttributiaddin(xmlAttributiDinamici);
		
		
		return input;
		
	}
	
	private Map<String, String> createRemapConditionsMap() {
		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}
}
