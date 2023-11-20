/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.ConvocazioneSedutaDataSource;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.DiscussioneSedutaDataSource;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ArgomentiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ConvocazioneSedutaBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.DiscussioneSedutaBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AvvioIterAttiInBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AvvioIterAttiOutBean;
import it.eng.client.AvvioProcedimentoService;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.workflow.service.bean.AvvioIterAttiServiceInBean;
import it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean;

@Datasource(id = "AvvioIterAttiActivitiDatasource")
public class AvvioIterAttiActivitiDatasource extends AbstractServiceDataSource<AvvioIterAttiInBean, AvvioIterAttiOutBean>{

	private static Logger mLogger = Logger.getLogger(AvvioIterAttiActivitiDatasource.class);
	@Override
	public AvvioIterAttiOutBean call(AvvioIterAttiInBean pInBean)
			throws Exception {
		mLogger.debug("Start call");
		AvvioIterAttiOutBean lAvvioIterAttiOutBean = new AvvioIterAttiOutBean();
		AvvioIterAttiServiceInBean lAvvioIterAttiServiceInBean = new AvvioIterAttiServiceInBean();
		BeanUtilsBean2.getInstance().copyProperties(lAvvioIterAttiServiceInBean, pInBean);
		AvvioProcedimentoService lAvvioIterAttiService = new AvvioProcedimentoService();
		AvvioIterAttiServiceOutBean lAvvioIterAttiServiceOutBean = 
			lAvvioIterAttiService.avviaiteratti(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lAvvioIterAttiServiceInBean);
		mLogger.debug(lAvvioIterAttiServiceOutBean.getEsito());
		if (lAvvioIterAttiServiceOutBean.getEsito() != null && lAvvioIterAttiServiceOutBean.getEsito()) {
			mLogger.debug("Id processo " + lAvvioIterAttiServiceOutBean.getIdProcesso());
			mLogger.debug("Process instance " + lAvvioIterAttiServiceOutBean.getProcessInstanceId());
			mLogger.debug("Id folder " + lAvvioIterAttiServiceOutBean.getIdFolder());
			mLogger.debug("Id ud " + lAvvioIterAttiServiceOutBean.getIdUd());
			mLogger.debug("Process definition id " + lAvvioIterAttiServiceOutBean.getProcessDefinitionId());
			BeanUtilsBean2.getInstance().copyProperties(lAvvioIterAttiOutBean, lAvvioIterAttiServiceOutBean);			
			if(StringUtils.isNotBlank(pInBean.getIdSeduta())){
				try {
					if("CONVOCAZIONE".equalsIgnoreCase(pInBean.getFinalitaOrgColl())) {
						 addPropostaConvocazione(pInBean);
					} else if("DISCUSSIONE".equalsIgnoreCase(pInBean.getFinalitaOrgColl())) {
						 addPropostaDiscussione(pInBean);
					}
				} catch (Exception e) {
					addMessage("Attenzione non è stato possibile aggiungere la " + lAvvioIterAttiServiceOutBean.getEstremiRegNumUd() +
							" alla seduta con id: " + pInBean.getIdSeduta(), "", MessageType.WARNING);
					mLogger.error("Errore nel salvataggio della seduta con id: " + pInBean.getIdSeduta());
				}
			}
		} else {
			mLogger.debug(lAvvioIterAttiServiceOutBean.getError());
			throw new StoreException(lAvvioIterAttiServiceOutBean.getError());
		}
		mLogger.debug("End call");
		return lAvvioIterAttiOutBean;
	}
	
	private DiscussioneSedutaDataSource getDiscussioneSedutaDataSource() {	
		
		DiscussioneSedutaDataSource lDiscussioneSedutaDataSource = new DiscussioneSedutaDataSource();		
		lDiscussioneSedutaDataSource.setSession(getSession());
		lDiscussioneSedutaDataSource.setExtraparams(getExtraparams());	
		// devo settare in lDiscussioneSedutaDataSource i messages di AvvioIterAttiActivitiDataSource per mostrare a video gli errori
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lDiscussioneSedutaDataSource.setMessages(getMessages()); 
		
		return lDiscussioneSedutaDataSource;
	}
	
