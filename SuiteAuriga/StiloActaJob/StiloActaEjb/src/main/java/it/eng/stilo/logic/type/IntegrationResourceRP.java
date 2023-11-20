/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.stilo.logic.service.builder.AllegatoDecretoPresidenteGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoPresidenteGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeliberaGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeliberaGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeterminaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeterminaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoOrdinanzaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoOrdinanzaRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoPresidenteGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoPresidenteGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.DeliberaGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DeliberaGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.DeterminaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DeterminaRequestBuilder;
import it.eng.stilo.logic.service.builder.DocumentRequestBuilder;
import it.eng.stilo.logic.service.builder.OrdinanzaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.OrdinanzaRequestBuilder;
import it.eng.stilo.logic.service.builder.RegistroAttiRequestBuilder;
import it.eng.stilo.logic.service.builder.RegistroPubblicazioniRequestBuilder;

public class IntegrationResourceRP implements  EIntegrationResource{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, String> dynamicAttributesMap;
	
	public IntegrationResourceRP(Map<String, String> dynamicAttributesMap) {
		super();
		this.dynamicAttributesMap=dynamicAttributesMap;
	}

	@Override
	public String getCode(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceRP.DETERMINA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getCode();

		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getCode();

		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getCode();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getCode();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getCode();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getCode();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getCode();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getCode();
	        }
		}
		
		return null;
	}

	@Override
	public String getVolumeDescrPattern(String tipo) {
		logger.debug("in rp tipo " + tipo);
		if( tipo!=null && tipo.equalsIgnoreCase("DD")){
			logger.debug("restituisco " + EIntegrationResourceRP.DETERMINA.getVolumeDescrPattern());
			return EIntegrationResourceRP.DETERMINA.getVolumeDescrPattern();
		}
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getVolumeDescrPattern();
	
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeDescrPattern();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getVolumeDescrPattern();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getVolumeDescrPattern();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getVolumeDescrPattern();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeDescrPattern();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeDescrPattern();
	        }
		} 
		return null;
	}

	@Override
	public int getVolumeCurrRetention(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceRP.DETERMINA.getVolumeCurrRetention() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeCurrRetention();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getVolumeCurrRetention();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getVolumeCurrRetention();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getVolumeCurrRetention();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeCurrRetention();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeCurrRetention();
	        }
		} 
		
		return 0;
	}

	@Override
	public int getVolumeGenRetention(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceRP.DETERMINA.getVolumeGenRetention() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeGenRetention();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getVolumeGenRetention();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getVolumeGenRetention();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getVolumeGenRetention();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeGenRetention();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getVolumeGenRetention();
	        }
		} 
		
		return 0;
	}

	@Override
	public EDocumentFormat getDocumentFormat(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceRP.DETERMINA.getDocumentFormat() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getDocumentFormat();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getDocumentFormat();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getDocumentFormat();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getDocumentFormat();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getDocumentFormat();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getDocumentFormat();
	        }
		} 
		
		return null;
	}

	@Override
	public EEfficacy getEfficacy(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceRP.DETERMINA.getEfficacy() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getEfficacy();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getEfficacy();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getEfficacy();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getEfficacy();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getEfficacy();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getEfficacy();
	        }
		} 
		
		return null;
	}

	@Override
	public int getValidityMonths(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase("DD"))
			return EIntegrationResourceRP.DETERMINA.getValidityMonths() ;
		if( tipo!=null && tipo.equalsIgnoreCase("DDO"))
			return EIntegrationResourceRP.DETERMINA_OMISSIS.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("ORD"))
			return EIntegrationResourceRP.ORDINANZA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORD_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO"))
			return EIntegrationResourceRP.ORDINANZA_OMISSIS.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("ORDO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO"))
			return EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DPGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGR_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO"))
			return EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase("DELGRO_ALL"))
			return EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("REG")){
			if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("tipoAtto") && dynamicAttributesMap.get("tipoAtto")!=null ){
				String tipoAtto = dynamicAttributesMap.get("tipoAtto");
				if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("determina")){
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getValidityMonths();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("ordinanza")){
					return EIntegrationResourceRP.REGISTRO_ORDINANZE.getValidityMonths();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("decreto")){
					return EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getValidityMonths();
				} else if( tipoAtto!=null && tipoAtto.equalsIgnoreCase("delibera")){
					return EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getValidityMonths();
				} else {
					return EIntegrationResourceRP.REGISTRO_DETERMINE.getValidityMonths();
				}
			} else {
	        	return EIntegrationResourceRP.REGISTRO_DETERMINE.getValidityMonths();
	        }
		} 
		
		return 0;
	}

	@Override
	public void setBuilders(Map<String, Supplier<DocumentRequestBuilder>> builders) {
		builders.put(EIntegrationResourceRP.DETERMINA.getCode(), DeterminaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.DETERMINA_OMISSIS.getCode(), DeterminaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_DETERMINA.getCode(),  AllegatoDeterminaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_DETERMINA_OMISSIS.getCode(), AllegatoDeterminaOmissisRequestBuilder::new);
       
        builders.put(EIntegrationResourceRP.ORDINANZA.getCode(), OrdinanzaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_ORDINANZA.getCode(), AllegatoOrdinanzaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ORDINANZA_OMISSIS.getCode(), OrdinanzaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_ORDINANZA_OMISSIS.getCode(), AllegatoOrdinanzaOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA.getCode(), DecretoPresidenteGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA.getCode(), AllegatoDecretoPresidenteGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getCode(), DecretoPresidenteGiuntaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS.getCode(), AllegatoDecretoPresidenteGiuntaOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceRP.DELIBERA_GIUNTA.getCode(), DeliberaGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA.getCode(), AllegatoDeliberaGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceRP.DELIBERA_GIUNTA_OMISSIS.getCode(), DeliberaGiuntaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceRP.ALLEGATO_DELIBERA_GIUNTA_OMISSIS.getCode(), AllegatoDeliberaGiuntaOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceRP.REGISTRO_DETERMINE.getCode(), RegistroAttiRequestBuilder::new);
        builders.put(EIntegrationResourceRP.REGISTRO_DELIBERE_GIUNTA.getCode(), RegistroAttiRequestBuilder::new);
        builders.put(EIntegrationResourceRP.REGISTRO_DECRETI_PRESIDENTE_GIUNTA.getCode(), RegistroAttiRequestBuilder::new);
        builders.put(EIntegrationResourceRP.REGISTRO_ORDINANZE.getCode(), RegistroAttiRequestBuilder::new);
        builders.put(EIntegrationResourceRP.REGISTRO_PUBBLICAZIONI.getCode(), RegistroPubblicazioniRequestBuilder::new);
	} 
    
	
	
}
