/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_ruoli_amm.bean.DmpkRuoliAmmDruoloammBean;
import it.eng.auriga.database.store.dmpk_ruoli_amm.bean.DmpkRuoliAmmIuruoloammBean;
import it.eng.auriga.database.store.dmpk_ruoli_amm.bean.DmpkRuoliAmmLoaddettruoloammBean;
import it.eng.auriga.database.store.dmpk_ruoli_amm.bean.DmpkRuoliAmmTrovaruoliammBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaRuoliAmministrativiBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaRuoliAmministrativiXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.client.DmpkRuoliAmmDruoloamm;
import it.eng.client.DmpkRuoliAmmIuruoloamm;
import it.eng.client.DmpkRuoliAmmLoaddettruoloamm;
import it.eng.client.DmpkRuoliAmmTrovaruoliamm;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AnagraficaRuoliAmministrativiDataSource")
public class AnagraficaRuoliAmministrativiDataSource extends AurigaAbstractFetchDatasource<AnagraficaRuoliAmministrativiBean>  {

	@Override
	public String getNomeEntita() {
		return "anagrafiche_ruoli_amministrativi";
	};

	@Override
	public PaginatorBean<AnagraficaRuoliAmministrativiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkRuoliAmmTrovaruoliammBean input =  createFetchInput(criteria, token, idUserLavoro);
		
