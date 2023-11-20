/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.type.ActaEnte;
import it.eng.stilo.logic.type.EIntegrationResource;
import it.eng.stilo.logic.type.EIntegrationResourceRP;
import it.eng.stilo.logic.type.IntegrationResourceCMTO;
import it.eng.stilo.logic.type.IntegrationResourceCOTO;
import it.eng.stilo.logic.type.IntegrationResourceRP;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RequestBuilderFactory {

    final static Map<String, Supplier<DocumentRequestBuilder>> builders = new HashMap<>();
  //  final static Map<EIntegrationResource, Supplier<DocumentRequestBuilder>> builders = new HashMap<>();

    static {
    	ActaEnte actaEnte = new ActaEnte();
		String ente = actaEnte.getEnte();
		
		if( ente!=null && ente.equalsIgnoreCase("RP")){
			
			IntegrationResourceRP intRP = new IntegrationResourceRP(null);
			intRP.setBuilders(builders);
			
//    		builders.put(EIntegrationResource.DETERMINA, DeterminaRequestBuilder::new);
//	        builders.put(EIntegrationResource.DETERMINA_OMISSIS, DeterminaOmissisRequestBuilder::new);
//	        builders.put(EIntegrationResource.REGISTRO_TRIMESTRALE_DETERMINE, RegistroDetermineRequestBuilder::new);
//	        builders.put(EIntegrationResource.ALLEGATO_DETERMINA,  AllegatoDeterminaRequestBuilder::new);
//	        builders.put(EIntegrationResource.ALLEGATO_DETERMINA_OMISSIS, AllegatoDeterminaOmissisRequestBuilder::new);
		}
		
		if( ente!=null && ente.equalsIgnoreCase("CMTO")){
			
			IntegrationResourceCMTO intCMTO = new IntegrationResourceCMTO();
			intCMTO.setBuilders(builders);
			
//    		builders.put(EIntegrationResource.DETERMINA, DeterminaRequestBuilder::new);
//	        builders.put(EIntegrationResource.DETERMINA_OMISSIS, DeterminaOmissisRequestBuilder::new);
//	        builders.put(EIntegrationResource.REGISTRO_TRIMESTRALE_DETERMINE, RegistroDetermineRequestBuilder::new);
//	        builders.put(EIntegrationResource.ALLEGATO_DETERMINA,  AllegatoDeterminaRequestBuilder::new);
//	        builders.put(EIntegrationResource.ALLEGATO_DETERMINA_OMISSIS, AllegatoDeterminaOmissisRequestBuilder::new);
		}
		
		if( ente!=null && ente.equalsIgnoreCase("COTO")){
			
			IntegrationResourceCOTO intCOTO = new IntegrationResourceCOTO();
			intCOTO.setBuilders(builders);
			
//    		builders.put(EIntegrationResource.DETERMINA, DeterminaRequestBuilder::new);
//	        builders.put(EIntegrationResource.DETERMINA_OMISSIS, DeterminaOmissisRequestBuilder::new);
//	        builders.put(EIntegrationResource.REGISTRO_TRIMESTRALE_DETERMINE, RegistroDetermineRequestBuilder::new);
//	        builders.put(EIntegrationResource.ALLEGATO_DETERMINA,  AllegatoDeterminaRequestBuilder::new);
//	        builders.put(EIntegrationResource.ALLEGATO_DETERMINA_OMISSIS, AllegatoDeterminaOmissisRequestBuilder::new);
		}
    } 

    public static DocumentRequestBuilder getBuilder(final String documentType, Map<String, String> dynamicAttributesMap) {
        Supplier<DocumentRequestBuilder> builder = builders.get(documentType);
        if (builder != null) {
            return builder.get();
        }
        throw new IllegalArgumentException("BuilderNotFound[" + documentType + "]");
    }

}
