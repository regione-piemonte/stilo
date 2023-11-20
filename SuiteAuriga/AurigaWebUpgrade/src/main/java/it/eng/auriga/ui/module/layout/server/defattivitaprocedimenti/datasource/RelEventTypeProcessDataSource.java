/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesAddrelevtty_proctyBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesDrelevtty_proctyBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesLoaddettevtty_proctyBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovaeventtypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.ProcessTypesXMLBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.RelEventTypeProcessBean;
import it.eng.client.DmpkProcessTypesAddrelevtty_procty;
import it.eng.client.DmpkProcessTypesDrelevtty_procty;
import it.eng.client.DmpkProcessTypesLoaddettevtty_procty;
import it.eng.client.DmpkProcessTypesTrovaeventtypes;
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

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@Datasource(id = "RelEventTypeProcessDataSource")
public class RelEventTypeProcessDataSource extends AbstractFetchDataSource<RelEventTypeProcessBean> {

	private static final Logger log = Logger.getLogger(RelEventTypeProcessDataSource.class);

	@Override
	public String getNomeEntita() {
		
		return "rel_event_type_process";
	}

	@Override
	public PaginatorBean<RelEventTypeProcessBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;

		String idProcessType = getExtraparams().get("idProcessType");
		String nomeProcessType = getExtraparams().get("nomeProcessType");

		List<RelEventTypeProcessBean> data = new ArrayList<RelEventTypeProcessBean>();

