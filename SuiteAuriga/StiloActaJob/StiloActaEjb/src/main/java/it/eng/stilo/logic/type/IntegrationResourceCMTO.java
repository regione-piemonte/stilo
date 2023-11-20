/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.stilo.logic.service.builder.AllegatoDecretoOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoPresidenteGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoPresidenteGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoSindacoConsigliereBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoSindacoConsigliereOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoSindacoViceSindacoBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDecretoSindacoViceSindacoOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeliberaConsiglioOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeliberaConsiglioRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeterminaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoDeterminaRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoLiquidazioniRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoOrdinanzaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.AllegatoOrdinanzaRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoPresidenteGiuntaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoPresidenteGiuntaRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoSindacoConsigliereOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoSindacoConsigliereRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoSindacoViceSindacoOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DecretoSindacoViceSindacoRequestBuilder;
import it.eng.stilo.logic.service.builder.DeliberaConsiglioOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DeliberaConsiglioRequestBuilder;
import it.eng.stilo.logic.service.builder.DeterminaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.DeterminaRequestBuilder;
import it.eng.stilo.logic.service.builder.DocumentRequestBuilder;
import it.eng.stilo.logic.service.builder.LiquidazioniRequestBuilder;
import it.eng.stilo.logic.service.builder.OrdinanzaOmissisRequestBuilder;
import it.eng.stilo.logic.service.builder.OrdinanzaRequestBuilder;
import it.eng.stilo.logic.service.builder.RegistroAttiRequestBuilder;
import it.eng.stilo.logic.service.builder.RegistroPubblicazioniRequestBuilder;

public class IntegrationResourceCMTO implements  EIntegrationResource {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String getCode(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) )
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase(CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getCode();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getCode();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getCode();
		return null;
	}

	@Override
	public String getVolumeDescrPattern(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) ){
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getVolumeDescrPattern();}
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase(CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getVolumeDescrPattern();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getVolumeDescrPattern();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getVolumeDescrPattern();
		return null;
	}

	@Override
	public int getVolumeCurrRetention(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) )
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getVolumeCurrRetention() ;
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getVolumeCurrRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getVolumeCurrRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getVolumeCurrRetention();
		
		return 0;
	}

	@Override
	public int getVolumeGenRetention(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) )
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getVolumeGenRetention() ;
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getVolumeGenRetention(); 
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getVolumeGenRetention();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getVolumeGenRetention();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getVolumeGenRetention();
		return 0;
	}

	@Override
	public EDocumentFormat getDocumentFormat(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) )
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getDocumentFormat() ;
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getDocumentFormat(); 
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getDocumentFormat();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getDocumentFormat();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getDocumentFormat();
		
		return null;
	}

	@Override
	public EEfficacy getEfficacy(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) )
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getEfficacy() ;
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getEfficacy(); 
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getEfficacy();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getEfficacy();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getEfficacy();
		
		return null;
	}

	@Override
	public int getValidityMonths(String tipo) {
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA ) )
			return EIntegrationResourceCMTO.DETERMINA_CMTO.getValidityMonths() ;
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB ) )
			return EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DETERMINA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("DDRT"))
			return EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA ) )
			return EIntegrationResourceCMTO.ORDINANZA_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB ) )
			return EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_ORDINANZA_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL))
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO ) )
			return EIntegrationResourceCMTO.DECRETO_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB ) )
			return EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB ) )
			return EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB ) )
			return EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_DELIBERACONSIGLIO_PUB_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getValidityMonths(); 
		
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE ) )
			return EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getValidityMonths();
		if( tipo!=null && tipo.equalsIgnoreCase( CODICE_LIQUIDAZIONE_ALL ) )
			return EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getValidityMonths();
		
		if( tipo!=null && tipo.equalsIgnoreCase("RPUBBL"))
			return EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getValidityMonths();
		
		return 0;
	}

	@Override
	public void setBuilders(Map<String, Supplier<DocumentRequestBuilder>> builders) {
		builders.put(EIntegrationResourceCMTO.DETERMINA_CMTO.getCode(), DeterminaRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.DETERMINA_OMISSIS_CMTO.getCode(), DeterminaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DETERMINA_CMTO.getCode(),  AllegatoDeterminaRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DETERMINA_OMISSIS_CMTO.getCode(), AllegatoDeterminaOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.REGISTRO_TRIMESTRALE_DETERMINE_CMTO.getCode(), RegistroAttiRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.REGISTRO_PUBBLICAZIONI_CMTO.getCode(), RegistroPubblicazioniRequestBuilder::new);
       
        builders.put(EIntegrationResourceCMTO.ORDINANZA_CMTO.getCode(), OrdinanzaRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_CMTO.getCode(), AllegatoOrdinanzaRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ORDINANZA_OMISSIS_CMTO.getCode(), OrdinanzaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_ORDINANZA_OMISSIS_CMTO.getCode(), AllegatoOrdinanzaOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_CMTO.getCode(), DecretoPresidenteGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO.getCode(), AllegatoDecretoPresidenteGiuntaRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getCode(), DecretoPresidenteGiuntaOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO.getCode(), AllegatoDecretoPresidenteGiuntaOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.DECRETO_CMTO.getCode(), DecretoRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETO_CMTO.getCode(), AllegatoDecretoRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.DECRETO_OMISSIS_CMTO.getCode(), DecretoOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETO_OMISSIS_CMTO.getCode(), AllegatoDecretoOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_CMTO.getCode(), DecretoSindacoConsigliereRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO.getCode(), AllegatoDecretoSindacoConsigliereBuilder::new);
        builders.put(EIntegrationResourceCMTO.DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getCode(), DecretoSindacoConsigliereOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO.getCode(), AllegatoDecretoSindacoConsigliereOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_CMTO.getCode(), DecretoSindacoViceSindacoRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO.getCode(), AllegatoDecretoSindacoViceSindacoBuilder::new);
        builders.put(EIntegrationResourceCMTO.DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getCode(), DecretoSindacoViceSindacoOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO.getCode(), AllegatoDecretoSindacoViceSindacoOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_CMTO.getCode(), DeliberaConsiglioRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_CMTO.getCode(), AllegatoDeliberaConsiglioRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.DELIBERA_CONSIGLIO_OMISSIS_CMTO.getCode(), DeliberaConsiglioOmissisRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO.getCode(), AllegatoDeliberaConsiglioOmissisRequestBuilder::new);
        
        builders.put(EIntegrationResourceCMTO.LIQUIDAZIONI_CMTO.getCode(), LiquidazioniRequestBuilder::new);
        builders.put(EIntegrationResourceCMTO.ALLEGATO_LIQUIDAZIONI_CMTO.getCode(), AllegatoLiquidazioniRequestBuilder::new);
	} 
    
	
}