		// Inizializzo l'OUTPUT
		DmpkRuoliAmmTrovaruoliamm lservice = new DmpkRuoliAmmTrovaruoliamm();
		StoreResultBean<DmpkRuoliAmmTrovaruoliammBean> output = lservice.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		// Leggo il result
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		// SETTO L'OUTPUT
		List<AnagraficaRuoliAmministrativiBean> data = new ArrayList<AnagraficaRuoliAmministrativiBean>();
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), AnagraficaRuoliAmministrativiBean.class, new AnagraficaRuoliAmministrativiXmlBeanDeserializationHelper(createRemapConditions()));
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<AnagraficaRuoliAmministrativiBean> lPaginatorBean = new PaginatorBean<AnagraficaRuoliAmministrativiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	@Override
	public AnagraficaRuoliAmministrativiBean get(AnagraficaRuoliAmministrativiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkRuoliAmmLoaddettruoloammBean input = new DmpkRuoliAmmLoaddettruoloammBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIdruoloio(bean.getIdRuolo() != null? bean.getIdRuolo() : null);

		// Inizializzo l'OUTPUT
		DmpkRuoliAmmLoaddettruoloamm lservice = new DmpkRuoliAmmLoaddettruoloamm();
		StoreResultBean<DmpkRuoliAmmLoaddettruoloammBean> output = lservice.execute(getLocale(), loginBean, input);

		// Leggo il result
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		// SETTO L'OUTPUT
		AnagraficaRuoliAmministrativiBean result = new AnagraficaRuoliAmministrativiBean();
		
		// Identificativo del ruolo
		result.setIdRuolo(output.getResultBean().getIdruoloio());
		
		// Descrizione del ruolo
		result.setDescrizioneRuolo(output.getResultBean().getDesruoloout());                   
		
		// (valori 1/0) Se 1 è un ruolo che si espleta limitatamente alla UO in cui lo si assume, se 0 è un ruolo che si espleta in tutto il soggetto produttore/AOO
		result.setFlgEspletaSoloAlleUO(output.getResultBean().getFlglimuoout() != null && output.getResultBean().getFlglimuoout().intValue() == 1 ? "1" : "0");
		
		// Codice che identifica il ruolo in un eventuale sistema esterno di provenienza
		result.setCiProvRuolo(output.getResultBean().getCiprovruoloout());
		
		// (valori 1/0) Indicatore di ruolo riservato dal sistema e non modificabile/cancellabile da GUI
		result.setRecProtetto(output.getResultBean().getFlglockedout() != null && output.getResultBean().getFlglockedout().intValue() == 1 ? "1" : "0");
			
		// Lista dei ruoli inclusi
		if (output.getResultBean().getXmlruoliinclusiout()!= null){
			List<String> listaRuoliInclusi = new ArrayList<String>();
			for (SimpleKeyValueBean lIdRuolo : XmlUtility.recuperaListaSemplice(output.getResultBean().getXmlruoliinclusiout())) {
				listaRuoliInclusi.add(lIdRuolo.getKey());
			}
			result.setListaRuoliInclusi(listaRuoliInclusi);
		}		
		return result;
	}

	@Override
	public AnagraficaRuoliAmministrativiBean add(AnagraficaRuoliAmministrativiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkRuoliAmmIuruoloammBean input = new DmpkRuoliAmmIuruoloammBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				
		// DesRuoloIn
		input.setDesruoloin(bean.getDescrizioneRuolo());
				
		// FlgLimUOIn
		if(bean.getFlgEspletaSoloAlleUO() != null && !bean.getFlgEspletaSoloAlleUO().equalsIgnoreCase("")){
			if(bean.getFlgEspletaSoloAlleUO().equalsIgnoreCase("1"))
				input.setFlglimuoin(1);
			else
				input.setFlglimuoin(0);
		}
		else{
			input.setFlglimuoin(null);
		}
			
		// CIProvRuoloIn
		input.setCiprovruoloin(bean.getCiProvRuolo());
				
		// FlgLockedIn
		if(bean.getRecProtetto() != null && !bean.getRecProtetto().equalsIgnoreCase("")){			
			if(bean.getRecProtetto().equalsIgnoreCase("1"))
				input.setFlglockedin(1);
			else
				input.setFlglockedin(0);
		}
		else{
			input.setFlglockedin(null);
		}
			
		// FlgModRuoliInclusiIn
		input.setFlgmodruoliinclusiin("C");
		
		// XMLRuoliInclusiIn
		if(bean.getListaRuoliInclusi() != null) {
			List<SimpleValueBean> listaIdRuoliInclusi     = new ArrayList<SimpleValueBean>();	
			for (String idRuolo : bean.getListaRuoliInclusi()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(idRuolo);
				listaIdRuoliInclusi.add(lSimpleValueBean);
			}
			input.setXmlruoliinclusiin(new XmlUtilitySerializer().bindXmlList(listaIdRuoliInclusi));			
		}
		
		// Eseguo il servizio
		DmpkRuoliAmmIuruoloamm lservice = new DmpkRuoliAmmIuruoloamm();
		StoreResultBean<DmpkRuoliAmmIuruoloammBean> result = lservice.execute(getLocale(), loginBean, input);

		// Leggo il result
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		
		bean.setIdRuolo(result.getResultBean().getIdruoloio());
		
		return bean;		
	}

	@Override
	public AnagraficaRuoliAmministrativiBean update(AnagraficaRuoliAmministrativiBean bean, AnagraficaRuoliAmministrativiBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkRuoliAmmIuruoloammBean input = new DmpkRuoliAmmIuruoloammBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				
		// IdRuoloIO
		input.setIdruoloio(bean.getIdRuolo() != null ? bean.getIdRuolo() : null);
		
		// DesRuoloIn
		input.setDesruoloin(bean.getDescrizioneRuolo());
						
		// FlgLimUOIn
		if(bean.getFlgEspletaSoloAlleUO() != null && !bean.getFlgEspletaSoloAlleUO().equalsIgnoreCase("")){
			if(bean.getFlgEspletaSoloAlleUO().equalsIgnoreCase("1"))
				input.setFlglimuoin(1);
			else
				input.setFlglimuoin(0);
		}
		else{
			input.setFlglimuoin(null);
		}
				
		// CIProvRuoloIn
		input.setCiprovruoloin(bean.getCiProvRuolo());
						
		// FlgLockedIn
		if(bean.getRecProtetto() != null && !bean.getRecProtetto().equalsIgnoreCase("")){			
			if(bean.getRecProtetto().equalsIgnoreCase("1"))
				input.setFlglockedin(1);
			else
				input.setFlglockedin(0);
		}
		else{
			input.setFlglockedin(null);
		}
				
		// FlgModRuoliInclusiIn
		input.setFlgmodruoliinclusiin("C");
		
		// XMLRuoliInclusiIn
		if(bean.getListaRuoliInclusi() != null) {
			List<SimpleValueBean> listaIdRuoliInclusi     = new ArrayList<SimpleValueBean>();	
			for (String idRuolo : bean.getListaRuoliInclusi()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(idRuolo);
				listaIdRuoliInclusi.add(lSimpleValueBean);
			}
			input.setXmlruoliinclusiin(new XmlUtilitySerializer().bindXmlList(listaIdRuoliInclusi));			
		}
		
		// Eseguo il servizio
		DmpkRuoliAmmIuruoloamm lservice = new DmpkRuoliAmmIuruoloamm();
		StoreResultBean<DmpkRuoliAmmIuruoloammBean> result = lservice.execute(getLocale(), loginBean, input);

		// Leggo il result
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		return bean;
	}

	@Override
	public AnagraficaRuoliAmministrativiBean remove(AnagraficaRuoliAmministrativiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkRuoliAmmDruoloammBean input = new DmpkRuoliAmmDruoloammBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(new Integer(1));		
		input.setIdruoloin(bean.getIdRuolo() != null ? bean.getIdRuolo() : null);
		input.setFlgcancfisicain(null);
		
		// Eseguo il servizio
		DmpkRuoliAmmDruoloamm lservice = new DmpkRuoliAmmDruoloamm();
		StoreResultBean<DmpkRuoliAmmDruoloammBean> output = lservice.execute(getLocale(), loginBean, input);
		
		// Leggo il result
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	protected DmpkRuoliAmmTrovaruoliammBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		// Inizializzo l'INPUT		
		DmpkRuoliAmmTrovaruoliammBean input = new DmpkRuoliAmmTrovaruoliammBean();
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {				
				if ("descrizioneRuolo".equals(crit.getFieldName())) {
					input.setDesruoloio(getTextFilterValue(crit));
				} 
				else if ("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						BigDecimal flgIncludiAnnullati = new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0);
						input.setFlginclannullatiio(flgIncludiAnnullati);
					}
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
		
		return input;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkRuoliAmmTrovaruoliammBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		// Inizializzo l'OUTPUT		
		DmpkRuoliAmmTrovaruoliamm lservice = new DmpkRuoliAmmTrovaruoliamm();
		StoreResultBean<DmpkRuoliAmmTrovaruoliammBean> output = lservice.execute(getLocale(), loginBean, input);
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

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkRuoliAmmTrovaruoliammBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkRuoliAmmTrovaruoliamm lservice = new DmpkRuoliAmmTrovaruoliamm();
		StoreResultBean<DmpkRuoliAmmTrovaruoliammBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), AnagraficaRuoliAmministrativiBean.class.getName());
		

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), AnagraficaRuoliAmministrativiXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());
		
	    if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		return bean;
	}

	private Map<String, String> createRemapConditions() {
		return new HashMap<String, String>();
	}
}