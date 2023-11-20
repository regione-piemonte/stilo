/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesDeventtypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesIueventtypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesLoaddetteventtypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovaeventtypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.AttrAddXEvtDelTipoBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.DefAttivitaProcedimentiBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.XMLAttrAddXEvtDelTipoBean;
import it.eng.client.DmpkProcessTypesDeventtype;
import it.eng.client.DmpkProcessTypesIueventtype;
import it.eng.client.DmpkProcessTypesLoaddetteventtype;
import it.eng.client.DmpkProcessTypesTrovaeventtypes;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
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


@Datasource(id = "DefAttivitaProcedimentiDataSource")
public class DefAttivitaProcedimentiDataSource extends AbstractFetchDataSource<DefAttivitaProcedimentiBean> {

	private static final Logger log = Logger.getLogger(DefAttivitaProcedimentiDataSource.class);

	@Override
	public String getNomeEntita() {
		return "definizione_attivita_procedimenti";
	};

	@Override
	public PaginatorBean<DefAttivitaProcedimentiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;

		List<DefAttivitaProcedimentiBean> data = new ArrayList<DefAttivitaProcedimentiBean>();

		DmpkProcessTypesTrovaeventtypesBean input = new DmpkProcessTypesTrovaeventtypesBean();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("descrizione".equals(crit.getFieldName())) {
					input.setDeseventio(getTextFilterValue(crit));
				} else if ("categoria".equals(crit.getFieldName())) {
					input.setCategoriaio(getTextFilterValue(crit));
				} else if ("puntualeDurativa".equals(crit.getFieldName())) {
					// durativa (=1) o puntuale (=0)
					input.setFlgdurativoio(new Boolean(String.valueOf(crit.getValue())) ? new Integer(1) : new Integer(0));
				} else if ("flgTuttiProcedimenti".equals(crit.getFieldName())) {
					input.setFlgvldxtuttiprocammio(new Boolean(String.valueOf(crit.getValue())) ? new Integer(1) : new Integer(0));
				} else if ("tipoProcess".equals(crit.getFieldName())) {
					String[] splitter = crit.getValue().toString().split("&-&");
					input.setIdprocesstypeio(new BigDecimal(splitter[0]));
					input.setNomeprocesstypeio(splitter[1]);
				} else if ("flgModTerminiProc".equals(crit.getFieldName())) {
					input.setFlgmodterminiprocio(String.valueOf(crit.getValue()));
				} else if ("flgAssociatoTipoDoc".equals(crit.getFieldName())) {
					// eventi associati (=1) o non associati (=0)
					input.setFlgassociatotipodocio(new Boolean(String.valueOf(crit.getValue())) ? new Integer(1) : new Integer(0));
				} else if ("tipologiaDocAss".equals(crit.getFieldName())) {
					input.setNomedoctypeio(getTextFilterValue(crit));
				} else if ("flgInclAnnullati".equals(crit.getFieldName())) {
					// (se 1 vengono estratti anche i tipi di eventi logicamente annullati, altrimenti no)
					input.setFlginclannullatiio(new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0));
				}
			}
		}

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);

		DmpkProcessTypesTrovaeventtypes dmpkProcessTypesTrovaeventtypes = new DmpkProcessTypesTrovaeventtypes();
		StoreResultBean<DmpkProcessTypesTrovaeventtypesBean> output = dmpkProcessTypesTrovaeventtypes.execute(getLocale(), loginBean, input);

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
					DefAttivitaProcedimentiBean bean = new DefAttivitaProcedimentiBean();

					bean.setIdEventType(new BigDecimal(v.get(0))); // Id. : colonna 1 dell’xml ListaXMLOut restituito dalla stored
					bean.setDescrizione(v.get(1)); // Descrizione: colonna 2 dell’xml ListaXMLOut restituito dalla stored
					bean.setCategoria(v.get(4));// Categoria: colonna 5 dell’xml ListaXMLOut restituito dalla stored
					bean.setPuntualeDurativa(v.get(3) != null && new Integer(v.get(3)).intValue() == 1 ? true : false);// Puntuale/durativa (colonna senza
																														// intestazione visibile e con due
																														// icone): colonna 4 dell’xml
																														// ListaXMLOut restituito dalla stored
																														// che vale 1 per durativo e 0 per
																														// puntuale
					bean.setTipologiaDocAss(v.get(13));// Tipologia documentale associata: colonna 14 dell’xml ListaXMLOut restituito dalla stored
					bean.setFlgTuttiProcedimenti(v.get(15) != null && new Integer(v.get(15)).intValue() == 1 ? true : false);// In tutti i procedimenti: colonna
																																// 16 dell’xml ListaXMLOut
																																// restituito dalla stored. La
																																// colonna mostra un’icona di
																																// spunta con alt “Presente in
																																// tutti i procedimenti” quando
																																// la colonna vale 1, altrimenti
																																// non appare nulla.
					bean.setNote(v.get(16)); // Note: colonna 17 dell’xml ListaXMLOut restituito dalla stored
					bean.setFlgAnnLogico(v.get(17) != null && new Integer(v.get(17)).intValue() == 1 ? true : false);// Validità: colonna 18 dell’xml
																														// ListaXMLOut restituito dalla stored
																														// (che è un flag 1/0 di annullamento
																														// logico). La colonna mostra un’icona
																														// di eliminazione logica (es. una croce
																														// rossa) con alt “Attività annullata”
																														// quando la colonna vale 1, altrimenti
																														// non appare nulla.
					data.add(bean);
				}
			}
		}
		PaginatorBean<DefAttivitaProcedimentiBean> lPaginatorBean = new PaginatorBean<DefAttivitaProcedimentiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public DefAttivitaProcedimentiBean add(DefAttivitaProcedimentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkProcessTypesIueventtypeBean input = settaInputAddOrUpdate(bean, loginBean);

		DmpkProcessTypesIueventtype dmpkProcessTypesIueventtype = new DmpkProcessTypesIueventtype();
		StoreResultBean<DmpkProcessTypesIueventtypeBean> output = dmpkProcessTypesIueventtype.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		} else {
			bean.setIdEventType(output.getResultBean().getIdeventtypeio());
		}

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

	private DmpkProcessTypesIueventtypeBean settaInputAddOrUpdate(DefAttivitaProcedimentiBean bean, AurigaLoginBean loginBean) throws Exception {
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesIueventtypeBean input = new DmpkProcessTypesIueventtypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setAnnotazioniin(bean.getNote());
		input.setDeseventin(bean.getDescrizione());
		input.setCategoriain(bean.getCategoria());
		input.setIdeventtypeio(bean.getIdEventType());

		input.setFlgvldxtuttiprocammin(bean.getFlgTuttiProcedimenti() != null && bean.getFlgTuttiProcedimenti() ? 1 : 0);
		input.setDuratamaxin(bean.getDurataMaxGiorni());
		input.setFlgdurativoin(bean.getPuntualeDurativa() != null && bean.getPuntualeDurativa() ? 1 : 0);

		String xmlList = null;
		if (bean.getListaAttributiAddXEventiDelTipo() != null && bean.getListaAttributiAddXEventiDelTipo().size() > 0) {
			xmlList = getXMLAttrAddXEvtDelTipoInOutBean(bean);
		}
		input.setXmlattraddxevtdeltipoin(xmlList);
		input.setFlgmodattraddxevtdeltipoin("C");
		if (bean.getTipologiaDocAss() != null && !"".equals(bean.getTipologiaDocAss())) {
			String[] splitter = bean.getTipologiaDocAss().split("&-&");
			input.setIddoctypein(new BigDecimal(splitter[0]));
			input.setNomedoctypein(splitter[1]);
			input.setCodtipodatareldocin(bean.getCodTipoDataRelDocInOut());
		} else {
			input.setIddoctypein(null);
			input.setNomedoctypein(null);
			input.setCodtipodatareldocin(null);
		}
		
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
//		salvaAttributiCustomSemplici(bean, sezioneCacheAttributiDinamici);
//		salvaAttributiCustomLista(bean, sezioneCacheAttributiDinamici);
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciXmlBean);
		input.setAttributiaddin(xmlAttributiDinamici);
		
		return input;
	}

	@Override
	public DefAttivitaProcedimentiBean update(DefAttivitaProcedimentiBean bean, DefAttivitaProcedimentiBean oldvalue) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkProcessTypesIueventtypeBean input = settaInputAddOrUpdate(bean, loginBean);

		DmpkProcessTypesIueventtype dmpkProcessTypesIueventtype = new DmpkProcessTypesIueventtype();
		StoreResultBean<DmpkProcessTypesIueventtypeBean> output = dmpkProcessTypesIueventtype.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

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

	@Override
	public DefAttivitaProcedimentiBean remove(DefAttivitaProcedimentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesDeventtypeBean input = new DmpkProcessTypesDeventtypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdeventtypein(bean.getIdEventType());
		input.setMotiviin(null);
		input.setFlgignorewarningin(new Integer(1));
		input.setFlgcancfisicain(null);

		DmpkProcessTypesDeventtype dmpkProcessTypesDeventtype = new DmpkProcessTypesDeventtype();
		StoreResultBean<DmpkProcessTypesDeventtypeBean> output = dmpkProcessTypesDeventtype.execute(getLocale(), loginBean, input);

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

	@Override
	public DefAttivitaProcedimentiBean get(DefAttivitaProcedimentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesLoaddetteventtypeBean input = new DmpkProcessTypesLoaddetteventtypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdeventtypeio(bean.getIdEventType());

		DmpkProcessTypesLoaddetteventtype loadDettAttr = new DmpkProcessTypesLoaddetteventtype();
		StoreResultBean<DmpkProcessTypesLoaddetteventtypeBean> output = loadDettAttr.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		DefAttivitaProcedimentiBean result = new DefAttivitaProcedimentiBean();

		result.setIdEventType(output.getResultBean().getIdeventtypeio());
		result.setDescrizione(output.getResultBean().getDeseventout());
		result.setCategoria(output.getResultBean().getCategoriaout());
		Integer puntualeDurativa = output.getResultBean().getFlgdurativoout();
		result.setPuntualeDurativa(puntualeDurativa != null && puntualeDurativa == 1 ? true : false);
		result.setDurataMaxGiorni(output.getResultBean().getDuratamaxout());
		Integer flgTuttiProcedimenti = output.getResultBean().getFlgvldxtuttiprocammout();
		result.setFlgTuttiProcedimenti(flgTuttiProcedimenti != null && flgTuttiProcedimenti == 1 ? true : false);
		result.setNote(output.getResultBean().getAnnotazioniout());
		BigDecimal bd = output.getResultBean().getIddoctypeout();
		result.setTipologiaDocAss(bd != null ? bd.toString() + "&-&" + output.getResultBean().getNomedoctypeout() : null);
		result.setCodTipoDataRelDocInOut(output.getResultBean().getCodtipodatareldocout());
		result.setRowid(output.getResultBean().getRowidout());
		
		List<AttrAddXEvtDelTipoBean> listaAttributiAddXEventiDelTipo = new ArrayList<AttrAddXEvtDelTipoBean>();
		if (output.getResultBean().getXmlattraddxevtdeltipoout() != null) {
			List<XMLAttrAddXEvtDelTipoBean> resultList = XmlListaUtility.recuperaLista(output.getResultBean().getXmlattraddxevtdeltipoout(), XMLAttrAddXEvtDelTipoBean.class);
			if(resultList != null && resultList.size() > 0){
				for(XMLAttrAddXEvtDelTipoBean item : resultList){
					
					AttrAddXEvtDelTipoBean attrAdd = new AttrAddXEvtDelTipoBean();
					
					attrAdd.setNome(item.getNome());
					attrAdd.setEtichetta(item.getEtichetta());
					attrAdd.setCheckObbligatorio(item.getCheckObbligatorio() != null && "1".equals(item.getCheckObbligatorio()));
					attrAdd.setMaxNumValori(item.getMaxNumValori() != null && !"".equals(item.getMaxNumValori()) ? item.getMaxNumValori() : null);
					if ("1".equals(attrAdd.getMaxNumValori())) {
						attrAdd.setCheckRipetibile(false);
					} else {
						attrAdd.setCheckRipetibile(true);
					}
					
					if (item.getCodice() != null && !"".equals(item.getCodice()) && item.getCodice().startsWith("HEADER_")) {
						attrAdd.setFlgTabPrincipale(true);
					} else {
						attrAdd.setCodice(item.getCodice());
						attrAdd.setLabel(item.getLabel());
					}
					
					attrAdd.setTsVldDal(item.getTsVldDal());
					attrAdd.setTsVldA(item.getTsVldA());
						
					listaAttributiAddXEventiDelTipo.add(attrAdd);
				}
			}
		}

		result.setListaAttributiAddXEventiDelTipo(listaAttributiAddXEventiDelTipo);
		return result;
	}

	protected String getXMLAttrAddXEvtDelTipoInOutBean(DefAttivitaProcedimentiBean bean) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		String xmlBean;
		List<XMLAttrAddXEvtDelTipoBean> lList = new ArrayList<XMLAttrAddXEvtDelTipoBean>();
		for (AttrAddXEvtDelTipoBean lBean : bean.getListaAttributiAddXEventiDelTipo()) {
			XMLAttrAddXEvtDelTipoBean lXmlBean = new XMLAttrAddXEvtDelTipoBean();
			lXmlBean.setNome(lBean.getNome());
			lXmlBean.setEtichetta(lBean.getEtichetta());
			lXmlBean.setCheckObbligatorio(lBean.getCheckObbligatorio() != null && lBean.getCheckObbligatorio() ? "1" : "0");
			if (lBean.getCheckRipetibile() != null && lBean.getCheckRipetibile() ? true : false) {
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
			lXmlBean.setTsVldDal(lBean.getTsVldDal());
			lXmlBean.setTsVldA(lBean.getTsVldA());
			lList.add(lXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlBean = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlBean;
	}

}