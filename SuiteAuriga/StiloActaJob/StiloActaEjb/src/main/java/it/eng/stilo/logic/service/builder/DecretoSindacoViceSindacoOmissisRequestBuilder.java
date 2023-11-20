/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.eng.stilo.logic.type.EIntegrationResource;

public class DecretoSindacoViceSindacoOmissisRequestBuilder extends DocumentDecretoRequestBuilder {

    @Override
    protected void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType) {
        super.doBuild(dynamicAttributesMap, idStatoDiEfficaciaNonFirmatoType);
         
       //documentoPropertiesType.setOggetto(EIntegrationResource.DETERMINA.name().toLowerCase());
    }

}
