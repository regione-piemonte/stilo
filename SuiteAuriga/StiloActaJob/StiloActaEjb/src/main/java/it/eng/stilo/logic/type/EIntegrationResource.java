/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import java.util.function.Supplier;

import it.eng.stilo.logic.service.builder.DocumentRequestBuilder;

public interface EIntegrationResource {

    public String getCode(String tipo);
    public String getVolumeDescrPattern(String tipo);
    public int getVolumeCurrRetention(String tipo);
    public int getVolumeGenRetention(String tipo);
    public EDocumentFormat getDocumentFormat(String tipo);
    public EEfficacy getEfficacy(String tipo);
    public int getValidityMonths(String tipo);
    
    public void setBuilders(Map<String, Supplier<DocumentRequestBuilder>> builders);
    
    public static String CODICE_DETERMINA="DD";
    public static String CODICE_DETERMINA_PUB="DDO";
    public static String CODICE_DETERMINA_ALL="DD_ALL";
    public static String CODICE_DETERMINA_PUB_ALL="DDO_ALL";
    
    public static String CODICE_DECRETO="DCR";
    public static String CODICE_DECRETO_PUB="DCRO";
    public static String CODICE_DECRETO_ALL="DCR_ALL";
    public static String CODICE_DECRETO_PUB_ALL="DCRO_ALL";
    
    public static String CODICE_DECRETO_PRESIDENTE_GIUNTA="DPGR";
    public static String CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB="DPGRO";
    public static String CODICE_DECRETO_PRESIDENTE_GIUNTA_ALL="DPGR_ALL";
    public static String CODICE_DECRETO_PRESIDENTE_GIUNTA_PUB_ALL="DPGRO_ALL";
    
    public static String CODICE_ORDINANZA="ORD";
    public static String CODICE_ORDINANZA_PUB="ORDO";
    public static String CODICE_ORDINANZA_ALL="ORD_ALL";
    public static String CODICE_ORDINANZA_PUB_ALL="ORDO_ALL";
    
    public static String CODICE_DELIBERACONSIGLIO="DELCONS";
    public static String CODICE_DELIBERACONSIGLIO_PUB="DELCONSO";
    public static String CODICE_DELIBERACONSIGLIO_ALL="DELCONS_ALL";
    public static String CODICE_DELIBERACONSIGLIO_PUB_ALL="DELCONSO_ALL";
    
    public static String CODICE_DECRETOSINDACOCONSIGLIERE="DCRC";
    public static String CODICE_DECRETOSINDACOCONSIGLIERE_PUB="DCRCO";
    public static String CODICE_DECRETOSINDACOCONSIGLIERE_ALL="DCRC_ALL";
    public static String CODICE_DECRETOSINDACOCONSIGLIERE_PUB_ALL="DCRCO_ALL";
    
    public static String CODICE_DECRETOSINDACOVICESINDACO="DCRS";
    public static String CODICE_DECRETOSINDACOVICESINDACO_PUB="DCRSO";
    public static String CODICE_DECRETOSINDACOVICESINDACO_ALL="DCRS_ALL";
    public static String CODICE_DECRETOSINDACOVICESINDACO_PUB_ALL="DCRSO_ALL";
    
    public static String CODICE_LIQUIDAZIONE="LIQ";
    public static String CODICE_LIQUIDAZIONE_ALL="LIQ_ALL";
    
}
