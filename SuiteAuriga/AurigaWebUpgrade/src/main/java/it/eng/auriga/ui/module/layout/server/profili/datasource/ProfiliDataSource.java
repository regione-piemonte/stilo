/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDprofiloBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIuprofiloBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettprofiloBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovagruppiprivilegiBean;

import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.XmlDatiProfiloOutBean;
import it.eng.auriga.ui.module.layout.server.profili.datasource.bean.ProfiliBean;
import it.eng.auriga.ui.module.layout.server.profili.datasource.bean.ProfiliXmlBean;
import it.eng.auriga.ui.module.layout.server.profili.datasource.bean.ProfiliXmlBeanDeserializationHelper;
import it.eng.client.DmpkDefSecurityDprofilo;
import it.eng.client.DmpkDefSecurityIuprofilo;
import it.eng.client.DmpkDefSecurityLoaddettprofilo;
import it.eng.client.DmpkDefSecurityTrovagruppiprivilegi;

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

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.util.ArrayList;
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

@Datasource(id="ProfiliDataSource")
public class ProfiliDataSource extends AurigaAbstractFetchDatasource<ProfiliBean>{

	private static final Logger log = Logger.getLogger(ProfiliDataSource.class);

	@Override
	public String getNomeEntita() {
		return "profili";
	};

