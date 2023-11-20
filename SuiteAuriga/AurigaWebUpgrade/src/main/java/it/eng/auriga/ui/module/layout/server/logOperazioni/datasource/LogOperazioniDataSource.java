/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityTrovalogBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.logOperazioni.datasource.bean.FiltriLogOperazioniBean;
import it.eng.auriga.ui.module.layout.server.logOperazioni.datasource.bean.LogOperazioniXmlBean;
import it.eng.auriga.ui.module.layout.server.logOperazioni.datasource.bean.LogOperazioniXmlBeanDeserializationHelper;
import it.eng.client.DmpkUtilityTrovalog;
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
import it.eng.xml.XmlUtilitySerializer;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Ottavio Passalacqua
 *
 */

@Datasource(id="LogOperazioniDataSource")
public class LogOperazioniDataSource extends AurigaAbstractFetchDatasource<LogOperazioniXmlBean>{

	private static final Logger log = Logger.getLogger(LogOperazioniDataSource.class);

	@Override
	public String getNomeEntita() {
		return "log_operazioni";
	};

	@Override
	public LogOperazioniXmlBean get(LogOperazioniXmlBean bean) throws Exception {
		// TODO Auto-generated method stub
		return bean;
	}	
	
	@Override
	public PaginatorBean<LogOperazioniXmlBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkUtilityTrovalogBean input =  createFetchInput(criteria, token, idUserLavoro);
	
        // Inizializzo l'OUTPUT
		DmpkUtilityTrovalog lservice  = new DmpkUtilityTrovalog();
		StoreResultBean<DmpkUtilityTrovalogBean> output = lservice.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		// SETTO L'OUTPUT
		List<LogOperazioniXmlBean> data = new ArrayList<LogOperazioniXmlBean>();
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), LogOperazioniXmlBean.class, new LogOperazioniXmlBeanDeserializationHelper(createRemapConditions()));
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
				
		PaginatorBean<LogOperazioniXmlBean> lPaginatorBean = new PaginatorBean<LogOperazioniXmlBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

   @Override
	public LogOperazioniXmlBean update(LogOperazioniXmlBean bean, LogOperazioniXmlBean oldvalue) throws Exception {
		// TODO Auto-generated method stub		
		return bean;		
	}

	@Override
	public LogOperazioniXmlBean add(LogOperazioniXmlBean bean) throws Exception {
        // TODO Auto-generated method stub		
		return bean;		
	}

	@Override
	public LogOperazioniXmlBean remove(LogOperazioniXmlBean bean) throws Exception {
		// TODO Auto-generated method stub
		return bean;
	}
	
	
	/**
	 * @param criteria
	 * @param token
	 * @param idUserLavoro
	 * @return
	 * @throws Exception
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected DmpkUtilityTrovalogBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
        FiltriLogOperazioniBean filtri = new FiltriLogOperazioniBean();	
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){					
				if("tipoOperazione".equals(crit.getFieldName())) {
					filtri.setTipoOperazione(getTextFilterValue(crit));	
				} 			
				else if("esitoOperazione".equals(crit.getFieldName())) {
					filtri.setEsitoOperazione(getTextFilterValue(crit));
				} 
				else if ("tsOperazione".equals(crit.getFieldName())) {
					Date[] estremiData = getDateConOraFilterValue(crit);
					if (estremiData[0] != null) {
						filtri.setTsOperazioneDa(estremiData[0]);
					}
					if (estremiData[1] != null) {
						filtri.setTsOperazioneA(estremiData[1]);
					}
				}
				else if("operazioneEffettuataDa".equals(crit.getFieldName())) {
					filtri.setOperazioneEffettuataDa(getTextFilterValue(crit));
				}
			}
		}
		
		// Inizializzo l'INPUT		
		DmpkUtilityTrovalogBean input = new DmpkUtilityTrovalogBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);		
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
				
		// Setto i filtri all'input del servizio
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setFiltriio(lXmlUtilitySerializer.bindXml(filtri));
		return input;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkUtilityTrovalogBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkUtilityTrovalog lservice = new DmpkUtilityTrovalog();
		StoreResultBean<DmpkUtilityTrovalogBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), LogOperazioniXmlBean.class.getName());
		

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), LogOperazioniXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());
		
	    if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		return bean;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkUtilityTrovalogBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		// Inizializzo l'OUTPUT		
		DmpkUtilityTrovalog lservice = new DmpkUtilityTrovalog();
		StoreResultBean<DmpkUtilityTrovalogBean> output = lservice.execute(getLocale(), loginBean, input);
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if(output.isInError()) {
					throw new StoreException(output);		
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
		}
		bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		return bean;
	}	
	
	private Map<String, String> createRemapConditions() {
		return new HashMap<String, String>();
	}
}