	private void addPropostaDiscussione(AvvioIterAttiInBean pInBean) throws Exception {
		
		DiscussioneSedutaDataSource dsDS = getDiscussioneSedutaDataSource();
		
		DiscussioneSedutaBean dsBean = new DiscussioneSedutaBean();
		dsBean.setIdSeduta(pInBean.getIdSeduta());
		dsBean.setOrganoCollegiale(pInBean.getOrganoCollegiale());
		
		// Recupero dati della discussione
		DiscussioneSedutaBean resultDSBean = dsDS.call(dsBean);
		resultDSBean.setOrganoCollegiale(pInBean.getOrganoCollegiale());
		
		//Aggiungo nuova proposta avviata
		ArgomentiOdgXmlBean lArgomentiOdgXmlBean = new ArgomentiOdgXmlBean();
		lArgomentiOdgXmlBean.setIdUd(pInBean.getIdUd());
		lArgomentiOdgXmlBean.setTipo(pInBean.getNomeTipoDocProposta());
		lArgomentiOdgXmlBean.setFlgAggiunto("1");
		lArgomentiOdgXmlBean.setFlgElimina("0");
		lArgomentiOdgXmlBean.setOggetto(pInBean.getOggetto());
		lArgomentiOdgXmlBean.setNrOrdineOdg(null);
		if(resultDSBean.getListaArgomentiOdg() == null) {
			resultDSBean.setListaArgomentiOdg(new ArrayList<ArgomentiOdgXmlBean>());
		}
		resultDSBean.getListaArgomentiOdg().add(lArgomentiOdgXmlBean);

		// Salvo discussione aggiornata
		dsDS.saveDiscussioneSeduta(resultDSBean);
	}
	
	private ConvocazioneSedutaDataSource getConvocazioneSedutaDataSource() {	
		
		ConvocazioneSedutaDataSource lConvocazioneSedutaDataSource = new ConvocazioneSedutaDataSource();		
		lConvocazioneSedutaDataSource.setSession(getSession());
		lConvocazioneSedutaDataSource.setExtraparams(getExtraparams());	
		// devo settare in lConvocazioneSedutaDataSource i messages di AvvioIterAttiActivitiDataSource per mostrare a video gli errori
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lConvocazioneSedutaDataSource.setMessages(getMessages()); 
		
		return lConvocazioneSedutaDataSource;
	}
	
	private void addPropostaConvocazione(AvvioIterAttiInBean pInBean) throws Exception {
		
		ConvocazioneSedutaDataSource csDS = getConvocazioneSedutaDataSource();
		
		ConvocazioneSedutaBean csBean = new ConvocazioneSedutaBean();
		csBean.setIdSeduta(pInBean.getIdSeduta());
		csBean.setOrganoCollegiale(pInBean.getOrganoCollegiale());
		
		// Recupero dati della convocazione
		ConvocazioneSedutaBean resultCSBean = csDS.call(csBean);
		resultCSBean.setOrganoCollegiale(pInBean.getOrganoCollegiale());
		
		//Aggiungo nuova proposta avviata
		ArgomentiOdgXmlBean lArgomentiOdgXmlBean = new ArgomentiOdgXmlBean();
		lArgomentiOdgXmlBean.setIdUd(pInBean.getIdUd());
		lArgomentiOdgXmlBean.setTipo(pInBean.getNomeTipoDocProposta());
		lArgomentiOdgXmlBean.setFlgAggiunto("1");
		lArgomentiOdgXmlBean.setFlgElimina("0");
		lArgomentiOdgXmlBean.setOggetto(pInBean.getOggetto());
		lArgomentiOdgXmlBean.setNrOrdineOdg(null);
		if(resultCSBean.getListaArgomentiOdg() == null) {
			resultCSBean.setListaArgomentiOdg(new ArrayList<ArgomentiOdgXmlBean>());
		}
		resultCSBean.getListaArgomentiOdg().add(lArgomentiOdgXmlBean);

		// Salvo convocazione aggiornata
		csDS.salvaConvocazioneSeduta(resultCSBean);
	}
}