		DmpkProcessTypesTrovaeventtypesBean input = new DmpkProcessTypesTrovaeventtypesBean();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("descrizione".equals(crit.getFieldName())) {
					input.setDeseventio(getTextFilterValue(crit));
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

		input.setIdprocesstypeio(new BigDecimal(idProcessType));
		input.setNomeprocesstypeio(nomeProcessType);

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
					RelEventTypeProcessBean bean = new RelEventTypeProcessBean();

					bean.setIdEventTypeIn(v.get(0) != null && !"".equals(v.get(0)) ? v.get(0) : null);
					bean.setDesEventTypeIn(v.get(1));
					bean.setFlgDurativoIO(v.get(3) != null && new Integer(v.get(3)).intValue() == 1 ? true : false);
					bean.setCategoriaIO(v.get(4));
					bean.setIdDocTypeIO(v.get(12) != null && !"".equals(v.get(12)) ? v.get(12) : null);
					bean.setNomeDocTypeIO(v.get(13) != null ? v.get(13) : null);
					bean.setFlgVldXTuttiProcAmmIO(v.get(15) != null && new Integer(v.get(15)).intValue() == 1 ? true : false);

					data.add(bean);
				}
			}
		}
		PaginatorBean<RelEventTypeProcessBean> lPaginatorBean = new PaginatorBean<RelEventTypeProcessBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public RelEventTypeProcessBean add(RelEventTypeProcessBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String idProcessType = getExtraparams().get("idProcessType");
		String nomeProcessType = getExtraparams().get("nomeProcessType");

		DmpkProcessTypesAddrelevtty_proctyBean input = getProctyBean(bean, loginBean, idProcessType, nomeProcessType);
		DmpkProcessTypesAddrelevtty_procty lDmpkProcessTypesAddrelevtty_procty = new DmpkProcessTypesAddrelevtty_procty();
		StoreResultBean<DmpkProcessTypesAddrelevtty_proctyBean> output = lDmpkProcessTypesAddrelevtty_procty.execute(getLocale(), loginBean, input);

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
	public RelEventTypeProcessBean update(RelEventTypeProcessBean bean, RelEventTypeProcessBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String idProcessType = getExtraparams().get("idProcessType");
		String nomeProcessType = getExtraparams().get("nomeProcessType");

		DmpkProcessTypesAddrelevtty_proctyBean input = getProctyBean(bean, loginBean, idProcessType, nomeProcessType);
		DmpkProcessTypesAddrelevtty_procty lDmpkProcessTypesAddrelevtty_procty = new DmpkProcessTypesAddrelevtty_procty();
		StoreResultBean<DmpkProcessTypesAddrelevtty_proctyBean> output = lDmpkProcessTypesAddrelevtty_procty.execute(getLocale(), loginBean, input);

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

	public DmpkProcessTypesAddrelevtty_proctyBean getProctyBean(RelEventTypeProcessBean bean, AurigaLoginBean loginBean, String idProcessType,
			String nomeProcessType) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesAddrelevtty_proctyBean input = new DmpkProcessTypesAddrelevtty_proctyBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdeventtypein(bean.getIdEventTypeIn() != null ? new BigDecimal(bean.getIdEventTypeIn()) : null);
		input.setDeseventtypein(bean.getDesEventTypeIn());

		String processTypesXMLBeanString = getProcessTypesXMLBean(bean, idProcessType, nomeProcessType);
		input.setProcesstypesxmlin(processTypesXMLBeanString);

		return input;
	}

	@Override
	public RelEventTypeProcessBean remove(RelEventTypeProcessBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String idProcessType = getExtraparams().get("idProcessType");

		DmpkProcessTypesDrelevtty_proctyBean input = new DmpkProcessTypesDrelevtty_proctyBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdeventtypein(bean.getIdEventTypeIn() != null ? new BigDecimal(bean.getIdEventTypeIn()) : null);
		input.setIdprocesstypein(idProcessType != null && !"".equals(idProcessType) ? new BigDecimal(idProcessType) : null);
		input.setMotiviin(null);

		DmpkProcessTypesDrelevtty_procty dmpkProcessTypesDrelevtty_procty = new DmpkProcessTypesDrelevtty_procty();
		StoreResultBean<DmpkProcessTypesDrelevtty_proctyBean> output = dmpkProcessTypesDrelevtty_procty.execute(getLocale(), loginBean, input);

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
	public RelEventTypeProcessBean get(RelEventTypeProcessBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String idProcessType = getExtraparams().get("idProcessType");

		DmpkProcessTypesLoaddettevtty_proctyBean input = new DmpkProcessTypesLoaddettevtty_proctyBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdeventtypein(bean.getIdEventTypeIn() != null ? new BigDecimal(bean.getIdEventTypeIn()) : null);
		input.setIdprocesstypein(idProcessType != null && !"".equals(idProcessType) ? new BigDecimal(idProcessType) : null);

		DmpkProcessTypesLoaddettevtty_procty dmpkProcessTypesLoaddettevtty_procty = new DmpkProcessTypesLoaddettevtty_procty();
		StoreResultBean<DmpkProcessTypesLoaddettevtty_proctyBean> output = dmpkProcessTypesLoaddettevtty_procty.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		RelEventTypeProcessBean result = new RelEventTypeProcessBean();
		result.setIdEventTypeIn(output.getResultBean().getIdeventtypein() != null ? "" + output.getResultBean().getIdeventtypein().longValue() : null);
		result.setDesEventTypeIn(output.getResultBean().getDeseventout());
		result.setIdProcessTypeIO(output.getResultBean().getIdprocesstypein() != null ? "" + output.getResultBean().getIdprocesstypein().longValue() : null);

		if (bean.getListProcessTypesXMLBean() != null && bean.getListProcessTypesXMLBean().size() > 0) {
			result.setListProcessTypesXMLBean(bean.getListProcessTypesXMLBean());
		}

		return result;
	}

	protected String getProcessTypesXMLBean(RelEventTypeProcessBean bean, String idProcessType, String nomeProcessType) throws JAXBException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmlBean;
		List<ProcessTypesXMLBean> lList = new ArrayList<ProcessTypesXMLBean>();

		ProcessTypesXMLBean lXmlBean = new ProcessTypesXMLBean();
		lXmlBean.setId(new Integer(idProcessType));
		lXmlBean.setNome(nomeProcessType);

		lList.add(lXmlBean);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlBean = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlBean;
	}

}