	@Override
	public ProfiliBean get(ProfiliBean bean) throws Exception {
		// TODO Auto-generated method stub
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityLoaddettprofiloBean input = new DmpkDefSecurityLoaddettprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprofiloio(new BigDecimal(bean.getIdProfilo()));
		
		DmpkDefSecurityLoaddettprofilo dmpkDefSecurityLoaddettprofilo = new DmpkDefSecurityLoaddettprofilo();
		StoreResultBean<DmpkDefSecurityLoaddettprofiloBean> output = dmpkDefSecurityLoaddettprofilo.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		bean.setNomeProfilo(output.getResultBean().getNomeprofiloio());
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmldatiprofiloout())) {
			XmlDatiProfiloOutBean scXmlDatiProfilo = new XmlDatiProfiloOutBean();
		    XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		    scXmlDatiProfilo = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmldatiprofiloout(), XmlDatiProfiloOutBean.class);      	       
		    if(scXmlDatiProfilo != null) {		    	   
		    	    bean.setLivMaxRiservatezza(StringUtils.isNotBlank(scXmlDatiProfilo.getLivMaxRiservatezza()) ? "1".equals(scXmlDatiProfilo.getLivMaxRiservatezza()) : null);
					bean.setFlgVisibTuttiRiservati(StringUtils.isNotBlank(scXmlDatiProfilo.getFlgVisibTuttiRiservati()) ? "1".equals(scXmlDatiProfilo.getFlgVisibTuttiRiservati()) : null);
					bean.setAccessoDocIndipACL(scXmlDatiProfilo.getAccessoDocIndipACL());
					bean.setAccessoFolderIndipACL(scXmlDatiProfilo.getAccessoFolderIndipACL());
					bean.setAccessoWorkspaceIndipACL(scXmlDatiProfilo.getAccessoWorkspaceIndipACL());
					bean.setAccessoDocIndipUserAbil(scXmlDatiProfilo.getAccessoDocIndipUserAbil());
					bean.setAccessoFolderIndipUserAbil(scXmlDatiProfilo.getAccessoFolderIndipUserAbil());
					bean.setPresentiPrivSuFunzioni(StringUtils.isNotBlank(scXmlDatiProfilo.getPresentiPrivSuFunzioni()) ? "1".equals(scXmlDatiProfilo.getPresentiPrivSuFunzioni()) : false);		    	
			}					
		}	
		
		return bean;
	}	
	
	@Override
	public PaginatorBean<ProfiliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		/*
		// Inizializzo l'INPUT
		DmpkDefSecurityTrovagruppiprivilegiBean input =  createFetchInput(criteria, token, idUserLavoro);
	
        // Inizializzo l'OUTPUT
		DmpkDefSecurityTrovagruppiprivilegi lservice  = new DmpkDefSecurityTrovagruppiprivilegi();
		StoreResultBean<DmpkDefSecurityTrovagruppiprivilegiBean> output = lservice.execute(getLocale(), loginBean, input);
		*/
		boolean overflow = false;
		
		/*
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		*/
		// SETTO L'OUTPUT
		List<ProfiliBean> data = new ArrayList<ProfiliBean>();
		/*
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), ProfiliBean.class, new ProfiliBeanDeserializationHelper(createRemapConditions()));
		}
		
		*/
		
		/*
		// li carico momentaneamente fissi
		ProfiliBean lProfiliBean1 = new ProfiliBean();
		lProfiliBean1.setIdProfilo("1");
		lProfiliBean1.setNomeProfilo("Utente trasversale");
		data.add(lProfiliBean1);
		
		ProfiliBean lProfiliBean2 = new ProfiliBean();
		lProfiliBean2.setIdProfilo("2");
		lProfiliBean2.setNomeProfilo("UTENTE BASE");
		data.add(lProfiliBean2);
		
		ProfiliBean lProfiliBean3 = new ProfiliBean();
		lProfiliBean3.setIdProfilo("3");
		lProfiliBean3.setNomeProfilo("UTENTE AMMINISTRATORE");
		data.add(lProfiliBean3);
		*/
		
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
				
		PaginatorBean<ProfiliBean> lPaginatorBean = new PaginatorBean<ProfiliBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

   @Override
	public ProfiliBean update(ProfiliBean bean, ProfiliBean oldvalue) throws Exception {
		// TODO Auto-generated method stub
	    AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityIuprofiloBean input = new DmpkDefSecurityIuprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprofiloio(StringUtils.isNotBlank(bean.getIdProfilo()) ? new BigDecimal(bean.getIdProfilo()) : null);
		input.setNomeprofiloin(bean.getNomeProfilo());
		
		
		XmlDatiProfiloOutBean scXmlDatiProfilo = new XmlDatiProfiloOutBean();
		scXmlDatiProfilo.setLivMaxRiservatezza((bean.getLivMaxRiservatezza() != null && bean.getLivMaxRiservatezza()) ? "1" : "");
		scXmlDatiProfilo.setFlgVisibTuttiRiservati((bean.getFlgVisibTuttiRiservati() != null && bean.getFlgVisibTuttiRiservati()) ? "1" : "");
		scXmlDatiProfilo.setAccessoDocIndipACL(bean.getAccessoDocIndipACL() != null ? bean.getAccessoDocIndipACL() : "");
		scXmlDatiProfilo.setAccessoFolderIndipACL(bean.getAccessoFolderIndipACL() != null ? bean.getAccessoFolderIndipACL() : "");
		scXmlDatiProfilo.setAccessoWorkspaceIndipACL(bean.getAccessoWorkspaceIndipACL() != null ? bean.getAccessoWorkspaceIndipACL() : "");
		scXmlDatiProfilo.setAccessoDocIndipUserAbil(bean.getAccessoDocIndipUserAbil() != null ? bean.getAccessoDocIndipUserAbil() : "");
		scXmlDatiProfilo.setAccessoFolderIndipUserAbil(bean.getAccessoFolderIndipUserAbil() != null ? bean.getAccessoFolderIndipUserAbil() : "");
		scXmlDatiProfilo.setPresentiPrivSuFunzioni((bean.getPresentiPrivSuFunzioni() != null && bean.getPresentiPrivSuFunzioni()) ? "1" : "");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
	    input.setXmldatiprofiloin(lXmlUtilitySerializer.bindXml(scXmlDatiProfilo));
		
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityIuprofilo service = new DmpkDefSecurityIuprofilo();
		StoreResultBean<DmpkDefSecurityIuprofiloBean> result = service.execute(getLocale(), loginBean, input);
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
	public ProfiliBean add(ProfiliBean bean) throws Exception {
        // TODO Auto-generated method stub		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityIuprofiloBean input = new DmpkDefSecurityIuprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomeprofiloin(bean.getNomeProfilo());

		XmlDatiProfiloOutBean scXmlDatiProfilo = new XmlDatiProfiloOutBean();
		scXmlDatiProfilo.setLivMaxRiservatezza((bean.getLivMaxRiservatezza() != null && bean.getLivMaxRiservatezza()) ? "1" : "");
		scXmlDatiProfilo.setFlgVisibTuttiRiservati((bean.getFlgVisibTuttiRiservati() != null && bean.getFlgVisibTuttiRiservati()) ? "1" : "");
		scXmlDatiProfilo.setAccessoDocIndipACL(bean.getAccessoDocIndipACL() != null ? bean.getAccessoDocIndipACL() : "");
		scXmlDatiProfilo.setAccessoFolderIndipACL(bean.getAccessoFolderIndipACL() != null ? bean.getAccessoFolderIndipACL() : "");
		scXmlDatiProfilo.setAccessoWorkspaceIndipACL(bean.getAccessoWorkspaceIndipACL() != null ? bean.getAccessoWorkspaceIndipACL() : "");
		scXmlDatiProfilo.setAccessoDocIndipUserAbil(bean.getAccessoDocIndipUserAbil() != null ? bean.getAccessoDocIndipUserAbil() : "");
		scXmlDatiProfilo.setAccessoFolderIndipUserAbil(bean.getAccessoFolderIndipUserAbil() != null ? bean.getAccessoFolderIndipUserAbil() : "");
		scXmlDatiProfilo.setPresentiPrivSuFunzioni((bean.getPresentiPrivSuFunzioni() != null && bean.getPresentiPrivSuFunzioni()) ? "1" : "");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
	    input.setXmldatiprofiloin(lXmlUtilitySerializer.bindXml(scXmlDatiProfilo));
	    
		// Inizializzo l'OUTPUT
		DmpkDefSecurityIuprofilo service = new DmpkDefSecurityIuprofilo();
		StoreResultBean<DmpkDefSecurityIuprofiloBean> result = service.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdProfilo(result.getResultBean().getIdprofiloio().toString());
		
		return bean;		
	}

	@Override
	public ProfiliBean remove(ProfiliBean bean) throws Exception {
		
		// TODO Auto-generated method stub
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityDprofiloBean input = new DmpkDefSecurityDprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprofiloin(StringUtils.isNotBlank(bean.getIdProfilo()) ? new BigDecimal(bean.getIdProfilo()) : null);
		input.setNomeprofiloin(bean.getNomeProfilo());
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
		
		if(StringUtils.isNotBlank(getExtraparams().get("flgIgnoreWarning"))) {
			input.setFlgignorewarningin(new Integer(getExtraparams().get("flgIgnoreWarning")));
		}
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityDprofilo service = new DmpkDefSecurityDprofilo();
		StoreResultBean<DmpkDefSecurityDprofiloBean> result = service.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(result.getDefaultMessage())) {
			if(result.isInError()) {
				throw new StoreException(result);		
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setWarning(result.getResultBean().getWarningmsgout());
		
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
	protected DmpkDefSecurityTrovagruppiprivilegiBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		String nomeSubProfilo = null;
        
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){					
				if("nomeSubProfilo".equals(crit.getFieldName())) {
					nomeSubProfilo = getTextFilterValue(crit);	
				} 			
			}
		}
		
		// Inizializzo l'INPUT		
		DmpkDefSecurityTrovagruppiprivilegiBean input = new DmpkDefSecurityTrovagruppiprivilegiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);		
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setNomegruppoprivio(nomeSubProfilo);
		
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
		DmpkDefSecurityTrovagruppiprivilegiBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityTrovagruppiprivilegi lservice = new DmpkDefSecurityTrovagruppiprivilegi();
		StoreResultBean<DmpkDefSecurityTrovagruppiprivilegiBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ProfiliXmlBean.class.getName());
		

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), ProfiliXmlBeanDeserializationHelper.class);

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
		DmpkDefSecurityTrovagruppiprivilegiBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		// Inizializzo l'OUTPUT		
		DmpkDefSecurityTrovagruppiprivilegi lservice = new DmpkDefSecurityTrovagruppiprivilegi();
		StoreResultBean<DmpkDefSecurityTrovagruppiprivilegiBean> output = lservice.execute(getLocale(), loginBean, input);
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