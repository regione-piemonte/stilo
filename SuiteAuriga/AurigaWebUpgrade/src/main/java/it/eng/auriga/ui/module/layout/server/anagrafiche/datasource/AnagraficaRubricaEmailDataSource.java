/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovainrubricaemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaRubricaEmailBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaRubricaEmailXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaRubricaEmailXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ListaMembriGruppoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.RubricaEmailBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.SoggettoGruppoEmailBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.aurigamailbusiness.bean.DominioBean;
import it.eng.aurigamailbusiness.bean.MailingListBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.bean.TMembriMailingListBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.bean.TUtentiModPecBean;
import it.eng.aurigamailbusiness.bean.VoceRubricaBeanIn;
import it.eng.client.AurigaMailService;
import it.eng.client.CasellaUtility;
import it.eng.client.DmpkIntMgoEmailTrovainrubricaemail;
import it.eng.client.RubricaService;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.spring.utility.SpringAppContext;
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
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AnagraficaRubricaEmailDataSource")
public class AnagraficaRubricaEmailDataSource extends AurigaAbstractFetchDatasource<AnagraficaRubricaEmailBean> {

	@Override
	public String getNomeEntita() {
		return "anagrafiche_rubricaemail";
	}	
	
	@Override
	public AnagraficaRubricaEmailBean get(AnagraficaRubricaEmailBean bean) throws Exception {		
				
      
		// Leggo l'OUTPUT
        AnagraficaRubricaEmailBean node = null;	
        
        if(StringUtils.isNotBlank(bean.getIdVoceRubrica())) {
        
        	TRubricaEmailBean lRubricaEmailBean = AurigaMailService.getDaoTRubricaEmail().get(getLocale(), bean.getIdVoceRubrica());
        	
        	node = new AnagraficaRubricaEmailBean();	

        	// Codice Amministrazione
        	node.setCodAmministrazione(lRubricaEmailBean.getCodAmministrazione());
        	
        	// Codice AOO
        	node.setCodAoo(lRubricaEmailBean.getCodAoo());
        	
        	// Codice IPA ( cod_amm + cod_aoo )
			if (lRubricaEmailBean.getCodAmministrazione()!=null && !lRubricaEmailBean.getCodAmministrazione().equalsIgnoreCase("") &&
				lRubricaEmailBean.getCodAoo()!=null && !lRubricaEmailBean.getCodAoo().equalsIgnoreCase("")) {
				node.setCodiceIPA(lRubricaEmailBean.getCodAmministrazione() + "-" + lRubricaEmailBean.getCodAoo());							
			} else {
				node.setCodiceIPA("");
			}
								
			// Flag PEC verificata  ( 1 = si, 0/null = no)	
			node.setFlgPECverificata(lRubricaEmailBean.getFlgPecVerificata());
			
			// Flag email presente in IPA ( 1=si, 0/null=no)
			node.setFlgPresenteInIPA(lRubricaEmailBean.getFlgPresenteInIpa());
				
			//Flag validita' (1=valido,0=annullato)
			node.setFlgValido(!lRubricaEmailBean.getFlgAnn());
			
            // ID indirizzo
			node.setIdVoceRubrica(lRubricaEmailBean.getIdVoceRubricaEmail());
			
			// Indirizzo email
			node.setIndirizzoEmail(lRubricaEmailBean.getAccountEmail());
			
			// Nome
			node.setNome(lRubricaEmailBean.getDescrizioneVoce());
			
			// Tipo account ( C=casella PEC, O=casella ordinaria, null) 
			node.setTipoAccount(lRubricaEmailBean.getTipoAccount());
			
			// Flag PEC
			if (lRubricaEmailBean.getTipoAccount()!=null && !lRubricaEmailBean.getTipoAccount().equalsIgnoreCase("")){
				node.setFlgPEC(lRubricaEmailBean.getTipoAccount().equalsIgnoreCase("C"));
			} else {
				node.setFlgPEC(false);
			}
						
			// Tipo indirizzo ( S=account semplice, G=gruppi)
			node.setTipoIndirizzo(lRubricaEmailBean.getFlgMailingList() ? "G" : "S");
			if (lRubricaEmailBean.getFlgMailingList()) {
				TMembriMailingListBean lFilterBean = new TMembriMailingListBean();
				lFilterBean.setIdVoceRubricaMailingList(lRubricaEmailBean.getIdVoceRubricaEmail());
				TFilterFetch<TMembriMailingListBean> lTFilterFetch = new TFilterFetch<TMembriMailingListBean>();
				lTFilterFetch.setFilter(lFilterBean);
				TPagingList<TMembriMailingListBean> lResult = AurigaMailService.getDaoTMembriMailingList().search(getLocale(), lTFilterFetch);
				List<SoggettoGruppoEmailBean> listaSoggettiGruppo = new ArrayList<SoggettoGruppoEmailBean>(); 
				if(lResult.getData() != null && lResult.getData().size()>0) {
					for(int i = 0; i < lResult.getData().size(); i++) {
						TRubricaEmailBean lTRubricaEmail = AurigaMailService.getDaoTRubricaEmail().get(getLocale(), lResult.getData().get(i).getIdVoceRubricaMembro());
						SoggettoGruppoEmailBean lSoggettoGruppo = new SoggettoGruppoEmailBean();
						lSoggettoGruppo.setIdSoggettoGruppo(lTRubricaEmail.getIdVoceRubricaEmail());
						lSoggettoGruppo.setTipoMembro(lTRubricaEmail.getFlgMailingList() ? "G" : "S");
						lSoggettoGruppo.setDenominazioneSoggetto(lTRubricaEmail.getDescrizioneVoce());
						lSoggettoGruppo.setIndirizzoMailSoggetto(lTRubricaEmail.getAccountEmail());
						listaSoggettiGruppo.add(lSoggettoGruppo);
					}					
				}
				node.setListaSoggettiGruppo(listaSoggettiGruppo);
			}	

    	    // Tipo Soggetto di riferimento
			node.setTipoSoggettoRif(lRubricaEmailBean.getTipoSoggRif());
			
			// Id utente inserimento
			node.setIdUtenteIns(lRubricaEmailBean.getIdUteIns());
			
			// Descrizione utente inserimento
			if(StringUtils.isNotBlank(lRubricaEmailBean.getIdUteIns())) {
				TUtentiModPecBean lFilterBean = new TUtentiModPecBean();
				lFilterBean.setIdUtente(lRubricaEmailBean.getIdUteIns());
				TFilterFetch<TUtentiModPecBean> lTFilterFetch = new TFilterFetch<TUtentiModPecBean>();
				lTFilterFetch.setFilter(lFilterBean);
				TPagingList<TUtentiModPecBean> lResult = AurigaMailService.getDaoTUtentiModPec().search(getLocale(), lTFilterFetch);
				if(lResult.getData() != null && lResult.getData().size()==1) {
					TUtentiModPecBean lUteInsBean = lResult.getData().get(0);
					node.setUtenteIns(lUteInsBean.getCognome() + " " + lUteInsBean.getNome());
				}
			}
						
    	    // Data inserimento
			node.setDataIns(lRubricaEmailBean.getTsIns());
			
			// Id utente ultimo aggiornamento
			node.setIdUtenteUltMod(lRubricaEmailBean.getIdUteUltimoAgg());
			
			// Descrizione utente ultimo aggiornamento
			if(StringUtils.isNotBlank(lRubricaEmailBean.getIdUteUltimoAgg())) {
				TUtentiModPecBean lFilterBean = new TUtentiModPecBean();
				lFilterBean.setIdUtente(lRubricaEmailBean.getIdUteUltimoAgg());
				TFilterFetch<TUtentiModPecBean> lTFilterFetch = new TFilterFetch<TUtentiModPecBean>();
				lTFilterFetch.setFilter(lFilterBean);
				TPagingList<TUtentiModPecBean> lResult = AurigaMailService.getDaoTUtentiModPec().search(getLocale(), lTFilterFetch);
				if(lResult.getData() != null && lResult.getData().size()==1) {
					TUtentiModPecBean lUteUltimoAggBean = lResult.getData().get(0);
					node.setUtenteUltMod(lUteUltimoAggBean.getCognome() + " " + lUteUltimoAggBean.getNome());
				}				
			}
						
    	    // Data ultimo aggiornamento
			node.setDataUltMod(lRubricaEmailBean.getTsUltimoAgg());		
		    
			// id prov. sogg rif.
			node.setIdProvSoggRif(lRubricaEmailBean.getIdProvSoggRif());
			
			// id fruitore
			node.setIdFruitoreCasella(lRubricaEmailBean.getIdFruitoreCasella());			 		
        }   
        
		return node;
	}
	
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		StoreResultBean<DmpkIntMgoEmailTrovainrubricaemailBean> output = trovaRubrica(bean.getCriteria(), -1);
				
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} 
		}

		//SETTO L'OUTPUT
		if(output.getResultBean().getResultout()!=null){
			bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		}
		
		return bean;
	}
	
	private DmpkIntMgoEmailTrovainrubricaemailBean createInputBean(AdvancedCriteria criteria, Integer overflowLimit) throws Exception, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		CriteriAvanzati cAvanzati = new CriteriAvanzati();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;	
		
		//Flag che indica se il campo nome è valorizzato
		boolean flgNomeValorizzato = false;
		if(criteria!=null && criteria.getCriteria()!=null){		
			
			for(Criterion crit : criteria.getCriteria()){
				if("tipoVoce".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						cAvanzati.setTipoVoce((String)crit.getValue());
					}
				} 
				if("indirizzoEmail".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						Map mappa = (Map) crit.getValue(); 
						cAvanzati.setAccount((String) mappa.get("parole"));
						cAvanzati.setOperAccount(getDecodeCritOperator(crit.getOperator()));
					}
				}
				else if("nome".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						Map mappa = (Map) crit.getValue(); 
						String parole = (String) mappa.get("parole");
						if ((parole != null) && !"".equalsIgnoreCase(parole)){
							cAvanzati.setDesVoce((String) mappa.get("parole"));
							cAvanzati.setOperDesVoce(getDecodeCritOperator(crit.getOperator()));
							// Il campo nome è valorizzato
							flgNomeValorizzato = true;
						}
					}
				} 
				else if("tipoAccount".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						List lista = (List) crit.getValue();
						cAvanzati.setTipoAccount(StringUtils.join(lista, ","));
					}									
				}
				else if("dataSalvataggio".equals(crit.getFieldName())) {
					Date[] estremiDataSalvataggio = getDateFilterValue(crit);
					cAvanzati.setInseritaDal(estremiDataSalvataggio[0]);
					cAvanzati.setInseritaAl(estremiDataSalvataggio[1]);
				}
				else if("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						cAvanzati.setIncludiAnnullati(new Boolean(String.valueOf(crit.getValue())) ? "1" : "0");
					}									
				}
				else if("flgInseritaDaMe".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						cAvanzati.setInseritaDaMe(new Boolean(String.valueOf(crit.getValue())) ? "1" : "0");
					}									
				}else if("iniziale".equals(crit.getFieldName())) {
					// Non deve sovrascrivere il campo nome
					if (!flgNomeValorizzato){
						if(crit.getValue() != null) {
							cAvanzati.setDesVoce((String) crit.getValue());
							cAvanzati.setOperDesVoce(getDecodeCritOperator("ISTARTSWITH"));
						}		
					}
				}
			}
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
        String advancedFilters = lXmlUtilitySerializer.bindXml(cAvanzati);
		
		DmpkIntMgoEmailTrovainrubricaemailBean input = new DmpkIntMgoEmailTrovainrubricaemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setFlgsenzatotin(null);
		input.setFiltriio(advancedFilters);
		
		if(overflowLimit != null && overflowLimit == -1) {
			input.setColtoreturnin("1");
			input.setOverflowlimitin(overflowLimit);
		}
		
		return input;
		
	}
	
	private StoreResultBean<DmpkIntMgoEmailTrovainrubricaemailBean> trovaRubrica(AdvancedCriteria criteria, Integer overflowLimit) throws Exception{
			
		DmpkIntMgoEmailTrovainrubricaemailBean input = createInputBean (criteria, overflowLimit );
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		DmpkIntMgoEmailTrovainrubricaemail dmpkIntMgoEmailTrovainrubricaemail = new DmpkIntMgoEmailTrovainrubricaemail();
		StoreResultBean<DmpkIntMgoEmailTrovainrubricaemailBean> output = dmpkIntMgoEmailTrovainrubricaemail.execute(getLocale(), loginBean, input);
		
		return output;
					
	}
	
	@Override
	public PaginatorBean<AnagraficaRubricaEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {				
		
		StoreResultBean<DmpkIntMgoEmailTrovainrubricaemailBean> output = trovaRubrica(criteria, null);
		
		boolean overflowLimitOnLayout = false;
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) 
		{
			if(output.isInError()) 
			{
				throw new StoreException(output);		
			} 
			else 
			{
				GenericConfigBean config = (GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
				
				Boolean showOverflow =  config.getShowAlertMaxRecord();
				
				if(showOverflow!=null && showOverflow)
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				//else
					overflowLimitOnLayout = true;
				
				//addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		
		List<AnagraficaRubricaEmailBean> data = new ArrayList<AnagraficaRubricaEmailBean>();
		
		AnagraficaRubricaEmailXmlBeanDeserializationHelper helper = new AnagraficaRubricaEmailXmlBeanDeserializationHelper(new HashMap<String, String>());
		
		if (output.getResultBean().getNrototrecout() != null && output.getResultBean().getNrototrecout() > 0 ) {
			
			data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), AnagraficaRubricaEmailBean.class, helper);
			
		}
		
		//salvo i dati in sessione per renderli disponibili l'esportazione
	    getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<AnagraficaRubricaEmailBean> lPaginatorBean = new PaginatorBean<AnagraficaRubricaEmailBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflowLimitOnLayout);
		
		return lPaginatorBean;	
	}
	
	private AnagraficaRubricaEmailBean convertToAnagrafica(RubricaEmailBean lRubricaEmailBean) throws Exception {
		AnagraficaRubricaEmailBean node = new AnagraficaRubricaEmailBean();	 
		
    	// Codice Amministrazione
    	node.setCodAmministrazione(lRubricaEmailBean.getCodAmministrazione());
    	
    	// Codice AOO
    	node.setCodAoo(lRubricaEmailBean.getCodAoo());
		
    	// Codice IPA ( cod_amm + cod_aoo )
		if (lRubricaEmailBean.getCodAmministrazione()!=null && !lRubricaEmailBean.getCodAmministrazione().equalsIgnoreCase("") &&
			lRubricaEmailBean.getCodAoo()!=null && !lRubricaEmailBean.getCodAoo().equalsIgnoreCase("")) {
			node.setCodiceIPA(lRubricaEmailBean.getCodAmministrazione() + "-" + lRubricaEmailBean.getCodAoo());
		} else {
			node.setCodiceIPA("");
		}
		
		// Flag PEC verificata  ( 1 = si, 0/null = no)
		node.setFlgPECverificata(lRubricaEmailBean.isFlgPecVerificata());				
		
		// Flag email presente in IPA ( 1=si, 0/null=no)
		node.setFlgPresenteInIPA(lRubricaEmailBean.isFlgPresenteInIpa());
		
		
		//Flag validita' (1=valido,0=annullato)
		node.setFlgValido(!lRubricaEmailBean.isFlgAnn());
						
		// ID indirizzo
		node.setIdVoceRubrica(lRubricaEmailBean.getIdVoceRubricaEmail());
		
		// Indirizzo email
		node.setIndirizzoEmail(lRubricaEmailBean.getAccountEmail());
		
		// Nome
		node.setNome(lRubricaEmailBean.getDescrizioneVoce());
		
		// Tipo account ( C=casella PEC, O=casella ordinaria, null) 
		node.setTipoAccount(lRubricaEmailBean.getTipoAccount());
		
		// Flag PEC
		if (lRubricaEmailBean.getTipoAccount()!=null && !lRubricaEmailBean.getTipoAccount().equalsIgnoreCase("")){
			node.setFlgPEC(lRubricaEmailBean.getTipoAccount().equalsIgnoreCase("C"));
		} else {
			node.setFlgPEC(false);
		}
		
		// Tipo indirizzo ( S=account semplice, G=gruppi)
		node.setTipoIndirizzo(lRubricaEmailBean.isFlgMailingList() ? "G" : "S");
					
		 // Tipo Soggetto di riferimento
		node.setTipoSoggettoRif(lRubricaEmailBean.getTipoSoggRif());
		
		// Id utente inserimento
		node.setIdUtenteIns(lRubricaEmailBean.getIdUteIns());
		
		// Descrizione utente inserimento
		if(StringUtils.isNotBlank(lRubricaEmailBean.getIdUteIns())) {
			TUtentiModPecBean lFilterBean = new TUtentiModPecBean();
			lFilterBean.setIdUtente(lRubricaEmailBean.getIdUteIns());
			TFilterFetch<TUtentiModPecBean> lTFilterFetch = new TFilterFetch<TUtentiModPecBean>();
			lTFilterFetch.setFilter(lFilterBean);
			TPagingList<TUtentiModPecBean> lResult = AurigaMailService.getDaoTUtentiModPec().search(getLocale(), lTFilterFetch);
			if(lResult.getData() != null && lResult.getData().size()==1) {
				TUtentiModPecBean lUteInsBean = lResult.getData().get(0);
				node.setUtenteIns(lUteInsBean.getCognome() + " " + lUteInsBean.getNome());
			}
		}
		
		// Data inserimento
		node.setDataIns(lRubricaEmailBean.getTsIns());
		
		// Id utente ultimo aggiornamento
		node.setIdUtenteUltMod(lRubricaEmailBean.getIdUteUltimoAgg());
		
		// Descrizione utente ultimo aggiornamento
		if(StringUtils.isNotBlank(lRubricaEmailBean.getIdUteUltimoAgg())) {
			TUtentiModPecBean lFilterBean = new TUtentiModPecBean();
			lFilterBean.setIdUtente(lRubricaEmailBean.getIdUteUltimoAgg());
			TFilterFetch<TUtentiModPecBean> lTFilterFetch = new TFilterFetch<TUtentiModPecBean>();
			lTFilterFetch.setFilter(lFilterBean);
			TPagingList<TUtentiModPecBean> lResult = AurigaMailService.getDaoTUtentiModPec().search(getLocale(), lTFilterFetch);
			if(lResult.getData() != null && lResult.getData().size()==1) {
				TUtentiModPecBean lUteUltimoAggBean = lResult.getData().get(0);
				node.setUtenteUltMod(lUteUltimoAggBean.getCognome() + " " + lUteUltimoAggBean.getNome());
			}				
		}
		
		// Data ultimo aggiornamento
		node.setDataUltMod(lRubricaEmailBean.getTsUltimoAgg());
						
		// id prov. sogg rif.
		node.setIdProvSoggRif(lRubricaEmailBean.getIdProvSoggRif());
		
		// id fruitore
		node.setIdFruitoreCasella(lRubricaEmailBean.getIdFruitoreCasella());
		
		return node;
	}
	
	@Override
	public AnagraficaRubricaEmailBean remove(AnagraficaRubricaEmailBean bean)
			throws Exception {
		
		TRubricaEmailBean lRubricaEmailBean = AurigaMailService.getDaoTRubricaEmail().get(getLocale(), bean.getIdVoceRubrica());
		lRubricaEmailBean.setFlgAnn(true);
    	AurigaMailService.getDaoTRubricaEmail().update(getLocale(), lRubricaEmailBean);
		return bean;
	}
	
	@Override
	public AnagraficaRubricaEmailBean update(AnagraficaRubricaEmailBean bean, AnagraficaRubricaEmailBean oldvalue) throws Exception {
			
		if(StringUtils.isNotBlank(bean.getIdVoceRubrica())) {
        	
        	TRubricaEmailBean lTRubricaEmailBean = new TRubricaEmailBean();
        	lTRubricaEmailBean.setAccountEmail(bean.getIndirizzoEmail());
        	lTRubricaEmailBean.setCodAmministrazione(bean.getCodAmministrazione());
        	lTRubricaEmailBean.setCodAoo(bean.getCodAoo());
        	lTRubricaEmailBean.setDescrizioneVoce(bean.getNome());
        	lTRubricaEmailBean.setFlgAnn(!bean.isFlgValido());
        	
        	boolean isMailingList = bean.getTipoIndirizzo() != null && "G".equals(bean.getTipoIndirizzo());
    		lTRubricaEmailBean.setFlgMailingList(isMailingList);    		    		
    		
        	lTRubricaEmailBean.setFlgPecVerificata(bean.isFlgPECverificata());
        	lTRubricaEmailBean.setFlgPresenteInIpa(bean.isFlgPresenteInIPA());
        	lTRubricaEmailBean.setIdFruitoreCasella(bean.getIdFruitoreCasella());
        	lTRubricaEmailBean.setIdProvSoggRif(bean.getIdProvSoggRif());
        	lTRubricaEmailBean.setIdVoceRubricaEmail(bean.getIdVoceRubrica());
        	lTRubricaEmailBean.setTipoAccount(bean.getTipoAccount());
        	lTRubricaEmailBean.setTipoSoggRif(bean.getTipoSoggettoRif());   
        	lTRubricaEmailBean.setIdUteIns(bean.getIdUtenteIns());
        	lTRubricaEmailBean.setTsIns(bean.getDataIns());
        	lTRubricaEmailBean.setIdUteUltimoAgg(bean.getIdUtenteUltMod());
        	lTRubricaEmailBean.setTsUltimoAgg(bean.getDataUltMod());
        	
        	VoceRubricaBeanIn input = new VoceRubricaBeanIn();		
    		input.setIdFruitore(bean.getIdFruitoreCasella());
      	    input.setVoceRubrica(lTRubricaEmailBean);
    		
        	RubricaService lRubricaService = new RubricaService();		
    		ResultBean<TRubricaEmailBean> lResultBean = lRubricaService.updatevocerubrica(getLocale(), input);
    		
    		if(StringUtils.isNotBlank(lResultBean.getDefaultMessage())) {
    			if(lResultBean.isInError()) {
    				throw new StoreException(lResultBean.getDefaultMessage());		
    			} else {
    				addMessage(lResultBean.getDefaultMessage(), "", MessageType.WARNING);
    			}
    		}
    		
    		if(isMailingList) {
    			if(bean.getListaSoggettiGruppo() != null && bean.getListaSoggettiGruppo().size() > 0) {
    				List<String> idVoceRubricaMembri = new ArrayList<String>();
    				for(int i = 0; i < bean.getListaSoggettiGruppo().size(); i++) {
    					idVoceRubricaMembri.add(bean.getListaSoggettiGruppo().get(i).getIdSoggettoGruppo());
    				}
    				MailingListBean lMailingListBean = new MailingListBean();
    				lMailingListBean.setVoceMailingList(lTRubricaEmailBean);
    				lMailingListBean.setIdVoceRubricaMembri(idVoceRubricaMembri);
    				ResultBean<TRubricaEmailBean> lResultBean2 = lRubricaService.inserisciaggiornamailinglist(getLocale(), lMailingListBean);
    				
    				if(StringUtils.isNotBlank(lResultBean2.getDefaultMessage())) {
    	    			if(lResultBean2.isInError()) {
    	    				throw new StoreException(lResultBean2.getDefaultMessage());		
    	    			} else {
    	    				addMessage(lResultBean2.getDefaultMessage(), "", MessageType.WARNING);
    	    			}
    	    		}
    			}
    		}      		
    		    		
        }
		
        return bean;
	}
	
	@Override
	public AnagraficaRubricaEmailBean add(AnagraficaRubricaEmailBean bean) throws Exception {	
		
		// Cerco l'ID_FRUITORE
		DominioBean lDominioBean = new DominioBean();
		CasellaUtility lCasellaUtility = new CasellaUtility();
		lDominioBean.setIdDominio(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio().toString());
		
		TAnagFruitoriCaselleBean lTAnagFruitoriCaselleBean = lCasellaUtility.getfruitorecasellafromdominio(getLocale(), lDominioBean);
		String idFruitore=lTAnagFruitoriCaselleBean.getIdFruitoreCasella();
		
		// Inizializzo l'INPUT
		TRubricaEmailBean lTRubricaEmailBean = new TRubricaEmailBean();
		lTRubricaEmailBean.setIdFruitoreCasella(idFruitore);
		lTRubricaEmailBean.setAccountEmail(bean.getIndirizzoEmail());
		lTRubricaEmailBean.setDescrizioneVoce(bean.getNome());
		lTRubricaEmailBean.setTipoAccount(bean.getTipoAccount());  	
		lTRubricaEmailBean.setFlgAnn(false);
		
		boolean isMailingList = bean.getTipoIndirizzo() != null && "G".equals(bean.getTipoIndirizzo());
		lTRubricaEmailBean.setFlgMailingList(isMailingList);
		
		VoceRubricaBeanIn input = new VoceRubricaBeanIn();		
		input.setIdFruitore(idFruitore);
  	    input.setVoceRubrica(lTRubricaEmailBean);
		
		// Salvo l'INPUT
		RubricaService lRubricaService = new RubricaService();	
		ResultBean<TRubricaEmailBean> lResultBean = null;
		
		if(isMailingList) {
			List<String> idVoceRubricaMembri = new ArrayList<String>();
			if(bean.getListaSoggettiGruppo() != null && bean.getListaSoggettiGruppo().size() > 0) {
				for(int i = 0; i < bean.getListaSoggettiGruppo().size(); i++) {
					idVoceRubricaMembri.add(bean.getListaSoggettiGruppo().get(i).getIdSoggettoGruppo());
				}
			}
			MailingListBean lMailingListBean = new MailingListBean();
			lMailingListBean.setVoceMailingList(lTRubricaEmailBean);
			lMailingListBean.setIdVoceRubricaMembri(idVoceRubricaMembri);
			lResultBean = lRubricaService.inserisciaggiornamailinglist(getLocale(), lMailingListBean);
		} else {
			lResultBean = lRubricaService.savevocerubrica(getLocale(), input);		
		}
		
		if(StringUtils.isNotBlank(lResultBean.getDefaultMessage())) {
			if(lResultBean.isInError()) {
				throw new StoreException(lResultBean.getDefaultMessage());		
			} else {
				addMessage(lResultBean.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdVoceRubrica(lResultBean.getResultBean().getIdVoceRubricaEmail());									
		
		return bean;	
	}	
	
	public ListaMembriGruppoBean trovaMembriGruppo(AnagraficaRubricaEmailBean bean) throws Exception {		
				
      
		// Leggo l'OUTPUT
		ListaMembriGruppoBean output = null;	
        
        if(StringUtils.isNotBlank(bean.getIdVoceRubrica())) {
        
        	TRubricaEmailBean lTRubricaEmailBean = AurigaMailService.getDaoTRubricaEmail().get(getLocale(), bean.getIdVoceRubrica());
        	
        	output = new ListaMembriGruppoBean();	
			
            // ID indirizzo
        	output.setIdMailingList(lTRubricaEmailBean.getIdVoceRubricaEmail());
						
			if(lTRubricaEmailBean.getFlgMailingList()) {
				TMembriMailingListBean lTMembriMailingListBean = new TMembriMailingListBean();
				lTMembriMailingListBean.setIdVoceRubricaMailingList(lTRubricaEmailBean.getIdVoceRubricaEmail());		
				TPagingList<TMembriMailingListBean> lResult = AurigaMailService.getDaoTMembriMailingList().findallmembrimailinglist(getLocale(), lTMembriMailingListBean);
				List<AnagraficaRubricaEmailBean> listaMembri = new ArrayList<AnagraficaRubricaEmailBean>(); 
				if(lResult.getData() != null && lResult.getData().size()>0) {
					for(int i = 0; i < lResult.getData().size(); i++) {
						RubricaEmailBean lRubricaEmailBean = (RubricaEmailBean) UtilPopulate.populate(AurigaMailService.getDaoTRubricaEmail().get(getLocale(), lResult.getData().get(i).getIdVoceRubricaMembro()), RubricaEmailBean.class);										
						AnagraficaRubricaEmailBean node = convertToAnagrafica(lRubricaEmailBean);																	
						listaMembri.add(node);
					}					
				}
				output.setListaMembri(listaMembri);
			}
        }   
        
		return output;
	}
	
	private String getDecodeCritOperator(String operator){
		
		String out="";
		
		if (operator == null)
		    return out;
		
		if(operator.equalsIgnoreCase("WORDSSTARTWITH")){
			out = "contiene";
		}
		else if (operator.equalsIgnoreCase("IEQUALS")){
	       	out = "uguale";
		}
		else if (operator.equalsIgnoreCase("IENDSWITH")){
	       	out = "finisce";
		}
	    else if (operator.equalsIgnoreCase("ISTARTSWITH")){
	       	out = "inizia";
	    }
		else if (operator.equalsIgnoreCase("LIKE")){
			out = "like";
		}
		
		return out;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		
		Integer overflowLimit = -2;
		
		StoreResultBean<DmpkIntMgoEmailTrovainrubricaemailBean> result = trovaRubrica(bean.getCriteria(), overflowLimit);
		
		if(result.isInError() && StringUtils.isNotBlank(result.getDefaultMessage())) {
			throw new StoreException(result);
		}
		
		Integer jobId = result.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), AnagraficaRubricaEmailXmlBean.class.getName());

		//salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), AnagraficaRubricaEmailXmlBeanDeserializationHelper.class);
		
		updateJob(loginBean, bean, jobId, loginBean.getIdUser());
		
		if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		
		return bean;
	}
	
	private Map<String, String> createRemapConditions() {
		return new HashMap<String,String>();
	}
	
}