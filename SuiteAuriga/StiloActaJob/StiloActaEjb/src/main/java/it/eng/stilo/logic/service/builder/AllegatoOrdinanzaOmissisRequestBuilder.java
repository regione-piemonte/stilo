/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.eng.stilo.logic.type.ActaEnte;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EIntegrationResource;
import it.eng.stilo.logic.type.EIntegrationResourceCMTO;
import it.eng.stilo.logic.type.EIntegrationResourceCOTO;
import it.eng.stilo.logic.type.EIntegrationResourceRP;

public class AllegatoOrdinanzaOmissisRequestBuilder extends AllegatoOrdinanzaRequestBuilder {

    @Override
    protected void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType) {
        super.doBuild(dynamicAttributesMap, idStatoDiEfficaciaNonFirmatoType);
        
        if( dynamicAttributesMap.containsKey("oggetto") && dynamicAttributesMap.get("oggetto")!=null)
    		documentoPropertiesType.setOggetto( dynamicAttributesMap.get("oggetto") );
    	else {
    		ActaEnte actaEnte = new ActaEnte();
    		String ente = actaEnte.getEnte();
    		
    		if( ente!=null && ente.equalsIgnoreCase("RP")){
    			documentoPropertiesType.setOggetto(EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.name().toLowerCase());
    		}
    		if( ente!=null && ente.equalsIgnoreCase("CMTO")){
    			documentoPropertiesType.setOggetto(EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.name().toLowerCase());
    		}
    		if( ente!=null && ente.equalsIgnoreCase("COTO")){
    			documentoPropertiesType.setOggetto(EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.name().toLowerCase());
    		}
    	}
        documentoArchivisticoIRC.setClassificazionePrincipale(documentRequestBean.getClassificationId());
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati personali (fisso e true per tutti i tipi)
        documentoPropertiesType.setDatiPersonali( getValue(EAttributeType.PERSONALE) );
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati riservati (fisso a false)
        documentoPropertiesType.setDatiRiservati(getValue(EAttributeType.RISERVATO));
    }

}
