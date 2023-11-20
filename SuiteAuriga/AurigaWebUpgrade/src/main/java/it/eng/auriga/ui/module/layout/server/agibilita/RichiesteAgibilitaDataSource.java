/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_portale_crm.bean.DmpkIntPortaleCrmGestrichsbloccolimagibilitaBean;
import it.eng.auriga.database.store.dmpk_int_portale_crm.bean.DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.agibilita.bean.DatiEmailAgibilitaXmlOutBean;
import it.eng.auriga.ui.module.layout.server.agibilita.bean.FiltriAgibilitaXmlBean;
import it.eng.auriga.ui.module.layout.server.agibilita.bean.RichiesteAgibilitaBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkIntPortaleCrmGestrichsbloccolimagibilita;
import it.eng.client.DmpkIntPortaleCrmTrovarichsbloccolimagibilita;
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

@Datasource(id = "RichiesteAgibilitaDataSource")
public class RichiesteAgibilitaDataSource extends AurigaAbstractFetchDatasource<RichiesteAgibilitaBean>  {

	private static final Logger log = Logger.getLogger(RichiesteAgibilitaDataSource.class);

	@Override
	public String getNomeEntita() {
		return "richieste_agib";
	};

	@Override
	public PaginatorBean<RichiesteAgibilitaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String xmlFiltersAgibilita = getFilterString(criteria);
		
		DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean input = new DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFiltriio(xmlFiltersAgibilita);
		input.setColorderbyio(null);			
		input.setFlgsenzapaginazionein(new Integer(1));
		
		DmpkIntPortaleCrmTrovarichsbloccolimagibilita store = new DmpkIntPortaleCrmTrovarichsbloccolimagibilita();
		StoreResultBean<DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean> output = store.execute(getLocale(), loginBean, input);			
			
