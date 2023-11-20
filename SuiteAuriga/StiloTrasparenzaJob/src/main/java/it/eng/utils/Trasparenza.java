/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.bean.ProvvedimentoTrasparenzaBean;
import it.eng.utility.client.trasparenza.ClientSpringTrasparenza;
import it.eng.utility.client.trasparenza.ClientTrasparenza;
import it.eng.utility.data.InsertProvvedimentoTrasparenzaRequest;
import it.eng.utility.data.InsertProvvedimentoTrasparenzaResponse;

public class Trasparenza {
	
	private static Logger log = Logger.getLogger(Trasparenza.class);
	
	public Trasparenza() {
		
	}
	
	public ProvvedimentoTrasparenzaBean aggiungiRecordTrasparenza(ProvvedimentoTrasparenzaBean bean) {
		
		try {
			InsertProvvedimentoTrasparenzaRequest input = new InsertProvvedimentoTrasparenzaRequest();
			input.setAnnoProvvedimento(bean.getAnno());
			input.setDataProvvedimento(bean.getDataProv());
			input.setNumeroProvvedimento(bean.getNumProv());
			input.setOggettoProvvedimeno(bean.getOggetto());
			input.setSemestreProvvedimento(bean.getSemestre());
			input.setTipoProvvedimento(bean.getTipoProvvedimento());
			
			// chiamata a servizio rest
			ClientTrasparenza client = ClientSpringTrasparenza.getClient();
			InsertProvvedimentoTrasparenzaResponse response = client.inserisciProvvedimentoTrasparenza(input);
			
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
