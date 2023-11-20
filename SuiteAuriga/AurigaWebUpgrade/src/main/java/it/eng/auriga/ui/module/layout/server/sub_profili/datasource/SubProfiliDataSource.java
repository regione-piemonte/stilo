/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDgruppoprivilegiBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIugruppoprivilegiBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettgruppoprivilegiBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovagruppiprivilegiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.sub_profili.datasource.bean.DettGruppoPrivPrivilegiXmlBean;
import it.eng.auriga.ui.module.layout.server.sub_profili.datasource.bean.PrivilegiFunzioneBean;
import it.eng.auriga.ui.module.layout.server.sub_profili.datasource.bean.SubProfiliBean;
import it.eng.auriga.ui.module.layout.server.sub_profili.datasource.bean.SubProfiliXmlBeanDeserializationHelper;
import it.eng.client.DmpkDefSecurityDgruppoprivilegi;
import it.eng.client.DmpkDefSecurityIugruppoprivilegi;
import it.eng.client.DmpkDefSecurityLoaddettgruppoprivilegi;
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

@Datasource(id="SubProfiliDataSource")
public class SubProfiliDataSource extends AurigaAbstractFetchDatasource<SubProfiliBean>{

	private static final Logger log = Logger.getLogger(SubProfiliDataSource.class);

	@Override
	public String getNomeEntita() {
		return "sub_profili";
	};

	@Override
	public SubProfiliBean get(SubProfiliBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityLoaddettgruppoprivilegiBean input = new DmpkDefSecurityLoaddettgruppoprivilegiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdgruppoprivio(bean.getIdGruppoPriv()!=null ? bean.getIdGruppoPriv() : null);
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityLoaddettgruppoprivilegi lservizio = new DmpkDefSecurityLoaddettgruppoprivilegi();
		StoreResultBean<DmpkDefSecurityLoaddettgruppoprivilegiBean> output = lservizio.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
				
		SubProfiliBean result = new SubProfiliBean();		
		result.setIdGruppoPriv(output.getResultBean().getIdgruppoprivio());
		result.setNomeGruppoPriv(output.getResultBean().getNomegruppoprivio());
		result.setNoteGruppoPriv(output.getResultBean().getNotegruppoprivout());		
		result.setCiProvGruppoPriv(output.getResultBean().getCiprovgruppoprivout());
		result.setFlglockedPriv(output.getResultBean().getFlglockedout());
		

		// Leggo i privilegi
		List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegi = new ArrayList<DettGruppoPrivPrivilegiXmlBean>();
		if (output.getResultBean().getXmlprivilegiout() != null) {
			listaPrivilegi = XmlListaUtility.recuperaLista(output.getResultBean().getXmlprivilegiout(), DettGruppoPrivPrivilegiXmlBean.class);
		}
				
        if(listaPrivilegi!=null && listaPrivilegi.size()>0) {        	
        	
        	// Estraggo i privilegi di tipo F = Funzionalità del sistema
        	List<PrivilegiFunzioneBean> listaPrivilegiFunzionalitaSistema = new ArrayList<PrivilegiFunzioneBean>();
        	listaPrivilegiFunzionalitaSistema = getPrivilegiFunzionalitaSistema(listaPrivilegi);        	
        	bean.setListaFunzionalitaSistema(listaPrivilegiFunzionalitaSistema);
        		        	
        	// Estraggo i privilegi di tipo TD = Tipo di documento
        	List<PrivilegiFunzioneBean> listaPrivilegiTipoDcumento = new ArrayList<PrivilegiFunzioneBean>();
        	listaPrivilegiTipoDcumento = getPrivilegiTipoDcumento(listaPrivilegi);        	
        	bean.setListaTipoDocumento(listaPrivilegiTipoDcumento);
        	        	        	
        	// Estraggo i privilegi di tipo C =	Classificazione
        	List<PrivilegiFunzioneBean> listaPrivilegiClassificazione = new ArrayList<PrivilegiFunzioneBean>();
        	listaPrivilegiClassificazione = getPrivilegiClassificazione(listaPrivilegi);        	
        	bean.setListaClassificazione(listaPrivilegiClassificazione);
        	        	
        	// Estraggo i privilegi di tipo TR = Tipo di registrazione (protocollo o repertorio o altro)
        	List<PrivilegiFunzioneBean> listaPrivilegiTipoRegistrazione = new ArrayList<PrivilegiFunzioneBean>();
        	listaPrivilegiTipoRegistrazione = getPrivilegiTipoRegistrazione(listaPrivilegi);        	
        	bean.setListaTipoRegistrazione(listaPrivilegiTipoRegistrazione);    
        	
        	// Estraggo i privilegi di tipo TP = Tipo di processo/procedimento
        	List<PrivilegiFunzioneBean> listaPrivilegiTipoProcessoProcedimento = new ArrayList<PrivilegiFunzioneBean>();
        	listaPrivilegiTipoProcessoProcedimento = getPrivilegiTipoProcessoProcedimento(listaPrivilegi);        	
        	bean.setListaTipoProcessoProcedimento(listaPrivilegiTipoProcessoProcedimento);
        	
        	// Estraggo i privilegi di tipo TF = Tipo di folder
        	List<PrivilegiFunzioneBean> listaPrivilegiTipoFolder = new ArrayList<PrivilegiFunzioneBean>();
        	listaPrivilegiTipoFolder = getPrivilegiTipoFolder(listaPrivilegi);        	
        	bean.setListaTipoFolder(listaPrivilegiTipoFolder);
        }
		return bean;
	}	
	
	
	@Override
	public PaginatorBean<SubProfiliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityTrovagruppiprivilegiBean input =  createFetchInput(criteria, token, idUserLavoro);
	
