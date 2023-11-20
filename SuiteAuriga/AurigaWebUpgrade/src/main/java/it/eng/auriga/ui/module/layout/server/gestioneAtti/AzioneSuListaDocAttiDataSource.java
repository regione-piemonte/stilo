/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfAzionesulistadocattiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AzioneSuListaAttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.DocInfoLibroFirma;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.XmlDettAzioneSuListaAttiBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.DatiContabiliSIBDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.NuovaPropostaAtto2DataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2Bean;
import it.eng.client.DmpkWfAzionesulistadocatti;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AzioneSuListaDocAttiDataSource")
public class AzioneSuListaDocAttiDataSource extends AbstractDataSource<AzioneSuListaAttiBean, AzioneSuListaAttiBean>{	
	
	public static final String _RILASCIO_VISTO = "RILASCIO_VISTO";
	public static final String _RIFIUTO_VISTO = "RIFIUTO_VISTO";
	public static final String _EVENTO_AMC = "EVENTO_AMC";
	
	public NuovaPropostaAtto2DataSource getNuovaPropostaAtto2DataSource() {			
		NuovaPropostaAtto2DataSource lNuovaPropostaAtto2DataSource = new NuovaPropostaAtto2DataSource() {
			
			@Override
			public boolean isAttivaRequestMovimentiDaAMC(NuovaPropostaAtto2Bean bean) {
				return false;
			}
		};
		lNuovaPropostaAtto2DataSource.setSession(getSession());
		lNuovaPropostaAtto2DataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2DataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lNuovaPropostaAtto2DataSource.setMessages(getMessages()); 		
		return lNuovaPropostaAtto2DataSource;
	}
	
	public DatiContabiliSIBDataSource getDatiContabiliSIBDataSource() {	
		DatiContabiliSIBDataSource lDatiContabiliSIBDataSource = new DatiContabiliSIBDataSource();
		lDatiContabiliSIBDataSource.setSession(getSession());
		lDatiContabiliSIBDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lDatiContabiliSIBDataSource.setMessages(getMessages()); 		
		return lDatiContabiliSIBDataSource;
	}	
	
	public boolean isAttivoSIB() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}

	@Override
	public AzioneSuListaAttiBean add(AzioneSuListaAttiBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = new HashMap<String, String>();;		
		
		boolean isAttivaNuovaPropostaAtto2 = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_NUOVA_PROPOSTA_ATTO_2");
		
		if(isAttivaNuovaPropostaAtto2 && bean.getAzione() != null && _EVENTO_AMC.equalsIgnoreCase(bean.getAzione()) && StringUtils.isNotBlank(bean.getEventoAMC())) {
				
			for(AttiBean attoBean : bean.getListaRecord()) {
				// se non è andato in errore
				if(!errorMessages.containsKey(attoBean.getUnitaDocumentariaId())) {
					// se è attiva l'integrazione con SIB
					if(isAttivoSIB()) {	
						NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean = new NuovaPropostaAtto2Bean(); 
						lNuovaPropostaAtto2Bean.setIdUd(attoBean.getUnitaDocumentariaId());		
						lNuovaPropostaAtto2Bean = getNuovaPropostaAtto2DataSource().get(lNuovaPropostaAtto2Bean);	
						lNuovaPropostaAtto2Bean.setEventoSIB(bean.getEventoAMC());
						getDatiContabiliSIBDataSource().aggiornaAtto(lNuovaPropostaAtto2Bean);			
						
						if(lNuovaPropostaAtto2Bean.getEsitoEventoSIB() != null && lNuovaPropostaAtto2Bean.getEsitoEventoSIB().equals("KO")) {
							errorMessages.put(attoBean.getUnitaDocumentariaId(), "Si è verificato un errore durante la chiamata al servizio " + bean.getEventoAMC() + " di SIB: " + lNuovaPropostaAtto2Bean.getErrMsgEventoSIB());																	
						} 																		
					} else {
						throw new StoreException("Nessuna integrazione prevista con sistema contabile");												
					}
				}					
			}				
			
		} else {
		
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			
			DmpkWfAzionesulistadocattiBean input = new DmpkWfAzionesulistadocattiBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			input.setAzionein(bean.getAzione());
			input.setListapropattiin(lXmlUtilitySerializer.bindXmlList(getListaPropAtti(bean)));		
			input.setXmldettazionein(lXmlUtilitySerializer.bindXml(getXmlDettAzione(bean)));
					
			DmpkWfAzionesulistadocatti store = new DmpkWfAzionesulistadocatti();
			StoreResultBean<DmpkWfAzionesulistadocattiBean> output = store.execute(getLocale(),loginBean, input);
				
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					throw new StoreException(output);
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
	
			if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {				
				StringReader sr = new StringReader(output.getResultBean().getEsitiout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					if (v.get(3).equalsIgnoreCase("ko")) {
						errorMessages.put(v.get(0), v.get(4));
					}
				}
			}
			
		}
		
		bean.setErrorMessages(errorMessages);		
		
		return bean;
	}	
	
	private List<DocInfoLibroFirma> getListaPropAtti(AzioneSuListaAttiBean bean) throws Exception {
		List<DocInfoLibroFirma> listaPropAtti = new ArrayList<DocInfoLibroFirma>();
		if(bean.getListaRecord() != null){
			for(AttiBean attoBean : bean.getListaRecord()) {
				DocInfoLibroFirma lDocInfoLibroFirma = new DocInfoLibroFirma();
				lDocInfoLibroFirma.setIdUd(attoBean.getUnitaDocumentariaId());
				lDocInfoLibroFirma.setIdProcess(attoBean.getIdProcedimento());
				listaPropAtti.add(lDocInfoLibroFirma);
			}
		}
		return listaPropAtti;
	}
	
	private XmlDettAzioneSuListaAttiBean getXmlDettAzione(AzioneSuListaAttiBean bean) throws Exception {
		XmlDettAzioneSuListaAttiBean scXmlDettAzione = new XmlDettAzioneSuListaAttiBean();
		if(StringUtils.isNotBlank(bean.getAzione())) {
			if(_RILASCIO_VISTO.equalsIgnoreCase(bean.getAzione())) {
				scXmlDettAzione.setOsservazioni(bean.getMotivoOsservazioni());
			} else if(_RIFIUTO_VISTO.equalsIgnoreCase(bean.getAzione())) {
				scXmlDettAzione.setOsservazioni(bean.getMotivoOsservazioni());
			}
		}		
		return scXmlDettAzione;
	}
	
	@Override
	public AzioneSuListaAttiBean get(AzioneSuListaAttiBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public AzioneSuListaAttiBean remove(AzioneSuListaAttiBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public AzioneSuListaAttiBean update(AzioneSuListaAttiBean bean,
			AzioneSuListaAttiBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<AzioneSuListaAttiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AzioneSuListaAttiBean bean)
	throws Exception {
		
		return null;
	}

}
