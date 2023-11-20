/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EIntegrationResource;

public class DeterminaOmissisRequestBuilder extends DocumentDeterminaRequestBuilder {

    @Override
    protected void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType) {
        super.doBuild( dynamicAttributesMap, idStatoDiEfficaciaNonFirmatoType );
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati sensibili (fisso 
        documentoPropertiesType.setDatiSensibili(getValue(EAttributeType.SENSIBILE));
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati sensibili (fisso a false)
        documentoPropertiesType.setDatiSensibili(getValue(EAttributeType.SENSIBILE));
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati riservati (fisso a false)
        documentoPropertiesType.setDatiRiservati(getValue(EAttributeType.RISERVATO));
       
        //documentoPropertiesType.setOggetto(EIntegrationResource.DETERMINA_OMISSIS.name().toLowerCase());
    }

}