        // Inizializzo l'OUTPUT
		DmpkDefSecurityTrovagruppiprivilegi lservice  = new DmpkDefSecurityTrovagruppiprivilegi();
		StoreResultBean<DmpkDefSecurityTrovagruppiprivilegiBean> output = lservice.execute(getLocale(), loginBean, input);
		
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
		List<SubProfiliBean> data = new ArrayList<SubProfiliBean>();
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), SubProfiliBean.class, new SubProfiliXmlBeanDeserializationHelper(createRemapConditions()));
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
				
		PaginatorBean<SubProfiliBean> lPaginatorBean = new PaginatorBean<SubProfiliBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

   @Override
	public SubProfiliBean update(SubProfiliBean bean, SubProfiliBean oldvalue) throws Exception {
		// TODO Auto-generated method stub
	    AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityIugruppoprivilegiBean input = new DmpkDefSecurityIugruppoprivilegiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdgruppoprivio(bean.getIdGruppoPriv()!=null ? bean.getIdGruppoPriv() : null);
		input.setNomegruppoprivin(bean.getNomeGruppoPriv());
		input.setNotegruppoprivin(bean.getNoteGruppoPriv());
		
		
		// Salvo tutti i privilegi
		List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegi = new ArrayList<DettGruppoPrivPrivilegiXmlBean>();
		
		// Salvo i privilegi di tipo F = Funzionalità del sistema		
		setPrivilegi(listaPrivilegi, bean.getListaFunzionalitaSistema());
		
		// Salvo i privilegi di tipo TD = Tipo di documento
		setPrivilegi(listaPrivilegi, bean.getListaTipoDocumento());
		
    	// Salvo i privilegi di tipo C =	Classificazione
		setPrivilegi(listaPrivilegi, bean.getListaClassificazione());
		
    	// Salvo i privilegi di tipo TR = Tipo di registrazione (protocollo o repertorio o altro)
		setPrivilegi(listaPrivilegi, bean.getListaTipoRegistrazione());
		
		// Salvo i privilegi di tipo TP = Tipo di processo/procedimento
		setPrivilegi(listaPrivilegi, bean.getListaTipoProcessoProcedimento());
		
    	// Salvo i privilegi di tipo TF = Tipo di folder
		setPrivilegi(listaPrivilegi, bean.getListaTipoFolder());
		
		if( listaPrivilegi!=null && listaPrivilegi.size()>0){
			input.setXmlprivilegiin(new XmlUtilitySerializer().bindXmlList(listaPrivilegi));			
		}
		
		input.setFlgmodprivilegiin("C");
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityIugruppoprivilegi service = new DmpkDefSecurityIugruppoprivilegi();
		StoreResultBean<DmpkDefSecurityIugruppoprivilegiBean> result = service.execute(getLocale(), loginBean, input);
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
	public SubProfiliBean add(SubProfiliBean bean) throws Exception {
        // TODO Auto-generated method stub		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityIugruppoprivilegiBean input = new DmpkDefSecurityIugruppoprivilegiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomegruppoprivin(bean.getNomeGruppoPriv());
		input.setNotegruppoprivin(bean.getNoteGruppoPriv());

		// Salvo tutti i privilegi
		List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegi = new ArrayList<DettGruppoPrivPrivilegiXmlBean>();
				
		// Salvo i privilegi di tipo F = Funzionalità del sistema		
		setPrivilegi(listaPrivilegi, bean.getListaFunzionalitaSistema());
				
		// Salvo i privilegi di tipo TD = Tipo di documento
		setPrivilegi(listaPrivilegi, bean.getListaTipoDocumento());
				
		// Salvo i privilegi di tipo C =	Classificazione
		setPrivilegi(listaPrivilegi, bean.getListaClassificazione());
				
		// Salvo i privilegi di tipo TR = Tipo di registrazione (protocollo o repertorio o altro)
		setPrivilegi(listaPrivilegi, bean.getListaTipoRegistrazione());
				
		// Salvo i privilegi di tipo TP = Tipo di processo/procedimento
		setPrivilegi(listaPrivilegi, bean.getListaTipoProcessoProcedimento());
				
	    // Salvo i privilegi di tipo TF = Tipo di folder
		setPrivilegi(listaPrivilegi, bean.getListaTipoFolder());
				
		if( listaPrivilegi!=null && listaPrivilegi.size()>0){
			input.setXmlprivilegiin(new XmlUtilitySerializer().bindXmlList(listaPrivilegi));
			input.setFlgmodprivilegiin("C");
		}	
				
		// Inizializzo l'OUTPUT
		DmpkDefSecurityIugruppoprivilegi service = new DmpkDefSecurityIugruppoprivilegi();
		StoreResultBean<DmpkDefSecurityIugruppoprivilegiBean> result = service.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdGruppoPriv(result.getResultBean().getIdgruppoprivio());		
		
		return bean;		
	}

	@Override
	public SubProfiliBean remove(SubProfiliBean bean) throws Exception {
		
		// TODO Auto-generated method stub
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityDgruppoprivilegiBean input = new DmpkDefSecurityDgruppoprivilegiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdgruppoprivin(bean.getIdGruppoPriv()!=null ? bean.getIdGruppoPriv() : null);
		input.setFlgcancfisicain(null);
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityDgruppoprivilegi service = new DmpkDefSecurityDgruppoprivilegi();
		StoreResultBean<DmpkDefSecurityDgruppoprivilegiBean> result = service.execute(getLocale(), loginBean, input);
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
		
		// Inizializzo l'INPUT		
		DmpkDefSecurityTrovagruppiprivilegiBean input = new DmpkDefSecurityTrovagruppiprivilegiBean();
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){					
				if("nomeSubProfilo".equals(crit.getFieldName())) {
					input.setNomegruppoprivio(getTextFilterValue(crit));
					
				}  else if ("flgInclAnnullati".equals(crit.getFieldName())) {
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
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), SubProfiliBean.class.getName());
		

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), SubProfiliXmlBeanDeserializationHelper.class);

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
	
	private List<PrivilegiFunzioneBean> getPrivilegiTipoRegistrazione(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiFunzioneBean> lList = new ArrayList<PrivilegiFunzioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (DettGruppoPrivPrivilegiXmlBean privilegioBean : listaPrivilegiIn) {
				if(StringUtils.isNotBlank(privilegioBean.getTipoOggetto())) {
					// Se il tipo e' TR = Tipo di registrazione (protocollo o repertorio o altro)
					if(privilegioBean.getTipoOggetto().equalsIgnoreCase("TR")){
						PrivilegiFunzioneBean privilegio = new PrivilegiFunzioneBean();
						privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
						privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
						privilegio.setNriLivelliClassificazionePriv(privilegioBean.getNriLivelloClassificazione());
						privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());	
						
						if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
							String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
							if(values!=null && values.length>0){
								List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
								for (String item : values) {
									listaPrivilegiOggettoPriv.add(item);
								}
								privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
							}
						}
						lList.add(privilegio);
					}
				}
			}			
		}			
		return lList;		
	}
	
	private List<PrivilegiFunzioneBean> getPrivilegiClassificazione(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiFunzioneBean> lList = new ArrayList<PrivilegiFunzioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (DettGruppoPrivPrivilegiXmlBean privilegioBean : listaPrivilegiIn) {
				if(StringUtils.isNotBlank(privilegioBean.getTipoOggetto())) {
					// Se il tipo e' C = Classificazione
					if(privilegioBean.getTipoOggetto().equalsIgnoreCase("C")){
						PrivilegiFunzioneBean privilegio = new PrivilegiFunzioneBean();
						privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
						privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
						privilegio.setNriLivelliClassificazionePriv(privilegioBean.getNriLivelloClassificazione());
						privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());	
						
						if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
							String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
							if(values!=null && values.length>0){
								List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
								for (String item : values) {
									listaPrivilegiOggettoPriv.add(item);
								}
								privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
							}
						}
						lList.add(privilegio);
					}
				}
			}			
		}			
		return lList;		
	}
	
	private List<PrivilegiFunzioneBean> getPrivilegiTipoFolder(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiFunzioneBean> lList = new ArrayList<PrivilegiFunzioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (DettGruppoPrivPrivilegiXmlBean privilegioBean : listaPrivilegiIn) {
				if(StringUtils.isNotBlank(privilegioBean.getTipoOggetto())) {
					// Se il tipo e' TF	= Tipo di folder
					if(privilegioBean.getTipoOggetto().equalsIgnoreCase("TF")){
						PrivilegiFunzioneBean privilegio = new PrivilegiFunzioneBean();
						privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
						privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
						privilegio.setNriLivelliClassificazionePriv(privilegioBean.getNriLivelloClassificazione());
						privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());	
						
						if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
							String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
							if(values!=null && values.length>0){
								List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
								for (String item : values) {
									listaPrivilegiOggettoPriv.add(item);
								}
								privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
							}
						}
						lList.add(privilegio);
					}
				}
			}			
		}			
		return lList;		
	}
	
	private List<PrivilegiFunzioneBean> getPrivilegiTipoDcumento(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiFunzioneBean> lList = new ArrayList<PrivilegiFunzioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (DettGruppoPrivPrivilegiXmlBean privilegioBean : listaPrivilegiIn) {
				if(StringUtils.isNotBlank(privilegioBean.getTipoOggetto())) {
					// Se il tipo e' TP = Tipo di processo/procedimento
					if(privilegioBean.getTipoOggetto().equalsIgnoreCase("TD")){
						PrivilegiFunzioneBean privilegio = new PrivilegiFunzioneBean();
						privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
						privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
						privilegio.setNriLivelliClassificazionePriv(privilegioBean.getNriLivelloClassificazione());
						privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());	
						
						if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
							String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
							if(values!=null && values.length>0){
								List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
								for (String item : values) {
									listaPrivilegiOggettoPriv.add(item);
								}
								privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
							}
						}
						lList.add(privilegio);
					}
				}
			}			
		}			
		return lList;		
	}
	
	private List<PrivilegiFunzioneBean> getPrivilegiTipoProcessoProcedimento(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiFunzioneBean> lList = new ArrayList<PrivilegiFunzioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (DettGruppoPrivPrivilegiXmlBean privilegioBean : listaPrivilegiIn) {
				if(StringUtils.isNotBlank(privilegioBean.getTipoOggetto())) {
					// Se il tipo e' TP = Tipo di processo/procedimento
					if(privilegioBean.getTipoOggetto().equalsIgnoreCase("TP")){
						PrivilegiFunzioneBean privilegio = new PrivilegiFunzioneBean();
						privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
						privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
						privilegio.setNriLivelliClassificazionePriv(privilegioBean.getNriLivelloClassificazione());
						privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());
						
						if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
							String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
							if(values!=null && values.length>0){
								List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
								for (String item : values) {
									listaPrivilegiOggettoPriv.add(item);
								}
								privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
							}
						}
						lList.add(privilegio);
					}
				}
			}			
		}			
		return lList;		
	}
	
	private List<PrivilegiFunzioneBean> getPrivilegiFunzionalitaSistema(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiIn)  throws Exception  {
		List<PrivilegiFunzioneBean> lList = new ArrayList<PrivilegiFunzioneBean>();
		if (listaPrivilegiIn!=null && listaPrivilegiIn.size()>0){
			for (DettGruppoPrivPrivilegiXmlBean privilegioBean : listaPrivilegiIn) {
				if(StringUtils.isNotBlank(privilegioBean.getTipoOggetto())) {
					// Se il tipo e' F = Funzionalità del sistema
					if(privilegioBean.getTipoOggetto().equalsIgnoreCase("F")){
						PrivilegiFunzioneBean privilegio = new PrivilegiFunzioneBean();
						privilegio.setTipoOggettoPriv(privilegioBean.getTipoOggetto());
						privilegio.setCodiceOggettoPriv(privilegioBean.getCodiceOggetto());
						privilegio.setNriLivelliClassificazionePriv(privilegioBean.getNriLivelloClassificazione());						
						privilegio.setDenominazioneOggettoPriv(privilegioBean.getDenominazioneOggetto());	
						
						if(StringUtils.isNotBlank(privilegioBean.getListaCodiciPrivilegiOggetto())){
							String[] values = privilegioBean.getListaCodiciPrivilegiOggetto().split(";");							
							if(values!=null && values.length>0){
								List<String> listaPrivilegiOggettoPriv = new ArrayList<String>();
								for (String item : values) {
									listaPrivilegiOggettoPriv.add(item);
								}
								privilegio.setListaPrivilegiOggettoPriv(listaPrivilegiOggettoPriv);
							}
						}
						lList.add(privilegio);
					}
				}
			}			
		}	
		return lList;
	}

	public void setPrivilegi(List<DettGruppoPrivPrivilegiXmlBean> listaPrivilegiOut,  List<PrivilegiFunzioneBean> listaPrivilegiFunzioneIn) throws Exception {
		   
		   if (listaPrivilegiFunzioneIn!=null && listaPrivilegiFunzioneIn.size()>0){
			  	for (PrivilegiFunzioneBean privilegioBean : listaPrivilegiFunzioneIn) {
						DettGruppoPrivPrivilegiXmlBean dettGruppoPrivPrivilegiXmlBean = new DettGruppoPrivPrivilegiXmlBean();
						dettGruppoPrivPrivilegiXmlBean.setTipoOggetto(privilegioBean.getTipoOggettoPriv());                                // -- 1: Indica il tipo di oggetto
						dettGruppoPrivPrivilegiXmlBean.setCodiceOggetto(privilegioBean.getCodiceOggettoPriv());                            // -- 2: Codice identificativo dell'oggetto
						dettGruppoPrivPrivilegiXmlBean.setDenominazioneOggetto(privilegioBean.getDenominazioneOggettoPriv());              // -- 3: Descrizione/denominazione dell'oggetto
						dettGruppoPrivPrivilegiXmlBean.setNriLivelloClassificazione(privilegioBean.getNriLivelliClassificazionePriv());    // -- 4: n.ri livello della classificazione
						//  -- 5: Indica i privilegi (se più di uno separati da ;)
						if (privilegioBean.getListaPrivilegiOggettoPriv()!=null && privilegioBean.getListaPrivilegiOggettoPriv().size()>0){
							String listaCodiciPrivilegiOggetto = StringUtils.join(privilegioBean.getListaPrivilegiOggettoPriv(), ";");
							dettGruppoPrivPrivilegiXmlBean.setListaCodiciPrivilegiOggetto(listaCodiciPrivilegiOggetto);
						}				
						listaPrivilegiOut.add(dettGruppoPrivPrivilegiXmlBean);				
				}			
		   }	   
	}  
}