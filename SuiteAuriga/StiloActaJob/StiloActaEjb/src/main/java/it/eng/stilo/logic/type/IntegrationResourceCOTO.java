/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.stilo.logic.service.builder.AllegatoDecretoPresidenteGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoPresidenteGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeterminaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeterminaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoOrdinanzaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoOrdinanzaRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoPresidenteGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoPresidenteGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.DeterminaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DeterminaRequestBuilder;
import it.eng.stilo.logic.service.builder.DocumentRequestBuilder;
import it.eng.stilo.logic.service.builder.OrdinanzaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.OrdinanzaRequestBuilder;
import it.eng.stilo.logic.service.builder.RegistroAttiRequestBuilder;
import it.eng.stilo.logic.service.builder.RegistroPubblicazioniRequestBuilder;

public class IntegrationResourceCOTO implements  EIntegrationResource{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String getCode(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceCOTO.DETERMINA_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getCode();
		return null;
	}

	@Override
	public String getVolumeDescrPattern(String tipo) {
		logger.debug("in COTO tipo " + tipo);
		if( tipo!=null && tipo.equalsIgnoreCase("DD")){
			logger.debug("restituisco " + EIntegrationResourceCOTO.DETERMINA_COTO.getVolumeDescrPattern());
			return EIntegrationResourceCOTO.DETERMINA_COTO.getVolumeDescrPattern();
		}
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getVolumeDescrPattern();
		
		return null;
	}

	@Override
	public int getVolumeCurrRetention(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceCOTO.DETERMINA_COTO.getVolumeCurrRetention() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getVolumeCurrRetention();
		
		return 0;
	}

	@Override
	public int getVolumeGenRetention(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceCOTO.DETERMINA_COTO.getVolumeGenRetention() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getVolumeGenRetention();
		
		return 0;
	}

	@Override
	public EDocumentFormat getDocumentFormat(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceCOTO.DETERMINA_COTO.getDocumentFormat() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getDocumentFormat();
		
		return null;
	}

	@Override
	public EEfficacy getEfficacy(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceCOTO.DETERMINA_COTO.getEfficacy() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getEfficacy();
		
		return null;
	}

	@Override
	public int getValidityMonths(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceCOTO.DETERMINA_COTO.getValidityMonths() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceCOTO.ORDINANZA_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getValidityMonths();
		
		return 0;
	}

	@Override
	public void setBuilders(Map<String, Supplier<DocumentRequestBuilder>> builders) {
		builders.put(EIntegrationResourceCOTO.DETERMINA_COTO.getCode(), DeterminaRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.DETERMINA_OMISSIS_COTO.getCode(), DeterminaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.REGISTRO_TRIMESTRALE_DETERMINE_COTO.getCode(), RegistroAttiRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.REGISTRO_PUBBLICAZIONI_COTO.getCode(), RegistroPubblicazioniRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ALLEGATO_DETERMINA_COTO.getCode(),  AllegatoDeterminaRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ALLEGATO_DETERMINA_OMISSIS_COTO.getCode(), AllegatoDeterminaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ORDINANZA_COTO.getCode(), OrdinanzaRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_COTO.getCode(), AllegatoOrdinanzaRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ORDINANZA_OMISSIS_COTO.getCode(), OrdinanzaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ALLEGATO_ORDINANZA_OMISSIS_COTO.getCode(), AllegatoOrdinanzaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_COTO.getCode(), DecretoPresidenteGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO.getCode(), AllegatoDecretoPresidenteGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getCode(), DecretoPresidenteGiuntaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCOTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO.getCode(), AllegatoDecretoPresidenteGiuntaOmissisRequestBuilder::new);
	} 
    
	
	
}
