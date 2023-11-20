/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesDprocesstypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesIuprocesstypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesLoaddettprocesstypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovaprocesstypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafeprocedimenti.datasource.bean.AnagrafeProcedimentiBean;
import it.eng.auriga.ui.module.layout.server.anagrafeprocedimenti.datasource.bean.FlussiWorkflowAssociatiXmlBean;
import it.eng.client.DmpkProcessTypesDprocesstype;
import it.eng.client.DmpkProcessTypesIuprocesstype;
import it.eng.client.DmpkProcessTypesLoaddettprocesstype;
import it.eng.client.DmpkProcessTypesTrovaprocesstypes;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AnagrafeProcedimentiDataSource")
public class AnagrafeProcedimentiDataSource extends AbstractFetchDataSource<AnagrafeProcedimentiBean> {

	private static final Logger log = Logger.getLogger(AnagrafeProcedimentiDataSource.class);

	@Override
	public String getNomeEntita() {
		return "anagrafe_procedimenti";
	};

	@Override
	public PaginatorBean<AnagrafeProcedimentiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;

		List<AnagrafeProcedimentiBean> data = new ArrayList<AnagrafeProcedimentiBean>();

		DmpkProcessTypesTrovaprocesstypesBean input = new DmpkProcessTypesTrovaprocesstypesBean();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nome".equals(crit.getFieldName())) {
					input.setNomeio(getTextFilterValue(crit));
				} else if ("descrizione".equals(crit.getFieldName())) {
					input.setDescrizioneio(getTextFilterValue(crit));
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

		DmpkProcessTypesTrovaprocesstypes dmpkProcessTypesTrovaprocesstypes = new DmpkProcessTypesTrovaprocesstypes();
		StoreResultBean<DmpkProcessTypesTrovaprocesstypesBean> output = dmpkProcessTypesTrovaprocesstypes.execute(getLocale(), loginBean, input);

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
					AnagrafeProcedimentiBean bean = new AnagrafeProcedimentiBean();

					bean.setId(v.get(0));
					bean.setNome(v.get(1));
					bean.setDescrizione(v.get(2));
					bean.setDtInizioVld(v.get(5) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(5)) : null);
					bean.setDtFineVld(v.get(6) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6)) : null);
					bean.setDurataMax(v.get(7) != null ? new BigDecimal(v.get(7)) : null); // DurataMax: colonna 8 dell’xml ListaXMLOut restituito dalla stored
					bean.setUoCompetente(v.get(9)); // UoCompetente: colonna 10 dell’xml ListaXMLOut restituito dalla stored
					bean.setResponsabile(v.get(11)); // Responsabile: colonna 12 dell’xml ListaXMLOut restituito dalla stored
					bean.setFlgAmministrativo(v.get(12) != null && "1".equals(v.get(12)) ? true : false);
					bean.setIniziativa(v.get(13));
					bean.setRifNormativi(v.get(14)); // RifNormativi: colonna 15 dell’xml ListaXMLOut restituito dalla stored
					bean.setFlgSospendibile(v.get(15) != null ? true : false);
					bean.setNroMaxSospensioni(v.get(16) != null ? new Integer(v.get(16)) : null);
					bean.setFlgInterrompibile(v.get(17) != null ? true : false);
					bean.setNroMaxGGXInterr(v.get(18) != null ? new Integer(v.get(18)) : null);
					bean.setFlgPartiEsterne(v.get(19) != null ? true : false);
					bean.setFlgSilenzioAssenso(v.get(20) != null ? true : false);
					bean.setNrGGSilenzioAssenso(v.get(21) != null ? new Integer(v.get(21)) : null);

					data.add(bean);
				}
			}
		}

		PaginatorBean<AnagrafeProcedimentiBean> lPaginatorBean = new PaginatorBean<AnagrafeProcedimentiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public AnagrafeProcedimentiBean add(AnagrafeProcedimentiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesIuprocesstypeBean input = new DmpkProcessTypesIuprocesstypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocesstypeio(null);
		input.setNomein(bean.getNome());
		input.setDescrizionein(bean.getDescrizione());
		input.setDtiniziovldin(bean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioVld()) : null);
		input.setDtfinevldin(bean.getDtFineVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineVld()) : null);
		input.setIdprocesstypegenin(bean.getIdProcessTypeGen() != null ? new BigDecimal(bean.getIdProcessTypeGen()) : null);
		input.setNomeprocesstypegenin(bean.getNomeProcessTypeGen());
		input.setFlgtipoiniziativain(bean.getIniziativa());
		input.setFlgfascsfin(bean.getFlgFascSF());
		input.setGgdurataprevistain(bean.getDurataMax() != null ? bean.getDurataMax() : null);
		input.setBasenormativain(bean.getRifNormativi());

		input.setFlgsospendibilein(bean.getFlgSospendibile() != null && bean.getFlgSospendibile() ? new Integer(1) : null);
		if (bean.getFlgSospendibile() != null && bean.getFlgSospendibile()) {
			input.setNromaxsospensioniin(bean.getNroMaxSospensioni() != null ? new BigDecimal(bean.getNroMaxSospensioni()) : null);
		}

		input.setFlginterrompibilein(bean.getFlgInterrompibile() != null && bean.getFlgInterrompibile() ? new Integer(1) : null);
		if (bean.getFlgInterrompibile() != null && bean.getFlgInterrompibile()) {
			input.setNromaxggxinterrin(bean.getNroMaxGGXInterr() != null ? new BigDecimal(bean.getNroMaxGGXInterr()) : null);
		}

		input.setFlgpartiesternein(bean.getFlgPartiEsterne() != null && bean.getFlgPartiEsterne() ? new Integer(1) : null);

		input.setFlgsilenzioassensoin(bean.getFlgSilenzioAssenso() != null && bean.getFlgSilenzioAssenso() ? new Integer(1) : null);
		if (bean.getFlgSilenzioAssenso() != null && bean.getFlgSilenzioAssenso()) {
			input.setGgsilenzioassensoin(bean.getNrGGSilenzioAssenso() != null ? new BigDecimal(bean.getNrGGSilenzioAssenso()) : null);
		}

		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilXVisualizz() != null && bean.getFlgRichAbilXVisualizz() ? new Integer(1) : null);
		input.setFlglockedin(bean.getFlgLocked() != null && bean.getFlgLocked() ? new Integer(1) : null);

		if (bean.getListaFlussiWfAssociati() != null && bean.getListaFlussiWfAssociati().size() > 0) {
			String xmlFlussiWorkFlowAssociati = getXmlFlussiWorkflowAssociati(bean);
			input.setXmlflussiwfin(xmlFlussiWorkFlowAssociati);
		}

		DmpkProcessTypesIuprocesstype dmpkProcessTypesIuprocesstype = new DmpkProcessTypesIuprocesstype();
		StoreResultBean<DmpkProcessTypesIuprocesstypeBean> output = dmpkProcessTypesIuprocesstype.execute(getLocale(), loginBean, input);

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
		bean.setId(output.getResultBean().getIdprocesstypeio() != null ? output.getResultBean().getIdprocesstypeio().toString() : null);

		return bean;
	}

	@Override
	public AnagrafeProcedimentiBean update(AnagrafeProcedimentiBean bean, AnagrafeProcedimentiBean oldvalue) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesIuprocesstypeBean input = new DmpkProcessTypesIuprocesstypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocesstypeio(bean.getId() != null ? new BigDecimal(bean.getId()) : null);
		input.setNomein(bean.getNome());
		input.setDescrizionein(bean.getDescrizione());
		input.setDtiniziovldin(bean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioVld()) : null);
		input.setDtfinevldin(bean.getDtFineVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineVld()) : null);
		input.setIdprocesstypegenin(bean.getIdProcessTypeGen() != null ? new BigDecimal(bean.getIdProcessTypeGen()) : null);
		input.setNomeprocesstypegenin(bean.getNomeProcessTypeGen());
		input.setFlgtipoiniziativain(bean.getIniziativa());
		input.setFlgfascsfin(bean.getFlgFascSF());
		input.setGgdurataprevistain(bean.getDurataMax() != null ? bean.getDurataMax() : null);
		input.setBasenormativain(bean.getRifNormativi());

		input.setFlgsospendibilein(bean.getFlgSospendibile() != null && bean.getFlgSospendibile() ? new Integer(1) : null);
		if (bean.getFlgSospendibile() != null && bean.getFlgSospendibile()) {
			input.setNromaxsospensioniin(bean.getNroMaxSospensioni() != null ? new BigDecimal(bean.getNroMaxSospensioni()) : null);
		}

		input.setFlginterrompibilein(bean.getFlgInterrompibile() != null && bean.getFlgInterrompibile() ? new Integer(1) : null);
		if (bean.getFlgInterrompibile() != null && bean.getFlgInterrompibile()) {
			input.setNromaxggxinterrin(bean.getNroMaxGGXInterr() != null ? new BigDecimal(bean.getNroMaxGGXInterr()) : null);
		}

		input.setFlgpartiesternein(bean.getFlgPartiEsterne() != null && bean.getFlgPartiEsterne() ? new Integer(1) : null);

		input.setFlgsilenzioassensoin(bean.getFlgSilenzioAssenso() != null && bean.getFlgSilenzioAssenso() ? new Integer(1) : null);
		if (bean.getFlgSilenzioAssenso() != null && bean.getFlgSilenzioAssenso()) {
			input.setGgsilenzioassensoin(bean.getNrGGSilenzioAssenso() != null ? new BigDecimal(bean.getNrGGSilenzioAssenso()) : null);
		}

		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilXVisualizz() != null && bean.getFlgRichAbilXVisualizz() ? new Integer(1) : null);
		input.setFlglockedin(bean.getFlgLocked() != null && bean.getFlgLocked() ? new Integer(1) : null);

		if (bean.getListaFlussiWfAssociati() != null && bean.getListaFlussiWfAssociati().size() > 0) {
			String xmlFlussiWorkFlowAssociati = getXmlFlussiWorkflowAssociati(bean);
			input.setXmlflussiwfin(xmlFlussiWorkFlowAssociati);
		}

		DmpkProcessTypesIuprocesstype dmpkProcessTypesIuprocesstype = new DmpkProcessTypesIuprocesstype();
		StoreResultBean<DmpkProcessTypesIuprocesstypeBean> output = dmpkProcessTypesIuprocesstype.execute(getLocale(), loginBean, input);

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
	public AnagrafeProcedimentiBean get(AnagrafeProcedimentiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesLoaddettprocesstypeBean input = new DmpkProcessTypesLoaddettprocesstypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocesstypeio(bean.getId() != null ? new BigDecimal(bean.getId()) : null);

		DmpkProcessTypesLoaddettprocesstype dmpkProcessTypesLoaddettprocesstype = new DmpkProcessTypesLoaddettprocesstype();
		StoreResultBean<DmpkProcessTypesLoaddettprocesstypeBean> output = dmpkProcessTypesLoaddettprocesstype.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		AnagrafeProcedimentiBean result = new AnagrafeProcedimentiBean();
		result.setId(output.getResultBean().getIdprocesstypeio() != null ? output.getResultBean().getIdprocesstypeio().toString() : null);
		result.setNome(output.getResultBean().getNomeout());
		result.setDescrizione(output.getResultBean().getDescrizioneout());
		result.setDtInizioVld(output.getResultBean().getDtiniziovldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean()
				.getDtiniziovldout()) : null);
		result.setDtFineVld(output.getResultBean().getDtfinevldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean()
				.getDtfinevldout()) : null);
		result.setIdProcessTypeGen(output.getResultBean().getIdprocesstypegenout() != null ? output.getResultBean().getIdprocesstypegenout().toString() : null);
		result.setNomeProcessTypeGen(output.getResultBean().getNomeprocesstypegenout());

		// result.setUoCompetente
		// result.setResponsabile
		result.setFlgAmministrativo(output.getResultBean().getFlgprocammout() != null && output.getResultBean().getFlgprocammout() == 1 ? true : false);
		result.setDurataMax(output.getResultBean().getGgdurataprevistaout() != null ? output.getResultBean().getGgdurataprevistaout() : null);
		result.setRifNormativi(output.getResultBean().getBasenormativaout());

		List<FlussiWorkflowAssociatiXmlBean> listaFlussiWorkflowAssociatiXml = new ArrayList<FlussiWorkflowAssociatiXmlBean>();
		if (output.getResultBean().getXmlflussiwfout() != null && output.getResultBean().getXmlflussiwfout().length() > 0) {
			StringReader sr = new StringReader(output.getResultBean().getXmlflussiwfout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					FlussiWorkflowAssociatiXmlBean lFlussiWorkflowAssociatiXmlBean = new FlussiWorkflowAssociatiXmlBean();
					lFlussiWorkflowAssociatiXmlBean.setCodTipoFlusso(v.get(1));
					lFlussiWorkflowAssociatiXmlBean.setNomeTipoFlusso(v.get(2));
					lFlussiWorkflowAssociatiXmlBean.setDataInizioVld(v.get(3) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(3)) : null);
					lFlussiWorkflowAssociatiXmlBean.setDataFineVld(v.get(4) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(4)) : null);		
					listaFlussiWorkflowAssociatiXml.add(lFlussiWorkflowAssociatiXmlBean);
				}
				result.setListaFlussiWfAssociati(listaFlussiWorkflowAssociatiXml);
			}
		}

		result.setIniziativa(output.getResultBean().getFlgtipoiniziativaout());
		result.setFlgSospendibile(output.getResultBean().getFlgsospendibileout() != null && output.getResultBean().getFlgsospendibileout() == 1 ? true : false);
		result.setNroMaxSospensioni(output.getResultBean().getNromaxsospensioniout() != null ? output.getResultBean().getNromaxsospensioniout().intValue()
				: null);
		result.setFlgInterrompibile(output.getResultBean().getFlginterrompibileout() != null && output.getResultBean().getFlginterrompibileout() == 1 ? true
				: false);
		result.setNroMaxGGXInterr(output.getResultBean().getNromaxggxinterrout() != null ? output.getResultBean().getNromaxggxinterrout().intValue() : null);
		result.setFlgPartiEsterne(output.getResultBean().getFlgpartiesterneout() != null && output.getResultBean().getFlgpartiesterneout() == 1 ? true : false);
		result.setFlgSilenzioAssenso(output.getResultBean().getFlgsilenzioassensoout() != null && output.getResultBean().getFlgsilenzioassensoout() == 1 ? true
				: false);
		result.setNrGGSilenzioAssenso(output.getResultBean().getGgsilenzioassensoout() != null ? output.getResultBean().getGgsilenzioassensoout().intValue()
				: null);
		result.setFlgFascSF(output.getResultBean().getFlgfascsfout());
		result.setFlgLocked(output.getResultBean().getFlglockedout() != null && output.getResultBean().getFlglockedout() == 1 ? true : false);
		result.setFlgRichAbilXVisualizz(output.getResultBean().getFlgrichabilxvisualizzout() != null
				&& output.getResultBean().getFlgrichabilxvisualizzout() == 1 ? true : null);

		return result;
	}

	@Override
	public AnagrafeProcedimentiBean remove(AnagrafeProcedimentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesDprocesstypeBean input = new DmpkProcessTypesDprocesstypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setMotiviin(null);
		input.setFlgignorewarningin(new Integer(1));
		input.setFlgcancfisicain(null);
		input.setIdprocesstypein(bean.getId() != null ? new BigDecimal(bean.getId()) : null);

		DmpkProcessTypesDprocesstype dmpkProcessTypesDprocesstype = new DmpkProcessTypesDprocesstype();
		StoreResultBean<DmpkProcessTypesDprocesstypeBean> output = dmpkProcessTypesDprocesstype.execute(getLocale(), loginBean, input);

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

	private String getXmlFlussiWorkflowAssociati(AnagrafeProcedimentiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ParseException {
		
		String xmlFlussiWorkflow = null;
		
		for (FlussiWorkflowAssociatiXmlBean lFlussiWorkflowAssociatiXmlBean : bean.getListaFlussiWfAssociati()) {			
			lFlussiWorkflowAssociatiXmlBean.setNomeTipoFlusso(lFlussiWorkflowAssociatiXmlBean.getCodTipoFlusso());			
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlFlussiWorkflow = lXmlUtilitySerializer.bindXmlList(bean.getListaFlussiWfAssociati());
		
		return xmlFlussiWorkflow;
	}

}