/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.bean.ProvvedimentoTrasparenzaBean;
import it.eng.utility.client.trasparenza.ClientClearo;
import it.eng.utility.client.trasparenza.ClientSpringClearo;
import it.eng.utility.client.trasparenza.bean.Provvedimento;
import it.eng.utility.client.trasparenza.bean.ProvvedimentoOutput;

public class Trasparenza {
	
	private static Logger log = Logger.getLogger(Trasparenza.class);
	
	public Trasparenza() {
		
	}
	
	public ProvvedimentoTrasparenzaBean aggiungiRecordTrasparenza(ProvvedimentoTrasparenzaBean bean) {
		
		try {
			Provvedimento provvedimento = new Provvedimento();
			//provvedimento.setAliasIndiceFascicolo(bean.getAliasIndiceFascicolo());
			//provvedimento.setAliasProvvedimento(bean.getAliasProvvedimento());
			provvedimento.setAnno(bean.getAnno());
			provvedimento.setDataProv(bean.getDataProv());
			provvedimento.setFlagConcessione(bean.getFlagConcessione());
			provvedimento.setNumProv(bean.getNumProv());
			provvedimento.setOggetto(bean.getOggetto());
			provvedimento.setSemestre(bean.getSemestre());
			//provvedimento.setSpesa(bean.getSpesa());
			//provvedimento.setUrlIndiceFascicolo(bean.getUrlIndiceFascicolo());
			//provvedimento.setUrlProvvedimento(bean.getUrlProvvedimento());
			
			// chiamata a servizio WS
			ClientClearo client = ClientSpringClearo.getClient();
			ProvvedimentoOutput response = client.aggiungiRecordTrasparenza(provvedimento);
			
			if (response != null && response.isResult()) {
				bean.setEsitoAddRecordTrasparenza("OK");
				bean.setErrorMsgAddRecordTrasparenza(null);
				
				log.info("WS Clearo eseguito correttamente");
			}
			else {
				bean.setEsitoAddRecordTrasparenza("KO");
				bean.setErrorMsgAddRecordTrasparenza(response.getErrorMsg());
				
				log.info("Errore nel WS Clearo " + response.getErrorMsg());
			}
		} catch (Exception e) {
			bean.setEsitoAddRecordTrasparenza("KO");
			bean.setErrorMsgAddRecordTrasparenza(e.getMessage());
			
			log.error(e.getMessage());
		}
		
		return bean;
	}
	
}
