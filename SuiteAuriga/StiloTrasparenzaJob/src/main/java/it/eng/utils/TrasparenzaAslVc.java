/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utils;

import org.apache.log4j.Logger;

import it.eng.bean.ProvvedimentoTrasparenzaBean;
import it.eng.utility.client.trasparenza.ClientSpringTrasparenzaAslVc;
import it.eng.utility.client.trasparenza.ClientTrasparenzaAslVc;
import it.eng.utility.data.InsertProvvedimentoTrasparenzaAslVcRequest;
import it.eng.utility.data.InsertProvvedimentoTrasparenzaResponse;

public class TrasparenzaAslVc {
	
	private static Logger log = Logger.getLogger(TrasparenzaAslVc.class);
	
	public TrasparenzaAslVc() {
		
	}
	
	public ProvvedimentoTrasparenzaBean aggiungiDeterminaTrasparenzaAslVc(ProvvedimentoTrasparenzaBean bean) {
		
		try {
			InsertProvvedimentoTrasparenzaAslVcRequest input = new InsertProvvedimentoTrasparenzaAslVcRequest();
			input.setAnnoProvvedimento(bean.getAnno());
			input.setMeseProvvedimento(bean.getMese());
			input.setDataProvvedimento(bean.getDataProv());
			input.setNumeroProvvedimento(bean.getNumProv());
			input.setOggettoProvvedimeno(bean.getOggetto());
			input.setTipoProvvedimento(bean.getTipoProvvedimento());
			input.setDataPubblicazioneDal(bean.getDataPubblicazioneDal());
			input.setDataPubblicazioneAl(bean.getDataPubblicazioneAl());
			
			// chiamata a servizio rest
			ClientTrasparenzaAslVc client = ClientSpringTrasparenzaAslVc.getClient();
			InsertProvvedimentoTrasparenzaResponse response = client.inserisciDeterminaTrasparenzaAslVc(input);
			
			if (response != null && response.isEsito()) {
				bean.setEsitoAddRecordTrasparenza("OK");
				bean.setErrorMsgAddRecordTrasparenza(null);
				
				log.info("Servizio rest Clearo eseguito correttamente");
			}
			else {
				bean.setEsitoAddRecordTrasparenza("KO");
				bean.setErrorMsgAddRecordTrasparenza(response.getResponseMsg());
				
				log.info("Errore nel servizio rest Clearo " + response.getResponseMsg());
			}
		} catch (Exception e) {
			bean.setEsitoAddRecordTrasparenza("KO");
			bean.setErrorMsgAddRecordTrasparenza(e.getMessage());
			
			log.error(e.getMessage());
		}
		
		return bean;
	}
	
	public ProvvedimentoTrasparenzaBean aggiungiDeliberaTrasparenzaAslVc(ProvvedimentoTrasparenzaBean bean) {
		
		try {
			InsertProvvedimentoTrasparenzaAslVcRequest input = new InsertProvvedimentoTrasparenzaAslVcRequest();
			input.setAnnoProvvedimento(bean.getAnno());
			input.setMeseProvvedimento(bean.getMese());
			input.setDataProvvedimento(bean.getDataProv());
			input.setNumeroProvvedimento(bean.getNumProv());
			input.setOggettoProvvedimeno(bean.getOggetto());
			input.setTipoProvvedimento(bean.getTipoProvvedimento());
			input.setDataPubblicazioneDal(bean.getDataPubblicazioneDal());
			input.setDataPubblicazioneAl(bean.getDataPubblicazioneAl());
			
			// chiamata a servizio rest
			ClientTrasparenzaAslVc client = ClientSpringTrasparenzaAslVc.getClient();
			InsertProvvedimentoTrasparenzaResponse response = client.inserisciDeliberaTrasparenzaAslVc(input);
			
			if (response != null && response.isEsito()) {
				bean.setEsitoAddRecordTrasparenza("OK");
				bean.setErrorMsgAddRecordTrasparenza(null);
				
				log.info("Servizio rest Clearo eseguito correttamente");
			}
			else {
				bean.setEsitoAddRecordTrasparenza("KO");
				bean.setErrorMsgAddRecordTrasparenza(response.getResponseMsg());
				
				log.info("Errore nel servizio rest Clearo " + response.getResponseMsg());
			}
		} catch (Exception e) {
			bean.setEsitoAddRecordTrasparenza("KO");
			bean.setErrorMsgAddRecordTrasparenza(e.getMessage());
			
			log.error(e.getMessage());
		}
		
		return bean;
	}
	
}