		boolean overflow = false;
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
			}
		}
		
		List<RichiesteAgibilitaBean> data = new ArrayList<RichiesteAgibilitaBean>();
		if (output.getResultBean() != null) {
			String xml = output.getResultBean().getResultout();
			data = XmlListaUtility.recuperaLista(xml, RichiesteAgibilitaBean.class);
		}	
		
		PaginatorBean<RichiesteAgibilitaBean> lPaginatorBean = new PaginatorBean<RichiesteAgibilitaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;		
	}

	protected String getFilterString(AdvancedCriteria criteria)
			throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		FiltriAgibilitaXmlBean lFiltriAgibilitaXml = new FiltriAgibilitaXmlBean();

		String statoRichiesta = null;
		Date richiesteDal = null;		
		Date richiesteAl = null;		
		Date evaseDal = null;
		Date evaseAl = null;
		String nominativoRichiedente = null;
		
		
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if("statoRichiesta".equals(crit.getFieldName())) {
					statoRichiesta = getTextFilterValue(crit);
				}else if("nominativoRichiedente".equals(crit.getFieldName())) {
					nominativoRichiedente = getTextFilterValue(crit);
				}else if ("dataRicezione".equals(crit.getFieldName())) {
					Date[] estremiDataRicezione = getDateFilterValue(crit);
					if (richiesteDal != null) {
						richiesteDal = richiesteDal.compareTo(estremiDataRicezione[0]) < 0 ? estremiDataRicezione[0] : richiesteDal;
					} else {
						richiesteDal = estremiDataRicezione[0];
					}
					if (richiesteAl != null) {
						richiesteAl = richiesteAl.compareTo(estremiDataRicezione[1]) > 0 ? estremiDataRicezione[1] : richiesteAl;
					} else {
						richiesteAl = estremiDataRicezione[1];
					}
				} else if ("dataGestione".equals(crit.getFieldName())) {
					Date[] estremiDataGestione = getDateFilterValue(crit);
					if (evaseDal != null) {
						evaseDal = evaseDal.compareTo(estremiDataGestione[0]) < 0 ? estremiDataGestione[0] : evaseDal;
					} else {
						evaseDal = estremiDataGestione[0];
					}
					if (evaseAl != null) {
						evaseAl = evaseAl.compareTo(estremiDataGestione[1]) > 0 ? estremiDataGestione[1] : evaseAl;
					} else {
						evaseAl = estremiDataGestione[1];
					}
				}
			}			
		}
		
		if(StringUtils.isNotBlank(statoRichiesta)) {
			lFiltriAgibilitaXml.setStato(statoRichiesta);
		}
		if(StringUtils.isNotBlank(nominativoRichiedente)){
			lFiltriAgibilitaXml.setCognomeNomeRichiedente(nominativoRichiedente);
		}
		if(richiesteDal != null){
			lFiltriAgibilitaXml.setRichiesteDal(richiesteDal);
		}
		if(richiesteAl != null){
			lFiltriAgibilitaXml.setRichiesteAl(richiesteAl);
		}
		if(evaseDal != null){
			lFiltriAgibilitaXml.setEvaseDal(evaseDal);
		}
		if(richiesteAl != null){
			lFiltriAgibilitaXml.setEvaseAl(evaseAl);
		}
		
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlFiltersAgibilita = lXmlUtilitySerializer.bindXml(lFiltriAgibilitaXml);
		return xmlFiltersAgibilita;
	}


	public void manageEsitoRichiesta (RichiesteAgibilitaBean record) throws Exception {
		String motivazioneRespinta = null;
		String esito = null;
		String nuovoLimite = null;
		
		if("rigetto".equalsIgnoreCase(getExtraparams().get("esito"))) {
			motivazioneRespinta = getExtraparams().get("motivazione");			
			esito = "respinta";
		}else {
			esito = "accolta";
			nuovoLimite = getExtraparams().get("nuovoLimiteRich");	
		}
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkIntPortaleCrmGestrichsbloccolimagibilitaBean input = new DmpkIntPortaleCrmGestrichsbloccolimagibilitaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(idUserLavoro!=null ? new BigDecimal(idUserLavoro) : null);
		input.setIdrichiestain(record.getIdRichiesta());
		input.setEsitogestionein(esito);
		input.setMotivorespingimentoin(motivazioneRespinta);
		input.setNuovolimitein(nuovoLimite!=null ? new BigDecimal(nuovoLimite) : null);
		
		DmpkIntPortaleCrmGestrichsbloccolimagibilita store = new DmpkIntPortaleCrmGestrichsbloccolimagibilita();
		StoreResultBean<DmpkIntPortaleCrmGestrichsbloccolimagibilitaBean> output = store.execute(getLocale(), loginBean, input);			
			
		DatiEmailAgibilitaXmlOutBean lDatiEmailAgibilitaXmlOutBean = null;
		
		
			if(output.isInError()) {
				throw new StoreException(output);	
			}
			
		
			if(output.getResultBean() != null) {
				XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
				lDatiEmailAgibilitaXmlOutBean = lXmlUtility.unbindXml(output.getResultBean().getDatiemailrispostaout(),
						DatiEmailAgibilitaXmlOutBean.class);
				
				try {
					inviaMail(lDatiEmailAgibilitaXmlOutBean);
				}catch(Exception e) {
					log.error(e.getMessage(), e);
					throw new StoreException("La richiesta è stata accolta ma c'è stato un errore durante l'invio della mail: " + e.getMessage());
				}
			}
		

	}

	private void inviaMail(DatiEmailAgibilitaXmlOutBean lDatiEmailAgibilitaXmlOutBean) throws Exception {
		
		SenderBean senderBean = new SenderBean();
		ResultBean<EmailSentReferenceBean> output = null;

		senderBean.setFlgInvioSeparato(false);
		senderBean.setIsPec(true);

		// Mittente
		senderBean.setAccount(lDatiEmailAgibilitaXmlOutBean.getIndirizzoMittenteEmailRisposta());
		senderBean.setAddressFrom(lDatiEmailAgibilitaXmlOutBean.getIndirizzoMittenteEmailRisposta());

		// Destinatari principali
		String[] destinatariTo = lDatiEmailAgibilitaXmlOutBean.getDestinatariEmailRisposta().split(",");
		senderBean.setAddressTo(Arrays.asList(destinatariTo));
		
		// Oggetto
		senderBean.setSubject(lDatiEmailAgibilitaXmlOutBean.getOggettoEmailRisposta());

		// CORPO
		senderBean.setBody(lDatiEmailAgibilitaXmlOutBean.getCorpoEmailRisposta());
		senderBean.setIsHtml(true);

		// Conferma di lettura
		senderBean.setReturnReceipt(false);

		try {
			output = AurigaMailService.getMailSenderService().sendandsave(new Locale("it"), senderBean);
			
			if(output.getDefaultMessage()!=null) {
				throw new Exception(output.getDefaultMessage());
			}
			
		} catch (Exception e) {
			log.error("Errore durante invio mail: " + e.getMessage(), e);
			throw new Exception(e);
		}
		
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String xmlFiltersAgibilita = getFilterString(bean.getCriteria());
		DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean input = new DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFiltriio(xmlFiltersAgibilita);
		input.setColorderbyio(null);			
		input.setFlgsenzapaginazionein(new Integer(1));
		
		DmpkIntPortaleCrmTrovarichsbloccolimagibilita store = new DmpkIntPortaleCrmTrovarichsbloccolimagibilita();
		StoreResultBean<DmpkIntPortaleCrmTrovarichsbloccolimagibilitaBean> output = store.execute(getLocale(), loginBean, input);			
			
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		// SETTO L'OUTPUT
		if (output.getResultBean().getResultout() != null) {
			bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		}

		return bean;
	}
}
